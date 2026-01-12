package com.barlinc.unusual_prehistory.entity.utils;

import net.minecraft.world.entity.Pose;

public enum UP2Poses {

    ATTACKING,
    ATTACKING_FAST,
    BITING,
    HEADBUTTING,
    HEADBUTTING_FAST,
    START_CAMO,
    STOP_CAMO,
    BURROWED,
    TAIL_WHIPPING,
    START_CHARGING,
    CHARGING,
    STOP_CHARGING,
    DEATH_ROLL,
    SPITTING,
    BELLOWING,
    START_FLYING,
    START_SITTING,
    SITTING,
    STOP_SITTING,
    START_SLEEPING,
    SLEEPING,
    STOP_SLEEPING,
    SLASH_RUSH,
    WARNING,
    KICKING,
    POKING,
    FORAGING,
    MITOSIS,
    ALERTED,
    STOMPING;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
