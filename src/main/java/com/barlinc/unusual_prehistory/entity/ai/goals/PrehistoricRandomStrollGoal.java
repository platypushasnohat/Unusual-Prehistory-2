package com.barlinc.unusual_prehistory.entity.ai.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class PrehistoricRandomStrollGoal extends RandomStrollGoal {

    protected final boolean shouldAvoidWater;

    public PrehistoricRandomStrollGoal(PathfinderMob mob, double speedModifier) {
        this(mob, speedModifier, 120, true);
    }

    public PrehistoricRandomStrollGoal(PathfinderMob mob, double speedModifier, boolean shouldAvoidWater) {
        this(mob, speedModifier, 120, shouldAvoidWater);
    }

    public PrehistoricRandomStrollGoal(PathfinderMob mob, double speedModifier, int interval, boolean shouldAvoidWater) {
        super(mob, speedModifier, interval, true);
        this.shouldAvoidWater = shouldAvoidWater;
    }

    @Override
    public void tick() {
        this.mob.getLookControl().setLookAt(this.wantedX, this.wantedY, this.wantedZ, 30F, 30F);
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        if (this.shouldAvoidWater) {
            Vec3 randomPos;
            if (mob.isInWater()) {
                randomPos = LandRandomPos.getPos(mob, 30, 8);
                return randomPos == null ? LandRandomPos.getPos(mob, 10, 7) : randomPos;
            }
            randomPos = mob.getRandom().nextFloat() > 0.001F ? LandRandomPos.getPos(mob, 10, 7) : DefaultRandomPos.getPos(mob, 10, 7);
            return randomPos;
        } else {
            return DefaultRandomPos.getPos(this.mob, 10, 7);
        }
    }
}
