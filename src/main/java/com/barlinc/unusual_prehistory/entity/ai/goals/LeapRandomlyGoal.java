package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class LeapRandomlyGoal extends Goal {

    protected final PrehistoricMob mob;
    protected final int chance;
    protected final int maxLeapDistance;
    protected final float verticalStrength;
    protected Vec3 leapPos = null;

    public LeapRandomlyGoal(PrehistoricMob mob, int chance, int maxLeapDistance, float verticalStrength) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.mob = mob;
        this.chance = chance;
        this.maxLeapDistance = maxLeapDistance;
        this.verticalStrength = verticalStrength;
    }

    @SuppressWarnings({"ReassignedVariable", "SuspiciousNameCombination"})
    @Override
    public void start() {
        if (leapPos != null) {
            if (mob.getNavigation().getPath() != null) {
                this.mob.getNavigation().stop();
            }
            Vec3 deltaMovement = mob.getDeltaMovement();
            Vec3 vec3 = new Vec3(leapPos.x - mob.getX(), 0.0D, leapPos.z - mob.getZ());
            if (vec3.lengthSqr() > 1.0E-7D) {
                vec3 = vec3.normalize().scale(0.9D).add(deltaMovement.scale(0.8D));
            }
            if (mob instanceof LeapingMob leapingMob) {
                leapingMob.onLeap();
            }
            this.mob.setDeltaMovement(vec3.x, verticalStrength, vec3.z);
            this.mob.setYRot(-((float) Mth.atan2(vec3.x, vec3.z)) * Mth.RAD_TO_DEG);
            this.mob.yBodyRot = mob.getYRot();
            this.mob.yHeadRot = mob.getYRot();
            this.leapPos = null;
        }
    }

    @Override
    public void stop() {
        this.leapPos = null;
    }

    @Override
    public boolean canUse() {
        if (mob.isSitting() || mob.isEepy() || mob.refuseToMove()) {
            return false;
        }
        else if (mob.isInWaterOrBubble()) {
            return false;
        }
        else if (mob.getRandom().nextInt(chance) == 0 && mob.onGround() && mob.getNavigation().isDone()) {
            Vec3 found = LandRandomPos.getPos(mob, maxLeapDistance, maxLeapDistance);
            if (found != null && mob.distanceToSqr(found) < maxLeapDistance * maxLeapDistance && this.hasLineOfSightBlock(found)){
                this.leapPos = found;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return leapPos != null && mob.distanceToSqr(leapPos) < maxLeapDistance * maxLeapDistance && this.hasLineOfSightBlock(leapPos);
    }

    protected boolean hasLineOfSightBlock(Vec3 blockVec) {
        Vec3 vec3 = new Vec3(mob.getX(), mob.getEyeY(), mob.getZ());
        BlockHitResult hitResult = mob.level().clip(new ClipContext(vec3, blockVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob));
        return blockVec.distanceTo(hitResult.getLocation()) < 1.2F;
    }
}