package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2Tab;
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
        UP2Blocks.AUTO_TRANSLATE.forEach(this::forBlock);

        // items
        UP2Items.ITEM_TRANSLATIONS.forEach(this::forItem);

        // entities
        this.forEntity(UP2Entities.CARNOTAURUS);
        this.forEntity(UP2Entities.DIPLOCAULUS);
        this.forEntity(UP2Entities.DROMAEOSAURUS);
        this.forEntity(UP2Entities.DUNKLEOSTEUS);
        this.forEntity(UP2Entities.JAWLESS_FISH);
        this.forEntity(UP2Entities.KENTROSAURUS);
        this.forEntity(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM);
        this.forEntity(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH);
        this.forEntity(UP2Entities.MAJUNGASAURUS);
        this.forEntity(UP2Entities.MEGALANIA);
        this.forEntity(UP2Entities.STETHACANTHUS);
        this.forEntity(UP2Entities.UNICORN);

        this.forEntity(UP2Entities.BOAT);
        this.addEntityType(UP2Entities.CHEST_BOAT, "Boat with Chest");

        this.addItem(UP2Items.GINKGO_CHEST_BOAT, "Ginkgo Boat with Chest");
        this.addItem(UP2Items.LEPIDODENDRON_CHEST_BOAT, "Lepidodendron Boat with Chest");

        this.addItem(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE, "Bottle of Kimmeridgebrachypteraeschnidium");
        this.addItem(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET, "Bucket of Kimmeridgebrachypteraeschnidium Nymph");
        this.addItem(UP2Items.JAWLESS_FISH_BUCKET, "Bucket of Jawless Fish");
        this.addItem(UP2Items.STETHACANTHUS_BUCKET, "Bucket of Stethacanthus");
        this.addItem(UP2Items.DIPLOCAULUS_BUCKET, "Bucket of Diplocaulus");
        this.addItem(UP2Items.DUNKLEOSTEUS_BUCKET, "Bucket of Dunkleosteus");

        // sounds
        this.sound(UP2SoundEvents.CARNOTAURUS_STEP, "Footsteps");

        this.sound(UP2SoundEvents.CARNOTAURUS_HURT, "Carnotaurus hurts");
        this.sound(UP2SoundEvents.CARNOTAURUS_DEATH, "Carnotaurus dies");
        this.sound(UP2SoundEvents.CARNOTAURUS_IDLE, "Carnotaurus grumbles");

        this.sound(UP2SoundEvents.DIPLOCAULUS_HURT, "Diplocaulus hurts");
        this.sound(UP2SoundEvents.DIPLOCAULUS_DEATH, "Diplocaulus dies");
        this.sound(UP2SoundEvents.DIPLOCAULUS_IDLE, "Diplocaulus croaks");

        this.sound(UP2SoundEvents.DUNKLEOSTEUS_HURT, "Dunkleosteus hurts");
        this.sound(UP2SoundEvents.DUNKLEOSTEUS_DEATH, "Dunkleosteus dies");
        this.sound(UP2SoundEvents.DUNKLEOSTEUS_FLOP, "Dunkleosteus flops");

        this.sound(UP2SoundEvents.JAWLESS_FISH_HURT, "Jawless Fish hurts");
        this.sound(UP2SoundEvents.JAWLESS_FISH_DEATH, "Jawless Fish dies");
        this.sound(UP2SoundEvents.JAWLESS_FISH_FLOP, "Jawless Fish flops");

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

        this.sound(UP2SoundEvents.MAJUNGASAURUS_HURT, "Majungasaurus hurts");
        this.sound(UP2SoundEvents.MAJUNGASAURUS_DEATH, "Majungasaurus dies");
        this.sound(UP2SoundEvents.MAJUNGASAURUS_IDLE, "Majungasaurus groans");

        this.sound(UP2SoundEvents.MEGALANIA_HURT, "Megalania hurts");
        this.sound(UP2SoundEvents.MEGALANIA_DEATH, "Megalania dies");
        this.sound(UP2SoundEvents.MEGALANIA_IDLE, "Megalania hisses");

        this.sound(UP2SoundEvents.STETHACANTHUS_HURT, "Stethacanthus hurts");
        this.sound(UP2SoundEvents.STETHACANTHUS_DEATH, "Stethacanthus dies");
        this.sound(UP2SoundEvents.STETHACANTHUS_FLOP, "Stethacanthus flops");

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

        // kimmeridgebrachypteraeschnidium bottle
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.0", "Black Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.1", "Blue Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.2", "Brown Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.3", "Cyan Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.4", "Gray Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.5", "Green Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.6", "Light Blue Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.7", "Light Gray Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.8", "Lime Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.9", "Magenta Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.10", "Orange Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.11", "Pink Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.12", "Purple Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.13", "Red Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.14", "White Body");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_base_color.15", "Yellow Body");

        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.0", "Black Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.1", "Blue Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.2", "Brown Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.3", "Cyan Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.4", "Gray Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.5", "Green Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.6", "Light Blue Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.7", "Light Gray Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.8", "Lime Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.9", "Magenta Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.10", "Orange Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.11", "Pink Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.12", "Purple Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.13", "Red Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.14", "White Wings");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_wing_color.15", "Yellow Wings");

        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.0", "Black");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.1", "Blue");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.2", "Brown");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.3", "Cyan");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.4", "Gray");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.5", "Green");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.6", "Light Blue");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.7", "Light Gray");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.8", "Lime");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.9", "Magenta");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.10", "Orange");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.11", "Pink");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.12", "Purple");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.13", "Red");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.14", "White");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern_color.15", "Yellow");

        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.stripe", "Stripes");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.tailshade", "Tail");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.topshade", "Back");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.halfshade", "Duality");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.large_stripe", "Large Stripes");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.racing_stripe", "Racing Stripe");
        this.add("unusual_prehistory.kimmeridgebrachypteraeschnidium_pattern.large_racing_stripe", "Large Racing Stripe");

        this.add("unusual_prehistory.jei.transmogrification", "Transmogrification");
        this.add("unusual_prehistory.jei.chiseling", "Chiseling");
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
}
