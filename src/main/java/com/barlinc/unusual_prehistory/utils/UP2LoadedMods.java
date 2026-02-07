package com.barlinc.unusual_prehistory.utils;

import net.minecraftforge.fml.ModList;

public class UP2LoadedMods {

    private static boolean oculusLoaded;
    private static boolean dyeDepotLoaded;

    public static void afterAllModsLoaded(){
        oculusLoaded = ModList.get().isLoaded("oculus");
        dyeDepotLoaded = ModList.get().isLoaded("dye_depot");
    }

    public static boolean isOculusLoaded() {
        return oculusLoaded;
    }

    public static boolean isDyeDepotLoaded() {
        return dyeDepotLoaded;
    }
}
