package com.barlinc.unusual_prehistory.client.models.entity.mob.update_6;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.update_6.ShringasaurusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Shringasaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class ShringasaurusModel extends UP2Model<Shringasaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body_upper;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart neck_glow1;
    private final ModelPart neck_glow2;
    private final ModelPart neck_glow3;
    private final ModelPart neck_glow4;
    private final ModelPart head;
    private final ModelPart left_eyelid;
    private final ModelPart right_eyelid;
    private final ModelPart left_eye;
    private final ModelPart right_eye;
    private final ModelPart left_horn;
    private final ModelPart right_horn;
    private final ModelPart jaw;
    private final ModelPart throat;
    private final ModelPart throat_glow;
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

	public ShringasaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body_upper = this.body_main.getChild("body_upper");
        this.body = this.body_upper.getChild("body");
        this.neck = this.body.getChild("neck");
        this.neck_glow1 = this.neck.getChild("neck_glow1");
        this.neck_glow2 = this.neck.getChild("neck_glow2");
        this.neck_glow3 = this.neck.getChild("neck_glow3");
        this.neck_glow4 = this.neck.getChild("neck_glow4");
        this.head = this.neck.getChild("head");
        this.left_eyelid = this.head.getChild("left_eyelid");
        this.right_eyelid = this.head.getChild("right_eyelid");
        this.left_eye = this.head.getChild("left_eye");
        this.right_eye = this.head.getChild("right_eye");
        this.left_horn = this.head.getChild("left_horn");
        this.right_horn = this.head.getChild("right_horn");
        this.jaw = this.head.getChild("jaw");
        this.throat = this.jaw.getChild("throat");
        this.throat_glow = this.throat.getChild("throat_glow");
        this.tail = this.body.getChild("tail");
        this.arm_control = this.body_upper.getChild("arm_control");
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

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -16.0F, 0.0F));

        PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 9.0F));

        PartDefinition body = body_upper.addOrReplaceChild("body", CubeListBuilder.create().texOffs(88, 47).addBox(-9.5F, -7.0F, -12.0F, 19.0F, 15.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -9.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 47).addBox(-6.0F, -9.0F, -26.5F, 12.0F, 15.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(74, 94).addBox(3.0F, -11.0F, -22.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 109).addBox(3.0F, -5.0F, -28.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(74, 94).addBox(3.0F, -11.0F, -13.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(74, 94).addBox(3.0F, -11.0F, -4.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(74, 94).mirror().addBox(-5.0F, -11.0F, -22.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(74, 94).mirror().addBox(-5.0F, -11.0F, -13.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(74, 94).mirror().addBox(-5.0F, -11.0F, -4.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 109).mirror().addBox(-5.0F, -5.0F, -28.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, -7.5F, -0.7854F, 0.0F, 0.0F));

        PartDefinition neck_glow1 = neck.addOrReplaceChild("neck_glow1", CubeListBuilder.create().texOffs(75, 72).mirror().addBox(-10.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false)
                .texOffs(75, 72).addBox(-2.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(5.0F, -9.0F, -2.5F));

        PartDefinition neck_glow2 = neck.addOrReplaceChild("neck_glow2", CubeListBuilder.create().texOffs(75, 72).mirror().addBox(-10.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false)
                .texOffs(75, 72).addBox(-2.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(5.0F, -9.0F, -11.5F));

        PartDefinition neck_glow3 = neck.addOrReplaceChild("neck_glow3", CubeListBuilder.create().texOffs(75, 72).mirror().addBox(-10.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false)
                .texOffs(75, 72).addBox(-2.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(5.0F, -9.0F, -20.5F));

        PartDefinition neck_glow4 = neck.addOrReplaceChild("neck_glow4", CubeListBuilder.create().texOffs(8, 109).mirror().addBox(-9.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false)
                .texOffs(8, 109).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(4.0F, -1.0F, -27.5F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(90, 33).addBox(-3.5F, -3.0F, -6.0F, 7.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(48, 102).addBox(-2.5F, -3.0F, -10.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -25.0F, 0.9599F, 0.0F, 0.0F));

        PartDefinition left_eyelid = head.addOrReplaceChild("left_eyelid", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offset(3.0F, -1.5F, -3.0F));

        PartDefinition right_eyelid = head.addOrReplaceChild("right_eyelid", CubeListBuilder.create().texOffs(0, 4).mirror().addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offset(-3.0F, -1.5F, -3.0F));

        PartDefinition left_eye = head.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(0, -4).addBox(0.0F, -1.5F, -1.5F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(3.25F, -1.5F, -3.5F));

        PartDefinition right_eye = head.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(0, -4).mirror().addBox(0.0F, -1.5F, -1.5F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-3.25F, -1.5F, -3.5F));

        PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(100, 103).addBox(1.4F, -6.0F, -2.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(66, 102).addBox(1.4F, -6.0F, -8.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -3.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(100, 103).mirror().addBox(-3.4F, -6.0F, -2.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(66, 102).mirror().addBox(-3.4F, -6.0F, -8.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -3.0F, -3.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(22, 94).addBox(-3.5F, 0.0F, -4.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(82, 103).addBox(-2.5F, 0.0F, -8.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -2.0F));

        PartDefinition throat = jaw.addOrReplaceChild("throat", CubeListBuilder.create().texOffs(90, 21).addBox(-4.5F, 0.0F, -1.5F, 9.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.5F));

        PartDefinition throat_glow = throat.addOrReplaceChild("throat_glow", CubeListBuilder.create().texOffs(120, 21).addBox(-4.5F, -3.0F, -3.0F, 9.0F, 6.0F, 6.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 3.0F, 1.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -4.0F, -3.0F, 9.0F, 11.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 10.0F));

        PartDefinition arm_control = body_upper.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -19.0F));

        PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(90, 0).addBox(-2.5F, -3.0F, -3.0F, 5.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 0.0F, 0.0F));

        PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition foot_r1 = left_arm2.addOrReplaceChild("foot_r1", CubeListBuilder.create().texOffs(48, 94).addBox(-5.0F, 0.0F, -6.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(90, 0).mirror().addBox(-2.5F, -3.0F, -3.0F, 5.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5F, 0.0F, 0.0F));

        PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition foot_r2 = right_arm2.addOrReplaceChild("foot_r2", CubeListBuilder.create().texOffs(48, 94).mirror().addBox(-2.0F, 0.0F, -6.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 9.0F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(88, 86).addBox(-3.5F, -4.0F, -4.0F, 7.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(0, 94).addBox(-2.5F, -2.0F, -2.0F, 5.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 1.0F));

        PartDefinition foot_r3 = left_leg3.addOrReplaceChild("foot_r3", CubeListBuilder.create().texOffs(22, 102).addBox(-5.0F, 0.0F, -6.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(88, 86).mirror().addBox(-3.5F, -4.0F, -4.0F, 7.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(0, 94).mirror().addBox(-2.5F, -2.0F, -2.0F, 5.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 1.0F));

        PartDefinition foot_r4 = right_leg3.addOrReplaceChild("foot_r4", CubeListBuilder.create().texOffs(22, 102).mirror().addBox(-2.0F, 0.0F, -6.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(@NotNull Shringasaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        if (!entity.isCharging()) {
            if (entity.isRunning()) {
                this.animateWalk(ShringasaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.3F, 2.6F);
            }
            else {
                this.animateWalk(ShringasaurusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
            }
        }
		this.animateIdleSmooth(entity.idleAnimationState, ShringasaurusAnimations.IDLE, ageInTicks, limbSwingAmount, entity.isRunning() ? 2.6F : 4);
        this.animateSmooth(entity.eepyAnimationState, ShringasaurusAnimations.SLEEP, ageInTicks);
        this.animateSmooth(entity.attack1AnimationState, ShringasaurusAnimations.ATTACK_BLEND1, ageInTicks);
        this.animateSmooth(entity.attack2AnimationState, ShringasaurusAnimations.ATTACK_BLEND2, ageInTicks);
        this.animateSmooth(entity.chargeStartAnimationState, ShringasaurusAnimations.CHARGE_START, ageInTicks);
        this.animateSmooth(entity.chargeAnimationState, ShringasaurusAnimations.CHARGE, ageInTicks, 1.25F);
        this.animateSmooth(entity.chargeEndAnimationState, ShringasaurusAnimations.CHARGE_END, ageInTicks);

        this.faceTarget(entity, netHeadYaw, headPitch, 2, neck, head);

        float partialTicks = ageInTicks - entity.tickCount;
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail.yRot = Mth.lerp(0.2F, this.tail.yRot, tailYaw * 0.15F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}