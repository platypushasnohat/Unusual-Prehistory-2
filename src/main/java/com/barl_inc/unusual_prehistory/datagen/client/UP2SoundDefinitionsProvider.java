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
