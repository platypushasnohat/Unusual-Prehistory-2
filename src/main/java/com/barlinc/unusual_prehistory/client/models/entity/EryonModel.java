package com.barlinc.unusual_prehistory.client.models.entity;

import com.barlinc.unusual_prehistory.client.animations.EryonAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.base.UP2Model;
import com.barlinc.unusual_prehistory.entity.Eryon;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class EryonModel extends UP2Model<Eryon> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart eyes;
    private final ModelPart left_antennae;
    private final ModelPart right_antennae;
    private final ModelPart left_claw;
    private final ModelPart right_claw;
    private final ModelPart tail;
    private final ModelPart leg_control;
    private final ModelPart left_front_leg;
    private final ModelPart right_front_leg;
    private final ModelPart left_middle_front_leg;
    private final ModelPart right_middle_front_leg;
    private final ModelPart left_middle_back_leg;
    private final ModelPart right_middle_back_leg;
    private final ModelPart left_back_leg;
    private final ModelPart right_back_leg;

	public EryonModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.eyes = this.body.getChild("eyes");
        this.left_antennae = this.body.getChild("left_antennae");
        this.right_antennae = this.body.getChild("right_antennae");
        this.left_claw = this.body.getChild("left_claw");
        this.right_claw = this.body.getChild("right_claw");
        this.tail = this.body.getChild("tail");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_front_leg = this.leg_control.getChild("left_front_leg");
        this.right_front_leg = this.leg_control.getChild("right_front_leg");
        this.left_middle_front_leg = this.leg_control.getChild("left_middle_front_leg");
        this.right_middle_front_leg = this.leg_control.getChild("right_middle_front_leg");
        this.left_middle_back_leg = this.leg_control.getChild("left_middle_back_leg");
        this.right_middle_back_leg = this.leg_control.getChild("right_middle_back_leg");
        this.left_back_leg = this.leg_control.getChild("left_back_leg");
        this.right_back_leg = this.leg_control.getChild("right_back_leg");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 1.5F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -1.0F, -3.5F, 7.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(17, 20).addBox(3.5F, -1.0F, -4.5F, 2.0F, 0.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(17, 20).mirror().addBox(-5.5F, -1.0F, -4.5F, 2.0F, 0.0F, 6.0F, new CubeDeformation(0.001F)).mirror(false)
                .texOffs(0, 21).addBox(-3.5F, -1.0F, 1.5F, 7.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eyes = body.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(22, 19).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.0F, -3.5F));

        PartDefinition left_antennae = body.addOrReplaceChild("left_antennae", CubeListBuilder.create().texOffs(22, 7).addBox(-0.5F, -0.01F, -5.0F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.0F, -1.0F, -2.5F, -0.3491F, 0.0F, 0.0F));

        PartDefinition right_antennae = body.addOrReplaceChild("right_antennae", CubeListBuilder.create().texOffs(22, 7).mirror().addBox(-1.5F, -0.01F, -5.0F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -1.0F, -2.5F, -0.3491F, 0.0F, 0.0F));

        PartDefinition left_claw = body.addOrReplaceChild("left_claw", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, 0.0F, -6.5F, 4.0F, 0.0F, 7.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(3.5F, 0.0F, -3.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition right_claw = body.addOrReplaceChild("right_claw", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-4.0F, 0.0F, -6.5F, 4.0F, 0.0F, 7.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 0.0F, -3.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(22, 12).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(22, 17).addBox(-2.5F, 0.0F, 3.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.5F, 1.5F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition left_front_leg = leg_control.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(14, 26).addBox(0.0F, -0.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(3.5F, 0.0F, -1.5F));

        PartDefinition right_front_leg = leg_control.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(14, 26).mirror().addBox(-3.0F, -0.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(-3.5F, 0.0F, -1.5F));

        PartDefinition left_middle_front_leg = leg_control.addOrReplaceChild("left_middle_front_leg", CubeListBuilder.create().texOffs(14, 26).addBox(0.0F, -0.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(3.5F, 0.0F, -0.5F));

        PartDefinition right_middle_front_leg = leg_control.addOrReplaceChild("right_middle_front_leg", CubeListBuilder.create().texOffs(14, 26).mirror().addBox(-3.0F, -0.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(-3.5F, 0.0F, -0.5F));

        PartDefinition left_middle_back_leg = leg_control.addOrReplaceChild("left_middle_back_leg", CubeListBuilder.create().texOffs(14, 26).addBox(0.0F, -0.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(3.5F, 0.0F, 0.5F));

        PartDefinition right_middle_back_leg = leg_control.addOrReplaceChild("right_middle_back_leg", CubeListBuilder.create().texOffs(14, 26).mirror().addBox(-3.0F, -0.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(-3.5F, 0.0F, 0.5F));

        PartDefinition left_back_leg = leg_control.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(14, 26).addBox(0.0F, -0.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(3.5F, 0.0F, 1.5F));

        PartDefinition right_back_leg = leg_control.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(14, 26).mirror().addBox(-3.0F, -0.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(-3.5F, 0.0F, 1.5F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Eryon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(EryonAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 3.0F);
		this.animateIdle(entity.idleAnimationState, EryonAnimations.IDLE, ageInTicks, 1, limbSwingAmount * 4);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}