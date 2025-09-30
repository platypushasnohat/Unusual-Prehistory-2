package com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.dunkleosteus.DunkleosteusMediumAnimations;
import com.unusualmodding.unusual_prehistory.entity.Dunkleosteus;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DunkleosteusMediumModel extends HierarchicalModel<Dunkleosteus> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body_main;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart upper_jaw;
	private final ModelPart jaw;
	private final ModelPart dorsal;
	private final ModelPart left_front_fin;
	private final ModelPart right_front_fin;
	private final ModelPart left_back_fin;
	private final ModelPart right_back_fin;
	private final ModelPart tailRot;
	private final ModelPart tail1;
	private final ModelPart tailFinRot;
	private final ModelPart tail2;

	public DunkleosteusMediumModel(ModelPart root) {
		this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body_main = this.swim_control.getChild("body_main");
		this.body = this.body_main.getChild("body");
		this.head = this.body.getChild("head");
		this.upper_jaw = this.head.getChild("upper_jaw");
		this.jaw = this.head.getChild("jaw");
		this.dorsal = this.body.getChild("dorsal");
		this.left_front_fin = this.body.getChild("left_front_fin");
		this.right_front_fin = this.body.getChild("right_front_fin");
		this.left_back_fin = this.body.getChild("left_back_fin");
		this.right_back_fin = this.body.getChild("right_back_fin");
		this.tailRot = this.body.getChild("tailRot");
		this.tail1 = this.tailRot.getChild("tail1");
		this.tailFinRot = this.tail1.getChild("tailFinRot");
		this.tail2 = this.tailFinRot.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));
		PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(50, 30).addBox(-4.5F, -8.0F, -8.0F, 9.0F, 15.0F, 16.0F, new CubeDeformation(0.2F)).texOffs(0, 30).addBox(-4.5F, -8.0F, -8.0F, 9.0F, 15.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, -8.0F));
		PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(54, 0).addBox(-3.5F, -5.0F, -9.0F, 7.0F, 4.0F, 12.0F, new CubeDeformation(0.06F)).texOffs(46, 90).addBox(-2.5F, -5.0F, -10.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.06F)).texOffs(54, 16).addBox(-3.5F, -1.0F, -9.0F, 7.0F, 2.0F, 12.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(34, 61).addBox(-3.5F, 0.0F, -9.0F, 7.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(72, 61).addBox(-3.5F, 3.0F, -9.0F, 7.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition dorsal = body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(72, 72).addBox(-1.0F, -4.5F, -5.0F, 2.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 5.0F, 0.7854F, 0.0F, 0.0F));
		PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create().texOffs(34, 76).addBox(0.0F, -1.0F, -3.0F, 11.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(3, 100).mirror().addBox(0.25F, -1.0F, -3.25F, 11.0F, 2.0F, 5.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(4.5F, 6.0F, -3.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create().texOffs(34, 76).mirror().addBox(-11.0F, -1.0F, -3.0F, 11.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(3, 100).addBox(-10.75F, -1.0F, -3.25F, 11.0F, 2.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-4.5F, 6.0F, -3.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition left_back_fin = body.addOrReplaceChild("left_back_fin", CubeListBuilder.create().texOffs(46, 83).mirror().addBox(0.0F, -1.0F, -1.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 6.0F, 6.5F, 0.0F, 0.0F, 0.7854F));
		PartDefinition right_back_fin = body.addOrReplaceChild("right_back_fin", CubeListBuilder.create().texOffs(46, 83).addBox(-4.0F, -1.0F, -1.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 6.0F, 6.5F, 0.0F, 0.0F, -0.7854F));
		PartDefinition tailRot = body.addOrReplaceChild("tailRot", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, 8.0F));
		PartDefinition tail1 = tailRot.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 61).addBox(-2.5F, -3.5F, 0.0F, 5.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition fin_r1 = tail1.addOrReplaceChild("fin_r1", CubeListBuilder.create().texOffs(12, 87).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.5F, 4.0F, 0.2618F, 0.0F, 0.0F));
		PartDefinition tailFinRot = tail1.addOrReplaceChild("tailFinRot", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 9.0F));
		PartDefinition tail2 = tailFinRot.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6981F, 0.0F, 0.0F));
		PartDefinition fluke_r1 = tail2.addOrReplaceChild("fluke_r1", CubeListBuilder.create().texOffs(32, 83).addBox(-1.0F, -3.0F, -4.5F, 2.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 4.5F, -0.2618F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Dunkleosteus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		float prevOnLandProgress = entity.prevOnLandProgress;
		float onLandProgress = entity.onLandProgress;
		float partialTicks = ageInTicks - entity.tickCount;
		float landProgress = prevOnLandProgress + (onLandProgress - prevOnLandProgress) * partialTicks;

		this.animate(entity.swimmingAnimationState, DunkleosteusMediumAnimations.SWIM, ageInTicks, 0.5F + limbSwingAmount * 1.25F);
		this.animate(entity.floppingAnimationState, DunkleosteusMediumAnimations.FLOP, ageInTicks);
		this.animate(entity.bitingAnimationState, DunkleosteusMediumAnimations.ATTACK, ageInTicks);
		this.animate(entity.yawningAnimationState, DunkleosteusMediumAnimations.YAWN, ageInTicks);

		this.swim_control.xRot = headPitch * (Mth.DEG_TO_RAD);
		this.swim_control.zRot += landProgress * ((float) Math.toRadians(90) / 5F);
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