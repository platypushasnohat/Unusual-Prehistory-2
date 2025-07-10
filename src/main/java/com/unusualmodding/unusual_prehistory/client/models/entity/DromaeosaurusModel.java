package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.DromaeosaurusAnimations;
import com.unusualmodding.unusual_prehistory.entity.Dromaeosaurus;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class DromaeosaurusModel<T extends Dromaeosaurus> extends HierarchicalModel<T> {

	private final ModelPart Dromaeosaurus;
	private final ModelPart Dromaeo_Upper_Body;
	private final ModelPart Breathe;
	private final ModelPart Dromaeo_Neck;
	private final ModelPart Dromaeo_head;
	private final ModelPart Dromaeo_Jaw;
	private final ModelPart Dromaeo_Arm_Left;
	private final ModelPart Dromaeo_Tail;
	private final ModelPart Dromaeo_Arm_Right;
	private final ModelPart Dromaeo_Leg_Left;
	private final ModelPart Dromaeo_Knee_Left;
	private final ModelPart Dromaeo_Foot_Left;
	private final ModelPart Dromaeo_Sickle_Left;
	private final ModelPart Dromaeo_Leg_Right;
	private final ModelPart Dromaeo_Knee_Right;
	private final ModelPart Dromaeo_Foot_Right;
	private final ModelPart Dromaeo_Sickle_Right;

	public DromaeosaurusModel(ModelPart root) {
		this.Dromaeosaurus = root.getChild("Dromaeosaurus");
		this.Dromaeo_Upper_Body = this.Dromaeosaurus.getChild("Dromaeo_Upper_Body");
		this.Breathe = this.Dromaeo_Upper_Body.getChild("Breathe");
		this.Dromaeo_Neck = this.Dromaeo_Upper_Body.getChild("Dromaeo_Neck");
		this.Dromaeo_head = this.Dromaeo_Neck.getChild("Dromaeo_head");
		this.Dromaeo_Jaw = this.Dromaeo_head.getChild("Dromaeo_Jaw");
		this.Dromaeo_Arm_Left = this.Dromaeo_Upper_Body.getChild("Dromaeo_Arm_Left");
		this.Dromaeo_Tail = this.Dromaeo_Upper_Body.getChild("Dromaeo_Tail");
		this.Dromaeo_Arm_Right = this.Dromaeo_Upper_Body.getChild("Dromaeo_Arm_Right");
		this.Dromaeo_Leg_Left = this.Dromaeosaurus.getChild("Dromaeo_Leg_Left");
		this.Dromaeo_Knee_Left = this.Dromaeo_Leg_Left.getChild("Dromaeo_Knee_Left");
		this.Dromaeo_Foot_Left = this.Dromaeo_Knee_Left.getChild("Dromaeo_Foot_Left");
		this.Dromaeo_Sickle_Left = this.Dromaeo_Foot_Left.getChild("Dromaeo_Sickle_Left");
		this.Dromaeo_Leg_Right = this.Dromaeosaurus.getChild("Dromaeo_Leg_Right");
		this.Dromaeo_Knee_Right = this.Dromaeo_Leg_Right.getChild("Dromaeo_Knee_Right");
		this.Dromaeo_Foot_Right = this.Dromaeo_Knee_Right.getChild("Dromaeo_Foot_Right");
		this.Dromaeo_Sickle_Right = this.Dromaeo_Foot_Right.getChild("Dromaeo_Sickle_Right");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Dromaeosaurus = partdefinition.addOrReplaceChild("Dromaeosaurus", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition Dromaeo_Upper_Body = Dromaeosaurus.addOrReplaceChild("Dromaeo_Upper_Body", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 3.5F));

		PartDefinition Breathe = Dromaeo_Upper_Body.addOrReplaceChild("Breathe", CubeListBuilder.create().texOffs(0, 39).addBox(-4.0F, -7.0F, -5.0F, 6.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.0F, -3.5F));

		PartDefinition Dromaeo_Neck = Dromaeo_Upper_Body.addOrReplaceChild("Dromaeo_Neck", CubeListBuilder.create().texOffs(0, 56).addBox(-1.5F, -5.0F, -3.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0025F))
		.texOffs(32, 51).addBox(-1.5F, -10.0F, -1.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(34, 63).addBox(-1.5F, -10.01F, 3.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -8.5F));

		PartDefinition Dromaeo_head = Dromaeo_Neck.addOrReplaceChild("Dromaeo_head", CubeListBuilder.create().texOffs(46, 52).addBox(-2.5F, -1.01F, -4.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(14, 56).addBox(-1.5F, -1.01F, -8.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 2.0F));

		PartDefinition Dromaeo_Jaw = Dromaeo_head.addOrReplaceChild("Dromaeo_Jaw", CubeListBuilder.create().texOffs(62, 60).addBox(-1.5F, -0.01F, -4.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -4.0F));

		PartDefinition Dromaeo_Arm_Left = Dromaeo_Upper_Body.addOrReplaceChild("Dromaeo_Arm_Left", CubeListBuilder.create().texOffs(64, 39).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(50, 39).addBox(0.99F, 1.0F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(14, 63).addBox(-1.0F, 2.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(44, 64).addBox(-1.0F, 2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.0F, -6.5F));

		PartDefinition Dromaeo_Tail = Dromaeo_Upper_Body.addOrReplaceChild("Dromaeo_Tail", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 1.5F));

		PartDefinition tail_r1 = Dromaeo_Tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-7.0F, 0.0F, 0.0F, 7.0F, 0.0F, 39.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition tail_r2 = Dromaeo_Tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 7.0F, 0.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition Dromaeo_Arm_Right = Dromaeo_Upper_Body.addOrReplaceChild("Dromaeo_Arm_Right", CubeListBuilder.create().texOffs(64, 39).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(50, 39).mirror().addBox(-0.99F, 1.0F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(14, 63).mirror().addBox(-1.0F, 2.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(44, 64).mirror().addBox(-1.0F, 2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 1.0F, -6.5F));

		PartDefinition Dromaeo_Leg_Left = Dromaeosaurus.addOrReplaceChild("Dromaeo_Leg_Left", CubeListBuilder.create().texOffs(32, 39).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 2.0F, 3.5F));

		PartDefinition Dromaeo_Knee_Left = Dromaeo_Leg_Left.addOrReplaceChild("Dromaeo_Knee_Left", CubeListBuilder.create().texOffs(26, 63).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 2.5F));

		PartDefinition Dromaeo_Foot_Left = Dromaeo_Knee_Left.addOrReplaceChild("Dromaeo_Foot_Left", CubeListBuilder.create().texOffs(46, 60).addBox(-1.0F, 0.09F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 6.9F, 1.0F));

		PartDefinition Dromaeo_Sickle_Left = Dromaeo_Foot_Left.addOrReplaceChild("Dromaeo_Sickle_Left", CubeListBuilder.create().texOffs(28, 56).addBox(0.0F, -2.91F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offset(-1.0F, 0.0F, -2.0F));

		PartDefinition Dromaeo_Leg_Right = Dromaeosaurus.addOrReplaceChild("Dromaeo_Leg_Right", CubeListBuilder.create().texOffs(32, 39).mirror().addBox(-2.0F, -2.0F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 2.0F, 3.5F));

		PartDefinition Dromaeo_Knee_Right = Dromaeo_Leg_Right.addOrReplaceChild("Dromaeo_Knee_Right", CubeListBuilder.create().texOffs(26, 63).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.0F, 2.5F));

		PartDefinition Dromaeo_Foot_Right = Dromaeo_Knee_Right.addOrReplaceChild("Dromaeo_Foot_Right", CubeListBuilder.create().texOffs(46, 60).mirror().addBox(-3.0F, 0.09F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 6.9F, 1.0F));

		PartDefinition Dromaeo_Sickle_Right = Dromaeo_Foot_Right.addOrReplaceChild("Dromaeo_Sickle_Right", CubeListBuilder.create().texOffs(28, 56).mirror().addBox(0.0F, -2.91F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(1.0F, 0.0F, -2.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Dromaeosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(DromaeosaurusAnimations.RUN, limbSwing, limbSwingAmount, 1, 2);
		this.animate(entity.biteAnimationState, DromaeosaurusAnimations.BITE, ageInTicks);
		this.animate(entity.fallAnimationState, DromaeosaurusAnimations.FALL, ageInTicks);
		this.animate(entity.sleepStartAnimationState, DromaeosaurusAnimations.SLEEP_START, ageInTicks);
		this.animate(entity.sleepAnimationState, DromaeosaurusAnimations.SLEEP, ageInTicks);
		this.animate(entity.sleepEndAnimationState, DromaeosaurusAnimations.SLEEP_END, ageInTicks);

		this.Dromaeo_Neck.xRot += (headPitch * ((float) Math.PI / 180)) / 2;
		this.Dromaeo_Neck.yRot += (netHeadYaw * ((float) Math.PI / 180)) / 2;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.Dromaeosaurus.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.Dromaeosaurus;
	}
}