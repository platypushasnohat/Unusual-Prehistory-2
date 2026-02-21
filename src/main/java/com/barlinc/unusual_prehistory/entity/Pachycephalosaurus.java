package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.goals.PachycephalosaurusAttackGoal;
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

    private static final EntityDataAccessor<Integer> CHARGE_COOLDOWN = SynchedEntityData.defineId(Pachycephalosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FIGHT_COOLDOWN = SynchedEntityData.defineId(Pachycephalosaurus.class, EntityDataSerializers.INT);

    private int grazeCooldown = 700 + this.getRandom().nextInt(60 * 50);
    private int huffCooldown = 600 + this.getRandom().nextInt(60 * 60);
    private int stompCooldown = 500 + this.getRandom().nextInt(60 * 70);

    public final AnimationState huffAnimationState = new AnimationState();
    public final AnimationState stomp1AnimationState = new AnimationState();
    public final AnimationState stomp2AnimationState = new AnimationState();
    public final AnimationState grazeAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState warnAnimationState = new AnimationState();

    private int warnTicks;

    public Pachycephalosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.7D, 10, 4));
        this.goalSelector.addGoal(3, new PachycephalosaurusAttackGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.PACHYCEPHALOSAURUS_FOOD), false));
        this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new SleepingGoal(this));
        this.goalSelector.addGoal(9, new PachycephalosaurusGrazeGoal(this));
        this.goalSelector.addGoal(9, new PachycephalosaurusHuffGoal(this));
        this.goalSelector.addGoal(9, new PachycephalosaurusStompGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this, Pachycephalosaurus.class));
        this.targetSelector.addGoal(1, new PachycephalosaurusTargetOthersGoal<>(this, Pachycephalosaurus.class));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ARMOR, 4.0F);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.9F;
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
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.PACHYCEPHALOSAURUS_FOOD);
    }

    @Override
    public Vec3 getLookVec() {
        return new Vec3(0, 0.15F, -this.getBbWidth() * 0.8F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
    }

    public boolean canChargeAtOtherPachycephalosaurus(LivingEntity entity) {
        if (entity instanceof Mob mob && mob.getTarget() != null) {
            return false;
        }
        return this.canAttack(entity) && !this.isInWaterOrBubble() && this.getFightCooldown() <= 0 && entity.getLastHurtByMob() == null && !entity.isInWaterOrBubble();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getChargeCooldown() > 0) this.setChargeCooldown(this.getChargeCooldown() - 1);
        if (this.getFightCooldown() > 0 && !this.isMobEepy() && !this.isInWaterOrBubble()) this.setFightCooldown(this.getFightCooldown() - 1);
    }

    @Override
    public void setupAnimationCooldowns() {
        if (warnTicks > 0) warnTicks--;
        if (warnTicks == 0 && this.getPose() == UP2Poses.WARNING.get()) this.setPose(Pose.STANDING);
        if (huffCooldown > 0) huffCooldown--;
        if (stompCooldown > 0) stompCooldown--;
        if (grazeCooldown > 0) grazeCooldown--;
    }

    @Override
    public void setupAnimationStates() {
        if (warnTicks == 0 && this.warnAnimationState.isStarted()) this.warnAnimationState.stop();
        this.idleAnimationState.animateWhen(this.getAttackState() != 1 && !this.isInWater() && !this.isInEepyPoseTransition(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWater() && this.getAttackState() != 1, this.tickCount);

        if (this.isMobVisuallyEepy()) {
            this.idleAnimationState.stop();
            this.huffAnimationState.stop();
            this.stomp1AnimationState.stop();
            this.stomp2AnimationState.stop();
            this.grazeAnimationState.stop();
            this.warnAnimationState.stop();

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
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.WARNING.get()) {
                this.warnAnimationState.start(this.tickCount);
                this.warnTicks = 50;
            }
            else if (this.getPose() == Pose.STANDING) {
                this.warnAnimationState.stop();
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
        this.entityData.define(CHARGE_COOLDOWN, 0);
        this.entityData.define(FIGHT_COOLDOWN, 1000);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("FightCooldown", this.getFightCooldown());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setFightCooldown(compoundTag.getInt("FightCooldown"));
    }

    public void setChargeCooldown(int cooldown) {
        this.entityData.set(CHARGE_COOLDOWN, cooldown);
    }

    public int getChargeCooldown() {
        return this.entityData.get(CHARGE_COOLDOWN);
    }

    public void setFightCooldown(int cooldown) {
        this.entityData.set(FIGHT_COOLDOWN, cooldown);
    }

    public int getFightCooldown() {
        return this.entityData.get(FIGHT_COOLDOWN);
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
    private static class PachycephalosaurusTargetOthersGoal<T extends LivingEntity> extends PrehistoricNearestAttackableTargetGoal<T> {

        public PachycephalosaurusTargetOthersGoal(Pachycephalosaurus pachycephalosaurus, Class<T> targetClass) {
            super(pachycephalosaurus, targetClass, 200, true, true, pachycephalosaurus::canChargeAtOtherPachycephalosaurus);
        }

        @Override
        public void start() {
            this.mob.setTarget(target);
            if (target instanceof Pachycephalosaurus pachycephalosaurus && pachycephalosaurus.getTarget() == null && pachycephalosaurus.getLastHurtByMob() == null && !pachycephalosaurus.isInWaterOrBubble()) {
                pachycephalosaurus.setTarget(mob);
            }
            super.start();
        }
    }

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
