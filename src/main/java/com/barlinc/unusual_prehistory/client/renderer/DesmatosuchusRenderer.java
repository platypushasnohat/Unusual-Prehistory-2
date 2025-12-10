package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.DesmatosuchusModel;
import com.barlinc.unusual_prehistory.entity.Desmatosuchus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DesmatosuchusRenderer extends MobRenderer<Desmatosuchus, DesmatosuchusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/desmatosuchus/desmatosuchus.png");

    public DesmatosuchusRenderer(EntityRendererProvider.Context context) {
        super(context, new DesmatosuchusModel(context.bakeLayer(UP2ModelLayers.DESMATOSUCHUS)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Desmatosuchus entity) {
        return TEXTURE;
    }
}
