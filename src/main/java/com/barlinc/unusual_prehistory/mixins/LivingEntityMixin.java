package com.barlinc.unusual_prehistory.mixins;

import com.barlinc.unusual_prehistory.entity.accessor.LivingEntityAccessor;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityAccessor {

    // todo: use this to prevent grabbed mobs from being grabbed multiple times?

    @Unique
    private static final EntityDataAccessor<Boolean> DATA_GRABBED = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BOOLEAN);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("TAIL"), method = "defineSynchedData")
    private void unusualPrehistory$defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_GRABBED, false);
    }

    @Override
    public boolean unusualPrehistory$isBeingGrabbed() {
        return this.entityData.get(DATA_GRABBED);
    }

    @Override
    public void unusualPrehistory$setBeingGrabbed(boolean grabbed) {
        this.entityData.set(DATA_GRABBED, grabbed);
    }
}
