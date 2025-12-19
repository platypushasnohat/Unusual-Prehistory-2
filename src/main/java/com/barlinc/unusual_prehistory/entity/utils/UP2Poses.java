package com.barlinc.unusual_prehistory.entity.utils;

import net.minecraft.world.entity.Pose;

public enum UP2Poses {

    ATTACKING,
    BITING,
    HEADBUTTING,
    START_CAMO,
    STOP_CAMO,
    BURROWED,
    WAVING,
    TAIL_WHIPPING,
    START_CHARGING,
    CHARGING,
    STOP_CHARGING,
    DEATH_ROLL,
    SPITTING,
    BELLOWING,
    START_FLYING,
    SITTING,
    WARN;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
