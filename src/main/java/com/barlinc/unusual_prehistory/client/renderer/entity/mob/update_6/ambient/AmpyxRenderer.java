package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.ambient;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.ambient.AmpyxModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.ambient.Ampyx;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class AmpyxRenderer extends MobRenderer<Ampyx, AmpyxModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/ambient/ampyx.png");

    public AmpyxRenderer(EntityRendererProvider.Context context) {
        super(context, new AmpyxModel(context.bakeLayer(UP2ModelLayers.AMPYX)), 0.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Ampyx entity) {
        return TEXTURE;
    }

    @Override
    protected int getBlockLightLevel(@NotNull Ampyx entity, @NotNull BlockPos pos) {
        return 15;
    }
}
