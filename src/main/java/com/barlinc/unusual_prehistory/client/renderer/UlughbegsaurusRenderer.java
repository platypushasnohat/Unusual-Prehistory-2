package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.UlughbegsaurusModel;
import com.barlinc.unusual_prehistory.entity.Ulughbegsaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class UlughbegsaurusRenderer extends MobRenderer<Ulughbegsaurus, UlughbegsaurusModel> {

    public UlughbegsaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new UlughbegsaurusModel(context.bakeLayer(UP2ModelLayers.ULUGHBEGSAURUS)), 0.95F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Ulughbegsaurus entity) {
        Ulughbegsaurus.UlughbegsaurusVariant variant = Ulughbegsaurus.UlughbegsaurusVariant.byId(entity.getVariant());
        return UnusualPrehistory2.modPrefix("textures/entity/ulughbegsaurus/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}