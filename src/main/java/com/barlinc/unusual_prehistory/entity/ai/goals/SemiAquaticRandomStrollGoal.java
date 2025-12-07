package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SemiAquaticRandomStrollGoal extends RandomStrollGoal {

     private final SemiAquaticMob entity;

     public SemiAquaticRandomStrollGoal(SemiAquaticMob entity, double speedModifier) {
         super(entity, speedModifier);
         this.entity = entity;
     }

     @Override
     public boolean canUse() {
         return super.canUse() && this.entity.isLandNavigator && !entity.isInWater();
     }

     @Override
     public boolean canContinueToUse() {
         return super.canContinueToUse() && this.entity.isLandNavigator && !entity.isInWater();
     }

    @Nullable
    @Override
    protected Vec3 getPosition() {
         return this.mob.getRandom().nextFloat() < 0.45F ? LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
    }
}