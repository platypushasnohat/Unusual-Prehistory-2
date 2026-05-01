package com.barlinc.unusual_prehistory.entity.utils;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("deprecation")
public class MobUtils {

    public static float getDepthPathfindingFavor(BlockPos pos, LevelReader world) {
        int y = pos.getY() + Math.abs(world.getMinBuildHeight());
        return 1.0F / (float) (y == 0 ? 1 : y);
    }

    public static float getSurfacePathfindingFavor(BlockPos pos, LevelReader world) {
        int y = Math.abs(world.getMaxBuildHeight()) - pos.getY();
        return 1.0F / (float) (y == 0 ? 1 : y);
    }

    public static void travelInWater(PathfinderMob mob, Vec3 travelVector) {
        mob.moveRelative(mob.getSpeed(), travelVector);
        mob.move(MoverType.SELF, mob.getDeltaMovement());
        mob.setDeltaMovement(mob.getDeltaMovement().scale(0.9D));
        if (mob.horizontalCollision && mob.isEyeInFluid(FluidTags.WATER) && mob.isPathFinding()) {
            mob.setDeltaMovement(mob.getDeltaMovement().add(0.0, 0.005, 0.0));
        }
    }

    public static void savePrehistoricDataToBucket(PrehistoricMob mob, ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(mob, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, (compoundTag) -> {
            compoundTag.putInt("Age", mob.getAge());
            compoundTag.putInt("PacifiedTicks", mob.getPacifiedTicks());
            compoundTag.putBoolean("FromEgg", mob.isFromEgg());
            compoundTag.putInt("EatingCooldown", mob.getEatCooldown());
        });
    }

    public static void loadPrehistoricDataFromBucket(PrehistoricMob mob, CompoundTag compoundTag) {
        Bucketable.loadDefaultDataFromBucketTag(mob, compoundTag);
        if (compoundTag.contains("Age")) {
            mob.setAge(compoundTag.getInt("Age"));
        }
        if (compoundTag.contains("PacifiedTicks")) {
            mob.setPacifiedTicks(compoundTag.getInt("PacifiedTicks"));
        }
        if (compoundTag.contains("FromEgg")) {
            mob.setFromEgg(compoundTag.getBoolean("FromEgg"));
        }
        if (compoundTag.contains("EatingCooldown")) {
            mob.setEatCooldown(compoundTag.getInt("EatingCooldown"));
        }
    }
}
