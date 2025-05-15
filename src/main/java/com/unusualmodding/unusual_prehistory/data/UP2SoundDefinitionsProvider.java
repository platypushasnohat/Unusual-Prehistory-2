package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.sounds.UP2Sounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
public class UP2SoundDefinitionsProvider extends SoundDefinitionsProvider {

    public UP2SoundDefinitionsProvider(PackOutput packOutput, ExistingFileHelper helper) {
        super(packOutput, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        this.sound(UP2Sounds.KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/hurt_0")),
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/hurt_1"))
        );

        this.sound(UP2Sounds.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/death_0"))
        );

        this.sound(UP2Sounds.KIMMERIDGEBRACHYPTERAESCHNIDIUM_FLAP,
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/flap_0"))
        );
    }

    private void soundDefinition(Supplier<SoundEvent> soundEvent, String subtitle, SoundDefinition.Sound... sounds) {
        this.add(soundEvent.get(), SoundDefinition.definition().subtitle("subtitles." + subtitle).with(sounds));
    }

    private void sound(Supplier<SoundEvent> soundEvent, SoundDefinition.Sound... sounds){
        this.soundDefinition(soundEvent, soundEvent.get().getLocation().getPath(), sounds);
    }
}
