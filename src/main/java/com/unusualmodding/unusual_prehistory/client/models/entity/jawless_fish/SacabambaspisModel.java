package com.unusualmodding.unusual_prehistory.client.models.entity.jawless_fish;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.JawlessFishAnimations;
import com.unusualmodding.unusual_prehistory.entity.JawlessFish;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class SacabambaspisModel extends HierarchicalModel<JawlessFish> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body_sideways;
	private final ModelPart body;
	private final ModelPart tail1;
	private final ModelPart tail2;

	public SacabambaspisModel(ModelPart root) {
		this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body_sideways = this.swim_control.getChild("body_sideways");
		this.body = this.body_sideways.getChild("body");
		this.tail1 = this.body.getChild("tail1");
		this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -2.5F));
		PartDefinition body_sideways = swim_control.addOrReplaceChild("body_sideways", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.5F));
		PartDefinition body = body_sideways.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(3, 14).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -0.5F, 4.0F));
		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 9).addBox(0.0F, -3.0F, 0.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.5F, 6.0F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(JawlessFish entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		float prevOnLandProgress = entity.prevOnLandProgress;
		float onLandProgress = entity.onLandProgress;
		float partialTicks = ageInTicks - entity.tickCount;
		float landProgress = prevOnLandProgress + (onLandProgress - prevOnLandProgress) * partialTicks;

		this.animate(entity.swimmingAnimationState, JawlessFishAnimations.SWIM, ageInTicks, 0.5F + limbSwingAmount * 1.5F);
		this.animate(entity.floppingAnimationState, JawlessFishAnimations.FLOP, ageInTicks);

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