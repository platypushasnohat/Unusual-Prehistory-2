package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.megalania.*;
import com.unusualmodding.unusual_prehistory.entity.Megalania;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MegalaniaModel<T extends Megalania> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart body_main;
	private final ModelPart body_upper;
	private final ModelPart body_overlay;
	private final ModelPart body;
	private final ModelPart neck_overlay;
	private final ModelPart neck;
	private final ModelPart head_overlay;
	private final ModelPart head;
	private final ModelPart jaw_overlay;
	private final ModelPart jaw;
	private final ModelPart tongue;
	private final ModelPart tail1_overlay;
	private final ModelPart tail1;
	private final ModelPart tail2_overlay;
	private final ModelPart tail2;
	private final ModelPart tail3_overlay;
	private final ModelPart tail3;
	private final ModelPart left_arm1;
	private final ModelPart left_arm2;
	private final ModelPart right_arm1;
	private final ModelPart right_arm2;
	private final ModelPart left_leg1;
	private final ModelPart left_leg2;
	private final ModelPart right_leg1;
	private final ModelPart right_leg2;

	public MegalaniaModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.body_upper = this.body_main.getChild("body_upper");
		this.body_overlay = this.body_upper.getChild("body_overlay");
		this.body = this.body_overlay.getChild("body");
		this.neck_overlay = this.body.getChild("neck_overlay");
		this.neck = this.neck_overlay.getChild("neck");
		this.head_overlay = this.neck.getChild("head_overlay");
		this.head = this.head_overlay.getChild("head");
		this.jaw_overlay = this.head.getChild("jaw_overlay");
		this.jaw = this.jaw_overlay.getChild("jaw");
		this.tongue = this.jaw.getChild("tongue");
		this.tail1_overlay = this.body.getChild("tail1_overlay");
		this.tail1 = this.tail1_overlay.getChild("tail1");
		this.tail2_overlay = this.tail1.getChild("tail2_overlay");
		this.tail2 = this.tail2_overlay.getChild("tail2");
		this.tail3_overlay = this.tail2.getChild("tail3_overlay");
		this.tail3 = this.tail3_overlay.getChild("tail3");
		this.left_arm1 = this.body_upper.getChild("left_arm1");
		this.left_arm2 = this.left_arm1.getChild("left_arm2");
		this.right_arm1 = this.body_upper.getChild("right_arm1");
		this.right_arm2 = this.right_arm1.getChild("right_arm2");
		this.left_leg1 = this.body_main.getChild("left_leg1");
		this.left_leg2 = this.left_leg1.getChild("left_leg2");
		this.right_leg1 = this.body_main.getChild("right_leg1");
		this.right_leg2 = this.right_leg1.getChild("right_leg2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -13.5F, 0.0F));
		PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 9.5F));
		PartDefinition body_overlay = body_upper.addOrReplaceChild("body_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, -9.5F));
		PartDefinition body = body_overlay.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, -7.5F, -15.5F, 19.0F, 15.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition neck_overlay = body.addOrReplaceChild("neck_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, -6.5F, -15.5F));
		PartDefinition neck = neck_overlay.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(57, 48).addBox(-3.0F, 0.0F, -13.0F, 6.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head_overlay = neck.addOrReplaceChild("head_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -5.0F));
		PartDefinition head = head_overlay.addOrReplaceChild("head", CubeListBuilder.create().texOffs(90, 111).addBox(-3.5F, 0.0F, -16.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(70, 0).addBox(-4.5F, 0.0F, -8.0F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.01F)).texOffs(54, 87).addBox(-2.5F, 2.5F, -15.0F, 5.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(28, 114).addBox(-4.5F, 3.0F, -4.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition jaw_overlay = head.addOrReplaceChild("jaw_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, -4.0F));
		PartDefinition jaw = jaw_overlay.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(60, 111).addBox(-3.5F, 0.0F, -12.0F, 7.0F, 4.0F, 8.0F, new CubeDeformation(0.01F)).texOffs(54, 96).addBox(-2.5F, -0.5F, -11.0F, 5.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(6, 70).addBox(-4.5F, 0.0F, -4.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition tongue = jaw.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(17, 47).addBox(-1.5F, -0.01F, -9.0F, 3.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -1.0F));
		PartDefinition tail1_overlay = body.addOrReplaceChild("tail1_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, 15.5F));
		PartDefinition tail1 = tail1_overlay.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 46).addBox(-5.5F, -4.0F, -4.0F, 11.0F, 7.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition tail2_overlay = tail1.addOrReplaceChild("tail2_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 29.0F));
		PartDefinition tail2 = tail2_overlay.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(60, 86).addBox(-4.0F, -2.5F, 0.0F, 8.0F, 5.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition tail3_overlay = tail2.addOrReplaceChild("tail3_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 20.0F));
		PartDefinition tail3 = tail3_overlay.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 86).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 4.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition left_arm1 = body_upper.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(71, 12).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 0.0F, -19.0F));
		PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(1, 56).addBox(-3.5F, 0.0F, -5.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 9.0F, -1.0F));
		PartDefinition right_arm1 = body_upper.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(71, 12).mirror().addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5F, 0.0F, -19.0F));
		PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(1, 56).mirror().addBox(-4.5F, 0.0F, -5.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 9.0F, -1.0F));
		PartDefinition left_leg1 = body_main.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(4, 10).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 3.5F, 9.5F));
		PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(0, 115).addBox(-4.0F, 0.0F, -5.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -1.0F));
		PartDefinition right_leg1 = body_main.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(4, 10).mirror().addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5F, 3.5F, 9.5F));
		PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(0, 115).mirror().addBox(-4.0F, 0.0F, -5.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 9.0F, -1.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Megalania entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(MegalaniaAnimations.WALK, limbSwing, limbSwingAmount, 5, 8);
		if (entity.isStillEnough()) {
			this.animate(entity.idleAnimationState, MegalaniaIdleAnimations.IDLE, ageInTicks);
		}

		this.head.xRot += headPitch * ((float) Math.PI / 180f) - (headPitch * ((float) Math.PI / 180f)) / 2;
		this.head.yRot += netHeadYaw * ((float) Math.PI / 180f) - (netHeadYaw * ((float) Math.PI / 180f)) / 2;
		this.neck.xRot += (headPitch * ((float) Math.PI / 180f)) / 2;
		this.neck.yRot += (netHeadYaw * ((float) Math.PI / 180f)) / 2;
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