package com.unusualmodding.unusual_prehistory.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FrozenMeatItem extends Item {

    public FrozenMeatItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        entity.setTicksFrozen(Math.min(entity.getTicksRequiredToFreeze() * 4, entity.getTicksFrozen() + 120));
        return super.finishUsingItem(stack, level, entity);
    }
}
