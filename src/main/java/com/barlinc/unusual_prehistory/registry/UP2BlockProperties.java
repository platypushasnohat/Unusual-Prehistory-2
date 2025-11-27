package com.barlinc.unusual_prehistory.registry;

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

public class UP2BlockProperties {

    public static final WoodType GINKGO_WOOD_TYPE = WoodType.register(new WoodType("unusual_prehistory:ginkgo", BlockSetType.OAK));
    public static final WoodType LEPIDODENDRON_WOOD_TYPE = WoodType.register(new WoodType("unusual_prehistory:lepidodendron", BlockSetType.OAK));

    public static final BlockBehaviour.Properties PLANT = BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY);
    public static final BlockBehaviour.Properties TALL_PLANT = BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noCollission().instabreak().ignitedByLava().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY);

    public static final BlockBehaviour.Properties EGG = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.5F).sound(SoundType.METAL).noOcclusion();
    public static final BlockBehaviour.Properties WATER_EGG = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).instabreak().noOcclusion().noCollission().sound(SoundType.FROGSPAWN);

    public static final BlockBehaviour.Properties FOSSIL_BLOCK = BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK);

    public static final BlockBehaviour.Properties TAR = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(100.0F).noCollission().sound(SoundType.MUD).emissiveRendering((state, world, pos) -> false).noLootTable().replaceable().liquid().pushReaction(PushReaction.DESTROY);

    public static BlockBehaviour.Properties fossilLantern(int lightLevel) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK).lightLevel((state) -> lightLevel);
    }

    public static BlockBehaviour.Properties leaves(MapColor color, SoundType sound) {
        return BlockBehaviour.Properties.of().mapColor(color).strength(0.2F).randomTicks().sound(sound).noOcclusion().isValidSpawn(UP2BlockProperties::ocelotOrParrot).isSuffocating(UP2BlockProperties::never).isViewBlocking(UP2BlockProperties::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(UP2BlockProperties::never);
    }

    public static BlockBehaviour.Properties sapling(MapColor color, SoundType sound) {
        return BlockBehaviour.Properties.of().mapColor(color).noCollission().randomTicks().instabreak().sound(sound).pushReaction(PushReaction.DESTROY);
    }

    public static BlockBehaviour.Properties log(MapColor color, SoundType sound, NoteBlockInstrument instrument) {
        return BlockBehaviour.Properties.of().mapColor(color).strength(2.0F, 3.0F).sound(sound).instrument(instrument).ignitedByLava();
    }

    public static BlockBehaviour.Properties plank(MapColor color, SoundType sound, NoteBlockInstrument instrument) {
        return BlockBehaviour.Properties.of().mapColor(color).strength(2.0F, 3.0F).sound(sound).instrument(instrument).ignitedByLava();
    }

    public static BlockBehaviour.Properties woodenPressurePlate(MapColor color, SoundType sound, NoteBlockInstrument instrument) {
        return BlockBehaviour.Properties.of().mapColor(color).strength(0.5F).sound(sound).instrument(instrument).noCollission().forceSolidOn().pushReaction(PushReaction.DESTROY).ignitedByLava();
    }

    public static BlockBehaviour.Properties woodenButton(SoundType sound, NoteBlockInstrument instrument) {
        return BlockBehaviour.Properties.of().strength(0.5F).sound(sound).instrument(instrument).noCollission().pushReaction(PushReaction.DESTROY);
    }

    public static BlockBehaviour.Properties woodenDoor(MapColor color, SoundType sound, NoteBlockInstrument instrument) {
        return BlockBehaviour.Properties.of().mapColor(color).strength(3.0F).sound(sound).instrument(instrument).pushReaction(PushReaction.DESTROY).noOcclusion();
    }

    public static BlockBehaviour.Properties woodenTrapdoor(MapColor color, SoundType sound, NoteBlockInstrument instrument) {
        return BlockBehaviour.Properties.of().mapColor(color).strength(3.0F).sound(sound).instrument(instrument).noOcclusion().isValidSpawn(UP2BlockProperties::neverEntity);
    }

    public static BlockBehaviour.Properties coral(MapColor color) {
        return BlockBehaviour.Properties.of().mapColor(color).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY);
    }

    public static BlockBehaviour.Properties coralBlock(MapColor color) {
        return BlockBehaviour.Properties.of().mapColor(color).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK);
    }

    public static boolean ocelotOrParrot(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entity) {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }

    public static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    public static boolean neverEntity(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entity) {
        return false;
    }
}
