package com.unusualmodding.unusual_prehistory.blocks.blockentity;

import com.unusualmodding.unusual_prehistory.registry.UP2BlockEntities;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.LecternMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class UP2LecternBlockEntity extends BlockEntity implements Clearable, MenuProvider {

    private ItemStack book = ItemStack.EMPTY;

    private final Container bookAccess = new Container() {

        @Override
        public int getContainerSize() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return UP2LecternBlockEntity.this.book.isEmpty();
        }

        @Override
        public ItemStack getItem(int i) {
            return i == 0 ? UP2LecternBlockEntity.this.book : ItemStack.EMPTY;
        }

        @Override
        public ItemStack removeItem(int i, int j) {
            if (i == 0) {
                ItemStack itemstack = UP2LecternBlockEntity.this.book.split(j);
                if (UP2LecternBlockEntity.this.book.isEmpty()) {
                    UP2LecternBlockEntity.this.onBookItemRemove();
                }
                return itemstack;
            } else {
                return ItemStack.EMPTY;
            }
        }

        @Override
        public ItemStack removeItemNoUpdate(int i) {
            if (i == 0) {
                ItemStack itemstack = UP2LecternBlockEntity.this.book;
                UP2LecternBlockEntity.this.book = ItemStack.EMPTY;
                UP2LecternBlockEntity.this.onBookItemRemove();
                return itemstack;
            } else {
                return ItemStack.EMPTY;
            }
        }

        @Override
        public void setItem(int i, ItemStack stack) {
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public void setChanged() {
            UP2LecternBlockEntity.this.setChanged();
        }

        @Override
        public boolean stillValid(Player p_59588_) {
            if (UP2LecternBlockEntity.this.level.getBlockEntity(UP2LecternBlockEntity.this.worldPosition) != UP2LecternBlockEntity.this) {
                return false;
            } else {
                return !(p_59588_.distanceToSqr((double) UP2LecternBlockEntity.this.worldPosition.getX() + 0.5D, (double) UP2LecternBlockEntity.this.worldPosition.getY() + 0.5D, (double) UP2LecternBlockEntity.this.worldPosition.getZ() + 0.5D) > 64.0D) && UP2LecternBlockEntity.this.hasBook();
            }
        }

        @Override
        public boolean canPlaceItem(int i, ItemStack stack) {
            return false;
        }

        @Override
        public void clearContent() {
        }
    };

    private final ContainerData dataAccess = new ContainerData() {

        @Override
        public int get(int i) {
            return 0;
        }

        @Override
        public void set(int i, int j) {
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public UP2LecternBlockEntity(BlockPos pos, BlockState state) {
        super(UP2BlockEntities.LECTERN_BLOCK_ENTITY.get(), pos, state);
    }

    public ItemStack getBook() {
        return this.book;
    }

    public boolean hasBook() {
        return this.book.is(UP2Items.PALEOPEDIA.get());
    }

    public void setBook(ItemStack stack) {
        this.setBook(stack, null);
    }

    void onBookItemRemove() {
        LecternBlock.resetBookState(null, this.getLevel(), this.getBlockPos(), this.getBlockState(), false);
    }

    public void setBook(ItemStack itemStack, @Nullable Player player) {
        this.book = itemStack;
        this.setChanged();
    }

    public int getRedstoneSignal() {
        return this.hasBook() ? 1 : 0;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Book", 10)) {
            this.book = ItemStack.of(tag.getCompound("Book"));
        } else {
            this.book = ItemStack.EMPTY;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.getBook().isEmpty()) {
            tag.put("Book", this.getBook().save(new CompoundTag()));
        }
    }

    @Override
    public void clearContent() {
        this.setBook(ItemStack.EMPTY);
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new LecternMenu(i, this.bookAccess, this.dataAccess);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.lectern");
    }
}
