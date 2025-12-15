package com.barlinc.unusual_prehistory.entity.utils;

@Deprecated
public enum Behaviors {

    IDLE("Idling"),
    ANGRY("Angry"),
    PANIC("Panicking");

    private final String name;

    Behaviors(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}