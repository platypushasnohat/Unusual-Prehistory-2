package com.barlinc.unusual_prehistory.client.models.entity.mob.future;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.future.PalaeophisAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.future.Palaeophis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class PalaeophisModel extends UP2Model<Palaeophis> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart head_rotate;
    private final ModelPart head;
    private final ModelPart upper_jaw;
    private final ModelPart jaw;
    private final ModelPart tongue;
    private final ModelPart dewlap;
    private final ModelPart body_rotate1;
    private final ModelPart body1;
    private final ModelPart body_rotate2;
    private final ModelPart body2;
    private final ModelPart body_rotate3;
    private final ModelPart body3;
    private final ModelPart body_rotate4;
    private final ModelPart body4;
    private final ModelPart body_rotate5;
    private final ModelPart body5;
    private final ModelPart tail_rotate1;
    private final ModelPart tail1;
    private final ModelPart tail_rotate2;
    private final ModelPart tail2;
    private final ModelPart tail_rotate3;
    private final ModelPart tail3;

	public PalaeophisModel(ModelPart root) {
        super(0.25F, 72);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.head_rotate = this.body_main.getChild("head_rotate");
        this.head = this.head_rotate.getChild("head");
        this.upper_jaw = this.head.getChild("upper_jaw");
        this.jaw = this.head.getChild("jaw");
        this.tongue = this.jaw.getChild("tongue");
        this.dewlap = this.jaw.getChild("dewlap");
        this.body_rotate1 = this.head.getChild("body_rotate1");
        this.body1 = this.body_rotate1.getChild("body1");
        this.body_rotate2 = this.body1.getChild("body_rotate2");
        this.body2 = this.body_rotate2.getChild("body2");
        this.body_rotate3 = this.body2.getChild("body_rotate3");
        this.body3 = this.body_rotate3.getChild("body3");
        this.body_rotate4 = this.body3.getChild("body_rotate4");
        this.body4 = this.body_rotate4.getChild("body4");
        this.body_rotate5 = this.body4.getChild("body_rotate5");
        this.body5 = this.body_rotate5.getChild("body5");
        this.tail_rotate1 = this.body5.getChild("tail_rotate1");
        this.tail1 = this.tail_rotate1.getChild("tail1");
        this.tail_rotate2 = this.tail1.getChild("tail_rotate2");
        this.tail2 = this.tail_rotate2.getChild("tail2");
        this.tail_rotate3 = this.tail2.getChild("tail_rotate3");
        this.tail3 = this.tail_rotate3.getChild("tail3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 76.0F));

        PartDefinition head_rotate = body_main.addOrReplaceChild("head_rotate", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, -89.0F));

        PartDefinition head = head_rotate.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(54, 170).addBox(-7.0F, -2.0F, -5.0F, 14.0F, 2.0F, 7.0F, new CubeDeformation(0.01F))
                .texOffs(56, 181).addBox(-5.0F, -1.0F, -21.0F, 10.0F, 5.0F, 13.0F, new CubeDeformation(0.01F))
                .texOffs(182, 21).addBox(-5.0F, 4.0F, -21.0F, 10.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(167, 21).addBox(-1.5F, 4.0F, -16.0F, 3.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(180, 40).addBox(-7.0F, -2.0F, -8.0F, 14.0F, 6.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -2.0F, -2.0F));

        PartDefinition crest_r1 = upper_jaw.addOrReplaceChild("crest_r1", CubeListBuilder.create().texOffs(134, 180).mirror().addBox(0.0F, -4.0F, -4.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.25F, 0.0F, -11.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition crest_r2 = upper_jaw.addOrReplaceChild("crest_r2", CubeListBuilder.create().texOffs(134, 180).addBox(0.0F, -4.0F, -4.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.25F, 0.0F, -11.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(96, 125).addBox(-5.0F, 4.0F, -21.0F, 10.0F, 3.0F, 13.0F, new CubeDeformation(0.01F))
                .texOffs(115, 47).addBox(-7.0F, 4.0F, -8.0F, 14.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(54, 156).addBox(-7.0F, 0.0F, -5.0F, 14.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(182, 1).addBox(-5.0F, 1.0F, -21.0F, 10.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -2.0F));

        PartDefinition tongue = jaw.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(101, 180).addBox(-2.0F, 0.0F, -15.0F, 4.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -5.0F));

        PartDefinition dewlap = jaw.addOrReplaceChild("dewlap", CubeListBuilder.create().texOffs(99, 154).addBox(-3.0F, 0.0F, -11.0F, 6.0F, 5.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 2.0F));

        PartDefinition body_rotate1 = head.addOrReplaceChild("body_rotate1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body1 = body_rotate1.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(160, 154).addBox(-5.5F, -5.0F, 0.0F, 11.0F, 10.0F, 17.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body_rotate2 = body1.addOrReplaceChild("body_rotate2", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 16.0F));

        PartDefinition body2 = body_rotate2.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(104, 53).addBox(-6.5F, -7.0F, 1.0F, 13.0F, 13.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body_rotate3 = body2.addOrReplaceChild("body_rotate3", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 37.0F));

        PartDefinition body3 = body_rotate3.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, -9.0F, 1.0F, 19.0F, 18.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body_rotate4 = body3.addOrReplaceChild("body_rotate4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 36.0F));

        PartDefinition body4 = body_rotate4.addOrReplaceChild("body4", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, -9.0F, 0.0F, 19.0F, 18.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body_rotate5 = body4.addOrReplaceChild("body_rotate5", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 35.0F));

        PartDefinition body5 = body_rotate5.addOrReplaceChild("body5", CubeListBuilder.create().texOffs(0, 53).addBox(-7.5F, -11.0F, 0.0F, 15.0F, 16.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail_rotate1 = body5.addOrReplaceChild("tail_rotate1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 37.0F));

        PartDefinition tail1 = tail_rotate1.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 106).addBox(-5.5F, -8.0F, 0.0F, 11.0F, 13.0F, 37.0F, new CubeDeformation(0.0F))
                .texOffs(0, 156).addBox(-0.5F, 5.0F, 11.0F, 1.0F, 7.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail_rotate2 = tail1.addOrReplaceChild("tail_rotate2", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 37.0F));

        PartDefinition tail2 = tail_rotate2.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(104, 103).addBox(-2.5F, -4.0F, 0.0F, 5.0F, 8.0F, 43.0F, new CubeDeformation(0.0F))
                .texOffs(108, 0).addBox(-0.5F, 4.0F, 0.0F, 1.0F, 12.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail_rotate3 = tail2.addOrReplaceChild("tail_rotate3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 35.0F));

        PartDefinition tail3 = tail_rotate3.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(157, 103).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 17.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Palaeophis entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

        if (entity.isInWater()) {
            this.animateWalk(PalaeophisAnimations.SWIM, limbSwing, limbSwingAmount, 1.25F, 2.5F);
            this.animateIdle(entity.idleAnimationState, PalaeophisAnimations.SWIM, ageInTicks, 0.8F, limbSwingAmount * 2);
        } else {
            this.animate(entity.idleAnimationState, PalaeophisAnimations.IDLE, ageInTicks);
        }

        float deg = ((float) Math.PI / 180F);
        float partialTicks = ageInTicks - entity.tickCount;
        double defaultY = Mth.wrapDegrees(entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTicks);
        double tail1Y = (entity.getTrailTransformation(8, partialTicks)) - defaultY;
        double tail2Y = (entity.getTrailTransformation(15, partialTicks)) - defaultY - tail1Y;
        double tail3Y = (entity.getTrailTransformation(21, partialTicks)) - defaultY - tail2Y;
        double tail4Y = (entity.getTrailTransformation(26, partialTicks)) - defaultY - tail3Y;
        double tail5Y = (entity.getTrailTransformation(30, partialTicks)) - defaultY - tail4Y;
        double tail6Y = (entity.getTrailTransformation(33, partialTicks)) - defaultY - tail5Y;
        double tail7Y = (entity.getTrailTransformation(35, partialTicks)) - defaultY - tail6Y;

        float swimPitch = Mth.lerp(0.2F, this.root.xRot, Mth.clamp(headPitch * deg, -0.4F, 0.4F));
        this.root.xRot += swimPitch * 0.35F;
        this.head.xRot += swimPitch * 0.4F;
        this.body_main.xRot += swimPitch * 0.4F;
        this.body1.xRot += swimPitch * 0.35F;
        this.body2.xRot += swimPitch * 0.35F;
        this.body3.xRot += swimPitch * 0.3F;
        this.body4.xRot += swimPitch * 0.3F;
        this.tail1.xRot += swimPitch * 0.2F;
        this.tail2.xRot += swimPitch * 0.25F;
        this.tail3.xRot += swimPitch * 0.1F;

        this.body_rotate1.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail1Y) * 0.35F);
        this.body_rotate2.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail2Y) * 0.35F);
        this.body_rotate3.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail3Y) * 0.4F);
        this.body_rotate4.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail4Y) * 0.4F);
        this.tail_rotate1.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail5Y) * 0.4F);
        this.tail_rotate2.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail6Y) * 0.25F);
        this.tail_rotate3.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail7Y) * 0.1F);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}