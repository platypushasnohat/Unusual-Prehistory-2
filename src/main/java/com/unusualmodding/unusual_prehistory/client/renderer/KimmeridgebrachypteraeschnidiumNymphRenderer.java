package com.unusualmodding.unusual_prehistory.client.renderer;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.KimmeridgebrachypteraeschnidiumNymphModel;
import com.unusualmodding.unusual_prehistory.entity.KimmeridgebrachypteraeschnidiumNymph;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumNymphRenderer extends MobRenderer<KimmeridgebrachypteraeschnidiumNymph, KimmeridgebrachypteraeschnidiumNymphModel<KimmeridgebrachypteraeschnidiumNymph>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/kimmeridgebrachypteraeschnidium/nymph.png");

    public KimmeridgebrachypteraeschnidiumNymphRenderer(EntityRendererProvider.Context context) {
        super(context, new KimmeridgebrachypteraeschnidiumNymphModel<>(context.bakeLayer(UP2EntityModelLayers.KIMMERIDGEBRACHYTERAESCHNIDIUM_NYMPH_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(KimmeridgebrachypteraeschnidiumNymph entity) {
        return TEXTURE;
    }
}