package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.KentrosaurusAnimations;
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
	private final ModelPart arm_control;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart leg_control;
	private final ModelPart left_leg1;
	private final ModelPart left_leg2;
	private final ModelPart right_leg1;
	private final ModelPart right_leg2;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart right_neckplates;
	private final ModelPart left_neckplates;
	private final ModelPart tail1;
	private final ModelPart left_thagomizer2;
	private final ModelPart right_thagomizer2;
	private final ModelPart tail2;
	private final ModelPart left_thagomizer3;
	private final ModelPart left_thagomizer4;
	private final ModelPart left_thagomizer5;
	private final ModelPart left_thagomizer6;
	private final ModelPart right_thagomizer3;
	private final ModelPart right_thagomizer4;
	private final ModelPart right_thagomizer5;
	private final ModelPart right_thagomizer6;
	private final ModelPart right_thagomizer1;
	private final ModelPart left_thagomizer1;
	private final ModelPart right_backplates;
	private final ModelPart left_backplates;
	private final ModelPart right_shoulder;
	private final ModelPart left_shoulder;

	public KentrosaurusModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.arm_control = this.body_main.getChild("arm_control");
		this.left_arm = this.arm_control.getChild("left_arm");
		this.right_arm = this.arm_control.getChild("right_arm");
		this.leg_control = this.body_main.getChild("leg_control");
		this.left_leg1 = this.leg_control.getChild("left_leg1");
		this.left_leg2 = this.left_leg1.getChild("left_leg2");
		this.right_leg1 = this.leg_control.getChild("right_leg1");
		this.right_leg2 = this.right_leg1.getChild("right_leg2");
		this.body = this.body_main.getChild("body");
		this.head = this.body.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.right_neckplates = this.head.getChild("right_neckplates");
		this.left_neckplates = this.head.getChild("left_neckplates");
		this.tail1 = this.body.getChild("tail1");
		this.left_thagomizer2 = this.tail1.getChild("left_thagomizer2");
		this.right_thagomizer2 = this.tail1.getChild("right_thagomizer2");
		this.tail2 = this.tail1.getChild("tail2");
		this.left_thagomizer3 = this.tail2.getChild("left_thagomizer3");
		this.left_thagomizer4 = this.tail2.getChild("left_thagomizer4");
		this.left_thagomizer5 = this.tail2.getChild("left_thagomizer5");
		this.left_thagomizer6 = this.tail2.getChild("left_thagomizer6");
		this.right_thagomizer3 = this.tail2.getChild("right_thagomizer3");
		this.right_thagomizer4 = this.tail2.getChild("right_thagomizer4");
		this.right_thagomizer5 = this.tail2.getChild("right_thagomizer5");
		this.right_thagomizer6 = this.tail2.getChild("right_thagomizer6");
		this.right_thagomizer1 = this.body.getChild("right_thagomizer1");
		this.left_thagomizer1 = this.body.getChild("left_thagomizer1");
		this.right_backplates = this.body.getChild("right_backplates");
		this.left_backplates = this.body.getChild("left_backplates");
		this.right_shoulder = this.body.getChild("right_shoulder");
		this.left_shoulder = this.body.getChild("left_shoulder");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -19.0F, 0.0F));
		PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(5.5F, 9.0F, -10.5F));
		PartDefinition left_arm = arm_control.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(41, 60).addBox(-3.0F, -2.0F, -3.5F, 6.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(17, 29).addBox(-3.0F, 8.0F, -5.5F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition right_arm = arm_control.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(41, 60).mirror().addBox(-3.0F, -2.0F, -3.5F, 6.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(17, 29).mirror().addBox(-3.0F, 8.0F, -5.5F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-11.0F, 0.0F, 0.0F));
		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 8.0F));
		PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(71, 7).addBox(-5.0F, -6.0F, -7.0F, 9.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 0.0F, 0.0F));
		PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(2, 67).addBox(-3.5F, -3.0F, -5.0F, 7.0F, 16.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(15, 25).addBox(-3.5F, 11.0F, -7.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 6.0F, 5.0F));
		PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(71, 7).mirror().addBox(-4.0F, -6.0F, -7.0F, 9.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5F, 0.0F, 0.0F));
		PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(2, 67).mirror().addBox(-3.5F, -3.0F, -5.0F, 7.0F, 16.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(15, 25).mirror().addBox(-3.5F, 11.0F, -7.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, 6.0F, 5.0F));
		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, -18.0F, -16.0F, 19.0F, 27.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(56, 70).addBox(-1.5F, 0.0F, -11.0F, 3.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(61, 91).addBox(-2.5F, -5.0F, -9.0F, 5.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-2.5F, -5.0F, -15.0F, 5.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(4, 10).addBox(-2.5F, -2.0F, -13.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -16.0F));
		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(12, 15).addBox(-2.5F, 0.0F, -4.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)).texOffs(2, 15).addBox(-2.5F, 1.0F, -6.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -2.0F, -9.0F));
		PartDefinition right_neckplates = head.addOrReplaceChild("right_neckplates", CubeListBuilder.create(), PartPose.offset(-1.5F, -5.1F, -4.0F));
		PartDefinition plates_r1 = right_neckplates.addOrReplaceChild("plates_r1", CubeListBuilder.create().texOffs(46, 90).mirror().addBox(-1.0F, -5.0F, -1.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.9653F, -0.197F, -3.0F, 0.0F, 0.0F, -0.1745F));
		PartDefinition left_neckplates = head.addOrReplaceChild("left_neckplates", CubeListBuilder.create(), PartPose.offset(1.5F, -5.1F, -4.0F));
		PartDefinition plates_r2 = left_neckplates.addOrReplaceChild("plates_r2", CubeListBuilder.create().texOffs(46, 90).addBox(1.0F, -5.0F, -1.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-0.9653F, -0.197F, -3.0F, 0.0F, 0.0F, 0.1745F));
		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(85, 70).addBox(-4.5F, -2.5F, 0.0F, 9.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.5F, 17.0F));
		PartDefinition left_thagomizer2 = tail1.addOrReplaceChild("left_thagomizer2", CubeListBuilder.create().texOffs(1, 74).addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.5F, -1.5F, 5.0F, 0.0F, 0.0F, 0.48F));
		PartDefinition right_thagomizer2 = tail1.addOrReplaceChild("right_thagomizer2", CubeListBuilder.create().texOffs(1, 74).mirror().addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -1.5F, 5.0F, 0.0F, 0.0F, -0.48F));
		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 60).addBox(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));
		PartDefinition left_thagomizer3 = tail2.addOrReplaceChild("left_thagomizer3", CubeListBuilder.create().texOffs(1, 74).addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.0F, -1.75F, 2.0F, 0.0F, 0.0F, 0.6109F));
		PartDefinition left_thagomizer4 = tail2.addOrReplaceChild("left_thagomizer4", CubeListBuilder.create().texOffs(1, 74).addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.0F, -1.75F, 10.0F, 0.0F, 0.0F, 0.829F));
		PartDefinition left_thagomizer5 = tail2.addOrReplaceChild("left_thagomizer5", CubeListBuilder.create().texOffs(1, 74).addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.0F, -2.0F, 19.0F, 0.0F, 0.0F, 1.0472F));
		PartDefinition left_thagomizer6 = tail2.addOrReplaceChild("left_thagomizer6", CubeListBuilder.create().texOffs(1, 74).addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.0F, -2.0F, 27.0F, 0.0F, 0.0F, 1.2654F));
		PartDefinition right_thagomizer3 = tail2.addOrReplaceChild("right_thagomizer3", CubeListBuilder.create().texOffs(1, 74).mirror().addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -1.75F, 2.0F, 0.0F, 0.0F, -0.6109F));
		PartDefinition right_thagomizer4 = tail2.addOrReplaceChild("right_thagomizer4", CubeListBuilder.create().texOffs(1, 74).mirror().addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -1.75F, 10.0F, 0.0F, 0.0F, -0.829F));
		PartDefinition right_thagomizer5 = tail2.addOrReplaceChild("right_thagomizer5", CubeListBuilder.create().texOffs(1, 74).mirror().addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -2.0F, 19.0F, 0.0F, 0.0F, -1.0472F));
		PartDefinition right_thagomizer6 = tail2.addOrReplaceChild("right_thagomizer6", CubeListBuilder.create().texOffs(1, 74).mirror().addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -2.0F, 27.0F, 0.0F, 0.0F, -1.2654F));
		PartDefinition right_thagomizer1 = body.addOrReplaceChild("right_thagomizer1", CubeListBuilder.create().texOffs(1, 74).mirror().addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -16.0F, 16.0F, 0.0F, 0.0F, -0.1745F));
		PartDefinition left_thagomizer1 = body.addOrReplaceChild("left_thagomizer1", CubeListBuilder.create().texOffs(1, 74).addBox(0.0F, -13.0F, -1.0F, 0.0F, 14.0F, 22.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.5F, -16.0F, 16.0F, 0.0F, 0.0F, 0.1745F));
		PartDefinition right_backplates = body.addOrReplaceChild("right_backplates", CubeListBuilder.create().texOffs(67, 35).mirror().addBox(0.0F, -9.0F, -14.0F, 0.0F, 9.0F, 26.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.5F, -18.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
		PartDefinition left_backplates = body.addOrReplaceChild("left_backplates", CubeListBuilder.create().texOffs(67, 35).addBox(0.0F, -9.0F, -14.0F, 0.0F, 9.0F, 26.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.5F, -18.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
		PartDefinition right_shoulder = body.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(18, 6).mirror().addBox(-19.0F, -1.0F, 1.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(72, 0).mirror().addBox(-19.0F, -1.0F, -1.5F, 21.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.5F, 3.0F, -10.5F, -0.0873F, 0.3491F, 0.6109F));
		PartDefinition left_shoulder = body.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0F, -1.0F, -1.5F, 21.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(18, 6).addBox(16.0F, -1.0F, 1.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5F, 3.0F, -10.5F, -0.0873F, -0.3491F, -0.6109F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Kentrosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (!entity.isInWaterOrBubble()) {
			if (this.young) {
				this.animateWalk(KentrosaurusAnimations.WALK, limbSwing, limbSwingAmount, 1, 4);
			} else {
				this.animateWalk(KentrosaurusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
			}
		}

		this.animate(entity.swimmingAnimationState, KentrosaurusAnimations.SWIM, ageInTicks, 0.7F + limbSwingAmount * 0.5F);
		this.animate(entity.attack1AnimationState, KentrosaurusAnimations.ATTACK1, ageInTicks);
		this.animate(entity.attack2AnimationState, KentrosaurusAnimations.ATTACK2, ageInTicks);
		this.animate(entity.idleAnimationState, KentrosaurusAnimations.IDLE, ageInTicks);
		this.animate(entity.layDownIdleAnimationState, KentrosaurusAnimations.LAY_DOWN_IDLE, ageInTicks);
		this.animate(entity.layDownAnimationState, KentrosaurusAnimations.LAY_DOWN, ageInTicks);
		this.animate(entity.standUpAnimationState, KentrosaurusAnimations.STAND_UP, ageInTicks);
		this.animate(entity.grazeAnimationState, KentrosaurusAnimations.GRAZE, ageInTicks);
		this.animate(entity.shakeAnimationState, KentrosaurusAnimations.SHAKE, ageInTicks);

		if (this.young) {
			this.applyStatic(KentrosaurusAnimations.BABY_TRANSFORM);
		}

		this.head.xRot += entity.isKentrosaurusLayingDown() ? 0F : (headPitch * ((float) Math.PI / 180F)) / 2;
		this.head.yRot += netHeadYaw * ((float) Math.PI / 180F) - (netHeadYaw * ((float) Math.PI / 180F)) / 2;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
		if (this.young) {
			float babyScale = 0.5F;
			float bodyYOffset = 24.0F;
			poseStack.pushPose();
			poseStack.scale(babyScale, babyScale, babyScale);
			poseStack.translate(0.0F, bodyYOffset / 16.0f, 0.0F);
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