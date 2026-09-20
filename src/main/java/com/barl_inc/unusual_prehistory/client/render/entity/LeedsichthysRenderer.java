package com.barl_inc.unusual_prehistory.client.render.entity;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.model.LeedsichthysModel;
import com.barl_inc.unusual_prehistory.entity.cliff_fossil.Leedsichthys;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.platypushasnohat.sinew.client.model.entity.SinewEntityModel;
import com.platypushasnohat.sinew.client.render.entity.SinewMobRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LeedsichthysRenderer extends SinewMobRenderer<Leedsichthys, SinewEntityModel<Leedsichthys>> {

    private static final ResourceLocation TEXTURE_LOCATION = UnusualPrehistory2.location("textures/entity/leedsichthys/leedsichthys.png");
    private static final ResourceLocation BABY_TEXTURE_LOCATION = UnusualPrehistory2.location("textures/entity/leedsichthys/leedsichthys_baby.png");

    private final LeedsichthysModel adultModel;
    private final LeedsichthysModel babyModel;

    public LeedsichthysRenderer(EntityRendererProvider.Context context) {
        super(context, new LeedsichthysModel(context.bakeLayer(UP2ModelLayers.LEEDSICHTHYS)), 1.0F);
        this.adultModel = new LeedsichthysModel(context.bakeLayer(UP2ModelLayers.LEEDSICHTHYS));
        this.babyModel = new LeedsichthysModel(context.bakeLayer(UP2ModelLayers.LEEDSICHTHYS_BABY));
    }

    @Override
    public void render(Leedsichthys entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.model = entity.isBaby() ? this.babyModel : this.adultModel;
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Leedsichthys entity) {
        return entity.isBaby() ? BABY_TEXTURE_LOCATION : TEXTURE_LOCATION;
    }
}
