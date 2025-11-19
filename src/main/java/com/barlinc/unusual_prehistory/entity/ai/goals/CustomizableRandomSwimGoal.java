package com.barlinc.unusual_prehistory.entity.ai.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class CustomizableRandomSwimGoal extends RandomStrollGoal {

    private final PathfinderMob entity;
    protected Vec3 wantedPos;

    private final int radius;
    private final int height;
    private final int prox;

    public CustomizableRandomSwimGoal(PathfinderMob entity, double speedMultiplier, int interval, int radius, int height, int proximity) {
        super(entity, speedMultiplier, interval);
        this.entity = entity;
        this.radius = radius;
        this.height = height;
        this.prox = proximity;
    }

    @Override
    public boolean canUse() {
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        this.wantedPos = new Vec3(this.wantedX, this.wantedY, this.wantedZ);
        return super.canContinueToUse() && !(this.wantedPos.distanceTo(this.entity.position()) <= this.entity.getBbWidth() * prox);
    }

    @Nullable
    protected Vec3 getPosition() {
        return BehaviorUtils.getRandomSwimmablePos(this.mob, radius, height);
    }
}