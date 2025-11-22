package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.KentrosaurusModel;
import com.barlinc.unusual_prehistory.entity.Kentrosaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class KentrosaurusRenderer extends MobRenderer<Kentrosaurus, KentrosaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/kentrosaurus.png");

    public KentrosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new KentrosaurusModel(context.bakeLayer(UP2ModelLayers.KENTROSAURUS)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Kentrosaurus entity) {
        return TEXTURE;
    }
}
