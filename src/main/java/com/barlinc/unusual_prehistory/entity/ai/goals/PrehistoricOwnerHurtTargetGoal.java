package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.TameablePrehistoricMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class PrehistoricOwnerHurtTargetGoal extends TargetGoal {

   private final TameablePrehistoricMob tamedMob;
   private LivingEntity ownerLastHurt;
   private int timestamp;

   public PrehistoricOwnerHurtTargetGoal(TameablePrehistoricMob tamedMob) {
      super(tamedMob, false);
      this.tamedMob = tamedMob;
      this.setFlags(EnumSet.of(Flag.TARGET));
   }

   @Override
   public boolean canUse() {
      if (this.tamedMob.isTame() && !this.tamedMob.isOrderedToSit()) {
         LivingEntity livingentity = this.tamedMob.getOwner();
         if (livingentity == null) {
            return false;
         } else {
            this.ownerLastHurt = livingentity.getLastHurtMob();
            int i = livingentity.getLastHurtMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT) && this.tamedMob.wantsToAttack(this.ownerLastHurt, livingentity);
         }
      } else {
         return false;
      }
   }

   @Override
   public void start() {
      this.mob.setTarget(this.ownerLastHurt);
      LivingEntity livingentity = this.tamedMob.getOwner();
      if (livingentity != null) {
         this.timestamp = livingentity.getLastHurtMobTimestamp();
      }

      super.start();
   }
}