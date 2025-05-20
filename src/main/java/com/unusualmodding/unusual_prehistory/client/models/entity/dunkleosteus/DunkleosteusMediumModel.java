package com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.models.entity.base.UP2Model;
import com.unusualmodding.unusual_prehistory.client.animations.dunkleosteus.DunkleosteusMediumAnimations;
import com.unusualmodding.unusual_prehistory.entity.DunkleosteusEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DunkleosteusMediumModel<T extends DunkleosteusEntity> extends UP2Model<T> {

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

	public DunkleosteusMediumModel(ModelPart root) {
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
		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));
		PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));
		PartDefinition body_overlay = body_main.addOrReplaceChild("body_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition body = body_overlay.addOrReplaceChild("body", CubeListBuilder.create().texOffs(50, 30).addBox(-4.5F, -8.0F, -8.0F, 9.0F, 15.0F, 16.0F, new CubeDeformation(0.2F)).texOffs(0, 30).addBox(-4.5F, -8.0F, -8.0F, 9.0F, 15.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, -8.0F));
		PartDefinition upper_jaw_overlay = head.addOrReplaceChild("upper_jaw_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition upper_jaw = upper_jaw_overlay.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(54, 0).addBox(-3.5F, -5.0F, -9.0F, 7.0F, 4.0F, 12.0F, new CubeDeformation(0.06F)).texOffs(46, 90).addBox(-2.5F, -5.0F, -10.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.06F)).texOffs(54, 16).addBox(-3.5F, -1.0F, -9.0F, 7.0F, 2.0F, 12.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition jaw_overlay = head.addOrReplaceChild("jaw_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition jaw = jaw_overlay.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(34, 61).addBox(-3.5F, 0.0F, -9.0F, 7.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(72, 61).addBox(-3.5F, 3.0F, -9.0F, 7.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition dorsal = body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(72, 72).addBox(-1.0F, -4.5F, -5.0F, 2.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 5.0F, 0.7854F, 0.0F, 0.0F));
		PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create().texOffs(34, 76).addBox(0.0F, -1.0F, -3.0F, 11.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(3, 100).mirror().addBox(0.25F, -1.0F, -3.25F, 11.0F, 2.0F, 5.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(4.5F, 6.0F, -3.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create().texOffs(34, 76).mirror().addBox(-11.0F, -1.0F, -3.0F, 11.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(3, 100).addBox(-10.75F, -1.0F, -3.25F, 11.0F, 2.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-4.5F, 6.0F, -3.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition left_back_fin = body.addOrReplaceChild("left_back_fin", CubeListBuilder.create().texOffs(46, 83).mirror().addBox(0.0F, -1.0F, -1.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 6.0F, 6.5F, 0.0F, 0.0F, 0.7854F));
		PartDefinition right_back_fin = body.addOrReplaceChild("right_back_fin", CubeListBuilder.create().texOffs(46, 83).addBox(-4.0F, -1.0F, -1.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 6.0F, 6.5F, 0.0F, 0.0F, -0.7854F));
		PartDefinition tail1_overlay = body.addOrReplaceChild("tail1_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, 8.0F));
		PartDefinition tail1 = tail1_overlay.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 61).addBox(-2.5F, -3.5F, 0.0F, 5.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition fin_r1 = tail1.addOrReplaceChild("fin_r1", CubeListBuilder.create().texOffs(12, 87).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.5F, 4.0F, 0.2618F, 0.0F, 0.0F));
		PartDefinition tail2_overlay = tail1.addOrReplaceChild("tail2_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 9.0F));
		PartDefinition tail2 = tail2_overlay.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6981F, 0.0F, 0.0F));
		PartDefinition fluke_r1 = tail2.addOrReplaceChild("fluke_r1", CubeListBuilder.create().texOffs(32, 83).addBox(-1.0F, -3.0F, -4.5F, 2.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 4.5F, -0.2618F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(DunkleosteusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(entity.swimAnimationState, DunkleosteusMediumAnimations.SWIM, ageInTicks, limbSwingAmount * 2.5F);
		this.animateIdle(entity.idleAnimationState, DunkleosteusMediumAnimations.IDLE, ageInTicks, 1.0F, 1.0F - Math.abs(limbSwingAmount));
		this.animate(entity.flopAnimationState, DunkleosteusMediumAnimations.FLOP, ageInTicks, 1.0F);
		this.animate(entity.attackAnimationState, DunkleosteusMediumAnimations.ATTACK, ageInTicks, 1.0F);
		this.animate(entity.yawnAnimationState, DunkleosteusMediumAnimations.YAWN, ageInTicks, 1.0F);

		this.swim_control.xRot = headPitch * (Mth.DEG_TO_RAD);
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