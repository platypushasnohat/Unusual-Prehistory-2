package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import java.util.function.Supplier;

public class UP2SoundDefinitionsProvider extends SoundDefinitionsProvider {

    public UP2SoundDefinitionsProvider(PackOutput packOutput, ExistingFileHelper helper) {
        super(packOutput, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {

        // Carnotaurus
        this.sound(UP2SoundEvents.CARNOTAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/hurt1")).pitch(1.2F)
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/death1"))
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/idle2"))
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_STEP,
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/step1")),
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/step2"))
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_ROAR,
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/roar1")).pitch(0.9F).attenuationDistance(32)
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_SNIFF,
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/sniff1")).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/bite1")),
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/bite2")),
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/bite3"))
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_CHARGE,
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/charge1")).attenuationDistance(32)
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_HEADBUTT,
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/headbutt1"))
        );

        // Diplocaulus
        this.sound(UP2SoundEvents.DIPLOCAULUS_HURT,
                sound("mob/frog/hurt1").pitch(0.8F),
                sound("mob/frog/hurt2").pitch(0.8F),
                sound("mob/frog/hurt3").pitch(0.8F),
                sound("mob/frog/hurt4").pitch(0.8F)
        );
        this.sound(UP2SoundEvents.DIPLOCAULUS_DEATH,
                sound("mob/frog/death1").pitch(0.8F),
                sound("mob/frog/death2").pitch(0.8F),
                sound("mob/frog/death3").pitch(0.8F)
        );
        this.sound(UP2SoundEvents.DIPLOCAULUS_IDLE,
                sound("mob/frog/idle2").pitch(0.8F),
                sound("mob/frog/idle3").pitch(0.8F),
                sound("mob/frog/idle5").pitch(0.8F),
                sound("mob/frog/idle6").pitch(0.8F),
                sound("mob/frog/idle7").pitch(0.8F)
        );

        // Dromaeosaurus
        this.sound(UP2SoundEvents.DROMAEOSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/dromaeosaurus/hurt_0")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/dromaeosaurus/hurt_1")).volume(0.8F)
        );
        this.sound(UP2SoundEvents.DROMAEOSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/dromaeosaurus/death_0")).volume(0.8F)
        );
        this.sound(UP2SoundEvents.DROMAEOSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/dromaeosaurus/idle_0")),
                sound(UnusualPrehistory2.modPrefix("entity/dromaeosaurus/idle_1")),
                sound(UnusualPrehistory2.modPrefix("entity/dromaeosaurus/idle_2"))
        );
        this.sound(UP2SoundEvents.DROMAEOSAURUS_EEPY,
                sound(UnusualPrehistory2.modPrefix("entity/dromaeosaurus/sleep_0")).volume(0.7F),
                sound(UnusualPrehistory2.modPrefix("entity/dromaeosaurus/sleep_1")).volume(0.7F)
        );

        // Dunkleosteus
        this.sound(UP2SoundEvents.DUNKLEOSTEUS_HURT,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );
        this.sound(UP2SoundEvents.DUNKLEOSTEUS_DEATH,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );
        this.sound(UP2SoundEvents.DUNKLEOSTEUS_FLOP,
                sound("entity/fish/flop1").volume(0.3F),
                sound("entity/fish/flop2").volume(0.3F),
                sound("entity/fish/flop3").volume(0.3F),
                sound("entity/fish/flop4").volume(0.3F)
        );
        this.sound(UP2SoundEvents.SMALL_DUNKLEOSTEUS_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/dunkleosteus/small_bite1")).volume(0.7F)
        );
        this.sound(UP2SoundEvents.MEDIUM_DUNKLEOSTEUS_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/dunkleosteus/medium_bite1")).volume(0.9F)
        );
        this.sound(UP2SoundEvents.LARGE_DUNKLEOSTEUS_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/dunkleosteus/large_bite1"))
        );

        // Jawless fish
        this.sound(UP2SoundEvents.JAWLESS_FISH_HURT,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );
        this.sound(UP2SoundEvents.JAWLESS_FISH_DEATH,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );
        this.sound(UP2SoundEvents.JAWLESS_FISH_FLOP,
                sound("entity/fish/flop1").volume(0.3F),
                sound("entity/fish/flop2").volume(0.3F),
                sound("entity/fish/flop3").volume(0.3F),
                sound("entity/fish/flop4").volume(0.3F)
        );

        // Kentrosaurus
        this.sound(UP2SoundEvents.KENTROSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/kentrosaurus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/kentrosaurus/hurt2"))
        );
        this.sound(UP2SoundEvents.KENTROSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/kentrosaurus/death1")),
                sound(UnusualPrehistory2.modPrefix("entity/kentrosaurus/death2"))
        );
        this.sound(UP2SoundEvents.KENTROSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/kentrosaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/kentrosaurus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/kentrosaurus/idle3"))
        );
        this.sound(UP2SoundEvents.KENTROSAURUS_STEP,
                sound("mob/camel/step1").volume(0.45F).pitch(1.12F),
                sound("mob/camel/step2").volume(0.45F).pitch(1.12F),
                sound("mob/camel/step3").volume(0.45F).pitch(1.12F),
                sound("mob/camel/step4").volume(0.45F).pitch(1.12F),
                sound("mob/camel/step5").volume(0.45F).pitch(1.12F),
                sound("mob/camel/step6").volume(0.45F).pitch(1.12F)
        );
        this.sound(UP2SoundEvents.KENTROSAURUS_EAT,
                sound("mob/goat/eat1").pitch(0.86F),
                sound("mob/goat/eat2").pitch(0.86F),
                sound("mob/goat/eat3").pitch(0.86F)
        );

        // Kimmeridgebrachypteraeschnidium
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/hurt1")).pitch(1.4F),
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/hurt2")).pitch(1.4F)
        );
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/death1"))
        );
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_LOOP,
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/loop1")).volume(0.6F).attenuationDistance(8)
        );

        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/hurt1")).pitch(2.0F),
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/hurt2")).pitch(2.0F)
        );
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/kimmeridgebrachypteraeschnidium/death1")).pitch(1.5F)
        );

        // Majungasaurus
        this.sound(UP2SoundEvents.MAJUNGASAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/majungasaurus/hurt1")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/majungasaurus/hurt2")).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.MAJUNGASAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/majungasaurus/death1")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/majungasaurus/death2")).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.MAJUNGASAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/majungasaurus/idle1")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/majungasaurus/idle2")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/majungasaurus/idle3")).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.MAJUNGASAURUS_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/bite1")).pitch(1.2F),
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/bite2")).pitch(1.2F),
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/bite3")).pitch(1.2F)
        );

        // Megalania
        this.sound(UP2SoundEvents.MEGALANIA_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/megalania/hurt1")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/megalania/hurt2")).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.MEGALANIA_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/megalania/death1")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/megalania/death2")).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.MEGALANIA_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/megalania/idle1")).pitch(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/megalania/idle2")).pitch(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/megalania/idle3")).pitch(0.8F)
        );
        this.sound(UP2SoundEvents.MEGALANIA_ROAR,
                sound(UnusualPrehistory2.modPrefix("entity/megalania/roar1"))
        );
        this.sound(UP2SoundEvents.MEGALANIA_STEP,
                sound("mob/camel/step1").volume(0.5F).pitch(0.87F),
                sound("mob/camel/step2").volume(0.5F).pitch(0.87F),
                sound("mob/camel/step3").volume(0.5F).pitch(0.87F),
                sound("mob/camel/step4").volume(0.5F).pitch(0.87F),
                sound("mob/camel/step5").volume(0.5F).pitch(0.87F),
                sound("mob/camel/step6").volume(0.5F).pitch(0.87F)
        );
        this.sound(UP2SoundEvents.MEGALANIA_TAIL_SWING,
                sound(UnusualPrehistory2.modPrefix("entity/megalania/tail_swing1")).volume(0.9F).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/megalania/tail_swing2")).volume(0.9F).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.MEGALANIA_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/megalania/bite1")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/megalania/bite2")).volume(0.8F)
        );

        // Metriorhynchus
        this.sound(UP2SoundEvents.METRIORHYNCHUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/metriorhynchus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/metriorhynchus/hurt2"))
        );
        this.sound(UP2SoundEvents.METRIORHYNCHUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/metriorhynchus/death1"))
        );
        this.sound(UP2SoundEvents.METRIORHYNCHUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/metriorhynchus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/metriorhynchus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/metriorhynchus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/metriorhynchus/idle4"))
        );
        this.sound(UP2SoundEvents.METRIORHYNCHUS_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/metriorhynchus/bite1")),
                sound(UnusualPrehistory2.modPrefix("entity/metriorhynchus/bite2"))
        );
        this.sound(UP2SoundEvents.METRIORHYNCHUS_BELLOW,
                sound(UnusualPrehistory2.modPrefix("entity/metriorhynchus/bellow1"))
        );

        // Onchopristis
        this.sound(UP2SoundEvents.ONCHOPRISTIS_HURT,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );
        this.sound(UP2SoundEvents.ONCHOPRISTIS_DEATH,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );
        this.sound(UP2SoundEvents.ONCHOPRISTIS_FLOP,
                sound("entity/fish/flop1").volume(0.3F),
                sound("entity/fish/flop2").volume(0.3F),
                sound("entity/fish/flop3").volume(0.3F),
                sound("entity/fish/flop4").volume(0.3F)
        );

        // Stethacanthus
        this.sound(UP2SoundEvents.STETHACANTHUS_HURT,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );
        this.sound(UP2SoundEvents.STETHACANTHUS_DEATH,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );
        this.sound(UP2SoundEvents.STETHACANTHUS_FLOP,
                sound("entity/fish/flop1").volume(0.3F),
                sound("entity/fish/flop2").volume(0.3F),
                sound("entity/fish/flop3").volume(0.3F),
                sound("entity/fish/flop4").volume(0.3F)
        );
        this.sound(UP2SoundEvents.STETHACANTHUS_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/stethacanthus/bite1")).volume(0.6F)
        );

        // Talpanas
        this.sound(UP2SoundEvents.TALPANAS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/talpanas/hurt1")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/talpanas/hurt2")).volume(0.8F)
        );
        this.sound(UP2SoundEvents.TALPANAS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/talpanas/death1")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/talpanas/death2")).volume(0.8F)
        );
        this.sound(UP2SoundEvents.TALPANAS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/talpanas/idle1")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/talpanas/idle2")).volume(0.8F)
        );

        // Telecrex
        this.sound(UP2SoundEvents.TELECREX_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/telecrex/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/telecrex/hurt2"))
        );
        this.sound(UP2SoundEvents.TELECREX_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/telecrex/death1")),
                sound(UnusualPrehistory2.modPrefix("entity/telecrex/death2"))
        );
        this.sound(UP2SoundEvents.TELECREX_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/telecrex/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/telecrex/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/telecrex/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/telecrex/idle4"))
        );

        // Unicorn
        this.sound(UP2SoundEvents.UNICORN_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/unicorn/hurt1"))
        );
        this.sound(UP2SoundEvents.UNICORN_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/unicorn/death1"))
        );
        this.sound(UP2SoundEvents.UNICORN_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/unicorn/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/unicorn/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/unicorn/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/unicorn/idle4"))
        );

        // Living Ooze
        this.sound(UP2SoundEvents.LIVING_OOZE_HURT,
                sound("mob/slime/big1").pitch(1.15F),
                sound("mob/slime/big2").pitch(1.15F),
                sound("mob/slime/big3").pitch(1.15F),
                sound("mob/slime/big4").pitch(1.15F)
        );
        this.sound(UP2SoundEvents.LIVING_OOZE_DEATH,
                sound("mob/slime/big1").pitch(1.1F),
                sound("mob/slime/big2").pitch(1.1F),
                sound("mob/slime/big3").pitch(1.1F),
                sound("mob/slime/big4").pitch(1.1F)
        );
        this.sound(UP2SoundEvents.LIVING_OOZE_SQUISH,
                sound("mob/slime/big1").pitch(1.15F).volume(0.5F),
                sound("mob/slime/big2").pitch(1.15F).volume(0.5F),
                sound("mob/slime/big3").pitch(1.15F).volume(0.5F),
                sound("mob/slime/big4").pitch(1.15F).volume(0.5F)
        );
        this.sound(UP2SoundEvents.LIVING_OOZE_JUMP,
                sound("mob/slime/big1").pitch(1.15F).volume(0.5F),
                sound("mob/slime/big2").pitch(1.15F).volume(0.5F),
                sound("mob/slime/big3").pitch(1.15F).volume(0.5F),
                sound("mob/slime/big4").pitch(1.15F).volume(0.5F)
        );
        this.sound(UP2SoundEvents.LIVING_OOZE_SPIT,
                sound("mob/slime/attack1").pitch(1.1F).volume(0.5F),
                sound("mob/slime/attack2").pitch(1.1F)
        );

        // Transmogrifier
        this.sound(UP2SoundEvents.TRANSMOGRIFIER_LOOP,
                sound(UnusualPrehistory2.modPrefix("block/transmogrifier/loop1")).attenuationDistance(8).volume(0.7F).pitch(0.78F)
        );
        this.sound(UP2SoundEvents.TRANSMOGRIFIER_START,
                sound(UnusualPrehistory2.modPrefix("block/transmogrifier/start1")).attenuationDistance(10)
        );
        this.sound(UP2SoundEvents.TRANSMOGRIFIER_STOP,
                sound(UnusualPrehistory2.modPrefix("block/transmogrifier/stop1")).attenuationDistance(10)
        );

        // Tar
        this.sound(UP2SoundEvents.TAR_POP,
                sound(UnusualPrehistory2.modPrefix("block/tar/pop1"))
        );

        // Music discs
        this.sound(UP2SoundEvents.DOOMSURF_DISC,
                sound(UnusualPrehistory2.modPrefix("music/doomsurf_disc")).stream()
        );
        this.sound(UP2SoundEvents.MEGALANIA_DISC,
                sound(UnusualPrehistory2.modPrefix("music/megalania_disc")).stream()
        );
        this.sound(UP2SoundEvents.TARIFYING_DISC,
                sound(UnusualPrehistory2.modPrefix("music/tarifying_disc")).stream()
        );
    }

    private void soundDefinition(Supplier<SoundEvent> soundEvent, String subtitle, SoundDefinition.Sound... sounds) {
        this.add(soundEvent.get(), SoundDefinition.definition().subtitle("subtitles." + subtitle).with(sounds));
    }

    private void sound(Supplier<SoundEvent> soundEvent, SoundDefinition.Sound... sounds){
        this.soundDefinition(soundEvent, soundEvent.get().getLocation().getPath(), sounds);
    }
}
