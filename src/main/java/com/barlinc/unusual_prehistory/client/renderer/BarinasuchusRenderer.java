package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.BarinasuchusModel;
import com.barlinc.unusual_prehistory.entity.Barinasuchus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BarinasuchusRenderer extends MobRenderer<Barinasuchus, BarinasuchusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/barinasuchus/barinasuchus.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/barinasuchus/barinasuchus_eepy.png");

    public BarinasuchusRenderer(EntityRendererProvider.Context context) {
        super(context, new BarinasuchusModel(context.bakeLayer(UP2ModelLayers.BARINASUCHUS)), 0.9F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Barinasuchus entity) {
        return entity.isMobEepy() ? TEXTURE_EEPY : TEXTURE;
    }
}
