package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.TelecrexModel;
import com.barlinc.unusual_prehistory.entity.Telecrex;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TelecrexRenderer extends MobRenderer<Telecrex, TelecrexModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/telecrex.png");

    public TelecrexRenderer(EntityRendererProvider.Context context) {
        super(context, new TelecrexModel(context.bakeLayer(UP2ModelLayers.TELECREX)), 0.4F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Telecrex entity) {
        return TEXTURE;
    }
}
