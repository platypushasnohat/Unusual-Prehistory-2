package com.barlinc.unusual_prehistory.client.models.entity.mob.future.ambient;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.future.ambient.DelitzschalaAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.future.ambient.Delitzschala;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DelitzschalaModel extends UP2Model<Delitzschala> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart left_wing1;
    private final ModelPart left_wing2;
    private final ModelPart left_wing3;
    private final ModelPart right_wing1;
    private final ModelPart right_wing2;
    private final ModelPart right_wing3;
    private final ModelPart Tail;

	public DelitzschalaModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.left_wing1 = this.body.getChild("left_wing1");
        this.left_wing2 = this.body.getChild("left_wing2");
        this.left_wing3 = this.body.getChild("left_wing3");
        this.right_wing1 = this.body.getChild("right_wing1");
        this.right_wing2 = this.body.getChild("right_wing2");
        this.right_wing3 = this.body.getChild("right_wing3");
        this.Tail = this.body.getChild("Tail");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, 0.0F, -4.0F, 3.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_wing1 = body.addOrReplaceChild("left_wing1", CubeListBuilder.create().texOffs(7, 7).addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, 0.0F, -2.0F));

        PartDefinition left_wing2 = body.addOrReplaceChild("left_wing2", CubeListBuilder.create().texOffs(6, 4).addBox(0.0F, 0.0F, -1.0F, 7.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, 0.0F, 0.0F));

        PartDefinition left_wing3 = body.addOrReplaceChild("left_wing3", CubeListBuilder.create().texOffs(5, 0).addBox(0.0F, 0.0F, -1.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, 0.0F, 2.0F));

        PartDefinition right_wing1 = body.addOrReplaceChild("right_wing1", CubeListBuilder.create().texOffs(7, 7).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, 0.0F, -2.0F));

        PartDefinition right_wing2 = body.addOrReplaceChild("right_wing2", CubeListBuilder.create().texOffs(6, 4).mirror().addBox(-7.0F, 0.0F, -1.0F, 7.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, 0.0F, 0.0F));

        PartDefinition right_wing3 = body.addOrReplaceChild("right_wing3", CubeListBuilder.create().texOffs(5, 0).mirror().addBox(-5.0F, 0.0F, -1.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, 0.0F, 2.0F));

        PartDefinition Tail = body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(0, 9).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 4.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(@NotNull Delitzschala entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateIdleSmooth(entity.idleAnimationState, DelitzschalaAnimations.IDLE, ageInTicks, limbSwingAmount);
        this.animateSmooth(entity.flyAnimationState, DelitzschalaAnimations.FLY, ageInTicks);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}