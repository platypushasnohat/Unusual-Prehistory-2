package com.unusualmodding.unusual_prehistory.client.renderer;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.DromaeosaurusModel;
import com.unusualmodding.unusual_prehistory.entity.Dromaeosaurus;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class DromaeosaurusRenderer extends MobRenderer<Dromaeosaurus, DromaeosaurusModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/dromaeosaurus/dromaeosaurus.png");
    private static final ResourceLocation TEXTURE_SLEEPING = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/dromaeosaurus/dromaeosaurus_eepy.png");

    public DromaeosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new DromaeosaurusModel(context.bakeLayer(UP2EntityModelLayers.DROMAEOSAURUS)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Dromaeosaurus entity) {
        return entity.isDromaeosaurusVisuallyLayingDown() ? TEXTURE_SLEEPING : TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Dromaeosaurus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}