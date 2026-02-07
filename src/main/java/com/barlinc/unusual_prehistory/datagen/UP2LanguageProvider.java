package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.UnusualPrehistory2Tab;
import com.barlinc.unusual_prehistory.registry.*;
import com.barlinc.unusual_prehistory.utils.UP2TextUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.text.WordUtils;
import org.codehaus.plexus.util.StringUtils;

import java.util.Objects;
import java.util.function.Supplier;

public class UP2LanguageProvider extends LanguageProvider {

    public UP2LanguageProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), UnusualPrehistory2.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        // creative tab
        creativeTab(UnusualPrehistory2Tab.UNUSUAL_PREHISTORY_2_TAB.get(), "Unusual Prehistory 2");

        // blocks
        UP2Blocks.BLOCK_TRANSLATIONS.forEach(this::forBlock);

        // items
        UP2Items.ITEM_TRANSLATIONS.forEach(this::forItem);

        // entities
        this.forEntity(UP2Entities.AEGIROCASSIS);
        this.forEntity(UP2Entities.BARINASUCHUS);
        this.forEntity(UP2Entities.BRACHIOSAURUS);
        this.forEntity(UP2Entities.CARNOTAURUS);
        this.forEntity(UP2Entities.COELACANTHUS);
        this.forEntity(UP2Entities.DESMATOSUCHUS);
        this.forEntity(UP2Entities.DIMORPHODON);
        this.forEntity(UP2Entities.DIPLOCAULUS);
        this.forEntity(UP2Entities.DROMAEOSAURUS);
        this.forEntity(UP2Entities.DUNKLEOSTEUS);
        this.forEntity(UP2Entities.HIBBERTOPTERUS);
        this.forEntity(UP2Entities.JAWLESS_FISH);
        this.forEntity(UP2Entities.KAPROSUCHUS);
        this.forEntity(UP2Entities.KENTROSAURUS);
        this.forEntity(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM);
        this.forEntity(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH);
        this.forEntity(UP2Entities.LEPTICTIDIUM);
        this.forEntity(UP2Entities.LOBE_FINNED_FISH);
        this.forEntity(UP2Entities.LYSTROSAURUS);
        this.forEntity(UP2Entities.MAJUNGASAURUS);
        this.forEntity(UP2Entities.MANIPULATOR);
        this.forEntity(UP2Entities.MEGALANIA);
        this.forEntity(UP2Entities.METRIORHYNCHUS);
        this.forEntity(UP2Entities.ONCHOPRISTIS);
        this.forEntity(UP2Entities.PACHYCEPHALOSAURUS);
        this.forEntity(UP2Entities.PRAEPUSA);
        this.forEntity(UP2Entities.PSILOPTERUS);
        this.forEntity(UP2Entities.PTERODACTYLUS);
        this.forEntity(UP2Entities.STETHACANTHUS);
        this.forEntity(UP2Entities.TALPANAS);
        this.forEntity(UP2Entities.TARTUOSTEUS);
        this.forEntity(UP2Entities.TELECREX);
        this.forEntity(UP2Entities.THERIZINOSAURUS);
        this.forEntity(UP2Entities.ULUGHBEGSAURUS);
        this.forEntity(UP2Entities.UNICORN);

        this.forEntity(UP2Entities.DIMORPHODON_EGG);
        this.forEntity(UP2Entities.DROMAEOSAURUS_EGG);
        this.forEntity(UP2Entities.PSILOPTERUS_EGG);
        this.forEntity(UP2Entities.PTERODACTYLUS_EGG);
        this.forEntity(UP2Entities.TALPANAS_EGG);
        this.forEntity(UP2Entities.TELECREX_EGG);

        this.forEntity(UP2Entities.LIVING_OOZE);

        this.forEntity(UP2Entities.BOAT);
        this.addEntityType(UP2Entities.CHEST_BOAT, "Boat with Chest");

        this.addItem(UP2Items.PETRIFIED_LUCA, "Petrified L.U.C.A");
        this.addItem(UP2Items.LUCA, "L.U.C.A");

        this.addItem(UP2Items.DIRT_ON_A_STICK, "Dirt on a Stick");

        this.addItem(UP2Items.DRYOPHYLLUM_CHEST_BOAT, "Dryophyllum Boat with Chest");
        this.addItem(UP2Items.GINKGO_CHEST_BOAT, "Ginkgo Boat with Chest");
        this.addItem(UP2Items.LEPIDODENDRON_CHEST_BOAT, "Lepidodendron Boat with Chest");
        this.addItem(UP2Items.METASEQUOIA_CHEST_BOAT, "Metasequoia Boat with Chest");

        this.addItem(UP2Items.COELACANTHUS_BUCKET, "Bucket of Coelacanthus");
        this.addItem(UP2Items.DIPLOCAULUS_BUCKET, "Bucket of Diplocaulus");
        this.addItem(UP2Items.DUNKLEOSTEUS_BUCKET, "Bucket of Dunkleosteus");
        this.addItem(UP2Items.JAWLESS_FISH_BUCKET, "Bucket of Jawless Fish");
        this.addItem(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE, "Bottle of Kimmeridgebrachypteraeschnidium");
        this.addItem(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET, "Bucket of Kimmeridgebrachypteraeschnidium Nymph");
        this.addItem(UP2Items.LIVING_OOZE_BUCKET, "Bucket of Living Ooze");
        this.addItem(UP2Items.LOBE_FINNED_FISH_BUCKET, "Bucket of Lobe Finned Fish");
        this.addItem(UP2Items.PRAEPUSA_BUCKET, "Bucket of Praepusa");
        this.addItem(UP2Items.STETHACANTHUS_BUCKET, "Bucket of Stethacanthus");

        // sounds
        this.sound(UP2SoundEvents.TAR_POP, "Tar pops");

        this.sound(UP2SoundEvents.AEGIROCASSIS_HURT, "Aegirocassis hurts");
        this.sound(UP2SoundEvents.AEGIROCASSIS_DEATH, "Aegirocassis dies");
        this.sound(UP2SoundEvents.AEGIROCASSIS_IDLE, "Aegirocassis rumbles");
        this.sound(UP2SoundEvents.AEGIROCASSIS_HOVER, "Aegirocassis tries to fly");

        this.sound(UP2SoundEvents.BARINASUCHUS_HURT, "Barinasuchus hurts");
        this.sound(UP2SoundEvents.BARINASUCHUS_DEATH, "Barinasuchus dies");
        this.sound(UP2SoundEvents.BARINASUCHUS_IDLE, "Barinasuchus grumbles");
        this.sound(UP2SoundEvents.BARINASUCHUS_ATTACK, "Barinasuchus snaps");
        this.sound(UP2SoundEvents.BARINASUCHUS_THREATEN, "Barinasuchus rumbles");

        this.sound(UP2SoundEvents.BRACHIOSAURUS_HURT, "Brachiosaurus hurts");
        this.sound(UP2SoundEvents.BRACHIOSAURUS_DEATH, "Brachiosaurus dies");
        this.sound(UP2SoundEvents.BRACHIOSAURUS_IDLE, "Brachiosaurus rumbles");
        this.sound(UP2SoundEvents.BRACHIOSAURUS_STOMP, "Brachiosaurus stomps");

        this.sound(UP2SoundEvents.CARNOTAURUS_STEP, "Carnotaurus steps");
        this.sound(UP2SoundEvents.CARNOTAURUS_HURT, "Carnotaurus hurts");
        this.sound(UP2SoundEvents.CARNOTAURUS_DEATH, "Carnotaurus dies");
        this.sound(UP2SoundEvents.CARNOTAURUS_IDLE, "Carnotaurus grumbles");
        this.sound(UP2SoundEvents.CARNOTAURUS_SNIFF, "Carnotaurus sniffs");
        this.sound(UP2SoundEvents.CARNOTAURUS_BITE, "Carnotaurus bites");
        this.sound(UP2SoundEvents.CARNOTAURUS_CHARGE, "Carnotaurus charges");
        this.sound(UP2SoundEvents.CARNOTAURUS_HEADBUTT, "Carnotaurus headbutts");
        this.sound(UP2SoundEvents.CARNOTAURUS_ROAR, "Carnotaurus roars");

        this.sound(UP2SoundEvents.DESMATOSUCHUS_HURT, "Desmatosuchus hurts");
        this.sound(UP2SoundEvents.DESMATOSUCHUS_DEATH, "Desmatosuchus dies");
        this.sound(UP2SoundEvents.DESMATOSUCHUS_IDLE, "Desmatosuchus groans");
        this.sound(UP2SoundEvents.DESMATOSUCHUS_STEP, "Desmatosuchus steps");

        this.sound(UP2SoundEvents.DIMORPHODON_HURT, "Dimorphodon hurts");
        this.sound(UP2SoundEvents.DIMORPHODON_DEATH, "Dimorphodon dies");
        this.sound(UP2SoundEvents.DIMORPHODON_IDLE, "Dimorphodon screeches");

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

        this.sound(UP2SoundEvents.HIBBERTOPTERUS_HURT, "Hibbertopterus hurts");
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_DEATH, "Hibbertopterus dies");
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_IDLE, "Hibbertopterus rattles");
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_STEP, "Hibbertopterus steps");

        this.sound(UP2SoundEvents.JAWLESS_FISH_HURT, "Jawless Fish hurts");
        this.sound(UP2SoundEvents.JAWLESS_FISH_DEATH, "Jawless Fish dies");
        this.sound(UP2SoundEvents.JAWLESS_FISH_FLOP, "Jawless Fish flops");

        this.sound(UP2SoundEvents.KAPROSUCHUS_HURT, "Kaprosuchus hurts");
        this.sound(UP2SoundEvents.KAPROSUCHUS_DEATH, "Kaprosuchus dies");
        this.sound(UP2SoundEvents.KAPROSUCHUS_IDLE, "Kaprosuchus hisses");

        this.sound(UP2SoundEvents.KENTROSAURUS_HURT, "Kentrosaurus hurts");
        this.sound(UP2SoundEvents.KENTROSAURUS_DEATH, "Kentrosaurus dies");
        this.sound(UP2SoundEvents.KENTROSAURUS_IDLE, "Kentrosaurus groans");
        this.sound(UP2SoundEvents.KENTROSAURUS_STEP, "Kentrosaurus steps");
        this.sound(UP2SoundEvents.KENTROSAURUS_EAT, "Kentrosaurus eats");

        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT, "Kimmeridgebrachypteraeschnidium hurts");
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH, "Kimmeridgebrachypteraeschnidium dies");
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_LOOP, "Kimmeridgebrachypteraeschnidium buzzes");

        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_HURT, "Kimmeridgebrachypteraeschnidium Nymph hurts");
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_DEATH, "Kimmeridgebrachypteraeschnidium Nymph dies");

        this.sound(UP2SoundEvents.LEPTICTIDIUM_HURT, "Leptictidium hurts");
        this.sound(UP2SoundEvents.LEPTICTIDIUM_DEATH, "Leptictidium dies");
        this.sound(UP2SoundEvents.LEPTICTIDIUM_IDLE, "Leptictidium squeaks");

        this.sound(UP2SoundEvents.LYSTROSAURUS_HURT, "Lystrosaurus hurts");
        this.sound(UP2SoundEvents.LYSTROSAURUS_DEATH, "Lystrosaurus dies");
        this.sound(UP2SoundEvents.LYSTROSAURUS_IDLE, "Lystrosaurus oinks");
        this.sound(UP2SoundEvents.LYSTROSAURUS_STEP, "Lystrosaurus steps");
        this.sound(UP2SoundEvents.LYSTROSAURUS_CHAINSMOKER, "Lystrosaurus coughs");

        this.sound(UP2SoundEvents.MAJUNGASAURUS_HURT, "Majungasaurus hurts");
        this.sound(UP2SoundEvents.MAJUNGASAURUS_DEATH, "Majungasaurus dies");
        this.sound(UP2SoundEvents.MAJUNGASAURUS_IDLE, "Majungasaurus groans");
        this.sound(UP2SoundEvents.MAJUNGASAURUS_BITE, "Majungasaurus bites");
        this.sound(UP2SoundEvents.MAJUNGASAURUS_SNIFF, "Majungasaurus sniffs");
        this.sound(UP2SoundEvents.MAJUNGASAURUS_STEP, "Majungasaurus steps");

        this.sound(UP2SoundEvents.MANIPULATOR_HURT, "Manipulator hurts");
        this.sound(UP2SoundEvents.MANIPULATOR_DEATH, "Manipulator dies");
        this.sound(UP2SoundEvents.MANIPULATOR_IDLE, "Manipulator chatters");
        this.sound(UP2SoundEvents.MANIPULATOR_STEP, "Manipulator steps");

        this.sound(UP2SoundEvents.MEGALANIA_HURT, "Megalania hurts");
        this.sound(UP2SoundEvents.MEGALANIA_DEATH, "Megalania dies");
        this.sound(UP2SoundEvents.MEGALANIA_IDLE, "Megalania hisses");
        this.sound(UP2SoundEvents.MEGALANIA_ROAR, "Megalania roars");
        this.sound(UP2SoundEvents.MEGALANIA_STEP, "Megalania steps");
        this.sound(UP2SoundEvents.MEGALANIA_TAIL_SWING, "Megalania swings tail");
        this.sound(UP2SoundEvents.MEGALANIA_BITE, "Megalania bites");
        this.sound(UP2SoundEvents.MEGALANIA_JUMPSCARE, "Megalania jumpscares");

        this.sound(UP2SoundEvents.METRIORHYNCHUS_HURT, "Metriorhynchus hurts");
        this.sound(UP2SoundEvents.METRIORHYNCHUS_DEATH, "Metriorhynchus dies");
        this.sound(UP2SoundEvents.METRIORHYNCHUS_IDLE, "Metriorhynchus hisses");
        this.sound(UP2SoundEvents.METRIORHYNCHUS_BITE, "Metriorhynchus bites");
        this.sound(UP2SoundEvents.METRIORHYNCHUS_BELLOW, "Metriorhynchus bellows");

        this.sound(UP2SoundEvents.ONCHOPRISTIS_HURT, "Onchopristis hurts");
        this.sound(UP2SoundEvents.ONCHOPRISTIS_DEATH, "Onchopristis dies");
        this.sound(UP2SoundEvents.ONCHOPRISTIS_FLOP, "Onchopristis flops");

        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_HURT, "Pachycephalosaurus hurts");
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_DEATH, "Pachycephalosaurus dies");
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_IDLE, "Pachycephalosaurus grunts");
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_ATTACK, "Pachycephalosaurus attacks");
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_WARN, "Pachycephalosaurus warns");
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_STEP, "Pachycephalosaurus steps");

        this.sound(UP2SoundEvents.PRAEPUSA_HURT, "Praepusa hurts");
        this.sound(UP2SoundEvents.PRAEPUSA_DEATH, "Praepusa dies");
        this.sound(UP2SoundEvents.PRAEPUSA_IDLE, "Praepusa honks");
        this.sound(UP2SoundEvents.PRAEPUSA_MITOSIS, "Praepusa mitoses");
        this.sound(UP2SoundEvents.PRAEPUSA_BOUNCE, "Praepusa bounces");

        this.sound(UP2SoundEvents.PTERODACTYLUS_HURT, "Pterodactylus hurts");
        this.sound(UP2SoundEvents.PTERODACTYLUS_DEATH, "Pterodactylus dies");
        this.sound(UP2SoundEvents.PTERODACTYLUS_IDLE, "Pterodactylus chirps");

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

        this.sound(UP2SoundEvents.THERIZINOSAURUS_HURT, "Therizinosaurus hurts");
        this.sound(UP2SoundEvents.THERIZINOSAURUS_DEATH, "Therizinosaurus dies");
        this.sound(UP2SoundEvents.THERIZINOSAURUS_IDLE, "Therizinosaurus squawks");
        this.sound(UP2SoundEvents.THERIZINOSAURUS_ATTACK, "Therizinosaurus slices");
        this.sound(UP2SoundEvents.THERIZINOSAURUS_STEP, "Therizinosaurus steps");
        this.sound(UP2SoundEvents.THERIZINOSAURUS_ROAR, "Therizinosaurus screams");
        this.sound(UP2SoundEvents.THERIZINOSAURUS_NOTICE, "Therizinosaurus takes notice");

        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_HURT, "Ulughbegsaurus hurts");
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_DEATH, "Ulughbegsaurus dies");
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_IDLE, "Ulughbegsaurus groans");
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_ATTACK, "Ulughbegsaurus bites");

        this.sound(UP2SoundEvents.UNICORN_HURT, "Unicorn hurts");
        this.sound(UP2SoundEvents.UNICORN_DEATH, "Unicorn dies");
        this.sound(UP2SoundEvents.UNICORN_IDLE, "Unicorn grunts");

        this.sound(UP2SoundEvents.LIVING_OOZE_HURT, "Living Ooze hurts");
        this.sound(UP2SoundEvents.LIVING_OOZE_DEATH, "Living Ooze dies");
        this.sound(UP2SoundEvents.LIVING_OOZE_SQUISH, "Living Ooze squishes");
        this.sound(UP2SoundEvents.LIVING_OOZE_JUMP, "Living Ooze jumps");
        this.sound(UP2SoundEvents.LIVING_OOZE_SPIT, "Living Ooze spits");

        this.sound(UP2SoundEvents.TRANSMOGRIFIER_LOOP, "Transmogrifying");
        this.sound(UP2SoundEvents.TRANSMOGRIFIER_START, "Transmogrifier starts");
        this.sound(UP2SoundEvents.TRANSMOGRIFIER_STOP, "Transmogrifier stops");

        this.sound(UP2SoundEvents.DOOMSURF_DISC, "Music Disc");
        this.musicDisc(UP2Items.DOOMSURF_DISC, "ChipsTheCat - Doomsurf");
        this.sound(UP2SoundEvents.MEGALANIA_DISC, "Music Disc");
        this.musicDisc(UP2Items.MEGALANIA_DISC, "ValiantEnvoy - MEGALANIA");
        this.sound(UP2SoundEvents.TARIFYING_DISC, "Music Disc");
        this.musicDisc(UP2Items.TARIFYING_DISC, "Dylanvhs - Tarifying");

        // Advancements
        this.translateAdvancement("root", "Unusual Prehistory 2", "Revive creatures from the ancient past!");

        this.translateAdvancement("paleozoic_root", "Paleozoic Era", "Paleozoic era creatures");
        this.translateAdvancement("mesozoic_root", "Mesozoic Era", "Mesozoic era creatures");
        this.translateAdvancement("triassic_root", "Triassic Period", "Triassic period creatures");
        this.translateAdvancement("jurassic_root", "Jurassic Period", "Jurassic period creatures");
        this.translateAdvancement("cretaceous_root", "Cretaceous Period", "Cretaceous period creatures");
        this.translateAdvancement("cenozoic_root", "Cenozoic Era", "Cenozoic era creatures");
        this.translateAdvancement("holocene_root", "Holocene Epoch", "Holocene epoch creatures");

        this.translateAdvancement("revive_all_mobs", "Unusual Prehistorian", "Revive all known prehistoric creatures");
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
        this.translateAdvancement("revive_lobe_finned_fish", "Shark Bait, Hoo Ha Ha!", "Revive a Lobe Finned Fish");
        this.translateAdvancement("revive_lystrosaurus", "Survivalist", "Revive a Lystrosaurus");
        this.translateAdvancement("revive_majungasaurus", "Camouflaging Cannibal", "Revive a Majungasaurus");
        this.translateAdvancement("revive_manipulator", "Mandibles", "Revive a Manipulator");
        this.translateAdvancement("revive_megalania", "The Giant Goanna", "Revive a Megalania");
        this.translateAdvancement("revive_metriorhynchus", "The Meltdown", "Revive a Metriorhynchus");
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

        this.translateAdvancement("pacify_mob", "Chill Pill", "Feed an Enchanted Golden Apple to an aggressive creature to make it permanently neutral");
        this.translateAdvancement("breed_holocene_mobs", "Repopulation!", "Breed a pair of Holocene animals");
        this.translateAdvancement("obtain_fossil", "Rock and Bone", "Use a Brush to uncover fossils at a Fossil Site or Tar Pit");
        this.translateAdvancement("obtain_holocene_remains", "Not So Ancient", "Find the remains of a recently extinct creature");
        this.translateAdvancement("obtain_machine_parts", "Electrical Doodads", "Find some Machine Parts in a loot chest");
        this.translateAdvancement("obtain_transmogrifier", "Jesse, We Have to Cook", "Craft a Transmogrifier, the key component in creature revival");
        this.translateAdvancement("obtain_organic_ooze", "It's Looking at Me...", "Craft some Organic Ooze to fuel the revival process");
        this.translateAdvancement("obtain_egg", "E G G S", "Recreate your first prehistoric egg or embryo");
        this.translateAdvancement("obtain_living_ooze", "Alakagoo!", "Create a Living Ooze in a cauldron to incubate embryos with");

        this.translateEffect(UP2MobEffects.FURY, "Gain increased speed and attack speed as your health gets lower");

        // kimmeridgebrachypteraeschnidium bottle
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

        this.add("entity.unusual_prehistory.diplocaulus.variant_brevirostris", "D. brevirostris");
        this.add("entity.unusual_prehistory.diplocaulus.variant_magnicornis", "D. magnicornis");
        this.add("entity.unusual_prehistory.diplocaulus.variant_recurvatis", "D. recurvatis");
        this.add("entity.unusual_prehistory.diplocaulus.variant_salamandroides", "D. salamandroides");

        this.add("entity.unusual_prehistory.dunkleosteus.variant_raveri", "D. raveri");
        this.add("entity.unusual_prehistory.dunkleosteus.variant_marsaisi", "D. marsaisi");
        this.add("entity.unusual_prehistory.dunkleosteus.variant_terrelli", "D. terrelli");

        this.add("entity.unusual_prehistory.lobe_finned_fish.variant_allenypterus", "Allenypterus");
        this.add("entity.unusual_prehistory.lobe_finned_fish.variant_eusthenopteron", "Eusthenopteron");
        this.add("entity.unusual_prehistory.lobe_finned_fish.variant_gooloogongia", "Gooloogongia");
        this.add("entity.unusual_prehistory.lobe_finned_fish.variant_laccognathus", "Laccognathus");
        this.add("entity.unusual_prehistory.lobe_finned_fish.variant_scaumenacia", "Scaumenacia");

        this.add("unusual_prehistory.jei.transmogrification", "Transmogrification");

        // tame commands
        this.add("entity.unusual_prehistory.all.command_0", "%s is wandering");
        this.add("entity.unusual_prehistory.all.command_1", "%s is staying");
        this.add("entity.unusual_prehistory.all.command_2", "%s is following");

        this.add("item.unusual_prehistory.fossil_explorer_map", "Fossil Site Map");

        // Book
        this.add("unusual_prehistory.patchouli.book.name", "Paleopedia");
        this.add("unusual_prehistory.patchouli.book.landing", "This book acts as a guide to the revival process of various ancient plants and animals, along with any notable traits or uses that they may have.");

        this.add("death.attack.unusual_prehistory.execute", "%s was executed by %s");
    }

    private void forBlock(Supplier<? extends Block> block) {
        this.addBlock(block, UP2TextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())).getPath()));
    }

    private void forItem(Supplier<? extends Item> item) {
        this.addItem(item, UP2TextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get())).getPath()));
    }

    private void forEntity(Supplier<? extends EntityType<?>> entity) {
        this.addEntityType(entity, UP2TextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entity.get())).getPath()));
    }

    private String format(ResourceLocation registryName) {
        return WordUtils.capitalizeFully(registryName.getPath().replace("_", " "));
    }

    protected void painting(String name, String author) {
        this.add("painting." + UnusualPrehistory2.MOD_ID + "." + name + ".title",  UP2TextUtils.createTranslation(name));
        this.add("painting." + UnusualPrehistory2.MOD_ID + "." + name + ".author",  author);
    }

    protected void musicDisc(Supplier<? extends Item> item, String description) {
        String disc = item.get().getDescriptionId();
        this.add(disc, "Music Disc");
        this.add(disc + ".desc", description);
    }

    public void translateAdvancement(String key, String name, String desc) {
        this.add("advancement." + UnusualPrehistory2.MOD_ID + "." + key, name);
        this.add("advancement." + UnusualPrehistory2.MOD_ID + "." + key + ".desc", desc);
    }

    private void translateEffect(RegistryObject<? extends MobEffect> effect, String desc) {
        this.add(effect.get(), toUpper(ForgeRegistries.MOB_EFFECTS, effect));
        this.add(effect.get().getDescriptionId() + ".description", desc);
    }

    public void creativeTab(CreativeModeTab key, String name){
        add(key.getDisplayName().getString(), name);
    }

    public void sound(Supplier<? extends SoundEvent> key, String subtitle){
        this.add("subtitles." + key.get().getLocation().getPath(), subtitle);
    }

    private static <T> String toUpper(IForgeRegistry<T> registry, RegistryObject<? extends T> object) {
        return toUpper(registry.getKey(object.get()).getPath());
    }

    private static String toUpper(String string) {
        return StringUtils.capitaliseAllWords(string.replace('_', ' '));
    }
}
