package com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.HesperornisModel;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Hesperornis;
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
public class HesperornisRenderer extends MobRenderer<Hesperornis, HesperornisModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/hesperornis/orange.png");

    public HesperornisRenderer(EntityRendererProvider.Context context) {
        super(context, new HesperornisModel(context.bakeLayer(UP2ModelLayers.HESPERORNIS)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Hesperornis entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Hesperornis entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}
