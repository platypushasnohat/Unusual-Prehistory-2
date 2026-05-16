package com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.update_6.ArthropleuraAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura.ArthropleuraPart;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class ArthropleuraTailModel extends UP2Model<ArthropleuraPart> {

    private final ModelPart root;
    private final ModelPart rotation_control;
    private final ModelPart tail1;
    private final ModelPart leg_control;
    private final ModelPart leg_wiggle;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;
    private final ModelPart segment;
    private final ModelPart segment_jiggle;
    private final ModelPart tail2;
    private final ModelPart tail3;

	public ArthropleuraTailModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.rotation_control = this.root.getChild("rotation_control");
        this.tail1 = this.rotation_control.getChild("tail1");
        this.leg_control = this.tail1.getChild("leg_control");
        this.leg_wiggle = this.leg_control.getChild("leg_wiggle");
        this.leg_left1 = this.leg_wiggle.getChild("leg_left1");
        this.leg_left2 = this.leg_wiggle.getChild("leg_left2");
        this.leg_left3 = this.leg_wiggle.getChild("leg_left3");
        this.leg_right1 = this.leg_wiggle.getChild("leg_right1");
        this.leg_right2 = this.leg_wiggle.getChild("leg_right2");
        this.leg_right3 = this.leg_wiggle.getChild("leg_right3");
        this.segment = this.tail1.getChild("segment");
        this.segment_jiggle = this.segment.getChild("segment_jiggle");
        this.tail2 = this.segment_jiggle.getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition rotation_control = root.addOrReplaceChild("rotation_control", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -1.0F));

        PartDefinition tail1 = rotation_control.addOrReplaceChild("tail1", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -7.25F));

        PartDefinition leg_control = tail1.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_wiggle = leg_control.addOrReplaceChild("leg_wiggle", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left1 = leg_wiggle.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(28, 94).addBox(-1.0F, -1.0F, -0.75F, 9.0F, 6.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.0F, 3.0F, 2.0F, 0.0F, 0.0873F, -0.1309F));

        PartDefinition leg_left2 = leg_wiggle.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(28, 94).addBox(-1.0F, -1.0F, -0.75F, 9.0F, 6.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.0F, 3.0F, 6.5F, 0.0F, -0.1309F, -0.1309F));

        PartDefinition leg_left3 = leg_wiggle.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(28, 94).addBox(-1.0F, -1.0F, -0.75F, 9.0F, 6.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.0F, 3.0F, 11.0F, 0.0F, -0.3491F, -0.1309F));

        PartDefinition leg_right1 = leg_wiggle.addOrReplaceChild("leg_right1", CubeListBuilder.create(), PartPose.offset(-3.0F, 3.0F, 6.0F));

        PartDefinition leg_r1 = leg_right1.addOrReplaceChild("leg_r1", CubeListBuilder.create().texOffs(28, 100).mirror().addBox(-8.0F, -3.0F, -1.75F, 9.0F, 6.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.0F, -3.0F, 0.0F, -0.0873F, 0.1309F));

        PartDefinition leg_right2 = leg_wiggle.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(28, 100).mirror().addBox(-8.0F, -1.0F, -0.75F, 9.0F, 6.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 3.0F, 6.5F, 0.0F, 0.1309F, 0.1309F));

        PartDefinition leg_right3 = leg_wiggle.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(28, 100).mirror().addBox(-8.0F, -1.0F, -0.75F, 9.0F, 6.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 3.0F, 11.0F, 0.0F, 0.3491F, 0.1309F));

        PartDefinition segment = tail1.addOrReplaceChild("segment", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition segment_jiggle = segment.addOrReplaceChild("segment_jiggle", CubeListBuilder.create().texOffs(0, 64).addBox(-6.0F, -3.0F, -0.75F, 12.0F, 6.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(0, 51).addBox(-14.0F, 1.0F, -0.75F, 28.0F, 0.0F, 13.0F, new CubeDeformation(0.0025F))
                .texOffs(85, 51).addBox(-4.0F, -2.0F, -4.75F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail2 = segment_jiggle.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(88, 78).addBox(-3.0F, -2.0F, -0.75F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(82, 59).addBox(-5.0F, 0.0F, 0.25F, 10.0F, 0.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 1.0F, 13.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(-1, 96).addBox(-5.0F, 0.0F, -1.75F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 6.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NotNull ArthropleuraPart entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(ArthropleuraAnimations.TAIL_WALK, limbSwing, limbSwingAmount, 2, 4);
        this.animateIdleSmooth(entity.idleAnimationState, ArthropleuraAnimations.TAIL_IDLE, ageInTicks, limbSwingAmount, 4);
        this.look(rotation_control, rotationY, rotationX, 1.0F, 1.0F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}