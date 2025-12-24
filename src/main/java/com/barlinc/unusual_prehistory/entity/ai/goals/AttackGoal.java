package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.GrabbingMob;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class AttackGoal extends Goal {

    protected int timer = 0;
    protected final PrehistoricMob mob;

    public AttackGoal(PrehistoricMob mob) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.mob = mob;
    }

    @Override
    public void start() {
        this.mob.setAttackState(0);
        this.mob.setAggressive(true);
        this.mob.setRunning(true);
        this.timer = 0;
        if (mob instanceof GrabbingMob grabbingMob) {
            grabbingMob.setHeldMobId(-1);
        }
    }

    @Override
    public void stop() {
        LivingEntity target = this.mob.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) this.mob.setTarget(null);
        this.mob.setAttackState(0);
        this.mob.setAggressive(false);
        this.mob.setRunning(false);
        this.mob.getNavigation().stop();
        if (mob instanceof GrabbingMob grabbingMob) {
            grabbingMob.setHeldMobId(-1);
        }
    }

    @Override
    public boolean canUse() {
        return !this.mob.isBaby() && this.mob.getTarget() != null && this.mob.getTarget().isAlive() && !this.mob.isVehicle() && !this.mob.isMobSitting();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) return false;
        else if (!target.isAlive()) return false;
        else if (!this.mob.isWithinRestriction(target.blockPosition())) return false;
        else return !(target instanceof Player) || !target.isSpectator() && !((Player) target).isCreative() || !this.mob.getNavigation().isDone();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + target.getBbWidth();
    }
}
