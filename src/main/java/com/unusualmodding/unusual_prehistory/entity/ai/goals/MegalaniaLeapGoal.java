package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Megalania;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class MegalaniaLeapGoal extends Goal {

   private final Megalania megalania;

   public MegalaniaLeapGoal(Megalania megalania) {
      this.megalania = megalania;
      this.setFlags(EnumSet.of(Flag.JUMP, Flag.LOOK));
   }

   @Override
   public boolean canUse() {
      return !megalania.isInWaterOrBubble() && megalania.getTarget() != null && megalania.distanceToSqr(megalania.getTarget()) > 100 && megalania.onGround() && megalania.getPose() == Pose.STANDING && !this.megalania.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) && this.megalania.getLeapCooldown() <= 0;
   }

   @Override
   public void start() {
      if (megalania.getTarget() != null) {
         megalania.setPose(Pose.LONG_JUMPING);
         Vec3 direction = new Vec3(megalania.getTarget().getX() - megalania.getX(), megalania.getTarget().getY() - megalania.getY(), megalania.getTarget().getZ() - megalania.getZ()).normalize().scale(3.0D);
         megalania.setDeltaMovement(new Vec3(direction.x, 0.55D, direction.z));
         megalania.getNavigation().stop();
         megalania.leapCooldown();
      }
   }

   @Override
   public void tick() {
      if (megalania.getTarget() != null) {
         megalania.getLookControl().setLookAt(megalania.getTarget(), 30F, 30F);
      }
   }

   @Override
   public boolean canContinueToUse() {
      return !megalania.onGround();
   }
}