package com.barlinc.unusual_prehistory.client.models.entity;

import com.barlinc.unusual_prehistory.client.animations.KaprosuchusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.base.UP2Model;
import com.barlinc.unusual_prehistory.entity.Kaprosuchus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class KaprosuchusModel extends UP2Model<Kaprosuchus> {

    private final ModelPart root;
    private final ModelPart upper_body;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart left_arm;
    private final ModelPart left_hand;
    private final ModelPart right_arm;
    private final ModelPart right_hand;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart left_foot;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;
    private final ModelPart right_foot;

	public KaprosuchusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.upper_body = this.root.getChild("upper_body");
        this.body = this.upper_body.getChild("body");
        this.head = this.body.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tail = this.body.getChild("tail");
        this.left_arm = this.upper_body.getChild("left_arm");
        this.left_hand = this.left_arm.getChild("left_hand");
        this.right_arm = this.upper_body.getChild("right_arm");
        this.right_hand = this.right_arm.getChild("right_hand");
        this.left_leg1 = this.root.getChild("left_leg1");
        this.left_leg2 = this.left_leg1.getChild("left_leg2");
        this.left_foot = this.left_leg2.getChild("left_foot");
        this.right_leg1 = this.root.getChild("right_leg1");
        this.right_leg2 = this.right_leg1.getChild("right_leg2");
        this.right_foot = this.right_leg2.getChild("right_foot");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, -2.0F));

        PartDefinition upper_body = root.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 8.5F));

        PartDefinition body = upper_body.addOrReplaceChild("body", CubeListBuilder.create().texOffs(34, 101).addBox(-3.0F, -4.0F, 3.0F, 6.0F, 8.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(37, 54).addBox(-2.0F, -6.0F, -10.0F, 4.0F, 2.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(82, 0).addBox(-5.5F, -4.0F, -10.0F, 11.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -8.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 102).addBox(-3.5F, -1.0F, -6.0F, 7.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(34, 87).addBox(-2.5F, 1.0F, -18.0F, 5.0F, 2.0F, 12.0F, new CubeDeformation(0.01F))
                .texOffs(98, 41).addBox(-1.5F, 3.0F, -18.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -10.0F));

        PartDefinition fronteeth_r1 = head.addOrReplaceChild("fronteeth_r1", CubeListBuilder.create().texOffs(122, 87).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 1.0F, -18.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition sideteeth_r1 = head.addOrReplaceChild("sideteeth_r1", CubeListBuilder.create().texOffs(64, 101).mirror().addBox(0.0F, -6.0F, -1.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 1.0F, -17.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition sideteeth_r2 = head.addOrReplaceChild("sideteeth_r2", CubeListBuilder.create().texOffs(64, 101).addBox(0.0F, -6.0F, -1.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.5F, 1.0F, -17.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(116, 25).addBox(-2.5F, -1.0F, -5.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 87).addBox(-2.5F, -1.0F, -17.0F, 5.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(68, 87).addBox(-2.5F, 2.0F, -17.0F, 5.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(82, 41).addBox(-2.5F, -2.0F, -6.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(58, 119).addBox(-2.5F, -4.0F, -7.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -1.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -5.5F, -1.5F, 3.0F, 7.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, 10.5F));

        PartDefinition spikes_r1 = tail.addOrReplaceChild("spikes_r1", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(0.0F, -8.0F, -1.0F, 0.0F, 8.0F, 34.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -5.5F, -0.5F, 0.0F, 0.0F, -0.0436F));

        PartDefinition spikes_r2 = tail.addOrReplaceChild("spikes_r2", CubeListBuilder.create().texOffs(0, 45).addBox(0.0F, -8.0F, -1.0F, 0.0F, 8.0F, 34.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.5F, -5.5F, -0.5F, 0.0F, 0.0F, 0.0873F));

        PartDefinition left_arm = upper_body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(78, 119).addBox(-2.0F, -1.0F, -2.0F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 4.0F, -16.0F));

        PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(117, 38).addBox(-2.5F, -1.0F, -3.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 8.9F, -1.0F));

        PartDefinition right_arm = upper_body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(78, 119).mirror().addBox(-1.0F, -1.0F, -2.0F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 4.0F, -16.0F));

        PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(117, 38).mirror().addBox(-2.5F, -1.0F, -3.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, 8.9F, -1.0F));

        PartDefinition left_leg1 = root.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(102, 87).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.0F, 8.5F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(20, 112).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(26, 102).addBox(1.5F, -2.0F, 3.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition left_foot = left_leg2.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(117, 45).addBox(-2.5F, -1.0F, -3.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.9F, 0.0F));

        PartDefinition right_leg1 = root.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(102, 87).mirror().addBox(-2.0F, -3.0F, -3.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 1.0F, 8.5F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(26, 102).mirror().addBox(-1.5F, -2.0F, 3.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(20, 112).mirror().addBox(-1.5F, -2.0F, -1.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition right_foot = right_leg2.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(117, 45).mirror().addBox(-2.5F, -1.0F, -3.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 8.9F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(@NotNull Kaprosuchus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        if (entity.isInWater()) {
            this.animateWalk(KaprosuchusAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        } else {
            if (entity.isRunning()) this.animateWalk(KaprosuchusAnimations.RUN, limbSwing, limbSwingAmount, 1, 2);
            else this.animateWalk(KaprosuchusAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 3);
        }
		this.animateIdle(entity.idleAnimationState, KaprosuchusAnimations.IDLE, ageInTicks, 1, limbSwingAmount * 4);
        this.animateIdle(entity.swimIdleAnimationState, KaprosuchusAnimations.SWIM_IDLE, ageInTicks, 1, limbSwingAmount * 4);
        this.animate(entity.attackAnimationState, KaprosuchusAnimations.BITE_BLEND, ageInTicks);

        if (this.young) this.applyStatic(KaprosuchusAnimations.BABY_TRANSFORM);
        if (entity.isInWater()) this.root.xRot = headPitch * ((float) Math.PI / 180F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}