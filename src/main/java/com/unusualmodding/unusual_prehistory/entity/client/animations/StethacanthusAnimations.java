package com.unusualmodding.unusual_prehistory.entity.client.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Made with Blockbench 4.12.4
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * @author Author
 */
public class StethacanthusAnimations {

	public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(8.0F).looping()
	.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
		new Keyframe(0.0F, KeyframeAnimations.posVec(0.7071F, 2.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_front_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_front_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("dorsal", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -8.6603F, -2.5F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_back_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, -30.0F, 40.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_back_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 30.0F, -40.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("tail1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, -25.9808F, -5.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("tail2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
	public static final AnimationDefinition SWIM = AnimationDefinition.Builder.withLength(4.0F).looping()
	.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
		new Keyframe(0.0F, KeyframeAnimations.posVec(0.7071F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_front_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_front_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("dorsal", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, -8.6603F, -2.5F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_back_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, -30.0F, 40.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_back_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 30.0F, -40.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("tail1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -25.9808F, -5.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("tail2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
	public static final AnimationDefinition FLOP = AnimationDefinition.Builder.withLength(4.0F).looping()
	.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 95.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
		new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -8.6603F, -2.5F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_front_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_front_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("dorsal", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -8.6603F, -2.5F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_back_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, -30.0F, 100.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_back_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 30.0F, -40.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("tail1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -17.3205F, -5.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("tail2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(-8.6603F, 0.0F, -10.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
	public static final AnimationDefinition ATTACK = AnimationDefinition.Builder.withLength(1.0F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
		new Keyframe(0.0F, KeyframeAnimations.posVec(0.7071F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3333F, KeyframeAnimations.posVec(0.7071F, 1.0F, 3.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.posVec(-0.7071F, 1.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6667F, KeyframeAnimations.posVec(-0.7071F, 1.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.posVec(0.7071F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.SCALE,
		new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 0.9F, 1.2F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-40.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-50.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, -5.0F, 5.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(80.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(100.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_front_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, -30.0F, 50.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_front_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, 30.0F, -50.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("dorsal", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -8.6603F, -2.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-30.0F, 8.6603F, 2.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -8.6603F, -2.5F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_back_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, -30.0F, 40.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -60.0F, 40.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(20.0F, -30.0F, 40.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_back_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 30.0F, -40.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 60.0F, -40.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(20.0F, 30.0F, -40.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("tail1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -25.9808F, -5.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 25.9808F, 5.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -25.9808F, -5.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("tail2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
}