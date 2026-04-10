package com.barlinc.unusual_prehistory.entity.mob.update_6;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.AmphibiousMob;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
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
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class Prognathodon extends AmphibiousMob implements LeapingMob {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Prognathodon.class, EntityDataSerializers.BOOLEAN);

    public int biteCooldown = 0;

    private int yawnCooldown = 800 + this.getRandom().nextInt(800);
    private int tongueCooldown = 150 + this.getRandom().nextInt(150);
    private int nipCooldown = 700 + this.getRandom().nextInt(700);

    public final SmoothAnimationState leapAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState tongueAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState nip1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState nip2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();

    private boolean attackAlt = false;
    private boolean nipAlt = false;

    private int leapTicks;

    public Prognathodon(EntityType<? extends AmphibiousMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(true);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 90.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.12F)
                .add(Attributes.ATTACK_DAMAGE, 11.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.STEP_HEIGHT, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LargeBabyPanicGoal(this, 2.0D, 16, 8));
        this.goalSelector.addGoal(1, new AquaticLeapGoal(this, 10, 1.0D, 0.98D));
        this.goalSelector.addGoal(2, new PrognathodonAttackGoal(this));
        this.goalSelector.addGoal(3, new EnterWaterGoal(this, 1.0D, 80));
        // todo: add a special timer for this?
        this.goalSelector.addGoal(3, new BreathAirGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.PROGNATHODON_FOOD), false));
        this.goalSelector.addGoal(5, new CustomizableRandomSwimGoal(this, 1.0D, 20, 30, 15, 3, true));
        this.goalSelector.addGoal(6, new PrognathodonYawnGoal(this));
        this.goalSelector.addGoal(6, new PrognathodonTongueGoal(this));
        this.goalSelector.addGoal(6, new PrognathodonNipGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, false, entity -> entity.getType().is(UP2EntityTags.PROGNATHODON_FIGHT_TARGETS)) {
            @Override
            public boolean canUse() {
                return super.canUse() && Prognathodon.this.isInWaterOrBubble();
            }
        });
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 400, true, true, entity -> entity.getType().is(UP2EntityTags.PROGNATHODON_TARGETS)) {
            @Override
            public boolean canUse() {
                return super.canUse() && Prognathodon.this.isInWaterOrBubble();
            }
        });
        this.targetSelector.addGoal(3, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 400, true, true, this::canAttack) {
            @Override
            public boolean canUse() {
                return super.canUse() && Prognathodon.this.isInWaterOrBubble();
            }
        });
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.25F : 1.0F;
    }

    @Override
    public boolean killedEntity(@NotNull ServerLevel level, @NotNull LivingEntity victim) {
        this.heal(12);
        return super.killedEntity(level, victim);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.horizontalCollision && this.isEyeInFluid(FluidTags.WATER) && this.isPathFinding()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.005, 0.0));
            }
            if (!this.isEyeInFluid(FluidTags.WATER)) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.01D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new PrehistoricMoveControl(this);
            this.lookControl = new PrehistoricLookControl(this);
            this.navigation = this.createNavigation(this.level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new PrehistoricSwimmingMoveControl(this, 1000, 5, 1.05F);
            this.lookControl = new PrehistoricSwimmingLookControl(this, 4);
            this.navigation = new WaterBoundPathNavigation(this, this.level());
            this.isLandNavigator = false;
        }
    }

    @Override
    public boolean isPushable() {
        return this.isBaby();
    }

    @Override
    public void tick() {
        super.tick();
        final boolean ground = !this.isInWaterOrBubble() && !this.isLeaping();
        if (!ground && this.isLandNavigator) {
            this.switchNavigator(false);
        }
        if (ground && !this.isLandNavigator) {
            this.switchNavigator(true);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.leapAnimationState.animateWhen(this.isLeaping(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.tongueAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.nip1AnimationState.animateWhen(this.getIdleState() == 3 && !nipAlt, this.tickCount);
        this.nip2AnimationState.animateWhen(this.getIdleState() == 3 && nipAlt, this.tickCount);
    }

    @Override
    public float getWalkAnimationSpeed() {
        if (this.isInWaterOrBubble()) return super.getWalkAnimationSpeed();
        return this.isBaby() ? 8.0F : 16.0F;
    }

    @Override
    public void tickCooldowns() {
        if (biteCooldown > 0) biteCooldown--;
        if (yawnCooldown > 0) yawnCooldown--;
        if (tongueCooldown > 0) tongueCooldown--;
        if (nipCooldown > 0) nipCooldown--;
        if (leapTicks > 0) leapTicks--;
        if (leapTicks == 0 && this.getPose() == Pose.FALL_FLYING) this.setPose(Pose.STANDING);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == Pose.FALL_FLYING) {
                this.leapTicks = 30;
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(2);
    }

    protected void yawnCooldown() {
        this.yawnCooldown = 800 + this.getRandom().nextInt(800);
    }

    protected void tongueCooldown() {
        this.tongueCooldown = 150 + this.getRandom().nextInt(150);
    }

    protected void nipCooldown() {
        this.nipCooldown = 700 + this.getRandom().nextInt(700);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.PROGNATHODON_FOOD);
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_PROGNATHODON);
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.PROGNATHODON.get().create(level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LEAPING, false);
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.PROGNATHODON_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.PROGNATHODON_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.PROGNATHODON_DEATH.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 310;
    }

    @Override
    public float getSoundVolume() {
        return this.isBaby() ? 1.0F : 2.0F;
    }

    private static class PrognathodonAttackGoal extends AttackGoal {

        private final Prognathodon prognathodon;

        public PrognathodonAttackGoal(Prognathodon prognathodon) {
            super(prognathodon);
            this.prognathodon = prognathodon;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && (prognathodon.getTarget().isInWaterOrBubble() || !prognathodon.isInWaterOrBubble());
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && (prognathodon.getTarget().isInWaterOrBubble() || !prognathodon.isInWaterOrBubble());
        }

        @Override
        public void tick() {
            LivingEntity target = this.prognathodon.getTarget();
            if (target != null) {
                double distance = this.prognathodon.distanceToSqr(target);
                int attackState = this.prognathodon.getAttackState();

                this.prognathodon.getLookControl().setLookAt(target, 30F, 30F);
                this.prognathodon.lookAt(target, 30F, 30F);

                if (attackState == 1) this.tickBite();
                else {
                    if (distance <= this.getAttackReachSqr(target) && prognathodon.biteCooldown == 0) {
                        this.prognathodon.setAttackState(1);
                    } else {
                        this.prognathodon.getNavigation().moveTo(target, prognathodon.isInWaterOrBubble() ? 2.0D : 1.25D);
                    }
                }
            }
        }

        protected void tickBite() {
            this.timer++;
            if (timer == 1) {
                this.prognathodon.attackAlt = prognathodon.getRandom().nextBoolean();
                this.prognathodon.setPose(UP2Poses.ATTACKING.get());
                this.prognathodon.playSound(UP2SoundEvents.PROGNATHODON_ATTACK.get(), 2.0F, 1.0F * prognathodon.getRandom().nextFloat() * 0.2F);
            }
            if (timer == 9) this.biteNearbyEntities();
            if (timer > 20) {
                this.timer = 0;
                this.prognathodon.setPose(Pose.STANDING);
                this.prognathodon.setAttackState(0);
                this.prognathodon.biteCooldown = 7;
            }
        }

        private void biteNearbyEntities() {
            List<LivingEntity> nearbyEntities = prognathodon.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), prognathodon, prognathodon.getBoundingBox().inflate(3.5D));
            if (!nearbyEntities.isEmpty()) {
                nearbyEntities.stream().filter(entity -> entity != prognathodon).limit(3).forEach(entity -> {
                    entity.hurt(entity.damageSources().mobAttack(prognathodon), (float) prognathodon.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    this.prognathodon.strongKnockback(entity, 0.8D, 0.1D);
                    if (entity.isDamageSourceBlocked(prognathodon.damageSources().mobAttack(prognathodon)) && entity instanceof Player player) {
                        player.disableShield();
                    }
                    this.prognathodon.swing(InteractionHand.MAIN_HAND);
                });
            }
        }
    }

    private static class PrognathodonYawnGoal extends IdleAnimationGoal {

        private final Prognathodon prognathodon;

        public PrognathodonYawnGoal(Prognathodon prognathodon) {
            super(prognathodon, 60, 1, false, false);
            this.prognathodon = prognathodon;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && prognathodon.yawnCooldown == 0 && prognathodon.isInWaterOrBubble();
        }

        @Override
        public void stop() {
            super.stop();
            this.prognathodon.yawnCooldown();
        }
    }

    private static class PrognathodonTongueGoal extends IdleAnimationGoal {

        private final Prognathodon prognathodon;

        public PrognathodonTongueGoal(Prognathodon prognathodon) {
            super(prognathodon, 40, 2, false, false);
            this.prognathodon = prognathodon;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && prognathodon.tongueCooldown == 0 && prognathodon.isInWaterOrBubble();
        }

        @Override
        public void stop() {
            super.stop();
            this.prognathodon.tongueCooldown();
        }
    }

    private static class PrognathodonNipGoal extends IdleAnimationGoal {

        private final Prognathodon prognathodon;

        public PrognathodonNipGoal(Prognathodon prognathodon) {
            super(prognathodon, 20, 3, false, false);
            this.prognathodon = prognathodon;
        }

        @Override
        public void start() {
            super.start();
            this.prognathodon.nipAlt = prognathodon.getRandom().nextBoolean();
        }

        @Override
        public boolean canUse() {
            return super.canUse() && prognathodon.nipCooldown == 0 && prognathodon.isInWaterOrBubble();
        }

        @Override
        public void stop() {
            super.stop();
            this.prognathodon.nipCooldown();
        }
    }
}
