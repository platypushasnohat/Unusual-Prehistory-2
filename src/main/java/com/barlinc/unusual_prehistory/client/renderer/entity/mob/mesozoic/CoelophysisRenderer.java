package com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.CoelophysisModel;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Coelophysis;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CoelophysisRenderer extends MobRenderer<Coelophysis, CoelophysisModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/coelophysis.png");

    public CoelophysisRenderer(EntityRendererProvider.Context context) {
        super(context, new CoelophysisModel(context.bakeLayer(UP2ModelLayers.COELOPHYSIS)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Coelophysis entity) {
        return TEXTURE;
    }
}