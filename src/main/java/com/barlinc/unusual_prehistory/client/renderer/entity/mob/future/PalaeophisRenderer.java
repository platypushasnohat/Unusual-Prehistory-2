package com.barlinc.unusual_prehistory.client.renderer.entity.mob.future;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.future.PalaeophisModel;
import com.barlinc.unusual_prehistory.entity.mob.future.Palaeophis;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PalaeophisRenderer extends MobRenderer<Palaeophis, PalaeophisModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/palaeophis.png");

    public PalaeophisRenderer(EntityRendererProvider.Context context) {
        super(context, new PalaeophisModel(context.bakeLayer(UP2ModelLayers.PALAEOPHIS)), 0.1F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Palaeophis entity) {
        return TEXTURE;
    }
}