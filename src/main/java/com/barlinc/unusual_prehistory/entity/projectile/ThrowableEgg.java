package com.barlinc.unusual_prehistory.entity.projectile;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ThrowableEgg extends ThrowableItemProjectile {

    private final Supplier<EntityType<? extends PrehistoricMob>> mobToSpawn;
    private final Supplier<Item> eggItem;

    public ThrowableEgg(EntityType<? extends ThrowableItemProjectile> projectileType, Level level, Supplier<Item> eggItem, Supplier<EntityType<? extends PrehistoricMob>> mobToSpawn) {
        super(projectileType, level);
        this.eggItem = eggItem;
        this.mobToSpawn = mobToSpawn;
    }

    public ThrowableEgg(EntityType<? extends ThrowableItemProjectile> projectileType, Level level, double x, double y, double z, Supplier<Item> eggItem, Supplier<EntityType<? extends PrehistoricMob>> mobToSpawn) {
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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    public void spawnMob(EntityType<? extends PrehistoricMob> entityType) {
        int i = 1;
        if (this.random.nextInt(200) == 0) {
            i = 4;
        }
        PrehistoricMob mob = entityType.create(this.level());
        for (int j = 0; j < i; j++) {
            if (mob != null) {
                mob.setAge(-24000);
                mob.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                this.level().addFreshEntity(mob);
            }
        }
        if (!this.level().isClientSide && this.getOwner() != null && this.getOwner() instanceof ServerPlayer serverPlayer && mob != null) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, mob);
        }
        this.level().broadcastEntityEvent(this, (byte) 3);
        this.discard();
    }
}
