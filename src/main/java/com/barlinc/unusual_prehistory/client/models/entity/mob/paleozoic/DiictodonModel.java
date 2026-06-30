package com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.paleozoic.DiictodonAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.Diictodon;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DiictodonModel extends UP2Model<Diictodon> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart leg_control;
    private final ModelPart leg_left;
    private final ModelPart leg_right;
    private final ModelPart arm_control;
    private final ModelPart arm_left;
    private final ModelPart arm_right;

	public DiictodonModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tail = this.body.getChild("tail");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_left = this.leg_control.getChild("leg_left");
        this.leg_right = this.leg_control.getChild("leg_right");
        this.arm_control = this.body_main.getChild("arm_control");
        this.arm_left = this.arm_control.getChild("arm_left");
        this.arm_right = this.arm_control.getChild("arm_right");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.5F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -3.0F, -4.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(12, 19).addBox(-2.5F, -3.0F, -6.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(15, 1).addBox(-2.5F, -1.0F, -6.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -3.0F, -4.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(22, 5).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(0, 19).addBox(-2.0F, 1.0F, -6.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(15, 11).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 3.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition leg_left = leg_control.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(25, 26).addBox(0.0F, 2.0F, -1.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.02F))
                .texOffs(10, 24).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.0F, 2.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition leg_right = leg_control.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(25, 26).mirror().addBox(-2.0F, 2.0F, -1.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.02F)).mirror(false)
                .texOffs(10, 24).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 0.0F, 2.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition arm_left = arm_control.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(10, 24).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(25, 26).addBox(0.0F, 2.0F, -1.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(2.5F, 0.0F, -3.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition arm_right = arm_control.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(25, 26).mirror().addBox(-2.0F, 2.0F, -1.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.02F)).mirror(false)
                .texOffs(10, 24).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 0.0F, -3.0F, 0.0F, -0.5236F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(@NotNull Diictodon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isRunning()) {
            this.animateWalk(DiictodonAnimations.RUN, limbSwing, limbSwingAmount, 1.0F, 2.5F);
        } else {
            this.animateWalk(DiictodonAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }

		this.animateIdleSmooth(entity.idleAnimationState, DiictodonAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);

        if (this.young) {
            this.applyStatic(DiictodonAnimations.BABY_TRANSFORM);
        }

        this.faceTarget(entity, netHeadYaw, headPitch, 2, head);
        float tailYaw = entity.getTailYaw(partialTicks);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}