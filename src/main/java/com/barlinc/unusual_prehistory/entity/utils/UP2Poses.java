package com.barlinc.unusual_prehistory.entity.utils;

import net.minecraft.world.entity.Pose;

public enum UP2Poses {

    ATTACKING,
    RESTING,
    BITING,
    HEADBUTTING,
    STEALTH,
    PREENING,
    BURROWED,
    WAVING,
    TAIL_WHIPPING,
    START_CHARGING,
    CHARGING,
    STOP_CHARGING;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
