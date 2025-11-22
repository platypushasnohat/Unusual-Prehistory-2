package com.barlinc.unusual_prehistory.client.models.entity;

import com.barlinc.unusual_prehistory.client.animations.TelecrexAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.base.UP2Model;
import com.barlinc.unusual_prehistory.entity.Telecrex;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class TelecrexModel extends UP2Model<Telecrex> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart left_wing;
    private final ModelPart right_wing;
    private final ModelPart tail;
    private final ModelPart leg_control;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

	public TelecrexModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.left_wing = this.body.getChild("left_wing");
        this.right_wing = this.body.getChild("right_wing");
        this.tail = this.body.getChild("tail");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg = this.leg_control.getChild("left_leg");
        this.right_leg = this.leg_control.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -8.5F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -7.0F, 8.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.5F, 2.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(14, 40).addBox(0.0F, -8.0F, -2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0025F))
                .texOffs(36, 12).addBox(-1.5F, -3.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 40).addBox(-0.5F, -5.0F, -4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 16).addBox(-0.5F, -3.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-1.5F, -6.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 40).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -7.0F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, -7.0F, 0.0F, 1.0F, 7.0F, 13.0F, new CubeDeformation(0.01F)), PartPose.offset(4.5F, -3.0F, -5.0F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 19).mirror().addBox(-0.5F, -7.0F, 0.0F, 1.0F, 7.0F, 13.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-4.5F, -3.0F, -5.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(28, 33).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -9.0F, 3.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 4.5F, 2.0F));

        PartDefinition left_leg = leg_control.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(36, 6).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 0.0F, 0.0F));

        PartDefinition right_leg = leg_control.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(36, 6).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Telecrex entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (!entity.isFlying()) {
            this.animateWalk(TelecrexAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
		}

        this.animateIdle(entity.idleAnimationState, TelecrexAnimations.IDLE, ageInTicks, 1, limbSwingAmount * 4);
        this.animateIdle(entity.hoveringAnimationState, TelecrexAnimations.HOVER, ageInTicks, 1, limbSwingAmount * 4);
        this.animate(entity.flyingAnimationState, TelecrexAnimations.FLY, ageInTicks);
//        this.animate(entity.flyingFastAnimationState, TelecrexAnimations.FLYFAST, ageInTicks);
        this.animate(entity.lookoutAnimationState, TelecrexAnimations.LOOKOUT_BLEND, ageInTicks);
		this.animate(entity.peckingAnimationState, TelecrexAnimations.PECK, ageInTicks);

		this.head.xRot += headPitch * Mth.DEG_TO_RAD / 2;
		this.head.yRot += netHeadYaw * Mth.DEG_TO_RAD / 2;

		float partialTicks = ageInTicks - entity.tickCount;
		float flyProgress = entity.getFlyProgress(partialTicks);
		float rollAmount = entity.getFlightRoll(partialTicks) / 57.295776F * flyProgress;
		float flightPitchAmount = entity.getFlightPitch(partialTicks) / 57.295776F * flyProgress;

		this.body_main.xRot += flightPitchAmount / 2;
		this.body_main.zRot += rollAmount / 2;

		if (this.young) this.applyStatic(TelecrexAnimations.BABY_TRANSFORM);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}