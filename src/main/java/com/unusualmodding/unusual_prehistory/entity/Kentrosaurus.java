package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goals.*;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.KentrosaurusMoveControl;
import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Kentrosaurus extends PrehistoricMob {

    public static final EntityDataAccessor<Integer> LAY_DOWN_COOLDOWN = SynchedEntityData.defineId(Kentrosaurus.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> GRAZING_COOLDOWN = SynchedEntityData.defineId(Kentrosaurus.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> SHAKE_COOLDOWN = SynchedEntityData.defineId(Kentrosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Kentrosaurus.class, EntityDataSerializers.INT);
    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(2F, 1.75F);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState standUpAnimationState = new AnimationState();
    public final AnimationState layDownAnimationState = new AnimationState();
    public final AnimationState layDownIdleAnimationState = new AnimationState();
    public final AnimationState grazeAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState shakeAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    public Kentrosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new KentrosaurusMoveControl(this);
        this.setMaxUpStep(1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new KentrosaurusPanicGoal(this));
        this.goalSelector.addGoal(2, new KentrosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2, Ingredient.of(UP2ItemTags.KENTROSAURUS_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new KentrosaurusGrazeGoal(this));
        this.goalSelector.addGoal(9, new KentrosaurusShakeGoal(this));
        this.goalSelector.addGoal(10, new KentrosaurusLayDownGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 36.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.16F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.65F;
    }

    @Override
    public double getFluidJumpThreshold() {
        if (useLowerFluidJumpThreshold) {
            return super.getFluidJumpThreshold();
        }
        return 0.35 * getBbHeight();
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
    public void tick() {
        super.tick();
        if (this.isKentrosaurusLayingDown() && this.isInWater()) {
            this.standUpInstantly();
        }
    }

    @Override
    public void setupAnimationCooldowns() {
        if (this.getLayDownCooldown() > 0) {
            this.setLayDownCooldown(this.getLayDownCooldown() - 1);
        }
        if (this.getGrazingCooldown() > 0) {
            this.setGrazingCooldown(this.getGrazingCooldown() - 1);
        }
        if (this.getShakeCooldown() > 0) {
            this.setShakeCooldown(this.getShakeCooldown() - 1);
        }
    }

    @Override
    public void setupAnimationStates() {
        if (this.idleAnimationTimeout == 0) {
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        this.attack1AnimationState.animateWhen(this.isAlive() && this.getAttackState() == 1, this.tickCount);
        this.attack2AnimationState.animateWhen(this.isAlive() && this.getAttackState() == 2, this.tickCount);
        this.swimmingAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);

        if (this.isKentrosaurusVisuallyLayingDown()) {
            this.standUpAnimationState.stop();
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
            this.idleAnimationState.stop();
            this.grazeAnimationState.stop();
            this.shakeAnimationState.stop();

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
            if (this.getPose() == UP2Poses.SHAKING.get()) this.shakeAnimationState.start(this.tickCount);
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

    @Override
    public boolean refuseToMove() {
        return this.isKentrosaurusLayingDown() || this.isInPoseTransition();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LAY_DOWN_COOLDOWN, 12 * 20 + getRandom().nextInt(30 * 20));
        this.entityData.define(GRAZING_COOLDOWN, 30 * 2 * 20 + getRandom().nextInt(30 * 8 * 20));
        this.entityData.define(SHAKE_COOLDOWN, 70 * 2 * 20 + getRandom().nextInt(70 * 8 * 20));
        this.entityData.define(ATTACK_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("LayDownCooldown", this.getLayDownCooldown());
        compoundTag.putInt("GrazingCooldown", this.getGrazingCooldown());
        compoundTag.putInt("ShakeCooldown", this.getShakeCooldown());
        compoundTag.putInt("AttackState", this.getAttackState());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLayDownCooldown(compoundTag.getInt("LayDownCooldown"));
        this.setGrazingCooldown(compoundTag.getInt("GrazingCooldown"));
        this.setShakeCooldown(compoundTag.getInt("ShakeCooldown"));
        this.setAttackState(compoundTag.getInt("AttackState"));
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

    public int getGrazingCooldown() {
        return this.entityData.get(GRAZING_COOLDOWN);
    }
    public void setGrazingCooldown(int cooldown) {
        this.entityData.set(GRAZING_COOLDOWN, cooldown);
    }

    public int getShakeCooldown() {
        return this.entityData.get(SHAKE_COOLDOWN);
    }

    public void setShakeCooldown(int cooldown) {
        this.entityData.set(SHAKE_COOLDOWN, cooldown);
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
}
