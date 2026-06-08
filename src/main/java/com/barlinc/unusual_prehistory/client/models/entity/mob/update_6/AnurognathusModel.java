package com.barlinc.unusual_prehistory.client.models.entity.mob.update_6;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.update_6.AnurognathusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Anurognathus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class AnurognathusModel extends UP2Model<Anurognathus> {

    private final ModelPart root;
    private final ModelPart flight_control;
    private final ModelPart body_main;
    private final ModelPart body_upper;
    private final ModelPart body;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart wing_control;
    private final ModelPart wing_left1;
    private final ModelPart wing_left2;
    private final ModelPart wing_left3;
    private final ModelPart wing_right1;
    private final ModelPart wing_right2;
    private final ModelPart wing_right3;
    private final ModelPart leg_control;
    private final ModelPart leg_left;
    private final ModelPart leg_right;

	public AnurognathusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.flight_control = this.root.getChild("flight_control");
        this.body_main = this.flight_control.getChild("body_main");
        this.body_upper = this.body_main.getChild("body_upper");
        this.body = this.body_upper.getChild("body");
        this.eye_left = this.body.getChild("eye_left");
        this.eye_right = this.body.getChild("eye_right");
        this.jaw = this.body.getChild("jaw");
        this.tail = this.body.getChild("tail");
        this.wing_control = this.body_upper.getChild("wing_control");
        this.wing_left1 = this.wing_control.getChild("wing_left1");
        this.wing_left2 = this.wing_left1.getChild("wing_left2");
        this.wing_left3 = this.wing_left2.getChild("wing_left3");
        this.wing_right1 = this.wing_control.getChild("wing_right1");
        this.wing_right2 = this.wing_right1.getChild("wing_right2");
        this.wing_right3 = this.wing_right2.getChild("wing_right3");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_left = this.leg_control.getChild("leg_left");
        this.leg_right = this.leg_control.getChild("leg_right");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition flight_control = root.addOrReplaceChild("flight_control", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition body_main = flight_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        PartDefinition body = body_upper.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -2.0F, -11.0F, 9.0F, 3.0F, 11.0F, new CubeDeformation(0.01F))
                .texOffs(0, 38).addBox(-4.5F, 1.0F, -5.0F, 9.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(36, 29).addBox(-1.5F, 1.0F, -11.01F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eye_left = body.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(30, 0).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offset(3.0F, -0.5F, -9.5F));

        PartDefinition eye_right = body.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-3.0F, -0.5F, -9.5F));

        PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(30, 22).addBox(-4.5F, 0.0F, -6.0F, 9.0F, 1.0F, 6.0F, new CubeDeformation(-0.01F))
                .texOffs(30, 14).addBox(-4.5F, -1.98F, -6.0F, 9.0F, 2.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 1.0F, -5.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(40, 6).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wing_control = body_upper.addOrReplaceChild("wing_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -8.0F));

        PartDefinition wing_left1 = wing_control.addOrReplaceChild("wing_left1", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.02F)), PartPose.offset(4.5F, 0.0F, 0.0F));

        PartDefinition wing_left2 = wing_left1.addOrReplaceChild("wing_left2", CubeListBuilder.create().texOffs(40, 9).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offset(10.0F, 0.0F, 0.0F));

        PartDefinition wing_left3 = wing_left2.addOrReplaceChild("wing_left3", CubeListBuilder.create().texOffs(0, 30).addBox(0.0F, 0.0F, 0.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wing_right1 = wing_control.addOrReplaceChild("wing_right1", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-10.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-4.5F, 0.0F, 0.0F));

        PartDefinition wing_right2 = wing_right1.addOrReplaceChild("wing_right2", CubeListBuilder.create().texOffs(40, 9).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-10.0F, 0.0F, 0.0F));

        PartDefinition wing_right3 = wing_right2.addOrReplaceChild("wing_right3", CubeListBuilder.create().texOffs(0, 30).mirror().addBox(-8.0F, 0.0F, 0.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        PartDefinition leg_left = leg_control.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(28, 38).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition leg_right = leg_control.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(28, 38).mirror().addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Anurognathus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isFlying() && !entity.isEepy()) {
            this.animateWalk(AnurognathusAnimations.WALK, limbSwing, limbSwingAmount, 1.6F, 3.2F);
        }

        this.animateIdleSmooth(entity.idleAnimationState, AnurognathusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 3.2F);
        this.animateSmooth(entity.flyAnimationState, AnurognathusAnimations.FLY, ageInTicks, partialTicks);
        this.animateSmooth(entity.flyFastAnimationState, AnurognathusAnimations.FLY_FAST, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, AnurognathusAnimations.SLEEP, ageInTicks, partialTicks);

		float rollAmount = entity.getFlightRoll(partialTicks) / (180F / (float) Math.PI);
		float flightPitchAmount = entity.getFlightPitch(partialTicks) / (180F / (float) Math.PI);

        if (entity.isFlying()) {
            this.flight_control.xRot += flightPitchAmount;
            this.flight_control.zRot += rollAmount;
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}