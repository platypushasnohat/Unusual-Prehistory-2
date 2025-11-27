package com.barlinc.unusual_prehistory.entity.ai.goals.kentrosaurus;

import com.barlinc.unusual_prehistory.entity.Kentrosaurus;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class KentrosaurusDefendThornsGoal extends TargetGoal {

    private final Kentrosaurus kentrosaurus;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    @Nullable
    protected LivingEntity target;

    public KentrosaurusDefendThornsGoal(Kentrosaurus kentrosaurus) {
        super(kentrosaurus, false);
        this.kentrosaurus = kentrosaurus;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
         this.findTarget();
         if (this.target == null) {
            return false;
         } else {
            this.ownerLastHurtBy = this.target.getLastHurtByMob();
            int i = this.target.getLastHurtByMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT);
         }
    }

    @Override
    public void start() {
      this.mob.setTarget(this.ownerLastHurtBy);
      LivingEntity livingentity = this.target;
      if (livingentity != null) {
         this.timestamp = livingentity.getLastHurtByMobTimestamp();
      }
      super.start();
    }

    protected void findTarget() {
        this.target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(LivingEntity.class, this.getTargetSearchArea(this.getFollowDistance()), (target) -> true), TargetingConditions.forCombat().range(this.getFollowDistance()).selector(this.kentrosaurus::entityHasThorns), this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }

    protected AABB getTargetSearchArea(double distance) {
        return this.kentrosaurus.getBoundingBox().inflate(distance, 4.0D, distance);
    }
}