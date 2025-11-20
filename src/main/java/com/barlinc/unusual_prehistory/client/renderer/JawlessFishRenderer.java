package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.jawless_fish.*;
import com.barlinc.unusual_prehistory.entity.JawlessFish;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
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
        super(context, new CephalaspisModel(context.bakeLayer(UP2ModelLayers.CEPHALASPIS)), 0.3F);
        this.cephalaspisModel = new CephalaspisModel(context.bakeLayer(UP2ModelLayers.CEPHALASPIS));
        this.doryaspisModel = new DoryaspisModel(context.bakeLayer(UP2ModelLayers.DORYASPIS));
        this.furcacaudaModel = new FurcacaudaModel(context.bakeLayer(UP2ModelLayers.FURACACAUDA));
        this.sacabambaspisModel = new SacabambaspisModel(context.bakeLayer(UP2ModelLayers.SACABAMBASPIS));
        this.arandaspisModel = new ArandaspisModel(context.bakeLayer(UP2ModelLayers.ARANDASPIS));
    }

    @Override
    public void render(JawlessFish entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
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
    public @NotNull ResourceLocation getTextureLocation(JawlessFish entity) {
        JawlessFish.JawlessFishVariant variant = JawlessFish.JawlessFishVariant.byId(entity.getVariant());
        return UnusualPrehistory2.modPrefix("textures/entity/jawless_fish/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }

    @Override
    @Nullable
    protected RenderType getRenderType(@NotNull JawlessFish entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}
