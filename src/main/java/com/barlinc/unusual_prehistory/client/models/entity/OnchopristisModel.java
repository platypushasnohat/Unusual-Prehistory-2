package com.barlinc.unusual_prehistory.client.models.entity;

import com.barlinc.unusual_prehistory.client.animations.OnchopristisAnimations;
import com.barlinc.unusual_prehistory.client.animations.StethacanthusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.base.UP2Model;
import com.barlinc.unusual_prehistory.entity.Onchopristis;
import com.barlinc.unusual_prehistory.entity.Stethacanthus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class OnchopristisModel extends UP2Model<Onchopristis> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart saw;
    private final ModelPart left_pectoralfin1;
    private final ModelPart left_pectoralfin2;
    private final ModelPart right_pectoralfin1;
    private final ModelPart right_pectoralfin2;
    private final ModelPart tail1;
    private final ModelPart left_pelvicfin;
    private final ModelPart right_pelvicfin;
    private final ModelPart tail2;

	public OnchopristisModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.saw = this.body.getChild("saw");
        this.left_pectoralfin1 = this.body.getChild("left_pectoralfin1");
        this.left_pectoralfin2 = this.left_pectoralfin1.getChild("left_pectoralfin2");
        this.right_pectoralfin1 = this.body.getChild("right_pectoralfin1");
        this.right_pectoralfin2 = this.right_pectoralfin1.getChild("right_pectoralfin2");
        this.tail1 = this.body.getChild("tail1");
        this.left_pelvicfin = this.tail1.getChild("left_pelvicfin");
        this.right_pelvicfin = this.tail1.getChild("right_pelvicfin");
        this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, -3.0F, -11.0F, 19.0F, 5.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(66, 81).addBox(6.5F, -4.0F, -12.0F, 0.0F, 3.0F, 22.0F, new CubeDeformation(0.0025F))
                .texOffs(66, 78).addBox(0.0F, -4.0F, -12.0F, 0.0F, 3.0F, 22.0F, new CubeDeformation(0.0025F))
                .texOffs(66, 81).mirror().addBox(-6.5F, -4.0F, -12.0F, 0.0F, 3.0F, 22.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition saw = body.addOrReplaceChild("saw", CubeListBuilder.create().texOffs(0, 56).addBox(-1.5F, -1.0F, -30.0F, 3.0F, 2.0F, 30.0F, new CubeDeformation(0.0F))
                .texOffs(0, 27).addBox(-3.5F, 0.0F, -29.0F, 7.0F, 0.0F, 29.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 1.0F, -11.0F));

        PartDefinition left_pectoralfin1 = body.addOrReplaceChild("left_pectoralfin1", CubeListBuilder.create().texOffs(82, 0).addBox(0.0F, -0.5F, -9.5F, 4.0F, 1.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 1.5F, -1.5F));

        PartDefinition left_pectoralfin2 = left_pectoralfin1.addOrReplaceChild("left_pectoralfin2", CubeListBuilder.create().texOffs(72, 27).addBox(0.0F, -0.5F, -7.5F, 11.0F, 1.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, 2.0F));

        PartDefinition right_pectoralfin1 = body.addOrReplaceChild("right_pectoralfin1", CubeListBuilder.create().texOffs(82, 0).mirror().addBox(-4.0F, -0.5F, -9.5F, 4.0F, 1.0F, 19.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5F, 1.5F, -1.5F));

        PartDefinition right_pectoralfin2 = right_pectoralfin1.addOrReplaceChild("right_pectoralfin2", CubeListBuilder.create().texOffs(72, 27).mirror().addBox(-11.0F, -0.5F, -7.5F, 11.0F, 1.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 0.0F, 2.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(66, 56).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(22, 88).addBox(0.0F, -4.0F, 6.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 1.0F, 11.0F));

        PartDefinition left_pelvicfin = tail1.addOrReplaceChild("left_pelvicfin", CubeListBuilder.create().texOffs(72, 43).addBox(0.0F, 0.0F, -4.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 0.0F, 4.0F));

        PartDefinition right_pelvicfin = tail1.addOrReplaceChild("right_pelvicfin", CubeListBuilder.create().texOffs(72, 43).mirror().addBox(-6.0F, 0.0F, -4.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 0.0F, 4.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 88).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 23.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Onchopristis entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        if (entity.isInWater()) this.animateWalk(OnchopristisAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        this.animateIdle(entity.swimIdleAnimationState, OnchopristisAnimations.IDLE, ageInTicks, 1, limbSwingAmount * 4);
		this.animate(entity.floppingAnimationState, OnchopristisAnimations.SWIM, ageInTicks, 2);
		this.animate(entity.attack1AnimationState, OnchopristisAnimations.ATTACK_BLEND1, ageInTicks);
        this.animate(entity.attack2AnimationState, OnchopristisAnimations.ATTACK_BLEND2, ageInTicks);
        this.swim_control.xRot = headPitch * (Mth.DEG_TO_RAD) / 2;
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}