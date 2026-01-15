package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.CarnotaurusModel;
import com.barlinc.unusual_prehistory.entity.Carnotaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class CarnotaurusRenderer extends MobRenderer<Carnotaurus, CarnotaurusModel> {

    public CarnotaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new CarnotaurusModel(context.bakeLayer(UP2ModelLayers.CARNOTAURUS)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Carnotaurus entity) {
        Carnotaurus.CarnotaurusVariant variant = Carnotaurus.CarnotaurusVariant.byId(entity.getVariant());
        if (entity.isMobEepy()) return UnusualPrehistory2.modPrefix("textures/entity/carnotaurus/" + variant.name().toLowerCase(Locale.ROOT) + "_eepy.png");
        return UnusualPrehistory2.modPrefix("textures/entity/carnotaurus/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}