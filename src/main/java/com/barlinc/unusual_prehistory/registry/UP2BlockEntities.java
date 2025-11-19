package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.blocks.blockentity.TransmogrifierBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2BlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<BlockEntityType<TransmogrifierBlockEntity>> TRANSMOGRIFIER = BLOCK_ENTITIES.register("transmogrifier", () -> BlockEntityType.Builder.of(TransmogrifierBlockEntity::new, UP2Blocks.TRANSMOGRIFIER.get()).build(null));

}
