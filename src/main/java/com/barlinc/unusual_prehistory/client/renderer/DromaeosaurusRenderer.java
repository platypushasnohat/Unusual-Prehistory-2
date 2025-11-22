package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.DromaeosaurusModel;
import com.barlinc.unusual_prehistory.entity.Dromaeosaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DromaeosaurusRenderer extends MobRenderer<Dromaeosaurus, DromaeosaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/dromaeosaurus/dromaeosaurus.png");
    private static final ResourceLocation TEXTURE_SLEEPING = UnusualPrehistory2.modPrefix("textures/entity/dromaeosaurus/dromaeosaurus_eepy.png");

    public DromaeosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new DromaeosaurusModel(context.bakeLayer(UP2ModelLayers.DROMAEOSAURUS)), 0.6F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Dromaeosaurus entity) {
        return entity.isDromaeosaurusVisuallyEeping() ? TEXTURE_SLEEPING : TEXTURE;
    }
}