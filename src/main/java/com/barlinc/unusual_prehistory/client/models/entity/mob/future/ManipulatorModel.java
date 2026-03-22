package com.barlinc.unusual_prehistory.client.models.entity.mob.future;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.future.ManipulatorAnimations;
import com.barlinc.unusual_prehistory.client.animations.entity.mob.future.ManipulatorAttackAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.future.Manipulator;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class ManipulatorModel extends UP2Model<Manipulator> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart thorax;
    private final ModelPart head;
    private final ModelPart left_antenna1;
    private final ModelPart left_antenna2;
    private final ModelPart right_antenna1;
    private final ModelPart right_antenna2;
    private final ModelPart left_mandible;
    private final ModelPart right_mandible;
    private final ModelPart left_feelers;
    private final ModelPart right_feelers;
    private final ModelPart abdomen;
    private final ModelPart left_elytra;
    private final ModelPart right_elytra;
    private final ModelPart left_wing_pivot;
    private final ModelPart left_wing;
    private final ModelPart right_wing_pivot;
    private final ModelPart right_wing;
    private final ModelPart limb_control;
    private final ModelPart left_arm_pivot;
    private final ModelPart left_arm1;
    private final ModelPart left_arm2;
    private final ModelPart left_arm3;
    private final ModelPart left_arm4;
    private final ModelPart right_arm_pivot;
    private final ModelPart right_arm1;
    private final ModelPart right_arm2;
    private final ModelPart right_arm3;
    private final ModelPart right_arm4;
    private final ModelPart left_back_leg_pivot;
    private final ModelPart left_back_leg1;
    private final ModelPart left_back_leg2;
    private final ModelPart left_back_leg3;
    private final ModelPart right_back_leg_pivot;
    private final ModelPart right_back_leg1;
    private final ModelPart right_back_leg2;
    private final ModelPart right_back_leg3;
    private final ModelPart left_front_leg_pivot;
    private final ModelPart left_front_leg1;
    private final ModelPart left_front_leg2;
    private final ModelPart left_front_leg3;
    private final ModelPart right_front_leg_pivot;
    private final ModelPart right_front_leg1;
    private final ModelPart right_front_leg2;
    private final ModelPart right_front_leg3;

	public ManipulatorModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.thorax = this.body.getChild("thorax");
        this.head = this.thorax.getChild("head");
        this.left_antenna1 = this.head.getChild("left_antenna1");
        this.left_antenna2 = this.left_antenna1.getChild("left_antenna2");
        this.right_antenna1 = this.head.getChild("right_antenna1");
        this.right_antenna2 = this.right_antenna1.getChild("right_antenna2");
        this.left_mandible = this.head.getChild("left_mandible");
        this.right_mandible = this.head.getChild("right_mandible");
        this.left_feelers = this.head.getChild("left_feelers");
        this.right_feelers = this.head.getChild("right_feelers");
        this.abdomen = this.body.getChild("abdomen");
        this.left_elytra = this.abdomen.getChild("left_elytra");
        this.right_elytra = this.abdomen.getChild("right_elytra");
        this.left_wing_pivot = this.abdomen.getChild("left_wing_pivot");
        this.left_wing = this.left_wing_pivot.getChild("left_wing");
        this.right_wing_pivot = this.abdomen.getChild("right_wing_pivot");
        this.right_wing = this.right_wing_pivot.getChild("right_wing");
        this.limb_control = this.body_main.getChild("limb_control");
        this.left_arm_pivot = this.limb_control.getChild("left_arm_pivot");
        this.left_arm1 = this.left_arm_pivot.getChild("left_arm1");
        this.left_arm2 = this.left_arm1.getChild("left_arm2");
        this.left_arm3 = this.left_arm2.getChild("left_arm3");
        this.left_arm4 = this.left_arm3.getChild("left_arm4");
        this.right_arm_pivot = this.limb_control.getChild("right_arm_pivot");
        this.right_arm1 = this.right_arm_pivot.getChild("right_arm1");
        this.right_arm2 = this.right_arm1.getChild("right_arm2");
        this.right_arm3 = this.right_arm2.getChild("right_arm3");
        this.right_arm4 = this.right_arm3.getChild("right_arm4");
        this.left_back_leg_pivot = this.limb_control.getChild("left_back_leg_pivot");
        this.left_back_leg1 = this.left_back_leg_pivot.getChild("left_back_leg1");
        this.left_back_leg2 = this.left_back_leg1.getChild("left_back_leg2");
        this.left_back_leg3 = this.left_back_leg2.getChild("left_back_leg3");
        this.right_back_leg_pivot = this.limb_control.getChild("right_back_leg_pivot");
        this.right_back_leg1 = this.right_back_leg_pivot.getChild("right_back_leg1");
        this.right_back_leg2 = this.right_back_leg1.getChild("right_back_leg2");
        this.right_back_leg3 = this.right_back_leg2.getChild("right_back_leg3");
        this.left_front_leg_pivot = this.limb_control.getChild("left_front_leg_pivot");
        this.left_front_leg1 = this.left_front_leg_pivot.getChild("left_front_leg1");
        this.left_front_leg2 = this.left_front_leg1.getChild("left_front_leg2");
        this.left_front_leg3 = this.left_front_leg2.getChild("left_front_leg3");
        this.right_front_leg_pivot = this.limb_control.getChild("right_front_leg_pivot");
        this.right_front_leg1 = this.right_front_leg_pivot.getChild("right_front_leg1");
        this.right_front_leg2 = this.right_front_leg1.getChild("right_front_leg2");
        this.right_front_leg3 = this.right_front_leg2.getChild("right_front_leg3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -24.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition thorax = body.addOrReplaceChild("thorax", CubeListBuilder.create().texOffs(96, 110).addBox(-2.5F, -2.0F, -11.0F, 5.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = thorax.addOrReplaceChild("head", CubeListBuilder.create().texOffs(120, 52).addBox(-4.0F, -2.0F, -3.0F, 8.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(120, 59).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 8.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -11.0F));

        PartDefinition left_antenna1 = head.addOrReplaceChild("left_antenna1", CubeListBuilder.create().texOffs(96, 126).addBox(-0.5F, -21.0F, 0.0F, 1.0F, 21.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, 0.0F, -3.0F));

        PartDefinition left_antenna2 = left_antenna1.addOrReplaceChild("left_antenna2", CubeListBuilder.create().texOffs(96, 90).addBox(-0.5F, 0.0F, -20.0F, 1.0F, 0.0F, 20.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -21.0F, 0.0F));

        PartDefinition right_antenna1 = head.addOrReplaceChild("right_antenna1", CubeListBuilder.create().texOffs(96, 126).mirror().addBox(-0.5F, -21.0F, 0.0F, 1.0F, 21.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, 0.0F, -3.0F));

        PartDefinition right_antenna2 = right_antenna1.addOrReplaceChild("right_antenna2", CubeListBuilder.create().texOffs(96, 90).mirror().addBox(-0.5F, 0.0F, -20.0F, 1.0F, 0.0F, 20.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, -21.0F, 0.0F));

        PartDefinition left_mandible = head.addOrReplaceChild("left_mandible", CubeListBuilder.create().texOffs(128, 25).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 6.0F, -2.0F));

        PartDefinition right_mandible = head.addOrReplaceChild("right_mandible", CubeListBuilder.create().texOffs(128, 25).mirror().addBox(0.0F, 0.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 6.0F, -2.0F));

        PartDefinition left_feelers = head.addOrReplaceChild("left_feelers", CubeListBuilder.create().texOffs(120, 0).addBox(0.0F, 0.0F, -4.5F, 0.0F, 20.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(2.0F, 4.0F, -1.5F));

        PartDefinition right_feelers = head.addOrReplaceChild("right_feelers", CubeListBuilder.create().texOffs(120, 0).mirror().addBox(0.0F, 0.0F, -4.5F, 0.0F, 20.0F, 5.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-2.0F, 4.0F, -1.5F));

        PartDefinition abdomen = body.addOrReplaceChild("abdomen", CubeListBuilder.create().texOffs(0, 90).addBox(-4.5F, -3.0F, 0.0F, 9.0F, 7.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_elytra = abdomen.addOrReplaceChild("left_elytra", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -1.0F, 0.0F, 16.0F, 5.0F, 44.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -2.5F, -0.1F));

        PartDefinition right_elytra = abdomen.addOrReplaceChild("right_elytra", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-5.0F, -1.0F, 0.0F, 16.0F, 5.0F, 44.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, -2.5F, -0.1F));

        PartDefinition left_wing_pivot = abdomen.addOrReplaceChild("left_wing_pivot", CubeListBuilder.create(), PartPose.offset(3.0F, -3.25F, 0.0F));

        PartDefinition left_wing = left_wing_pivot.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 49).mirror().addBox(-18.0F, 0.0F, 0.0F, 19.0F, 0.0F, 41.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_wing_pivot = abdomen.addOrReplaceChild("right_wing_pivot", CubeListBuilder.create(), PartPose.offset(-3.0F, -3.25F, 0.0F));

        PartDefinition right_wing = right_wing_pivot.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 49).addBox(-1.0F, 0.0F, 0.0F, 19.0F, 0.0F, 41.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition limb_control = body_main.addOrReplaceChild("limb_control", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition left_arm_pivot = limb_control.addOrReplaceChild("left_arm_pivot", CubeListBuilder.create(), PartPose.offset(2.5F, 0.0F, -4.0F));

        PartDefinition left_arm1 = left_arm_pivot.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(120, 70).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(120, 82).addBox(1.5F, 6.0F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(120, 25).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 25.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 9.0F, 0.0F));

        PartDefinition left_arm3 = left_arm2.addOrReplaceChild("left_arm3", CubeListBuilder.create().texOffs(98, 126).addBox(0.0F, 0.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition left_arm4 = left_arm3.addOrReplaceChild("left_arm4", CubeListBuilder.create().texOffs(116, 126).addBox(-6.0F, 0.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm_pivot = limb_control.addOrReplaceChild("right_arm_pivot", CubeListBuilder.create(), PartPose.offset(-2.5F, 0.0F, -4.0F));

        PartDefinition right_arm1 = right_arm_pivot.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(120, 70).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(120, 82).mirror().addBox(-6.5F, 6.0F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(120, 25).mirror().addBox(0.0F, 0.0F, -1.0F, 2.0F, 25.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.5F, 9.0F, 0.0F));

        PartDefinition right_arm3 = right_arm2.addOrReplaceChild("right_arm3", CubeListBuilder.create().texOffs(98, 126).mirror().addBox(-7.0F, 0.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition right_arm4 = right_arm3.addOrReplaceChild("right_arm4", CubeListBuilder.create().texOffs(116, 126).mirror().addBox(0.0F, 0.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_back_leg_pivot = limb_control.addOrReplaceChild("left_back_leg_pivot", CubeListBuilder.create(), PartPose.offset(3.5F, 0.0F, 4.0F));

        PartDefinition left_back_leg1 = left_back_leg_pivot.addOrReplaceChild("left_back_leg1", CubeListBuilder.create().texOffs(120, 70).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(120, 82).addBox(1.5F, 6.0F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_back_leg2 = left_back_leg1.addOrReplaceChild("left_back_leg2", CubeListBuilder.create().texOffs(120, 25).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 25.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 9.0F, 0.0F));

        PartDefinition left_back_leg3 = left_back_leg2.addOrReplaceChild("left_back_leg3", CubeListBuilder.create().texOffs(98, 126).addBox(0.0F, 0.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition right_back_leg_pivot = limb_control.addOrReplaceChild("right_back_leg_pivot", CubeListBuilder.create(), PartPose.offset(-3.5F, 0.0F, 4.0F));

        PartDefinition right_back_leg1 = right_back_leg_pivot.addOrReplaceChild("right_back_leg1", CubeListBuilder.create().texOffs(120, 70).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(120, 82).mirror().addBox(-6.5F, 6.0F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_back_leg2 = right_back_leg1.addOrReplaceChild("right_back_leg2", CubeListBuilder.create().texOffs(120, 25).mirror().addBox(0.0F, 0.0F, -1.0F, 2.0F, 25.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.5F, 9.0F, 0.0F));

        PartDefinition right_back_leg3 = right_back_leg2.addOrReplaceChild("right_back_leg3", CubeListBuilder.create().texOffs(98, 126).mirror().addBox(-7.0F, 0.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition left_front_leg_pivot = limb_control.addOrReplaceChild("left_front_leg_pivot", CubeListBuilder.create(), PartPose.offset(3.5F, 0.0F, 0.0F));

        PartDefinition left_front_leg1 = left_front_leg_pivot.addOrReplaceChild("left_front_leg1", CubeListBuilder.create().texOffs(120, 70).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(120, 82).addBox(1.5F, 6.0F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_front_leg2 = left_front_leg1.addOrReplaceChild("left_front_leg2", CubeListBuilder.create().texOffs(120, 25).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 25.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 9.0F, 0.0F));

        PartDefinition left_front_leg3 = left_front_leg2.addOrReplaceChild("left_front_leg3", CubeListBuilder.create().texOffs(98, 126).addBox(0.0F, 0.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition right_front_leg_pivot = limb_control.addOrReplaceChild("right_front_leg_pivot", CubeListBuilder.create(), PartPose.offset(-3.5F, 0.0F, -0.5F));

        PartDefinition right_front_leg1 = right_front_leg_pivot.addOrReplaceChild("right_front_leg1", CubeListBuilder.create().texOffs(120, 70).mirror().addBox(-1.5F, 0.0F, -0.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(120, 82).mirror().addBox(-6.5F, 6.0F, -0.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -0.5F));

        PartDefinition right_front_leg2 = right_front_leg1.addOrReplaceChild("right_front_leg2", CubeListBuilder.create().texOffs(120, 25).mirror().addBox(0.0F, 0.0F, 0.0F, 2.0F, 25.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.5F, 9.0F, 0.0F));

        PartDefinition right_front_leg3 = right_front_leg2.addOrReplaceChild("right_front_leg3", CubeListBuilder.create().texOffs(98, 126).mirror().addBox(-7.0F, 0.0F, 0.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 25.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Manipulator entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

        if (entity.isRunning()) {
            this.animateWalk(entity.isHoldingItem() ? ManipulatorAttackAnimations.RUN_ARMED : ManipulatorAnimations.RUN, limbSwing, limbSwingAmount, 1.5F, 3);
        } else {
            this.animateWalk(entity.isHoldingItem() ? ManipulatorAttackAnimations.WALK_ARMED : ManipulatorAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
        }

        this.animateIdleSmooth(entity.idleAnimationState, ManipulatorAnimations.IDLE, ageInTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.idleArmedAnimationState, ManipulatorAttackAnimations.IDLE_ARMED, ageInTicks, limbSwingAmount);
        this.animateSmooth(entity.sitAnimationState, ManipulatorAnimations.SIT, ageInTicks);
        this.animateSmooth(entity.sitArmedAnimationState, ManipulatorAttackAnimations.SIT_ARMED, ageInTicks);
        this.animateSmooth(entity.danceAnimationState, ManipulatorAnimations.DANCE, ageInTicks);
        this.animateSmooth(entity.attackAnimationState, ManipulatorAttackAnimations.ATTACK_UNARMED_BLEND, ageInTicks);
        this.animateSmooth(entity.attackArmedAnimationState, ManipulatorAttackAnimations.ATTACK_ARMED_BLEND, ageInTicks);
        this.animateSmooth(entity.blockAnimationState, ManipulatorAttackAnimations.SHIELDBLOCK_BLEND, ageInTicks);

        this.animateHead(entity, this.head, netHeadYaw, headPitch);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}

    public void translateToHand(@NotNull HumanoidArm arm, @NotNull PoseStack poseStack) {
        this.limb_control.translateAndRotate(poseStack);
        if (arm == HumanoidArm.RIGHT) {
            this.right_arm_pivot.translateAndRotate(poseStack);
            this.right_arm1.translateAndRotate(poseStack);
            this.right_arm2.translateAndRotate(poseStack);
            this.right_arm3.translateAndRotate(poseStack);
            this.right_arm4.translateAndRotate(poseStack);
        } else {
            this.left_arm_pivot.translateAndRotate(poseStack);
            this.left_arm1.translateAndRotate(poseStack);
            this.left_arm2.translateAndRotate(poseStack);
            this.left_arm3.translateAndRotate(poseStack);
            this.left_arm4.translateAndRotate(poseStack);
        }
    }
}