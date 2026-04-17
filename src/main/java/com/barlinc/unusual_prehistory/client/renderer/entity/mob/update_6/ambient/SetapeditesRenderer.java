package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.ambient;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.ambient.SetapeditesModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.ambient.Setapedites;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SetapeditesRenderer extends MobRenderer<Setapedites, SetapeditesModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/ambient/setapedites/setapedites.png");

    public SetapeditesRenderer(EntityRendererProvider.Context context) {
        super(context, new SetapeditesModel(context.bakeLayer(UP2ModelLayers.SETAPEDITES)), 0.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Setapedites entity) {
        return TEXTURE;
    }

    @Override
    protected int getBlockLightLevel(@NotNull Setapedites entity, @NotNull BlockPos pos) {
        return 15;
    }
}
