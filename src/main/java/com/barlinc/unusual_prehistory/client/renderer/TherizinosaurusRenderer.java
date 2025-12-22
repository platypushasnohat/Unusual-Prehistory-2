package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.TherizinosaurusModel;
import com.barlinc.unusual_prehistory.client.renderer.layers.TherizinosaurusEyesLayer;
import com.barlinc.unusual_prehistory.entity.Therizinosaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TherizinosaurusRenderer extends MobRenderer<Therizinosaurus, TherizinosaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/therizinosaurus/therizinosaurus.png");
    private static final ResourceLocation TEXTURE_SHAVED = UnusualPrehistory2.modPrefix("textures/entity/therizinosaurus/therizinosaurus_shaved.png");

    public TherizinosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new TherizinosaurusModel(context.bakeLayer(UP2ModelLayers.THERIZINOSAURUS)), 1.3F);
        this.addLayer(new TherizinosaurusEyesLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Therizinosaurus entity) {
        return entity.isShaved() || entity.isBaby() ? TEXTURE_SHAVED : TEXTURE;
    }
}