package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.ThylacineModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Thylacine;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ThylacineRenderer extends MobRenderer<Thylacine, ThylacineModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/thylacine/thylacine.png");

    public ThylacineRenderer(EntityRendererProvider.Context context) {
        super(context, new ThylacineModel(context.bakeLayer(UP2ModelLayers.THYLACINE)), 0.3F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Thylacine entity) {
        return TEXTURE;
    }
}
