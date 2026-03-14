package com.barlinc.unusual_prehistory.client.models.entity.mob.update_4;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.update_4.PsilopterusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_4.Psilopterus;
import com.barlinc.unusual_prehistory.utils.UP2ModelUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class PsilopterusModel extends UP2Model<Psilopterus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart tail;
    private final ModelPart leg_control;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart left_leg3;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;
    private final ModelPart right_leg3;

    public PsilopterusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.left_arm = this.body.getChild("left_arm");
        this.right_arm = this.body.getChild("right_arm");
        this.tail = this.body.getChild("tail");
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

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -11.5F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-3.5F, -6.5F, -8.0F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(5, 38).addBox(-3.5F, -6.5F, 2.0F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(34, 36).addBox(-2.5F, -10.5F, -3.0F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 50).addBox(-1.0F, -10.5F, -10.0F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(21, 58).addBox(-1.0F, -10.5F, -11.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 3).addBox(-2.5F, -10.5F, 2.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 0).addBox(-1.0F, -2.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0025F))
                .texOffs(40, 43).addBox(0.0F, -17.5F, -9.0F, 0.0F, 9.0F, 12.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -4.0F, -7.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(16, 50).addBox(-1.0F, 0.0F, -5.5F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, -2.5F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(34, 16).addBox(0.0F, 0.0F, -0.5F, 1.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, -5.5F, -6.5F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(34, 16).mirror().addBox(-1.0F, 0.0F, -0.5F, 1.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.5F, -5.5F, -6.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 0.0F, 15.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, -6.5F, 2.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.5F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(30, 54).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(54, 36).addBox(0.0F, 1.5F, 1.5F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(54, 41).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.5F, 6.5F, 1.5F));

        PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));

        PartDefinition claw_r1 = left_leg3.addOrReplaceChild("claw_r1", CubeListBuilder.create().texOffs(51, 17).addBox(-2.5F, -2.01F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 19).addBox(-2.5F, -0.01F, -4.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(30, 54).mirror().addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(54, 36).mirror().addBox(-1.0F, 1.5F, 1.5F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(54, 41).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-0.5F, 6.5F, 1.5F));

        PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));

        PartDefinition foot_r1 = right_leg3.addOrReplaceChild("foot_r1", CubeListBuilder.create().texOffs(26, 19).mirror().addBox(-2.5F, -0.01F, -4.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(51, 17).mirror().addBox(1.5F, -2.01F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Psilopterus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        if (!entity.isInWater() && !entity.isEepy()) {
            if (entity.isRunning() && entity.getAttackState() != 2) this.animateWalk(PsilopterusAnimations.RUN, limbSwing, limbSwingAmount, 1.25F, 2.5F);
            else this.animateWalk(PsilopterusAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 3);
        }

        this.animateIdle(entity.idleAnimationState, PsilopterusAnimations.IDLE, ageInTicks,1, limbSwingAmount * 4);
        this.animate(entity.eepyStartAnimationState, PsilopterusAnimations.SLEEP_START, ageInTicks);
        this.animate(entity.eepyAnimationState, PsilopterusAnimations.SLEEP, ageInTicks);
        this.animate(entity.eepyEndAnimationState, PsilopterusAnimations.SLEEP_END, ageInTicks);
        this.animate(entity.swimAnimationState, PsilopterusAnimations.SWIM, ageInTicks);
        this.animate(entity.attack1AnimationState, PsilopterusAnimations.ATTACK_BLEND1, ageInTicks);
        this.animate(entity.attack2AnimationState, PsilopterusAnimations.ATTACK_BLEND2, ageInTicks);
        this.animate(entity.kickAnimationState, PsilopterusAnimations.KICK, ageInTicks);
        this.animate(entity.pokeAnimationState, PsilopterusAnimations.POKE_BLEND, ageInTicks);
        this.animate(entity.dig1AnimationState, PsilopterusAnimations.DIG1, ageInTicks);
        this.animate(entity.dig2AnimationState, PsilopterusAnimations.DIG2, ageInTicks);
        this.animate(entity.preen1AnimationState, PsilopterusAnimations.PREEN1, ageInTicks);
        this.animate(entity.preen2AnimationState, PsilopterusAnimations.PREEN2, ageInTicks);

        if (this.young) this.applyStatic(PsilopterusAnimations.BABY_TRANSFORM);

        UP2ModelUtils.animateHead(entity, this.head, netHeadYaw, headPitch);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}