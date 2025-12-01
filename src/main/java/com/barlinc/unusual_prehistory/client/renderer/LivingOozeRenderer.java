package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.LivingOozeModel;
import com.barlinc.unusual_prehistory.client.renderer.layers.LivingOozeContainedItemLayer;
import com.barlinc.unusual_prehistory.client.renderer.layers.LivingOozeOuterLayer;
import com.barlinc.unusual_prehistory.entity.LivingOoze;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class LivingOozeRenderer extends MobRenderer<LivingOoze, LivingOozeModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/living_ooze/normal.png");

    public LivingOozeRenderer(EntityRendererProvider.Context context) {
        super(context, new LivingOozeModel(context.bakeLayer(UP2ModelLayers.LIVING_OOZE)), 0.5F);
        this.addLayer(new LivingOozeContainedItemLayer(this));
        this.addLayer(new LivingOozeOuterLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LivingOoze entity) {
        return TEXTURE;
    }

    @Nullable
    @Override
    protected RenderType getRenderType(@NotNull LivingOoze entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        ResourceLocation resourcelocation = this.getTextureLocation(entity);
        if (translucent) {
            return RenderType.itemEntityTranslucentCull(resourcelocation);
        } else if (bodyVisible) {
            return RenderType.entityTranslucent(resourcelocation);
        } else {
            return glowing ? RenderType.outline(resourcelocation) : null;
        }
    }
}