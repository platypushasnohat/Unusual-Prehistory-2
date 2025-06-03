package com.unusualmodding.unusual_prehistory.registry;

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

    public static final RegistryObject<SoundEvent> DIPLOCAULUS_HURT = registerSoundEvent("diplocaulus_hurt");
    public static final RegistryObject<SoundEvent> DIPLOCAULUS_DEATH = registerSoundEvent("diplocaulus_death");
    public static final RegistryObject<SoundEvent> DIPLOCAULUS_IDLE = registerSoundEvent("diplocaulus_flop");

    public static final RegistryObject<SoundEvent> DUNKLEOSTEUS_HURT = registerSoundEvent("dunkleosteus_hurt");
    public static final RegistryObject<SoundEvent> DUNKLEOSTEUS_DEATH = registerSoundEvent("dunkleosteus_death");
    public static final RegistryObject<SoundEvent> DUNKLEOSTEUS_FLOP = registerSoundEvent("dunkleosteus_flop");

    public static final RegistryObject<SoundEvent> JAWLESS_FISH_HURT = registerSoundEvent("jawless_fish_hurt");
    public static final RegistryObject<SoundEvent> JAWLESS_FISH_DEATH = registerSoundEvent("jawless_fish_death");
    public static final RegistryObject<SoundEvent> JAWLESS_FISH_FLOP = registerSoundEvent("jawless_fish_flop");

    public static final RegistryObject<SoundEvent> KENTROSAURUS_HURT = registerSoundEvent("kentrosaurus_hurt");
    public static final RegistryObject<SoundEvent> KENTROSAURUS_DEATH = registerSoundEvent("kentrosaurus_death");
    public static final RegistryObject<SoundEvent> KENTROSAURUS_IDLE = registerSoundEvent("kentrosaurus_idle");
    public static final RegistryObject<SoundEvent> KENTROSAURUS_STEP = registerSoundEvent("kentrosaurus_step");
    public static final RegistryObject<SoundEvent> KENTROSAURUS_EAT = registerSoundEvent("kentrosaurus_eat");

    public static final RegistryObject<SoundEvent> KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT = registerSoundEvent("kimmeridgebrachypteraeschnidium_hurt");
    public static final RegistryObject<SoundEvent> KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH = registerSoundEvent("kimmeridgebrachypteraeschnidium_death");
    public static final RegistryObject<SoundEvent> KIMMERIDGEBRACHYPTERAESCHNIDIUM_LOOP = registerSoundEvent("kimmeridgebrachypteraeschnidium_loop");

    public static final RegistryObject<SoundEvent> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_HURT = registerSoundEvent("kimmeridgebrachypteraeschnidium_nymph_hurt");
    public static final RegistryObject<SoundEvent> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_DEATH = registerSoundEvent("kimmeridgebrachypteraeschnidium_nymph_death");

    public static final RegistryObject<SoundEvent> SCAUMENACIA_HURT = registerSoundEvent("scaumenacia_hurt");
    public static final RegistryObject<SoundEvent> SCAUMENACIA_DEATH = registerSoundEvent("scaumenacia_death");
    public static final RegistryObject<SoundEvent> SCAUMENACIA_FLOP = registerSoundEvent("scaumenacia_flop");

    public static final RegistryObject<SoundEvent> STETHACANTHUS_HURT = registerSoundEvent("stethacanthus_hurt");
    public static final RegistryObject<SoundEvent> STETHACANTHUS_DEATH = registerSoundEvent("stethacanthus_death");
    public static final RegistryObject<SoundEvent> STETHACANTHUS_FLOP = registerSoundEvent("stethacanthus_flop");

    public static final RegistryObject<SoundEvent> UNICORN_HURT = registerSoundEvent("unicorn_hurt");
    public static final RegistryObject<SoundEvent> UNICORN_DEATH = registerSoundEvent("unicorn_death");
    public static final RegistryObject<SoundEvent> UNICORN_IDLE = registerSoundEvent("unicorn_idle");

    private static RegistryObject<SoundEvent> registerSoundEvent(final String soundName) {
        return SOUND_EVENTS.register(soundName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(UnusualPrehistory2.MOD_ID, soundName)));
    }
}
