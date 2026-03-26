package com.barlinc.unusual_prehistory.items;

import com.barlinc.unusual_prehistory.entity.mob.update_5.Aegirocassis;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class UP2MobBucketItem extends MobBucketItem {

    private final IntFunction<String> variantNameGetter;

    public UP2MobBucketItem(Supplier<? extends EntityType<?>> entityType, Fluid fluid, SoundEvent sound, Properties properties) {
        this(entityType, fluid, sound, properties, null);
    }

    public UP2MobBucketItem(Supplier<? extends EntityType<?>> entityType, Fluid fluid, SoundEvent sound, Properties properties, @Nullable IntFunction<String> variantNameGetter) {
        super(entityType, () -> fluid, () -> sound, properties.stacksTo(1));
        this.variantNameGetter = variantNameGetter;
    }

    @Override
    public void checkExtraContent(@Nullable Player player, @NotNull Level level, @NotNull ItemStack stack, @NotNull BlockPos pos) {
        if (level instanceof ServerLevel) {
            this.spawn((ServerLevel)level, stack, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    private void spawn(ServerLevel level, ItemStack stack, BlockPos pos) {
        Entity entity = this.getFishType().spawn(level, stack, null, pos, MobSpawnType.BUCKET, true, false);
        if (entity instanceof Bucketable bucketable) {
            bucketable.loadFromBucketTag(stack.getOrCreateTag());
            bucketable.setFromBucket(true);
        }
        if (entity instanceof Aegirocassis aegirocassis && !aegirocassis.isBaby()) {
            aegirocassis.setBaby(true);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);

        if (variantNameGetter == null) return;
        ChatFormatting[] grayChatFormatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
        CompoundTag compoundTag = stack.getTag();

        if (compoundTag == null || !compoundTag.contains("BucketVariantTag", 3)) return;

        int variantId = compoundTag.getInt("BucketVariantTag");
        String variantName = variantNameGetter.apply(variantId);

        EntityType<?> type = this.getFishType();
        ResourceLocation key = EntityType.getKey(type);

        String translationKey = "entity." + key.getNamespace() + "." + key.getPath() + ".variant_" + variantName;

        tooltip.add(Component.translatable(translationKey).withStyle(grayChatFormatting));

        if (type == UP2Entities.COELACANTHUS.get()) {
            if (compoundTag.contains("Size", 3)) {
                int i = compoundTag.getInt("Size");
                String size = "entity." + key.getNamespace() + "." + key.getPath() + ".size";
                tooltip.add((Component.translatable(size, i)).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            }
        }
    }
}