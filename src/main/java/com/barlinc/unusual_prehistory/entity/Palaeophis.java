package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
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
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class Palaeophis extends PrehistoricAquaticMob {

    public final PalaeophisPart head1Part;
    public final PalaeophisPart head2Part;
    public final PalaeophisPart head3Part;
    public final PalaeophisPart tail1Part;
    public final PalaeophisPart tail2Part;
    public final PalaeophisPart tail3Part;
    private final PalaeophisPart[] allParts;
    private float fishPitch = 0;
    private float prevFishPitch = 0;
    private float fakeYRot;
    private float[][] trailTransformations = new float[128][2];
    private int trailPointer = -1;

    public Palaeophis(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        this.fakeYRot = this.getYRot();
        this.head1Part = new PalaeophisPart(this, this, 0.9F, 0.9F);
        this.head2Part = new PalaeophisPart(this, head1Part, 1.5F, 0.9F);
        this.head3Part = new PalaeophisPart(this, head2Part, 1.5F, 0.9F);
        this.tail1Part = new PalaeophisPart(this, head3Part, 1.5F, 0.9F);
        this.tail2Part = new PalaeophisPart(this, tail1Part, 1.5F, 0.9F);
        this.tail3Part = new PalaeophisPart(this, tail2Part, 1.5F, 0.9F);
        this.allParts = new PalaeophisPart[]{head1Part, head2Part, head3Part, tail1Part, tail2Part, tail3Part};
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.55F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TARTUOSTEUS_FOOD), false));
        this.goalSelector.addGoal(5, new CustomizableRandomSwimGoal(this, 1.0D, 10));
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
    public void tick() {
        this.prevFishPitch = fishPitch;
        super.tick();
        this.yBodyRot = Mth.approachDegrees(this.yBodyRotO, yBodyRot, getHeadRotSpeed());
        this.fakeYRot = Mth.approachDegrees(fakeYRot, this.yBodyRot, 10);
        this.tickMultipart();
        float targetPitch = this.isInWaterOrBubble() ? Mth.clamp((float) this.getDeltaMovement().y * 25, -1.4F, 1.4F) * -(float) (180F / (float) Math.PI) : 0;
        this.fishPitch = Mth.approachDegrees(fishPitch, targetPitch, 1);
    }

    private void tickMultipart() {
        if (trailPointer == -1) {
            this.fakeYRot = this.yBodyRot;
            for (int i = 0; i < this.trailTransformations.length; i++) {
                this.trailTransformations[i][0] = this.fishPitch;
                this.trailTransformations[i][1] = this.fakeYRot;
            }
        }
        if (++this.trailPointer == this.trailTransformations.length) {
            this.trailPointer = 0;
        }
        this.trailTransformations[this.trailPointer][0] = this.fishPitch;
        this.trailTransformations[this.trailPointer][1] = this.fakeYRot;

        Vec3[] avector3d = new Vec3[this.allParts.length];
        for (int j = 0; j < this.allParts.length; ++j) {
            avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
        }
        this.head1Part.setToTransformation(new Vec3(0, 0, 7.0F), this.getTrailTransformation(5, 0, 1.0F), this.getTrailTransformation(5, 1, 1.0F));
        this.head2Part.setToTransformation(new Vec3(0, 0, -2.5F), this.getTrailTransformation(10, 0, 1.0F), this.getTrailTransformation(10, 1, 1.0F));
        this.head3Part.setToTransformation(new Vec3(0, 0, -2.5F), this.getTrailTransformation(15, 0, 1.0F), this.getTrailTransformation(15, 1, 1.0F));
        this.tail1Part.setToTransformation(new Vec3(0, 0, -5.0F), this.getTrailTransformation(20, 0, 1.0F), this.getTrailTransformation(20, 1, 1.0F));
        this.tail2Part.setToTransformation(new Vec3(0, 0, -2.5F), this.getTrailTransformation(25, 0, 1.0F), this.getTrailTransformation(25, 1, 1.0F));
        this.tail3Part.setToTransformation(new Vec3(0, 0, -2.5F), this.getTrailTransformation(30, 0, 1.0F), this.getTrailTransformation(30, 1, 1.0F));
        for (int l = 0; l < this.allParts.length; l++) {
            this.allParts[l].xo = avector3d[l].x;
            this.allParts[l].yo = avector3d[l].y;
            this.allParts[l].zo = avector3d[l].z;
            this.allParts[l].xOld = avector3d[l].x;
            this.allParts[l].yOld = avector3d[l].y;
            this.allParts[l].zOld = avector3d[l].z;
        }
    }

    protected static float lerpRotation(float v, float v1) {
        while (v1 - v < -180.0F) {
            v -= 360.0F;
        }
        while (v1 - v >= 180.0F) {
            v += 360.0F;
        }
        return Mth.lerp(0.2F, v, v1);
    }

    public float getFishPitch(float partialTick) {
        return (prevFishPitch + (fishPitch - prevFishPitch) * partialTick);
    }

    public float getTrailTransformation(int pointer, int index, float partialTick) {
        if (this.isRemoved()) {
            partialTick = 1.0F;
        }
        int i = this.trailPointer - pointer & 127;
        int j = this.trailPointer - pointer - 1 & 127;
        float d0 = this.trailTransformations[j][index];
        float d1 = this.trailTransformations[i][index] - d0;
        return d0 + d1 * partialTick;
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