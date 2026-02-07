package com.barlinc.unusual_prehistory.screens;

import com.barlinc.unusual_prehistory.entity.Manipulator;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ManipulatorContainer extends AbstractContainerMenu {

    private final Container manipulatorInventory;
    private final Manipulator manipulator;

    public ManipulatorContainer(int id, Inventory playerInventory, Container manipulatorInventory, final Manipulator manipulator) {
        super(null, id);
        this.manipulatorInventory = manipulatorInventory;
        this.manipulator = manipulator;
        manipulatorInventory.startOpen(playerInventory.player);
        this.addSlot(new Slot(manipulatorInventory, 0, 8, 18) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return !playerInventory.player.level().isClientSide;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public void set(@NotNull ItemStack stack) {
                super.set(stack);
                manipulator.setItemSlot(EquipmentSlot.MAINHAND, stack);
            }

            @Override
            public boolean mayPickup(@NotNull Player player) {
                return !playerInventory.player.level().isClientSide;
            }
        });
        this.addSlot(new Slot(manipulatorInventory, 1, 8, 36) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return !playerInventory.player.level().isClientSide;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public void set(@NotNull ItemStack stack) {
                super.set(stack);
                manipulator.setItemSlot(EquipmentSlot.OFFHAND, stack);
            }

            @Override
            public boolean mayPickup(@NotNull Player player) {
                return !playerInventory.player.level().isClientSide;
            }
        });
        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < 9; j1++) {
                this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }
        for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.manipulatorInventory.stillValid(player) && this.manipulator.isAlive() && this.manipulator.distanceTo(player) < 8.0F;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int i = this.manipulatorInventory.getContainerSize();
            if (index < i) {
                if (!this.moveItemStackTo(itemstack1, i, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).mayPlace(itemstack1) && !this.getSlot(1).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).mayPlace(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i <= 2 || !this.moveItemStackTo(itemstack1, 2, i, false)) {
                int j = i + 27;
                int k = j + 9;
                if (index >= j && index < k) {
                    if (!this.moveItemStackTo(itemstack1, i, j, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= i && index < j) {
                    if (!this.moveItemStackTo(itemstack1, j, k, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, j, j, false)) {
                    return ItemStack.EMPTY;
                }

                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.manipulatorInventory.stopOpen(player);
        this.manipulator.interacting = false;
    }
}