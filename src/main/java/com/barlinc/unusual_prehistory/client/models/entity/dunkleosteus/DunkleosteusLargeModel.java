package com.barlinc.unusual_prehistory.client.models.entity.dunkleosteus;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.barlinc.unusual_prehistory.client.animations.dunkleosteus.DunkleosteusLargeAnimations;
import com.barlinc.unusual_prehistory.entity.Dunkleosteus;
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
public class DunkleosteusLargeModel extends HierarchicalModel<Dunkleosteus> {

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

	public DunkleosteusLargeModel(ModelPart root) {
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
		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, 0.0F));
		PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-12.5F, -18.0F, -16.0F, 25.0F, 35.0F, 32.0F, new CubeDeformation(0.2F)).texOffs(0, 67).addBox(-12.5F, -18.0F, -16.0F, 25.0F, 35.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, -16.0F));
		PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(82, 78).addBox(-6.5F, -7.0F, -11.0F, 13.0F, 7.0F, 14.0F, new CubeDeformation(0.06F)).texOffs(82, 0).addBox(-6.5F, 0.0F, -11.0F, 13.0F, 5.0F, 14.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(114, 99).addBox(-6.5F, -4.0F, -11.0F, 13.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(122, 78).addBox(-6.5F, 0.0F, -11.0F, 13.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));
		PartDefinition dorsal = body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(0, 79).addBox(-1.0F, -3.5F, -4.0F, 2.0F, 7.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.0F, 16.0F, 0.7854F, 0.0F, 0.0F));
		PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create().texOffs(0, 134).addBox(0.0F, -1.0F, -5.0F, 18.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(82, 20).addBox(0.0F, -1.0F, -5.0F, 18.0F, 2.0F, 10.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(12.5F, 16.0F, -2.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create().texOffs(82, 20).mirror().addBox(-18.0F, -1.0F, -5.0F, 18.0F, 2.0F, 10.0F, new CubeDeformation(0.1F)).mirror(false).texOffs(0, 134).mirror().addBox(-18.0F, -1.0F, -5.0F, 18.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.5F, 16.0F, -2.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition left_back_fin = body.addOrReplaceChild("left_back_fin", CubeListBuilder.create().texOffs(6, 13).addBox(0.0F, -1.0F, -1.5F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.5F, 16.0F, 14.5F, 0.0F, 0.0F, 0.7854F));
		PartDefinition right_back_fin = body.addOrReplaceChild("right_back_fin", CubeListBuilder.create().texOffs(6, 13).mirror().addBox(-6.0F, -1.0F, -1.5F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.5F, 16.0F, 14.5F, 0.0F, 0.0F, -0.7854F));
		PartDefinition tailRot = body.addOrReplaceChild("tailRot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 16.0F));
		PartDefinition tail1 = tailRot.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(122, 45).addBox(-5.5F, -5.5F, 0.0F, 11.0F, 11.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition fin_r1 = tail1.addOrReplaceChild("fin_r1", CubeListBuilder.create().texOffs(18, 22).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.5F, 8.0F, 0.2618F, 0.0F, 0.0F));
		PartDefinition tailFinRot = tail1.addOrReplaceChild("tailFinRot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 19.0F));
		PartDefinition tail2 = tailFinRot.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(114, 0).addBox(-1.5F, -3.0F, 0.0F, 3.0F, 8.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));
		PartDefinition fluke_r1 = tail2.addOrReplaceChild("fluke_r1", CubeListBuilder.create().texOffs(122, 48).addBox(-1.0F, -3.0F, -4.5F, 2.0F, 10.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 4.5F, -0.2618F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Dunkleosteus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		float prevOnLandProgress = entity.prevOnLandProgress;
		float onLandProgress = entity.onLandProgress;
		float partialTicks = ageInTicks - entity.tickCount;
		float landProgress = prevOnLandProgress + (onLandProgress - prevOnLandProgress) * partialTicks;

		this.animate(entity.swimmingAnimationState, DunkleosteusLargeAnimations.SWIM, ageInTicks, 0.7F + (Mth.clamp(limbSwingAmount, 0.45F, 1.0F) * 1.3F));
		this.animate(entity.floppingAnimationState, DunkleosteusLargeAnimations.FLOP, ageInTicks);
		this.animate(entity.bitingAnimationState, DunkleosteusLargeAnimations.ATTACK, ageInTicks);
		this.animate(entity.yawningAnimationState, DunkleosteusLargeAnimations.YAWN, ageInTicks);

		this.swim_control.xRot = headPitch * (Mth.DEG_TO_RAD);
		this.swim_control.zRot += landProgress * ((float) Math.toRadians(90) / 5F);
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