package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_1;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.TelecrexModel;
import com.barlinc.unusual_prehistory.entity.mob.update_1.Telecrex;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TelecrexRenderer extends MobRenderer<Telecrex, TelecrexModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/telecrex.png");

    public TelecrexRenderer(EntityRendererProvider.Context context) {
        super(context, new TelecrexModel(context.bakeLayer(UP2ModelLayers.TELECREX)), 0.4F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Telecrex entity) {
        return TEXTURE;
    }
}
