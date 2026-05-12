package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.arthropleura.ArthropleuraHeadModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura.Arthropleura;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ArthropleuraRenderer extends MobRenderer<Arthropleura, ArthropleuraHeadModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/arthropleura/arthropleura.png");

    public ArthropleuraRenderer(EntityRendererProvider.Context context) {
        super(context, new ArthropleuraHeadModel(context.bakeLayer(UP2ModelLayers.ARTHROPLEURA_HEAD)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Arthropleura entity) {
        return TEXTURE;
    }
}
