package com.unusualmodding.unusual_prehistory.client.models.entity.base;

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

    public UP2Model() {
        this(RenderType::entityCutoutNoCull);
    }

    public UP2Model(Function<ResourceLocation, RenderType> pRenderType) {
        super(pRenderType);
    }

    protected void animateIdle(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks, float speed, float animationScaleFactor) {
        float scale = Math.max(0, Math.min(1 - Math.abs(speed), 1f));
        animationState.updateTime(ageInTicks, speed);
        animationState.ifStarted((state) -> KeyframeAnimations.animate(this, animationDefinition, state.getAccumulatedTime(), animationScaleFactor, ANIMATION_VECTOR_CACHE));
    }
}