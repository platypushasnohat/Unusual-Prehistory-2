package com.barlinc.unusual_prehistory.screens;

import com.barlinc.unusual_prehistory.blocks.entity.TransmogrifierBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TransmogrifierResultSlot extends Slot {

    public final TransmogrifierBlockEntity blockEntity;
    private final Player player;
    private int removeCount;

    public TransmogrifierResultSlot(TransmogrifierBlockEntity blockEntity, Player player, Container container, int index, int x, int y) {
        super(container, index, x, y);
        this.blockEntity = blockEntity;
        this.player = player;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack itemStack) {
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
    public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
        this.checkTakeAchievements(stack);
        super.onTake(player, stack);
    }

    @Override
    protected void onQuickCraft(@NotNull ItemStack stack, int amount) {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level(), this.player, this.removeCount);
        this.removeCount = 0;
    }
}