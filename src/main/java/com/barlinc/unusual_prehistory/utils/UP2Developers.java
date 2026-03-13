package com.barlinc.unusual_prehistory.utils;

import java.util.UUID;

public enum UP2Developers {

    DEV(UUID.fromString("380df991-f603-344c-a090-369bad2a924a")),
    PLATYPUS(UUID.fromString("97399daf-aecd-45c9-a6f2-c18e9c9b18a2")),
    PEEKO32213(UUID.fromString("142543af-76fe-45d9-8d53-8f169c86ec64")),
    TEMO5886(UUID.fromString("44d61b0b-e43b-469b-aac3-99274ed48f9b")),
    DENOBODY2(UUID.fromString("3562ab33-f01b-4801-aab5-807f3750ded1")),
    VALIANTSQUIDWARD(UUID.fromString("198c9341-de41-4164-9a7c-1395cc96234c")),
    AERYS(UUID.fromString("3e11a940-8aff-449c-80d1-fc0f427f4876")),
    CHIPS(UUID.fromString("b0f6689a-b1a3-4830-887b-ec31862861d7")),
    SLAVIEN(UUID.fromString("4fd56a02-1e84-45f7-8c45-a77997f18f8e")),
    NOVA(UUID.fromString("01fa723b-177c-42b2-8583-5dce22bad26a")),
    ICYDWARF(UUID.fromString("cf6cdaba-1b84-42cc-ac78-24b1569abd3f")),
    VAKYPANDA(UUID.fromString("0c22615f-a189-4f4e-85ae-79fd80c353c8")),
    KINGOFTHEOVIS64(UUID.fromString("8f14a01a-8dd8-44b5-a766-96a7d2d8ec47")),
    LILLID0PTERA(UUID.fromString("4371a927-5cb7-442b-b9e7-942f714f43a7")),
    MAGMASTRIDER(UUID.fromString("046fb8ea-2c53-4a66-b322-de11f527f612")),
    CRYDIGO(UUID.fromString("15be46af-ab50-4d04-acaf-bbf713faf1f9")),
    // MOUTH
    INDOMINATOR(UUID.fromString("053de6af-392b-4385-90c5-34395b7e8757"));

    private final UUID uuid;

    UP2Developers(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public static boolean isDeveloper(UUID uuid) {
        for (UP2Developers dev : values()) {
            if (dev.getUuid().equals(uuid)) return true;
        }
        return false;
    }
}
