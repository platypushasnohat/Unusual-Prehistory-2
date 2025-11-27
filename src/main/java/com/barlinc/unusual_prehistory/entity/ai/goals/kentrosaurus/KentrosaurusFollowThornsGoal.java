package com.barlinc.unusual_prehistory.entity.ai.goals.kentrosaurus;

import com.barlinc.unusual_prehistory.entity.Kentrosaurus;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class KentrosaurusFollowThornsGoal extends Goal {

    private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
    private final TargetingConditions targetingConditions;
    protected final Kentrosaurus kentrosaurus;

    @Nullable
    protected LivingEntity livingEntity;
    private int calmDown;

    public KentrosaurusFollowThornsGoal(Kentrosaurus kentrosaurus) {
        this.kentrosaurus = kentrosaurus;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
    }

    @Override
    public boolean canUse() {
        if (this.calmDown > 0) {
            --this.calmDown;
            return false;
        } else {
            this.livingEntity = this.kentrosaurus.level().getNearestPlayer(this.targetingConditions, this.kentrosaurus);
            return this.livingEntity != null;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void stop() {
        this.livingEntity = null;
        this.kentrosaurus.getNavigation().stop();
        this.calmDown = reducedTickDelay(100);
    }

    @Override
    public void tick() {
        this.kentrosaurus.getLookControl().setLookAt(this.livingEntity, (float) (this.kentrosaurus.getMaxHeadYRot() + 20), (float) this.kentrosaurus.getMaxHeadXRot());
        if (this.kentrosaurus.distanceToSqr(this.livingEntity) < 6.25D) {
            this.kentrosaurus.getNavigation().stop();
        } else {
            this.kentrosaurus.getNavigation().moveTo(this.livingEntity, 1);
        }
    }

    private boolean shouldFollow(LivingEntity entity) {
        return this.kentrosaurus.entityHasThorns(entity);
    }
}