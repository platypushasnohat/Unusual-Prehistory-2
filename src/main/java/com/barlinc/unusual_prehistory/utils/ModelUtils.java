package com.barlinc.unusual_prehistory.utils;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.client.model.geom.ModelPart;

public class ModelUtils {

    public static void animateHead(PrehistoricMob entity, ModelPart part, float netHeadYaw, float headPitch) {
        if (!entity.isMobEepy() && !entity.isMobSitting()) {
            part.xRot += headPitch * ((float) Math.PI / 180) / 2;
            part.yRot += netHeadYaw * ((float) Math.PI / 180) / 2;
        }
    }
}
