package com.barlinc.unusual_prehistory.entity.mob_effects;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Dazzled extends MobEffect {

    public static final ResourceLocation DAZZLED_MODIFIER = UnusualPrehistory2.modPrefix("dazzled_speed_decrease");

    public Dazzled() {
        super(MobEffectCategory.HARMFUL, 0xfbf994);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int level) {
        if (!entity.level().isClientSide && entity instanceof Mob mob && entity.tickCount % 10 == 0) {
            if (mob.getTarget() != null) {
                mob.setTarget(null);
            }
            if (mob.getLastHurtByMob() != null) {
                mob.setLastHurtByMob(null);
            }
        }
        AttributeInstance instance = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (instance != null) {
            instance.removeModifier(DAZZLED_MODIFIER);
            instance.addTransientModifier(new AttributeModifier(DAZZLED_MODIFIER, -0.2F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
        return true;
    }

    @Override
    public void removeAttributeModifiers(AttributeMap attributeMap) {
        AttributeInstance instance = attributeMap.getInstance(Attributes.MOVEMENT_SPEED);
        if (instance != null) {
            instance.removeModifier(DAZZLED_MODIFIER);
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration > 0;
    }
}
