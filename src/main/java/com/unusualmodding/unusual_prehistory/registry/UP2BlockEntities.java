package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.blocks.blockentity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2BlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<BlockEntityType<TransmogrifierBlockEntity>> TRANSMOGRIFIER = BLOCK_ENTITIES.register("transmogrifier", () -> BlockEntityType.Builder.of(TransmogrifierBlockEntity::new, UP2Blocks.TRANSMOGRIFIER.get()).build(null));
    public static final RegistryObject<BlockEntityType<FossilBlockEntity>> FOSSIL = BLOCK_ENTITIES.register("fossil", () -> BlockEntityType.Builder.of(FossilBlockEntity::new, UP2Blocks.FOSSIL.get(), UP2Blocks.DEEPSLATE_FOSSIL.get()).build(null));

}
