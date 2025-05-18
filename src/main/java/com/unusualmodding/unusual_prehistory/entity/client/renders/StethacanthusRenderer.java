package com.unusualmodding.unusual_prehistory.entity.client.renders;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.client.UP2ModelLayers;
import com.unusualmodding.unusual_prehistory.entity.client.models.StethacanthusModel2;
import com.unusualmodding.unusual_prehistory.entity.custom.StethacanthusEntityTest;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class StethacanthusRenderer extends MobRenderer<StethacanthusEntityTest, StethacanthusModel2<StethacanthusEntityTest>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/stethacanthus.png");

    public StethacanthusRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new StethacanthusModel2<>(pContext.bakeLayer(UP2ModelLayers.STETHACANTHUS_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(StethacanthusEntityTest entity) {
        return TEXTURE;
    }
}
