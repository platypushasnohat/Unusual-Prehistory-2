package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.TalpanasModel;
import com.barlinc.unusual_prehistory.entity.Talpanas;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TalpanasRenderer extends MobRenderer<Talpanas, TalpanasModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/talpanas.png");

    public TalpanasRenderer(EntityRendererProvider.Context context) {
        super(context, new TalpanasModel(context.bakeLayer(UP2ModelLayers.TALPANAS)), 0.3F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Talpanas entity) {
        return TEXTURE;
    }
}
