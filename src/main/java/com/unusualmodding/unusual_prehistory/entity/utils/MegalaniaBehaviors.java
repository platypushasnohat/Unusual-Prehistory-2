package com.unusualmodding.unusual_prehistory.entity.utils;

import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.sounds.SoundEvent;

public enum MegalaniaBehaviors {

    ROAR("Roaring", UP2SoundEvents.MEGALANIA_IDLE.get(), 80);

    private final String name;
    private final SoundEvent sound;
    private final int length;

    MegalaniaBehaviors(String name, SoundEvent sound, int length) {
        this.name = name;
        this.sound = sound;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public SoundEvent getSound() {
        return sound;
    }

    public int getLength() {
        return length;
    }
}