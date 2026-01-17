package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.KaprosuchusModel;
import com.barlinc.unusual_prehistory.client.models.entity.PraepusaModel;
import com.barlinc.unusual_prehistory.entity.Kaprosuchus;
import com.barlinc.unusual_prehistory.entity.Praepusa;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class KaprosuchusRenderer extends MobRenderer<Kaprosuchus, KaprosuchusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/kaprosuchus/kaprosuchus.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/kaprosuchus/kaprosuchus_eepy.png");

    public KaprosuchusRenderer(EntityRendererProvider.Context context) {
        super(context, new KaprosuchusModel(context.bakeLayer(UP2ModelLayers.KAPROSUCHUS)), 0.6F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Kaprosuchus entity) {
        return entity.isMobEepy() ? TEXTURE_EEPY : TEXTURE;
    }
}