package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.TalpanasAnimations;
import com.unusualmodding.unusual_prehistory.client.animations.TelecrexAnimations;
import com.unusualmodding.unusual_prehistory.entity.Talpanas;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class TalpanasModel extends HierarchicalModel<Talpanas> {

	private final ModelPart root;
	private final ModelPart body_main;
	private final ModelPart leg_control;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart left_wing;
	private final ModelPart right_wing;

	public TalpanasModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.leg_control = this.body_main.getChild("leg_control");
		this.left_leg = this.leg_control.getChild("left_leg");
		this.right_leg = this.leg_control.getChild("right_leg");
		this.body = this.body_main.getChild("body");
		this.head = this.body.getChild("head");
		this.tail = this.body.getChild("tail");
		this.left_wing = this.body.getChild("left_wing");
		this.right_wing = this.body.getChild("right_wing");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));
		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(2.5F, 3.0F, 3.0F));
		PartDefinition left_leg = leg_control.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(20, 33).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)).texOffs(10, 30).addBox(-1.5F, 3.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition right_leg = leg_control.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(20, 33).mirror().addBox(-0.5F, -1.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false).texOffs(10, 30).mirror().addBox(-1.5F, 3.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-5.0F, 0.0F, 0.0F));
		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -5.0F, 8.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 18).addBox(-2.0F, -3.0F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 30).addBox(-1.5F, -2.0F, -3.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 27).addBox(-2.5F, -2.0F, -5.5F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -3.5F));
		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 18).addBox(-2.5F, -4.0F, 0.01F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 5.0F));
		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(20, 24).addBox(0.0F, 0.0F, -1.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -3.0F, -1.0F));
		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(20, 24).mirror().addBox(-1.0F, 0.0F, -1.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -3.0F, -1.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Talpanas entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

		if (!entity.isInWaterOrBubble()) {
			if (this.young) {
				this.animateWalk(TalpanasAnimations.WALK, limbSwing, limbSwingAmount, 1, 2);
			} else {
				this.animateWalk(TalpanasAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
			}
		}

		if (this.young) {
			this.applyStatic(TelecrexAnimations.BABY_TRANSFORM);
		}

        this.animate(entity.idleAnimationState, TalpanasAnimations.IDLE, ageInTicks);
        this.animate(entity.flapAnimationState, TalpanasAnimations.FLAP, ageInTicks);
		this.animate(entity.swimmingAnimationState, TalpanasAnimations.SWIM, ageInTicks, 0.6F + limbSwingAmount * 1.5F);
		this.animate(entity.peckingAnimationState, TalpanasAnimations.PECK, ageInTicks);

		this.head.xRot += headPitch * Mth.DEG_TO_RAD / 2;
		this.head.yRot += netHeadYaw * Mth.DEG_TO_RAD / 2;
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