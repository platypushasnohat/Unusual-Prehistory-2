package com.unusualmodding.unusual_prehistory.client.renderer;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
import com.unusualmodding.unusual_prehistory.client.models.entity.StethacanthusModel;
import com.unusualmodding.unusual_prehistory.entity.Stethacanthus;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class StethacanthusRenderer extends MobRenderer<Stethacanthus, StethacanthusModel> {

    private static final ResourceLocation STETHACANTHUS = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/stethacanthus.png");

    public StethacanthusRenderer(EntityRendererProvider.Context context) {
        super(context, new StethacanthusModel(context.bakeLayer(UP2EntityModelLayers.STETHACANTHUS)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Stethacanthus entity) {
        return STETHACANTHUS;
    }

    @Override
    protected @Nullable RenderType getRenderType(Stethacanthus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(STETHACANTHUS);
    }
}
