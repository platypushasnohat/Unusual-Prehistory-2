package com.barlinc.unusual_prehistory.integration.jade;

import com.barlinc.unusual_prehistory.blocks.MatrixBlock;
import com.barlinc.unusual_prehistory.blocks.TransmogrifierBlock;
import com.barlinc.unusual_prehistory.blocks.entity.TransmogrifierBlockEntity;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.mob.update_3.LivingOoze;
import com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura.Arthropleura;
import com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura.ArthropleuraPart;
import net.minecraft.world.entity.Entity;
import snownee.jade.api.*;

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

        registration.addRayTraceCallback((hitResult, accessor, originalAccessor) -> {
            if (accessor instanceof EntityAccessor entityAccessor) {
                Entity entity = entityAccessor.getEntity();
                if (entity instanceof ArthropleuraPart arthropleuraPart) {
                    Entity headEntity = arthropleuraPart.getHeadEntity();
                    if (headEntity instanceof Arthropleura arthropleura) {
                        return registration.entityAccessor().from(entityAccessor).entity(arthropleura).build();
                    }
                }
            }
            return accessor;
        });

        // todo: this doesn't work
        // Overrides jade's sus block hiding plugin because we extend brushable block for the matrices
        registration.addRayTraceCallback((hitResult, accessor, originalAccessor) -> {
            if (accessor instanceof BlockAccessor blockAccessor) {
                if (blockAccessor.getBlock() instanceof MatrixBlock matrixBlock) {
                    return registration.blockAccessor().from(blockAccessor).blockState(matrixBlock.defaultBlockState()).build();
                }
            }
            return accessor;
        });
    }
}
