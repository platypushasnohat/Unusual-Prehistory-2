package com.unusualmodding.unusual_prehistory.menus;

import com.unusualmodding.unusual_prehistory.blocks.blockentity.CultivatorBlockEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2MenuTypes;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CultivatorMenu extends AbstractContainerMenu {

    private final CultivatorBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public CultivatorMenu(int containerId, Inventory inventory, FriendlyByteBuf data) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(5));
    }

    public CultivatorMenu(int containerId, Inventory inventory, BlockEntity blockEntity, ContainerData data) {
        super(UP2MenuTypes.CULTIVATOR_MENU.get(), containerId);

        checkContainerSize(inventory, 4);

        this.blockEntity = ((CultivatorBlockEntity) blockEntity);
        this.level = inventory.player.level();
        this.data = data;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new OozeSlot(handler, 1, 80, 64));
            this.addSlot(new SlotItemHandler(handler, 0, 33, 31));

            this.addSlot(new UP2ResultSlot(handler, 2, 128, 31));
            this.addSlot(new UP2ResultSlot(handler, 3, 152, 64));
        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress(int scale) {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        if (progress == 0 || maxProgress == 0) {
            return 0;
        }
        return Mth.ceil((float) scale * (float) progress / (float) maxProgress);
    }

    public int getScaledFuel(int scale) {
        int fuel = this.data.get(2);
        int maxFuel = this.data.get(3);
        if (fuel == 0 || maxFuel == 0) {
            return 0;
        }
        return Mth.ceil((float) scale * (float) fuel / (float) maxFuel);
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int INVENTORY_ROW_COUNT = 3;
    private static final int INVENTORY_COLUMN_COUNT = 9;
    private static final int INVENTORY_SLOT_COUNT = INVENTORY_COLUMN_COUNT * INVENTORY_ROW_COUNT;
    private static final int SLOT_COUNT = HOTBAR_SLOT_COUNT + INVENTORY_SLOT_COUNT;
    private static final int FIRST_SLOT_INDEX = 0;

    private static final int CULTIVATOR_SLOT_INDEX = FIRST_SLOT_INDEX + SLOT_COUNT;
    private static final int CULTIVATOR_SLOT_COUNT = 4;

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = slots.get(index);

        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index < FIRST_SLOT_INDEX + SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, CULTIVATOR_SLOT_INDEX, CULTIVATOR_SLOT_INDEX + CULTIVATOR_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < CULTIVATOR_SLOT_INDEX + CULTIVATOR_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, FIRST_SLOT_INDEX, FIRST_SLOT_INDEX + SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, UP2Blocks.CULTIVATOR.get());
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

    private static class OozeSlot extends SlotItemHandler {

        public OozeSlot(IItemHandler itemHandler, int index, int x, int y) {
            super(itemHandler, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack itemStack) {
            return super.mayPlace(itemStack) && itemStack.is(UP2ItemTags.CULTIVATOR_FUEL);
        }
    }
}
