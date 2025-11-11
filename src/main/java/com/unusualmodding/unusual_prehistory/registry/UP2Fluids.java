package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.blocks.fluid.TarFluidType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2Fluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, UnusualPrehistory2.MOD_ID);
    private static final DeferredRegister<FluidType> TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<FluidType> TAR_TYPE = TYPES.register("tar", TarFluidType::new);

    public static final RegistryObject<FlowingFluid> TAR = FLUIDS.register("tar", () -> new ForgeFlowingFluid.Source(UP2Fluids.TAR_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_TAR = FLUIDS.register("flowing_tar", () -> new ForgeFlowingFluid.Flowing(UP2Fluids.TAR_PROPERTIES));

    public static final ForgeFlowingFluid.Properties TAR_PROPERTIES = new ForgeFlowingFluid.Properties(TAR_TYPE, TAR, FLOWING_TAR).bucket(UP2Items.TAR_BUCKET).block(UP2Blocks.TAR).tickRate(30);

    public static void register(IEventBus modBus) {
        FLUIDS.register(modBus);
        TYPES.register(modBus);
    }
}
