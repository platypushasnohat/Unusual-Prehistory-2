package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class UP2Particles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GINKGO_LEAVES = registerParticle("ginkgo_leaves", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GOLDEN_GINKGO_LEAVES = registerParticle("golden_ginkgo_leaves", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EEPY = registerParticle("eepy", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> OOZE_BUBBLE = registerParticle("ooze_bubble", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TAR_BUBBLE = registerParticle("tar_bubble", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GOLDEN_HEART = registerParticle("golden_heart", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOWFLAKE = registerParticle("snowflake", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> IMPACT_STUN = registerParticle("impact_stun", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> POPPING_BUBBLE = registerParticle("popping_bubble", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SWEET_GROG_BUBBLE = registerParticle("sweet_grog_bubble", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FOUL_GROG_BUBBLE = registerParticle("foul_grog_bubble", ()-> new SimpleParticleType(false));

    private static <P extends ParticleType<?>> DeferredHolder<ParticleType<?>, P> registerParticle(String name, Supplier<P> particle ) {
        return PARTICLE_TYPES.register(name, particle);
    }
}
