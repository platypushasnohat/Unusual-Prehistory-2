package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.HynerpetonModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Hynerpeton;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class HynerpetonRenderer extends MobRenderer<Hynerpeton, HynerpetonModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/hynerpeton.png");

    public HynerpetonRenderer(EntityRendererProvider.Context context) {
        super(context, new HynerpetonModel(context.bakeLayer(UP2ModelLayers.HYNERPETON)), 0.3F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Hynerpeton entity) {
        return TEXTURE;
    }
}
