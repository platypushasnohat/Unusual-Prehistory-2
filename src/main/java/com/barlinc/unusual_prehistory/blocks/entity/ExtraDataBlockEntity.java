package com.barlinc.unusual_prehistory.blocks.entity;

import com.barlinc.unusual_prehistory.registry.UP2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ExtraDataBlockEntity extends BlockEntity {

    private UUID owner;

    public ExtraDataBlockEntity(BlockPos pos, BlockState state) {
        super(UP2BlockEntities.EXTRA_DATA_BLOCK_ENTITY.get(), pos, state);
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        this.setChanged();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(compoundTag, registries);
        if (owner != null) compoundTag.putUUID("Owner", owner);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag compoundTag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(compoundTag, registries);
        if (compoundTag.hasUUID("Owner")) owner = compoundTag.getUUID("Owner");
    }
}
