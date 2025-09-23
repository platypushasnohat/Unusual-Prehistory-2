package com.unusualmodding.unusual_prehistory.client.models.entity.diplocaulus;

import com.unusualmodding.unusual_prehistory.client.animations.diplocaulus.*;
import com.unusualmodding.unusual_prehistory.client.models.entity.base.UP2Model;
import com.unusualmodding.unusual_prehistory.entity.Diplocaulus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DiplocaulusMagnicornisModel extends UP2Model<Diplocaulus> {

	private final ModelPart root;
	private final ModelPart body_main;
	private final ModelPart body;
	private final ModelPart neck;
	private final ModelPart head;
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

	public DiplocaulusMagnicornisModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.body = this.body_main.getChild("body");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
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
		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -2.05F, 0.0F));
		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 18).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 5.0F, 8.0F, new CubeDeformation(0.05F)).texOffs(18, 10).addBox(0.0F, -4.0F, -4.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0025F)).texOffs(-10, 0).addBox(-5.0F, -2.05F, -7.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 31).addBox(-1.5F, -1.0F, -4.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -4.0F));
		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -3.0F));
		PartDefinition jaw_r1 = head.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(14, 31).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0025F)).texOffs(16, 0).addBox(-2.0F, -1.01F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).texOffs(0, 26).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -2.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition righthead_r1 = head.addOrReplaceChild("righthead_r1", CubeListBuilder.create().texOffs(16, 17).mirror().addBox(-8.0F, -2.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(12, 10).mirror().addBox(-8.0F, -2.0F, 0.0F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.825F, 0.0F, -2.0F, 0.0F, 0.7854F, 0.0F));
		PartDefinition lefthead_r1 = head.addOrReplaceChild("lefthead_r1", CubeListBuilder.create().texOffs(32, 13).addBox(6.0F, -2.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(20, 5).addBox(0.0F, -2.0F, 0.0F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.825F, 0.0F, -2.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 9).addBox(0.0F, -2.0F, -1.0F, 0.0F, 5.0F, 12.0F, new CubeDeformation(0.0025F)).texOffs(-11, 10).addBox(-3.0F, -1.0F, 0.0F, 6.0F, 0.0F, 11.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -1.0F, 4.0F));
		PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.4F, -2.0F));
		PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(28, 1).addBox(0.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));
		PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(10, 15).addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(3.0F, 1.59F, 0.0F));
		PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(28, 1).mirror().addBox(-3.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 0.0F, 0.0F));
		PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(10, 15).mirror().addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-3.0F, 1.59F, 0.0F));
		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.4F, 3.0F));
		PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(28, 1).addBox(0.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));
		PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(10, 15).addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(3.0F, 1.59F, 0.0F));
		PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(28, 1).mirror().addBox(-3.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 0.0F, -1.0F));
		PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(10, 15).mirror().addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-3.0F, 1.59F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Diplocaulus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.isInWaterOrBubble()) {
			this.animateWalk(DiplocaulusMagnicornisAnimations.SWIM, limbSwing, limbSwingAmount, 2, 4);
			this.animateIdle(entity.swimIdleAnimationState, DiplocaulusMagnicornisAnimations.SWIM_IDLE, ageInTicks, 1, 1 - Math.abs(limbSwingAmount));
			this.root.xRot = headPitch * (Mth.DEG_TO_RAD);
			this.body_main.resetPose();
		} else {
			this.animateWalk(DiplocaulusMagnicornisAnimations.WALK, limbSwing, limbSwingAmount, 4, 8);
			this.animateIdle(entity.idleAnimationState, DiplocaulusMagnicornisAnimations.IDLE, ageInTicks, 1, 1 - Math.abs(limbSwingAmount));
		}

		this.animate(entity.quirkAnimationState, DiplocaulusMagnicornisAnimations.QUIRK, ageInTicks);

		if (this.young) {
			this.applyStatic(DiplocaulusAnimations.BABY_TRANSFORM);
		}

		this.head.xRot += headPitch * (Mth.DEG_TO_RAD) - (headPitch * (Mth.DEG_TO_RAD)) / 4;
		this.head.yRot += netHeadYaw * (Mth.DEG_TO_RAD) - (netHeadYaw * (Mth.DEG_TO_RAD)) / 4;
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}