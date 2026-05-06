package com.barlinc.unusual_prehistory.client.models.entity.mob.update_6;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.update_6.BrontoscorpioAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Brontoscorpio;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class BrontoscorpioModel extends UP2Model<Brontoscorpio> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart left_arm1;
    private final ModelPart left_arm2;
    private final ModelPart left_upper_claw;
    private final ModelPart left_lower_claw;
    private final ModelPart right_arm1;
    private final ModelPart right_arm2;
    private final ModelPart right_upper_claw;
    private final ModelPart right_lower_claw;
    private final ModelPart leg_control;
    private final ModelPart left_leg_cluster;
    private final ModelPart left_leg_1;
    private final ModelPart left_leg_pivot_1;
    private final ModelPart left_leg_2;
    private final ModelPart left_leg_pivot_2;
    private final ModelPart left_leg_3;
    private final ModelPart left_leg_pivot_3;
    private final ModelPart left_leg_4;
    private final ModelPart left_leg_pivot_4;
    private final ModelPart right_leg_cluster;
    private final ModelPart right_leg_1;
    private final ModelPart right_leg_pivot_1;
    private final ModelPart right_leg_2;
    private final ModelPart right_leg_pivot_2;
    private final ModelPart right_leg_3;
    private final ModelPart right_leg_pivot_3;
    private final ModelPart right_leg_4;
    private final ModelPart right_leg_pivot_4;

	public BrontoscorpioModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.left_arm1 = this.body.getChild("left_arm1");
        this.left_arm2 = this.left_arm1.getChild("left_arm2");
        this.left_upper_claw = this.left_arm2.getChild("left_upper_claw");
        this.left_lower_claw = this.left_arm2.getChild("left_lower_claw");
        this.right_arm1 = this.body.getChild("right_arm1");
        this.right_arm2 = this.right_arm1.getChild("right_arm2");
        this.right_upper_claw = this.right_arm2.getChild("right_upper_claw");
        this.right_lower_claw = this.right_arm2.getChild("right_lower_claw");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg_cluster = this.leg_control.getChild("left_leg_cluster");
        this.left_leg_1 = this.left_leg_cluster.getChild("left_leg_1");
        this.left_leg_pivot_1 = this.left_leg_1.getChild("left_leg_pivot_1");
        this.left_leg_2 = this.left_leg_cluster.getChild("left_leg_2");
        this.left_leg_pivot_2 = this.left_leg_2.getChild("left_leg_pivot_2");
        this.left_leg_3 = this.left_leg_cluster.getChild("left_leg_3");
        this.left_leg_pivot_3 = this.left_leg_3.getChild("left_leg_pivot_3");
        this.left_leg_4 = this.left_leg_cluster.getChild("left_leg_4");
        this.left_leg_pivot_4 = this.left_leg_4.getChild("left_leg_pivot_4");
        this.right_leg_cluster = this.leg_control.getChild("right_leg_cluster");
        this.right_leg_1 = this.right_leg_cluster.getChild("right_leg_1");
        this.right_leg_pivot_1 = this.right_leg_1.getChild("right_leg_pivot_1");
        this.right_leg_2 = this.right_leg_cluster.getChild("right_leg_2");
        this.right_leg_pivot_2 = this.right_leg_2.getChild("right_leg_pivot_2");
        this.right_leg_3 = this.right_leg_cluster.getChild("right_leg_3");
        this.right_leg_pivot_3 = this.right_leg_3.getChild("right_leg_pivot_3");
        this.right_leg_4 = this.right_leg_cluster.getChild("right_leg_4");
        this.right_leg_pivot_4 = this.right_leg_4.getChild("right_leg_pivot_4");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, -5.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.5F, -3.5F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-3.0F, -2.5F, -3.5F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -0.5F, 2.5F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(30, 2).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.5F, 5.5F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(30, 27).addBox(0.0F, -3.0F, -8.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 24).addBox(-1.0F, -3.0F, -5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 1.0F));

        PartDefinition left_arm1 = body.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, -1.0F, -2.0F, 13.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -0.5F, -4.0F));

        PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(16, 27).addBox(-5.0F, -4.0F, -2.0F, 5.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, 0.0F, -2.0F));

        PartDefinition left_upper_claw = left_arm2.addOrReplaceChild("left_upper_claw", CubeListBuilder.create().texOffs(22, 20).addBox(-7.0F, -5.0F, -1.0F, 8.0F, 5.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(8, 32).addBox(-9.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-9.0F, -3.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 1.0F, -1.0F));

        PartDefinition left_lower_claw = left_arm2.addOrReplaceChild("left_lower_claw", CubeListBuilder.create().texOffs(17, 16).addBox(-8.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(30, 13).addBox(-10.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 35).addBox(-10.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 3.0F, -1.0F));

        PartDefinition right_arm1 = body.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-13.0F, -1.0F, -2.0F, 13.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, -0.5F, -4.0F));

        PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(16, 27).mirror().addBox(0.0F, -4.0F, -2.0F, 5.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-13.0F, 0.0F, -2.0F));

        PartDefinition right_upper_claw = right_arm2.addOrReplaceChild("right_upper_claw", CubeListBuilder.create().texOffs(22, 20).mirror().addBox(-1.0F, -5.0F, -1.0F, 8.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(8, 32).mirror().addBox(7.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 36).mirror().addBox(8.0F, -3.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 1.0F, -1.0F));

        PartDefinition right_lower_claw = right_arm2.addOrReplaceChild("right_lower_claw", CubeListBuilder.create().texOffs(17, 16).mirror().addBox(-2.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(30, 13).mirror().addBox(8.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 35).mirror().addBox(9.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 3.0F, -1.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition left_leg_cluster = leg_control.addOrReplaceChild("left_leg_cluster", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition left_leg_1 = left_leg_cluster.addOrReplaceChild("left_leg_1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition left_leg_pivot_1 = left_leg_1.addOrReplaceChild("left_leg_pivot_1", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, 0.75F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg_2 = left_leg_cluster.addOrReplaceChild("left_leg_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.125F, -0.25F, 0.0F, 0.0F, 0.5236F));

        PartDefinition left_leg_pivot_2 = left_leg_2.addOrReplaceChild("left_leg_pivot_2", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, 0.75F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.125F, 0.0F));

        PartDefinition left_leg_3 = left_leg_cluster.addOrReplaceChild("left_leg_3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, 1.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition left_leg_pivot_3 = left_leg_3.addOrReplaceChild("left_leg_pivot_3", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, 0.75F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg_4 = left_leg_cluster.addOrReplaceChild("left_leg_4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.125F, 3.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition left_leg_pivot_4 = left_leg_4.addOrReplaceChild("left_leg_pivot_4", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, 0.75F, -0.25F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.125F, 0.0F));

        PartDefinition right_leg_cluster = leg_control.addOrReplaceChild("right_leg_cluster", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition right_leg_1 = right_leg_cluster.addOrReplaceChild("right_leg_1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_leg_pivot_1 = right_leg_1.addOrReplaceChild("right_leg_pivot_1", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-8.0F, 0.75F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg_2 = right_leg_cluster.addOrReplaceChild("right_leg_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.125F, -0.25F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_leg_pivot_2 = right_leg_2.addOrReplaceChild("right_leg_pivot_2", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-8.0F, 0.75F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.125F, 0.0F));

        PartDefinition right_leg_3 = right_leg_cluster.addOrReplaceChild("right_leg_3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, 1.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_leg_pivot_3 = right_leg_3.addOrReplaceChild("right_leg_pivot_3", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-8.0F, 0.75F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg_4 = right_leg_cluster.addOrReplaceChild("right_leg_4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.125F, 3.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_leg_pivot_4 = right_leg_4.addOrReplaceChild("right_leg_pivot_4", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-8.0F, 0.75F, -0.25F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.125F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Brontoscorpio entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        if (entity.getPose() != UP2Poses.WARNING.get() && entity.getPose() != UP2Poses.TAIL_WHIPPING.get()) {
            this.animateWalk(BrontoscorpioAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
        }
		this.animateIdleSmooth(entity.idleAnimationState, BrontoscorpioAnimations.IDLE, ageInTicks, limbSwingAmount, 4);
        this.animateSmooth(entity.warnAnimationState, BrontoscorpioAnimations.SCREECH, ageInTicks);
        this.animateSmooth(entity.attack1AnimationState, BrontoscorpioAnimations.PINCH_BLEND1, ageInTicks);
        this.animateSmooth(entity.attack2AnimationState, BrontoscorpioAnimations.PINCH_BLEND2, ageInTicks);
        this.animateSmooth(entity.stingAnimationState, BrontoscorpioAnimations.STING, ageInTicks);
        this.animateSmooth(entity.snip1AnimationState, BrontoscorpioAnimations.IDLE_SNIP_BLEND1, ageInTicks);
        this.animateSmooth(entity.snip2AnimationState, BrontoscorpioAnimations.IDLE_SNIP_BLEND2, ageInTicks);
        this.animateSmooth(entity.quirkAnimationState, BrontoscorpioAnimations.IDLE_QUIRK_BLEND, ageInTicks);
        this.animateSmooth(entity.feedAnimationState, BrontoscorpioAnimations.FEED_BLEND, ageInTicks);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}