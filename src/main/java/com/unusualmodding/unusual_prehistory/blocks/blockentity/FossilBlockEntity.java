package com.unusualmodding.unusual_prehistory.blocks.blockentity;

import com.mojang.logging.LogUtils;
import com.unusualmodding.unusual_prehistory.registry.UP2BlockEntities;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class FossilBlockEntity extends BlockEntity {

    private static final Logger LOGGER = LogUtils.getLogger();
    private int chiselCount;
    private int maxChiselCount;
    private long coolDownEndsAtTick;
    private ItemStack item = ItemStack.EMPTY;
    @Nullable
    private ResourceLocation lootTable;
    private long lootTableSeed;

    public FossilBlockEntity(BlockPos pos, BlockState state) {
        super(UP2BlockEntities.FOSSIL.get(), pos, state);
    }

    public boolean chisel(long l) {
        if (this.level instanceof ServerLevel serverLevel) {
            this.maxChiselCount = 9 + serverLevel.random.nextInt(2);
            if (l >= this.coolDownEndsAtTick) {
                this.coolDownEndsAtTick = l + 10L;
                int i = this.getCompletionState();
                if (this.chiselCount++ >= this.maxChiselCount) {
                    this.chiselingCompleted();
                    return true;
                } else {
                    int j = this.getCompletionState();
                    if (i != j) {
                        BlockState blockstate = this.getBlockState();
                        BlockState blockstate1 = blockstate.setValue(BlockStateProperties.DUSTED, j);
                        this.level.setBlock(this.getBlockPos(), blockstate1, 3);
                    }
                    return false;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public void unpackLootTable(Player player) {
        if (this.lootTable != null && this.level != null && !this.level.isClientSide() && this.level.getServer() != null) {
            LootTable loottable = this.level.getServer().getLootData().getLootTable(this.lootTable);
            if (player instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer) player;
                CriteriaTriggers.GENERATE_LOOT.trigger(serverplayer, this.lootTable);
            }

            LootParams lootparams = (new LootParams.Builder((ServerLevel)this.level)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition)).withLuck(player.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player).create(LootContextParamSets.CHEST);
            ObjectArrayList<ItemStack> objectarraylist = loottable.getRandomItems(lootparams, this.lootTableSeed);

            this.item = switch (objectarraylist.size()) {
                case 0 -> ItemStack.EMPTY;
                case 1 -> objectarraylist.get(0);
                default -> {
                    LOGGER.warn("Expected max 1 loot from loot table {} got {}", this.lootTable, objectarraylist.size());
                    yield objectarraylist.get(0);
                }
            };
            this.lootTable = null;
            this.setChanged();
        }
    }

    private void chiselingCompleted() {
        if (this.level != null && this.level.getServer() != null) {
            BlockState blockstate = this.getBlockState();
            this.level.levelEvent(3008, this.getBlockPos(), Block.getId(blockstate));
            this.level.setBlock(this.worldPosition, Blocks.AIR.defaultBlockState(), 3);
        }
    }

    public void dropContent(Player player) {
        if (this.level != null && this.level.getServer() != null) {
            this.unpackLootTable(player);
            if (!this.item.isEmpty()) {
                Block.popResource(this.level, this.worldPosition, this.item);
                this.item = ItemStack.EMPTY;
            }
        }
    }

    private boolean tryLoadLootTable(CompoundTag compoundTag) {
        if (compoundTag.contains("LootTable", 8)) {
            this.lootTable = new ResourceLocation(compoundTag.getString("LootTable"));
            this.lootTableSeed = compoundTag.getLong("LootTableSeed");
            return true;
        } else {
            return false;
        }
    }

    private boolean trySaveLootTable(CompoundTag compoundTag) {
        if (this.lootTable == null) {
            return false;
        } else {
            compoundTag.putString("LootTable", this.lootTable.toString());
            if (this.lootTableSeed != 0L) {
                compoundTag.putLong("LootTableSeed", this.lootTableSeed);
            }
            return true;
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = super.getUpdateTag();
        compoundtag.put("item", this.item.save(new CompoundTag()));
        compoundtag.putInt("chiselCount", this.chiselCount);
        compoundtag.putInt("maxChiselCount", this.maxChiselCount);
        return compoundtag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        if (!this.tryLoadLootTable(compoundTag) && compoundTag.contains("item")) {
            this.item = ItemStack.of(compoundTag.getCompound("item"));
        }
        this.chiselCount = compoundTag.getInt("chiselCount");
        this.maxChiselCount = compoundTag.getInt("maxChiselCount");
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        if (!this.trySaveLootTable(compoundTag)) {
            compoundTag.put("item", this.item.save(new CompoundTag()));
        }
        compoundTag.putInt("chiselCount", this.chiselCount);
        compoundTag.putInt("maxChiselCount", this.maxChiselCount);
    }

    public void setLootTable(ResourceLocation resourceLocation, long lootTableSeed) {
        this.lootTable = resourceLocation;
        this.lootTableSeed = lootTableSeed;
    }

    private int getCompletionState() {
        if (this.chiselCount == 0) {
            return 0;
        } else if (this.chiselCount < 4) {
            return 1;
        } else {
            return this.chiselCount < 8 ? 2 : 3;
        }
    }

    public ItemStack getItem() {
        return this.item;
    }
}