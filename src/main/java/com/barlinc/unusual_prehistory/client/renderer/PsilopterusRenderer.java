package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.PsilopterusModel;
import com.barlinc.unusual_prehistory.entity.Psilopterus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PsilopterusRenderer extends MobRenderer<Psilopterus, PsilopterusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/psilopterus/psilopterus.png");
    private static final ResourceLocation TEXTURE_LEADER = UnusualPrehistory2.modPrefix("textures/entity/psilopterus/psilopterus_leader.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/psilopterus/psilopterus_eepy.png");
    private static final ResourceLocation TEXTURE_LEADER_EEPY = UnusualPrehistory2.modPrefix("textures/entity/psilopterus/psilopterus_leader_eepy.png");

    public PsilopterusRenderer(EntityRendererProvider.Context context) {
        super(context, new PsilopterusModel(context.bakeLayer(UP2ModelLayers.PSILOPTERUS)), 0.4F);
    }

    @Override
    protected void scale(Psilopterus entity, @NotNull PoseStack poseStack, float partialTicks) {
        if (entity.isPackLeader()) {
            poseStack.scale(1.1F, 1.1F, 1.1F);
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Psilopterus entity) {
        if (entity.isPackLeader()) return entity.isMobEepy() ? TEXTURE_LEADER_EEPY : TEXTURE_LEADER;
        return entity.isMobEepy() ? TEXTURE_EEPY : TEXTURE;
    }
}
