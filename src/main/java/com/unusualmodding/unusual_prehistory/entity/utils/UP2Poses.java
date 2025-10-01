package com.unusualmodding.unusual_prehistory.entity.utils;

import net.minecraft.world.entity.Pose;

public enum UP2Poses {

    GRAZING,
    RESTING,
    BITING,
    CHARGING_START,
    CHARGING,
    CHARGING_END,
    STEALTH,
    YAWNING,
    ROARING,
    SHAKING,
    LOOKOUT,
    PECKING,
    PREENING,
    BURROWED;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
