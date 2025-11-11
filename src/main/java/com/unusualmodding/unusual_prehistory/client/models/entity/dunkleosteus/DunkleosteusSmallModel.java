package com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.dunkleosteus.DunkleosteusSmallAnimations;
import com.unusualmodding.unusual_prehistory.entity.Dunkleosteus;
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
public class DunkleosteusSmallModel extends HierarchicalModel<Dunkleosteus> {

	private final ModelPart root;
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
	private final ModelPart tail1;
	private final ModelPart tail2;

	public DunkleosteusSmallModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.body = this.body_main.getChild("body");
		this.head = this.body.getChild("head");
		this.upper_jaw = this.head.getChild("upper_jaw");
		this.jaw = this.head.getChild("jaw");
		this.dorsal = this.body.getChild("dorsal");
		this.left_front_fin = this.body.getChild("left_front_fin");
		this.right_front_fin = this.body.getChild("right_front_fin");
		this.left_back_fin = this.body.getChild("left_back_fin");
		this.right_back_fin = this.body.getChild("right_back_fin");
		this.tail1 = this.body.getChild("tail1");
		this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));
		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -2.5F, 6.0F, 8.0F, 7.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -2.0F));
		PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(16, 15).addBox(-3.0F, -3.0F, -5.5F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0025F)).texOffs(13, 1).addBox(-3.0F, -1.0F, -5.5F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)).texOffs(28, 5).addBox(-3.0F, -3.0F, -3.5F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(16, 24).addBox(-3.0F, 0.0F, -5.5F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.001F)).texOffs(13, 1).addBox(-3.0F, 1.0F, -5.5F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition dorsal = body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(8, 18).addBox(0.0F, -3.0F, -4.5F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -3.0F, 4.0F));
		PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create().texOffs(0, 21).addBox(0.0F, -1.0F, -1.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.0F, 4.0F, -2.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create().texOffs(0, 21).mirror().addBox(0.0F, -1.0F, -1.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 4.0F, -2.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition left_back_fin = body.addOrReplaceChild("left_back_fin", CubeListBuilder.create().texOffs(3, 2).addBox(0.0F, -1.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.0F, 4.0F, 3.5F, 0.0F, 0.0F, -0.7854F));
		PartDefinition right_back_fin = body.addOrReplaceChild("right_back_fin", CubeListBuilder.create().texOffs(3, 2).mirror().addBox(0.0F, -1.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 4.0F, 3.5F, 0.0F, 0.0F, 0.7854F));
		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -6.0F, -1.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 4.5F));
		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(34, 7).addBox(0.0F, -4.0F, 0.0F, 0.0F, 8.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 7.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Dunkleosteus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		float prevOnLandProgress = entity.prevOnLandProgress;
		float onLandProgress = entity.onLandProgress;
		float partialTicks = ageInTicks - entity.tickCount;
		float landProgress = prevOnLandProgress + (onLandProgress - prevOnLandProgress) * partialTicks;

		this.animate(entity.swimmingAnimationState, DunkleosteusSmallAnimations.SWIM, ageInTicks, 0.7F + (Mth.clamp(limbSwingAmount, 0.45F, 1.0F) * 1.3F));
		this.animate(entity.floppingAnimationState, DunkleosteusSmallAnimations.FLOP, ageInTicks);
		this.animate(entity.bitingAnimationState, DunkleosteusSmallAnimations.ATTACK, ageInTicks);
		this.animate(entity.yawningAnimationState, DunkleosteusSmallAnimations.YAWN, ageInTicks);

		this.root.xRot = headPitch * (Mth.DEG_TO_RAD);
		this.root.zRot += landProgress * ((float) Math.toRadians(90) / 5F);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
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
	public @NotNull ModelPart root() {
		return this.root;
	}
}