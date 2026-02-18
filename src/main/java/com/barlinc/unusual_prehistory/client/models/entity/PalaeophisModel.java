package com.barlinc.unusual_prehistory.client.models.entity;

import com.barlinc.unusual_prehistory.client.models.entity.base.UP2Model;
import com.barlinc.unusual_prehistory.entity.Palaeophis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class PalaeophisModel extends UP2Model<Palaeophis> {

    private final ModelPart root;
    private final ModelPart front_body;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw_top;
    private final ModelPart jaw_bottom;
    private final ModelPart tongue;
    private final ModelPart dewlap;
    private final ModelPart back_body;
    private final ModelPart middle_body;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;

	public PalaeophisModel(ModelPart root) {
        super(0.25F, 72);
        this.root = root.getChild("root");
        this.front_body = this.root.getChild("front_body");
        this.body = this.front_body.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.jaw_top = this.head.getChild("jaw_top");
        this.jaw_bottom = this.head.getChild("jaw_bottom");
        this.tongue = this.jaw_bottom.getChild("tongue");
        this.dewlap = this.jaw_bottom.getChild("dewlap");
        this.back_body = this.root.getChild("back_body");
        this.middle_body = this.back_body.getChild("middle_body");
        this.tail1 = this.middle_body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition front_body = root.addOrReplaceChild("front_body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, -9.0F, -35.0F, 19.0F, 18.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = front_body.addOrReplaceChild("body", CubeListBuilder.create().texOffs(104, 53).addBox(-6.5F, -7.0F, -37.0F, 13.0F, 13.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -35.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(160, 154).addBox(-5.5F, -5.0F, -17.0F, 11.0F, 10.0F, 17.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 1.0F, -37.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -17.0F));

        PartDefinition jaw_top = head.addOrReplaceChild("jaw_top", CubeListBuilder.create().texOffs(54, 170).addBox(-7.0F, -2.0F, -5.0F, 14.0F, 2.0F, 7.0F, new CubeDeformation(0.01F))
                .texOffs(56, 181).addBox(-5.0F, -1.0F, -21.0F, 10.0F, 5.0F, 13.0F, new CubeDeformation(0.01F))
                .texOffs(182, 21).addBox(-5.0F, 4.0F, -21.0F, 10.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(167, 21).addBox(-1.5F, 4.0F, -16.0F, 3.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(180, 40).addBox(-7.0F, -2.0F, -8.0F, 14.0F, 6.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -2.0F, -2.0F));

        PartDefinition crest_r1 = jaw_top.addOrReplaceChild("crest_r1", CubeListBuilder.create().texOffs(134, 180).mirror().addBox(0.0F, -4.0F, -4.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.25F, 0.0F, -11.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition crest_r2 = jaw_top.addOrReplaceChild("crest_r2", CubeListBuilder.create().texOffs(134, 180).addBox(0.0F, -4.0F, -4.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.25F, 0.0F, -11.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition jaw_bottom = head.addOrReplaceChild("jaw_bottom", CubeListBuilder.create().texOffs(96, 125).addBox(-5.0F, 4.0F, -21.0F, 10.0F, 3.0F, 13.0F, new CubeDeformation(0.01F))
                .texOffs(115, 47).addBox(-7.0F, 4.0F, -8.0F, 14.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(54, 156).addBox(-7.0F, 0.0F, -5.0F, 14.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(182, 1).addBox(-5.0F, 1.0F, -21.0F, 10.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -2.0F));

        PartDefinition tongue = jaw_bottom.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(101, 180).addBox(-2.0F, 0.0F, -15.0F, 4.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -5.0F));

        PartDefinition dewlap = jaw_bottom.addOrReplaceChild("dewlap", CubeListBuilder.create().texOffs(99, 154).addBox(-3.0F, 0.0F, -11.0F, 6.0F, 5.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 2.0F));

        PartDefinition back_body = root.addOrReplaceChild("back_body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, -9.0F, 0.0F, 19.0F, 18.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition middle_body = back_body.addOrReplaceChild("middle_body", CubeListBuilder.create().texOffs(0, 53).addBox(-7.5F, -11.0F, 0.0F, 15.0F, 16.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 35.0F));

        PartDefinition tail1 = middle_body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 106).addBox(-5.5F, -8.0F, 0.0F, 11.0F, 13.0F, 37.0F, new CubeDeformation(0.0F))
                .texOffs(0, 156).addBox(-0.5F, 5.0F, 11.0F, 1.0F, 7.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 37.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(104, 103).addBox(-2.5F, -4.0F, 0.0F, 5.0F, 8.0F, 43.0F, new CubeDeformation(0.0F))
                .texOffs(108, 0).addBox(-0.5F, 4.0F, 0.0F, 1.0F, 12.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 37.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(157, 103).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 17.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 35.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Palaeophis entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
//        this.root.xRot = headPitch * ((float) Math.PI / 180F);
        float partialTicks = ageInTicks - entity.tickCount;
        float fishPitch = entity.getFishPitch(partialTicks);

        double defaultX = Mth.wrapDegrees(fishPitch);
        double defaultY = Mth.wrapDegrees(entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTicks);
        double tail1X = (entity.getTrailTransformation(10, 0, partialTicks)) - defaultX;
        double tail1Y = (entity.getTrailTransformation(10, 1, partialTicks)) - defaultY;
        double tail2X = (entity.getTrailTransformation(20, 0, partialTicks)) - defaultX - tail1X;
        double tail2Y = (entity.getTrailTransformation(20, 1, partialTicks)) - defaultY - tail1Y;
        double tail3X = (entity.getTrailTransformation(30, 0, partialTicks)) - defaultX - tail2X;
        double tail3Y = (entity.getTrailTransformation(30, 1, partialTicks)) - defaultY - tail2Y;
        double tail4X = (entity.getTrailTransformation(40, 0, partialTicks)) - defaultX - tail3X;
        double tail4Y = (entity.getTrailTransformation(40, 1, partialTicks)) - defaultY - tail3Y;
        double tail5X = (entity.getTrailTransformation(50, 0, partialTicks)) - defaultX - tail4X;
        double tail5Y = (entity.getTrailTransformation(50, 1, partialTicks)) - defaultY - tail4Y;

        this.front_body.xRot += (float) Math.toRadians(fishPitch);
        this.body.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail1Y) * 1);
        this.neck.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail2Y) * 0.35F);
        this.head.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail2Y) * 0.35F);
        this.back_body.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail3Y) * 0.4F);
        this.middle_body.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail4Y) * 0.4F);
        this.tail1.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail5Y) * 0.4F);

        this.body.xRot += (float) Math.toRadians(tail1X * 1);
        this.neck.xRot += (float) Math.toRadians(tail2X * 0.5F);
        this.head.xRot += (float) Math.toRadians(tail2X * 0.5F);
        this.back_body.xRot += (float) Math.toRadians(tail3X * 0.35F);
        this.middle_body.xRot += (float) Math.toRadians(tail4X * 0.25F);
        this.tail1.xRot += (float) Math.toRadians(tail5X * 0.25F);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}