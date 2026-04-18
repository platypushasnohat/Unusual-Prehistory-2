package com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.ambient;

import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_6.ambient.Ampyx;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class AmpyxModel extends UP2Model<Ampyx> {

    private final ModelPart root;

	public AmpyxModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 8).addBox(-2.5F, -0.6667F, -3.9167F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(-0.5F, -0.6667F, 1.0833F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.5F, 0.3333F, -3.9167F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(18, 6).addBox(-0.5F, -0.6667F, -11.9167F, 1.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(-4, 14).addBox(3.5F, 0.3333F, -1.9167F, 1.0F, 0.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(-4, 14).addBox(-4.5F, 0.3333F, -1.9167F, 1.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.6667F, 2.1667F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Ampyx entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        if (entity.isInWaterOrBubble() && !entity.crawling) {
            this.root.xRot = headPitch * ((float) Math.PI / 180F);
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}