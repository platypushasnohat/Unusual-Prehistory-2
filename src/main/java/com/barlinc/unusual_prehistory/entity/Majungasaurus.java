package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.MajungasaurusAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricNearestAttackableTargetGoal;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Majungasaurus extends PrehistoricMob {

    public static final EntityDataAccessor<Integer> STEALTH_COOLDOWN = SynchedEntityData.defineId(Majungasaurus.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState eyesAnimationState = new AnimationState();
    public final AnimationState biteRightAnimationState = new AnimationState();
    public final AnimationState biteLeftAnimationState = new AnimationState();
    public final AnimationState enterStealthAnimationState = new AnimationState();
    public final AnimationState stealthIdleAnimationState = new AnimationState();
    public final AnimationState exitStealthAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();

    private float stealthProgress;
    private float prevStealthProgress;

    private int biteTicks;

    public Majungasaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MajungasaurusAttackGoal(this));
        this.goalSelector.addGoal(2, new LargeBabyPanicGoal(this, 1.7D, 10, 4));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.MAJUNGASAURUS_FOOD), false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new AvoidEntityGoal<>(this, Majungasaurus.class, 12.0F, 2.0D, 2.0D, this::avoidsParents) {
            public boolean canUse(){
                return super.canUse() && Majungasaurus.this.isBaby();
            }
        });
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, Majungasaurus.class, 300, true, true, this::canCannibalize));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 600, true, false, entity -> entity.getType().is(UP2EntityTags.MAJUNGASAURUS_TARGETS)));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 32.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.19F)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                .add(Attributes.ARMOR, 4.0D);
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float amount) {
        this.exitStealthInstantly();
        super.actuallyHurt(damageSource, amount);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.MAJUNGASAURUS_FOOD);
    }

    public float getStealthProgress(float partialTicks) {
        return (prevStealthProgress + (stealthProgress - prevStealthProgress) * partialTicks) * 0.05F;
    }

    public boolean canCannibalize(LivingEntity entity) {
        return this.canAttack(entity) && (entity.getHealth() < entity.getMaxHealth() * 0.5F || entity.isBaby());
    }

    public boolean avoidsParents(LivingEntity entity) {
        Majungasaurus majungasaurus = (Majungasaurus) entity;
        return this.isBaby() && !entity.isBaby() && !majungasaurus.isMajungasaurusStealthMode();
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_MAJUNGASAURUS);
    }

    @Override
    public void tick () {
        super.tick();

        prevStealthProgress = stealthProgress;

        if (this.biteTicks > 0) biteTicks--;
        if (this.biteTicks == 0 && this.getPose() == UP2Poses.BITING.get()) this.setPose(Pose.STANDING);

        if (this.isMajungasaurusStealthMode() && stealthProgress < 20F) stealthProgress++;
        if (!this.isMajungasaurusStealthMode() && stealthProgress > 0F) stealthProgress--;

        if (this.getStealthCooldown() > 0) this.setStealthCooldown(this.getStealthCooldown() - 1);
    }

    @Override
    public void setupAnimationStates() {
        if (biteTicks == 0 && (this.biteRightAnimationState.isStarted() || this.biteLeftAnimationState.isStarted())) {
            this.biteRightAnimationState.stop();
            this.biteLeftAnimationState.stop();
        }

        this.idleAnimationState.animateWhen(!this.isMajungasaurusStealthMode() && !this.isInWater(), this.tickCount);
        this.eyesAnimationState.animateWhen(this.getBehavior().equals(Behaviors.IDLE.getName()), this.tickCount);
        this.swimmingAnimationState.animateWhen(!this.isMajungasaurusStealthMode() && this.isInWater(), this.tickCount);

        if (this.isMajungasaurusVisuallyStealthMode()) {
            this.exitStealthAnimationState.stop();
            this.idleAnimationState.stop();

            if (this.isVisuallyStealthMode()) {
                this.enterStealthAnimationState.startIfStopped(this.tickCount);
                this.stealthIdleAnimationState.stop();
            } else {
                this.enterStealthAnimationState.stop();
                this.stealthIdleAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.stealthIdleAnimationState.stop();
            this.enterStealthAnimationState.stop();
            this.exitStealthAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
        }
    }

    @Override
    public float getWalkAnimationSpeed() {
        return this.isBaby() ? 2.0F : 4.0F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STEALTH_COOLDOWN, 60 + random.nextInt(10));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("StealthCooldown", this.getStealthCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setStealthCooldown(compoundTag.getInt("StealthCooldown"));
    }

    public int getStealthCooldown() {
        return this.entityData.get(STEALTH_COOLDOWN);
    }

    public void setStealthCooldown(int cooldown) {
        this.entityData.set(STEALTH_COOLDOWN, cooldown);
    }

    public void stealthCooldown() {
        this.entityData.set(STEALTH_COOLDOWN, 60 + random.nextInt(10));
    }

    public boolean isMajungasaurusStealthMode() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
    }

    public boolean isMajungasaurusVisuallyStealthMode() {
        return this.getPoseTime() < 0L != this.isMajungasaurusStealthMode();
    }

    @Override
    public boolean isInPoseTransition() {
        long l = this.getPoseTime();
        return l < (long) (20);
    }

    private boolean isVisuallyStealthMode() {
        return this.isMajungasaurusStealthMode() && this.getPoseTime() < 20L && this.getPoseTime() >= 0L;
    }

    public void enterStealth() {
        if (this.isMajungasaurusStealthMode()) return;
        this.setPose(UP2Poses.STEALTH.get());
        this.resetLastPoseChangeTick(-(this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void exitStealth() {
        if (!this.isMajungasaurusStealthMode()) {
            return;
        }
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTick((this.level()).getGameTime());
        this.stealthCooldown();
        this.refreshDimensions();
    }

    public void exitStealthInstantly() {
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTickToFullStand((this.level()).getGameTime());
        this.stealthCooldown();
        this.refreshDimensions();
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == UP2Poses.BITING.get()) {
                if (this.getRandom().nextBoolean()) this.biteRightAnimationState.start(this.tickCount);
                this.biteLeftAnimationState.start(this.tickCount);
                this.biteTicks = 15;
            }
            if (this.getPose() == Pose.STANDING) {
                this.biteRightAnimationState.stop();
                this.biteLeftAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        Majungasaurus majungasaurus = UP2Entities.MAJUNGASAURUS.get().create(level);
        majungasaurus.setVariant(this.getVariant());
        return majungasaurus;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isMajungasaurusStealthMode() ? SoundEvents.EMPTY : UP2SoundEvents.MAJUNGASAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.MAJUNGASAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.MAJUNGASAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
       if (this.isMajungasaurusStealthMode()) {
           this.playSound(SoundEvents.CAMEL_STEP, 0.1F, 0.9F);
       } else {
           this.playSound(SoundEvents.CAMEL_STEP, 1.0F, 0.9F);
       }
    }

    public enum MajungasaurusVariant {
        MAJUNGASAURUS(0),
        DUSKLURKER(1);

        private final int id;

        MajungasaurusVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static MajungasaurusVariant byId(int id) {
            if (id < 0 || id >= MajungasaurusVariant.values().length) {
                id = 0;
            }
            return MajungasaurusVariant.values()[id];
        }
    }

    @Override
    public int getVariantCount() {
        return MajungasaurusVariant.values().length;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        if (level.getRandom().nextFloat() < 0.15F && level.getLevel().isNight()) this.setVariant(1);
        else this.setVariant(0);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
    }

    public static boolean canSpawn(EntityType<Majungasaurus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.MAJUNGASAURUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }
}
