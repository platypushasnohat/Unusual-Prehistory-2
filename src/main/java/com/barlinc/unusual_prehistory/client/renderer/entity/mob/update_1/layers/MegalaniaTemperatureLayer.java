package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_1.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.MegalaniaModel;
import com.barlinc.unusual_prehistory.entity.mob.update_1.Megalania;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class MegalaniaTemperatureLayer extends RenderLayer<Megalania, MegalaniaModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_temperate.png");
    private static final ResourceLocation TEXTURE_COLD = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_cold.png");
    private static final ResourceLocation TEXTURE_WARM = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_warm.png");
    private static final ResourceLocation TEXTURE_NETHER = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_nether.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_temperate_eepy.png");
    private static final ResourceLocation TEXTURE_COLD_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_cold_eepy.png");
    private static final ResourceLocation TEXTURE_WARM_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_warm_eepy.png");
    private static final ResourceLocation TEXTURE_NETHER_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_nether_eepy.png");

    public MegalaniaTemperatureLayer(RenderLayerParent<Megalania, MegalaniaModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Megalania entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) return;
        Megalania.TemperatureStates prev = entity.getPrevTemperatureState();
        Megalania.TemperatureStates current = entity.getTemperatureState();
        if (prev == null || prev == current) return;
        float progress = entity.getTempProgress(partialTicks);
        float alpha = 1.0F - progress;
        if (alpha <= 0.0F) return;
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(this.getTexture(entity)));
        this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), (int) alpha);
    }

    public ResourceLocation getTexture(Megalania entity) {
        return switch (entity.getPrevTemperatureState()) {
            case COLD -> entity.isEepy() ? TEXTURE_COLD_EEPY : TEXTURE_COLD;
            case WARM -> entity.isEepy() ? TEXTURE_WARM_EEPY : TEXTURE_WARM;
            case NETHER -> entity.isEepy() ? TEXTURE_NETHER_EEPY : TEXTURE_NETHER;
            default -> entity.isEepy() ? TEXTURE_EEPY : TEXTURE;
        };
    }
}