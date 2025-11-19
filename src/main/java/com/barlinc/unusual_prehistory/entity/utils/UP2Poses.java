package com.barlinc.unusual_prehistory.entity.utils;

import net.minecraft.world.entity.Pose;

public enum UP2Poses {

    RESTING,
    BITING,
    STEALTH,
    YAWNING,
    PREENING,
    BURROWED,
    WAVING,
    TAIL_WHIPPING;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
