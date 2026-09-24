package com.barl_inc.unusual_prehistory.client.render.entity;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.model.AmmoniteModel;
import com.barl_inc.unusual_prehistory.entity.cliff_fossil.Ammonite;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class AmmoniteRenderer extends MobRenderer<Ammonite, AmmoniteModel> {

    private final AmmoniteModel hoplitesModel;
    private final AmmoniteModel crioceratitesModel;
    private final AmmoniteModel nostocerasModel;
    private final AmmoniteModel pinacocerasModel;
    private final AmmoniteModel tropitesModel;

    public AmmoniteRenderer(EntityRendererProvider.Context context) {
        super(context, new AmmoniteModel(context.bakeLayer(UP2ModelLayers.AMMONITE_HOPLITES)), 0.25F);
        this.hoplitesModel = new AmmoniteModel(context.bakeLayer(UP2ModelLayers.AMMONITE_HOPLITES));
        this.crioceratitesModel = new AmmoniteModel(context.bakeLayer(UP2ModelLayers.AMMONITE_CRIOCERATITES));
        this.nostocerasModel = new AmmoniteModel(context.bakeLayer(UP2ModelLayers.AMMONITE_NOSTOCERAS));
        this.pinacocerasModel = new AmmoniteModel(context.bakeLayer(UP2ModelLayers.AMMONITE_PINACOCERAS));
        this.tropitesModel = new AmmoniteModel(context.bakeLayer(UP2ModelLayers.AMMONITE_TROPITES));
    }

    @Override
    public void render(Ammonite entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        Ammonite.AmmoniteVariant variant = entity.getVariant();
        switch (variant) {
            case Ammonite.AmmoniteVariant.AMMONITE_CRIOCERATITES:
                this.model = this.crioceratitesModel;
                break;
            case Ammonite.AmmoniteVariant.AMMONITE_NOSTOCERAS:
                this.model = this.nostocerasModel;
                break;
            case Ammonite.AmmoniteVariant.AMMONITE_PINACOCERAS:
                this.model = this.pinacocerasModel;
                break;
            case Ammonite.AmmoniteVariant.AMMONITE_TROPITES:
                this.model = this.tropitesModel;
                break;
            default:
                this.model = this.hoplitesModel;
                break;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    protected void setupRotations(Ammonite entity, PoseStack poseStack, float bob, float yBodyRot, float partialTicks, float scale) {
        super.setupRotations(entity, poseStack, bob, yBodyRot + 180.0F, partialTicks, scale);
    }

    @Override
    public ResourceLocation getTextureLocation(Ammonite entity) {
        Ammonite.AmmoniteVariant variant = entity.getVariant();
        return UnusualPrehistory2.location("textures/entity/ammonite/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}
