package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Carnotaurus;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class CarnotaurusAttackGoal extends AttackGoal {

    private int timer = 0;
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
                tickBite();
                this.carnotaurus.getNavigation().moveTo(target, 1.75D);
            } else if (attackState == 2) {
                tickHeadbutt();
            } else {
                this.carnotaurus.getNavigation().moveTo(target, 2.0D);
                if (distance <= 10) {
                    if (this.carnotaurus.getRandom().nextBoolean()) {
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

        if (this.timer == 9) this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_BITE.get(), 1.0F, carnotaurus.getVoicePitch());

        if (this.timer == 11) {
            if (this.carnotaurus.distanceTo(Objects.requireNonNull(target)) <= this.getAttackReachSqr(target)) {
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
                this.carnotaurus.doHurtTarget(target);
            }
        }
        if (this.timer > 22) {
            this.timer = 0;
            this.carnotaurus.setAttackState(0);
        }
    }

    protected void tickHeadbutt() {
        this.timer++;
        LivingEntity target = this.carnotaurus.getTarget();
        this.carnotaurus.getNavigation().stop();

        if (this.timer == 9) {
            this.carnotaurus.addDeltaMovement(this.carnotaurus.getLookAngle().scale(2.0D).multiply(0.25D, 0, 0.25D));
        }

        if (this.timer == 12) {
            if (this.carnotaurus.distanceTo(Objects.requireNonNull(target)) <= this.getAttackReachSqr(target)) {
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
                if (this.carnotaurus.doHurtTarget(target)) {
                    this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_HEADBUTT.get(), 1.0F, carnotaurus.getVoicePitch());
                }
                this.carnotaurus.strongKnockback(target, 1.5D, 0.5D);
                if (target.isDamageSourceBlocked(this.carnotaurus.damageSources().mobAttack(this.carnotaurus)) && target instanceof Player player){
                    player.disableShield(true);
                }
            }
        }
        if (this.timer > 28) {
            this.timer = 0;
            this.carnotaurus.setAttackState(0);
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.35F * this.mob.getBbWidth() * 1.35F + target.getBbWidth();
    }
}
