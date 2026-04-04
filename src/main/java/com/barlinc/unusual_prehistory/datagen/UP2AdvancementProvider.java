package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.*;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UP2AdvancementProvider implements AdvancementProvider.AdvancementGenerator {

    public static AdvancementProvider create(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        return new AdvancementProvider(output, provider, helper, List.of(new UP2AdvancementProvider()));
    }

    @Override
    public void generate(Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper helper) {

        AdvancementHolder root = Advancement.Builder.advancement().display(UP2Items.UNUSUAL_PREHISTORY.get(), Component.translatable("advancement.unusual_prehistory.root"), Component.translatable("advancement.unusual_prehistory.root.desc"), UnusualPrehistory2.modPrefix("textures/block/cobbled_fossilized_bone.png"), AdvancementType.TASK, false, false, false)
                .addCriterion("fossils", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.FOSSILS).build()))
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.EGGS).build()))
                .addCriterion("machine_parts", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.MACHINE_PARTS.get()))
                .addCriterion("paleopedia", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.PALEOPEDIA.get()))
                .addCriterion("transmogrifier", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Blocks.TRANSMOGRIFIER.get()))
                .addCriterion("organic_ooze", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.ORGANIC_OOZE.get()))
                .addCriterion("living_ooze", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.LIVING_OOZE_BUCKET.get()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, UnusualPrehistory2.modPrefix("root"), helper);

        Advancement.Builder.advancement()
                .addCriterion("open_book_creative", new UP2CriteriaTriggers.TriggerInstance(UP2Criterion.OPEN_BOOK_CREATIVE_MODE.getId(), ContextAwarePredicate.ANY))
                .save(consumer, UnusualPrehistory2.modPrefix("open_book_creative"), helper);

        Advancement.Builder.advancement()
                .addCriterion("obtain_tar_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.TAR_BUCKET.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_tar_bucket"), helper);

        Advancement.Builder.advancement()
                .addCriterion("obtain_asphalt", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Blocks.ASPHALT.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_asphalt"), helper);

        // Progression & misc
        AdvancementHolder fossils = createAdvancement("obtain_fossil", root, UP2Items.UNKNOWN_FOSSIL.get(), AdvancementType.TASK, false)
                .addCriterion("fossils", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.FOSSILS).build()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_fossil"), helper);

        AdvancementHolder machineParts = createAdvancement("obtain_machine_parts", fossils, UP2Items.MACHINE_PARTS.get(), AdvancementType.TASK, false)
                .addCriterion("machine_parts", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.MACHINE_PARTS.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_machine_parts"), helper);

        AdvancementHolder transmogrifier = createAdvancement("obtain_transmogrifier", machineParts, UP2Blocks.TRANSMOGRIFIER.get(), AdvancementType.GOAL, false)
                .addCriterion("transmogrifier", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Blocks.TRANSMOGRIFIER.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_transmogrifier"), helper);

        AdvancementHolder organicOoze = createAdvancement("obtain_organic_ooze", transmogrifier, UP2Items.ORGANIC_OOZE.get(), AdvancementType.TASK, false)
                .addCriterion("organic_ooze", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.ORGANIC_OOZE.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_organic_ooze"), helper);

        AdvancementHolder eggs = Advancement.Builder.advancement().parent(organicOoze).display(UP2Items.UNKNOWN_EGG.get(), Component.translatable("advancement.unusual_prehistory.obtain_egg"), Component.translatable("advancement.unusual_prehistory.obtain_egg.desc"), null, AdvancementType.GOAL, true, true, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_egg"), helper);

        createAdvancement("obtain_living_ooze", organicOoze, UP2Items.LIVING_OOZE_BUCKET.get(), AdvancementType.GOAL, false)
                .addCriterion("living_ooze_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.LIVING_OOZE_BUCKET.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_living_ooze"), helper);

        // Paleozoic periods
        AdvancementHolder paleozoicRoot = Advancement.Builder.advancement().parent(eggs).display(UP2Items.PALEOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.paleozoic_root"), Component.translatable("advancement.unusual_prehistory.paleozoic_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, UnusualPrehistory2.modPrefix("paleozoic_root"), helper);

        AdvancementHolder ordovicianRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PERIOD_ORDOVICIAN.get(), Component.translatable("advancement.unusual_prehistory.ordovician_root"), Component.translatable("advancement.unusual_prehistory.ordovician_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("ordovician_root"), helper);
        AdvancementHolder devonianRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PERIOD_DEVONIAN.get(), Component.translatable("advancement.unusual_prehistory.devonian_root"), Component.translatable("advancement.unusual_prehistory.devonian_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("devonian_root"), helper);
        AdvancementHolder carboniferousRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PERIOD_CARBONIFEROUS.get(), Component.translatable("advancement.unusual_prehistory.carboniferous_root"), Component.translatable("advancement.unusual_prehistory.carboniferous_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("carboniferous_root"), helper);
        AdvancementHolder permianRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PERIOD_PERMIAN.get(), Component.translatable("advancement.unusual_prehistory.permian_root"), Component.translatable("advancement.unusual_prehistory.permian_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("permian_root"), helper);

        AdvancementHolder mesozoicRoot = Advancement.Builder.advancement().parent(eggs).display(UP2Items.MESOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.mesozoic_root"), Component.translatable("advancement.unusual_prehistory.mesozoic_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.MESOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("mesozoic_root"), helper);

        AdvancementHolder triassicRoot = Advancement.Builder.advancement().parent(mesozoicRoot).display(UP2Items.PERIOD_TRIASSIC.get(), Component.translatable("advancement.unusual_prehistory.triassic_root"), Component.translatable("advancement.unusual_prehistory.triassic_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.MESOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("triassic_root"), helper);
        AdvancementHolder jurassicRoot = Advancement.Builder.advancement().parent(mesozoicRoot).display(UP2Items.PERIOD_JURASSIC.get(), Component.translatable("advancement.unusual_prehistory.jurassic_root"), Component.translatable("advancement.unusual_prehistory.jurassic_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.MESOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("jurassic_root"), helper);
        AdvancementHolder cretaceousRoot = Advancement.Builder.advancement().parent(mesozoicRoot).display(UP2Items.PERIOD_CRETACEOUS.get(), Component.translatable("advancement.unusual_prehistory.cretaceous_root"), Component.translatable("advancement.unusual_prehistory.cretaceous_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.MESOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("cretaceous_root"), helper);

        AdvancementHolder cenozoicRoot = Advancement.Builder.advancement().parent(eggs).display(UP2Items.CENOZOIC_FOSSIL.get(), Component.translatable("advancement.unusual_prehistory.cenozoic_root"), Component.translatable("advancement.unusual_prehistory.cenozoic_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("cenozoic_root"), helper);

        AdvancementHolder paleogeneRoot = Advancement.Builder.advancement().parent(cenozoicRoot).display(UP2Items.PERIOD_PALEOGENE.get(), Component.translatable("advancement.unusual_prehistory.paleogene_root"), Component.translatable("advancement.unusual_prehistory.paleogene_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("paleogene_root"), helper);
        AdvancementHolder neogeneRoot = Advancement.Builder.advancement().parent(cenozoicRoot).display(UP2Items.PERIOD_NEOGENE.get(), Component.translatable("advancement.unusual_prehistory.neogene_root"), Component.translatable("advancement.unusual_prehistory.neogene_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("neogene_root"), helper);
        AdvancementHolder quaternaryRoot = Advancement.Builder.advancement().parent(cenozoicRoot).display(UP2Items.PERIOD_QUATERNARY.get(), Component.translatable("advancement.unusual_prehistory.quaternary_root"), Component.translatable("advancement.unusual_prehistory.quaternary_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("quaternary_root"), helper);
        AdvancementHolder holoceneRoot = Advancement.Builder.advancement().parent(cenozoicRoot).display(UP2Items.PERIOD_HOLOCENE.get(), Component.translatable("advancement.unusual_prehistory.holocene_root"), Component.translatable("advancement.unusual_prehistory.holocene_root.desc"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(RequirementsStrategy.OR).save(consumer, UnusualPrehistory2.modPrefix("holocene_root"), helper);

        // Ordovician
        AdvancementHolder reviveAegirocassis = reviveMobAdvancement("revive_aegirocassis", ordovicianRoot, UP2Blocks.AEGIROCASSIS_EGGS.get(), UP2Entities.AEGIROCASSIS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_aegirocassis"), helper);

        // Devonian
        AdvancementHolder reviveDunkleosteus = reviveMobAdvancement("revive_dunkleosteus", devonianRoot, UP2Blocks.DUNKLEOSTEUS_SAC.get(), UP2Entities.DUNKLEOSTEUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_dunkleosteus"), helper);
        AdvancementHolder reviveJawlessFish = reviveMobAdvancement("revive_jawless_fish", reviveDunkleosteus, UP2Blocks.JAWLESS_FISH_ROE.get(), UP2Entities.JAWLESS_FISH.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_jawless_fish"), helper);
        AdvancementHolder reviveStethacanthus = reviveMobAdvancement("revive_stethacanthus", reviveJawlessFish, UP2Blocks.STETHACANTHUS_SAC.get(), UP2Entities.STETHACANTHUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_stethacanthus"), helper);
        AdvancementHolder reviveTartuosteus = reviveMobAdvancement("revive_tartuosteus", reviveStethacanthus, UP2Blocks.TARTUOSTEUS_ROE.get(), UP2Entities.TARTUOSTEUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_tartuosteus"), helper);

        // Carboniferous
        AdvancementHolder reviveDiplocaulus = reviveMobAdvancement("revive_diplocaulus", carboniferousRoot, UP2Blocks.DIPLOCAULUS_EGGS.get(), UP2Entities.DIPLOCAULUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_diplocaulus"), helper);
        AdvancementHolder reviveHibbertopterus = reviveMobAdvancement("revive_hibbertopterus", reviveDiplocaulus, UP2Blocks.HIBBERTOPTERUS_EGGS.get(), UP2Entities.HIBBERTOPTERUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_hibbertopterus"), helper);
        AdvancementHolder reviveLobeFinnedFish = reviveMobAdvancement("revive_lobe_finned_fish", reviveHibbertopterus, UP2Blocks.LOBE_FINNED_FISH_ROE.get(), UP2Entities.LOBE_FINNED_FISH.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_lobe_finned_fish"), helper);

        // Permian
        AdvancementHolder reviveCoelacanthus = reviveMobAdvancement("revive_coelacanthus", permianRoot, UP2Blocks.COELACANTHUS_ROE.get(), UP2Entities.COELACANTHUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_coelacanthus"), helper);

        // Triassic
        AdvancementHolder reviveDesmatosuchus = reviveMobAdvancement("revive_desmatosuchus", triassicRoot, UP2Blocks.DESMATOSUCHUS_EGG.get(), UP2Entities.DESMATOSUCHUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_desmatosuchus"), helper);
        AdvancementHolder reviveLystrosaurus = reviveMobAdvancement("revive_lystrosaurus", reviveDesmatosuchus, UP2Blocks.LYSTROSAURUS_EGG.get(), UP2Entities.LYSTROSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_lystrosaurus"), helper);

        // Jurassic
        AdvancementHolder reviveBrachiosaurus = reviveMobAdvancement("revive_brachiosaurus", jurassicRoot, UP2Blocks.BRACHIOSAURUS_EGG.get(), UP2Entities.BRACHIOSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_brachiosaurus"), helper);
        AdvancementHolder reviveKentrosaurus = reviveMobAdvancement("revive_kentrosaurus", reviveBrachiosaurus, UP2Blocks.KENTROSAURUS_EGG.get(), UP2Entities.KENTROSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_kentrosaurus"), helper);
        AdvancementHolder reviveKimmeridgebrachypteraeschnidium = reviveMobAdvancement("revive_kimmeridgebrachypteraeschnidium", reviveKentrosaurus, UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get(), UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_kimmeridgebrachypteraeschnidium"), helper);
        AdvancementHolder reviveMetriorhynchus = reviveMobAdvancement("revive_metriorhynchus", reviveKimmeridgebrachypteraeschnidium, UP2Items.METRIORHYNCHUS_EMBRYO.get(), UP2Entities.METRIORHYNCHUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_metriorhynchus"), helper);
        AdvancementHolder revivePterodactylus = reviveMobAdvancement("revive_pterodactylus", reviveMetriorhynchus, UP2Items.PTERODACTYLUS_EGG.get(), UP2Entities.PTERODACTYLUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_pterodactylus"), helper);

        // Cretaceous
        AdvancementHolder reviveCarnotaurus = reviveMobAdvancement("revive_carnotaurus", cretaceousRoot, UP2Blocks.CARNOTAURUS_EGG.get(), UP2Entities.CARNOTAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_carnotaurus"), helper);
        AdvancementHolder reviveDromaeosaurus = reviveMobAdvancement("revive_dromaeosaurus", reviveCarnotaurus, UP2Items.DROMAEOSAURUS_EGG.get(), UP2Entities.DROMAEOSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_dromaeosaurus"), helper);
        AdvancementHolder reviveKaprosuchus = reviveMobAdvancement("revive_kaprosuchus", reviveDromaeosaurus, UP2Blocks.KAPROSUCHUS_EGG.get(), UP2Entities.KAPROSUCHUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_kaprosuchus"), helper);
        AdvancementHolder reviveMajungasaurus = reviveMobAdvancement("revive_majungasaurus", reviveKaprosuchus, UP2Blocks.MAJUNGASAURUS_EGG.get(), UP2Entities.MAJUNGASAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_majungasaurus"), helper);
        AdvancementHolder reviveMosasaurus = reviveMobAdvancement("revive_mosasaurus", reviveMajungasaurus, UP2Items.MOSASAURUS_EMBRYO.get(), UP2Entities.MOSASAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_mosasaurus"), helper);
        AdvancementHolder reviveOnchopristis = reviveMobAdvancement("revive_onchopristis", reviveMosasaurus, UP2Blocks.ONCHOPRISTIS_SAC.get(), UP2Entities.ONCHOPRISTIS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_onchopristis"), helper);
        AdvancementHolder revivePachycephalosaurus = reviveMobAdvancement("revive_pachycephalosaurus", reviveOnchopristis, UP2Blocks.PACHYCEPHALOSAURUS_EGG.get(), UP2Entities.PACHYCEPHALOSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_pachycephalosaurus"), helper);
        AdvancementHolder reviveUlughbegsaurus = reviveMobAdvancement("revive_ulughbegsaurus", revivePachycephalosaurus, UP2Blocks.ULUGHBEGSAURUS_EGG.get(), UP2Entities.ULUGHBEGSAURUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_ulughbegsaurus"), helper);

        // Paleogene
        AdvancementHolder reviveLeptictidium = reviveMobAdvancement("revive_leptictidium", paleogeneRoot, UP2Items.LEPTICTIDIUM_EMBRYO.get(), UP2Entities.LEPTICTIDIUM.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_leptictidium"), helper);
        AdvancementHolder revivePsilopterus = reviveMobAdvancement("revive_psilopterus", reviveLeptictidium, UP2Items.PSILOPTERUS_EGG.get(), UP2Entities.PSILOPTERUS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_psilopterus"), helper);
        AdvancementHolder reviveTelecrex = reviveMobAdvancement("revive_telecrex", revivePsilopterus, UP2Items.TELECREX_EGG.get(), UP2Entities.TELECREX.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_telecrex"), helper);

        // Neogene
        AdvancementHolder revivePraepusa = reviveMobAdvancement("revive_praepusa", neogeneRoot, UP2Items.PRAEPUSA_EMBRYO.get(), UP2Entities.PRAEPUSA.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_praepusa"), helper);

        // Quaternary
        AdvancementHolder reviveMegalania = reviveMobAdvancement("revive_megalania", quaternaryRoot, UP2Blocks.MEGALANIA_EGG.get(), UP2Entities.MEGALANIA.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_megalania"), helper);

        // Holocene
        AdvancementHolder reviveTalpanas = reviveMobAdvancement("revive_talpanas", holoceneRoot, UP2Items.TALPANAS_EGG.get(), UP2Entities.TALPANAS.get()).save(consumer, UnusualPrehistory2.modPrefix("revive_talpanas"), helper);
    }

    private static Advancement.Builder createAdvancement(String name, String category, AdvancementHolder parent, ItemLike icon, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(icon,
                Component.translatable("advancements." + UnusualPrehistory2.MOD_ID + "." + category + "." + name + ".title"),
                Component.translatable("advancements." + UnusualPrehistory2.MOD_ID + "." + category + "." + name + ".description"),
                null, frame, showToast, announceToChat, hidden);
    }

    private static Advancement.Builder createAdvancement(String name, String category, ResourceLocation parent, ItemLike icon, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return createAdvancement(name, category, Advancement.Builder.advancement().build(parent), icon, frame, showToast, announceToChat, hidden);
    }

    private static Advancement.Builder reviveMobAdvancement(String name, Advancement parent, ItemLike icon, EntityType<?> entityType) {
        return createAdvancement(name, parent, icon, AdvancementType.TASK, true, true, true)
                .addCriterion(name, SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(entityType)))
                .rewards(AdvancementRewards.Builder.experience(5));
    }
}
