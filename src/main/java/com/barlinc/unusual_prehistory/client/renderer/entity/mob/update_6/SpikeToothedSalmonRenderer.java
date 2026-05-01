package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.SpikeToothedSalmonModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.SpikeToothedSalmon;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class SpikeToothedSalmonRenderer extends MobRenderer<SpikeToothedSalmon, SpikeToothedSalmonModel> {

    private static final ResourceLocation ZOMBIE_TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/spike_toothed_salmon/zombie.png");

    public SpikeToothedSalmonRenderer(EntityRendererProvider.Context context) {
        super(context, new SpikeToothedSalmonModel(context.bakeLayer(UP2ModelLayers.SPIKE_TOOTHED_SALMON)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SpikeToothedSalmon entity) {
        if (entity.isZombie()) {
            return ZOMBIE_TEXTURE;
        } else {
            SpikeToothedSalmon.SpikeToothedSalmonVariant variant = SpikeToothedSalmon.SpikeToothedSalmonVariant.byId(entity.getVariant().getId());
            return UnusualPrehistory2.modPrefix("textures/entity/mob/spike_toothed_salmon/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
        }
    }

    @Override
    protected boolean isShaking(@NotNull SpikeToothedSalmon entity) {
        return super.isShaking(entity) || entity.isConverting();
    }
}
