package com.barlinc.unusual_prehistory.utils;

import net.neoforged.fml.ModList;

public class UP2LoadedMods {

    private static boolean patchouliLoaded;
    private static boolean oculusLoaded;
    private static boolean dyeDepotLoaded;

    public static void afterAllModsLoaded() {
        patchouliLoaded = ModList.get().isLoaded("patchouli");
        oculusLoaded = ModList.get().isLoaded("oculus");
        dyeDepotLoaded = ModList.get().isLoaded("dye_depot");
    }

    public static boolean isPatchouliLoaded() {
        return patchouliLoaded;
    }

    public static boolean isOculusLoaded() {
        return oculusLoaded;
    }

    public static boolean isDyeDepotLoaded() {
        return dyeDepotLoaded;
    }
}
