package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_1;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.DromaeosaurusModel;
import com.barlinc.unusual_prehistory.entity.mob.update_1.Dromaeosaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DromaeosaurusRenderer extends MobRenderer<Dromaeosaurus, DromaeosaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/dromaeosaurus/yellow.png");

    public DromaeosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new DromaeosaurusModel(context.bakeLayer(UP2ModelLayers.DROMAEOSAURUS)), 0.4F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Dromaeosaurus entity) {
        return TEXTURE;
    }
}