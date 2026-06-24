package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.PachyrhinosaurusModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Pachyrhinosaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PachyrhinosaurusRenderer extends MobRenderer<Pachyrhinosaurus, PachyrhinosaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/pachyrhinosaurus.png");

    public PachyrhinosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new PachyrhinosaurusModel(context.bakeLayer(UP2ModelLayers.PACHYRHINOSAURUS)), 1.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Pachyrhinosaurus entity) {
        return TEXTURE;
    }
}
