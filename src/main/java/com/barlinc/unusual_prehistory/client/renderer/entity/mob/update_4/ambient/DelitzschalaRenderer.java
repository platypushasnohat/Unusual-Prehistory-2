package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_4.ambient;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_4.ambient.DelitzschalaModel;
import com.barlinc.unusual_prehistory.entity.mob.update_4.ambient.Delitzschala;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DelitzschalaRenderer extends MobRenderer<Delitzschala, DelitzschalaModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/ambient/delitzschala.png");

    public DelitzschalaRenderer(EntityRendererProvider.Context context) {
        super(context, new DelitzschalaModel(context.bakeLayer(UP2ModelLayers.DELITZSCHALA)), 0.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Delitzschala entity) {
        return TEXTURE;
    }
}
