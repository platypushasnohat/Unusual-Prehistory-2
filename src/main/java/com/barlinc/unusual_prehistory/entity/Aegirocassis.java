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
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
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

    public Aegirocassis(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new AegirocassisMoveControl(this, 1, 0.02F, 0.1F);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        this.headPart = new AegirocassisPart(this, this, 3.5F, 3.8F);
        this.tailPart1 = new AegirocassisPart(this, this, 3.5F, 3.8F);
        this.tailPart2 = new AegirocassisPart(this, tailPart1, 3.5F, 3.8F);
        this.allParts = new AegirocassisPart[]{headPart, tailPart1, tailPart2};
        this.setId(ENTITY_COUNTER.getAndAdd(allParts.length + 1) + 1);
        this.fakeYRot = this.getYRot();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.65F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LargeBabyPanicGoal(this, 2.0D, 10, 4));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.STETHACANTHUS_FOOD), false));
        this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 50));
    }

    @Override
    public void setId(int i1) {
        super.setId(i1);
        for (int i = 0; i < this.allParts.length; i++) {
            this.allParts[i].setId(i1 + i + 1);
        }
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.STETHACANTHUS_FOOD);
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
        return 4;
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
        this.headPart.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, 4.0F), this.getXRot(), this.getYHeadRot()).add(center));
        this.tailPart1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -4.0F), this.getXRot(), this.getYawFromBuffer(2, 1.0F)).add(center));
        this.tailPart2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -4.0F), this.getXRot(), this.getYawFromBuffer(4, 1.0F)).add(this.tailPart1.centeredPosition()));
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
        return UP2SoundEvents.STETHACANTHUS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.STETHACANTHUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.STETHACANTHUS_FLOP.get();
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

    public static class AegirocassisMoveControl extends MoveControl {

        private final int maxTurnX;
        private final float inWaterSpeedModifier;
        private final float outsideWaterSpeedModifier;
        private Vec3 targetPos = Vec3.ZERO;

        public AegirocassisMoveControl(Mob mob, int maxTurnX, float inWaterSpeedModifier, float outsideWaterSpeedModifier) {
            super(mob);
            this.maxTurnX = maxTurnX;
            this.inWaterSpeedModifier = inWaterSpeedModifier;
            this.outsideWaterSpeedModifier = outsideWaterSpeedModifier;
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO && !this.mob.getNavigation().isDone()) {
                if (this.mob.tickCount % 60 == 0) {
                    this.generateNewTarget();
                }
                double d0 = this.targetPos.x - this.mob.getX();
                double d1 = this.targetPos.y - this.mob.getY();
                double d2 = this.targetPos.z - this.mob.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double) 2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float) (Mth.atan2(d2, d0) * (double) (180.0F / (float) Math.PI)) - 90.0F;
                    this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f, 2.0F));
                    this.mob.yBodyRot = this.mob.getYRot();
                    this.mob.yHeadRot = this.mob.getYRot();
                    float f1 = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.mob.isInWater()) {
                        this.mob.setSpeed(f1 * this.inWaterSpeedModifier);
                        double d4 = Math.sqrt(d0 * d0 + d2 * d2);
                        if (Math.abs(d1) > (double) 1.0E-5F || Math.abs(d4) > (double) 1.0E-5F) {
                            float f3 = -((float) (Mth.atan2(d1, d4) * (double) (180.0F / (float) Math.PI)));
                            f3 = Mth.clamp(Mth.wrapDegrees(f3), (float) (-this.maxTurnX), (float) this.maxTurnX);
                            this.mob.setXRot(this.rotlerp(this.mob.getXRot(), f3, 2.0F));
                        }
                        float f6 = Mth.cos(this.mob.getXRot() * ((float) Math.PI / 180.0F));
                        float f4 = Mth.sin(this.mob.getXRot() * ((float) Math.PI / 180.0F));
                        this.mob.zza = f6 * f1;
                        this.mob.yya = -f4 * f1;
                    } else {
                        float f5 = Math.abs(Mth.wrapDegrees(this.mob.getYRot() - f));
                        float f2 = this.getTurningSpeedFactor(f5);
                        this.mob.setSpeed(f1 * this.outsideWaterSpeedModifier * f2);
                    }
                }
            } else {
                this.mob.setSpeed(0.0F);
                this.mob.setXxa(0.0F);
                this.mob.setYya(0.0F);
                this.mob.setZza(0.0F);
            }
        }

        private void generateNewTarget() {
            Level level = this.mob.level();
            for (int i = 0; i < 10; i++) {
                Vec3 pos = this.getSpreadPosition(this.mob, new Vec3(35, 10, 35));
                HitResult hitResult = this.mob.level().clip(new ClipContext(this.mob.position(), pos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.mob));
                if (hitResult instanceof BlockHitResult blockHit) {
                    BlockPos targetPos = blockHit.getBlockPos();
                    BlockState blockState = level.getBlockState(targetPos);
                    if (blockState.is(Blocks.WATER)) {
                        this.targetPos = blockHit.getLocation();
                        break;
                    }
                }
            }
        }

        private Vec3 getSpreadPosition(Entity entity, Vec3 range) {
            double x = entity.getX() + (entity.level().random.nextDouble() - entity.level().random.nextDouble()) * range.x + 0.5D;
            double y = entity.getY() + (entity.level().random.nextDouble() - entity.level().random.nextDouble()) * range.y + 0.5D;
            double z = entity.getZ() + (entity.level().random.nextDouble() - entity.level().random.nextDouble()) * range.z + 0.5D;
            return new Vec3(x, y, z);
        }

        private float getTurningSpeedFactor(float v) {
            return 1.0F - Mth.clamp((v - 10.0F) / 50.0F, 0.0F, 1.0F);
        }
    }
}