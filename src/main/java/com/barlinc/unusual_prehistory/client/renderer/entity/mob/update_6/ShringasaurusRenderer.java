package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.ShringasaurusModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Shringasaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ShringasaurusRenderer extends MobRenderer<Shringasaurus, ShringasaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/shringasaurus.png");

    public ShringasaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new ShringasaurusModel(context.bakeLayer(UP2ModelLayers.SHRINGASAURUS)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Shringasaurus entity) {
        return TEXTURE;
    }
}
