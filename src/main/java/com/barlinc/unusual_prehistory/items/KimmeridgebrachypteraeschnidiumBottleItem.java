package com.barlinc.unusual_prehistory.items;

import com.barlinc.unusual_prehistory.entity.Kimmeridgebrachypteraeschnidium;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class KimmeridgebrachypteraeschnidiumBottleItem extends Item {

    public KimmeridgebrachypteraeschnidiumBottleItem(Properties builder) {
        super(builder.stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        level.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            CompoundTag compoundTag = itemstack.getOrCreateTag();
            if (!context.getPlayer().getAbilities().instabuild) {
                context.getPlayer().setItemInHand(context.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            }
            Entity entity = UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get().spawn((ServerLevel) level, itemstack, context.getPlayer(), blockpos1, MobSpawnType.BUCKET, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);

            if (entity instanceof Kimmeridgebrachypteraeschnidium mob) {
                int age = compoundTag.contains("Age") ? compoundTag.getInt("Age") : 0;
                float health = compoundTag.contains("Health") ? compoundTag.getFloat("Health") : 6.0F;
                int base_color = compoundTag.contains("BaseColor") ? compoundTag.getInt("BaseColor") : level.random.nextInt(16);
                int pattern = compoundTag.contains("Pattern") ? compoundTag.getInt("Pattern") : level.random.nextInt(7);
                int pattern_color = compoundTag.contains("PatternColor") ? compoundTag.getInt("PatternColor") : level.random.nextInt(16);
                int wing_color = compoundTag.contains("WingColor") ? compoundTag.getInt("WingColor") : level.random.nextInt(16);
                boolean hasPattern = compoundTag.contains("HasPattern") && compoundTag.getBoolean("HasPattern");
                mob.setAge(age);
                mob.setHealth(health);
                mob.setPersistenceRequired();
                mob.setBaseColor(base_color);
                mob.setPattern(pattern);
                mob.setPatternColor(pattern_color);
                mob.setWingColor(wing_color);
                mob.setHasPattern(hasPattern);
            }
            return InteractionResult.CONSUME;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        ChatFormatting[] grayChatFormatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null && compoundTag.contains("BaseColor", 3)) {

            int base_color = compoundTag.getInt("BaseColor");
            int pattern = compoundTag.getInt("Pattern");
            int pattern_color = compoundTag.getInt("PatternColor");
            int wing_color = compoundTag.getInt("WingColor");
            boolean hasPattern = compoundTag.getBoolean("HasPattern");

            String base = "entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_" + base_color;
            String patterns = "entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_" + Kimmeridgebrachypteraeschnidium.getPatternName(pattern);
            String patternColor = "entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_" + pattern_color;
            String wingColor = "entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_" + wing_color;

            MutableComponent patternInfo = Component.translatable(patternColor);
            patternInfo.append(CommonComponents.SPACE).append(Component.translatable(patterns));
            patternInfo.withStyle(grayChatFormatting);

            tooltip.add(Component.translatable(base).withStyle(grayChatFormatting));
            tooltip.add(Component.translatable(wingColor).withStyle(grayChatFormatting));
            if (hasPattern) {
                tooltip.add(patternInfo);
            }
        }
    }
}