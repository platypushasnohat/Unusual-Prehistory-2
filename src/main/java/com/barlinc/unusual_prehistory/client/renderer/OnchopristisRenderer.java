package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.OnchopristisModel;
import com.barlinc.unusual_prehistory.entity.Onchopristis;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class OnchopristisRenderer extends MobRenderer<Onchopristis, OnchopristisModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/onchopristis.png");

    public OnchopristisRenderer(EntityRendererProvider.Context context) {
        super(context, new OnchopristisModel(context.bakeLayer(UP2ModelLayers.ONCHOPRISTIS)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Onchopristis entity) {
        return TEXTURE;
    }
}
