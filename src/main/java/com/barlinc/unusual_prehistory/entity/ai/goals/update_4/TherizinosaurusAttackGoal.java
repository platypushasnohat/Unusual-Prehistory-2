package com.barlinc.unusual_prehistory.entity.ai.goals.update_4;

import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.mob.future.Therizinosaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class TherizinosaurusAttackGoal extends AttackGoal {

    private final Therizinosaurus therizinosaurus;

    public TherizinosaurusAttackGoal(Therizinosaurus therizinosaurus) {
        super(therizinosaurus);
        this.therizinosaurus = therizinosaurus;
    }

    @Override
    public void tick() {
        LivingEntity target = therizinosaurus.getTarget();
        if (target != null) {
            this.therizinosaurus.lookAt(therizinosaurus.getTarget(), 30F, 30F);
            this.therizinosaurus.getLookControl().setLookAt(therizinosaurus.getTarget(), 30F, 30F);

            double distance = therizinosaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = therizinosaurus.getAttackState();

            if (attackState == 1) {
                this.tickSlash();
                this.therizinosaurus.getNavigation().stop();
            } else {
                if (distance > 12) therizinosaurus.getNavigation().moveTo(target, 1.7D);
                if (distance < 14 && therizinosaurus.slashCooldown == 0) {
                    this.therizinosaurus.setAttackState(1);
                }
            }
        }
    }

    protected void tickSlash() {
        this.timer++;
        LivingEntity target = therizinosaurus.getTarget();
        if (timer == 10) {
            this.therizinosaurus.setPose(UP2Poses.ATTACKING.get());
        }
        if (timer == 17) therizinosaurus.playSound(UP2SoundEvents.THERIZINOSAURUS_ATTACK.get(), 1.0F, 0.9F + therizinosaurus.getRandom().nextFloat() * 0.3F);
        if (timer == 24) {
            if (this.isInAttackRange(target, 3.0D)) {
                this.therizinosaurus.swing(InteractionHand.MAIN_HAND);
                this.therizinosaurus.doHurtTarget(target);
                this.therizinosaurus.strongKnockback(target, 0.5D, 0.0D);
            }
        }
        if (timer > 30) {
            this.timer = 0;
            this.therizinosaurus.slashCooldown = 5 + therizinosaurus.getRandom().nextInt(6);
            this.therizinosaurus.setAttackState(0);
        }
    }
}
