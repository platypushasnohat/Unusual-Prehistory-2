package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.GastricBroodingFrogModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.GastricBroodingFrog;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class GastricBroodingFrogRenderer extends MobRenderer<GastricBroodingFrog, GastricBroodingFrogModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/gastric_brooding_frog/temperate.png");

    public GastricBroodingFrogRenderer(EntityRendererProvider.Context context) {
        super(context, new GastricBroodingFrogModel(context.bakeLayer(UP2ModelLayers.GASTRIC_BROODING_FROG)), 0.3F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GastricBroodingFrog entity) {
        return TEXTURE;
    }
}
