package com.barlinc.unusual_prehistory.client.models.entity.mob.update_6;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.update_6.AntarctopeltaAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Antarctopelta;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class AntarctopeltaModel extends UP2Model<Antarctopelta> {

    private final ModelPart root;
    private final ModelPart Antarcto_Mainbody;
    private final ModelPart Antarcto_Upper_Body;
    private final ModelPart neck;
    private final ModelPart Antarcto_Spikes_Left;
    private final ModelPart Antarcto_Spikes_Right;
    private final ModelPart head;
    private final ModelPart Antarcto_Jaw;
    private final ModelPart tail;
    private final ModelPart Antarcto_Leg_Left;
    private final ModelPart Antarcto_Leg_Right;
    private final ModelPart Antarcto_Arm_Left;
    private final ModelPart Antarcto_Arm_Right;

	public AntarctopeltaModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.Antarcto_Mainbody = this.root.getChild("Antarcto_Mainbody");
        this.Antarcto_Upper_Body = this.Antarcto_Mainbody.getChild("Antarcto_Upper_Body");
        this.neck = this.Antarcto_Upper_Body.getChild("neck");
        this.Antarcto_Spikes_Left = this.neck.getChild("Antarcto_Spikes_Left");
        this.Antarcto_Spikes_Right = this.neck.getChild("Antarcto_Spikes_Right");
        this.head = this.neck.getChild("head");
        this.Antarcto_Jaw = this.head.getChild("Antarcto_Jaw");
        this.tail = this.Antarcto_Upper_Body.getChild("Antarcto_Tail");
        this.Antarcto_Leg_Left = this.Antarcto_Mainbody.getChild("Antarcto_Leg_Left");
        this.Antarcto_Leg_Right = this.Antarcto_Mainbody.getChild("Antarcto_Leg_Right");
        this.Antarcto_Arm_Left = this.Antarcto_Mainbody.getChild("Antarcto_Arm_Left");
        this.Antarcto_Arm_Right = this.Antarcto_Mainbody.getChild("Antarcto_Arm_Right");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.5F, 16.0F, 0.0F));

        PartDefinition Antarcto_Mainbody = root.addOrReplaceChild("Antarcto_Mainbody", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition Antarcto_Upper_Body = Antarcto_Mainbody.addOrReplaceChild("Antarcto_Upper_Body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -8.0F, -12.0F, 17.0F, 14.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(79, 73).addBox(-11.0F, 4.0F, -11.0F, 21.0F, 0.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck = Antarcto_Upper_Body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(66, 56).addBox(-4.5F, -2.5F, -10.0F, 9.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(50, 73).addBox(-3.5F, 2.5F, -8.0F, 7.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -2.5F, -12.0F));

        PartDefinition Antarcto_Spikes_Left = neck.addOrReplaceChild("Antarcto_Spikes_Left", CubeListBuilder.create().texOffs(42, 79).addBox(0.0F, -7.0F, 1.0F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0025F))
                .texOffs(48, 87).addBox(0.0F, -5.0F, -2.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0025F))
                .texOffs(52, 87).addBox(0.0F, -3.0F, -5.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.5F, -1.5F, -5.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition Antarcto_Spikes_Right = neck.addOrReplaceChild("Antarcto_Spikes_Right", CubeListBuilder.create().texOffs(42, 79).mirror().addBox(0.0F, -7.0F, 1.0F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(48, 87).mirror().addBox(0.0F, -5.0F, -2.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(52, 87).mirror().addBox(0.0F, -3.0F, -5.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.5F, -1.5F, -5.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 85).addBox(-2.5F, -2.5F, -5.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(86, 73).addBox(-2.5F, -2.5F, -7.0F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -10.0F));

        PartDefinition Antarcto_Jaw = head.addOrReplaceChild("Antarcto_Jaw", CubeListBuilder.create().texOffs(20, 85).addBox(-2.5F, 0.0F, -5.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition Antarcto_Tail = Antarcto_Upper_Body.addOrReplaceChild("Antarcto_Tail", CubeListBuilder.create().texOffs(80, 0).addBox(-7.5F, -1.0F, 6.0F, 15.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(-3.5F, -3.0F, 0.0F, 7.0F, 6.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(66, 39).addBox(-10.5F, -1.0F, 22.0F, 21.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 71).addBox(-9.5F, -1.0F, 15.0F, 19.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 79).addBox(-8.5F, -1.0F, 10.0F, 17.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 0.0F, 11.0F));

        PartDefinition Antarcto_Leg_Left = Antarcto_Mainbody.addOrReplaceChild("Antarcto_Leg_Left", CubeListBuilder.create().texOffs(80, 21).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(86, 84).addBox(-2.5F, 6.0F, -4.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 6.0F, 7.0F));

        PartDefinition Antarcto_Leg_Right = Antarcto_Mainbody.addOrReplaceChild("Antarcto_Leg_Right", CubeListBuilder.create().texOffs(80, 21).mirror().addBox(-2.5F, -2.0F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(86, 84).mirror().addBox(-2.5F, 6.0F, -4.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, 6.0F, 7.0F));

        PartDefinition Antarcto_Arm_Left = Antarcto_Mainbody.addOrReplaceChild("Antarcto_Arm_Left", CubeListBuilder.create().texOffs(80, 5).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(86, 81).addBox(-2.5F, 6.0F, -4.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 6.0F, -8.0F));

        PartDefinition Antarcto_Arm_Right = Antarcto_Mainbody.addOrReplaceChild("Antarcto_Arm_Right", CubeListBuilder.create().texOffs(80, 5).mirror().addBox(-2.5F, -2.0F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(86, 81).mirror().addBox(-2.5F, 6.0F, -4.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, 6.0F, -8.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(@NotNull Antarctopelta entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(AntarctopeltaAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
		this.animateIdleSmooth(entity.idleAnimationState, AntarctopeltaAnimations.IDLE, ageInTicks, limbSwingAmount);
        if (this.young) this.applyStatic(AntarctopeltaAnimations.BABY_TRANSFORM);
        this.faceTarget(netHeadYaw, headPitch, 2, neck, head);
        float partialTicks = ageInTicks - entity.tickCount;
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail.yRot = Mth.lerp(0.2F, this.tail.yRot, tailYaw * 0.25F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}