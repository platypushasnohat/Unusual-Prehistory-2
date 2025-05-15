package com.unusualmodding.unusual_prehistory;

import com.unusualmodding.unusual_prehistory.blocks.UP2Blocks;
import com.unusualmodding.unusual_prehistory.compat.UP2Compat;
import com.unusualmodding.unusual_prehistory.data.*;
import com.unusualmodding.unusual_prehistory.entity.UP2Entities;
import com.unusualmodding.unusual_prehistory.items.UP2Items;
import com.unusualmodding.unusual_prehistory.particles.UP2Particles;
import com.unusualmodding.unusual_prehistory.sounds.UP2Sounds;
import com.unusualmodding.unusual_prehistory.tab.UP2CreativeTabs;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Mod(UnusualPrehistory2.MOD_ID)
public class UnusualPrehistory2 {

    public static final String MOD_ID = "unusual_prehistory";
    public static final Logger LOGGER = LogManager.getLogger();

    public UnusualPrehistory2() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        UP2CreativeTabs.CREATIVE_TABS.register(bus);
        UP2Items.ITEMS.register(bus);
        UP2Blocks.BLOCKS.register(bus);
        UP2Entities.ENTITY_TYPE.register(bus);
        UP2Sounds.SOUND_EVENTS.register(bus);
        UP2Particles.PARTICLE_TYPES.register(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::dataSetup);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            UP2Compat.registerCompat();
        });
    }

    private void clientSetup(FMLClientSetupEvent event) {
    }

    private void dataSetup(GatherDataEvent data) {
        DataGenerator generator = data.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = data.getLookupProvider();
        ExistingFileHelper helper = data.getExistingFileHelper();

        boolean client = data.includeClient();
        generator.addProvider(client, new UP2BlockstateProvider(data));
        generator.addProvider(client, new UP2ItemModelProvider(data));
        generator.addProvider(client, new UP2SoundDefinitionsProvider(output, helper));
        generator.addProvider(client, new UP2LangProvider(data));

        boolean server = data.includeServer();
        UP2BlockTagProvider blockTags = new UP2BlockTagProvider(output, provider, helper);
        generator.addProvider(server, blockTags);
        generator.addProvider(server, new UP2ItemTagProvider(output, provider, blockTags.contentsGetter(), helper));
        generator.addProvider(server, UP2LootProvider.register(output));
    }

    public static ResourceLocation modPrefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }
}

