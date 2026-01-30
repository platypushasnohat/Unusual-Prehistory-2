package com.barlinc.unusual_prehistory.items;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class UP2FoodOnAStickItem extends Item {

   private final int consumeItemDamage;

   public UP2FoodOnAStickItem(Item.Properties pProperties, int pConsumeItemDamage) {
      super(pProperties);
      this.consumeItemDamage = pConsumeItemDamage;
   }

   @Override
   public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (!level.isClientSide) {
           Entity entity = player.getControlledVehicle();
           if (player.isPassenger() && entity instanceof ItemSteerable itemsteerable) {
               if (itemsteerable.boost()) {
                   itemstack.hurtAndBreak(this.consumeItemDamage, player, (player1) -> player1.broadcastBreakEvent(hand));
                   if (itemstack.isEmpty()) {
                       ItemStack stack = new ItemStack(Items.FISHING_ROD);
                       stack.setTag(itemstack.getTag());
                       return InteractionResultHolder.success(stack);
                   }
                   return InteractionResultHolder.success(itemstack);
               }
           }
           player.awardStat(Stats.ITEM_USED.get(this));
      }
      return InteractionResultHolder.pass(itemstack);
   }
}