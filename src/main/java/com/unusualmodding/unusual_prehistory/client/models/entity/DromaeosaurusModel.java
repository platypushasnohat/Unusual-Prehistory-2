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

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart breathe;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart armLeft;
	private final ModelPart tail;
	private final ModelPart armRight;
	private final ModelPart legLeft1;
	private final ModelPart legLeft2;
	private final ModelPart legLeft3;
	private final ModelPart legLeft4;
	private final ModelPart legRight1;
	private final ModelPart legRight2;
	private final ModelPart legRight3;
	private final ModelPart legRight4;

	public DromaeosaurusModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.breathe = this.body.getChild("breathe");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.armLeft = this.body.getChild("armLeft");
		this.tail = this.body.getChild("tail");
		this.armRight = this.body.getChild("armRight");
		this.legLeft1 = this.root.getChild("legLeft1");
		this.legLeft2 = this.legLeft1.getChild("legLeft2");
		this.legLeft3 = this.legLeft2.getChild("legLeft3");
		this.legLeft4 = this.legLeft3.getChild("legLeft4");
		this.legRight1 = this.root.getChild("legRight1");
		this.legRight2 = this.legRight1.getChild("legRight2");
		this.legRight3 = this.legRight2.getChild("legRight3");
		this.legRight4 = this.legRight3.getChild("legRight4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 3.5F));

		PartDefinition breathe = body.addOrReplaceChild("breathe", CubeListBuilder.create()
				.texOffs(0, 39).addBox(-4.0F, -7.0F, -5.0F, 6.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.0F, -3.5F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create()
				.texOffs(0, 56).addBox(-1.5F, -5.0F, -3.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0025F))
				.texOffs(32, 51).addBox(-1.5F, -10.0F, -1.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(34, 63).addBox(-1.5F, -10.01F, 3.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -8.5F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(46, 52).addBox(-2.5F, -1.01F, -4.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(14, 56).addBox(-1.5F, -1.01F, -8.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 2.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create()
				.texOffs(62, 60).addBox(-1.5F, -0.01F, -4.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -4.0F));

		PartDefinition armLeft = body.addOrReplaceChild("armLeft", CubeListBuilder.create()
				.texOffs(64, 39).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(50, 39).addBox(0.99F, 1.0F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(14, 63).addBox(-1.0F, 2.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(44, 64).addBox(-1.0F, 2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.0F, -6.5F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 1.5F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create()
				.texOffs(0, 0).mirror().addBox(-7.0F, 0.0F, 0.0F, 7.0F, 0.0F, 39.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition tail_r2 = tail.addOrReplaceChild("tail_r2", CubeListBuilder.create()
				.texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 7.0F, 0.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition armRight = body.addOrReplaceChild("armRight", CubeListBuilder.create()
				.texOffs(64, 39).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(50, 39).mirror().addBox(-0.99F, 1.0F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(14, 63).mirror().addBox(-1.0F, 2.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 64).mirror().addBox(-1.0F, 2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 1.0F, -6.5F));

		PartDefinition legLeft1 = root.addOrReplaceChild("legLeft1", CubeListBuilder.create()
				.texOffs(32, 39).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 2.0F, 3.5F));

		PartDefinition legLeft2 = legLeft1.addOrReplaceChild("legLeft2", CubeListBuilder.create()
				.texOffs(26, 63).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 2.5F));

		PartDefinition legLeft3 = legLeft2.addOrReplaceChild("legLeft3", CubeListBuilder.create()
				.texOffs(46, 60).addBox(-1.0F, 0.09F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 6.9F, 1.0F));

		PartDefinition legLeft4 = legLeft3.addOrReplaceChild("legLeft4", CubeListBuilder.create()
				.texOffs(28, 56).addBox(0.0F, -2.91F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offset(-1.0F, 0.0F, -2.0F));

		PartDefinition legRight1 = root.addOrReplaceChild("legRight1", CubeListBuilder.create()
				.texOffs(32, 39).mirror().addBox(-2.0F, -2.0F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 2.0F, 3.5F));

		PartDefinition legRight2 = legRight1.addOrReplaceChild("legRight2", CubeListBuilder.create()
				.texOffs(26, 63).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.0F, 2.5F));

		PartDefinition legRight3 = legRight2.addOrReplaceChild("legRight3", CubeListBuilder.create()
				.texOffs(46, 60).mirror().addBox(-3.0F, 0.09F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 6.9F, 1.0F));

		PartDefinition legRight4 = legRight3.addOrReplaceChild("legRight4", CubeListBuilder.create()
				.texOffs(28, 56).mirror().addBox(0.0F, -2.91F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(1.0F, 0.0F, -2.0F));

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

		this.neck.xRot += (headPitch * ((float) Math.PI / 180)) / 2;
		this.neck.yRot += (netHeadYaw * ((float) Math.PI / 180)) / 2;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}