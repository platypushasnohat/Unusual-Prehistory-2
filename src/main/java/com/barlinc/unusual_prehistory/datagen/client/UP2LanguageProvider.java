package com.barlinc.unusual_prehistory.datagen.client;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.UnusualPrehistory2Tab;
import com.barlinc.unusual_prehistory.registry.*;
import com.barlinc.unusual_prehistory.utils.UP2TextUtils;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Objects;
import java.util.function.Supplier;

public class UP2LanguageProvider extends LanguageProvider {

    public UP2LanguageProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), UnusualPrehistory2.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        // creative tab
        creativeTab(UnusualPrehistory2Tab.UNUSUAL_PREHISTORY_2_TAB.get(), "Unusual Prehistory");

        // blocks
        UP2Blocks.BLOCK_TRANSLATIONS.forEach(this::forBlock);

        // items
        UP2Items.ITEM_TRANSLATIONS.forEach(this::forItem);

        // Entities
        UP2Entities.ENTITY_TRANSLATIONS.forEach(this::forEntity);

        // Update 1
        this.addItem(UP2Items.DIPLOCAULUS_BUCKET, "Bucket of Diplocaulus");
        this.addItem(UP2Items.DUNKLEOSTEUS_BUCKET, "Bucket of Dunkleosteus");
        this.addItem(UP2Items.JAWLESS_FISH_BUCKET, "Bucket of Jawless Fish");
        this.addItem(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE, "Bottle of Kimmeridgebrachypteraeschnidium");
        this.addItem(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET, "Bucket of Kimmeridgebrachypteraeschnidium Nymph");
        this.addItem(UP2Items.STETHACANTHUS_BUCKET, "Bucket of Stethacanthus");

        this.addItem(UP2Items.GINKGO_CHEST_BOAT, "Ginkgo Boat with Chest");
        this.addItem(UP2Items.LEPIDODENDRON_CHEST_BOAT, "Lepidodendron Boat with Chest");

        this.translateBannerPattern("carnotaurus");
        this.translateBannerPattern("diplocaulus");
        this.translateBannerPattern("dromaeosaurus");
        this.translateBannerPattern("dunkleosteus");
        this.translateBannerPattern("jawless_fish");
        this.translateBannerPattern("kentrosaurus");
        this.translateBannerPattern("kimmeridgebrachypteraeschnidium");
        this.translateBannerPattern("majungasaurus");
        this.translateBannerPattern("megalania");
        this.translateBannerPattern("stethacanthus");
        this.translateBannerPattern("talpanas");
        this.translateBannerPattern("telecrex");

        this.sound(UP2SoundEvents.TAR_POP, "Tar pops");

        this.sound(UP2SoundEvents.CARNOTAURUS_STEP, "Carnotaurus steps");
        this.sound(UP2SoundEvents.CARNOTAURUS_HURT, "Carnotaurus hurts");
        this.sound(UP2SoundEvents.CARNOTAURUS_DEATH, "Carnotaurus dies");
        this.sound(UP2SoundEvents.CARNOTAURUS_IDLE, "Carnotaurus grumbles");
        this.sound(UP2SoundEvents.CARNOTAURUS_SNIFF, "Carnotaurus sniffs");
        this.sound(UP2SoundEvents.CARNOTAURUS_BITE, "Carnotaurus bites");
        this.sound(UP2SoundEvents.CARNOTAURUS_CHARGE, "Carnotaurus charges");
        this.sound(UP2SoundEvents.CARNOTAURUS_HEADBUTT, "Carnotaurus headbutts");
        this.sound(UP2SoundEvents.CARNOTAURUS_HEADBUTT_VOCAL, "Carnotaurus prepares headbutt");
        this.sound(UP2SoundEvents.CARNOTAURUS_ROAR, "Carnotaurus roars");

        this.sound(UP2SoundEvents.DIPLOCAULUS_HURT, "Diplocaulus hurts");
        this.sound(UP2SoundEvents.DIPLOCAULUS_DEATH, "Diplocaulus dies");
        this.sound(UP2SoundEvents.DIPLOCAULUS_IDLE, "Diplocaulus croaks");
        this.sound(UP2SoundEvents.DIPLOCAULUS_STEP, "Diplocaulus steps");

        this.sound(UP2SoundEvents.DROMAEOSAURUS_HURT, "Dromaeosaurus hurts");
        this.sound(UP2SoundEvents.DROMAEOSAURUS_DEATH, "Dromaeosaurus dies");
        this.sound(UP2SoundEvents.DROMAEOSAURUS_IDLE, "Dromaeosaurus chatters");
        this.sound(UP2SoundEvents.DROMAEOSAURUS_EEPY, "Dromaeosaurus snores");

        this.sound(UP2SoundEvents.DUNKLEOSTEUS_HURT, "Dunkleosteus hurts");
        this.sound(UP2SoundEvents.DUNKLEOSTEUS_DEATH, "Dunkleosteus dies");
        this.sound(UP2SoundEvents.DUNKLEOSTEUS_FLOP, "Dunkleosteus flops");
        this.sound(UP2SoundEvents.SMALL_DUNKLEOSTEUS_BITE, "Dunkleosteus nibbles");
        this.sound(UP2SoundEvents.MEDIUM_DUNKLEOSTEUS_BITE, "Dunkleosteus chomps");
        this.sound(UP2SoundEvents.LARGE_DUNKLEOSTEUS_BITE, "Dunkleosteus crushes");

        this.sound(UP2SoundEvents.JAWLESS_FISH_HURT, "Jawless Fish hurts");
        this.sound(UP2SoundEvents.JAWLESS_FISH_DEATH, "Jawless Fish dies");
        this.sound(UP2SoundEvents.JAWLESS_FISH_FLOP, "Jawless Fish flops");

        this.sound(UP2SoundEvents.KENTROSAURUS_HURT, "Kentrosaurus hurts");
        this.sound(UP2SoundEvents.KENTROSAURUS_DEATH, "Kentrosaurus dies");
        this.sound(UP2SoundEvents.KENTROSAURUS_IDLE, "Kentrosaurus groans");
        this.sound(UP2SoundEvents.KENTROSAURUS_STEP, "Kentrosaurus steps");

        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT, "Kimmeridgebrachypteraeschnidium hurts");
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH, "Kimmeridgebrachypteraeschnidium dies");
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_LOOP, "Kimmeridgebrachypteraeschnidium buzzes");

        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_HURT, "Kimmeridgebrachypteraeschnidium Nymph hurts");
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_DEATH, "Kimmeridgebrachypteraeschnidium Nymph dies");

        this.sound(UP2SoundEvents.MAJUNGASAURUS_HURT, "Majungasaurus hurts");
        this.sound(UP2SoundEvents.MAJUNGASAURUS_DEATH, "Majungasaurus dies");
        this.sound(UP2SoundEvents.MAJUNGASAURUS_IDLE, "Majungasaurus groans");
        this.sound(UP2SoundEvents.MAJUNGASAURUS_ATTACK, "Majungasaurus bites");
        this.sound(UP2SoundEvents.MAJUNGASAURUS_SNIFF, "Majungasaurus sniffs");
        this.sound(UP2SoundEvents.MAJUNGASAURUS_STEP, "Majungasaurus steps");

        this.sound(UP2SoundEvents.MEGALANIA_HURT, "Megalania hurts");
        this.sound(UP2SoundEvents.MEGALANIA_DEATH, "Megalania dies");
        this.sound(UP2SoundEvents.MEGALANIA_IDLE, "Megalania hisses");
        this.sound(UP2SoundEvents.MEGALANIA_ROAR, "Megalania roars");
        this.sound(UP2SoundEvents.MEGALANIA_STEP, "Megalania steps");
        this.sound(UP2SoundEvents.MEGALANIA_TAIL_SWING, "Megalania swings tail");
        this.sound(UP2SoundEvents.MEGALANIA_BITE, "Megalania bites");

        this.sound(UP2SoundEvents.STETHACANTHUS_HURT, "Stethacanthus hurts");
        this.sound(UP2SoundEvents.STETHACANTHUS_DEATH, "Stethacanthus dies");
        this.sound(UP2SoundEvents.STETHACANTHUS_FLOP, "Stethacanthus flops");
        this.sound(UP2SoundEvents.STETHACANTHUS_BITE, "Stethacanthus chomps");

        this.sound(UP2SoundEvents.TALPANAS_HURT, "Talpanas hurts");
        this.sound(UP2SoundEvents.TALPANAS_DEATH, "Talpanas dies");
        this.sound(UP2SoundEvents.TALPANAS_IDLE, "Talpanas quacks");

        this.sound(UP2SoundEvents.TELECREX_HURT, "Telecrex hurts");
        this.sound(UP2SoundEvents.TELECREX_DEATH, "Telecrex dies");
        this.sound(UP2SoundEvents.TELECREX_IDLE, "Telecrex squawks");

        this.sound(UP2SoundEvents.UNICORN_HURT, "Unicorn hurts");
        this.sound(UP2SoundEvents.UNICORN_DEATH, "Unicorn dies");
        this.sound(UP2SoundEvents.UNICORN_IDLE, "Unicorn grunts");

        this.sound(UP2SoundEvents.TRANSMOGRIFIER_LOOP, "Transmogrifying");
        this.sound(UP2SoundEvents.TRANSMOGRIFIER_START, "Transmogrifier starts");
        this.sound(UP2SoundEvents.TRANSMOGRIFIER_STOP, "Transmogrifier stops");

        this.sound(UP2SoundEvents.TARIFYING_DISC, "Music Disc");
        this.musicDisc(UP2Items.TARIFYING_DISC, UP2JukeboxSongs.TARIFYING, "Dylanvhs - Tarifying");

        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_0", "Black Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_1", "Blue Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_2", "Brown Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_3", "Cyan Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_4", "Gray Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_5", "Green Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_6", "Light Blue Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_7", "Light Gray Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_8", "Lime Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_9", "Magenta Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_10", "Orange Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_11", "Pink Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_12", "Purple Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_13", "Red Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_14", "White Body");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_15", "Yellow Body");

        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_0", "Black Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_1", "Blue Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_2", "Brown Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_3", "Cyan Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_4", "Gray Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_5", "Green Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_6", "Light Blue Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_7", "Light Gray Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_8", "Lime Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_9", "Magenta Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_10", "Orange Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_11", "Pink Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_12", "Purple Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_13", "Red Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_14", "White Wings");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_15", "Yellow Wings");

        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_0", "Black");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_1", "Blue");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_2", "Brown");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_3", "Cyan");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_4", "Gray");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_5", "Green");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_6", "Light Blue");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_7", "Light Gray");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_8", "Lime");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_9", "Magenta");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_10", "Orange");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_11", "Pink");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_12", "Purple");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_13", "Red");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_14", "White");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_15", "Yellow");

        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_stripe", "Stripes");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_tailshade", "Tail");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_topshade", "Back");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_halfshade", "Duality");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_large_stripe", "Large Stripes");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_racing_stripe", "Racing Stripe");
        this.add("entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_large_racing_stripe", "Large Racing Stripe");

        this.add("entity.unusual_prehistory.jawless_fish.variant_arandaspis", "Arandaspis");
        this.add("entity.unusual_prehistory.jawless_fish.variant_cephalaspis", "Cephalaspis");
        this.add("entity.unusual_prehistory.jawless_fish.variant_doryaspis", "Doryaspis");
        this.add("entity.unusual_prehistory.jawless_fish.variant_furcacauda", "Furcacauda");
        this.add("entity.unusual_prehistory.jawless_fish.variant_sacabambaspis", "Sacabambaspis");

        this.add("entity.unusual_prehistory.diplocaulus.variant_tiger", "Tiger");
        this.add("entity.unusual_prehistory.diplocaulus.variant_swampy", "Swampy");
        this.add("entity.unusual_prehistory.diplocaulus.variant_muddy", "Muddy");
        this.add("entity.unusual_prehistory.diplocaulus.variant_dwarf", "Dwarf");

        this.add("entity.unusual_prehistory.dunkleosteus.variant_small", "Small");
        this.add("entity.unusual_prehistory.dunkleosteus.variant_medium", "Medium");
        this.add("entity.unusual_prehistory.dunkleosteus.variant_large", "Large");

        this.add("unusual_prehistory.jei.transmogrification", "Transmogrification");

        this.add("entity.unusual_prehistory.all.command_0", "%s is wandering");
        this.add("entity.unusual_prehistory.all.command_1", "%s is staying");
        this.add("entity.unusual_prehistory.all.command_2", "%s is following");

        // Update 2
        this.sound(UP2SoundEvents.ONCHOPRISTIS_HURT, "Onchopristis hurts");
        this.sound(UP2SoundEvents.ONCHOPRISTIS_DEATH, "Onchopristis dies");
        this.sound(UP2SoundEvents.ONCHOPRISTIS_FLOP, "Onchopristis flops");

        this.sound(UP2SoundEvents.DOOMSURF_DISC, "Music Disc");
        this.musicDisc(UP2Items.DOOMSURF_DISC, UP2JukeboxSongs.DOOMSURF, "ChipsTheCat - Doomsurf");

        this.sound(UP2SoundEvents.MEGALANIA_DISC, "Music Disc");
        this.musicDisc(UP2Items.MEGALANIA_DISC, UP2JukeboxSongs.MEGALANIA, "ValiantEnvoy - MEGALANIA");

        this.translateBannerPattern("onchopristis");

        // Update 3
        this.addItem(UP2Items.LIVING_OOZE_BUCKET, "Bucket of Living Ooze");

        this.sound(UP2SoundEvents.LIVING_OOZE_HURT, "Living Ooze hurts");
        this.sound(UP2SoundEvents.LIVING_OOZE_DEATH, "Living Ooze dies");
        this.sound(UP2SoundEvents.LIVING_OOZE_SQUISH, "Living Ooze squishes");
        this.sound(UP2SoundEvents.LIVING_OOZE_JUMP, "Living Ooze jumps");
        this.sound(UP2SoundEvents.LIVING_OOZE_SPIT, "Living Ooze spits");

        this.sound(UP2SoundEvents.METRIORHYNCHUS_HURT, "Metriorhynchus hurts");
        this.sound(UP2SoundEvents.METRIORHYNCHUS_DEATH, "Metriorhynchus dies");
        this.sound(UP2SoundEvents.METRIORHYNCHUS_IDLE, "Metriorhynchus hisses");
        this.sound(UP2SoundEvents.METRIORHYNCHUS_BITE, "Metriorhynchus bites");
        this.sound(UP2SoundEvents.METRIORHYNCHUS_BELLOW, "Metriorhynchus bellows");

        this.translateBannerPattern("ooze");
        this.translateBannerPattern("metriorhynchus");
        this.translateBannerPattern("tartuosteus");

        // Update 4
        this.addItem(UP2Items.COELACANTHUS_BUCKET, "Bucket of Coelacanthus");
        this.addItem(UP2Items.LOBE_FINNED_FISH_BUCKET, "Bucket of Lobe Finned Fish");
        this.addItem(UP2Items.PRAEPUSA_BUCKET, "Bucket of Praepusa");
        this.addItem(UP2Items.PTERODACTYLUS_POT, "Pot of Pterodactylus");

        this.addItem(UP2Items.DRYOPHYLLUM_CHEST_BOAT, "Dryophyllum Boat with Chest");
        this.addItem(UP2Items.METASEQUOIA_CHEST_BOAT, "Metasequoia Boat with Chest");

        this.addItem(UP2Items.DIRT_ON_A_STICK, "Dirt on a Stick");

        this.translateBannerPattern("brachiosaurus");
        this.translateBannerPattern("coelacanthus");
        this.translateBannerPattern("hibbertopterus");
        this.translateBannerPattern("kaprosuchus");
        this.translateBannerPattern("leptictidium");
        this.translateBannerPattern("lobe_finned_fish");
        this.translateBannerPattern("lystrosaurus");
        this.translateBannerPattern("pachycephalosaurus");
        this.translateBannerPattern("praepusa");
        this.translateBannerPattern("pterodactylus");
        this.translateBannerPattern("ulughbegsaurus");

        this.sound(UP2SoundEvents.BRACHIOSAURUS_HURT, "Brachiosaurus hurts");
        this.sound(UP2SoundEvents.BRACHIOSAURUS_DEATH, "Brachiosaurus dies");
        this.sound(UP2SoundEvents.BRACHIOSAURUS_IDLE, "Brachiosaurus rumbles");
        this.sound(UP2SoundEvents.BRACHIOSAURUS_ATTACK, "Brachiosaurus attacks");
        this.sound(UP2SoundEvents.BRACHIOSAURUS_STEP, "Brachiosaurus stomps");
        this.sound(UP2SoundEvents.BRACHIOSAURUS_CALL, "Brachiosaurus calls");

        this.sound(UP2SoundEvents.COELACANTHUS_DEATH, "Coelacanthus hurts");
        this.sound(UP2SoundEvents.COELACANTHUS_HURT, "Coelacanthus dies");
        this.sound(UP2SoundEvents.COELACANTHUS_FLOP, "Coelacanthus flops");

        this.sound(UP2SoundEvents.HIBBERTOPTERUS_HURT, "Hibbertopterus hurts");
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_DEATH, "Hibbertopterus dies");
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_IDLE, "Hibbertopterus rattles");
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_STEP, "Hibbertopterus steps");

        this.sound(UP2SoundEvents.KAPROSUCHUS_HURT, "Kaprosuchus hurts");
        this.sound(UP2SoundEvents.KAPROSUCHUS_DEATH, "Kaprosuchus dies");
        this.sound(UP2SoundEvents.KAPROSUCHUS_IDLE, "Kaprosuchus hisses");

        this.sound(UP2SoundEvents.LEPTICTIDIUM_HURT, "Leptictidium hurts");
        this.sound(UP2SoundEvents.LEPTICTIDIUM_DEATH, "Leptictidium dies");
        this.sound(UP2SoundEvents.LEPTICTIDIUM_IDLE, "Leptictidium squeaks");

        this.sound(UP2SoundEvents.LYSTROSAURUS_HURT, "Lystrosaurus hurts");
        this.sound(UP2SoundEvents.LYSTROSAURUS_DEATH, "Lystrosaurus dies");
        this.sound(UP2SoundEvents.LYSTROSAURUS_IDLE, "Lystrosaurus oinks");
        this.sound(UP2SoundEvents.LYSTROSAURUS_STEP, "Lystrosaurus steps");
        this.sound(UP2SoundEvents.LYSTROSAURUS_CHAINSMOKER, "Lystrosaurus coughs");

        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_HURT, "Pachycephalosaurus hurts");
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_DEATH, "Pachycephalosaurus dies");
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_IDLE, "Pachycephalosaurus grunts");
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_WARN, "Pachycephalosaurus warns");
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_STEP, "Pachycephalosaurus steps");
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_BONK, "Pachycephalosaurus bonks");

        this.sound(UP2SoundEvents.PRAEPUSA_HURT, "Praepusa hurts");
        this.sound(UP2SoundEvents.PRAEPUSA_DEATH, "Praepusa dies");
        this.sound(UP2SoundEvents.PRAEPUSA_IDLE, "Praepusa honks");
        this.sound(UP2SoundEvents.PRAEPUSA_MITOSIS, "Praepusa mitoses");
        this.sound(UP2SoundEvents.PRAEPUSA_BOUNCE, "Praepusa bounces");

        this.sound(UP2SoundEvents.PTERODACTYLUS_HURT, "Pterodactylus hurts");
        this.sound(UP2SoundEvents.PTERODACTYLUS_DEATH, "Pterodactylus dies");
        this.sound(UP2SoundEvents.PTERODACTYLUS_IDLE, "Pterodactylus chirps");

        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_HURT, "Ulughbegsaurus hurts");
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_DEATH, "Ulughbegsaurus dies");
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_IDLE, "Ulughbegsaurus groans");
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_ATTACK, "Ulughbegsaurus bites");
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_STEP, "Ulughbegsaurus steps");

        this.add("entity.unusual_prehistory.coelacanthus.variant_fishy", "Fishy");
        this.add("entity.unusual_prehistory.coelacanthus.variant_golden", "Golden");
        this.add("entity.unusual_prehistory.coelacanthus.variant_lilac", "Lilac");
        this.add("entity.unusual_prehistory.coelacanthus.variant_peach", "Peach");
        this.add("entity.unusual_prehistory.coelacanthus.variant_rose", "Rose");
        this.add("entity.unusual_prehistory.coelacanthus.variant_silver", "Silver");
        this.add("entity.unusual_prehistory.coelacanthus.variant_blue", "Blue");
        this.add("entity.unusual_prehistory.coelacanthus.size", "Size: %s");

        this.add("entity.unusual_prehistory.lobe_finned_fish.variant_allenypterus", "Allenypterus");
        this.add("entity.unusual_prehistory.lobe_finned_fish.variant_eusthenopteron", "Eusthenopteron");
        this.add("entity.unusual_prehistory.lobe_finned_fish.variant_gooloogongia", "Gooloogongia");
        this.add("entity.unusual_prehistory.lobe_finned_fish.variant_laccognathus", "Laccognathus");
        this.add("entity.unusual_prehistory.lobe_finned_fish.variant_scaumenacia", "Scaumenacia");

        this.add("entity.unusual_prehistory.pterodactylus.variant_brown", "Brown");
        this.add("entity.unusual_prehistory.pterodactylus.variant_banana", "Banana");

        // Update 5
        this.addItem(UP2Items.BABY_AEGIROCASSIS_BUCKET, "Bucket of Baby Aegirocassis");

        this.addItem(UP2Items.UNKNOWN_FOSSIL, "??? Fossil");
        this.addItem(UP2Items.UNKNOWN_EGG, "??? Egg");

        this.translateBannerPatternItem(UP2Items.PALEOZOIC_BANNER_PATTERN, "Paleozoic Creatures");
        this.translateBannerPatternItem(UP2Items.MESOZOIC_BANNER_PATTERN, "Mesozoic Creatures");
        this.translateBannerPatternItem(UP2Items.CENOZOIC_BANNER_PATTERN, "Cenozoic Creatures");
        this.translateBannerPatternItem(UP2Items.OOZE_BANNER_PATTERN, "Ooze");

        this.translateBannerPattern("aegirocassis");
        this.translateBannerPattern("desmatosuchus");
        this.translateBannerPattern("psilopterus");

        this.sound(UP2SoundEvents.PUMMEL_AND_SNATCH_DISC, "Music Disc");
        this.musicDisc(UP2Items.PUMMEL_AND_SNATCH_DISC, UP2JukeboxSongs.PUMMEL_AND_SNATCH, "ChipsTheCat - Pummel and Snatch");

        this.sound(UP2SoundEvents.AEGIROCASSIS_HURT, "Aegirocassis hurts");
        this.sound(UP2SoundEvents.AEGIROCASSIS_DEATH, "Aegirocassis dies");
        this.sound(UP2SoundEvents.AEGIROCASSIS_IDLE, "Aegirocassis rumbles");
        this.sound(UP2SoundEvents.AEGIROCASSIS_HOVER, "Aegirocassis tries to fly");

        this.sound(UP2SoundEvents.DESMATOSUCHUS_HURT, "Desmatosuchus hurts");
        this.sound(UP2SoundEvents.DESMATOSUCHUS_DEATH, "Desmatosuchus dies");
        this.sound(UP2SoundEvents.DESMATOSUCHUS_IDLE, "Desmatosuchus groans");
        this.sound(UP2SoundEvents.DESMATOSUCHUS_STEP, "Desmatosuchus steps");

        this.sound(UP2SoundEvents.PSILOPTERUS_HURT, "Psilopterus hurts");
        this.sound(UP2SoundEvents.PSILOPTERUS_DEATH, "Psilopterus dies");
        this.sound(UP2SoundEvents.PSILOPTERUS_IDLE, "Psilopterus squawks");
        this.sound(UP2SoundEvents.PSILOPTERUS_ATTACK, "Psilopterus attacks");
        this.sound(UP2SoundEvents.PSILOPTERUS_BITE, "Psilopterus bites");
        this.sound(UP2SoundEvents.PSILOPTERUS_CALL, "Psilopterus calls");

        this.sound(UP2SoundEvents.GRUG_HURT, "Grug hurts");
        this.sound(UP2SoundEvents.GRUG_DEATH, "Grug dies");
        this.sound(UP2SoundEvents.GRUG_IDLE, "Grug speaks his wisdom");
        this.sound(UP2SoundEvents.GRUG_CHASE, "Grug runs");

        // Update 6
        this.sound(UP2SoundEvents.COTYLORHYNCHUS_HURT, "Cotylorhynchus hurts");
        this.sound(UP2SoundEvents.COTYLORHYNCHUS_DEATH, "Cotylorhynchus dies");
        this.sound(UP2SoundEvents.COTYLORHYNCHUS_IDLE, "Cotylorhynchus grumbles");
        this.sound(UP2SoundEvents.COTYLORHYNCHUS_STEP, "Cotylorhynchus steps");
        this.sound(UP2SoundEvents.COTYLORHYNCHUS_BURP, "Cotylorhynchus burps");

        this.sound(UP2SoundEvents.PROGNATHODON_HURT, "Prognathodon hurts");
        this.sound(UP2SoundEvents.PROGNATHODON_DEATH, "Prognathodon dies");
        this.sound(UP2SoundEvents.PROGNATHODON_IDLE, "Prognathodon bellows");
        this.sound(UP2SoundEvents.PROGNATHODON_ATTACK, "Prognathodon attacks");

        this.translateBannerPattern("prognathodon");

        // Future
        this.addItem(UP2Items.PETRIFIED_LUCA, "Petrified L.U.C.A");
        this.addItem(UP2Items.LUCA, "L.U.C.A");

        this.sound(UP2SoundEvents.BARINASUCHUS_HURT, "Barinasuchus hurts");
        this.sound(UP2SoundEvents.BARINASUCHUS_DEATH, "Barinasuchus dies");
        this.sound(UP2SoundEvents.BARINASUCHUS_IDLE, "Barinasuchus grumbles");
        this.sound(UP2SoundEvents.BARINASUCHUS_ATTACK, "Barinasuchus snaps");
        this.sound(UP2SoundEvents.BARINASUCHUS_THREATEN, "Barinasuchus rumbles");

        this.sound(UP2SoundEvents.DIMORPHODON_HURT, "Dimorphodon hurts");
        this.sound(UP2SoundEvents.DIMORPHODON_DEATH, "Dimorphodon dies");
        this.sound(UP2SoundEvents.DIMORPHODON_IDLE, "Dimorphodon chatters");

        this.sound(UP2SoundEvents.MANIPULATOR_HURT, "Manipulator hurts");
        this.sound(UP2SoundEvents.MANIPULATOR_DEATH, "Manipulator dies");
        this.sound(UP2SoundEvents.MANIPULATOR_IDLE, "Manipulator chatters");
        this.sound(UP2SoundEvents.MANIPULATOR_STEP, "Manipulator steps");
        this.sound(UP2SoundEvents.MANIPULATOR_ATTACK_VOCAL, "Manipulator prepares attack");
        this.sound(UP2SoundEvents.MANIPULATOR_ATTACK_SLASH, "Manipulator slashes");

        this.sound(UP2SoundEvents.THERIZINOSAURUS_HURT, "Therizinosaurus hurts");
        this.sound(UP2SoundEvents.THERIZINOSAURUS_DEATH, "Therizinosaurus dies");
        this.sound(UP2SoundEvents.THERIZINOSAURUS_IDLE, "Therizinosaurus squawks");
        this.sound(UP2SoundEvents.THERIZINOSAURUS_ATTACK, "Therizinosaurus slices");
        this.sound(UP2SoundEvents.THERIZINOSAURUS_STEP, "Therizinosaurus steps");
        this.sound(UP2SoundEvents.THERIZINOSAURUS_ROAR, "Therizinosaurus screams");
        this.sound(UP2SoundEvents.THERIZINOSAURUS_NOTICE, "Therizinosaurus takes notice");

        this.sound(UP2SoundEvents.BUG_HURT, "Bug hurts");
        this.sound(UP2SoundEvents.BUG_DEATH, "Bug dies");
        this.sound(UP2SoundEvents.BUG_BUZZ, "Bug buzzes");

        // Progression & Misc Advancements
        this.translateAdvancement("root", "Unusual Prehistory", "Revive creatures from the ancient past!");
        this.translateAdvancement("obtain_fossil", "Rock and Bone", "Use a Brush to uncover fossils at a Fossil Site or Tar Pit");
        this.translateAdvancement("obtain_machine_parts", "Electrical Doodads", "Find some Machine Parts in a loot chest");
        this.translateAdvancement("obtain_transmogrifier", "Jesse, We Have to Cook", "Craft a Transmogrifier, the key component in creature revival");
        this.translateAdvancement("obtain_organic_ooze", "It's Looking at Me...", "Craft some Organic Ooze to fuel the revival process");
        this.translateAdvancement("obtain_egg", "E G G S", "Recreate your first prehistoric egg or embryo");
        this.translateAdvancement("obtain_living_ooze", "Alakagoo!", "Create a Living Ooze in a cauldron to incubate embryos with");

        this.translateAdvancement("pacify_mob", "Chill Pill", "Feed an Enchanted Golden Apple to an aggressive creature to make it permanently neutral");
        this.translateAdvancement("breed_holocene_mobs", "Repopulation!", "Breed a pair of Holocene animals");
        this.translateAdvancement("revive_all_mobs", "Unusual Prehistorian", "Revive all known prehistoric creatures");

        this.translateAdvancement("open_book_creative", "Entry Unlocker", "Used to allow entry unlocking in the Paleopedia");

        // Revival Advancements
        this.translateAdvancement("paleozoic_root", "Paleozoic Era", "Paleozoic era creatures");
        this.translateAdvancement("ordovician_root", "Ordovician Period", "Ordovician period creatures");
        this.translateAdvancement("silurian_root", "Silurian Period", "Silurian period creatures");
        this.translateAdvancement("devonian_root", "Devonian Period", "Devonian period creatures");
        this.translateAdvancement("carboniferous_root", "Carboniferous Period", "Carboniferous period creatures");
        this.translateAdvancement("permian_root", "Permian Period", "Permian period creatures");

        this.translateAdvancement("mesozoic_root", "Mesozoic Era", "Mesozoic era creatures");
        this.translateAdvancement("triassic_root", "Triassic Period", "Triassic period creatures");
        this.translateAdvancement("jurassic_root", "Jurassic Period", "Jurassic period creatures");
        this.translateAdvancement("cretaceous_root", "Cretaceous Period", "Cretaceous period creatures");

        this.translateAdvancement("cenozoic_root", "Cenozoic Era", "Cenozoic era creatures");
        this.translateAdvancement("paleogene_root", "Paleogene Period", "Paleogene period creatures");
        this.translateAdvancement("neogene_root", "Neogene Period", "Neogene period creatures");
        this.translateAdvancement("quaternary_root", "Quaternary Period", "Quaternary period creatures");
        this.translateAdvancement("holocene_root", "Holocene Epoch", "Holocene epoch creatures");

        this.translateAdvancement("revive_aegirocassis", "Vessel of God", "Revive an Aegirocassis");
        this.translateAdvancement("revive_barinasuchus", "Croc and Roll", "Revive a Barinasuchus");
        this.translateAdvancement("revive_brachiosaurus", "Time of the Titans", "Revive a Brachiosaurus");
        this.translateAdvancement("revive_carnotaurus", "Endless Fury", "Revive a Carnotaurus");
        this.translateAdvancement("revive_coelacanthus", "Fishy", "Revive a Coelacanthus");
        this.translateAdvancement("revive_desmatosuchus", "Flat Back", "Revive a Desmatosuchus");
        this.translateAdvancement("revive_dimorphodon", "Up Up and Away!", "Revive a Dimorphodon");
        this.translateAdvancement("revive_diplocaulus", "Comes Right Back", "Revive a Diplocaulus");
        this.translateAdvancement("revive_dromaeosaurus", "Dino Run", "Revive a Dromaeosaurus");
        this.translateAdvancement("revive_dunkleosteus", "Definitely Not a Shark", "Revive a Dunkleosteus");
        this.translateAdvancement("revive_hibbertopterus", "No Thoughts, Head Empty", "Revive a Hibbertopterus");
        this.translateAdvancement("revive_jawless_fish", "No Chewing For You", "Revive a Jawless Fish");
        this.translateAdvancement("revive_kaprosuchus", "Boar Croc", "Revive a Kaprosuchus");
        this.translateAdvancement("revive_kentrosaurus", "Extra Pointy!", "Revive a Kentrosaurus");
        this.translateAdvancement("revive_kimmeridgebrachypteraeschnidium", "The man on the street will not be able to remember this.", "Revive a Kimmeridgebrachypteraeschnidium");
        this.translateAdvancement("revive_leptictidium", "Plague Carriers", "Revive a Leptictidium");
        this.translateAdvancement("revive_lobe_finned_fish", "Shark Bait, Hoo Ha Ha!", "Revive a Lobe Finned Fish");
        this.translateAdvancement("revive_lystrosaurus", "Survivalist", "Revive a Lystrosaurus");
        this.translateAdvancement("revive_majungasaurus", "Camouflaging Cannibal", "Revive a Majungasaurus");
        this.translateAdvancement("revive_manipulator", "Mandibles", "Revive a Manipulator");
        this.translateAdvancement("revive_megalania", "The Giant Goanna", "Revive a Megalania");
        this.translateAdvancement("revive_metriorhynchus", "The Meltdown", "Revive a Metriorhynchus");
        this.translateAdvancement("revive_prognathodon", "???", "Revive a Prognathodon");
        this.translateAdvancement("revive_onchopristis", "Cretaceous Chainsaw Massacre", "Revive an Onchopristis");
        this.translateAdvancement("revive_pachycephalosaurus", "Thick-Headed", "Revive a Pachycephalosaurus");
        this.translateAdvancement("revive_praepusa", "Cumulative Cuteness", "Revive a Praepusa");
        this.translateAdvancement("revive_psilopterus", "Clever Girl", "Revive a Psilopterus");
        this.translateAdvancement("revive_pterodactylus", "Honey I Shrunk the Pterosaur", "Revive a Pterodactylus");
        this.translateAdvancement("revive_stethacanthus", "Not Quite a Shark", "Revive a Stethacanthus");
        this.translateAdvancement("revive_talpanas", "Blind as a Duck", "Revive a Talpanas");
        this.translateAdvancement("revive_tartuosteus", "Thinkin’ Bout Moss Balls", "Revive a Tartuosteus");
        this.translateAdvancement("revive_telecrex", "From a Singular Femur", "Revive a Telecrex");
        this.translateAdvancement("revive_therizinosaurus", "Tickle Chicken", "Revive a Therizinosaurus");
        this.translateAdvancement("revive_ulughbegsaurus", "How Do You Pronounce That?", "Revive an Ulughbegsaurus");

        this.translateEffect(UP2MobEffects.FURY, "Gain increased speed and attack speed as your health gets lower");

        // Book
        this.add("item.unusual_prehistory.paleopedia.landing", "This book acts as a guide to the revival process of various ancient plants and animals, along with any notable traits or uses that they may have.");
        this.add("item.unusual_prehistory.paleopedia.desc", "By Professor Peeko Noneyah Jr.");

        this.add("item.unusual_prehistory.paleopedia.recipe_title", "");

        //Questline
        this.add("item.unusual_prehistory.paleopedia.entry.getting_started.obtain_fossil.title", "Fossil Hunting");
        this.add("item.unusual_prehistory.paleopedia.entry.getting_started.obtain_fossil.desc", "One of the first steps in your journey is to find $(l:unusual_prehistory:features/world)Fossil Sites$() underground or on the surface. Fossils can be obtained using a $(highlight)Brush$() on suspicious blocks in these sites.");

        this.add("item.unusual_prehistory.paleopedia.entry.getting_started.obtain_machine_parts.title", "Ancient Machines");
        this.add("item.unusual_prehistory.paleopedia.entry.getting_started.obtain_machine_parts.desc", "Finding $(l:unusual_prehistory:features/blocks_items/machine_parts)Machine Parts$() is essential to the revival process.");

        this.add("item.unusual_prehistory.paleopedia.entry.getting_started.obtain_organic_ooze.title", "Goo is Fuel");
        this.add("item.unusual_prehistory.paleopedia.entry.getting_started.obtain_organic_ooze.desc", "The $(l:unusual_prehistory:features/blocks_items/transmogrifier)Transmogrifier$() is fueled with Organic Ooze. Take care to make more for reviving larger creatures!");

        this.add("item.unusual_prehistory.paleopedia.entry.getting_started.obtain_transmogrifier.title", "Transmogrification");
        this.add("item.unusual_prehistory.paleopedia.entry.getting_started.obtain_transmogrifier.desc", "Using $(l:unusual_prehistory:features/blocks_items/machine_parts)Machine Parts$(), one can create a $(l:unusual_prehistory:features/blocks_items/transmogrifier)Transmogrifier$() fueled by $(l:unusual_prehistory:features/mobs/organic_ooze)Organic Ooze$().");

        this.add("item.unusual_prehistory.paleopedia.entry.getting_started.obtain_egg.title", "Life Finds a Way");
        this.add("item.unusual_prehistory.paleopedia.entry.getting_started.obtain_egg.desc", "Using the process of transmogrification, new life may be created from a fossil. $(l:unusual_prehistory:features/miscellaneous/incubation)Incubate$(/l) your newly made egg and let extinct creatures roam the Overworld once more.");

        //Features
        this.add("item.unusual_prehistory.paleopedia.entry.machine_parts.desc", "Machine Parts can be found within $(highlight)Dungeons$(), $(highlight)Jungle Temples$(), $(highlight)Desert Pyramids$() or $(highlight)Mineshafts$().");
        this.add("item.unusual_prehistory.paleopedia.entry.organic_ooze.desc", "Organic Ooze is used as fuel for the $(l:unusual_prehistory:features/blocks_items/transmogrifier)Transmogrifier$(/l), and also to make $(l:unusual_prehistory:features/mobs/living_ooze)Living Ooze$(/l).");
        this.add("item.unusual_prehistory.paleopedia.entry.transmogrifier.desc", "The Transmogrifier is a machine used to transmogrify fossils into living organisms. It is fueled with $(l:unusual_prehistory:features/blocks_items/organic_ooze)Organic Ooze$(/l) and any fossil can be placed within it to begin conversion. Larger mobs require more fuel.");
        this.add("item.unusual_prehistory.paleopedia.entry.tar.desc", "Tar is found in $(l:unusual_prehistory:features/world/tar_pit)Tar Pits$(/l). All mobs and the player sink in tar, and can suffocate if left under for too long. Tar can be picked up with a bucket.");
        this.add("item.unusual_prehistory.paleopedia.entry.asphalt.desc", "Asphalt forms when tar comes into contact with lava or water. Mobs on asphalt gain increased movement speed.");
        this.add("item.unusual_prehistory.paleopedia.entry.reinforced_glass.desc", "Reinforced Glass is blast-resistant and mob-resistant glass, and can be used to protect viewing areas from aggressive mobs.");

        //World
        this.add("item.unusual_prehistory.paleopedia.entry.mesozoic_fossil_site.desc", "Mesozoic Fossil Sites are encased large fossils found underground. They are surrounded by $(highlight)Mesonite$(). Excavating fossils from mesozoic fossil sites yields mesozoic fossils.");
        this.add("item.unusual_prehistory.paleopedia.entry.mesozoic_fossil_site.title", "Mesozoic Fossil Site");

        this.add("item.unusual_prehistory.paleopedia.entry.tar_pit.desc", "Tar Pits are found on the surface. They are pools of bubbling $(l:unusual_prehistory:features/blocks_items/tar)Tar$(/l), with a large fossil in their center. Excavating fossils from tar pits yields cenozoic fossils. ");
        this.add("item.unusual_prehistory.paleopedia.entry.tar_pit.title", "Tar Pit");

        this.add("item.unusual_prehistory.paleopedia.entry.paleozoic_fossil_site.desc", "Paleozoic Fossil Sites are encased large fossils found deep underground. They are surrounded by $(highlight)Paleostone$(). Excavating fossils from paleozoic fossil sites yields paleozoic fossils.");
        this.add("item.unusual_prehistory.paleopedia.entry.paleozoic_fossil_site.title", "Paleozoic Fossil Site");

        this.add("item.unusual_prehistory.paleopedia.entry.petrified_tree.desc", "Petrified Trees are structures found underground. They are encased in $(highlight)Floralite$(). Excavating fossils from petrified trees yields plant fossils.");
        this.add("item.unusual_prehistory.paleopedia.entry.petrified_tree.title", "Petrified Tree");

        this.add("item.unusual_prehistory.paleopedia.entry.holocene_artifact.desc", "Recently extinct mobs have left behind traces—artifacts—of themselves in the world. Use these remains to bring them back once more.");
        this.add("item.unusual_prehistory.paleopedia.entry.holocene_artifact.title", "Holocene Artifact");

        //Misc
        this.add("item.unusual_prehistory.paleopedia.entry.pacification.title", "Pacification");
        this.add("item.unusual_prehistory.paleopedia.entry.pacification.desc", "Aggressive mobs may be pacified for a little less than a day by feeding them their favorite foods. An $(highlight)Enchanted Golden Apple$() can be used to pacify mobs permanently.");

        this.add("item.unusual_prehistory.paleopedia.entry.incubation.title", "Incubation");

        this.add("item.unusual_prehistory.paleopedia.entry.incubation.raft_egg.title", "Raft Egg");
        this.add("item.unusual_prehistory.paleopedia.entry.incubation.raft_egg.desc", "Egg must be placed on-top of water to hatch.");
        this.add("item.unusual_prehistory.paleopedia.entry.incubation.raft_egg.tooltip", "Egg must be placed on-top of water to hatch");

        this.add("item.unusual_prehistory.paleopedia.entry.incubation.nest_egg.title", "Nest Egg");
        this.add("item.unusual_prehistory.paleopedia.entry.incubation.nest_egg.desc", "Egg must be placed down to hatch. $(highlight)Wool$(), $(highlight)Moss$() or $(highlight)Hay Bales$() may be placed under nest eggs to speed up hatching.");
        this.add("item.unusual_prehistory.paleopedia.entry.incubation.nest_egg.tooltip", "Egg must be placed down to hatch");

        this.add("item.unusual_prehistory.paleopedia.entry.incubation.projectile_egg.title", "Projectile Egg");
        this.add("item.unusual_prehistory.paleopedia.entry.incubation.projectile_egg.desc", "Egg must be thrown to hatch.");
        this.add("item.unusual_prehistory.paleopedia.entry.incubation.projectile_egg.tooltip", "Egg must be thrown to hatch");

        this.add("item.unusual_prehistory.paleopedia.entry.incubation.embryo.title", "Embryo");
        this.add("item.unusual_prehistory.paleopedia.entry.incubation.embryo.desc", "Embryos must be placed in $(l:unusual_prehistory:features/mobs/living_ooze)Living Ooze$(/l) to gestate.");
        this.add("item.unusual_prehistory.paleopedia.entry.incubation.embryo.tooltip", "Embryo must be placed in §aLiving Ooze§7to gestate");

        this.add("item.unusual_prehistory.paleopedia.entry.incubation.aquatic_egg.title", "Aquatic Egg");
        this.add("item.unusual_prehistory.paleopedia.entry.incubation.aquatic_egg.desc", "Egg must be placed underwater to hatch.");
        this.add("item.unusual_prehistory.paleopedia.entry.incubation.aquatic_egg.tooltip", "Egg must be placed underwater to hatch");

        this.add("item.unusual_prehistory.paleopedia.entry.activity.diurnal.title", "");
        this.add("item.unusual_prehistory.paleopedia.entry.activity.diurnal.tooltip", "");

        this.add("item.unusual_prehistory.paleopedia.entry.activity.nocturnal.title", "");
        this.add("item.unusual_prehistory.paleopedia.entry.activity.nocturnal.tooltip", "");

        this.add("item.unusual_prehistory.paleopedia.entry.activity.crepuscular.title", "");
        this.add("item.unusual_prehistory.paleopedia.entry.activity.crepuscular.tooltip", "");

        this.add("item.unusual_prehistory.paleopedia.entry.activity.cathemeral.title", "");
        this.add("item.unusual_prehistory.paleopedia.entry.activity.cathemeral.tooltip", "");

        this.add("item.unusual_prehistory.paleopedia.entry.activity.sleepless.title", "");
        this.add("item.unusual_prehistory.paleopedia.entry.activity.sleepless.tooltip", "");

        this.add("death.attack.unusual_prehistory.execute", "%s was executed by %s");

        this.add("death.attack.unusual_prehistory.grug", "%s got Grugged");
    }

    private void forBlock(Supplier<? extends Block> block) {
        this.addBlock(block, UP2TextUtils.createTranslation(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block.get())).getPath()));
    }

    private void forItem(Supplier<? extends Item> item) {
        this.addItem(item, UP2TextUtils.createTranslation(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item.get())).getPath()));
    }

    private void forEntity(Supplier<? extends EntityType<?>> entity) {
        this.addEntityType(entity, UP2TextUtils.createTranslation(Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(entity.get())).getPath()));
    }

    private String format(ResourceLocation registryName) {
        return WordUtils.capitalizeFully(registryName.getPath().replace("_", " "));
    }

    protected void painting(String name, String author) {
        this.add("painting." + UnusualPrehistory2.MOD_ID + "." + name + ".title",  UP2TextUtils.createTranslation(name));
        this.add("painting." + UnusualPrehistory2.MOD_ID + "." + name + ".author",  author);
    }

    protected void musicDisc(Supplier<? extends Item> item, ResourceKey<JukeboxSong> song, String name) {
        String disc = item.get().getDescriptionId();
        this.add(disc, "Music Disc");
        String key = Util.makeDescriptionId("jukebox_song", song.location());
        this.add(key, name);
    }

    public void translateAdvancement(String key, String name, String desc) {
        this.add("advancements." + UnusualPrehistory2.MOD_ID + "." + key + ".title", name);
        this.add("advancements." + UnusualPrehistory2.MOD_ID + "." + key + ".description", desc);
    }

    private void translateEffect(DeferredHolder<? extends MobEffect, ?> effect, String desc) {
        this.add(effect.get(), UP2TextUtils.createTranslation(effect.get().toString()));
        this.add(effect.get().getDescriptionId() + ".description", desc);
    }

    private void translateBannerPatternItem(DeferredItem<? extends Item> item, String name) {
        String desc = UP2TextUtils.createTranslation(name);
        this.add(item.get(), "Banner Pattern");
        this.addDescription(item, desc);
    }

    private void translateBannerPattern(String name) {
        for (DyeColor dye : DyeColor.values()) {
            this.add("block.minecraft.banner." + UnusualPrehistory2.MOD_ID + "." + name + "." + dye.getName(), UP2TextUtils.createTranslation(dye.getName()) + " " + UP2TextUtils.createTranslation(name));
        }
    }

    private void addDescription(DeferredHolder<? extends ItemLike, ?> item, String desc) {
        this.add(item.get().asItem().getDescriptionId() + ".desc", desc);
    }

    public void creativeTab(CreativeModeTab key, String name){
        add(key.getDisplayName().getString(), name);
    }

    public void sound(Supplier<? extends SoundEvent> key, String subtitle){
        this.add("subtitles." + key.get().getLocation().getPath(), subtitle);
    }
}
