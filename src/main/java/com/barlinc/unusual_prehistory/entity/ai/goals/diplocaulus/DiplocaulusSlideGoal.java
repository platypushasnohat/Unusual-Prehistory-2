package com.barlinc.unusual_prehistory.entity.ai.goals.diplocaulus;

import com.barlinc.unusual_prehistory.entity.mob.update_1.Diplocaulus;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class DiplocaulusSlideGoal extends Goal {

    protected final Diplocaulus diplocaulus;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;
    protected final double speedModifier;

    public DiplocaulusSlideGoal(Diplocaulus diplocaulus, double speedModifier) {
        this.diplocaulus = diplocaulus;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (diplocaulus.isVehicle() || diplocaulus.isPathFinding() || diplocaulus.isInWaterOrBubble() && !this.isSlideableBlock()) {
            return false;
        } else {
            if (diplocaulus.getNoActionTime() >= 100) return false;
            if (diplocaulus.getRandom().nextInt(reducedTickDelay(120)) != 0) return false;
            Vec3 vec3 = this.getPosition();
            if (vec3 == null) return false;
            else {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                return true;
            }
        }
    }

    protected boolean isSlideableBlock() {
        return diplocaulus.level().getBlockState(diplocaulus.blockPosition().below()).is(UP2BlockTags.DIPLOCAULUS_SLIDING_BLOCKS) || diplocaulus.level().getBlockState(diplocaulus.blockPosition()).is(UP2BlockTags.DIPLOCAULUS_SLIDING_BLOCKS);
    }

    @Override
    public void tick() {
        this.diplocaulus.getLookControl().setLookAt(wantedX, wantedY, wantedZ, 30F, 30F);
    }

    @Nullable
    protected Vec3 getPosition() {
        return DefaultRandomPos.getPos(diplocaulus, 20, 1);
    }

    @Override
    public boolean canContinueToUse() {
        return !diplocaulus.getNavigation().isDone() && !diplocaulus.isVehicle() && !diplocaulus.isInWaterOrBubble() && this.isSlideableBlock();
    }

    @Override
    public void start() {
        this.diplocaulus.setSliding(true);
        this.diplocaulus.getNavigation().moveTo(wantedX, wantedY, wantedZ, speedModifier);
    }

    @Override
    public void stop() {
        this.diplocaulus.setSliding(false);
        this.diplocaulus.getNavigation().stop();
    }
}