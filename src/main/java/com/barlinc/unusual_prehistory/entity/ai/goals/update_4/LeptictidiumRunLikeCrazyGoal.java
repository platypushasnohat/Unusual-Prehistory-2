package com.barlinc.unusual_prehistory.entity.ai.goals.update_4;

import com.barlinc.unusual_prehistory.entity.mob.update_4.Leptictidium;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class LeptictidiumRunLikeCrazyGoal extends Goal {

    protected final Leptictidium leptictidium;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;

    public LeptictidiumRunLikeCrazyGoal(Leptictidium leptictidium) {
        this.leptictidium = leptictidium;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.leptictidium.isVehicle()) {
            return false;
        } else {
            Vec3 vec3 = this.getPosition();
            if (vec3 == null) {
                return false;
            } else if (this.leptictidium.getHealth() <= this.leptictidium.getMaxHealth() * 0.5F) {
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
        return DefaultRandomPos.getPos(this.leptictidium, 15, 7);
    }

    @Override
    public boolean canContinueToUse() {
        return !this.leptictidium.isVehicle() && !this.leptictidium.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.leptictidium.setRunning(true);
        this.leptictidium.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, 1.6D + (0.25F * (1.0F - leptictidium.getHealth() / leptictidium.getMaxHealth())));
    }

    @Override
    public void tick() {
        this.leptictidium.getLookControl().setLookAt(this.wantedX, this.wantedY, this.wantedZ, 30F, 30F);
    }

    @Override
    public void stop() {
        this.leptictidium.setRunning(false);
        this.leptictidium.getNavigation().stop();
    }
}