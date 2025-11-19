package com.barlinc.unusual_prehistory.blocks.blockentity;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.blocks.TransmogrifierBlock;
import com.barlinc.unusual_prehistory.recipes.TransmogrificationRecipe;
import com.barlinc.unusual_prehistory.registry.UP2BlockEntities;
import com.barlinc.unusual_prehistory.registry.UP2Particles;
import com.barlinc.unusual_prehistory.registry.UP2RecipeTypes;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.screens.TransmogrifierMenu;
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
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
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

@SuppressWarnings("unused, FieldCanBeLocal")
public class TransmogrifierBlockEntity extends SyncedBlockEntity implements MenuProvider, WorldlyContainer, Nameable {

    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2};

    private final ItemStackHandler inventory = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.of(() -> inventory);
    protected final ContainerData data;

    private final RecipeType<TransmogrificationRecipe> recipeType;

    private int progress;
    private int maxProgress;

    private int fuel = 0;
    private int maxFuel = 800;

    private Component customName;

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
        boolean didInventoryChange = false;
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
            spawnParticles(level, pos, state);
            if (blockEntity.progress >= processTime) {
                blockEntity.progress = 1;
                blockEntity.assembleRecipe(level, input, recipe);
                didInventoryChange = true;
            }
        }
        if (blockEntity.isTransmogrifying() && !blockEntity.isRemoved() && level.isClientSide()) {
            UnusualPrehistory2.PROXY.playWorldSound(blockEntity, (byte) 0);
        }
        if (!level.isClientSide()) {
            state = state.getBlock().defaultBlockState().setValue(TransmogrifierBlock.LIT, blockEntity.isTransmogrifying()).setValue(TransmogrifierBlock.FACING, state.getValue(TransmogrifierBlock.FACING));
            level.setBlock(pos, state, 3);
        }
        if (didInventoryChange) {
            blockEntity.inventoryChanged();
        }
    }

    public static void spawnParticles(Level level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(TransmogrifierBlock.FACING).getCounterClockWise();
        Direction.Axis axis = direction.getAxis();
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.5D;
        double z = pos.getZ() + 0.5D;
        double offset = 0.0D;
        double xdirection = axis == Direction.Axis.X ? direction.getStepX() * 0.52D : offset;
        double zdirection = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52D : offset;
        double xoffset = 0.0D;
        double zoffset = 0.0D;
        if (direction == Direction.NORTH) {
            xoffset = -0.25D;
        } else if (direction == Direction.SOUTH) {
            xoffset = 0.25D;
        } else if (direction == Direction.EAST) {
            zoffset = -0.25D;
        } else if (direction == Direction.WEST) {
            zoffset = 0.25D;
        }
        double xspeed = direction.getStepX() * 0.2F;
        double zspeed = direction.getStepZ() * 0.2F;
        BlockPos sidePos = pos.relative(direction, 1);
        BlockState sideState = level.getBlockState(sidePos);
        if (level.random.nextInt(10) == 0 && (sideState.isAir() || sideState.getCollisionShape(level, sidePos).isEmpty())) {
            level.addParticle(UP2Particles.OOZE_BUBBLE.get(), (x + xdirection) + xoffset, y - 0.2D, (z + zdirection) + zoffset, xspeed, 0.0D, zspeed);
        }
    }

    @Override
    public void setRemoved() {
        UnusualPrehistory2.PROXY.clearSoundCacheFor(this);
        super.setRemoved();
    }

    protected Optional<TransmogrificationRecipe> getRecipeFor(Level level, Container input) {
        return level.getRecipeManager().getRecipeFor(UP2RecipeTypes.TRANSMOGRIFICATION.get(), input, level);
    }

    private void assembleRecipe(final Level level, final Container input, TransmogrificationRecipe recipe) {
        final ItemStack output = recipe.assemble(input, level.registryAccess());
        if (output.isEmpty()) {
            return;
        }
        final IItemHandler itemHandler = this.inventory;
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
        SimpleContainer inventory = new SimpleContainer(this.inventory.getSlots());
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            inventory.setItem(i, this.inventory.getStackInSlot(i));
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
        ItemStack fuelStack = inventory.getStackInSlot(1);
        int fuelAmount = getFuelAmount(fuelStack);
        if (fuelAmount > 0) {
            inventory.extractItem(1, 1, false);
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
        lazyItemHandler = LazyOptional.of(() -> inventory);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void getDrops() {
        SimpleContainer inventory = new SimpleContainer(this.inventory.getSlots());
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            inventory.setItem(i, this.inventory.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public boolean canRemoveItem(int slot) {
        return slot == 2;
    }

    @Override
    public @NotNull Component getName() {
        return customName != null ? customName : Component.translatable("block.unusual_prehistory.transmogrifier");
    }

    @Override
    public @NotNull Component getDisplayName() {
        return getName();
    }

    @Override
    @Nullable
    public Component getCustomName() {
        return customName;
    }

    public void setCustomName(Component name) {
        customName = name;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new TransmogrifierMenu(id, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.put("Inventory", inventory.serializeNBT());
        compoundTag.putInt("Progress", progress);
        if (customName != null) {
            compoundTag.putString("CustomName", Component.Serializer.toJson(customName));
        }
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        inventory.deserializeNBT(compoundTag.getCompound("Inventory"));
        progress = compoundTag.getInt("Progress");
        if (compoundTag.contains("CustomName", 8)) {
            customName = Component.Serializer.fromJson(compoundTag.getString("CustomName"));
        }
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction direction) {
        if (direction == Direction.UP) {
            return SLOTS_FOR_UP;
        } else {
            return direction == Direction.DOWN ? SLOTS_FOR_DOWN : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack itemStack, @Nullable Direction direction) {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, @NotNull ItemStack itemStack, @NotNull Direction direction) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return this.inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            if (!this.inventory.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return this.inventory.getStackInSlot(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        if (canRemoveItem(slot)) {
            return this.inventory.extractItem(slot, amount, false);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        if (canRemoveItem(slot)) {
            return this.inventory.extractItem(slot, 0, false);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        if (canTakeItem(slot, stack)) {
            this.inventory.setStackInSlot(slot, stack);
        }
    }

    public boolean canTakeItem(int slot, ItemStack stack) {
        return (slot == 0 || slot == 1) && this.inventory.isItemValid(slot, stack);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.inventory.setSize(3);
    }

    public boolean isTransmogrifying() {
        return progress > 0;
    }
}