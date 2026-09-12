package com.barl_inc.unusual_prehistory.block.entity;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.block.TransmogrifierBlock;
import com.barl_inc.unusual_prehistory.inventory.TransmogrifierMenu;
import com.barl_inc.unusual_prehistory.recipe.TransmogrificationRecipe;
import com.barl_inc.unusual_prehistory.registry.UP2BlockEntityTypes;
import com.barl_inc.unusual_prehistory.registry.UP2RecipeTypes;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class TransmogrifierBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {
    
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};

    private final RecipeType<? extends TransmogrificationRecipe> recipeType;
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    
    private int activeTime;
    private int activeDuration;
    private int processingProgress;
    private int processingTotalTime;

    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int i) {
            return switch (i) {
                case 0 -> {
                    if (TransmogrifierBlockEntity.this.activeDuration > Short.MAX_VALUE) {
                        // Neo: preserve activeTime / activeDuration ratio on the client as data slots are synced as shorts.
                        yield Mth.floor(((double) TransmogrifierBlockEntity.this.activeTime / TransmogrifierBlockEntity.this.activeDuration) * Short.MAX_VALUE);
                    }
                    yield TransmogrifierBlockEntity.this.activeTime;
                }
                case 1 -> Math.min(TransmogrifierBlockEntity.this.activeDuration, Short.MAX_VALUE);
                case 2 -> TransmogrifierBlockEntity.this.processingProgress;
                case 3 -> TransmogrifierBlockEntity.this.processingTotalTime;
                default -> 0;
            };
        }

        @Override
        public void set(int i, int j) {
            switch (i) {
                case 0:
                    TransmogrifierBlockEntity.this.activeTime = j;
                    break;
                case 1:
                    TransmogrifierBlockEntity.this.activeDuration = j;
                    break;
                case 2:
                    TransmogrifierBlockEntity.this.processingProgress = j;
                    break;
                case 3:
                    TransmogrifierBlockEntity.this.processingTotalTime = j;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<SingleRecipeInput, ? extends TransmogrificationRecipe> quickCheck;

    public TransmogrifierBlockEntity(BlockPos pos, BlockState blockState) {
        super(UP2BlockEntityTypes.TRANSMOGRIFIER_BLOCK_ENTITY.get(), pos, blockState);
        this.quickCheck = RecipeManager.createCheck(UP2RecipeTypes.TRANSMOGRIFICATION.get());
        this.recipeType = UP2RecipeTypes.TRANSMOGRIFICATION.get();
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new TransmogrifierMenu(id, player, this, this.dataAccess);
    }
    
    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.unusual_prehistory.transmogrifier");
    }

    public boolean isActive() {
        return this.activeTime > 0;
    }

    public int getProcessingProgress() {
        return this.processingProgress;
    }

    public int getProcessingTotalTime() {
        return this.processingTotalTime;
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.items, provider);
        this.activeTime = compoundTag.getInt("ActiveTime");
        this.processingProgress = compoundTag.getInt("ProcessingTime");
        this.processingTotalTime = compoundTag.getInt("ProcessingTimeTotal");
        this.activeDuration = this.getFuelDuration(this.items.get(1));
        CompoundTag compoundtag = compoundTag.getCompound("RecipesUsed");
        for (String key : compoundtag.getAllKeys()) {
            this.recipesUsed.put(ResourceLocation.parse(key), compoundtag.getInt(key));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.putInt("ActiveTime", this.activeTime);
        compoundTag.putInt("ProcessingTime", this.processingProgress);
        compoundTag.putInt("ProcessingTimeTotal", this.processingTotalTime);
        ContainerHelper.saveAllItems(compoundTag, this.items, provider);
        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((location, i) -> compoundtag.putInt(location.toString(), i));
        compoundTag.put("RecipesUsed", compoundtag);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TransmogrifierBlockEntity blockEntity) {

        if (state.getValue(TransmogrifierBlock.LIT) && level.isClientSide) {
            if (!blockEntity.isRemoved()) {
                UnusualPrehistory2.PROXY.playWorldSound(blockEntity, (byte) 0);
            }
        }

        if (!level.isClientSide) {

            boolean flag = blockEntity.isActive();
            boolean flag1 = false;

            if (blockEntity.isActive()) {
                blockEntity.activeTime--;
            }

            ItemStack itemstack = blockEntity.items.get(1);
            ItemStack itemstack1 = blockEntity.items.get(0);
            boolean flag2 = !itemstack1.isEmpty();
            boolean flag3 = !itemstack.isEmpty();

            if (blockEntity.isActive() || flag3 && flag2) {
                RecipeHolder<? extends TransmogrificationRecipe> recipeholder;
                if (flag2) {
                    recipeholder = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(itemstack1), level).orElse(null);
                } else {
                    recipeholder = null;
                }

                int i = blockEntity.getMaxStackSize();
                if (!blockEntity.isActive() && canBurn(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity)) {
                    blockEntity.activeTime = blockEntity.getFuelDuration(itemstack);
                    blockEntity.activeDuration = blockEntity.activeTime;
                    if (blockEntity.isActive()) {
                        flag1 = true;
                        if (itemstack.hasCraftingRemainingItem())
                            blockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                        else if (flag3) {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                blockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                            }
                        }
                    }
                }

                if (blockEntity.isActive() && canBurn(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity)) {
                    blockEntity.processingProgress++;
                    if (blockEntity.processingProgress == blockEntity.processingTotalTime) {
                        blockEntity.processingProgress = 0;
                        blockEntity.processingTotalTime = getTotalProcessingTime(level, blockEntity);
                        if (burnFuel(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity)) {
                            blockEntity.setRecipeUsed(recipeholder);
                        }

                        flag1 = true;
                    }
                } else {
                    blockEntity.processingProgress = 0;
                }
            } else if (!blockEntity.isActive() && blockEntity.processingProgress > 0) {
                blockEntity.processingProgress = Mth.clamp(blockEntity.processingProgress - 2, 0, blockEntity.processingTotalTime);
            }

            if (flag != blockEntity.isActive()) {
                flag1 = true;
                state = state.setValue(TransmogrifierBlock.LIT, blockEntity.isActive());
                level.setBlock(pos, state, 3);
            }

            if (flag1) {
                setChanged(level, pos, state);
            }
        }
    }

    private static boolean canBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<? extends TransmogrificationRecipe> recipe, NonNullList<ItemStack> inventory, int maxStackSize, TransmogrifierBlockEntity transmogrifier) {
        if (!inventory.get(0).isEmpty() && recipe != null) {
            ItemStack itemstack = recipe.value().assemble(new SingleRecipeInput(transmogrifier.getItem(0)), registryAccess);
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = inventory.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItemSameComponents(itemstack1, itemstack)) {
                    return false;
                } else {
                    // Neo fix: make transmogrifier respect stack sizes in transmogrifier recipes
                    return itemstack1.getCount() + itemstack.getCount() <= maxStackSize && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize() || itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Neo fix: make transmogrifier respect stack sizes in transmogrifier recipes
                }
            }
        } else {
            return false;
        }
    }

    private static boolean burnFuel(RegistryAccess registryAccess, @Nullable RecipeHolder<? extends TransmogrificationRecipe> recipe, NonNullList<ItemStack> inventory, int maxStackSize, TransmogrifierBlockEntity transmogrifier) {
        if (recipe != null && canBurn(registryAccess, recipe, inventory, maxStackSize, transmogrifier)) {
            ItemStack itemstack = inventory.get(0);
            ItemStack itemstack1 = recipe.value().assemble(new SingleRecipeInput(transmogrifier.getItem(0)), registryAccess);
            ItemStack itemstack2 = inventory.get(2);
            if (itemstack2.isEmpty()) {
                inventory.set(2, itemstack1.copy());
            } else if (ItemStack.isSameItemSameComponents(itemstack2, itemstack1)) {
                itemstack2.grow(itemstack1.getCount());
            }
            itemstack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    protected int getFuelDuration(ItemStack fuel) {
        return fuel.is(UP2ItemTags.TRANSMOGRIFIER_FUEL) ? 960 : 0;
    }

    public static boolean isFuel(ItemStack stack) {
        return stack.is(UP2ItemTags.TRANSMOGRIFIER_FUEL);
    }

    private static int getTotalProcessingTime(Level level, TransmogrifierBlockEntity blockEntity) {
        SingleRecipeInput singlerecipeinput = new SingleRecipeInput(blockEntity.getItem(0));
        return blockEntity.quickCheck.getRecipeFor(singlerecipeinput, level).map(holder -> holder.value().processingTime()).orElse(1200);
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
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return direction != Direction.DOWN || index != 1 || stack.is(Items.WATER_BUCKET) || stack.is(Items.BUCKET);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, stack);
        this.items.set(index, stack);
        stack.limitSize(this.getMaxStackSize(stack));
        if (index == 0 && !flag) {
            this.processingTotalTime = getTotalProcessingTime(this.level, this);
            this.processingProgress = 0;
            this.setChanged();
        }
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemstack = this.items.get(1);
            return stack.is(UP2ItemTags.TRANSMOGRIFIER_FUEL) || stack.is(Items.BUCKET) && !itemstack.is(Items.BUCKET);
        }
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.id();
            this.recipesUsed.addTo(resourcelocation, 1);
        }
    }

    @Nullable
    @Override
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player player, List<ItemStack> items) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<RecipeHolder<?>> list = this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position());
        player.awardRecipes(list);
        for (RecipeHolder<?> recipeholder : list) {
            if (recipeholder != null) {
                player.triggerRecipeCrafted(recipeholder, this.items);
            }
        }
        this.recipesUsed.clear();
    }

    public List<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 popVec) {
        List<RecipeHolder<?>> list = Lists.newArrayList();
        for (Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent(holder -> {
                list.add(holder);
                createExperience(level, popVec, entry.getIntValue(), ((TransmogrificationRecipe) holder.value()).experience());
            });
        }
        return list;
    }

    private static void createExperience(ServerLevel level, Vec3 popVec, int craftedAmount, float experience) {
        int expTotal = Mth.floor((float) craftedAmount * experience);
        float expFraction = Mth.frac((float) craftedAmount * experience);
        if (expFraction != 0.0F && Math.random() < (double) expFraction) {
            ++expTotal;
        }
        ExperienceOrb.award(level, popVec, expTotal);
    }

    @Override
    public void fillStackedContents(StackedContents helper) {
        for (ItemStack itemstack : this.items) {
            helper.accountStack(itemstack);
        }
    }

    @Override
    public void setRemoved() {
        UnusualPrehistory2.PROXY.clearSoundCacheFor(this);
        super.setRemoved();
    }
}