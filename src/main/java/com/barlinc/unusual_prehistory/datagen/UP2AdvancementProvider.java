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

public class UP2AdvancementProvider implements AdvancementGenerator {

    private static final EntityType<?>[] REVIVABLE_MOBS = new EntityType<?>[] {
            UP2Entities.CARNOTAURUS.get(), UP2Entities.DIPLOCAULUS.get(), UP2Entities.DROMAEOSAURUS.get(),
            UP2Entities.DUNKLEOSTEUS.get(), UP2Entities.JAWLESS_FISH.get(), UP2Entities.KENTROSAURUS.get(),
            UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(), UP2Entities.MAJUNGASAURUS.get(), UP2Entities.MEGALANIA.get(),
            UP2Entities.METRIORHYNCHUS.get(), UP2Entities.ONCHOPRISTIS.get(),UP2Entities.STETHACANTHUS.get(),
            UP2Entities.TALPANAS.get(), UP2Entities.TELECREX.get()
    };

    private static final ItemLike[] ROOT = new ItemLike[] {
            UP2Items.MACHINE_PARTS.get(),
            UP2Items.AGED_FEATHER.get(),

            UP2Items.FURY_FOSSIL.get(), UP2Items.BOOMERANG_FOSSIL.get(), UP2Items.RUNNER_FOSSIL.get(),
            UP2Items.GUILLOTINE_FOSSIL.get(), UP2Items.JAWLESS_FOSSIL.get(), UP2Items.PRICKLY_FOSSIL.get(),
            UP2Items.IMPERATIVE_FOSSIL.get(), UP2Items.RUGOSE_FOSSIL.get(), UP2Items.THERMAL_FOSSIL.get(),
            UP2Items.LONG_CROCODILE_FOSSIL.get(), UP2Items.SAW_FOSSIL.get(), UP2Items.ANVIL_FOSSIL.get(),
            UP2Items.PLUMAGE_FOSSIL.get(),

            UP2Items.BENNETTITALES_FOSSIL.get(), UP2Items.CALAMOPHYTON_FOSSIL.get(), UP2Items.CLADOPHEBIS_FOSSIL.get(),
            UP2Items.COOKSONIA_FOSSIL.get(), UP2Items.HORSETAIL_FOSSIL.get(), UP2Items.LEEFRUCTUS_FOSSIL.get(),
            UP2Items.QUILLWORT_FOSSIL.get(), UP2Items.RAIGUENRAYUN_FOSSIL.get(), UP2Items.RHYNIA_FOSSIL.get(),
            UP2Items.GINKGO_FOSSIL.get(), UP2Items.LEPIDODENDRON_FOSSIL.get()
    };

    private static final ItemLike[] FOSSILS = new ItemLike[] {
            // Animals
            UP2Items.FURY_FOSSIL.get(), UP2Items.BOOMERANG_FOSSIL.get(), UP2Items.RUNNER_FOSSIL.get(),
            UP2Items.GUILLOTINE_FOSSIL.get(), UP2Items.JAWLESS_FOSSIL.get(), UP2Items.PRICKLY_FOSSIL.get(),
            UP2Items.IMPERATIVE_FOSSIL.get(), UP2Items.RUGOSE_FOSSIL.get(), UP2Items.THERMAL_FOSSIL.get(),
            UP2Items.LONG_CROCODILE_FOSSIL.get(), UP2Items.SAW_FOSSIL.get(), UP2Items.ANVIL_FOSSIL.get(),
            UP2Items.PLUMAGE_FOSSIL.get(),

            // Plants
            UP2Items.BENNETTITALES_FOSSIL.get(), UP2Items.CALAMOPHYTON_FOSSIL.get(), UP2Items.CLADOPHEBIS_FOSSIL.get(),
            UP2Items.COOKSONIA_FOSSIL.get(), UP2Items.HORSETAIL_FOSSIL.get(), UP2Items.LEEFRUCTUS_FOSSIL.get(),
            UP2Items.QUILLWORT_FOSSIL.get(), UP2Items.RAIGUENRAYUN_FOSSIL.get(), UP2Items.RHYNIA_FOSSIL.get(),
            UP2Items.GINKGO_FOSSIL.get(), UP2Items.LEPIDODENDRON_FOSSIL.get()
    };

