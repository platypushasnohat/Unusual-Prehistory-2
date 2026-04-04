package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class UP2EnumProxy {

    public static final EnumProxy<Boat.Type> GINKGO_BOAT_TYPE = new EnumProxy<>(Boat.Type.class, UP2Blocks.GINKGO.planks(), UnusualPrehistory2.MOD_ID + ":ginkgo", UP2Items.GINKGO_BOAT, UP2Items.GINKGO_CHEST_BOAT, Items.STICK, false);
    public static final EnumProxy<Boat.Type> LEPIDODENDRON_BOAT_TYPE = new EnumProxy<>(Boat.Type.class, UP2Blocks.LEPIDODENDRON.planks(), UnusualPrehistory2.MOD_ID + ":lepidodendron", UP2Items.LEPIDODENDRON_BOAT, UP2Items.LEPIDODENDRON_CHEST_BOAT, Items.STICK, false);

    public static final EnumProxy<Boat.Type> DRYOPHYLLUM_BOAT_TYPE = new EnumProxy<>(Boat.Type.class, UP2Blocks.DRYOPHYLLUM.planks(), UnusualPrehistory2.MOD_ID + ":dryophyllum", UP2Items.DRYOPHYLLUM_BOAT, UP2Items.DRYOPHYLLUM_CHEST_BOAT, Items.STICK, false);
    public static final EnumProxy<Boat.Type> METASEQUOIA_BOAT_TYPE = new EnumProxy<>(Boat.Type.class, UP2Blocks.METASEQUOIA.planks(), UnusualPrehistory2.MOD_ID + ":metasequoia", UP2Items.METASEQUOIA_BOAT, UP2Items.METASEQUOIA_CHEST_BOAT, Items.STICK, false);

}
