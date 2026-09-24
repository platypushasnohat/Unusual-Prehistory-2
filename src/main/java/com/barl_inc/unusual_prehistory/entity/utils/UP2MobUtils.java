package com.barl_inc.unusual_prehistory.entity.utils;

import com.barl_inc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class UP2MobUtils {

    public static void savePrehistoricDataToBucket(PrehistoricMob mob, ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(mob, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, (compoundTag) -> {
            compoundTag.putInt("Age", mob.getAge());
            if (mob.getOwnerUUID() != null) {
                compoundTag.putUUID("Owner", mob.getOwnerUUID());
            }
        });
    }

    public static void loadPrehistoricDataFromBucket(PrehistoricMob mob, CompoundTag compoundTag) {
        Bucketable.loadDefaultDataFromBucketTag(mob, compoundTag);

        if (compoundTag.contains("Age")) {
            mob.setAge(compoundTag.getInt("Age"));
        }

        if (compoundTag.contains("Owner")) {
            UUID uuid;
            if (compoundTag.hasUUID("Owner")) {
                uuid = compoundTag.getUUID("Owner");
            } else {
                String owner = compoundTag.getString("Owner");
                uuid = OldUsersConverter.convertMobOwnerIfNecessary(Objects.requireNonNull(mob.getServer()), owner);
            }
            if (uuid != null) {
                try {
                    mob.setOwnerUUID(uuid);
                    mob.setTame(true, false);
                } catch (Throwable throwable) {
                    mob.setTame(false, true);
                }
            }
        }
    }
}
