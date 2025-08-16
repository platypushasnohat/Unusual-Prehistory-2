package com.unusualmodding.unusual_prehistory.client.renderer;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.TelecrexModel;
import com.unusualmodding.unusual_prehistory.entity.Telecrex;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class TelecrexRenderer extends MobRenderer<Telecrex, TelecrexModel<Telecrex>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/telecrex.png");

    public TelecrexRenderer(EntityRendererProvider.Context context) {
        super(context, new TelecrexModel<>(context.bakeLayer(UP2EntityModelLayers.TELECREX)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(Telecrex entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(Telecrex entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}
