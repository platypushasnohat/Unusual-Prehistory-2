package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.RandomSitGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.TherizinosaurusAttackGoal;
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
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IForgeShearable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class Therizinosaurus extends PrehistoricMob implements IForgeShearable {

    private static final EntityDataAccessor<Boolean> SHAVED = SynchedEntityData.defineId(Therizinosaurus.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(2.2F, 3.98F);

    public int slashCooldown = 0;
    public int slashRushCooldown = 150 + this.getRandom().nextInt(200);
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

    private int attackTicks;
    private int slashRushTicks;
    private int chargeStartTicks;
    private int chargeEndTicks;

    public Therizinosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.1F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new TherizinosaurusAttackGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.THERIZINOSAURUS_FOOD), false));
        this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new RandomSitGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
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
        return dimensions.height * 0.9F;
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

    public boolean isWithinYRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - this.getY()) < 2;
    }

    public void slashRushCooldown() {
        this.slashRushCooldown = 150 + this.getRandom().nextInt(200);
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
        if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) this.setPose(Pose.STANDING);
        if (slashRushTicks == 0 && this.getPose() == UP2Poses.SLASH_RUSH.get()) this.setPose(Pose.STANDING);
        if (chargeStartTicks == 0 && this.getPose() == UP2Poses.START_CHARGING.get()) this.setPose(UP2Poses.CHARGING.get());
        if (chargeEndTicks == 0 && this.getPose() == UP2Poses.STOP_CHARGING.get()) this.setPose(Pose.STANDING);
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

        this.idleAnimationState.animateWhen(!this.isInAttackingPose() && !this.isInWater(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWater() && !this.isInAttackingPose(), this.tickCount);

        if (this.isMobVisuallySitting()) {
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
            this.slashRushAnimationState.stop();
            this.chargeStartAnimationState.stop();
            this.chargeEndAnimationState.stop();
            this.idleAnimationState.stop();

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
                this.slashRushTicks = 40;
            }
            else if (this.getPose() == UP2Poses.START_CHARGING.get()) {
                this.chargeStartAnimationState.start(this.tickCount);
                this.chargeStartTicks = 40;
            }
            else if (this.getPose() == UP2Poses.CHARGING.get()) {
                this.chargeStartAnimationState.stop();
            }
            else if (this.getPose() == UP2Poses.STOP_CHARGING.get()) {
                this.chargeEndAnimationState.start(this.tickCount);
                this.chargeEndTicks = 40;
            }
            else {
                this.attack1AnimationState.stop();
                this.attack2AnimationState.stop();
                this.slashRushAnimationState.stop();
                this.chargeStartAnimationState.stop();
                this.chargeEndAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    public void handleEntityEvent(byte id) {
        switch (id) {
            default -> super.handleEntityEvent(id);
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 3;
    }

    @Override
    public @NotNull List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, Level level, BlockPos pos, int fortune) {
        if (!level.isClientSide && !this.isShaved()) {
            level.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
            this.gameEvent(GameEvent.SHEAR, player);
            this.setShaved(true);
        }
        return Collections.emptyList();
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3, 3, 3);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHAVED, false);
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
}
