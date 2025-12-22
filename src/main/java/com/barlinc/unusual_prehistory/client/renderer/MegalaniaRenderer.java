package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.MegalaniaModel;
import com.barlinc.unusual_prehistory.client.renderer.layers.MegalaniaTemperatureLayer;
import com.barlinc.unusual_prehistory.entity.Megalania;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class MegalaniaRenderer extends MobRenderer<Megalania, MegalaniaModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/megalania/megalania_temperate.png");
    private static final ResourceLocation TEXTURE_COLD = UnusualPrehistory2.modPrefix("textures/entity/megalania/megalania_cold.png");
    private static final ResourceLocation TEXTURE_WARM = UnusualPrehistory2.modPrefix("textures/entity/megalania/megalania_warm.png");
    private static final ResourceLocation TEXTURE_NETHER = UnusualPrehistory2.modPrefix("textures/entity/megalania/megalania_nether.png");

    public MegalaniaRenderer(EntityRendererProvider.Context context) {
        super(context, new MegalaniaModel(context.bakeLayer(UP2ModelLayers.MEGALANIA)), 0.9F);
        this.addLayer(new MegalaniaTemperatureLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Megalania entity) {
        return switch (entity.getTemperatureState()) {
            case COLD -> TEXTURE_COLD;
            case WARM -> TEXTURE_WARM;
            case NETHER -> TEXTURE_NETHER;
            default -> TEXTURE;
        };
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Megalania entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}