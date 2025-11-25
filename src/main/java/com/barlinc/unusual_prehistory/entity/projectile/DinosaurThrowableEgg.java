package com.barlinc.unusual_prehistory.entity.projectile;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class DinosaurThrowableEgg extends ThrowableEgg {

    private final Supplier<EntityType<? extends PrehistoricMob>> mobToSpawn;
    private final Supplier<Item> eggItem;

    public DinosaurThrowableEgg(EntityType<? extends ThrowableItemProjectile> projectileType,
                                Level level,
                                Supplier<Item> eggItem,
                                Supplier<EntityType<? extends PrehistoricMob>> mobToSpawn) {
        super(projectileType, level);
        this.eggItem = eggItem;
        this.mobToSpawn = mobToSpawn;
    }

    public DinosaurThrowableEgg(EntityType<? extends ThrowableItemProjectile> projectileType,
                                Level level,
                                double x, double y, double z,
                                Supplier<Item> eggItem,
                                Supplier<EntityType<? extends PrehistoricMob>> mobToSpawn) {
        super(projectileType, x, y, z, level);
        this.eggItem = eggItem;
        this.mobToSpawn = mobToSpawn;
    }

    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide && mobToSpawn.get() != null) {
            this.spawnMob(mobToSpawn.get());
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        if(eggItem.get() == null) return Items.AIR;
        return eggItem.get();
    }
}
