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

    // Mobs
    public static final RegistryObject<SoundEvent> AEGIROCASSIS_HURT = registerSoundEvent("aegirocassis_hurt");
    public static final RegistryObject<SoundEvent> AEGIROCASSIS_DEATH = registerSoundEvent("aegirocassis_death");

    public static final RegistryObject<SoundEvent> BARINASUCHUS_HURT = registerSoundEvent("barinasuchus_hurt");
    public static final RegistryObject<SoundEvent> BARINASUCHUS_DEATH = registerSoundEvent("barinasuchus_death");
    public static final RegistryObject<SoundEvent> BARINASUCHUS_IDLE = registerSoundEvent("barinasuchus_idle");
    public static final RegistryObject<SoundEvent> BARINASUCHUS_ATTACK = registerSoundEvent("barinasuchus_attack");
    public static final RegistryObject<SoundEvent> BARINASUCHUS_THREATEN = registerSoundEvent("barinasuchus_threaten");

    public static final RegistryObject<SoundEvent> BRACHIOSAURUS_HURT = registerSoundEvent("brachiosaurus_hurt");
    public static final RegistryObject<SoundEvent> BRACHIOSAURUS_DEATH = registerSoundEvent("brachiosaurus_death");
    public static final RegistryObject<SoundEvent> BRACHIOSAURUS_IDLE = registerSoundEvent("brachiosaurus_idle");
    public static final RegistryObject<SoundEvent> BRACHIOSAURUS_STOMP = registerSoundEvent("brachiosaurus_stomp");

    public static final RegistryObject<SoundEvent> CARNOTAURUS_HURT = registerSoundEvent("carnotaurus_hurt");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_DEATH = registerSoundEvent("carnotaurus_death");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_IDLE = registerSoundEvent("carnotaurus_idle");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_STEP = registerSoundEvent("carnotaurus_step");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_ROAR = registerSoundEvent("carnotaurus_roar");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_SNIFF = registerSoundEvent("carnotaurus_sniff");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_BITE = registerSoundEvent("carnotaurus_bite");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_CHARGE = registerSoundEvent("carnotaurus_charge");
    public static final RegistryObject<SoundEvent> CARNOTAURUS_HEADBUTT = registerSoundEvent("carnotaurus_headbutt");

    public static final RegistryObject<SoundEvent> DESMATOSUCHUS_HURT = registerSoundEvent("desmatosuchus_hurt");
    public static final RegistryObject<SoundEvent> DESMATOSUCHUS_DEATH = registerSoundEvent("desmatosuchus_death");
    public static final RegistryObject<SoundEvent> DESMATOSUCHUS_IDLE = registerSoundEvent("desmatosuchus_idle");
    public static final RegistryObject<SoundEvent> DESMATOSUCHUS_STEP = registerSoundEvent("desmatosuchus_step");

    public static final RegistryObject<SoundEvent> DIPLOCAULUS_HURT = registerSoundEvent("diplocaulus_hurt");
    public static final RegistryObject<SoundEvent> DIPLOCAULUS_DEATH = registerSoundEvent("diplocaulus_death");
    public static final RegistryObject<SoundEvent> DIPLOCAULUS_IDLE = registerSoundEvent("diplocaulus_idle");
    public static final RegistryObject<SoundEvent> DIPLOCAULUS_STEP = registerSoundEvent("diplocaulus_step");

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

    public static final RegistryObject<SoundEvent> HIBBERTOPTERUS_HURT = registerSoundEvent("hibbertopterus_hurt");
    public static final RegistryObject<SoundEvent> HIBBERTOPTERUS_DEATH = registerSoundEvent("hibbertopterus_death");
    public static final RegistryObject<SoundEvent> HIBBERTOPTERUS_IDLE = registerSoundEvent("hibbertopterus_idle");
    public static final RegistryObject<SoundEvent> HIBBERTOPTERUS_STEP = registerSoundEvent("hibbertopterus_step");

    public static final RegistryObject<SoundEvent> JAWLESS_FISH_HURT = registerSoundEvent("jawless_fish_hurt");
    public static final RegistryObject<SoundEvent> JAWLESS_FISH_DEATH = registerSoundEvent("jawless_fish_death");
    public static final RegistryObject<SoundEvent> JAWLESS_FISH_FLOP = registerSoundEvent("jawless_fish_flop");

    public static final RegistryObject<SoundEvent> KAPROSUCHUS_HURT = registerSoundEvent("kaprosuchus_hurt");
    public static final RegistryObject<SoundEvent> KAPROSUCHUS_DEATH = registerSoundEvent("kaprosuchus_death");
    public static final RegistryObject<SoundEvent> KAPROSUCHUS_IDLE = registerSoundEvent("kaprosuchus_idle");

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

    public static final RegistryObject<SoundEvent> LEPTICTIDIUM_HURT = registerSoundEvent("leptictidium_hurt");
    public static final RegistryObject<SoundEvent> LEPTICTIDIUM_DEATH = registerSoundEvent("leptictidium_death");
    public static final RegistryObject<SoundEvent> LEPTICTIDIUM_IDLE = registerSoundEvent("leptictidium_idle");

    public static final RegistryObject<SoundEvent> LYSTROSAURUS_HURT = registerSoundEvent("lystrosaurus_hurt");
    public static final RegistryObject<SoundEvent> LYSTROSAURUS_DEATH = registerSoundEvent("lystrosaurus_death");
    public static final RegistryObject<SoundEvent> LYSTROSAURUS_IDLE = registerSoundEvent("lystrosaurus_idle");
    public static final RegistryObject<SoundEvent> LYSTROSAURUS_STEP = registerSoundEvent("lystrosaurus_step");
    public static final RegistryObject<SoundEvent> LYSTROSAURUS_CHAINSMOKER = registerSoundEvent("lystrosaurus_chainsmoker");

    public static final RegistryObject<SoundEvent> MAJUNGASAURUS_HURT = registerSoundEvent("majungasaurus_hurt");
    public static final RegistryObject<SoundEvent> MAJUNGASAURUS_DEATH = registerSoundEvent("majungasaurus_death");
    public static final RegistryObject<SoundEvent> MAJUNGASAURUS_IDLE = registerSoundEvent("majungasaurus_idle");
    public static final RegistryObject<SoundEvent> MAJUNGASAURUS_BITE = registerSoundEvent("majungasaurus_bite");
    public static final RegistryObject<SoundEvent> MAJUNGASAURUS_SNIFF = registerSoundEvent("majungasaurus_sniff");
    public static final RegistryObject<SoundEvent> MAJUNGASAURUS_STEP = registerSoundEvent("majungasaurus_step");

    public static final RegistryObject<SoundEvent> MANIPULATOR_HURT = registerSoundEvent("manipulator_hurt");
    public static final RegistryObject<SoundEvent> MANIPULATOR_DEATH = registerSoundEvent("manipulator_death");
    public static final RegistryObject<SoundEvent> MANIPULATOR_IDLE = registerSoundEvent("manipulator_idle");
    public static final RegistryObject<SoundEvent> MANIPULATOR_STEP = registerSoundEvent("manipulator_step");

    public static final RegistryObject<SoundEvent> MEGALANIA_HURT = registerSoundEvent("megalania_hurt");
    public static final RegistryObject<SoundEvent> MEGALANIA_DEATH = registerSoundEvent("megalania_death");
    public static final RegistryObject<SoundEvent> MEGALANIA_IDLE = registerSoundEvent("megalania_idle");
    public static final RegistryObject<SoundEvent> MEGALANIA_ROAR = registerSoundEvent("megalania_roar");
    public static final RegistryObject<SoundEvent> MEGALANIA_STEP = registerSoundEvent("megalania_step");
    public static final RegistryObject<SoundEvent> MEGALANIA_TAIL_SWING = registerSoundEvent("megalania_tail_swing");
    public static final RegistryObject<SoundEvent> MEGALANIA_BITE = registerSoundEvent("megalania_bite");

    public static final RegistryObject<SoundEvent> METRIORHYNCHUS_HURT = registerSoundEvent("metriorhynchus_hurt");
    public static final RegistryObject<SoundEvent> METRIORHYNCHUS_DEATH = registerSoundEvent("metriorhynchus_death");
    public static final RegistryObject<SoundEvent> METRIORHYNCHUS_IDLE = registerSoundEvent("metriorhynchus_idle");
    public static final RegistryObject<SoundEvent> METRIORHYNCHUS_BITE = registerSoundEvent("metriorhynchus_bite");
    public static final RegistryObject<SoundEvent> METRIORHYNCHUS_BELLOW = registerSoundEvent("metriorhynchus_bellow");

    public static final RegistryObject<SoundEvent> ONCHOPRISTIS_HURT = registerSoundEvent("onchopristis_hurt");
    public static final RegistryObject<SoundEvent> ONCHOPRISTIS_DEATH = registerSoundEvent("onchopristis_death");
    public static final RegistryObject<SoundEvent> ONCHOPRISTIS_FLOP = registerSoundEvent("onchopristis_flop");

    public static final RegistryObject<SoundEvent> PACHYCEPHALOSAURUS_HURT = registerSoundEvent("pachycephalosaurus_hurt");
    public static final RegistryObject<SoundEvent> PACHYCEPHALOSAURUS_DEATH = registerSoundEvent("pachycephalosaurus_death");
    public static final RegistryObject<SoundEvent> PACHYCEPHALOSAURUS_IDLE = registerSoundEvent("pachycephalosaurus_idle");
    public static final RegistryObject<SoundEvent> PACHYCEPHALOSAURUS_ATTACK = registerSoundEvent("pachycephalosaurus_attack");
    public static final RegistryObject<SoundEvent> PACHYCEPHALOSAURUS_WARN = registerSoundEvent("pachycephalosaurus_warn");
    public static final RegistryObject<SoundEvent> PACHYCEPHALOSAURUS_STEP = registerSoundEvent("pachycephalosaurus_step");

    public static final RegistryObject<SoundEvent> PRAEPUSA_HURT = registerSoundEvent("praepusa_hurt");
    public static final RegistryObject<SoundEvent> PRAEPUSA_DEATH = registerSoundEvent("praepusa_death");
    public static final RegistryObject<SoundEvent> PRAEPUSA_IDLE = registerSoundEvent("praepusa_idle");
    public static final RegistryObject<SoundEvent> PRAEPUSA_MITOSIS = registerSoundEvent("praepusa_mitosis");
    public static final RegistryObject<SoundEvent> PRAEPUSA_BOUNCE = registerSoundEvent("praepusa_bounce");

    public static final RegistryObject<SoundEvent> PTERODACTYLUS_HURT = registerSoundEvent("pterodactylus_hurt");
    public static final RegistryObject<SoundEvent> PTERODACTYLUS_DEATH = registerSoundEvent("pterodactylus_death");
    public static final RegistryObject<SoundEvent> PTERODACTYLUS_IDLE = registerSoundEvent("pterodactylus_idle");

    public static final RegistryObject<SoundEvent> STETHACANTHUS_HURT = registerSoundEvent("stethacanthus_hurt");
    public static final RegistryObject<SoundEvent> STETHACANTHUS_DEATH = registerSoundEvent("stethacanthus_death");
    public static final RegistryObject<SoundEvent> STETHACANTHUS_FLOP = registerSoundEvent("stethacanthus_flop");
    public static final RegistryObject<SoundEvent> STETHACANTHUS_BITE = registerSoundEvent("stethacanthus_bite");

    public static final RegistryObject<SoundEvent> TALPANAS_HURT = registerSoundEvent("talpanas_hurt");
    public static final RegistryObject<SoundEvent> TALPANAS_DEATH = registerSoundEvent("talpanas_death");
    public static final RegistryObject<SoundEvent> TALPANAS_IDLE = registerSoundEvent("talpanas_idle");

    public static final RegistryObject<SoundEvent> TELECREX_HURT = registerSoundEvent("telecrex_hurt");
    public static final RegistryObject<SoundEvent> TELECREX_DEATH = registerSoundEvent("telecrex_death");
    public static final RegistryObject<SoundEvent> TELECREX_IDLE = registerSoundEvent("telecrex_idle");

    public static final RegistryObject<SoundEvent> THERIZINOSAURUS_HURT = registerSoundEvent("therizinosaurus_hurt");
    public static final RegistryObject<SoundEvent> THERIZINOSAURUS_DEATH = registerSoundEvent("therizinosaurus_death");
    public static final RegistryObject<SoundEvent> THERIZINOSAURUS_IDLE = registerSoundEvent("therizinosaurus_idle");
    public static final RegistryObject<SoundEvent> THERIZINOSAURUS_ATTACK = registerSoundEvent("therizinosaurus_attack");
    public static final RegistryObject<SoundEvent> THERIZINOSAURUS_STEP = registerSoundEvent("therizinosaurus_step");
    public static final RegistryObject<SoundEvent> THERIZINOSAURUS_WARN = registerSoundEvent("therizinosaurus_warn");

    public static final RegistryObject<SoundEvent> ULUGHBEGSAURUS_HURT = registerSoundEvent("ulughbegsaurus_hurt");
    public static final RegistryObject<SoundEvent> ULUGHBEGSAURUS_DEATH = registerSoundEvent("ulughbegsaurus_death");
    public static final RegistryObject<SoundEvent> ULUGHBEGSAURUS_IDLE = registerSoundEvent("ulughbegsaurus_idle");
    public static final RegistryObject<SoundEvent> ULUGHBEGSAURUS_ATTACK = registerSoundEvent("ulughbegsaurus_attack");

    public static final RegistryObject<SoundEvent> UNICORN_HURT = registerSoundEvent("unicorn_hurt");
    public static final RegistryObject<SoundEvent> UNICORN_DEATH = registerSoundEvent("unicorn_death");
    public static final RegistryObject<SoundEvent> UNICORN_IDLE = registerSoundEvent("unicorn_idle");

    // Misc
    public static final RegistryObject<SoundEvent> LIVING_OOZE_HURT = registerSoundEvent("living_ooze_hurt");
    public static final RegistryObject<SoundEvent> LIVING_OOZE_DEATH = registerSoundEvent("living_ooze_death");
    public static final RegistryObject<SoundEvent> LIVING_OOZE_SQUISH = registerSoundEvent("living_ooze_squish");
    public static final RegistryObject<SoundEvent> LIVING_OOZE_JUMP = registerSoundEvent("living_ooze_jump");
    public static final RegistryObject<SoundEvent> LIVING_OOZE_SPIT = registerSoundEvent("living_ooze_spit");

    public static final RegistryObject<SoundEvent> TRANSMOGRIFIER_LOOP = registerSoundEvent("transmogrifier_loop");
    public static final RegistryObject<SoundEvent> TRANSMOGRIFIER_START = registerSoundEvent("transmogrifier_start");
    public static final RegistryObject<SoundEvent> TRANSMOGRIFIER_STOP = registerSoundEvent("transmogrifier_stop");

    public static final RegistryObject<SoundEvent> DOOMSURF_DISC = registerSoundEvent("doomsurf_disc");
    public static final RegistryObject<SoundEvent> MEGALANIA_DISC = registerSoundEvent("megalania_disc");
    public static final RegistryObject<SoundEvent> TARIFYING_DISC = registerSoundEvent("tarifying_disc");

    public static final RegistryObject<SoundEvent> TAR_POP = registerSoundEvent("tar_pop");

    private static RegistryObject<SoundEvent> registerSoundEvent(final String soundName) {
        return SOUND_EVENTS.register(soundName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(UnusualPrehistory2.MOD_ID, soundName)));
    }
}
