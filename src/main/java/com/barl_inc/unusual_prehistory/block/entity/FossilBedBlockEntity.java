package com.barl_inc.unusual_prehistory.block.entity;

import com.barl_inc.unusual_prehistory.registry.UP2BlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import javax.annotation.Nullable;
import java.util.List;

public class FossilBedBlockEntity extends BlockEntity {

    @Nullable
    private ResourceKey<LootTable> lootTable;
    private long lootTableSeed;

    public FossilBedBlockEntity(BlockPos pos, BlockState state) {
        super(UP2BlockEntityTypes.FOSSIL_BED_BLOCK_ENTITY.get(), pos, state);
    }

    public void setLootTable(ResourceKey<LootTable> lootTable, long seed) {
        this.lootTable = lootTable;
        this.lootTableSeed = seed;
        this.setChanged();
    }

    @Nullable
    public ResourceKey<LootTable> getLootTable() {
        return lootTable;
    }

    public long getLootTableSeed() {
        return lootTableSeed;
    }

    public void unpackLootTable(ServerLevel level) {
        if (this.lootTable != null) {
            LootTable table = level.getServer().reloadableRegistries().getLootTable(this.lootTable);
            LootParams params = new LootParams.Builder(level).withParameter(LootContextParams.ORIGIN, this.worldPosition.getCenter()).create(LootContextParamSets.CHEST);
            List<ItemStack> items = table.getRandomItems(params, this.lootTableSeed);
            for (ItemStack stack : items) {
                Block.popResource(level, this.worldPosition, stack);
            }
            this.lootTable = null;
            this.setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, Provider provider) {
        super.saveAdditional(compoundTag, provider);
        if (this.lootTable != null) {
            compoundTag.putString("LootTable", this.lootTable.location().toString());
            if (this.lootTableSeed != 0L) {
                compoundTag.putLong("LootTableSeed", this.lootTableSeed);
            }
        }
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, Provider provider) {
        super.loadAdditional(compoundTag, provider);
        if (compoundTag.contains("LootTable")) {
            this.lootTable = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(compoundTag.getString("LootTable")));
            this.lootTableSeed = compoundTag.getLong("LootTableSeed");
        }
    }
}