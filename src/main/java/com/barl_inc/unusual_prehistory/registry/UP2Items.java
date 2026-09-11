package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class UP2Items {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UnusualPrehistory2.MOD_ID);
    public static List<DeferredItem<? extends Item>> ITEM_TRANSLATIONS = new ArrayList<>();

}
