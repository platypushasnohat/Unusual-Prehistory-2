package com.unusualmodding.unusual_prehistory.blocks.blockentity;

import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import com.unusualmodding.unusual_prehistory.screens.TransmogrifierMenu;
import com.unusualmodding.unusual_prehistory.recipes.TransmogrificationRecipe;
import com.unusualmodding.unusual_prehistory.registry.UP2BlockEntities;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class TransmogrifierBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {

    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2};

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.of(() -> itemHandler);
    protected final ContainerData data;

    private final RecipeType<TransmogrificationRecipe> recipeType;

    private int progress;
    private int maxProgress;

    private int fuel = 0;
    private int maxFuel = 700;

    public TransmogrifierBlockEntity(BlockPos pos, BlockState state) {
        super(UP2BlockEntities.TRANSMOGRIFIER.get(), pos, state);
        this.recipeType = UP2RecipeTypes.TRANSMOGRIFICATION.get();
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> TransmogrifierBlockEntity.this.progress;
                    case 1 -> TransmogrifierBlockEntity.this.maxProgress;
                    case 2 -> TransmogrifierBlockEntity.this.fuel;
                    case 3 -> TransmogrifierBlockEntity.this.maxFuel;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> TransmogrifierBlockEntity.this.progress = value;
                    case 1 -> TransmogrifierBlockEntity.this.maxProgress = value;
                    case 2 -> TransmogrifierBlockEntity.this.fuel = value;
                    case 3 -> TransmogrifierBlockEntity.this.maxFuel = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TransmogrifierBlockEntity blockEntity) {
        if (!blockEntity.hasRecipe(blockEntity, level)) {
            blockEntity.progress = 0;
            if (!blockEntity.hasFuel()) {
                blockEntity.refuel();
            }
            setChanged(level, pos, state);
        } else {
            final Container input = blockEntity.getContainer(level);
            TransmogrificationRecipe recipe = blockEntity.getRecipeFor(level, input).get();
            int processTime = recipe.getProcessingTime();
            blockEntity.maxProgress = processTime;
            blockEntity.progress++;
            blockEntity.depleteFuel();
            setChanged(level, pos, state);
            if (blockEntity.progress >= processTime) {
                blockEntity.progress = 0;
                blockEntity.assembleRecipe(level, input, recipe);
            }
        }
    }

    protected Optional<TransmogrificationRecipe> getRecipeFor(Level level, Container input) {
        return level.getRecipeManager().getRecipeFor(UP2RecipeTypes.TRANSMOGRIFICATION.get(), input, level);
    }

    private void assembleRecipe(final Level level, final Container input, TransmogrificationRecipe recipe) {
        final ItemStack output = recipe.assemble(input, level.registryAccess());
        if (output.isEmpty()) {
            return;
        }
        final IItemHandler itemHandler = this.itemHandler;
        itemHandler.insertItem(2, output, false);
        itemHandler.extractItem(0, 1, false);
    }

    protected boolean hasRecipe(TransmogrifierBlockEntity blockEntity, Level level) {
        final Container inventory = getContainer(level);
        Optional<TransmogrificationRecipe> recipe = getRecipeFor(level, inventory);
        return recipe.isPresent() && blockEntity.hasFuel() && canInsertAmountIntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory, recipe.get().assemble(inventory, level.registryAccess()));
    }

    private static boolean canInsertItemIntoOutputSlot(Container inventory, ItemStack output) {
        return inventory.getItem(2).getItem() == output.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(Container inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount() && inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }

    private Container getContainer(Level level) {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        return inventory;
    }

    private void depleteFuel() {
        this.fuel--;
        if (this.fuel <= 0) {
            fuel = 0;
            refuel();
        }
    }

    private void refuel() {
        ItemStack fuelStack = itemHandler.getStackInSlot(1);
        int fuelAmount = getFuelAmount(fuelStack);
        if (fuelAmount > 0) {
            itemHandler.extractItem(1, 1, false);
            fuel = fuelAmount;
        }
    }

    private boolean hasFuel() {
        return fuel > 0;
    }

    private int getFuelAmount(final ItemStack fuelStack) {
        if (!fuelStack.isEmpty() && fuelStack.is(UP2ItemTags.TRANSMOGRIFIER_FUEL)) {
            return maxFuel;
        }
        return 0;
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    private LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == ForgeCapabilities.ITEM_HANDLER) {
            if (direction == null) {
                return lazyItemHandler.cast();
            } else {
                return handlers[direction.ordinal()].cast();
            }
        }
        return super.getCapability(capability, direction);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public boolean canRemoveItem(int slot) {
        return slot == 2;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.unusual_prehistory.transmogrifier");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new TransmogrifierMenu(id, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        compoundTag.put("inventory", itemHandler.serializeNBT());
        compoundTag.putInt("transmogrifier.progress", progress);
        super.saveAdditional(compoundTag);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        itemHandler.deserializeNBT(compoundTag.getCompound("inventory"));
        progress = compoundTag.getInt("transmogrifier.progress");
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.UP) {
            return SLOTS_FOR_UP;
        } else {
            return direction == Direction.DOWN ? SLOTS_FOR_DOWN : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return this.itemHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            if (!this.itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.itemHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (canRemoveItem(slot)) {
            return this.itemHandler.extractItem(slot, amount, false);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        if (canRemoveItem(slot)) {
            return this.itemHandler.extractItem(slot, 0, false);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (canTakeItem(slot, stack)) {
            this.itemHandler.setStackInSlot(slot, stack);
        }
    }

    public boolean canTakeItem(int slot, ItemStack stack) {
        return (slot == 0 || slot == 1) && this.itemHandler.isItemValid(slot, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.itemHandler.setSize(3);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }
}
