package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.MegalaniaModel;
import com.unusualmodding.unusual_prehistory.entity.Megalania;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class MegalaniaRenderer extends MobRenderer<Megalania, MegalaniaModel<Megalania>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/megalania/megalania_temperate.png");

    public MegalaniaRenderer(EntityRendererProvider.Context context) {
        super(context, new MegalaniaModel<>(context.bakeLayer(UP2EntityModelLayers.MEGALANIA)), 0.9F);
    }

    @Override
    protected void scale(Megalania entity, PoseStack matrices, float amount) {
        if (entity.isBaby()) matrices.scale(0.6F, 0.6F, 0.6F);
        else super.scale(entity, matrices, amount);
    }

    @Override
    public ResourceLocation getTextureLocation(Megalania entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(Megalania entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}