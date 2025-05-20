package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.base.UP2Model;
import com.unusualmodding.unusual_prehistory.client.models.entity.jawless_fish.*;
import com.unusualmodding.unusual_prehistory.entity.JawlessFishEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class JawlessFishRenderer extends MobRenderer<JawlessFishEntity, UP2Model<JawlessFishEntity>> {

    private final CephalaspisModel<JawlessFishEntity> cephalaspisModel;
    private final DoryaspisModel<JawlessFishEntity> doryaspisModel;
    private final FurcacaudaModel<JawlessFishEntity> furcacaudaModel;
    private final SacabambaspisModel<JawlessFishEntity> sacabambaspisModel;

    public JawlessFishRenderer(EntityRendererProvider.Context context) {
        super(context, new CephalaspisModel<>(context.bakeLayer(UP2ModelLayers.CEPHALASPIS_LAYER)), 0.5F);
        this.cephalaspisModel = new CephalaspisModel<>(context.bakeLayer(UP2ModelLayers.CEPHALASPIS_LAYER));
        this.doryaspisModel = new DoryaspisModel<>(context.bakeLayer(UP2ModelLayers.DORYASPIS_LAYER));
        this.furcacaudaModel = new FurcacaudaModel<>(context.bakeLayer(UP2ModelLayers.FURACACAUDA_LAYER));
        this.sacabambaspisModel = new SacabambaspisModel<>(context.bakeLayer(UP2ModelLayers.SACABAMBASPIS_LAYER));
    }

    @Override
    public void render(JawlessFishEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        switch (entity.getVariant()){
            case 1:
                this.model = doryaspisModel;
                break;
            case 2:
                this.model = furcacaudaModel;
                break;
            case 3:
                this.model = sacabambaspisModel;
                break;
            default:
                this.model = cephalaspisModel;
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected void scale(JawlessFishEntity entity, PoseStack matrices, float amount) {
        if (entity.isBaby()) matrices.scale(0.5F, 0.5F, 0.5F);
        else super.scale(entity, matrices, amount);
    }

    @Override
    public ResourceLocation getTextureLocation(JawlessFishEntity entity) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/jawless_fish/" + entity.getVariantName() + ".png");
    }

    @Override
    protected @Nullable RenderType getRenderType(JawlessFishEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutout(new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/jawless_fish/" + entity.getVariantName() + ".png"));
    }
}
