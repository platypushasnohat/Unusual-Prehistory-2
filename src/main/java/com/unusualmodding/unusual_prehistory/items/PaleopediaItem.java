package com.unusualmodding.unusual_prehistory.items;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PaleopediaItem extends Item {

    private boolean usedOnEntity = false;

    public PaleopediaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        ItemStack itemStackIn = playerIn.getItemInHand(hand);
        if (playerIn instanceof ServerPlayer) {
            ServerPlayer serverplayerentity = (ServerPlayer) playerIn;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, itemStackIn);
            serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
        }
        if (playerIn.level().isClientSide && target.getEncodeId() != null && target.getEncodeId().contains(UnusualPrehistory2.MOD_ID + ":")) {
            usedOnEntity = true;
            String id = target.getEncodeId().replace(UnusualPrehistory2.MOD_ID + ":", "");
            UnusualPrehistory2.PROXY.openPaleopediaGUI(itemStackIn, id);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!usedOnEntity) {
            if (player instanceof ServerPlayer) {
                ServerPlayer serverplayerentity = (ServerPlayer) player;
                CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, itemStack);
                serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
            }
            if (level.isClientSide) {
                UnusualPrehistory2.PROXY.openPaleopediaGUI(itemStack);
            }
        }
        usedOnEntity = false;

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("item.unusual_prehistory.paleopedia.desc").withStyle(ChatFormatting.GRAY));
    }
}
