package com.barl_inc.unusual_prehistory.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.IntFunction;

public class UP2MobBucketItem extends MobBucketItem {

    public final IntFunction<String> variantNameGetter;
    public final EntityType<?> entityType;

    public UP2MobBucketItem(EntityType<?> entityType, Fluid fluid, SoundEvent sound, Properties properties) {
        this(entityType, fluid, sound, properties, null);
    }

    public UP2MobBucketItem(EntityType<?> entityType, Fluid fluid, SoundEvent sound, Properties properties, @Nullable IntFunction<String> variantNameGetter) {
        super(entityType, fluid, sound, properties.stacksTo(1));
        this.variantNameGetter = variantNameGetter;
        this.entityType = entityType;
    }

    private void spawn(ServerLevel level, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityType.spawn(level, stack, null, pos, MobSpawnType.BUCKET, true, false);
        if (entity instanceof Bucketable bucketable) {
            CustomData customdata = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
            bucketable.loadFromBucketTag(customdata.copyTag());
            bucketable.setFromBucket(true);
        }
    }

    @Override
    public void checkExtraContent(@Nullable Player player, Level level, ItemStack stack, BlockPos pos) {
        if (level instanceof ServerLevel) {
            this.spawn((ServerLevel)level, stack, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);
        if (this.variantNameGetter == null) {
            return;
        }
        CustomData bucketData = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
        if (bucketData.isEmpty() || !bucketData.contains("Variant")) {
            return;
        }
        int variantId = bucketData.copyTag().getInt("Variant");
        String variantName = this.variantNameGetter.apply(variantId);
        ResourceLocation key = EntityType.getKey(this.entityType);
        String translationKey = "entity." + key.getNamespace() + "." + key.getPath() + ".variant_" + variantName;
        components.add(Component.translatable(translationKey).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
}