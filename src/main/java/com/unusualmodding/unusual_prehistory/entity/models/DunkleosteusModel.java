package com.unusualmodding.unusual_prehistory.entity.models;

import com.unusualmodding.unusual_prehistory.entity.custom.DunkleosteusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static com.unusualmodding.unusual_prehistory.UnusualPrehistory2.modPrefix;

public class DunkleosteusModel extends GeoModel<DunkleosteusEntity> {

    private static final ResourceLocation TEXTURE_TERRELLI = modPrefix("textures/entity/dunkleosteus/dunkleosteus_terrelli.png");
    private static final ResourceLocation TEXTURE_MARSAISI = modPrefix("textures/entity/dunkleosteus/dunkleosteus_marsaisi.png");
    private static final ResourceLocation TEXTURE_RAVERI = modPrefix("textures/entity/dunkleosteus/dunkleosteus_raveri.png");

    private static final ResourceLocation MODEL_TERRELLI = modPrefix("geo/dunkleosteus/dunkleosteus_terrelli.geo.json");
    private static final ResourceLocation MODEL_MARSAISI = modPrefix("geo/dunkleosteus/dunkleosteus_marsaisi.geo.json");
    private static final ResourceLocation MODEL_RAVERI = modPrefix("geo/dunkleosteus/dunkleosteus_raveri.geo.json");

    private static final ResourceLocation ANIMATION_TERRELLI = modPrefix("animations/dunkleosteus/dunkleosteus_terrelli.animation.json");
    private static final ResourceLocation ANIMATION_MARSAISI = modPrefix("animations/dunkleosteus/dunkleosteus_marsaisi.animation.json");
    private static final ResourceLocation ANIMATION_RAVERI = modPrefix("animations/dunkleosteus/dunkleosteus_raveri.animation.json");

    @Override
    public ResourceLocation getModelResource(DunkleosteusEntity object) {
        return switch (object.getDunkSize()) {
            case 1 -> MODEL_MARSAISI;
            case 2 -> MODEL_TERRELLI;
            default -> MODEL_RAVERI;
        };
    }

    @Override
    public ResourceLocation getTextureResource(DunkleosteusEntity object) {
        return switch (object.getDunkSize()) {
            case 1 -> TEXTURE_MARSAISI;
            case 2 -> TEXTURE_TERRELLI;
            default -> TEXTURE_RAVERI;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(DunkleosteusEntity object) {
        return switch (object.getDunkSize()) {
            case 1 -> ANIMATION_MARSAISI;
            case 2 -> ANIMATION_TERRELLI;
            default -> ANIMATION_RAVERI;
        };
    }

    @Override
    public void setCustomAnimations(DunkleosteusEntity entity, long instanceId, AnimationState<DunkleosteusEntity> animationState) {
        super.setCustomAnimations(entity, instanceId, animationState);
        if (animationState == null) return;

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");

        swimControl.setRotX(entityData.headPitch() * (Mth.DEG_TO_RAD));
    }
}

