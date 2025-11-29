package com.barlinc.unusual_prehistory.client.models.entity;

import com.barlinc.unusual_prehistory.client.animations.MetriorhynchusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.base.UP2Model;
import com.barlinc.unusual_prehistory.entity.Metriorhynchus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class MetriorhynchusModel extends UP2Model<Metriorhynchus> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart arm_control;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart leg_control;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

	public MetriorhynchusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body_main = this.swim_control.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.arm_control = this.body_main.getChild("arm_control");
        this.left_arm = this.arm_control.getChild("left_arm");
        this.right_arm = this.arm_control.getChild("right_arm");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg = this.leg_control.getChild("left_leg");
        this.right_leg = this.leg_control.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -15.0F, -14.0F, 0.0F, 8.0F, 30.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 0).addBox(-7.5F, -7.0F, -16.0F, 15.0F, 15.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(95, 35).addBox(-6.5F, -7.0F, -7.0F, 13.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(50, 89).addBox(-4.5F, -3.0F, -9.0F, 9.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(32, 91).addBox(-4.5F, -7.0F, -1.0F, 9.0F, 4.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(84, 105).addBox(-1.5F, -3.0F, -22.0F, 3.0F, 3.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(65, 108).addBox(-1.0F, 0.0F, -21.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(120, 49).addBox(-1.5F, -5.0F, -22.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 47).addBox(1.5F, -8.0F, -7.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 47).mirror().addBox(-6.5F, -8.0F, -7.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 7.0F, -16.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 110).addBox(-4.5F, 0.0F, -9.0F, 9.0F, 2.0F, 11.0F, new CubeDeformation(0.01F))
                .texOffs(110, 78).addBox(-1.5F, 0.0F, -22.0F, 3.0F, 2.0F, 13.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition teeth_r1 = jaw.addOrReplaceChild("teeth_r1", CubeListBuilder.create().texOffs(116, 101).mirror().addBox(0.0F, -3.0F, -12.0F, 0.0F, 3.0F, 17.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 0.0F, -10.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition teeth_r2 = jaw.addOrReplaceChild("teeth_r2", CubeListBuilder.create().texOffs(48, 85).addBox(-2.0F, -1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.5F, 0.0F, -22.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition teeth_r3 = jaw.addOrReplaceChild("teeth_r3", CubeListBuilder.create().texOffs(116, 101).addBox(0.0F, -3.0F, -12.0F, 0.0F, 3.0F, 17.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.5F, 0.0F, -10.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(60, 78).addBox(1.5F, -5.0F, 0.0F, 0.0F, 2.0F, 25.0F, new CubeDeformation(0.0025F))
                .texOffs(60, 47).addBox(-2.5F, -3.0F, 0.0F, 5.0F, 6.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(60, 78).mirror().addBox(-1.5F, -5.0F, 0.0F, 0.0F, 2.0F, 25.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, 16.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 25.0F));

        PartDefinition tailspikes_r1 = tail2.addOrReplaceChild("tailspikes_r1", CubeListBuilder.create().texOffs(94, 0).addBox(0.0F, 0.0F, 5.0F, 0.0F, 2.0F, 23.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 85).addBox(-1.0F, -3.0F, 6.0F, 2.0F, 3.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(36, 117).addBox(-1.0F, -12.0F, 0.0F, 2.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -2.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, -9.0F));

        PartDefinition left_arm = arm_control.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(94, 25).addBox(0.0F, -1.0F, 0.0F, 13.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(52, 117).addBox(12.0F, -0.99F, 0.0F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offset(7.5F, 0.0F, 0.0F));

        PartDefinition right_arm = arm_control.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(94, 25).mirror().addBox(-13.0F, -1.0F, 0.0F, 13.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(52, 117).mirror().addBox(-14.0F, -0.99F, 0.0F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-7.5F, 0.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 13.0F));

        PartDefinition left_leg = leg_control.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(120, 56).addBox(-1.0F, -0.99F, 8.0F, 7.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F))
                .texOffs(110, 93).addBox(0.0F, -1.0F, 0.0F, 6.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 0.0F, 0.0F));

        PartDefinition right_leg = leg_control.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(120, 56).mirror().addBox(-6.0F, -0.99F, 8.0F, 7.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(110, 93).mirror().addBox(-6.0F, -1.0F, 0.0F, 6.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.5F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Metriorhynchus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (!entity.isInWater()) {
			this.animateWalk(MetriorhynchusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
		} else if (entity.getPose() != UP2Poses.DEATH_ROLL.get()) {
            this.swim_control.xRot = headPitch * ((float) Math.PI / 180F);
            this.animateWalk(entity.isRunning() ? MetriorhynchusAnimations.SWIMFAST : MetriorhynchusAnimations.SWIM, limbSwing, limbSwingAmount, 1, 2);
        }

		if (this.young) this.applyStatic(MetriorhynchusAnimations.BABY_TRANSFORM);

		this.animateIdle(entity.idleAnimationState, MetriorhynchusAnimations.IDLE, ageInTicks,1, limbSwingAmount * 4);
        this.animateIdle(entity.swimIdleAnimationState, MetriorhynchusAnimations.SWIM_IDLE, ageInTicks,1, limbSwingAmount * 4);
        this.animate(entity.bite1AnimationState, MetriorhynchusAnimations.BITE_BLEND1, ageInTicks);
        this.animate(entity.bite2AnimationState, MetriorhynchusAnimations.BITE_BLEND2, ageInTicks);
        this.animate(entity.deathRoll1AnimationState, MetriorhynchusAnimations.DEATHROLL1, ageInTicks);
        this.animate(entity.deathRoll2AnimationState, MetriorhynchusAnimations.DEATHROLL2, ageInTicks);
        this.animate(entity.bellowAnimationState, MetriorhynchusAnimations.BELLOW_BLEND, ageInTicks);

		this.head.xRot += (headPitch * ((float) Math.PI / 180)) / 2;
		this.head.yRot += (netHeadYaw * ((float) Math.PI / 180)) / 2;
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}

    public void translateToMouth(PoseStack poseStack) {
        this.body.translateAndRotate(poseStack);
        this.head.translateAndRotate(poseStack);
        this.jaw.translateAndRotate(poseStack);
    }
}