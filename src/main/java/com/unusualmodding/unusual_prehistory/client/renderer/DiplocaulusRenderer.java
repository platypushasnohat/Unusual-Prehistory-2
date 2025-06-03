package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.base.UP2Model;
import com.unusualmodding.unusual_prehistory.client.models.entity.diplocaulus.*;
import com.unusualmodding.unusual_prehistory.entity.Diplocaulus;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class DiplocaulusRenderer extends MobRenderer<Diplocaulus, UP2Model<Diplocaulus>> {

    private final DiplocaulusBrevirostrisModel<Diplocaulus> brevirostrisModel;
    private final DiplocaulusMagnicornisModel<Diplocaulus> magnicornisModel;
    private final DiplocaulusRecurvatisModel<Diplocaulus> recurvatisModel;
    private final DiplocaulusSalamandroidesModel<Diplocaulus> salamandroidesModel;

    public DiplocaulusRenderer(EntityRendererProvider.Context context) {
        super(context, new DiplocaulusBrevirostrisModel<>(context.bakeLayer(UP2EntityModelLayers.DIPLOCAULUS_BREVIROSTRIS_LAYER)), 0.5F);
        this.brevirostrisModel = new DiplocaulusBrevirostrisModel<>(context.bakeLayer(UP2EntityModelLayers.DIPLOCAULUS_BREVIROSTRIS_LAYER));
        this.magnicornisModel = new DiplocaulusMagnicornisModel<>(context.bakeLayer(UP2EntityModelLayers.DIPLOCAULUS_MAGNICORNIS_LAYER));
        this.recurvatisModel = new DiplocaulusRecurvatisModel<>(context.bakeLayer(UP2EntityModelLayers.DIPLOCAULUS_RECURVATIS_LAYER));
        this.salamandroidesModel = new DiplocaulusSalamandroidesModel<>(context.bakeLayer(UP2EntityModelLayers.DIPLOCAULUS_SALAMANDROIDES_LAYER));
    }

    @Override
    public void render(Diplocaulus entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        switch (entity.getVariant()){
            case 1:
                this.model = magnicornisModel;
                break;
            case 2:
                this.model = recurvatisModel;
                break;
            case 3:
                this.model = salamandroidesModel;
                break;
            default:
                this.model = brevirostrisModel;
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected void scale(Diplocaulus entity, PoseStack matrices, float amount) {
        if (entity.isBaby()) matrices.scale(0.6F, 0.6F, 0.6F);
        else super.scale(entity, matrices, amount);
    }

    @Override
    public ResourceLocation getTextureLocation(Diplocaulus entity) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/diplocaulus/diplocaulus_" + entity.getVariantName() + ".png");
    }

    @Override
    protected @Nullable RenderType getRenderType(Diplocaulus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/diplocaulus/diplocaulus_" + entity.getVariantName() + ".png"));
    }
}
