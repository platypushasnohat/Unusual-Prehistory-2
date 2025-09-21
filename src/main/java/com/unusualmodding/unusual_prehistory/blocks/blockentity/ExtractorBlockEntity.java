package com.unusualmodding.unusual_prehistory.blocks.blockentity;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.menus.ExtractorMenu;
import com.unusualmodding.unusual_prehistory.recipes.data.ExtractingRecipeJsonManager;
import com.unusualmodding.unusual_prehistory.registry.UP2BlockEntities;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ExtractorBlockEntity extends BlockEntity implements MenuProvider {

    public ExtractorBlockEntity(BlockPos pos, BlockState state) {

        super(UP2BlockEntities.EXTRACTOR_BLOCK_ENTITY.get(), pos, state);

        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> ExtractorBlockEntity.this.progress;
                    case 1 -> ExtractorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ExtractorBlockEntity.this.progress = value;
                    case 1 -> ExtractorBlockEntity.this.maxProgress = value;
                }
            }

            public int getCount() {
                return 8;
            }
        };
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(8) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 64;
        }
    };

    private IItemHandler hopperHandler = new IItemHandler() {
        @Override
        public int getSlots() {
            return itemHandler.getSlots();
        }

        @NotNull
        @Override
        public ItemStack getStackInSlot(int slot) {
            return itemHandler.getStackInSlot(slot);
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot != 0 && slot != 1) {
                return itemHandler.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY;
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (stack.isEmpty()) {
                return stack;
            }

            if (slot == 0 && stack.is(Items.GLASS_BOTTLE)){
                return itemHandler.insertItem(slot, stack, simulate);
            }

            if (slot == 1 && stack.is(UP2ItemTags.FOSSILS)) {
                return itemHandler.insertItem(slot, stack, simulate);
            }

            return stack;
        }

        @Override
        public int getSlotLimit(int slot) {
            return itemHandler.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return itemHandler.isItemValid(slot, stack);
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandlerOptional = LazyOptional.of(() -> itemHandler);
    private LazyOptional<IItemHandler> hopperHandlerOptional = LazyOptional.of(() -> hopperHandler);

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 144;

    @Override
    public Component getDisplayName() {
        return Component.translatable(UnusualPrehistory2.MOD_ID + ".blockentity.extractor");
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction direction) {
        if (capability == ForgeCapabilities.ITEM_HANDLER) {
            if (direction == null) {
                return lazyItemHandlerOptional.cast();
            } else{
                return hopperHandlerOptional.cast();
            }
        }
        return super.getCapability(capability, direction);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandlerOptional = LazyOptional.of(() -> itemHandler);
        hopperHandlerOptional = LazyOptional.of(() -> hopperHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandlerOptional.invalidate();
        hopperHandlerOptional.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("extractor.progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("extractor.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ExtractorBlockEntity pBlockEntity) {
        if (hasRecipe(pBlockEntity)) {
            pBlockEntity.progress = Math.min(pBlockEntity.progress + 1, pBlockEntity.maxProgress);
            setChanged(pLevel, pPos, pState);
            if(pBlockEntity.progress >= pBlockEntity.maxProgress && !pLevel.isClientSide()) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(ExtractorBlockEntity entity) {
        Level level = entity.level;

        if (ExtractingRecipeJsonManager.getRecipes().isEmpty() && !level.isClientSide) {
            ExtractingRecipeJsonManager.populateRecipeMap(level);
        }

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());

        for (int i = 1; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        boolean hasRecipe = ExtractingRecipeJsonManager.containsRecipe(inventory.getItem(1).getItem());

        return hasRecipe && canInsertAmountIntoOutputSlot(inventory) && hasFlaskInWaterSlot(entity);
    }

    private static boolean hasFlaskInWaterSlot(ExtractorBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(0).getItem() == Items.GLASS_BOTTLE;
    }

    private static void craftItem(ExtractorBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        boolean hasRecipe = ExtractingRecipeJsonManager.containsRecipe(inventory.getItem(1).getItem());

        if (hasRecipe) {
            ItemStack result = ExtractingRecipeJsonManager.getRandomItemStack(inventory.getItem(1).getItem(), level);

            entity.itemHandler.extractItem(1, 1, false);

            if (isDNABottle(result)) {
                entity.itemHandler.extractItem(0, 1, false);
            }

            for (int i = 2, n = entity.itemHandler.getSlots(); i < n; i++) {
                if (entity.itemHandler.insertItem(i, result, false).isEmpty()) {
                    break;
                }
            }
            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        for (int slot = 2, n = inventory.getContainerSize(); slot < n; slot++) {
            if (inventory.getItem(slot).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDNABottle(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.DNA_BOTTLES);
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new ExtractorMenu(containerId, inventory, this, this.data);
    }
}
