package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Map;
import java.util.Set;

public record UP2CauldronInteractions(ResourceLocation name, Map<Item, CauldronInteraction> map) {

    private static final Set<UP2CauldronInteractions> CAULDRON_INTERACTIONS = new ObjectArraySet<>();

    public static UP2CauldronInteractions OOZE = UP2CauldronInteractions.register(UnusualPrehistory2.modPrefix("ooze"), CauldronInteraction.newInteractionMap());

    public static void registerCauldronInteractions() {
        OOZE.map().put(Items.BUCKET, (state, level, pos, player, hand, stack) -> CauldronInteraction.fillBucket(state, level, pos, player, hand, stack, new ItemStack(UP2Items.LIVING_OOZE_BUCKET.get()), (cauldronState) -> cauldronState.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BUCKET_FILL));
        fillWithItem(UP2Items.ORGANIC_OOZE.get(), UP2Blocks.OOZE_CAULDRON.get(), OOZE.map());
    }

    public static void fillWithItem(Item item, Block filledCauldron, Map<Item, CauldronInteraction> map) {
        CauldronInteraction.addDefaultInteractions(map);
        map.put(item, (state, level, pos, player, hand, stack) -> {
            if (state.getValue(LayeredCauldronBlock.LEVEL) != 3) {
                if (!level.isClientSide) {
                    player.awardStat(Stats.USE_CAULDRON);
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    level.setBlockAndUpdate(pos, state.cycle(LayeredCauldronBlock.LEVEL));
                    level.playSound(null, pos, SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        });
        CauldronInteraction.EMPTY.put(item, (state, level, pos, player, hand, stack) -> {
            if (!level.isClientSide) {
                Item item1 = stack.getItem();
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(item1));
                level.setBlockAndUpdate(pos, filledCauldron.defaultBlockState());
                level.playSound(null, pos, SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        });
    }

    public static UP2CauldronInteractions create(ResourceLocation name, Map<Item, CauldronInteraction> map) {
        return new UP2CauldronInteractions(name, map);
    }

    public static synchronized UP2CauldronInteractions register(UP2CauldronInteractions map) {
        CAULDRON_INTERACTIONS.add(map);
        return map;
    }

    public static UP2CauldronInteractions register(ResourceLocation name, Map<Item, CauldronInteraction> map) {
        return register(create(name, map));
    }

    public static ImmutableList<UP2CauldronInteractions> values() {
        return ImmutableList.copyOf(CAULDRON_INTERACTIONS);
    }
}
