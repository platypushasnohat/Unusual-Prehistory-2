package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_4;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_4.CoelacanthusModel;
import com.barlinc.unusual_prehistory.entity.mob.update_4.Coelacanthus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class CoelacanthusRenderer extends MobRenderer<Coelacanthus, CoelacanthusModel> {

    public CoelacanthusRenderer(EntityRendererProvider.Context context) {
        super(context, new CoelacanthusModel(context.bakeLayer(UP2ModelLayers.COELACANTHUS)), 0.25F);
    }

    @Override
    protected void scale(Coelacanthus entity, PoseStack poseStack, float partialTicks) {
        float scale = 1.0F + 0.1F * (float) entity.getCoelacanthusSize();
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Coelacanthus entity) {
        Coelacanthus.CoelacanthusVariant variant = Coelacanthus.CoelacanthusVariant.byId(entity.getVariant());
        return UnusualPrehistory2.modPrefix("textures/entity/coelacanthus/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}
