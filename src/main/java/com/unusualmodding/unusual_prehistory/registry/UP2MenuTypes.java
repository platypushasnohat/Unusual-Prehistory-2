package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.menus.CultivatorMenu;
import com.unusualmodding.unusual_prehistory.menus.ExtractorMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2MenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<MenuType<CultivatorMenu>> CULTIVATOR_MENU = registerMenuType(CultivatorMenu::new, "cultivator_menu");

    public static final RegistryObject<MenuType<ExtractorMenu>> ANALYZER_MENU = registerMenuType(ExtractorMenu::new, "analyzer_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
}
