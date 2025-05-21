package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.base.AncientAquaticEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.entity.ai.goal.AquaticLeapGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goal.CustomRandomSwimGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goal.LargePanicGoal;
import com.unusualmodding.unusual_prehistory.registry.UP2Sounds;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2EntityTags;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Objects;

public class DunkleosteusEntity extends AncientAquaticEntity {

    private static final EntityDataAccessor<Integer> DUNK_SIZE = SynchedEntityData.defineId(DunkleosteusEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PASSIVE = SynchedEntityData.defineId(DunkleosteusEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> YAWN_COOLDOWN = SynchedEntityData.defineId(DunkleosteusEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> YAWN_TIMER = SynchedEntityData.defineId(DunkleosteusEntity.class, EntityDataSerializers.INT);

    private static final EntityDimensions SMALL_SIZE = EntityDimensions.scalable(0.75F, 0.6F);
    private static final EntityDimensions MEDIUM_SIZE = EntityDimensions.scalable(1.2F, 1.1F);
    private static final EntityDimensions LARGE_SIZE = EntityDimensions.scalable(2.2F, 2.1F);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState yawnAnimationState = new AnimationState();

    public DunkleosteusEntity(EntityType<? extends AncientAquaticEntity> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 4, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 2);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.MOVEMENT_SPEED, 1.0F).add(Attributes.ARMOR, 2.0D).add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new DunkleosteusAttackGoal(this));
        this.goalSelector.addGoal(3, new CustomRandomSwimGoal(this, 1, 10, 70, 70, 3));
        this.goalSelector.addGoal(4, new DunkleosteusYawnGoal(this));
        this.goalSelector.addGoal(5, new DunkleosteusFleeGoal());
        this.goalSelector.addGoal(7, new AquaticLeapGoal(this, 20));
        this.targetSelector.addGoal(6, new DunkleosteusNearestAttackableTargetGoal(this));
        this.targetSelector.addGoal(8, new DunkleosteusHurtByTargetGoal(this));
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(pTravelVector);
        }
    }

    // flop
    @Override
    public void aiStep() {
        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4F, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }
        super.aiStep();
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setRunning(this.getMoveControl().getSpeedModifier() >= 1.25D);
        } else {
            this.setRunning(false);
        }
        super.customServerAiStep();
    }

    public void tick () {
        super.tick();
        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }
        if (this.getYawnCooldown() > 0) {
            this.setYawnCooldown(this.getYawnCooldown() - 1);
        }
        if (this.getYawnTimer() > 0) {
            this.setYawnTimer(this.getYawnTimer() - 1);
            if (this.getYawnTimer() == 0) {
                this.yawnCooldown();
            }
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isInWaterOrBubble() && this.isAlive(), this.tickCount);
        this.swimAnimationState.animateWhen(this.walkAnimation.isMoving() && this.isInWaterOrBubble(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getAttackState() == 1, this.tickCount);
        this.yawnAnimationState.animateWhen(this.getYawnCooldown() == 0, this.tickCount);
        if (this.getAttackState() == 1) {
            this.yawnAnimationState.stop();
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.5F;
    }

    private boolean isTarget(Entity entity) {
        if (this.getDunkSize() == 1) {
            return entity.getType().is(UP2EntityTags.MEDIUM_DUNKLEOSTEUS_TARGETS) && entity instanceof Player;
        } else if (this.getDunkSize() == 2) {
            return entity.getType().is(UP2EntityTags.BIG_DUNKLEOSTEUS_TARGETS) && entity instanceof Player;
        } else {
            return entity.getType().is(UP2EntityTags.SMALL_DUNKLEOSTEUS_TARGETS);
        }
    }

    // mob interactions
    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(UP2ItemTags.PACIFIES_DUNKLEOSTEUS) && !this.isPassive()) {
            if (!this.level().isClientSide) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 1));
                this.level().broadcastEntityEvent(this, (byte) 20);
                this.setPassive(true);
                this.gameEvent(GameEvent.EAT, this);
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        } else if (itemstack.is(UP2ItemTags.DUNKLEOSTEUS_FOOD) && this.isPassive() && this.getHealth() < this.getMaxHealth()) {
            if (!this.level().isClientSide) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                this.heal((float) itemstack.getFoodProperties(this).getNutrition());
                this.gameEvent(GameEvent.EAT, this);
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if (prev && this.isPassive() && entity instanceof LivingEntity && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().getUUID().equals(entity.getUUID()))) return false;
        else return prev;
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2Sounds.DUNKLEOSTEUS_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UP2Sounds.DUNKLEOSTEUS_DEATH.get();
    }

    protected SoundEvent getFlopSound() {
        return UP2Sounds.DUNKLEOSTEUS_FLOP.get();
    }

    public float getVoicePitch() {
        final float f = (3 - this.getDunkSize()) * 0.33F;
        return (float) (super.getVoicePitch() * Math.sqrt(f) * 1.2F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DUNK_SIZE, 0);
        this.entityData.define(PASSIVE, false);
        this.entityData.define(YAWN_COOLDOWN, 2 * 20 + random.nextInt(12 * 20));
        this.entityData.define(YAWN_TIMER, 0);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Passive", this.isPassive());
        compoundTag.putFloat("Size", this.getDunkSize());
        compoundTag.putInt("YawnCooldown", this.getYawnCooldown());
        compoundTag.putInt("YawnTimer", this.getYawnTimer());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setPassive(compoundTag.getBoolean("Passive"));
        this.setDunkSize(compoundTag.getInt("Size"));
        this.setYawnCooldown(compoundTag.getInt("YawnCooldown"));
        this.setYawnTimer(compoundTag.getInt("YawnTimer"));
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (DUNK_SIZE.equals(accessor)) {
            this.refreshDimensions();
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0F * this.getDunkSize() + 10.0F);
            this.getAttribute(Attributes.ARMOR).setBaseValue(2.0F * this.getDunkSize() + 6.0F);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.0F * this.getDunkSize() + 6.0F);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(1.0F - this.getDunkSize() * 0.07F);
            this.heal(50.0F);
        }
        super.onSyncedDataUpdated(accessor);
    }

    public int getDunkSize() {
        return Mth.clamp(this.entityData.get(DUNK_SIZE), 0, 2);
    }
    public void setDunkSize(int dunkSize) {
        this.entityData.set(DUNK_SIZE, dunkSize);
    }

    public int getYawnTimer() {
        return this.entityData.get(YAWN_TIMER);
    }
    public void setYawnTimer(int timer) {
        this.entityData.set(YAWN_TIMER, timer);
    }

    public int getYawnCooldown() {
        return this.entityData.get(YAWN_COOLDOWN);
    }
    public void setYawnCooldown(int cooldown) {
        this.entityData.set(YAWN_COOLDOWN, cooldown);
    }
    public void yawnCooldown() {
        this.entityData.set(YAWN_COOLDOWN, 6 * 20 + random.nextInt(60 * 2 * 20));
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return getDimsForDunk().scale(this.getScale());
    }

    private EntityDimensions getDimsForDunk() {
        return switch (this.getDunkSize()) {
            case 1 -> MEDIUM_SIZE;
            case 2 -> LARGE_SIZE;
            default -> SMALL_SIZE;
        };
    }

    public String getVariantName() {
        return switch (this.getDunkSize()) {
            case 1 -> "marsaisi";
            case 2 -> "terrelli";
            default -> "raveri";
        };
    }

    public void setPassive(boolean passive) {
        this.entityData.set(PASSIVE, passive);
    }

    public boolean isPassive() {
        return this.entityData.get(PASSIVE);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        int sizeChange = this.random.nextInt(0, 100);
        if (sizeChange <= 30) {
            this.setDunkSize(1);
        } else if (sizeChange <= 60) {
            this.setDunkSize(2);
        } else {
            this.setDunkSize(0);
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        DunkleosteusEntity dunkleosteus = UP2Entities.DUNKLEOSTEUS.get().create(serverLevel);
        dunkleosteus.setDunkSize(this.getDunkSize());
        return dunkleosteus;
    }

    // goals
    private static class DunkleosteusAttackGoal extends Goal {
        private int animTime = 0;
        DunkleosteusEntity dunkleosteus;

        public DunkleosteusAttackGoal(DunkleosteusEntity dunkleosteus) {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            this.dunkleosteus = dunkleosteus;
        }

        public boolean canUse() {
            return this.dunkleosteus.getTarget() != null && this.dunkleosteus.getTarget().isAlive();
        }

        public void start() {
            this.dunkleosteus.setRunning(true);
            this.dunkleosteus.setAttackState(0);
            this.animTime = 0;
        }

        public void stop() {
            this.dunkleosteus.setRunning(false);
            this.dunkleosteus.setAttackState(0);
        }

        public void tick() {
            LivingEntity target = this.dunkleosteus.getTarget();
            if (target != null) {
                this.dunkleosteus.lookAt(this.dunkleosteus.getTarget(), 30F, 30F);
                this.dunkleosteus.getLookControl().setLookAt(this.dunkleosteus.getTarget(), 30F, 30F);
                double distance = this.dunkleosteus.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int attackState = this.dunkleosteus.getAttackState();

                if (attackState == 1) {
                    tickBiteAttack();
                } else {
                    this.dunkleosteus.getNavigation().moveTo(target, 1.45D);
                    if (distance <= 5 + (double) this.dunkleosteus.getDunkSize() * 1.6) {
                        this.dunkleosteus.setAttackState(1);
                    }
                }
            }
        }

        protected void tickBiteAttack() {
            animTime++;
            if (animTime == 5) {
                if (this.dunkleosteus.distanceTo(Objects.requireNonNull(this.dunkleosteus.getTarget())) < 1.25F * this.dunkleosteus.getDunkSize() + 1.25F) {
                    this.dunkleosteus.doHurtTarget(this.dunkleosteus.getTarget());
                    this.dunkleosteus.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (animTime >= 8) {
                animTime = 0;
                this.dunkleosteus.setAttackState(0);
            }
        }
    }

    private class DunkleosteusFleeGoal extends LargePanicGoal {
        public DunkleosteusFleeGoal() {
            super(DunkleosteusEntity.this, 2.0D);
        }

        @Override
        protected boolean shouldPanic() {
            return this.mob.getLastHurtByMob() != null && (DunkleosteusEntity.this.getDunkSize() == 0 || DunkleosteusEntity.this.isBaby());
        }

        @Override
        protected boolean findRandomPosition() {
            Vec3 vec3 = DefaultRandomPos.getPos(this.mob, 12, 8);
            if (vec3 == null) {
                return false;
            } else {
                this.posX = vec3.x;
                this.posY = vec3.y;
                this.posZ = vec3.z;
                return true;
            }
        }
    }

    private static class DunkleosteusHurtByTargetGoal extends TargetGoal {

        private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();

        DunkleosteusEntity dunkleosteus;
        private int timestamp;

        public DunkleosteusHurtByTargetGoal(DunkleosteusEntity dunkleosteus) {
            super(dunkleosteus, true);
            this.dunkleosteus = dunkleosteus;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        public boolean canUse() {
            int time = this.mob.getLastHurtByMobTimestamp();
            LivingEntity entity = this.mob.getLastHurtByMob();
            if (time != this.timestamp && entity != null) {
                if (entity.getType() == EntityType.PLAYER && this.mob.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER) || this.dunkleosteus.getDunkSize() == 0 || this.dunkleosteus.isBaby()) {
                    return false;
                }
                else {
                    return this.canAttack(entity, HURT_BY_TARGETING);
                }
            }
            else {
                return false;
            }
        }

        public void start() {
            this.mob.setTarget(this.mob.getLastHurtByMob());
            this.targetMob = this.mob.getTarget();
            this.timestamp = this.mob.getLastHurtByMobTimestamp();
            this.unseenMemoryTicks = 300;
            super.start();
        }
    }

    private static class DunkleosteusNearestAttackableTargetGoal extends TargetGoal {

        DunkleosteusEntity dunkleosteus;
        protected final int randomInterval;
        @Nullable
        protected LivingEntity target;

        public DunkleosteusNearestAttackableTargetGoal(DunkleosteusEntity dunkleosteus) {
            super(dunkleosteus, true, true);
            this.dunkleosteus = dunkleosteus;
            this.randomInterval = reducedTickDelay(10);
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        public boolean canUse() {
            if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0 || this.dunkleosteus.isPassive() || this.dunkleosteus.isBaby()) {
                return false;
            } else {
                this.findTarget();
                return this.target != null;
            }
        }

        protected AABB getTargetSearchArea(double pTargetDistance) {
            return this.mob.getBoundingBox().inflate(pTargetDistance, 6.0F, pTargetDistance);
        }

        protected void findTarget() {
            this.target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(LivingEntity.class, this.getTargetSearchArea(this.getFollowDistance()), (entity) -> true), TargetingConditions.forCombat().range(this.getFollowDistance()).selector(entity -> this.dunkleosteus.isTarget(entity)), this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        }

        public void start() {
            this.mob.setTarget(this.target);
            super.start();
        }
    }

    private static class DunkleosteusYawnGoal extends Goal {

        DunkleosteusEntity dunkleosteus;

        public DunkleosteusYawnGoal(DunkleosteusEntity dunkleosteus) {
            this.dunkleosteus = dunkleosteus;
        }

        @Override
        public boolean canUse() {
            return this.dunkleosteus.getYawnCooldown() == 0 && this.dunkleosteus.isInWater();
        }

        @Override
        public boolean canContinueToUse() {
            return this.dunkleosteus.getYawnTimer() > 0 && this.dunkleosteus.isInWater();
        }

        @Override
        public void start() {
            super.start();
            this.dunkleosteus.setYawnTimer(40);
        }

        @Override
        public void tick() {
            super.tick();
        }

        @Override
        public void stop() {
            super.stop();
            this.dunkleosteus.yawnCooldown();
        }
    }
}
