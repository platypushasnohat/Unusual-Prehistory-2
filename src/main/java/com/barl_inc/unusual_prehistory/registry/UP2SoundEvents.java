package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2SoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(Registries.SOUND_EVENT, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> TRANSMOGRIFIER_PROCESSING = registerSoundEvent("transmogrifier_processing");

    public static final DeferredHolder<SoundEvent, SoundEvent> LEEDSICHTHYS_HURT = registerSoundEvent("leedsichthys_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> LEEDSICHTHYS_DEATH = registerSoundEvent("leedsichthys_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> LEEDSICHTHYS_IDLE = registerSoundEvent("leedsichthys_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> LEEDSICHTHYS_SWIM = registerSoundEvent("leedsichthys_swim");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(final String soundName) {
        return SOUND_EVENT.register(soundName, () -> SoundEvent.createVariableRangeEvent(UnusualPrehistory2.location(soundName)));
    }
}
