package com.unusualmodding.unusual_prehistory.client.models.entity.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public abstract class UP2Model<E extends Entity> extends HierarchicalModel<E> {

    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    private final float youngScaleFactor;
    private final float bodyYOffset;

    public UP2Model() {
        this(RenderType::entityCutoutNoCull, 0.5F, 24.0F);
    }

    public UP2Model(float youngScaleFactor, float bodyYOffset) {
        this(RenderType::entityCutoutNoCull, bodyYOffset, youngScaleFactor);
    }

    public UP2Model(Function<ResourceLocation, RenderType> pRenderType, float youngScaleFactor, float bodyYOffset) {
        super(pRenderType);
        this.youngScaleFactor = youngScaleFactor;
        this.bodyYOffset = bodyYOffset;
    }

    protected void animateIdle(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks, float speed, float animationScaleFactor) {
        animationState.updateTime(ageInTicks, speed);
        animationState.ifStarted((state) -> KeyframeAnimations.animate(this, animationDefinition, state.getAccumulatedTime(), animationScaleFactor, ANIMATION_VECTOR_CACHE));
    }

    protected void animateIdle(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks, float speed) {
        float scale = Math.max(0, Math.min(1 - Math.abs(speed), 1.0F));
        animationState.updateTime(ageInTicks, speed);
        animationState.ifStarted((state) -> KeyframeAnimations.animate(this, animationDefinition, state.getAccumulatedTime(), scale, ANIMATION_VECTOR_CACHE));
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
        if (this.young) {
            poseStack.pushPose();
            poseStack.scale(this.youngScaleFactor, this.youngScaleFactor, this.youngScaleFactor);
            poseStack.translate(0.0F, this.bodyYOffset / 16.0F, 0.0F);
            this.root().render(poseStack, vertexConsumer, i, j, f, g, h, k);
            poseStack.popPose();
        } else {
            this.root().render(poseStack, vertexConsumer, i, j, f, g, h, k);
        }
    }
}