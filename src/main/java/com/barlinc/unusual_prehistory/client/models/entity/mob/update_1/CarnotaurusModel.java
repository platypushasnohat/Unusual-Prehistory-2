package com.barlinc.unusual_prehistory.client.models.entity.mob.update_1;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.update_1.CarnotaurusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_1.Carnotaurus;
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
public class CarnotaurusModel extends UP2Model<Carnotaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart breathing;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart upper_jaw;
    private final ModelPart left_horn;
    private final ModelPart right_horn;
    private final ModelPart jaw;
    private final ModelPart dewlap;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart leg_control;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart left_leg3;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;
    private final ModelPart right_leg3;

	public CarnotaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.breathing = this.body.getChild("breathing");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.upper_jaw = this.head.getChild("upper_jaw");
        this.left_horn = this.upper_jaw.getChild("left_horn");
        this.right_horn = this.upper_jaw.getChild("right_horn");
        this.jaw = this.head.getChild("jaw");
        this.dewlap = this.jaw.getChild("dewlap");
        this.left_arm = this.body.getChild("left_arm");
        this.right_arm = this.body.getChild("right_arm");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
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

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -27.0F, 6.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition breathing = body.addOrReplaceChild("breathing", CubeListBuilder.create().texOffs(127, 130).addBox(-2.5F, -13.0F, -1.0F, 5.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.5F, -12.0F, -16.0F, 17.0F, 24.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -6.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(74, 90).addBox(-3.5F, -19.0F, -10.0F, 7.0F, 24.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(126, 90).addBox(-2.5F, -21.0F, -4.0F, 5.0F, 25.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, -18.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -12.6F, -5.1F));

        PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(150, 11).addBox(-4.5F, -8.91F, -3.9F, 9.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(146, 36).addBox(-4.5F, -8.91F, -12.9F, 9.0F, 11.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(185, 25).addBox(-4.5F, 2.09F, -12.9F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(150, 24).addBox(-3.5F, 2.09F, -11.9F, 7.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 0.0F));

        PartDefinition left_horn = upper_jaw.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(88, 133).addBox(-2.0F, -2.92F, -12.0F, 4.0F, 6.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(30, 152).addBox(-2.0F, -6.91F, -12.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(58, 110).addBox(-2.0F, -6.91F, -7.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -6.0F, -3.9F, 0.0F, -0.0873F, 0.0873F));

        PartDefinition right_horn = upper_jaw.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(88, 133).mirror().addBox(-2.0F, -2.92F, -12.0F, 4.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 152).mirror().addBox(-2.0F, -6.91F, -12.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(58, 110).mirror().addBox(-2.0F, -6.91F, -7.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.5F, -6.0F, -3.9F, 0.0F, 0.0873F, -0.0873F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 152).addBox(-3.5F, 1.09F, -11.9F, 7.0F, 1.0F, 8.0F, new CubeDeformation(-0.01F))
                .texOffs(148, 82).addBox(-4.5F, 0.09F, -3.9F, 9.0F, 4.0F, 4.0F, new CubeDeformation(-0.01F))
                .texOffs(150, 0).addBox(-4.5F, 2.09F, -12.9F, 9.0F, 2.0F, 9.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 2.5F, 0.0F));

        PartDefinition dewlap = jaw.addOrReplaceChild("dewlap", CubeListBuilder.create().texOffs(126, 148).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 10.0F, 12.0F, new CubeDeformation(-0.01F))
                .texOffs(148, 56).addBox(0.0F, 0.0F, -7.0F, 0.0F, 11.0F, 15.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 4.0F, -4.9F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(58, 98).addBox(-1.5F, 0.0F, -1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(58, 119).addBox(-1.5F, 4.0F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(8.51F, 3.0F, -15.0F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(58, 98).mirror().addBox(-0.5F, 0.0F, -1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(58, 119).mirror().addBox(-0.5F, 4.0F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.51F, 3.0F, -15.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 98).addBox(-4.5F, -5.0F, 0.0F, 9.0F, 12.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(1, 130).addBox(-2.5F, -7.0F, 0.0F, 5.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 10.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 56).addBox(-2.5F, -5.0F, 0.0F, 5.0F, 10.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(74, 56).addBox(-2.5F, -7.0F, 0.0F, 5.0F, 2.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 20.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(7.5F, 3.0F, 0.0F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(98, 0).addBox(-5.0F, -11.0F, -8.0F, 10.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(54, 133).addBox(-3.5F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 7.0F, 6.0F));

        PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(98, 36).addBox(-4.5F, 0.0F, -8.0F, 9.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(58, 114).addBox(2.5F, 0.0F, -11.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(58, 114).addBox(-4.5F, 0.0F, -11.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(58, 105).addBox(-1.5F, 0.0F, -11.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(58, 123).addBox(-1.5F, 0.0F, 7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 1.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(98, 0).mirror().addBox(-5.0F, -11.0F, -8.0F, 10.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-15.0F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(54, 133).mirror().addBox(-3.5F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, 7.0F, 6.0F));

        PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(98, 36).mirror().addBox(-4.5F, 0.0F, -8.0F, 9.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(58, 114).mirror().addBox(-4.5F, 0.0F, -11.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(58, 114).mirror().addBox(2.5F, 0.0F, -11.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(58, 105).mirror().addBox(-1.5F, 0.0F, -11.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(58, 123).mirror().addBox(-1.5F, 0.0F, 7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 15.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Carnotaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (!entity.isInWater() && !entity.isEepy()) {
            if (entity.getPose() != UP2Poses.CHARGING.get()) {
                if (entity.isRunning()) this.animateWalk(CarnotaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.3F, 2.6F);
                else this.animateWalk(CarnotaurusAnimations.WALK, limbSwing, limbSwingAmount, 4, 8);
            } else {
                this.animateWalk(CarnotaurusAnimations.CHARGE, limbSwing, limbSwingAmount, 1.2F, 2.4F);
            }
		}

		if (this.young) this.applyStatic(CarnotaurusAnimations.BABY_TRANSFORM);

		this.animateIdleSmooth(entity.idleAnimationState, CarnotaurusAnimations.IDLE, ageInTicks,limbSwingAmount);
		this.animateSmooth(entity.attack1AnimationState, CarnotaurusAnimations.BITE_BLEND1, ageInTicks);
        this.animateSmooth(entity.attack2AnimationState, CarnotaurusAnimations.BITE_BLEND2, ageInTicks);
        this.animateSmooth(entity.headbuttAnimationState, CarnotaurusAnimations.HEADBUTT_BLEND, ageInTicks);
        this.animateSmooth(entity.attackFast1AnimationState, CarnotaurusAnimations.BITE_FAST_BLEND1, ageInTicks);
        this.animateSmooth(entity.attackFast2AnimationState, CarnotaurusAnimations.BITE_FAST_BLEND2, ageInTicks);
        this.animateSmooth(entity.headbuttFastAnimationState, CarnotaurusAnimations.HEADBUTT_FAST_BLEND, ageInTicks);
        this.animateSmooth(entity.chargeStartAnimationState, CarnotaurusAnimations.CHARGE_START, ageInTicks);
        this.animateSmooth(entity.chargeEndAnimationState, CarnotaurusAnimations.CHARGE_END, ageInTicks);
		this.animateSmooth(entity.roarAnimationState, CarnotaurusAnimations.AGGRO_ROAR_BLEND, ageInTicks);
		this.animateSmooth(entity.angryAnimationState, CarnotaurusAnimations.AGGRO_BLEND, ageInTicks);
        this.animateSmooth(entity.swimAnimationState, CarnotaurusAnimations.SWIM, ageInTicks);
        this.animateSmooth(entity.sniff1AnimationState, CarnotaurusAnimations.SNIFF_BLEND1, ageInTicks);
        this.animateSmooth(entity.sniff2AnimationState, CarnotaurusAnimations.SNIFF_BLEND2, ageInTicks);
        this.animateSmooth(entity.yawnAnimationState, CarnotaurusAnimations.YAWN_BLEND, ageInTicks);
        this.animateSmooth(entity.shakeAnimationState, CarnotaurusAnimations.SHAKE_BLEND, ageInTicks);
        this.animateSmooth(entity.eepyAnimationState, CarnotaurusAnimations.SLEEP, ageInTicks);

//        this.animateHead(entity, this.neck, netHeadYaw, headPitch);
        this.faceTarget(netHeadYaw, headPitch, 2, neck);

        float partialTicks = ageInTicks - entity.tickCount;
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail1.yRot = Mth.lerp(0.2F, this.tail1.yRot, tailYaw * 0.3F);
        this.tail2.yRot = Mth.lerp(0.2F, this.tail2.yRot, tailYaw * 0.25F);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}