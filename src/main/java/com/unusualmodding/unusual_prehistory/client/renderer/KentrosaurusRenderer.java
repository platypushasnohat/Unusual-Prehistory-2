package com.unusualmodding.unusual_prehistory.client.renderer;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.KentrosaurusModel;
import com.unusualmodding.unusual_prehistory.entity.Kentrosaurus;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class KentrosaurusRenderer extends MobRenderer<Kentrosaurus, KentrosaurusModel<Kentrosaurus>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/kentrosaurus.png");

    public KentrosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new KentrosaurusModel<>(context.bakeLayer(UP2EntityModelLayers.KENTROSAURUS)), 0.9F);
    }

    @Override
    public ResourceLocation getTextureLocation(Kentrosaurus entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(Kentrosaurus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}
