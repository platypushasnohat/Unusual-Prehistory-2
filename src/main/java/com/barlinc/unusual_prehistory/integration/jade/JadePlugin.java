package com.barlinc.unusual_prehistory.integration.jade;

import com.barlinc.unusual_prehistory.blocks.TransmogrifierBlock;
import com.barlinc.unusual_prehistory.blocks.entity.TransmogrifierBlockEntity;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.mob.update_3.LivingOoze;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new TransmogrifierProvider(), TransmogrifierBlockEntity.class);

    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(new PrehistoricMobProvider(), PrehistoricMob.class);
        registration.registerEntityComponent(new LivingOozeProvider(), LivingOoze.class);
        registration.registerBlockComponent(new TransmogrifierProvider(), TransmogrifierBlock.class);
    }
}
