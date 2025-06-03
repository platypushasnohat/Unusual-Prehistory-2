package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.kentrosaurus.*;
import com.unusualmodding.unusual_prehistory.entity.Kentrosaurus;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class KentrosaurusModel<T extends Kentrosaurus> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart body_main;
	private final ModelPart body_overlay;
	private final ModelPart body;
	private final ModelPart head_overlay;
	private final ModelPart head;
	private final ModelPart jaw_overlay;
	private final ModelPart jaw;
	private final ModelPart left_neckplates_overlay;
	private final ModelPart left_neckplates;
	private final ModelPart right_neckplates_overlay;
	private final ModelPart right_neckplates;
	private final ModelPart left_shoulder_overlay;
	private final ModelPart left_shoulder;
	private final ModelPart right_shoulder_overlay;
	private final ModelPart right_shoulder;
	private final ModelPart left_backplates_overlay;
	private final ModelPart left_backplates;
	private final ModelPart right_backplates_overlay;
	private final ModelPart right_backplates;
	private final ModelPart left_thagomizer1_overlay;
	private final ModelPart left_thagomizer1;
	private final ModelPart right_thagomizer1_overlay;
	private final ModelPart right_thagomizer1;
	private final ModelPart tail1_overlay;
	private final ModelPart tail1;
	private final ModelPart left_thagomizer2_overlay;
	private final ModelPart left_thagomizer2;
	private final ModelPart right_thagomizer2_overlay;
	private final ModelPart right_thagomizer2;
	private final ModelPart tail2_overlay;
	private final ModelPart tail2;
	private final ModelPart left_thagomizer3_overlay;
	private final ModelPart left_thagomizer3;
	private final ModelPart left_thagomizer4_overlay;
	private final ModelPart left_thagomizer4;
	private final ModelPart left_thagomizer5_overlay;
	private final ModelPart left_thagomizer5;
	private final ModelPart left_thagomizer6_overlay;
	private final ModelPart left_thagomizer6;
	private final ModelPart right_thagomizer3_overlay;
	private final ModelPart right_thagomizer3;
	private final ModelPart right_thagomizer4_overlay;
	private final ModelPart right_thagomizer4;
	private final ModelPart right_thagomizer5_overlay;
	private final ModelPart right_thagomizer5;
	private final ModelPart right_thagomizer6_overlay;
	private final ModelPart right_thagomizer6;
	private final ModelPart arm_control;
	private final ModelPart left_arm_overlay;
	private final ModelPart left_arm;
	private final ModelPart right_arm_overlay;
	private final ModelPart right_arm;
	private final ModelPart leg_control;
	private final ModelPart left_leg1_overlay;
	private final ModelPart left_leg1;
	private final ModelPart left_leg2_overlay;
	private final ModelPart left_leg2;
	private final ModelPart right_leg1_overlay;
	private final ModelPart right_leg1;
	private final ModelPart right_leg2_overlay;
	private final ModelPart right_leg2;

	public KentrosaurusModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.body_overlay = this.body_main.getChild("body_overlay");
		this.body = this.body_overlay.getChild("body");
		this.head_overlay = this.body.getChild("head_overlay");
		this.head = this.head_overlay.getChild("head");
		this.jaw_overlay = this.head.getChild("jaw_overlay");
		this.jaw = this.jaw_overlay.getChild("jaw");
		this.left_neckplates_overlay = this.head.getChild("left_neckplates_overlay");
		this.left_neckplates = this.left_neckplates_overlay.getChild("left_neckplates");
		this.right_neckplates_overlay = this.head.getChild("right_neckplates_overlay");
		this.right_neckplates = this.right_neckplates_overlay.getChild("right_neckplates");
		this.left_shoulder_overlay = this.body.getChild("left_shoulder_overlay");
		this.left_shoulder = this.left_shoulder_overlay.getChild("left_shoulder");
		this.right_shoulder_overlay = this.body.getChild("right_shoulder_overlay");
		this.right_shoulder = this.right_shoulder_overlay.getChild("right_shoulder");
		this.left_backplates_overlay = this.body.getChild("left_backplates_overlay");
		this.left_backplates = this.left_backplates_overlay.getChild("left_backplates");
		this.right_backplates_overlay = this.body.getChild("right_backplates_overlay");
		this.right_backplates = this.right_backplates_overlay.getChild("right_backplates");
		this.left_thagomizer1_overlay = this.body.getChild("left_thagomizer1_overlay");
		this.left_thagomizer1 = this.left_thagomizer1_overlay.getChild("left_thagomizer1");
		this.right_thagomizer1_overlay = this.body.getChild("right_thagomizer1_overlay");
		this.right_thagomizer1 = this.right_thagomizer1_overlay.getChild("right_thagomizer1");
		this.tail1_overlay = this.body.getChild("tail1_overlay");
		this.tail1 = this.tail1_overlay.getChild("tail1");
		this.left_thagomizer2_overlay = this.tail1.getChild("left_thagomizer2_overlay");
		this.left_thagomizer2 = this.left_thagomizer2_overlay.getChild("left_thagomizer2");
		this.right_thagomizer2_overlay = this.tail1.getChild("right_thagomizer2_overlay");
		this.right_thagomizer2 = this.right_thagomizer2_overlay.getChild("right_thagomizer2");
		this.tail2_overlay = this.tail1.getChild("tail2_overlay");
		this.tail2 = this.tail2_overlay.getChild("tail2");
		this.left_thagomizer3_overlay = this.tail2.getChild("left_thagomizer3_overlay");
		this.left_thagomizer3 = this.left_thagomizer3_overlay.getChild("left_thagomizer3");
		this.left_thagomizer4_overlay = this.tail2.getChild("left_thagomizer4_overlay");
		this.left_thagomizer4 = this.left_thagomizer4_overlay.getChild("left_thagomizer4");
		this.left_thagomizer5_overlay = this.tail2.getChild("left_thagomizer5_overlay");
		this.left_thagomizer5 = this.left_thagomizer5_overlay.getChild("left_thagomizer5");
		this.left_thagomizer6_overlay = this.tail2.getChild("left_thagomizer6_overlay");
		this.left_thagomizer6 = this.left_thagomizer6_overlay.getChild("left_thagomizer6");
		this.right_thagomizer3_overlay = this.tail2.getChild("right_thagomizer3_overlay");
		this.right_thagomizer3 = this.right_thagomizer3_overlay.getChild("right_thagomizer3");
		this.right_thagomizer4_overlay = this.tail2.getChild("right_thagomizer4_overlay");
		this.right_thagomizer4 = this.right_thagomizer4_overlay.getChild("right_thagomizer4");
		this.right_thagomizer5_overlay = this.tail2.getChild("right_thagomizer5_overlay");
		this.right_thagomizer5 = this.right_thagomizer5_overlay.getChild("right_thagomizer5");
		this.right_thagomizer6_overlay = this.tail2.getChild("right_thagomizer6_overlay");
		this.right_thagomizer6 = this.right_thagomizer6_overlay.getChild("right_thagomizer6");
		this.arm_control = this.body_main.getChild("arm_control");
		this.left_arm_overlay = this.arm_control.getChild("left_arm_overlay");
		this.left_arm = this.left_arm_overlay.getChild("left_arm");
		this.right_arm_overlay = this.arm_control.getChild("right_arm_overlay");
		this.right_arm = this.right_arm_overlay.getChild("right_arm");
		this.leg_control = this.body_main.getChild("leg_control");
		this.left_leg1_overlay = this.leg_control.getChild("left_leg1_overlay");
		this.left_leg1 = this.left_leg1_overlay.getChild("left_leg1");
		this.left_leg2_overlay = this.left_leg1.getChild("left_leg2_overlay");
		this.left_leg2 = this.left_leg2_overlay.getChild("left_leg2");
		this.right_leg1_overlay = this.leg_control.getChild("right_leg1_overlay");
		this.right_leg1 = this.right_leg1_overlay.getChild("right_leg1");
		this.right_leg2_overlay = this.right_leg1.getChild("right_leg2_overlay");
		this.right_leg2 = this.right_leg2_overlay.getChild("right_leg2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -19.0F, 0.0F));
		PartDefinition body_overlay = body_main.addOrReplaceChild("body_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition body = body_overlay.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, -18.0F, -16.0F, 19.0F, 27.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head_overlay = body.addOrReplaceChild("head_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, -16.0F));
		PartDefinition head = head_overlay.addOrReplaceChild("head", CubeListBuilder.create().texOffs(83, 122).addBox(-1.5F, 0.0F, -11.0F, 3.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(46, 119).addBox(-2.5F, -5.0F, -9.0F, 5.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(126, 84).addBox(-2.5F, -5.0F, -15.0F, 5.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(45, 113).addBox(-2.5F, -2.0F, -13.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition jaw_overlay = head.addOrReplaceChild("jaw_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -9.0F));
		PartDefinition jaw = jaw_overlay.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(125, 107).addBox(-2.5F, 0.0F, -4.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)).texOffs(115, 107).addBox(-2.5F, 1.0F, -6.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition left_neckplates_overlay = head.addOrReplaceChild("left_neckplates_overlay", CubeListBuilder.create(), PartPose.offset(1.5F, -5.1F, -4.0F));
		PartDefinition left_neckplates = left_neckplates_overlay.addOrReplaceChild("left_neckplates", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition plates_r1 = left_neckplates.addOrReplaceChild("plates_r1", CubeListBuilder.create().texOffs(126, 95).addBox(1.0F, -5.0F, -1.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-0.9653F, -0.197F, -3.0F, 0.0F, 0.0F, 0.1745F));
		PartDefinition right_neckplates_overlay = head.addOrReplaceChild("right_neckplates_overlay", CubeListBuilder.create(), PartPose.offset(-1.5F, -5.1F, -4.0F));
		PartDefinition right_neckplates = right_neckplates_overlay.addOrReplaceChild("right_neckplates", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition plates_r2 = right_neckplates.addOrReplaceChild("plates_r2", CubeListBuilder.create().texOffs(126, 95).mirror().addBox(-1.0F, -5.0F, -1.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.9653F, -0.197F, -3.0F, 0.0F, 0.0F, -0.1745F));
		PartDefinition left_shoulder_overlay = body.addOrReplaceChild("left_shoulder_overlay", CubeListBuilder.create(), PartPose.offset(8.5F, 3.0F, -10.5F));
		PartDefinition left_shoulder = left_shoulder_overlay.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(0, 145).addBox(-2.0F, -1.0F, -1.5F, 21.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(57, 145).addBox(16.0F, -1.0F, 1.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, -0.3491F, -0.6109F));
		PartDefinition right_shoulder_overlay = body.addOrReplaceChild("right_shoulder_overlay", CubeListBuilder.create(), PartPose.offset(-8.5F, 3.0F, -10.5F));
		PartDefinition right_shoulder = right_shoulder_overlay.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(57, 145).mirror().addBox(-19.0F, -1.0F, 1.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 145).mirror().addBox(-19.0F, -1.0F, -1.5F, 21.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.3491F, 0.6109F));
		PartDefinition left_backplates_overlay = body.addOrReplaceChild("left_backplates_overlay", CubeListBuilder.create(), PartPose.offset(4.5F, -18.0F, 0.0F));
		PartDefinition left_backplates = left_backplates_overlay.addOrReplaceChild("left_backplates", CubeListBuilder.create().texOffs(72, 84).addBox(0.0F, -9.0F, -14.0F, 0.0F, 9.0F, 26.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
		PartDefinition right_backplates_overlay = body.addOrReplaceChild("right_backplates_overlay", CubeListBuilder.create(), PartPose.offset(-4.5F, -18.0F, 0.0F));
		PartDefinition right_backplates = right_backplates_overlay.addOrReplaceChild("right_backplates", CubeListBuilder.create().texOffs(72, 84).mirror().addBox(0.0F, -9.0F, -14.0F, 0.0F, 9.0F, 26.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
		PartDefinition left_thagomizer1_overlay = body.addOrReplaceChild("left_thagomizer1_overlay", CubeListBuilder.create(), PartPose.offset(2.5F, -16.0F, 16.0F));
		PartDefinition left_thagomizer1 = left_thagomizer1_overlay.addOrReplaceChild("left_thagomizer1", CubeListBuilder.create().texOffs(0, 96).addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));
		PartDefinition right_thagomizer1_overlay = body.addOrReplaceChild("right_thagomizer1_overlay", CubeListBuilder.create(), PartPose.offset(-2.5F, -16.0F, 16.0F));
		PartDefinition right_thagomizer1 = right_thagomizer1_overlay.addOrReplaceChild("right_thagomizer1", CubeListBuilder.create().texOffs(0, 96).mirror().addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));
		PartDefinition tail1_overlay = body.addOrReplaceChild("tail1_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, -13.5F, 17.0F));
		PartDefinition tail1 = tail1_overlay.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(104, 26).addBox(-4.5F, -2.5F, 0.0F, 9.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition left_thagomizer2_overlay = tail1.addOrReplaceChild("left_thagomizer2_overlay", CubeListBuilder.create(), PartPose.offset(1.5F, -1.5F, 5.0F));
		PartDefinition left_thagomizer2 = left_thagomizer2_overlay.addOrReplaceChild("left_thagomizer2", CubeListBuilder.create().texOffs(0, 96).addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));
		PartDefinition right_thagomizer2_overlay = tail1.addOrReplaceChild("right_thagomizer2_overlay", CubeListBuilder.create(), PartPose.offset(-1.5F, -1.5F, 5.0F));
		PartDefinition right_thagomizer2 = right_thagomizer2_overlay.addOrReplaceChild("right_thagomizer2", CubeListBuilder.create().texOffs(0, 96).mirror().addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.48F));
		PartDefinition tail2_overlay = tail1.addOrReplaceChild("tail2_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 12.0F));
		PartDefinition tail2 = tail2_overlay.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 60).addBox(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition left_thagomizer3_overlay = tail2.addOrReplaceChild("left_thagomizer3_overlay", CubeListBuilder.create(), PartPose.offset(2.0F, -1.75F, 2.0F));
		PartDefinition left_thagomizer3 = left_thagomizer3_overlay.addOrReplaceChild("left_thagomizer3", CubeListBuilder.create().texOffs(0, 96).addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6109F));
		PartDefinition left_thagomizer4_overlay = tail2.addOrReplaceChild("left_thagomizer4_overlay", CubeListBuilder.create(), PartPose.offset(2.0F, -1.75F, 10.0F));
		PartDefinition left_thagomizer4 = left_thagomizer4_overlay.addOrReplaceChild("left_thagomizer4", CubeListBuilder.create().texOffs(0, 96).addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.829F));
		PartDefinition left_thagomizer5_overlay = tail2.addOrReplaceChild("left_thagomizer5_overlay", CubeListBuilder.create(), PartPose.offset(2.0F, -2.0F, 19.0F));
		PartDefinition left_thagomizer5 = left_thagomizer5_overlay.addOrReplaceChild("left_thagomizer5", CubeListBuilder.create().texOffs(0, 96).addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0472F));
		PartDefinition left_thagomizer6_overlay = tail2.addOrReplaceChild("left_thagomizer6_overlay", CubeListBuilder.create(), PartPose.offset(2.0F, -2.0F, 27.0F));
		PartDefinition left_thagomizer6 = left_thagomizer6_overlay.addOrReplaceChild("left_thagomizer6", CubeListBuilder.create().texOffs(0, 96).addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.2654F));
		PartDefinition right_thagomizer3_overlay = tail2.addOrReplaceChild("right_thagomizer3_overlay", CubeListBuilder.create(), PartPose.offset(-2.0F, -1.75F, 2.0F));
		PartDefinition right_thagomizer3 = right_thagomizer3_overlay.addOrReplaceChild("right_thagomizer3", CubeListBuilder.create().texOffs(0, 96).mirror().addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.6109F));
		PartDefinition right_thagomizer4_overlay = tail2.addOrReplaceChild("right_thagomizer4_overlay", CubeListBuilder.create(), PartPose.offset(-2.0F, -1.75F, 10.0F));
		PartDefinition right_thagomizer4 = right_thagomizer4_overlay.addOrReplaceChild("right_thagomizer4", CubeListBuilder.create().texOffs(0, 96).mirror().addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.829F));
		PartDefinition right_thagomizer5_overlay = tail2.addOrReplaceChild("right_thagomizer5_overlay", CubeListBuilder.create(), PartPose.offset(-2.0F, -2.0F, 19.0F));
		PartDefinition right_thagomizer5 = right_thagomizer5_overlay.addOrReplaceChild("right_thagomizer5", CubeListBuilder.create().texOffs(0, 96).mirror().addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0472F));
		PartDefinition right_thagomizer6_overlay = tail2.addOrReplaceChild("right_thagomizer6_overlay", CubeListBuilder.create(), PartPose.offset(-2.0F, -2.0F, 27.0F));
		PartDefinition right_thagomizer6 = right_thagomizer6_overlay.addOrReplaceChild("right_thagomizer6", CubeListBuilder.create().texOffs(0, 96).mirror().addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.2654F));
		PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(5.5F, 9.0F, -10.5F));
		PartDefinition left_arm_overlay = arm_control.addOrReplaceChild("left_arm_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition left_arm = left_arm_overlay.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(50, 68).addBox(-3.0F, -2.0F, -3.5F, 6.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(122, 56).addBox(-3.0F, 8.0F, -5.5F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition right_arm_overlay = arm_control.addOrReplaceChild("right_arm_overlay", CubeListBuilder.create(), PartPose.offset(-11.0F, 0.0F, 0.0F));
		PartDefinition right_arm = right_arm_overlay.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(50, 68).mirror().addBox(-3.0F, -2.0F, -3.5F, 6.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(122, 56).mirror().addBox(-3.0F, 8.0F, -5.5F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 8.0F));
		PartDefinition left_leg1_overlay = leg_control.addOrReplaceChild("left_leg1_overlay", CubeListBuilder.create(), PartPose.offset(9.5F, 0.0F, 0.0F));
		PartDefinition left_leg1 = left_leg1_overlay.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(104, 0).addBox(-5.0F, -6.0F, -7.0F, 9.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition left_leg2_overlay = left_leg1.addOrReplaceChild("left_leg2_overlay", CubeListBuilder.create(), PartPose.offset(-0.5F, 6.0F, 5.0F));
		PartDefinition left_leg2 = left_leg2_overlay.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(114, 119).addBox(-3.5F, -3.0F, -5.0F, 7.0F, 16.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(104, 56).addBox(-3.5F, 11.0F, -7.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition right_leg1_overlay = leg_control.addOrReplaceChild("right_leg1_overlay", CubeListBuilder.create(), PartPose.offset(-9.5F, 0.0F, 0.0F));
		PartDefinition right_leg1 = right_leg1_overlay.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(104, 0).mirror().addBox(-4.0F, -6.0F, -7.0F, 9.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition right_leg2_overlay = right_leg1.addOrReplaceChild("right_leg2_overlay", CubeListBuilder.create(), PartPose.offset(0.5F, 6.0F, 5.0F));
		PartDefinition right_leg2 = right_leg2_overlay.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(114, 119).mirror().addBox(-3.5F, -3.0F, -5.0F, 7.0F, 16.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(104, 56).mirror().addBox(-3.5F, 11.0F, -7.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Kentrosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (entity.isInWaterOrBubble()) {
			this.animateWalk(KentrosaurusAnimations.SWIM, limbSwing * 2, limbSwingAmount * 2, 2, 4);
		} else {
			this.animateWalk(KentrosaurusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
		}

		this.animate(entity.attack1AnimationState, KentrosaurusAnimations.ATTACK1, ageInTicks, 1.25F);
		this.animate(entity.attack2AnimationState, KentrosaurusAnimations.ATTACK2, ageInTicks, 1.25F);
		this.animate(entity.idleAnimationState, KentrosaurusIdleAnimations.IDLE, ageInTicks);
		this.animate(entity.layDownIdleAnimationState, KentrosaurusIdleAnimations.LAY_DOWN_IDLE, ageInTicks);
		this.animate(entity.layDownAnimationState, KentrosaurusIdleAnimations.LAY_DOWN, ageInTicks);
		this.animate(entity.standUpAnimationState, KentrosaurusIdleAnimations.STAND_UP, ageInTicks);
		this.animate(entity.grazeAnimationState, KentrosaurusIdleAnimations.GRAZE, ageInTicks);

		this.head.xRot += entity.isKentrosaurusLayingDown() ? 0F : (headPitch * ((float) Math.PI / 180F)) / 2;
		this.head.yRot += netHeadYaw * ((float) Math.PI / 180F) - (netHeadYaw * ((float) Math.PI / 180F)) / 2;
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