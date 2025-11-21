package com.barlinc.unusual_prehistory.mixins;

import net.minecraft.world.level.saveddata.maps.MapDecoration;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(MapDecoration.Type.class)
@Unique
public class MapDecorationTypeMixin {

    @Shadow
    @Final
    @Mutable
    private static MapDecoration.Type[] $VALUES;

    @Unique
    private static final MapDecoration.Type FOSSIL_SITE = unusualPrehistory$addType("FOSSIL_SITE", true, 0X6B6B6B, false);

    @Invoker("<init>")
    public static MapDecoration.Type unusualPrehistory$invokeInit(String internalName, int internalId, boolean renderOnFrame, int mapColor, boolean trackCount) {
        throw new AssertionError();
    }

    @Unique
    private static MapDecoration.Type unusualPrehistory$addType(String internalName, boolean renderOnFrame, int mapColor, boolean trackCount) {
        ArrayList<MapDecoration.Type> variants = new ArrayList<>(Arrays.asList($VALUES));
        MapDecoration.Type instrument = unusualPrehistory$invokeInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, renderOnFrame, mapColor, trackCount);
        variants.add(instrument);
        MapDecorationTypeMixin.$VALUES = variants.toArray(new MapDecoration.Type[0]);
        return instrument;
    }
}