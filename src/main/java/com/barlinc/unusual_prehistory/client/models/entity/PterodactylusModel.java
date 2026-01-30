package com.barlinc.unusual_prehistory.client.models.entity;

import com.barlinc.unusual_prehistory.client.animations.PterodactylusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.base.UP2Model;
import com.barlinc.unusual_prehistory.entity.Pterodactylus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class PterodactylusModel extends UP2Model<Pterodactylus> {

    private final ModelPart root;
    private final ModelPart flight_control;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart leg_control;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart left_wing1;
    private final ModelPart left_wing2;
    private final ModelPart left_wing3;
    private final ModelPart right_wing1;
    private final ModelPart right_wing2;
    private final ModelPart right_wing3;

	public PterodactylusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.flight_control = this.root.getChild("flight_control");
        this.body_main = this.flight_control.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg = this.leg_control.getChild("left_leg");
        this.right_leg = this.leg_control.getChild("right_leg");
        this.left_wing1 = this.body_main.getChild("left_wing1");
        this.left_wing2 = this.left_wing1.getChild("left_wing2");
        this.left_wing3 = this.left_wing2.getChild("left_wing3");
        this.right_wing1 = this.body_main.getChild("right_wing1");
        this.right_wing2 = this.right_wing1.getChild("right_wing2");
        this.right_wing3 = this.right_wing2.getChild("right_wing3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition flight_control = root.addOrReplaceChild("flight_control", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition body_main = flight_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(0.0F, 0.0F, 1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 7).addBox(-0.5F, -3.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(20, 11).addBox(0.0F, -6.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition left_leg = leg_control.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(0.5F, 0.0F, 0.5F));

        PartDefinition cube_r1 = left_leg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(20, 18).mirror().addBox(-0.5F, 2.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(8, 21).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition right_leg = leg_control.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.0F, 0.5F));

        PartDefinition cube_r2 = right_leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(20, 18).addBox(-1.5F, 2.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 21).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition left_wing1 = body_main.addOrReplaceChild("left_wing1", CubeListBuilder.create().texOffs(-5, 7).addBox(0.0F, 0.0F, -2.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.5F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition left_wing2 = left_wing1.addOrReplaceChild("left_wing2", CubeListBuilder.create().texOffs(-2, 23).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, -2.5F));

        PartDefinition left_wing3 = left_wing2.addOrReplaceChild("left_wing3", CubeListBuilder.create().texOffs(-1, 2).addBox(0.0F, 0.0F, -2.0F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition right_wing1 = body_main.addOrReplaceChild("right_wing1", CubeListBuilder.create().texOffs(-5, 7).mirror().addBox(-4.0F, 0.0F, -2.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -0.5F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition right_wing2 = right_wing1.addOrReplaceChild("right_wing2", CubeListBuilder.create().texOffs(-2, 23).mirror().addBox(-1.0F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 0.0F, -2.5F));

        PartDefinition right_wing3 = right_wing2.addOrReplaceChild("right_wing3", CubeListBuilder.create().texOffs(-1, 2).mirror().addBox(-7.0F, 0.0F, -2.0F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 2.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Pterodactylus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.idleAnimationState, PterodactylusAnimations.GROUD_IDLE, ageInTicks);
        this.animate(entity.hangIdleAnimationState, PterodactylusAnimations.HANG_IDLE, ageInTicks);
        this.animateIdle(entity.hoverAnimationState, PterodactylusAnimations.FLY_HOVER, ageInTicks, 1, limbSwingAmount * 4);
        this.animate(entity.flyAnimationState, PterodactylusAnimations.FLY, ageInTicks, 1.25F);
        this.animate(entity.flyFastAnimationState, PterodactylusAnimations.FLYFAST, ageInTicks);
        this.animate(entity.stretchAnimationState, PterodactylusAnimations.GROUND_STRETCH_BLEND, ageInTicks);
        this.animate(entity.hangingStretchAnimationState, PterodactylusAnimations.HANG_STRETCH_BLEND, ageInTicks);

		float partialTicks = ageInTicks - entity.tickCount;
		float flyProgress = entity.getFlyProgress(partialTicks);
		float rollAmount = entity.getFlightRoll(partialTicks) / 57.295776F * flyProgress;
		float flightPitchAmount = entity.getFlightPitch(partialTicks) / 57.295776F * flyProgress;

		this.flight_control.xRot += flightPitchAmount;
		this.flight_control.zRot += rollAmount * 0.7F;
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}