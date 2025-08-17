package com.unusualmodding.unusual_prehistory.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.blocks.blockentity.UP2LecternBlockEntity;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UP2LecternRenderer implements BlockEntityRenderer<UP2LecternBlockEntity> {

    private final BookModel bookModel;
    public static final ResourceLocation PALEOPEDIA = new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/lectern_paleopedia.png");

    public UP2LecternRenderer(BlockEntityRendererProvider.Context context) {
        this.bookModel = new BookModel(context.bakeLayer(ModelLayers.BOOK));
    }

    public void render(UP2LecternBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int i, int j) {
        BlockState blockstate = blockEntity.getBlockState();
        if (blockstate.getValue(LecternBlock.HAS_BOOK)) {
            poseStack.pushPose();
            poseStack.translate(0.5D, 1.0625D, 0.5D);
            float f = blockstate.getValue(LecternBlock.FACING).getClockWise().toYRot();
            poseStack.mulPose(Axis.YP.rotationDegrees(-f));
            poseStack.mulPose(Axis.ZP.rotationDegrees(67.5F));
            poseStack.translate(0.0D, -0.125D, 0.0D);
            this.bookModel.setupAnim(0.0F, 0.1F, 0.9F, 1.2F);
            VertexConsumer pages = bufferSource.getBuffer(RenderType.entityCutoutNoCull(PALEOPEDIA));
            this.bookModel.render(poseStack, pages, i, j, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
    }
}
