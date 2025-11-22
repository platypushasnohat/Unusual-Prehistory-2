package com.barlinc.unusual_prehistory.entity.projectile;

import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class TalpanasEgg extends ThrowableEgg {

    public TalpanasEgg(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public TalpanasEgg(Level level, double x, double y, double z) {
        super(UP2Entities.TALPANAS_EGG.get(), x, y, z, level);
    }

    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.spawnMob(UP2Entities.TALPANAS.get());
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return UP2Items.TALPANAS_EGG.get();
    }
}
