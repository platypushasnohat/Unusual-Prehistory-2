package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UP2SoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<SoundEvent> CARNOTAURUS_HURT = registerSoundEvent("carnotaurus_hurt");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_DEATH = registerSoundEvent("carnotaurus_death");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_IDLE = registerSoundEvent("carnotaurus_idle");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_STEP = registerSoundEvent("carnotaurus_step");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_ROAR = registerSoundEvent("carnotaurus_roar");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_SNIFF = registerSoundEvent("carnotaurus_sniff");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_BITE = registerSoundEvent("carnotaurus_bite");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_CHARGE = registerSoundEvent("carnotaurus_charge");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_HEADBUTT = registerSoundEvent("carnotaurus_headbutt");

    public static final RegistryObject<SoundEvent> DIPLOCAULUS_HURT = registerSoundEvent("diplocaulus_hurt");
    public static final RegistryObject<SoundEvent> DIPLOCAULUS_DEATH = registerSoundEvent("diplocaulus_death");
    public static final RegistryObject<SoundEvent> DIPLOCAULUS_IDLE = registerSoundEvent("diplocaulus_flop");

    public static final RegistryObject<SoundEvent> DROMAEOSAURUS_HURT = registerSoundEvent("dromaeosaurus_hurt");
    public static final RegistryObject<SoundEvent> DROMAEOSAURUS_DEATH = registerSoundEvent("dromaeosaurus_death");
    public static final RegistryObject<SoundEvent> DROMAEOSAURUS_IDLE = registerSoundEvent("dromaeosaurus_idle");
    public static final RegistryObject<SoundEvent> DROMAEOSAURUS_EEPY = registerSoundEvent("dromaeosaurus_eepy");

    public static final RegistryObject<SoundEvent> DUNKLEOSTEUS_HURT = registerSoundEvent("dunkleosteus_hurt");
    public static final RegistryObject<SoundEvent> DUNKLEOSTEUS_DEATH = registerSoundEvent("dunkleosteus_death");
    public static final RegistryObject<SoundEvent> DUNKLEOSTEUS_FLOP = registerSoundEvent("dunkleosteus_flop");
    public static final RegistryObject<SoundEvent> SMALL_DUNKLEOSTEUS_BITE = registerSoundEvent("small_dunkleosteus_bite");
    public static final RegistryObject<SoundEvent> MEDIUM_DUNKLEOSTEUS_BITE = registerSoundEvent("medium_dunkleosteus_bite");
    public static final RegistryObject<SoundEvent> LARGE_DUNKLEOSTEUS_BITE = registerSoundEvent("large_dunkleosteus_bite");

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

    public static final RegistryObject<SoundEvent> MAJUNGASAURUS_HURT = registerSoundEvent("majungasaurus_hurt");
    public static final RegistryObject<SoundEvent> MAJUNGASAURUS_DEATH = registerSoundEvent("majungasaurus_death");
    public static final RegistryObject<SoundEvent> MAJUNGASAURUS_IDLE = registerSoundEvent("majungasaurus_idle");

    public static final RegistryObject<SoundEvent> MEGALANIA_HURT = registerSoundEvent("megalania_hurt");
    public static final RegistryObject<SoundEvent> MEGALANIA_DEATH = registerSoundEvent("megalania_death");
    public static final RegistryObject<SoundEvent> MEGALANIA_IDLE = registerSoundEvent("megalania_idle");

    public static final RegistryObject<SoundEvent> STETHACANTHUS_HURT = registerSoundEvent("stethacanthus_hurt");
    public static final RegistryObject<SoundEvent> STETHACANTHUS_DEATH = registerSoundEvent("stethacanthus_death");
    public static final RegistryObject<SoundEvent> STETHACANTHUS_FLOP = registerSoundEvent("stethacanthus_flop");

    public static final RegistryObject<SoundEvent> TALPANAS_HURT = registerSoundEvent("talpanas_hurt");
    public static final RegistryObject<SoundEvent> TALPANAS_DEATH = registerSoundEvent("talpanas_death");
    public static final RegistryObject<SoundEvent> TALPANAS_IDLE = registerSoundEvent("talpanas_idle");

    public static final RegistryObject<SoundEvent> TELECREX_HURT = registerSoundEvent("telecrex_hurt");
    public static final RegistryObject<SoundEvent> TELECREX_DEATH = registerSoundEvent("telecrex_death");
    public static final RegistryObject<SoundEvent> TELECREX_IDLE = registerSoundEvent("telecrex_idle");

    public static final RegistryObject<SoundEvent> UNICORN_HURT = registerSoundEvent("unicorn_hurt");
    public static final RegistryObject<SoundEvent> UNICORN_DEATH = registerSoundEvent("unicorn_death");
    public static final RegistryObject<SoundEvent> UNICORN_IDLE = registerSoundEvent("unicorn_idle");

    public static final RegistryObject<SoundEvent> TRANSMOGRIFIER_LOOP = registerSoundEvent("transmogrifier_loop");
    public static final RegistryObject<SoundEvent> TRANSMOGRIFIER_START = registerSoundEvent("transmogrifier_start");
    public static final RegistryObject<SoundEvent> TRANSMOGRIFIER_STOP = registerSoundEvent("transmogrifier_stop");

    public static final RegistryObject<SoundEvent> TARIFYING_DISC = registerSoundEvent("tarifying_disc");

    public static final RegistryObject<SoundEvent> TAR_POP = registerSoundEvent("tar_pop");

    private static RegistryObject<SoundEvent> registerSoundEvent(final String soundName) {
        return SOUND_EVENTS.register(soundName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(UnusualPrehistory2.MOD_ID, soundName)));
    }
}
