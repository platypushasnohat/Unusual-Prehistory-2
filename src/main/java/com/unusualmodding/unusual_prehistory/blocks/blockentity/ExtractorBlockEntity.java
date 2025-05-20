package com.unusualmodding.unusual_prehistory.blocks.blockentity;

import com.unusualmodding.unusual_prehistory.registry.UP2BlockEntities;
import com.unusualmodding.unusual_prehistory.recipes.ExtractingRecipe;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeTypes;
import com.unusualmodding.unusual_prehistory.client.screens.ExtractorMenu;
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

import java.util.Optional;

public class ExtractorBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {

    private final ItemStackHandler itemHandler = new ItemStackHandler(8){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int BOTTLE_SLOT = 1;
    private static final int[] SLOTS_FOR_UP = new int[] {INPUT_SLOT};
    private static final int[] SLOTS_FOR_SIDES= new int[] {BOTTLE_SLOT};
    private static final int[] SLOTS_FOR_DOWN = new int[] {2,3,4,5,6,7};

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress;
    private int maxProgress;

    public ExtractorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(UP2BlockEntities.EXTRACTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> ExtractorBlockEntity.this.progress;
                    case 1 -> ExtractorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> ExtractorBlockEntity.this.progress = pValue;
                    case 1 -> ExtractorBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ExtractorBlockEntity pBlockEntity) {
        if (pBlockEntity.hasRecipe(pLevel)) {
            final Container input = pBlockEntity.getContainer(pLevel);
            ExtractingRecipe recipe = pBlockEntity.getRecipeFor(pLevel, input).get();
            int processTime = recipe.getProcessTime();
            pBlockEntity.maxProgress = processTime;
            pBlockEntity.progress = Math.min(pBlockEntity.progress + 1, processTime);
            setChanged(pLevel, pPos, pState);

            if (pBlockEntity.progress >= processTime) {
                pBlockEntity.craftItem(pLevel, recipe);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    protected Optional<ExtractingRecipe> getRecipeFor(Level level, Container input) {
        return level.getRecipeManager().getRecipeFor(UP2RecipeTypes.EXTRACTING_RECIPE.get(), input, level);
    }

    private void craftItem(Level level, ExtractingRecipe recipe) {

        ItemStack result = recipe.getOutputItem(level.getRandom());

        this.itemHandler.extractItem(0, 1, false);
        this.itemHandler.extractItem(1, 1, false);

        boolean success = false;
        for (int i = 2, n = this.itemHandler.getSlots(); i < n; i++) {
            if (this.itemHandler.insertItem(i, result, false).isEmpty()) {
                success = true;
                break;
            }
        }
        this.resetProgress();
    }

    private void resetProgress() {
        this.progress = 0;
    }

    protected boolean hasRecipe(Level level) {
        final Container input = getContainer(level);
        Optional<ExtractingRecipe> oRecipe = getRecipeFor(level, input);
        return oRecipe.isPresent() && canInsertAmountIntoOutputSlot(input);
    }

    private Container getContainer(Level level) {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        return inventory;
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    private LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            } else{
                return handlers[side.ordinal()].cast();
            }
        }
        return super.getCapability(cap, side);
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
        for (int x = 0; x < handlers.length; x++) handlers[x].invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.unusual_prehistory.extractor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ExtractorMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public boolean canTakeItem(int slot, @NotNull ItemStack stack) {

        if (slot == 0 && stack.is(UP2ItemTags.EXTRACTOR_INPUT)) {
            return true;
        }

        if (slot == 1 && stack.is(Items.GLASS_BOTTLE)) {
            return true;
        }

        return false;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("extractor.progress", progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("extractor.progress");
    }

    private static boolean canInsertAmountIntoOutputSlot(Container inventory) {
        for (int slot = 2, n = inventory.getContainerSize(); slot < n; slot++) {
            if (inventory.getItem(slot).isEmpty()) {
                return true;
            }
        }
        return false;
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
    public boolean canPlaceItemThroughFace(int slot, ItemStack itemStack, @Nullable Direction direction) {
        return slot == 0 && itemStack.is(UP2ItemTags.EXTRACTOR_INPUT) || slot == 1 && itemStack.is(Items.GLASS_BOTTLE);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack itemStack, Direction direction) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return this.itemHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            if(!this.itemHandler.getStackInSlot(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return this.itemHandler.getStackInSlot(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        if (canRemoveItem(pSlot)) {
            return this.itemHandler.extractItem(pSlot, pAmount, false);
        }
        return ItemStack.EMPTY;
    }

    public boolean canRemoveItem(int slot) {
        return (slot != 1) && (slot != 0);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        if (canRemoveItem(pSlot)) {
            return this.itemHandler.extractItem(pSlot, 0, false);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int pSlot, ItemStack itemStack) {
        if (canTakeItem(pSlot, itemStack)) {
            this.itemHandler.setStackInSlot(pSlot, itemStack);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.itemHandler.setSize(8);
    }
}