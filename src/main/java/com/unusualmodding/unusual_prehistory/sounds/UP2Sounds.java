package com.unusualmodding.unusual_prehistory.sounds;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UP2Sounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<SoundEvent> KIMMERIDGEBRACHYPTERAESCHNIDIUM_FLAP = registerSoundEvent("kimmeridgebrachypteraeschnidium_flap");
    public static final RegistryObject<SoundEvent> KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH = registerSoundEvent("kimmeridgebrachypteraeschnidium_death");
    public static final RegistryObject<SoundEvent> KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT = registerSoundEvent("kimmeridgebrachypteraeschnidium_hurt");

    private static RegistryObject<SoundEvent> registerSoundEvent(final String soundName) {
        return SOUND_EVENTS.register(soundName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(UnusualPrehistory2.MOD_ID, soundName)));
    }
}
