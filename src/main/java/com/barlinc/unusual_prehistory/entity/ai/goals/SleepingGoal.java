package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SleepingGoal extends Goal {

	public PrehistoricMob prehistoricMob;

	public SleepingGoal(PrehistoricMob prehistoricMob) {
		this.prehistoricMob = prehistoricMob;
	}

	@Override
	public boolean canUse() {
//		Level level = prehistoricMob.level();
//		for (Player player : level.getEntitiesOfClass(Player.class, prehistoricMob.getBoundingBox().inflate(0.5D))) {
//			if (!player.isShiftKeyDown()) return false;
//		}
		return prehistoricMob.isEepyTime() && prehistoricMob.getLastHurtByMob() == null && prehistoricMob.getTarget() == null && !prehistoricMob.isInWater() && !prehistoricMob.isInLava() && prehistoricMob.getEepyCooldown() == 0 && !prehistoricMob.isMobSitting() && !prehistoricMob.isBaby();
	}

	@Override
	public boolean canContinueToUse() {
//		Level level = prehistoricMob.level();
//		for (Player player : level.getEntitiesOfClass(Player.class, prehistoricMob.getBoundingBox().inflate(0.5D))) {
//			if (player.isShiftKeyDown()) {
//				if (!prehistoricMob.isEepyTime() || prehistoricMob.getLastHurtByMob() != null || !super.canContinueToUse() || prehistoricMob.getTarget() != null || prehistoricMob.isInWater() || prehistoricMob.isInLava()) {
//                    this.stop();
//					return false;
//				}
//                else return true;
//			}
//            else {
//				this.stop();
//				return false;
//			}
//		}
		if (!prehistoricMob.isEepyTime() || prehistoricMob.getLastHurtByMob() != null || !super.canContinueToUse() || prehistoricMob.getTarget() != null || prehistoricMob.isInWater() || prehistoricMob.isInLava()) {
			this.stop();
			return false;
		}
        else return true;
	}

	@Override
	public void start() {
		this.prehistoricMob.xxa = 0.0F;
		this.prehistoricMob.yya = 0.0F;
		this.prehistoricMob.zza = 0.0F;
		this.prehistoricMob.getNavigation().stop();
		this.prehistoricMob.startEepy();
	}

    @Override
	public void tick() {
		super.tick();
		this.prehistoricMob.getNavigation().stop();;
//		Level level = prehistoricMob.level();
//		for (Player player : level.getEntitiesOfClass(Player.class, prehistoricMob.getBoundingBox().inflate(0.5D))) {
//			if (!player.isShiftKeyDown()) {
//				this.stop();
//			}
//		}
		if (!prehistoricMob.isEepyTime() || prehistoricMob.getLastHurtByMob() != null || prehistoricMob.getTarget() != null || prehistoricMob.isInWater() || prehistoricMob.isInLava()) {
			this.stop();
		}
	}

	@Override
	public void stop() {
		this.prehistoricMob.setEepyCooldown(100);
		this.prehistoricMob.stopEepy();
	}
}