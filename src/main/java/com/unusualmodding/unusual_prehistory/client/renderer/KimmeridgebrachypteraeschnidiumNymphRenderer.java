package com.unusualmodding.unusual_prehistory.client.renderer;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.KimmeridgebrachypteraeschnidiumNymphModel;
import com.unusualmodding.unusual_prehistory.entity.KimmeridgebrachypteraeschnidiumNymphEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumNymphRenderer extends MobRenderer<KimmeridgebrachypteraeschnidiumNymphEntity, KimmeridgebrachypteraeschnidiumNymphModel<KimmeridgebrachypteraeschnidiumNymphEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/kimmeridgebrachypteraeschnidium/nymph.png");

    public KimmeridgebrachypteraeschnidiumNymphRenderer(EntityRendererProvider.Context context) {
        super(context, new KimmeridgebrachypteraeschnidiumNymphModel<>(context.bakeLayer(UP2ModelLayers.KIMMERIDGEBRACHYTERAESCHNIDIUM_NYMPH_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(KimmeridgebrachypteraeschnidiumNymphEntity entity) {
        return TEXTURE;
    }
}