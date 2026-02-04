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

        // Aegirocassis
        this.sound(UP2SoundEvents.AEGIROCASSIS_DEATH,
                sound("entity/fish/hurt1").pitch(0.3F),
                sound("entity/fish/hurt2").pitch(0.3F),
                sound("entity/fish/hurt3").pitch(0.3F),
                sound("entity/fish/hurt4").pitch(0.3F)
        );
        this.sound(UP2SoundEvents.AEGIROCASSIS_HURT,
                sound("entity/fish/hurt1").pitch(0.5F),
                sound("entity/fish/hurt2").pitch(0.5F),
                sound("entity/fish/hurt3").pitch(0.5F),
                sound("entity/fish/hurt4").pitch(0.5F)
        );

        // Barinasuchus
        this.sound(UP2SoundEvents.BARINASUCHUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/barinasuchus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/barinasuchus/hurt2"))
        );
        this.sound(UP2SoundEvents.BARINASUCHUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/barinasuchus/death1"))
        );
        this.sound(UP2SoundEvents.BARINASUCHUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/barinasuchus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/barinasuchus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/barinasuchus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/barinasuchus/idle4"))
        );
        this.sound(UP2SoundEvents.BARINASUCHUS_ATTACK,
                sound(UnusualPrehistory2.modPrefix("entity/barinasuchus/bite1"))
        );
        this.sound(UP2SoundEvents.BARINASUCHUS_THREATEN,
                sound(UnusualPrehistory2.modPrefix("entity/barinasuchus/threaten1")).attenuationDistance(32)
        );

        // Brachiosaurus
        this.sound(UP2SoundEvents.BRACHIOSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/brachiosaurus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/brachiosaurus/hurt2"))
        );
        this.sound(UP2SoundEvents.BRACHIOSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/brachiosaurus/death1"))
        );
        this.sound(UP2SoundEvents.BRACHIOSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/brachiosaurus/idle1")).attenuationDistance(20),
                sound(UnusualPrehistory2.modPrefix("entity/brachiosaurus/idle2")).attenuationDistance(20),
                sound(UnusualPrehistory2.modPrefix("entity/brachiosaurus/idle3")).attenuationDistance(20)
        );
        this.sound(UP2SoundEvents.BRACHIOSAURUS_STOMP,
                sound(UnusualPrehistory2.modPrefix("entity/brachiosaurus/stomp1")).attenuationDistance(32)
        );

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

        // Desmatosuchus
        this.sound(UP2SoundEvents.DESMATOSUCHUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/desmatosuchus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/desmatosuchus/hurt2"))
        );
        this.sound(UP2SoundEvents.DESMATOSUCHUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/desmatosuchus/death1"))
        );
        this.sound(UP2SoundEvents.DESMATOSUCHUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/desmatosuchus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/desmatosuchus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/desmatosuchus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/desmatosuchus/idle4"))
        );
        this.sound(UP2SoundEvents.DESMATOSUCHUS_STEP,
                sound("mob/pig/step1").pitch(0.8F),
                sound("mob/pig/step2").pitch(0.8F),
                sound("mob/pig/step3").pitch(0.8F),
                sound("mob/pig/step4").pitch(0.8F),
                sound("mob/pig/step5").pitch(0.8F)
        );

        // Diplocaulus
        this.sound(UP2SoundEvents.DIPLOCAULUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/diplocaulus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/diplocaulus/hurt2"))
        );
        this.sound(UP2SoundEvents.DIPLOCAULUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/diplocaulus/death1"))
        );
        this.sound(UP2SoundEvents.DIPLOCAULUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/diplocaulus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/diplocaulus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/diplocaulus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/diplocaulus/idle4"))
        );
        this.sound(UP2SoundEvents.DIPLOCAULUS_STEP,
                sound("mob/frog/step1").pitch(0.95F),
                sound("mob/frog/step2").pitch(0.95F),
                sound("mob/frog/step3").pitch(0.95F),
                sound("mob/frog/step4").pitch(0.95F)
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

        // Hibbertopterus
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/hibbertopterus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/hibbertopterus/hurt2"))
        );
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/hibbertopterus/death1"))
        );
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/hibbertopterus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/hibbertopterus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/hibbertopterus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/hibbertopterus/idle4"))
        );
        this.sound(UP2SoundEvents.HIBBERTOPTERUS_STEP,
                sound(UnusualPrehistory2.modPrefix("entity/hibbertopterus/step1")),
                sound(UnusualPrehistory2.modPrefix("entity/hibbertopterus/step2"))
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
                sound(UnusualPrehistory2.modPrefix("entity/kaprosuchus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/kaprosuchus/hurt2"))
        );
        this.sound(UP2SoundEvents.KAPROSUCHUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/kaprosuchus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/kaprosuchus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/kaprosuchus/idle3"))
        );
        this.sound(UP2SoundEvents.KAPROSUCHUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/kaprosuchus/death1"))
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

        // Leptictidium
        this.sound(UP2SoundEvents.LEPTICTIDIUM_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/leptictidium/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/leptictidium/hurt2")),
                sound(UnusualPrehistory2.modPrefix("entity/leptictidium/hurt3"))
        );
        this.sound(UP2SoundEvents.LEPTICTIDIUM_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/leptictidium/death1")),
                sound(UnusualPrehistory2.modPrefix("entity/leptictidium/death2"))
        );
        this.sound(UP2SoundEvents.LEPTICTIDIUM_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/leptictidium/idle1")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/leptictidium/idle2")).volume(0.8F),
                sound(UnusualPrehistory2.modPrefix("entity/leptictidium/idle3")).volume(0.8F)
        );

        // Lystrosaurus
        this.sound(UP2SoundEvents.LYSTROSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/hurt1")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/hurt2")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/hurt3")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/hurt4")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/hurt5")).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.LYSTROSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/death1")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/death2")).pitch(0.9F),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/death3")).pitch(0.9F)
        );
        this.sound(UP2SoundEvents.LYSTROSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/idle4")),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/idle5")),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/idle6")),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/idle7"))
        );
        this.sound(UP2SoundEvents.LYSTROSAURUS_STEP,
                sound("mob/pig/step1").pitch(0.9F),
                sound("mob/pig/step2").pitch(0.9F),
                sound("mob/pig/step3").pitch(0.9F),
                sound("mob/pig/step4").pitch(0.9F),
                sound("mob/pig/step5").pitch(0.9F)
        );
        this.sound(UP2SoundEvents.LYSTROSAURUS_CHAINSMOKER,
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/cough1")),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/cough2")),
                sound(UnusualPrehistory2.modPrefix("entity/lystrosaurus/cough3"))
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
        this.sound(UP2SoundEvents.MAJUNGASAURUS_SNIFF,
                sound(UnusualPrehistory2.modPrefix("entity/carnotaurus/sniff1")).pitch(1.2F)
        );
        this.sound(UP2SoundEvents.MAJUNGASAURUS_STEP,
                sound("mob/camel/step1").volume(0.4F).pitch(0.9F),
                sound("mob/camel/step2").volume(0.4F).pitch(0.9F),
                sound("mob/camel/step3").volume(0.4F).pitch(0.9F),
                sound("mob/camel/step4").volume(0.4F).pitch(0.9F),
                sound("mob/camel/step5").volume(0.4F).pitch(0.9F),
                sound("mob/camel/step6").volume(0.4F).pitch(0.9F)
        );

        // Manipulator
        this.sound(UP2SoundEvents.MANIPULATOR_HURT,
                sound("mob/spider/say1").pitch(0.6F),
                sound("mob/spider/say2").pitch(0.6F),
                sound("mob/spider/say3").pitch(0.6F),
                sound("mob/spider/say4").pitch(0.6F)
        );
        this.sound(UP2SoundEvents.MANIPULATOR_DEATH,
                sound("mob/spider/death").pitch(0.85F)
        );
        this.sound(UP2SoundEvents.MANIPULATOR_IDLE,
                sound("mob/spider/say1").pitch(0.85F),
                sound("mob/spider/say2").pitch(0.85F),
                sound("mob/spider/say3").pitch(0.85F),
                sound("mob/spider/say4").pitch(0.85F)
        );
        this.sound(UP2SoundEvents.MANIPULATOR_STEP,
                sound("mob/spider/step1").pitch(0.85F),
                sound("mob/spider/step2").pitch(0.85F),
                sound("mob/spider/step3").pitch(0.85F),
                sound("mob/spider/step4").pitch(0.85F)
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
        this.sound(UP2SoundEvents.MEGALANIA_JUMPSCARE,
                sound(UnusualPrehistory2.modPrefix("entity/megalania/jumpscare1")).attenuationDistance(20)
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

        // Pachycephalosaurus
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/pachycephalosaurus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/pachycephalosaurus/hurt2"))
        );
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/pachycephalosaurus/death1"))
        );
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/pachycephalosaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/pachycephalosaurus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/pachycephalosaurus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/pachycephalosaurus/idle4")),
                sound(UnusualPrehistory2.modPrefix("entity/pachycephalosaurus/idle5"))
        );
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_ATTACK,
                sound(UnusualPrehistory2.modPrefix("entity/pachycephalosaurus/attack1")),
                sound(UnusualPrehistory2.modPrefix("entity/pachycephalosaurus/attack2"))
        );
        this.sound(UP2SoundEvents.PACHYCEPHALOSAURUS_WARN,
                sound(UnusualPrehistory2.modPrefix("entity/pachycephalosaurus/warn1"))
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
                sound(UnusualPrehistory2.modPrefix("entity/praepusa/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/praepusa/hurt2"))
        );
        this.sound(UP2SoundEvents.PRAEPUSA_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/praepusa/death1")).pitch(0.8F)
        );
        this.sound(UP2SoundEvents.PRAEPUSA_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/praepusa/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/praepusa/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/praepusa/idle3"))
        );
        this.sound(UP2SoundEvents.PRAEPUSA_MITOSIS,
                sound("mob/slime/attack1").pitch(1.3F),
                sound("mob/slime/attack2").pitch(1.3F)
        );
        this.sound(UP2SoundEvents.PRAEPUSA_BOUNCE,
                sound(UnusualPrehistory2.modPrefix("entity/praepusa/bounce1"))
        );

        // Pterodactylus
        this.sound(UP2SoundEvents.PTERODACTYLUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/pterodactylus/hurt1"))
        );
        this.sound(UP2SoundEvents.PTERODACTYLUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/pterodactylus/death1"))
        );
        this.sound(UP2SoundEvents.PTERODACTYLUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/pterodactylus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/pterodactylus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/pterodactylus/idle3"))
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

        // Therizinosaurus
        this.sound(UP2SoundEvents.THERIZINOSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/hurt1")),
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/hurt2"))
        );
        this.sound(UP2SoundEvents.THERIZINOSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/death1"))
        );
        this.sound(UP2SoundEvents.THERIZINOSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/idle3")),
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/idle4"))
        );
        this.sound(UP2SoundEvents.THERIZINOSAURUS_ATTACK,
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/attack1")),
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/attack2")),
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/attack3"))
        );
        this.sound(UP2SoundEvents.THERIZINOSAURUS_STEP,
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/step1")),
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/step2")),
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/step3"))
        );
        this.sound(UP2SoundEvents.THERIZINOSAURUS_WARN,
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/warn1")).attenuationDistance(32).pitch(0.85F),
                sound(UnusualPrehistory2.modPrefix("entity/therizinosaurus/warn2")).attenuationDistance(32).pitch(0.85F)
        );

        // Ulughbegsaurus
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_HURT,
                sound(UnusualPrehistory2.modPrefix("entity/ulughbegsaurus/hurt1"))
        );
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_DEATH,
                sound(UnusualPrehistory2.modPrefix("entity/ulughbegsaurus/death1"))
        );
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_IDLE,
                sound(UnusualPrehistory2.modPrefix("entity/ulughbegsaurus/idle1")),
                sound(UnusualPrehistory2.modPrefix("entity/ulughbegsaurus/idle2")),
                sound(UnusualPrehistory2.modPrefix("entity/ulughbegsaurus/idle3"))
        );
        this.sound(UP2SoundEvents.ULUGHBEGSAURUS_ATTACK,
                sound(UnusualPrehistory2.modPrefix("entity/ulughbegsaurus/bite1"))
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
