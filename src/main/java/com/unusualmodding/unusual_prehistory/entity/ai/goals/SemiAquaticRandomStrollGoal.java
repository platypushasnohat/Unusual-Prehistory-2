package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.base.SemiAquaticPrehistoricMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class SemiAquaticRandomStrollGoal extends RandomStrollGoal {

     private final SemiAquaticPrehistoricMob entity;

     public SemiAquaticRandomStrollGoal(SemiAquaticPrehistoricMob entity, double speedModifier) {
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
}