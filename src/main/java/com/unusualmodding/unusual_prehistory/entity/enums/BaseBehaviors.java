package com.unusualmodding.unusual_prehistory.entity.enums;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public enum BaseBehaviors {

    IDLE("Idling", SoundEvents.EMPTY, 0);

    private final String name;
    private final SoundEvent sound;
    private final int length;

    BaseBehaviors(String name, SoundEvent sound, int length) {
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