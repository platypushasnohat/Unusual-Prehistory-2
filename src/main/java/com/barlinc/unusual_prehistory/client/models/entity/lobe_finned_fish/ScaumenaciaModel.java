package com.barlinc.unusual_prehistory.client.models.entity.lobe_finned_fish;

import com.barlinc.unusual_prehistory.client.animations.lobe_finned_fish.ScaumenaciaAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.base.UP2Model;
import com.barlinc.unusual_prehistory.entity.LobeFinnedFish;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class ScaumenaciaModel extends UP2Model<LobeFinnedFish> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart dorsal;
    private final ModelPart left_front_fin;
    private final ModelPart right_front_fin;
    private final ModelPart left_back_fin;
    private final ModelPart right_back_fin;
    private final ModelPart tail;

	public ScaumenaciaModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.dorsal = this.body.getChild("dorsal");
        this.left_front_fin = this.body.getChild("left_front_fin");
        this.right_front_fin = this.body.getChild("right_front_fin");
        this.left_back_fin = this.body.getChild("left_back_fin");
        this.right_back_fin = this.body.getChild("right_back_fin");
        this.tail = this.body.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(26, 22).addBox(-1.5F, -0.5F, -7.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.5F, -3.5F, -5.0F, 3.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition dorsal = body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(26, 0).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -3.5F, 0.0F));

        PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create().texOffs(26, 18).addBox(0.0F, 0.0F, -1.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, 2.5F, -3.0F));

        PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create().texOffs(26, 18).mirror().addBox(-5.0F, 0.0F, -1.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, 2.5F, -3.0F));

        PartDefinition left_back_fin = body.addOrReplaceChild("left_back_fin", CubeListBuilder.create().texOffs(26, 12).addBox(0.0F, 0.0F, -0.5F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, 2.5F, 2.5F));

        PartDefinition right_back_fin = body.addOrReplaceChild("right_back_fin", CubeListBuilder.create().texOffs(26, 12).mirror().addBox(-3.0F, 0.0F, -0.5F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, 2.5F, 2.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 12.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(LobeFinnedFish entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(ScaumenaciaAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        this.animateIdle(entity.swimIdleAnimationState, ScaumenaciaAnimations.SWIM_IDLE, ageInTicks, 1, limbSwingAmount * 3);
		this.animate(entity.floppingAnimationState, ScaumenaciaAnimations.FLOP, ageInTicks);
		this.swim_control.xRot = headPitch * (Mth.DEG_TO_RAD);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}