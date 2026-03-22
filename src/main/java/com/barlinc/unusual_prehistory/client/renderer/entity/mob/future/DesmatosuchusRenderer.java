package com.barlinc.unusual_prehistory.client.renderer.entity.mob.future;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.future.DesmatosuchusModel;
import com.barlinc.unusual_prehistory.entity.mob.future.Desmatosuchus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DesmatosuchusRenderer extends MobRenderer<Desmatosuchus, DesmatosuchusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/desmatosuchus/desmatosuchus.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/desmatosuchus/desmatosuchus_eepy.png");
    private static final ResourceLocation TEXTURE_MOSSY = UnusualPrehistory2.modPrefix("textures/entity/desmatosuchus/desmatosuchus_mossy.png");
    private static final ResourceLocation TEXTURE_MOSSY_EEPY = UnusualPrehistory2.modPrefix("textures/entity/desmatosuchus/desmatosuchus_mossy_eepy.png");
    private static final ResourceLocation TEXTURE_MUDDY = UnusualPrehistory2.modPrefix("textures/entity/desmatosuchus/desmatosuchus_muddy.png");
    private static final ResourceLocation TEXTURE_MUDDY_EEPY = UnusualPrehistory2.modPrefix("textures/entity/desmatosuchus/desmatosuchus_muddy_eepy.png");
    private static final ResourceLocation TEXTURE_SNOWY = UnusualPrehistory2.modPrefix("textures/entity/desmatosuchus/desmatosuchus_snowy.png");
    private static final ResourceLocation TEXTURE_SNOWY_EEPY = UnusualPrehistory2.modPrefix("textures/entity/desmatosuchus/desmatosuchus_snowy_eepy.png");

    public DesmatosuchusRenderer(EntityRendererProvider.Context context) {
        super(context, new DesmatosuchusModel(context.bakeLayer(UP2ModelLayers.DESMATOSUCHUS)), 0.8F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Desmatosuchus entity) {
        boolean eepy = entity.isEepy();
        switch (entity.getDirtLevel()) {
            case 1: return eepy ? TEXTURE_MOSSY_EEPY : TEXTURE_MOSSY;
            case 2: return eepy ? TEXTURE_MUDDY_EEPY : TEXTURE_MUDDY;
            case 3: return eepy ? TEXTURE_SNOWY_EEPY : TEXTURE_SNOWY;
            default: return eepy ? TEXTURE_EEPY : TEXTURE;
        }
    }
}
