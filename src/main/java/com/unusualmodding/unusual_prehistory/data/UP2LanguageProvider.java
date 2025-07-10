package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import com.unusualmodding.unusual_prehistory.UP2CreativeTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class UP2LanguageProvider extends LanguageProvider {

    public UP2LanguageProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), UnusualPrehistory2.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        // creative tab
        creativeTab(UP2CreativeTabs.UNUSUAL_PREHISTORY_2_TAB.get(), "Unusual Prehistory 2");

        // blocks
        UP2Blocks.AUTO_TRANSLATE.forEach(this::forBlock);

        // items
        UP2Items.AUTO_TRANSLATE.forEach(this::forItem);

        // entities
        forEntity(UP2Entities.CARNOTAURUS);
        forEntity(UP2Entities.DIPLOCAULUS);
        forEntity(UP2Entities.DROMAEOSAURUS);
        forEntity(UP2Entities.DUNKLEOSTEUS);
        forEntity(UP2Entities.JAWLESS_FISH);
        forEntity(UP2Entities.KENTROSAURUS);
        forEntity(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM);
        forEntity(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH);
        forEntity(UP2Entities.MAJUNGASAURUS);
        forEntity(UP2Entities.MEGALANIA);
        forEntity(UP2Entities.SCAUMENACIA);
        forEntity(UP2Entities.STETHACANTHUS);
        forEntity(UP2Entities.UNICORN);

        // creature dna
        dnaItem(UP2Items.CARNOTAURUS_DNA.get());
        dnaItem(UP2Items.DROMAEOSAURUS_DNA.get());
        dnaItem(UP2Items.DIPLOCAULUS_DNA.get());
        dnaItem(UP2Items.DUNKLEOSTEUS_DNA.get());
        dnaItem(UP2Items.JAWLESS_FISH_DNA.get());
        dnaItem(UP2Items.KENTROSAURUS_DNA.get());
        dnaItem(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DNA.get());
        dnaItem(UP2Items.MAJUNGASAURUS_DNA.get());
        dnaItem(UP2Items.MEGALANIA_DNA.get());
        dnaItem(UP2Items.SCAUMENACIA_DNA.get());
        dnaItem(UP2Items.STETHACANTHUS_DNA.get());
        dnaItem(UP2Items.TELECREX_DNA.get());

        // plant dna
        dnaItem(UP2Items.ANOSTYLOSTROMA_DNA.get());
        dnaItem(UP2Items.ARCHAEFRUCTUS_DNA.get());
        dnaItem(UP2Items.ARCHAEOSIGILLARIA_DNA.get());
        dnaItem(UP2Items.BENNETTITALES_DNA.get());
        dnaItem(UP2Items.CALAMOPHYTON_DNA.get());
        dnaItem(UP2Items.CLADOPHLEBIS_DNA.get());
        dnaItem(UP2Items.CLATHRODICTYON_CORAL_DNA.get());
        dnaItem(UP2Items.COOKSONIA_DNA.get());
        dnaItem(UP2Items.GINKGO_DNA.get());
        dnaItem(UP2Items.HORSETAIL_DNA.get());
        dnaItem(UP2Items.ISOETES_DNA.get());
        dnaItem(UP2Items.LEEFRUCTUS_DNA.get());
        dnaItem(UP2Items.LEPIDODENDRON_DNA.get());
        dnaItem(UP2Items.NELUMBITES_DNA.get());
        dnaItem(UP2Items.QUEREUXIA_DNA.get());
        dnaItem(UP2Items.RAIGUENRAYUN_DNA.get());
        dnaItem(UP2Items.RHYNIA_DNA.get());
        dnaItem(UP2Items.SARRACENIA_DNA.get());

        addItem(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE, "Bottle of Kimmeridgebrachypteraeschnidium");
        addItem(UP2Items.JAWLESS_FISH_BUCKET, "Bucket of Jawless Fish");
        addItem(UP2Items.SCAUMENACIA_BUCKET, "Bucket of Scaumenacia");
        addItem(UP2Items.STETHACANTHUS_BUCKET, "Bucket of Stethacanthus");

        // sounds
        sound(UP2SoundEvents.CARNOTAURUS_HURT, "Carnotaurus hurts");
        sound(UP2SoundEvents.CARNOTAURUS_DEATH, "Carnotaurus dies");
        sound(UP2SoundEvents.CARNOTAURUS_IDLE, "Carnotaurus grumbles");

        sound(UP2SoundEvents.DIPLOCAULUS_HURT, "Diplocaulus hurts");
        sound(UP2SoundEvents.DIPLOCAULUS_DEATH, "Diplocaulus dies");
        sound(UP2SoundEvents.DIPLOCAULUS_IDLE, "Diplocaulus croaks");

        sound(UP2SoundEvents.DUNKLEOSTEUS_HURT, "Dunkleosteus hurts");
        sound(UP2SoundEvents.DUNKLEOSTEUS_DEATH, "Dunkleosteus dies");
        sound(UP2SoundEvents.DUNKLEOSTEUS_FLOP, "Dunkleosteus flops");

        sound(UP2SoundEvents.JAWLESS_FISH_HURT, "Jawless Fish hurts");
        sound(UP2SoundEvents.JAWLESS_FISH_DEATH, "Jawless Fish dies");
        sound(UP2SoundEvents.JAWLESS_FISH_FLOP, "Jawless Fish flops");

        sound(UP2SoundEvents.KENTROSAURUS_HURT, "Kentrosaurus hurts");
        sound(UP2SoundEvents.KENTROSAURUS_DEATH, "Kentrosaurus dies");
        sound(UP2SoundEvents.KENTROSAURUS_IDLE, "Kentrosaurus groans");
        sound(UP2SoundEvents.KENTROSAURUS_STEP, "Kentrosaurus steps");
        sound(UP2SoundEvents.KENTROSAURUS_EAT, "Kentrosaurus eats");

        sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT, "Kimmeridgebrachypteraeschnidium hurts");
        sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH, "Kimmeridgebrachypteraeschnidium dies");
        sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_LOOP, "Kimmeridgebrachypteraeschnidium buzzes");

        sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_HURT, "Kimmeridgebrachypteraeschnidium Nymph hurts");
        sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_DEATH, "Kimmeridgebrachypteraeschnidium Nymph dies");

        sound(UP2SoundEvents.MAJUNGASAURUS_HURT, "Majungasaurus hurts");
        sound(UP2SoundEvents.MAJUNGASAURUS_DEATH, "Majungasaurus dies");
        sound(UP2SoundEvents.MAJUNGASAURUS_IDLE, "Majungasaurus groans");

        sound(UP2SoundEvents.MEGALANIA_HURT, "Megalania hurts");
        sound(UP2SoundEvents.MEGALANIA_DEATH, "Megalania dies");
        sound(UP2SoundEvents.MEGALANIA_IDLE, "Megalania hisses");

        sound(UP2SoundEvents.SCAUMENACIA_HURT, "Scaumenacia hurts");
        sound(UP2SoundEvents.SCAUMENACIA_DEATH, "Scaumenacia dies");
        sound(UP2SoundEvents.SCAUMENACIA_FLOP, "Scaumenacia flops");

        sound(UP2SoundEvents.STETHACANTHUS_HURT, "Stethacanthus hurts");
        sound(UP2SoundEvents.STETHACANTHUS_DEATH, "Stethacanthus dies");
        sound(UP2SoundEvents.STETHACANTHUS_FLOP, "Stethacanthus flops");

        sound(UP2SoundEvents.TALPANAS_HURT, "Talpanas hurts");
        sound(UP2SoundEvents.TALPANAS_DEATH, "Talpanas dies");
        sound(UP2SoundEvents.TALPANAS_IDLE, "Talpanas quacks");
        sound(UP2SoundEvents.TALPANAS_PANIC, "Talpanas panics");

        sound(UP2SoundEvents.TELECREX_HURT, "Telecrex hurts");
        sound(UP2SoundEvents.TELECREX_DEATH, "Telecrex dies");
        sound(UP2SoundEvents.TELECREX_IDLE, "Telecrex squawks");

        sound(UP2SoundEvents.UNICORN_HURT, "Unicorn hurts");
        sound(UP2SoundEvents.UNICORN_DEATH, "Unicorn dies");
        sound(UP2SoundEvents.UNICORN_IDLE, "Unicorn grunts");

        // kimmeridgebrachypteraeschnidium bottle
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.0", "Black Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.1", "Blue Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.2", "Brown Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.3", "Cyan Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.4", "Gray Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.5", "Green Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.6", "Light Blue Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.7", "Light Gray Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.8", "Lime Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.9", "Magenta Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.10", "Orange Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.11", "Pink Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.12", "Purple Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.13", "Red Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.14", "White Body");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.15", "Yellow Body");

        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.0", "Black Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.1", "Blue Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.2", "Brown Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.3", "Cyan Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.4", "Gray Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.5", "Green Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.6", "Light Blue Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.7", "Light Gray Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.8", "Lime Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.9", "Magenta Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.10", "Orange Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.11", "Pink Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.12", "Purple Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.13", "Red Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.14", "White Wings");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.15", "Yellow Wings");

        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.0", "Black");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.1", "Blue");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.2", "Brown");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.3", "Cyan");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.4", "Gray");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.5", "Green");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.6", "Light Blue");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.7", "Light Gray");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.8", "Lime");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.9", "Magenta");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.10", "Orange");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.11", "Pink");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.12", "Purple");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.13", "Red");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.14", "White");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.15", "Yellow");

        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.stripe", "Stripes");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.tailshade", "Tail");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.topshade", "Back");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.halfshade", "Duality");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.large_stripe", "Large Stripes");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.racing_stripe", "Racing Stripe");
        add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.large_racing_stripe", "Large Racing Stripe");

        // Block entities
        blockEntity("extractor", "                          Extractor");
        blockEntity("extractor_jei", "Extractor");
        blockEntity("cultivator", "Cultivator");
        blockEntity("cultivator_jei", "Cultivator");
    }

    private void forBlock(Supplier<? extends Block> block) {
        addBlock(block, UP2TextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())).getPath()));
    }

    private void forItem(Supplier<? extends Item> item) {
        addItem(item, UP2TextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get())).getPath()));
    }

    private void forEntity(Supplier<? extends EntityType<?>> entity) {
        addEntityType(entity, UP2TextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entity.get())).getPath()));
    }

    private String format(ResourceLocation registryName) {
        return WordUtils.capitalizeFully(registryName.getPath().replace("_", " "));
    }

    protected void painting(String name, String author) {
        add("painting." + UnusualPrehistory2.MOD_ID + "." + name + ".title",  UP2TextUtils.createTranslation(name));
        add("painting." + UnusualPrehistory2.MOD_ID + "." + name + ".author",  author);
    }

    protected void musicDisc(Supplier<? extends Item> item, String description) {
        String disc = item.get().getDescriptionId();
        add(disc, "Music Disc");
        add(disc + ".desc", description);
    }

    public void creativeTab(CreativeModeTab key, String name){
        add(key.getDisplayName().getString(), name);
    }

    public void sound(Supplier<? extends SoundEvent> key, String subtitle){
        add("subtitles." + key.get().getLocation().getPath(), subtitle);
    }

    public void blockEntity(String beName,String name){
        add(UnusualPrehistory2.MOD_ID + ".blockentity." + beName, name);
    }

    private void dnaItem(Item... items) {
        List.of(items).forEach((item -> this.add(item, "Bottle of " + format(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item))).replace(" Dna Bottle", "") + " DNA")));
    }
}
