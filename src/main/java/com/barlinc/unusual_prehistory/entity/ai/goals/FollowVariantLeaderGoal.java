package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.SchoolingAquaticMob;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class FollowVariantLeaderGoal extends Goal {

    protected final SchoolingAquaticMob mob;
    private int timeToRecalcPath;
    private int nextStartTick;

    public FollowVariantLeaderGoal(SchoolingAquaticMob mob) {
        this.mob = mob;
        this.nextStartTick = this.nextStartTick(mob);
    }

    protected int nextStartTick(SchoolingAquaticMob mob) {
        return reducedTickDelay(200 + mob.getRandom().nextInt(200) % 20);
    }

    @Override
    public boolean canUse() {
        if (mob.hasFollowers()) {
            return false;
        } else if (mob.isFollower()) {
            return true;
        } else if (nextStartTick > 0) {
            this.nextStartTick--;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(mob);
            Predicate<SchoolingAquaticMob> predicate = (fishy) -> fishy.canBeFollowed() || !fishy.isFollower();
            List<? extends SchoolingAquaticMob> list = mob.level().getEntitiesOfClass(mob.getClass(), mob.getBoundingBox().inflate(10.0D, 10.0D, 10.0D), predicate);
            SchoolingAquaticMob schoolingFish = DataFixUtils.orElse(list.stream().filter(SchoolingAquaticMob::canBeFollowed).findAny(), mob);
            schoolingFish.addFollowers(list.stream().filter((fishy2) -> !fishy2.isFollower()));
            return mob.isFollower();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return mob.isFollower() && mob.inRangeOfLeader();
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
        if (this.timeToRecalcPath-- <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.mob.pathToLeader();
        }
    }
}
