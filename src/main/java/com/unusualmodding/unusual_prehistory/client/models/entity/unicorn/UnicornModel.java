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
public class UnicornModel<T extends UnicornEntity> extends UP2Model<T> {

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart upperBody;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;

	public UnicornModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.upperBody = this.body.getChild("upperBody");
		this.neck = this.upperBody.getChild("neck");
		this.head = this.neck.getChild("head");
		this.tail = this.upperBody.getChild("tail");
		this.rightLeg = this.body.getChild("rightLeg");
		this.leftLeg = this.body.getChild("leftLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition upperBody = body.addOrReplaceChild("upperBody", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -14.0F, -34.0F, 12.0F, 14.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, -1.0472F, 0.0F, 0.0F));
		PartDefinition neck = upperBody.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(70, 74).addBox(-4.0F, -5.0F, -7.0F, 8.0F, 9.0F, 9.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, -9.0F, -34.0F));
		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, -5.0F, -28.0F, 4.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)).texOffs(72, 48).addBox(-4.0F, -5.0F, -7.0F, 8.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(20, 71).addBox(-4.0F, -5.0F, -10.0F, 8.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));
		PartDefinition tail = upperBody.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(44, 48).addBox(-3.0F, -14.0F, -5.0F, 6.0F, 19.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, -3.1F));
		PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 71).addBox(-2.65F, -4.0F, -2.5F, 5.0F, 30.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -26.0F, -9.5F));
		PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 71).mirror().addBox(-2.35F, -4.0F, -2.5F, 5.0F, 30.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, -26.0F, -9.5F));
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