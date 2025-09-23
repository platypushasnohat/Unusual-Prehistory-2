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

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class DiplocaulusRenderer extends MobRenderer<Diplocaulus, UP2Model<Diplocaulus>> {

    private final DiplocaulusBrevirostrisModel brevirostrisModel;
    private final DiplocaulusMagnicornisModel magnicornisModel;
    private final DiplocaulusRecurvatisModel recurvatisModel;
    private final DiplocaulusSalamandroidesModel salamandroidesModel;

    public DiplocaulusRenderer(EntityRendererProvider.Context context) {
        super(context, new DiplocaulusBrevirostrisModel(context.bakeLayer(UP2EntityModelLayers.DIPLOCAULUS_BREVIROSTRIS)), 0.5F);
        this.brevirostrisModel = new DiplocaulusBrevirostrisModel(context.bakeLayer(UP2EntityModelLayers.DIPLOCAULUS_BREVIROSTRIS));
        this.magnicornisModel = new DiplocaulusMagnicornisModel(context.bakeLayer(UP2EntityModelLayers.DIPLOCAULUS_MAGNICORNIS));
        this.recurvatisModel = new DiplocaulusRecurvatisModel(context.bakeLayer(UP2EntityModelLayers.DIPLOCAULUS_RECURVATIS));
        this.salamandroidesModel = new DiplocaulusSalamandroidesModel(context.bakeLayer(UP2EntityModelLayers.DIPLOCAULUS_SALAMANDROIDES));
    }

    @Override
    public void render(Diplocaulus entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        switch (Diplocaulus.DiplocaulusVariant.byId(entity.getVariant()).getId()) {
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
    public ResourceLocation getTextureLocation(Diplocaulus entity) {
        Diplocaulus.DiplocaulusVariant variant = Diplocaulus.DiplocaulusVariant.byId(entity.getVariant());
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/diplocaulus/diplocaulus_" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }

    @Override
    @Nullable
    protected RenderType getRenderType(Diplocaulus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        Diplocaulus.DiplocaulusVariant variant = Diplocaulus.DiplocaulusVariant.byId(entity.getVariant());
        return RenderType.entityCutoutNoCull(new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/diplocaulus/diplocaulus_" + variant.name().toLowerCase(Locale.ROOT) + ".png"));
    }
}
