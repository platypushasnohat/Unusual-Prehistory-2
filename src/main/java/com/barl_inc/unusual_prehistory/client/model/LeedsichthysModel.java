package com.barl_inc.unusual_prehistory.client.model;

import com.barl_inc.unusual_prehistory.client.model.animation.LeedsichthysAnimations;
import com.barl_inc.unusual_prehistory.entity.cliff_fossil.Leedsichthys;
import com.mojang.blaze3d.vertex.PoseStack;
import com.platypushasnohat.sinew.client.model.entity.SinewEntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public class LeedsichthysModel extends SinewEntityModel<Leedsichthys> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart tail1;
    private final ModelPart tail2;

    public LeedsichthysModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    protected void setupAnimations(Leedsichthys entity, float limbSwing, float limbSwingAmount, float ageInTicks, float partialTicks, float netHeadYaw, float headPitch) {
        if (entity.isBaby()) {
            this.animateWalkSmooth(entity.swimAnimationState, LeedsichthysAnimations.BABY_SWIM, limbSwing, limbSwingAmount, partialTicks);
            this.animateIdleSmooth(entity.swimIdleAnimationState, LeedsichthysAnimations.BABY_IDLE, ageInTicks, partialTicks, limbSwingAmount);
            if ((entity.getId() & 1) == 0) {
                this.animateSmooth(entity.flopAnimationState, LeedsichthysAnimations.BABY_BEACHED1, ageInTicks, partialTicks);
            } else {
                this.animateSmooth(entity.flopAnimationState, LeedsichthysAnimations.BABY_BEACHED2, ageInTicks, partialTicks);
            }
        } else {
            this.animateWalkSmooth(entity.swimAnimationState, LeedsichthysAnimations.SWIM, limbSwing, limbSwingAmount, partialTicks);
            this.animateIdleSmooth(entity.swimIdleAnimationState, LeedsichthysAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
            if ((entity.getId() & 1) == 0) {
                this.animateSmooth(entity.flopAnimationState, LeedsichthysAnimations.BEACHED1, ageInTicks, partialTicks);
            } else {
                this.animateSmooth(entity.flopAnimationState, LeedsichthysAnimations.BEACHED2, ageInTicks, partialTicks);
            }
        }

        this.rotatePart(this.swim_control, entity.getSwimPitch(partialTicks) * Mth.DEG_TO_RAD, 0.0F, entity.getRoll(partialTicks) * Mth.DEG_TO_RAD);
        this.bendPart(this.tail1, entity, 1, partialTicks);
        this.bendPart(this.tail2, entity, 2, partialTicks);
    }

    public Vec3 getRiderPosition(Vec3 offset) {
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        this.swim_control.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        Vector4f offsetVec = new Vector4f((float) offset.x, (float) offset.y, (float) offset.z, 1.0F);
        offsetVec.mul(poseStack.last().pose());
        Vec3 position = new Vec3(offsetVec.x, offsetVec.y, offsetVec.z);
        poseStack.popPose();
        return position;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -30.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-27.5F, -36.0F, -42.0F, 55.0F, 66.0F, 121.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("gills", CubeListBuilder.create().texOffs(0, 299).addBox(-29.5F, -10.5F, -10.5F, 59.0F, 21.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.5F, -27.5F));

        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(231, 0).addBox(-27.5F, -44.0F, -68.0F, 55.0F, 44.0F, 67.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 8.0F, -41.0F));

        PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(10, 197).addBox(-28.5F, -2.95F, -77.0F, 57.0F, 17.0F, 85.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, -32.0F));

        jaw.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(5, 408).addBox(22.0F, -37.0F, 0.0F, 2.0F, 37.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(5, 408).mirror().addBox(-27.0F, -37.0F, 0.0F, 2.0F, 37.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, -3.0F, -28.0F));

        body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(37, 4).addBox(-0.5F, -20.0F, 24.0F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(56, 63).addBox(-0.5F, -20.0F, 0.0F, 2.0F, 24.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -36.0F, 50.0F));

        body.addOrReplaceChild("pectoralfin_left", CubeListBuilder.create().texOffs(64, 0).addBox(0.0F, 0.0F, -6.0F, 15.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(209, 187).addBox(7.0F, 1.6F, -5.9F, 79.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(27.5F, 19.0F, -10.0F));

        body.addOrReplaceChild("pectoralfin_right", CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-15.0F, 0.0F, -6.0F, 15.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(209, 187).mirror().addBox(-86.0F, 1.6F, -5.9F, 79.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-27.5F, 19.0F, -10.0F));

        body.addOrReplaceChild("pelvicfin_left", CubeListBuilder.create().texOffs(160, 314).addBox(0.0F, -0.9848F, -5.8263F, 13.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(143, 327).addBox(9.0F, 0.6152F, -5.7264F, 53.0F, 3.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(26.5F, 26.0F, 14.0F));

        body.addOrReplaceChild("pelvicfin_right", CubeListBuilder.create().texOffs(160, 314).mirror().addBox(-13.0F, -0.9848F, -5.8263F, 13.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(143, 327).mirror().addBox(-62.0F, 0.6152F, -5.7264F, 53.0F, 3.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-26.5F, 26.0F, 14.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(234, 229).addBox(-14.5F, -8.0F, 0.0F, 29.0F, 28.0F, 70.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -22.0F, 77.0F));

        tail1.addOrReplaceChild("anal", CubeListBuilder.create().texOffs(37, 4).addBox(-0.5F, 21.0F, 24.0F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(56, 63).addBox(-0.5F, 0.0F, 0.0F, 2.0F, 24.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, 0.0F, 24.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 19.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 187).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 6.0F, 20.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 6.0F, 70.0F));

        tail2.addOrReplaceChild("tailfin_top", CubeListBuilder.create().texOffs(80, 15).addBox(-2.0F, -71.0F, 17.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(40, 0).addBox(-2.0F, -71.0F, 1.0F, 4.0F, 71.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -1.0F));

        tail2.addOrReplaceChild("tailfin_bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 71.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(24, 0).addBox(-2.0F, 65.0F, 15.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }

    public static LayerDefinition createBabyBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.5F, -4.0F, 4.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(8, 12).addBox(-2.0F, -2.5F, -7.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(8, 18).addBox(-2.0F, 1.5F, -7.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("fins_left", CubeListBuilder.create().texOffs(20, 7).addBox(0.0F, 0.0F, -1.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 1.5F, -1.0F));

        body.addOrReplaceChild("fins_right", CubeListBuilder.create().texOffs(8, 22).addBox(-4.0F, 0.0F, -1.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 1.5F, -1.0F));

        body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(22, 9).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, 1.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(15, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

        tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, -4.5F, 0.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }
}
