package com.barl_inc.unusual_prehistory.integration.jade;

import com.barl_inc.unusual_prehistory.block.EggBlock;
import com.barl_inc.unusual_prehistory.block.TransmogrifierBlock;
import com.barl_inc.unusual_prehistory.block.entity.TransmogrifierBlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new TransmogrifierProvider(), TransmogrifierBlockEntity.class);
        registration.registerBlockDataProvider(new EggBlockProvider(), EggBlock.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new TransmogrifierProvider(), TransmogrifierBlock.class);
        registration.registerBlockComponent(new EggBlockProvider(), EggBlock.class);
    }
}
