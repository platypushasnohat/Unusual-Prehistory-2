package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.arthropleura.ArthropleuraTailModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura.ArthropleuraBody;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ArthropleuraTailRenderer extends ArthropleuraRenderer<ArthropleuraBody, ArthropleuraTailModel<ArthropleuraBody>> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/arthropleura/arthropleura.png");

    public ArthropleuraTailRenderer(EntityRendererProvider.Context context) {
        super(context, new ArthropleuraTailModel<>(context.bakeLayer(UP2ModelLayers.ARTHROPLEURA_TAIL)));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ArthropleuraBody entity) {
        return TEXTURE;
    }
}
