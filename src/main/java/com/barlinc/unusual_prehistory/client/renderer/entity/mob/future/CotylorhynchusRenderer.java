package com.barlinc.unusual_prehistory.client.renderer.entity.mob.future;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.future.CotylorhynchusModel;
import com.barlinc.unusual_prehistory.entity.mob.future.Cotylorhynchus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CotylorhynchusRenderer extends MobRenderer<Cotylorhynchus, CotylorhynchusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/cotylorhynchus.png");

    public CotylorhynchusRenderer(EntityRendererProvider.Context context) {
        super(context, new CotylorhynchusModel(context.bakeLayer(UP2ModelLayers.COTYLORHYNCHUS)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Cotylorhynchus entity) {
        return TEXTURE;
    }
}
