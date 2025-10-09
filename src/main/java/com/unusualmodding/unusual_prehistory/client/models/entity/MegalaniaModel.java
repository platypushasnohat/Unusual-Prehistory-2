package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.KentrosaurusAnimations;
import com.unusualmodding.unusual_prehistory.client.animations.megalania.*;
import com.unusualmodding.unusual_prehistory.entity.Megalania;
import com.unusualmodding.unusual_prehistory.entity.utils.Behaviors;
import com.unusualmodding.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class MegalaniaModel extends HierarchicalModel<Megalania> {

	private final ModelPart root;
	private final ModelPart body_main;
	private final ModelPart body_upper;
	private final ModelPart body;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart tongue;
	private final ModelPart tail1_rot;
	private final ModelPart tail1;
	private final ModelPart tail2_rot;
	private final ModelPart tail2;
	private final ModelPart tail3_rot;
	private final ModelPart tail3;
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

	public MegalaniaModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.body_upper = this.body_main.getChild("body_upper");
		this.body = this.body_upper.getChild("body");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.tongue = this.jaw.getChild("tongue");
		this.tail1_rot = this.body.getChild("tail1_rot");
		this.tail1 = this.tail1_rot.getChild("tail1");
		this.tail2_rot = this.tail1.getChild("tail2_rot");
		this.tail2 = this.tail2_rot.getChild("tail2");
		this.tail3_rot = this.tail2.getChild("tail3_rot");
		this.tail3 = this.tail3_rot.getChild("tail3");
		this.arm_control = this.body_upper.getChild("arm_control");
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

		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -13.5F, 0.0F));

		PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 9.5F));

		PartDefinition body = body_upper.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, -7.5F, -15.5F, 19.0F, 15.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, -9.5F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(89, 46).addBox(-3.0F, 0.0F, -13.0F, 6.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, -15.5F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(90, 111).addBox(-3.5F, 0.0F, -16.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(88, 70).addBox(-4.5F, 0.0F, -8.0F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.01F))
				.texOffs(102, 96).addBox(-2.5F, 2.5F, -15.0F, 5.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(28, 114).addBox(-4.5F, 3.0F, -4.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(60, 111).addBox(-3.5F, 0.0F, -12.0F, 7.0F, 4.0F, 8.0F, new CubeDeformation(0.01F))
				.texOffs(112, 114).addBox(-2.5F, -0.5F, -11.0F, 5.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(116, 81).addBox(-4.5F, 0.0F, -4.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 3.0F, -4.0F));

		PartDefinition tongue = jaw.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(11, 54).addBox(-1.5F, -0.01F, -9.0F, 3.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition tail1_rot = body.addOrReplaceChild("tail1_rot", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, 15.5F));

		PartDefinition tail1 = tail1_rot.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 46).addBox(-5.5F, -4.0F, -4.0F, 11.0F, 7.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail2_rot = tail1.addOrReplaceChild("tail2_rot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 29.0F));

		PartDefinition tail2 = tail2_rot.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(60, 86).addBox(-4.0F, -2.5F, 0.0F, 8.0F, 5.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail3_rot = tail2.addOrReplaceChild("tail3_rot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 20.0F));

		PartDefinition tail3 = tail3_rot.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 86).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 4.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arm_control = body_upper.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -19.0F));

		PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(100, 0).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 0.0F, 0.0F));

		PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(100, 36).addBox(-3.5F, 0.0F, -5.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 9.0F, -1.0F));

		PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(100, 0).mirror().addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5F, 0.0F, 0.0F));

		PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(100, 36).mirror().addBox(-4.5F, 0.0F, -5.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 9.0F, -1.0F));

		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 9.5F));

		PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(100, 18).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 0.0F, 0.0F));

		PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(0, 115).addBox(-4.0F, 0.0F, -5.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -1.0F));

		PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(100, 18).mirror().addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5F, 0.0F, 0.0F));

		PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(0, 115).mirror().addBox(-4.0F, 0.0F, -5.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 9.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Megalania entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.getPose() != UP2Poses.ROARING.get() && entity.getPose() != Pose.LONG_JUMPING) {
			if (!entity.isInWaterOrBubble()) {
				if (entity.getBehavior().equals(Behaviors.ANGRY.getName())) {
					if (entity.getDeltaMovement().horizontalDistance() > 1.0E-5F) {
						this.animateWalk(MegalaniaAnimations.RUN, limbSwing, limbSwingAmount, 1, 2);
					}
				} else {
					if (entity.getDeltaMovement().horizontalDistance() > 1.0E-5F) {
						this.animateWalk(MegalaniaAnimations.WALK, limbSwing, limbSwingAmount, 4, 8);
					}
				}
				this.animate(entity.idleAnimationState, MegalaniaAnimations.IDLE, ageInTicks);
			} else {
				this.root.xRot = headPitch * (Mth.DEG_TO_RAD) / 2;
				this.animate(entity.swimmingAnimationState, MegalaniaAnimations.SWIM, ageInTicks, 0.6F + limbSwingAmount * 1.5F);
			}
			this.head.xRot += (headPitch * ((float) Math.PI / 180)) / 4;
			this.head.yRot += (netHeadYaw * ((float) Math.PI / 180)) / 4;
			this.neck.xRot += (headPitch * ((float) Math.PI / 180)) / 2;
			this.neck.yRot += (netHeadYaw * ((float) Math.PI / 180)) / 4;
		}

		if (this.young) {
			this.applyStatic(MegalaniaAnimations.BABY_TRANSFORM);
		}

		this.animate(entity.yawningAnimationState, MegalaniaIdleAnimations.YAWN, ageInTicks);
		this.animate(entity.tongueAnimationState, MegalaniaIdleAnimations.TONGUE, ageInTicks);
		this.animate(entity.roaringAnimationState, MegalaniaIdleAnimations.ROAR, ageInTicks);
		this.animate(entity.bitingAnimationState, MegalaniaAnimations.BITE, ageInTicks);
		this.animate(entity.tailWhipAnimationState, MegalaniaAnimations.TAIL_WHIP, ageInTicks);
		this.animate(entity.leapingAnimationState, MegalaniaAnimations.LEAP, ageInTicks);
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