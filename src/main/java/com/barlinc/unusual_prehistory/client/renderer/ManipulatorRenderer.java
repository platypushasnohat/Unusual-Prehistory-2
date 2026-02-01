package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.manipulator.ManipulatorModel;
import com.barlinc.unusual_prehistory.client.renderer.layers.ManipulatorHeldItemLayer;
import com.barlinc.unusual_prehistory.entity.Manipulator;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ManipulatorRenderer extends MobRenderer<Manipulator, ManipulatorModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/manipulator/manipulator.png");

    public ManipulatorRenderer(EntityRendererProvider.Context context) {
        super(context, new ManipulatorModel(context.bakeLayer(UP2ModelLayers.MANIPULATOR)), 0.9F);
        this.addLayer(new ManipulatorHeldItemLayer(this, context.getItemInHandRenderer()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Manipulator entity) {
        return TEXTURE;
    }
}
