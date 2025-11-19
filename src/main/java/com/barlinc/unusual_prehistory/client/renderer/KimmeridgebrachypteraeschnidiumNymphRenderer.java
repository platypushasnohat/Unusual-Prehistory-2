package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.KimmeridgebrachypteraeschnidiumNymphModel;
import com.barlinc.unusual_prehistory.entity.KimmeridgebrachypteraeschnidiumNymph;
import com.barlinc.unusual_prehistory.registry.UP2EntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumNymphRenderer extends MobRenderer<KimmeridgebrachypteraeschnidiumNymph, KimmeridgebrachypteraeschnidiumNymphModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/kimmeridgebrachypteraeschnidium/nymph.png");

    public KimmeridgebrachypteraeschnidiumNymphRenderer(EntityRendererProvider.Context context) {
        super(context, new KimmeridgebrachypteraeschnidiumNymphModel(context.bakeLayer(UP2EntityModelLayers.KIMMERIDGEBRACHYTERAESCHNIDIUM_NYMPH)), 0.2f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull KimmeridgebrachypteraeschnidiumNymph entity) {
        return TEXTURE;
    }
}