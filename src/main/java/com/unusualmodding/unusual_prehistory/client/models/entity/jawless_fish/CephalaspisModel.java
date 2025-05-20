package com.unusualmodding.unusual_prehistory.client.models.entity.jawless_fish;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.animations.JawlessFishAnimations;
import com.unusualmodding.unusual_prehistory.client.models.entity.base.UP2Model;
import com.unusualmodding.unusual_prehistory.entity.JawlessFishEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class CephalaspisModel<T extends JawlessFishEntity> extends UP2Model<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body_sideways;
	private final ModelPart body;
	private final ModelPart right_fin;
	private final ModelPart left_fin;
	private final ModelPart tail1;
	private final ModelPart tail2;

	public CephalaspisModel(ModelPart root) {
		this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body_sideways = this.swim_control.getChild("body_sideways");
		this.body = this.body_sideways.getChild("body");
		this.right_fin = this.body.getChild("right_fin");
		this.left_fin = this.body.getChild("left_fin");
		this.tail1 = this.body.getChild("tail1");
		this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, -2.0F));
		PartDefinition body_sideways = swim_control.addOrReplaceChild("body_sideways", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, -2.0F));
		PartDefinition body = body_sideways.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, 2.0F, -3.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-2.0F, -0.05F, -1.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(10, 12).addBox(0.0F, -2.0F, 4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition right_fin = body.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.05F, 0.0F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 2.0F, 1.0F, 0.0F, -0.48F, 0.0F));
		PartDefinition left_fin = body.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(0, 0).addBox(0.05F, 0.0F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 2.0F, 1.0F, 0.0F, 0.48F, 0.0F));
		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(-1, 1).addBox(0.0F, -1.5F, 0.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 5.0F));
		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 9).addBox(0.0F, -2.5F, 0.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(JawlessFishEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(entity.swimAnimationState, JawlessFishAnimations.SWIM, ageInTicks, limbSwingAmount * 3.0F);
		this.animate(entity.flopAnimationState, JawlessFishAnimations.FLOP, ageInTicks, 1.0F);

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