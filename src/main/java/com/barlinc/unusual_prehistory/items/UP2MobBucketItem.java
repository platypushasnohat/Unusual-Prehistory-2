package com.barlinc.unusual_prehistory.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);

        if (variantNameGetter == null) return;
        ChatFormatting[] grayChatFormatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
        CompoundTag tag = stack.getTag();

        if (tag == null || !tag.contains("BucketVariantTag", 3)) return;

        int variantId = tag.getInt("BucketVariantTag");
        String variantName = variantNameGetter.apply(variantId);

        EntityType<?> type = this.getFishType();
        ResourceLocation key = EntityType.getKey(type);

        String translationKey = "entity." + key.getNamespace() + "." + key.getPath() + ".variant_" + variantName;

        tooltip.add(Component.translatable(translationKey).withStyle(grayChatFormatting));
    }
}