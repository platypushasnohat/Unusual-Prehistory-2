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

@OnlyIn(Dist.CLIENT)
public class CarnotaurusRenderer extends MobRenderer<Carnotaurus, CarnotaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/carnotaurus.png");

    public CarnotaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new CarnotaurusModel(context.bakeLayer(UP2ModelLayers.CARNOTAURUS)), 0.8F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Carnotaurus entity) {
        return TEXTURE;
    }
}