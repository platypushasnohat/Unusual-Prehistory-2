package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.ForgeAdvancementProvider.AdvancementGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UP2AdvancementProvider implements AdvancementGenerator {

    public static ForgeAdvancementProvider register(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        return new ForgeAdvancementProvider(output, provider, helper, List.of(new UP2AdvancementProvider()));
    }

    @SuppressWarnings("unused")
    @Override
    public void generate(@NotNull Provider provider, @NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper helper) {

        Advancement root = Advancement.Builder.advancement()
                .display(UP2Items.UNUSUAL_PREHISTORY.get(),
                        Component.translatable("advancement.unusual_prehistory.root"),
                        Component.translatable("advancement.unusual_prehistory.root.desc"),
                        UnusualPrehistory2.modPrefix("textures/block/mossy_dirt_top.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("root", KilledTrigger.TriggerInstance.playerKilledEntity())
                .save(consumer, UnusualPrehistory2.modPrefix("root"), helper);

        this.hatchEggAdvancement(root, UP2Items.DROMAEOSAURUS_EGG.get(), "hatch_dromaeosaurus", UP2Entities.DROMAEOSAURUS.get(), consumer, helper);
    }

    private void hatchEggAdvancement(Advancement parent, Item displayItem, String translationKey, EntityType<? extends Entity> entityType, Consumer<Advancement> consumer, ExistingFileHelper helper) {
        Advancement.Builder.advancement()
                .parent(parent)
                .display(displayItem,
                        Component.translatable("advancement.unusual_prehistory." + translationKey),
                        Component.translatable("advancement.unusual_prehistory." + translationKey + ".desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion(translationKey, SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(entityType)))
                .save(consumer, UnusualPrehistory2.modPrefix(translationKey), helper);
    }
}
