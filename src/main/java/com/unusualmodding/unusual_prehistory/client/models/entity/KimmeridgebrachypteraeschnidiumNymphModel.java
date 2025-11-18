package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.kimmeridgebrachypteraeschnidium.KimmeridgebrachypteraeschnidiumNymphAnimations;
import com.unusualmodding.unusual_prehistory.entity.KimmeridgebrachypteraeschnidiumNymph;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class KimmeridgebrachypteraeschnidiumNymphModel extends HierarchicalModel<KimmeridgebrachypteraeschnidiumNymph> {

	private final ModelPart root;
	private final ModelPart body_main;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart leg_control;
	private final ModelPart left_front_leg;
	private final ModelPart right_front_leg;
	private final ModelPart left_middle_leg;
	private final ModelPart right_middle_leg;
	private final ModelPart left_back_leg;
	private final ModelPart right_back_leg;

	public KimmeridgebrachypteraeschnidiumNymphModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.body = this.body_main.getChild("body");
		this.head = this.body.getChild("head");
		this.leg_control = this.body_main.getChild("leg_control");
		this.left_front_leg = this.leg_control.getChild("left_front_leg");
		this.right_front_leg = this.leg_control.getChild("right_front_leg");
		this.left_middle_leg = this.leg_control.getChild("left_middle_leg");
		this.right_middle_leg = this.leg_control.getChild("right_middle_leg");
		this.left_back_leg = this.leg_control.getChild("left_back_leg");
		this.right_back_leg = this.leg_control.getChild("right_back_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 22.4F, 0.0F));
		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 9).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(-5, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 5).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 12).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));
		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));
		PartDefinition left_front_leg = leg_control.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(6, 5).addBox(0.0F, 0.0F, -1.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.0F, 0.0F, -1.5F, 0.0F, 0.1745F, 0.1309F));
		PartDefinition right_front_leg = leg_control.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(6, 5).mirror().addBox(-4.0F, 0.0F, -1.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, -1.5F, 0.0F, -0.1745F, -0.1309F));
		PartDefinition left_middle_leg = leg_control.addOrReplaceChild("left_middle_leg", CubeListBuilder.create().texOffs(6, 2).addBox(0.0F, 0.0F, -1.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.5F, 0.0F, 0.0F, 0.1309F));
		PartDefinition right_middle_leg = leg_control.addOrReplaceChild("right_middle_leg", CubeListBuilder.create().texOffs(6, 2).mirror().addBox(-4.0F, 0.0F, -1.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.5F, 0.0F, 0.0F, -0.1309F));
		PartDefinition left_back_leg = leg_control.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(6, 0).addBox(0.0F, 0.0F, -0.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.0F, 0.0F, 1.5F, 0.0F, -0.1745F, 0.1309F));
		PartDefinition right_back_leg = leg_control.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(6, 0).mirror().addBox(-4.0F, 0.0F, -0.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, 1.5F, 0.0F, 0.1745F, -0.1309F));
		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(KimmeridgebrachypteraeschnidiumNymph entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(KimmeridgebrachypteraeschnidiumNymphAnimations.SCUTTLE, limbSwing, limbSwingAmount, 4f, 4f);
        this.animate(entity.idleAnimationState, KimmeridgebrachypteraeschnidiumNymphAnimations.IDLE, ageInTicks, 1.0f);
        this.animate(entity.lookoutAnimationState, KimmeridgebrachypteraeschnidiumNymphAnimations.LOOKOUT, ageInTicks, 1.0f);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}