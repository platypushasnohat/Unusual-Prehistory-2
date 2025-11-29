package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.kentrosaurus.KentrosaurusAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.kentrosaurus.KentrosaurusDefendThornsGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.kentrosaurus.KentrosaurusFollowThornsGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.kentrosaurus.KentrosaurusLayDownGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.PrehistoricMobMoveControl;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2DamageTypeTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Kentrosaurus extends PrehistoricMob {

    public static final EntityDataAccessor<Integer> LAY_DOWN_COOLDOWN = SynchedEntityData.defineId(Kentrosaurus.class, EntityDataSerializers.INT);
    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(1.98F, 1.75F);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState standUpAnimationState = new AnimationState();
    public final AnimationState layDownAnimationState = new AnimationState();
    public final AnimationState layDownIdleAnimationState = new AnimationState();
    public final AnimationState grazeAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();

    private int attackTicks;

    private final byte GRAZE = 66;
    private int grazingTicks = 0;

    public Kentrosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new KentrosaurusMoveControl(this);
        this.setMaxUpStep(1);
        this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new KentrosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new KentrosaurusFollowThornsGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.KENTROSAURUS_FOOD), false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new KentrosaurusLayDownGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new KentrosaurusDefendThornsGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 36.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.16F)
                .add(Attributes.ARMOR, 4.0F);
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
        return 0.35 * getBbHeight();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.KENTROSAURUS_FOOD);
    }

    public boolean entityHasThorns(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getEnchantmentLevel(Enchantments.THORNS) > 0 || entity.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(Enchantments.THORNS) > 0 || entity.getItemBySlot(EquipmentSlot.LEGS).getEnchantmentLevel(Enchantments.THORNS) > 0 || entity.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(Enchantments.THORNS) > 0;
    }

    public static void angerNearbyKentrosaurus(Player player, boolean angerIfSeen) {
        List<Kentrosaurus> list = player.level().getEntitiesOfClass(Kentrosaurus.class, player.getBoundingBox().inflate(16));
        list.stream().filter((kentrosaurus) -> !angerIfSeen || BehaviorUtils.canSee(kentrosaurus, player)).forEach((kentrosaurus) -> kentrosaurus.standAndSetTarget(player));
    }

    private void standAndSetTarget(LivingEntity target) {
        this.setTarget(target);
        this.standUp();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isKentrosaurusLayingDown() && this.isInWaterOrBubble()) this.standUpInstantly();
        if (this.level().canSeeSky(this.blockPosition()) && (this.level().isThundering() || this.level().isRaining())) this.standUp();
    }

    @Override
    public void setupAnimationCooldowns() {
        if (this.attackTicks > 0) attackTicks--;
        if (this.attackTicks == 0 && this.getPose() == UP2Poses.TAIL_WHIPPING.get()) this.setPose(Pose.STANDING);
        if (!this.isInWaterOrBubble() && this.getBehavior().equals(Behaviors.IDLE.getName())) {
            if (this.getLayDownCooldown() > 0) this.setLayDownCooldown(this.getLayDownCooldown() - 1);
            if (this.canGraze() && this.random.nextInt(350) == 0) {
                this.setGrazing(true);
            }
            if (this.isGrazing() && this.grazingTicks++ > 40) {
                this.grazingTicks = 0;
                this.setGrazing(false);
            }
        }
    }

    private boolean canGraze() {
        return !this.isKentrosaurusLayingDown() && this.getPose() == Pose.STANDING && !this.isGrazing() && this.level().getBlockState(this.blockPosition().below()).is(Blocks.GRASS_BLOCK);
    }

    @Override
    public void setupAnimationStates() {
        if (attackTicks == 0 && (this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted())) {
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
        }
        this.idleAnimationState.animateWhen(!this.isInWater() && this.getPose() != UP2Poses.TAIL_WHIPPING.get(), this.tickCount);
        this.swimmingAnimationState.animateWhen(this.isInWater() && this.getPose() != UP2Poses.TAIL_WHIPPING.get(), this.tickCount);
        this.grazeAnimationState.animateWhen(this.isGrazing(), this.tickCount);

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
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.TAIL_WHIPPING.get()) {
                this.grazeAnimationState.stop();
                if (this.getRandom().nextBoolean()) this.attack1AnimationState.start(this.tickCount);
                else this.attack2AnimationState.start(this.tickCount);
                this.attackTicks = 40;
            }
            else {
                this.attack1AnimationState.stop();
                this.attack2AnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    public boolean isGrazing() {
        return this.getFlag(GRAZE);
    }

    public void setGrazing(boolean grazing) {
        this.setFlag(GRAZE, grazing);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return (pose == UP2Poses.RESTING.get() ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose));
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isKentrosaurusLayingDown();
    }

    @Override
    public void travel(@NotNull Vec3 vec3) {
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
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(UP2DamageTypeTags.KENTROSAURUS_IMMUNE_TO);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.isKentrosaurusLayingDown() || (this.isGrazing() && this.grazingTicks > 0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LAY_DOWN_COOLDOWN, 50 * 50 + getRandom().nextInt(60 * 2 * 20));
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

    public int getLayDownCooldown() {
        return this.entityData.get(LAY_DOWN_COOLDOWN);
    }

    public void setLayDownCooldown(int cooldown) {
        this.entityData.set(LAY_DOWN_COOLDOWN, cooldown);
    }

    public void layDownCooldown() {
        this.entityData.set(LAY_DOWN_COOLDOWN, 50 * 50 + random.nextInt(60 * 2 * 20));
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

    private static class KentrosaurusMoveControl extends PrehistoricMobMoveControl {

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
}
