package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID)
public class UP2Particles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<ParticleType> GINKGO_LEAVES = registerParticle("ginkgo_leaves", ()-> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType> GOLDEN_GINKGO_LEAVES = registerParticle("golden_ginkgo_leaves", ()-> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> EEPY = registerParticle("eepy", ()-> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> OOZE_BUBBLE = registerParticle("ooze_bubble", ()-> new SimpleParticleType(false));

    private static <P extends ParticleType> RegistryObject<P> registerParticle(String name, Supplier<P> particle ) {
        return PARTICLE_TYPES.register(name, particle);
    }
}
