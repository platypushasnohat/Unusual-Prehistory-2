package com.barlinc.unusual_prehistory.entity.effects;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public class Fury extends MobEffect {

    private static final ResourceLocation FURY_ATTACK_SPEED_ID = UnusualPrehistory2.modPrefix("fury_attack_speed_boost");
    private static final ResourceLocation FURY_SPEED_ID = UnusualPrehistory2.modPrefix("fury_speed_boost");

    public Fury() {
        super(MobEffectCategory.NEUTRAL, 0x8c2f27);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int level) {
        AttributeInstance attackSpeed = entity.getAttribute(Attributes.ATTACK_SPEED);
        AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);

        float levelScale = (1 + level) * 2.5F;
        if (attackSpeed != null) {
            this.removeFuryModifiers(entity);
            float attackSpeedBoost = ((1F - (entity.getHealth() / entity.getMaxHealth())) * levelScale) * 0.5F;
            attackSpeed.addTransientModifier(new AttributeModifier(FURY_ATTACK_SPEED_ID, attackSpeedBoost, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
        if (speed != null) {
            this.removeFuryModifiers(entity);
            float speedBoost = ((1F - (entity.getHealth() / entity.getMaxHealth())) * levelScale) * 0.2F;
            speed.addTransientModifier(new AttributeModifier(FURY_SPEED_ID, speedBoost, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration > 0;
    }

    protected void removeFuryModifiers(LivingEntity living) {
        AttributeInstance attackSpeed = living.getAttribute(Attributes.ATTACK_SPEED);
        AttributeInstance speed = living.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attackSpeed != null) {
            if (attackSpeed.getModifier(FURY_ATTACK_SPEED_ID) != null) {
                attackSpeed.removeModifier(FURY_ATTACK_SPEED_ID);
            }
        }
        if (speed != null) {
            if (speed.getModifier(FURY_SPEED_ID) != null) {
                speed.removeModifier(FURY_SPEED_ID);
            }
        }
    }

    @Override
    public void onMobRemoved(@NotNull LivingEntity entity, int amplifier, Entity.@NotNull RemovalReason reason) {
        this.removeFuryModifiers(entity);
    }

}