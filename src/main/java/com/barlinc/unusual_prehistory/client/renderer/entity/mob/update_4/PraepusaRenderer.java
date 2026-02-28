package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_4;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_4.PraepusaModel;
import com.barlinc.unusual_prehistory.entity.mob.update_4.Praepusa;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PraepusaRenderer extends MobRenderer<Praepusa, PraepusaModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/praepusa.png");

    public PraepusaRenderer(EntityRendererProvider.Context context) {
        super(context, new PraepusaModel(context.bakeLayer(UP2ModelLayers.PRAEPUSA)), 0.4F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Praepusa entity) {
        return TEXTURE;
    }
}