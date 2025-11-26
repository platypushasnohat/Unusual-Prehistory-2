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
    private static final MapDecoration.Type PALEOZOIC_FOSSIL_SITE = unusualPrehistory2$addType("PALEOZOIC_FOSSIL_SITE", true, 0X6B6B6B, false);

    @Unique
    private static final MapDecoration.Type MESOZOIC_FOSSIL_SITE = unusualPrehistory2$addType("MESOZOIC_FOSSIL_SITE", true, 0X6B6B6B, false);

    @Unique
    private static final MapDecoration.Type PETRIFIED_TREE_SITE = unusualPrehistory2$addType("PETRIFIED_TREE_SITE", true, 0X6B6B6B, false);

    @Invoker("<init>")
    public static MapDecoration.Type unusualPrehistory2$invokeInit(String internalName, int internalId, boolean renderOnFrame, int mapColor, boolean trackCount) {
        throw new AssertionError();
    }

    @Unique
    private static MapDecoration.Type unusualPrehistory2$addType(String internalName, boolean renderOnFrame, int mapColor, boolean trackCount) {
        ArrayList<MapDecoration.Type> variants = new ArrayList<>(Arrays.asList($VALUES));
        MapDecoration.Type instrument = unusualPrehistory2$invokeInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, renderOnFrame, mapColor, trackCount);
        variants.add(instrument);
        MapDecorationTypeMixin.$VALUES = variants.toArray(new MapDecoration.Type[0]);
        return instrument;
    }
}