package com.barlinc.unusual_prehistory.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;

public class BrachiosaurusPart extends PartEntity<Brachiosaurus> {

    public final Brachiosaurus parent;
    public final String name;
    private final EntityDimensions dimensions;

    public BrachiosaurusPart(Brachiosaurus parent, String name, float width, float height) {
        super(parent);
        this.parent = parent;
        this.name = name;
        this.dimensions = EntityDimensions.scalable(width, height);
        this.refreshDimensions();
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return this.dimensions;
    }

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand interactionHand) {
        return this.parent.interact(player, interactionHand);
    }

    @Override
    public boolean save(@NotNull CompoundTag compoundTag) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.parent.canBeCollidedWith();
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return super.canCollideWith(entity) && !(entity instanceof Brachiosaurus) && !(entity instanceof BrachiosaurusPart);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean is(@NotNull Entity entity) {
        return this == entity || this.parent == entity;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return !this.isInvulnerableTo(source) && (source.getEntity() != null && this.parent.getControllingPassenger() != null && !(source.getEntity().is(this.parent.getControllingPassenger()))) && this.parent.hurt(source, amount);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(2.0D, 0.5D, 2.0D);
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
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }
}