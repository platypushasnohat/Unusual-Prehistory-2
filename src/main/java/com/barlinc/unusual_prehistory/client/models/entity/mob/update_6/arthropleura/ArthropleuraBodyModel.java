package com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.arthropleura;

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
public class ArthropleuraBodyModel<E extends ArthropleuraPart> extends UP2Model<E> {

    private final ModelPart root;
    private final ModelPart rotation_control;
    private final ModelPart body;
    private final ModelPart leg_control;
    private final ModelPart leg_wiggle;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_left4;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;
    private final ModelPart leg_right4;
    private final ModelPart segment;
    private final ModelPart segment_jiggle;

	public ArthropleuraBodyModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.rotation_control = this.root.getChild("rotation_control");
        this.body = this.rotation_control.getChild("body");
        this.leg_control = this.body.getChild("leg_control");
        this.leg_wiggle = this.leg_control.getChild("leg_wiggle");
        this.leg_left1 = this.leg_wiggle.getChild("leg_left1");
        this.leg_left2 = this.leg_wiggle.getChild("leg_left2");
        this.leg_left3 = this.leg_wiggle.getChild("leg_left3");
        this.leg_left4 = this.leg_wiggle.getChild("leg_left4");
        this.leg_right1 = this.leg_wiggle.getChild("leg_right1");
        this.leg_right2 = this.leg_wiggle.getChild("leg_right2");
        this.leg_right3 = this.leg_wiggle.getChild("leg_right3");
        this.leg_right4 = this.leg_wiggle.getChild("leg_right4");
        this.segment = this.body.getChild("segment");
        this.segment_jiggle = this.segment.getChild("segment_jiggle");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition rotation_control = root.addOrReplaceChild("rotation_control", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition body = rotation_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 12.4775F));

        PartDefinition leg_control = body.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 0.0F));

        PartDefinition leg_wiggle = leg_control.addOrReplaceChild("leg_wiggle", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left1 = leg_wiggle.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(88, 88).addBox(-1.0F, -1.0F, 0.525F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(5.0F, 3.0F, -20.0F, 0.0F, 0.1745F, -0.1745F));

        PartDefinition leg_left2 = leg_wiggle.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(88, 88).addBox(-1.0F, -1.0F, 0.525F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(5.0F, 3.0F, -15.5F, 0.0F, 0.0873F, -0.1745F));

        PartDefinition leg_left3 = leg_wiggle.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(88, 88).addBox(-1.0F, -1.0F, 0.525F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(5.0F, 3.0F, -10.5F, 0.0F, -0.0873F, -0.1745F));

        PartDefinition leg_left4 = leg_wiggle.addOrReplaceChild("leg_left4", CubeListBuilder.create().texOffs(88, 88).addBox(-1.0F, -1.0F, 0.525F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(5.0F, 3.0F, -6.0F, 0.0F, -0.1745F, -0.1745F));

        PartDefinition leg_right1 = leg_wiggle.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(88, 95).mirror().addBox(-12.0F, -1.0F, 0.525F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 3.0F, -20.0F, 0.0F, -0.1745F, 0.1745F));

        PartDefinition leg_right2 = leg_wiggle.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(88, 95).mirror().addBox(-12.0F, -1.0F, 0.525F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 3.0F, -15.5F, 0.0F, -0.0873F, 0.1745F));

        PartDefinition leg_right3 = leg_wiggle.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(88, 95).mirror().addBox(-12.0F, -1.0F, 0.525F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 3.0F, -10.5F, 0.0F, 0.0873F, 0.1745F));

        PartDefinition leg_right4 = leg_wiggle.addOrReplaceChild("leg_right4", CubeListBuilder.create().texOffs(88, 95).mirror().addBox(-12.0F, -1.0F, 0.525F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 3.0F, -6.0F, 0.0F, 0.1745F, 0.1745F));

        PartDefinition segment = body.addOrReplaceChild("segment", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 0.0F));

        PartDefinition segment_jiggle = segment.addOrReplaceChild("segment_jiggle", CubeListBuilder.create().texOffs(0, 22).addBox(-15.1789F, -4.0F, -23.4839F, 28.0F, 7.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-19.1789F, 1.0F, -23.4839F, 36.0F, 0.0F, 22.0F, new CubeDeformation(0.0025F))
                .texOffs(82, 51).addBox(-8.1789F, -2.0F, -1.4839F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NotNull E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        this.look(rotation_control, rotationY, rotationX, 1.0F, 1.0F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}