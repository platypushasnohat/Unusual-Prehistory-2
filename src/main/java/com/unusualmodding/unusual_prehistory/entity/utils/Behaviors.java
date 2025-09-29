package com.unusualmodding.unusual_prehistory.entity.utils;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public enum Behaviors {

    IDLE("Idling", SoundEvents.EMPTY, 0),
    ANGRY("Angry", SoundEvents.EMPTY, 0),
    PANIC("Panicking", SoundEvents.EMPTY, 0);

    private final String name;
    private final SoundEvent sound;
    private final int length;

    Behaviors(String name, SoundEvent sound, int length) {
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