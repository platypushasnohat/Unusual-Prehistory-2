package com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.dunkleosteus.DunkleosteusMediumAnimations;
import com.unusualmodding.unusual_prehistory.client.models.entity.base.UP2Model;
import com.unusualmodding.unusual_prehistory.client.animations.dunkleosteus.DunkleosteusSmallAnimations;
import com.unusualmodding.unusual_prehistory.entity.DunkleosteusEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DunkleosteusSmallModel<T extends DunkleosteusEntity> extends UP2Model<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body_main;
	private final ModelPart body_overlay;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart upper_jaw_overlay;
	private final ModelPart upper_jaw;
	private final ModelPart jaw_overlay;
	private final ModelPart jaw;
	private final ModelPart dorsal;
	private final ModelPart left_front_fin;
	private final ModelPart right_front_fin;
	private final ModelPart left_back_fin;
	private final ModelPart right_back_fin;
	private final ModelPart tail1_overlay;
	private final ModelPart tail1;
	private final ModelPart tail2_overlay;
	private final ModelPart tail2;

	public DunkleosteusSmallModel(ModelPart root) {
		this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body_main = this.swim_control.getChild("body_main");
		this.body_overlay = this.body_main.getChild("body_overlay");
		this.body = this.body_overlay.getChild("body");
		this.head = this.body.getChild("head");
		this.upper_jaw_overlay = this.head.getChild("upper_jaw_overlay");
		this.upper_jaw = this.upper_jaw_overlay.getChild("upper_jaw");
		this.jaw_overlay = this.head.getChild("jaw_overlay");
		this.jaw = this.jaw_overlay.getChild("jaw");
		this.dorsal = this.body.getChild("dorsal");
		this.left_front_fin = this.body.getChild("left_front_fin");
		this.right_front_fin = this.body.getChild("right_front_fin");
		this.left_back_fin = this.body.getChild("left_back_fin");
		this.right_back_fin = this.body.getChild("right_back_fin");
		this.tail1_overlay = this.body.getChild("tail1_overlay");
		this.tail1 = this.tail1_overlay.getChild("tail1");
		this.tail2_overlay = this.tail1.getChild("tail2_overlay");
		this.tail2 = this.tail2_overlay.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));
		PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));
		PartDefinition body_overlay = body_main.addOrReplaceChild("body_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition body = body_overlay.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -2.5F, 6.0F, 8.0F, 7.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -2.0F));
		PartDefinition upper_jaw_overlay = head.addOrReplaceChild("upper_jaw_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition upper_jaw = upper_jaw_overlay.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(20, 15).addBox(-3.0F, -3.0F, -5.5F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0025F)).texOffs(-4, 41).addBox(-3.0F, -1.0F, -5.5F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)).texOffs(26, 0).addBox(-3.0F, -3.0F, -3.5F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition jaw_overlay = head.addOrReplaceChild("jaw_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition jaw = jaw_overlay.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(2, 47).addBox(-3.0F, 0.0F, -5.5F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.001F)).texOffs(-4, 41).addBox(-3.0F, 1.0F, -5.5F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition dorsal = body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(20, 28).addBox(0.0F, -3.0F, -4.5F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -3.0F, 4.0F));
		PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create().texOffs(34, 26).addBox(0.0F, -1.0F, -1.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.0F, 4.0F, -2.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create().texOffs(34, 26).mirror().addBox(0.0F, -1.0F, -1.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 4.0F, -2.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition left_back_fin = body.addOrReplaceChild("left_back_fin", CubeListBuilder.create().texOffs(27, 9).addBox(0.0F, -1.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.0F, 4.0F, 3.5F, 0.0F, 0.0F, -0.7854F));
		PartDefinition right_back_fin = body.addOrReplaceChild("right_back_fin", CubeListBuilder.create().texOffs(27, 9).mirror().addBox(0.0F, -1.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 4.0F, 3.5F, 0.0F, 0.0F, 0.7854F));
		PartDefinition tail1_overlay = body.addOrReplaceChild("tail1_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.5F));
		PartDefinition tail1 = tail1_overlay.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(2, 17).addBox(0.0F, -6.0F, -1.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition tail2_overlay = tail1.addOrReplaceChild("tail2_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 7.0F));
		PartDefinition tail2 = tail2_overlay.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(38, 6).addBox(0.0F, -4.0F, 0.0F, 0.0F, 8.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(DunkleosteusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.isInWater()) {
			this.animateWalk(DunkleosteusSmallAnimations.SWIM, limbSwing, limbSwingAmount, 2f, 2f);
		}

		this.animateIdle(entity.idleAnimationState, DunkleosteusSmallAnimations.IDLE, ageInTicks, 1.0F, 1.0F - Math.abs(limbSwingAmount));
		this.animate(entity.flopAnimationState, DunkleosteusSmallAnimations.FLOP, ageInTicks, 1.0F);
		this.animate(entity.attackAnimationState, DunkleosteusSmallAnimations.ATTACK, ageInTicks, 1.0F);
		this.animate(entity.yawnAnimationState, DunkleosteusSmallAnimations.YAWN, ageInTicks, 1.0F);

		this.swim_control.xRot = headPitch * (Mth.DEG_TO_RAD);
		this.swim_control.zRot = netHeadYaw * ((Mth.DEG_TO_RAD) / 2);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}