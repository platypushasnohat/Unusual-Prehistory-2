package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.DromaeosaurusAnimations;
import com.unusualmodding.unusual_prehistory.client.animations.carnotaurus.*;
import com.unusualmodding.unusual_prehistory.entity.Carnotaurus;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CarnotaurusModel<T extends Carnotaurus> extends HierarchicalModel<T> {

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
	private final ModelPart head_angry;
	private final ModelPart head;
	private final ModelPart upper_jaw;
	private final ModelPart horn1;
	private final ModelPart horn2;
	private final ModelPart lower_jaw;
	private final ModelPart dewlap;
	private final ModelPart arm_left;
	private final ModelPart arm_right;
	private final ModelPart tail1;
	private final ModelPart tail2;

	public CarnotaurusModel(ModelPart root) {
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
		this.head_angry = this.neck.getChild("head_angry");
		this.head = this.head_angry.getChild("head");
		this.upper_jaw = this.head.getChild("upper_jaw");
		this.horn1 = this.upper_jaw.getChild("horn1");
		this.horn2 = this.upper_jaw.getChild("horn2");
		this.lower_jaw = this.head.getChild("lower_jaw");
		this.dewlap = this.lower_jaw.getChild("dewlap");
		this.arm_left = this.body.getChild("arm_left");
		this.arm_right = this.body.getChild("arm_right");
		this.tail1 = this.body.getChild("tail1");
		this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(7.5F, 10.0F, 6.0F));

		PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create()
				.texOffs(98, 0).addBox(-5.0F, -11.0F, -8.0F, 10.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create()
				.texOffs(54, 133).addBox(-3.5F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 7.0F, 6.0F));

		PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create()
				.texOffs(98, 36).addBox(-4.5F, 0.0F, -8.0F, 9.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(58, 114).addBox(2.5F, 0.0F, -11.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(58, 114).addBox(-4.5F, 0.0F, -11.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(58, 105).addBox(-1.5F, 0.0F, -11.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(58, 123).addBox(-1.5F, 0.0F, 7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 1.0F));

		PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create()
				.texOffs(98, 0).mirror().addBox(-5.0F, -11.0F, -8.0F, 10.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-15.0F, 0.0F, 0.0F));

		PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create()
				.texOffs(54, 133).mirror().addBox(-3.5F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, 7.0F, 6.0F));

		PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create()
				.texOffs(98, 36).mirror().addBox(-4.5F, 0.0F, -8.0F, 9.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(58, 114).mirror().addBox(-4.5F, 0.0F, -11.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(58, 114).mirror().addBox(2.5F, 0.0F, -11.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(58, 105).mirror().addBox(-1.5F, 0.0F, -11.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(58, 123).mirror().addBox(-1.5F, 0.0F, 7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 15.0F, 1.0F));

		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 6.0F));

		PartDefinition breathe = body.addOrReplaceChild("breathe", CubeListBuilder.create().texOffs(127, 130).addBox(-2.5F, -13.0F, -1.0F, 5.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-8.5F, -12.0F, -16.0F, 17.0F, 24.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -6.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(74, 90).addBox(-3.5F, -19.0F, -10.0F, 7.0F, 24.0F, 19.0F, new CubeDeformation(0.0F))
				.texOffs(126, 90).addBox(-2.5F, -21.0F, -4.0F, 5.0F, 25.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, -18.0F));

		PartDefinition head_angry = neck.addOrReplaceChild("head_angry", CubeListBuilder.create(), PartPose.offset(0.0F, -12.6F, -5.1F));

		PartDefinition head = head_angry.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create()
				.texOffs(150, 11).addBox(-4.5F, -8.91F, -3.9F, 9.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(146, 36).addBox(-4.5F, -8.91F, -12.9F, 9.0F, 11.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(185, 25).addBox(-4.5F, 2.09F, -12.9F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(150, 24).addBox(-3.5F, 2.09F, -11.9F, 7.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 0.0F));

		PartDefinition horn1 = upper_jaw.addOrReplaceChild("horn1", CubeListBuilder.create()
				.texOffs(88, 133).addBox(-2.0F, -2.92F, -12.0F, 4.0F, 6.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(30, 152).addBox(-2.0F, -6.91F, -12.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(58, 110).addBox(-2.0F, -6.91F, -7.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -6.0F, -3.9F, 0.0F, -0.0873F, 0.0873F));

		PartDefinition horn2 = upper_jaw.addOrReplaceChild("horn2", CubeListBuilder.create()
				.texOffs(88, 133).mirror().addBox(-2.0F, -2.92F, -12.0F, 4.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(30, 152).mirror().addBox(-2.0F, -6.91F, -12.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(58, 110).mirror().addBox(-2.0F, -6.91F, -7.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.5F, -6.0F, -3.9F, 0.0F, 0.0873F, -0.0873F));

		PartDefinition lower_jaw = head.addOrReplaceChild("lower_jaw", CubeListBuilder.create()
				.texOffs(0, 152).addBox(-3.5F, 1.09F, -11.9F, 7.0F, 1.0F, 8.0F, new CubeDeformation(-0.01F))
				.texOffs(148, 82).addBox(-4.5F, 0.09F, -3.9F, 9.0F, 4.0F, 4.0F, new CubeDeformation(-0.01F))
				.texOffs(150, 0).addBox(-4.5F, 2.09F, -12.9F, 9.0F, 2.0F, 9.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 2.5F, 0.0F));

		PartDefinition dewlap = lower_jaw.addOrReplaceChild("dewlap", CubeListBuilder.create()
				.texOffs(126, 148).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 10.0F, 12.0F, new CubeDeformation(-0.01F))
				.texOffs(148, 56).addBox(0.0F, 0.0F, -7.0F, 0.0F, 11.0F, 15.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 4.0F, -4.9F));

		PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create()
				.texOffs(58, 98).addBox(-1.5F, 0.0F, -1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(58, 119).addBox(-1.5F, 4.0F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(8.51F, 3.0F, -15.0F));

		PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create()
				.texOffs(58, 98).mirror().addBox(-0.5F, 0.0F, -1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(58, 119).mirror().addBox(-0.5F, 4.0F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.51F, 3.0F, -15.0F));

		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create()
				.texOffs(0, 98).addBox(-4.5F, -5.0F, 0.0F, 9.0F, 12.0F, 20.0F, new CubeDeformation(0.0F))
				.texOffs(1, 130).addBox(-2.5F, -7.0F, 0.0F, 5.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 10.0F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create()
				.texOffs(0, 56).addBox(-2.5F, -5.0F, 0.0F, 5.0F, 10.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(74, 56).addBox(-2.5F, -7.0F, 0.0F, 5.0F, 2.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 20.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Carnotaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (this.young) {
			this.applyStatic(CarnotaurusAnimations.BABY_TRANSFORM);
			this.animateWalk(CarnotaurusAnimations.WALK, limbSwing, limbSwingAmount, 2, 8);
		} else {
			this.animateWalk(CarnotaurusAnimations.WALK, limbSwing, limbSwingAmount, 4, 8);
		}

		this.animate(entity.idleAnimationState, CarnotaurusAnimations.IDLE, ageInTicks);
		this.animate(entity.biteRightAnimationState, CarnotaurusAnimations.BITE_RIGHT, ageInTicks);
		this.animate(entity.biteLeftAnimationState, CarnotaurusAnimations.BITE_LEFT, ageInTicks);

		this.animate(entity.chargeStartAnimationState, CarnotaurusAnimations.CHARGE_START, ageInTicks);
		this.animate(entity.chargeAnimationState, CarnotaurusAnimations.CHARGE, ageInTicks);
		this.animate(entity.chargeEndAnimationState, CarnotaurusAnimations.CHARGE_END, ageInTicks);

		this.neck.xRot += (headPitch * ((float) Math.PI / 180)) / 2;
		this.neck.yRot += (netHeadYaw * ((float) Math.PI / 180)) / 2;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
		if (this.young) {
			float babyScale = 0.5F;
			float bodyYOffset = 24.0F;
			poseStack.pushPose();
			poseStack.scale(babyScale, babyScale, babyScale);
			poseStack.translate(0.0F, bodyYOffset / 16.0F, 0.0F);
			this.root().render(poseStack, vertexConsumer, i, j, f, g, h, k);
			poseStack.popPose();
		} else {
			this.root().render(poseStack, vertexConsumer, i, j, f, g, h, k);
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}