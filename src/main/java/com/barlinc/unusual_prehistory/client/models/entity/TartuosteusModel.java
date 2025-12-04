package com.barlinc.unusual_prehistory.client.models.entity;

import com.barlinc.unusual_prehistory.client.animations.TartuosteusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.base.UP2Model;
import com.barlinc.unusual_prehistory.entity.Tartuosteus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class TartuosteusModel extends UP2Model<Tartuosteus> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart left_fin;
    private final ModelPart right_fin;
    private final ModelPart tail;

	public TartuosteusModel(ModelPart root) {
        super(0.25F, 72);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.left_fin = this.body.getChild("left_fin");
        this.right_fin = this.body.getChild("right_fin");
        this.tail = this.body.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -3.0F, -9.0F, 20.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition left_fin = body.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(0, 67).addBox(0.0F, -1.0F, -4.0F, 16.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -2.0F, 5.0F));

        PartDefinition right_fin = body.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(0, 67).mirror().addBox(-16.0F, -1.0F, -4.0F, 16.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-10.0F, -2.0F, 5.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(44, 26).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 25).addBox(0.0F, -5.5F, -1.0F, 0.0F, 13.0F, 25.0F, new CubeDeformation(0.0025F))
                .texOffs(43, 49).addBox(-3.0F, 0.5F, 0.0F, 6.0F, 0.0F, 19.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -0.5F, 9.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Tartuosteus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.isInWater()) {
            this.animateWalk(TartuosteusAnimations.SWIM, limbSwing, limbSwingAmount, 2, 4);
        }

        this.animateIdle(entity.swimIdleAnimationState, TartuosteusAnimations.IDLE, ageInTicks,1, limbSwingAmount * 4);
		this.animate(entity.floppingAnimationState, TartuosteusAnimations.FLOP, ageInTicks);

        this.swim_control.xRot = headPitch * (Mth.DEG_TO_RAD);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}