package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.mob.update_4.Brachiosaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.events.ScreenShakeEvent;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
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
                this.tickStomp();
            }
            else {
                if (distance > this.getAttackReachSqr(target)) {
                    this.brachiosaurus.getNavigation().moveTo(target, 1.5D);
                }
                if (distance <= this.getAttackReachSqr(target) && brachiosaurus.getStompCooldown() <= 0) {
                    brachiosaurus.setAttackState(1);
                }
            }
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
            this.brachiosaurus.setStompCooldown(24 + brachiosaurus.getRandom().nextInt(20));
        }
    }
}