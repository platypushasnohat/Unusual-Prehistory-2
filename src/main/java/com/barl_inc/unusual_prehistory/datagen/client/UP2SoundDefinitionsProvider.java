package com.barl_inc.unusual_prehistory.datagen.client;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.platypushasnohat.sinew.datagen.client.SinewSoundDefinitionsProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class UP2SoundDefinitionsProvider extends SinewSoundDefinitionsProvider {

    public UP2SoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        this.registerSound(UP2SoundEvents.TRANSMOGRIFIER_PROCESSING,
                sound(UnusualPrehistory2.location("block/transmogrifier/processing")).attenuationDistance(8)
        );

        this.registerSound(UP2SoundEvents.AMMONITE_HURT,
                sound("entity/squid/hurt1").pitch(1.3F).volume(0.5F),
                sound("entity/squid/hurt2").pitch(1.3F).volume(0.5F),
                sound("entity/squid/hurt3").pitch(1.3F).volume(0.5F),
                sound("entity/squid/hurt4").pitch(1.3F).volume(0.5F)
        );
        this.registerSound(UP2SoundEvents.AMMONITE_DEATH,
                sound("entity/squid/death3").pitch(1.4F).volume(0.5F)
        );
        this.registerSound(UP2SoundEvents.AMMONITE_IDLE,
                sound(UnusualPrehistory2.location("mob/ammonite/idle1")).volume(0.4F),
                sound(UnusualPrehistory2.location("mob/ammonite/idle2")).volume(0.4F),
                sound(UnusualPrehistory2.location("mob/ammonite/idle3")).volume(0.4F),
                sound(UnusualPrehistory2.location("mob/ammonite/idle4")).volume(0.4F),
                sound(UnusualPrehistory2.location("mob/ammonite/idle5")).volume(0.4F)
        );
        this.registerSound(UP2SoundEvents.AMMONITE_SWIM,
                sound(UnusualPrehistory2.location("mob/ammonite/swim1")).volume(0.4F),
                sound(UnusualPrehistory2.location("mob/ammonite/swim2")).volume(0.4F),
                sound(UnusualPrehistory2.location("mob/ammonite/swim3")).volume(0.4F),
                sound(UnusualPrehistory2.location("mob/ammonite/swim4")).volume(0.4F),
                sound(UnusualPrehistory2.location("mob/ammonite/swim5")).volume(0.4F)
        );
        this.registerSound(UP2SoundEvents.AMMONITE_FLOP,
                sound("entity/fish/flop1").pitch(0.9F).volume(0.2F),
                sound("entity/fish/flop2").pitch(0.9F).volume(0.2F),
                sound("entity/fish/flop3").pitch(0.9F).volume(0.2F),
                sound("entity/fish/flop4").pitch(0.9F).volume(0.2F)
        );

        this.registerSound(UP2SoundEvents.LEEDSICHTHYS_HURT,
                sound(UnusualPrehistory2.location("mob/leedsichthys/hurt1")).volume(0.5F),
                sound(UnusualPrehistory2.location("mob/leedsichthys/hurt2")).volume(0.5F)
        );
        this.registerSound(UP2SoundEvents.LEEDSICHTHYS_DEATH,
                sound(UnusualPrehistory2.location("mob/leedsichthys/death"))
        );
        this.registerSound(UP2SoundEvents.LEEDSICHTHYS_IDLE,
                sound(UnusualPrehistory2.location("mob/leedsichthys/idle1")).volume(0.6F),
                sound(UnusualPrehistory2.location("mob/leedsichthys/idle2")).volume(0.6F),
                sound(UnusualPrehistory2.location("mob/leedsichthys/idle3")).volume(0.6F)
        );
        this.registerSound(UP2SoundEvents.LEEDSICHTHYS_SWIM,
                sound(UnusualPrehistory2.location("mob/leedsichthys/swim1")),
                sound(UnusualPrehistory2.location("mob/leedsichthys/swim2")),
                sound(UnusualPrehistory2.location("mob/leedsichthys/swim3"))
        );
    }
}
