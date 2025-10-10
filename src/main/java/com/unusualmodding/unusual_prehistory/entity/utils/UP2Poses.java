package com.unusualmodding.unusual_prehistory.entity.utils;

import net.minecraft.world.entity.Pose;

public enum UP2Poses {

    RESTING,
    BITING,
    STEALTH,
    YAWNING,
    ROARING,
    PREENING,
    BURROWED,
    WAVING;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
