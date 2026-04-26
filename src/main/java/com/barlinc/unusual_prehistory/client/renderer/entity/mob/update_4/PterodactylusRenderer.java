package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_4;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_4.PterodactylusModel;
import com.barlinc.unusual_prehistory.entity.mob.update_4.Pterodactylus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class PterodactylusRenderer extends MobRenderer<Pterodactylus, PterodactylusModel> {

    public PterodactylusRenderer(EntityRendererProvider.Context context) {
        super(context, new PterodactylusModel(context.bakeLayer(UP2ModelLayers.PTERODACTYLUS)), 0.3F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Pterodactylus entity) {
        Pterodactylus.PterodactylusVariant variant = Pterodactylus.PterodactylusVariant.byId(entity.getVariant().getId());
        return UnusualPrehistory2.modPrefix("textures/entity/mob/pterodactylus/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}
