package com.barlinc.unusual_prehistory;

import com.barlinc.unusual_prehistory.datagen.*;
import com.barlinc.unusual_prehistory.registry.*;
import com.barlinc.unusual_prehistory.utils.ClientProxy;
import com.barlinc.unusual_prehistory.utils.CommonProxy;
import com.barlinc.unusual_prehistory.utils.UP2LoadedMods;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Mod(UnusualPrehistory2.MOD_ID)
public class UnusualPrehistory2 {

    public static final String MOD_ID = "unusual_prehistory";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static CommonProxy PROXY = FMLEnvironment.dist.isClient() ? new ClientProxy() : new CommonProxy();

    public UnusualPrehistory2(IEventBus bus, ModContainer container) {
        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::loadComplete);
        bus.addListener(this::dataSetup);

        UP2Entities.ENTITY_TYPE.register(bus);
        UP2Blocks.BLOCKS.register(bus);
        UP2Items.ITEMS.register(bus);
        UP2Fluids.FLUIDS.register(bus);
        UP2Fluids.TYPES.register(bus);
        UP2BlockEntities.BLOCK_ENTITIES.register(bus);
        UP2MobEffects.MOB_EFFECTS.register(bus);
        UP2MenuTypes.MENUS.register(bus);
        UP2Recipes.RECIPE_TYPES.register(bus);
        UP2Recipes.RECIPE_SERIALIZERS.register(bus);
        UP2Features.FEATURES.register(bus);
        UP2Trees.TREE_DECORATORS.register(bus);
        UP2Trees.TRUNK_PLACERS.register(bus);
        UP2Trees.FOLIAGE_PLACERS.register(bus);
        UP2Structures.STRUCTURE_TYPES.register(bus);
        UP2StructureProcessors.PROCESSOR_TYPES.register(bus);
        UP2SoundEvents.SOUND_EVENTS.register(bus);
        UP2Particles.PARTICLE_TYPES.register(bus);
        UnusualPrehistory2Tab.CREATIVE_TABS.register(bus);
        PROXY.commonInit();
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(UP2Compat::registerCompat);
        UP2Network.registerNetwork();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> PROXY.clientInit());
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(UP2Fluids::postInit);
        event.enqueueWork(UP2LoadedMods::afterAllModsLoaded);
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
        generator.addProvider(client, new UP2LanguageProvider(data));

        boolean server = data.includeServer();

        UP2DatapackProvider datapackEntries = new UP2DatapackProvider(output, provider);
        generator.addProvider(server, datapackEntries);
        provider = datapackEntries.getRegistryProvider();

        UP2BlockTagProvider blockTags = new UP2BlockTagProvider(output, provider, helper);
        generator.addProvider(server, blockTags);
        generator.addProvider(server, new UP2ItemTagProvider(output, provider, blockTags.contentsGetter(), helper));
        generator.addProvider(server, new UP2EntityTagProvider(output, provider, helper));
        generator.addProvider(server, new UP2BiomeTagProvider(output, provider, helper));
        generator.addProvider(server, new UP2BannerPatternTagProvider(output, provider, helper));
        generator.addProvider(server, new UP2DamageTypeTagProvider(output, provider, helper));
        generator.addProvider(server, UP2LootProvider.register(output));
        generator.addProvider(server, new UP2RecipeProvider(output));
        generator.addProvider(server, UP2AdvancementProvider.register(output, provider, helper));
    }

    public static ResourceLocation modPrefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name.toLowerCase(Locale.ROOT));
    }
}