    private static final ItemLike[] HOLOCENE_REMAINS = new ItemLike[] {
            // Animals
            UP2Items.AGED_FEATHER.get()
    };

    private static final ItemLike[] EGGS = new ItemLike[] {
            UP2Blocks.CARNOTAURUS_EGG.get(), UP2Blocks.DIPLOCAULUS_EGGS.get(), UP2Items.DROMAEOSAURUS_EGG.get(),
            UP2Blocks.DUNKLEOSTEUS_SAC.get(), UP2Blocks.JAWLESS_FISH_ROE.get(), UP2Blocks.KENTROSAURUS_EGG.get(),
            UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get(), UP2Blocks.MAJUNGASAURUS_EGG.get(), UP2Blocks.MEGALANIA_EGG.get(),
            UP2Items.METRIORHYNCHUS_EMBRYO.get(), UP2Blocks.ONCHOPRISTIS_SAC.get(), UP2Blocks.STETHACANTHUS_SAC.get(),
            UP2Items.TALPANAS_EGG.get(), UP2Items.TELECREX_EGG.get()
    };

    public static ForgeAdvancementProvider register(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        return new ForgeAdvancementProvider(output, provider, helper, List.of(new UP2AdvancementProvider()));
    }

    @SuppressWarnings("unused")
    @Override
    public void generate(@NotNull Provider provider, @NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper helper) {

        Advancement root = this.addItemList(ROOT, Advancement.Builder.advancement()
                .display(UP2Items.UNUSUAL_PREHISTORY.get(),
                        Component.translatable("advancement.unusual_prehistory.root"),
                        Component.translatable("advancement.unusual_prehistory.root.desc"),
                        UnusualPrehistory2.modPrefix("textures/block/mossy_dirt_top.png"), FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("root"), helper);

        // Progression
        Advancement fossils = this.addItemList(FOSSILS, Advancement.Builder.advancement().parent(root)
                .display(UP2Items.RUGOSE_FOSSIL.get(),
                        Component.translatable("advancement.unusual_prehistory.obtain_fossil"),
                        Component.translatable("advancement.unusual_prehistory.obtain_fossil.desc"),
                        null, FrameType.TASK, true, true, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_fossil"), helper);

        Advancement holoceneRemains = this.addItemList(HOLOCENE_REMAINS, Advancement.Builder.advancement().parent(fossils)
                .display(UP2Items.AGED_FEATHER.get(),
                        Component.translatable("advancement.unusual_prehistory.obtain_holocene_remains"),
                        Component.translatable("advancement.unusual_prehistory.obtain_holocene_remains.desc"),
                        null, FrameType.TASK, true, true, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_holocene_remains"), helper);

        Advancement machineParts = Advancement.Builder.advancement().parent(holoceneRemains)
                .display(UP2Items.MACHINE_PARTS.get(),
                        Component.translatable("advancement.unusual_prehistory.obtain_machine_parts"),
                        Component.translatable("advancement.unusual_prehistory.obtain_machine_parts.desc"),
                        null, FrameType.TASK, true, true, false)
                .addCriterion("machine_parts", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.MACHINE_PARTS.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_machine_parts"), helper);

        Advancement transmogrifier = Advancement.Builder.advancement().parent(machineParts)
                .display(UP2Blocks.TRANSMOGRIFIER.get(),
                        Component.translatable("advancement.unusual_prehistory.obtain_transmogrifier"),
                        Component.translatable("advancement.unusual_prehistory.obtain_transmogrifier.desc"),
                        null, FrameType.GOAL, true, true, false)
                .addCriterion("transmogrifier", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Blocks.TRANSMOGRIFIER.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_transmogrifier"), helper);

        Advancement organicOoze = Advancement.Builder.advancement().parent(transmogrifier)
                .display(UP2Items.ORGANIC_OOZE.get(),
                        Component.translatable("advancement.unusual_prehistory.obtain_organic_ooze"),
                        Component.translatable("advancement.unusual_prehistory.obtain_organic_ooze.desc"),
                        null, FrameType.TASK, true, true, false)
                .addCriterion("organic_ooze", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.ORGANIC_OOZE.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_organic_ooze"), helper);

        Advancement eggs = this.addItemList(EGGS, Advancement.Builder.advancement().parent(organicOoze)
                .display(UP2Blocks.MAJUNGASAURUS_EGG.get(),
                        Component.translatable("advancement.unusual_prehistory.obtain_egg"),
                        Component.translatable("advancement.unusual_prehistory.obtain_egg.desc"),
                        null, FrameType.GOAL, true, true, false)
                .requirements(RequirementsStrategy.OR))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_egg"), helper);

        // Trees
        Advancement ginkgo = Advancement.Builder.advancement().parent(organicOoze)
                .display(UP2Blocks.GINKGO_SAPLING.get(),
                        Component.translatable("advancement.unusual_prehistory.revive_ginkgo"),
                        Component.translatable("advancement.unusual_prehistory.revive_ginkgo.desc"),
                        null, FrameType.TASK, true, true, false)
                .addCriterion("ginkgo_sapling", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Blocks.GINKGO_SAPLING.get()))
                .addCriterion("golden_ginkgo_sapling", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Blocks.GOLDEN_GINKGO_SAPLING.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, UnusualPrehistory2.modPrefix("revive_ginkgo"), helper);

        Advancement lepidodendron = Advancement.Builder.advancement().parent(organicOoze)
                .display(UP2Blocks.LEPIDODENDRON_CONE.get(),
                        Component.translatable("advancement.unusual_prehistory.revive_lepidodendron"),
                        Component.translatable("advancement.unusual_prehistory.revive_lepidodendron.desc"),
                        null, FrameType.TASK, true, true, false)
                .addCriterion("lepidodendron_cone", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Blocks.LEPIDODENDRON_CONE.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("revive_lepidodendron"), helper);

        // Hatching
        Advancement carnotaurus = Advancement.Builder.advancement()
                .parent(eggs)
                .display(UP2Blocks.CARNOTAURUS_EGG.get(),
                        Component.translatable("advancement.unusual_prehistory.hatch_carnotaurus"),
                        Component.translatable("advancement.unusual_prehistory.hatch_carnotaurus.desc"),
                        null, FrameType.TASK, true, true, false)
                .addCriterion("hatch_carnotaurus", SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(UP2Entities.CARNOTAURUS.get())))
                .save(consumer, UnusualPrehistory2.modPrefix("hatch_carnotaurus"), helper);

        Advancement talpanas = Advancement.Builder.advancement()
                .parent(eggs)
                .display(UP2Items.TALPANAS_EGG.get(),
                        Component.translatable("advancement.unusual_prehistory.hatch_talpanas"),
                        Component.translatable("advancement.unusual_prehistory.hatch_talpanas.desc"),
                        null, FrameType.TASK, true, true, false)
                .addCriterion("hatch_talpanas", SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(UP2Entities.TALPANAS.get())))
                .save(consumer, UnusualPrehistory2.modPrefix("hatch_talpanas"), helper);

        this.hatchEggAdvancement(eggs, UP2Blocks.DIPLOCAULUS_EGGS.get(), "hatch_diplocaulus", UP2Entities.DIPLOCAULUS.get(), consumer, helper);
        this.hatchEggAdvancement(eggs, UP2Items.DROMAEOSAURUS_EGG.get(), "hatch_dromaeosaurus", UP2Entities.DROMAEOSAURUS.get(), consumer, helper);
        this.hatchEggAdvancement(eggs, UP2Blocks.DUNKLEOSTEUS_SAC.get(), "hatch_dunkleosteus", UP2Entities.DUNKLEOSTEUS.get(), consumer, helper);
        this.hatchEggAdvancement(eggs, UP2Blocks.JAWLESS_FISH_ROE.get(), "hatch_jawless_fish", UP2Entities.JAWLESS_FISH.get(), consumer, helper);
        this.hatchEggAdvancement(eggs, UP2Blocks.KENTROSAURUS_EGG.get(), "hatch_kentrosaurus", UP2Entities.KENTROSAURUS.get(), consumer, helper);
        this.hatchEggAdvancement(eggs, UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get(), "hatch_kimmeridgebrachypteraeschnidium", UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(), consumer, helper);
        this.hatchEggAdvancement(eggs, UP2Blocks.MAJUNGASAURUS_EGG.get(), "hatch_majungasaurus", UP2Entities.MAJUNGASAURUS.get(), consumer, helper);
        this.hatchEggAdvancement(eggs, UP2Blocks.MEGALANIA_EGG.get(), "hatch_megalania", UP2Entities.MEGALANIA.get(), consumer, helper);
        this.hatchEggAdvancement(eggs, UP2Items.METRIORHYNCHUS_EMBRYO.get(), "hatch_metriorhynchus", UP2Entities.METRIORHYNCHUS.get(), consumer, helper);
        this.hatchEggAdvancement(eggs, UP2Blocks.ONCHOPRISTIS_SAC.get(), "hatch_onchopristis", UP2Entities.ONCHOPRISTIS.get(), consumer, helper);
        this.hatchEggAdvancement(eggs, UP2Blocks.STETHACANTHUS_SAC.get(), "hatch_stethacanthus", UP2Entities.STETHACANTHUS.get(), consumer, helper);
        this.hatchEggAdvancement(eggs, UP2Items.TELECREX_EGG.get(), "hatch_telecrex", UP2Entities.TELECREX.get(), consumer, helper);

        this.addMobList(REVIVABLE_MOBS, Advancement.Builder.advancement().parent(eggs)
                .display(Items.BRUSH,
                        Component.translatable("advancement.unusual_prehistory.hatch_all_mobs"),
                        Component.translatable("advancement.unusual_prehistory.hatch_all_mobs.desc"),
                        null, FrameType.CHALLENGE, true, true, true)
                .requirements(RequirementsStrategy.AND))
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, UnusualPrehistory2.modPrefix("hatch_all_mobs"), helper);

        // Mob interactions
        Advancement.Builder.advancement().parent(talpanas)
                .display(Items.HANGING_ROOTS,
                        Component.translatable("advancement.unusual_prehistory.breed_holocene_mobs"),
                        Component.translatable("advancement.unusual_prehistory.breed_holocene_mobs.desc"),
                        null, FrameType.TASK, true, true, false)
                .addCriterion("breed_holocene_mobs", new UP2CriteriaTriggers.TriggerInstance(UP2Criterion.BREED_HOLOCENE_MOBS.getId(), ContextAwarePredicate.ANY))
                .save(consumer, UnusualPrehistory2.modPrefix("breed_holocene_mobs"), helper);

        Advancement.Builder.advancement().parent(carnotaurus)
                .display(Items.ENCHANTED_GOLDEN_APPLE,
                        Component.translatable("advancement.unusual_prehistory.pacify_mob"),
                        Component.translatable("advancement.unusual_prehistory.pacify_mob.desc"),
                        null, FrameType.CHALLENGE, true, true, false)
                .addCriterion("pacify_mob", new UP2CriteriaTriggers.TriggerInstance(UP2Criterion.PACIFY_MOB.getId(), ContextAwarePredicate.ANY))
                .rewards(AdvancementRewards.Builder.experience(50))
                .save(consumer, UnusualPrehistory2.modPrefix("pacify_mob"), helper);
    }

    private void hatchEggAdvancement(Advancement parent, ItemLike displayItem, String translationKey, EntityType<?> entityType, Consumer<Advancement> consumer, ExistingFileHelper helper) {
        Advancement.Builder.advancement()
                .parent(parent)
                .display(displayItem,
                        Component.translatable("advancement.unusual_prehistory." + translationKey),
                        Component.translatable("advancement.unusual_prehistory." + translationKey + ".desc"),
                        null, FrameType.TASK, true, true, false)
                .addCriterion(translationKey, SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(entityType)))
                .save(consumer, UnusualPrehistory2.modPrefix(translationKey), helper);
    }

    private Advancement.Builder addItemList(ItemLike[] itemLikes, Advancement.Builder builder) {
        for (ItemLike dendrologistBlock : itemLikes) {
            builder.addCriterion(ForgeRegistries.ITEMS.getKey(dendrologistBlock.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(dendrologistBlock));
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
