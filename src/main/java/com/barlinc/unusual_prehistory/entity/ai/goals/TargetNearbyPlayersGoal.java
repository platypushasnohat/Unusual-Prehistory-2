package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class TargetNearbyPlayersGoal extends NearestAttackableTargetGoal<Player> {

    protected final PrehistoricMob prehistoricMob;
    protected final double distance;

    public TargetNearbyPlayersGoal(PrehistoricMob prehistoricMob) {
        this(prehistoricMob, 100, 2.0D);
    }

    public TargetNearbyPlayersGoal(PrehistoricMob prehistoricMob, int interval, double distance) {
        super(prehistoricMob, Player.class, interval, true, true, null);
        this.prehistoricMob = prehistoricMob;
        this.distance = distance;
    }

    @Override
    public boolean canUse() {
        if (prehistoricMob.isBaby()) {
            return false;
        } else {
            return super.canUse();
        }
    }

    @Override
    protected double getFollowDistance() {
        return this.distance;
    }
}