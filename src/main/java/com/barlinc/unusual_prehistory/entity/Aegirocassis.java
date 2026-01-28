package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.CustomizableRandomSwimGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class Aegirocassis extends PrehistoricAquaticMob {

    public final AegirocassisPart headPart;
    public final AegirocassisPart tailPart1;
    public final AegirocassisPart tailPart2;
    private final AegirocassisPart[] allParts;

    private boolean wasPreviouslyBaby;

    private float fakeYRot = 0;
    private float[] yawBuffer = new float[128];
    private int yawPointer = -1;

    public final AnimationState eyesAnimationState = new AnimationState();
    public final AnimationState mouthAnimationState = new AnimationState();

    public Aegirocassis(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 1000, 10, 0.02F, 0.1F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        this.headPart = new AegirocassisPart(this, this, 3.6F, 4.1F);
        this.tailPart1 = new AegirocassisPart(this, this, 3.6F, 4.1F);
        this.tailPart2 = new AegirocassisPart(this, tailPart1, 3.6F, 4.1F);
        this.allParts = new AegirocassisPart[]{headPart, tailPart1, tailPart2};
        this.setId(ENTITY_COUNTER.getAndAdd(allParts.length + 1) + 1);
        this.fakeYRot = this.getYRot();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.7F)
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LargeBabyPanicGoal(this, 2.0D, 10, 4));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.AEGIROCASSIS_FOOD), false));
        this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1, 60, 15, 10, 3, true));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && Aegirocassis.this.isInWaterOrBubble();
            }
        });
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return this.getDepthPathfindingFavor(pos, level);
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.allParts.length; i++) {
            this.allParts[i].setId(id + i + 1);
        }
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.AEGIROCASSIS_FOOD);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.horizontalCollision && this.isEyeInFluid(FluidTags.WATER) && this.isPathFinding()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.005, 0.0));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean shouldFlop() {
        return false;
    }

    @Override
    public float getScale() {
        return this.isBaby() ? 0.25F : 1.0F;
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?>[] getParts() {
        return allParts;
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(4, 6, 4);
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        super.remove(removalReason);
        if (allParts != null) {
            for (AegirocassisPart aegirocassisPart : allParts) {
                aegirocassisPart.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    public int getHeadRotSpeed() {
        return 5;
    }

    @Override
    public void tick() {
        this.tickMultipart();
        super.tick();

        this.yBodyRot = Mth.approachDegrees(yBodyRotO, yBodyRot, this.getHeadRotSpeed());
        this.fakeYRot = Mth.approachDegrees(fakeYRot, this.yBodyRot, 10);

        if (wasPreviouslyBaby != this.isBaby()) {
            this.wasPreviouslyBaby = this.isBaby();
            this.refreshDimensions();
            for (AegirocassisPart aegirocassisPart : this.allParts) {
                aegirocassisPart.refreshDimensions();
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.eyesAnimationState.animateWhen(this.isAlive(), this.tickCount);
        this.mouthAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
    }

    @Override
    public void setupAnimationCooldowns() {
    }

    private void tickMultipart() {
        if (yawPointer == -1) {
            this.fakeYRot = this.yBodyRot;
            for (int i = 0; i < yawBuffer.length; i++) {
                this.yawBuffer[i] = this.fakeYRot;
            }
        }
        if (++this.yawPointer == this.yawBuffer.length) {
            this.yawPointer = 0;
        }
        this.yawBuffer[this.yawPointer] = this.fakeYRot;

        Vec3[] avector3d = new Vec3[this.allParts.length];
        for (int j = 0; j < this.allParts.length; ++j) {
            avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
        }
        Vec3 center = this.position().add(0, this.getBbHeight() * 0.5F, 0);
        float zOffset = this.isBaby() ? 1.0F : 4.0F;
        this.headPart.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, zOffset), this.getXRot(), this.getYHeadRot()).add(center));
        this.tailPart1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -zOffset), this.getXRot(), this.getYawFromBuffer(2, 1.0F)).add(center));
        this.tailPart2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -zOffset), this.getXRot(), this.getYawFromBuffer(4, 1.0F)).add(this.tailPart1.centeredPosition()));
        for (int l = 0; l < this.allParts.length; ++l) {
            this.allParts[l].xo = avector3d[l].x;
            this.allParts[l].yo = avector3d[l].y;
            this.allParts[l].zo = avector3d[l].z;
            this.allParts[l].xOld = avector3d[l].x;
            this.allParts[l].yOld = avector3d[l].y;
            this.allParts[l].zOld = avector3d[l].z;
        }
    }

    public float getTrailTransformation(int pointer, float partialTick) {
        if (this.isRemoved()) {
            partialTick = 1.0F;
        }
        int i = this.yawPointer - pointer & 127;
        int j = this.yawPointer - pointer - 1 & 127;
        float d0 = this.yawBuffer[j];
        float d1 = this.yawBuffer[i] - d0;
        return d0 + d1 * partialTick;
    }

    private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
        return offset.xRot(-xRot * ((float) Math.PI / 180F)).yRot(-yRot * ((float) Math.PI / 180F));
    }

    public float getYawFromBuffer(int pointer, float partialTick) {
        int i = this.yawPointer - pointer & 127;
        int j = this.yawPointer - pointer - 1 & 127;
        float d0 = this.yawBuffer[j];
        float d1 = this.yawBuffer[i] - d0;
        return d0 + d1 * partialTick;
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.AEGIROCASSIS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.AEGIROCASSIS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return SoundEvents.EMPTY;
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.STETHACANTHUS_BUCKET.get());
    }

    @Override
    public boolean canBucket() {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.AEGIROCASSIS.get().create(serverLevel);
    }
}