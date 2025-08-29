package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.unusualmodding.unusual_prehistory.entity.base.SchoolingAquaticEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.CustomRandomSwimGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.FollowVariantLeaderGoal;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2EntityTags;
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
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class Scaumenacia extends SchoolingAquaticEntity implements Bucketable {

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Scaumenacia.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();

    public Scaumenacia(EntityType<? extends SchoolingAquaticEntity> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 1000, 6, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 4);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(2, new FollowVariantLeaderGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, LivingEntity.class, 6.0F, 2.0D, 2.0D, entity -> entity.getType().is(UP2EntityTags.SCAUMENACIA_AVOIDS)));
        this.goalSelector.addGoal(4, new CustomRandomSwimGoal(this, 1, 1, 32, 32, 3));
        this.goalSelector.addGoal(5, new ScaumenaciaFleeGoal());
    }

    // schooling
    public int getMaxSchoolSize() {
        return 12;
    }

    @Override
    public void addFollowers(Stream<? extends SchoolingAquaticEntity> entity) {
        entity.limit(this.getMaxSchoolSize() - this.schoolSize).filter((entity1) -> entity1 != this).forEach((entity2) -> entity2.startFollowing(this));
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

    public void tick () {
        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }
        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4F, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }
        super.tick();
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isInWaterOrBubble() && this.isAlive(), this.tickCount);
        this.swimAnimationState.animateWhen(this.walkAnimation.isMoving() && this.isInWaterOrBubble(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.6F;
    }

    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.SCAUMENACIA_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return UP2SoundEvents.SCAUMENACIA_HURT.get();
    }

    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.SCAUMENACIA_FLOP.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("FromBucket", this.isFromBucket());
        compound.putBoolean("Bucketed", this.fromBucket());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFromBucket(compound.getBoolean("FromBucket"));
        this.setFromBucket(compound.getBoolean("Bucketed"));
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putFloat("Health", this.getHealth());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }
    public boolean removeWhenFarAway(double distance) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    private boolean isFromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    public ItemStack getBucketItemStack() {
        ItemStack stack = new ItemStack(Items.SALMON_BUCKET);
        if (this.hasCustomName()) {
            stack.setHoverName(this.getCustomName());
        }
        return stack;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        Scaumenacia scaumenacia = UP2Entities.SCAUMENACIA.get().create(serverLevel);
        scaumenacia.setVariant(this.getVariant());
        return scaumenacia;
    }

    // variants
    public void determineVariant(int variantChange){
        if (variantChange <= 1) {
            this.setVariant(1);
        } else {
            this.setVariant(0);
        }
    }

    // goals
    private class ScaumenaciaFleeGoal extends LargePanicGoal {
        public ScaumenaciaFleeGoal() {
            super(Scaumenacia.this, 2.0D);
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
}
