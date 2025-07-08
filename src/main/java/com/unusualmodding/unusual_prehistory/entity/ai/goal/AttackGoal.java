package com.unusualmodding.unusual_prehistory.entity.ai.goal;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class AttackGoal extends Goal {

    protected int attackTime = 0;
    protected final PathfinderMob mob;
    protected Path path;
    protected double pathedTargetX;
    protected double pathedTargetY;
    protected double pathedTargetZ;
    protected int ticksUntilNextPathRecalculation;

    public AttackGoal(PathfinderMob mob) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.mob = mob;
    }

    @Override
    public void start() {
        this.mob.setAggressive(true);
        this.attackTime = 0;
        this.ticksUntilNextPathRecalculation = 0;
    }

    @Override
    public void stop() {
        LivingEntity target = this.mob.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) this.mob.setTarget(null);
        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) return false;
        else {
            this.path = this.mob.getNavigation().createPath(target, 0);
            if (this.path != null) return true;
            else return this.getAttackReachSqr(target) >= this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
        }
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
