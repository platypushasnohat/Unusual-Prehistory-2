package com.barlinc.unusual_prehistory.utils;

import net.minecraftforge.fml.ModList;

public class UP2LoadedMods {

    private static boolean oculusLoaded;

    public static void afterAllModsLoaded(){
        oculusLoaded = ModList.get().isLoaded("oculus");
    }

    public static boolean isOculusLoaded() {
        return oculusLoaded;
    }
}
