package com.barlinc.unusual_prehistory.modifier;

import com.barlinc.unusual_prehistory.registry.UP2MapIcons;
import com.barlinc.unusual_prehistory.registry.tags.UP2StructureTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FossilSiteMapLootModifier implements IGlobalLootModifier {

    public static final Supplier<Codec<FossilSiteMapLootModifier>> CODEC = () -> RecordCodecBuilder.create(instance -> instance.group(LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(lm -> lm.conditions)).apply(instance, FossilSiteMapLootModifier::new));

    private final LootItemCondition[] conditions;

    private final Predicate<LootContext> orConditions;

    protected FossilSiteMapLootModifier(LootItemCondition[] conditions) {
        this.conditions = conditions;
        this.orConditions = LootItemConditions.orConditions(conditions);
    }

    @NotNull
    @Override
    public ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        return this.orConditions.test(context) ? this.doApply(generatedLoot, context) : generatedLoot;
    }

    @Nonnull
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() < this.getChance() && context.hasParam(LootContextParams.ORIGIN)) {
            ServerLevel serverlevel = context.getLevel();
            BlockPos chestPos = BlockPos.containing(context.getParam(LootContextParams.ORIGIN));
            BlockPos blockpos = serverlevel.findNearestMapStructure(UP2StructureTags.ON_FOSSIL_MAPS, chestPos, 100, true);
            if (blockpos != null) {
                ItemStack itemstack = MapItem.create(serverlevel, blockpos.getX(), blockpos.getZ(), (byte) 2, true, true);
                MapItem.renderBiomePreviewMap(serverlevel, itemstack);
                MapItemSavedData.addTargetDecoration(itemstack, blockpos, "+", UP2MapIcons.FOSSIL_SITE_MAP_DECORATION);
                itemstack.setHoverName(Component.translatable("item.unusual_prehistory.fossil_explorer_map"));
                generatedLoot.add(itemstack);
            }
        }
        return generatedLoot;
    }

    private float getChance() {
        return 0.15F;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}