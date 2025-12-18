package com.barlinc.unusual_prehistory.client.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LeptictidiumAnimations {

	public static final AnimationDefinition BABY_TRANSFORM = AnimationDefinition.Builder.withLength(0.0F).looping()
			.addAnimation("Lepti_Ear_Left", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.6F, 1.6F, 1.6F), AnimationChannel.Interpolations.LINEAR)
			))
            .addAnimation("Lepti_Ear_Right", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.6F, 1.6F, 1.6F), AnimationChannel.Interpolations.LINEAR)
            ))
			.build();
}