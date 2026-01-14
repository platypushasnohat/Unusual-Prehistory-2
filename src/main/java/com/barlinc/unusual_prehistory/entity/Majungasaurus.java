package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
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
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
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

import java.util.function.Predicate;

public class Majungasaurus extends PrehistoricMob {

    public static final EntityDataAccessor<Boolean> CAMO = SynchedEntityData.defineId(Majungasaurus.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> CAMO_AVOIDING = SynchedEntityData.defineId(Majungasaurus.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> CAMO_COOLDOWN = SynchedEntityData.defineId(Majungasaurus.class, EntityDataSerializers.INT);

    private static final EntityDimensions EEPY_DIMENSIONS = EntityDimensions.scalable(1.2F, 1.1F);

    public float prevCamoProgress;
    public float camoProgress;

    public float prevAngryProgress;
    public float angryProgress;

    public final AnimationState eyesAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState startCamoAnimationState = new AnimationState();
    public final AnimationState camoIdleAnimationState = new AnimationState();
    public final AnimationState stopCamoAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState yawnAnimationState = new AnimationState();
    public final AnimationState sniff1AnimationState = new AnimationState();
    public final AnimationState sniff2AnimationState = new AnimationState();
    public final AnimationState shakeAnimationState = new AnimationState();

    private int attackTicks;
    private int startCamoTicks;
    private int stopCamoTicks;

    private int yawnCooldown = 500 + this.getRandom().nextInt(50 * 50);
    private int shakeCooldown = 600 + this.getRandom().nextInt(60 * 60);
    private int sniffCooldown = 700 + this.getRandom().nextInt(70 * 60);

    public Majungasaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.7D, 10, 4));
        this.goalSelector.addGoal(2, new MajungasaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.MAJUNGASAURUS_FOOD), false));
        this.goalSelector.addGoal(4, new PrehistoricRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new MajungasaurusAvoidEntityGoal<>(this, Majungasaurus.class, this::avoidsParents) {
            public boolean canUse(){
                return super.canUse() && Majungasaurus.this.isBaby();
            }
        });
        this.goalSelector.addGoal(6, new MajungasaurusAvoidEntityGoal<>(this, LivingEntity.class, entity -> entity.getType().is(UP2EntityTags.MAJUNGASAURUS_AVOIDS)));
        this.goalSelector.addGoal(7, new NocturnalSleepGoal(this));
        this.goalSelector.addGoal(8, new MajungasaurusYawnGoal(this));
        this.goalSelector.addGoal(8, new MajungasaurusShakeGoal(this));
        this.goalSelector.addGoal(9, new MajungasaurusSniffGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, Majungasaurus.class, 200, true, true, this::canCannibalize));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.MAJUNGASAURUS_TARGETS)));
        this.targetSelector.addGoal(3, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 300, true, true, this::attacksPlayers));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
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
        this.stopCamoInstantly();
        super.actuallyHurt(damageSource, amount);
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        if (this.getPose() == UP2Poses.STOP_CAMO.get() || this.getPose() == UP2Poses.START_CAMO.get()) {
            travelVec = travelVec.multiply(0.25, 1.0, 0.25);
        }
        super.travel(travelVec);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.MAJUNGASAURUS_FOOD);
    }

    public float getCamoProgress(float partialTicks) {
        return (prevCamoProgress + (camoProgress - prevCamoProgress) * partialTicks) * 0.1F;
    }

    public float getAngryProgress(float partialTicks) {
        return (prevAngryProgress + (angryProgress - prevAngryProgress) * partialTicks) * 0.1F;
    }

    public boolean canCannibalize(LivingEntity entity) {
        return this.canAttack(entity) && (entity.getHealth() < entity.getMaxHealth() * 0.5F || entity.isBaby());
    }

    public boolean avoidsParents(LivingEntity entity) {
        Majungasaurus majungasaurus = (Majungasaurus) entity;
        return this.isBaby() && !entity.isBaby() && !majungasaurus.isCamo();
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_MAJUNGASAURUS);
    }

    public boolean isNightTime() {
        return this.level().getDayTime() < 23000 && this.level().getDayTime() > 12000 && !this.level().dimensionType().hasFixedTime();
    }

    protected boolean attacksPlayers(LivingEntity entity) {
        return this.canAttack(entity) && this.isNightTime();
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return this.getPose() == UP2Poses.SLEEPING.get() ? EEPY_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 3;
    }

    @Override
    public void tick () {
        super.tick();

        this.prevCamoProgress = camoProgress;
        this.prevAngryProgress = angryProgress;

        if (this.attackTicks > 0) attackTicks--;
        if (this.attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) this.setPose(Pose.STANDING);

        if ((this.isCamo() || this.isCamoAvoiding()) && camoProgress < 10.0F) camoProgress++;
        else if (!(this.isCamo() || this.isCamoAvoiding()) && camoProgress > 0.0F) camoProgress--;

        if (this.isAggressive() && angryProgress < 10.0F) angryProgress++;
        else if (!this.isAggressive() && angryProgress > 0.0F) angryProgress--;

        if (this.getCamoCooldown() > 0) this.setCamoCooldown(this.getCamoCooldown() - 1);
    }

    @Override
    public void setupAnimationCooldowns() {
        if (startCamoTicks > 0) startCamoTicks--;
        if (stopCamoTicks > 0) stopCamoTicks--;
        if (startCamoTicks == 0 && this.getPose() == UP2Poses.START_CAMO.get()) this.setPose(Pose.STANDING);
        if (stopCamoTicks == 0 && this.getPose() == UP2Poses.STOP_CAMO.get()) this.setPose(Pose.STANDING);
        if (!this.isMobEepy()) {
            if (yawnCooldown > 0) yawnCooldown--;
            if (shakeCooldown > 0) shakeCooldown--;
            if (sniffCooldown > 0) sniffCooldown--;
        }
    }

    @Override
    public void setupAnimationStates() {
        if (attackTicks == 0 && (this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted())) {
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
        }
        if (startCamoTicks == 0 && this.startCamoAnimationState.isStarted()) this.startCamoAnimationState.stop();
        if (stopCamoTicks == 0 && this.stopCamoAnimationState.isStarted()) this.stopCamoAnimationState.stop();
        this.idleAnimationState.animateWhen(!this.isCamo() && !this.isInWater(), this.tickCount);
        this.camoIdleAnimationState.animateWhen(this.isCamo() && !this.isInWater() && this.getPose() != UP2Poses.START_CAMO.get() && this.getPose() != UP2Poses.STOP_CAMO.get(), this.tickCount);
        this.eyesAnimationState.animateWhen(!this.isAggressive(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWater(), this.tickCount);

        if (this.isMobVisuallyEepy()) {
            this.sleepEndAnimationState.stop();
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
            this.eyesAnimationState.stop();
            this.idleAnimationState.stop();
            this.startCamoAnimationState.stop();
            this.stopCamoAnimationState.stop();
            this.camoIdleAnimationState.stop();

            if (this.isVisuallyEepy()) {
                this.sleepStartAnimationState.startIfStopped(this.tickCount);
                this.sleepAnimationState.stop();
            } else {
                this.sleepStartAnimationState.stop();
                this.sleepAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.sleepStartAnimationState.stop();
            this.sleepAnimationState.stop();
            this.sleepEndAnimationState.animateWhen(this.isInEepyPoseTransition() && this.getEepyPoseTime() >= 0L, this.tickCount);
        }
    }

    @Override
    public float getWalkAnimationSpeed() {
        return this.isBaby() ? 2.0F : 4.0F;
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.attackTicks = 15;
                if (this.getRandom().nextBoolean()) this.attack1AnimationState.start(this.tickCount);
                else this.attack2AnimationState.start(this.tickCount);
            }
            else if (this.getPose() == UP2Poses.START_CAMO.get()) {
                this.startCamoTicks = 20;
                this.startCamoAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == UP2Poses.STOP_CAMO.get()) {
                this.stopCamoTicks = 20;
                this.stopCamoAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.STANDING) {
                this.attack1AnimationState.stop();
                this.attack2AnimationState.stop();
                this.startCamoAnimationState.stop();
                this.stopCamoAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    public void handleEntityEvent(byte id) {
        switch (id) {
            case 67 -> this.yawnAnimationState.start(this.tickCount);
            case 68 -> this.yawnAnimationState.stop();

            case 69 -> this.shakeAnimationState.start(this.tickCount);
            case 70 -> this.shakeAnimationState.stop();

            case 71 -> {
                if (this.getRandom().nextBoolean()) this.sniff1AnimationState.start(this.tickCount);
                else this.sniff2AnimationState.start(this.tickCount);
            }
            case 72 -> {
                this.sniff1AnimationState.stop();
                this.sniff2AnimationState.stop();
            }
            default -> super.handleEntityEvent(id);
        }
    }

    protected void yawnCooldown() {
        this.yawnCooldown = 500 + this.getRandom().nextInt(50 * 50);
    }

    protected void shakeCooldown() {
        this.shakeCooldown = 600 + this.getRandom().nextInt(60 * 60);
    }

    protected void sniffCooldown() {
        this.sniffCooldown = 700 + this.getRandom().nextInt(70 * 60);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CAMO, false);
        this.entityData.define(CAMO_AVOIDING, false);
        this.entityData.define(CAMO_COOLDOWN, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("CamoCooldown", this.getCamoCooldown());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setCamoCooldown(compoundTag.getInt("CamoCooldown"));
    }

    public boolean isCamo() {
        return this.entityData.get(CAMO);
    }
    public void setCamo(boolean camo) {
        this.entityData.set(CAMO, camo);
    }

    public boolean isCamoAvoiding() {
        return this.entityData.get(CAMO_AVOIDING);
    }
    public void setCamoAvoiding(boolean camo) {
        this.entityData.set(CAMO_AVOIDING, camo);
    }

    public int getCamoCooldown() {
        return this.entityData.get(CAMO_COOLDOWN);
    }
    public void setCamoCooldown(int cooldown) {
        this.entityData.set(CAMO_COOLDOWN, cooldown);
    }
    public void camoCooldown() {
        this.setCamoCooldown(30 + this.getRandom().nextInt(10));
    }

    public void startCamo() {
        if (this.isCamo()) return;
        this.setPose(UP2Poses.START_CAMO.get());
        this.setCamo(true);
    }

    public void stopCamo() {
        if (!this.isCamo()) return;
        this.setPose(UP2Poses.STOP_CAMO.get());
        this.setCamo(false);
        this.camoCooldown();
    }

    public void stopCamoInstantly() {
        this.setCamo(false);
        this.camoCooldown();
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
        return this.isCamo() || this.isCamoAvoiding() || this.isMobEepy() ? SoundEvents.EMPTY : UP2SoundEvents.MAJUNGASAURUS_IDLE.get();
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
       if (this.isCamo()) {
           this.playSound(SoundEvents.EMPTY);
       } else {
           this.playSound(SoundEvents.CAMEL_STEP, this.isCamoAvoiding() ? 0.5F : 1.0F, 0.9F);
       }
    }

    @Override
    protected Entity.@NotNull MovementEmission getMovementEmission() {
        return this.isCamo() || this.isCamoAvoiding() ? Entity.MovementEmission.NONE : super.getMovementEmission();
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

    // Goals
    private static class MajungasaurusAvoidEntityGoal<T extends LivingEntity> extends PrehistoricAvoidEntityGoal<T> {

        private final Majungasaurus majungasaurus;

        public MajungasaurusAvoidEntityGoal(Majungasaurus mob, Class<T> classToAvoid, Predicate<LivingEntity> predicateOnAvoid) {
            super(mob, classToAvoid, 12.0F, 1.5D, predicateOnAvoid, false);
            this.majungasaurus = mob;
        }

        @Override
        public void start() {
            super.start();
            if (majungasaurus.getCamoCooldown() == 0) this.majungasaurus.setCamoAvoiding(true);
        }

        @Override
        public void stop() {
            super.stop();
            this.majungasaurus.setCamoAvoiding(false);
            this.majungasaurus.camoCooldown();
        }
    }

    private static class MajungasaurusYawnGoal extends AnimationGoal {

        private final Majungasaurus majungasaurus;

        public MajungasaurusYawnGoal(Majungasaurus majungasaurus) {
            super(majungasaurus, 60, 1, (byte) 67, (byte) 68, false);
            this.majungasaurus = majungasaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && majungasaurus.yawnCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.majungasaurus.yawnCooldown();
        }
    }

    private static class MajungasaurusShakeGoal extends AnimationGoal {

        private final Majungasaurus majungasaurus;

        public MajungasaurusShakeGoal(Majungasaurus majungasaurus) {
            super(majungasaurus, 80, 2, (byte) 69, (byte) 70, false);
            this.majungasaurus = majungasaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && majungasaurus.shakeCooldown == 0 && !majungasaurus.isMobSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.majungasaurus.shakeCooldown();
        }
    }

    private static class MajungasaurusSniffGoal extends AnimationGoal {

        private final Majungasaurus majungasaurus;

        public MajungasaurusSniffGoal(Majungasaurus majungasaurus) {
            super(majungasaurus, 80, 3, (byte) 71, (byte) 72);
            this.majungasaurus = majungasaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && majungasaurus.sniffCooldown == 0 && !majungasaurus.isMobSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.majungasaurus.sniffCooldown();
        }

        @Override
        public void tick() {
            super.tick();
            if (timer == 50) majungasaurus.playSound(UP2SoundEvents.MAJUNGASAURUS_SNIFF.get(), 1.0F, majungasaurus.getVoicePitch());
        }
    }
}
