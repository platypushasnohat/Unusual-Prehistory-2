package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.CoelacanthusModel;
import com.barlinc.unusual_prehistory.entity.Coelacanthus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CoelacanthusRenderer extends MobRenderer<Coelacanthus, CoelacanthusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/coelacanthus.png");

    public CoelacanthusRenderer(EntityRendererProvider.Context context) {
        super(context, new CoelacanthusModel(context.bakeLayer(UP2ModelLayers.COELACANTHUS)), 0.25F);
    }

    @Override
    protected void scale(Coelacanthus entity, PoseStack poseStack, float partialTicks) {
        float scale = 1.0F + 0.1F * (float) entity.getCoelacanthusSize();
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Coelacanthus entity) {
        return TEXTURE;
    }
}
