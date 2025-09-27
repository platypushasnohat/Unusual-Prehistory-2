package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.TelecrexAnimations;
import com.unusualmodding.unusual_prehistory.entity.Telecrex;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class TelecrexModel extends HierarchicalModel<Telecrex> {

	private final ModelPart root;
	private final ModelPart body_main;
	private final ModelPart leg_control;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart left_wing;
	private final ModelPart right_wing;
	private final ModelPart tail;

	public TelecrexModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.leg_control = this.body_main.getChild("leg_control");
		this.left_leg = this.leg_control.getChild("left_leg");
		this.right_leg = this.leg_control.getChild("right_leg");
		this.body = this.body_main.getChild("body");
		this.head = this.body.getChild("head");
		this.left_wing = this.body.getChild("left_wing");
		this.right_wing = this.body.getChild("right_wing");
		this.tail = this.body.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -8.5F, 0.0F));
		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 4.5F, 2.0F));
		PartDefinition left_leg = leg_control.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 0.0F, 0.0F));
		PartDefinition right_leg = leg_control.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 23).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 0.0F, 0.0F));
		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -7.0F, 8.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.5F, 2.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(6, 0).addBox(0.0F, -8.0F, -2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0025F)).texOffs(0, 19).addBox(-1.5F, -3.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-0.5F, -5.0F, -4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(6, 0).addBox(-0.5F, -3.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 25).addBox(-1.5F, -6.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(2, 4).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -7.0F));
		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(24, 6).addBox(-0.5F, -7.0F, 0.0F, 1.0F, 7.0F, 13.0F, new CubeDeformation(0.01F)), PartPose.offset(4.5F, -3.0F, -5.0F));
		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(24, 6).mirror().addBox(-0.5F, -7.0F, 0.0F, 1.0F, 7.0F, 13.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-4.5F, -3.0F, -5.0F));
		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(4, 19).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -9.0F, 3.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Telecrex entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (!entity.isFlying()) {
			if (this.young) {
				this.animateWalk(TelecrexAnimations.WALK, limbSwing, limbSwingAmount, 1, 2);
			} else {
				this.animateWalk(TelecrexAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
			}
		}

		this.animate(entity.idleAnimationState, TelecrexAnimations.IDLE, ageInTicks);
		this.animate(entity.flyingAnimationState, TelecrexAnimations.FLY, ageInTicks);
		this.animate(entity.lookoutAnimationState, TelecrexAnimations.LOOKOUT, ageInTicks);
		this.animate(entity.peckingAnimationState, TelecrexAnimations.PECK, ageInTicks);

		this.head.xRot += headPitch * Mth.DEG_TO_RAD / 2;
		this.head.yRot += netHeadYaw * Mth.DEG_TO_RAD / 2;

		float partialTicks = ageInTicks - entity.tickCount;
		float flyProgress = entity.getFlyProgress(partialTicks);
		float rollAmount = entity.getFlightRoll(partialTicks) / 57.295776F * flyProgress;
		float flightPitchAmount = entity.getFlightPitch(partialTicks) / 57.295776F * flyProgress;

		body_main.xRot += flightPitchAmount / 2;
		body_main.zRot += rollAmount / 2;

		if (this.young) {
			this.applyStatic(TelecrexAnimations.BABY_TRANSFORM);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
		if (this.young) {
			float babyScale = 0.6f;
			float bodyYOffset = 16.0f;
			poseStack.pushPose();
			poseStack.scale(babyScale, babyScale, babyScale);
			poseStack.translate(0.0f, bodyYOffset / 16.0f, 0.0f);
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