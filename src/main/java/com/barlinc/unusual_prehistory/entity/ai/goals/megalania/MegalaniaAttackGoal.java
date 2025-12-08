package com.barlinc.unusual_prehistory.entity.ai.goals.megalania;

import com.barlinc.unusual_prehistory.entity.Megalania;
import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Objects;

public class MegalaniaAttackGoal extends AttackGoal {

    private final Megalania megalania;

    public MegalaniaAttackGoal(Megalania megalania) {
        super(megalania);
        this.megalania = megalania;
        megalania.getNavigation().setCanFloat(false);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.megalania.getPose() != Pose.ROARING && !this.megalania.isMegalaniaLayingDown();
    }

    @Override
    public void tick() {
        LivingEntity target = this.megalania.getTarget();
        if (target != null) {
            this.megalania.lookAt(this.megalania.getTarget(), 30F, 30F);
            this.megalania.getLookControl().setLookAt(this.megalania.getTarget(), 30F, 30F);

            double distance = this.megalania.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.megalania.getAttackState();

            if (attackState == 1) {
                this.megalania.getNavigation().moveTo(target, this.megalania.isInWater() ? this.getSpeedMultiplier() * 0.5F : this.getSpeedMultiplier() * 0.7F);
                this.tickBite();
            }
            else if (attackState == 2) {
                this.tickTailWhip();
            }
            else {
                this.megalania.getNavigation().moveTo(target, this.megalania.isInWater() ? this.getSpeedMultiplier() * 0.7F : this.getSpeedMultiplier());
                if (distance <= this.getAttackReachSqr(target)) {
                    this.selectAttack();
                }
            }
        }
    }

    private double getSpeedMultiplier() {
        switch (megalania.getTemperatureState()) {
            case COLD -> {
                return 1.4D;
            }
            case WARM -> {
                return 1.9D;
            }
            case NETHER -> {
                return 1.5D;
            }
            default -> {
                return 2.0D;
            }
        }
    }

    private void selectAttack() {
        if (megalania.isInWater()) {
            if (megalania.biteCooldown == 0) this.megalania.setAttackState(1);
        } else {
            if (megalania.getRandom().nextBoolean() && megalania.talWhipCooldown == 0) this.megalania.setAttackState(2);
            else if (megalania.biteCooldown == 0) this.megalania.setAttackState(1);
        }
    }

    private void tickBite() {
        this.timer++;
        LivingEntity target = this.megalania.getTarget();
        if (timer == 1) this.megalania.setPose(UP2Poses.BITING.get());
        if (timer == 5) this.megalania.playSound(UP2SoundEvents.MEGALANIA_BITE.get(), 1.0F, 1.0F);
        if (this.timer == 11) {
            if (this.megalania.distanceTo(Objects.requireNonNull(target)) <= this.getAttackReachSqr(target)) {
                this.megalania.swing(InteractionHand.MAIN_HAND);
                this.megalania.doHurtTarget(target);
                this.megalania.applyPoison(target);
            }
        }
        if (this.timer > 20) {
            this.timer = 0;
            this.megalania.setAttackState(0);
        }
    }

    private void tickTailWhip() {
        this.timer++;
        this.megalania.getNavigation().stop();

        if (timer == 1) this.megalania.setPose(UP2Poses.TAIL_WHIPPING.get());
        if (timer == 9) this.megalania.playSound(UP2SoundEvents.MEGALANIA_TAIL_SWING.get(), 1.0F, 1.0F);
        if (this.timer == 12) this.megalania.addDeltaMovement(this.megalania.getLookAngle().scale(2.0D).multiply(0.25D, 0, 0.25D));
        if (this.timer == 14) {
            this.hurtNearbyEntities();
        }
        if (this.timer > 30) {
            this.timer = 0;
            this.megalania.setAttackState(0);
        }
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = megalania.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), megalania, megalania.getBoundingBox().inflate(2.5, -0.5, 2.5));
        if (!nearbyEntities.isEmpty()) {
            nearbyEntities.stream().filter(entity -> !entity.is(megalania) && !entity.isAlliedTo(megalania)).limit(3).forEach(entity -> {
                entity.hurt(entity.damageSources().mobAttack(megalania), (float) megalania.getAttributeValue(Attributes.ATTACK_DAMAGE));
                megalania.strongKnockback(entity, 1.3D, 0.2D);
                if (entity.isDamageSourceBlocked(megalania.damageSources().mobAttack(megalania)) && entity instanceof Player player) {
                    player.disableShield(true);
                }
                megalania.swing(InteractionHand.MAIN_HAND);
            });
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.75F * this.mob.getBbWidth() * 1.75F + target.getBbWidth();
    }
}
