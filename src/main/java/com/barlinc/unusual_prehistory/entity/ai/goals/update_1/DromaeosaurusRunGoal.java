package com.barlinc.unusual_prehistory.entity.ai.goals.update_1;

import com.barlinc.unusual_prehistory.entity.mob.update_1.Dromaeosaurus;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class DromaeosaurusRunGoal extends Goal {

    protected final Dromaeosaurus dromaeosaurus;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;

    public DromaeosaurusRunGoal(Dromaeosaurus dromaeosaurus) {
        this.dromaeosaurus = dromaeosaurus;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.dromaeosaurus.isVehicle()) {
            return false;
        } else {
            Vec3 vec3 = this.getPosition();
            if (vec3 == null) {
                return false;
            } else if ((!this.dromaeosaurus.isEepy() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F) && dromaeosaurus.getNavigation().isDone()) {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                return true;
            }
        }
        return false;
    }

    @Nullable
    protected Vec3 getPosition() {
        Vec3 randomPos;
        if (dromaeosaurus.isInWater()) {
            randomPos = LandRandomPos.getPos(dromaeosaurus, 30, 8);
            return randomPos == null ? LandRandomPos.getPos(dromaeosaurus, 10, 7) : randomPos;
        }
        randomPos = dromaeosaurus.getRandom().nextFloat() > 0.001F ? LandRandomPos.getPos(dromaeosaurus, 10, 7) : DefaultRandomPos.getPos(dromaeosaurus, 10, 7);
        return randomPos;
    }

    @Override
    public boolean canContinueToUse() {
        return (this.dromaeosaurus.level().isDay() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F || this.dromaeosaurus.level().dimension() != Level.OVERWORLD) && !this.dromaeosaurus.isVehicle() && !this.dromaeosaurus.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.dromaeosaurus.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, 1.0D);
    }

    @Override
    public void stop() {
        this.dromaeosaurus.setSprinting(false);
        this.dromaeosaurus.getNavigation().stop();
    }
}