package com.barlinc.unusual_prehistory.items;

import com.barlinc.unusual_prehistory.entity.LivingOoze;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class EmbryoItem extends Item {

    public final Supplier<? extends EntityType<?>> toSpawn;

    public EmbryoItem(Properties properties, Supplier<? extends EntityType<?>> toSpawn) {
        super(properties);
        this.toSpawn = toSpawn;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity entity, @NotNull InteractionHand hand) {
        if (entity instanceof LivingOoze livingOoze) {
            if (!player.level().isClientSide && livingOoze.isAlive()) {
                stack.shrink(1);
                livingOoze.setHeldItem(stack);
                livingOoze.hatchedEntity = this.toSpawn;
                livingOoze.setMobTimer(100);
            }
            return InteractionResult.sidedSuccess(player.level().isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }
}
