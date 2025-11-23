package com.barlinc.unusual_prehistory.blocks.blockentity;

import com.barlinc.unusual_prehistory.registry.UP2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ExtraDataBlockEntity extends BlockEntity {

    private UUID owner;

    public ExtraDataBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(UP2BlockEntities.EXTRA_DATA_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        setChanged();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (owner != null) compoundTag.putUUID("Owner", owner);
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        if (compoundTag.hasUUID("Owner")) owner = compoundTag.getUUID("Owner");
    }
}
