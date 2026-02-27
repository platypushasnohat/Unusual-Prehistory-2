package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2MobEffects;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Carnotaurus extends PrehistoricMob {

    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(Carnotaurus.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDimensions EEPY_DIMENSIONS = EntityDimensions.scalable(1.6F, 1.7F);

    public int chargeCooldown = 200 + this.getRandom().nextInt(200);
    public int roarCooldown = 100;
    public int biteCooldown = 0;
    public int headbuttCooldown = 0;

    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState chargeStartAnimationState = new AnimationState();
    public final AnimationState chargeEndAnimationState = new AnimationState();
    public final AnimationState headbuttAnimationState = new AnimationState();
    public final AnimationState angryAnimationState = new AnimationState();
    public final AnimationState roarAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState attackFast1AnimationState = new AnimationState();
    public final AnimationState attackFast2AnimationState = new AnimationState();
    public final AnimationState headbuttFastAnimationState = new AnimationState();
    public final AnimationState yawnAnimationState = new AnimationState();
    public final AnimationState shakeAnimationState = new AnimationState();
    public final AnimationState sniff1AnimationState = new AnimationState();
    public final AnimationState sniff2AnimationState = new AnimationState();

    public int yawnCooldown = 500 + this.getRandom().nextInt(50 * 50);
    public int shakeCooldown = 600 + this.getRandom().nextInt(50 * 50);
    public int sniffCooldown = 700 + this.getRandom().nextInt(70 * 60);

    private int attackTicks;
    private int headbuttTicks;
    private int attackFastTicks;
    private int headbuttFastTicks;
    private int startChargeTicks;
    private int stopChargeTicks;
    private int roarTicks;

    public Carnotaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.7D, 10, 4));
        this.goalSelector.addGoal(2, new CarnotaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.CARNOTAURUS_FOOD), false));
        this.goalSelector.addGoal(4, new PrehistoricRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new SleepingGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && !Carnotaurus.this.isAngry();
            }
        });
        this.goalSelector.addGoal(7, new CarnotaurusYawnGoal(this));
        this.goalSelector.addGoal(7, new CarnotaurusShakeGoal(this));
        this.goalSelector.addGoal(8, new CarnotaurusSniffGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 100, true, true, this::canAttack));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, 60, true, true, entity -> !entity.getType().is(UP2EntityTags.CARNOTAURUS_IGNORES)));
        this.targetSelector.addGoal(3, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.CARNOTAURUS_TARGETS)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.ATTACK_DAMAGE, 9.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
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

    public void roar() {
        if (this.isAlive()) {
            this.level().broadcastEntityEvent(this, (byte) 39);
            this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
            this.addEffect(new MobEffectInstance(UP2MobEffects.FURY.get(), 300, 0));
            this.gameEvent(GameEvent.ENTITY_ROAR);
        }
    }

    public boolean isFurious() {
        return this.hasEffect(UP2MobEffects.FURY.get());
    }

    @Override
    public boolean killedEntity(@NotNull ServerLevel level, @NotNull LivingEntity victim) {
        this.heal(6);
        return super.killedEntity(level, victim);
    }

    public boolean isWithinYRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - this.getY()) < 2;
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_CARNOTAURUS);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.CARNOTAURUS_FOOD);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return pose == UP2Poses.EEPY.get() ? EEPY_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 3;
    }

    public void chargeCooldown() {
        this.chargeCooldown = 200 + this.getRandom().nextInt(200);
    }

    public void roarCooldown() {
        this.roarCooldown = 600 + this.getRandom().nextInt(400);
    }

    @Override
    public void tick() {
        super.tick();
        if (chargeCooldown > 0) chargeCooldown--;
        if (roarCooldown > 0) roarCooldown--;
        if (headbuttCooldown > 0) headbuttCooldown--;
        if (biteCooldown > 0) biteCooldown--;
        this.setAngry(this.getHealth() < this.getMaxHealth() * 0.5F && !this.isBaby());
    }

    public void setupAnimationStates() {
        if (attackTicks == 0 && (this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted())) {
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
        }
        if (attackFastTicks == 0 && (this.attackFast1AnimationState.isStarted() || this.attackFast2AnimationState.isStarted())) {
            this.attackFast1AnimationState.stop();
            this.attackFast2AnimationState.stop();
        }
        if (headbuttTicks == 0 && this.headbuttAnimationState.isStarted()) this.headbuttAnimationState.stop();
        if (headbuttFastTicks == 0 && this.headbuttFastAnimationState.isStarted()) this.headbuttFastAnimationState.stop();
        if (startChargeTicks == 0 && this.chargeStartAnimationState.isStarted()) this.chargeStartAnimationState.stop();
        if (stopChargeTicks == 0 && this.chargeEndAnimationState.isStarted()) this.chargeEndAnimationState.stop();
        if (roarTicks == 0 && this.roarAnimationState.isStarted()) this.roarAnimationState.stop();
        this.angryAnimationState.animateWhen(this.isAngry(), this.tickCount);
        this.idleAnimationState.animateWhen(this.getPose() != Pose.ROARING && !this.isInWater() && !this.isInSitPoseTransition() && !this.isInEepyPoseTransition(), this.tickCount);
        this.swimAnimationState.animateWhen(this.getPose() != Pose.ROARING && this.isInWater(), this.tickCount);

        if (this.isMobVisuallyEepy()) {
            this.eepyEndAnimationState.stop();
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
            this.attackFast1AnimationState.stop();
            this.attackFast2AnimationState.stop();
            this.headbuttAnimationState.stop();
            this.headbuttFastAnimationState.stop();
            this.chargeStartAnimationState.stop();
            this.chargeEndAnimationState.stop();
            this.idleAnimationState.stop();
            this.shakeAnimationState.stop();
            this.sniff1AnimationState.stop();
            this.sniff2AnimationState.stop();
            this.roarAnimationState.stop();

            if (this.isVisuallyEepy()) {
                this.eepyStartAnimationState.startIfStopped(this.tickCount);
                this.eepyAnimationState.stop();
            } else {
                this.eepyStartAnimationState.stop();
                this.eepyAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.eepyStartAnimationState.stop();
            this.eepyAnimationState.stop();
            this.eepyEndAnimationState.animateWhen(this.isInEepyPoseTransition() && this.getEepyPoseTime() >= 0L, this.tickCount);
        }
    }

    @Override
    public void setupAnimationCooldowns() {
        if (!this.isMobEepy()) {
            if (yawnCooldown > 0) yawnCooldown--;
            if (shakeCooldown > 0) shakeCooldown--;
            if (sniffCooldown > 0) sniffCooldown--;
        }

        if (this.attackTicks > 0) attackTicks--;
        if (this.headbuttTicks > 0) headbuttTicks--;
        if (this.attackFastTicks > 0) attackFastTicks--;
        if (this.headbuttFastTicks > 0) headbuttFastTicks--;
        if (this.startChargeTicks > 0) startChargeTicks--;
        if (this.stopChargeTicks > 0) stopChargeTicks--;
        if (this.roarTicks > 0) roarTicks--;
        if (this.attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) this.setPose(Pose.STANDING);
        if (this.attackFastTicks == 0 && this.getPose() == UP2Poses.ATTACKING_FAST.get()) this.setPose(Pose.STANDING);
        if (this.headbuttTicks == 0 && this.getPose() == UP2Poses.HEADBUTTING.get()) this.setPose(Pose.STANDING);
        if (this.headbuttFastTicks == 0 && this.getPose() == UP2Poses.HEADBUTTING_FAST.get()) this.setPose(Pose.STANDING);
        if (this.startChargeTicks == 0 && this.getPose() == UP2Poses.START_CHARGING.get()) this.setPose(UP2Poses.CHARGING.get());
        if (this.stopChargeTicks == 0 && this.getPose() == UP2Poses.STOP_CHARGING.get()) this.setPose(Pose.STANDING);
        if (this.roarTicks == 0 && this.getPose() == Pose.ROARING) this.setPose(Pose.STANDING);
    }

    @Override
    public float getWalkAnimationSpeed() {
        return this.isBaby() ? 2.0F : 4.0F;
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                if (this.getRandom().nextBoolean()) this.attack1AnimationState.start(this.tickCount);
                else this.attack2AnimationState.start(this.tickCount);
                this.attackTicks = 15;
            }
            else if (this.getPose() == UP2Poses.HEADBUTTING.get()) {
                this.headbuttAnimationState.start(this.tickCount);
                this.headbuttTicks = 20;
            }
            else if (this.getPose() == UP2Poses.ATTACKING_FAST.get()) {
                if (this.getRandom().nextBoolean()) this.attackFast1AnimationState.start(this.tickCount);
                else this.attackFast2AnimationState.start(this.tickCount);
                this.attackFastTicks = 12;
            }
            else if (this.getPose() == UP2Poses.HEADBUTTING_FAST.get()) {
                this.headbuttFastAnimationState.start(this.tickCount);
                this.headbuttFastTicks = 16;
            }
            else if (this.getPose() == UP2Poses.START_CHARGING.get()) {
                this.startChargeTicks = 30;
                this.chargeStartAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == UP2Poses.CHARGING.get()) {
                this.chargeStartAnimationState.stop();
            }
            else if (this.getPose() == UP2Poses.STOP_CHARGING.get()) {
                this.stopChargeTicks = 15;
                this.chargeEndAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.ROARING) {
                this.roarTicks = 40;
                this.roarAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.STANDING) {
                this.attack1AnimationState.stop();
                this.attack2AnimationState.stop();
                this.attackFast1AnimationState.stop();
                this.attackFast2AnimationState.stop();
                this.headbuttAnimationState.stop();
                this.headbuttFastAnimationState.stop();
                this.roarAnimationState.stop();
                this.chargeStartAnimationState.stop();
                this.chargeEndAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    public void handleEntityEvent(byte id) {
        switch (id) {
            case 39 -> this.roarEffect();

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

    private void roarEffect() {
        for(int i = 0; i < 10; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
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
        this.entityData.define(ANGRY, false);
    }

    public boolean isAngry() {
        return this.entityData.get(ANGRY);
    }
    public void setAngry(boolean angry) {
        this.entityData.set(ANGRY, angry);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        Carnotaurus carnotaurus = UP2Entities.CARNOTAURUS.get().create(level);
        carnotaurus.setVariant(this.getVariant());
        return carnotaurus;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.CARNOTAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.CARNOTAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.CARNOTAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.CARNOTAURUS_STEP.get(), this.isBaby() ? 0.25F : 1.0F, this.isBaby() ? 1.2F : 0.9F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }

    public enum CarnotaurusVariant {
        CARNOTAURUS(0),
        GOLDEN_EMPEROR(1);

        private final int id;

        CarnotaurusVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static CarnotaurusVariant byId(int id) {
            if (id < 0 || id >= CarnotaurusVariant.values().length) {
                id = 0;
            }
            return CarnotaurusVariant.values()[id];
        }
    }

    @Override
    public int getVariantCount() {
        return CarnotaurusVariant.values().length;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        if (level.getRandom().nextFloat() < 0.2F) this.setVariant(1);
        else this.setVariant(0);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
    }

    public static boolean canSpawn(EntityType<Carnotaurus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.CARNOTAURUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    // Goals
    private static class CarnotaurusYawnGoal extends AnimationGoal {

        private final Carnotaurus carnotaurus;

        public CarnotaurusYawnGoal(Carnotaurus carnotaurus) {
            super(carnotaurus, 60, 1, (byte) 67, (byte) 68, false);
            this.carnotaurus = carnotaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && carnotaurus.yawnCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.carnotaurus.yawnCooldown();
        }
    }

    private static class CarnotaurusShakeGoal extends AnimationGoal {

        private final Carnotaurus carnotaurus;

        public CarnotaurusShakeGoal(Carnotaurus carnotaurus) {
            super(carnotaurus, 40, 2, (byte) 69, (byte) 70, false);
            this.carnotaurus = carnotaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && carnotaurus.shakeCooldown == 0 && !carnotaurus.isMobSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.carnotaurus.shakeCooldown();
        }
    }

    private static class CarnotaurusSniffGoal extends AnimationGoal {

        private final Carnotaurus carnotaurus;

        public CarnotaurusSniffGoal(Carnotaurus carnotaurus) {
            super(carnotaurus, 80, 3, (byte) 71, (byte) 72);
            this.carnotaurus = carnotaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && carnotaurus.sniffCooldown == 0 && !carnotaurus.isMobSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.carnotaurus.sniffCooldown();
        }

        @Override
        public void tick() {
            super.tick();
            if (timer == 50) carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_SNIFF.get(), 1.0F, carnotaurus.getVoicePitch());
        }
    }
}
