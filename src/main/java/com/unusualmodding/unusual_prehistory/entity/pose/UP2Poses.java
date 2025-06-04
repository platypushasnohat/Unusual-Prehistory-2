package com.unusualmodding.unusual_prehistory.entity.pose;

import net.minecraft.world.entity.Pose;

public enum UP2Poses {

    GRAZING,
    RESTING;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
