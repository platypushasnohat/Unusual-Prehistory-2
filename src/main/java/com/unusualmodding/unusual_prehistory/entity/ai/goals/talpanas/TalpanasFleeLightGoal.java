package com.unusualmodding.unusual_prehistory.entity.ai.goals.talpanas;

import com.unusualmodding.unusual_prehistory.entity.Talpanas;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class TalpanasFleeLightGoal extends Goal {

        private final Talpanas talpanas;

        public TalpanasFleeLightGoal(Talpanas talpanas) {
            this.talpanas = talpanas;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return !this.talpanas.isVehicle() && (this.talpanas.level().getBrightness(LightLayer.BLOCK, this.talpanas.blockPosition()) > this.talpanas.getLightThreshold()) && this.talpanas.fleeFromPosition != null;
        }

        public void stop() {
            this.talpanas.fleeFromPosition = null;
            this.talpanas.fleeLightFor = 40;
        }

        public void tick() {
            this.talpanas.fleeLightFor = 40;
            if (this.talpanas.getNavigation().isDone()) {
                Vec3 vec3 = LandRandomPos.getPosAway(this.talpanas, 10, 7, this.talpanas.fleeFromPosition);
                if (vec3 != null) {
                    this.talpanas.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.25F);
                }
            }
        }
    }