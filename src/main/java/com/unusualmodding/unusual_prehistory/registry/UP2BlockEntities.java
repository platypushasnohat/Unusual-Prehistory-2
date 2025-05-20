package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.blocks.blockentity.ExtractorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2BlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("extractor", () -> BlockEntityType.Builder.of(ExtractorBlockEntity::new, UP2Blocks.EXTRACTOR.get()).build(null));

}
