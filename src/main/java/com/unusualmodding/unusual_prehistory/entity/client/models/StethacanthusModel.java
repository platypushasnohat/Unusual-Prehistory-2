package com.unusualmodding.unusual_prehistory.entity.client.models;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.custom.StethacanthusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class StethacanthusModel extends GeoModel<StethacanthusEntity> {

    @Override
    public ResourceLocation getModelResource(StethacanthusEntity object) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "geo/stethacanthus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StethacanthusEntity object) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/stethacanthus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StethacanthusEntity object) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "animations/stethacanthus.animation.json");
    }

    @Override
    public void setCustomAnimations(StethacanthusEntity animatable, long instanceId, AnimationState<StethacanthusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");

        if (animatable.isBaby()) {
            head.setScaleX(1.3F);
            head.setScaleY(1.3F);
            head.setScaleZ(1.3F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }

        swimControl.setRotX(entityData.headPitch() * (Mth.DEG_TO_RAD));
    }
}

