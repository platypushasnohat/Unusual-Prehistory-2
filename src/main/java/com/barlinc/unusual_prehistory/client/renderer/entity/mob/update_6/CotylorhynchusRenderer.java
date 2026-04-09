package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.CotylorhynchusModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Cotylorhynchus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CotylorhynchusRenderer extends MobRenderer<Cotylorhynchus, CotylorhynchusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/cotylorhynchus/cotylorhynchus.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/cotylorhynchus/cotylorhynchus_eepy.png");

    public CotylorhynchusRenderer(EntityRendererProvider.Context context) {
        super(context, new CotylorhynchusModel(context.bakeLayer(UP2ModelLayers.COTYLORHYNCHUS)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Cotylorhynchus entity) {
        return entity.isEepy() ? TEXTURE_EEPY : TEXTURE;
    }
}
