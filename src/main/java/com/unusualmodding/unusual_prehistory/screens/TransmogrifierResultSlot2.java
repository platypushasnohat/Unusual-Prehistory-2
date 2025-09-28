package com.unusualmodding.unusual_prehistory.screens;

import com.unusualmodding.unusual_prehistory.blocks.blockentity.TransmogrifierBlockEntity2;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class TransmogrifierResultSlot2 extends SlotItemHandler {

    public final TransmogrifierBlockEntity2 blockEntity;
    private final Player player;
    private int removeCount;

    public TransmogrifierResultSlot2(TransmogrifierBlockEntity2 blockEntity, Player player, IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
        this.blockEntity = blockEntity;
        this.player = player;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return false;
    }

    @Override
    @NotNull
    public ItemStack remove(int amount) {
        if (this.hasItem()) {
            this.removeCount += Math.min(amount, this.getItem().getCount());
        }
        return super.remove(amount);
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        super.onTake(player, stack);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount) {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level(), this.player, this.removeCount);

        if (this.player instanceof ServerPlayer serverplayer) {
            this.blockEntity.awardUsedRecipesAndPopExperience(serverplayer);
        }

        this.removeCount = 0;
    }
}
