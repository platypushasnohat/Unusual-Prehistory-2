package com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ArthropleuraBody extends ArthropleuraPart {

    private static final EntityDataAccessor<Boolean> TAIL = SynchedEntityData.defineId(ArthropleuraBody.class, EntityDataSerializers.BOOLEAN);
    public ArthropleuraHead head;

    public ArthropleuraBody(EntityType<? extends ArthropleuraPart> entity, Level level) {
        super(entity, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TAIL, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Tail", this.isTail());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setTail(compoundTag.getBoolean("Tail"));
    }

    @Override
    public boolean isFood(@NotNull ItemStack itemStack) {
        return false;
    }

    public void setTail(boolean tail) {
        this.entityData.set(TAIL, tail);
    }
    public boolean isTail() {
        return this.entityData.get(TAIL);
    }

    @Override
    public void tick() {
        super.tick();
        if ((head == null || head.isDeadOrDying()) && !this.isNoAi()) {
            this.hurt(this.damageSources().starve(), Float.MAX_VALUE);
        }
    }

    @Override
    public boolean hasCustomName() {
        if (this.head != null) {
            return head.hasCustomName();
        }
        return false;
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        if (head != null) {
            this.head.setCustomName(name);
        }
    }

    @Override
    public void setCustomNameVisible(boolean pAlwaysRenderNameTag) {
        if (head != null) {
            this.head.setCustomNameVisible(pAlwaysRenderNameTag);
        }
    }

    @Override
    public boolean onClimbable() {
        return this.isInWater() || super.onClimbable();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (!this.isInvulnerableTo(source) && head != null) {
            this.head.hurt(source, amount);
        }
        return false;
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FALL);
    }

//    @Override
//    public boolean is(@NotNull Entity entity) {
//        return this == entity || this.head == entity;
//    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }
}