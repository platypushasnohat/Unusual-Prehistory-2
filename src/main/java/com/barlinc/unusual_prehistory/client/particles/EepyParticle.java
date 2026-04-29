package com.barlinc.unusual_prehistory.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class EepyParticle extends TextureSheetParticle {

    private final float sinOffset;
    private final float cosOffset;
    private final float rotationDirection;
    private final float initialX;
    private final float initialZ;

    EepyParticle(ClientLevel world, double x, double y, double z, SpriteSet spriteProvider) {
        super(world, x, y, z);
        this.gravity = -0.06F;
        this.lifetime = 40;
        this.sinOffset = this.random.nextFloat();
        this.cosOffset = this.random.nextFloat();
        this.rotationDirection = this.random.nextBoolean() ? 1 : -1;
        this.initialX = (float) x;
        this.initialZ = (float) z;
        this.setSpriteFromAge(spriteProvider);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        float xTarget = initialX + (float) Math.cos(this.age * 0.2F + this.cosOffset * 4) * 0.1F * rotationDirection;
        float zTarget = initialZ + (float) Math.sin(this.age * 0.2F + this.sinOffset * 4) * 0.1F * rotationDirection;
        this.xd = 0.1F * (xTarget - x);
        this.zd = 0.1F * (zTarget - z);
        this.setAlpha(1F - (this.age / (float) this.lifetime));
    }

    @Override
    @SuppressWarnings("deprecation")
    protected int getLightColor(float f) {
        BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
        if (this.level.hasChunkAt(blockPos)) {
            return LevelRenderer.getLightColor(this.level, blockPos);
        }
        return 0;
    }

    @OnlyIn(Dist.CLIENT)
    public record Provider(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new EepyParticle(level, x, y, z, this.spriteProvider);
        }
    }
}
