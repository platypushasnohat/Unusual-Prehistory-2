package com.barlinc.unusual_prehistory.client.models.entity;

import com.barlinc.unusual_prehistory.client.animations.LeptictidiumAnimations;
import com.barlinc.unusual_prehistory.client.animations.carnotaurus.CarnotaurusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.base.UP2Model;
import com.barlinc.unusual_prehistory.entity.Leptictidium;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class LeptictidiumModel extends UP2Model<Leptictidium> {

    private final ModelPart Leptictidium;
    private final ModelPart Lepti_Upper_Body;
    private final ModelPart Lepti_Tail;
    private final ModelPart Lepti_Tailtip;
    private final ModelPart Lepti_Arm_Left;
    private final ModelPart Lepti_Arm_Right;
    private final ModelPart Lepti_Ear_Left;
    private final ModelPart Lepti_Ear_Right;
    private final ModelPart Lepti_Leg_Left;
    private final ModelPart Lepti_Foot_Left;
    private final ModelPart Lepti_Leg_Right;
    private final ModelPart Lepti_Foot_Right;

	public LeptictidiumModel(ModelPart root) {
        super(0.5F, 24);
        this.Leptictidium = root.getChild("Leptictidium");
        this.Lepti_Upper_Body = this.Leptictidium.getChild("Lepti_Upper_Body");
        this.Lepti_Tail = this.Lepti_Upper_Body.getChild("Lepti_Tail");
        this.Lepti_Tailtip = this.Lepti_Tail.getChild("Lepti_Tailtip");
        this.Lepti_Arm_Left = this.Lepti_Upper_Body.getChild("Lepti_Arm_Left");
        this.Lepti_Arm_Right = this.Lepti_Upper_Body.getChild("Lepti_Arm_Right");
        this.Lepti_Ear_Left = this.Lepti_Upper_Body.getChild("Lepti_Ear_Left");
        this.Lepti_Ear_Right = this.Lepti_Upper_Body.getChild("Lepti_Ear_Right");
        this.Lepti_Leg_Left = this.Leptictidium.getChild("Lepti_Leg_Left");
        this.Lepti_Foot_Left = this.Lepti_Leg_Left.getChild("Lepti_Foot_Left");
        this.Lepti_Leg_Right = this.Leptictidium.getChild("Lepti_Leg_Right");
        this.Lepti_Foot_Right = this.Lepti_Leg_Right.getChild("Lepti_Foot_Right");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Leptictidium = partdefinition.addOrReplaceChild("Leptictidium", CubeListBuilder.create(), PartPose.offset(1.5F, 14.9F, -0.5F));

        PartDefinition Lepti_Upper_Body = Leptictidium.addOrReplaceChild("Lepti_Upper_Body", CubeListBuilder.create().texOffs(0, 14).addBox(-2.5F, -5.0F, -7.0F, 5.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(24, 14).addBox(-0.5F, 0.0F, -13.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(28, 8).addBox(-0.5F, -2.0F, -14.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 2.0F, 3.5F));

        PartDefinition Lepti_Tail = Lepti_Upper_Body.addOrReplaceChild("Lepti_Tail", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition Lepti_Tailtip = Lepti_Tail.addOrReplaceChild("Lepti_Tailtip", CubeListBuilder.create().texOffs(12, 27).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.5F, 13.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition Lepti_Arm_Left = Lepti_Upper_Body.addOrReplaceChild("Lepti_Arm_Left", CubeListBuilder.create().texOffs(28, 4).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 1.0F, -5.0F));

        PartDefinition Lepti_Arm_Right = Lepti_Upper_Body.addOrReplaceChild("Lepti_Arm_Right", CubeListBuilder.create().texOffs(28, 4).mirror().addBox(-1.5F, 0.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 1.0F, -5.0F));

        PartDefinition Lepti_Ear_Left = Lepti_Upper_Body.addOrReplaceChild("Lepti_Ear_Left", CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, -3.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -5.0F, -4.0F, -0.2618F, -0.2618F, 0.0F));

        PartDefinition Lepti_Ear_Right = Lepti_Upper_Body.addOrReplaceChild("Lepti_Ear_Right", CubeListBuilder.create().texOffs(28, 0).mirror().addBox(-3.0F, -3.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -5.0F, -4.0F, -0.2618F, 0.2618F, 0.0F));

        PartDefinition Lepti_Leg_Left = Leptictidium.addOrReplaceChild("Lepti_Leg_Left", CubeListBuilder.create().texOffs(24, 21).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(22, 27).addBox(-0.5F, 1.0F, 4.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, 3.5F, -0.7854F, 0.0F, 0.0F));

        PartDefinition Lepti_Foot_Left = Lepti_Leg_Left.addOrReplaceChild("Lepti_Foot_Left", CubeListBuilder.create().texOffs(0, 27).addBox(-0.5F, 0.0F, -5.0F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 4.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition Lepti_Leg_Right = Leptictidium.addOrReplaceChild("Lepti_Leg_Right", CubeListBuilder.create().texOffs(24, 21).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(22, 27).mirror().addBox(-0.5F, 1.0F, 4.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 2.0F, 3.5F, -0.7854F, 0.0F, 0.0F));

        PartDefinition Lepti_Foot_Right = Lepti_Leg_Right.addOrReplaceChild("Lepti_Foot_Right", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(-0.5F, 0.0F, -5.0F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.0F, 4.0F, 0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Leptictidium entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (!entity.isInWater()) {
            if (entity.isRunning()) this.animateWalk(CarnotaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.3F, 2.6F);
			else this.animateWalk(CarnotaurusAnimations.WALK, limbSwing, limbSwingAmount, 4, 8);
		}

		if (this.young) this.applyStatic(LeptictidiumAnimations.BABY_TRANSFORM);

		this.animateIdle(entity.idleAnimationState, CarnotaurusAnimations.IDLE, ageInTicks,1, limbSwingAmount * 4);

//		this.neck.xRot += (headPitch * ((float) Math.PI / 180)) / 2;
//		this.neck.yRot += (netHeadYaw * ((float) Math.PI / 180)) / 2;
	}

	@Override
	public @NotNull ModelPart root() {
		return this.Leptictidium;
	}
}