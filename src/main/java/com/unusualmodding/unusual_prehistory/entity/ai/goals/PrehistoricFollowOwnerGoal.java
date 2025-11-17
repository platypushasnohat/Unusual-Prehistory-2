package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.base.TameablePrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import java.util.EnumSet;

public class PrehistoricFollowOwnerGoal extends Goal {

    private final TameablePrehistoricMob tamedMob;
    private LivingEntity owner;
    private final LevelReader level;
    private final double speedModifier;
    private final PathNavigation navigation;
    private int timeToRecalcPath;
    private final float stopDistance;
    private final float startDistance;
    private float oldWaterCost;
    private final boolean canFly;

    public PrehistoricFollowOwnerGoal(TameablePrehistoricMob tamedMob, double speedModifier, float startDistance, float stopDistance, boolean canFly) {
        this.tamedMob = tamedMob;
        this.level = this.tamedMob.level();
        this.speedModifier = speedModifier;
        this.navigation = this.tamedMob.getNavigation();
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.canFly = canFly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(this.tamedMob.getNavigation() instanceof GroundPathNavigation) && !(this.tamedMob.getNavigation() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for PrehistoricFollowOwnerGoal");
        }
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.tamedMob.getOwner();
        if (owner == null) {
            return false;
        } else if (owner.isSpectator()) {
            return false;
        } else if (this.unableToMove()) {
            return false;
        } else if (this.tamedMob.distanceToSqr(owner) < (double) (this.startDistance * this.startDistance)) {
            return false;
        } else {
            this.owner = owner;
            return this.shouldFollow() && !this.isInCombat();
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.navigation.isDone()) {
            return false;
        } else if (this.unableToMove()) {
            return false;
        } else {
            return !(this.tamedMob.distanceToSqr(this.owner) <= (double)(this.stopDistance * this.stopDistance)) && this.shouldFollow() && !this.isInCombat();
        }
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.tamedMob.getPathfindingMalus(BlockPathTypes.WATER);
        this.tamedMob.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.tamedMob.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        this.tamedMob.getLookControl().setLookAt(this.owner, 10.0F, (float)this.tamedMob.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            if (this.tamedMob.distanceToSqr(this.owner) >= 144.0D) {
                this.teleportToOwner();
            } else {
                this.navigation.moveTo(this.owner, this.speedModifier);
            }
        }
    }

    private boolean unableToMove() {
        return this.tamedMob.isOrderedToSit() || this.tamedMob.isPassenger() || this.tamedMob.isLeashed();
    }

    private void teleportToOwner() {
        BlockPos blockpos = this.owner.blockPosition();
        for (int i = 0; i < 10; ++i) {
            int j = this.randomIntInclusive(-3, 3);
            int k = this.randomIntInclusive(-1, 1);
            int l = this.randomIntInclusive(-3, 3);
            boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
            if (flag) {
                return;
            }
        }
    }

    private boolean maybeTeleportTo(int x, int y, int z) {
        if (Math.abs((double) x - this.owner.getX()) < 2.0D && Math.abs((double) z - this.owner.getZ()) < 2.0D) {
            return false;
        } else if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        } else {
            this.tamedMob.moveTo((double) x + 0.5D, y, (double) z + 0.5D, this.tamedMob.getYRot(), this.tamedMob.getXRot());
            this.navigation.stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos blockPos) {
        BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(this.level, blockPos.mutable());
        if (blockpathtypes != BlockPathTypes.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = this.level.getBlockState(blockPos.below());
            if (!this.canFly && blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = blockPos.subtract(this.tamedMob.blockPosition());
                return this.level.noCollision(this.tamedMob, this.tamedMob.getBoundingBox().move(blockpos));
            }
        }
    }

    private boolean shouldFollow() {
        return tamedMob.getCommand() == 2;
    }

    private boolean isInCombat() {
        Entity owner = tamedMob.getOwner();
        if (owner != null) {
            return tamedMob.distanceTo(owner) < 30 && tamedMob.getTarget() != null && tamedMob.getTarget().isAlive();
        }
        return false;
    }

    private int randomIntInclusive(int min, int max) {
        return this.tamedMob.getRandom().nextInt(max - min + 1) + min;
    }
}