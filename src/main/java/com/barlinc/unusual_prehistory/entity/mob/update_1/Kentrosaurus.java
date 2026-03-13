package com.barlinc.unusual_prehistory.entity.mob.update_1;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2DamageTypeTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class Kentrosaurus extends PrehistoricMob {

    private int attackCooldown = 0;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState grazeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState stretch1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState stretch2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState angryAnimationState = new SmoothAnimationState();

    private boolean attackAlt = false;

    private int grazeCooldown = 400 + this.getRandom().nextInt(50 * 50);
    private int shakeCooldown = 300 + this.getRandom().nextInt(20 * 60);
    private int yawnCooldown = 400 + this.getRandom().nextInt(50 * 30);
    private int stretchCooldown = 400 + this.getRandom().nextInt(60 * 50);

    private boolean stretchAlt = false;

    public Kentrosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 2.0D, 10, 4) {
            @Override
            protected boolean shouldPanic() {
                return super.shouldPanic() && (Kentrosaurus.this.getHealth() <= Kentrosaurus.this.getMaxHealth() * 0.4F || Kentrosaurus.this.isBaby());
            }
        });
        this.goalSelector.addGoal(2, new KentrosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new KentrosaurusFollowThornsGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.KENTROSAURUS_FOOD), false));
        this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new PrehistoricFollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new SleepingGoal(this));
        this.goalSelector.addGoal(9, new KentrosaurusGrazeGoal(this));
        this.goalSelector.addGoal(9, new KentrosaurusShakeGoal(this));
        this.goalSelector.addGoal(9, new KentrosaurusStretchGoal(this));
        this.goalSelector.addGoal(9, new KentrosaurusYawnGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new KentrosaurusDefendThornsGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.16F)
                .add(Attributes.ARMOR, 4.0F);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.5F;
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && this.horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.4D * this.getBbHeight();
    }

    @Override
    public float getStepHeight() {
        return 1.1F;
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
        return stack.is(UP2ItemTags.KENTROSAURUS_FOOD);
    }

    public boolean entityHasThorns(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getEnchantmentLevel(Enchantments.THORNS) > 0 || entity.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(Enchantments.THORNS) > 0 || entity.getItemBySlot(EquipmentSlot.LEGS).getEnchantmentLevel(Enchantments.THORNS) > 0 || entity.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(Enchantments.THORNS) > 0;
    }

    public static void angerNearbyKentrosaurus(Player player, boolean angerIfSeen) {
        List<Kentrosaurus> list = player.level().getEntitiesOfClass(Kentrosaurus.class, player.getBoundingBox().inflate(16));
        list.stream().filter((kentrosaurus) -> !angerIfSeen || BehaviorUtils.canSee(kentrosaurus, player)).forEach((kentrosaurus) -> kentrosaurus.setTarget(player));
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0, 0, -this.getBbWidth() * 1.1F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (!this.level().isClientSide) {
            if (attackCooldown > 0) attackCooldown--;
            if (!this.isEepy()) {
                if (grazeCooldown > 0) grazeCooldown--;
                if (shakeCooldown > 0) shakeCooldown--;
                if (stretchCooldown > 0) stretchCooldown--;
                if (yawnCooldown > 0) yawnCooldown--;
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.getPose() != UP2Poses.ATTACKING.get() && !this.isInWater() && !this.isEepy(), this.tickCount);
        this.swimAnimationState.animateWhen(this.getPose() != UP2Poses.ATTACKING.get() && this.isInWater(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.angryAnimationState.animateWhen(this.getPose() != UP2Poses.ATTACKING.get() && this.isAggressive(), this.tickCount);
        this.grazeAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == 3, this.tickCount);
        this.stretch1AnimationState.animateWhen(this.getIdleState() == 4 && !stretchAlt, this.tickCount);
        this.stretch2AnimationState.animateWhen(this.getIdleState() == 4 && stretchAlt, this.tickCount);
    }

    protected void grazeCooldown() {
        this.grazeCooldown = 400 + this.getRandom().nextInt(50 * 50);
    }

    protected void shakeCooldown() {
        this.shakeCooldown = 300 + this.getRandom().nextInt(20 * 60);
    }

    protected void yawnCooldown() {
        this.yawnCooldown = 400 + this.getRandom().nextInt(50 * 30);
    }

    protected void stretchCooldown() {
        this.stretchCooldown = 400 + this.getRandom().nextInt(60 * 50);
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource source, float amount) {
        if (!source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !source.is(DamageTypes.THORNS)) {
            Entity entity = source.getDirectEntity();
            if (entity instanceof LivingEntity target) {
                target.hurt(this.damageSources().thorns(this), 2.0F);
            }
        }
        super.actuallyHurt(source, amount);
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(UP2DamageTypeTags.KENTROSAURUS_IMMUNE_TO);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 4;
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

    public static boolean canSpawn(EntityType<Kentrosaurus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.KENTROSAURUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    // Goals
    private static class KentrosaurusDefendThornsGoal extends TargetGoal {

        private final Kentrosaurus kentrosaurus;
        private LivingEntity ownerLastHurtBy;
        private int timestamp;

        @Nullable
        protected LivingEntity target;

        public KentrosaurusDefendThornsGoal(Kentrosaurus kentrosaurus) {
            super(kentrosaurus, false);
            this.kentrosaurus = kentrosaurus;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        @Override
        public boolean canUse() {
            this.findTarget();
            if (this.target == null) {
                return false;
            } else {
                this.ownerLastHurtBy = this.target.getLastHurtByMob();
                int i = this.target.getLastHurtByMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT);
            }
        }

        @Override
        public void start() {
            this.mob.setTarget(this.ownerLastHurtBy);
            LivingEntity livingentity = this.target;
            if (livingentity != null) {
                this.timestamp = livingentity.getLastHurtByMobTimestamp();
            }
            super.start();
        }

        protected void findTarget() {
            this.target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(LivingEntity.class, this.getTargetSearchArea(this.getFollowDistance()), (target) -> true), TargetingConditions.forCombat().range(this.getFollowDistance()).selector(this.kentrosaurus::entityHasThorns), this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        }

        protected AABB getTargetSearchArea(double distance) {
            return this.kentrosaurus.getBoundingBox().inflate(distance, 4.0D, distance);
        }
    }

    private static class KentrosaurusFollowThornsGoal extends Goal {

        private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
        private final TargetingConditions targetingConditions;
        protected final Kentrosaurus kentrosaurus;

        @Nullable
        protected LivingEntity livingEntity;
        private int calmDown;

        public KentrosaurusFollowThornsGoal(Kentrosaurus kentrosaurus) {
            this.kentrosaurus = kentrosaurus;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
        }

        @Override
        public boolean canUse() {
            if (calmDown > 0) {
                this.calmDown--;
                return false;
            } else {
                this.livingEntity = kentrosaurus.level().getNearestPlayer(targetingConditions, kentrosaurus);
                return this.livingEntity != null;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }

        @Override
        public void stop() {
            this.livingEntity = null;
            this.kentrosaurus.getNavigation().stop();
            this.calmDown = reducedTickDelay(100);
        }

        @Override
        public void tick() {
            if (livingEntity != null) {
                this.kentrosaurus.getLookControl().setLookAt(livingEntity, (float) (kentrosaurus.getMaxHeadYRot() + 20), (float) kentrosaurus.getMaxHeadXRot());
                if (this.kentrosaurus.distanceToSqr(livingEntity) < 6.25D) {
                    this.kentrosaurus.getNavigation().stop();
                } else {
                    this.kentrosaurus.getNavigation().moveTo(livingEntity, 1);
                }
            }
        }

        private boolean shouldFollow(LivingEntity entity) {
            return this.kentrosaurus.entityHasThorns(entity);
        }
    }

    private static class KentrosaurusAttackGoal extends AttackGoal {

        private final Kentrosaurus kentrosaurus;

        public KentrosaurusAttackGoal(Kentrosaurus kentrosaurus) {
            super(kentrosaurus);
            this.kentrosaurus = kentrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && kentrosaurus.getHealth() > kentrosaurus.getMaxHealth() * 0.4F;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && kentrosaurus.getHealth() > kentrosaurus.getMaxHealth() * 0.4F;
        }

        @Override
        public void tick() {
            LivingEntity target = this.kentrosaurus.getTarget();
            if (target != null) {
                this.kentrosaurus.lookAt(this.kentrosaurus.getTarget(), 30F, 30F);
                this.kentrosaurus.getLookControl().setLookAt(this.kentrosaurus.getTarget(), 30F, 30F);
                double distance = this.kentrosaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());

                if (kentrosaurus.getAttackState() == 1) {
                    this.tickAttack();
                } else {
                    this.kentrosaurus.getNavigation().moveTo(target, 2.0D);
                    if (distance <= this.getAttackReachSqr(target) && kentrosaurus.attackCooldown == 0) {
                        this.kentrosaurus.setAttackState(1);
                    }
                }
            }
        }

        protected void tickAttack() {
            this.timer++;
            LivingEntity target = this.kentrosaurus.getTarget();
            if (timer == 1) {
                this.kentrosaurus.attackAlt = kentrosaurus.getRandom().nextBoolean();
                this.kentrosaurus.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 19) {
                if (this.isInAttackRange(target, 3.0D)) {
                    this.kentrosaurus.swing(InteractionHand.MAIN_HAND);
                    this.kentrosaurus.doHurtTarget(target);
                }
            }
            if (timer > 40) {
                this.timer = 0;
                this.kentrosaurus.setAttackState(0);
                this.kentrosaurus.attackCooldown = 5;
                this.kentrosaurus.setPose(Pose.STANDING);
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity target) {
            return this.mob.getBbWidth() * 2.3F * this.mob.getBbWidth() * 2.3F + target.getBbWidth();
        }
    }

    private static class KentrosaurusGrazeGoal extends IdleAnimationGoal {

        private final Kentrosaurus kentrosaurus;

        public KentrosaurusGrazeGoal(Kentrosaurus kentrosaurus) {
            super(kentrosaurus, 40, 1, (byte) 67, (byte) 68);
            this.kentrosaurus = kentrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && kentrosaurus.grazeCooldown == 0 && kentrosaurus.level().getBlockState(kentrosaurus.blockPosition().below()).is(UP2BlockTags.KENTROSAURUS_GRAZING_BLOCKS);
        }

        @Override
        public void stop() {
            super.stop();
            this.kentrosaurus.grazeCooldown();
        }
    }

    private static class KentrosaurusShakeGoal extends IdleAnimationGoal {

        private final Kentrosaurus kentrosaurus;

        public KentrosaurusShakeGoal(Kentrosaurus kentrosaurus) {
            super(kentrosaurus, 40, 2, (byte) 69, (byte) 70, false);
            this.kentrosaurus = kentrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && kentrosaurus.shakeCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.kentrosaurus.shakeCooldown();
        }
    }

    private static class KentrosaurusYawnGoal extends IdleAnimationGoal {

        private final Kentrosaurus kentrosaurus;

        public KentrosaurusYawnGoal(Kentrosaurus kentrosaurus) {
            super(kentrosaurus, 80, 3, (byte) 71, (byte) 72, false);
            this.kentrosaurus = kentrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && kentrosaurus.yawnCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.kentrosaurus.yawnCooldown();
        }
    }

    private static class KentrosaurusStretchGoal extends IdleAnimationGoal {

        private final Kentrosaurus kentrosaurus;

        public KentrosaurusStretchGoal(Kentrosaurus kentrosaurus) {
            super(kentrosaurus, 60, 4, (byte) 73, (byte) 74);
            this.kentrosaurus = kentrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && kentrosaurus.stretchCooldown == 0;
        }

        @Override
        public void start() {
            super.start();
            this.kentrosaurus.stretchAlt = kentrosaurus.getRandom().nextBoolean();
        }

        @Override
        public void stop() {
            super.stop();
            this.kentrosaurus.stretchCooldown();
        }
    }
}
