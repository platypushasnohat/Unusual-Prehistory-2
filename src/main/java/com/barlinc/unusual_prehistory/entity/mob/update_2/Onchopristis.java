package com.barlinc.unusual_prehistory.entity.mob.update_2;

import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.CustomizableRandomSwimGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class Onchopristis extends PrehistoricAquaticMob {

    public static final EntityDataAccessor<Boolean> BURROWED = SynchedEntityData.defineId(Onchopristis.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> BURROW_COOLDOWN = SynchedEntityData.defineId(Onchopristis.class, EntityDataSerializers.INT);

    private int attackCooldown = 0;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState burrowAnimationState = new SmoothAnimationState();

    private boolean attackAlt = false;

    public Onchopristis(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new OnchopristisMoveControl(this);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.64F)
                .add(Attributes.FOLLOW_RANGE, 16.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new OnchopristisAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.ONCHOPRISTIS_FOOD), false));
        this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 40, 10, 7));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVec);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.horizontalCollision && this.isEyeInFluid(FluidTags.WATER) && this.isPathFinding()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.005, 0.0));
            }
            if ((!this.onGround() || this.isBurrowed()) && this.getBurrowCooldown() == 0 && !this.isLeashed()) {
                if (this.getNavigation().getPath() != null) {
                    this.getNavigation().stop();
                }
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.05D, 0.0D));
            }
        } else {
            super.travel(travelVec);
        }
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.5F;
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return this.getDepthPathfindingFavor(pos, level);
    }

    @Override
    public boolean isPushable() {
        return !this.isBurrowed();
    }

    @Override
    public void doPush(@NotNull Entity entity) {
        if (!this.isBurrowed()) super.doPush(entity);
    }

    @Override
    public void tick() {
        super.tick();

        if (attackCooldown > 0) attackCooldown--;

        if (this.isAlive() && this.isBurrowed() && !this.level().isClientSide && this.getTarget() == null && tickCount % 20 == 0) {
            this.getSteppedOn();
        }

        if (this.isInWater() && !this.isAggressive() && this.getLastHurtMob() == null) {
            if (this.getBurrowCooldown() > 0) this.setBurrowCooldown(this.getBurrowCooldown() - 1);
        }

        this.tickBurrowing();
    }

    private void tickBurrowing() {
        if (this.isBurrowed() && !this.isInWater()) this.setBurrowed(false);

        if (this.isInWater()) {
            if (!this.isLeashed() && this.getBurrowCooldown() == 0 && this.onGround() && !this.isBurrowed() && !this.isAggressive() && this.getLastHurtMob() == null) {
                this.setBurrowed(true);
            }
        }

        if (this.isBurrowed() && this.getRandom().nextInt(1600) == 0) {
            this.setBurrowCooldown(600 + this.getRandom().nextInt(600));
            this.setBurrowed(false);
        }
    }

    private void getSteppedOn() {
        this.level().getEntities(this, this.getAggroHitbox()).forEach((entity) -> {
            if (entity instanceof LivingEntity mob && mob.isAlive() && !(mob instanceof Onchopristis)) {
                if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(mob) && !this.isPacified()) {
                    this.setTarget(mob);
                    this.setBurrowed(false);
                    this.setBurrowCooldown(600 + this.getRandom().nextInt(600));
                }
            }
        });
    }

    @NotNull
    public AABB getAggroHitbox() {
        return this.getBoundingBox().deflate(0, 0.1, 0).move(0, 0.4, 0);
    }

    @Override
    protected void onLeashDistance(float distance) {
        if (distance > 6.0F && this.isBurrowed()) {
            this.setBurrowed(false);
            this.setBurrowCooldown(600 + this.getRandom().nextInt(600));
        }
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource source, float amount) {
        super.actuallyHurt(source, amount);
        if (this.isBurrowed()) {
            this.setBurrowed(false);
            this.setBurrowCooldown(600 + this.getRandom().nextInt(600));
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isBurrowed(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.burrowAnimationState.animateWhen(this.isBurrowed(), this.tickCount);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.isBurrowed();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BURROWED, false);
        this.entityData.define(BURROW_COOLDOWN, 600 + this.getRandom().nextInt(600));
    }

    public boolean isBurrowed() {
        return this.entityData.get(BURROWED);
    }

    public void setBurrowed(boolean burrowed) {
        this.entityData.set(BURROWED, burrowed);
    }

    public int getBurrowCooldown() {
        return this.entityData.get(BURROW_COOLDOWN);
    }

    public void setBurrowCooldown(int cooldown) {
        this.entityData.set(BURROW_COOLDOWN, cooldown);
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_ONCHOPRISTIS);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.ONCHOPRISTIS_FOOD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.ONCHOPRISTIS.get().create(level);
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return ItemStack.EMPTY;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.ONCHOPRISTIS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.ONCHOPRISTIS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.ONCHOPRISTIS_FLOP.get();
    }

    // Goals
    private static class OnchopristisAttackGoal extends AttackGoal {

        private final Onchopristis onchopristis;

        public OnchopristisAttackGoal(Onchopristis onchopristis) {
            super(onchopristis);
            this.onchopristis = onchopristis;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && (onchopristis.getTarget().isInWater() || !onchopristis.isInWater());
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && (onchopristis.getTarget().isInWater() || !onchopristis.isInWater());
        }

        @Override
        public void tick() {
            LivingEntity target = onchopristis.getTarget();
            if (target != null) {
                this.onchopristis.lookAt(target, 30F, 30F);
                this.onchopristis.getLookControl().setLookAt(target, 30F, 30F);

                double distance = onchopristis.distanceToSqr(target);
                int attackState = onchopristis.getAttackState();

                this.onchopristis.getNavigation().moveTo(target, 1.5D);

                if (attackState == 1) {
                    this.tickAttack();
                } else if (distance < this.getAttackReachSqr(target)) {
                    this.onchopristis.setAttackState(1);
                }
            }
        }

        protected void tickAttack() {
            this.timer++;
            LivingEntity target = onchopristis.getTarget();
            if (timer == 1) {
                this.onchopristis.attackAlt = onchopristis.getRandom().nextBoolean();
                this.onchopristis.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 9) {
                if (this.isInAttackRange(target, 2.0D)) {
                    this.onchopristis.doHurtTarget(target);
                    this.onchopristis.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 20) {
                this.timer = 0;
                this.onchopristis.setPose(Pose.STANDING);
                this.onchopristis.setAttackState(0);
            }
        }
    }

    private static class OnchopristisMoveControl extends SmoothSwimmingMoveControl {

        protected final Onchopristis onchopristis;

        public OnchopristisMoveControl(Onchopristis onchopristis) {
            super(onchopristis, 85, 10, 0.02F, 0.1F, false);
            this.onchopristis = onchopristis;
        }

        @Override
        public void tick() {
            if (!this.onchopristis.refuseToMove()) {
                if (this.operation == MoveControl.Operation.MOVE_TO && !this.onchopristis.isLeashed() && this.onchopristis.isBurrowed()) {
                    this.onchopristis.setBurrowed(false);
                }
                super.tick();
            }
        }
    }
}
