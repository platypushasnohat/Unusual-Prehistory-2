package com.unusualmodding.unusual_prehistory.blocks.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class TarFluidType extends FluidType {

    public TarFluidType() {
        super(FluidType.Properties.create()
                .descriptionId("block.unusual_prehistory.tar")
                .motionScale(0.0015D)
                .canExtinguish(false)
                .supportsBoating(false)
                .canSwim(false)
                .canDrown(false)
                .density(4000)
                .viscosity(12000)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_POWDER_SNOW)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_POWDER_SNOW));
    }

    @Override
    public double motionScale(Entity entity) {
        return 0.0015D;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return UnusualPrehistory2.modPrefix("block/fluid/tar");
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return UnusualPrehistory2.modPrefix("block/fluid/tar_flowing");
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return UnusualPrehistory2.modPrefix("block/fluid/tar_flowing");
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return new Vector3f(0, 0, 0);
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(0.0F);
                RenderSystem.setShaderFogEnd(1.0F);
            }
        });
    }
}
