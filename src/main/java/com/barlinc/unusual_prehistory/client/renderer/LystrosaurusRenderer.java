package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.LystrosaurusModel;
import com.barlinc.unusual_prehistory.entity.Lystrosaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LystrosaurusRenderer extends MobRenderer<Lystrosaurus, LystrosaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/lystrosaurus.png");

    public LystrosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new LystrosaurusModel(context.bakeLayer(UP2ModelLayers.LYSTROSAURUS)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Lystrosaurus entity) {
        return TEXTURE;
    }
}
