//package com.unusualmodding.unusual_prehistory.blocks.blockentity;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.*;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.inventory.AbstractContainerMenu;
//import net.minecraft.world.inventory.ContainerData;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.RecipeType;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.ForgeCapabilities;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.items.IItemHandler;
//import net.minecraftforge.items.ItemStackHandler;
//import net.minecraftforge.items.wrapper.SidedInvWrapper;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.Optional;
//
//public class CultivatorEntity extends BlockEntity implements MenuProvider, WorldlyContainer {
//
//    private static final int[] SLOTS_FOR_UP = new int[]{0};
//    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
//    private static final int[] SLOTS_FOR_DOWN = new int[]{2};
//
//    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
//        @Override
//        protected void onContentsChanged(int slot) {
//            setChanged();
//        }
//    };
//    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.of(() -> itemHandler);
//    protected final ContainerData data;
//
//    private final RecipeType<CultivatorRecipe> recipeType;
//    private int progress;
//    private int maxProgress;
//
//
//    public CultivatorEntity(BlockPos pPos, BlockState pBlockState) {
//        super(UP2BlockEntities.CULTIVATOR_BE.get(), pPos, pBlockState);
//        this.recipeType = UP2RecipeTypes.CULTIVATOR_TYPE.get();
//        this.data = new ContainerData() {
//            @Override
//            public int get(int pIndex) {
//                return switch (pIndex) {
//                    case 0 -> CultivatorEntity.this.progress;
//                    case 1 -> CultivatorEntity.this.maxProgress;
//                    default -> 0;
//                };
//            }
//
//            @Override
//            public void set(int pIndex, int pValue) {
//                switch (pIndex) {
//                    case 0 -> CultivatorEntity.this.progress = pValue;
//                    case 1 -> CultivatorEntity.this.maxProgress = pValue;
//                }
//            }
//
//            @Override
//            public int getCount() {
//                return 2;
//            }
//        };
//    }
//
//    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, CultivatorEntity entity) {
//        if (!entity.hasRecipe(pLevel)) {
//            entity.progress = 0;
//            setChanged(pLevel,pPos,pState);
//        } else {
//            final Container input = entity.getContainer(pLevel);
//            CultivatorRecipe recipe = entity.getRecipeFor(pLevel, input).get();
//            int processTime = recipe.getProcessTime();
//            entity.maxProgress = processTime;
//            if (entity.itemHandler.getStackInSlot(2).isEmpty()) {
//                entity.progress++;
//                setChanged(pLevel,pPos,pState);
//            }
//            if (entity.progress >= processTime) {
//                entity.progress = 0;
//                entity.assembleRecipe(pLevel, input, recipe);
//            }
//        }
//    }
//
//    protected Optional<CultivatorRecipe> getRecipeFor(Level level, Container input) {
//        return level.getRecipeManager().getRecipeFor(ModRecipeTypes.CULTIVATOR_TYPE.get(), input, level);
//    }
//
//    private void assembleRecipe(final Level level, final Container input, CultivatorRecipe recipe) {
//        final ItemStack output = recipe.assemble(input, level.registryAccess());
//        if (output.isEmpty()) {
//            return;
//        }
//
//        final IItemHandler itemHandler = this.itemHandler;
//        itemHandler.insertItem(2, output, false);
//        itemHandler.extractItem(0, 1, false);
//        itemHandler.extractItem(1, 1, false);
//    }
//
//    protected boolean hasRecipe(Level level) {
//        final Container input = getContainer(level);
//        Optional<CultivatorRecipe> oRecipe = getRecipeFor(level, input);
//        return oRecipe.isPresent();
//    }
//
//    private Container getContainer(Level level) {
//        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
//        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
//            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
//        }
//        return inventory;
//    }
//
//    @Override
//    public void reviveCaps() {
//        super.reviveCaps();
//        this.handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
//    }
//
//    private LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
//
//    @Override
//    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
//        if (cap == ForgeCapabilities.ITEM_HANDLER) {
//            if (side == null) {
//                return lazyItemHandler.cast();
//            } else {
//                return handlers[side.ordinal()].cast();
//            }
//
//        }
//        return super.getCapability(cap, side);
//    }
//
//    @Override
//    public void onLoad() {
//        super.onLoad();
//        lazyItemHandler = LazyOptional.of(() -> itemHandler);
//    }
//
//    @Override
//    public void invalidateCaps() {
//        super.invalidateCaps();
//        lazyItemHandler.invalidate();
//    }
//
//    public void drops() {
//        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
//        for (int i = 0; i < itemHandler.getSlots(); i++) {
//            inventory.setItem(i, itemHandler.getStackInSlot(i));
//        }
//        Containers.dropContents(this.level, this.worldPosition, inventory);
//    }
//
//    public boolean canRemoveItem(int slot) {
//        return slot == 2;
//    }
//
//    @Override
//    public Component getDisplayName() {
//        return Component.translatable("block.unusual_prehistory.cultivator");
//    }
//
//    @Nullable
//    @Override
//    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
//        return new CultivatorMenu(pContainerId, pPlayerInventory, this, this.data);
//    }
//
//    @Override
//    protected void saveAdditional(CompoundTag pTag) {
//        pTag.put("inventory", itemHandler.serializeNBT());
//        pTag.putInt("cultivator.progress", progress);
//        super.saveAdditional(pTag);
//    }
//
//    @Override
//    public void load(CompoundTag pTag) {
//        super.load(pTag);
//        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
//        progress = pTag.getInt("cultivator.progress");
//    }
//
//    @Override
//    public int[] getSlotsForFace(Direction direction) {
//        if (direction == Direction.UP) {
//            return SLOTS_FOR_UP;
//        } else {
//            return direction == Direction.DOWN ? SLOTS_FOR_DOWN : SLOTS_FOR_SIDES;
//        }
//    }
//
//    @Override
//    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
//        return true;
//    }
//
//    @Override
//    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
//        return true;
//    }
//
//    @Override
//    public int getContainerSize() {
//        return this.itemHandler.getSlots();
//    }
//
//    @Override
//    public boolean isEmpty() {
//        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
//            if (!this.itemHandler.getStackInSlot(i).isEmpty()) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public ItemStack getItem(int pSlot) {
//        return this.itemHandler.getStackInSlot(pSlot);
//    }
//
//    @Override
//    public ItemStack removeItem(int pSlot, int pAmount) {
//        if (canRemoveItem(pSlot)) {
//            return this.itemHandler.extractItem(pSlot, pAmount, false);
//        }
//        return ItemStack.EMPTY;
//    }
//
//    @Override
//    public ItemStack removeItemNoUpdate(int pSlot) {
//        if (canRemoveItem(pSlot)) {
//            return this.itemHandler.extractItem(pSlot, 0, false);
//        }
//        return ItemStack.EMPTY;
//    }
//
//    @Override
//    public void setItem(int pSlot, ItemStack pStack) {
//        if (canTakeItem(pSlot, pStack)) {
//            this.itemHandler.setStackInSlot(pSlot, pStack);
//        }
//    }
//
//
//    public boolean canTakeItem(int slot, ItemStack stack) {
//        return (slot == 0 || slot == 1) && this.itemHandler.isItemValid(slot, stack);
//    }
//
//    @Override
//    public boolean stillValid(Player player) {
//        return Container.stillValidBlockEntity(this, player);
//    }
//
//    @Override
//    public void clearContent() {
//        this.itemHandler.setSize(3);
//    }
//}
