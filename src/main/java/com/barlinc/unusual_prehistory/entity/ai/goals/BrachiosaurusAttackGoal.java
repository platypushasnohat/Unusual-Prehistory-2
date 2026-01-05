package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.Brachiosaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.events.ScreenShakeEvent;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BrachiosaurusAttackGoal extends AttackGoal {

    protected final Brachiosaurus brachiosaurus;

    public BrachiosaurusAttackGoal(Brachiosaurus brachiosaurus) {
        super(brachiosaurus);
        this.brachiosaurus = brachiosaurus;
    }

    @Override
    public void tick() {
        LivingEntity target = brachiosaurus.getTarget();
        if (target != null) {
            double distance = brachiosaurus.distanceToSqr(target);
            this.brachiosaurus.lookAt(target, 30F, 30F);
            this.brachiosaurus.getLookControl().setLookAt(target, 30F, 30F);

            if (this.brachiosaurus.getAttackState() == 1) {
                this.brachiosaurus.getNavigation().stop();
                this.tickKick();
            }
            else if (this.brachiosaurus.getAttackState() == 2) {
                this.brachiosaurus.getNavigation().stop();
                this.tickStomp();
            }
            else {
                this.brachiosaurus.getNavigation().moveTo(target, 1.6D);
                if (distance <= this.getAttackReachSqr(target)) {
                    if (brachiosaurus.attackCooldown == 0 && brachiosaurus.stompCooldown > 0) brachiosaurus.setAttackState(1);
                    else if (brachiosaurus.stompCooldown == 0) brachiosaurus.setAttackState(2);
                }
            }
        }
    }

    protected void tickKick() {
        this.timer++;
        LivingEntity target = brachiosaurus.getTarget();
        if (timer == 1) brachiosaurus.setPose(UP2Poses.ATTACKING.get());
        if (timer == 20) {
            if (this.isInAttackRange(target, 3.0D)) {
                this.brachiosaurus.doHurtTarget(target);
                this.brachiosaurus.swing(InteractionHand.MAIN_HAND);
                this.brachiosaurus.strongKnockback(target, 7.0D, 0.3D);
            }
        }
        if (timer > 40) {
            this.timer = 0;
            this.brachiosaurus.setAttackState(0);
        }
    }

    protected void tickStomp() {
        this.timer++;
        LivingEntity target = brachiosaurus.getTarget();
        if (timer == 7) brachiosaurus.playSound(UP2SoundEvents.BRACHIOSAURUS_STOMP.get(), 2.5F, 1.0F);
        if (timer == 10) brachiosaurus.setPose(UP2Poses.STOMPING.get());
        if (timer == 48) {
            for (LivingEntity entity : brachiosaurus.level().getEntitiesOfClass(LivingEntity.class, brachiosaurus.getBoundingBox().inflate(6.0D, -0.5D, 6.0D))) {
                if (entity == brachiosaurus) {
                    continue;
                }
                entity.hurt(brachiosaurus.damageSources().mobAttack(brachiosaurus), (float) (brachiosaurus.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0F));
                brachiosaurus.strongKnockback(entity, 9.0D, 0.55D);
            }
            UnusualPrehistory2.PROXY.screenShake(new ScreenShakeEvent(brachiosaurus.position(), 40, 4.0F, 24, false));
            this.brachiosaurus.level().broadcastEntityEvent(brachiosaurus, (byte) 40);
        }

        if (timer > 70) {
            this.brachiosaurus.lookAt(target, 30F, 30F);
            this.brachiosaurus.getLookControl().setLookAt(target, 30F, 30F);
        }

        if (this.timer > 80) {
            this.timer = 0;
            this.brachiosaurus.setAttackState(0);
            this.brachiosaurus.stompCooldown = 150 + brachiosaurus.getRandom().nextInt(100);
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.7F * this.mob.getBbWidth() * 1.7F + target.getBbWidth();
    }
}