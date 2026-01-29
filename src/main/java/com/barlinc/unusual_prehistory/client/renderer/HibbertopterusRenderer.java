package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.HibbertopterusModel;
import com.barlinc.unusual_prehistory.client.renderer.layers.HibbertopterusRiderLayer;
import com.barlinc.unusual_prehistory.entity.Hibbertopterus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class HibbertopterusRenderer extends MobRenderer<Hibbertopterus, HibbertopterusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/hibbertopterus.png");

    public HibbertopterusRenderer(EntityRendererProvider.Context context) {
        super(context, new HibbertopterusModel(context.bakeLayer(UP2ModelLayers.HIBBERTOPTERUS)), 1.2F);
        this.addLayer(new HibbertopterusRiderLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Hibbertopterus entity) {
        return TEXTURE;
    }
}