package com.unusualmodding.unusual_prehistory.blocks.blockentity;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.screens.TransmogrifierMenu;
import com.unusualmodding.unusual_prehistory.network.SyncItemStackC2SPacket;
import com.unusualmodding.unusual_prehistory.recipes.TransmogrificationRecipe;
import com.unusualmodding.unusual_prehistory.registry.UP2BlockEntities;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import com.unusualmodding.unusual_prehistory.registry.UP2Network;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeTypes;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

import javax.annotation.Nonnull;
import java.util.Optional;

public class TransmogrifierBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {

    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_SIDES= new int[]{1};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 3};

    public TransmogrifierBlockEntity(BlockPos pos, BlockState blockstate) {
        super(UP2BlockEntities.TRANSMOGRIFIER.get(), pos, blockstate);
        this.data = new ContainerData() {

            public int get(int index) {
                return switch (index) {
                    case 0 -> TransmogrifierBlockEntity.this.progress;
                    case 1 -> TransmogrifierBlockEntity.this.maxProgress;
                    case 2 -> TransmogrifierBlockEntity.this.fuel;
                    case 3 -> TransmogrifierBlockEntity.this.maxFuel;
                    case 4 -> TransmogrifierBlockEntity.this.tickCount;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> TransmogrifierBlockEntity.this.progress = value;
                    case 1 -> TransmogrifierBlockEntity.this.maxProgress = value;
                    case 2 -> TransmogrifierBlockEntity.this.fuel = value;
                    case 3 -> TransmogrifierBlockEntity.this.maxFuel = value;
                    case 4 -> TransmogrifierBlockEntity.this.tickCount = value;
                }
            }
            public int getCount() {
                return 5;
            }
        };
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide) {
                UP2Network.sendToClients(new SyncItemStackC2SPacket(this, worldPosition));
            }
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandlerOptional = LazyOptional.of(() -> itemHandler);

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 1152;
    private int fuel = 0;
    private int maxFuel = 790;

    public int tickCount = 0;

    @Override
    public Component getDisplayName() {
        return Component.translatable(UnusualPrehistory2.MOD_ID + ".blockentity.transmogrifier");
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandlerOptional = LazyOptional.of(() -> itemHandler);
        if (level != null && !level.isClientSide) {
            UP2Network.sendToClients(new SyncItemStackC2SPacket(this.itemHandler, worldPosition));
        }
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandlerOptional.invalidate();
        for (LazyOptional<? extends IItemHandler> handler : handlers) handler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.putInt("Progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        progress = nbt.getInt("Progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public ItemStack getRenderStack() {
        ItemStack stack;
        if(!itemHandler.getStackInSlot(2).isEmpty()){
            stack = itemHandler.getStackInSlot(2);
        } else{
            stack = itemHandler.getStackInSlot(0);
        }
        return stack;
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++){
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TransmogrifierBlockEntity blockEntity) {
        blockEntity.tickCount++;

        if (hasRecipe(blockEntity)) {
            blockEntity.progress++;
            blockEntity.depleteFuel();
            setChanged(level, pos, state);
            if (blockEntity.progress > blockEntity.maxProgress) {
                craftItem(blockEntity);
            }
        } else {
            blockEntity.resetProgress();
            if(!blockEntity.hasFuel()) {
                blockEntity.refuel();
            }
            setChanged(level, pos, state);
        }
    }

    private static boolean hasRecipe(TransmogrifierBlockEntity blockEntity) {
        Level level = blockEntity.level;
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemHandler.getSlots());

        for (int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
        }

        Optional<TransmogrificationRecipe> match = level.getRecipeManager().getRecipeFor(UP2RecipeTypes.TRANSMOGRIFICATION.get(), inventory, level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory, match.get().assemble(inventory, level.registryAccess())) && canDiscardFlask(inventory, new ItemStack(Items.GLASS_BOTTLE)) && blockEntity.hasFuel();
    }

    private static void craftItem(TransmogrifierBlockEntity blockEntity) {
        Level level = blockEntity.level;
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemHandler.getSlots());

        for (int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
        }

        Optional<TransmogrificationRecipe> match = level.getRecipeManager().getRecipeFor(UP2RecipeTypes.TRANSMOGRIFICATION.get(), inventory, level);

        if (match.isPresent()) {
            blockEntity.itemHandler.extractItem(0,1, false);
            blockEntity.itemHandler.insertItem(2, match.get().assemble(inventory, level.registryAccess()), false);
            blockEntity.itemHandler.insertItem(3, new ItemStack(Items.GLASS_BOTTLE), false);
            blockEntity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
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
        if (!fuelStack.isEmpty() && fuelStack.is(UP2ItemTags.CULTIVATOR_FUEL)) {
            return maxFuel;
        }
        return 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(2).getItem() == output.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canDiscardFlask(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(3).getItem() == output.getItem() || inventory.getItem(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount() && inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new TransmogrifierMenu(containerId, inventory, this, this.data);
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
        return canTakeItem(pIndex, pItemStack);
    }

    public boolean canTakeItem(int slot, ItemStack stack) {
        if (slot == 0 && stack.is(UP2ItemTags.DNA_BOTTLES)) {
            return true;
        }
        return slot == 1 && stack.is(UP2Items.ORGANIC_OOZE.get());
    }

    public boolean canRemoveItem(int slot) {
        return (slot == 2) || (slot == 3);
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
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
    public void setItem(int slot, ItemStack itemStack) {
        if (canTakeItem(slot, itemStack)) {
            this.itemHandler.setStackInSlot(slot, itemStack);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.itemHandler.setSize(4);
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    private LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction direction) {
        if (capability == ForgeCapabilities.ITEM_HANDLER) {
            if (direction == null) {
                return lazyItemHandlerOptional.cast();
            } else {
                return handlers[direction.ordinal()].cast();
            }
        }
        return super.getCapability(capability, direction);
    }
}
