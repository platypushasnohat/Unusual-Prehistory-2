package com.barlinc.unusual_prehistory.client.models.entity.mob.update_6;

import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Wonambi;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class WonambiModel extends UP2Model<Wonambi> {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tongue;
    private final ModelPart neck;
    private final ModelPart neck2;
    private final ModelPart neck3;
    private final ModelPart body1;
    private final ModelPart body2;
    private final ModelPart body3;
    private final ModelPart body4;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;

	public WonambiModel(ModelPart root) {
        super(0.25F, 72);
        this.root = root.getChild("root");
        this.head = this.root.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tongue = this.jaw.getChild("tongue");
        this.neck = this.head.getChild("neck");
        this.neck2 = this.neck.getChild("neck2");
        this.neck3 = this.neck2.getChild("neck3");
        this.body1 = this.neck3.getChild("body1");
        this.body2 = this.body1.getChild("body2");
        this.body3 = this.body2.getChild("body3");
        this.body4 = this.body3.getChild("body4");
        this.tail1 = this.body4.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
        this.tail4 = this.tail3.getChild("tail4");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 41.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 29).addBox(-3.0F, -2.0F, -6.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(32, 8).addBox(-2.0F, -2.0F, -10.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(14, 39).addBox(2.0F, -3.0F, -6.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 39).mirror().addBox(-3.0F, -3.0F, -6.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(40, 37).addBox(-1.0F, -2.0F, -12.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -44.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(24, 37).addBox(-2.0F, 0.0F, -10.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-3.0F, 0.0F, -6.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(1.5F, -1.0F, -9.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 39).mirror().addBox(-1.5F, -1.0F, -9.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tongue = jaw.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(32, 0).addBox(-1.5F, 0.0F, -8.0F, 3.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -2.0F));

        PartDefinition neck = head.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(28, 43).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition neck2 = neck.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(28, 16).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 8.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 8.0F));

        PartDefinition neck3 = neck2.addOrReplaceChild("neck3", CubeListBuilder.create().texOffs(28, 16).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));

        PartDefinition body1 = neck3.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 8.0F));

        PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition body3 = body2.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition body4 = body3.addOrReplaceChild("body4", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail1 = body4.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(28, 16).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 10.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(28, 16).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 8.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 8.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(28, 16).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));

        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 8.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Wonambi entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float deg = ((float) Math.PI / 180F);
        float partialTicks = ageInTicks - entity.tickCount;
        double defaultY = Mth.wrapDegrees(entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTicks);
        double tail1Y = (entity.getTrailTransformation(10, partialTicks)) - defaultY;
        double tail2Y = (entity.getTrailTransformation(19, partialTicks)) - defaultY - tail1Y;
        double tail3Y = (entity.getTrailTransformation(28, partialTicks)) - defaultY - tail2Y;
        double tail4Y = (entity.getTrailTransformation(36, partialTicks)) - defaultY - tail3Y;
        double tail5Y = (entity.getTrailTransformation(43, partialTicks)) - defaultY - tail4Y;
        double tail6Y = (entity.getTrailTransformation(49, partialTicks)) - defaultY - tail5Y;
        double tail7Y = (entity.getTrailTransformation(54, partialTicks)) - defaultY - tail6Y;
        double tail8Y = (entity.getTrailTransformation(58, partialTicks)) - defaultY - tail7Y;
        double tail9Y = (entity.getTrailTransformation(61, partialTicks)) - defaultY - tail8Y;
        double tail10Y = (entity.getTrailTransformation(63, partialTicks)) - defaultY - tail9Y;

        this.neck.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail1Y) * 0.5F);
        this.neck2.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail2Y) * 0.5F);
        this.neck3.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail3Y) * 0.5F);
        this.body1.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail4Y) * 0.5F);
        this.body2.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail5Y) * 0.4F);
        this.body3.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail6Y) * 0.35F);
        this.body4.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail7Y) * 0.4F);
        this.tail1.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail8Y) * 0.45F);
        this.tail2.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail9Y) * 0.35F);
        this.tail3.yRot += (float) Math.toRadians(Mth.wrapDegrees(tail10Y) * 0.3F);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}