package com.unusualmodding.unusual_prehistory.blocks.blockentity;

import com.mojang.logging.LogUtils;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.blocks.SuspiciousStoneBlock;
import com.unusualmodding.unusual_prehistory.registry.UP2BlockEntities;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
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
import java.util.Objects;

public class SuspiciousStoneBlockEntity extends BlockEntity {

    private static final Logger LOGGER = LogUtils.getLogger();
    private int chiselCount;
    private long chiselCountResetsAtTick;
    private long coolDownEndsAtTick;
    private ItemStack item = ItemStack.EMPTY;
    @Nullable
    private Direction hitDirection;
    @Nullable
    private ResourceLocation lootTable;
    private long lootTableSeed;

    public SuspiciousStoneBlockEntity(BlockPos pos, BlockState state) {
        super(UP2BlockEntities.SUSPICIOUS_STONE.get(), pos, state);
    }

    public boolean chisel(long l, Player player, Direction direction) {
        if (this.hitDirection == null) {
            this.hitDirection = direction;
        }

        this.chiselCountResetsAtTick = l + 40L;
        if (l >= this.coolDownEndsAtTick && this.level instanceof ServerLevel) {
            this.coolDownEndsAtTick = l + 10L;
            this.unpackLootTable(player);
            int i = this.getCompletionState();
            if (++this.chiselCount >= 10) {
                this.chiselingCompleted(player);
                return true;
            } else {
                this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 40);
                int j = this.getCompletionState();
                if (i != j) {
                    BlockState blockstate = this.getBlockState();
                    BlockState blockstate1 = blockstate.setValue(BlockStateProperties.DUSTED, j);
                    this.level.setBlock(this.getBlockPos(), blockstate1, 3);
                }
                return false;
            }
        } else {
            return false;
        }
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
                    LOGGER.warn("Expected max 1 loot from loot table " + this.lootTable + " got " + objectarraylist.size());
                    yield objectarraylist.get(0);
                }
            };;
            this.lootTable = null;
            this.setChanged();
        }
    }

    private void chiselingCompleted(Player player) {
        if (this.level != null && this.level.getServer() != null) {
            this.dropContent(player);
            BlockState blockstate = this.getBlockState();
            this.level.levelEvent(3008, this.getBlockPos(), Block.getId(blockstate));
            Block block = this.getBlockState().getBlock();
            Block block1;
            if (block instanceof SuspiciousStoneBlock) {
                SuspiciousStoneBlock suspiciousStoneBlock = (SuspiciousStoneBlock) block;
                block1 = suspiciousStoneBlock.getTurnsInto();
            } else {
                block1 = Blocks.AIR;
            }
            this.level.setBlock(this.worldPosition, block1.defaultBlockState(), 3);
        }
    }

    private void dropContent(Player player) {
        if (this.level != null && this.level.getServer() != null) {
            this.unpackLootTable(player);
            if (!this.item.isEmpty()) {
                double d0 = EntityType.ITEM.getWidth();
                double d1 = 1.0D - d0;
                double d2 = d0 / 2.0D;
                Direction direction = Objects.requireNonNullElse(this.hitDirection, Direction.UP);
                BlockPos blockpos = this.worldPosition.relative(direction, 1);
                double d3 = (double) blockpos.getX() + 0.5D * d1 + d2;
                double d4 = (double) blockpos.getY() + 0.5D + (double)(EntityType.ITEM.getHeight() / 2.0F);
                double d5 = (double) blockpos.getZ() + 0.5D * d1 + d2;
                ItemEntity itementity = new ItemEntity(this.level, d3, d4, d5, this.item.split(this.level.random.nextInt(21) + 10));
                itementity.setDeltaMovement(Vec3.ZERO);
                this.level.addFreshEntity(itementity);
                this.item = ItemStack.EMPTY;
            }
        }
    }

    public void checkReset() {
        if (this.level != null) {
            if (this.chiselCount != 0 && this.level.getGameTime() >= this.chiselCountResetsAtTick) {
                int i = this.getCompletionState();
                this.chiselCount = Math.max(0, this.chiselCount - 2);
                int j = this.getCompletionState();
                if (i != j) {
                    this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(BlockStateProperties.DUSTED, j), 3);
                }
                this.chiselCountResetsAtTick = this.level.getGameTime() + 4L;
            }

            if (this.chiselCount == 0) {
                this.hitDirection = null;
                this.chiselCountResetsAtTick = 0L;
                this.coolDownEndsAtTick = 0L;
            } else {
                this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), (int) (this.chiselCountResetsAtTick - this.level.getGameTime()));
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
        if (this.hitDirection != null) {
            compoundtag.putInt("hit_direction", this.hitDirection.ordinal());
        }

        compoundtag.put("item", this.item.save(new CompoundTag()));
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
        if (compoundTag.contains("hit_direction")) {
            this.hitDirection = Direction.values()[compoundTag.getInt("hit_direction")];
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        if (!this.trySaveLootTable(compoundTag)) {
            compoundTag.put("item", this.item.save(new CompoundTag()));
        }
    }

    public void setLootTable(ResourceLocation resourceLocation, long lootTableSeed) {
        this.lootTable = resourceLocation;
        this.lootTableSeed = lootTableSeed;
    }

    private int getCompletionState() {
        if (this.chiselCount == 0) {
            return 0;
        } else if (this.chiselCount < 3) {
            return 1;
        } else {
            return this.chiselCount < 6 ? 2 : 3;
        }
    }

    @Nullable
    public Direction getHitDirection() {
        return this.hitDirection;
    }

    public ItemStack getItem() {
        return this.item;
    }
}