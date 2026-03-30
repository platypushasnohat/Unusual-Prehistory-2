package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.*;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.ForgeAdvancementProvider.AdvancementGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UP2AdvancementProvider implements AdvancementGenerator {

    public static ForgeAdvancementProvider register(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        return new ForgeAdvancementProvider(output, provider, helper, List.of(new UP2AdvancementProvider()));
    }

    @Override
    public void generate(@NotNull Provider provider, @NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper helper) {

        Advancement root = Advancement.Builder.advancement().display(UP2Items.UNUSUAL_PREHISTORY.get(), Component.translatable("advancement.unusual_prehistory.root"), Component.translatable("advancement.unusual_prehistory.root.desc"), UnusualPrehistory2.modPrefix("textures/block/cobbled_fossilized_bone.png"), FrameType.TASK, false, false, false)
                .addCriterion("fossils", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.FOSSILS).build()))
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.EGGS).build()))
                .addCriterion("machine_parts", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.MACHINE_PARTS.get()))
                .addCriterion("paleopedia", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.PALEOPEDIA.get()))
                .addCriterion("transmogrifier", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Blocks.TRANSMOGRIFIER.get()))
                .addCriterion("organic_ooze", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.ORGANIC_OOZE.get()))
                .addCriterion("living_ooze", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.LIVING_OOZE_BUCKET.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, UnusualPrehistory2.modPrefix("root"), helper);

        Advancement.Builder.advancement().addCriterion("open_book_creative", new UP2CriteriaTriggers.TriggerInstance(UP2Criterion.OPEN_BOOK_CREATIVE_MODE.getId(), ContextAwarePredicate.ANY))
                .save(consumer, UnusualPrehistory2.modPrefix("open_book_creative"), helper);

        // Progression & misc
        Advancement fossils = createAdvancement("obtain_fossil", root, UP2Items.UNKNOWN_FOSSIL.get(), FrameType.TASK, false)
                .addCriterion("fossils", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.FOSSILS).build()))
                .requirements(RequirementsStrategy.OR)
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

        Advancement eggs = Advancement.Builder.advancement().parent(organicOoze).display(UP2Items.UNKNOWN_EGG.get(), Component.translatable("advancement.unusual_prehistory.obtain_egg"), Component.translatable("advancement.unusual_prehistory.obtain_egg.desc"), null, FrameType.GOAL, true, true, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.EGGS).build()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_egg"), helper);

        createAdvancement("obtain_living_ooze", organicOoze, UP2Items.LIVING_OOZE_BUCKET.get(), FrameType.GOAL, false)
                .addCriterion("living_ooze_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.LIVING_OOZE_BUCKET.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_living_ooze"), helper);

        // Paleozoic periods
        Advancement paleozoicRoot = Advancement.Builder.advancement().parent(eggs).display(UP2Items.PALEOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.paleozoic_root"), Component.translatable("advancement.unusual_prehistory.paleozoic_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, UnusualPrehistory2.modPrefix("paleozoic_root"), helper);

        Advancement ordovicianRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PALEOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.ordovician_root"), Component.translatable("advancement.unusual_prehistory.ordovician_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("ordovician_root"), helper);
        Advancement silurianRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PALEOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.silurian_root"), Component.translatable("advancement.unusual_prehistory.silurian_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("silurian_root"), helper);
        Advancement devonianRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PALEOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.devonian_root"), Component.translatable("advancement.unusual_prehistory.devonian_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("devonian_root"), helper);
        Advancement carboniferousRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PALEOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.carboniferous_root"), Component.translatable("advancement.unusual_prehistory.carboniferous_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("carboniferous_root"), helper);
        Advancement permianRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PALEOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.permian_root"), Component.translatable("advancement.unusual_prehistory.permian_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("permian_root"), helper);

        Advancement mesozoicRoot = Advancement.Builder.advancement().parent(eggs).display(UP2Items.MESOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.mesozoic_root"), Component.translatable("advancement.unusual_prehistory.mesozoic_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.MESOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("mesozoic_root"), helper);

        Advancement triassicRoot = Advancement.Builder.advancement().parent(mesozoicRoot).display(UP2Items.MESOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.triassic_root"), Component.translatable("advancement.unusual_prehistory.triassic_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.MESOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("triassic_root"), helper);
        Advancement jurassicRoot = Advancement.Builder.advancement().parent(mesozoicRoot).display(UP2Items.MESOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.jurassic_root"), Component.translatable("advancement.unusual_prehistory.jurassic_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.MESOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("jurassic_root"), helper);
        Advancement cretaceousRoot = Advancement.Builder.advancement().parent(mesozoicRoot).display(UP2Items.MESOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.cretaceous_root"), Component.translatable("advancement.unusual_prehistory.cretaceous_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.MESOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("cretaceous_root"), helper);

        Advancement cenozoicRoot = Advancement.Builder.advancement().parent(eggs).display(UP2Items.CENOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.cenozoic_root"), Component.translatable("advancement.unusual_prehistory.cenozoic_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("cenozoic_root"), helper);

        Advancement paleogeneRoot = Advancement.Builder.advancement().parent(cenozoicRoot).display(UP2Items.CENOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.paleogene_root"), Component.translatable("advancement.unusual_prehistory.paleogene_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("paleogene_root"), helper);
        Advancement neogeneRoot = Advancement.Builder.advancement().parent(cenozoicRoot).display(UP2Items.CENOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.neogene_root"), Component.translatable("advancement.unusual_prehistory.neogene_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("neogene_root"), helper);
        Advancement quaternaryRoot = Advancement.Builder.advancement().parent(cenozoicRoot).display(UP2Items.CENOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.quaternary_root"), Component.translatable("advancement.unusual_prehistory.quaternary_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("quaternary_root"), helper);
        Advancement holoceneRoot = Advancement.Builder.advancement().parent(cenozoicRoot).display(UP2Items.HOLOCENE_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.holocene_root"), Component.translatable("advancement.unusual_prehistory.holocene_root.desc"), null, FrameType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("holocene_root"), helper);

        // Ordovician
        Advancement reviveAegirocassis = reviveMobAdvancement("revive_aegirocassis", ordovicianRoot, UP2Blocks.AEGIROCASSIS_EGGS.get(), UP2Entities.AEGIROCASSIS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_aegirocassis"), helper);

        // Devonian
        Advancement reviveDunkleosteus = reviveMobAdvancement("revive_dunkleosteus", devonianRoot, UP2Blocks.DUNKLEOSTEUS_SAC.get(), UP2Entities.DUNKLEOSTEUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_dunkleosteus"), helper);
        Advancement reviveJawlessFish = reviveMobAdvancement("revive_jawless_fish", reviveDunkleosteus, UP2Blocks.JAWLESS_FISH_ROE.get(), UP2Entities.JAWLESS_FISH.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_jawless_fish"), helper);
        Advancement reviveStethacanthus = reviveMobAdvancement("revive_stethacanthus", reviveJawlessFish, UP2Blocks.STETHACANTHUS_SAC.get(), UP2Entities.STETHACANTHUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_stethacanthus"), helper);
        Advancement reviveTartuosteus = reviveMobAdvancement("revive_tartuosteus", reviveStethacanthus, UP2Blocks.TARTUOSTEUS_ROE.get(), UP2Entities.TARTUOSTEUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_tartuosteus"), helper);

        // Carboniferous
        Advancement reviveDiplocaulus = reviveMobAdvancement("revive_diplocaulus", carboniferousRoot, UP2Blocks.DIPLOCAULUS_EGGS.get(), UP2Entities.DIPLOCAULUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_diplocaulus"), helper);
        Advancement reviveHibbertopterus = reviveMobAdvancement("revive_hibbertopterus", reviveDiplocaulus, UP2Blocks.HIBBERTOPTERUS_EGGS.get(), UP2Entities.HIBBERTOPTERUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_hibbertopterus"), helper);
        Advancement reviveLobeFinnedFish = reviveMobAdvancement("revive_lobe_finned_fish", reviveHibbertopterus, UP2Blocks.LOBE_FINNED_FISH_ROE.get(), UP2Entities.LOBE_FINNED_FISH.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_lobe_finned_fish"), helper);

        // Permian
        Advancement reviveCoelacanthus = reviveMobAdvancement("revive_coelacanthus", permianRoot, UP2Blocks.COELACANTHUS_ROE.get(), UP2Entities.COELACANTHUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_coelacanthus"), helper);

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
        Advancement reviveMosasaurus = reviveMobAdvancement("revive_mosasaurus", reviveMajungasaurus, UP2Items.MOSASAURUS_EMBRYO.get(), UP2Entities.MOSASAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_mosasaurus"), helper);
        Advancement reviveOnchopristis = reviveMobAdvancement("revive_onchopristis", reviveMosasaurus, UP2Blocks.ONCHOPRISTIS_SAC.get(), UP2Entities.ONCHOPRISTIS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_onchopristis"), helper);
        Advancement revivePachycephalosaurus = reviveMobAdvancement("revive_pachycephalosaurus", reviveOnchopristis, UP2Blocks.PACHYCEPHALOSAURUS_EGG.get(), UP2Entities.PACHYCEPHALOSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_pachycephalosaurus"), helper);
        Advancement reviveUlughbegsaurus = reviveMobAdvancement("revive_ulughbegsaurus", revivePachycephalosaurus, UP2Blocks.ULUGHBEGSAURUS_EGG.get(), UP2Entities.ULUGHBEGSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_ulughbegsaurus"), helper);

        // Paleogene
        Advancement reviveLeptictidium = reviveMobAdvancement("revive_leptictidium", paleogeneRoot, UP2Items.LEPTICTIDIUM_EMBRYO.get(), UP2Entities.LEPTICTIDIUM.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_leptictidium"), helper);
        Advancement revivePsilopterus = reviveMobAdvancement("revive_psilopterus", reviveLeptictidium, UP2Items.PSILOPTERUS_EGG.get(), UP2Entities.PSILOPTERUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_psilopterus"), helper);
        Advancement reviveTelecrex = reviveMobAdvancement("revive_telecrex", revivePsilopterus, UP2Items.TELECREX_EGG.get(), UP2Entities.TELECREX.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_telecrex"), helper);

        // Neogene
        Advancement revivePraepusa = reviveMobAdvancement("revive_praepusa", neogeneRoot, UP2Items.PRAEPUSA_EMBRYO.get(), UP2Entities.PRAEPUSA.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_praepusa"), helper);

        // Quaternary
        Advancement reviveMegalania = reviveMobAdvancement("revive_megalania", quaternaryRoot, UP2Blocks.MEGALANIA_EGG.get(), UP2Entities.MEGALANIA.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_megalania"), helper);

        // Holocene
        Advancement reviveTalpanas = reviveMobAdvancement("revive_talpanas", holoceneRoot, UP2Items.TALPANAS_EGG.get(), UP2Entities.TALPANAS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_talpanas"), helper);
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
        return createAdvancement(name, parent, icon, FrameType.TASK, true, true, true)
                .addCriterion(name, SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(entityType)))
                .rewards(AdvancementRewards.Builder.experience(5));
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
}
