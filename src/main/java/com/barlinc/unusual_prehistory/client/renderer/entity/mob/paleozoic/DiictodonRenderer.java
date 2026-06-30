package com.barlinc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic.DiictodonModel;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.Diictodon;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DiictodonRenderer extends MobRenderer<Diictodon, DiictodonModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/diictodon/diictodon.png");

    public DiictodonRenderer(EntityRendererProvider.Context context) {
        super(context, new DiictodonModel(context.bakeLayer(UP2ModelLayers.DIICTODON)), 0.25F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Diictodon entity) {
        return TEXTURE;
    }
}