package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.*;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.ForgeAdvancementProvider.AdvancementGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class UP2AdvancementProvider implements AdvancementGenerator {

    private static final EntityType<?>[] REVIVABLE_MOBS = new EntityType<?>[] {
            UP2Entities.BRACHIOSAURUS.get(),
            UP2Entities.CARNOTAURUS.get(),
            UP2Entities.DESMATOSUCHUS.get(),
            UP2Entities.DIPLOCAULUS.get(), UP2Entities.DROMAEOSAURUS.get(), UP2Entities.DUNKLEOSTEUS.get(),
            UP2Entities.HIBBERTOPTERUS.get(),
            UP2Entities.JAWLESS_FISH.get(),
            UP2Entities.KAPROSUCHUS.get(), UP2Entities.KENTROSAURUS.get(), UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(),
            UP2Entities.LEPTICTIDIUM.get(), UP2Entities.LYSTROSAURUS.get(),
            UP2Entities.MAJUNGASAURUS.get(), UP2Entities.MEGALANIA.get(), UP2Entities.METRIORHYNCHUS.get(),
            UP2Entities.ONCHOPRISTIS.get(),
            UP2Entities.PACHYCEPHALOSAURUS.get(), UP2Entities.PRAEPUSA.get(), UP2Entities.PTERODACTYLUS.get(),
            UP2Entities.STETHACANTHUS.get(),
            UP2Entities.TALPANAS.get(), UP2Entities.TELECREX.get(),
            UP2Entities.ULUGHBEGSAURUS.get()
    };

    private static final ItemLike[] ROOT = new ItemLike[] {
            UP2Items.MACHINE_PARTS.get()
    };

    public static List<ItemLike> ALL_EGG_BLOCKS = UP2Blocks.EGG_BLOCKS.stream().map(Supplier::get).collect(Collectors.toList());
    public static List<ItemLike> ALL_EGG_ITEMS = UP2Items.EGG_EMBRYO_ITEMS.stream().map(Supplier::get).collect(Collectors.toList());
    public static List<ItemLike> ALL_FOSSILS = UP2Items.FOSSILS.stream().map(Supplier::get).collect(Collectors.toList());

    public static ForgeAdvancementProvider register(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        return new ForgeAdvancementProvider(output, provider, helper, List.of(new UP2AdvancementProvider()));
    }

    @Override
    public void generate(@NotNull Provider provider, @NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper helper) {

        Advancement root = this.addRoot(Advancement.Builder.advancement().display(UP2Items.UNUSUAL_PREHISTORY.get(), Component.translatable("advancement.unusual_prehistory.root"), Component.translatable("advancement.unusual_prehistory.root.desc"), UnusualPrehistory2.modPrefix("textures/block/mossy_dirt_top.png"), FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("root"), helper);

        // Progression
        Advancement fossils = this.addItemList(ALL_FOSSILS, Advancement.Builder.advancement().parent(root).display(UP2Items.RUGOSE_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.obtain_fossil"), Component.translatable("advancement.unusual_prehistory.obtain_fossil.desc"), null, FrameType.TASK, true, true, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_fossil"), helper);

        Advancement machineParts = createAdvancement("obtain_machine_parts", fossils, UP2Items.MACHINE_PARTS.get(), FrameType.TASK, false)
                .addCriterion("machine_parts", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.MACHINE_PARTS.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_machine_parts"), helper);

        Advancement transmogrifier = createAdvancement("obtain_transmogrifier", machineParts, UP2Blocks.TRANSMOGRIFIER.get(), FrameType.GOAL, false)
                .addCriterion("transmogrifier", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Blocks.TRANSMOGRIFIER.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_transmogrifier"), helper);

        Advancement organicOoze = createAdvancement("obtain_organic_ooze", transmogrifier, UP2Items.ORGANIC_OOZE.get(), FrameType.TASK, false)
                .addCriterion("organic_ooze", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.ORGANIC_OOZE.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_organic_ooze"), helper);

        createAdvancement("obtain_living_ooze", organicOoze, UP2Items.LIVING_OOZE_BUCKET.get(), FrameType.GOAL, false)
                .addCriterion("living_ooze_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.LIVING_OOZE_BUCKET.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_living_ooze"), helper);

        Advancement eggs = this.addAllEggs(Advancement.Builder.advancement().parent(organicOoze)
                .display(UP2Blocks.MAJUNGASAURUS_EGG.get(),
                        Component.translatable("advancement.unusual_prehistory.obtain_egg"),
                        Component.translatable("advancement.unusual_prehistory.obtain_egg.desc"),
                        null, FrameType.GOAL, true, true, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_egg"), helper);

        // Revival
        this.addMobList(REVIVABLE_MOBS, Advancement.Builder.advancement().parent(eggs).display(Items.BRUSH, Component.translatable("advancement.unusual_prehistory.revive_all_mobs"), Component.translatable("advancement.unusual_prehistory.revive_all_mobs.desc"), null, FrameType.CHALLENGE, true, true, true)
                .requirements(RequirementsStrategy.AND))
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, UnusualPrehistory2.modPrefix("revive_all_mobs"), helper);

        Advancement paleozoicRoot = this.addAllEggs(Advancement.Builder.advancement().parent(eggs).display(UP2Items.PALEOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.paleozoic_root"), Component.translatable("advancement.unusual_prehistory.paleozoic_root.desc"), null, FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("paleozoic_root"), helper);

        Advancement mesozoicRoot = this.addAllEggs(Advancement.Builder.advancement().parent(eggs).display(UP2Items.MESOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.mesozoic_root"), Component.translatable("advancement.unusual_prehistory.mesozoic_root.desc"), null, FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("mesozoic_root"), helper);

        Advancement triassicRoot = this.addAllEggs(Advancement.Builder.advancement().parent(mesozoicRoot).display(UP2Blocks.DESMATOSUCHUS_EGG.get(), Component.translatable("advancement.unusual_prehistory.triassic_root"), Component.translatable("advancement.unusual_prehistory.triassic_root.desc"), null, FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("triassic_root"), helper);

        Advancement jurassicRoot = this.addAllEggs(Advancement.Builder.advancement().parent(mesozoicRoot).display(UP2Blocks.KENTROSAURUS_EGG.get(), Component.translatable("advancement.unusual_prehistory.jurassic_root"), Component.translatable("advancement.unusual_prehistory.jurassic_root.desc"), null, FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("jurassic_root"), helper);

        Advancement cretaceousRoot = this.addAllEggs(Advancement.Builder.advancement().parent(mesozoicRoot).display(UP2Items.DROMAEOSAURUS_EGG.get(), Component.translatable("advancement.unusual_prehistory.cretaceous_root"), Component.translatable("advancement.unusual_prehistory.cretaceous_root.desc"), null, FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("cretaceous_root"), helper);

        Advancement cenozoicRoot = this.addAllEggs(Advancement.Builder.advancement().parent(eggs).display(UP2Items.CENOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.cenozoic_root"), Component.translatable("advancement.unusual_prehistory.cenozoic_root.desc"), null, FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("cenozoic_root"), helper);

        Advancement holoceneRoot = this.addAllEggs(Advancement.Builder.advancement().parent(cenozoicRoot).display(UP2Items.HOLOCENE_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.holocene_root"), Component.translatable("advancement.unusual_prehistory.holocene_root.desc"), null, FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("holocene_root"), helper);

        // Paleozoic
        Advancement reviveCoelacanthus = reviveMobAdvancement("revive_coelacanthus", paleozoicRoot, UP2Blocks.COELACANTHUS_ROE.get(), UP2Entities.COELACANTHUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_coelacanthus"), helper);
        Advancement reviveDiplocaulus = reviveMobAdvancement("revive_diplocaulus", reviveCoelacanthus, UP2Blocks.DIPLOCAULUS_EGGS.get(), UP2Entities.DIPLOCAULUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_diplocaulus"), helper);
        Advancement reviveDunkleosteus = reviveMobAdvancement("revive_dunkleosteus", reviveDiplocaulus, UP2Blocks.DUNKLEOSTEUS_SAC.get(), UP2Entities.DUNKLEOSTEUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_dunkleosteus"), helper);
        Advancement reviveHibbertopterus = reviveMobAdvancement("revive_hibbertopterus", reviveDunkleosteus, UP2Blocks.HIBBERTOPTERUS_EGGS.get(), UP2Entities.HIBBERTOPTERUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_hibbertopterus"), helper);
        Advancement reviveJawlessFish = reviveMobAdvancement("revive_jawless_fish", reviveHibbertopterus, UP2Blocks.JAWLESS_FISH_ROE.get(), UP2Entities.JAWLESS_FISH.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_jawless_fish"), helper);
        Advancement reviveLobeFinnedFish = reviveMobAdvancement("revive_lobe_finned_fish", reviveJawlessFish, UP2Blocks.LOBE_FINNED_FISH_ROE.get(), UP2Entities.LOBE_FINNED_FISH.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_lobe_finned_fish"), helper);
        Advancement reviveStethacanthus = reviveMobAdvancement("revive_stethacanthus", reviveLobeFinnedFish, UP2Blocks.STETHACANTHUS_SAC.get(), UP2Entities.STETHACANTHUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_stethacanthus"), helper);
        Advancement reviveTartuosteus = reviveMobAdvancement("revive_tartuosteus", reviveStethacanthus, UP2Blocks.TARTUOSTEUS_ROE.get(), UP2Entities.TARTUOSTEUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_tartuosteus"), helper);

        // Triassic
        Advancement reviveDesmatosuchus = reviveMobAdvancement("revive_desmatosuchus", triassicRoot, UP2Blocks.DESMATOSUCHUS_EGG.get(), UP2Entities.DESMATOSUCHUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_desmatosuchus"), helper);
        Advancement reviveLystrosaurus = reviveMobAdvancement("revive_lystrosaurus", reviveDesmatosuchus, UP2Blocks.LYSTROSAURUS_EGG.get(), UP2Entities.LYSTROSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_lystrosaurus"), helper);

        // Jurassic
        Advancement reviveBrachiosaurus = reviveMobAdvancement("revive_brachiosaurus", jurassicRoot, UP2Blocks.BRACHIOSAURUS_EGG.get(), UP2Entities.BRACHIOSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_brachiosaurus"), helper);
        Advancement reviveKentrosaurus = reviveMobAdvancement("revive_kentrosaurus", reviveBrachiosaurus, UP2Blocks.KENTROSAURUS_EGG.get(), UP2Entities.KENTROSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_kentrosaurus"), helper);
        Advancement reviveKimmeridgebrachypteraeschnidium = reviveMobAdvancement("revive_kimmeridgebrachypteraeschnidium", reviveKentrosaurus, UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get(), UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_kimmeridgebrachypteraeschnidium"), helper);
        Advancement reviveMetriorhynchus = reviveMobAdvancement("revive_metriorhynchus", reviveKimmeridgebrachypteraeschnidium, UP2Items.METRIORHYNCHUS_EMBRYO.get(), UP2Entities.METRIORHYNCHUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_metriorhynchus"), helper);
        Advancement revivePterodactylus = reviveMobAdvancement("revive_pterodactylus", reviveMetriorhynchus, UP2Items.PTERODACTYLUS_EGG.get(), UP2Entities.PTERODACTYLUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_pterodactylus"), helper);

        // Cretaceous
        Advancement reviveCarnotaurus = reviveMobAdvancement("revive_carnotaurus", cretaceousRoot, UP2Blocks.CARNOTAURUS_EGG.get(), UP2Entities.CARNOTAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_carnotaurus"), helper);
        Advancement reviveDromaeosaurus = reviveMobAdvancement("revive_dromaeosaurus", reviveCarnotaurus, UP2Items.DROMAEOSAURUS_EGG.get(), UP2Entities.DROMAEOSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_dromaeosaurus"), helper);
        Advancement reviveKaprosuchus = reviveMobAdvancement("revive_kaprosuchus", reviveDromaeosaurus, UP2Blocks.KAPROSUCHUS_EGG.get(), UP2Entities.KAPROSUCHUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_kaprosuchus"), helper);
        Advancement reviveMajungasaurus = reviveMobAdvancement("revive_majungasaurus", reviveKaprosuchus, UP2Blocks.MAJUNGASAURUS_EGG.get(), UP2Entities.MAJUNGASAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_majungasaurus"), helper);
        Advancement reviveOnchopristis = reviveMobAdvancement("revive_onchopristis", reviveMajungasaurus, UP2Blocks.ONCHOPRISTIS_SAC.get(), UP2Entities.ONCHOPRISTIS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_onchopristis"), helper);
        Advancement revivePachycephalosaurus = reviveMobAdvancement("revive_pachycephalosaurus", reviveOnchopristis, UP2Blocks.PACHYCEPHALOSAURUS_EGG.get(), UP2Entities.PACHYCEPHALOSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_pachycephalosaurus"), helper);
        Advancement reviveUlughbegsaurus = reviveMobAdvancement("revive_ulughbegsaurus", revivePachycephalosaurus, UP2Blocks.ULUGHBEGSAURUS_EGG.get(), UP2Entities.ULUGHBEGSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_ulughbegsaurus"), helper);

        // Cenozoic
        Advancement reviveLeptictidium = reviveMobAdvancement("revive_leptictidium", cenozoicRoot, UP2Items.LEPTICTIDIUM_EMBRYO.get(), UP2Entities.LEPTICTIDIUM.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_leptictidium"), helper);
        Advancement reviveMegalania = reviveMobAdvancement("revive_megalania", reviveLeptictidium, UP2Blocks.MEGALANIA_EGG.get(), UP2Entities.MEGALANIA.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_megalania"), helper);
        Advancement revivePraepusa = reviveMobAdvancement("revive_praepusa", reviveMegalania, UP2Items.PRAEPUSA_EMBRYO.get(), UP2Entities.PRAEPUSA.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_praepusa"), helper);
        Advancement reviveTelecrex = reviveMobAdvancement("revive_telecrex", revivePraepusa, UP2Items.TELECREX_EGG.get(), UP2Entities.TELECREX.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_telecrex"), helper);

        // Holocene
        Advancement reviveTalpanas = reviveMobAdvancement("revive_talpanas", holoceneRoot, UP2Items.TALPANAS_EGG.get(), UP2Entities.TALPANAS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_talpanas"), helper);

        // Advancement line filler
        Advancement paleozoicEnd = this.addAllEggs(Advancement.Builder.advancement().parent(reviveTartuosteus).display(UP2Items.PALEOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.paleozoic_root"), Component.translatable("advancement.unusual_prehistory.paleozoic_root.desc"), null, FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("paleozoic_end"), helper);

        Advancement cenozoicEnd = this.addAllEggs(Advancement.Builder.advancement().parent(reviveTelecrex).display(UP2Items.CENOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.cenozoic_root"), Component.translatable("advancement.unusual_prehistory.cenozoic_root.desc"), null, FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("cenozoic_end"), helper);

        Advancement triassicEnd = this.addAllEggs(Advancement.Builder.advancement().parent(reviveLystrosaurus).display(UP2Blocks.DESMATOSUCHUS_EGG.get(), Component.translatable("advancement.unusual_prehistory.triassic_root"), Component.translatable("advancement.unusual_prehistory.triassic_root.desc"), null, FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("triassic_end"), helper);

        Advancement jurassicEnd = this.addAllEggs(Advancement.Builder.advancement().parent(revivePterodactylus).display(UP2Blocks.KENTROSAURUS_EGG.get(), Component.translatable("advancement.unusual_prehistory.jurassic_root"), Component.translatable("advancement.unusual_prehistory.jurassic_root.desc"), null, FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("jurassic_end"), helper);

        Advancement cretaceousEnd = this.addAllEggs(Advancement.Builder.advancement().parent(reviveUlughbegsaurus).display(UP2Items.DROMAEOSAURUS_EGG.get(), Component.translatable("advancement.unusual_prehistory.cretaceous_root"), Component.translatable("advancement.unusual_prehistory.cretaceous_root.desc"), null, FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("cretaceous_end"), helper);

        // Mob interactions
        createAdvancement("breed_holocene_mobs", holoceneRoot, Items.HANGING_ROOTS, FrameType.TASK, false)
                .addCriterion("breed_holocene_mobs", new UP2CriteriaTriggers.TriggerInstance(UP2Criterion.BREED_HOLOCENE_MOBS.getId(), ContextAwarePredicate.ANY))
                .save(consumer, UnusualPrehistory2.modPrefix("breed_holocene_mobs"), helper);

        createAdvancement("pacify_mob", eggs, Items.ENCHANTED_GOLDEN_APPLE, FrameType.CHALLENGE, false)
                .addCriterion("pacify_mob", new UP2CriteriaTriggers.TriggerInstance(UP2Criterion.PACIFY_MOB_PERMANENT.getId(), ContextAwarePredicate.ANY))
                .rewards(AdvancementRewards.Builder.experience(50))
                .save(consumer, UnusualPrehistory2.modPrefix("pacify_mob"), helper);
    }

    private static Advancement.Builder createAdvancement(String name, Advancement parent, ItemLike icon, FrameType frame, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(icon,
                Component.translatable("advancement." + UnusualPrehistory2.MOD_ID + "." + name),
                Component.translatable("advancement." + UnusualPrehistory2.MOD_ID + "." + name + ".desc"),
                null, frame, true, true, hidden);
    }

    private static Advancement.Builder createAdvancement(String name, Advancement parent, ItemLike icon, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(icon,
                Component.translatable("advancement." + UnusualPrehistory2.MOD_ID + "." + name),
                Component.translatable("advancement." + UnusualPrehistory2.MOD_ID + "." + name + ".desc"),
                null, frame, showToast, announceToChat, hidden);
    }

    private static Advancement.Builder reviveMobAdvancement(String name, Advancement parent, ItemLike icon, EntityType<?> entityType) {
        return createAdvancement(name, parent, icon, FrameType.TASK, true, true, false).addCriterion(name, SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(entityType)));
    }

    private Advancement.Builder addItemList(ItemLike[] itemLikes, Advancement.Builder builder) {
        for (ItemLike items : itemLikes) {
            builder.addCriterion(ForgeRegistries.ITEMS.getKey(items.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(items));
        }
        return builder;
    }

    private Advancement.Builder addItemList(List<ItemLike> itemLikes, Advancement.Builder builder) {
        for (ItemLike items : itemLikes) {
            builder.addCriterion(ForgeRegistries.ITEMS.getKey(items.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(items));
        }
        return builder;
    }

    private Advancement.Builder addMobList(EntityType<?>[] entityTypes, Advancement.Builder builder) {
        for (EntityType<?> entity : entityTypes) {
            builder.addCriterion(EntityType.getKey(entity).getPath(), SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(entity)));
        }
        return builder;
    }

    private Advancement.Builder addAllEggs(Advancement.Builder builder) {
        this.addItemList(ALL_EGG_BLOCKS, builder);
        this.addItemList(ALL_EGG_ITEMS, builder);
        return builder;
    }

    private Advancement.Builder addRoot(Advancement.Builder builder) {
        this.addItemList(ALL_EGG_BLOCKS, builder);
        this.addItemList(ALL_EGG_ITEMS, builder);
        this.addItemList(ALL_FOSSILS, builder);
        this.addItemList(ROOT, builder);
        return builder;
    }
}
