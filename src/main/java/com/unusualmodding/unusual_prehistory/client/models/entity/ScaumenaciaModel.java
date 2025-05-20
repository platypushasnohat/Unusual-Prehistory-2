package com.unusualmodding.unusual_prehistory.client.models.entity;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.ScaumenaciaEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ScaumenaciaModel extends GeoModel<ScaumenaciaEntity> {

    public static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/scaumenacia/scaumenacia.png"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/scaumenacia/scaumenacia_buddah.png")
    };

    @Override
    public ResourceLocation getModelResource(ScaumenaciaEntity scaumenacia) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "geo/scaumenacia.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ScaumenaciaEntity scaumenacia) {
        return TEXTURES[scaumenacia.getVariant()];
    }

    @Override
    public ResourceLocation getAnimationResource(ScaumenaciaEntity scaumenacia) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "animations/scaumenacia.animation.json");
    }

    @Override
    public void setCustomAnimations(ScaumenaciaEntity scaumenacia, long instanceId, AnimationState<ScaumenaciaEntity> animationState) {
        super.setCustomAnimations(scaumenacia, instanceId, animationState);

        CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        swimControl.setRotX(entityData.headPitch() * (Mth.DEG_TO_RAD));
    }
}

