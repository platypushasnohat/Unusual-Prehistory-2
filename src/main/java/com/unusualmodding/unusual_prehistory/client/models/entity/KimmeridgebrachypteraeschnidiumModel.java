package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.kimmeridgebrachypteraeschnidium.*;
import com.unusualmodding.unusual_prehistory.client.models.entity.base.UP2Model;
import com.unusualmodding.unusual_prehistory.entity.KimmeridgebrachypteraeschnidiumEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class KimmeridgebrachypteraeschnidiumModel<T extends KimmeridgebrachypteraeschnidiumEntity> extends UP2Model<T> {

	private final ModelPart root;
	private final ModelPart body_main;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart left_front_wing;
	private final ModelPart right_front_wing;
	private final ModelPart left_back_wing;
	private final ModelPart right_back_wing;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart leg_control;
	private final ModelPart left_front_leg;
	private final ModelPart right_front_leg;
	private final ModelPart left_back_leg;
	private final ModelPart right_back_leg;

	public KimmeridgebrachypteraeschnidiumModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.body = this.body_main.getChild("body");
		this.tail = this.body.getChild("tail");
		this.head = this.body.getChild("head");
		this.left_front_wing = this.body.getChild("left_front_wing");
		this.right_front_wing = this.body.getChild("right_front_wing");
		this.left_back_wing = this.body.getChild("left_back_wing");
		this.right_back_wing = this.body.getChild("right_back_wing");
		this.left_arm = this.body.getChild("left_arm");
		this.right_arm = this.body.getChild("right_arm");
		this.leg_control = this.body_main.getChild("leg_control");
		this.left_front_leg = this.leg_control.getChild("left_front_leg");
		this.right_front_leg = this.leg_control.getChild("right_front_leg");
		this.left_back_leg = this.leg_control.getChild("left_back_leg");
		this.right_back_leg = this.leg_control.getChild("right_back_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));
		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(14, 16).addBox(-2.0F, -2.0F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 1.5F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(12, 22).addBox(-3.0F, -1.0F, -2.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.5F));
		PartDefinition left_front_wing = body.addOrReplaceChild("left_front_wing", CubeListBuilder.create().texOffs(-1, 8).mirror().addBox(0.0F, 0.0F, -7.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(1.0F, -2.0F, -0.5F, 0.0F, 0.0F, -0.1745F));
		PartDefinition right_front_wing = body.addOrReplaceChild("right_front_wing", CubeListBuilder.create().texOffs(-1, 8).addBox(-10.0F, 0.0F, -7.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-1.0F, -2.0F, -0.5F, 0.0F, 0.0F, 0.1745F));
		PartDefinition left_back_wing = body.addOrReplaceChild("left_back_wing", CubeListBuilder.create().texOffs(-1, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0F, 1.5F, 0.0F, 0.0F, -0.1745F));
		PartDefinition right_back_wing = body.addOrReplaceChild("right_back_wing", CubeListBuilder.create().texOffs(-1, 0).addBox(-10.0F, 0.0F, -1.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, -2.0F, 1.5F, 0.0F, 0.0F, 0.1745F));
		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(0.0F, 0.0F, -3.0F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.0F, -0.5F, -0.48F, 0.0F, 0.0F));
		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, 0.0F, -3.0F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-1.0F, 1.0F, -0.5F, -0.48F, 0.0F, 0.0F));
		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(1.0F, 1.0F, -0.5F));
		PartDefinition left_front_leg = leg_control.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(2, -2).mirror().addBox(0.0F, 0.0F, -2.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7418F));
		PartDefinition right_front_leg = leg_control.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(2, -2).addBox(0.0F, 0.0F, -2.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7418F));
		PartDefinition left_back_leg = leg_control.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7418F));
		PartDefinition right_back_leg = leg_control.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7418F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(KimmeridgebrachypteraeschnidiumEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.flyAnimationState, KimmeridgebrachypteraeschnidiumMovementAnimations.FLY, ageInTicks, 1f);
		this.animateIdle(entity.idleAnimationState, KimmeridgebrachypteraeschnidiumIdleAnimations.IDLE, ageInTicks, 1f, 1f - Math.abs(limbSwingAmount));
		this.animateIdle(entity.preenAnimationState, KimmeridgebrachypteraeschnidiumIdleAnimations.PREEN, ageInTicks, 1f, 1f - Math.abs(limbSwingAmount));
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