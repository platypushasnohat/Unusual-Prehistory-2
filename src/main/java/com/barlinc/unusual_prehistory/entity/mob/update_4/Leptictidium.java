package com.barlinc.unusual_prehistory.entity.mob.update_4;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class Leptictidium extends PrehistoricMob {

    private int attackCooldown = 0;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sniffAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState preenAnimationState = new SmoothAnimationState();

    private int sniffCooldown = 400 + this.getRandom().nextInt(400);
    private int preenCooldown = 700 + this.getRandom().nextInt(700);

    public Leptictidium(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LeptictidiumAttackGoal(this));
        this.goalSelector.addGoal(2, new LeptictidiumRunLikeCrazyGoal(this));
        this.goalSelector.addGoal(3, new LargePanicGoal(this, 1.6D, 10, 4));
        this.goalSelector.addGoal(4, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 8.0F, 1.6D, entity -> entity.getType().is(UP2EntityTags.LEPTICTIDIUM_AVOIDS)));
        this.goalSelector.addGoal(4, new PrehistoricAvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.LEPTICTIDIUM_FOOD), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(7, new PrehistoricRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new LeptictidiumPreenGoal(this));
        this.goalSelector.addGoal(9, new LeptictidiumSniffGoal(this));
        this.targetSelector.addGoal(0, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 200, true, true, entity -> entity.getType().is(EntityTypeTags.ARTHROPOD) && !entity.getType().is(UP2EntityTags.LEPTICTIDIUM_DOESNT_TARGET)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && this.horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.7D * this.getBbHeight();
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
    public float getAdditionalStepHeight() {
        return this.isRunning() ? 0.4F : super.getAdditionalStepHeight();
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.LEPTICTIDIUM_FOOD);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.tickCount % 20 == 0) {
                this.infectNearbyMobs();
                this.getInfectedByNearbyMobs();
            }
            if (attackCooldown > 0) attackCooldown--;
        }
    }

    private void infectNearbyMobs() {
        List<LivingEntity> nearbyEntities = this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat(), this, this.getBoundingBox().inflate(3.0));
        if (!this.getActiveEffects().isEmpty() && !nearbyEntities.isEmpty()) {
            for (MobEffectInstance effectInstance : this.getActiveEffects()) {
                nearbyEntities.stream().filter(entity -> entity != this && !entity.hasEffect(effectInstance.getEffect()) && effectInstance.getDuration() > 20 && !effectInstance.getEffect().value().isInstantenous()).forEach(entity -> entity.addEffect(new MobEffectInstance(effectInstance.getEffect(), (int) (effectInstance.getDuration() * 0.3F), 0), this));
            }
        }
    }

    private void getInfectedByNearbyMobs() {
        List<LivingEntity> nearbyEntities = this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat(), this, this.getBoundingBox().inflate(3.0));
        if (!nearbyEntities.isEmpty()) {
            nearbyEntities.stream().filter(entity -> entity != this).forEach(entity -> {
                if (!entity.getActiveEffects().isEmpty()) {
                    for (MobEffectInstance effectInstance : entity.getActiveEffects()) {
                        if (!this.hasEffect(effectInstance.getEffect()) && effectInstance.getDuration() > 20 && !effectInstance.getEffect().value().isInstantenous()) {
                            this.addEffect(new MobEffectInstance(effectInstance.getEffect(), (int) (effectInstance.getDuration() * 0.3F), effectInstance.getAmplifier()), entity);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (!this.level().isClientSide) {
            if (preenCooldown > 0) preenCooldown--;
            if (sniffCooldown > 0) sniffCooldown--;
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && this.getPose() == Pose.STANDING && this.getIdleState() != 1, this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble() && this.getPose() == Pose.STANDING, this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
        this.preenAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.sniffAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.LEPTICTIDIUM.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.LEPTICTIDIUM_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.LEPTICTIDIUM_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.LEPTICTIDIUM_DEATH.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 150;
    }

    // Goals
    private static class LeptictidiumRunLikeCrazyGoal extends Goal {

        protected final Leptictidium leptictidium;
        protected double wantedX;
        protected double wantedY;
        protected double wantedZ;

        public LeptictidiumRunLikeCrazyGoal(Leptictidium leptictidium) {
            this.leptictidium = leptictidium;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.leptictidium.isVehicle()) {
                return false;
            } else {
                Vec3 vec3 = this.getPosition();
                if (vec3 == null) {
                    return false;
                } else if (this.leptictidium.getHealth() <= this.leptictidium.getMaxHealth() * 0.5F) {
                    this.wantedX = vec3.x;
                    this.wantedY = vec3.y;
                    this.wantedZ = vec3.z;
                    return true;
                }
            }
            return false;
        }

        @javax.annotation.Nullable
        protected Vec3 getPosition() {
            return DefaultRandomPos.getPos(this.leptictidium, 15, 7);
        }

        @Override
        public boolean canContinueToUse() {
            return !this.leptictidium.isVehicle() && !this.leptictidium.getNavigation().isDone();
        }

        @Override
        public void start() {
            this.leptictidium.setRunning(true);
            this.leptictidium.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, 1.6D + (0.25F * (1.0F - leptictidium.getHealth() / leptictidium.getMaxHealth())));
        }

        @Override
        public void tick() {
            this.leptictidium.getLookControl().setLookAt(this.wantedX, this.wantedY, this.wantedZ, 30F, 30F);
        }

        @Override
        public void stop() {
            this.leptictidium.setRunning(false);
            this.leptictidium.getNavigation().stop();
        }
    }

    private static class LeptictidiumAttackGoal extends AttackGoal {

        private final Leptictidium leptictidium;

        public LeptictidiumAttackGoal(Leptictidium leptictidium) {
            super(leptictidium);
            this.leptictidium = leptictidium;
        }

        @Override
        public void tick() {
            LivingEntity target = leptictidium.getTarget();
            if (target != null) {
                double distance = leptictidium.distanceToSqr(target);
                this.leptictidium.lookAt(leptictidium.getTarget(), 30F, 30F);
                this.leptictidium.getLookControl().setLookAt(target, 30F, 30F);

                if (leptictidium.getAttackState() == 1) {
                    this.leptictidium.getNavigation().stop();
                    this.tickAttack();
                } else {
                    this.leptictidium.getNavigation().moveTo(target, 1.7D);
                    if (distance <= this.getAttackReachSqr(target) && leptictidium.attackCooldown == 0) {
                        this.leptictidium.setAttackState(1);
                    }
                }
            }
        }

        protected void tickAttack() {
            this.timer++;
            LivingEntity target = leptictidium.getTarget();
            if (timer == 1) leptictidium.setPose(UP2Poses.ATTACKING.get());
            if (timer == 8) {
                if (this.isInAttackRange(target, 1.5D)) {
                    this.leptictidium.doHurtTarget(target);
                    this.leptictidium.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 20) {
                this.timer = 0;
                this.leptictidium.setPose(Pose.STANDING);
                this.leptictidium.setAttackState(0);
                this.leptictidium.attackCooldown = 5 + leptictidium.getRandom().nextInt(5);
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity target) {
            return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + target.getBbWidth();
        }
    }

    private static class LeptictidiumPreenGoal extends IdleAnimationGoal {

        private final Leptictidium leptictidium;

        public LeptictidiumPreenGoal(Leptictidium leptictidium) {
            super(leptictidium, 30, 1);
            this.leptictidium = leptictidium;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && leptictidium.preenCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.leptictidium.preenCooldown = 700 + leptictidium.getRandom().nextInt(700);
        }
    }

    private static class LeptictidiumSniffGoal extends IdleAnimationGoal {

        private final Leptictidium leptictidium;

        public LeptictidiumSniffGoal(Leptictidium leptictidium) {
            super(leptictidium, 30, 2, false);
            this.leptictidium = leptictidium;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && leptictidium.sniffCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.leptictidium.sniffCooldown = 400 + leptictidium.getRandom().nextInt(400);
        }
    }
}
