package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Talpanas;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class TalpanasSeekShelterGoal extends Goal {

    protected final Talpanas talpanas;
    private double wantedX;
    private double wantedY;
    private double wantedZ;

    public TalpanasSeekShelterGoal(Talpanas talpanas) {
        this.talpanas = talpanas;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (talpanas.level().getBrightness(LightLayer.BLOCK, talpanas.blockPosition()) > talpanas.getLightThreshold() || (talpanas.level().isDay() && talpanas.level().canSeeSky(this.talpanas.blockPosition()))) {
            return this.setWantedPos();
        } else {
            return false;
        }
    }

    protected boolean setWantedPos() {
        Vec3 vec3 = this.getHidePos();
        if (vec3 == null) {
            return false;
        } else {
            this.wantedX = vec3.x;
            this.wantedY = vec3.y;
            this.wantedZ = vec3.z;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.talpanas.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.talpanas.getNavigation().moveTo(wantedX, wantedY, wantedZ, 1.2D);
    }

    @Nullable
    protected Vec3 getHidePos() {
        RandomSource randomsource = talpanas.getRandom();
        BlockPos blockpos = talpanas.blockPosition();
        for (int i = 0; i < 10; i++) {
            BlockPos blockpos1 = blockpos.offset(randomsource.nextInt(20) - 10, randomsource.nextInt(6) - 3, randomsource.nextInt(20) - 10);
            if ((talpanas.level().getBrightness(LightLayer.BLOCK, blockpos1) <= talpanas.getLightThreshold() || !talpanas.level().canSeeSky(blockpos1)) && talpanas.getWalkTargetValue(blockpos1) > 0.0F) {
                return Vec3.atBottomCenterOf(blockpos1);
            }
        }
        return null;
    }
}