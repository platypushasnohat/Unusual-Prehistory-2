package com.barlinc.unusual_prehistory.entity.utils;

import net.minecraft.world.entity.Pose;

public enum UP2Poses {

    ATTACKING,
    ATTACKING_FAST,
    HEADBUTTING,
    HEADBUTTING_FAST,
    START_CAMO,
    STOP_CAMO,
    BURROWED,
    TAIL_WHIPPING,
    START_CHARGING,
    CHARGING,
    STOP_CHARGING,
    GRABBING,
    SPITTING,
    BELLOWING,
    START_FLYING,
    START_SITTING,
    SITTING,
    STOP_SITTING,
    START_EEPY,
    EEPY,
    STOP_EEPY,
    WARNING,
    KICKING,
    POKING,
    FORAGING,
    MITOSIS,
    ALERTED,
    ENRAGED,
    STOMPING,
    RECOVERING;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
