package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.TusoteuthisModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Tusoteuthis;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TusoteuthisRenderer extends MobRenderer<Tusoteuthis, TusoteuthisModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/tusoteuthis.png");

    public TusoteuthisRenderer(EntityRendererProvider.Context context) {
        super(context, new TusoteuthisModel(context.bakeLayer(UP2ModelLayers.TUSOTEUTHIS)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Tusoteuthis entity) {
        return TEXTURE;
    }
}
