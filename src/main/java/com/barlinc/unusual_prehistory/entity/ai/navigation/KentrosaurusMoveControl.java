package com.barlinc.unusual_prehistory.entity.ai.navigation;

import com.barlinc.unusual_prehistory.entity.Kentrosaurus;
import net.minecraft.world.entity.ai.control.MoveControl;

public class KentrosaurusMoveControl extends PrehistoricMobMoveControl {

    protected final Kentrosaurus kentrosaurus;

    public KentrosaurusMoveControl(Kentrosaurus kentrosaurus) {
        super(kentrosaurus);
        this.kentrosaurus = kentrosaurus;
    }

    @Override
    public void tick() {
        if (!this.kentrosaurus.refuseToMove()) {
            if (this.operation == MoveControl.Operation.MOVE_TO && !this.kentrosaurus.isLeashed() && this.kentrosaurus.isKentrosaurusLayingDown() && !this.kentrosaurus.isInPoseTransition()) {
                this.kentrosaurus.standUp();
            }
            super.tick();
        }
    }
}
