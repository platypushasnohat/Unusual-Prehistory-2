package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.AegirocassisModel;
import com.barlinc.unusual_prehistory.entity.Aegirocassis;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class AegirocassisRenderer extends MobRenderer<Aegirocassis, AegirocassisModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/aegirocassis/aegirocassis.png");

    public AegirocassisRenderer(EntityRendererProvider.Context context) {
        super(context, new AegirocassisModel(context.bakeLayer(UP2ModelLayers.AEGIROCASSIS)), 1.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Aegirocassis entity) {
        return TEXTURE;
    }
}
