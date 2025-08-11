package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.majungasaurus.*;
import com.unusualmodding.unusual_prehistory.entity.Majungasaurus;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MajungasaurusModel<T extends Majungasaurus> extends HierarchicalModel<T> {

	private float alpha = 1.0F;

	private final ModelPart root;
	private final ModelPart body_main;
	private final ModelPart leg_control;
	private final ModelPart left_leg1;
	private final ModelPart left_leg2;
	private final ModelPart left_leg3;
	private final ModelPart right_leg1;
	private final ModelPart right_leg2;
	private final ModelPart right_leg3;
	private final ModelPart body;
	private final ModelPart breathe;
	private final ModelPart neck;
	private final ModelPart dewlap;
	private final ModelPart head;
	private final ModelPart upper_jaw;
	private final ModelPart left_eye;
	private final ModelPart right_eye;
	private final ModelPart jaw;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart tail1;
	private final ModelPart tail2;

	public MajungasaurusModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.leg_control = this.body_main.getChild("leg_control");
		this.left_leg1 = this.leg_control.getChild("left_leg1");
		this.left_leg2 = this.left_leg1.getChild("left_leg2");
		this.left_leg3 = this.left_leg2.getChild("left_leg3");
		this.right_leg1 = this.leg_control.getChild("right_leg1");
		this.right_leg2 = this.right_leg1.getChild("right_leg2");
		this.right_leg3 = this.right_leg2.getChild("right_leg3");
		this.body = this.body_main.getChild("body");
		this.breathe = this.body.getChild("breathe");
		this.neck = this.body.getChild("neck");
		this.dewlap = this.neck.getChild("dewlap");
		this.head = this.neck.getChild("head");
		this.upper_jaw = this.head.getChild("upper_jaw");
		this.left_eye = this.upper_jaw.getChild("left_eye");
		this.right_eye = this.upper_jaw.getChild("right_eye");
		this.jaw = this.head.getChild("jaw");
		this.left_arm = this.body.getChild("left_arm");
		this.right_arm = this.body.getChild("right_arm");
		this.tail1 = this.body.getChild("tail1");
		this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(-0.5F, 2.0F, -6.0F));

		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.5F, 3.0F, 4.0F));

		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create()
				.texOffs(86, 0).mirror().addBox(-4.0F, -4.0F, -5.0F, 7.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.5F, 0.0F, 0.0F));

		PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create()
				.texOffs(120, 9).addBox(-3.0F, -1.0F, -3.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 4.0F));

		PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create()
				.texOffs(70, 113).addBox(-3.5F, 0.0F, -4.5F, 7.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(82, 123).addBox(2.5F, 0.0F, -6.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(74, 123).addBox(-0.5F, 0.0F, -6.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(74, 123).addBox(-3.5F, 0.0F, -6.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 10.0F, -0.5F));

		PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create()
				.texOffs(86, 0).addBox(-3.0F, -4.0F, -5.0F, 7.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.5F, 0.0F, 0.0F));

		PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create()
				.texOffs(120, 9).mirror().addBox(-2.0F, -1.0F, -3.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 7.0F, 4.0F));

		PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create()
				.texOffs(70, 113).mirror().addBox(-3.5F, 0.0F, -4.5F, 7.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(82, 123).mirror().addBox(-3.5F, 0.0F, -6.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(74, 123).mirror().addBox(-1.5F, 0.0F, -6.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(74, 123).mirror().addBox(1.5F, 0.0F, -6.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, 10.0F, -0.5F));

		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create()
				.texOffs(104, 85).addBox(-2.5F, -15.0F, -2.0F, 5.0F, 17.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(70, 85).addBox(-3.5F, -14.0F, -5.0F, 7.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -15.0F));

		PartDefinition dewlap = neck.addOrReplaceChild("dewlap", CubeListBuilder.create()
				.texOffs(122, 65).addBox(-1.0F, -5.0F, -8.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(58, 117).addBox(0.0F, -6.0F, -9.0F, 0.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, -1.0F));

		PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create()
				.texOffs(0, 117).addBox(-4.5F, -6.0F, -5.0F, 9.0F, 6.0F, 5.0F, new CubeDeformation(0.01F))
				.texOffs(104, 110).addBox(-4.5F, -6.0F, -11.0F, 9.0F, 7.0F, 6.0F, new CubeDeformation(0.01F))
				.texOffs(70, 80).addBox(-1.0F, -9.0F, -4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(122, 76).addBox(1.5F, -8.0F, -9.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(122, 76).mirror().addBox(-3.5F, -8.0F, -9.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(116, 35).addBox(-4.5F, 1.0F, -11.0F, 9.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition left_eye = upper_jaw.addOrReplaceChild("left_eye", CubeListBuilder.create()
				.texOffs(120, 25).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -2.5F, -5.5F));

		PartDefinition right_eye = upper_jaw.addOrReplaceChild("right_eye", CubeListBuilder.create()
				.texOffs(120, 25).mirror().addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -2.5F, -5.5F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create()
				.texOffs(28, 117).addBox(-5.0F, 1.0F, -11.0F, 9.0F, 3.0F, 6.0F, new CubeDeformation(0.01F))
				.texOffs(120, 0).addBox(-5.0F, 0.0F, -5.0F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.01F))
				.texOffs(86, 35).addBox(-5.0F, 0.0F, -11.0F, 9.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 2.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create()
				.texOffs(66, 123).addBox(-0.99F, 0.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 2.0F, -12.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create()
				.texOffs(66, 123).mirror().addBox(-0.01F, 0.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.5F, 2.0F, -12.0F));

		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create()
				.texOffs(80, 65).addBox(-4.5F, -2.5F, 0.0F, 9.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(86, 22).addBox(-2.5F, -3.5F, 0.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, 7.0F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-3.0F, -2.5F, 0.0F, 6.0F, 5.0F, 37.0F, new CubeDeformation(0.0F))
				.texOffs(0, 42).addBox(-1.5F, -3.5F, 0.0F, 3.0F, 1.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));

		PartDefinition breathe = body.addOrReplaceChild("breathe", CubeListBuilder.create()
				.texOffs(80, 42).addBox(-3.0F, -11.0F, -15.0F, 6.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(0, 80).addBox(-6.5F, -10.0F, -15.0F, 13.0F, 15.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Majungasaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.isInWaterOrBubble()) {
			if (this.young) {
				this.animateWalk(MajungasaurusAnimations.SWIM, limbSwing, limbSwingAmount, 2, 8);
			} else {
				this.animateWalk(MajungasaurusAnimations.SWIM, limbSwing, limbSwingAmount, 4, 8);
			}
		} else {
			if (entity.isMajungasaurusVisuallyStealthMode() && !entity.isInPoseTransition()) {
				this.animateWalk(MajungasaurusStateAnimations.CAMOFLAUGE_WALK, limbSwing, limbSwingAmount, 4, 8);
			} else {
				if (this.young) {
					this.animateWalk(MajungasaurusAnimations.WALK, limbSwing, limbSwingAmount, 2, 8);
				} else {
					this.animateWalk(MajungasaurusAnimations.WALK, limbSwing, limbSwingAmount, 4, 8);
				}
			}
		}

		if (this.young) {
			this.applyStatic(MajungasaurusAnimations.BABY_TRANSFORM);
		}

		this.animate(entity.idleAnimationState, MajungasaurusAnimations.IDLE, ageInTicks);
		this.animate(entity.biteRightAnimationState, MajungasaurusAnimations.BITE_RIGHT, ageInTicks);
		this.animate(entity.biteLeftAnimationState, MajungasaurusAnimations.BITE_LEFT, ageInTicks);

		this.animate(entity.enterStealthAnimationState, MajungasaurusStateAnimations.CAMOFLAUGE_START, ageInTicks);
		this.animate(entity.stealthIdleAnimationState, MajungasaurusStateAnimations.CAMOFLAUGE_IDLE, ageInTicks);
		this.animate(entity.exitStealthAnimationState, MajungasaurusStateAnimations.CAMOFLAUGE_END, ageInTicks);

		this.animate(entity.eyesAnimationState, MajungasaurusIdleAnimations.EYES, ageInTicks);

		this.neck.xRot += (headPitch * ((float) Math.PI / 180)) / 2;
		this.neck.yRot += (netHeadYaw * ((float) Math.PI / 180)) / 2;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (this.young) {
			float babyScale = 0.5F;
			float bodyYOffset = 24.0F;
			poseStack.pushPose();
			poseStack.scale(babyScale, babyScale, babyScale);
			poseStack.translate(0.0F, bodyYOffset / 16.0F, 0.0F);
			this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha * this.alpha);
			poseStack.popPose();
		} else {
			poseStack.pushPose();
			this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha * this.alpha);
			poseStack.popPose();
		}
    }

	@Override
	public ModelPart root() {
		return this.root;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
}