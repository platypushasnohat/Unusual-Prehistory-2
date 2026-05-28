package com.barlinc.unusual_prehistory.blocks.entity;

import com.barlinc.unusual_prehistory.registry.UP2BlockEntities;
import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class MatrixBlockEntity extends BlockEntity implements Brushable {

    private int brushCount;
    private long brushCountResetsAtTick;
    private long coolDownEndsAtTick;

    @Nullable
    private ResourceKey<LootTable> lootTable;
    private long lootTableSeed;
    private LootRarity rarity = LootRarity.COMMON;

    public MatrixBlockEntity(BlockPos pos, BlockState state) {
        super(UP2BlockEntities.MATRIX_BLOCK_ENTITY.get(), pos, state);
    }

    public boolean brush(long startTick) {
        this.brushCountResetsAtTick = startTick + 40L;
        if (startTick >= coolDownEndsAtTick && level instanceof ServerLevel serverLevel) {
            this.coolDownEndsAtTick = startTick + 10L;
            this.brushCount++;
            if (brushCount >= 4) {
                this.brushingCompleted();
                return true;
            }
            serverLevel.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 2);
            return false;
        }
        return false;
    }

    private void brushingCompleted() {
        if (level instanceof ServerLevel serverLevel) {
            BlockPos pos = worldPosition;
            BlockState oldState = this.getBlockState();
            this.level.levelEvent(3008, pos, Block.getId(oldState));
            ResourceKey<LootTable> table = lootTable;
            long seed = lootTableSeed;
            serverLevel.setBlock(pos, rarity.getBlock().defaultBlockState(), 3);
            BlockEntity blockEntity = serverLevel.getBlockEntity(pos);
            if (blockEntity instanceof FossilBedBlockEntity fossilBed && table != null) {
                fossilBed.setLootTable(table, seed);
            }
        }
    }

    public void checkReset() {
        if (level != null) {
            if (brushCount != 0 && level.getGameTime() >= brushCountResetsAtTick) {
                this.brushCount = Math.max(0, brushCount - 1);
                this.brushCountResetsAtTick = level.getGameTime() + 4L;
            }
            if (brushCount == 0) {
                this.brushCountResetsAtTick = 0L;
                this.coolDownEndsAtTick = 0L;
            } else {
                this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 2);
            }
        }
    }

    public void setRarity(LootRarity rarity) {
        this.rarity = rarity;
        this.setChanged();
    }

    public LootRarity getRarity() {
        return rarity;
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

    private void tryLoadLootTable(CompoundTag compoundTag) {
        if (compoundTag.contains("LootTable", 8)) {
            this.lootTable = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(compoundTag.getString("LootTable")));
            this.lootTableSeed = compoundTag.getLong("LootTableSeed");
        }
        if (compoundTag.contains("Rarity")) {
            this.rarity = LootRarity.valueOf(compoundTag.getString("Rarity"));
        }
    }

    private void trySaveLootTable(CompoundTag compoundTag) {
        if (lootTable != null) {
            compoundTag.putString("LootTable", lootTable.location().toString());
        }
        if (lootTableSeed != 0L) {
            compoundTag.putLong("LootTableSeed", lootTableSeed);
        }
        compoundTag.putString("Rarity", rarity.name());
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag, @NotNull Provider provider) {
        super.saveAdditional(compoundTag, provider);
        this.trySaveLootTable(compoundTag);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag compoundTag, @NotNull Provider provider) {
        super.loadAdditional(compoundTag, provider);
        this.tryLoadLootTable(compoundTag);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(@NotNull Provider provider) {
        CompoundTag compoundTag = super.getUpdateTag(provider);
        this.trySaveLootTable(compoundTag);
        return compoundTag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public enum LootRarity {
        COMMON(UP2Blocks.FOSSIL_BED.get()),
        UNCOMMON(UP2Blocks.UNCOMMON_FOSSIL_BED.get()),
        RARE(UP2Blocks.RARE_FOSSIL_BED.get()),
        UNUSUAL(UP2Blocks.UNUSUAL_FOSSIL_BED.get());

        private final Block block;

        LootRarity(Block block) {
            this.block = block;
        }

        public Block getBlock() {
            return block;
        }
    }
}