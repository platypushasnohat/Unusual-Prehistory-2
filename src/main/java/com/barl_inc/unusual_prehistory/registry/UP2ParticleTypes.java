package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class UP2ParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> OOZE_BUBBLE = registerParticle("ooze_bubble", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOWFLAKE = registerParticle("snowflake", ()-> new SimpleParticleType(false));

    private static <P extends ParticleType<?>> DeferredHolder<ParticleType<?>, P> registerParticle(String name, Supplier<P> particle ) {
        return PARTICLE_TYPES.register(name, particle);
    }
}
