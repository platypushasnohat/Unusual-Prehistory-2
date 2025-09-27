package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
import com.unusualmodding.unusual_prehistory.entity.enums.Behaviors;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

public class LargePanicGoal extends PanicGoal {

    private final PrehistoricMob prehistoricMob;

    public LargePanicGoal(PrehistoricMob mob, double speedModifier) {
        super(mob, speedModifier);
        this.prehistoricMob = mob;
    }

    @Override
    public void start() {
        super.start();
        this.prehistoricMob.setBehavior(Behaviors.PANIC.getName());
    }

    @Override
    public void stop() {
        super.stop();
        this.prehistoricMob.setBehavior(Behaviors.IDLE.getName());
    }

    @Override
    protected boolean findRandomPosition() {
        Vec3 vec3 = DefaultRandomPos.getPos(this.prehistoricMob, 10, 4);
        if (vec3 == null) {
            return false;
        } else {
            this.posX = vec3.x;
            this.posY = vec3.y;
            this.posZ = vec3.z;
            return true;
        }
    }

    @Override
    protected BlockPos lookForWater(BlockGetter level, Entity entity, int range) {
        BlockPos entityPos = entity.blockPosition();
        if (!level.getBlockState(entityPos).getCollisionShape(level, entityPos).isEmpty()) {
            return null;
        }
        return BlockPos.findClosestMatch(entityPos, range + 6, 4, (pos) -> level.getFluidState(pos).is(FluidTags.WATER)).orElse(null);
    }
}

