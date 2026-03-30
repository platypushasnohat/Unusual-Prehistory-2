package com.barlinc.unusual_prehistory.entity.mob.update_5;

import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.registry.UP2DamageTypes;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Grug extends PrehistoricMob {

    public Grug(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new GrugAttackGoal(this));
        this.goalSelector.addGoal(2, new PrehistoricRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 20.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 450.0D)
                .add(Attributes.ATTACK_DAMAGE, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.98F;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
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
    public float getStepHeight() {
        return 1.0F;
    }

    @Override
    public int getHealCooldown() {
        return 150;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.GRUG_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.GRUG_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.GRUG_DEATH.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 300;
    }

    // goals
    private static class GrugAttackGoal extends AttackGoal {

        private final Grug grug;

        public GrugAttackGoal(Grug grug) {
            super(grug);
            this.grug = grug;
        }

        @Override
        public void tick() {
            LivingEntity target = this.grug.getTarget();
            if (target != null) {
                this.grug.lookAt(target, 30F, 30F);
                this.grug.getLookControl().setLookAt(target, 30F, 30F);
                double distance = this.grug.distanceToSqr(target);
                int attackState = this.grug.getAttackState();
                this.grug.getNavigation().moveTo(target, 2.0D);

                if (attackState == 1) {
                    this.tickAttack();
                } else if (distance < this.getAttackReachSqr(target)) {
                    this.grug.setAttackState(1);
                }
            }
        }

        protected void tickAttack() {
            this.timer++;
            LivingEntity target = grug.getTarget();
            if (timer == 1) {
                if (this.isInAttackRange(target, 2.5D)) {
                    DamageSource damagesource = UP2DamageTypes.grug(grug.level(), grug, grug);
                    target.hurt(damagesource, (float) grug.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    this.grug.strongKnockback(target, 4.0D, 0.5D);
                    this.grug.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 1) {
                this.timer = 0;
                this.grug.setAttackState(0);
            }
        }
    }
}
