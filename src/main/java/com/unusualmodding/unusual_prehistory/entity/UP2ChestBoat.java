package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.utils.UP2BoatType;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;

public class UP2ChestBoat extends ChestBoat implements UP2BoatType {

    public UP2ChestBoat(EntityType type, Level level) {
        super(type, level);
        this.blocksBuilding = true;
    }

    public UP2ChestBoat(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(UP2Entities.CHEST_BOAT.get(), level);
        this.setBoundingBox(this.makeBoundingBox());
    }

    public UP2ChestBoat(Level level, double x, double y, double z) {
        this(UP2Entities.CHEST_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public UP2ChestBoat(Level level, Vec3 location, UP2BoatType.Type type) {
        this(level, location.x, location.y, location.z);
        this.setUP2BoatType(type);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putString("UP2BoatType", getUP2BoatType().getName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains("UP2BoatType")) {
            this.entityData.set(DATA_ID_TYPE, UP2BoatType.Type.byName(compoundTag.getString("UP2BoatType")).ordinal());
        }
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        this.lastYd = this.getDeltaMovement().y;
        if (!this.isPassenger()) {
            if (onGround) {
                if (this.fallDistance > 3.0F) {
                    if (this.status != Status.ON_LAND) {
                        this.resetFallDistance();
                        return;
                    }

                    this.causeFallDamage(this.fallDistance, 1.0F, this.damageSources().fall());
                    if (!this.level().isClientSide && !this.isRemoved()) {
                        this.kill();
                        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            for (int i = 0; i < 3; ++i) {
                                this.spawnAtLocation(this.getUP2BoatType().getPlankSupplier().get());
                            }

                            for (int j = 0; j < 2; ++j) {
                                this.spawnAtLocation(Items.STICK);
                            }
                        }
                    }
                }

                this.resetFallDistance();
            } else if (!this.level().getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && y < 0.0D) {
                this.fallDistance -= (float) y;
            }
        }
    }

    public void setUP2BoatType(UP2BoatType.Type type) {
        this.entityData.set(DATA_ID_TYPE, type.ordinal());
    }

    @Override
    public UP2BoatType.Type getUP2BoatType() {
        return UP2BoatType.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    public void setVariant(Boat.Type vanillaType) {
    }

    @Override
    public Item getDropItem() {
        return getUP2BoatType().getChestDropSupplier().get();
    }

    @Override
    public Boat.Type getVariant() {
        return Boat.Type.OAK;
    }

}
