package com.barl_inc.unusual_prehistory;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Mod(UnusualPrehistory2.MOD_ID)
public class UnusualPrehistory2 {

    public static final String MOD_ID = "unusual_prehistory";

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path.toLowerCase(Locale.ROOT));
    }

    public UnusualPrehistory2(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::dataSetup);
    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        boolean server = event.includeServer();
        boolean client = event.includeClient();
    }
}
