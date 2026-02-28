package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.update_4.Dimorphodon;
import net.minecraft.world.entity.LivingEntity;

public class DimorphodonGrabGoal extends AttackGoal {

    private final Dimorphodon dimorphodon;

    public DimorphodonGrabGoal(Dimorphodon dimorphodon) {
        super(dimorphodon);
        this.dimorphodon = dimorphodon;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && dimorphodon.grabCooldown == 0 && dimorphodon.canPickUpTarget(dimorphodon.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && dimorphodon.grabCooldown == 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.dimorphodon.setTarget(null);
        this.dimorphodon.grabCooldown = 100 + dimorphodon.getRandom().nextInt(100);
        if (!dimorphodon.onGround()) {
            this.dimorphodon.setFlying(true);
        }
    }

    @Override
    public void tick() {
        LivingEntity target = dimorphodon.getTarget();
        if (target != null) {
            double distance = dimorphodon.distanceToSqr(target);
            if (dimorphodon.getAttackState() == 1) this.tickGrab();
            else if (distance <= this.getAttackReachSqr(target) && dimorphodon.canPickUpTarget(target) && dimorphodon.grabCooldown == 0) {
                this.dimorphodon.setAttackState(1);
            } else {
                this.dimorphodon.getLookControl().setLookAt(target, 30F, 30F);
                this.dimorphodon.getNavigation().moveTo(target, 2.0D);
            }
        }
    }

    protected void tickGrab() {
        this.timer++;
        this.dimorphodon.getNavigation().stop();
        LivingEntity target = dimorphodon.getTarget();
        if (timer == 1) {
            if (this.isInAttackRange(target, 1.5D)) {
                this.dimorphodon.setHeldMobId(target.getId());
            }
        }
        if (timer > 1 && dimorphodon.getHeldMobId() != -1) {
            this.dimorphodon.setFlying(true);
            this.dimorphodon.setDeltaMovement(dimorphodon.getDeltaMovement().x, 0.18D, dimorphodon.getDeltaMovement().z);
        }
        if (timer > 80 || (timer > 2 && dimorphodon.getHeldMobId() == -1)) {
            this.stop();
        }
    }
}
