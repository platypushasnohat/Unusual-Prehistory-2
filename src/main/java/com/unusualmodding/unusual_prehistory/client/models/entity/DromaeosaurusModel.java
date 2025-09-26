package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.DromaeosaurusAnimations;
import com.unusualmodding.unusual_prehistory.entity.Dromaeosaurus;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DromaeosaurusModel extends HierarchicalModel<Dromaeosaurus> {

	private final ModelPart root;
	private final ModelPart body_main;
	private final ModelPart body;
	private final ModelPart breathing;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart tail1;
	private final ModelPart tail2;
	private final ModelPart leg_control;
	private final ModelPart left_leg1;
	private final ModelPart left_leg2;
	private final ModelPart left_leg3;
	private final ModelPart left_claw;
	private final ModelPart right_leg1;
	private final ModelPart right_leg2;
	private final ModelPart right_leg3;
	private final ModelPart right_claw;

	public DromaeosaurusModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.body = this.body_main.getChild("body");
		this.breathing = this.body.getChild("breathing");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.left_arm = this.body.getChild("left_arm");
		this.right_arm = this.body.getChild("right_arm");
		this.tail1 = this.body.getChild("tail1");
		this.tail2 = this.tail1.getChild("tail2");
		this.leg_control = this.body_main.getChild("leg_control");
		this.left_leg1 = this.leg_control.getChild("left_leg1");
		this.left_leg2 = this.left_leg1.getChild("left_leg2");
		this.left_leg3 = this.left_leg2.getChild("left_leg3");
		this.left_claw = this.left_leg3.getChild("left_claw");
		this.right_leg1 = this.leg_control.getChild("right_leg1");
		this.right_leg2 = this.right_leg1.getChild("right_leg2");
		this.right_leg3 = this.right_leg2.getChild("right_leg3");
		this.right_claw = this.right_leg3.getChild("right_claw");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 3.5F));

		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition breathing = body.addOrReplaceChild("breathing", CubeListBuilder.create().texOffs(0, 39).addBox(-4.0F, -7.0F, -5.0F, 6.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.0F, -3.5F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 56).addBox(-1.5F, -5.0F, -3.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0025F))
				.texOffs(32, 51).addBox(-1.5F, -10.0F, -1.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(34, 63).addBox(-1.5F, -10.01F, 3.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -8.5F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(46, 52).addBox(-2.5F, -1.01F, -4.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(14, 56).addBox(-1.5F, -1.01F, -8.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 2.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(62, 60).addBox(-1.5F, -0.01F, -4.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -4.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(64, 39).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(50, 39).addBox(0.99F, 1.0F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0025F))
				.texOffs(14, 63).addBox(-1.0F, 2.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(44, 64).addBox(-1.0F, 2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.0F, -6.5F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(64, 39).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(50, 39).mirror().addBox(-0.99F, 1.0F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false)
				.texOffs(14, 63).mirror().addBox(-1.0F, 2.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 64).mirror().addBox(-1.0F, 2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 1.0F, -6.5F));

		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 1.5F));

		PartDefinition tail_r1 = tail1.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(24, 24).mirror().addBox(-7.0F, 0.0F, 0.0F, 7.0F, 0.0F, 15.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition tail_r2 = tail1.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(24, 24).addBox(0.0F, 0.0F, 0.0F, 7.0F, 0.0F, 15.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 15.0F));

		PartDefinition tail_r3 = tail2.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(15, 0).mirror().addBox(-7.0F, 0.0F, 0.0F, 7.0F, 0.0F, 24.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition tail_r4 = tail2.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(15, 0).addBox(0.0F, 0.0F, 0.0F, 7.0F, 0.0F, 24.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(32, 39).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 0.0F, 0.0F));

		PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(26, 63).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 2.5F));

		PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(46, 60).addBox(-1.0F, 0.09F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 6.9F, 1.0F));

		PartDefinition left_claw = left_leg3.addOrReplaceChild("left_claw", CubeListBuilder.create().texOffs(28, 56).addBox(0.0F, -2.91F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offset(-1.0F, 0.0F, -2.0F));

		PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(32, 39).mirror().addBox(-2.0F, -2.0F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 0.0F, 0.0F));

		PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(26, 63).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.0F, 2.5F));

		PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(46, 60).mirror().addBox(-3.0F, 0.09F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 6.9F, 1.0F));

		PartDefinition right_claw = right_leg3.addOrReplaceChild("right_claw", CubeListBuilder.create().texOffs(28, 56).mirror().addBox(0.0F, -2.91F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(1.0F, 0.0F, -2.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Dromaeosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (this.young) {
			this.applyStatic(DromaeosaurusAnimations.BABY_TRANSFORM);
			this.animateWalk(DromaeosaurusAnimations.RUN, limbSwing, limbSwingAmount, 0.5F, 2);
		} else {
			this.animateWalk(DromaeosaurusAnimations.RUN, limbSwing, limbSwingAmount, 1, 2);
		}

		this.animate(entity.idleAnimationState, DromaeosaurusAnimations.IDLE, ageInTicks);
		this.animate(entity.biteAnimationState, DromaeosaurusAnimations.BITE, ageInTicks);
		this.animate(entity.fallAnimationState, DromaeosaurusAnimations.JUMP, ageInTicks);
		this.animate(entity.sleepStartAnimationState, DromaeosaurusAnimations.SLEEP_START, ageInTicks);
		this.animate(entity.sleepAnimationState, DromaeosaurusAnimations.SLEEP, ageInTicks);
		this.animate(entity.sleepEndAnimationState, DromaeosaurusAnimations.SLEEP_END, ageInTicks);

		this.neck.xRot += entity.isDromaeosaurusSleeping() ? 0F : (headPitch * ((float) Math.PI / 180F)) / 2;
		this.neck.yRot += netHeadYaw * ((float) Math.PI / 180F) - (netHeadYaw * ((float) Math.PI / 180F)) / 2;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
		if (this.young) {
			float babyScale = 0.6F;
			float bodyYOffset = 16.0F;
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