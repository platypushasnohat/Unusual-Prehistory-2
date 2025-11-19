package com.barlinc.unusual_prehistory.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EepyParticle extends TextureSheetParticle {

    private final SpriteSet spriteProvider;
    private float sinOffset;
    private float cosOffset;
    private float rotationDirection;
    private float initialX;
    private float initialZ;

    EepyParticle(ClientLevel world, double x, double y, double z, SpriteSet spriteProvider) {
        super(world, x, y, z);
        this.spriteProvider = spriteProvider;
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
    public ParticleRenderType getRenderType() {
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
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            return new EepyParticle(clientWorld, d, e, f, this.spriteProvider);
        }
    }
}
