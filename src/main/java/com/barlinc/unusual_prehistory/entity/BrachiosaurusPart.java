package com.barlinc.unusual_prehistory.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
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

    private final EntityDimensions dimensions;
    public float scale = 1;

    public BrachiosaurusPart(Brachiosaurus parent, float sizeXZ, float sizeY) {
        super(parent);
        this.blocksBuilding = true;
        this.dimensions = EntityDimensions.scalable(sizeXZ, sizeY);
        this.refreshDimensions();
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        Brachiosaurus parent = this.getParent();
        return parent == null ? dimensions : dimensions.scale(parent.getScale());
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        Brachiosaurus parent = this.getParent();
        if (parent == null) {
            return InteractionResult.PASS;
        } else {
            this.playSound(SoundEvents.ITEM_BREAK);
//            if (player.level().isClientSide) {
//                AlexsCaves.sendMSGToServer(new MultipartEntityMessage(parent.getId(), player.getId(), 0, 0));
//            }
            return parent.interact(player, hand);
        }
    }

    @Override
    public boolean save(@NotNull CompoundTag compoundTag) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        Brachiosaurus parent = this.getParent();
        return parent != null && parent.canBeCollidedWith();
    }

    @Override
    public boolean isPickable() {
        Brachiosaurus parent = this.getParent();
        return parent != null && parent.isPickable();
    }

    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        Brachiosaurus parent = this.getParent();
        return super.isInvulnerableTo(source) || parent != null && parent.isInvulnerableTo(source) || source.getEntity() != null && this.getParent().isPassengerOfSameVehicle(source.getEntity());
    }

    @Override
    public boolean is(@NotNull Entity entity) {
        return this == entity || this.getParent() == entity;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        throw new UnsupportedOperationException();
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

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        Brachiosaurus parent = this.getParent();
//        if (!this.isInvulnerableTo(source) && parent != null) {
//            Entity player = source.getEntity();
//            if (player != null && !parent.isAlliedTo(player) && player.level().isClientSide) {
//                AlexsCaves.sendMSGToServer(new MultipartEntityMessage(parent.getId(), player.getId(), 1, amount));
//            }
//        }
        return false;
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
}