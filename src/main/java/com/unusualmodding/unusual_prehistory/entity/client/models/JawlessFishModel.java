 package com.unusualmodding.unusual_prehistory.entity.client.models;

 import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
 import com.unusualmodding.unusual_prehistory.entity.custom.JawlessFishEntity;
 import net.minecraft.resources.ResourceLocation;
 import net.minecraft.util.Mth;
 import software.bernie.geckolib.constant.DataTickets;
 import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
 import software.bernie.geckolib.core.animation.AnimationState;
 import software.bernie.geckolib.model.GeoModel;
 import software.bernie.geckolib.model.data.EntityModelData;

 public class JawlessFishModel extends GeoModel<JawlessFishEntity> {

     public static final ResourceLocation[] MODELS = new ResourceLocation[] {
             new ResourceLocation(UnusualPrehistory2.MOD_ID, "geo/jawless_fish/cephalaspis.geo.json"),
             new ResourceLocation(UnusualPrehistory2.MOD_ID, "geo/jawless_fish/doryaspis.geo.json"),
             new ResourceLocation(UnusualPrehistory2.MOD_ID, "geo/jawless_fish/furcacauda.geo.json"),
             new ResourceLocation(UnusualPrehistory2.MOD_ID, "geo/jawless_fish/sacabambaspis.geo.json")
     };

     public static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
             new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/jawless_fish/cephalaspis.png"),
             new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/jawless_fish/doryaspis.png"),
             new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/jawless_fish/furacacauda.png"),
             new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/jawless_fish/sacabambaspis.png")
     };

     @Override
     public ResourceLocation getModelResource(JawlessFishEntity jawlessFish) {
         return MODELS[jawlessFish.getVariant()];
     }

     @Override
     public ResourceLocation getTextureResource(JawlessFishEntity jawlessFish) {
         return TEXTURES[jawlessFish.getVariant()];
     }

     @Override
     public ResourceLocation getAnimationResource(JawlessFishEntity jawlessFish) {
         return new ResourceLocation(UnusualPrehistory2.MOD_ID, "animations/jawless_fish.animation.json");
     }

     @Override
     public void setCustomAnimations(JawlessFishEntity jawlessFish, long instanceId, AnimationState<JawlessFishEntity> animationState) {
         super.setCustomAnimations(jawlessFish, instanceId, animationState);
         CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");

         EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

         swimControl.setRotX(entityData.headPitch() * (Mth.DEG_TO_RAD));
     }
 }
