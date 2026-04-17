package com.barlinc.unusual_prehistory.screens;

import com.barlinc.unusual_prehistory.blocks.entity.TransmogrifierBlockEntity;
import com.barlinc.unusual_prehistory.registry.UP2MenuTypes;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TransmogrifierMenu extends AbstractContainerMenu {

    private final Container container;
    public final ContainerData data;

    public TransmogrifierMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(4), new SimpleContainerData(4));
    }

    public TransmogrifierMenu(int syncId, Inventory playerInventory, Container container, ContainerData data) {
        super(UP2MenuTypes.TRANSMOGRIFIER.get(), syncId);
        this.container = container;
        this.data = data;
        this.addSlot(new TransmogrifierOozeSlot(container, 1, 82, 59));
        this.addSlot(new Slot(container, 0, 37, 31));
        this.addSlot(new TransmogrifierOutputSlot(playerInventory.player, container, 2, 125, 31));
        this.addPlayerInventory(playerInventory);
        this.addPlayerHotbar(playerInventory);
        this.addDataSlots(data);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (invSlot < this.container.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return newStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.container.stillValid(player);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledFuel(int scale) {
        int fuel = this.data.get(0);
        int maxFuel = this.data.get(1);
        if (fuel == 0 || maxFuel == 0) {
            return 0;
        }
        return Mth.ceil((float) scale * (float) fuel / (float) maxFuel);
    }

    public int getScaledProgress(int scale) {
        int progress = this.data.get(2);
        int maxProgress = this.data.get(3);
        if (progress == 0 || maxProgress == 0) {
            return 0;
        }
        return Mth.ceil((float) scale * (float) progress / (float) maxProgress);
    }

    public static class TransmogrifierOutputSlot extends Slot {

        private final Player player;
        private int removeCount;

        public TransmogrifierOutputSlot(Player player, Container container, int slot, int xPosition, int yPosition) {
            super(container, slot, xPosition, yPosition);
            this.player = player;
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return false;
        }

        @Override
        public @NotNull ItemStack remove(int amount) {
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
            if (this.player instanceof ServerPlayer serverplayer) {
                if (this.container instanceof TransmogrifierBlockEntity blockEntity) {
                    blockEntity.awardUsedRecipesAndPopExperience(serverplayer);
                }
            }
            this.removeCount = 0;
        }
    }

    public static class TransmogrifierOozeSlot extends Slot {

        public TransmogrifierOozeSlot(Container inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.is(UP2ItemTags.TRANSMOGRIFIER_FUEL);
        }
    }
}