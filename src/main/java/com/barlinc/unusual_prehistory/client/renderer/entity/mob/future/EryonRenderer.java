package com.barlinc.unusual_prehistory.client.renderer.entity.mob.future;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.future.EryonModel;
import com.barlinc.unusual_prehistory.entity.mob.future.Eryon;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class EryonRenderer extends MobRenderer<Eryon, EryonModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/eryon.png");

    public EryonRenderer(EntityRendererProvider.Context context) {
        super(context, new EryonModel(context.bakeLayer(UP2ModelLayers.ERYON)), 0.1F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Eryon entity) {
        return TEXTURE;
    }
}
