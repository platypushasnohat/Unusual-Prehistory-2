package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.registry.UP2Sounds;
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
        this.sound(UP2Sounds.DUNKLEOSTEUS_HURT,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );

        this.sound(UP2Sounds.DUNKLEOSTEUS_DEATH,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );

        this.sound(UP2Sounds.DUNKLEOSTEUS_FLOP,
                sound("entity/fish/flop1").volume(0.3f),
                sound("entity/fish/flop2").volume(0.3f),
                sound("entity/fish/flop3").volume(0.3f),
                sound("entity/fish/flop4").volume(0.3f)
        );

        this.sound(UP2Sounds.JAWLESS_FISH_HURT,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );

        this.sound(UP2Sounds.JAWLESS_FISH_DEATH,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );

        this.sound(UP2Sounds.JAWLESS_FISH_FLOP,
                sound("entity/fish/flop1").volume(0.3f),
                sound("entity/fish/flop2").volume(0.3f),
                sound("entity/fish/flop3").volume(0.3f),
                sound("entity/fish/flop4").volume(0.3f)
        );

        this.sound(UP2Sounds.KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/hurt_0")).pitch(1.4f),
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/hurt_1")).pitch(1.4f)
        );

        this.sound(UP2Sounds.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/death_0"))
        );

        this.sound(UP2Sounds.KIMMERIDGEBRACHYPTERAESCHNIDIUM_FLAP,
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/flap_0")).volume(0.3f)
        );

        this.sound(UP2Sounds.SCAUMENACIA_HURT,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );

        this.sound(UP2Sounds.SCAUMENACIA_DEATH,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );

        this.sound(UP2Sounds.SCAUMENACIA_FLOP,
                sound("entity/fish/flop1").volume(0.3f),
                sound("entity/fish/flop2").volume(0.3f),
                sound("entity/fish/flop3").volume(0.3f),
                sound("entity/fish/flop4").volume(0.3f)
        );

        this.sound(UP2Sounds.STETHACANTHUS_HURT,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );

        this.sound(UP2Sounds.STETHACANTHUS_DEATH,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );

        this.sound(UP2Sounds.STETHACANTHUS_FLOP,
                sound("entity/fish/flop1").volume(0.3f),
                sound("entity/fish/flop2").volume(0.3f),
                sound("entity/fish/flop3").volume(0.3f),
                sound("entity/fish/flop4").volume(0.3f)
        );

        this.sound(UP2Sounds.UNICORN_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/unicorn/hurt_0"))
        );

        this.sound(UP2Sounds.UNICORN_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/unicorn/death_0"))
        );

        this.sound(UP2Sounds.UNICORN_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/unicorn/idle_0")),
                sound(UnusualPrehistory2.modPrefix("entity/unicorn/idle_1")),
                sound(UnusualPrehistory2.modPrefix("entity/unicorn/idle_2")),
                sound(UnusualPrehistory2.modPrefix("entity/unicorn/idle_3"))
        );
    }

    private void soundDefinition(Supplier<SoundEvent> soundEvent, String subtitle, SoundDefinition.Sound... sounds) {
        this.add(soundEvent.get(), SoundDefinition.definition().subtitle("subtitles." + subtitle).with(sounds));
    }

    private void sound(Supplier<SoundEvent> soundEvent, SoundDefinition.Sound... sounds){
        this.soundDefinition(soundEvent, soundEvent.get().getLocation().getPath(), sounds);
    }
}
