package com.barlinc.unusual_prehistory.client.models.entity.mob.update_6;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.update_6.KingLingcodAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_6.KingLingcod;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class KingLingcodModel extends UP2Model<KingLingcod> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart dorsal1;
    private final ModelPart head;
    private final ModelPart left_eye;
    private final ModelPart right_eye;
    private final ModelPart jaw;
    private final ModelPart left_front_fin;
    private final ModelPart right_front_fin;
    private final ModelPart left_back_fin;
    private final ModelPart right_back_fin;
    private final ModelPart tail1;
    private final ModelPart dorsal2;
    private final ModelPart ventral;
    private final ModelPart tail2;

	public KingLingcodModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.dorsal1 = this.body.getChild("dorsal1");
        this.head = this.body.getChild("head");
        this.left_eye = this.head.getChild("left_eye");
        this.right_eye = this.head.getChild("right_eye");
        this.jaw = this.head.getChild("jaw");
        this.left_front_fin = this.body.getChild("left_front_fin");
        this.right_front_fin = this.body.getChild("right_front_fin");
        this.left_back_fin = this.body.getChild("left_back_fin");
        this.right_back_fin = this.body.getChild("right_back_fin");
        this.tail1 = this.body.getChild("tail1");
        this.dorsal2 = this.tail1.getChild("dorsal2");
        this.ventral = this.tail1.getChild("ventral");
        this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -5.0F, -8.0F, 9.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition dorsal1 = body.addOrReplaceChild("dorsal1", CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, -6.0F, 0.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -5.0F, -6.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(34, 26).addBox(-4.5F, -2.5F, -9.0F, 9.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-4.5F, 0.5F, -9.0F, 9.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, -8.0F));

        PartDefinition left_eye = head.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(50, 18).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -2.5F, -3.5F));

        PartDefinition right_eye = head.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(50, 18).mirror().addBox(-2.0F, -2.0F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, -2.5F, -3.5F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(34, 40).addBox(-5.0F, 0.0F, -9.0F, 10.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(35, 5).mirror().addBox(-6.0F, 0.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(35, 5).addBox(4.0F, 0.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -1.0F));

        PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create(), PartPose.offset(4.5F, 2.0F, -6.0F));

        PartDefinition frontfin_r1 = left_front_fin.addOrReplaceChild("frontfin_r1", CubeListBuilder.create().texOffs(52, 43).addBox(0.01F, -1.0F, -1.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.48F, -0.2182F));

        PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create(), PartPose.offset(-4.5F, 2.0F, -6.0F));

        PartDefinition frontfin_r2 = right_front_fin.addOrReplaceChild("frontfin_r2", CubeListBuilder.create().texOffs(52, 43).mirror().addBox(-0.01F, -1.0F, -1.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, -0.48F, 0.2182F));

        PartDefinition left_back_fin = body.addOrReplaceChild("left_back_fin", CubeListBuilder.create(), PartPose.offset(1.5F, 5.0F, -4.0F));

        PartDefinition bottomfin_r1 = left_back_fin.addOrReplaceChild("bottomfin_r1", CubeListBuilder.create().texOffs(31, 11).addBox(-1.0F, 0.01F, -1.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.6981F));

        PartDefinition right_back_fin = body.addOrReplaceChild("right_back_fin", CubeListBuilder.create(), PartPose.offset(-1.5F, 5.0F, -4.0F));

        PartDefinition bottomfin_r2 = right_back_fin.addOrReplaceChild("bottomfin_r2", CubeListBuilder.create().texOffs(31, 11).mirror().addBox(-3.0F, 0.01F, -1.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, -0.6981F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 26).addBox(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 8.0F));

        PartDefinition dorsal2 = tail1.addOrReplaceChild("dorsal2", CubeListBuilder.create().texOffs(34, 43).addBox(0.0F, -7.5F, -4.5F, 0.0F, 6.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 6.5F));

        PartDefinition ventral = tail1.addOrReplaceChild("ventral", CubeListBuilder.create().texOffs(34, 53).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 2.5F, 2.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(1, 43).addBox(0.0F, -3.5F, 0.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -0.5F, 12.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(KingLingcod entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble() && entity.getPose() != UP2Poses.ATTACKING.get()) {
            this.animateWalk(KingLingcodAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, KingLingcodAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 3);
		this.animateSmooth(entity.flopAnimationState, KingLingcodAnimations.FLOP, ageInTicks, partialTicks);
		this.animateSmooth(entity.attackAnimationState, KingLingcodAnimations.ATTACK, ageInTicks, partialTicks);

        if (entity.isInWaterOrBubble()) {
            this.swim_control.xRot = headPitch * ((float) Math.PI / 180F);
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}