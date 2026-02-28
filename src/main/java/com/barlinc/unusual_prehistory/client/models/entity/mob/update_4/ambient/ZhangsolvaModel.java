package com.barlinc.unusual_prehistory.client.models.entity.mob.update_4.ambient;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.update_4.ambient.ZhangsolvaAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_4.ambient.Zhangsolva;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class ZhangsolvaModel extends UP2Model<Zhangsolva> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart proboscis;
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private final ModelPart left_leg1;
    private final ModelPart right_leg1;
    private final ModelPart left_leg2;
    private final ModelPart right_leg2;
    private final ModelPart left_leg3;
    private final ModelPart right_leg3;

	public ZhangsolvaModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.proboscis = this.body.getChild("proboscis");
        this.right_wing = this.body.getChild("right_wing");
        this.left_wing = this.body.getChild("left_wing");
        this.left_leg1 = this.root.getChild("left_leg1");
        this.right_leg1 = this.root.getChild("right_leg1");
        this.left_leg2 = this.root.getChild("left_leg2");
        this.right_leg2 = this.root.getChild("right_leg2");
        this.left_leg3 = this.root.getChild("left_leg3");
        this.right_leg3 = this.root.getChild("right_leg3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -4.0F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(14, 12).addBox(0.5F, -4.0F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 12).mirror().addBox(-2.5F, -4.0F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition proboscis = body.addOrReplaceChild("proboscis", CubeListBuilder.create().texOffs(20, 23).addBox(0.0F, 0.0F, -6.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -4.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -3.0F, 2.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -3.0F, 2.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition left_leg1 = root.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(8, 23).addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4F, 0.0F, 1.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition right_leg1 = root.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(8, 23).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.4F, 0.0F, 1.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition left_leg2 = root.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(8, 24).addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 0.0F, 1.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition right_leg2 = root.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(8, 24).mirror().addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 0.0F, 1.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition left_leg3 = root.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(8, 23).addBox(0.04F, 0.0F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3F, 0.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition right_leg3 = root.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(8, 23).mirror().addBox(-0.04F, 0.0F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.3F, 0.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(@NotNull Zhangsolva entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateIdle(entity.idleAnimationState, ZhangsolvaAnimations.IDLE, ageInTicks, 1, limbSwingAmount * 4);
        this.animate(entity.flyAnimationState, ZhangsolvaAnimations.FLY, ageInTicks);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}