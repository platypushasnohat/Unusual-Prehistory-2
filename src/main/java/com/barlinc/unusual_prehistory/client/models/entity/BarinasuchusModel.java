package com.barlinc.unusual_prehistory.client.models.entity;

import com.barlinc.unusual_prehistory.client.animations.BarinasuchusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.base.UP2Model;
import com.barlinc.unusual_prehistory.entity.Barinasuchus;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class BarinasuchusModel extends UP2Model<Barinasuchus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart arm_control;
    private final ModelPart left_arm1;
    private final ModelPart left_arm2;
    private final ModelPart right_arm1;
    private final ModelPart right_arm2;
    private final ModelPart leg_control;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart left_leg3;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;
    private final ModelPart right_leg3;

	public BarinasuchusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tail = this.body.getChild("tail");
        this.arm_control = this.body_main.getChild("arm_control");
        this.left_arm1 = this.arm_control.getChild("left_arm1");
        this.left_arm2 = this.left_arm1.getChild("left_arm2");
        this.right_arm1 = this.arm_control.getChild("right_arm1");
        this.right_arm2 = this.right_arm1.getChild("right_arm2");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg1 = this.leg_control.getChild("left_leg1");
        this.left_leg2 = this.left_leg1.getChild("left_leg2");
        this.left_leg3 = this.left_leg2.getChild("left_leg3");
        this.right_leg1 = this.leg_control.getChild("right_leg1");
        this.right_leg2 = this.right_leg1.getChild("right_leg2");
        this.right_leg3 = this.right_leg2.getChild("right_leg3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -23.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 59).addBox(-9.5F, -8.0F, -16.0F, 19.0F, 15.0F, 35.0F, new CubeDeformation(0.0F))
                .texOffs(86, 109).addBox(-2.5F, -9.0F, -13.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(86, 109).mirror().addBox(0.5F, -9.0F, 12.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(86, 109).mirror().addBox(0.5F, -9.0F, 7.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(86, 109).mirror().addBox(0.5F, -9.0F, 2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(86, 109).mirror().addBox(0.5F, -9.0F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(86, 109).mirror().addBox(0.5F, -9.0F, -8.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(86, 109).mirror().addBox(0.5F, -9.0F, -13.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(86, 109).addBox(-2.5F, -9.0F, 12.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(86, 109).addBox(-2.5F, -9.0F, 7.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(86, 109).addBox(-2.5F, -9.0F, 2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(86, 109).addBox(-2.5F, -9.0F, -8.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(86, 109).addBox(-2.5F, -9.0F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(132, 137).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(86, 109).addBox(-3.0F, -6.0F, -17.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(86, 109).addBox(1.0F, -6.0F, -17.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(108, 52).addBox(-4.0F, -5.0F, -21.0F, 8.0F, 7.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(108, 106).addBox(-4.0F, 0.75F, -21.0F, 8.0F, 4.0F, 17.0F, new CubeDeformation(-0.1F))
                .texOffs(132, 127).addBox(-4.0F, -6.0F, -13.0F, 8.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -18.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 109).addBox(-4.0F, 1.0F, -21.0F, 8.0F, 3.0F, 14.0F, new CubeDeformation(0.2F))
                .texOffs(44, 109).addBox(-4.0F, -3.0F, -21.0F, 8.0F, 5.0F, 13.0F, new CubeDeformation(0.1F))
                .texOffs(30, 127).addBox(-5.0F, -2.0F, -7.0F, 10.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -1.0F, 5.0F, 11.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 18.0F));

        PartDefinition Tail_r1 = tail.addOrReplaceChild("Tail_r1", CubeListBuilder.create().texOffs(106, 0).addBox(0.0F, -3.0F, -3.0F, 0.0F, 4.0F, 48.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-1.0F, -3.0F, 5.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition Tail_r2 = tail.addOrReplaceChild("Tail_r2", CubeListBuilder.create().texOffs(106, 0).addBox(0.0F, -3.0F, -3.0F, 0.0F, 4.0F, 48.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.0F, -3.0F, 5.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, -7.0F));

        PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(0, 126).addBox(0.0F, -4.0F, -4.0F, 7.0F, 21.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(78, 140).addBox(7.0F, -4.0F, 4.0F, 0.0F, 16.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offset(6.0F, 0.0F, 0.0F));

        PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(68, 127).addBox(-4.5F, -2.0F, -7.5F, 9.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 15.0F, 0.5F));

        PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(78, 140).mirror().addBox(-7.0F, -4.0F, 4.0F, 0.0F, 16.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(0, 126).mirror().addBox(-7.0F, -4.0F, -4.0F, 7.0F, 21.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, 0.0F, 0.0F));

        PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(68, 127).mirror().addBox(-4.5F, -2.0F, -7.5F, 9.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.5F, 15.0F, 0.5F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(6.0F, 8.0F, 13.0F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(108, 76).addBox(0.0F, -9.0F, -10.0F, 7.0F, 16.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(68, 140).addBox(7.0F, -9.0F, 4.0F, 0.0F, 16.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(104, 127).addBox(-3.0F, 0.0F, -4.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(-0.1F)), PartPose.offset(3.0F, 5.0F, 1.5F));

        PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(68, 127).addBox(-4.5F, -2.0F, -7.0F, 9.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 8.0F, 0.5F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(108, 76).mirror().addBox(-7.0F, -9.0F, -10.0F, 7.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(68, 140).mirror().addBox(-7.0F, -9.0F, 4.0F, 0.0F, 16.0F, 5.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-12.0F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(104, 127).mirror().addBox(-4.0F, 0.0F, -4.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(-3.0F, 5.0F, 1.5F));

        PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(68, 127).mirror().addBox(-4.5F, -2.0F, -7.0F, 9.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 8.0F, 0.5F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(@NotNull Barinasuchus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        if (!entity.isInWater()) {
            if (entity.isRunning() || (entity.hasControllingPassenger() && entity.getControllingPassenger().isSprinting())) this.animateWalk(BarinasuchusAnimations.RUN, limbSwing, limbSwingAmount, 1.1F, 2.2F);
            else this.animateWalk(BarinasuchusAnimations.WALK, limbSwing, limbSwingAmount, 1.3F, 2.6F);
        }
		this.animateIdle(entity.idleAnimationState, BarinasuchusAnimations.IDLE, ageInTicks, 1, limbSwingAmount * 4);
        this.animate(entity.swimAnimationState, BarinasuchusAnimations.SWIM, ageInTicks, 2);
        this.animate(entity.attack1AnimationState, BarinasuchusAnimations.BITE_BLEND1, ageInTicks);
        this.animate(entity.attack2AnimationState, BarinasuchusAnimations.BITE_BLEND2, ageInTicks);
		this.animate(entity.sitStartAnimationState, BarinasuchusAnimations.SIT_START, ageInTicks);
		this.animate(entity.sitAnimationState, BarinasuchusAnimations.SIT, ageInTicks);
		this.animate(entity.sitEndAnimationState, BarinasuchusAnimations.SIT_END, ageInTicks);
        this.animate(entity.yawnAnimationState, BarinasuchusAnimations.YAWN_BLEND, ageInTicks);
        this.animate(entity.shakeAnimationState, BarinasuchusAnimations.SHAKE_BLEND, ageInTicks);
        this.animate(entity.threatenAnimationState, BarinasuchusAnimations.THREATEN, ageInTicks);
        this.animate(entity.sleepStartAnimationState, BarinasuchusAnimations.SLEEP_START, ageInTicks);
        this.animate(entity.sleepAnimationState, BarinasuchusAnimations.SLEEP, ageInTicks);
        this.animate(entity.sleepEndAnimationState, BarinasuchusAnimations.SLEEP_END, ageInTicks);

        if (this.young) this.applyStatic(BarinasuchusAnimations.BABY_TRANSFORM);

        if (!entity.isMobEepy()) {
            this.head.xRot += headPitch * ((float) Math.PI / 180F) / 2;
            this.head.yRot += netHeadYaw * ((float) Math.PI / 180F) / 2;
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}

    public Vec3 getRiderPosition(Vec3 offset) {
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        Vector4f armOffsetVec = new Vector4f((float) offset.x, (float) offset.y, (float) offset.z, 1.0F);
        armOffsetVec.mul(poseStack.last().pose());
        Vec3 vec3 = new Vec3(armOffsetVec.x(), armOffsetVec.y(), armOffsetVec.z());
        poseStack.popPose();
        return vec3;
    }

    public void translateRiderToBody(PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        this.body_main.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
    }
}