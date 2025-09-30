package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.jawless_fish.*;
import com.unusualmodding.unusual_prehistory.entity.JawlessFish;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class JawlessFishRenderer extends MobRenderer<JawlessFish, HierarchicalModel<JawlessFish>> {

    private final CephalaspisModel cephalaspisModel;
    private final DoryaspisModel doryaspisModel;
    private final FurcacaudaModel furcacaudaModel;
    private final SacabambaspisModel sacabambaspisModel;
    private final ArandaspisModel arandaspisModel;

    public JawlessFishRenderer(EntityRendererProvider.Context context) {
        super(context, new CephalaspisModel(context.bakeLayer(UP2EntityModelLayers.CEPHALASPIS)), 0.3F);
        this.cephalaspisModel = new CephalaspisModel(context.bakeLayer(UP2EntityModelLayers.CEPHALASPIS));
        this.doryaspisModel = new DoryaspisModel(context.bakeLayer(UP2EntityModelLayers.DORYASPIS));
        this.furcacaudaModel = new FurcacaudaModel(context.bakeLayer(UP2EntityModelLayers.FURACACAUDA));
        this.sacabambaspisModel = new SacabambaspisModel(context.bakeLayer(UP2EntityModelLayers.SACABAMBASPIS));
        this.arandaspisModel = new ArandaspisModel(context.bakeLayer(UP2EntityModelLayers.ARANDASPIS));
    }

    @Override
    public void render(JawlessFish entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        switch (entity.getVariant()) {
            case 1:
                this.model = cephalaspisModel;
                break;
            case 2:
                this.model = doryaspisModel;
                break;
            case 3:
                this.model = furcacaudaModel;
                break;
            case 4:
                this.model = sacabambaspisModel;
                break;
            default:
                this.model = arandaspisModel;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(JawlessFish entity) {
        JawlessFish.JawlessFishVariant variant = JawlessFish.JawlessFishVariant.byId(entity.getVariant());
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/jawless_fish/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }

    @Override
    @Nullable
    protected RenderType getRenderType(JawlessFish entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        JawlessFish.JawlessFishVariant variant = JawlessFish.JawlessFishVariant.byId(entity.getVariant());
        return RenderType.entityCutoutNoCull(new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/jawless_fish/" + variant.name().toLowerCase(Locale.ROOT) + ".png"));
    }
}
