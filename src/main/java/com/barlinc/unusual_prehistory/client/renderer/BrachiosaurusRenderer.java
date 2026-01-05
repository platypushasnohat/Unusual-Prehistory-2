package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.BrachiosaurusModel;
import com.barlinc.unusual_prehistory.client.renderer.layers.BrachiosaurusRiderLayer;
import com.barlinc.unusual_prehistory.entity.Brachiosaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BrachiosaurusRenderer extends MobRenderer<Brachiosaurus, BrachiosaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/brachiosaurus.png");

    public BrachiosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new BrachiosaurusModel(context.bakeLayer(UP2ModelLayers.BRACHIOSAURUS)), 1.8F);
        this.addLayer(new BrachiosaurusRiderLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Brachiosaurus entity) {
        return TEXTURE;
    }
}
