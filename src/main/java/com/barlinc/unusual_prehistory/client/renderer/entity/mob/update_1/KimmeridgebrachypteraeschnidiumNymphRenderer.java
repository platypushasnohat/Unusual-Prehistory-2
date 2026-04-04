package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_1;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.KimmeridgebrachypteraeschnidiumNymphModel;
import com.barlinc.unusual_prehistory.entity.mob.update_1.KimmeridgebrachypteraeschnidiumNymph;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumNymphRenderer extends MobRenderer<KimmeridgebrachypteraeschnidiumNymph, KimmeridgebrachypteraeschnidiumNymphModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/kimmeridgebrachypteraeschnidium/nymph.png");

    public KimmeridgebrachypteraeschnidiumNymphRenderer(EntityRendererProvider.Context context) {
        super(context, new KimmeridgebrachypteraeschnidiumNymphModel(context.bakeLayer(UP2ModelLayers.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH)), 0.2F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull KimmeridgebrachypteraeschnidiumNymph entity) {
        return TEXTURE;
    }
}