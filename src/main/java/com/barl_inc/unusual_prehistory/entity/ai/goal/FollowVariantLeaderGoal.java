package com.barl_inc.unusual_prehistory.entity.ai.goal;

import com.barl_inc.unusual_prehistory.entity.base.SchoolingPrehistoricMob;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class FollowVariantLeaderGoal extends Goal {

    public final SchoolingPrehistoricMob mob;
    public int timeToRecalcPath;
    public int nextStartTick;

    public FollowVariantLeaderGoal(SchoolingPrehistoricMob mob) {
        this.mob = mob;
        this.nextStartTick = this.nextStartTick(mob);
    }

    public int nextStartTick(SchoolingPrehistoricMob mob) {
        return reducedTickDelay(200 + mob.getRandom().nextInt(200) % 20);
    }

    @Override
    public boolean canUse() {
        if (this.mob.hasFollowers()) {
            return false;
        } else if (this.mob.isFollower()) {
            return true;
        } else if (this.nextStartTick > 0) {
            this.nextStartTick--;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(this.mob);
            Predicate<SchoolingPrehistoricMob> predicate = (mob) -> mob.canBeFollowed() || !mob.isFollower();
            List<? extends SchoolingPrehistoricMob> list = this.mob.level().getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(10.0D), predicate);
            SchoolingPrehistoricMob schoolingFish = DataFixUtils.orElse(list.stream().filter(SchoolingPrehistoricMob::canBeFollowed).findAny(), this.mob);
            schoolingFish.addFollowers(list.stream().filter((mob) -> !mob.isFollower()));
            return this.mob.isFollower();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.isFollower() && this.mob.inRangeOfLeader();
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.mob.stopFollowing();
    }

    @Override
    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.mob.pathToLeader();
        }
    }
}
