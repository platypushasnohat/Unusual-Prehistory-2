package com.barlinc.unusual_prehistory.blocks.entity;

import net.minecraft.server.level.ServerLevel;

public interface Brushable {

    default boolean brush(long startTick) {
        return false;
    }
}
