package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_1;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.KentrosaurusModel;
import com.barlinc.unusual_prehistory.entity.mob.update_1.Kentrosaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class KentrosaurusRenderer extends MobRenderer<Kentrosaurus, KentrosaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/kentrosaurus/kentrosaurus.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/kentrosaurus/kentrosaurus_eepy.png");

    public KentrosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new KentrosaurusModel(context.bakeLayer(UP2ModelLayers.KENTROSAURUS)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Kentrosaurus entity) {
        return entity.isEepy() ? TEXTURE_EEPY : TEXTURE;
    }
}
