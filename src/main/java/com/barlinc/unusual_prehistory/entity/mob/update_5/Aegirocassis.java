package com.barlinc.unusual_prehistory.entity.mob.update_5;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.AquaticLeapGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.CustomizableRandomSwimGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.AquaticPathNavigation;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.AmbientMob;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import com.barlinc.unusual_prehistory.entity.utils.MobUtils;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class Aegirocassis extends PrehistoricAquaticMob implements LeapingMob {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Aegirocassis.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SPAWN_CHILDREN_COOLDOWN = SynchedEntityData.defineId(Aegirocassis.class, EntityDataSerializers.INT);

    public final AegirocassisPart headPart;
    public final AegirocassisPart tailPart1;
    public final AegirocassisPart tailPart2;
    private final AegirocassisPart[] allParts;

    private boolean wasPreviouslyBaby;

    private float fakeYRot = 0;
    private float[] yawBuffer = new float[128];
    private int yawPointer = -1;

    public float prevGlowProgress;
    public float glowProgress;

    public final SmoothAnimationState eyesAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState mouthAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState leapStartAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState leapAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState rollAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState eatAnimationState = new SmoothAnimationState(1.0F);

    private int leapStartTicks;
    private int leapTicks;

    public Aegirocassis(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(false);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 1000, 3, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 2);
        this.headPart = new AegirocassisPart(this, this, 3.5F, 3.9F);
        this.tailPart1 = new AegirocassisPart(this, this, 3.5F, 3.9F);
        this.tailPart2 = new AegirocassisPart(this, tailPart1, 3.5F, 3.9F);
        this.allParts = new AegirocassisPart[]{headPart, tailPart1, tailPart2};
        this.setId(ENTITY_COUNTER.getAndAdd(allParts.length + 1) + 1);
        this.fakeYRot = this.getYRot();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 220.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.7F)
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LargeBabyPanicGoal(this, 2.0D, 10, 4));
        this.goalSelector.addGoal(1, new AegirocassisTryToFlyGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.AEGIROCASSIS_FOOD), false));
        this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1, 40, 30, 15, 3, true));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && Aegirocassis.this.isInWaterOrBubble();
            }
        });
