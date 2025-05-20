package com.unusualmodding.unusual_prehistory.client.models.entity.unicorn;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.UnicornAnimations;
import com.unusualmodding.unusual_prehistory.client.models.entity.base.UP2Model;
import com.unusualmodding.unusual_prehistory.entity.UnicornEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class UnicornSkeletonModel<T extends UnicornEntity> extends UP2Model<T> {

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart upperBody;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart tail;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public UnicornSkeletonModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.upperBody = this.body.getChild("upperBody");
		this.neck = this.upperBody.getChild("neck");
		this.head = this.neck.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.tail = this.upperBody.getChild("tail");
		this.leftLeg = this.body.getChild("leftLeg");
		this.rightLeg = this.body.getChild("rightLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 12.0F));
		PartDefinition upperBody = body.addOrReplaceChild("upperBody", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -14.0F, -34.0F, 4.0F, 6.0F, 38.0F, new CubeDeformation(0.0F)).texOffs(0, 44).addBox(0.0F, -8.0F, -34.0F, 0.0F, 14.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, -1.0472F, 0.0F, 0.0F));
		PartDefinition neck = upperBody.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(84, 24).addBox(-2.0F, -5.0F, -7.0F, 4.0F, 6.0F, 9.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, -9.0F, -34.0F));
		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(68, 44).addBox(-2.0F, -5.0F, -28.0F, 4.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)).texOffs(88, 67).addBox(-3.0F, -5.0F, -7.0F, 6.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(47, 102).addBox(-3.0F, 0.0F, -3.1F, 6.0F, 10.0F, 1.0F, new CubeDeformation(-0.1F)).texOffs(18, 92).addBox(-3.0F, -5.0F, -10.0F, 6.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));
		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(88, 89).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(84, 39).addBox(-3.0F, 15.0F, 3.0F, 6.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -3.0F));
		PartDefinition tail = upperBody.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 92).addBox(-2.0F, -9.0F, 2.0F, 4.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, -3.1F));
		PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(84, 0).mirror().addBox(-2.4F, -11.0F, -2.5F, 5.0F, 15.0F, 9.0F, new CubeDeformation(0.1F)).mirror(false).texOffs(68, 67).mirror().addBox(-2.4F, -4.0F, -2.5F, 5.0F, 30.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.5F, -26.0F, -21.5F));
		PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(68, 67).addBox(-2.6F, -4.0F, -2.5F, 5.0F, 30.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(84, 0).addBox(-2.6F, -11.0F, -2.5F, 5.0F, 15.0F, 9.0F, new CubeDeformation(0.1F)), PartPose.offset(3.5F, -26.0F, -21.5F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(UnicornEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		animateWalk(UnicornAnimations.WALK,limbSwing, limbSwingAmount, 4f, 8f);
		this.animateIdle(entity.idleAnimationState, UnicornAnimations.IDLE, ageInTicks, 1f, 1f - Math.abs(limbSwingAmount));
		this.neck.xRot += headPitch * ((float) Math.PI / 180f) - (headPitch * ((float) Math.PI / 180f)) / 2;
		this.neck.yRot += headYaw * ((float) Math.PI / 180f) - (headYaw * ((float) Math.PI / 180f)) / 2;
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