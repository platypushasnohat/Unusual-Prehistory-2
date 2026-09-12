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
                sound(UnusualPrehistory2.location("block/transmogrifier/processing"))
        );
    }
}
