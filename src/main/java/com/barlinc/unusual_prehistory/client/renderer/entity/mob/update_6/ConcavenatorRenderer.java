package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.ConcavenatorModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Concavenator;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ConcavenatorRenderer extends MobRenderer<Concavenator, ConcavenatorModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/concavenator/concavenator.png");

    public ConcavenatorRenderer(EntityRendererProvider.Context context) {
        super(context, new ConcavenatorModel(context.bakeLayer(UP2ModelLayers.CONCAVENATOR)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Concavenator entity) {
        return TEXTURE;
    }
}
