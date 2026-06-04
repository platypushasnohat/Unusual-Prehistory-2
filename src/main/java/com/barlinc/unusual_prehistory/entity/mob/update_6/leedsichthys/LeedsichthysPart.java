package com.barlinc.unusual_prehistory.entity.mob.update_6.leedsichthys;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class LeedsichthysPart extends PartEntity<Leedsichthys> {

    private final EntityDimensions dimensions;

    public LeedsichthysPart(Leedsichthys parent, float width, float height) {
        super(parent);
        this.dimensions = EntityDimensions.scalable(width, height);
        this.refreshDimensions();
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return dimensions.scale(this.getParent().getAgeScale());
    }

    @Override
    public void push(double x, double y, double z) {
        this.getParent().push(x, y, z);
    }

    @Override
    public final @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        return this.getParent().interact(player, hand);
    }

    @Override
    public boolean save(@NotNull CompoundTag compoundTag) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.getParent().canBeCollidedWith();
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public @NotNull Entity getRootVehicle() {
        return this.getParent().getRootVehicle();
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return this.getParent().getPickResult();
    }

    @Override
    public boolean is(@NotNull Entity entity) {
        return this == entity || this.getParent() == entity;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return this.getParent().hurt(source, amount);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(2.0D, 1.0D, 2.0D);
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    public void setPosCenteredY(Vec3 pos) {
        this.setPos(pos.x, pos.y - this.getBbHeight() * 0.5F, pos.z);
    }

    public Vec3 centeredPosition() {
        return this.position().add(0, this.getBbHeight() * 0.5F, 0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }
}