//        this.goalSelector.addGoal(6, new AegirocassisRollGoal(this));
//        this.goalSelector.addGoal(6, new AegirocassisEatGoal(this));
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return this.level().isNight() ? MobUtils.getSurfacePathfindingFavor(pos, level) : MobUtils.getDepthPathfindingFavor(pos, level);
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.allParts.length; i++) {
            this.allParts[i].setId(id + i + 1);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.AEGIROCASSIS_FOOD);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    protected void switchNavigator(boolean inShallows) {
        this.navigation.stop();
        if (inShallows) {
            this.navigation = new SmoothAmphibiousPathNavigation(this, this.level());
            this.shallowWater = true;
        } else {
            this.navigation = new AquaticPathNavigation(this, this.level(), true);
            this.shallowWater = false;
        }
    }

    @Override
    public boolean isPushable() {
        return this.isBaby();
    }

    @Override
    public boolean shouldFlop() {
        return false;
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.25F : 1.0F;
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?> @NotNull [] getParts() {
        return allParts;
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(4, 6, 4);
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        UnusualPrehistory2.PROXY.clearSoundCacheFor(this);
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
    public boolean refuseToLook() {
        return super.refuseToLook() && this.getIdleState() != 2;
    }

    @Override
    public void tick() {
        this.tickMultipart();
        super.tick();

        this.prevGlowProgress = glowProgress;
        if (this.isInWaterOrBubble() && glowProgress < 5.0F) glowProgress++;
        else if (!this.isInWaterOrBubble() && glowProgress > 0.0F) glowProgress--;

        this.fakeYRot = Mth.approachDegrees(fakeYRot, this.yBodyRot, 10);

        if (wasPreviouslyBaby != this.isBaby()) {
            this.wasPreviouslyBaby = this.isBaby();
            this.refreshDimensions();
            for (AegirocassisPart aegirocassisPart : this.allParts) {
                aegirocassisPart.refreshDimensions();
            }
        }

        if (this.getPose() == UP2Poses.START_FLYING.get() && leapStartTicks > 20) {
            if (!this.isInWaterOrBubble() && this.getDeltaMovement().y < 0.0) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0F, 0.3F, 1.0F));
            }
        }
        if (this.getPose() == Pose.FALL_FLYING && (this.isInWaterOrBubble() || this.onGround())) {
            this.setPose(Pose.STANDING);
        }

        if (this.level().isClientSide && this.isAlive() && this.isLeaping()) UnusualPrehistory2.PROXY.playWorldSound(this, (byte) 2);

        final boolean shallowWater = this.isInShallowWater();
        if (shallowWater && !this.shallowWater) {
            this.switchNavigator(true);
        } else if (!shallowWater && this.shallowWater) {
            this.switchNavigator(false);
        }

        if (this.getSpawnChildrenCooldown() == 0 && !this.isBaby()) {
            if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
                Entity entity = this.getRandom().nextBoolean() ? UP2Entities.AMPYX.get().create(serverLevel) : UP2Entities.SETAPEDITES.get().create(serverLevel);
                Vec3 vec3 = this.blockPosition().getCenter();
                if (entity instanceof AmbientMob mob) {
                    mob.setShouldBeRestricted(true);
                    entity.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(serverLevel.getRandom().nextFloat() * 360.0F), 0.0F);
                    serverLevel.addFreshEntity(entity);
                    EventHooks.finalizeMobSpawn(mob, serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.NATURAL, null);
                }
            }
            this.setSpawnChildrenCooldown(2600 + this.level().getRandom().nextInt(1200));
        }
    }

    public float getGlowProgress(float partialTicks) {
        return (prevGlowProgress + (glowProgress - prevGlowProgress) * partialTicks) * 0.2F;
    }

    @Override
    public void setupAnimationStates() {
        this.eyesAnimationState.animateWhen(this.isAlive() && !this.isTryingToFly(), this.tickCount);
        this.mouthAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isTryingToFly() && this.getIdleState() != 2, this.tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isTryingToFly(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isTryingToFly(), this.tickCount);
        this.leapStartAnimationState.animateWhen(this.getPose() == UP2Poses.START_FLYING.get(), this.tickCount);
        this.leapAnimationState.animateWhen(this.getPose() == Pose.FALL_FLYING, this.tickCount);
        this.rollAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.eatAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
    }

    public boolean isTryingToFly() {
        return this.getPose() == UP2Poses.START_FLYING.get() || this.getPose() == Pose.FALL_FLYING;
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (leapStartTicks > 0) leapStartTicks--;
        if (leapTicks > 0) leapTicks--;
        if (leapStartTicks == 0 && this.getPose() == UP2Poses.START_FLYING.get()) this.setPose(Pose.FALL_FLYING);
        if (leapTicks == 0 && this.getPose() == Pose.FALL_FLYING) this.setPose(Pose.STANDING);
        if (this.isInWaterOrBubble()) {
            if (this.getSpawnChildrenCooldown() > 0) {
                this.setSpawnChildrenCooldown(this.getSpawnChildrenCooldown() - 1);
            }
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.START_FLYING.get()) {
                this.leapStartTicks = 120;
            }
            else if (this.getPose() == Pose.FALL_FLYING) {
                this.leapTicks = 60;
            }
        }
        super.onSyncedDataUpdated(accessor);
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
        float headOffset = this.isBaby() ? 0.8F : 4.0F;
        float tailOffset = this.isBaby() ? 0.5F : 4.0F;
        this.headPart.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, headOffset), this.getXRot() * 0.33F, this.getYHeadRot()).add(center));
        this.tailPart1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -tailOffset), this.getXRot() * 0.33F, this.getYawFromBuffer(2, 1.0F)).add(center));
        this.tailPart2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -tailOffset), this.getXRot() * 0.33F, this.getYawFromBuffer(4, 1.0F)).add(this.tailPart1.centeredPosition()));
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LEAPING, false);
        builder.define(SPAWN_CHILDREN_COOLDOWN, 70);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpawnChildrenCooldown", this.getSpawnChildrenCooldown());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSpawnChildrenCooldown(compoundTag.getInt("SpawnChildrenCooldown"));
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    public int getSpawnChildrenCooldown() {
        return this.entityData.get(SPAWN_CHILDREN_COOLDOWN);
    }

    public void setSpawnChildrenCooldown(int cooldown) {
        this.entityData.set(SPAWN_CHILDREN_COOLDOWN, cooldown);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.AEGIROCASSIS_IDLE.get();
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
    public int getAmbientSoundInterval() {
        return 350;
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.BABY_AEGIROCASSIS_BUCKET.get());
    }

    @Override
    public boolean canBucket() {
        return this.isBaby();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.AEGIROCASSIS.get().create(serverLevel);
    }

    @Override
    public float getSoundVolume() {
        return this.isBaby() ? 1.0F : 4.0F;
    }

    // Goals
    private static class AegirocassisTryToFlyGoal extends AquaticLeapGoal {

        private final Aegirocassis aegirocassis;

        public AegirocassisTryToFlyGoal(Aegirocassis aegirocassis) {
            super(aegirocassis, 20, 1.0D, 1.25D);
            this.aegirocassis = aegirocassis;
        }

        @Override
        public void start() {
            super.start();
            this.aegirocassis.setPose(UP2Poses.START_FLYING.get());
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !aegirocassis.isBaby();
        }

        @Override
        public boolean canContinueToUse() {
            Vec3 motion = aegirocassis.getDeltaMovement();
            if (!aegirocassis.isInWaterOrBubble() && motion.y < -0.1D) return false;
            if (aegirocassis.onGround()) return false;
            return aegirocassis.isLeaping();
        }

        @Override
        public void tick() {
            boolean flag = breached;
            this.aegirocassis.getNavigation().stop();
            if (!flag) {
                FluidState fluidstate = aegirocassis.level().getFluidState(aegirocassis.blockPosition());
                this.breached = fluidstate.is(FluidTags.WATER);
            }

            if (breached && !flag) {
                this.aegirocassis.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
            }

            Vec3 vec3 = aegirocassis.getDeltaMovement();
            Vec3 movement = new Vec3(aegirocassis.getMotionDirection().getStepX(), 0, aegirocassis.getMotionDirection().getStepZ()).normalize().scale(0.1F);
            Vec3 glide = new Vec3(movement.x, vec3.y, movement.z);
            this.aegirocassis.setDeltaMovement(glide);
            this.aegirocassis.setYRot(((float) Mth.atan2(aegirocassis.getMotionDirection().getStepZ(), aegirocassis.getMotionDirection().getStepX())) * Mth.RAD_TO_DEG - 90F);
        }
    }

