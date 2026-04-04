package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_5.MosasaurusModel;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5.layers.MosasaurusHeldMobLayer;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Mosasaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class MosasaurusRenderer extends MobRenderer<Mosasaurus, MosasaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/mosasaurus.png");

    public MosasaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new MosasaurusModel(context.bakeLayer(UP2ModelLayers.MOSASAURUS)), 1.0F);
        this.addLayer(new MosasaurusHeldMobLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Mosasaurus entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Mosasaurus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}
