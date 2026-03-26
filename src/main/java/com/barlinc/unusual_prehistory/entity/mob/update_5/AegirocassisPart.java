package com.barlinc.unusual_prehistory.entity.mob.update_5;

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

public class AegirocassisPart extends PartEntity<Aegirocassis> {

    public final Aegirocassis parent;
    private final EntityDimensions dimensions;
    private final Entity connectedTo;

    public AegirocassisPart(Aegirocassis parent, Entity connectedTo, float width, float height) {
        super(parent);
        this.parent = parent;
        this.connectedTo = connectedTo;
        this.dimensions = EntityDimensions.scalable(width, height);
        this.refreshDimensions();
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return parent == null ? dimensions : dimensions.scale(parent.getScale());
    }

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand interactionHand) {
        if (parent == null) return InteractionResult.PASS;
        else return parent.interact(player, interactionHand);
    }

    @Override
    public boolean save(@NotNull CompoundTag compoundTag) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return parent != null && parent.canBeCollidedWith();
    }

    @Override
    public boolean isPickable() {
        return parent != null && parent.isPickable();
    }

    @Override
    public boolean is(@NotNull Entity entity) {
        return this == entity || parent == entity;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return !this.isInvulnerableTo(source) && parent.hurt(source, amount);
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
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }
}