package com.barlinc.unusual_prehistory.utils;

import com.barlinc.unusual_prehistory.network.ParticlePacket;
import com.barlinc.unusual_prehistory.registry.UP2Network;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class UP2ParticleUtils {

    public static void queueParticlesOnBlockFaces(Level level, BlockPos pos, ParticleOptions particle, IntProvider count) {
        for (Direction direction : Direction.values()) {
            queueParticlesOnBlockFace(level, pos, particle, count, direction, () -> getRandomSpeedRanges(level.random), 0.55D);
        }
    }

    public static void queueParticlesOnBlockFace(Level level, BlockPos pos, ParticleOptions particleOptions, IntProvider count, Direction direction, Supplier<Vec3> supplier, double v) {
        int sample = count.sample(level.random);
        for (int j = 0; j < sample; j++) {
            queueParticleOnFace(level, pos, direction, particleOptions, supplier.get(), v);
        }
    }

    public static void queueParticleOnFace(Level level, BlockPos pos, Direction direction, ParticleOptions particle, Vec3 speed, double v) {
        ParticlePacket particlePacket = new ParticlePacket();
        Vec3 vec3 = Vec3.atCenterOf(pos);
        int stepX = direction.getStepX();
        int stepY = direction.getStepY();
        int stepZ = direction.getStepZ();
        double x = vec3.x + (stepX == 0 ? Mth.nextDouble(level.random, -0.5D, 0.5D) : (double) stepX * v);
        double y = vec3.y + (stepY == 0 ? Mth.nextDouble(level.random, -0.5D, 0.5D) : (double) stepY * v);
        double z = vec3.z + (stepZ == 0 ? Mth.nextDouble(level.random, -0.5D, 0.5D) : (double) stepZ * v);
        double xSpeed = stepX == 0 ? speed.x() : 0.0D;
        double ySpeed = stepY == 0 ? speed.y() : 0.0D;
        double zSpeed = stepZ == 0 ? speed.z() : 0.0D;
        level.addParticle(particle, x, y, z, xSpeed, ySpeed, zSpeed);
        particlePacket.queueParticle(particle, false, x, y, z, xSpeed, ySpeed, zSpeed);
        UP2Network.sendToTrackingChunk(particlePacket, level, pos);
    }

    private static Vec3 getRandomSpeedRanges(RandomSource random) {
        return new Vec3(Mth.nextDouble(random, -0.5D, 0.5D), Mth.nextDouble(random, -0.5D, 0.5D), Mth.nextDouble(random, -0.5D, 0.5D));
    }
}
