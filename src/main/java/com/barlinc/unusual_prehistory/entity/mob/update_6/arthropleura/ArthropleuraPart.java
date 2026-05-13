package com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ArthropleuraPart extends PrehistoricMob {

    public float segmentLength = 1.5F;
    public List<ArthropleuraBody> behindSegments;
    public ArthropleuraPart ahead;
    public ArthropleuraBody behind;
    int airTime;
    private static final EntityDataAccessor<Integer> SEGMENT_ID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ORIGINAL_SEGMENT_ID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BODY_LENGTH = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ORIGINAL_BODY_LENGTH = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> X_BODY_ROT = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Y_BODY_ROT = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.FLOAT);
    private float bodyRotX;
    private float bodyRotY;

    protected ArthropleuraPart(EntityType<? extends ArthropleuraPart> entity, Level level) {
        super(entity, level);
        this.behindSegments = new ArrayList<>();
        this.airTime = 0;
    }

    @Override
    protected void registerGoals() {
    }

    @Override
    public void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(X_BODY_ROT, 0.0F);
        builder.define(Y_BODY_ROT, 0.0F);
        builder.define(SEGMENT_ID, 0);
        builder.define(ORIGINAL_SEGMENT_ID, 0);
        builder.define(BODY_LENGTH, 0);
        builder.define(ORIGINAL_BODY_LENGTH, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SegmentID", this.getSegmentID());
        compoundTag.putInt("OriginalSegmentID", this.getOriginalSegmentID());
        compoundTag.putInt("BodyLength", this.getBodyLength());
        compoundTag.putInt("OriginalBodyLength", this.getOriginalBodyLength());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSegmentID(compoundTag.getInt("SegmentID"));
        this.setOriginalSegmentID(compoundTag.getInt("OriginalSegmentID"));
        this.setBodyLength(compoundTag.getInt("BodyLength"));
        this.setOriginalBodyLength(compoundTag.getInt("OriginalBodyLength"));
    }

    public void setSegmentID(int id) {
        this.entityData.set(SEGMENT_ID, id);
    }
    public int getSegmentID() {
        return this.entityData.get(SEGMENT_ID);
    }
    public void setOriginalSegmentID(int id) {
        this.entityData.set(ORIGINAL_SEGMENT_ID, id);
    }
    public int getOriginalSegmentID() {
        return this.entityData.get(ORIGINAL_SEGMENT_ID);
    }

    public void setBodyLength(int bodyLength) {
        this.entityData.set(BODY_LENGTH, bodyLength);
    }
    public int getBodyLength() {
        return this.entityData.get(BODY_LENGTH);
    }
    public int getOriginalBodyLength() {
        return this.entityData.get(ORIGINAL_BODY_LENGTH);
    }
    public void setOriginalBodyLength(int bodyLength) {
        this.entityData.set(ORIGINAL_BODY_LENGTH, bodyLength);
    }

    public float getBodyRotX(float partialTick) {
        return Mth.rotLerp(partialTick, bodyRotX, this.entityData.get(X_BODY_ROT));
    }
    public void setBodyRotX(float rotation) {
        this.entityData.set(X_BODY_ROT, rotation);
    }

    public float getBodyRotY(float partialTick) {
        return Mth.rotLerp(partialTick, bodyRotY, this.entityData.get(Y_BODY_ROT));
    }
    public void setBodyRotY(float rotation) {
        this.entityData.set(Y_BODY_ROT, rotation);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.bodyRotX = this.entityData.get(X_BODY_ROT);
        this.bodyRotY = this.entityData.get(Y_BODY_ROT);
        if (!this.onGround() && !this.onClimbable()) {
            this.airTime++;
        } else {
            this.airTime = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            Vec3 dir1 = Vec3.ZERO;
            float count = 0.0F;
            if (ahead != null) {
                dir1 = this.position().subtract(ahead.position()).reverse();
                count++;
            }
            Vec3 dir2 = Vec3.ZERO;
            if (behind != null) {
                dir2 = this.position().subtract(behind.position());
                count++;
            }
            Vec3 average = dir1.add(dir2).scale(1.0F / Math.max(count, 1));
            this.face(this.position().add(average));
        }
    }

    public void applyRopeConstraints(int iterations) {
        if (behindSegments == null || behindSegments.isEmpty()) {
            return;
        }
        Vec3[] desiredPositions = new Vec3[behindSegments.size() + 1];
        desiredPositions[0] = this.position();
        for (int i = 0; i < behindSegments.size(); i++) {
            desiredPositions[i + 1] = behindSegments.get(i).position();
        }
        for (int iteration = 0; iteration < iterations; iteration++) {
            desiredPositions[0] = this.position();
            for (int i = 0; i < behindSegments.size(); i++) {
                Vec3 parent = desiredPositions[i];
                Vec3 child = desiredPositions[i + 1];
                Vec3 delta = child.subtract(parent);
                double dist = delta.length();
                if (dist < 1.0E-6) {
                    continue;
                }
                Vec3 correction = delta.normalize().scale(dist - segmentLength);
                desiredPositions[i + 1] = child.subtract(correction);
            }
        }
        for (int i = 0; i < behindSegments.size(); i++) {
            ArthropleuraBody segment = behindSegments.get(i);
            Vec3 move = desiredPositions[i + 1].subtract(segment.position());
            segment.moveWithoutClamping(MoverType.SELF, move);
        }
    }

    public ArthropleuraPart getSegmentFromIndex(int segmentIndex) {
        if (segmentIndex < -1 || segmentIndex > behindSegments.size() - 1) {
            return null;
        }
        return segmentIndex == -1 ? this : behindSegments.get(segmentIndex);
    }

    public void face(Vec3 position) {
        if (!this.level().isClientSide) {
            double xDirection = position.x() - this.getX();
            double yDirection = position.y() - this.getY();
            double zDirection = position.z() - this.getZ();
            double magnitude = Math.sqrt(xDirection * xDirection + zDirection * zDirection);
            double yaw = (Mth.atan2(zDirection, xDirection) * (180F / Math.PI)) - 90.0F;
            double pitch = -(Mth.atan2(yDirection, magnitude) * (180F / Math.PI));
            float speed = 0.8F;
            float currentYaw = this.getBodyRotY(0);
            float currentPitch = this.getBodyRotX(0);
            float yawDelta = Mth.wrapDegrees((float) yaw - currentYaw);
            float pitchDelta = (float) pitch - currentPitch;
            this.setBodyRotX(currentPitch + pitchDelta * speed);
            this.setXRot(currentPitch + pitchDelta * speed);
            this.setYBodyRot(currentYaw + yawDelta * speed);
            this.setBodyRotY(currentYaw + yawDelta * speed);
        }
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        if (entity instanceof ArthropleuraPart) {
            return false;
        } else {
            return super.canCollideWith(entity);
        }
    }

    @Override
    public void push(@NotNull Entity entity) {
        if (!(entity instanceof ArthropleuraPart)) {
            super.push(entity);
        } else {
            if (this.position().distanceToSqr(entity.position()) < segmentLength * 0.5F) {
                super.push(entity);
            }
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        super.travel(travelVector);
        if (this.isEffectiveAi() && ((airTime < 10) || this.isInWater() || this.isNoGravity())) {
            this.moveRelative(0.08F, travelVector);
        }
    }

    @Override
    public void move(@NotNull MoverType moverType, @NotNull Vec3 pos) {
        if (!this.isDeadOrDying()) {
            double segLengthSqr = (this.segmentLength * this.segmentLength);
            Vec3 resultingPos = this.position().add(0.0, pos.y(), 0.0);
            Vec3 adjustedPos = pos;
            if (behind != null) {
                double distToPrevSegmentSqr = resultingPos.distanceToSqr(behind.position());
                if (distToPrevSegmentSqr > segLengthSqr) {
                    double distToPrevSegment = Math.sqrt(distToPrevSegmentSqr);
                    double difference = distToPrevSegment - segmentLength;
                    adjustedPos = pos.normalize().scale(pos.length() - difference);
                }
            } else if (ahead != null) {
                Vec3 falseBehind = this.position().add(this.position().subtract(ahead.position()).reverse());
                double distToNextSegmentSqr = resultingPos.distanceToSqr(falseBehind);
                if (distToNextSegmentSqr > segLengthSqr) {
                    double distToNextSegment = Math.sqrt(distToNextSegmentSqr);
                    double difference = distToNextSegment - segmentLength;
                    adjustedPos = pos.normalize().scale(pos.length() - difference);
                }
            }
            super.move(moverType, new Vec3(pos.x, adjustedPos.y, pos.z));
        } else {
            super.move(moverType, pos);
        }
    }

    public void moveWithoutClamping(MoverType moverType, Vec3 pos) {
        super.move(moverType, pos);
    }
}