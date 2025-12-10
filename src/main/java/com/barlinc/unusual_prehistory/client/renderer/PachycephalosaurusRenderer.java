package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.PachycephalosaurusModel;
import com.barlinc.unusual_prehistory.entity.Pachycephalosaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class PachycephalosaurusRenderer extends MobRenderer<Pachycephalosaurus, PachycephalosaurusModel> {

    public PachycephalosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new PachycephalosaurusModel(context.bakeLayer(UP2ModelLayers.PACHYCEPHALOSAURUS)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Pachycephalosaurus entity) {
        Pachycephalosaurus.PachycephalosaurusVariant variant = Pachycephalosaurus.PachycephalosaurusVariant.byId(entity.getVariant());
        return UnusualPrehistory2.modPrefix("textures/entity/pachycephalosaurus/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}
