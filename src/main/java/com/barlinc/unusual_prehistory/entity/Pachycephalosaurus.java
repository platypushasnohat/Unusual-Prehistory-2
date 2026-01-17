package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.AnimationGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.RandomSitGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.pachycephalosaurus.PachycephalosaurusAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.pachycephalosaurus.PachycephalosaurusTargetNearbyPlayersGoal;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Pachycephalosaurus extends PrehistoricMob {

    private static final EntityDataAccessor<Boolean> CAN_CHARGE = SynchedEntityData.defineId(Pachycephalosaurus.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(0.8F, 0.7F);

    public int chargeCooldown = 150 + this.getRandom().nextInt(150);

    private int grazeCooldown = 700 + this.getRandom().nextInt(60 * 50);
    private int huffCooldown = 600 + this.getRandom().nextInt(60 * 60);
    private int stompCooldown = 500 + this.getRandom().nextInt(60 * 70);

    public final AnimationState huffAnimationState = new AnimationState();
    public final AnimationState stomp1AnimationState = new AnimationState();
    public final AnimationState stomp2AnimationState = new AnimationState();
    public final AnimationState grazeAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState warnAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState attack3AnimationState = new AnimationState();

    private int attackTicks;
    private int warnTicks;

    public Pachycephalosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(3, new PachycephalosaurusAttackGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.PACHYCEPHALOSAURUS_FOOD), false));
        this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new RandomSitGoal(this));
        this.goalSelector.addGoal(9, new PachycephalosaurusGrazeGoal(this));
        this.goalSelector.addGoal(9, new PachycephalosaurusHuffGoal(this));
        this.goalSelector.addGoal(9, new PachycephalosaurusStompGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PachycephalosaurusTargetNearbyPlayersGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ARMOR, 8.0F);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.9F;
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
    public float getStepHeight() {
        return this.isRunning() ? 1.0F : 0.6F;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return (pose == UP2Poses.SITTING.get() ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose));
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.PACHYCEPHALOSAURUS_FOOD);
    }

    @Override
    public void tick() {
        super.tick();
        if (chargeCooldown > 0) chargeCooldown--;
    }

    @Override
    public void setupAnimationCooldowns() {
        if (attackTicks > 0) attackTicks--;
        if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) this.setPose(Pose.STANDING);
        if (warnTicks > 0) warnTicks--;
        if (warnTicks == 0 && this.getPose() == UP2Poses.WARNING.get()) this.setPose(Pose.STANDING);
        if (huffCooldown > 0) huffCooldown--;
        if (stompCooldown > 0) stompCooldown--;
        if (grazeCooldown > 0) grazeCooldown--;
    }

    @Override
    public void setupAnimationStates() {
        if (attackTicks == 0 && (this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted() || this.attack3AnimationState.isStarted())) {
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
            this.attack3AnimationState.stop();
        }
        if (warnTicks == 0 && this.warnAnimationState.isStarted()) this.warnAnimationState.stop();
        this.idleAnimationState.animateWhen(this.getPose() != UP2Poses.ATTACKING.get() && !this.isInWater(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWater(), this.tickCount);

        if (this.isMobVisuallySitting()) {
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
            this.attack3AnimationState.stop();
            this.idleAnimationState.stop();
            this.huffAnimationState.stop();
            this.stomp1AnimationState.stop();
            this.stomp2AnimationState.stop();
            this.grazeAnimationState.stop();
            this.warnAnimationState.stop();

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
            this.sitEndAnimationState.animateWhen(this.isInSitPoseTransition() && this.getSitPoseTime() >= 0L, this.tickCount);
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                if (this.getRandom().nextFloat() < 0.33F) this.attack1AnimationState.start(this.tickCount);
                else if (this.getRandom().nextFloat() < 0.66F) this.attack2AnimationState.start(this.tickCount);
                else if (this.getRandom().nextFloat() < 1.0F) this.attack3AnimationState.start(this.tickCount);
                this.attackTicks = 20;
            }
            else if (this.getPose() == UP2Poses.WARNING.get()) {
                this.warnAnimationState.start(this.tickCount);
                this.warnTicks = 50;
            }
            else {
                this.attack1AnimationState.stop();
                this.attack2AnimationState.stop();
                this.attack3AnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    public void handleEntityEvent(byte id) {
        switch (id) {
            case 67 -> this.grazeAnimationState.start(this.tickCount);
            case 68 -> this.grazeAnimationState.stop();
            case 69 -> this.huffAnimationState.start(this.tickCount);
            case 70 -> this.huffAnimationState.stop();
            case 71 -> {
                if (this.getRandom().nextBoolean()) this.stomp1AnimationState.start(this.tickCount);
                else this.stomp2AnimationState.start(this.tickCount);
            }
            case 72 -> {
                this.stomp1AnimationState.stop();
                this.stomp2AnimationState.stop();
            }
            default -> super.handleEntityEvent(id);
        }
    }

    protected void grazeCooldown() {
        this.grazeCooldown = 700 + this.getRandom().nextInt(60 * 50);
    }

    protected void huffCooldown() {
        this.huffCooldown = 600 + this.getRandom().nextInt(60 * 60);
    }

    protected void stompCooldown() {
        this.stompCooldown = 500 + this.getRandom().nextInt(60 * 70);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 3;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CAN_CHARGE, true);
    }

    public void setCanCharge(boolean canCharge) {
        this.entityData.set(CAN_CHARGE, canCharge);
    }

    public boolean canCharge() {
        return this.entityData.get(CAN_CHARGE);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        Pachycephalosaurus pachycephalosaurus = UP2Entities.PACHYCEPHALOSAURUS.get().create(level);
        pachycephalosaurus.setVariant(this.getVariant());
        return pachycephalosaurus;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.PACHYCEPHALOSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.PACHYCEPHALOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.PACHYCEPHALOSAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.PACHYCEPHALOSAURUS_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    public enum PachycephalosaurusVariant {
        LAVENDER(0),
        MAROON(1);

        private final int id;

        PachycephalosaurusVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static PachycephalosaurusVariant byId(int id) {
            if (id < 0 || id >= PachycephalosaurusVariant.values().length) {
                id = 0;
            }
            return PachycephalosaurusVariant.values()[id];
        }
    }

    @Override
    public int getVariantCount() {
        return PachycephalosaurusVariant.values().length;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        this.setVariant(level.getRandom().nextInt(this.getVariantCount()));
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
    }

    public static boolean canSpawn(EntityType<Pachycephalosaurus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.PACHYCEPHALOSAURUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    // Goals
    private static class PachycephalosaurusGrazeGoal extends AnimationGoal {

        private final Pachycephalosaurus pachycephalosaurus;

        public PachycephalosaurusGrazeGoal(Pachycephalosaurus pachycephalosaurus) {
            super(pachycephalosaurus, 60, 1, (byte) 67, (byte) 68);
            this.pachycephalosaurus = pachycephalosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && pachycephalosaurus.grazeCooldown == 0 && !pachycephalosaurus.isMobSitting() && pachycephalosaurus.level().getBlockState(pachycephalosaurus.blockPosition().below()).is(UP2BlockTags.PACHYCEPHALOSAURUS_GRAZING_BLOCKS);
        }

        @Override
        public void stop() {
            super.stop();
            this.pachycephalosaurus.grazeCooldown();
        }
    }

    private static class PachycephalosaurusHuffGoal extends AnimationGoal {

        private final Pachycephalosaurus pachycephalosaurus;

        public PachycephalosaurusHuffGoal(Pachycephalosaurus pachycephalosaurus) {
            super(pachycephalosaurus, 30, 2, (byte) 69, (byte) 70, false);
            this.pachycephalosaurus = pachycephalosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && pachycephalosaurus.huffCooldown == 0 && !pachycephalosaurus.isMobSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.pachycephalosaurus.huffCooldown();
        }
    }

    private static class PachycephalosaurusStompGoal extends AnimationGoal {

        private final Pachycephalosaurus pachycephalosaurus;

        public PachycephalosaurusStompGoal(Pachycephalosaurus pachycephalosaurus) {
            super(pachycephalosaurus, 10, 3, (byte) 71, (byte) 72);
            this.pachycephalosaurus = pachycephalosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && pachycephalosaurus.stompCooldown == 0 && !pachycephalosaurus.isMobSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.pachycephalosaurus.stompCooldown();
        }
    }
}
