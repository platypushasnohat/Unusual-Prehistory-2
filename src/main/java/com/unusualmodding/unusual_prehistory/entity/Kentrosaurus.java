package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.enums.KentrosaurusBehaviors;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.EnumSet;
import java.util.Objects;

public class Kentrosaurus extends Animal {

    public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(Kentrosaurus.class, EntityDataSerializers.LONG);
    private static final EntityDataAccessor<String> BEHAVIOR = SynchedEntityData.defineId(Kentrosaurus.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> LAY_DOWN_COOLDOWN = SynchedEntityData.defineId(Kentrosaurus.class, EntityDataSerializers.INT);
    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(2F, 1.75F);
    public static final EntityDataAccessor<Integer> GRAZING_COOLDOWN = SynchedEntityData.defineId(Kentrosaurus.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> GRAZING_TIMER = SynchedEntityData.defineId(Kentrosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Kentrosaurus.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState standUpAnimationState = new AnimationState();
    public final AnimationState layDownAnimationState = new AnimationState();
    public final AnimationState layDownIdleAnimationState = new AnimationState();
    public final AnimationState grazeAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    public Kentrosaurus(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new KentrosaurusMoveControl(this);
        this.lookControl = new KentrosaurusLookControl(this);
        this.setMaxUpStep(1);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new KentrosaurusBodyRotationControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new KentrosaurusPanicGoal(this));
        this.goalSelector.addGoal(2, new KentrosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2, Ingredient.of(UP2ItemTags.KENTROSAURUS_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(8, new KentrosaurusGrazeGoal(this));
        this.goalSelector.addGoal(9, new KentrosaurusLayDownGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 36.0)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.MOVEMENT_SPEED, 0.16);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.65F;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(UP2ItemTags.KENTROSAURUS_FOOD);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        boolean food = this.isFood(itemStack);
        InteractionResult interactionResult = super.mobInteract(player, interactionHand);
        if (interactionResult.consumesAction() && food) {
            this.level().playSound(null, this, UP2SoundEvents.KENTROSAURUS_EAT.get(), SoundSource.NEUTRAL, 1.0f, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        }
        if (food && this.isKentrosaurusLayingDown()) {
            this.standUp();
            this.level().playSound(null, this, UP2SoundEvents.KENTROSAURUS_EAT.get(), SoundSource.NEUTRAL, 1.0f, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.gameEvent(GameEvent.EAT, this);
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        if (this.getHealth() != this.getMaxHealth() && food) {
            this.heal((float) Objects.requireNonNull(itemStack.getFoodProperties(this)).getNutrition());
            this.level().playSound(null, this, UP2SoundEvents.KENTROSAURUS_EAT.get(), SoundSource.NEUTRAL, 1.0f, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level().broadcastEntityEvent(this, (byte) 18);
            this.gameEvent(GameEvent.EAT, this);
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return interactionResult;
    }

    @Override
    public boolean canFallInLove() {
        return false;
    }

    @Override
    public void tick () {
        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }

        if (this.getLayDownCooldown() > 0) {
            this.setLayDownCooldown(this.getLayDownCooldown() - 1);
        }
        if (this.isKentrosaurusLayingDown() && this.isInWater()) {
            this.standUpInstantly();
        }

        if (this.getGrazingCooldown() > 0) {
            if (this.getBehavior().equals(KentrosaurusBehaviors.GRAZE.getName())) this.setBehavior(KentrosaurusBehaviors.IDLE.getName());
            this.setGrazingCooldown(this.getGrazingCooldown() - 1);
        }
        if (this.getGrazingTimer() > 0) {
            this.setGrazingTimer(this.getGrazingTimer() - 1);
            if (this.getGrazingTimer() == 0) {
                this.setPose(Pose.STANDING);
                this.setBehavior(KentrosaurusBehaviors.IDLE.getName());
                this.grazingCooldown();
            }
        }

        if (this.tickCount % 200 == 0 && this.getHealth() < this.getMaxHealth()) {
            this.heal(2);
        }

        super.tick();
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout == 0) {
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        this.attack1AnimationState.animateWhen(this.isAlive() && this.getAttackState() == 1, this.tickCount);
        this.attack2AnimationState.animateWhen(this.isAlive() && this.getAttackState() == 2, this.tickCount);

        if (this.isKentrosaurusVisuallyLayingDown()) {
            this.standUpAnimationState.stop();
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
            this.idleAnimationState.stop();
            this.grazeAnimationState.stop();

            if (this.isVisuallyLayingDown()) {
                this.layDownAnimationState.startIfStopped(this.tickCount);
                this.layDownIdleAnimationState.stop();
            } else {
                this.layDownAnimationState.stop();
                this.layDownIdleAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.layDownAnimationState.stop();
            this.layDownIdleAnimationState.stop();
            this.standUpAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return (pose == UP2Poses.RESTING.get() ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == UP2Poses.GRAZING.get()) this.grazeAnimationState.start(this.tickCount);
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isKentrosaurusLayingDown();
    }

    @Override
    public void travel(Vec3 vec3) {
        if (this.refuseToMove() && this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            vec3 = vec3.multiply(0.0, 1.0, 0.0);
        }
        super.travel(vec3);
    }

    @Override
    protected void onLeashDistance(float distance) {
        if (distance > 6.0F && this.isKentrosaurusLayingDown() && !this.isInPoseTransition()) {
            this.standUp();
        }
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float amount) {
        this.standUpInstantly();
        if (!damageSource.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !damageSource.is(DamageTypes.THORNS)) {
            Entity entity = damageSource.getDirectEntity();
            if (entity instanceof LivingEntity target) {
                target.hurt(this.damageSources().thorns(this), 2.0F);
            }
            super.actuallyHurt(damageSource, amount);
        }
        super.actuallyHurt(damageSource, amount);
    }

    public boolean refuseToMove() {
        return this.isKentrosaurusLayingDown() || this.isInPoseTransition();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LAST_POSE_CHANGE_TICK, 0L);
        this.entityData.define(BEHAVIOR, KentrosaurusBehaviors.IDLE.getName());
        this.entityData.define(LAY_DOWN_COOLDOWN, 12 * 20 + random.nextInt(30 * 20));
        this.entityData.define(GRAZING_COOLDOWN, 30 * 2 * 20 + random.nextInt(30 * 8 * 20));
        this.entityData.define(GRAZING_TIMER, 0);
        this.entityData.define(ATTACK_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putLong("LastPoseTick", this.entityData.get(LAST_POSE_CHANGE_TICK));
        compoundTag.putString("Behavior", this.getBehavior());
        compoundTag.putInt("LayDownCooldown", this.getLayDownCooldown());
        compoundTag.putInt("GrazingCooldown", this.getGrazingCooldown());
        compoundTag.putInt("GrazingTimer", this.getGrazingTimer());
        compoundTag.putInt("AttackState", this.getAttackState());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLayDownCooldown(compoundTag.getInt("LayDownCooldown"));
        this.setGrazingCooldown(compoundTag.getInt("GrazingCooldown"));
        this.setGrazingTimer(compoundTag.getInt("GrazingTimer"));
        this.setBehavior(compoundTag.getString("Behavior"));
        this.setAttackState(compoundTag.getInt("AttackState"));
        long l = compoundTag.getLong("LastPoseTick");
        if (l < 0L) this.setPose(UP2Poses.RESTING.get());
        this.resetLastPoseChangeTick(l);
    }

    public String getBehavior() {
        return this.entityData.get(BEHAVIOR);
    }

    public void setBehavior(String behavior) {
        this.entityData.set(BEHAVIOR, behavior);
    }

    public int getLayDownCooldown() {
        return this.entityData.get(LAY_DOWN_COOLDOWN);
    }

    public void setLayDownCooldown(int cooldown) {
        this.entityData.set(LAY_DOWN_COOLDOWN, cooldown);
    }

    public void layDownCooldown() {
        this.entityData.set(LAY_DOWN_COOLDOWN, 30 * 20 + random.nextInt(60 * 2 * 20));
    }

    public void standUpCooldown() {
        this.entityData.set(LAY_DOWN_COOLDOWN, 50 * 20 + random.nextInt(50 * 2 * 20));
    }

    public int getGrazingTimer() {
        return this.entityData.get(GRAZING_TIMER);
    }

    public void setGrazingTimer(int timer) {
        this.entityData.set(GRAZING_TIMER, timer);
    }

    public int getGrazingCooldown() {
        return this.entityData.get(GRAZING_COOLDOWN);
    }

    public void setGrazingCooldown(int cooldown) {
        this.entityData.set(GRAZING_COOLDOWN, cooldown);
    }

    public void grazingCooldown() {
        this.entityData.set(GRAZING_COOLDOWN, 30 * 2 * 20 + random.nextInt(30 * 8 * 20));
    }

    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    public void setAttackState(int attackState) {
        this.entityData.set(ATTACK_STATE, attackState);
    }

    public boolean isKentrosaurusLayingDown() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
    }

    public boolean isKentrosaurusVisuallyLayingDown() {
        return this.getPoseTime() < 0L != this.isKentrosaurusLayingDown();
    }

    public boolean isInPoseTransition() {
        long l = this.getPoseTime();
        return l < (long) (20);
    }

    private boolean isVisuallyLayingDown() {
        return this.isKentrosaurusLayingDown() && this.getPoseTime() < 20L && this.getPoseTime() >= 0L;
    }

    public void layDown() {
        if (this.isKentrosaurusLayingDown()) return;
        this.setPose(UP2Poses.RESTING.get());
        this.resetLastPoseChangeTick(-(this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void standUp() {
        if (!this.isKentrosaurusLayingDown()) {
            return;
        }
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTick((this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void standUpInstantly() {
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTickToFullStand((this.level()).getGameTime());
        this.refreshDimensions();
    }

    @VisibleForTesting
    public void resetLastPoseChangeTick(long l) {
        this.entityData.set(LAST_POSE_CHANGE_TICK, l);
    }

    private void resetLastPoseChangeTickToFullStand(long l) {
        this.resetLastPoseChangeTick(Math.max(0L, l - 52L - 1L));
    }

    public long getPoseTime() {
        return (this.level()).getGameTime() - Math.abs(this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob p_146744_) {
        return UP2Entities.KENTROSAURUS.get().create(serverLevel);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.KENTROSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.KENTROSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.KENTROSAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.KENTROSAURUS_STEP.get(), 1.0F, 1.1F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 150;
    }

    // goals
    private static class KentrosaurusAttackGoal extends Goal {

        private int attackTime = 0;
        Kentrosaurus kentrosaurus;

        public KentrosaurusAttackGoal(Kentrosaurus kentrosaurus) {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            this.kentrosaurus = kentrosaurus;
        }

        public boolean canUse() {
            return !this.kentrosaurus.isBaby() && this.kentrosaurus.getTarget() != null && this.kentrosaurus.getTarget().isAlive();
        }

        public void start() {
            this.kentrosaurus.setAttackState(0);
            this.attackTime = 0;
        }

        public void stop() {
            this.kentrosaurus.setAttackState(0);
        }

        public void tick() {
            LivingEntity target = this.kentrosaurus.getTarget();
            if (target != null) {
                this.kentrosaurus.lookAt(this.kentrosaurus.getTarget(), 30F, 30F);
                this.kentrosaurus.getLookControl().setLookAt(this.kentrosaurus.getTarget(), 30F, 30F);

                double distance = this.kentrosaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int attackState = this.kentrosaurus.getAttackState();

                if (attackState == 1 || attackState == 2) {
                    tickAttack();
                } else {
                    this.kentrosaurus.getNavigation().moveTo(target, 1.75D);
                    if (distance <= 13) {
                        if (this.kentrosaurus.random.nextBoolean()) {
                            this.kentrosaurus.setAttackState(1);
                        } else {
                            this.kentrosaurus.setAttackState(2);
                        }
                    }
                }
            }
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        protected void tickAttack() {
            this.attackTime++;
            LivingEntity target = this.kentrosaurus.getTarget();

            if (this.attackTime == 16) {
                if (this.kentrosaurus.distanceTo(Objects.requireNonNull(target)) <= this.getAttackReach(target)) {
                    this.kentrosaurus.swing(InteractionHand.MAIN_HAND);
                    this.kentrosaurus.doHurtTarget(target);
                }
            }
            if (this.attackTime >= 40) {
                this.attackTime = 0;
                this.kentrosaurus.setAttackState(0);
            }
        }

        protected double getAttackReach(LivingEntity target) {
            return this.kentrosaurus.getBbWidth() * 1.5F * this.kentrosaurus.getBbWidth() * 1.0F + target.getBbWidth();
        }
    }

    private static class KentrosaurusPanicGoal extends Goal {

        protected final Kentrosaurus kentrosaurus;

        protected double posX;
        protected double posY;
        protected double posZ;

        public KentrosaurusPanicGoal(Kentrosaurus kentrosaurus) {
            this.kentrosaurus = kentrosaurus;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (!this.shouldPanic()) {
                return false;
            } else {
                return this.findRandomPosition();
            }
        }

        protected boolean shouldPanic() {
            return this.kentrosaurus.getLastHurtByMob() != null && (this.kentrosaurus.getHealth() <= this.kentrosaurus.getMaxHealth() * 0.25F || this.kentrosaurus.isBaby());
        }

        protected boolean findRandomPosition() {
            Vec3 vec3 = LandRandomPos.getPos(this.kentrosaurus, 16, 8);
            if (vec3 == null) {
                return false;
            } else {
                this.posX = vec3.x;
                this.posY = vec3.y;
                this.posZ = vec3.z;
                return true;
            }
        }

        public void start() {
            this.kentrosaurus.getNavigation().moveTo(this.posX, this.posY, this.posZ, 1.75D);
        }

        public boolean canContinueToUse() {
            return !this.kentrosaurus.getNavigation().isDone();
        }
    }

    private static class KentrosaurusLayDownGoal extends Goal {

        protected final Kentrosaurus kentrosaurus;
        private final int minimalPoseTicks;

        public KentrosaurusLayDownGoal(Kentrosaurus kentrosaurus) {
            this.kentrosaurus = kentrosaurus;
            this.minimalPoseTicks = 20 * 20 + kentrosaurus.getRandom().nextInt(20 * 20);
        }

        @Override
        public boolean canUse() {
            return !this.kentrosaurus.isInWater() && this.kentrosaurus.getLayDownCooldown() == 0 && this.kentrosaurus.getPoseTime() >= (long) this.minimalPoseTicks && !this.kentrosaurus.isLeashed() && this.kentrosaurus.onGround() && this.kentrosaurus.getBehavior().equals(KentrosaurusBehaviors.IDLE.getName());
        }

        @Override
        public boolean canContinueToUse() {
            return !this.kentrosaurus.isInWater() && this.kentrosaurus.getPoseTime() >= (long) this.minimalPoseTicks && !this.kentrosaurus.isLeashed() && this.kentrosaurus.onGround();
        }

        @Override
        public void start() {
            if (this.kentrosaurus.isKentrosaurusLayingDown()) {
                this.kentrosaurus.layDownCooldown();
                this.kentrosaurus.standUp();
            } else {
                this.kentrosaurus.standUpCooldown();
                this.kentrosaurus.layDown();
            }
        }
    }

    private static class KentrosaurusGrazeGoal extends Goal {

        protected final Kentrosaurus kentrosaurus;
        protected final KentrosaurusBehaviors behavior;

        public KentrosaurusGrazeGoal(Kentrosaurus kentrosaurus) {
            this.kentrosaurus = kentrosaurus;
            this.behavior = KentrosaurusBehaviors.GRAZE;
        }

        @Override
        public boolean canUse() {
            return this.kentrosaurus.getGrazingCooldown() == 0 && this.kentrosaurus.getBehavior().equals(KentrosaurusBehaviors.IDLE.getName()) && !this.kentrosaurus.isInWater() && this.kentrosaurus.onGround() && !this.kentrosaurus.isKentrosaurusLayingDown() && this.kentrosaurus.level().getBlockState(this.kentrosaurus.blockPosition().below()).is(Blocks.GRASS_BLOCK);
        }

        @Override
        public boolean canContinueToUse() {
            return this.kentrosaurus.getGrazingTimer() > 0 && !this.kentrosaurus.isInWater() && this.kentrosaurus.onGround() && !this.kentrosaurus.isKentrosaurusLayingDown() && this.kentrosaurus.level().getBlockState(this.kentrosaurus.blockPosition().below()).is(Blocks.GRASS_BLOCK);
        }

        @Override
        public void start() {
            super.start();
            this.kentrosaurus.setPose(UP2Poses.GRAZING.get());
            this.kentrosaurus.setGrazingTimer(behavior.getLength());
            this.kentrosaurus.setBehavior(behavior.getName());
            this.kentrosaurus.playSound(behavior.getSound(), 1.0f, this.kentrosaurus.getVoicePitch());
        }

        @Override
        public void tick() {
            super.tick();
        }

        @Override
        public void stop() {
            super.stop();
        }
    }

    private static class KentrosaurusLookControl extends LookControl {

        protected final Kentrosaurus kentrosaurus;

        KentrosaurusLookControl(Kentrosaurus kentrosaurus) {
            super(kentrosaurus);
            this.kentrosaurus = kentrosaurus;
        }

        @Override
        public void tick() {
            if (!this.kentrosaurus.refuseToMove()) super.tick();
        }
    }

    private static class KentrosaurusMoveControl extends MoveControl {

        protected final Kentrosaurus kentrosaurus;

        public KentrosaurusMoveControl(Kentrosaurus kentrosaurus) {
            super(kentrosaurus);
            this.kentrosaurus = kentrosaurus;
        }

        @Override
        public void tick() {
            if (!this.kentrosaurus.refuseToMove()) {
                if (this.operation == MoveControl.Operation.MOVE_TO && !this.kentrosaurus.isLeashed() && this.kentrosaurus.isKentrosaurusLayingDown() && !this.kentrosaurus.isInPoseTransition()) {
                    this.kentrosaurus.standUp();
                }
                super.tick();
            }
        }
    }

    private static class KentrosaurusBodyRotationControl extends BodyRotationControl {

        protected final Kentrosaurus kentrosaurus;

        public KentrosaurusBodyRotationControl(Kentrosaurus kentrosaurus) {
            super(kentrosaurus);
            this.kentrosaurus = kentrosaurus;
        }

        @Override
        public void clientTick() {
            if (!this.kentrosaurus.refuseToMove()) super.clientTick();
        }
    }
}
