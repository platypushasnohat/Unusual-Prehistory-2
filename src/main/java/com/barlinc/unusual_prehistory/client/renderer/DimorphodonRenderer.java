package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.DimorphodonModel;
import com.barlinc.unusual_prehistory.entity.Dimorphodon;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DimorphodonRenderer extends MobRenderer<Dimorphodon, DimorphodonModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/dimorphodon.png");

    public DimorphodonRenderer(EntityRendererProvider.Context context) {
        super(context, new DimorphodonModel(context.bakeLayer(UP2ModelLayers.DIMORPHODON)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Dimorphodon entity) {
        return TEXTURE;
    }
}
