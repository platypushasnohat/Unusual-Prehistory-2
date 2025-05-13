package com.unusualmodding.unusual_prehistory.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class UP2Properties {

    public static final WoodType GINKGO = WoodType.register(new WoodType("ginkgo", BlockSetType.CHERRY));

    public static final BlockBehaviour.Properties DRYO_LOG_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD).instrument(NoteBlockInstrument.BASS);


    public static final BlockBehaviour.Properties PLANT_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY);
    public static final BlockBehaviour.Properties TALL_PLANT_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noCollission().instabreak().ignitedByLava().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY);

    public static final BlockBehaviour.Properties DEAD_CORAL_BLOCK_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F);
    public static final BlockBehaviour.Properties DEAD_CORAL_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission().instabreak();

    public static BlockBehaviour.Properties leavesProperties(MapColor color, SoundType sound) {
        return BlockBehaviour.Properties.of().mapColor(color).strength(0.2F).randomTicks().sound(sound).noOcclusion().isValidSpawn(UP2Properties::ocelotOrParrot).isSuffocating(UP2Properties::never).isViewBlocking(UP2Properties::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(UP2Properties::never);
    }

    public static BlockBehaviour.Properties logProperties(MapColor color, SoundType sound, NoteBlockInstrument instrument) {
        return BlockBehaviour.Properties.of().mapColor(color).strength(2.0F, 3.0F).sound(sound).instrument(instrument);
    }

    public static BlockBehaviour.Properties coralProperties(MapColor color) {
        return BlockBehaviour.Properties.of().mapColor(color).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY);
    }

    public static BlockBehaviour.Properties coralBlockProperties(MapColor color) {
        return BlockBehaviour.Properties.of().mapColor(color).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK);
    }

    public static boolean ocelotOrParrot(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entity) {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }

    public static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }
}
