package com.unusualmodding.unusual_prehistory.client.models.entity.diplocaulus;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.diplocaulus.DiplocaulusRecurvatisAnimations;
import com.unusualmodding.unusual_prehistory.client.models.entity.base.UP2Model;
import com.unusualmodding.unusual_prehistory.entity.DiplocaulusEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DiplocaulusRecurvatisModel<T extends DiplocaulusEntity> extends UP2Model<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body_main;
	private final ModelPart body;
	private final ModelPart neck;
	private final ModelPart head_overlay;
	private final ModelPart head;
	private final ModelPart jaw_overlay;
	private final ModelPart jaw;
	private final ModelPart tail;
	private final ModelPart arm_control;
	private final ModelPart left_arm1;
	private final ModelPart left_arm2;
	private final ModelPart right_arm1;
	private final ModelPart right_arm2;
	private final ModelPart leg_control;
	private final ModelPart left_leg1;
	private final ModelPart left_leg2;
	private final ModelPart right_leg1;
	private final ModelPart right_leg2;

	public DiplocaulusRecurvatisModel(ModelPart root) {
		this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body_main = this.swim_control.getChild("body_main");
		this.body = this.body_main.getChild("body");
		this.neck = this.body.getChild("neck");
		this.head_overlay = this.neck.getChild("head_overlay");
		this.head = this.head_overlay.getChild("head");
		this.jaw_overlay = this.head.getChild("jaw_overlay");
		this.jaw = this.jaw_overlay.getChild("jaw");
		this.tail = this.body.getChild("tail");
		this.arm_control = this.body_main.getChild("arm_control");
		this.left_arm1 = this.arm_control.getChild("left_arm1");
		this.left_arm2 = this.left_arm1.getChild("left_arm2");
		this.right_arm1 = this.arm_control.getChild("right_arm1");
		this.right_arm2 = this.right_arm1.getChild("right_arm2");
		this.leg_control = this.body_main.getChild("leg_control");
		this.left_leg1 = this.leg_control.getChild("left_leg1");
		this.left_leg2 = this.left_leg1.getChild("left_leg2");
		this.right_leg1 = this.leg_control.getChild("right_leg1");
		this.right_leg2 = this.right_leg1.getChild("right_leg2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -2.05F, 0.0F));
		PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 7).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.05F)).texOffs(14, 28).addBox(0.0F, -4.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0025F)).texOffs(0, 0).addBox(-5.0F, -2.05F, -5.0F, 10.0F, 0.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 37).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -3.0F));
		PartDefinition head_overlay = neck.addOrReplaceChild("head_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -1.0F));
		PartDefinition head = head_overlay.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition righthead_r1 = head.addOrReplaceChild("righthead_r1", CubeListBuilder.create().texOffs(0, 25).mirror().addBox(-5.0F, -2.0F, 0.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.825F, 0.0F, -2.0F, 0.0F, 0.7854F, 0.0F));
		PartDefinition lefthead_r1 = head.addOrReplaceChild("lefthead_r1", CubeListBuilder.create().texOffs(26, 28).addBox(0.0F, -2.0F, 0.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.825F, 0.0F, -2.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition face_r1 = head.addOrReplaceChild("face_r1", CubeListBuilder.create().texOffs(20, 18).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -2.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition jaw_overlay = head.addOrReplaceChild("jaw_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition jaw = jaw_overlay.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition jaw_r1 = jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(14, 23).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0025F)).texOffs(0, 29).addBox(-2.0F, -1.01F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 1.0F, -2.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -3.0F, -1.0F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0025F)).texOffs(31, 22).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 3.0F));
		PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.4F, -2.0F));
		PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(12, 35).addBox(0.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));
		PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(-1, 33).addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(3.0F, 1.59F, 0.0F));
		PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(12, 35).mirror().addBox(-3.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 0.0F, 0.0F));
		PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(-1, 33).mirror().addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-3.0F, 1.59F, 0.0F));
		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.4F, 3.0F));
		PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(12, 35).addBox(0.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));
		PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(-1, 33).addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(3.0F, 1.59F, 0.0F));
		PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(12, 35).mirror().addBox(-3.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 0.0F, -1.0F));
		PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(-1, 33).mirror().addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-3.0F, 1.59F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(DiplocaulusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.isInWaterOrBubble()) {
			this.animateWalk(DiplocaulusRecurvatisAnimations.SWIM, limbSwing, limbSwingAmount, 2.0f, 1.0f);
			this.animateIdle(entity.idleAnimationState, DiplocaulusRecurvatisAnimations.SWIM_IDLE, ageInTicks, 1.0f, 1 - Math.abs(limbSwingAmount));
		}

		if (!entity.isInWaterOrBubble()) {
			this.animateWalk(DiplocaulusRecurvatisAnimations.WALK, limbSwing, limbSwingAmount, 3.0f, 3.0f);
			this.animateIdle(entity.idleAnimationState, DiplocaulusRecurvatisAnimations.IDLE, ageInTicks, 1.0f, 1 - Math.abs(limbSwingAmount));
		}

		this.animate(entity.quirkAnimationState, DiplocaulusRecurvatisAnimations.QUIRK, ageInTicks, 1.0f);

		if (entity.isInWaterOrBubble()) {
			this.body_main.resetPose();
			this.swim_control.xRot = headPitch * ((float) Math.PI / 180f);
			this.swim_control.yRot = netHeadYaw * ((float) Math.PI / 180f);
		}

		this.head.xRot += headPitch * ((float) Math.PI / 180f) - (headPitch * ((float) Math.PI / 180f)) / 2;
		this.head.yRot += netHeadYaw * ((float) Math.PI / 180f) - (netHeadYaw * ((float) Math.PI / 180f)) / 2;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}