//    private static class AegirocassisRollGoal extends IdleAnimationGoal {
//
//        private final Aegirocassis aegirocassis;
//
//        public AegirocassisRollGoal(Aegirocassis aegirocassis) {
//            super(aegirocassis, 80, 1, false, false);
//            this.aegirocassis = aegirocassis;
//        }
//
//        @Override
//        public boolean canUse() {
//            return super.canUse() && aegirocassis.rollCooldown == 0 && !aegirocassis.isLeaping() && aegirocassis.isInWaterOrBubble();
//        }
//
//        @Override
//        public boolean canContinueToUse() {
//            return super.canContinueToUse() && !aegirocassis.isLeaping() && aegirocassis.isInWaterOrBubble();
//        }
//
//        @Override
//        public void stop() {
//            super.stop();
//            this.aegirocassis.rollCooldown = 800 + aegirocassis.getRandom().nextInt(800);
//        }
//    }
//
//    private static class AegirocassisEatGoal extends IdleAnimationGoal {
//
//        private final Aegirocassis aegirocassis;
//
//        public AegirocassisEatGoal(Aegirocassis aegirocassis) {
//            super(aegirocassis, 40, 2, false, false);
//            this.aegirocassis = aegirocassis;
//        }
//
//        @Override
//        public boolean canUse() {
//            return super.canUse() && aegirocassis.eatCooldown == 0 && !aegirocassis.isLeaping() && aegirocassis.isInWaterOrBubble();
//        }
//
//        @Override
//        public boolean canContinueToUse() {
//            return super.canContinueToUse() && !aegirocassis.isLeaping() && aegirocassis.isInWaterOrBubble();
//        }
//
//        @Override
//        public void stop() {
//            super.stop();
//            this.aegirocassis.eatCooldown = 1200 + aegirocassis.getRandom().nextInt(1200);
//        }
//    }
}