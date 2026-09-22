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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
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
        CompoundTag usedTag = compoundTag.getCompound("RecipesUsed");
        for (String key : usedTag.getAllKeys()) {
            this.recipesUsed.put(ResourceLocation.parse(key), usedTag.getInt(key));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.putInt("ActiveTime", this.activeTime);
        compoundTag.putInt("ProcessingTime", this.processingProgress);
        compoundTag.putInt("ProcessingTimeTotal", this.processingTotalTime);
        ContainerHelper.saveAllItems(compoundTag, this.items, provider);
        CompoundTag usedTag = new CompoundTag();
        this.recipesUsed.forEach((location, count) -> usedTag.putInt(location.toString(), count));
        compoundTag.put("RecipesUsed", usedTag);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TransmogrifierBlockEntity blockEntity) {

        if (state.getValue(TransmogrifierBlock.LIT) && level.isClientSide) {
            if (!blockEntity.isRemoved()) {
                UnusualPrehistory2.PROXY.playWorldSound(blockEntity, (byte) 0);
            }
        }

        if (!level.isClientSide) {

            boolean isActive = blockEntity.isActive();
            boolean flag = false;

            if (blockEntity.isActive()) {
                blockEntity.activeTime--;
            }

            ItemStack fuel = blockEntity.items.get(1);
            ItemStack input = blockEntity.items.get(0);
            boolean hasInput = !input.isEmpty();
            boolean hasFuel = !fuel.isEmpty();

            if (blockEntity.isActive() || hasFuel && hasInput) {
                RecipeHolder<? extends TransmogrificationRecipe> recipeHolder;
                if (hasInput) {
                    recipeHolder = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(input), level).orElse(null);
                } else {
                    recipeHolder = null;
                }

                int maxStackSize = blockEntity.getMaxStackSize();
                if (!blockEntity.isActive() && canBurn(level.registryAccess(), recipeHolder, blockEntity.items, maxStackSize, blockEntity)) {
                    blockEntity.activeTime = blockEntity.getFuelDuration(fuel);
                    blockEntity.activeDuration = blockEntity.activeTime;
                    if (blockEntity.isActive()) {
                        flag = true;
                        if (fuel.hasCraftingRemainingItem())
                            blockEntity.items.set(1, fuel.getCraftingRemainingItem());
                        else if (hasFuel) {
                            fuel.shrink(1);
                            if (fuel.isEmpty()) {
                                blockEntity.items.set(1, fuel.getCraftingRemainingItem());
                            }
                        }
                    }
                }

                if (blockEntity.isActive() && canBurn(level.registryAccess(), recipeHolder, blockEntity.items, maxStackSize, blockEntity)) {
                    blockEntity.processingProgress++;
                    if (blockEntity.processingProgress == blockEntity.processingTotalTime) {
                        blockEntity.processingProgress = 0;
                        blockEntity.processingTotalTime = getTotalProcessingTime(level, blockEntity);
                        if (burnFuel(level.registryAccess(), recipeHolder, blockEntity.items, maxStackSize, blockEntity)) {
                            blockEntity.setRecipeUsed(recipeHolder);
                        }
                        flag = true;
                    }
                } else {
                    blockEntity.processingProgress = 0;
                }
            } else if (!blockEntity.isActive() && blockEntity.processingProgress > 0) {
                blockEntity.processingProgress = Mth.clamp(blockEntity.processingProgress - 2, 0, blockEntity.processingTotalTime);
            }

            if (isActive != blockEntity.isActive()) {
                flag = true;
                state = state.setValue(TransmogrifierBlock.LIT, blockEntity.isActive());
                level.setBlock(pos, state, 3);
            }

            if (flag) {
                setChanged(level, pos, state);
            }
        }
    }

    private static boolean canBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<? extends TransmogrificationRecipe> recipe, NonNullList<ItemStack> inventory, int maxStackSize, TransmogrifierBlockEntity transmogrifier) {
        if (!inventory.get(0).isEmpty() && recipe != null) {
            ItemStack input = recipe.value().assemble(new SingleRecipeInput(transmogrifier.getItem(0)), registryAccess);
            if (input.isEmpty()) {
                return false;
            } else {
                ItemStack output = inventory.get(2);
                if (output.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItemSameComponents(output, input)) {
                    return false;
                } else {
                    return output.getCount() + input.getCount() <= maxStackSize && output.getCount() + input.getCount() <= output.getMaxStackSize() || output.getCount() + input.getCount() <= input.getMaxStackSize(); // Neo fix: make transmogrifier respect stack sizes in transmogrifier recipes
                }
            }
        } else {
            return false;
        }
    }

    private static boolean burnFuel(RegistryAccess registryAccess, @Nullable RecipeHolder<? extends TransmogrificationRecipe> recipe, NonNullList<ItemStack> inventory, int maxStackSize, TransmogrifierBlockEntity transmogrifier) {
        if (recipe != null && canBurn(registryAccess, recipe, inventory, maxStackSize, transmogrifier)) {
            ItemStack input = inventory.get(0);
            ItemStack assembled = recipe.value().assemble(new SingleRecipeInput(transmogrifier.getItem(0)), registryAccess);
            ItemStack output = inventory.get(2);
            if (output.isEmpty()) {
                inventory.set(2, assembled.copy());
            } else if (ItemStack.isSameItemSameComponents(output, assembled)) {
                output.grow(assembled.getCount());
            }
            input.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    protected int getFuelDuration(ItemStack fuel) {
        return fuel.is(UP2ItemTags.TRANSMOGRIFIER_FUEL) ? 1200 : 0;
    }

    private static int getTotalProcessingTime(Level level, TransmogrifierBlockEntity blockEntity) {
        SingleRecipeInput input = new SingleRecipeInput(blockEntity.getItem(0));
        return blockEntity.quickCheck.getRecipeFor(input, level).map(holder -> holder.value().processingTime()).orElse(1200);
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
            ItemStack fuel = this.items.get(1);
            return stack.is(UP2ItemTags.TRANSMOGRIFIER_FUEL) || stack.is(Items.BUCKET) && !fuel.is(Items.BUCKET);
        }
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipe) {
        if (recipe != null) {
            this.recipesUsed.addTo(recipe.id(), 1);
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
            expTotal++;
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