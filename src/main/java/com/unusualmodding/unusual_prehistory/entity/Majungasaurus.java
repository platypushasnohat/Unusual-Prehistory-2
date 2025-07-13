package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goal.AttackGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goal.LargePanicGoal;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2EntityTags;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.Objects;

public class Majungasaurus extends Animal {

    public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(Majungasaurus.class, EntityDataSerializers.LONG);
    public static final EntityDataAccessor<Integer> STEALTH_COOLDOWN = SynchedEntityData.defineId(Majungasaurus.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState eyesAnimationState = new AnimationState();
    public final AnimationState biteRightAnimationState = new AnimationState();
    public final AnimationState biteLeftAnimationState = new AnimationState();
    public final AnimationState enterStealthAnimationState = new AnimationState();
    public final AnimationState stealthIdleAnimationState = new AnimationState();
    public final AnimationState exitStealthAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;
    private float stealthProgress;
    private float prevStealthProgress;

    public Majungasaurus(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MajungasaurusAttackGoal(this));
        this.goalSelector.addGoal(2, new MajungasaurusPanicGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Majungasaurus.class, 300, true, true, this::canCannibalize) {
            public boolean canUse(){
                return super.canUse() && !Majungasaurus.this.isBaby();
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 600, true, false, entity -> entity.getType().is(UP2EntityTags.MAJUNGASAURUS_TARGETS)) {
            public boolean canUse(){
                return super.canUse() && !Majungasaurus.this.isBaby();
            }
        });
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 32.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.19F)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(UP2ItemTags.MAJUNGASAURUS_FOOD) && this.getHealth() < this.getMaxHealth()) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            this.heal((float) itemstack.getFoodProperties(this).getNutrition());
            this.gameEvent(GameEvent.EAT, this);
            this.playSound(SoundEvents.GENERIC_EAT, this.getSoundVolume(), this.getVoicePitch());
            for (int i = 0; i < 3; i++) {
                final double d2 = this.random.nextGaussian() * 0.02D;
                final double d0 = this.random.nextGaussian() * 0.02D;
                final double d1 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemstack), this.getX() + (double) (this.random.nextFloat() * this.getBbWidth()) - (double) this.getBbWidth() * 0.5F, this.getY() + this.getBbHeight() * 0.5F + (double) (this.random.nextFloat() * this.getBbHeight() * 0.5F), this.getZ() + (double) (this.random.nextFloat() * this.getBbWidth()) - (double) this.getBbWidth() * 0.5F, d0, d1, d2);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float amount) {
        this.exitStealthInstantly();
        super.actuallyHurt(damageSource, amount);
    }

    public float getStealthProgress(float partialTicks) {
        return (prevStealthProgress + (stealthProgress - prevStealthProgress) * partialTicks) * 0.05F;
    }

    public boolean canCannibalize(LivingEntity entity) {
        return this.canAttack(entity) && (entity.getHealth() < entity.getMaxHealth() * 0.5F || entity.isBaby());
    }

    @Override
    public void tick () {
        super.tick();

        prevStealthProgress = stealthProgress;

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        if (this.isMajungasaurusStealthMode() && stealthProgress < 20F) {
            stealthProgress++;
        }
        if (!this.isMajungasaurusStealthMode() && stealthProgress > 0F) {
            stealthProgress--;
        }

        if (this.getStealthCooldown() > 0) {
            this.setStealthCooldown(this.getStealthCooldown() - 1);
        }

        if (this.tickCount % 600 == 0 && this.getHealth() < this.getMaxHealth()) {
            this.heal(2);
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout == 0) {
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        this.eyesAnimationState.animateWhen(!this.isAggressive(), this.tickCount);

        if (this.isMajungasaurusVisuallyStealthMode()) {
            this.exitStealthAnimationState.stop();
            this.idleAnimationState.stop();

            if (this.isVisuallyStealthMode()) {
                this.enterStealthAnimationState.startIfStopped(this.tickCount);
                this.stealthIdleAnimationState.stop();
            } else {
                this.enterStealthAnimationState.stop();
                if (this.getDeltaMovement().horizontalDistance() <= 0.0) this.stealthIdleAnimationState.startIfStopped(this.tickCount);
                this.idleAnimationTimeout = 0;
            }
        } else {
            this.stealthIdleAnimationState.stop();
            this.enterStealthAnimationState.stop();
            this.exitStealthAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STEALTH_COOLDOWN, 60 + random.nextInt(10));
        this.entityData.define(LAST_POSE_CHANGE_TICK, 0L);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("StealthCooldown", this.getStealthCooldown());
        compoundTag.putLong("LastPoseTick", this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setStealthCooldown(compoundTag.getInt("StealthCooldown"));
        long l = compoundTag.getLong("LastPoseTick");
        if (l < 0L) this.setPose(UP2Poses.RESTING.get());
        this.resetLastPoseChangeTick(l);
    }

    public int getStealthCooldown() {
        return this.entityData.get(STEALTH_COOLDOWN);
    }

    public void setStealthCooldown(int cooldown) {
        this.entityData.set(STEALTH_COOLDOWN, cooldown);
    }

    public void stealthCooldown() {
        this.entityData.set(STEALTH_COOLDOWN, 60 + random.nextInt(10));
    }

    @VisibleForTesting
    public void resetLastPoseChangeTick(long l) {
        this.entityData.set(LAST_POSE_CHANGE_TICK, l);
    }

    private void resetLastPoseChangeTickToFullStand(long l) {
        this.resetLastPoseChangeTick(Math.max(0L, l - 52L - 1L));
    }

    public long getPoseTime() {
        return (this.level()).getGameTime() - Math.abs(this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    public boolean isMajungasaurusStealthMode() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
    }

    public boolean isMajungasaurusVisuallyStealthMode() {
        return this.getPoseTime() < 0L != this.isMajungasaurusStealthMode();
    }

    public boolean isInPoseTransition() {
        long l = this.getPoseTime();
        return l < (long) (20);
    }

    private boolean isVisuallyStealthMode() {
        return this.isMajungasaurusStealthMode() && this.getPoseTime() < 20L && this.getPoseTime() >= 0L;
    }

    public void enterStealth() {
        if (this.isMajungasaurusStealthMode()) return;
        this.setPose(UP2Poses.STEALTH.get());
        this.resetLastPoseChangeTick(-(this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void exitStealth() {
        if (!this.isMajungasaurusStealthMode()) {
            return;
        }
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTick((this.level()).getGameTime());
        this.stealthCooldown();
        this.refreshDimensions();
    }

    public void exitStealthInstantly() {
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTickToFullStand((this.level()).getGameTime());
        this.stealthCooldown();
        this.refreshDimensions();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == UP2Poses.BITING.get()) {
                if (this.getRandom().nextBoolean()) this.biteRightAnimationState.start(this.tickCount);
                this.biteLeftAnimationState.start(this.tickCount);
            }
            if (this.getPose() == Pose.STANDING) {
                this.biteRightAnimationState.stop();
                this.biteLeftAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return UP2Entities.MAJUNGASAURUS.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isMajungasaurusStealthMode() ? UP2SoundEvents.MAJUNGASAURUS_IDLE.get() : null;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.MAJUNGASAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.MAJUNGASAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
       if (this.isMajungasaurusStealthMode()) {
           this.playSound(SoundEvents.CAMEL_STEP, 0.25F, 0.9F);
       } else {
           this.playSound(SoundEvents.CAMEL_STEP, 1.0F, 0.9F);
       }
    }

    // goals
    static class MajungasaurusAttackGoal extends AttackGoal {

        protected final Majungasaurus majungasaurus;

        public MajungasaurusAttackGoal(Majungasaurus majungasaurus) {
            super(majungasaurus);
            this.majungasaurus = majungasaurus;
        }

        @Override
        public void start() {
            super.start();
            this.majungasaurus.setPose(Pose.STANDING);
            this.majungasaurus.exitStealth();
        }

        @Override
        public void stop() {
            super.stop();
            this.majungasaurus.setPose(Pose.STANDING);
            this.majungasaurus.exitStealth();
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !this.majungasaurus.isBaby();
        }

        @Override
        public void tick() {
            LivingEntity target = this.majungasaurus.getTarget();
            if (target != null) {
                double distanceToTarget = this.majungasaurus.getPerceivedTargetDistanceSquareForMeleeAttack(target);
                Pose pose = this.majungasaurus.getPose();

                this.majungasaurus.getLookControl().setLookAt(target, 30F, 30F);
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);

                if (this.majungasaurus.getSensing().hasLineOfSight(target) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 0.0 || this.majungasaurus.getRandom().nextFloat() < 0.05F)) {
                    this.pathedTargetX = target.getX();
                    this.pathedTargetY = target.getY();
                    this.pathedTargetZ = target.getZ();
                    this.ticksUntilNextPathRecalculation = 4 + this.majungasaurus.getRandom().nextInt(7);

                    if (distanceToTarget > 1024.0) this.ticksUntilNextPathRecalculation += 10;
                    else if (distanceToTarget > 256.0) this.ticksUntilNextPathRecalculation += 5;

                    if (!this.majungasaurus.getNavigation().moveTo(target, 1.0D)) this.ticksUntilNextPathRecalculation += 15;

                    this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
                }

                this.path = this.majungasaurus.getNavigation().createPath(target, 0);

                if (distanceToTarget > 50 && this.majungasaurus.getStealthCooldown() <= 0) {
                    this.majungasaurus.enterStealth();
                    this.majungasaurus.getNavigation().moveTo(this.path, 1.0D);
                }
                if (distanceToTarget <= 50 || this.majungasaurus.getStealthCooldown() > 0) {
                    this.majungasaurus.exitStealth();
                    this.majungasaurus.getNavigation().moveTo(this.path, 1.7D);
                }

                if (pose == UP2Poses.BITING.get()) tickBite();
                else if (distanceToTarget <= this.getAttackReachSqr(target) && pose != UP2Poses.CHARGING_START.get() && pose != UP2Poses.CHARGING.get() && pose != UP2Poses.CHARGING_END.get()) {
                    this.majungasaurus.setPose(UP2Poses.BITING.get());
                }
            }
        }

        protected void tickBite() {
            attackTime++;
            LivingEntity target = this.majungasaurus.getTarget();
            if (attackTime == 11) {
                if (this.majungasaurus.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                    this.majungasaurus.doHurtTarget(target);
                    this.majungasaurus.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (attackTime >= 22) {
                attackTime = 0;
                this.majungasaurus.setPose(Pose.STANDING);
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity target) {
            return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + target.getBbWidth();
        }
    }

    static class MajungasaurusPanicGoal extends LargePanicGoal {

        protected final Majungasaurus majungasaurus;

        public MajungasaurusPanicGoal(Majungasaurus majungasaurus) {
            super(majungasaurus, 1.7D);
            this.majungasaurus = majungasaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.majungasaurus.isBaby();
        }
    }
}
