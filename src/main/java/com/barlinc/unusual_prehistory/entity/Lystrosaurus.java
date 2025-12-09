package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2DamageTypeTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Lystrosaurus extends PrehistoricMob {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState eatAnimationState = new AnimationState();
    public final AnimationState biteAnimationState = new AnimationState();
    public final AnimationState bounceAnimationState = new AnimationState();
    public final AnimationState sitStartAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sitEndAnimationState = new AnimationState();
    public final AnimationState sleepStartAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepEndAnimationState = new AnimationState();
    public final AnimationState digAnimationState = new AnimationState();
    public final AnimationState shakeAnimationState = new AnimationState();
    public final AnimationState scratch1AnimationState = new AnimationState();
    public final AnimationState scratch2AnimationState = new AnimationState();
    public final AnimationState vocalAnimationState = new AnimationState();

    public Lystrosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.LYSTROSAURUS_FOOD), false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.ARMOR, 20.0F);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.9F;
    }

    @Override
    public double getFluidJumpThreshold() {
        if (useLowerFluidJumpThreshold) {
            return super.getFluidJumpThreshold();
        }
        return 0.5 * getBbHeight();
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            this.getNavigation().stop();
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.LYSTROSAURUS_FOOD);
    }

    @Override
    public int getHealCooldown() {
        return 80;
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float amount) {
        super.actuallyHurt(damageSource, amount * 0.15F);
    }

    @Override
    public void tick() {
        super.tick();

//        if (!this.level().isClientSide) {
//            this.bounceUp();
//        }
    }

    private void bounceUp() {
        Vec3 vec3 = this.getDeltaMovement();
        if (vec3.y < 0.0D && this.onGround()) {
            this.level().broadcastEntityEvent(this, (byte) 69);
            this.setDeltaMovement(vec3.x, -vec3.y * 8, vec3.z);
        }
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
        return false;
    }

    @Override
    public void setupAnimationCooldowns() {
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    public void handleEntityEvent(byte id) {
        switch (id) {
            case 67 -> this.biteAnimationState.start(this.tickCount);
            case 68 -> this.biteAnimationState.stop();
            case 69 -> this.bounceAnimationState.start(this.tickCount);
            case 70 -> this.bounceAnimationState.stop();
            default -> super.handleEntityEvent(id);
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(UP2DamageTypeTags.LYSTROSAURUS_IMMUNE_TO);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 4;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.LYSTROSAURUS.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.LYSTROSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.LYSTROSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.LYSTROSAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.LYSTROSAURUS_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }

    public static boolean canSpawn(EntityType<Lystrosaurus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.LYSTROSAURUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }
}
