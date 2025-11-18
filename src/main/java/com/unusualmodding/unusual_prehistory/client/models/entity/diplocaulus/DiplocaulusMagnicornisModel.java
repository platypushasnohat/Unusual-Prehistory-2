package com.unusualmodding.unusual_prehistory.client.models.entity.diplocaulus;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.diplocaulus.DiplocaulusAnimations;
import com.unusualmodding.unusual_prehistory.client.animations.diplocaulus.DiplocaulusMagnicornisAnimations;
import com.unusualmodding.unusual_prehistory.entity.Diplocaulus;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DiplocaulusMagnicornisModel extends HierarchicalModel<Diplocaulus> {

	private final ModelPart root;
	private final ModelPart body_main;
	private final ModelPart body;
	private final ModelPart neck;
	private final ModelPart head;
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

	public DiplocaulusMagnicornisModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.body = this.body_main.getChild("body");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
		this.jaw = this.head.getChild("jaw");
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

		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 21).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 5.0F, 8.0F, new CubeDeformation(0.05F))
				.texOffs(0, 43).addBox(0.0F, -4.0F, -4.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0025F))
				.texOffs(0, 0).addBox(-5.0F, -2.05F, -7.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(32, 39).addBox(-1.5F, -1.0F, -4.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -4.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -3.0F));

		PartDefinition righthead_r1 = head.addOrReplaceChild("righthead_r1", CubeListBuilder.create().texOffs(48, 24).mirror().addBox(-8.0F, -2.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(34, 15).mirror().addBox(-8.0F, -2.0F, 0.0F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.825F, 0.0F, -2.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition lefthead_r1 = head.addOrReplaceChild("lefthead_r1", CubeListBuilder.create().texOffs(48, 28).addBox(6.0F, -2.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(24, 34).addBox(0.0F, -2.0F, 0.0F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.825F, 0.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition face_r1 = head.addOrReplaceChild("face_r1", CubeListBuilder.create().texOffs(0, 38).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -2.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jaw_r1 = jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(16, 39).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0025F))
				.texOffs(40, 0).addBox(-2.0F, -1.01F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 1.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 21).addBox(0.0F, -2.0F, -1.0F, 0.0F, 5.0F, 12.0F, new CubeDeformation(0.0025F))
				.texOffs(0, 10).addBox(-3.0F, -1.0F, 0.0F, 6.0F, 0.0F, 11.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -1.0F, 4.0F));

		PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.4F, -2.0F));

		PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(46, 34).addBox(0.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));

		PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(12, 44).addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(3.0F, 1.59F, 0.0F));

		PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(46, 34).mirror().addBox(-3.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 0.0F, 0.0F));

		PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(12, 44).mirror().addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-3.0F, 1.59F, 0.0F));

		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.4F, 3.0F));

		PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(46, 34).addBox(0.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));

		PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(12, 44).addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(3.0F, 1.59F, 0.0F));

		PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(46, 34).mirror().addBox(-3.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 0.0F, -1.0F));

		PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(12, 44).mirror().addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-3.0F, 1.59F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Diplocaulus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.isInWaterOrBubble()) {
			this.root.xRot = headPitch * (Mth.DEG_TO_RAD);
		} else {
			this.animateWalk(DiplocaulusMagnicornisAnimations.WALK, limbSwing, limbSwingAmount, 4, 8);
		}

		this.animate(entity.swimmingAnimationState, DiplocaulusMagnicornisAnimations.SWIM, ageInTicks, 0.5F + limbSwingAmount);
		this.animate(entity.idleAnimationState, DiplocaulusMagnicornisAnimations.IDLE, ageInTicks);
		this.animate(entity.quirkAnimationState, DiplocaulusMagnicornisAnimations.QUIRK, ageInTicks);
		this.animate(entity.burrowStartAnimationState, DiplocaulusMagnicornisAnimations.BURROW_START, ageInTicks);
		this.animate(entity.burrowIdleAnimationState, DiplocaulusMagnicornisAnimations.BURROW, ageInTicks);

		if (this.young) {
			this.applyStatic(DiplocaulusAnimations.BABY_TRANSFORM);
		}

		if (entity.isDiplocaulusBurrowed()) {
			this.body_main.y = 2;
		}

		if (!entity.isDiplocaulusBurrowed()) {
			this.head.xRot += headPitch * (Mth.DEG_TO_RAD) / 4;
		}
		this.head.yRot += netHeadYaw * (Mth.DEG_TO_RAD) / 4;
	}

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (this.young) {
            float babyScale = 0.5F;
            float bodyYOffset = 24.0F;
            poseStack.pushPose();
            poseStack.scale(babyScale, babyScale, babyScale);
            poseStack.translate(0.0F, bodyYOffset / 16.0F, 0.0F);
            this.root().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            poseStack.popPose();
        } else {
            this.root().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}