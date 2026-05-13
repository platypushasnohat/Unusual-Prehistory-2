package com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class ArthropleuraPart extends Entity {

    private static final EntityDataAccessor<Optional<UUID>> CHILD_UUID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Optional<UUID>> PARENT_UUID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> PART_INDEX = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PART_TYPE = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);

    public boolean renderHurtFlag = false;

    private double prevHeight = 0;

    private int lSteps;
    private double lx;
    private double ly;
    private double lz;
    private double lyr;
    private double lxr;
    private double lxd;
    private double lyd;
    private double lzd;

    public ArthropleuraPart(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    public ArthropleuraPart(EntityType<? extends Entity> entityType, LivingEntity parent) {
        super(entityType, parent.level());
        this.setParent(parent);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.IN_WALL)  || super.isInvulnerableTo(source);
    }

    @Override
    public boolean canUsePortal(boolean allowVehicles) {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return false;
    }

//    @Override
//    public boolean isPickable() {
//        Entity parent = this.getParent();
//        return parent != null && parent.isPickable();
//    }
//
//    @Nullable
//    @Override
//    public ItemStack getPickResult() {
//        SpawnEggItem item = SpawnEggItem.byId(this.getParent().getType());
//        if (this.getParent() != null) {
//            return item == null ? null : new ItemStack(item);
//        } else {
//            return ItemStack.EMPTY;
//        }
//    }
//
//    @Override
//    public boolean hurt(@NotNull DamageSource source, float amount) {
//        Entity parent = this.getParent();
//        if (!this.isInvulnerableTo(source) && parent != null) {
//            parent.hurt(source, amount);
//        }
//        return false;
//    }

    @Override
    public void lerpTo(double x, double y, double z, float yr, float xr, int steps) {
        this.lx = x;
        this.ly = y;
        this.lz = z;
        this.lyr = yr;
        this.lxr = xr;
        this.lSteps = steps;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    @Override
    public void lerpMotion(double lerpX, double lerpY, double lerpZ) {
        this.lxd = lerpX;
        this.lyd = lerpY;
        this.lzd = lerpZ;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    @Override
    public void tick() {
        super.tick();
        this.setDeltaMovement(Vec3.ZERO);
        Entity parent = this.getParent();
        if (this.tickCount > 1) {
            this.refreshDimensions();
        }
        if (this.level().isClientSide) {
            if (parent instanceof Arthropleura arthropleura) {
                this.renderHurtFlag = arthropleura.hurtTime > 0 || arthropleura.deathTime > 0;
            }
            if (this.lSteps > 0) {
                double d5 = this.getX() + (this.lx - this.getX()) / (double) this.lSteps;
                double d6 = this.getY() + (this.ly - this.getY()) / (double) this.lSteps;
                double d7 = this.getZ() + (this.lz - this.getZ()) / (double) this.lSteps;
                double lerpRot = Mth.wrapDegrees(this.lyr - (double) this.getYRot());
                this.setYRot(this.getYRot() + (float) lerpRot / (float) this.lSteps);
                this.setXRot(this.getXRot() + (float) (this.lxr - (double) this.getXRot()) / (float) this.lSteps);
                this.lSteps--;
                this.setPos(d5, d6, d7);
            } else {
                this.reapplyPosition();
            }
        }
    }

    public Vec3 tickMultipartPosition(ArthropleuraPartIndex parentIndex, Vec3 parentPosition, float parentXRot, float yawForPart, boolean doHeight) {
        Vec3 parentButt = parentPosition.add(this.calcOffsetVec(-parentIndex.getBackOffset(), parentXRot, yawForPart));
        Vec3 ourButt = parentButt.add(this.calcOffsetVec((-this.getPartType().getBackOffset() - 0.5F * this.getBbWidth()), this.getXRot(), yawForPart));
        Vec3 avg = new Vec3((parentButt.x + ourButt.x) / 2.0F, (parentButt.y + ourButt.y) / 2.0F, (parentButt.z + ourButt.z) / 2.0F);
        double posX = parentButt.x - ourButt.x;
        double posZ = parentButt.z - ourButt.z;
        double sqrt = Math.sqrt(posX * posX + posZ * posZ);
        double height = doHeight ? (this.getLowPartHeight(parentButt.x, parentButt.y, parentButt.z) + this.getHighPartHeight(ourButt.x, ourButt.y, ourButt.z)) : 0.0D;
        if (Math.abs(height - prevHeight) > 0.2F) {
            this.prevHeight = height;
        }
        double partY = Mth.clamp(prevHeight, -1.0F, 1.0F);
        float yRot = (float) (Mth.atan2(posZ, posX) * 57.2957763671875D) - 90.0F;
        float rawAngle = Mth.wrapDegrees((float) (-(Mth.atan2(partY, sqrt) * Mth.RAD_TO_DEG)));
        float xRot = this.limitAngle(this.getXRot(), rawAngle, 10.0F);
        this.setXRot(xRot);
        this.setYRot(yRot);
        this.moveTo(avg.x, avg.y, avg.z, yRot, xRot);
        return avg;
    }

    public Vec3 calcOffsetVec(float offsetZ, float xRot, float yRot){
        return new Vec3(0, 0, offsetZ).xRot(xRot * Mth.DEG_TO_RAD).yRot(-yRot * Mth.DEG_TO_RAD);
    }

    public float limitAngle(float sourceAngle, float targetAngle, float maximumChange) {
        float f = Mth.wrapDegrees(targetAngle - sourceAngle);
        if (f > maximumChange) {
            f = maximumChange;
        }
        if (f < -maximumChange) {
            f = -maximumChange;
        }
        float f1 = sourceAngle + f;
        if (f1 < 0.0F) {
            f1 += 360.0F;
        } else if (f1 > 360.0F) {
            f1 -= 360.0F;
        }
        return f1;
    }

    public double getLowPartHeight(double x, double y, double z) {
        if (this.isFluidAt(x, y, z)) {
            return 0.0D;
        }
        double checkAt = 0.0D;
        while (checkAt > -3.0D && !this.isOpaqueBlockAt(x,y + checkAt, z)) {
            checkAt -= 0.2D;
        }
        return checkAt;
    }

    public double getHighPartHeight(double x, double y, double z) {
        if (this.isFluidAt(x, y, z)) {
            return 0.0D;
        }
        double checkAt = 0.0D;
        while (checkAt <= 3.0D) {
            if (this.isOpaqueBlockAt(x, y + checkAt, z)) {
                checkAt += 0.2D;
            } else {
                break;
            }
        }
        return checkAt;
    }

    public boolean isOpaqueBlockAt(double x, double y, double z) {
        if (this.noPhysics) {
            return false;
        } else {
            final double d = 1D;
            final Vec3 vec3 = new Vec3(x, y, z);
            final AABB axisAlignedBB = AABB.ofSize(vec3, d, 1.0E-6D, d);
            return this.level().getBlockStates(axisAlignedBB).filter(Predicate.not(BlockBehaviour.BlockStateBase::isAir)).anyMatch((state) -> {
                BlockPos blockpos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
                return state.isSuffocating(this.level(), blockpos) && Shapes.joinIsNotEmpty(state.getCollisionShape(this.level(), blockpos).move(vec3.x, vec3.y, vec3.z), Shapes.create(axisAlignedBB), BooleanOp.AND);
            });
        }
    }

    public boolean isFluidAt(double x, double y, double z) {
        if (this.noPhysics) {
            return false;
        } else {
            return !level().getFluidState(this.fromCoords(x, y, z)).isEmpty();
        }
    }

    public BlockPos fromCoords(double x, double y, double z){
        return new BlockPos((int) x, (int) y, (int) z);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(CHILD_UUID, Optional.empty());
        builder.define(PARENT_UUID, Optional.empty());
        builder.define(PART_INDEX, 0);
        builder.define(PART_TYPE, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        if (this.getParentId() != null) {
            compoundTag.putUUID("ParentUUID", this.getParentId());
        }
        if (this.getChildId() != null) {
            compoundTag.putUUID("ChildUUID", this.getChildId());
        }
        compoundTag.putInt("PartIndex", this.getIndex());
        compoundTag.putInt("PartType", getPartType().ordinal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.hasUUID("ParentUUID")) {
            this.setParentId(compoundTag.getUUID("ParentUUID"));
        }
        if (compoundTag.hasUUID("ChildUUID")) {
            this.setChildId(compoundTag.getUUID("ChildUUID"));
        }
        this.setIndex(compoundTag.getInt("PartIndex"));
        this.setPartType(ArthropleuraPartIndex.fromOrdinal(compoundTag.getInt("PartType")));
    }

    public Entity getParent() {
        if (!this.level().isClientSide) {
            final UUID id = this.getParentId();
            if (id != null) {
                return ((ServerLevel) this.level()).getEntity(id);
            }
        }
        return null;
    }

    public void setParent(Entity entity) {
        this.setParentId(entity.getUUID());
    }

    @Nullable
    public UUID getParentId() {
        return this.entityData.get(PARENT_UUID).orElse(null);
    }
    public void setParentId(@Nullable UUID uniqueId) {
        this.entityData.set(PARENT_UUID, Optional.ofNullable(uniqueId));
    }

    public Entity getChild() {
        if (!this.level().isClientSide) {
            final UUID id = getChildId();
            if (id != null) {
                return ((ServerLevel) level()).getEntity(id);
            }
        }
        return null;
    }

    @Nullable
    public UUID getChildId() {
        return this.entityData.get(CHILD_UUID).orElse(null);
    }
    public void setChildId(@Nullable UUID uniqueId) {
        this.entityData.set(CHILD_UUID, Optional.ofNullable(uniqueId));
    }

    public int getIndex() {
        return this.entityData.get(PART_INDEX);
    }
    public void setIndex(int index) {
        this.entityData.set(PART_INDEX, index);
    }

    public ArthropleuraPartIndex getPartType() {
        return ArthropleuraPartIndex.fromOrdinal(this.entityData.get(PART_TYPE));
    }

    public void setPartType(ArthropleuraPartIndex index) {
        this.entityData.set(PART_TYPE, index.ordinal());
    }
}