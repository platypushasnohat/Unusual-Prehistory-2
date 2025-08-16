package com.unusualmodding.unusual_prehistory.client.renderer;

import net.minecraft.client.renderer.ShaderInstance;
import org.jetbrains.annotations.Nullable;

public class UP2InternalShaders {

    private static ShaderInstance renderTypeSepiaShader;

    @Nullable
    public static ShaderInstance getRenderTypeSepiaShader() {
        return renderTypeSepiaShader;
    }

    public static void setRenderTypeSepiaShader(ShaderInstance instance) {
        renderTypeSepiaShader = instance;
    }
}
