package com.barlinc.unusual_prehistory.client.models.entity.mob.update_4;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.update_4.LeptictidiumAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_4.Leptictidium;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class LeptictidiumModel extends UP2Model<Leptictidium> {

    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart snout;
    private final ModelPart left_ear;
    private final ModelPart right_ear;
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

	public LeptictidiumModel(ModelPart root) {
        super(0.5F, 24);
        this.body_main = root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.snout = this.body.getChild("snout");
        this.left_ear = this.body.getChild("left_ear");
        this.right_ear = this.body.getChild("right_ear");
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

        PartDefinition body_main = partdefinition.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(1.5F, 14.9F, -0.5F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 14).addBox(-2.5F, -5.75F, -7.0F, 5.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 2.0F, 3.5F));

        PartDefinition snout = body.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(24, 14).addBox(-1.0F, -1.25F, -6.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(28, 8).addBox(-1.0F, -3.25F, -7.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.5F, -7.0F));

        PartDefinition left_ear = body.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, -3.75F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(2.5F, -5.0F, -4.0F));

        PartDefinition right_ear = body.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(28, 0).mirror().addBox(-3.0F, -3.75F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-2.5F, -5.0F, -4.0F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(28, 4).addBox(-1.5F, -0.75F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(2.0F, 1.0F, -5.0F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(28, 4).mirror().addBox(-1.5F, -0.75F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-2.0F, 1.0F, -5.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.25F, 0.0F, 1.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(12, 27).addBox(-0.5F, -1.75F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.5F, 13.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(-1.5F, 2.0F, 3.5F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(24, 21).addBox(-1.0F, -1.75F, -1.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(22, 27).addBox(-0.5F, -0.75F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 4.0F));

        PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(0, 27).addBox(-0.5F, -0.75F, -5.0F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(24, 21).mirror().addBox(-1.0F, -1.75F, -1.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(22, 27).mirror().addBox(-0.5F, -0.75F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 1.0F, 4.0F));

        PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(-0.5F, -0.75F, -5.0F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 5.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Leptictidium entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (!entity.isInWaterOrBubble()) {
            if (entity.isRunning()) this.animateWalk(LeptictidiumAnimations.RUN, limbSwing, limbSwingAmount, 1.25F, 2.5F);
			else this.animateWalk(LeptictidiumAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 3);
		}

		if (this.young) this.applyStatic(LeptictidiumAnimations.BABY_TRANSFORM);

		this.animateIdleSmooth(entity.idleAnimationState, LeptictidiumAnimations.IDLE, ageInTicks, limbSwingAmount);
        this.animateSmooth(entity.attackAnimationState, LeptictidiumAnimations.ATTACK, ageInTicks);
        this.animateSmooth(entity.swimAnimationState, LeptictidiumAnimations.SWIM, ageInTicks);
        this.animateSmooth(entity.preenAnimationState, LeptictidiumAnimations.IDLE_PREEN, ageInTicks);
        this.animateSmooth(entity.sniffAnimationState, LeptictidiumAnimations.SNIFF_BLEND, ageInTicks);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.body_main;
	}
}