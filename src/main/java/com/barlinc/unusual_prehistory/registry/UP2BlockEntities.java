package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.blocks.entity.ExtraDataBlockEntity;
import com.barlinc.unusual_prehistory.blocks.entity.TransmogrifierBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class UP2BlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TransmogrifierBlockEntity>> TRANSMOGRIFIER = BLOCK_ENTITIES.register("transmogrifier", () -> BlockEntityType.Builder.of(TransmogrifierBlockEntity::new, UP2Blocks.TRANSMOGRIFIER.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ExtraDataBlockEntity>> EXTRA_DATA_BLOCK_ENTITY = BLOCK_ENTITIES.register("extra_data_block_entity", () -> BlockEntityType.Builder.of(ExtraDataBlockEntity::new, UP2Blocks.EGG_BLOCKS.stream().map(Supplier::get).toArray(Block[]::new)).build(null));

    public static void addBlockEntities(final BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN,
                UP2Blocks.DRYOPHYLLUM.sign().get(), UP2Blocks.DRYOPHYLLUM.wallSign().get(),
                UP2Blocks.GINKGO.sign().get(), UP2Blocks.GINKGO.wallSign().get(),
                UP2Blocks.LEPIDODENDRON.sign().get(), UP2Blocks.LEPIDODENDRON.wallSign().get(),
                UP2Blocks.METASEQUOIA.sign().get(), UP2Blocks.METASEQUOIA.wallSign().get()
        );

        event.modify(BlockEntityType.HANGING_SIGN,
                UP2Blocks.DRYOPHYLLUM.hangingSign().get(), UP2Blocks.DRYOPHYLLUM.hangingWallSign().get(),
                UP2Blocks.GINKGO.hangingSign().get(), UP2Blocks.GINKGO.hangingWallSign().get(),
                UP2Blocks.LEPIDODENDRON.hangingSign().get(), UP2Blocks.LEPIDODENDRON.hangingWallSign().get(),
                UP2Blocks.METASEQUOIA.hangingSign().get(), UP2Blocks.METASEQUOIA.hangingWallSign().get()
        );
    }
}
