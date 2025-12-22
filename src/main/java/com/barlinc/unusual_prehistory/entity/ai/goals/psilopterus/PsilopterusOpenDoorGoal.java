package com.barlinc.unusual_prehistory.entity.ai.goals.psilopterus;

import com.barlinc.unusual_prehistory.entity.Psilopterus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.DoorInteractGoal;
import net.minecraft.world.phys.Vec3;

public class PsilopterusOpenDoorGoal extends DoorInteractGoal {

    private final Psilopterus psilopterus;
    private int timer;

    public PsilopterusOpenDoorGoal(Psilopterus psilopterus) {
        super(psilopterus);
        this.psilopterus = psilopterus;
    }

    @Override
    public void start() {
        this.timer = 0;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.isOpen() && psilopterus.getPose() == Pose.STANDING;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.isOpen();
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 vec3 = Vec3.atCenterOf(doorPos);
        if (!this.isOpen() && psilopterus.distanceToSqr(vec3) < 4) {
            this.psilopterus.lookAt(EntityAnchorArgument.Anchor.EYES, vec3);
            if (psilopterus.getPose() == Pose.STANDING) {
                this.psilopterus.setPose(UP2Poses.POKING.get());
            }
        }
        if (psilopterus.getPose() == UP2Poses.POKING.get()) {
            this.timer++;
            if (timer == 10) {
                this.setOpen(true);
                this.timer = 0;
            }
        }
    }
}