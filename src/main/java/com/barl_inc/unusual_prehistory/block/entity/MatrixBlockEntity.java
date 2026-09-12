package com.barl_inc.unusual_prehistory.block.entity;

import com.barl_inc.unusual_prehistory.registry.UP2BlockEntityTypes;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.platypushasnohat.sinew.block.Brushable;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootTable;

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
        super(UP2BlockEntityTypes.MATRIX_BLOCK_ENTITY.get(), pos, state);
    }

    public boolean brush(long startTick) {
        this.brushCountResetsAtTick = startTick + 40L;
        if (startTick >= this.coolDownEndsAtTick && this.level instanceof ServerLevel serverLevel) {
            this.coolDownEndsAtTick = startTick + 10L;
            int completionState = this.getCompletionState();
            if (++this.brushCount >= 10) {
                this.brushingCompleted();
                return true;
            } else {
                serverLevel.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 2);
                int j = this.getCompletionState();
                if (completionState != j) {
                    BlockState state = this.getBlockState().setValue(BlockStateProperties.DUSTED, j);
                    serverLevel.setBlock(this.getBlockPos(), state, 3);
                }
                return false;
            }
        } else {
            return false;
        }
    }

    private void brushingCompleted() {
        if (this.level instanceof ServerLevel serverLevel) {
            BlockPos pos = this.worldPosition;
            BlockState oldState = this.getBlockState();
            serverLevel.levelEvent(3008, pos, Block.getId(oldState));
            ResourceKey<LootTable> table = this.lootTable;
            long seed = this.lootTableSeed;
            serverLevel.setBlock(pos, this.rarity.getBlock().defaultBlockState(), 3);
            BlockEntity blockEntity = serverLevel.getBlockEntity(pos);
            if (blockEntity instanceof FossilBedBlockEntity fossilBed && table != null) {
                fossilBed.setLootTable(table, seed);
            }
        }
    }

    public void checkReset() {
        if (this.level != null) {
            if (this.brushCount != 0 && this.level.getGameTime() >= this.brushCountResetsAtTick) {
                this.brushCount = Math.max(0, this.brushCount - 1);
                this.brushCountResetsAtTick = this.level.getGameTime() + 4L;
            }
            if (this.brushCount == 0) {
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
        return this.rarity;
    }

    public void setLootTable(ResourceKey<LootTable> lootTable, long seed) {
        this.lootTable = lootTable;
        this.lootTableSeed = seed;
        this.setChanged();
    }

    @Nullable
    public ResourceKey<LootTable> getLootTable() {
        return this.lootTable;
    }

    public long getLootTableSeed() {
        return this.lootTableSeed;
    }

    private void tryLoadLootTable(CompoundTag compoundTag) {
        if (compoundTag.contains("LootTable", 8)) {
            this.lootTable = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(compoundTag.getString("LootTable")));
            this.lootTableSeed = compoundTag.getLong("LootTableSeed");
        }
        if (compoundTag.contains("Rarity")) {
            this.rarity = LootRarity.byId(compoundTag.getString("Rarity"));
        }
    }

    private void trySaveLootTable(CompoundTag compoundTag) {
        if (this.lootTable != null) {
            compoundTag.putString("LootTable", this.lootTable.location().toString());
        }
        if (this.lootTableSeed != 0L) {
            compoundTag.putLong("LootTableSeed", this.lootTableSeed);
        }
        compoundTag.putString("Rarity", this.rarity.getId());
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, Provider provider) {
        super.saveAdditional(compoundTag, provider);
        this.trySaveLootTable(compoundTag);
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, Provider provider) {
        super.loadAdditional(compoundTag, provider);
        this.tryLoadLootTable(compoundTag);
    }

    @Override
    public CompoundTag getUpdateTag(Provider provider) {
        CompoundTag compoundTag = super.getUpdateTag(provider);
        this.trySaveLootTable(compoundTag);
        return compoundTag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private int getCompletionState() {
        if (this.brushCount == 0) {
            return 0;
        } else if (this.brushCount < 3) {
            return 1;
        } else {
            return this.brushCount < 6 ? 2 : 3;
        }
    }

    public enum LootRarity {
        COMMON(UP2Blocks.COMMON_FOSSIL_BED.get(), "common"),
        UNCOMMON(UP2Blocks.UNCOMMON_FOSSIL_BED.get(), "uncommon"),
        RARE(UP2Blocks.RARE_FOSSIL_BED.get(), "rare"),
        UNUSUAL(UP2Blocks.UNUSUAL_FOSSIL_BED.get(), "unusual");

        private final Block block;
        private final String id;

        LootRarity(Block block, String id) {
            this.block = block;
            this.id = id;
        }

        public Block getBlock() {
            return this.block;
        }

        public String getId() {
            return this.id;
        }

        public static LootRarity byId(String id) {
            for (LootRarity rarity : LootRarity.values()) {
                if (rarity.getId().equals(id)) {
                    return rarity;
                }
            }
            return COMMON;
        }
    }
}