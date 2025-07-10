package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.DromaeosaurusModel;
import com.unusualmodding.unusual_prehistory.entity.Dromaeosaurus;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class DromaeosaurusRenderer extends MobRenderer<Dromaeosaurus, DromaeosaurusModel<Dromaeosaurus>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/dromaeosaurus.png");

    public DromaeosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new DromaeosaurusModel<>(context.bakeLayer(UP2EntityModelLayers.DROMAEOSAURUS)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(Dromaeosaurus entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(Dromaeosaurus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }

    @Override
    protected void setupRotations(Dromaeosaurus entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
        if (entity.isDromaeosaurusVisuallySleeping() || entity.isInPoseTransition()) {
            poseStack.translate(0, -0.5, 0);
        }
    }
}