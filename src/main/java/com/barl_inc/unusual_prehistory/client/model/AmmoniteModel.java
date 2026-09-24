package com.barl_inc.unusual_prehistory.client.model;

import com.barl_inc.unusual_prehistory.client.model.animation.AmmoniteAnimations;
import com.barl_inc.unusual_prehistory.entity.cliff_fossil.Ammonite;
import com.platypushasnohat.sinew.client.model.entity.AgeableSinewEntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class AmmoniteModel extends AgeableSinewEntityModel<Ammonite> {

    private final ModelPart root;
    private final ModelPart swim_control;

    public AmmoniteModel(ModelPart root) {
        super(RenderType::entityCutout, 0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    protected void setupAnimations(Ammonite entity, float limbSwing, float limbSwingAmount, float ageInTicks, float partialTicks, float netHeadYaw, float headPitch) {
        this.animateWalkSmooth(entity.swimAnimationState, AmmoniteAnimations.SWIM, limbSwing, limbSwingAmount, partialTicks);
        this.animateIdleSmooth(entity.swimIdleAnimationState, AmmoniteAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        if ((entity.getId() & 1) == 0) {
            this.animateSmooth(entity.flopAnimationState, AmmoniteAnimations.FLOP1, ageInTicks, partialTicks);
        } else {
            this.animateSmooth(entity.flopAnimationState, AmmoniteAnimations.FLOP2, ageInTicks, partialTicks);
        }
        this.rotatePart(this.swim_control, -entity.swimPitch.getPitch(partialTicks) * Mth.DEG_TO_RAD, 0.0F, entity.swimPitch.getRoll(partialTicks) * Mth.DEG_TO_RAD);
    }

    public static LayerDefinition createCrioceratitesBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));
        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition shell_crio = body.addOrReplaceChild("shell_crio", CubeListBuilder.create(), PartPose.offset(0.0F, 5.5F, 1.0F));
        shell_crio.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 21).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(20, 27).addBox(-2.0F, -13.0F, 3.0F, 4.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-2.0F, -10.0F, -8.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(30, 10).addBox(-2.0F, -8.0F, -1.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-2.0F, -6.0F, -5.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -13.0F, -8.0F, 4.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition head_crio = body.addOrReplaceChild("head_crio", CubeListBuilder.create(), PartPose.offset(0.0F, 5.5F, 1.0F));
        PartDefinition head = head_crio.addOrReplaceChild("head", CubeListBuilder.create().texOffs(34, 38).addBox(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(26, 41).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, -0.5F, -1.0F));
        eye_left.addOrReplaceChild("eyelash_r1", CubeListBuilder.create().texOffs(40, 10).mirror().addBox(0.0F, -2.0F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -2.0F, 0.5F, 0.0F, 0.0F, 0.2618F));
        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(26, 41).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -0.5F, -1.0F));
        eye_right.addOrReplaceChild("eyelash_r2", CubeListBuilder.create().texOffs(40, 10).addBox(0.0F, -2.0F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.0F, 0.5F, 0.0F, 0.0F, -0.2618F));
        head.addOrReplaceChild("arms_upper", CubeListBuilder.create().texOffs(35, 32).addBox(-1.5F, 0.0F, -6.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -4.0F));
        PartDefinition arms_left = head.addOrReplaceChild("arms_left", CubeListBuilder.create().texOffs(14, 41).mirror().addBox(0.0F, -1.5F, -6.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 0.0F, -4.0F));
        arms_left.addOrReplaceChild("tentacle_left", CubeListBuilder.create().texOffs(10, 45).mirror().addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -5.0F));
        PartDefinition arms_right = head.addOrReplaceChild("arms_right", CubeListBuilder.create().texOffs(14, 41).addBox(0.0F, -1.5F, -6.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 0.0F, -4.0F));
        arms_right.addOrReplaceChild("tentacle_right", CubeListBuilder.create().texOffs(20, 45).addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.0F));
        head.addOrReplaceChild("arms_lower", CubeListBuilder.create().texOffs(32, 32).mirror().addBox(-1.5F, 0.0F, -6.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 1.5F, -4.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public static LayerDefinition createHoplitesBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -7.5F, 0.0F));
        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 22).addBox(-3.0F, -12.5F, 0.0F, 6.0F, 15.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(30, 22).addBox(-3.0F, -12.5F, -5.0F, 6.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(36, 16).addBox(-3.0F, -4.5F, -9.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(2.0F, -13.5F, -6.0F, 0.0F, 8.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).mirror().addBox(-2.0F, -13.5F, -6.0F, 0.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 5.0F, -1.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(30, 37).addBox(-2.5F, -2.5F, -6.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -1.0F));
        head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(0, 46).addBox(-3.5F, -1.0F, -2.0F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -3.0F));
        head.addOrReplaceChild("arms_upper", CubeListBuilder.create().texOffs(36, 0).addBox(-2.5F, 0.0F, -8.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -6.0F));
        PartDefinition arms_left = head.addOrReplaceChild("arms_left", CubeListBuilder.create().texOffs(27, 41).addBox(0.0F, -2.5F, -7.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 0.0F, -6.0F));
        arms_left.addOrReplaceChild("tentacle_left", CubeListBuilder.create().texOffs(27, 46).addBox(0.0F, -2.5F, -7.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));
        PartDefinition arms_right = head.addOrReplaceChild("arms_right", CubeListBuilder.create().texOffs(13, 51).addBox(0.0F, -2.5F, -7.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 0.0F, -6.0F));
        arms_right.addOrReplaceChild("tentacle_right", CubeListBuilder.create().texOffs(27, 51).mirror().addBox(0.0F, -2.5F, -7.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -7.0F));
        head.addOrReplaceChild("arms_lower", CubeListBuilder.create().texOffs(31, 8).addBox(-2.5F, 0.0F, -8.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, -6.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public static LayerDefinition createNostocerasBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, 0.0F));
        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -6.5F, 0.0F));
        PartDefinition shell_nos = body.addOrReplaceChild("shell_nos", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, -1.5F));
        shell_nos.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 22).addBox(-1.5F, 0.0F, -2.5F, 3.0F, 5.0F, 8.0F, new CubeDeformation(0.25F))
                .texOffs(22, 22).addBox(-1.5F, -7.0F, 2.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.2F))
                .texOffs(0, 0).addBox(-4.5F, -10.0F, -3.5F, 9.0F, 3.0F, 9.0F, new CubeDeformation(0.25F))
                .texOffs(0, 12).addBox(-3.5F, -13.0F, -2.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(34, 22).addBox(-1.5F, -15.0F, -0.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition head_nos = body.addOrReplaceChild("head_nos", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, -1.5F));
        PartDefinition head = head_nos.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 32).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(0, 35).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));
        head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(0, 35).mirror().addBox(-0.5F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -0.5F, 0.5F));
        head.addOrReplaceChild("arms_upper", CubeListBuilder.create().texOffs(28, 12).addBox(-1.5F, 0.0F, -5.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -1.5F));
        PartDefinition arms_left = head.addOrReplaceChild("arms_left", CubeListBuilder.create().texOffs(39, 28).addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -0.5F, -1.5F));
        arms_left.addOrReplaceChild("tentacle_left", CubeListBuilder.create().texOffs(38, 30).addBox(0.0F, -1.5F, -6.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));
        PartDefinition arms_right = head.addOrReplaceChild("arms_right", CubeListBuilder.create().texOffs(39, 28).mirror().addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, -0.5F, -1.5F));
        arms_right.addOrReplaceChild("tentacle_right", CubeListBuilder.create().texOffs(38, 30).mirror().addBox(0.0F, -1.5F, -6.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -4.0F));
        head.addOrReplaceChild("arms_lower", CubeListBuilder.create().texOffs(28, 17).addBox(-1.5F, 0.0F, -5.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -1.5F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public static LayerDefinition createPinacocerasBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));
        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition shell_pina = body.addOrReplaceChild("shell_pina", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -4.0F));
        shell_pina.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, 2.0F, 2.0F, 14.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(22, 7).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition head_pina = body.addOrReplaceChild("head_pina", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -4.0F));
        PartDefinition head = head_pina.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, -4.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(14, 23).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(2.0F, -3.0F, 1.0F));
        eye_left.addOrReplaceChild("eyelash_r1", CubeListBuilder.create().texOffs(14, 28).addBox(0.0F, -1.0F, -2.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.0F, -1.0F, 0.0F, 0.0F, 0.2618F));
        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(14, 23).mirror().addBox(-1.0F, -2.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-2.0F, -3.0F, 1.0F));
        eye_right.addOrReplaceChild("eyelash_r2", CubeListBuilder.create().texOffs(14, 28).mirror().addBox(0.0F, -1.0F, -2.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -2.0F, -1.0F, 0.0F, 0.0F, -0.2618F));
        head.addOrReplaceChild("arms_upper", CubeListBuilder.create().texOffs(22, 0).addBox(-1.5F, 0.0F, -7.0F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -2.0F));
        PartDefinition arms_left = head.addOrReplaceChild("arms_left", CubeListBuilder.create().texOffs(26, 18).addBox(0.0F, -1.5F, -7.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -2.5F, -2.0F));
        arms_left.addOrReplaceChild("tentacle_left", CubeListBuilder.create().texOffs(28, 23).addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.0F));
        PartDefinition arms_right = head.addOrReplaceChild("arms_right", CubeListBuilder.create().texOffs(26, 18).mirror().addBox(0.0F, -1.5F, -7.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, -2.5F, -2.0F));
        arms_right.addOrReplaceChild("tentacle_right", CubeListBuilder.create().texOffs(28, 23).mirror().addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -6.0F));
        head.addOrReplaceChild("arms_lower", CubeListBuilder.create().texOffs(19, 0).mirror().addBox(-1.5F, 0.0F, -7.0F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -1.0F, -2.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public static LayerDefinition createTropitesBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 2.0F));
        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition shell_trop = body.addOrReplaceChild("shell_trop", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, -1.0F));
        shell_trop.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -8.5F, -3.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-5.0F, -2.5F, 0.0F, 10.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition head_trop = body.addOrReplaceChild("head_trop", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, -1.0F));
        PartDefinition head = head_trop.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 29).addBox(-4.0F, -2.5F, -4.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(34, 16).addBox(-5.0F, -1.5F, -1.49F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(34, 16).mirror().addBox(4.0F, -1.5F, -1.49F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -2.5F));
        head.addOrReplaceChild("arms_upper", CubeListBuilder.create().texOffs(24, 29).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -4.0F));
        PartDefinition arms_left = head.addOrReplaceChild("arms_left", CubeListBuilder.create().texOffs(41, 22).addBox(0.0F, -1.5F, -4.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, -4.0F));
        arms_left.addOrReplaceChild("tentacle_left", CubeListBuilder.create().texOffs(41, 32).addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));
        PartDefinition arms_right = head.addOrReplaceChild("arms_right", CubeListBuilder.create().texOffs(41, 22).mirror().addBox(0.0F, -1.5F, -4.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 0.0F, -4.0F));
        arms_right.addOrReplaceChild("tentacle_right", CubeListBuilder.create().texOffs(41, 32).mirror().addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -3.0F));
        head.addOrReplaceChild("arms_lower", CubeListBuilder.create().texOffs(24, 33).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, -4.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }
}
