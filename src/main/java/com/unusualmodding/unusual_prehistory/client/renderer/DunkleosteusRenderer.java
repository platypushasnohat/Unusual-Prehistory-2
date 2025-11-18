package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus.DunkleosteusLargeModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus.DunkleosteusMediumModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus.DunkleosteusSmallModel;
import com.unusualmodding.unusual_prehistory.entity.Dunkleosteus;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
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

@OnlyIn(Dist.CLIENT)
public class DunkleosteusRenderer extends MobRenderer<Dunkleosteus, HierarchicalModel<Dunkleosteus>> {

    private final DunkleosteusLargeModel dunkleosteusLargeModel;
    private final DunkleosteusMediumModel dunkleosteusMediumModel;
    private final DunkleosteusSmallModel dunkleosteusSmallModel;

    public DunkleosteusRenderer(EntityRendererProvider.Context context) {
        super(context, new DunkleosteusLargeModel(context.bakeLayer(UP2EntityModelLayers.DUNKLEOSTEUS_LARGE)), 0.5F);
        this.dunkleosteusLargeModel = new DunkleosteusLargeModel(context.bakeLayer(UP2EntityModelLayers.DUNKLEOSTEUS_LARGE));
        this.dunkleosteusMediumModel = new DunkleosteusMediumModel(context.bakeLayer(UP2EntityModelLayers.DUNKLEOSTEUS_MEDIUM));
        this.dunkleosteusSmallModel = new DunkleosteusSmallModel(context.bakeLayer(UP2EntityModelLayers.DUNKLEOSTEUS_SMALL));
    }

    @Override
    public void render(Dunkleosteus entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        switch (entity.getDunkSize()){
            case 1:
                this.model = dunkleosteusMediumModel;
                break;
            case 2:
                this.model = dunkleosteusLargeModel;
                break;
            default:
                this.model = dunkleosteusSmallModel;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Dunkleosteus entity) {
        return UnusualPrehistory2.modPrefix("textures/entity/dunkleosteus/dunkleosteus_" + entity.getVariantName() + ".png");
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Dunkleosteus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}
