package com.barlinc.unusual_prehistory.entity.utils;

import net.minecraft.world.entity.Pose;

public enum UP2Poses {

    ATTACKING,
    RESTING,
    BITING,
    HEADBUTTING,
    STEALTH,
    PREENING,
    PECKING,
    SHAKING,
    BURROWED,
    WAVING,
    TAIL_WHIPPING,
    START_CHARGING,
    CHARGING,
    STOP_CHARGING,
    DEATH_ROLL,
    SPITTING,
    BELLOWING,
    START_FLYING;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
