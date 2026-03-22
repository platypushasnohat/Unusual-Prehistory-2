package com.barlinc.unusual_prehistory.client.models.entity.mob.future;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.future.DimorphodonAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.future.Dimorphodon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DimorphodonModel extends UP2Model<Dimorphodon> {

    private final ModelPart root;
    private final ModelPart flight_control;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart wing_control;
    private final ModelPart left_wing1;
    private final ModelPart left_membrane;
    private final ModelPart left_wing2;
    private final ModelPart left_wing3;
    private final ModelPart right_wing1;
    private final ModelPart right_membrane;
    private final ModelPart right_wing2;
    private final ModelPart right_wing3;
    private final ModelPart leg_control;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;

	public DimorphodonModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.flight_control = this.root.getChild("flight_control");
        this.body_main = this.flight_control.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.wing_control = this.body_main.getChild("wing_control");
        this.left_wing1 = this.wing_control.getChild("left_wing1");
        this.left_membrane = this.left_wing1.getChild("left_membrane");
        this.left_wing2 = this.left_wing1.getChild("left_wing2");
        this.left_wing3 = this.left_wing1.getChild("left_wing3");
        this.right_wing1 = this.wing_control.getChild("right_wing1");
        this.right_membrane = this.right_wing1.getChild("right_membrane");
        this.right_wing2 = this.right_wing1.getChild("right_wing2");
        this.right_wing3 = this.right_wing1.getChild("right_wing3");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg1 = this.leg_control.getChild("left_leg1");
        this.left_leg2 = this.left_leg1.getChild("left_leg2");
        this.right_leg1 = this.leg_control.getChild("right_leg1");
        this.right_leg2 = this.right_leg1.getChild("right_leg2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition flight_control = root.addOrReplaceChild("flight_control", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 0.0F));

        PartDefinition body_main = flight_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(32, 39).addBox(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(48, 54).addBox(-2.5F, -6.0F, -2.0F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(0, 41).addBox(-2.5F, -6.0F, -10.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.01F))
                .texOffs(46, 11).addBox(-2.5F, -1.0F, -10.0F, 5.0F, 1.0F, 8.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -7.0F, -6.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 54).addBox(-2.0F, 0.0F, -8.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(24, 54).addBox(-2.0F, -1.0F, -8.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -2.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(32, 22).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -6.5F, 2.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 16.0F));

        PartDefinition wing_control = body_main.addOrReplaceChild("wing_control", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -4.0F));

        PartDefinition left_wing1 = wing_control.addOrReplaceChild("left_wing1", CubeListBuilder.create(), PartPose.offsetAndRotation(3.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.8378F));

        PartDefinition left_membrane = left_wing1.addOrReplaceChild("left_membrane", CubeListBuilder.create().texOffs(0, 11).addBox(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 11.0F, new CubeDeformation(0.005F)), PartPose.offset(0.0F, 0.0F, -1.0F));

        PartDefinition left_wing2 = left_wing1.addOrReplaceChild("left_wing2", CubeListBuilder.create().texOffs(46, 20).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(12.0F, 0.0F, -1.0F, 0.0F, 0.0F, -0.8552F));

        PartDefinition left_wing3 = left_wing1.addOrReplaceChild("left_wing3", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -1.0F, 21.0F, 0.0F, 11.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(12.0F, 0.0F, 0.0F, 0.0F, 0.0F, -2.0944F));

        PartDefinition right_wing1 = wing_control.addOrReplaceChild("right_wing1", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.8378F));

        PartDefinition right_membrane = right_wing1.addOrReplaceChild("right_membrane", CubeListBuilder.create().texOffs(0, 11).mirror().addBox(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 11.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offset(0.0F, 0.0F, -1.0F));

        PartDefinition right_wing2 = right_wing1.addOrReplaceChild("right_wing2", CubeListBuilder.create().texOffs(46, 20).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-12.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.8552F));

        PartDefinition right_wing3 = right_wing1.addOrReplaceChild("right_wing3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-21.0F, 0.0F, -1.0F, 21.0F, 0.0F, 11.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-12.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.0944F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(26, 41).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(62, 39).addBox(-2.5F, -0.02F, -4.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(26, 41).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(62, 39).mirror().addBox(-2.5F, -0.02F, -4.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 6.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Dimorphodon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (!entity.isFlying()) {
            if (entity.isInWater()) {
                this.animateWalk(DimorphodonAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
            } else {
                if (entity.isRunning()) this.animateWalk(DimorphodonAnimations.RUN, limbSwing, limbSwingAmount, 1, 2);
                else this.animateWalk(DimorphodonAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 3);
            }
		}

        this.animateIdleSmooth(entity.idleAnimationState, DimorphodonAnimations.IDLE, ageInTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.hoverAnimationState, DimorphodonAnimations.HOVER, ageInTicks, limbSwingAmount);
        this.animateSmooth(entity.flyAnimationState, DimorphodonAnimations.FLY, ageInTicks);
        this.animateSmooth(entity.flyFastAnimationState, DimorphodonAnimations.FLY_FAST, ageInTicks);
        this.animateSmooth(entity.danceAnimationState, DimorphodonAnimations.DANCE, ageInTicks);
        this.animateSmooth(entity.grabAnimationState, DimorphodonAnimations.GRAB, ageInTicks);
        this.animateSmooth(entity.nip1AnimationState, DimorphodonAnimations.NIP_BLEND1, ageInTicks);
        this.animateSmooth(entity.nip2AnimationState, DimorphodonAnimations.NIP_BLEND2, ageInTicks);
        this.animateSmooth(entity.tailChaseAnimationState, DimorphodonAnimations.TAILCHASE, ageInTicks);

        float deg = ((float) Math.PI / 180F);

		this.head.xRot += headPitch * deg / 2;
		this.head.yRot += netHeadYaw * deg / 2;

		float partialTicks = ageInTicks - entity.tickCount;
		float flyProgress = entity.getFlyProgress(partialTicks);
		float rollAmount = entity.getFlightRoll(partialTicks) / 57.295776F * flyProgress;
		float flightPitchAmount = entity.getFlightPitch(partialTicks) / 57.295776F * flyProgress;

		this.flight_control.xRot += flightPitchAmount / 2;
		this.flight_control.zRot += rollAmount / 2;

		if (this.young) this.applyStatic(DimorphodonAnimations.BABY_TRANSFORM);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}

    public void translateToFeet(PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        this.leg_control.translateAndRotate(poseStack);
    }
}