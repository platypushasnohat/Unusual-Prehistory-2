package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_4.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_4.Therizinosaurus;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TherizinosaurusEyesLayer extends RenderLayer<Therizinosaurus, UP2Model<Therizinosaurus>> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/therizinosaurus/therizinosaurus_eyes.png");

    public TherizinosaurusEyesLayer(RenderLayerParent<Therizinosaurus, UP2Model<Therizinosaurus>> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Therizinosaurus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible() || entity.isMobEepy()) return;
        VertexConsumer consumer = buffer.getBuffer(RenderType.eyes(TEXTURE));
        this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
    }
}