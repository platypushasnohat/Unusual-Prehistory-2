package com.barlinc.unusual_prehistory.client.models.entity.mob.future;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.future.TherizinosaurusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.future.Therizinosaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.utils.UP2ModelUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class TherizinosaurusModel extends UP2Model<Therizinosaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart body_adjust;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart hair;
    private final ModelPart left_arm;
    private final ModelPart left_claw1;
    private final ModelPart left_claw2;
    private final ModelPart left_claw3;
    private final ModelPart right_arm;
    private final ModelPart right_claw1;
    private final ModelPart right_claw2;
    private final ModelPart right_claw3;
    private final ModelPart tail;
    private final ModelPart tail_adjust;
    private final ModelPart tail_poof;
    private final ModelPart leg_control;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart left_leg3;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;
    private final ModelPart right_leg3;

	public TherizinosaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.body_adjust = this.body.getChild("body_adjust");
        this.neck = this.body_adjust.getChild("neck");
        this.head = this.neck.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.hair = this.head.getChild("hair");
        this.left_arm = this.body_adjust.getChild("left_arm");
        this.left_claw1 = this.left_arm.getChild("left_claw1");
        this.left_claw2 = this.left_arm.getChild("left_claw2");
        this.left_claw3 = this.left_arm.getChild("left_claw3");
        this.right_arm = this.body_adjust.getChild("right_arm");
        this.right_claw1 = this.right_arm.getChild("right_claw1");
        this.right_claw2 = this.right_arm.getChild("right_claw2");
        this.right_claw3 = this.right_arm.getChild("right_claw3");
        this.tail = this.body_adjust.getChild("tail");
        this.tail_adjust = this.tail.getChild("tail_adjust");
        this.tail_poof = this.tail_adjust.getChild("tail_poof");
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

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 16.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -37.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body_adjust = body.addOrReplaceChild("body_adjust", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition feathers_r1 = body_adjust.addOrReplaceChild("feathers_r1", CubeListBuilder.create().texOffs(176, 94).addBox(-14.5F, 16.0F, -36.0F, 29.0F, 4.0F, 44.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 1.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition Torso_r1 = body_adjust.addOrReplaceChild("Torso_r1", CubeListBuilder.create().texOffs(9, 95).addBox(-14.5F, -16.0F, -36.0F, 29.0F, 31.0F, 45.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition neck = body_adjust.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(5, 179).addBox(-8.5F, -16.0F, -11.0F, 17.0F, 28.0F, 22.0F, new CubeDeformation(0.01F))
                .texOffs(141, 206).addBox(-4.5F, -45.0F, -1.0F, 9.0F, 29.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -41.0F, 1.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(187, 55).addBox(-3.0F, -5.0F, -17.0F, 6.0F, 7.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(183, 79).addBox(-3.0F, 2.0F, -17.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -40.0F, -1.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(243, 56).addBox(-2.0F, 0.0F, -13.0F, 5.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 2.0F, -2.0F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(241, 204).addBox(-5.0F, 0.0F, -5.5F, 10.0F, 14.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -5.0F, -4.5F));

        PartDefinition left_arm = body_adjust.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(240, 229).addBox(0.0F, -4.0F, -4.0F, 6.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(62, 229).addBox(0.0F, -4.0F, 8.0F, 6.0F, 26.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(90, 175).addBox(5.99F, -9.0F, -4.0F, 0.0F, 27.0F, 24.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(14.5F, -35.0F, -9.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition left_claw1 = left_arm.addOrReplaceChild("left_claw1", CubeListBuilder.create().texOffs(188, 244).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 35.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(138, 199).addBox(-5.0F, 33.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 22.0F, 9.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition left_claw2 = left_arm.addOrReplaceChild("left_claw2", CubeListBuilder.create().texOffs(188, 244).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 37.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(138, 199).addBox(-5.0F, 35.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 22.0F, 12.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition left_claw3 = left_arm.addOrReplaceChild("left_claw3", CubeListBuilder.create().texOffs(188, 244).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 35.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(138, 199).addBox(-5.0F, 33.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 22.0F, 15.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition right_arm = body_adjust.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(240, 229).mirror().addBox(-6.0F, -4.0F, -4.0F, 6.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(62, 229).mirror().addBox(-6.0F, -4.0F, 8.0F, 6.0F, 26.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(90, 175).mirror().addBox(-5.99F, -9.0F, -4.0F, 0.0F, 27.0F, 24.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-14.5F, -35.0F, -9.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition right_claw1 = right_arm.addOrReplaceChild("right_claw1", CubeListBuilder.create().texOffs(188, 244).mirror().addBox(-2.0F, 0.0F, -1.0F, 4.0F, 35.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(138, 199).mirror().addBox(2.0F, 33.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 22.0F, 9.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition right_claw2 = right_arm.addOrReplaceChild("right_claw2", CubeListBuilder.create().texOffs(188, 244).mirror().addBox(-2.0F, 0.0F, -1.0F, 4.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(138, 199).mirror().addBox(2.0F, 35.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 22.0F, 12.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition right_claw3 = right_arm.addOrReplaceChild("right_claw3", CubeListBuilder.create().texOffs(188, 244).mirror().addBox(-2.0F, 0.0F, -1.0F, 4.0F, 35.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(138, 199).mirror().addBox(2.0F, 33.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 22.0F, 15.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition tail = body_adjust.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 16.0F));

        PartDefinition tail_adjust = tail.addOrReplaceChild("tail_adjust", CubeListBuilder.create().texOffs(164, 142).addBox(-4.5F, -5.4F, -4.0F, 9.0F, 11.0F, 51.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition tail_poof = tail_adjust.addOrReplaceChild("tail_poof", CubeListBuilder.create().texOffs(0, 0).addBox(-22.5F, 0.0F, -23.0F, 45.0F, 40.0F, 46.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.5F, 27.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(183, 0).addBox(-7.0F, -9.0F, -12.0F, 14.0F, 24.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(188, 204).addBox(6.99F, 5.0F, -12.0F, 0.0F, 14.0F, 26.0F, new CubeDeformation(0.0025F)), PartPose.offset(12.5F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(90, 226).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 27.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 12.0F));

        PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(0, 229).addBox(-7.0F, 0.0F, -9.0F, 14.0F, 4.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(138, 188).addBox(3.0F, 0.0F, -15.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(132, 179).addBox(3.0F, 3.0F, -15.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(132, 179).addBox(-7.0F, 3.0F, -15.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(138, 188).addBox(-7.0F, 0.0F, -15.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(138, 175).addBox(-2.0F, 0.0F, -17.0F, 4.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(131, 175).addBox(-2.0F, 3.0F, -17.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(183, 0).mirror().addBox(-7.0F, -9.0F, -12.0F, 14.0F, 24.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(188, 204).mirror().addBox(-6.99F, 5.0F, -12.0F, 0.0F, 14.0F, 26.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-12.5F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(90, 226).mirror().addBox(-5.0F, -5.0F, -5.0F, 10.0F, 27.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 13.0F, 12.0F));

        PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(0, 229).mirror().addBox(-7.0F, 0.0F, -9.0F, 14.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(138, 188).mirror().addBox(-7.0F, 0.0F, -15.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(132, 179).mirror().addBox(-7.0F, 3.0F, -15.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(132, 179).mirror().addBox(3.0F, 3.0F, -15.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(138, 188).mirror().addBox(3.0F, 0.0F, -15.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(138, 175).mirror().addBox(-2.0F, 0.0F, -17.0F, 4.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(131, 175).mirror().addBox(-2.0F, 3.0F, -17.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 20.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(Therizinosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

        if (!entity.isInWater() && !entity.isEepy() && entity.getPose() != UP2Poses.ATTACKING.get()) {
            if (entity.isRunning()) this.animateWalk(TherizinosaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.15F, 2.3F);
            else this.animateWalk(entity.isShaved() ? TherizinosaurusAnimations.WALK : TherizinosaurusAnimations.WALK_UNSHAVED, limbSwing, limbSwingAmount, 1.6F, 3.2F);
        }

        this.animateIdleSmooth(entity.idleAnimationState, TherizinosaurusAnimations.IDLE, ageInTicks, limbSwingAmount);
        this.animateSmooth(entity.swimAnimationState, TherizinosaurusAnimations.SWIM, ageInTicks);
		this.animateSmooth(entity.attack1AnimationState, TherizinosaurusAnimations.SLASH1, ageInTicks);
		this.animateSmooth(entity.attack2AnimationState, TherizinosaurusAnimations.SLASH2, ageInTicks);
        this.animateSmooth(entity.forageLowAnimationState, TherizinosaurusAnimations.GRAZE_LOW, ageInTicks);
        this.animateSmooth(entity.forageHighAnimationState, TherizinosaurusAnimations.GRAZE_HIGH, ageInTicks);
        this.animateSmooth(entity.shakeAnimationState, TherizinosaurusAnimations.IDLE_SHAKE_BLEND, ageInTicks);
        this.animateSmooth(entity.stretchAnimationState, TherizinosaurusAnimations.IDLE_STRETCH_BLEND, ageInTicks);
        this.animateSmooth(entity.alert1AnimationState, TherizinosaurusAnimations.ALERT_LEFT_BLEND, ageInTicks);
        this.animateSmooth(entity.alert2AnimationState, TherizinosaurusAnimations.ALERT_RIGHT_BLEND, ageInTicks);
        this.animateSmooth(entity.eepyAnimationState, TherizinosaurusAnimations.SIT, ageInTicks);
        this.animateSmooth(entity.roarAnimationState, TherizinosaurusAnimations.AGGRO_ROAR_BLEND, ageInTicks);
        this.animateSmooth(entity.angryAnimationState, TherizinosaurusAnimations.AGGRO_BLEND, ageInTicks);

        UP2ModelUtils.animateHead(entity, this.head, netHeadYaw, headPitch);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}