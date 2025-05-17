package com.unusualmodding.unusual_prehistory.entity.models;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.custom.DunkleosteusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DunkleosteusModel extends GeoModel<DunkleosteusEntity> {

    public static final ResourceLocation[] MODELS = new ResourceLocation[] {
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "geo/dunkleosteus/dunkleosteus_raveri.geo.json"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "geo/dunkleosteus/dunkleosteus_marsaisi.geo.json"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "geo/dunkleosteus/dunkleosteus_terrelli.geo.json")
    };

    public static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/dunkleosteus/dunkleosteus_raveri.png"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/dunkleosteus/dunkleosteus_marsaisi.png"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/dunkleosteus/dunkleosteus_terrelli.png")
    };

    public static final ResourceLocation[] ANIMATIONS = new ResourceLocation[] {
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "animations/dunkleosteus/dunkleosteus_raveri.animation.json"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "animations/dunkleosteus/dunkleosteus_marsaisi.animation.json"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "animations/dunkleosteus/dunkleosteus_terrelli.animation.json")
    };

    @Override
    public ResourceLocation getModelResource(DunkleosteusEntity dunkleosteus) {
        return MODELS[dunkleosteus.getDunkSize()];
    }

    @Override
    public ResourceLocation getTextureResource(DunkleosteusEntity dunkleosteus) {
        return TEXTURES[dunkleosteus.getDunkSize()];
    }

    @Override
    public ResourceLocation getAnimationResource(DunkleosteusEntity dunkleosteus) {
        return ANIMATIONS[dunkleosteus.getDunkSize()];
    }

    @Override
    public void setCustomAnimations(DunkleosteusEntity dunkleosteus, long instanceId, AnimationState<DunkleosteusEntity> animationState) {
        super.setCustomAnimations(dunkleosteus, instanceId, animationState);
        if (animationState == null) return;

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");

        swimControl.setRotX(entityData.headPitch() * (Mth.DEG_TO_RAD));
    }
}

