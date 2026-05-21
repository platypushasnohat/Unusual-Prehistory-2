package com.barlinc.unusual_prehistory.entity.mob.update_6;

import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.SleepingGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Shringasaurus extends PrehistoricMob {

    private static final EntityDimensions EEPY_DIMENSIONS = EntityDimensions.scalable(1.5F, 2.2F).withEyeHeight(2.1F);

    private int attackCooldown = 0;
    private int chargeCooldown = 100;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState chargeStartAnimationState = new SmoothAnimationState(0.75F);
    public final SmoothAnimationState chargeAnimationState = new SmoothAnimationState(0.75F);
    public final SmoothAnimationState chargeEndAnimationState = new SmoothAnimationState(0.75F);

    private boolean attackAlt = false;

    public Shringasaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new ShringasaurusAttackGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.MAMMOTH_FOOD), false));
        this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new SleepingGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.STEP_HEIGHT, 1.1D);
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
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.MAMMOTH_FOOD);
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0, 0.3F, -this.getBbWidth() * 0.9F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        if (this.isEepy()) {
            return EEPY_DIMENSIONS.scale(this.getAgeScale());
        } else {
            return super.getDefaultDimensions(pose);
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getPose() == UP2Poses.START_CHARGING.get() || this.getPose() == UP2Poses.STOP_CHARGING.get();
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackCooldown > 0) {
            this.attackCooldown--;
        }
        if (chargeCooldown > 0) {
            this.chargeCooldown--;
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy() && !this.isInWaterOrBubble() && !this.isCharging(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isCharging(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.chargeStartAnimationState.animateWhen(this.getPose() == UP2Poses.START_CHARGING.get(), this.tickCount);
        this.chargeAnimationState.animateWhen(this.getPose() == UP2Poses.CHARGING.get(), this.tickCount);
        this.chargeEndAnimationState.animateWhen(this.getPose() == UP2Poses.STOP_CHARGING.get(), this.tickCount);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isCharging() {
        return this.getPose() == UP2Poses.START_CHARGING.get() || this.getPose() == UP2Poses.CHARGING.get() || this.getPose() == UP2Poses.STOP_CHARGING.get();
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.attackAlt = this.getRandom().nextBoolean();
            }
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.SHRINGASAURUS.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.WOOLLY_MAMMOTH_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.WOOLLY_MAMMOTH_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.WOOLLY_MAMMOTH_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.WOOLLY_MAMMOTH_STEP.get(), this.isBaby() ? 0.15F : 0.3F, this.isBaby() ? 1.5F : 1.0F);
    }

    // Goals
    private static class ShringasaurusAttackGoal extends AttackGoal {

        private final Shringasaurus shringasaurus;
        private boolean hitTarget;

        public ShringasaurusAttackGoal(Shringasaurus shringasaurus) {
            super(shringasaurus);
            this.shringasaurus = shringasaurus;
        }

        @Override
        public void start() {
            super.start();
            this.hitTarget = false;
        }

        @Override
        public void tick() {
            LivingEntity target = shringasaurus.getTarget();
            if (target != null) {

                double distance = shringasaurus.distanceToSqr(target);
                int attackState = shringasaurus.getAttackState();

                if (attackState == 1) {
                    this.tickAttack(target);
                    this.lookAtATarget(target);
                    this.shringasaurus.getNavigation().stop();
                }
                else if (attackState == 2) {
                    this.tickCharge(target);
                    this.shringasaurus.getNavigation().stop();
                }
                else if (attackState == 3) {
                    this.tickChargeEnd(target);
                    this.shringasaurus.getNavigation().stop();
                }
                else {
                    if (!this.shringasaurus.isCharging()) {
                        this.lookAtATarget(target);
                        this.shringasaurus.getNavigation().moveTo(target, 1.6D);
                    }
                    if (distance < this.getAttackReachSqr(target) && shringasaurus.attackCooldown == 0) {
                        this.shringasaurus.setAttackState(1);
                    } else if (distance > this.getAttackReachSqr(target, 3.0D) && shringasaurus.chargeCooldown == 0 && !shringasaurus.isInWaterOrBubble()) {
                        this.shringasaurus.setAttackState(2);
                    }
                }
            }
        }

        private void lookAtATarget(LivingEntity target) {
            this.shringasaurus.lookAt(target, 30.0F, 30.0F);
            this.shringasaurus.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }

        protected void tickAttack(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.shringasaurus.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 10) {
                if (this.isInAttackRange(target, 2.0D)) {
                    this.shringasaurus.doHurtTarget(target);
                    this.strongKnockback(target, 0.3D, 0.1D);
                }
            }
            if (timer > 20) {
                this.timer = 0;
                this.shringasaurus.attackCooldown = 10;
                this.shringasaurus.setPose(Pose.STANDING);
                this.shringasaurus.setAttackState(0);
            }
        }

        protected void tickCharge(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.shringasaurus.setPose(UP2Poses.START_CHARGING.get());
            }
            if (timer < 15) {
                this.lookAtATarget(target);
            }
            if (timer == 15) {
                this.shringasaurus.setPose(UP2Poses.CHARGING.get());
                this.shringasaurus.playSound(UP2SoundEvents.PACHYCEPHALOSAURUS_WARN.get(), 1.8F, 0.9F + shringasaurus.getRandom().nextFloat() * 0.3F);
            }
            if (timer > 15 && timer < 50 && !hitTarget) {
                if (this.isInAttackRange(target, 1.5D)) {
                    this.hitTarget = true;
                } else {
                    this.chargeAtTarget(target, 0.45F);
                }
            }
            if (timer > 50 || hitTarget) {
                this.timer = 0;
                this.hitTarget = false;
                this.shringasaurus.setAttackState(3);
            }
        }

        protected void tickChargeEnd(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.shringasaurus.setPose(UP2Poses.STOP_CHARGING.get());
            }
            if (timer == 4) {
                if (this.isInAttackRange(target, 2.0D)) {
                    this.shringasaurus.doHurtTarget(target);
                    this.strongKnockback(target, 1.5D, 0.2D);
                }
            }
            if (timer > 15) {
                this.timer = 0;
                this.shringasaurus.attackCooldown = 20;
                this.shringasaurus.chargeCooldown = 100 + shringasaurus.getRandom().nextInt(50);
                this.shringasaurus.setPose(Pose.STANDING);
                this.shringasaurus.setAttackState(0);
            }
        }
    }
}
