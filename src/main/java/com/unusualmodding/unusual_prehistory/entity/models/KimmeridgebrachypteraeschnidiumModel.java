package com.unusualmodding.unusual_prehistory.entity.models;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.custom.KimmeridgebrachypteraeschnidiumEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KimmeridgebrachypteraeschnidiumModel extends GeoModel<KimmeridgebrachypteraeschnidiumEntity> {

    @Override
    public ResourceLocation getModelResource(KimmeridgebrachypteraeschnidiumEntity kimmer) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "geo/kimmeridgebrachypteraeschnidium.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KimmeridgebrachypteraeschnidiumEntity kimmer) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/kimmeridgebrachypteraeschnidium/base_" + kimmer.getBaseColor() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(KimmeridgebrachypteraeschnidiumEntity kimmer) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "animations/kimmeridgebrachypteraeschnidium.animation.json");
    }
}
