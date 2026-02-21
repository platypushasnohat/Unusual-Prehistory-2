package com.barlinc.unusual_prehistory.utils;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class MobAttackUtils {

    public static void chargeAtTarget(LivingEntity attacker, Entity target, float speed) {
        int speedFactor = attacker.hasEffect(MobEffects.MOVEMENT_SPEED) ? attacker.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
        int slownessFactor = attacker.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? attacker.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
        float effectSpeed = 0.1F * (speedFactor - slownessFactor);
        Vec3 chargeDirection = new Vec3(target.getX() - attacker.getX(), target.getY() - attacker.getY(), target.getZ() - attacker.getZ()).normalize();
        float YRot = Mth.approachDegrees(attacker.getYRot(), (float) (Mth.atan2(chargeDirection.z, chargeDirection.x) * (180F / Math.PI)) - 90.0F, 0.5F);
        speed = speed + effectSpeed;
        attacker.setYRot(YRot);
        attacker.setYBodyRot(YRot);
        attacker.setDeltaMovement(-Mth.sin(YRot * ((float) Math.PI / 180F)) * speed, attacker.getDeltaMovement().y, Mth.cos(YRot * ((float) Math.PI / 180F)) * speed);
    }
}
