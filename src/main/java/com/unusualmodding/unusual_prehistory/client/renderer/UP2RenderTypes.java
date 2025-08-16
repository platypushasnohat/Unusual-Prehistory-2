package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ForgeRenderTypes;

public class UP2RenderTypes extends RenderType {

    protected static final RenderStateShard.ShaderStateShard RENDERTYPE_SEPIA_SHADER = new RenderStateShard.ShaderStateShard(UP2InternalShaders::getRenderTypeSepiaShader);

    public UP2RenderTypes(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable runnable, Runnable runnable1) {
        super(string, vertexFormat, mode, p_173181_, p_173182_, p_173183_, runnable, runnable1);
    }

    // intended for the book, would render items and such with sepia filter, unused for now
    public static RenderType getBookWidget(ResourceLocation resourceLocation, boolean sepia) {
        if (sepia) {
            return create("book_widget", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_SEPIA_SHADER)
                    .setCullState(NO_CULL)
                    .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setOverlayState(OVERLAY)
                    .createCompositeState(true));
        } else {
            return ForgeRenderTypes.getUnlitTranslucent(resourceLocation);
        }
    }
}
