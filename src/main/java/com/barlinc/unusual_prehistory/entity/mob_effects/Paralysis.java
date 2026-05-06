package com.barlinc.unusual_prehistory.entity.mob_effects;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForgeMod;

public class Paralysis extends MobEffect {

    public static final ResourceLocation PARALYSIS_MODIFIER = UnusualPrehistory2.modPrefix("paralysis_slow_down");

	public Paralysis() {
		super(MobEffectCategory.HARMFUL, 0xffd649);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, PARALYSIS_MODIFIER, -0.9F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.getType().is(UP2EntityTags.UNAFFECTED_BY_PARALYSIS)) {
			return false;
		}
        if (entity.tickCount % 60 == 0 && entity.getHealth() > 1.0F && !(entity instanceof Player player && player.getAbilities().invulnerable)) {
            entity.hurt(entity.damageSources().source(NeoForgeMod.POISON_DAMAGE), 1.0F);
        }
        if (entity.tickCount % 10 == 0) {
            float amount = entity.getRandom().nextFloat() * 0.15F;
            float x = 0.0F;
            float z = 0.0F;
            if (entity.getRandom().nextBoolean()) {
                amount *= -1;
            }
            if (entity.getRandom().nextBoolean()) {
                x = amount;
            }
            else {
                z = amount;
            }
            entity.setDeltaMovement(entity.getDeltaMovement().add(x, 0.0F, z));
        }
		return true;
	}

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration > 0;
    }
}