package com.barlinc.unusual_prehistory.entity.ai.goals.carnotaurus;

import com.barlinc.unusual_prehistory.entity.Carnotaurus;
import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Objects;

public class CarnotaurusAttackGoal extends AttackGoal {

    private final Carnotaurus carnotaurus;

    public CarnotaurusAttackGoal(Carnotaurus carnotaurus) {
        super(carnotaurus);
        this.carnotaurus = carnotaurus;
    }

    @Override
    public void tick() {
        LivingEntity target = this.carnotaurus.getTarget();
        if (target != null) {
            this.carnotaurus.lookAt(this.carnotaurus.getTarget(), 30F, 30F);
            this.carnotaurus.getLookControl().setLookAt(this.carnotaurus.getTarget(), 30F, 30F);

            double distance = this.carnotaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.carnotaurus.getAttackState();

            if (attackState == 1) {
                this.tickBite();
                this.carnotaurus.getNavigation().moveTo(target, 1.8D);
            } else if (attackState == 2) {
                this.tickHeadbutt();
            } else {
                this.carnotaurus.getNavigation().moveTo(target, 2.2D);
                if (distance <= 10) {
                    if (this.carnotaurus.getRandom().nextBoolean() && !(target instanceof Creeper)) {
                        this.carnotaurus.setAttackState(1);
                    } else {
                        this.carnotaurus.setAttackState(2);
                    }
                }
            }
        }
    }

    protected void tickBite() {
        this.timer++;
        LivingEntity target = this.carnotaurus.getTarget();

        if (this.timer == 1) this.carnotaurus.setPose(UP2Poses.BITING.get());
        if (this.timer == 9) this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_BITE.get(), 1.0F, carnotaurus.getVoicePitch());

        if (this.timer == 11) {
            if (this.carnotaurus.distanceTo(Objects.requireNonNull(target)) <= this.getAttackReachSqr(target)) {
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
                this.carnotaurus.doHurtTarget(target);
            }
        }
        if (this.timer > 17) {
            this.timer = 0;
            this.carnotaurus.setAttackState(0);
        }
    }

    protected void tickHeadbutt() {
        this.timer++;
        this.carnotaurus.getNavigation().stop();

        if (this.timer == 1) this.carnotaurus.setPose(UP2Poses.HEADBUTTING.get());
        if (this.timer == 9) {
            this.carnotaurus.addDeltaMovement(this.carnotaurus.getLookAngle().scale(2.0D).multiply(0.25D, 0, 0.25D));
        }
        if (this.timer == 12) {
            this.hurtNearbyEntities();
        }
        if (this.timer > 23) {
            this.timer = 0;
            this.carnotaurus.setAttackState(0);
        }
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = carnotaurus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), carnotaurus, carnotaurus.getBoundingBox().inflate(2.0));
        if (!nearbyEntities.isEmpty()) {
            nearbyEntities.stream().filter(entity -> !(entity instanceof Carnotaurus) && !(entity instanceof Creeper)).limit(3).forEach(entity -> {
                if (entity.hurt(entity.damageSources().mobAttack(this.carnotaurus), (float) this.carnotaurus.getAttributeValue(Attributes.ATTACK_DAMAGE))) {
                    this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_HEADBUTT.get(), 1.0F, carnotaurus.getVoicePitch());
                }
                this.carnotaurus.strongKnockback(entity, 1.5D, 0.5D);
                if (entity.isDamageSourceBlocked(carnotaurus.damageSources().mobAttack(carnotaurus)) && entity instanceof Player player) {
                    player.disableShield(true);
                }
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
            });
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.35F * this.mob.getBbWidth() * 1.35F + target.getBbWidth();
    }
}
