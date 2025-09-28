package com.unusualmodding.unusual_prehistory.blocks.blockentity;

import com.google.common.collect.Lists;
import com.unusualmodding.unusual_prehistory.recipes.TransmogrificationRecipe;
import com.unusualmodding.unusual_prehistory.registry.UP2BlockEntities;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeTypes;
import com.unusualmodding.unusual_prehistory.screens.TransmogrifierMenu2;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.List;

public class TransmogrifierBlockEntity2 extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible {

    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};

    private final RecipeType<TransmogrificationRecipe> recipeType;
    private final RecipeManager.CachedCheck<Container, TransmogrificationRecipe> quickCheck;

    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

    int activeTime;
    int activeDuration;
    int transmogrificationTime;
    int transmogrificationTotalTime;

    protected final ContainerData dataAccess = new ContainerData() {

        public int get(int i) {
            return switch (i) {
                case 0 -> TransmogrifierBlockEntity2.this.activeTime;
                case 1 -> TransmogrifierBlockEntity2.this.activeDuration;
                case 2 -> TransmogrifierBlockEntity2.this.transmogrificationTime;
                case 3 -> TransmogrifierBlockEntity2.this.transmogrificationTotalTime;
                default -> 0;
            };
        }

        public void set(int i, int value) {
            switch (i) {
                case 0:
                    TransmogrifierBlockEntity2.this.activeTime = value;
                    break;
                case 1:
                    TransmogrifierBlockEntity2.this.activeDuration = value;
                    break;
                case 2:
                    TransmogrifierBlockEntity2.this.transmogrificationTime = value;
                    break;
                case 3:
                    TransmogrifierBlockEntity2.this.transmogrificationTotalTime = value;
            }
        }

        public int getCount() {
            return 4;
        }
    };

    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();

    protected TransmogrifierBlockEntity2(BlockPos pos, BlockState state) {
        super(UP2BlockEntities.TRANSMOGRIFIER.get(), pos, state);
        this.recipeType = UP2RecipeTypes.TRANSMOGRIFICATION.get();
        this.quickCheck = RecipeManager.createCheck(recipeType);
    }

    private boolean isLit() {
        return this.activeTime > 0;
    }

    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.items);
        this.activeTime = compoundTag.getInt("ActiveTime");
        this.transmogrificationTime = compoundTag.getInt("TransmogrificationTime");
        this.transmogrificationTotalTime = compoundTag.getInt("TransmogrificationTimeTotal");
        this.activeDuration = this.getGoopDuration(this.items.get(1));
        CompoundTag compoundtag = compoundTag.getCompound("RecipesUsed");
        for (String recipes : compoundtag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(recipes), compoundtag.getInt(recipes));
        }
    }

    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putInt("ActiveTime", this.activeTime);
        compoundTag.putInt("TransmogrificationTime", this.transmogrificationTime);
        compoundTag.putInt("TransmogrificationTimeTotal", this.transmogrificationTotalTime);
        ContainerHelper.saveAllItems(compoundTag, this.items);
        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((recipe, i) -> compoundtag.putInt(recipe.toString(), i));
        compoundTag.put("RecipesUsed", compoundtag);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.unusual_prehistory.transmogrifier");
    }

    @Override
    protected AbstractContainerMenu createMenu(int slots, Inventory inventory) {
        return new TransmogrifierMenu2(slots, inventory, this, this.dataAccess);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TransmogrifierBlockEntity2 blockEntity) {
        boolean flag = blockEntity.isLit();
        boolean flag1 = false;
        if (blockEntity.isLit()) {
            --blockEntity.activeTime;
        }

        ItemStack itemstack = blockEntity.items.get(1);
        boolean flag2 = !blockEntity.items.get(0).isEmpty();
        boolean flag3 = !itemstack.isEmpty();
        if (blockEntity.isLit() || flag3 && flag2) {
            Recipe<?> recipe;
            if (flag2) {
                recipe = blockEntity.quickCheck.getRecipeFor(blockEntity, level).orElse(null);
            } else {
                recipe = null;
            }

            int i = blockEntity.getMaxStackSize();
            if (!blockEntity.isLit() && blockEntity.canConsumeGoop(level.registryAccess(), recipe, blockEntity.items, i)) {
                blockEntity.activeTime = blockEntity.getGoopDuration(itemstack);
                blockEntity.activeDuration = blockEntity.activeTime;
                if (blockEntity.isLit()) {
                    flag1 = true;
                    if (itemstack.hasCraftingRemainingItem())
                        blockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                    else
                    if (flag3) {
                        Item item = itemstack.getItem();
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            blockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if (blockEntity.isLit() && blockEntity.canConsumeGoop(level.registryAccess(), recipe, blockEntity.items, i)) {
                ++blockEntity.transmogrificationTime;
                if (blockEntity.transmogrificationTime == blockEntity.transmogrificationTotalTime) {
                    blockEntity.transmogrificationTime = 0;
                    blockEntity.transmogrificationTotalTime = getTotalTransmogrificationTime(level, blockEntity);
                    if (blockEntity.consumeGoop(level.registryAccess(), recipe, blockEntity.items, i)) {
                        blockEntity.setRecipeUsed(recipe);
                    }

                    flag1 = true;
                }
            } else {
                blockEntity.transmogrificationTime = 0;
            }
        } else if (!blockEntity.isLit() && blockEntity.transmogrificationTime > 0) {
            blockEntity.transmogrificationTime = Mth.clamp(blockEntity.transmogrificationTime - 2, 0, blockEntity.transmogrificationTotalTime);
        }

        if (flag != blockEntity.isLit()) {
            flag1 = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(blockEntity.isLit()));
            level.setBlock(pos, state, 3);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }

    }

    private boolean canConsumeGoop(RegistryAccess registryAccess, @Nullable Recipe<?> recipe, NonNullList<ItemStack> stacks, int i) {
        if (!stacks.get(0).isEmpty() && recipe != null) {
            ItemStack itemstack = ((Recipe<WorldlyContainer>) recipe).assemble(this, registryAccess);
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = stacks.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItem(itemstack1, itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= i && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    private boolean consumeGoop(RegistryAccess registryAccess, @Nullable Recipe<?> recipe, NonNullList<ItemStack> items, int i) {
        if (recipe != null && this.canConsumeGoop(registryAccess, recipe, items, i)) {
            ItemStack itemstack = items.get(0);
            ItemStack itemstack1 = ((Recipe<WorldlyContainer>) recipe).assemble(this, registryAccess);
            ItemStack itemstack2 = items.get(2);
            if (itemstack2.isEmpty()) {
                items.set(2, itemstack1.copy());
            } else if (itemstack2.is(itemstack1.getItem())) {
                itemstack2.grow(itemstack1.getCount());
            }
            if (itemstack.is(Blocks.WET_SPONGE.asItem()) && !items.get(1).isEmpty() && items.get(1).is(Items.BUCKET)) {
                items.set(1, new ItemStack(Items.WATER_BUCKET));
            }
            itemstack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    protected int getGoopDuration(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            return 700;
        }
    }

    private static int getTotalTransmogrificationTime(Level level, TransmogrifierBlockEntity2 blockEntity) {
        return blockEntity.quickCheck.getRecipeFor(blockEntity, level).map(TransmogrificationRecipe::getProcessingTime).orElse(1000);
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(i, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack stack, Direction direction) {
        if (direction == Direction.DOWN && i == 1) {
            return stack.is(Items.WATER_BUCKET) || stack.is(Items.BUCKET);
        } else {
            return true;
        }
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(this.items, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        ItemStack itemstack = this.items.get(slot);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, stack);
        this.items.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        if (slot == 0 && !flag) {
            this.transmogrificationTotalTime = getTotalTransmogrificationTime(this.level, this);
            this.transmogrificationTime = 0;
            this.setChanged();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        } else if (slot != 1) {
            return true;
        } else {
            ItemStack itemstack = this.items.get(1);
            return ForgeHooks.getBurnTime(stack, this.recipeType) > 0 || stack.is(Items.BUCKET) && !itemstack.is(Items.BUCKET);
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }
    }

    @Nullable
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player player, List<ItemStack> itemStacks) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer serverPlayer) {
        List<Recipe<?>> list = this.getRecipesToAwardAndPopExperience(serverPlayer.serverLevel(), serverPlayer.position());
        serverPlayer.awardRecipes(list);
        for (Recipe<?> recipe : list) {
            if (recipe != null) {
                serverPlayer.triggerRecipeCrafted(recipe, this.items);
            }
        }
        this.recipesUsed.clear();
    }

    public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel serverLevel, Vec3 vec3) {
        List<Recipe<?>> list = Lists.newArrayList();
        for (Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            serverLevel.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                createExperience(serverLevel, vec3, entry.getIntValue(), ((AbstractCookingRecipe) recipe).getExperience());
            });
        }
        return list;
    }

    private static void createExperience(ServerLevel serverLevel, Vec3 vec3, int j, float k) {
        int i = Mth.floor((float) j * k);
        float f = Mth.frac((float) j * k);
        if (f != 0.0F && Math.random() < (double) f) {
            ++i;
        }
        ExperienceOrb.award(serverLevel, vec3, i);
    }

    @Override
    public void fillStackedContents(StackedContents contents) {
        for (ItemStack itemstack : this.items) {
            contents.accountStack(itemstack);
        }
    }

    LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER) {
            if (facing == Direction.UP) {
                return handlers[0].cast();
            } else if (facing == Direction.DOWN) {
                return handlers[1].cast();
            } else {
                return handlers[2].cast();
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (LazyOptional<? extends IItemHandler> handler : handlers) handler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    }
}
