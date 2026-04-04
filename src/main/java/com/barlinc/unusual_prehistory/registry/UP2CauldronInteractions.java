package com.barlinc.unusual_prehistory.registry;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteraction.InteractionMap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LayeredCauldronBlock;

public class UP2CauldronInteractions {

    public static InteractionMap OOZE = CauldronInteraction.newInteractionMap("unusual_prehistory:ooze");

    public static void registerCauldronInteractions() {
        addDefaultCauldronInteraction(UP2Items.LIVING_OOZE_BUCKET.get(), FILL_OOZE);
        OOZE.map().put(Items.BUCKET, (state, level, pos, player, hand, stack) -> CauldronInteraction.fillBucket(state, level, pos, player, hand, stack, new ItemStack(UP2Items.LIVING_OOZE_BUCKET.get()), (cauldronState) -> cauldronState.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.SLIME_BLOCK_PLACE));
    }

    public static final CauldronInteraction FILL_OOZE = (state, level, pos, player, hand, stack) -> CauldronInteraction.emptyBucket(level, pos, player, hand, stack, UP2Blocks.OOZE_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), SoundEvents.SLIME_BLOCK_BREAK);

    public static void addDefaultCauldronInteraction(Item item, CauldronInteraction interaction) {
        CauldronInteraction.INTERACTIONS.forEach((str, map) -> map.map().put(item, interaction));
    }
}
