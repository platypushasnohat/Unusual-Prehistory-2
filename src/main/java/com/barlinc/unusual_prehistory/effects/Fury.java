package com.barlinc.unusual_prehistory.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Fury extends MobEffect {

    private static final UUID FURY_ATTACK_SPEED_UUID = UUID.fromString("e0f87c4e-afbb-48b4-830b-815cff012072");
    private static final UUID FURY_SPEED_UUID = UUID.fromString("ef0f8dee-c1e8-4021-8620-0eae949aff55");

    public Fury() {
        super(MobEffectCategory.BENEFICIAL, 0x8c2f27);
    }

    public void applyEffectTick(LivingEntity entity, int level) {
        AttributeInstance attackSpeed = entity.getAttribute(Attributes.ATTACK_SPEED);
        AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        float levelScale = (1 + level) * 2.5F;

        if (attackSpeed != null) {
            this.removeFuryModifiers(entity);
            float attackSpeedBoost = ((1F - (entity.getHealth() / entity.getMaxHealth())) * levelScale) * 0.5F;
            attackSpeed.addTransientModifier(new AttributeModifier(FURY_ATTACK_SPEED_UUID, "Fury attack speed boost", attackSpeedBoost, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        if (speed != null) {
            this.removeFuryModifiers(entity);
            float speedBoost = ((1F - (entity.getHealth() / entity.getMaxHealth())) * levelScale) * 0.2F;
            speed.addTransientModifier(new AttributeModifier(FURY_SPEED_UUID, "Fury speed boost", speedBoost, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    protected void removeFuryModifiers(LivingEntity living) {
        AttributeInstance attackSpeed = living.getAttribute(Attributes.ATTACK_SPEED);
        AttributeInstance speed = living.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attackSpeed != null) {
            if (attackSpeed.getModifier(FURY_ATTACK_SPEED_UUID) != null) {
                attackSpeed.removeModifier(FURY_ATTACK_SPEED_UUID);
            }
        }
        if (speed != null) {
            if (speed.getModifier(FURY_SPEED_UUID) != null) {
                speed.removeModifier(FURY_SPEED_UUID);
            }
        }
    }

    @Override
    public void addAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap map, int i) {
        super.addAttributeModifiers(entity, map, i);
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap map, int i) {
        super.removeAttributeModifiers(entity, map, i);
        this.removeFuryModifiers(entity);
    }
}