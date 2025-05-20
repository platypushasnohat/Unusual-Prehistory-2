package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.ScaumenaciaAnimations;
import com.unusualmodding.unusual_prehistory.client.models.entity.base.UP2Model;
import com.unusualmodding.unusual_prehistory.entity.ScaumenaciaEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class ScaumenaciaModel<T extends ScaumenaciaEntity> extends UP2Model<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body;
	private final ModelPart dorsal;
	private final ModelPart left_front_fin;
	private final ModelPart right_front_fin;
	private final ModelPart left_back_fin;
	private final ModelPart right_back_fin;
	private final ModelPart tail;

	public ScaumenaciaModel(ModelPart root) {
		this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body = this.swim_control.getChild("body");
		this.dorsal = this.body.getChild("dorsal");
		this.left_front_fin = this.body.getChild("left_front_fin");
		this.right_front_fin = this.body.getChild("right_front_fin");
		this.left_back_fin = this.body.getChild("left_back_fin");
		this.right_back_fin = this.body.getChild("right_back_fin");
		this.tail = this.body.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));
		PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(26, 22).addBox(-1.5F, -0.5F, -7.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-1.5F, -3.5F, -5.0F, 3.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));
		PartDefinition dorsal = body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(26, 0).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -3.5F, 0.0F));
		PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create().texOffs(26, 18).addBox(0.0F, 0.0F, -1.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, 2.5F, -3.0F));
		PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create().texOffs(26, 18).mirror().addBox(-5.0F, 0.0F, -1.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, 2.5F, -3.0F));
		PartDefinition left_back_fin = body.addOrReplaceChild("left_back_fin", CubeListBuilder.create().texOffs(26, 12).addBox(0.0F, 0.0F, -0.5F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, 2.5F, 2.5F));
		PartDefinition right_back_fin = body.addOrReplaceChild("right_back_fin", CubeListBuilder.create().texOffs(26, 12).mirror().addBox(-3.0F, 0.0F, -0.5F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, 2.5F, 2.5F));
		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 12.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 5.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(ScaumenaciaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(entity.swimAnimationState, ScaumenaciaAnimations.SWIM, ageInTicks, limbSwingAmount * 3.0F);
		this.animateIdle(entity.idleAnimationState, ScaumenaciaAnimations.IDLE, ageInTicks, 1.0F, 1.0F - Math.abs(limbSwingAmount));
		this.animate(entity.flopAnimationState, ScaumenaciaAnimations.FLOP, ageInTicks, 1.0F);

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