package com.barlinc.unusual_prehistory.client.models.entity;

import com.barlinc.unusual_prehistory.client.animations.LystrosaurusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.base.UP2Model;
import com.barlinc.unusual_prehistory.entity.Lystrosaurus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class LystrosaurusModel extends UP2Model<Lystrosaurus> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart sleep_control;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart left_leg;
    private final ModelPart left_leg1;
    private final ModelPart right_leg;
    private final ModelPart right_leg1;
    private final ModelPart left_arm;
    private final ModelPart left_arm1;
    private final ModelPart right_arm;
    private final ModelPart right_arm1;

	public LystrosaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.sleep_control = this.body.getChild("sleep_control");
        this.head = this.body.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tail = this.body.getChild("tail");
        this.left_leg = this.root.getChild("left_leg");
        this.left_leg1 = this.left_leg.getChild("left_leg1");
        this.right_leg = this.root.getChild("right_leg");
        this.right_leg1 = this.right_leg.getChild("right_leg1");
        this.left_arm = this.root.getChild("left_arm");
        this.left_arm1 = this.left_arm.getChild("left_arm1");
        this.right_arm = this.root.getChild("right_arm");
        this.right_arm1 = this.right_arm.getChild("right_arm1");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -1.0F));

        PartDefinition sleep_control = body.addOrReplaceChild("sleep_control", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.5F, -8.0F, 12.0F, 11.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.5F, 1.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(1, 27).addBox(-3.5F, -4.0F, -6.0F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(47, 49).addBox(0.5F, -5.0F, -6.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(47, 49).mirror().addBox(-3.5F, -5.0F, -6.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(21, 49).addBox(-1.5F, -2.0F, -9.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(37, 45).mirror().addBox(-3.5F, -1.0F, -8.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(37, 45).addBox(1.5F, -1.0F, -8.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, -7.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(52, 27).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -6.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(32, 27).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 9.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 37).addBox(-2.0F, -2.0F, -2.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 50).addBox(2.0F, 2.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -4.0F, 5.0F, 0.0F, -0.1745F, 0.0F));

        PartDefinition left_leg1 = left_leg.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(21, 45).addBox(-2.5F, 0.0F, -4.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(3.5F, 4.0F, -1.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(32, 37).mirror().addBox(-5.0F, -2.0F, -2.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 50).mirror().addBox(-5.0F, 2.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.0F, -4.0F, 5.0F, 0.0F, 0.1745F, 0.0F));

        PartDefinition right_leg1 = right_leg.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(21, 45).mirror().addBox(-3.5F, 0.0F, -4.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.025F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 4.0F, -1.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 43).addBox(-2.0F, -2.0F, -1.5F, 7.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(52, 32).addBox(2.0F, 2.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -4.0F, -5.5F, 0.0F, 0.1745F, 0.0F));

        PartDefinition left_arm1 = left_arm.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(21, 45).addBox(-2.5F, 0.0F, -4.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(3.5F, 4.0F, -0.5F, 0.0F, -0.5236F, 0.0F));

        PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 43).mirror().addBox(-5.0F, -2.0F, -1.5F, 7.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(52, 32).mirror().addBox(-5.0F, 2.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.0F, -4.0F, -5.5F, 0.0F, -0.1745F, 0.0F));

        PartDefinition right_arm1 = right_arm.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(21, 45).mirror().addBox(-3.5F, 0.0F, -4.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.025F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 4.0F, -0.5F, 0.0F, 0.5236F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Lystrosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (!entity.isInWater()) {
            if (entity.isRunning()) this.animateWalk(LystrosaurusAnimations.RUN, limbSwing, limbSwingAmount, 1, 2);
            else this.animateWalk(LystrosaurusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
        } else {
            this.animateWalk(LystrosaurusAnimations.SWIM, limbSwing, limbSwingAmount, 4, 8);
        }

        this.animateIdle(entity.idleAnimationState, LystrosaurusAnimations.IDLE, ageInTicks,1, limbSwingAmount * 4);
        this.animate(entity.shakeAnimationState, LystrosaurusAnimations.SHAKE_BLEND, ageInTicks);
        this.animate(entity.bounceAnimationState, LystrosaurusAnimations.BOUNCING, ageInTicks);

		if (this.young) this.applyStatic(LystrosaurusAnimations.BABY_TRANSFORM);

		this.head.xRot += headPitch * ((float) Math.PI / 180F) / 2;
		this.head.yRot += netHeadYaw * ((float) Math.PI / 180F) / 2;
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}