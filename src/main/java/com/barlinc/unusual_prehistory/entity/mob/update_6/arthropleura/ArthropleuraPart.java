package com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class ArthropleuraPart extends Entity {

    private static final EntityDataAccessor<Optional<UUID>> HEAD_ENTITY_UUID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> HEAD_ENTITY_ID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> FRONT_ENTITY_UUID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> FRONT_ENTITY_ID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> BACK_ENTITY_UUID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> BACK_ENTITY_ID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);

    public boolean renderHurtFlag = false;

    private int lSteps;
    private double lx;
    private double ly;
    private double lz;
    private double lyr;
    private double lxr;
    private double lxd;
    private double lyd;
    private double lzd;

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState walkAnimationState = new SmoothAnimationState();

    public ArthropleuraPart(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isPickable() {
        Entity head = this.getHeadEntity();
        return head != null && head.isPickable();
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(UP2Items.ARTHROPLEURA_SPAWN_EGG.get());
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        Entity head = this.getHeadEntity();
        if (!this.isInvulnerableTo(source) && head != null) {
            head.hurt(source, amount);
        }
        return false;
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FALL);
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return super.canCollideWith(entity) && !(entity instanceof ArthropleuraPart) && !(entity instanceof Arthropleura);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public boolean isControlledByLocalInstance() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        Entity head = this.getHeadEntity();
        Entity front = this.getFrontEntity();
        Entity back = this.getBackEntity();

        if (this.level().isClientSide) {
            this.setupAnimationStates();
            if (head instanceof Arthropleura arthropleura) {
                this.renderHurtFlag = arthropleura.hurtTime > 0 || arthropleura.deathTime > 0;
            }
            if (this.lSteps > 0) {
                double x = this.getX() + (this.lx - this.getX()) / (double) this.lSteps;
                double y = this.getY() + (this.ly - this.getY()) / (double) this.lSteps;
                double z = this.getZ() + (this.lz - this.getZ()) / (double) this.lSteps;
                double lerpRot = Mth.wrapDegrees(this.lyr - (double) this.getYRot());
                this.setYRot(this.getYRot() + (float) lerpRot / (float) this.lSteps);
                this.setXRot(this.getXRot() + (float) (this.lxr - (double) this.getXRot()) / (float) this.lSteps);
                this.lSteps--;
                this.setPos(x, y, z);
            } else {
                this.reapplyPosition();
            }
        }
        else {
            this.entityData.set(HEAD_ENTITY_ID, head != null ? head.getId() : -1);
            this.entityData.set(FRONT_ENTITY_ID, front != null ? front.getId() : -1);
            this.entityData.set(BACK_ENTITY_ID, back != null ? back.getId() : -1);
            if (front == null || head == null) {
                if (tickCount > 3) {
                    this.discard();
                }
            } else {
                this.tickPartPosition(head, front, back);
                if (this.isHeadMoving()) {
                    this.tickPartRotation(front, Math.max(0.07F, 0.15F - (this.getIndex() * 0.01F)));
                }
            }
        }
    }

    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isHeadMoving(), this.tickCount);
        this.walkAnimationState.animateWhen(this.isHeadMoving(), this.tickCount);
    }

    public Vec3 getIdealPosition(@Nullable Entity parent) {
        Entity front = parent == null ? this.getFrontEntity() : parent;
        if (front != null) {
            float zOffset = 1.41F;
            if (this.getBackEntity() == null) {
                zOffset = 1.21F;
            }
            Vec3 offsetFromParent = new Vec3(0.0F, 0.0F, -zOffset).xRot(-(float) Math.toRadians(front.getXRot())).yRot(-(float) Math.toRadians(front.getYRot()));
            return front.position().add(offsetFromParent);
        } else {
            return this.position();
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void tickPartPosition(Entity head, Entity front, Entity back) {
        Vec3 distVec = this.getIdealPosition(front).subtract(this.position());
        float extraLength = (float) Math.max(distVec.length() - 0.5F, 0.0F);
        Vec3 distance = distVec.length() > 1.0F ? distVec.normalize().scale(1.0F + extraLength) : distVec;
        Vec3 nextPos = this.position().add(distance.multiply(0.8F, 1.0F, 0.8F));
        double y = this.calculateSurfaceY(nextPos, front, back);
        if (head.isInFluidType()) {
            this.setPos(nextPos.x, nextPos.y, nextPos.z);
        } else {
            this.setPos(nextPos.x, y, nextPos.z);
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    @SuppressWarnings("SameParameterValue")
    private void tickPartRotation(Entity front, float speed) {
        Vec3 frontsBack = front.position().add(new Vec3(0F, 0F, 0.8F).xRot(-(float) Math.toRadians(front.getXRot())).yRot(-(float) Math.toRadians(front.getYRot())));
        double xDirection = frontsBack.x - this.getX();
        double yDirection = frontsBack.y - this.getY();
        double zDirection = frontsBack.z - this.getZ();
        double magnitude = Math.sqrt(xDirection * xDirection + zDirection * zDirection);
        float xRot = Mth.wrapDegrees((float) (-(Mth.atan2(yDirection, magnitude) * (double) (180.0F / (float) Math.PI))));
        float yRot = Mth.wrapDegrees((float) (Mth.atan2(zDirection, xDirection) * (double) (180.0F / (float) Math.PI)) - 90.0F);
        float yDelta = Mth.wrapDegrees(yRot - this.getYRot());
        float xDelta = Mth.wrapDegrees(xRot - this.getXRot());
        this.setXRot(this.getXRot() + xDelta * speed);
        this.setYRot(this.getYRot() + yDelta * speed);
    }

    private boolean isHeadMoving() {
        Entity head = this.getHeadEntity();
        if (head == null) {
            return false;
        }
        return head.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }

    private double calculateSurfaceY(Vec3 pos, Entity front, Entity back) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        int posX = Mth.floor(pos.x);
        int posZ = Mth.floor(pos.z);
        int posY = Mth.floor(pos.y);

        mutableBlockPos.set(posX, posY, posZ);
        double surfaceY = pos.y;
        for (int y = posY; y > this.level().getMinBuildHeight(); y--) {
            mutableBlockPos.set(posX, y, posZ);
            if (!this.level().getBlockState(mutableBlockPos).getCollisionShape(this.level(), mutableBlockPos).isEmpty()) {
                surfaceY = y + 1.0D;
                break;
            }
        }

        double frontY = front.getY();
        double backY = back != null ? back.getY() : frontY;
        double average = (frontY + backY) * 0.5D;
        double targetY = Mth.lerp(0.5D, average, surfaceY);
        double minY = Math.max(frontY - 1.0D, backY - 1.0D);
        double maxY = Math.min(frontY + 1.0D, backY + 1.0D);
        return Mth.clamp(targetY, minY, maxY);
    }

    public static void createArthropleuraSegments(Arthropleura arthropleura, int count) {
        ArthropleuraPart prev = null;
        for (int i = 0; i < count; i++) {
            ArthropleuraPart current = new ArthropleuraPart(UP2Entities.ARTHROPLEURA_PART.get(), arthropleura.level());
            current.setHeadUUID(arthropleura.getUUID());
            current.setFrontEntityUUID(prev == null ? arthropleura.getUUID() : prev.getUUID());
            if (prev != null) {
                prev.setBackEntityUUID(current.getUUID());
            }
            current.setIndex(i);
            current.setPos(current.getIdealPosition(prev == null ? arthropleura : prev));
            current.setYRot(arthropleura.getYRot());
            arthropleura.level().addFreshEntity(current);
            prev = current;
        }
    }

    @Override
    public @NotNull Vec3 getLightProbePosition(float partialTicks) {
        if (this.getHeadEntity() != null) {
            return this.getHeadEntity().getEyePosition(partialTicks);
        }
        return super.getLightProbePosition(partialTicks);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void push(@NotNull Entity entity) {
        if (!(entity instanceof ArthropleuraPart)) {
            super.push(entity);
        }
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return super.getBoundingBoxForCulling().inflate(2);
    }

    @Override
    public boolean shouldBeSaved() {
        return (this.getRemovalReason() == null || this.getRemovalReason().shouldSave()) && !this.isPassenger();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(HEAD_ENTITY_UUID, Optional.empty());
        builder.define(HEAD_ENTITY_ID, -1);
        builder.define(FRONT_ENTITY_UUID, Optional.empty());
        builder.define(FRONT_ENTITY_ID, -1);
        builder.define(BACK_ENTITY_UUID, Optional.empty());
        builder.define(BACK_ENTITY_ID, -1);
        builder.define(INDEX, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.hasUUID("HeadUUID")) {
            this.setHeadUUID(compoundTag.getUUID("HeadUUID"));
        }
        if (compoundTag.hasUUID("FrontUUID")) {
            this.setFrontEntityUUID(compoundTag.getUUID("FrontUUID"));
        }
        if (compoundTag.hasUUID("BackUUID")) {
            this.setBackEntityUUID(compoundTag.getUUID("BackUUID"));
        }
        this.setIndex(compoundTag.getInt("Index"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        if (this.getHeadUUID() != null) {
            compoundTag.putUUID("HeadUUID", this.getHeadUUID());
        }
        if (this.getFrontEntityUUID() != null) {
            compoundTag.putUUID("FrontUUID", this.getFrontEntityUUID());
        }
        if (this.getBackEntityUUID() != null) {
            compoundTag.putUUID("BackUUID", this.getBackEntityUUID());
        }
        compoundTag.putInt("Index", this.getIndex());
    }

    @Nullable
    public UUID getBackEntityUUID() {
        return this.entityData.get(BACK_ENTITY_UUID).orElse(null);
    }
    public void setBackEntityUUID(@Nullable UUID uniqueId) {
        this.entityData.set(BACK_ENTITY_UUID, Optional.ofNullable(uniqueId));
    }

    @Nullable
    public UUID getHeadUUID() {
        return this.entityData.get(HEAD_ENTITY_UUID).orElse(null);
    }
    public void setHeadUUID(@Nullable UUID uniqueId) {
        this.entityData.set(HEAD_ENTITY_UUID, Optional.ofNullable(uniqueId));
    }

    @Nullable
    public UUID getFrontEntityUUID() {
        return this.entityData.get(FRONT_ENTITY_UUID).orElse(null);
    }
    public void setFrontEntityUUID(@Nullable UUID uniqueId) {
        this.entityData.set(FRONT_ENTITY_UUID, Optional.ofNullable(uniqueId));
    }

    public Entity getHeadEntity() {
        if (!this.level().isClientSide) {
            UUID id = getHeadUUID();
            return id == null ? null : ((ServerLevel) this.level()).getEntity(id);
        } else {
            int id = this.entityData.get(HEAD_ENTITY_ID);
            return id == -1 ? null : this.level().getEntity(id);
        }
    }
    public Entity getFrontEntity() {
        if (!this.level().isClientSide) {
            UUID id = getFrontEntityUUID();
            return id == null ? null : ((ServerLevel) this.level()).getEntity(id);
        } else {
            int id = this.entityData.get(FRONT_ENTITY_ID);
            return id == -1 ? null : this.level().getEntity(id);
        }
    }
    public Entity getBackEntity() {
        if (!this.level().isClientSide) {
            UUID id = getBackEntityUUID();
            return id == null ? null : ((ServerLevel) this.level()).getEntity(id);
        } else {
            int id = this.entityData.get(BACK_ENTITY_ID);
            return id == -1 ? null : this.level().getEntity(id);
        }
    }

    public int getIndex() {
        return this.entityData.get(INDEX);
    }
    public void setIndex(int i) {
        this.entityData.set(INDEX, i);
    }

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
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.ARTHROPLEURA_STEP.get(), 0.15F, 1.0F);
    }
}