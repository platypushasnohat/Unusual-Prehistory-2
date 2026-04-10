package com.barlinc.unusual_prehistory.entity.mob.update_1;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.BreedableMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Talpanas extends BreedableMob {

    public static final EntityDataAccessor<Integer> LIGHT_THRESHOLD = SynchedEntityData.defineId(Talpanas.class, EntityDataSerializers.INT);

    public final SmoothAnimationState flapAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState peckAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();

    private int peckCooldown = 300 + this.getRandom().nextInt(300);
    private int shakeCooldown = 240 + this.getRandom().nextInt(240);

    public Talpanas(EntityType<? extends BreedableMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 1.0F);
        this.setPathfindingMalus(PathType.LAVA, 1.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 1.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, 1.0F);
        this.setPathfindingMalus(PathType.DANGER_OTHER, 1.0F);
        this.setPathfindingMalus(PathType.DAMAGE_OTHER, 1.0F);
        this.setPathfindingMalus(PathType.DAMAGE_CAUTIOUS, 1.0F);
        this.setPathfindingMalus(PathType.POWDER_SNOW, 1.0F);
        this.setPathfindingMalus(PathType.DAMAGE_CAUTIOUS, 1.0F);
        this.setPathfindingMalus(PathType.DAMAGE_CAUTIOUS, 1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PrehistoricSitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new PrehistoricAvoidEntityGoal<>(this, Player.class, 4.0F, 1.5D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 4.0F, 1.5D, entity -> entity.getType().is(UP2EntityTags.TALPANAS_AVOIDS)));
        this.goalSelector.addGoal(3, new TalpanasSeekShelterGoal(this));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TALPANAS_FOOD), false));
        this.goalSelector.addGoal(6, new PrehistoricRandomStrollGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 3.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new SleepingGoal(this));
        this.goalSelector.addGoal(9, new TalpanasPeckGoal(this));
        this.goalSelector.addGoal(9, new TalpanasShakeGoal(this));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        SmoothGroundPathNavigation navigation = new SmoothGroundPathNavigation(this, level);
        navigation.setAvoidSun(true);
        return navigation;
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return this.getLightPathfindingFavor(pos, level);
    }

    private float getLightPathfindingFavor(BlockPos pos, LevelReader level) {
        if (level.getBrightness(LightLayer.BLOCK, pos) <= this.getLightThreshold() || level.getBrightness(LightLayer.SKY, pos) <= this.getLightThreshold()) {
            return 10.0F;
        }
        else return 0.0F;
    }

    @Override
    public float getAdditionalStepHeight() {
        return this.isRunning() ? 0.4F : super.getAdditionalStepHeight();
    }

    @Override
    public void aiStep() {
        super.aiStep();

        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && !this.isInWaterOrBubble() && vec3.y < 0.0) {
            this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
            if (this.getMovementEmission().emitsEvents()) {
                this.gameEvent(GameEvent.FLAP);
            }
        }
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            this.getNavigation().stop();
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
        return false;
    }

    @Override
    public boolean isEepyTime() {
        return this.level().isDay() && this.level().getBrightness(LightLayer.BLOCK, this.blockPosition()) <= this.getLightThreshold() && !this.level().canSeeSky(this.blockPosition());
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0, 0.15F, -this.getBbWidth() * 0.5F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.onGround() || this.isInWaterOrBubble() && !this.isEepy(), this.tickCount);
        this.flapAnimationState.animateWhen(!this.onGround() && !this.isInWaterOrBubble() && !this.isEepy(), this.tickCount);
        this.peckAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (!this.level().isClientSide && !this.isEepy()) {
            if (peckCooldown > 0) peckCooldown--;
            if (shakeCooldown > 0) shakeCooldown--;
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 2;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LIGHT_THRESHOLD, 5);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("LightThreshold", this.getLightThreshold());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLightThreshold(compoundTag.getInt("LightThreshold"));
    }

    public int getLightThreshold() {
        return this.entityData.get(LIGHT_THRESHOLD);
    }

    public void setLightThreshold(int threshold) {
        this.entityData.set(LIGHT_THRESHOLD, threshold);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.TALPANAS_FOOD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.TALPANAS.get().create(serverLevel);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.TALPANAS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.TALPANAS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.TALPANAS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.06F, 1.0F);
    }

    // Goals
    private static class TalpanasSeekShelterGoal extends Goal {

        protected final Talpanas talpanas;
        private double wantedX;
        private double wantedY;
        private double wantedZ;

        public TalpanasSeekShelterGoal(Talpanas talpanas) {
            this.talpanas = talpanas;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (talpanas.level().getBrightness(LightLayer.BLOCK, talpanas.blockPosition()) > talpanas.getLightThreshold() || (talpanas.level().isDay() && talpanas.level().canSeeSky(this.talpanas.blockPosition()))) {
                return this.setWantedPos();
            } else {
                return false;
            }
        }

        protected boolean setWantedPos() {
            Vec3 vec3 = this.getHidePos();
            if (vec3 == null) {
                return false;
            } else {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                return true;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return !this.talpanas.getNavigation().isDone();
        }

        @Override
        public void start() {
            this.talpanas.getNavigation().moveTo(wantedX, wantedY, wantedZ, 1.2D);
        }

        @Override
        public void tick() {
            this.talpanas.getLookControl().setLookAt(this.wantedX, this.wantedY, this.wantedZ, 30F, 30F);
        }

        @Nullable
        protected Vec3 getHidePos() {
            RandomSource randomsource = talpanas.getRandom();
            BlockPos blockpos = talpanas.blockPosition();
            for (int i = 0; i < 10; i++) {
                BlockPos blockpos1 = blockpos.offset(randomsource.nextInt(20) - 10, randomsource.nextInt(6) - 3, randomsource.nextInt(20) - 10);
                if ((talpanas.level().getBrightness(LightLayer.BLOCK, blockpos1) <= talpanas.getLightThreshold() || !talpanas.level().canSeeSky(blockpos1)) && talpanas.getWalkTargetValue(blockpos1) > 0.0F) {
                    return Vec3.atBottomCenterOf(blockpos1);
                }
            }
            return null;
        }
    }

    private static class TalpanasPeckGoal extends IdleAnimationGoal {

        private final Talpanas talpanas;

        public TalpanasPeckGoal(Talpanas talpanas) {
            super(talpanas, 40, 1);
            this.talpanas = talpanas;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && talpanas.shakeCooldown == 0 && talpanas.level().getBlockState(talpanas.blockPosition().below()).is(UP2BlockTags.TALPANAS_PECKING_BLOCKS);
        }

        @Override
        public void stop() {
            super.stop();
            this.talpanas.shakeCooldown = 240 + talpanas.getRandom().nextInt(240);
        }
    }

    private static class TalpanasShakeGoal extends IdleAnimationGoal {

        private final Talpanas talpanas;

        public TalpanasShakeGoal(Talpanas talpanas) {
            super(talpanas, 20, 2);
            this.talpanas = talpanas;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && talpanas.shakeCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.talpanas.shakeCooldown = 240 + talpanas.getRandom().nextInt(240);
        }
    }
}