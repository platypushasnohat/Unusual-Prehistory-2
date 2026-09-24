package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.block.entity.EggBlockEntity;
import com.barl_inc.unusual_prehistory.block.entity.FossilBedBlockEntity;
import com.barl_inc.unusual_prehistory.block.entity.MatrixBlockEntity;
import com.barl_inc.unusual_prehistory.block.entity.TransmogrifierBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("DataFlowIssue")
public class UP2BlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TransmogrifierBlockEntity>> TRANSMOGRIFIER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("transmogrifier_block_entity", () -> BlockEntityType.Builder.of(TransmogrifierBlockEntity::new, UP2Blocks.TRANSMOGRIFIER.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EggBlockEntity>> EGG_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("egg_block_entity", () -> BlockEntityType.Builder.of(EggBlockEntity::new, UP2Blocks.EGG_BLOCKS.stream().map(Supplier::get).toArray(Block[]::new)).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MatrixBlockEntity>> MATRIX_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("matrix_block_entity", () -> BlockEntityType.Builder.of(MatrixBlockEntity::new, UP2Blocks.GRAVEL_MATRIX.get(), UP2Blocks.SAND_MATRIX.get(), UP2Blocks.RED_SAND_MATRIX.get(), UP2Blocks.DIRT_MATRIX.get(), UP2Blocks.MUD_MATRIX.get(), UP2Blocks.SNOW_MATRIX.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FossilBedBlockEntity>> FOSSIL_BED_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("fossil_bed_block_entity", () -> BlockEntityType.Builder.of(FossilBedBlockEntity::new, UP2Blocks.COMMON_FOSSIL_BED.get(), UP2Blocks.UNCOMMON_FOSSIL_BED.get(), UP2Blocks.RARE_FOSSIL_BED.get(), UP2Blocks.UNUSUAL_FOSSIL_BED.get()).build(null));
}
