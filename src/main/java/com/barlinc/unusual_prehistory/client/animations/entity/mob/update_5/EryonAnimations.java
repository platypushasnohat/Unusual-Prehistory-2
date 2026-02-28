package com.barlinc.unusual_prehistory.client.animations.entity.mob.update_5;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EryonAnimations {

    public static final AnimationDefinition BLINK_BLEND = AnimationDefinition.Builder.withLength(1.0F)
            .addAnimation("eyes", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.4F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.9167F, KeyframeAnimations.scaleVec(1.4F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition IDLE_QUIRK_BLEND = AnimationDefinition.Builder.withLength(2.0F)
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, -15.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, -10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, -15.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, -10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0.0F, -15.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.5833F, KeyframeAnimations.degreeVec(0.0F, -15.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 15.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 15.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0.0F, 15.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.5833F, KeyframeAnimations.degreeVec(0.0F, 15.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -50.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, -40.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, -50.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, -45.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, -50.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, -50.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 10.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 50.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 50.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 45.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 50.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 50.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .build();

    public static final AnimationDefinition IDLE_EAT_BLEND = AnimationDefinition.Builder.withLength(3.0F)
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(4.63F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-4.81F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(4.81F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-4.63F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0833F, KeyframeAnimations.degreeVec(4.63F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-4.81F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(4.81F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-4.63F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-4.63F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(4.81F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-4.81F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(4.63F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-4.63F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.1667F, KeyframeAnimations.degreeVec(4.81F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-4.81F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.4167F, KeyframeAnimations.degreeVec(4.63F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 90.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 102.52F, -1.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 107.59F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 118.12F, -8.44F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 125.74F, -11.67F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0.0F, 122.06F, -14.06F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 120.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 20.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0833F, KeyframeAnimations.degreeVec(0.0F, 11.9F, -26.23F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.1667F, KeyframeAnimations.degreeVec(0.0F, 12.32F, -16.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 22.22F, -11.11F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(0.0F, 32.2F, -16.85F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.4167F, KeyframeAnimations.degreeVec(0.0F, 32.2F, -28.09F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 22.22F, -33.33F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.5833F, KeyframeAnimations.degreeVec(0.0F, 12.32F, -27.16F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.6667F, KeyframeAnimations.degreeVec(0.0F, 11.9F, -15.74F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.75F, KeyframeAnimations.degreeVec(0.0F, 20.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(-0.5F, -1.0F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.posVec(0.5F, -1.0F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.75F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -20.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-0.05F, -11.79F, 26.12F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-0.19F, -11.94F, 15.91F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-0.37F, -21.48F, 10.37F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-0.55F, -31.1F, 15.75F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-0.69F, -30.83F, 26.71F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-0.74F, -20.74F, 31.85F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-0.67F, -10.98F, 25.82F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-0.44F, -11.02F, 14.86F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, -20.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.0F, -20.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, -90.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0833F, KeyframeAnimations.degreeVec(0.0F, -102.52F, 1.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.1667F, KeyframeAnimations.degreeVec(0.0F, -107.59F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, -118.12F, 8.44F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.3333F, KeyframeAnimations.degreeVec(0.0F, -125.74F, 11.67F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.4167F, KeyframeAnimations.degreeVec(0.0F, -122.06F, 14.06F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, -120.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.posVec(0.5F, -1.0F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.5F, KeyframeAnimations.posVec(-0.5F, -1.0F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .build();

    public static final AnimationDefinition IDLE_OVERLAY_BLEND = AnimationDefinition.Builder.withLength(16.0F).looping()
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(2.17F, 2.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.625F, KeyframeAnimations.degreeVec(-2.17F, 0.65F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6458F, KeyframeAnimations.degreeVec(-2.17F, -0.65F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6875F, KeyframeAnimations.degreeVec(2.17F, -2.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7083F, KeyframeAnimations.degreeVec(2.17F, -2.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.75F, KeyframeAnimations.degreeVec(-2.17F, -0.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7708F, KeyframeAnimations.degreeVec(-2.17F, 0.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8125F, KeyframeAnimations.degreeVec(2.17F, 2.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8333F, KeyframeAnimations.degreeVec(2.17F, 2.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.875F, KeyframeAnimations.degreeVec(-2.17F, 0.65F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8958F, KeyframeAnimations.degreeVec(-2.17F, -0.65F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(0.0F, -1.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(-5.0F, -1.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6042F, KeyframeAnimations.degreeVec(-2.83F, -2.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.625F, KeyframeAnimations.degreeVec(-2.83F, -2.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6667F, KeyframeAnimations.degreeVec(-7.17F, -0.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6875F, KeyframeAnimations.degreeVec(-7.17F, 0.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7292F, KeyframeAnimations.degreeVec(-2.83F, 2.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.75F, KeyframeAnimations.degreeVec(-2.83F, 2.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7917F, KeyframeAnimations.degreeVec(-7.17F, 0.65F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8125F, KeyframeAnimations.degreeVec(-7.17F, -0.65F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8542F, KeyframeAnimations.degreeVec(-2.83F, -2.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.875F, KeyframeAnimations.degreeVec(-2.83F, -2.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(-7.17F, -0.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(7.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.15F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.15F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.15F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.15F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(-3.75F, -2.5F, -4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6042F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6458F, KeyframeAnimations.degreeVec(-6.25F, 4.33F, 1.29F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6667F, KeyframeAnimations.degreeVec(-7.5F, 5.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7083F, KeyframeAnimations.degreeVec(-3.75F, 2.5F, 4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7292F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7708F, KeyframeAnimations.degreeVec(-6.25F, -4.33F, -1.29F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7917F, KeyframeAnimations.degreeVec(-7.5F, -5.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8333F, KeyframeAnimations.degreeVec(-3.75F, -2.5F, -4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8542F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(-7.5F, 5.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(-7.5F, 5.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.625F, KeyframeAnimations.degreeVec(-3.75F, 2.5F, 4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6458F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6875F, KeyframeAnimations.degreeVec(-6.25F, -4.33F, -1.29F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7083F, KeyframeAnimations.degreeVec(-7.5F, -5.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.75F, KeyframeAnimations.degreeVec(-3.75F, -2.5F, -4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7708F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8125F, KeyframeAnimations.degreeVec(-6.25F, 4.33F, 1.29F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8333F, KeyframeAnimations.degreeVec(-7.5F, 5.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.875F, KeyframeAnimations.degreeVec(-3.75F, 2.5F, 4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8958F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(-3.75F, -2.5F, 1.29F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(-6.25F, -2.5F, -4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6042F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6458F, KeyframeAnimations.degreeVec(-3.75F, 4.33F, 1.29F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6667F, KeyframeAnimations.degreeVec(-2.5F, 5.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7083F, KeyframeAnimations.degreeVec(-6.25F, 2.5F, 4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7292F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7708F, KeyframeAnimations.degreeVec(-3.75F, -4.33F, -1.29F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7917F, KeyframeAnimations.degreeVec(-2.5F, -5.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8333F, KeyframeAnimations.degreeVec(-6.25F, -2.5F, -4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8542F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(-2.5F, 5.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(-2.5F, 5.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6042F, KeyframeAnimations.degreeVec(-3.75F, 4.33F, 4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6458F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7083F, KeyframeAnimations.degreeVec(-2.5F, -5.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.75F, KeyframeAnimations.degreeVec(-6.25F, -2.5F, -4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7708F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8125F, KeyframeAnimations.degreeVec(-3.75F, 4.33F, 1.29F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8333F, KeyframeAnimations.degreeVec(-2.5F, 5.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.875F, KeyframeAnimations.degreeVec(-6.25F, 2.5F, 4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8958F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(-6.25F, -2.5F, 1.29F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(-5.0F, 24.35F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6042F, KeyframeAnimations.degreeVec(-2.83F, 25.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.625F, KeyframeAnimations.degreeVec(-2.83F, 26.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6667F, KeyframeAnimations.degreeVec(-7.17F, 27.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6875F, KeyframeAnimations.degreeVec(-7.17F, 26.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7292F, KeyframeAnimations.degreeVec(-2.83F, 24.35F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.75F, KeyframeAnimations.degreeVec(-2.83F, 23.23F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7917F, KeyframeAnimations.degreeVec(-7.17F, 22.59F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8125F, KeyframeAnimations.degreeVec(-7.17F, 23.23F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8542F, KeyframeAnimations.degreeVec(-2.83F, 25.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.875F, KeyframeAnimations.degreeVec(-2.83F, 26.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8958F, KeyframeAnimations.degreeVec(-5.0F, 27.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(-7.17F, 27.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(-7.17F, 27.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6042F, KeyframeAnimations.degreeVec(-7.17F, 26.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6458F, KeyframeAnimations.degreeVec(-2.83F, 24.35F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6667F, KeyframeAnimations.degreeVec(-2.83F, 23.23F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7083F, KeyframeAnimations.degreeVec(-7.17F, 22.59F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7292F, KeyframeAnimations.degreeVec(-7.17F, 23.23F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7708F, KeyframeAnimations.degreeVec(-2.83F, 25.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7917F, KeyframeAnimations.degreeVec(-2.83F, 26.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8333F, KeyframeAnimations.degreeVec(-7.17F, 27.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8542F, KeyframeAnimations.degreeVec(-7.17F, 26.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8958F, KeyframeAnimations.degreeVec(-2.83F, 24.35F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(-2.83F, 23.23F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(-5.0F, -25.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6042F, KeyframeAnimations.degreeVec(-2.83F, -24.35F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.625F, KeyframeAnimations.degreeVec(-2.83F, -23.23F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6667F, KeyframeAnimations.degreeVec(-7.17F, -22.59F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6875F, KeyframeAnimations.degreeVec(-7.17F, -23.23F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7292F, KeyframeAnimations.degreeVec(-2.83F, -25.65F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.75F, KeyframeAnimations.degreeVec(-2.83F, -26.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7917F, KeyframeAnimations.degreeVec(-7.17F, -27.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8125F, KeyframeAnimations.degreeVec(-7.17F, -26.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8542F, KeyframeAnimations.degreeVec(-2.83F, -24.35F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.875F, KeyframeAnimations.degreeVec(-2.83F, -23.23F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8958F, KeyframeAnimations.degreeVec(-5.0F, -22.59F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(-7.17F, -22.59F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(-7.17F, -22.59F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6042F, KeyframeAnimations.degreeVec(-7.17F, -23.23F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6458F, KeyframeAnimations.degreeVec(-2.83F, -25.65F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6667F, KeyframeAnimations.degreeVec(-2.83F, -26.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7083F, KeyframeAnimations.degreeVec(-7.17F, -27.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7292F, KeyframeAnimations.degreeVec(-7.17F, -26.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7708F, KeyframeAnimations.degreeVec(-2.83F, -24.35F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7917F, KeyframeAnimations.degreeVec(-2.83F, -23.23F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8333F, KeyframeAnimations.degreeVec(-7.17F, -22.59F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8542F, KeyframeAnimations.degreeVec(-7.17F, -23.23F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8958F, KeyframeAnimations.degreeVec(-2.83F, -25.65F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(-2.83F, -26.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(0.0F, -0.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6042F, KeyframeAnimations.degreeVec(2.17F, 0.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.625F, KeyframeAnimations.degreeVec(2.17F, 1.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6667F, KeyframeAnimations.degreeVec(-2.17F, 2.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6875F, KeyframeAnimations.degreeVec(-2.17F, 1.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7292F, KeyframeAnimations.degreeVec(2.17F, -0.65F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.75F, KeyframeAnimations.degreeVec(2.17F, -1.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7917F, KeyframeAnimations.degreeVec(-2.17F, -2.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8125F, KeyframeAnimations.degreeVec(-2.17F, -1.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8542F, KeyframeAnimations.degreeVec(2.17F, 0.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.875F, KeyframeAnimations.degreeVec(2.17F, 1.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(-2.17F, 2.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(-2.17F, 2.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6042F, KeyframeAnimations.degreeVec(-2.17F, 1.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6458F, KeyframeAnimations.degreeVec(2.17F, -0.65F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6667F, KeyframeAnimations.degreeVec(2.17F, -1.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7083F, KeyframeAnimations.degreeVec(-2.17F, -2.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7292F, KeyframeAnimations.degreeVec(-2.17F, -1.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7708F, KeyframeAnimations.degreeVec(2.17F, 0.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7917F, KeyframeAnimations.degreeVec(2.17F, 1.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8333F, KeyframeAnimations.degreeVec(-2.17F, 2.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8542F, KeyframeAnimations.degreeVec(-2.17F, 1.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8958F, KeyframeAnimations.degreeVec(2.17F, -0.65F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(2.17F, -1.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(30.0F, 11.25F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6042F, KeyframeAnimations.degreeVec(20.0F, 17.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.625F, KeyframeAnimations.degreeVec(20.0F, 21.25F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6458F, KeyframeAnimations.degreeVec(20.0F, 22.41F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6667F, KeyframeAnimations.degreeVec(20.0F, 22.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6875F, KeyframeAnimations.degreeVec(20.0F, 22.41F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7083F, KeyframeAnimations.degreeVec(20.0F, 21.25F, -35.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7292F, KeyframeAnimations.degreeVec(20.0F, 17.5F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.75F, KeyframeAnimations.degreeVec(30.0F, 11.25F, -35.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7708F, KeyframeAnimations.degreeVec(37.32F, 5.09F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7917F, KeyframeAnimations.degreeVec(40.0F, 2.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8125F, KeyframeAnimations.degreeVec(37.32F, 5.09F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8333F, KeyframeAnimations.degreeVec(30.0F, 11.25F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8542F, KeyframeAnimations.degreeVec(20.0F, 17.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.875F, KeyframeAnimations.degreeVec(20.0F, 21.25F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8958F, KeyframeAnimations.degreeVec(20.0F, 22.41F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(20.0F, 22.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(40.0F, 7.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6042F, KeyframeAnimations.degreeVec(37.32F, 7.59F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.625F, KeyframeAnimations.degreeVec(30.0F, 8.75F, -35.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6458F, KeyframeAnimations.degreeVec(20.0F, 12.5F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6667F, KeyframeAnimations.degreeVec(20.0F, 18.75F, -35.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6875F, KeyframeAnimations.degreeVec(20.0F, 24.91F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7083F, KeyframeAnimations.degreeVec(20.0F, 27.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7292F, KeyframeAnimations.degreeVec(20.0F, 24.91F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.75F, KeyframeAnimations.degreeVec(20.0F, 18.75F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7708F, KeyframeAnimations.degreeVec(20.0F, 12.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7917F, KeyframeAnimations.degreeVec(30.0F, 8.75F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8125F, KeyframeAnimations.degreeVec(37.32F, 7.59F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8333F, KeyframeAnimations.degreeVec(40.0F, 7.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8542F, KeyframeAnimations.degreeVec(37.32F, 7.59F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.875F, KeyframeAnimations.degreeVec(30.0F, 8.75F, -35.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8958F, KeyframeAnimations.degreeVec(20.0F, 12.5F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(20.0F, 18.75F, -35.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(7.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.posVec(0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.posVec(0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(30.0F, -8.75F, 27.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6042F, KeyframeAnimations.degreeVec(20.0F, -12.5F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.625F, KeyframeAnimations.degreeVec(20.0F, -18.75F, 27.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6458F, KeyframeAnimations.degreeVec(20.0F, -24.91F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6667F, KeyframeAnimations.degreeVec(20.0F, -27.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6875F, KeyframeAnimations.degreeVec(20.0F, -24.91F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7083F, KeyframeAnimations.degreeVec(20.0F, -18.75F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7292F, KeyframeAnimations.degreeVec(20.0F, -12.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.75F, KeyframeAnimations.degreeVec(30.0F, -8.75F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7708F, KeyframeAnimations.degreeVec(37.32F, -7.59F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7917F, KeyframeAnimations.degreeVec(40.0F, -7.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8125F, KeyframeAnimations.degreeVec(37.32F, -7.59F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8333F, KeyframeAnimations.degreeVec(30.0F, -8.75F, 27.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8542F, KeyframeAnimations.degreeVec(20.0F, -12.5F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.875F, KeyframeAnimations.degreeVec(20.0F, -18.75F, 27.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8958F, KeyframeAnimations.degreeVec(20.0F, -24.91F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(20.0F, -27.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(40.0F, -2.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6042F, KeyframeAnimations.degreeVec(37.32F, -5.09F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.625F, KeyframeAnimations.degreeVec(30.0F, -11.25F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6458F, KeyframeAnimations.degreeVec(20.0F, -17.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6667F, KeyframeAnimations.degreeVec(20.0F, -21.25F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6875F, KeyframeAnimations.degreeVec(20.0F, -22.41F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7083F, KeyframeAnimations.degreeVec(20.0F, -22.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7292F, KeyframeAnimations.degreeVec(20.0F, -22.41F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.75F, KeyframeAnimations.degreeVec(20.0F, -21.25F, 27.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7708F, KeyframeAnimations.degreeVec(20.0F, -17.5F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7917F, KeyframeAnimations.degreeVec(30.0F, -11.25F, 27.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8125F, KeyframeAnimations.degreeVec(37.32F, -5.09F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8333F, KeyframeAnimations.degreeVec(40.0F, -2.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8542F, KeyframeAnimations.degreeVec(37.32F, -5.09F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.875F, KeyframeAnimations.degreeVec(30.0F, -11.25F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8958F, KeyframeAnimations.degreeVec(20.0F, -17.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(20.0F, -21.25F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(7.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.posVec(-0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.posVec(-0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(24.14F, 0.43F, -31.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6042F, KeyframeAnimations.degreeVec(29.32F, -4.32F, -17.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.625F, KeyframeAnimations.degreeVec(29.32F, -4.32F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6458F, KeyframeAnimations.degreeVec(24.14F, 0.43F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6667F, KeyframeAnimations.degreeVec(15.18F, 7.08F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6875F, KeyframeAnimations.degreeVec(10.0F, 12.25F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7083F, KeyframeAnimations.degreeVec(10.0F, 14.57F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7292F, KeyframeAnimations.degreeVec(10.0F, 14.99F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.75F, KeyframeAnimations.degreeVec(10.0F, 14.99F, -17.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7708F, KeyframeAnimations.degreeVec(10.0F, 14.57F, -31.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7917F, KeyframeAnimations.degreeVec(10.0F, 12.25F, -38.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8125F, KeyframeAnimations.degreeVec(15.18F, 7.08F, -38.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8333F, KeyframeAnimations.degreeVec(24.14F, 0.43F, -31.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8542F, KeyframeAnimations.degreeVec(29.32F, -4.32F, -17.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.875F, KeyframeAnimations.degreeVec(29.32F, -4.32F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8958F, KeyframeAnimations.degreeVec(24.14F, 0.43F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(15.18F, 7.08F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(10.0F, 7.92F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6042F, KeyframeAnimations.degreeVec(15.18F, 2.75F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.625F, KeyframeAnimations.degreeVec(24.14F, 0.43F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6458F, KeyframeAnimations.degreeVec(29.32F, 0.01F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6667F, KeyframeAnimations.degreeVec(29.32F, 0.01F, -17.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6875F, KeyframeAnimations.degreeVec(24.14F, 0.43F, -31.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7083F, KeyframeAnimations.degreeVec(15.18F, 2.75F, -38.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7292F, KeyframeAnimations.degreeVec(10.0F, 7.92F, -38.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.75F, KeyframeAnimations.degreeVec(10.0F, 14.57F, -31.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7708F, KeyframeAnimations.degreeVec(10.0F, 19.32F, -17.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7917F, KeyframeAnimations.degreeVec(10.0F, 19.32F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8125F, KeyframeAnimations.degreeVec(10.0F, 14.57F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8333F, KeyframeAnimations.degreeVec(10.0F, 7.92F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8542F, KeyframeAnimations.degreeVec(15.18F, 2.75F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.875F, KeyframeAnimations.degreeVec(24.14F, 0.43F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8958F, KeyframeAnimations.degreeVec(29.32F, 0.01F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(29.32F, 0.01F, -17.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(7.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.posVec(0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.posVec(0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(24.14F, -0.43F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6042F, KeyframeAnimations.degreeVec(29.32F, -0.01F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.625F, KeyframeAnimations.degreeVec(29.32F, -0.01F, 15.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6458F, KeyframeAnimations.degreeVec(24.14F, -0.43F, 24.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6667F, KeyframeAnimations.degreeVec(15.18F, -2.75F, 29.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6875F, KeyframeAnimations.degreeVec(10.0F, -7.92F, 29.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7083F, KeyframeAnimations.degreeVec(10.0F, -14.57F, 24.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7292F, KeyframeAnimations.degreeVec(10.0F, -19.32F, 15.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.75F, KeyframeAnimations.degreeVec(10.0F, -19.32F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7708F, KeyframeAnimations.degreeVec(8.75F, -11.34F, 12.17F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7917F, KeyframeAnimations.degreeVec(9.33F, -1.59F, 12.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8125F, KeyframeAnimations.degreeVec(13.24F, 5.78F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8333F, KeyframeAnimations.degreeVec(15.73F, 8.49F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8542F, KeyframeAnimations.degreeVec(10.99F, 7.03F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.875F, KeyframeAnimations.degreeVec(-0.17F, 3.75F, 11.29F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8958F, KeyframeAnimations.degreeVec(-5.73F, 1.04F, 11.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(-10.0F, 0.09F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(10.0F, -12.25F, 29.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6042F, KeyframeAnimations.degreeVec(15.18F, -7.08F, 29.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.625F, KeyframeAnimations.degreeVec(24.14F, -0.43F, 24.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6458F, KeyframeAnimations.degreeVec(29.32F, 4.32F, 15.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6667F, KeyframeAnimations.degreeVec(29.32F, 4.32F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6875F, KeyframeAnimations.degreeVec(24.14F, -0.43F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7083F, KeyframeAnimations.degreeVec(15.18F, -7.08F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7292F, KeyframeAnimations.degreeVec(10.0F, -12.25F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.75F, KeyframeAnimations.degreeVec(10.0F, -14.57F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7708F, KeyframeAnimations.degreeVec(10.0F, -14.99F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7917F, KeyframeAnimations.degreeVec(10.0F, -14.99F, 15.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8125F, KeyframeAnimations.degreeVec(10.0F, -14.57F, 24.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8333F, KeyframeAnimations.degreeVec(10.0F, -12.25F, 29.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8542F, KeyframeAnimations.degreeVec(15.18F, -7.08F, 29.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.875F, KeyframeAnimations.degreeVec(24.14F, -0.43F, 24.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8958F, KeyframeAnimations.degreeVec(29.32F, 4.32F, 15.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(29.32F, 4.32F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(7.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.posVec(-0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.posVec(-0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(-10.0F, -0.09F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6042F, KeyframeAnimations.degreeVec(-10.0F, -1.25F, -35.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.625F, KeyframeAnimations.degreeVec(-10.0F, -5.0F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6458F, KeyframeAnimations.degreeVec(0.0F, -11.25F, -35.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6667F, KeyframeAnimations.degreeVec(7.32F, -17.41F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6875F, KeyframeAnimations.degreeVec(10.0F, -20.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7083F, KeyframeAnimations.degreeVec(7.32F, -17.41F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7292F, KeyframeAnimations.degreeVec(0.0F, -11.25F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.75F, KeyframeAnimations.degreeVec(-10.0F, -5.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7708F, KeyframeAnimations.degreeVec(-10.0F, -1.25F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7917F, KeyframeAnimations.degreeVec(-10.0F, -0.09F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8125F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8333F, KeyframeAnimations.degreeVec(-10.0F, -0.09F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8542F, KeyframeAnimations.degreeVec(-10.0F, -1.25F, -35.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.875F, KeyframeAnimations.degreeVec(-10.0F, -5.0F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8958F, KeyframeAnimations.degreeVec(0.0F, -11.25F, -35.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(7.32F, -17.41F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(-10.0F, 2.41F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6042F, KeyframeAnimations.degreeVec(-10.0F, 5.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.625F, KeyframeAnimations.degreeVec(-10.0F, 2.41F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6458F, KeyframeAnimations.degreeVec(-10.0F, -3.75F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6667F, KeyframeAnimations.degreeVec(-10.0F, -10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6875F, KeyframeAnimations.degreeVec(0.0F, -13.75F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7083F, KeyframeAnimations.degreeVec(7.32F, -14.91F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7292F, KeyframeAnimations.degreeVec(10.0F, -15.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.75F, KeyframeAnimations.degreeVec(7.32F, -14.91F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7708F, KeyframeAnimations.degreeVec(0.0F, -13.75F, -35.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7917F, KeyframeAnimations.degreeVec(-10.0F, -10.0F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8125F, KeyframeAnimations.degreeVec(-10.0F, -3.75F, -35.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8333F, KeyframeAnimations.degreeVec(-10.0F, 2.41F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8542F, KeyframeAnimations.degreeVec(-10.0F, 5.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.875F, KeyframeAnimations.degreeVec(-10.0F, 2.41F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8958F, KeyframeAnimations.degreeVec(-10.0F, -3.75F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(-10.0F, -10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(7.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.posVec(0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.posVec(0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(-10.0F, -2.41F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6042F, KeyframeAnimations.degreeVec(-10.0F, 3.75F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.625F, KeyframeAnimations.degreeVec(-10.0F, 10.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6458F, KeyframeAnimations.degreeVec(0.0F, 13.75F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6667F, KeyframeAnimations.degreeVec(7.32F, 14.91F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6875F, KeyframeAnimations.degreeVec(10.0F, 15.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7083F, KeyframeAnimations.degreeVec(7.32F, 14.91F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7292F, KeyframeAnimations.degreeVec(0.0F, 13.75F, 27.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.75F, KeyframeAnimations.degreeVec(-10.0F, 10.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7708F, KeyframeAnimations.degreeVec(-10.0F, 3.75F, 27.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7917F, KeyframeAnimations.degreeVec(-10.0F, -2.41F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8125F, KeyframeAnimations.degreeVec(-10.0F, -5.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8333F, KeyframeAnimations.degreeVec(-10.0F, -2.41F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8542F, KeyframeAnimations.degreeVec(-10.0F, 3.75F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.875F, KeyframeAnimations.degreeVec(-10.0F, 10.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8958F, KeyframeAnimations.degreeVec(0.0F, 13.75F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(7.32F, 14.91F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(-10.0F, 0.09F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6042F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.625F, KeyframeAnimations.degreeVec(-10.0F, 0.09F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6458F, KeyframeAnimations.degreeVec(-10.0F, 1.25F, 27.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6667F, KeyframeAnimations.degreeVec(-10.0F, 5.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6875F, KeyframeAnimations.degreeVec(0.0F, 11.25F, 27.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7083F, KeyframeAnimations.degreeVec(7.32F, 17.41F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7292F, KeyframeAnimations.degreeVec(10.0F, 20.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.75F, KeyframeAnimations.degreeVec(7.32F, 17.41F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7708F, KeyframeAnimations.degreeVec(0.0F, 11.25F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7917F, KeyframeAnimations.degreeVec(-10.0F, 5.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8125F, KeyframeAnimations.degreeVec(-10.0F, 1.25F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8333F, KeyframeAnimations.degreeVec(-10.0F, 0.09F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8542F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.875F, KeyframeAnimations.degreeVec(-10.0F, 0.09F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8958F, KeyframeAnimations.degreeVec(-10.0F, 1.25F, 27.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(-10.0F, 5.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(7.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.posVec(-0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.posVec(-0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(-20.0F, -10.25F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6042F, KeyframeAnimations.degreeVec(-20.0F, -7.93F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.625F, KeyframeAnimations.degreeVec(-20.0F, -7.51F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6458F, KeyframeAnimations.degreeVec(-20.0F, -7.51F, -17.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6667F, KeyframeAnimations.degreeVec(-20.0F, -7.93F, -31.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6875F, KeyframeAnimations.degreeVec(-20.0F, -10.25F, -38.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7083F, KeyframeAnimations.degreeVec(-14.82F, -15.42F, -38.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7292F, KeyframeAnimations.degreeVec(-5.86F, -22.07F, -31.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.75F, KeyframeAnimations.degreeVec(-0.68F, -26.82F, -17.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7708F, KeyframeAnimations.degreeVec(-0.68F, -26.82F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7917F, KeyframeAnimations.degreeVec(-5.86F, -22.07F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8125F, KeyframeAnimations.degreeVec(-14.82F, -15.42F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8333F, KeyframeAnimations.degreeVec(-20.0F, -10.25F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8542F, KeyframeAnimations.degreeVec(-20.0F, -7.93F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.875F, KeyframeAnimations.degreeVec(-20.0F, -7.51F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8958F, KeyframeAnimations.degreeVec(-20.0F, -7.51F, -17.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(-20.0F, -7.93F, -31.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(-5.86F, -22.07F, -31.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6042F, KeyframeAnimations.degreeVec(-14.82F, -19.75F, -38.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.625F, KeyframeAnimations.degreeVec(-20.0F, -14.58F, -38.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6458F, KeyframeAnimations.degreeVec(-20.0F, -7.93F, -31.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6667F, KeyframeAnimations.degreeVec(-20.0F, -3.18F, -17.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6875F, KeyframeAnimations.degreeVec(-20.0F, -3.18F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7083F, KeyframeAnimations.degreeVec(-20.0F, -7.93F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7292F, KeyframeAnimations.degreeVec(-20.0F, -14.58F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.75F, KeyframeAnimations.degreeVec(-14.82F, -19.75F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7708F, KeyframeAnimations.degreeVec(-5.86F, -22.07F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7917F, KeyframeAnimations.degreeVec(-0.68F, -22.49F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8125F, KeyframeAnimations.degreeVec(-0.68F, -22.49F, -17.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8333F, KeyframeAnimations.degreeVec(-5.86F, -22.07F, -31.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8542F, KeyframeAnimations.degreeVec(-14.82F, -19.75F, -38.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.875F, KeyframeAnimations.degreeVec(-20.0F, -14.58F, -38.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8958F, KeyframeAnimations.degreeVec(-20.0F, -7.93F, -31.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(-20.0F, -3.18F, -17.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(7.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.posVec(0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.posVec(0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.5833F, KeyframeAnimations.degreeVec(-20.0F, 14.58F, 29.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6042F, KeyframeAnimations.degreeVec(-20.0F, 7.93F, 24.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.625F, KeyframeAnimations.degreeVec(-20.0F, 3.18F, 15.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6458F, KeyframeAnimations.degreeVec(-20.0F, 3.18F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6667F, KeyframeAnimations.degreeVec(-20.0F, 7.93F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.6875F, KeyframeAnimations.degreeVec(-20.0F, 14.58F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7083F, KeyframeAnimations.degreeVec(-14.82F, 19.75F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7292F, KeyframeAnimations.degreeVec(-5.86F, 22.07F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.75F, KeyframeAnimations.degreeVec(-0.68F, 22.49F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7708F, KeyframeAnimations.degreeVec(-0.68F, 22.49F, 15.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.7917F, KeyframeAnimations.degreeVec(-5.86F, 22.07F, 24.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8125F, KeyframeAnimations.degreeVec(-14.82F, 19.75F, 29.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8333F, KeyframeAnimations.degreeVec(-20.0F, 14.58F, 29.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8542F, KeyframeAnimations.degreeVec(-20.0F, 7.93F, 24.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.875F, KeyframeAnimations.degreeVec(-20.0F, 3.18F, 15.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.8958F, KeyframeAnimations.degreeVec(-20.0F, 3.18F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.degreeVec(-20.0F, 7.93F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5833F, KeyframeAnimations.degreeVec(-5.86F, 22.07F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6042F, KeyframeAnimations.degreeVec(-14.82F, 15.42F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.625F, KeyframeAnimations.degreeVec(-20.0F, 10.25F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6458F, KeyframeAnimations.degreeVec(-20.0F, 7.93F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6667F, KeyframeAnimations.degreeVec(-20.0F, 7.51F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.6875F, KeyframeAnimations.degreeVec(-20.0F, 7.51F, 15.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7083F, KeyframeAnimations.degreeVec(-20.0F, 7.93F, 24.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7292F, KeyframeAnimations.degreeVec(-20.0F, 10.25F, 29.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.75F, KeyframeAnimations.degreeVec(-14.82F, 15.42F, 29.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7708F, KeyframeAnimations.degreeVec(-5.86F, 22.07F, 24.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.7917F, KeyframeAnimations.degreeVec(-0.68F, 26.82F, 15.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8125F, KeyframeAnimations.degreeVec(-0.68F, 26.82F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8333F, KeyframeAnimations.degreeVec(-5.86F, 22.07F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8542F, KeyframeAnimations.degreeVec(-14.82F, 15.42F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.875F, KeyframeAnimations.degreeVec(-20.0F, 10.25F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.8958F, KeyframeAnimations.degreeVec(-20.0F, 7.93F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.degreeVec(-20.0F, 7.51F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(7.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(7.9167F, KeyframeAnimations.posVec(-0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(15.9167F, KeyframeAnimations.posVec(-0.1F, -0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(16.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body_main", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(2.0F).looping()
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-3.7F, 0.0F, 2.17F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-6.5F, 0.0F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-3.5F, 0.0F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-6.3F, 0.0F, -2.17F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.15F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(4.83F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(3.54F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-4.83F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-3.54F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-4.83F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-3.54F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.4167F, KeyframeAnimations.degreeVec(4.83F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.75F, KeyframeAnimations.degreeVec(3.54F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.6F, 5.0F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-2.6F, 5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(2.6F, 5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(2.6F, 5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-2.6F, 5.0F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-2.6F, 5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.5F, KeyframeAnimations.degreeVec(2.6F, 5.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(2.6F, 5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-2.6F, 5.0F, -4.33F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.6F, -5.0F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-2.6F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(2.6F, -5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(2.6F, -5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-2.6F, -5.0F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-2.6F, -5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.5F, KeyframeAnimations.degreeVec(2.6F, -5.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.6667F, KeyframeAnimations.degreeVec(2.6F, -5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-2.6F, -5.0F, -4.33F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.17F, 0.0F, -2.17F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-7.17F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.83F, 0.0F, -1.25F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.5F, KeyframeAnimations.degreeVec(-2.83F, 0.0F, 1.25F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-7.17F, 0.0F, -2.17F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, 11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, 8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, 11.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, 8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, -11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, -8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, -11.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, -8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.1F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, 7.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-10.0F, 7.5F, 11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-10.0F, 7.5F, 8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-10.0F, 7.5F, 11.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-10.0F, 7.5F, 8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-10.0F, 7.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.13F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, -7.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-10.0F, -7.5F, -11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-10.0F, -7.5F, -8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.25F, KeyframeAnimations.degreeVec(-10.0F, -7.5F, -11.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.75F, KeyframeAnimations.degreeVec(-10.0F, -7.5F, -8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(-10.0F, -7.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.12F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, -7.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(10.0F, -7.5F, 11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(10.0F, -7.5F, 8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.25F, KeyframeAnimations.degreeVec(10.0F, -7.5F, 11.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.75F, KeyframeAnimations.degreeVec(10.0F, -7.5F, 8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(10.0F, -7.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.14F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 7.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(10.0F, 7.5F, -11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(10.0F, 7.5F, -8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.25F, KeyframeAnimations.degreeVec(10.0F, 7.5F, -11.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.75F, KeyframeAnimations.degreeVec(10.0F, 7.5F, -8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(10.0F, 7.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.14F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, -15.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(20.0F, -15.0F, 11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(20.0F, -15.0F, 8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.25F, KeyframeAnimations.degreeVec(20.0F, -15.0F, 11.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.75F, KeyframeAnimations.degreeVec(20.0F, -15.0F, 8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(20.0F, -15.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.15F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 15.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(20.0F, 15.0F, -11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(20.0F, 15.0F, -8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.25F, KeyframeAnimations.degreeVec(20.0F, 15.0F, -11.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.75F, KeyframeAnimations.degreeVec(20.0F, 15.0F, -8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(2.0F, KeyframeAnimations.degreeVec(20.0F, 15.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.15F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .build();

    public static final AnimationDefinition FLOAT = AnimationDefinition.Builder.withLength(1.0F).looping()
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.15F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(4.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-4.83F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-3.54F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(4.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-4.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(4.83F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(3.54F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-4.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(19.33F, -15.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(15.0F, -15.0F, -31.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(10.17F, -15.0F, -28.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(11.46F, -15.0F, -30.75F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(16.29F, -15.0F, -30.75F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.875F, KeyframeAnimations.degreeVec(19.83F, -15.0F, -28.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(19.33F, -15.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(19.33F, 15.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(15.0F, 15.0F, 28.7F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(10.17F, 15.0F, 31.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5417F, KeyframeAnimations.degreeVec(11.46F, 15.0F, 29.25F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(16.29F, 15.0F, 29.25F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.875F, KeyframeAnimations.degreeVec(19.83F, 15.0F, 31.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(19.33F, 15.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-87.99F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-62.01F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-62.01F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-87.99F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 15.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(20.0F, 15.0F, 15.17F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(20.0F, 15.0F, 16.46F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(20.0F, 15.0F, 24.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.875F, KeyframeAnimations.degreeVec(20.0F, 15.0F, 23.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(20.0F, 15.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, -15.0F, -20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(20.0F, -15.0F, -15.17F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(20.0F, -15.0F, -16.46F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(20.0F, -15.0F, -24.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.875F, KeyframeAnimations.degreeVec(20.0F, -15.0F, -23.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(20.0F, -15.0F, -20.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 7.5F, 28.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(10.0F, 7.5F, 20.17F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, 7.5F, 21.46F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(10.0F, 7.5F, 29.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(10.0F, 7.5F, 28.54F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, -7.5F, -28.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(10.0F, -7.5F, -20.17F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, -7.5F, -21.46F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.8333F, KeyframeAnimations.degreeVec(10.0F, -7.5F, -29.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(10.0F, -7.5F, -28.54F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, -7.5F, 35.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-10.0F, -7.5F, 25.17F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-10.0F, -7.5F, 35.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, 7.5F, -35.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-10.0F, 7.5F, -25.17F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-10.0F, 7.5F, -35.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, 38.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, 39.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.625F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, 38.54F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, -38.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, -39.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.625F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, -38.54F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body_main", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-15.17F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-16.46F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-24.83F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.875F, KeyframeAnimations.degreeVec(-23.54F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body_main", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 1.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .build();

    public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(0.5F).looping()
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 1.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-2.83F, 2.41F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-7.17F, -0.65F, 2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-2.83F, -2.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-7.17F, -0.65F, -2.41F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-5.0F, 1.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, -5.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-6.25F, -4.33F, -4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-7.5F, 5.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.5F, -5.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.5F, -5.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-3.75F, -4.33F, -4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-2.5F, 5.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.5F, -5.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.17F, 27.59F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-2.83F, 31.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-7.17F, 31.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-2.83F, 28.23F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.17F, 27.59F, -0.65F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.17F, -32.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-2.83F, -28.23F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-7.17F, -28.23F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-2.83F, -31.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.17F, -32.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.17F, -2.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-2.83F, 1.77F, -1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-7.17F, 1.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-2.83F, -1.77F, 1.77F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.17F, -2.41F, -0.65F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 27.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(0.0F, 24.91F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(10.0F, 8.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(17.32F, 7.59F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(20.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(17.32F, 7.59F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(10.0F, 8.75F, -25.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 12.5F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 18.75F, -25.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.0F, 24.91F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 27.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, -7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(17.32F, -7.59F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(10.0F, -8.75F, 17.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, -18.75F, 17.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0.0F, -24.91F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -27.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, -24.91F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(10.0F, -8.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(17.32F, -7.59F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(20.0F, -7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.18F, 2.75F, -28.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(0.0F, 7.92F, -28.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, 14.57F, -21.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 19.32F, -7.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 19.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 7.92F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(5.18F, 2.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(14.14F, 0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(19.32F, 0.01F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(19.32F, 0.01F, -7.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(14.14F, 0.43F, -21.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.18F, 2.75F, -28.98F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -7.92F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(5.18F, -2.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(14.14F, -0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(19.32F, -0.01F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(19.32F, -0.01F, 5.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(14.14F, -0.43F, 14.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(5.18F, -2.75F, 19.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, -7.92F, 19.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, -14.57F, 14.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -19.32F, 5.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, -19.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -7.92F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.32F, -14.91F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(20.0F, -15.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(17.32F, -14.91F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(10.0F, -13.75F, -25.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, -10.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0.0F, -3.75F, -25.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 2.41F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 2.41F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(10.0F, -13.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(17.32F, -14.91F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -2.41F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(0.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, -2.41F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(10.0F, 13.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(17.32F, 14.91F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(20.0F, 15.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(17.32F, 14.91F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(10.0F, 13.75F, 17.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.0F, 3.75F, 17.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -2.41F, 10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -7.93F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(0.0F, -14.58F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(5.18F, -19.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(14.14F, -22.07F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(19.32F, -22.49F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(19.32F, -22.49F, -7.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(14.14F, -22.07F, -21.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(5.18F, -19.75F, -28.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, -14.58F, -28.98F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -7.93F, -21.21F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, -3.18F, -7.76F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.0F, -3.18F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -7.93F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(14.14F, 22.07F, 14.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(5.18F, 19.75F, 19.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, 14.58F, 19.32F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 7.93F, 14.14F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 3.18F, 5.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0.0F, 3.18F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 14.58F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5.18F, 19.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(14.14F, 22.07F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(19.32F, 22.49F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(19.32F, 22.49F, 5.18F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(14.14F, 22.07F, 14.14F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .build();

    public static final AnimationDefinition ESCAPE = AnimationDefinition.Builder.withLength(0.5F).looping()
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, 5.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(7.5F, 4.33F, 4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(5.0F, -5.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(15.0F, 0.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.0F, 5.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.0F, -5.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(12.5F, -4.33F, -4.83F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(5.0F, 0.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(15.0F, 5.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 3.54F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(15.0F, -5.0F, -3.54F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, 27.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(5.0F, 30.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(5.0F, 20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(5.0F, -7.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(5.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.0F, 27.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, -27.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(5.0F, -30.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(5.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(5.0F, 7.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(5.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.0F, -27.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-115.89F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-117.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-115.89F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(-108.75F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-92.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-46.61F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-46.61F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-92.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-108.75F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-115.89F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-30.0F, 5.0F, 75.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-25.67F, 5.0F, 72.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-25.67F, 5.0F, 67.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-30.0F, 5.0F, 65.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-34.33F, 5.0F, 67.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-34.33F, 5.0F, 72.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-30.0F, 5.0F, 75.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-30.0F, -5.0F, -75.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-25.67F, -5.0F, -72.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-25.67F, -5.0F, -67.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-30.0F, -5.0F, -65.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-34.33F, -5.0F, -67.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-34.33F, -5.0F, -72.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-30.0F, -5.0F, -75.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-34.33F, 5.0F, 82.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-32.5F, 5.0F, 84.33F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(-27.5F, 5.0F, 84.33F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-25.0F, 5.0F, 80.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-27.5F, 5.0F, 75.67F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-32.5F, 5.0F, 75.67F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-35.0F, 5.0F, 80.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-34.33F, 5.0F, 82.5F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-34.33F, -5.0F, -82.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-32.5F, -5.0F, -84.33F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(-27.5F, -5.0F, -84.33F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-25.0F, -5.0F, -80.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-27.5F, -5.0F, -75.67F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-32.5F, -5.0F, -75.67F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-35.0F, -5.0F, -80.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-34.33F, -5.0F, -82.5F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-34.33F, 5.0F, 87.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-35.0F, 5.0F, 90.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(-32.5F, 5.0F, 94.33F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-27.5F, 5.0F, 94.33F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-25.0F, 5.0F, 90.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-27.5F, 5.0F, 85.67F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-32.5F, 5.0F, 85.67F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-34.33F, 5.0F, 87.5F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-34.33F, -5.0F, -87.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-35.0F, -5.0F, -90.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(-32.5F, -5.0F, -94.33F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-27.5F, -5.0F, -94.33F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-25.0F, -5.0F, -90.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-27.5F, -5.0F, -85.67F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-32.5F, -5.0F, -85.67F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-34.33F, -5.0F, -87.5F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-30.0F, 5.0F, 95.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-34.33F, 5.0F, 97.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-34.33F, 5.0F, 102.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-30.0F, 5.0F, 105.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-25.67F, 5.0F, 102.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-25.67F, 5.0F, 97.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-30.0F, 5.0F, 95.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-30.0F, -5.0F, -95.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-34.33F, -5.0F, -97.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-34.33F, -5.0F, -102.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-30.0F, -5.0F, -105.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-25.67F, -5.0F, -102.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-25.67F, -5.0F, -97.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-30.0F, -5.0F, -95.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body_main", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 180.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(6.25F, 180.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(7.41F, 180.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(2.5F, 180.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-9.91F, 180.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-12.5F, 180.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-9.91F, 180.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(2.5F, 180.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body_main", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.13F, -4.9F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.posVec(0.0F, 1.5F, -5.56F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 2.5F, -5.56F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 2.87F, -4.9F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 3.0F, -3.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 2.5F, 1.36F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 2.0F, 2.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 1.5F, 1.36F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 1.0F, -3.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.13F, -4.9F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body_main", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.45F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.3982F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.15F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.075F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0518F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0518F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.075F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.15F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.3982F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.45F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("leg_control", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 0.75F, 0.75F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .build();

    public static final AnimationDefinition DIG = AnimationDefinition.Builder.withLength(0.5F).looping()
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(11.3F, 1.25F, -2.17F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(8.7F, 2.17F, 1.25F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(11.3F, -1.25F, 2.17F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(8.7F, -2.17F, -1.25F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.15F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(4.33F, -2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(4.33F, 2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-4.33F, 2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-4.33F, -2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_antennae", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(2.5F, 4.33F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-2.5F, 4.33F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-2.5F, -4.33F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(2.5F, -4.33F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 20.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(18.66F, 30.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(15.0F, 37.32F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(10.0F, 40.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(5.0F, 37.32F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 20.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5.0F, 2.68F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(15.0F, 2.68F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(18.66F, 10.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(20.0F, 20.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.13F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.87F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.87F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.13F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -20.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0417F, KeyframeAnimations.degreeVec(1.34F, -10.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(5.0F, -2.68F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(15.0F, -2.68F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(20.0F, -20.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(15.0F, -37.32F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(10.0F, -40.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(5.0F, -37.32F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4583F, KeyframeAnimations.degreeVec(1.34F, -30.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -20.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.87F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.13F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.13F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.87F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-17.17F, -2.17F, 1.25F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-12.83F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-17.17F, 2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-12.83F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-17.17F, -2.17F, 1.25F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, 11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, 8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-20.0F, 15.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, -11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, -8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-20.0F, -15.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.1F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, 7.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-10.0F, 7.5F, 11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-10.0F, 7.5F, 8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.0F, 7.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.13F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, -7.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-10.0F, -7.5F, -11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(-10.0F, -7.5F, -8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.0F, -7.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.12F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.13F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.14F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 7.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 7.5F, -11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 7.5F, -8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 7.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_middle_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.14F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, -15.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(10.0F, -15.0F, 11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(10.0F, -15.0F, 8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, -15.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.15F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_back_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 15.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(10.0F, 15.0F, -11.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(10.0F, 15.0F, -8.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, 15.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_back_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.15F, 0.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .build();

    public static final AnimationDefinition ATTACK_BLEND1 = AnimationDefinition.Builder.withLength(0.5F)
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10.0F, 5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(10.0F, -50.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(10.0F, -20.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 50.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, -50.0F, 40.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.2083F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.25F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .build();

    public static final AnimationDefinition ATTACK_BLEND2 = AnimationDefinition.Builder.withLength(0.5F)
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -50.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 40.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 50.0F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.2083F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.25F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(10.0F, 50.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(10.0F, 20.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("right_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .build();
}
