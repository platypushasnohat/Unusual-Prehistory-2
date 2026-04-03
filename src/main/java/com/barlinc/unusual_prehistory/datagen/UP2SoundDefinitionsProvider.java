package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.function.Supplier;

public class UP2SoundDefinitionsProvider extends SoundDefinitionsProvider {

    public UP2SoundDefinitionsProvider(PackOutput packOutput, ExistingFileHelper helper) {
        super(packOutput, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {

        // Aegirocassis
        this.sound(UP2SoundEvents.AEGIROCASSIS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/aegirocassis/death1"))
        );
        this.sound(UP2SoundEvents.AEGIROCASSIS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/aegirocassis/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/aegirocassis/hurt2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/aegirocassis/hurt3"))
        );
        this.sound(UP2SoundEvents.AEGIROCASSIS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/aegirocassis/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/aegirocassis/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/aegirocassis/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/aegirocassis/idle4")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/aegirocassis/idle5")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/aegirocassis/idle6"))
        );
        this.sound(UP2SoundEvents.AEGIROCASSIS_HOVER,
                sound(UnusualPrehistory2.modPrefix("entity/mob/aegirocassis/hover1"))
        );

        // Barinasuchus
        this.sound(UP2SoundEvents.BARINASUCHUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/barinasuchus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/barinasuchus/hurt2"))
        );
        this.sound(UP2SoundEvents.BARINASUCHUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/barinasuchus/death1"))
        );
        this.sound(UP2SoundEvents.BARINASUCHUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/barinasuchus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/barinasuchus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/barinasuchus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/barinasuchus/idle4"))
        );
        this.sound(UP2SoundEvents.BARINASUCHUS_ATTACK,
                sound(UnusualPrehistory2.modPrefix("entity/mob/barinasuchus/bite1"))
        );
        this.sound(UP2SoundEvents.BARINASUCHUS_THREATEN,
                sound(UnusualPrehistory2.modPrefix("entity/mob/barinasuchus/threaten1")).attenuationDistance(32)
        );

        // Brachiosaurus
        this.sound(UP2SoundEvents.BRACHIOSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/hurt2"))
        );
        this.sound(UP2SoundEvents.BRACHIOSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/death1"))
        );
        this.sound(UP2SoundEvents.BRACHIOSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/idle4"))
        );
        this.sound(UP2SoundEvents.BRACHIOSAURUS_ATTACK,
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/attack1")).attenuationDistance(32)
        );
        this.sound(UP2SoundEvents.BRACHIOSAURUS_STEP,
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/step1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/step2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/step3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/step4"))
        );
        this.sound(UP2SoundEvents.BRACHIOSAURUS_CALL,
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/call1")).attenuationDistance(32),
                sound(UnusualPrehistory2.modPrefix("entity/mob/brachiosaurus/call2")).attenuationDistance(32)
        );

        // Carnotaurus
        this.sound(UP2SoundEvents.CARNOTAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/hurt1")).pitch(1.2F)
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/death1"))
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/idle2"))
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_STEP,
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/step1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/step2"))
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_ROAR,
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/roar1")).pitch(0.9F).attenuationDistance(32)
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_SNIFF,
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/sniff1")).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/bite1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/bite2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/bite3"))
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_CHARGE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/charge1")).attenuationDistance(32)
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_HEADBUTT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/headbutt1"))
        );
        this.sound(UP2SoundEvents.CARNOTAURUS_HEADBUTT_VOCAL,
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/headbutt_vocal1"))
        );

        // Coelacanthus
        this.sound(UP2SoundEvents.COELACANTHUS_HURT,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );
        this.sound(UP2SoundEvents.COELACANTHUS_DEATH,
                sound("entity/fish/hurt1"),
                sound("entity/fish/hurt2"),
                sound("entity/fish/hurt3"),
                sound("entity/fish/hurt4")
        );
        this.sound(UP2SoundEvents.COELACANTHUS_FLOP,
                sound("entity/fish/flop1").volume(0.3F),
                sound("entity/fish/flop2").volume(0.3F),
                sound("entity/fish/flop3").volume(0.3F),
                sound("entity/fish/flop4").volume(0.3F)
        );

