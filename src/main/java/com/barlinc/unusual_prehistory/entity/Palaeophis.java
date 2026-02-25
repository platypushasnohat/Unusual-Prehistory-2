package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class Palaeophis extends PrehistoricAquaticMob {

    public final PalaeophisPart body1Part;
    public final PalaeophisPart body2Part;
    public final PalaeophisPart body3Part;
    public final PalaeophisPart tail1Part;
    public final PalaeophisPart tail2Part;
    public final PalaeophisPart tail3Part;
    private final PalaeophisPart[] allParts;
    private float fakeYRot;
    private float[] yawBuffer = new float[128];
    private int yawPointer = -1;

    public Palaeophis(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 1000, 10, 0.02F, 0.1F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 6);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.body1Part = new PalaeophisPart(this, this, 1.8F, 1.2F);
        this.body2Part = new PalaeophisPart(this, body1Part, 1.8F, 1.2F);
        this.body3Part = new PalaeophisPart(this, body2Part, 1.8F, 1.2F);
        this.tail1Part = new PalaeophisPart(this, body3Part, 1.8F, 1.2F);
        this.tail2Part = new PalaeophisPart(this, tail1Part, 1.5F, 1.2F);
        this.tail3Part = new PalaeophisPart(this, tail2Part, 1.25F, 1.2F);
        this.allParts = new PalaeophisPart[]{body1Part, body2Part, body3Part, tail1Part, tail2Part, tail3Part};
        this.setId(ENTITY_COUNTER.getAndAdd(allParts.length + 1) + 1);
        this.fakeYRot = this.getYRot();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.75F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TARTUOSTEUS_FOOD), false));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0D, 10));
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.allParts.length; i++) {
            this.allParts[i].setId(id + i + 1);
        }
    }

    @Override
    public int getHeadRotSpeed() {
        return 4;
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
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.TARTUOSTEUS_FOOD);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?>[] getParts() {
        return this.allParts;
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        super.remove(removalReason);
        if (allParts != null) {
            for (PartEntity<Palaeophis> part : allParts) {
                part.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    public boolean shouldFlop() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        this.yBodyRot = Mth.approachDegrees(this.yBodyRotO, yBodyRot, this.getHeadRotSpeed());
        this.fakeYRot = Mth.approachDegrees(fakeYRot, this.yBodyRot, 10);
        this.tickMultipart();
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    private void tickMultipart() {
        if (yawPointer == -1) {
            this.fakeYRot = this.yBodyRot;
            for (int i = 0; i < this.yawBuffer.length; i++) {
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

        this.body1Part.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -2.2F), this.getXRot() * 0.25F, this.getYHeadRot()).add(center));
        this.body2Part.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -2.2F), this.getXRot() * 0.25F, this.getYawFromBuffer(2, 1.0F)).add(this.body1Part.centeredPosition()));
        this.body3Part.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -2.2F), this.getXRot() * 0.25F, this.getYawFromBuffer(4, 1.0F)).add(this.body2Part.centeredPosition()));
        this.tail1Part.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -2.2F), this.getXRot() * 0.25F, this.getYawFromBuffer(6, 1.0F)).add(this.body3Part.centeredPosition()));
        this.tail2Part.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -2.2F), this.getXRot() * 0.25F, this.getYawFromBuffer(8, 1.0F)).add(this.tail1Part.centeredPosition()));
        this.tail3Part.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -2.2F), this.getXRot() * 0.25F, this.getYawFromBuffer(10, 1.0F)).add(this.tail2Part.centeredPosition()));

        for (int l = 0; l < this.allParts.length; l++) {
            this.allParts[l].xo = avector3d[l].x;
            this.allParts[l].yo = avector3d[l].y;
            this.allParts[l].zo = avector3d[l].z;
            this.allParts[l].xOld = avector3d[l].x;
            this.allParts[l].yOld = avector3d[l].y;
            this.allParts[l].zOld = avector3d[l].z;
        }
    }

    public float getTrailTransformation(int pointer, float partialTicks) {
        if (this.isRemoved()) {
            partialTicks = 1.0F;
        }
        int i = this.yawPointer - pointer & 127;
        int j = this.yawPointer - pointer - 1 & 127;
        float d0 = this.yawBuffer[j];
        float d1 = this.yawBuffer[i] - d0;
        return d0 + d1 * partialTicks;
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
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(6, 4, 6);
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.JAWLESS_FISH_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.JAWLESS_FISH_HURT.get();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.JAWLESS_FISH_FLOP.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        Palaeophis palaeophis = UP2Entities.PALAEOPHIS.get().create(serverLevel);
        palaeophis.setVariant(this.getVariant());
        return palaeophis;
    }

    public static boolean canSpawn(EntityType<Palaeophis> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return PrehistoricAquaticMob.checkSpawnRules(entityType, level, spawnType, pos, random);
    }
}