package com.barlinc.unusual_prehistory.entity.mob.update_1;

import com.barlinc.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricAvoidEntityGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricNearestAttackableTargetGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.SleepingGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.dromaeosaurus.DromaeosaurusAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.dromaeosaurus.DromaeosaurusLeapGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.dromaeosaurus.DromaeosaurusRunGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Dromaeosaurus extends PrehistoricMob {

    public int leapCooldown = 120 + this.getRandom().nextInt(120);

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState fallAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState eepyAnimationState = new SmoothAnimationState(0.25F);

    public Dromaeosaurus(EntityType<? extends Dromaeosaurus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new DromaeosaurusSleepingGoal(this));
        this.goalSelector.addGoal(3, new DromaeosaurusLeapGoal(this));
        this.goalSelector.addGoal(4, new DromaeosaurusAttackGoal(this));
        this.goalSelector.addGoal(5, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 12.0F,1.0D, entity -> entity.getType().is(UP2EntityTags.DROMAEOSAURUS_AVOIDS)));
        this.goalSelector.addGoal(6, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(7, new TemptGoal(this, 1.0D, Ingredient.of(UP2ItemTags.DROMAEOSAURUS_FOOD), false));
        this.goalSelector.addGoal(8, new DromaeosaurusRunGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, false, entity -> entity.getType().is(UP2EntityTags.DROMAEOSAURUS_TARGETS)) {
            @Override
            public boolean canUse(){
                return super.canUse() && !Dromaeosaurus.this.isEepy() && (Dromaeosaurus.this.level().isDay() || Dromaeosaurus.this.level().dimensionType().fixedTime().isPresent());
            }

            @Override
            public boolean canContinueToUse(){
                return super.canContinueToUse() && !Dromaeosaurus.this.isEepy() && (Dromaeosaurus.this.level().isDay() || Dromaeosaurus.this.level().dimensionType().fixedTime().isPresent());
            }
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4F);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        NoSpinGroundPathNavigation navigation = new NoSpinGroundPathNavigation(this, level);
        navigation.setCanOpenDoors(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    public float getStepHeight() {
        return 1.1F;
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_DROMAEOSAURUS);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DROMAEOSAURUS_FOOD);
    }

    @Override
    public void tick () {
        super.tick();
        this.setSprinting(this.getDeltaMovement().horizontalDistance() > 0.05D);
        if (leapCooldown > 0) leapCooldown--;
    }

    @Override
    public boolean isEepyTime() {
        return this.level().isNight() && this.getHealth() > this.getMaxHealth() * 0.5F && !this.isInWater() && this.onGround() && this.getLastHurtByMob() == null && this.getTarget() == null && !this.isLeashed();
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy(), this.tickCount);
        this.fallAnimationState.animateWhen(!this.onGround() && !this.isInWaterOrBubble() && !this.onClimbable() && !this.isPassenger() && !this.isEepy(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.DROMAEOSAURUS.get().create(level);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public void travel(@NotNull Vec3 vec3) {
        if (this.refuseToMove() && this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            vec3 = vec3.multiply(0.0, 1.0, 0.0);
        }
        super.travel(vec3);
    }

    @Override
    protected void onLeashDistance(float distance) {
        if (distance > 4.0F && this.isEepy()) {
            this.stopEepy();
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isEepy() ? UP2SoundEvents.DROMAEOSAURUS_EEPY.get() : UP2SoundEvents.DROMAEOSAURUS_IDLE.get();
    }

    @Override
    public boolean canPlayAmbientSound() {
        return true;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.DROMAEOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.DROMAEOSAURUS_DEATH.get();
    }

    public static boolean canSpawn(EntityType<Dromaeosaurus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.DROMAEOSAURUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    private static class DromaeosaurusSleepingGoal extends SleepingGoal {

        public DromaeosaurusSleepingGoal(PrehistoricMob prehistoricMob) {
            super(prehistoricMob);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void stop() {
            this.prehistoricMob.setEepyCooldown(20);
            this.prehistoricMob.stopEepy();
        }
    }
}
