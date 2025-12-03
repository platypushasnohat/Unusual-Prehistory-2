package com.barlinc.unusual_prehistory.client.renderer.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.LivingOozeModel;
import com.barlinc.unusual_prehistory.client.renderer.LivingOozeRenderer;
import com.barlinc.unusual_prehistory.entity.LivingOoze;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LivingOozeOuterLayer extends RenderLayer<LivingOoze, LivingOozeModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/living_ooze/normal.png");
    private static final ResourceLocation TEXTURE_SPITTING = UnusualPrehistory2.modPrefix("textures/entity/living_ooze/spitting.png");
    private static final ResourceLocation TEXTURE_COOLDOWN = UnusualPrehistory2.modPrefix("textures/entity/living_ooze/clarity.png");

    public LivingOozeOuterLayer(LivingOozeRenderer render) {
        super(render);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, @NotNull LivingOoze entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
        if (!entity.isInvisible()) {
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 0.9F);
        }
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull LivingOoze entity) {
        if (entity.getPose() == UP2Poses.SPITTING.get()) return TEXTURE_SPITTING;
        else if (entity.getCooldown() > 0) return TEXTURE_COOLDOWN;
        else return TEXTURE;
    }
}