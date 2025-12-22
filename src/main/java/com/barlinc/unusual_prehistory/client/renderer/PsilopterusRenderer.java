package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.PsilopterusModel;
import com.barlinc.unusual_prehistory.entity.Psilopterus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PsilopterusRenderer extends MobRenderer<Psilopterus, PsilopterusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/psilopterus/psilopterus.png");
    private static final ResourceLocation TEXTURE_LEADER = UnusualPrehistory2.modPrefix("textures/entity/psilopterus/psilopterus_leader.png");

    public PsilopterusRenderer(EntityRendererProvider.Context context) {
        super(context, new PsilopterusModel(context.bakeLayer(UP2ModelLayers.PSILOPTERUS)), 0.4F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Psilopterus entity) {
        return entity.isPackLeader() ? TEXTURE_LEADER : TEXTURE;
    }
}
