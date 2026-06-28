package com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.BananogmiusModel;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Bananogmius;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BananogmiusRenderer extends MobRenderer<Bananogmius, BananogmiusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/bananogmius.png");

    public BananogmiusRenderer(EntityRendererProvider.Context context) {
        super(context, new BananogmiusModel(context.bakeLayer(UP2ModelLayers.BANANOGMIUS)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Bananogmius entity) {
        return TEXTURE;
    }
}