        // Desmatosuchus
        this.sound(UP2SoundEvents.DESMATOSUCHUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/desmatosuchus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/desmatosuchus/hurt2"))
        );
        this.sound(UP2SoundEvents.DESMATOSUCHUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/desmatosuchus/death1"))
        );
        this.sound(UP2SoundEvents.DESMATOSUCHUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/desmatosuchus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/desmatosuchus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/desmatosuchus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/desmatosuchus/idle4"))
        );
        this.sound(UP2SoundEvents.DESMATOSUCHUS_STEP,
                sound("mob/pig/step1").pitch(0.8F),
                sound("mob/pig/step2").pitch(0.8F),
                sound("mob/pig/step3").pitch(0.8F),
                sound("mob/pig/step4").pitch(0.8F),
                sound("mob/pig/step5").pitch(0.8F)
        );

        // Dimorphodon
        this.sound(UP2SoundEvents.DIMORPHODON_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/dimorphodon/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/dimorphodon/hurt2"))
        );
        this.sound(UP2SoundEvents.DIMORPHODON_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/dimorphodon/death1"))
        );
        this.sound(UP2SoundEvents.DIMORPHODON_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/dimorphodon/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/dimorphodon/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/dimorphodon/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/dimorphodon/idle4"))
        );

        // Diplocaulus
        this.sound(UP2SoundEvents.DIPLOCAULUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/diplocaulus/hurt1")).volume(0.5F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/diplocaulus/hurt2")).volume(0.5F)
        );
        this.sound(UP2SoundEvents.DIPLOCAULUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/diplocaulus/death1")).volume(0.5F)
        );
        this.sound(UP2SoundEvents.DIPLOCAULUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/diplocaulus/idle1")).volume(0.17F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/diplocaulus/idle2")).volume(0.17F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/diplocaulus/idle3")).volume(0.17F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/diplocaulus/idle4")).volume(0.17F)
        );
        this.sound(UP2SoundEvents.DIPLOCAULUS_STEP,
                sound("mob/frog/step1").pitch(0.95F),
                sound("mob/frog/step2").pitch(0.95F),
                sound("mob/frog/step3").pitch(0.95F),
                sound("mob/frog/step4").pitch(0.95F)
        );

        // Dromaeosaurus
        this.sound(UP2SoundEvents.DROMAEOSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/dromaeosaurus/hurt_0")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/dromaeosaurus/hurt_1")).volume(0.8F)
        );
        this.sound(UP2SoundEvents.DROMAEOSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/dromaeosaurus/death_0")).volume(0.8F)
        );
        this.sound(UP2SoundEvents.DROMAEOSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/dromaeosaurus/idle_0")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/dromaeosaurus/idle_1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/dromaeosaurus/idle_2"))
        );
        this.sound(UP2SoundEvents.DROMAEOSAURUS_EEPY,
                sound(UnusualPrehistory2.modPrefix("entity/mob/dromaeosaurus/sleep_0")).volume(0.7F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/dromaeosaurus/sleep_1")).volume(0.7F)
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
                sound(UnusualPrehistory2.modPrefix("entity/mob/dunkleosteus/small_bite1")).volume(0.7F)
        );
        this.sound(UP2SoundEvents.MEDIUM_DUNKLEOSTEUS_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/dunkleosteus/medium_bite1")).volume(0.9F)
        );
        this.sound(UP2SoundEvents.LARGE_DUNKLEOSTEUS_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/dunkleosteus/large_bite1"))
        );

        // Hibbertopterus
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/hibbertopterus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/hibbertopterus/hurt2"))
        );
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/hibbertopterus/death1"))
        );
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/hibbertopterus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/hibbertopterus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/hibbertopterus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/hibbertopterus/idle4"))
        );
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_STEP,
                sound(UnusualPrehistory2.modPrefix("entity/mob/hibbertopterus/step1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/hibbertopterus/step2"))
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

        // Kaprosuchus
        this.sound(UP2SoundEvents.KAPROSUCHUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/kaprosuchus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/kaprosuchus/hurt2"))
        );
        this.sound(UP2SoundEvents.KAPROSUCHUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/kaprosuchus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/kaprosuchus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/kaprosuchus/idle3"))
        );
        this.sound(UP2SoundEvents.KAPROSUCHUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/kaprosuchus/death1"))
        );

        // Kentrosaurus
        this.sound(UP2SoundEvents.KENTROSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/kentrosaurus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/kentrosaurus/hurt2"))
        );
        this.sound(UP2SoundEvents.KENTROSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/kentrosaurus/death1"))
        );
        this.sound(UP2SoundEvents.KENTROSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/kentrosaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/kentrosaurus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/kentrosaurus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/kentrosaurus/idle4"))
        );
        this.sound(UP2SoundEvents.KENTROSAURUS_STEP,
                sound("mob/camel/step1").volume(0.45F).pitch(0.9F),
                sound("mob/camel/step2").volume(0.45F).pitch(0.9F),
                sound("mob/camel/step3").volume(0.45F).pitch(0.9F),
                sound("mob/camel/step4").volume(0.45F).pitch(0.9F),
                sound("mob/camel/step5").volume(0.45F).pitch(0.9F),
                sound("mob/camel/step6").volume(0.45F).pitch(0.9F)
        );

        // Kimmeridgebrachypteraeschnidium
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/kimmeridgebrachypteraeschnidium/hurt1")).pitch(1.4F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/kimmeridgebrachypteraeschnidium/hurt2")).pitch(1.4F)
        );
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/kimmeridgebrachypteraeschnidium/death1"))
        );
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_LOOP,
                sound(UnusualPrehistory2.modPrefix("entity/mob/kimmeridgebrachypteraeschnidium/loop1")).volume(0.6F).attenuationDistance(8)
        );

        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/kimmeridgebrachypteraeschnidium/hurt1")).pitch(2.0F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/kimmeridgebrachypteraeschnidium/hurt2")).pitch(2.0F)
        );
        this.sound(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/kimmeridgebrachypteraeschnidium/death1")).pitch(1.5F)
        );

        // Leptictidium
        this.sound(UP2SoundEvents.LEPTICTIDIUM_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/leptictidium/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/leptictidium/hurt2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/leptictidium/hurt3"))
        );
        this.sound(UP2SoundEvents.LEPTICTIDIUM_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/leptictidium/death1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/leptictidium/death2"))
        );
        this.sound(UP2SoundEvents.LEPTICTIDIUM_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/leptictidium/idle1")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/leptictidium/idle2")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/leptictidium/idle3")).volume(0.8F)
        );

        // Lystrosaurus
        this.sound(UP2SoundEvents.LYSTROSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/hurt1")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/hurt2")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/hurt3")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/hurt4")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/hurt5")).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.LYSTROSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/death1")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/death2")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/death3")).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.LYSTROSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/idle4")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/idle5")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/idle6")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/idle7"))
        );
        this.sound(UP2SoundEvents.LYSTROSAURUS_STEP,
                sound("mob/pig/step1").pitch(0.9F),
                sound("mob/pig/step2").pitch(0.9F),
                sound("mob/pig/step3").pitch(0.9F),
                sound("mob/pig/step4").pitch(0.9F),
                sound("mob/pig/step5").pitch(0.9F)
        );
        this.sound(UP2SoundEvents.LYSTROSAURUS_CHAINSMOKER,
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/cough1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/cough2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/lystrosaurus/cough3"))
        );

        // Majungasaurus
        this.sound(UP2SoundEvents.MAJUNGASAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/majungasaurus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/majungasaurus/hurt2"))
        );
        this.sound(UP2SoundEvents.MAJUNGASAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/majungasaurus/death1"))
        );
        this.sound(UP2SoundEvents.MAJUNGASAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/majungasaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/majungasaurus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/majungasaurus/idle3"))
        );
        this.sound(UP2SoundEvents.MAJUNGASAURUS_ATTACK,
                sound(UnusualPrehistory2.modPrefix("entity/mob/majungasaurus/attack1"))
        );
        this.sound(UP2SoundEvents.MAJUNGASAURUS_SNIFF,
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/sniff1")).pitch(1.2F)
        );
        this.sound(UP2SoundEvents.MAJUNGASAURUS_STEP,
                sound("mob/camel/step1").volume(0.4F).pitch(0.8F),
                sound("mob/camel/step2").volume(0.4F).pitch(0.8F),
                sound("mob/camel/step3").volume(0.4F).pitch(0.8F),
                sound("mob/camel/step4").volume(0.4F).pitch(0.8F),
                sound("mob/camel/step5").volume(0.4F).pitch(0.8F),
                sound("mob/camel/step6").volume(0.4F).pitch(0.8F)
        );

        // Manipulator
        this.sound(UP2SoundEvents.MANIPULATOR_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/manipulator/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/manipulator/hurt2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/manipulator/hurt3"))
        );
        this.sound(UP2SoundEvents.MANIPULATOR_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/manipulator/death1"))
        );
        this.sound(UP2SoundEvents.MANIPULATOR_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/manipulator/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/manipulator/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/manipulator/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/manipulator/idle4")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/manipulator/idle5"))
        );
        this.sound(UP2SoundEvents.MANIPULATOR_STEP,
                sound("mob/spider/step1").pitch(0.85F),
                sound("mob/spider/step2").pitch(0.85F),
                sound("mob/spider/step3").pitch(0.85F),
                sound("mob/spider/step4").pitch(0.85F)
        );
        this.sound(UP2SoundEvents.MANIPULATOR_ATTACK_VOCAL,
                sound(UnusualPrehistory2.modPrefix("entity/mob/manipulator/attack_vocal1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/manipulator/attack_vocal2"))
        );
        this.sound(UP2SoundEvents.MANIPULATOR_ATTACK_SLASH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/manipulator/attack_slash1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/manipulator/attack_slash2"))
        );

        // Megalania
        this.sound(UP2SoundEvents.MEGALANIA_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/megalania/hurt1")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/megalania/hurt2")).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.MEGALANIA_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/megalania/death1")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/megalania/death2")).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.MEGALANIA_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/megalania/idle1")).pitch(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/megalania/idle2")).pitch(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/megalania/idle3")).pitch(0.8F)
        );
        this.sound(UP2SoundEvents.MEGALANIA_ROAR,
                sound(UnusualPrehistory2.modPrefix("entity/mob/megalania/roar1"))
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
                sound(UnusualPrehistory2.modPrefix("entity/mob/megalania/tail_swing1")).volume(0.9F).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/megalania/tail_swing2")).volume(0.9F).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.MEGALANIA_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/megalania/bite1")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/megalania/bite2")).volume(0.8F)
        );

        // Metriorhynchus
        this.sound(UP2SoundEvents.METRIORHYNCHUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/metriorhynchus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/metriorhynchus/hurt2"))
        );
        this.sound(UP2SoundEvents.METRIORHYNCHUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/metriorhynchus/death1"))
        );
        this.sound(UP2SoundEvents.METRIORHYNCHUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/metriorhynchus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/metriorhynchus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/metriorhynchus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/metriorhynchus/idle4"))
        );
        this.sound(UP2SoundEvents.METRIORHYNCHUS_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/metriorhynchus/bite1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/metriorhynchus/bite2"))
        );
        this.sound(UP2SoundEvents.METRIORHYNCHUS_BELLOW,
                sound(UnusualPrehistory2.modPrefix("entity/mob/metriorhynchus/bellow1"))
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

        // Pachycephalosaurus
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/pachycephalosaurus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/pachycephalosaurus/hurt2"))
        );
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/pachycephalosaurus/death1"))
        );
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/pachycephalosaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/pachycephalosaurus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/pachycephalosaurus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/pachycephalosaurus/idle4")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/pachycephalosaurus/idle5"))
        );
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_WARN,
                sound(UnusualPrehistory2.modPrefix("entity/mob/pachycephalosaurus/warn1"))
        );
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_BONK,
                sound(UnusualPrehistory2.modPrefix("entity/mob/pachycephalosaurus/bonk1"))
        );
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_STEP,
                sound("mob/pig/step1").pitch(0.85F),
                sound("mob/pig/step2").pitch(0.85F),
                sound("mob/pig/step3").pitch(0.85F),
                sound("mob/pig/step4").pitch(0.85F),
                sound("mob/pig/step5").pitch(0.85F)
        );

        // Praepusa
        this.sound(UP2SoundEvents.PRAEPUSA_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/praepusa/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/praepusa/hurt2"))
        );
        this.sound(UP2SoundEvents.PRAEPUSA_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/praepusa/death1")).pitch(0.8F)
        );
        this.sound(UP2SoundEvents.PRAEPUSA_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/praepusa/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/praepusa/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/praepusa/idle3"))
        );
        this.sound(UP2SoundEvents.PRAEPUSA_MITOSIS,
                sound("mob/slime/attack1").pitch(1.3F),
                sound("mob/slime/attack2").pitch(1.3F)
        );
        this.sound(UP2SoundEvents.PRAEPUSA_BOUNCE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/praepusa/bounce1"))
        );

        // Psilopterus
        this.sound(UP2SoundEvents.PSILOPTERUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/death1"))
        );
        this.sound(UP2SoundEvents.PSILOPTERUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/hurt2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/hurt3"))
        );
        this.sound(UP2SoundEvents.PSILOPTERUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/idle4")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/idle5"))
        );
        this.sound(UP2SoundEvents.PSILOPTERUS_BITE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/bite1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/bite2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/bite3"))
        );
        this.sound(UP2SoundEvents.PSILOPTERUS_ATTACK,
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/attack1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/attack2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/attack3"))
        );
        this.sound(UP2SoundEvents.PSILOPTERUS_CALL,
                sound(UnusualPrehistory2.modPrefix("entity/mob/psilopterus/call1")).attenuationDistance(24)
        );

        // Grug
        this.sound(UP2SoundEvents.GRUG_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/grug/death1"))
        );
        this.sound(UP2SoundEvents.GRUG_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/grug/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/grug/hurt2"))
        );
        this.sound(UP2SoundEvents.GRUG_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/grug/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/grug/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/grug/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/grug/idle4")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/grug/idle5"))
        );
        this.sound(UP2SoundEvents.GRUG_CHASE,
                sound(UnusualPrehistory2.modPrefix("music/pummel_and_snatch_disc"))
        );

        // Pterodactylus
        this.sound(UP2SoundEvents.PTERODACTYLUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/pterodactylus/hurt1"))
        );
        this.sound(UP2SoundEvents.PTERODACTYLUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/pterodactylus/death1"))
        );
        this.sound(UP2SoundEvents.PTERODACTYLUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/pterodactylus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/pterodactylus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/pterodactylus/idle3"))
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
                sound(UnusualPrehistory2.modPrefix("entity/mob/stethacanthus/bite1")).volume(0.6F)
        );

        // Talpanas
        this.sound(UP2SoundEvents.TALPANAS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/talpanas/hurt1")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/talpanas/hurt2")).volume(0.8F)
        );
        this.sound(UP2SoundEvents.TALPANAS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/talpanas/death1")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/talpanas/death2")).volume(0.8F)
        );
        this.sound(UP2SoundEvents.TALPANAS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/talpanas/idle1")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/talpanas/idle2")).volume(0.8F)
        );

        // Telecrex
        this.sound(UP2SoundEvents.TELECREX_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/telecrex/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/telecrex/hurt2"))
        );
        this.sound(UP2SoundEvents.TELECREX_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/telecrex/death1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/telecrex/death2"))
        );
        this.sound(UP2SoundEvents.TELECREX_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/telecrex/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/telecrex/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/telecrex/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/telecrex/idle4"))
        );

        // Therizinosaurus
        this.sound(UP2SoundEvents.THERIZINOSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/hurt2"))
        );
        this.sound(UP2SoundEvents.THERIZINOSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/death1"))
        );
        this.sound(UP2SoundEvents.THERIZINOSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/idle3"))
        );
        this.sound(UP2SoundEvents.THERIZINOSAURUS_ATTACK,
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/attack1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/attack2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/attack3"))
        );
        this.sound(UP2SoundEvents.THERIZINOSAURUS_STEP,
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/step1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/step2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/step3"))
        );
        this.sound(UP2SoundEvents.THERIZINOSAURUS_ROAR,
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/roar1")).attenuationDistance(32),
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/roar2")).attenuationDistance(32)
        );
        this.sound(UP2SoundEvents.THERIZINOSAURUS_NOTICE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/notice1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/therizinosaurus/notice2"))
        );

        // Ulughbegsaurus
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/ulughbegsaurus/hurt1"))
        );
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/ulughbegsaurus/death1"))
        );
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/ulughbegsaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/ulughbegsaurus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/ulughbegsaurus/idle3"))
        );
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_ATTACK,
                sound(UnusualPrehistory2.modPrefix("entity/mob/ulughbegsaurus/bite1"))
        );
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_STEP,
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/step1")).pitch(1.13F).volume(0.6F),
                sound(UnusualPrehistory2.modPrefix("entity/mob/carnotaurus/step2")).pitch(1.13F).volume(0.6F)
        );

        // Unicorn
        this.sound(UP2SoundEvents.UNICORN_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/unicorn/hurt1"))
        );
        this.sound(UP2SoundEvents.UNICORN_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/unicorn/death1"))
        );
        this.sound(UP2SoundEvents.UNICORN_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/unicorn/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/unicorn/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/unicorn/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/unicorn/idle4"))
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
        this.sound(UP2SoundEvents.PUMMEL_AND_SNATCH_DISC,
                sound(UnusualPrehistory2.modPrefix("music/pummel_and_snatch_disc")).stream()
        );

        // Ambient mobs
        this.sound(UP2SoundEvents.BUG_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/kimmeridgebrachypteraeschnidium/death1")).pitch(1.75F)
        );
        this.sound(UP2SoundEvents.BUG_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/kimmeridgebrachypteraeschnidium/death1")).pitch(1.75F)
        );
        this.sound(UP2SoundEvents.BUG_BUZZ,
                sound(UnusualPrehistory2.modPrefix("entity/mob/ambient/bug_buzz1")).attenuationDistance(4)
        );

        // Update 5
        this.sound(UP2SoundEvents.MOSASAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/mob/mosasaurus/death1"))
        );
        this.sound(UP2SoundEvents.MOSASAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/mob/mosasaurus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/mosasaurus/hurt2"))
        );
        this.sound(UP2SoundEvents.MOSASAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/mob/mosasaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/mosasaurus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/mosasaurus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/mosasaurus/idle4"))
        );
        this.sound(UP2SoundEvents.MOSASAURUS_ATTACK,
                sound(UnusualPrehistory2.modPrefix("entity/mob/mosasaurus/attack1")),
                sound(UnusualPrehistory2.modPrefix("entity/mob/mosasaurus/attack2"))
        );
    }

    private void soundDefinition(Supplier<SoundEvent> soundEvent, String subtitle, SoundDefinition.Sound... sounds) {
        this.add(soundEvent.get(), SoundDefinition.definition().subtitle("subtitles." + subtitle).with(sounds));
    }

    private void sound(Supplier<SoundEvent> soundEvent, SoundDefinition.Sound... sounds){
        this.soundDefinition(soundEvent, soundEvent.get().getLocation().getPath(), sounds);
    }
}
