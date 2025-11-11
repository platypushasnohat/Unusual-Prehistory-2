package com.unusualmodding.unusual_prehistory.entity.ai.navigation;

import com.unusualmodding.unusual_prehistory.entity.Dromaeosaurus;
import net.minecraft.world.entity.ai.control.MoveControl;

public class DromaeosaurusMoveControl extends PrehistoricMobMoveControl {

        private final Dromaeosaurus dromaeosaurus;

        public DromaeosaurusMoveControl(Dromaeosaurus dromaeosaurus) {
            super(dromaeosaurus);
            this.dromaeosaurus = dromaeosaurus;
        }

        @Override
        public void tick() {
            if (!this.dromaeosaurus.refuseToMove()) {
                if (this.operation == MoveControl.Operation.MOVE_TO && !this.dromaeosaurus.isLeashed() && this.dromaeosaurus.isDromaeosaurusEeping() && !this.dromaeosaurus.isInPoseTransition()) {
                    this.dromaeosaurus.wakeUp();
                }
                super.tick();
            }
        }
    }