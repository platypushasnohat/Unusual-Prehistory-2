package com.barlinc.unusual_prehistory.entity.ai.goals.kimmeridgebrachypteraeschnidium;

import com.barlinc.unusual_prehistory.entity.Kimmeridgebrachypteraeschnidium;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;

public class KimmeridgebrachypteraeschnidiumLookAroundGoal extends RandomLookAroundGoal {

    private final Kimmeridgebrachypteraeschnidium dragonfly;

    public KimmeridgebrachypteraeschnidiumLookAroundGoal(Kimmeridgebrachypteraeschnidium dragonfly) {
        super(dragonfly);
        this.dragonfly = dragonfly;
    }

    @Override
    public boolean canUse() {
        return this.dragonfly.onGround() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return this.dragonfly.onGround() && super.canContinueToUse();
    }
}