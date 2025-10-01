package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goals.*;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.KentrosaurusMoveControl;
import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
import com.unusualmodding.unusual_prehistory.entity.utils.Behaviors;
import com.unusualmodding.unusual_prehistory.entity.utils.UP2Poses;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Kentrosaurus extends PrehistoricMob {

    public static final EntityDataAccessor<Integer> LAY_DOWN_COOLDOWN = SynchedEntityData.defineId(Kentrosaurus.class, EntityDataSerializers.INT);
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

    private int grazingTimer = 0;
    private int shakingTimer = 0;

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
        this.goalSelector.addGoal(4, new SeekShelterGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new KentrosaurusLayDownGoal(this));
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
        return size.height * 0.9F;
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
        if (this.isKentrosaurusLayingDown() && this.isInWaterOrBubble()) {
            this.standUpInstantly();
        }
        if (this.level().canSeeSky(this.blockPosition()) && (this.level().isThundering() || this.level().isRaining())) {
            this.standUp();
        }
    }

    @Override
    public void setupAnimationCooldowns() {
        if (!this.isInWaterOrBubble() && this.getBehavior().equals(Behaviors.IDLE.getName())) {
            if (this.getLayDownCooldown() > 0) {
                this.setLayDownCooldown(this.getLayDownCooldown() - 1);
            }
            if (!this.isKentrosaurusLayingDown()) {
                if (!this.isGrazing() && !this.isShaking() && this.random.nextInt(300) == 0 && this.level().getBlockState(this.blockPosition().below()).is(Blocks.GRASS_BLOCK)) {
                    this.setGrazing(true);
                }
                if (!this.isShaking() && !this.isGrazing() && this.random.nextInt(600) == 0) {
                    this.setShaking(true);
                }
            }
            if (this.isGrazing() && this.grazingTimer++ > 40) {
                this.grazingTimer = 0;
                this.setGrazing(false);
            }
            if (this.isShaking() && this.shakingTimer++ > 40) {
                this.shakingTimer = 0;
                this.setShaking(false);
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getAttackState() == 1, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getAttackState() == 2, this.tickCount);
        this.swimmingAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.grazeAnimationState.animateWhen(this.isGrazing(), this.tickCount);
        this.shakeAnimationState.animateWhen(this.isShaking(), this.tickCount);

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
    public boolean isImmobile() {
        return super.isImmobile() || this.isGrazing();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LAY_DOWN_COOLDOWN, 40 * 40 + getRandom().nextInt(60 * 2 * 20));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("LayDownCooldown", this.getLayDownCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLayDownCooldown(compoundTag.getInt("LayDownCooldown"));
    }

    public boolean isGrazing() {
        return this.getFlag(16);
    }

    public void setGrazing(boolean grazing) {
        this.setFlag(16, grazing);
    }

    public boolean isShaking() {
        return this.getFlag(32);
    }

    public void setShaking(boolean shaking) {
        this.setFlag(32, shaking);
    }

    public int getLayDownCooldown() {
        return this.entityData.get(LAY_DOWN_COOLDOWN);
    }

    public void setLayDownCooldown(int cooldown) {
        this.entityData.set(LAY_DOWN_COOLDOWN, cooldown);
    }

    public void layDownCooldown() {
        this.entityData.set(LAY_DOWN_COOLDOWN, 40 * 40 + random.nextInt(60 * 2 * 20));
    }

    public void standUpCooldown() {
        this.entityData.set(LAY_DOWN_COOLDOWN, 20 * 40 + random.nextInt(50 * 2 * 20));
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
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.KENTROSAURUS.get().create(level);
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
        return 160;
    }
}
