package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.WonambiModel;
import com.barlinc.unusual_prehistory.entity.Wonambi;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class WonambiRenderer extends MobRenderer<Wonambi, WonambiModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/wonambi.png");

    public WonambiRenderer(EntityRendererProvider.Context context) {
        super(context, new WonambiModel(context.bakeLayer(UP2ModelLayers.WONAMBI)), 0.1F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Wonambi entity) {
        return TEXTURE;
    }
}