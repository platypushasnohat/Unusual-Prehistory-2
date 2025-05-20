package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.ScaumenaciaModel;
import com.unusualmodding.unusual_prehistory.entity.ScaumenaciaEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScaumenaciaRenderer extends MobRenderer<ScaumenaciaEntity, ScaumenaciaModel<ScaumenaciaEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/scaumenacia/scaumenacia.png");
    private static final ResourceLocation TEXTURE_GOLDEN = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/scaumenacia/scaumenacia_golden.png");

    public ScaumenaciaRenderer(EntityRendererProvider.Context context) {
        super(context, new ScaumenaciaModel<>(context.bakeLayer(UP2ModelLayers.SCAMENACIA_LAYER)), 0.4F);
    }

    @Override
    protected void scale(ScaumenaciaEntity entity, PoseStack matrices, float amount) {
        if (entity.isBaby()) matrices.scale(0.5F, 0.5F, 0.5F);
        else super.scale(entity, matrices, amount);
    }

    @Override
    public ResourceLocation getTextureLocation(ScaumenaciaEntity entity) {
        if (entity.getVariant() == 1) {
            return TEXTURE_GOLDEN;
        }
        return TEXTURE;
    }
}
