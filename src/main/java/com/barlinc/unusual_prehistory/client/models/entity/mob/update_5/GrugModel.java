package com.barlinc.unusual_prehistory.client.models.entity.mob.update_5;

import com.barlinc.unusual_prehistory.entity.mob.update_5.Grug;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class GrugModel extends HumanoidModel<Grug> {

    public GrugModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(117, 123).addBox(-3.0F, -5.0F, -4.5F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(95, 55).addBox(-3.0F, -7.0F, -4.5F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(189, 34).addBox(-4.0F, -3.0F, -5.5F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -25.0F, 1.0F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(120, 133).addBox(-2.0F, -4.0F, 0.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -5.5F, -4.5F, -0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(68, 137).addBox(0.0F, -1.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -5.0F, -1.5F, 0.0F, -0.3054F, 0.0F));

        PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(64, 137).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -5.0F, -1.5F, 0.0F, 0.3054F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(94, 33).mirror().addBox(-17.0F, -5.0F, -5.5F, 18.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(98, 0).mirror().addBox(-35.0F, -5.0F, -3.5F, 18.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(56, 134).mirror().addBox(-39.0F, -5.0F, -4.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(122, 55).mirror().addBox(-43.0F, -5.0F, -2.5F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(74, 92).mirror().addBox(-45.0F, -5.0F, -2.5F, 2.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-16.0F, -16.0F, 5.5F, 0.0F, 0.0F, -0.8727F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(94, 33).addBox(-1.0F, -5.0F, -5.5F, 18.0F, 11.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(98, 0).addBox(17.0F, -5.0F, -3.5F, 18.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(56, 134).addBox(37.0F, -5.0F, -4.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(122, 55).addBox(35.0F, -5.0F, -2.5F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(74, 92).addBox(43.0F, -5.0F, -2.5F, 2.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -16.0F, 5.5F, 0.0F, 0.0F, 0.8727F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -11.0F, -8.0F, 32.0F, 16.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(74, 106).addBox(-20.0F, -11.0F, -8.0F, 4.0F, 8.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(0, 116).addBox(16.0F, -11.0F, -8.0F, 4.0F, 8.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(90, 64).addBox(-16.0F, 5.0F, -8.0F, 32.0F, 3.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(0, 33).addBox(-14.0F, 5.0F, -9.0F, 28.0F, 12.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(90, 84).addBox(-14.0F, 17.0F, -9.0F, 28.0F, 3.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(98, 17).addBox(-7.0F, -15.0F, -5.0F, 14.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 4.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(42, 116).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(74, 131).addBox(-2.0F, 10.0F, -2.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(96, 133).addBox(-1.5F, 16.0F, -6.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(42, 134).addBox(-2.0F, 15.75F, -7.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(64, 134).addBox(-1.5F, 16.0F, -7.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 6.0F, 4.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(42, 116).mirror().addBox(-4.0F, 0.0F, -3.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(96, 133).mirror().addBox(-2.5F, 16.0F, -6.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(42, 134).mirror().addBox(-3.0F, 15.75F, -7.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(64, 134).mirror().addBox(-2.5F, 16.0F, -7.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(74, 131).mirror().addBox(-3.0F, 10.0F, -2.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.0F, 6.0F, 4.0F));

        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 4.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }


    @Override
    public void setupAnim(@NotNull Grug entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.rightArm.yRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
        this.leftArm.yRot = -Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.head.yRot = (netHeadYaw * ((float) Math.PI / 180F)) / 2;
        this.head.xRot = (headPitch * ((float) Math.PI / 180F)) / 2;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float r, float g, float b, float a) {
        this.headParts().forEach((part) -> part.render(poseStack, consumer, packedLight, packedOverlay, r, g, b, a));
        this.bodyParts().forEach((part) -> part.render(poseStack, consumer, packedLight, packedOverlay, r, g, b, a));
    }
}