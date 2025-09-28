package com.unusualmodding.unusual_prehistory.blocks.blockentity;

import com.google.common.collect.Lists;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import com.unusualmodding.unusual_prehistory.screens.TransmogrifierMenu;
import com.unusualmodding.unusual_prehistory.recipes.TransmogrificationRecipe;
import com.unusualmodding.unusual_prehistory.registry.UP2BlockEntities;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeTypes;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class TransmogrifierBlockEntity extends SyncedBlockEntity implements MenuProvider, WorldlyContainer, RecipeHolder {

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
    private final Object2IntOpenHashMap<ResourceLocation> usedRecipeTracker;

    private int progress;
    private int maxProgress;

    private int fuel = 0;
    private int maxFuel = 700;

    public TransmogrifierBlockEntity(BlockPos pos, BlockState state) {
        super(UP2BlockEntities.TRANSMOGRIFIER.get(), pos, state);
        this.usedRecipeTracker = new Object2IntOpenHashMap<>();
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
        super.saveAdditional(compoundTag);
        compoundTag.put("Inventory", inventory.serializeNBT());
        compoundTag.putInt("Progress", progress);
        CompoundTag compoundRecipes = new CompoundTag();
        usedRecipeTracker.forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount));
        compoundTag.put("RecipesUsed", compoundRecipes);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        inventory.deserializeNBT(compoundTag.getCompound("Inventory"));
        progress = compoundTag.getInt("Progress");
        CompoundTag compoundRecipes = compoundTag.getCompound("RecipesUsed");
        for (String key : compoundRecipes.getAllKeys()) {
            usedRecipeTracker.put(new ResourceLocation(key), compoundRecipes.getInt(key));
        }
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
    public ItemStack getItem(int slot) {
        return this.inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (canRemoveItem(slot)) {
            return this.inventory.extractItem(slot, amount, false);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        if (canRemoveItem(slot)) {
            return this.inventory.extractItem(slot, 0, false);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (canTakeItem(slot, stack)) {
            this.inventory.setStackInSlot(slot, stack);
        }
    }

    public boolean canTakeItem(int slot, ItemStack stack) {
        return (slot == 0 || slot == 1) && this.inventory.isItemValid(slot, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.inventory.setSize(3);
    }

    @Override
    public void awardUsedRecipes(Player player, List<ItemStack> items) {
        List<Recipe<?>> usedRecipes = getUsedRecipesAndPopExperience(player.level(), player.position());
        player.awardRecipes(usedRecipes);
        usedRecipeTracker.clear();
    }

    public List<Recipe<?>> getUsedRecipesAndPopExperience(Level level, Vec3 pos) {
        List<Recipe<?>> list = Lists.newArrayList();

        for (Object2IntMap.Entry<ResourceLocation> entry : usedRecipeTracker.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                splitAndSpawnExperience((ServerLevel) level, pos, entry.getIntValue(), ((TransmogrificationRecipe) recipe).getExperience());
            });
        }

        return list;
    }

    private static void splitAndSpawnExperience(ServerLevel level, Vec3 pos, int craftedAmount, float experience) {
        int expTotal = Mth.floor((float) craftedAmount * experience);
        float expFraction = Mth.frac((float) craftedAmount * experience);
        if (expFraction != 0.0F && Math.random() < (double) expFraction) {
            ++expTotal;
        }
        ExperienceOrb.award(level, pos, expTotal);
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < 2; ++i) {
            if (i != 2) {
                drops.add(inventory.getStackInSlot(i));
            }
        }
        return drops;
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation recipeID = recipe.getId();
            usedRecipeTracker.addTo(recipeID, 1);
        }
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        return null;
    }
}