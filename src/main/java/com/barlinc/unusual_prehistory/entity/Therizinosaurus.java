package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.goals.therizinosaurus.TherizinosaurusAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.therizinosaurus.TherizinosaurusForageLeavesGoal;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
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
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class Therizinosaurus extends PrehistoricMob implements VibrationSystem {

    private static final EntityDataAccessor<Boolean> SHAVED = SynchedEntityData.defineId(Therizinosaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FORAGING_TREE = SynchedEntityData.defineId(Therizinosaurus.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(2.2F, 3.98F);

    private final VibrationUser vibrationUser;
    private final DynamicGameEventListener<LoudVibrationListener> loudVibrationListener;
    private final VibrationSystem.Data vibrationData;

    public int slashCooldown = 0;
    public int slashRushCooldown = 100 + this.getRandom().nextInt(60);
    public int chargeCooldown = 250 + this.getRandom().nextInt(400);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sitStartAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sitEndAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState slashRushAnimationState = new AnimationState();
    public final AnimationState chargeStartAnimationState = new AnimationState();
    public final AnimationState chargeEndAnimationState = new AnimationState();
    public final AnimationState forageLowAnimationState = new AnimationState();
    public final AnimationState forageHighAnimationState = new AnimationState();
    public final AnimationState shakeAnimationState = new AnimationState();
    public final AnimationState stretchAnimationState = new AnimationState();
    public final AnimationState clickAnimationState = new AnimationState();
    public final AnimationState alert1AnimationState = new AnimationState();
    public final AnimationState alert2AnimationState = new AnimationState();

    private int attackTicks;
    private int slashRushTicks;
    private int chargeStartTicks;
    private int chargeEndTicks;

    private int alertTicks;
    private int foragingTicks;

    private int shakeCooldown = 1200 + this.getRandom().nextInt(1200);
    private int stretchCooldown = 1400 + this.getRandom().nextInt(1400);
    private int clickCooldown = 1000 + this.getRandom().nextInt(1000);

    public Therizinosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.1F);
        this.vibrationUser = new VibrationUser(this);
        this.vibrationData = new VibrationSystem.Data();
        this.loudVibrationListener = new DynamicGameEventListener<>(new LoudVibrationListener(this, vibrationUser.getPositionSource(), GameEvent.JUKEBOX_PLAY.getNotificationRadius()));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new TherizinosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.THERIZINOSAURUS_FOOD), false));
        this.goalSelector.addGoal(4, new PrehistoricRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new RandomSitGoal(this));
        this.goalSelector.addGoal(8, new TherizinosaurusForageLeavesGoal(this, 1, 12));
        this.goalSelector.addGoal(9, new TherizinosaurusShakeGoal(this));
        this.goalSelector.addGoal(9, new TherizinosaurusStretchGoal(this));
        this.goalSelector.addGoal(9, new TherizinosaurusClickGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new TargetNearbyPlayersGoal(this, 80, 5.0D));
    }

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
        if (useLowerFluidJumpThreshold) {
            return super.getFluidJumpThreshold();
        }
        return 0.5 * getBbHeight();
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
        return (pose == UP2Poses.SITTING.get() ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.THERIZINOSAURUS_FOOD);
    }

    public boolean isWithinChargeYRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - this.getY()) < 2;
    }

    public void slashRushCooldown() {
        this.slashRushCooldown = 100 + this.getRandom().nextInt(60);
    }

    public void chargeCooldown() {
        this.chargeCooldown = 250 + this.getRandom().nextInt(400);
    }

    @Override
    public void tick() {
        super.tick();
        if (slashCooldown > 0) slashCooldown--;
        if (slashRushCooldown > 0) slashRushCooldown--;
        if (chargeCooldown > 0) chargeCooldown--;
    }

    @Override
    public void setupAnimationCooldowns() {
        if (attackTicks > 0) attackTicks--;
        if (slashRushTicks > 0) slashRushTicks--;
        if (chargeStartTicks > 0) chargeStartTicks--;
        if (chargeEndTicks > 0) chargeEndTicks--;
        if (foragingTicks > 0) foragingTicks--;
        if (alertTicks > 0) alertTicks--;
        if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) this.setPose(Pose.STANDING);
        if (slashRushTicks == 0 && this.getPose() == UP2Poses.SLASH_RUSH.get()) this.setPose(Pose.STANDING);
        if (chargeStartTicks == 0 && this.getPose() == UP2Poses.START_CHARGING.get()) this.setPose(UP2Poses.CHARGING.get());
        if (chargeEndTicks == 0 && this.getPose() == UP2Poses.STOP_CHARGING.get()) this.setPose(Pose.STANDING);
        if (foragingTicks == 0 && this.getPose() == UP2Poses.FORAGING.get()) this.setPose(Pose.STANDING);
        if (alertTicks == 0 && this.getPose() == UP2Poses.ALERTED.get()) this.setPose(Pose.STANDING);
        if (shakeCooldown > 0) shakeCooldown--;
        if (stretchCooldown > 0) stretchCooldown--;
        if (clickCooldown > 0) clickCooldown--;
    }

    @Override
    public void setupAnimationStates() {
        if (attackTicks == 0 && (this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted())) {
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
        }
        if (slashRushTicks == 0 && this.slashRushAnimationState.isStarted()) this.slashRushAnimationState.stop();
        if (chargeStartTicks == 0 && this.chargeStartAnimationState.isStarted()) this.chargeStartAnimationState.stop();
        if (chargeEndTicks == 0 && this.chargeEndAnimationState.isStarted()) this.chargeEndAnimationState.stop();
        if (foragingTicks == 0 && (this.forageLowAnimationState.isStarted() || this.forageHighAnimationState.isStarted())) {
            this.forageLowAnimationState.stop();
            this.forageHighAnimationState.stop();
        }
        if (alertTicks == 0 && (this.alert1AnimationState.isStarted() || this.alert2AnimationState.isStarted())) {
            this.alert1AnimationState.stop();
            this.alert2AnimationState.stop();
        }

        this.idleAnimationState.animateWhen(!this.isInAttackingPose() && !this.isInWater() && this.getPose() != UP2Poses.FORAGING.get(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWater() && !this.isInAttackingPose(), this.tickCount);

        if (this.isMobVisuallySitting()) {
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
            this.slashRushAnimationState.stop();
            this.chargeStartAnimationState.stop();
            this.chargeEndAnimationState.stop();
            this.idleAnimationState.stop();
            this.forageLowAnimationState.stop();
            this.forageHighAnimationState.stop();

            if (this.isVisuallySitting()) {
                this.sitStartAnimationState.startIfStopped(this.tickCount);
                this.sitAnimationState.stop();
            } else {
                this.sitStartAnimationState.stop();
                this.sitAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.sitStartAnimationState.stop();
            this.sitAnimationState.stop();
            this.sitEndAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
        }
    }

    public boolean isInAttackingPose() {
        return this.getPose() == UP2Poses.ATTACKING.get() || this.getPose() == UP2Poses.SLASH_RUSH.get() || this.getPose() == UP2Poses.START_CHARGING.get() || this.getPose() == UP2Poses.STOP_CHARGING.get();
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                if (this.getRandom().nextBoolean()) this.attack1AnimationState.start(this.tickCount);
                else this.attack2AnimationState.start(this.tickCount);
                this.attackTicks = 20;
            }
            else if (this.getPose() == UP2Poses.SLASH_RUSH.get()) {
                this.slashRushAnimationState.start(this.tickCount);
                this.slashRushTicks = 50;
            }
            else if (this.getPose() == UP2Poses.START_CHARGING.get()) {
                this.chargeStartAnimationState.start(this.tickCount);
                this.chargeStartTicks = 40;
            }
            else if (this.getPose() == UP2Poses.CHARGING.get()) {
                this.chargeStartAnimationState.stop();
            }
            else if (this.getPose() == UP2Poses.STOP_CHARGING.get()) {
                this.chargeStartAnimationState.stop();
                this.chargeEndAnimationState.start(this.tickCount);
                this.chargeEndTicks = 40;
            }
            else if (this.getPose() == UP2Poses.FORAGING.get()) {
                if (this.isForagingTree()) this.forageHighAnimationState.start(this.tickCount);
                else this.forageLowAnimationState.start(this.tickCount);
                this.foragingTicks = 60;
            }
            else if (this.getPose() == UP2Poses.ALERTED.get()) {
                if (this.getRandom().nextBoolean()) this.alert1AnimationState.start(this.tickCount);
                else this.alert2AnimationState.start(this.tickCount);
                this.alertTicks = 80;
            }
            else if (this.getPose() == Pose.STANDING) {
                this.attack1AnimationState.stop();
                this.attack2AnimationState.stop();
                this.slashRushAnimationState.stop();
                this.chargeStartAnimationState.stop();
                this.chargeEndAnimationState.stop();
                this.forageLowAnimationState.stop();
                this.forageHighAnimationState.stop();
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
            case 71 -> this.clickAnimationState.start(this.tickCount);
            case 72 -> this.clickAnimationState.stop();
            default -> super.handleEntityEvent(id);
        }
    }

    protected void shakeCooldown() {
        this.shakeCooldown = 1200 + this.getRandom().nextInt(1200);
    }

    protected void stretchCooldown() {
        this.stretchCooldown = 1400 + this.getRandom().nextInt(1400);
    }

    protected void clickCooldown() {
        this.clickCooldown = 1000 + this.getRandom().nextInt(1000);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 2 || this.getPose() == UP2Poses.FORAGING.get() || this.getPose() == UP2Poses.ALERTED.get();
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);
        if (!this.isShaved() && itemstack.is(Tags.Items.SHEARS)) {
            this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
            this.gameEvent(GameEvent.SHEAR, player);
            this.setShaved(true);
            return InteractionResult.SUCCESS;
        }
        return type;
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
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Shaved", this.isShaved());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setShaved(compoundTag.getBoolean("Shaved"));
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
        this.playSound(UP2SoundEvents.THERIZINOSAURUS_STEP.get(), this.isBaby() ? 0.2F : 0.5F, this.isBaby() ? 1.2F : 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 220;
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

    private static class VibrationUser implements VibrationSystem.User {

        private final Therizinosaurus therizinosaurus;
        private final PositionSource positionSource;

        public VibrationUser(Therizinosaurus therizinosaurus) {
            this.therizinosaurus = therizinosaurus;
            this.positionSource = new EntityPositionSource(therizinosaurus, therizinosaurus.getEyeHeight());
        }

        @Override
        public int getListenerRadius() {
            return 16;
        }

        @Override
        public @NotNull PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public boolean canReceiveVibration(@NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull GameEvent gameEvent, GameEvent.Context context) {
            if (therizinosaurus.isNoAi() || therizinosaurus.isBaby() || therizinosaurus.isShaved()) return false;
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

    private static class LoudVibrationListener implements GameEventListener {

        private final Therizinosaurus therizinosaurus;
        private final PositionSource listenerSource;
        private final int listenerRadius;

        public LoudVibrationListener(Therizinosaurus therizinosaurus, PositionSource positionSource, int i) {
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
        public boolean handleGameEvent(@NotNull ServerLevel serverLevel, @NotNull GameEvent gameEvent, GameEvent.Context context, @NotNull Vec3 vec3) {
            BlockPos blockPos = BlockPos.containing(vec3);
            if (Therizinosaurus.isLoudNoise(gameEvent, serverLevel, blockPos)) {
                this.therizinosaurus.setPose(UP2Poses.ALERTED.get());
                return true;
            }
            return false;
        }
    }

    public static boolean isLoudNoise(GameEvent gameEvent, ServerLevel serverLevel, BlockPos blockPos) {
        return gameEvent == GameEvent.EXPLODE || gameEvent == GameEvent.INSTRUMENT_PLAY || gameEvent == GameEvent.JUKEBOX_PLAY || (gameEvent == GameEvent.BLOCK_CHANGE && serverLevel.getBlockState(blockPos).is(Blocks.BELL));
    }

    // Goals
    private static class TherizinosaurusShakeGoal extends AnimationGoal {

        private final Therizinosaurus therizinosaurus;

        public TherizinosaurusShakeGoal(Therizinosaurus therizinosaurus) {
            super(therizinosaurus, 40, 1, (byte) 67, (byte) 68, false);
            this.therizinosaurus = therizinosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && therizinosaurus.shakeCooldown == 0 && !therizinosaurus.isMobSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.therizinosaurus.shakeCooldown();
        }
    }

    private static class TherizinosaurusStretchGoal extends AnimationGoal {

        private final Therizinosaurus therizinosaurus;

        public TherizinosaurusStretchGoal(Therizinosaurus therizinosaurus) {
            super(therizinosaurus, 100, 2, (byte) 69, (byte) 70);
            this.therizinosaurus = therizinosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && therizinosaurus.stretchCooldown == 0 && !therizinosaurus.isMobSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.therizinosaurus.stretchCooldown();
        }
    }

    private static class TherizinosaurusClickGoal extends AnimationGoal {

        private final Therizinosaurus therizinosaurus;

        public TherizinosaurusClickGoal(Therizinosaurus therizinosaurus) {
            super(therizinosaurus, 10, 3, (byte) 71, (byte) 72, false);
            this.therizinosaurus = therizinosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && therizinosaurus.clickCooldown == 0 && !therizinosaurus.isMobSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.therizinosaurus.clickCooldown();
        }
    }
}
