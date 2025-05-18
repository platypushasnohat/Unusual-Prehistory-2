package com.unusualmodding.unusual_prehistory.entity.custom;

import com.unusualmodding.unusual_prehistory.entity.SchoolingAquaticEntity;
import com.unusualmodding.unusual_prehistory.entity.UP2Entities;
import com.unusualmodding.unusual_prehistory.entity.ai.goal.AquaticLeapGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goal.CustomRandomSwimGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goal.FollowVariantLeaderGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goal.LargePanicGoal;
import com.unusualmodding.unusual_prehistory.entity.util.SmartBodyHelper;
import com.unusualmodding.unusual_prehistory.sounds.UP2Sounds;
import com.unusualmodding.unusual_prehistory.tags.UP2EntityTags;
import com.unusualmodding.unusual_prehistory.tags.UP2ItemTags;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Objects;
import java.util.stream.Stream;

public class StethacanthusEntityTest extends SchoolingAquaticEntity implements Bucketable {

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(StethacanthusEntityTest.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> PASSIVE = SynchedEntityData.defineId(StethacanthusEntityTest.class, EntityDataSerializers.BOOLEAN);

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.3F;
        helper.bodyLagStill = 0.25F;
        return helper;
    }

    public StethacanthusEntityTest(EntityType<? extends SchoolingAquaticEntity> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.1F)
                .add(Attributes.FOLLOW_RANGE, 16.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new FollowVariantLeaderGoal(this));
        this.goalSelector.addGoal(1, new StethacanthusAttackGoal());
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.5D, 1.0D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, LivingEntity.class, 12.0F, 1.5D, 1.0D, entity -> entity.getType().is(UP2EntityTags.STETHACANTHUS_AVOIDS)));
        this.goalSelector.addGoal(3, new CustomRandomSwimGoal(this, 1, 3, 30, 30, 3));
        this.goalSelector.addGoal(6, new AquaticLeapGoal(this, 15));
        this.goalSelector.addGoal(7, new StethacanthusFleeGoal());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.STETHACANTHUS_TARGETS)) {
        @Override
        public boolean canUse() {
            if (this.mob instanceof StethacanthusEntityTest stethacanthus) {
                if (stethacanthus.isPassive() || stethacanthus.isBaby()) return false;
            }
            return super.canUse();
        }});
    }

    // Schooling
    public int getMaxSchoolSize() {
        return 3;
    }

    @Override
    public void addFollowers(Stream<? extends SchoolingAquaticEntity> entity) {
        entity.limit(this.getMaxSchoolSize() - this.schoolSize).filter((entity1) -> entity1 != this).forEach((entity2) -> entity2.startFollowing(this));
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.55F;
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
            this.calculateEntityAnimation(true);
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
            super.customServerAiStep();
        }
    }

    public void tick () {
        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }
        super.tick();
    }

    // sounds
    protected SoundEvent getDeathSound() {
        return UP2Sounds.STETHACANTHUS_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_28281_) {
        return UP2Sounds.STETHACANTHUS_HURT.get();
    }

    protected SoundEvent getFlopSound() {
        return UP2Sounds.STETHACANTHUS_FLOP.get();
    }

    // synched data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(PASSIVE, false);
    }

    // save data
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("FromBucket", this.isFromBucket());
        compound.putBoolean("Bucketed", this.fromBucket());
        compound.putBoolean("Passive", this.isPassive());
    }
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFromBucket(compound.getBoolean("FromBucket"));
        this.setFromBucket(compound.getBoolean("Bucketed"));
        this.setPassive(compound.getBoolean("Passive"));
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double p_213397_1_) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    private boolean isFromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean p_203706_1_) {
        this.entityData.set(FROM_BUCKET, p_203706_1_);
    }

    public void setPassive(boolean passive) {
        this.entityData.set(PASSIVE, passive);
    }

    public boolean isPassive() {
        return this.entityData.get(PASSIVE);
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag compound) {
        Bucketable.loadDefaultDataFromBucketTag(this, compound);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(Items.SALMON_BUCKET);
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(UP2ItemTags.PACIFIES_STETHACANTHUS) && !this.isPassive()) {
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
        } else if (itemstack.is(UP2ItemTags.STETHACANTHUS_FOOD) && this.isPassive() && this.getHealth() < this.getMaxHealth()) {
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
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.STETHACANTHUS.get().create(serverLevel);
    }

    // animations
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isInWaterOrBubble() && this.isAlive(), this.tickCount);
        this.swimAnimationState.animateWhen(this.walkAnimation.isMoving() && this.isInWaterOrBubble(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f = Math.min(pPartialTick * 4.0F, 1.0F);
        this.walkAnimation.update(f, 0.4F);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    // goals
    class StethacanthusAttackGoal extends Goal {
        private int attackTime = 0;

        public StethacanthusAttackGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity target = StethacanthusEntityTest.this.getTarget();
            return target != null && target.isAlive() && target.isInWater() && !target.getType().is(UP2EntityTags.STETHACANTHUS_AVOIDS) && !(target instanceof Player);
        }

        public void start() {
            StethacanthusEntityTest.this.setAttackState(0);
            this.attackTime = 0;
        }

        public void stop() {
            StethacanthusEntityTest.this.setAttackState(0);
        }

        public void tick() {
            LivingEntity target = StethacanthusEntityTest.this.getTarget();
            if (target != null && target.isInWater()) {
                StethacanthusEntityTest.this.lookAt(StethacanthusEntityTest.this.getTarget(), 30F, 30F);
                StethacanthusEntityTest.this.getLookControl().setLookAt(StethacanthusEntityTest.this.getTarget(), 30F, 30F);

                double distance = StethacanthusEntityTest.this.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int attackState = StethacanthusEntityTest.this.getAttackState();

                if (attackState == 1) {
                    tickBiteAttack();
                    StethacanthusEntityTest.this.getNavigation().moveTo(target, 0.75D);
                } else {
                    StethacanthusEntityTest.this.getNavigation().moveTo(target, 1.4D);
                    if (distance <= 4) {
                        StethacanthusEntityTest.this.setAttackState(1);
                    }
                }
            }
        }

        protected void tickBiteAttack() {
            attackTime++;
            if (attackTime == 9) {
                if (StethacanthusEntityTest.this.distanceTo(Objects.requireNonNull(StethacanthusEntityTest.this.getTarget())) < 2F) {
                    StethacanthusEntityTest.this.doHurtTarget(StethacanthusEntityTest.this.getTarget());
                    StethacanthusEntityTest.this.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (attackTime >= 15) {
                attackTime = 0;
                StethacanthusEntityTest.this.setAttackState(0);
            }
        }
    }

    class StethacanthusFleeGoal extends LargePanicGoal {
        public StethacanthusFleeGoal() {
            super(StethacanthusEntityTest.this, 1.5D);
        }

        @Override
        protected boolean findRandomPosition() {
            Vec3 vec3 = DefaultRandomPos.getPos(this.mob, 16, 16);
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
}