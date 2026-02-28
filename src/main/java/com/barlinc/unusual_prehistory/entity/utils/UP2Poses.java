package com.barlinc.unusual_prehistory.entity.utils;

import net.minecraft.world.entity.Pose;

public enum UP2Poses {

    ATTACKING,
    ATTACKING_FAST,
    HEADBUTTING,
    HEADBUTTING_FAST,
    BURROWED,
    TAIL_WHIPPING,
    START_CHARGING,
    CHARGING,
    STOP_CHARGING,
    GRABBING,
    SPITTING,
    BELLOWING,
    START_FLYING,
    SITTING,
    SLEEPING,
    WARNING,
    KICKING,
    POKING,
    FORAGING,
    MITOSIS,
    ALERTED,
    ENRAGED,
    STOMPING,
    RECOVERING,
    ROARING;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
