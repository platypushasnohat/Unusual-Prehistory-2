package com.unusualmodding.unusual_prehistory.entity.projectile;

import com.unusualmodding.unusual_prehistory.entity.Talpanas;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

public class TalpanasEgg extends ThrowableItemProjectile {

    public TalpanasEgg(EntityType entity, Level level) {
        super(entity, level);
    }

    public TalpanasEgg(Level worldIn, LivingEntity throwerIn) {
        super(UP2Entities.TALPANAS_EGG.get(), throwerIn, worldIn);
    }

    public TalpanasEgg(Level worldIn, double x, double y, double z) {
        super(UP2Entities.TALPANAS_EGG.get(), x, y, z, worldIn);
    }

    public TalpanasEgg(PlayMessages.SpawnEntity entity, Level world) {
        this(UP2Entities.TALPANAS_EGG.get(), world);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            Talpanas talpanas = UP2Entities.TALPANAS.get().create(this.level());
            if (talpanas != null) {
                talpanas.setAge(-24000);
                talpanas.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                this.level().addFreshEntity(talpanas);
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    protected Item getDefaultItem() {
        return UP2Items.TELECREX_EGG.get();
    }
}
