package com.unusualmodding.unusual_prehistory.entity.ai.goals.talpanas;

import com.unusualmodding.unusual_prehistory.entity.Talpanas;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class TalpanasSwimGoal extends Goal {

	private final Talpanas talpanas;

	public TalpanasSwimGoal(Talpanas talpanas) {
		this.talpanas = talpanas;
		talpanas.getNavigation().setCanFloat(true);
	}

	@Override
	public boolean canUse() {
		return this.talpanas.isInWater() || this.talpanas.isInLava();
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		Vec3 motion = this.talpanas.getDeltaMovement();
		double d0 = this.talpanas.getFluidHeight(FluidTags.WATER);
		double d1 = motion.y;
		if (!this.talpanas.isBaby() && d0 > 0.3D || this.talpanas.isBaby() && d0 > 0.15D) {
			if (d1 < -0.02D) {
				d1 *= 0.3F;
			} else if (!this.talpanas.isBaby() && d0 > 0.31D || this.talpanas.isBaby() && d0 > 0.16) {
				d1 += motion.y < (double) 0.12F ? 0.03F : 0.0F;
			} else if (d1 < 0.0D) {
				d1 = 0.0D;
			}
		}
		this.talpanas.setDeltaMovement(motion.x, d1, motion.z);
	}
}