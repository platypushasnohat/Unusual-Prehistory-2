package com.unusualmodding.unusual_prehistory.entity;

import com.google.common.annotations.VisibleForTesting;
import com.unusualmodding.unusual_prehistory.entity.base.AncientEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2Sounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class KimmeridgebrachypteraeschnidiumNymphEntity extends PathfinderMob {

    public static final EntityDataAccessor<Integer> LOOKOUT_COOLDOWN = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumNymphEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> LOOKOUT_TIMER = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumNymphEntity.class, EntityDataSerializers.INT);

    @VisibleForTesting
    public static int ticksToBeDragonfly = Math.abs(-24000);
    private int age;

    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState lookoutAnimationState = new AnimationState();

    public KimmeridgebrachypteraeschnidiumNymphEntity(EntityType<? extends AncientEntity> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0D).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new KimmeridgebrachypteraeschnidiumNymphLookoutGoal(this));
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.6F;
    }

    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            this.setAge(this.age + 1);
        }

        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }

        if (this.getLookoutCooldown() > 0) {
            this.setLookoutCooldown(this.getLookoutCooldown() - 1);
        }
        if (this.getLookoutTimer() > 0) {
            this.setLookoutTimer(this.getLookoutTimer() - 1);
            if (this.getLookoutTimer() == 0) {
                this.lookoutCooldown();
            }
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
        this.walkAnimationState.animateWhen(this.walkAnimation.isMoving(), this.tickCount);
        this.lookoutAnimationState.animateWhen(this.getLookoutCooldown() == 0, this.tickCount);
        if (this.getLookoutTimer() > 0) {
            this.idleAnimationState.stop();
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LOOKOUT_COOLDOWN, 2 * 20 + random.nextInt(12 * 20));
        this.entityData.define(LOOKOUT_TIMER, 0);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Age", this.age);
        compoundTag.putInt("LookoutCooldown", this.getLookoutCooldown());
        compoundTag.putInt("LookoutTimer", this.getLookoutTimer());
    }

    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAge(compoundTag.getInt("Age"));
        this.setLookoutCooldown(compoundTag.getInt("LookoutCooldown"));
        this.setLookoutTimer(compoundTag.getInt("LookoutTimer"));
    }

    public int getLookoutTimer() {
        return this.entityData.get(LOOKOUT_TIMER);
    }
    public void setLookoutTimer(int timer) {
        this.entityData.set(LOOKOUT_TIMER, timer);
    }

    public int getLookoutCooldown() {
        return this.entityData.get(LOOKOUT_COOLDOWN);
    }
    public void setLookoutCooldown(int cooldown) {
        this.entityData.set(LOOKOUT_COOLDOWN, cooldown);
    }
    public void lookoutCooldown() {
        this.entityData.set(LOOKOUT_COOLDOWN, 6 * 20 + random.nextInt(30 * 2 * 20));
    }

    private int getAge() {
        return this.age;
    }

    private void ageUp(int pOffset) {
        this.setAge(this.age + pOffset * 20);
    }

    private void setAge(int pAge) {
        this.age = pAge;
        if (this.age >= ticksToBeDragonfly) {
            this.ageUp();
        }
    }

    private void ageUp() {
        Level level = this.level();
        if (level instanceof ServerLevel serverLevel) {
            KimmeridgebrachypteraeschnidiumEntity dragonfly = UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get().create(this.level());
            if (dragonfly != null) {
                dragonfly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                dragonfly.finalizeSpawn(serverLevel, this.level().getCurrentDifficultyAt(dragonfly.blockPosition()), MobSpawnType.CONVERSION, null, null);
                dragonfly.setNoAi(this.isNoAi());
                if (this.hasCustomName()) {
                    dragonfly.setCustomName(this.getCustomName());
                    dragonfly.setCustomNameVisible(this.isCustomNameVisible());
                }
                dragonfly.setPersistenceRequired();
                this.playSound(SoundEvents.TADPOLE_GROW_UP, 0.15F, 1.0F);
                serverLevel.addFreshEntityWithPassengers(dragonfly);
                this.discard();
            }
        }
    }

    private int getTicksLeftUntilAdult() {
        return Math.max(0, ticksToBeDragonfly - this.age);
    }

    @Override
    @NotNull
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    protected @NotNull MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    // sounds
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2Sounds.KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UP2Sounds.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH.get();
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
    }

    @Override
    protected float getSoundVolume() {
        return 0.25F;
    }

    // goals
    private static class KimmeridgebrachypteraeschnidiumNymphLookoutGoal extends Goal {

        KimmeridgebrachypteraeschnidiumNymphEntity nymph;

        public KimmeridgebrachypteraeschnidiumNymphLookoutGoal(KimmeridgebrachypteraeschnidiumNymphEntity dragonfly) {
            this.nymph = dragonfly;
        }

        @Override
        public boolean canUse() {
            return this.nymph.getLookoutCooldown() == 0 && this.nymph.onGround();
        }

        @Override
        public boolean canContinueToUse() {
            return this.nymph.getLookoutTimer() > 0 && this.nymph.onGround();
        }

        @Override
        public void start() {
            super.start();
            this.nymph.setLookoutTimer(60);
        }

        @Override
        public void tick() {
            super.tick();
        }

        @Override
        public void stop() {
            super.stop();
            this.nymph.lookoutCooldown();
        }
    }
}
