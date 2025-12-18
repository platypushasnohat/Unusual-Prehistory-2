package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.LeptictidiumModel;
import com.barlinc.unusual_prehistory.entity.Leptictidium;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LeptictidiumRenderer extends MobRenderer<Leptictidium, LeptictidiumModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/leptictidium.png");

    public LeptictidiumRenderer(EntityRendererProvider.Context context) {
        super(context, new LeptictidiumModel(context.bakeLayer(UP2ModelLayers.LEPTICTIDIUM)), 0.3F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Leptictidium entity) {
        return TEXTURE;
    }
}
