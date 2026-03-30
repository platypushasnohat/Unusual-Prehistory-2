package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_3;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_3.MetriorhynchusModel;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_3.layers.MetriorhynchusHeldMobLayer;
import com.barlinc.unusual_prehistory.entity.mob.update_3.Metriorhynchus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class MetriorhynchusRenderer extends MobRenderer<Metriorhynchus, MetriorhynchusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/metriorhynchus.png");

    public MetriorhynchusRenderer(EntityRendererProvider.Context context) {
        super(context, new MetriorhynchusModel(context.bakeLayer(UP2ModelLayers.METRIORHYNCHUS)), 0.8F);
        this.addLayer(new MetriorhynchusHeldMobLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Metriorhynchus entity) {
        return TEXTURE;
    }
}