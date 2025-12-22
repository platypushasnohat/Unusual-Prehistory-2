package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.utils.ButtonPressingMob;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.EnumSet;
import java.util.List;

public class PressButtonGoal extends Goal {

    protected final PathfinderMob mob;
    private BlockPos blockPos = BlockPos.ZERO;
    private Vec3i buttonNormal;
    private int nextStartTicks;
    private int tryTicks;
    private int maxStayTicks;
    private int pressWaitTicks;

    public PressButtonGoal(PathfinderMob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!(mob instanceof ButtonPressingMob)) return false;
        if (nextStartTicks > 0) {
            this.nextStartTicks--;
            return false;
        } else {
            this.nextStartTicks = 20;
            return this.findRandomButton();
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (tryTicks < -maxStayTicks || tryTicks > 1200) {
            return false;
        } else {
            return this.isUnpressedButton(mob.level(), blockPos);
        }
    }

    @Override
    public void start() {
        this.moveMobToBlock();
        this.tryTicks = 0;
        this.maxStayTicks = 80 + mob.getRandom().nextInt(80);
        this.pressWaitTicks = 16;
    }

    @Override
    public void tick() {
        this.mob.getLookControl().setLookAt(blockPos.getX() + 0.5D + buttonNormal.getX() * 0.5D, blockPos.getY() + 0.5D + buttonNormal.getY() * 0.5D, blockPos.getZ() + 0.5D + buttonNormal.getZ() * 0.5D, 10.0F, mob.getMaxHeadXRot());

        if (mob.distanceToSqr(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D) > 1.5625D) {
            this.tryTicks++;
            this.pressWaitTicks = 20;
            if (tryTicks % 40 == 0) {
                this.moveMobToBlock();
            }
        } else {
            this.tryTicks--;
            this.pressWaitTicks--;

            if (pressWaitTicks == 6) {
                if (mob instanceof ButtonPressingMob buttonPressingMob) buttonPressingMob.pressButton();
            } else if (pressWaitTicks <= 0) {
                BlockState state = mob.level().getBlockState(blockPos);
                if (state.getBlock() instanceof ButtonBlock buttonBlock && !state.getValue(ButtonBlock.POWERED)) {
                    buttonBlock.press(state, mob.level(), blockPos);
                    this.mob.level().playSound(null, this.blockPos, buttonBlock.getSound(false), SoundSource.BLOCKS, 0.3F, 0.8F);
                    this.mob.level().gameEvent(mob, GameEvent.BLOCK_ACTIVATE, blockPos);
                    if (mob instanceof ButtonPressingMob buttonPressingMob) buttonPressingMob.getTicksSinceButtonPress(80);
                }
            }
        }
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    private void moveMobToBlock() {
        this.mob.getNavigation().moveTo(mob.getNavigation().createPath(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0), 1.0D);
    }

    private boolean findRandomButton() {
        List<BlockPos> buttonpositions = Lists.newArrayList();

        for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(mob.getX() - 8.0D), Mth.floor(mob.getY() - 4.0D), Mth.floor(mob.getZ() - 8.0D), Mth.floor(mob.getX() + 8.0D), Mth.floor(mob.getY() + 4.0D), Mth.floor(mob.getZ() + 8.0D))) {
            if (mob.isWithinRestriction(pos) && this.isUnpressedButton(mob.level(), pos)) {
                buttonpositions.add(new BlockPos(pos));
            }
        }

        if (!buttonpositions.isEmpty()) {
            this.blockPos = buttonpositions.get(mob.getRandom().nextInt(buttonpositions.size()));
            BlockState state = mob.level().getBlockState(blockPos);
            AttachFace face = state.getValue(ButtonBlock.FACE);
            Direction direction = face == AttachFace.CEILING ? Direction.UP : face == AttachFace.FLOOR ? Direction.DOWN : state.getValue(ButtonBlock.FACING).getOpposite();
            this.buttonNormal = direction.getNormal();
            return true;
        }
        return false;
    }

    private boolean isUnpressedButton(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        BlockPos belowpos = pos.below();
        BlockState belowstate = level.getBlockState(belowpos);
        return state.getBlock() instanceof ButtonBlock && !state.getValue(ButtonBlock.POWERED) && belowstate.entityCanStandOn(level, belowpos, mob);
    }
}