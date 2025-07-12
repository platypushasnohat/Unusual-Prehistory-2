package com.unusualmodding.unusual_prehistory.items;

import com.unusualmodding.unusual_prehistory.entity.projectile.DromaeosaurusEgg;
import com.unusualmodding.unusual_prehistory.entity.projectile.TalpanasEgg;
import com.unusualmodding.unusual_prehistory.entity.projectile.TelecrexEgg;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Random;

public class ThrowableEggItem extends Item {

    private final Random random = new Random();

    public ThrowableEggItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.gameEvent(GameEvent.ITEM_INTERACT_START);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        
        if (!level.isClientSide) {
            ThrowableItemProjectile egg;

            if (this == UP2Items.DROMAEOSAURUS_EGG.get()) {
                egg = new DromaeosaurusEgg(level, player);
            }
            else if (this == UP2Items.TELECREX_EGG.get()) {
                egg = new TelecrexEgg(level, player);
            }
            else {
                egg = new TalpanasEgg(level, player);
            }

            egg.setItem(itemstack);
            egg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(egg);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
