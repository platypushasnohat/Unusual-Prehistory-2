package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.base.UP2Model;
import com.barlinc.unusual_prehistory.client.models.entity.therizinosaurus.TherizinosaurusBabyModel;
import com.barlinc.unusual_prehistory.client.models.entity.therizinosaurus.TherizinosaurusModel;
import com.barlinc.unusual_prehistory.client.renderer.layers.TherizinosaurusEyesLayer;
import com.barlinc.unusual_prehistory.entity.Therizinosaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TherizinosaurusRenderer extends MobRenderer<Therizinosaurus, UP2Model<Therizinosaurus>> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/therizinosaurus/therizinosaurus.png");
    private static final ResourceLocation TEXTURE_SHAVED = UnusualPrehistory2.modPrefix("textures/entity/therizinosaurus/therizinosaurus_shaved.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/therizinosaurus/therizinosaurus_eepy.png");
    private static final ResourceLocation TEXTURE_SHAVED_EEPY = UnusualPrehistory2.modPrefix("textures/entity/therizinosaurus/therizinosaurus_shaved_eepy.png");
    private static final ResourceLocation TEXTURE_BABY = UnusualPrehistory2.modPrefix("textures/entity/therizinosaurus/therizinosaurus_baby.png");

    private final TherizinosaurusModel adultModel;
    private final TherizinosaurusBabyModel babyModel;

    public TherizinosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new TherizinosaurusModel(context.bakeLayer(UP2ModelLayers.THERIZINOSAURUS)), 1.3F);
        this.addLayer(new TherizinosaurusEyesLayer(this));
        this.adultModel = new TherizinosaurusModel(context.bakeLayer(UP2ModelLayers.THERIZINOSAURUS));
        this.babyModel = new TherizinosaurusBabyModel(context.bakeLayer(UP2ModelLayers.THERIZINOSAURUS_BABY));
    }

    @Override
    public void render(Therizinosaurus entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        this.model = entity.isBaby() ? babyModel : adultModel;
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Therizinosaurus entity) {
        if (entity.isBaby()) return TEXTURE_BABY;
        else if (entity.isMobEepy()) return entity.isShaved() ? TEXTURE_SHAVED_EEPY : TEXTURE_EEPY;
        return entity.isShaved() ? TEXTURE_SHAVED : TEXTURE;
    }
}