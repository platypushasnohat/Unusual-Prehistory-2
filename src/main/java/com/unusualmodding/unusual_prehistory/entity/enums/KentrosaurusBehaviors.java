package com.unusualmodding.unusual_prehistory.entity.enums;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public enum KentrosaurusBehaviors {

    GRAZE("Grazing", SoundEvents.EMPTY, 41),
    SHAKE("Shaking", SoundEvents.EMPTY, 40);

    private final String name;
    private final SoundEvent sound;
    private final int length;

    KentrosaurusBehaviors(String name, SoundEvent sound, int length) {
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