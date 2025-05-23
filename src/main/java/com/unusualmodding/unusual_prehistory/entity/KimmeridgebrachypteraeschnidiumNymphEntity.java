package com.unusualmodding.unusual_prehistory.entity;

import com.google.common.annotations.VisibleForTesting;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.SemiAquaticPathNavigation;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.UP2BlockPos;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2Sounds;
import net.minecraft.commands.arguments.EntityAnchorArgument;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class KimmeridgebrachypteraeschnidiumNymphEntity extends PathfinderMob {

    public static final EntityDataAccessor<Integer> LOOKOUT_COOLDOWN = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumNymphEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> LOOKOUT_TIMER = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumNymphEntity.class, EntityDataSerializers.INT);

    @VisibleForTesting
    public static int ticksToBeDragonfly = Math.abs(-24000);
    private int age;

    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState lookoutAnimationState = new AnimationState();

    public KimmeridgebrachypteraeschnidiumNymphEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }

    protected PathNavigation createNavigation(Level worldIn) {
        SemiAquaticPathNavigation pathNavigation = new SemiAquaticPathNavigation(this, worldIn) {
            public boolean isStableDestination(BlockPos pos) {
                return this.level.getBlockState(pos).getFluidState().isEmpty();
            }
        };
        return pathNavigation;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new NymphFindWaterGoal(this));
        this.goalSelector.addGoal(1, new NymphLeaveWaterGoal(this));
        this.goalSelector.addGoal(2, new NymphLookoutGoal(this));
        this.goalSelector.addGoal(3, new NymphBottomWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.6F;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public float getWalkTargetValue(BlockPos pos, LevelReader worldIn) {
        return worldIn.getFluidState(pos.below()).isEmpty() && worldIn.getFluidState(pos).is(FluidTags.WATER) ? 10.0F : super.getWalkTargetValue(pos, worldIn);
    }

    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            if (this.jumping) {
                this.setDeltaMovement(this.getDeltaMovement().scale(1.4D));
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.72D, 0.0D));
            } else {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.4D));
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.08D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
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
    private static class NymphLookoutGoal extends Goal {

        KimmeridgebrachypteraeschnidiumNymphEntity nymph;

        public NymphLookoutGoal(KimmeridgebrachypteraeschnidiumNymphEntity nymph) {
            this.nymph = nymph;
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

    private static class NymphBottomWanderGoal extends RandomStrollGoal {

        public NymphBottomWanderGoal(KimmeridgebrachypteraeschnidiumNymphEntity nymph, double speed) {
            super(nymph, speed);
        }

        public boolean canUse(){
            interval = this.mob.isInWater() ? 50 : 10;
            return super.canUse();
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse();
        }

        @Nullable
        protected Vec3 getPosition() {
            if(this.mob.isInWater()) {
                BlockPos blockpos = null;
                final RandomSource random = this.mob.getRandom();
                for (int i = 0; i < 15; i++) {
                    BlockPos blockPos = this.mob.blockPosition().offset(random.nextInt(5) - 5 / 2, 3, random.nextInt(5) - 5 / 2);
                    while ((this.mob.level().isEmptyBlock(blockPos) || this.mob.level().getFluidState(blockPos).is(FluidTags.WATER)) && blockPos.getY() > 1) {
                        blockPos = blockPos.below();
                    }
                    if (isBottomOfSeafloor(this.mob.level(), blockPos.above())) {
                        blockpos = blockPos;
                    }
                }
                return blockpos != null ? new Vec3(blockpos.getX() + 0.5F, blockpos.getY() + 0.5F, blockpos.getZ() + 0.5F) : null;
            } else{
                return super.getPosition();
            }
        }

        private boolean isBottomOfSeafloor(LevelAccessor world, BlockPos pos){
            return world.getFluidState(pos).is(FluidTags.WATER) && world.getFluidState(pos.below()).isEmpty() && world.getBlockState(pos.below()).canOcclude();
        }
    }

    private static class NymphFindWaterGoal extends Goal {

        private final KimmeridgebrachypteraeschnidiumNymphEntity nymph;
        private BlockPos targetPos;
        private final int executionChance = 30;

        public NymphFindWaterGoal(KimmeridgebrachypteraeschnidiumNymphEntity creature) {
            this.nymph = creature;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            if (this.nymph.onGround() && !this.nymph.level().getFluidState(this.nymph.blockPosition()).is(FluidTags.WATER)) {
                if (this.nymph.getTarget() != null || this.nymph.getRandom().nextInt(executionChance) == 0) {
                    targetPos = generateTarget();
                    return targetPos != null;
                }
            }
            return false;
        }

        public void start() {
            if (targetPos != null) {
                this.nymph.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1D);
            }
        }

        public void tick() {
            if (targetPos != null) {
                this.nymph.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1D);
            }
        }

        public boolean canContinueToUse() {
            return !this.nymph.getNavigation().isDone() && targetPos != null && !this.nymph.level().getFluidState(this.nymph.blockPosition()).is(FluidTags.WATER);
        }

        public BlockPos generateTarget() {
            BlockPos blockpos = null;
            final RandomSource random = this.nymph.getRandom();
            final int range = 12;
            for (int i = 0; i < 15; i++) {
                BlockPos blockPos = this.nymph.blockPosition().offset(random.nextInt(range) - range / 2, 3, random.nextInt(range) - range / 2);
                while (this.nymph.level().isEmptyBlock(blockPos) && blockPos.getY() > 1) {
                    blockPos = blockPos.below();
                }
                if (this.nymph.level().getFluidState(blockPos).is(FluidTags.WATER)) {
                    blockpos = blockPos;
                }
            }
            return blockpos;
        }
    }

    public class NymphLeaveWaterGoal extends Goal {

        private final KimmeridgebrachypteraeschnidiumNymphEntity nymph;
        private BlockPos targetPos;
        private final int executionChance = 30;

        public NymphLeaveWaterGoal(KimmeridgebrachypteraeschnidiumNymphEntity nymph) {
            this.nymph = nymph;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            if (this.nymph.level().getFluidState(this.nymph.blockPosition()).is(FluidTags.WATER) && (this.nymph.getTarget() != null || this.nymph.getRandom().nextInt(executionChance) == 0)) {
                targetPos = generateTarget();
                return targetPos != null;
            }
            return false;
        }

        public void start() {
            if (targetPos != null) {
                this.nymph.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1D);
            }
        }

        public void tick() {
            if (targetPos != null) {
                this.nymph.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1D);
            }
            if (this.nymph.horizontalCollision && this.nymph.isInWater()) {
                final float f1 = nymph.getYRot() * Mth.DEG_TO_RAD;
                this.nymph.setDeltaMovement(nymph.getDeltaMovement().add(-Mth.sin(f1) * 0.2F, 0.1D, Mth.cos(f1) * 0.2F));
            }
        }

        public boolean canContinueToUse() {
            return !this.nymph.getNavigation().isDone() && targetPos != null && !this.nymph.level().getFluidState(targetPos).is(FluidTags.WATER);
        }

        public BlockPos generateTarget() {
            Vec3 vector3d = LandRandomPos.getPos(this.nymph, 23, 7);
            int tries = 0;
            while(vector3d != null && tries < 8) {
                boolean waterDetected = false;
                for(BlockPos blockpos1 : BlockPos.betweenClosed(Mth.floor(vector3d.x - 2.0D), Mth.floor(vector3d.y - 1.0D), Mth.floor(vector3d.z - 2.0D), Mth.floor(vector3d.x + 2.0D), Mth.floor(vector3d.y), Mth.floor(vector3d.z + 2.0D))) {
                    if (this.nymph.level().getFluidState(blockpos1).is(FluidTags.WATER)) {
                        waterDetected = true;
                        break;
                    }
                }
                if (waterDetected) {
                    vector3d = LandRandomPos.getPos(this.nymph, 23, 7);
                } else {
                    return UP2BlockPos.fromVec3(vector3d);
                }
                tries++;
            }
            return null;
        }
    }
}
