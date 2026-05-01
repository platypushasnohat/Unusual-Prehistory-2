package com.barlinc.unusual_prehistory.entity.utils;

import net.minecraft.world.entity.Pose;

public enum UP2Poses {

    ATTACKING,
    ATTACKING_FAST,
    HEADBUTTING,
    HEADBUTTING_FAST,
    TAIL_WHIPPING,
    START_CHARGING,
    CHARGING,
    STOP_CHARGING,
    GRABBING,
    GRAB_START,
    SPITTING,
    START_FLYING,
    WARNING,
    KICKING,
    POKING,
    FORAGING,
    MITOSIS,
    ALERTED,
    ENRAGED,
    STOMPING,
    RECOVERING,
    ROARING,
    BURPING;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}