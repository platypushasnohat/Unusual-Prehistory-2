package com.barlinc.unusual_prehistory.entity.mob.future;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.goals.update_4.TherizinosaurusAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.update_4.TherizinosaurusForageLeavesGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.BiConsumer;

public class Therizinosaurus extends PrehistoricMob implements VibrationSystem {

    private static final EntityDataAccessor<Boolean> SHAVED = SynchedEntityData.defineId(Therizinosaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FORAGING_TREE = SynchedEntityData.defineId(Therizinosaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ANGER_LEVEL = SynchedEntityData.defineId(Therizinosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANGER_TIME = SynchedEntityData.defineId(Therizinosaurus.class, EntityDataSerializers.INT);

    private final TherizinosaurusVibrationUser vibrationUser;
    private final DynamicGameEventListener<TherizinosaurusVibrationListener> loudVibrationListener;
    private final VibrationSystem.Data vibrationData;

    public int slashCooldown = 0;

    public int vibrationCooldown = 0;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState forageLowAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState forageHighAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState stretchAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState alert1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState alert2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState roarAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState angryAnimationState = new SmoothAnimationState();

    public int attackTicks;

    private int alertTicks;
    private int foragingTicks;
    private int roarTicks;

    private int shakeCooldown = 1200 + this.getRandom().nextInt(1200);
    private int stretchCooldown = 1400 + this.getRandom().nextInt(1400);

    public Therizinosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.1F);
        this.vibrationUser = new TherizinosaurusVibrationUser(this);
        this.vibrationData = new VibrationSystem.Data();
        this.loudVibrationListener = new DynamicGameEventListener<>(new TherizinosaurusVibrationListener(this, vibrationUser.getPositionSource(), GameEvent.JUKEBOX_PLAY.getNotificationRadius()));
    }

//    @Override
//    protected void registerGoals() {
//        this.goalSelector.addGoal(0, new FloatGoal(this));
//        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D, 10, 4));
//        this.goalSelector.addGoal(2, new TherizinosaurusAttackGoal(this));
//        this.goalSelector.addGoal(3, new TherizinosaurusFreakOutGoal(this));
//        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.THERIZINOSAURUS_FOOD), false) {
//            @Override
//            public boolean canUse() {
//                return super.canUse() && Therizinosaurus.this.getAngerLevel() < 4;
//            }
//        });
//        this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1) {
//            @Override
//            public boolean canUse() {
//                return super.canUse() && Therizinosaurus.this.getAngerLevel() < 4;
//            }
//        });
//        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
//        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
//        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
//        this.goalSelector.addGoal(9, new TherizinosaurusSleepGoal(this));
//        this.goalSelector.addGoal(10, new TherizinosaurusForageLeavesGoal(this, 1, 12));
//        this.goalSelector.addGoal(11, new TherizinosaurusShakeGoal(this));
//        this.goalSelector.addGoal(11, new TherizinosaurusStretchGoal(this));
//        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
//        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, Mob.class, 60, true, true, this::targetsEverything));
//        this.targetSelector.addGoal(2, new TargetNearbyPlayersGoal(this, 80, 5.0D));
//    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 160.0D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.98F;
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && this.horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.5D * this.getBbHeight();
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        if (this.isBaby()) return super.getDimensions(pose).scale(0.6F, 0.5F);
        return super.getDimensions(pose);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.THERIZINOSAURUS_FOOD);
    }

    public boolean targetsEverything(Entity entity) {
        return this.getAngerLevel() > 3;
    }

    @Override
    public boolean isEepyTime() {
        return this.level().isDay();
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0, 2.3F, -this.getBbWidth() * 0.8F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (slashCooldown > 0) slashCooldown--;
            if (vibrationCooldown > 0) vibrationCooldown--;
            if (this.getAngerTime() > 0) this.setAngerTime(this.getAngerTime() - 1);
        }

        if (!this.level().isClientSide && this.getAngerTime() == 0) {
            if (this.getAngerLevel() > 0) {
                this.setAngerLevel(this.getAngerLevel() - 1);
                this.setAngerTime(900 + this.getRandom().nextInt(300));
            }
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (!this.level().isClientSide) {
            if (attackTicks > 0) attackTicks--;
            if (foragingTicks > 0) foragingTicks--;
            if (alertTicks > 0) alertTicks--;
            if (roarTicks > 0) roarTicks--;
            if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) this.setPose(Pose.STANDING);
            if (foragingTicks == 0 && this.getPose() == UP2Poses.FORAGING.get()) this.setPose(Pose.STANDING);
            if (alertTicks == 0 && this.getPose() == UP2Poses.ALERTED.get()) this.setPose(Pose.STANDING);
            if (roarTicks == 0 && this.getPose() == UP2Poses.ENRAGED.get()) this.setPose(Pose.STANDING);
            if (!this.isEepy()) {
                if (shakeCooldown > 0) shakeCooldown--;
                if (stretchCooldown > 0) stretchCooldown--;
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        if (attackTicks == 0 && (this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted())) {
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
        }
        if (foragingTicks == 0 && (this.forageLowAnimationState.isStarted() || this.forageHighAnimationState.isStarted())) {
            this.forageLowAnimationState.stop();
            this.forageHighAnimationState.stop();
        }
        if (alertTicks == 0 && (this.alert1AnimationState.isStarted() || this.alert2AnimationState.isStarted())) {
            this.alert1AnimationState.stop();
            this.alert2AnimationState.stop();
        }
        if (roarTicks == 0 && this.roarAnimationState.isStarted()) this.roarAnimationState.stop();

        this.idleAnimationState.animateWhen(this.getPose() != UP2Poses.ATTACKING.get() && !this.isEepy() && !this.isInWater() && this.getPose() != UP2Poses.FORAGING.get(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWater() && this.getPose() != UP2Poses.ATTACKING.get(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.attack1AnimationState.isStarted(), this.tickCount);
        this.attack2AnimationState.animateWhen(this.attack2AnimationState.isStarted(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.angryAnimationState.animateWhen(this.getAngerLevel() > 3, this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                if (this.getRandom().nextBoolean()) this.attack1AnimationState.start(this.tickCount);
                else this.attack2AnimationState.start(this.tickCount);
                this.attackTicks = 20;
            } else if (this.getPose() == UP2Poses.FORAGING.get()) {
                if (this.isForagingTree()) this.forageHighAnimationState.start(this.tickCount);
                else this.forageLowAnimationState.start(this.tickCount);
                this.foragingTicks = 60;
            } else if (this.getPose() == UP2Poses.ALERTED.get()) {
                if (this.getRandom().nextBoolean()) this.alert1AnimationState.start(this.tickCount);
                else this.alert2AnimationState.start(this.tickCount);
                this.alertTicks = 80;
            } else if (this.getPose() == UP2Poses.ENRAGED.get()) {
                this.roarAnimationState.start(this.tickCount);
                this.roarTicks = 60;
            } else if (this.getPose() == Pose.STANDING) {
                this.attack1AnimationState.stop();
                this.attack2AnimationState.stop();
                this.forageLowAnimationState.stop();
                this.forageHighAnimationState.stop();
                this.roarAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case 67 -> this.shakeAnimationState.start(this.tickCount);
            case 68 -> this.shakeAnimationState.stop();
            case 69 -> this.stretchAnimationState.start(this.tickCount);
            case 70 -> this.stretchAnimationState.stop();
            default -> super.handleEntityEvent(id);
        }
    }

    protected void shakeCooldown() {
        this.shakeCooldown = 1200 + this.getRandom().nextInt(1200);
    }

    protected void stretchCooldown() {
        this.stretchCooldown = 1400 + this.getRandom().nextInt(1400);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 2 || this.getPose() == UP2Poses.FORAGING.get() || this.getPose() == UP2Poses.ALERTED.get() || this.getPose() == UP2Poses.ENRAGED.get();
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3, 3, 3);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHAVED, false);
        this.entityData.define(FORAGING_TREE, false);
        this.entityData.define(ANGER_LEVEL, 0);
        this.entityData.define(ANGER_TIME, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Shaved", this.isShaved());
        compoundTag.putInt("AngerLevel", this.getAngerLevel());
        compoundTag.putInt("AngerTime", this.getAngerTime());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setShaved(compoundTag.getBoolean("Shaved"));
        this.setAngerLevel(compoundTag.getInt("AngerLevel"));
        this.setAngerTime(compoundTag.getInt("AngerTime"));
    }

    public boolean isShaved() {
        return this.entityData.get(SHAVED);
    }
    public void setShaved(boolean shaved) {
        this.entityData.set(SHAVED, shaved);
    }

    public boolean isForagingTree() {
        return this.entityData.get(FORAGING_TREE);
    }
    public void setForagingTree(boolean foragingTree) {
        this.entityData.set(FORAGING_TREE, foragingTree);
    }

    public int getAngerLevel() {
        return this.entityData.get(ANGER_LEVEL);
    }
    public void setAngerLevel(int anger) {
        this.entityData.set(ANGER_LEVEL, anger);
    }

    public int getAngerTime() {
        return this.entityData.get(ANGER_TIME);
    }
    public void setAngerTime(int angerTime) {
        this.entityData.set(ANGER_TIME, angerTime);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        Therizinosaurus therizinosaurus = UP2Entities.THERIZINOSAURUS.get().create(level);
        therizinosaurus.setVariant(this.getVariant());
        return therizinosaurus;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.THERIZINOSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.THERIZINOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.THERIZINOSAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.THERIZINOSAURUS_STEP.get(), this.isBaby() ? 0.1F : 0.5F, this.isBaby() ? 1.7F : 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 300;
    }

    public static boolean canSpawn(EntityType<Therizinosaurus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.THERIZINOSAURUS_SPAWNABLE_ON);
    }

    // Vibrations
    @Override
    public void updateDynamicGameEventListener(@NotNull BiConsumer<DynamicGameEventListener<?>, ServerLevel> biConsumer) {
        if (this.level() instanceof ServerLevel serverLevel) {
            biConsumer.accept(this.loudVibrationListener, serverLevel);
        }
    }

    @Override
    public @NotNull Data getVibrationData() {
        return this.vibrationData;
    }

    @Override
    public @NotNull User getVibrationUser() {
        return this.vibrationUser;
    }

    private static class TherizinosaurusVibrationUser implements VibrationSystem.User {

        private final Therizinosaurus therizinosaurus;
        private final PositionSource positionSource;

        public TherizinosaurusVibrationUser(Therizinosaurus therizinosaurus) {
            this.therizinosaurus = therizinosaurus;
            this.positionSource = new EntityPositionSource(therizinosaurus, therizinosaurus.getEyeHeight());
        }

        @Override
        public int getListenerRadius() {
            return 24;
        }

        @Override
        public @NotNull PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public boolean canReceiveVibration(@NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull GameEvent gameEvent, GameEvent.@NotNull Context context) {
            if (therizinosaurus.isNoAi() || therizinosaurus.isBaby() || therizinosaurus.isShaved() || therizinosaurus.vibrationCooldown > 0) return false;
            return therizinosaurus.getTarget() != null;
        }

        @Override
        public void onReceiveVibration(@NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity entity2, float f) {
        }

        @Override
        public @NotNull TagKey<GameEvent> getListenableEvents() {
            return GameEventTags.ALLAY_CAN_LISTEN;
        }
    }

    private static class TherizinosaurusVibrationListener implements GameEventListener {

        private final Therizinosaurus therizinosaurus;
        private final PositionSource listenerSource;
        private final int listenerRadius;

        public TherizinosaurusVibrationListener(Therizinosaurus therizinosaurus, PositionSource positionSource, int i) {
            this.therizinosaurus = therizinosaurus;
            this.listenerSource = positionSource;
            this.listenerRadius = i;
        }

        @Override
        public @NotNull PositionSource getListenerSource() {
            return this.listenerSource;
        }

        @Override
        public int getListenerRadius() {
            return this.listenerRadius;
        }

        @Override
        public boolean handleGameEvent(@NotNull ServerLevel serverLevel, @NotNull GameEvent gameEvent, GameEvent.@NotNull Context context, @NotNull Vec3 vec3) {
            BlockPos blockPos = BlockPos.containing(vec3);
            if (Therizinosaurus.isLoudNoise(gameEvent, serverLevel, blockPos) && therizinosaurus.vibrationCooldown == 0 && therizinosaurus.getAngerLevel() < 4) {
                if (therizinosaurus.getAngerLevel() < 3) {
                    this.therizinosaurus.setPose(UP2Poses.ALERTED.get());
                    this.therizinosaurus.setAngerLevel(therizinosaurus.getAngerLevel() + 1);
                    this.therizinosaurus.playSound(UP2SoundEvents.THERIZINOSAURUS_NOTICE.get(), 1.0F, 0.9F + therizinosaurus.getRandom().nextFloat() * 0.15F);
                } else {
                    this.therizinosaurus.setPose(UP2Poses.ENRAGED.get());
                    this.therizinosaurus.setAngerLevel(therizinosaurus.getAngerLevel() + 1);
                    this.therizinosaurus.playSound(UP2SoundEvents.THERIZINOSAURUS_ROAR.get(), 3.0F, 0.9F + therizinosaurus.getRandom().nextFloat() * 0.15F);
                }
                this.therizinosaurus.setAngerTime(900 + serverLevel.getRandom().nextInt(300));
                this.therizinosaurus.vibrationCooldown = 85;
                return true;
            }
            return false;
        }
    }

    public static boolean isLoudNoise(GameEvent gameEvent, ServerLevel serverLevel, BlockPos blockPos) {
        return gameEvent == GameEvent.EXPLODE || gameEvent == GameEvent.INSTRUMENT_PLAY || gameEvent == GameEvent.JUKEBOX_PLAY || (gameEvent == GameEvent.BLOCK_CHANGE && serverLevel.getBlockState(blockPos).is(Blocks.BELL));
    }

    // Goals
    private static class TherizinosaurusSleepGoal extends SleepingGoal {

        private final Therizinosaurus therizinosaurus;

        public TherizinosaurusSleepGoal(Therizinosaurus therizinosaurus) {
            super(therizinosaurus);
            this.therizinosaurus = therizinosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && therizinosaurus.getAngerLevel() <= 0;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && therizinosaurus.getAngerLevel() <= 0;
        }
    }

    private static class TherizinosaurusFreakOutGoal extends Goal {

        protected final Therizinosaurus therizinosaurus;
        protected double wantedX;
        protected double wantedY;
        protected double wantedZ;

        public TherizinosaurusFreakOutGoal(Therizinosaurus therizinosaurus) {
            this.therizinosaurus = therizinosaurus;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (therizinosaurus.isVehicle() || therizinosaurus.getTarget() != null) {
                return false;
            } else {
                Vec3 position = this.getPosition();
                if (position == null) {
                    return false;
                } else if (therizinosaurus.getAngerLevel() > 3) {
                    this.wantedX = position.x;
                    this.wantedY = position.y;
                    this.wantedZ = position.z;
                    return true;
                }
            }
            return false;
        }

        @Nullable
        protected Vec3 getPosition() {
            return LandRandomPos.getPos(therizinosaurus, 10, 4);
        }

        @Override
        public boolean canContinueToUse() {
            return therizinosaurus.getAngerLevel() > 3 && !therizinosaurus.getNavigation().isDone() && therizinosaurus.getTarget() == null;
        }

        @Override
        public void start() {
            this.therizinosaurus.getNavigation().moveTo(wantedX, wantedY, wantedZ, 1.5D);
            this.therizinosaurus.setRunning(true);
        }

        @Override
        public void tick() {
            this.therizinosaurus.getLookControl().setLookAt(wantedX, wantedY, wantedZ, 30F, 30F);
        }

        @Override
        public void stop() {
            this.therizinosaurus.setRunning(false);
            this.therizinosaurus.getNavigation().stop();
        }
    }

    private static class TherizinosaurusShakeGoal extends IdleAnimationGoal {

        private final Therizinosaurus therizinosaurus;

        public TherizinosaurusShakeGoal(Therizinosaurus therizinosaurus) {
            super(therizinosaurus, 40, 1, (byte) 67, (byte) 68, false);
            this.therizinosaurus = therizinosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && therizinosaurus.getAngerLevel() < 4 && therizinosaurus.shakeCooldown == 0 && !therizinosaurus.isBaby();
        }

        @Override
        public void stop() {
            super.stop();
            this.therizinosaurus.shakeCooldown();
        }
    }

    private static class TherizinosaurusStretchGoal extends IdleAnimationGoal {

        private final Therizinosaurus therizinosaurus;

        public TherizinosaurusStretchGoal(Therizinosaurus therizinosaurus) {
            super(therizinosaurus, 100, 2, (byte) 69, (byte) 70);
            this.therizinosaurus = therizinosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && therizinosaurus.getAngerLevel() < 4 && therizinosaurus.stretchCooldown == 0 && !therizinosaurus.isBaby();
        }

        @Override
        public void stop() {
            super.stop();
            this.therizinosaurus.stretchCooldown();
        }
    }
}
