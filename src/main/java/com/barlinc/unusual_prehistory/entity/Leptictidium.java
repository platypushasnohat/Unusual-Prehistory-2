package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LeptictidiumRunLikeCrazyGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricAvoidEntityGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Leptictidium extends PrehistoricMob {

    public final AnimationState idleAnimationState = new AnimationState();

    public Leptictidium(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LeptictidiumRunLikeCrazyGoal(this));
        this.goalSelector.addGoal(2, new LargePanicGoal(this, 1.8D, 10, 4));
        this.goalSelector.addGoal(3, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 8.0F, 1.8D, entity -> entity.getType().is(UP2EntityTags.LEPTICTIDIUM_AVOIDS)));
        this.goalSelector.addGoal(3, new PrehistoricAvoidEntityGoal<>(this, Player.class, 8.0F, 1.8D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.LEPTICTIDIUM_FOOD), false));
        this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.8F;
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
        this.refuseToTravel(travelVec);
        super.travel(travelVec);
    }

    @Override
    public float getStepHeight() {
        return this.isRunning() ? 1.0F : 0.6F;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.LEPTICTIDIUM_FOOD);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.infectNearbyMobs();
            this.getInfectedByNearbyMobs();
        }
    }

    private void infectNearbyMobs() {
        List<LivingEntity> nearbyEntities = this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat(), this, this.getBoundingBox().inflate(3.0));
        if (!this.getActiveEffects().isEmpty() && !nearbyEntities.isEmpty()) {
            for (MobEffectInstance effectInstance : this.getActiveEffects()) {
                nearbyEntities.stream().filter(entity -> entity != this && !entity.hasEffect(effectInstance.getEffect()) && effectInstance.getDuration() > 20 && !effectInstance.getEffect().isInstantenous()).forEach(entity -> entity.addEffect(new MobEffectInstance(effectInstance.getEffect(), (int) (effectInstance.getDuration() * 0.3F), 0), this));
            }
        }
    }

    private void getInfectedByNearbyMobs() {
        List<LivingEntity> nearbyEntities = this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat(), this, this.getBoundingBox().inflate(3.0));
        if (!nearbyEntities.isEmpty()) {
            nearbyEntities.stream().filter(entity -> entity != this).forEach(entity -> {
                if (!entity.getActiveEffects().isEmpty()) {
                    for (MobEffectInstance effectInstance : entity.getActiveEffects()) {
                        if (!this.hasEffect(effectInstance.getEffect()) && effectInstance.getDuration() > 20 && !effectInstance.getEffect().isInstantenous()) {
                            this.addEffect(new MobEffectInstance(effectInstance.getEffect(), (int) (effectInstance.getDuration() * 0.3F), effectInstance.getAmplifier()), entity);
                        }
                    }
                }
            });
        }
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
            default -> super.handleEntityEvent(id);
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 4;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.LEPTICTIDIUM.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.LEPTICTIDIUM_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.LEPTICTIDIUM_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.LEPTICTIDIUM_DEATH.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 170;
    }

    public static boolean canSpawn(EntityType<Leptictidium> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.DESMATOSUCHUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }
}
