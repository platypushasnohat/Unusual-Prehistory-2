package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.blocks.fluid.TarFluidType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2Fluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, UnusualPrehistory2.MOD_ID);
    public static final DeferredRegister<FluidType> TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, UnusualPrehistory2.MOD_ID);

    private static ForgeFlowingFluid.Properties tarProperties() {
        return new ForgeFlowingFluid.Properties(TAR_TYPE, TAR_FLUID_SOURCE, TAR_FLUID_FLOWING).bucket(UP2Items.TAR_BUCKET).block(UP2Blocks.TAR).levelDecreasePerBlock(3).slopeFindDistance(3).tickRate(40);
    }

    public static final RegistryObject<FluidType> TAR_TYPE = TYPES.register("tar", () -> new TarFluidType(FluidType.Properties.create().descriptionId("block.unusual_prehistory.tar").density(4000).viscosity(12000).pathType(BlockPathTypes.WATER).adjacentPathType(BlockPathTypes.WATER_BORDER).canSwim(false).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_POWDER_SNOW).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_POWDER_SNOW)));
    public static final RegistryObject<FlowingFluid> TAR_FLUID_SOURCE = FLUIDS.register("tar", () -> new ForgeFlowingFluid.Source(tarProperties()));
    public static final RegistryObject<FlowingFluid> TAR_FLUID_FLOWING = FLUIDS.register("tar_flowing", () -> new ForgeFlowingFluid.Flowing(tarProperties()));

    public static void postInit() {
        FluidInteractionRegistry.addInteraction(TAR_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                ForgeMod.WATER_TYPE.get(),
                fluidState -> UP2Blocks.ASPHALT.get().defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(TAR_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                ForgeMod.LAVA_TYPE.get(),
                fluidState -> UP2Blocks.ASPHALT.get().defaultBlockState()
        ));
    }
}
