package com.unusualmodding.unusual_prehistory.entity.ai.goals.dunkleosteus;

import com.unusualmodding.unusual_prehistory.entity.Dunkleosteus;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class DunkleosteusNearestAttackableTargetGoal extends TargetGoal {

    private final Dunkleosteus dunkleosteus;
    private final int randomInterval;
    @Nullable
    private LivingEntity target;

    public DunkleosteusNearestAttackableTargetGoal(Dunkleosteus dunkleosteus) {
        super(dunkleosteus, true, true);
        this.dunkleosteus = dunkleosteus;
        this.randomInterval = reducedTickDelay(300);
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0 || this.dunkleosteus.isPacified() || this.dunkleosteus.isBaby()) {
            return false;
        } else {
            this.findTarget();
            return this.target != null;
        }
    }

    @Override
    public void start() {
        super.start();
        this.mob.setTarget(this.target);
    }

    protected AABB getTargetSearchArea(double pTargetDistance) {
        return this.mob.getBoundingBox().inflate(pTargetDistance, 6.0F, pTargetDistance);
    }

    protected void findTarget() {
        this.target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(LivingEntity.class, this.getTargetSearchArea(this.getFollowDistance()), (entity) -> true), TargetingConditions.forCombat().range(this.getFollowDistance()).selector(this.dunkleosteus::isTarget), this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }
}