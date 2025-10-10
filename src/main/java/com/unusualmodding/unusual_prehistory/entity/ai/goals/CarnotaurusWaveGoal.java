package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Carnotaurus;
import com.unusualmodding.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class CarnotaurusWaveGoal extends Goal {

   private static final TargetingConditions FRIEND_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();

   protected final Carnotaurus carnotaurus;
   protected final Level level;
   @Nullable
   protected Carnotaurus friend;
   private int timer;

   public CarnotaurusWaveGoal(Carnotaurus carnotaurus) {
      this.carnotaurus = carnotaurus;
      this.level = carnotaurus.level();
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   @Override
   public boolean canUse() {
      if (carnotaurus.waveCooldown != 0 && carnotaurus.getPose() != Pose.STANDING && carnotaurus.isInWater()) {
         return false;
      } else {
         this.friend = this.getFriend();
         return this.friend != null;
      }
   }

   @Override
   public boolean canContinueToUse() {
      return this.friend.isAlive() && this.timer < 60;
   }

   @Override
   public void stop() {
      this.friend = null;
      this.timer = 0;
      this.carnotaurus.setPose(Pose.STANDING);
      this.carnotaurus.waveCooldown = carnotaurus.getRandom().nextInt(10 * 40) + (20 * 40);
   }

   @Override
   public void tick() {
      if (this.friend != null) {
         this.carnotaurus.getLookControl().setLookAt(this.friend, 10.0F, (float) this.carnotaurus.getMaxHeadXRot());
         if (this.carnotaurus.distanceToSqr(this.friend) > 9.0D) {
            this.carnotaurus.getNavigation().moveTo(this.friend, 1.0D);
         }
         this.timer++;
         if (this.timer >= this.adjustedTickDelay(60) && this.carnotaurus.distanceToSqr(this.friend) < 9.0D) {
            this.carnotaurus.setPose(UP2Poses.WAVING.get());
            this.friend.setPose(UP2Poses.WAVING.get());
         }
      }
   }

   @Nullable
   private Carnotaurus getFriend() {
      List<? extends Carnotaurus> list = this.level.getNearbyEntities(Carnotaurus.class, FRIEND_TARGETING, this.carnotaurus, this.carnotaurus.getBoundingBox().inflate(8.0D));
      double d0 = Double.MAX_VALUE;
      Carnotaurus carnotaurus = null;
      for (Carnotaurus carnotaurus1 : list) {
         if (this.carnotaurus.canMate(carnotaurus1) && this.carnotaurus.distanceToSqr(carnotaurus1) < d0) {
            carnotaurus = carnotaurus1;
            d0 = this.carnotaurus.distanceToSqr(carnotaurus1);
         }
      }
      return carnotaurus;
   }
}