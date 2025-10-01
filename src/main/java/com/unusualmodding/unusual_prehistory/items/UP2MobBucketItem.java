package com.unusualmodding.unusual_prehistory.items;

import com.unusualmodding.unusual_prehistory.entity.Diplocaulus;
import com.unusualmodding.unusual_prehistory.entity.JawlessFish;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class UP2MobBucketItem extends MobBucketItem {

    public UP2MobBucketItem(Supplier<? extends EntityType<?>> entityType, Fluid fluid, SoundEvent sound, Properties properties) {
        super(entityType, () -> fluid, () -> sound, properties.stacksTo(1));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);

        ChatFormatting[] grayChatFormatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};

        if (getFishType() == UP2Entities.DIPLOCAULUS.get()) {
            CompoundTag compoundtag = stack.getTag();
            if (compoundtag != null && compoundtag.contains("BucketVariantTag", 3)) {
                int variant = compoundtag.getInt("BucketVariantTag");
                Diplocaulus.DiplocaulusVariant diplocaulusVariant = Diplocaulus.DiplocaulusVariant.byId(variant);
                String name = "entity.unusual_prehistory.diplocaulus.variant_" + diplocaulusVariant.name().toLowerCase(Locale.ROOT);
                tooltip.add((Component.translatable(name)).withStyle(grayChatFormatting));
            }
        }

        if (getFishType() == UP2Entities.JAWLESS_FISH.get()) {
            CompoundTag compoundtag = stack.getTag();
            if (compoundtag != null && compoundtag.contains("BucketVariantTag", 3)) {
                int variant = compoundtag.getInt("BucketVariantTag");
                JawlessFish.JawlessFishVariant jawlessFishVariant = JawlessFish.JawlessFishVariant.byId(variant);
                String name = "entity.unusual_prehistory.jawless_fish.variant_" + jawlessFishVariant.name().toLowerCase(Locale.ROOT);
                tooltip.add((Component.translatable(name)).withStyle(grayChatFormatting));
            }
        }
    }
}