package com.barlinc.unusual_prehistory.entity.utils;

public interface ButtonPressingMob {

    int getPushButtonCooldown();

    void setPushButtonCooldown(int cooldown);

    default boolean canPushButton() {
        return this.getPushButtonCooldown() == 0;
    }

    default void pressButton() {
    }
}
