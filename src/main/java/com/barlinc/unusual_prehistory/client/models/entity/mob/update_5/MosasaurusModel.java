package com.barlinc.unusual_prehistory.client.models.entity.mob.update_5;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.update_5.MosasaurusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Mosasaurus;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused, FieldCanBeLocal")
public class MosasaurusModel extends UP2Model<Mosasaurus> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw_upper;
    private final ModelPart jaw_lower1;
    private final ModelPart jaw_lower2;
    private final ModelPart tongue1;
    private final ModelPart tongue2;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart tail1_rot;
    private final ModelPart tail1;
    private final ModelPart tail2_rot;
    private final ModelPart tail2;
    private final ModelPart tail3_rot;
    private final ModelPart tail3;
    private final ModelPart tail4;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

	public MosasaurusModel(ModelPart root) {
        super(0.25F, 72);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.jaw_upper = this.head.getChild("jaw_upper");
        this.jaw_lower1 = this.head.getChild("jaw_lower1");
        this.jaw_lower2 = this.jaw_lower1.getChild("jaw_lower2");
        this.tongue1 = this.jaw_lower1.getChild("tongue1");
        this.tongue2 = this.tongue1.getChild("tongue2");
        this.left_arm = this.body.getChild("left_arm");
        this.right_arm = this.body.getChild("right_arm");
        this.tail1_rot = this.body.getChild("tail1_rot");
        this.tail1 = this.tail1_rot.getChild("tail1");
        this.tail2_rot = this.tail1.getChild("tail2_rot");
        this.tail2 = this.tail2_rot.getChild("tail2");
        this.tail3_rot = this.tail2.getChild("tail3_rot");
        this.tail3 = this.tail3_rot.getChild("tail3");
        this.tail4 = this.tail3.getChild("tail4");
        this.left_leg = this.tail1.getChild("left_leg");
        this.right_leg = this.tail1.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -21.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-20.5F, -16.0F, -34.0F, 41.0F, 42.0F, 61.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(204, 37).addBox(-7.5F, -9.0F, -17.0F, 15.0F, 30.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(204, 37).addBox(-7.5F, -9.0F, -17.0F, 15.0F, 30.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -33.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(246, 168).addBox(-9.5F, -4.0F, -5.0F, 19.0F, 17.0F, 7.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, -13.0F));

        PartDefinition jaw_upper = head.addOrReplaceChild("jaw_upper", CubeListBuilder.create().texOffs(0, 183).addBox(-10.5F, -5.0F, -27.0F, 21.0F, 11.0F, 29.0F, new CubeDeformation(0.0F))
                .texOffs(0, 223).addBox(-6.5F, -5.0F, -45.0F, 13.0F, 11.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(246, 192).addBox(-5.5F, 6.0F, -44.0F, 11.0F, 3.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(102, 250).addBox(-2.5F, 6.0F, -16.0F, 5.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(246, 212).addBox(-8.5F, 6.0F, -26.0F, 17.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition jaw_lower1 = head.addOrReplaceChild("jaw_lower1", CubeListBuilder.create().texOffs(204, 0).addBox(-10.5F, 0.0F, -29.0F, 21.0F, 8.0F, 29.0F, new CubeDeformation(0.0F))
                .texOffs(250, 89).addBox(-3.5F, -1.0F, -19.0F, 7.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(246, 212).addBox(-8.5F, -1.0F, -28.0F, 17.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 2.0F));

        PartDefinition jaw_lower2 = jaw_lower1.addOrReplaceChild("jaw_lower2", CubeListBuilder.create().texOffs(196, 226).addBox(-5.5F, -9.0F, -17.0F, 11.0F, 3.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(102, 226).addBox(-6.5F, -6.0F, -18.0F, 13.0F, 6.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -29.0F));

        PartDefinition tongue1 = jaw_lower1.addOrReplaceChild("tongue1", CubeListBuilder.create().texOffs(234, 246).addBox(-2.5F, 0.0F, -17.0F, 5.0F, 0.0F, 17.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -0.5F, -9.0F));

        PartDefinition tongue2 = tongue1.addOrReplaceChild("tongue2", CubeListBuilder.create().texOffs(204, 89).addBox(-4.5F, 0.0F, -14.0F, 9.0F, 0.0F, 14.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -17.0F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(100, 183).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(62, 223).addBox(-1.0F, 8.0F, -5.0F, 2.0F, 32.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(20.5F, 11.0F, -25.0F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(100, 183).mirror().addBox(-1.0F, -1.0F, -5.0F, 2.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(62, 223).mirror().addBox(-1.0F, 8.0F, -5.0F, 2.0F, 32.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-20.5F, 11.0F, -25.0F));

        PartDefinition tail1_rot = body.addOrReplaceChild("tail1_rot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 23.0F));

        PartDefinition tail1 = tail1_rot.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 103).addBox(-11.5F, -14.0F, -6.0F, 23.0F, 38.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail2_rot = tail1.addOrReplaceChild("tail2_rot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 30.0F));

        PartDefinition tail2 = tail2_rot.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(130, 103).addBox(-9.5F, -9.0F, 1.0F, 19.0F, 22.0F, 43.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail3_rot = tail2.addOrReplaceChild("tail3_rot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 42.0F));

        PartDefinition tail3 = tail3_rot.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(130, 168).addBox(-5.5F, -6.0F, -6.0F, 12.0F, 12.0F, 46.0F, new CubeDeformation(0.0F))
                .texOffs(100, 202).addBox(-0.5F, -15.0F, 33.0F, 2.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 35.0F));

        PartDefinition cube_r1 = tail4.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(164, 226).addBox(-0.5F, -3.0F, -2.0F, 2.0F, 35.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition left_leg = tail1.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(134, 250).addBox(-1.0F, -1.0F, -3.5F, 2.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(196, 246).addBox(-1.0F, 4.0F, -3.5F, 2.0F, 19.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(11.5F, 7.0F, 13.5F));

        PartDefinition right_leg = tail1.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(134, 250).mirror().addBox(-1.0F, -1.0F, -3.5F, 2.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(196, 246).mirror().addBox(-1.0F, 4.0F, -3.5F, 2.0F, 19.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-11.5F, 7.0F, 13.5F));

        return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(@NotNull Mosasaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float deg = ((float) Math.PI / 180F);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble() || entity.isLeaping()) {
            if (entity.isRunning()) this.animateWalk(MosasaurusAnimations.SWIMFAST, limbSwing, limbSwingAmount, 0.8F, 1);
            else this.animateWalk(MosasaurusAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 2);
        }

        if (this.young) this.applyStatic(MosasaurusAnimations.BABY_TRANSFORM);

        this.animateIdleSmooth(entity.swimIdleAnimationState, MosasaurusAnimations.SWIM_IDLE, ageInTicks, limbSwingAmount);
        this.animateSmooth(entity.flopAnimationState, MosasaurusAnimations.BEACHED, ageInTicks);
        this.animateSmooth(entity.attack1AnimationState, MosasaurusAnimations.BITE_BLEND1, ageInTicks);
        this.animateSmooth(entity.attack2AnimationState, MosasaurusAnimations.BITE_BLEND2, ageInTicks);
        this.animateSmooth(entity.grabStartAnimationState, MosasaurusAnimations.GRAB_START_BLEND, ageInTicks);
        this.animateSmooth(entity.grabAnimationState, MosasaurusAnimations.GRAB_SUCCESS_BLEND, ageInTicks);
        this.animateSmooth(entity.yawnAnimationState, MosasaurusAnimations.YAWN_BLEND, ageInTicks);
        this.animateSmooth(entity.tongueAnimationState, MosasaurusAnimations.TONGUEFLICK_BLEND, ageInTicks);
        this.animateSmooth(entity.nip1AnimationState, MosasaurusAnimations.NIP_BLEND1, ageInTicks);
        this.animateSmooth(entity.nip2AnimationState, MosasaurusAnimations.NIP_BLEND2, ageInTicks);
        this.animate(entity.leapAnimationState, MosasaurusAnimations.JUMP, ageInTicks);

        if (entity.isInWaterOrBubble() && !entity.isLeaping()) {
            this.swim_control.xRot = headPitch * deg / 3;
        } else if (entity.isLeaping()) {
            this.swim_control.xRot = headPitch * deg;
        }

//        double bodyYRot = Mth.wrapDegrees(entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTicks);
//        double segment1Y = (entity.getTrailTransformation(8, partialTicks)) - bodyYRot;
//        double segment2Y = (entity.getTrailTransformation(16, partialTicks)) - bodyYRot - segment1Y;
//        double segment3Y = (entity.getTrailTransformation(24, partialTicks)) - bodyYRot - segment2Y;
//
//        this.tail1.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment1Y) * 0.3F);
//        this.tail2.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment2Y) * 0.26F);
//        this.tail3.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment3Y) * 0.2F);

        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail1.yRot = Mth.lerp(0.2F, this.tail1.yRot, tailYaw * 0.35F);
        this.tail2.yRot = Mth.lerp(0.2F, this.tail2.yRot, tailYaw * 0.3F);
        this.tail3.yRot = Mth.lerp(0.2F, this.tail3.yRot, tailYaw * 0.25F);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }

    public void translateToMouth(PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        this.swim_control.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        this.head.translateAndRotate(poseStack);
        this.jaw_lower1.translateAndRotate(poseStack);
        this.jaw_lower2.translateAndRotate(poseStack);
    }
}