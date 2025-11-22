package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.blocks.blockentity.ExtraDataBlockEntity;
import com.barlinc.unusual_prehistory.blocks.blockentity.TransmogrifierBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class UP2BlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<BlockEntityType<TransmogrifierBlockEntity>> TRANSMOGRIFIER = BLOCK_ENTITIES.register("transmogrifier", () -> BlockEntityType.Builder.of(TransmogrifierBlockEntity::new, UP2Blocks.TRANSMOGRIFIER.get()).build(null));
    public static final RegistryObject<BlockEntityType<ExtraDataBlockEntity>> EXTRA_DATA_BLOCK_ENTITY = BLOCK_ENTITIES.register("extra_data_block_entity", () -> BlockEntityType.Builder.of(ExtraDataBlockEntity::new,             UP2Blocks.EGG_BLOCKS.stream().map(Supplier::get).toArray(Block[]::new)).build(null));

}
