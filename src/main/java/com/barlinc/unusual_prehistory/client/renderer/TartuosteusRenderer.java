package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.TartuosteusModel;
import com.barlinc.unusual_prehistory.entity.Tartuosteus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class TartuosteusRenderer extends MobRenderer<Tartuosteus, TartuosteusModel> {

    public TartuosteusRenderer(EntityRendererProvider.Context context) {
        super(context, new TartuosteusModel(context.bakeLayer(UP2ModelLayers.TARTUOSTEUS)), 0.6F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Tartuosteus entity) {
        Tartuosteus.TartuosteusVariant variant = Tartuosteus.TartuosteusVariant.byId(entity.getVariant());
        return UnusualPrehistory2.modPrefix("textures/entity/tartuosteus/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}