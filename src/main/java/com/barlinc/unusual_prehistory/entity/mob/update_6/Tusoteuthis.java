package com.barlinc.unusual_prehistory.entity.mob.update_6;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.CustomizableRandomSwimGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.MobUtils;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class Tusoteuthis extends PrehistoricAquaticMob {

    private int attackCooldown = 0;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();

    public float spin;
    public float prevSpin;
    public float spinSpeed;
    public float prevSpinSpeed;

    public Tusoteuthis(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6F)
                .add(Attributes.ATTACK_DAMAGE, 7.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 2.0D, 16, 8));
        this.goalSelector.addGoal(2, new TusoteuthisAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.LORRAINOSAURUS_FOOD), false));
        this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 100, 7, 15));
        this.targetSelector.addGoal(1, new TusoteuthisTargetUnderneathGoal<>(this, LivingEntity.class, this::canTargetEntitiesUnderneath));
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean isPushable() {
        return this.isBaby();
    }

    @Override
    public boolean shouldFlop() {
        return false;
    }

    private boolean canTargetEntitiesUnderneath(LivingEntity target) {
        if (target instanceof Tusoteuthis) {
            return false;
        }
        if (!this.isInWaterOrBubble()) {
            return false;
        }
        return (target.getBbWidth() < this.getBbWidth() * 0.25F && target.getBbHeight() < this.getBbHeight() * 0.25F) && target.getY() < this.getY();
    }

    public float getSpinProgress(float partialTicks) {
        return prevSpin + (spin - prevSpin) * partialTicks;
    }

    @Override
    public void tick() {
        super.tick();

        this.prevSpin = spin;
        this.prevSpinSpeed = spinSpeed;

        if (this.getPose() == UP2Poses.ATTACKING.get()) {
            this.spinSpeed += 2.0F;
            if (spinSpeed > 45.0F) {
                this.spinSpeed = 45.0F;
            }
        } else {
            this.spinSpeed *= 0.9F;
            if (spinSpeed < 0.1F) {
                this.spinSpeed = 0.0F;
            }
        }

        this.spin += spinSpeed;

        if (spinSpeed == 0.0F) {
            float spinDegrees = spin % 360.0F;
            if (spinDegrees > 180.0F) {
                spinDegrees -= 360.0F;
            } else if (spinDegrees < -180.0F) {
                spinDegrees += 360.0F;
            }
            this.spin -= spinDegrees * 0.15F;
            if (Math.abs(spinDegrees) < 0.25F) {
                this.spin = 0.0F;
                this.prevSpin = 0.0F;
                this.spinSpeed = 0.0F;
                this.prevSpinSpeed = 0.0F;
            }
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackCooldown > 0) {
            this.attackCooldown--;
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && this.getPose() != UP2Poses.ATTACKING.get(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 700 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 2) {
            return 800 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    public boolean canPlayIdles(Entity entity) {
        return entity.isInWaterOrBubble();
    }

    private Vec3 getMouthVec(LivingEntity target) {
        return new Vec3(this.getX(), this.getBoundingBox().minY - target.getBbHeight(), this.getZ());
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 67) {
            Vec3 vec3 = new Vec3(this.getX(), this.getBoundingBox().minY - 0.5F, this.getZ());
            double depth = this.getRandom().nextDouble() * 6.0D;
            this.level().addAlwaysVisibleParticle(ParticleTypes.BUBBLE, vec3.x + (this.getRandom().nextDouble() - 0.5D), vec3.y - depth, vec3.z + (this.getRandom().nextDouble() - 0.5D), 0.0D, 0.15D, 0.0D);
        }
        super.handleEntityEvent(id);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(2);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.LORRAINOSAURUS_FOOD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.TUSOTEUTHIS.get().create(level);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.LORRAINOSAURUS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.LORRAINOSAURUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.LORRAINOSAURUS_DEATH.get();
    }

    private static class TusoteuthisAttackGoal extends AttackGoal {

        private final Tusoteuthis tusoteuthis;

        public TusoteuthisAttackGoal(Tusoteuthis tusoteuthis) {
            super(tusoteuthis);
            this.tusoteuthis = tusoteuthis;
        }

        @Override
        public void tick() {
            LivingEntity target = tusoteuthis.getTarget();
            if (target != null) {

                double dx = tusoteuthis.getX() - target.getX();
                double dz = tusoteuthis.getZ() - target.getZ();
                double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
                double verticalDistance = tusoteuthis.getY() - target.getY();
                int attackState = tusoteuthis.getAttackState();

                if (attackState == 1) {
                    this.tickAttack(target, verticalDistance, horizontalDistance);
                    this.tusoteuthis.getNavigation().stop();
                }
                else {
                    this.tusoteuthis.getNavigation().moveTo(target.getX(), target.getY() + 5.0D, target.getZ(), 1.5D);
                    if (horizontalDistance < 4.0D && verticalDistance > 4.0D && verticalDistance < 8.0D && tusoteuthis.attackCooldown == 0) {
                        this.tusoteuthis.setAttackState(1);
                    }
                }
            }
        }

        protected void tickAttack(LivingEntity target, double verticalDistance, double horizontalDistance) {
            this.timer++;
            if (timer == 1) {
                this.tusoteuthis.setPose(UP2Poses.ATTACKING.get());
                this.tusoteuthis.playSound(SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, 1.0F, 0.9F + tusoteuthis.getRandom().nextFloat() * 0.2F);
            }
            if (timer < 100 && tusoteuthis.hasLineOfSight(target) && verticalDistance <= 10.0D && horizontalDistance < 5.0D) {
                Vec3 offset = tusoteuthis.getMouthVec(target).subtract(target.position());
                Vec3 velocity = offset.scale(0.05D);
                target.setDeltaMovement(target.getDeltaMovement().scale(0.5D).add(velocity));
                if (target instanceof PathfinderMob pathfinderMob) {
                    if (pathfinderMob.getNavigation().getPath() != null) {
                        pathfinderMob.getNavigation().stop();
                    }
                }
                target.hurtMarked = true;
                if (timer > 20 && timer % 20 == 0 && this.isInAttackRange(target, 0.25D) ) {
                    this.tusoteuthis.doHurtTarget(target);
                }
                if (timer % 2 == 0) {
                    this.tusoteuthis.level().broadcastEntityEvent(tusoteuthis, (byte) 67);
                }
                if (timer % 60 == 0) {
                    this.tusoteuthis.playSound(SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, 1.0F, 0.9F + tusoteuthis.getRandom().nextFloat() * 0.2F);
                }
            }
            if (timer > 100) {
                this.timer = 0;
                this.tusoteuthis.attackCooldown = 100 + tusoteuthis.getRandom().nextInt(50);
                this.tusoteuthis.setPose(Pose.STANDING);
                this.tusoteuthis.setAttackState(0);
            }
        }
    }

    private static class TusoteuthisTargetUnderneathGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

        private final Tusoteuthis tusoteuthis;

        public TusoteuthisTargetUnderneathGoal(Tusoteuthis tusoteuthis, Class<T> targetClass, Predicate<LivingEntity> targetPredicate) {
            super(tusoteuthis, targetClass, 100, true, true, targetPredicate);
            this.tusoteuthis = tusoteuthis;
        }

        @Override
        protected @NotNull AABB getTargetSearchArea(double distance) {
            double radius = 4.0D;
            return new AABB(tusoteuthis.getX() - radius, tusoteuthis.getY() - 32.0D, tusoteuthis.getZ() - radius, tusoteuthis.getX() + radius, tusoteuthis.getY(), tusoteuthis.getZ() + radius);
        }
    }
}
