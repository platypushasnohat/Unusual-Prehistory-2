package com.barlinc.unusual_prehistory.blocks.blockentity;

import com.barlinc.unusual_prehistory.registry.UP2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (owner != null)
            tag.putUUID("Owner", owner);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.hasUUID("Owner"))
            owner = tag.getUUID("Owner");
    }

}
