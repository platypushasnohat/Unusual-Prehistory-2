package com.unusualmodding.unusual_prehistory.client.renderer;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.TalpanasModel;
import com.unusualmodding.unusual_prehistory.entity.Talpanas;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class TalpanasRenderer extends MobRenderer<Talpanas, TalpanasModel<Talpanas>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/talpanas.png");

    public TalpanasRenderer(EntityRendererProvider.Context context) {
        super(context, new TalpanasModel<>(context.bakeLayer(UP2EntityModelLayers.TALPANAS)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(Talpanas entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(Talpanas entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}
