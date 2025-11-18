package com.unusualmodding.unusual_prehistory.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TarBubbleParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    protected TarBubbleParticle(ClientLevel level, double x, double y, double z, double xspeed, double yspeed, double zspeed, SpriteSet sprites) {
        super(level, x, y, z);
        this.sprites = sprites;
        this.setSize(0.02F, 0.02F);
        this.quadSize = 0.1F + level.random.nextFloat() * 0.2F;
        this.xd = xspeed * (double) 0.2F + (Math.random() * 2.0D - 1.0D) * (double) 0.02F;
        this.yd = yspeed * (double) 0.3F + (Math.random() * 2.0D - 1.0D) * (double) 0.02F;
        this.zd = zspeed * (double) 0.2F + (Math.random() * 2.0D - 1.0D) * (double) 0.02F;
        this.lifetime = 10 + level.getRandom().nextInt(10);
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ < this.lifetime) {
            this.setSpriteFromAge(this.sprites);
            this.yd += 0.003D;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.85F;
            this.yd *= 0.85F;
            this.zd *= 0.85F;
        } else {
            this.remove();
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xspeed, double yspeed, double zspeed) {
            return new TarBubbleParticle(level, x, y, z, xspeed, yspeed, zspeed, sprites);
        }
    }
}