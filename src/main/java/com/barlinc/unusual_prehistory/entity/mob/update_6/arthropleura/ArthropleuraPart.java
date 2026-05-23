package com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
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
    private boolean wasPreviouslyBaby;

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
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        InteractionResult result = super.interact(player, hand);
        ItemStack itemStack = player.getItemInHand(hand);
        if (this.getBackEntity() != null && !(this.getHeadEntity() instanceof Arthropleura arthropleura && arthropleura.isBaby() && !arthropleura.isFood(itemStack))) {
            Entity leashed = this.getLeashed(player).orElse(null);
            if (leashed instanceof Mob mob) {
                mob.stopRiding();
                mob.setLeashedTo(player, true);
                mob.startRiding(this, true);
                return InteractionResult.SUCCESS;
            }
            if (!this.isVehicle()) {
                player.startRiding(this);
            } else {
                for (Entity passenger : this.getPassengers()) {
                    if (this.getControllingPassenger() != passenger) {
                        passenger.stopRiding();
                        if (!this.level().isClientSide) {
                            Arthropleura.yeetPassengers(passenger);
                        }
                    }
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            return result;
        }
    }

    public boolean canEntityRide(LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        if (entity.getType().is(UP2EntityTags.ARTHROPLEURA_CANT_CARRY)) {
            return false;
        }
        return (entity.getBbWidth() < this.getBbWidth() * 2.0F && entity.getBbHeight() < this.getBbHeight() * 4.0F) || entity.getType().is(UP2EntityTags.ARTHROPLEURA_CAN_CARRY);
    }

    public Optional<Entity> getLeashed(Player player) {
        List<Entity> entities = player.level().getEntities((Entity) null, player.getBoundingBox().inflate(10), entity -> true);
        for (Entity entity : entities) {
            if (entity instanceof Mob mob && mob.getLeashHolder() == player && this.canEntityRide(mob)) {
                return Optional.of(mob);
            }
        }
        return Optional.empty();
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        float scale = this.getHeadEntity() instanceof Arthropleura arthropleura ? arthropleura.getAgeScale() : 1.0F;
        return super.getDimensions(pose).scale(scale);
    }

    @Override
    public void tick() {
        super.tick();

        Entity head = this.getHeadEntity();
        Entity front = this.getFrontEntity();
        Entity back = this.getBackEntity();

        if (head instanceof Arthropleura arthropleura && arthropleura.isBaby() != wasPreviouslyBaby) {
            this.wasPreviouslyBaby = arthropleura.isBaby();
            this.refreshDimensions();
        }

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
        } else {
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
                    this.tickPartRotation(front, Math.max(0.1F, 0.18F - (this.getIndex() * 0.001F)));
                }
            }
        }
    }

    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    public Vec3 getIdealPosition(Entity head, Entity front) {
        float zOffset = 2.0F;
        float wiggle = 0.0F;
        if (this.isHeadMoving()) {
            wiggle = 0.1F * (float) Math.sin(head.tickCount * 0.3F - this.getIndex());
        }
        if (this.getBackEntity() == null) {
            zOffset -= 1.0F;
        }
        if (head instanceof Arthropleura arthropleura && arthropleura.isBaby()) {
            zOffset -= 0.7F;
        }
        Vec3 offsetFromParent = new Vec3(wiggle, 0.0F, -zOffset).xRot(-(float) Math.toRadians(front.getXRot())).yRot(-(float) Math.toRadians(front.getYRot()));
        return front.position().add(offsetFromParent);
    }

    private double smoothedGroundY;

    private void tickPartPosition(Entity head, Entity front, Entity back) {
        Vec3 ideal = this.getIdealPosition(head, front);

        if (!head.isInFluidType()) {
            double groundY = this.getGroundY(ideal.x, ideal.y, ideal.z);
            this.smoothedGroundY = Mth.lerp(0.15D, this.smoothedGroundY, groundY);
            double desiredY = this.smoothedGroundY + 0.15D;
            // Ignore unrealistic terrain jumps
            if (Math.abs(desiredY - front.getY()) < 2.0D) {
                double terrainDelta = desiredY - ideal.y;
                double terrainInfluence = terrainDelta < 0.0D ? 0.28D : 0.10D;
                ideal = new Vec3(ideal.x, ideal.y + terrainDelta * terrainInfluence, ideal.z);
            }
        }


        Vec3 delta = ideal.subtract(this.position());

        if (delta.lengthSqr() < 0.0025D) {
            return;
        }

        Vec3 finalPos = this.position().lerp(ideal, 0.25D);

        Vec3 move = finalPos.subtract(this.position());

        Vec3 horizontalMove = new Vec3(move.x, 0.0D, move.z);

        Vec3 testPos = this.position().add(horizontalMove);

        if (this.collidesWithBlocks(testPos)) {
            horizontalMove = Vec3.ZERO;
        }

        finalPos = this.position().add(horizontalMove.x, move.y, horizontalMove.z);
        finalPos = this.applySegmentConstraints(finalPos, front, head);

        double groundY = this.getGroundY(finalPos.x, finalPos.y, finalPos.z);
        double distanceToGround = finalPos.y - groundY;

        if (distanceToGround > 0.3D) {
            double sag = Math.min(0.12D, (distanceToGround - 0.3D) * 0.08D);
            finalPos = finalPos.subtract(0.0D, sag, 0.0D);
        }

        if (this.collidesWithBlocks(finalPos)) {
            finalPos = new Vec3(this.getX(), finalPos.y, this.getZ());
            finalPos = this.applySegmentConstraints(finalPos, front, head);
        }

        finalPos = this.pushOutOfBlocks(finalPos);

        this.setPos(finalPos.x, finalPos.y, finalPos.z);
    }

    private Vec3 applySegmentConstraints(Vec3 pos, Entity front, Entity head) {
        Vec3 offset = pos.subtract(front.position());

        Vec3 horizontal = new Vec3(offset.x, 0.0D, offset.z);

        double horizontalLength = horizontal.length();
        double desiredDistance = 1.1D;

        if (head instanceof Arthropleura arthropleura && arthropleura.isBaby()) {
            desiredDistance = 0.7D;
        }

        if (horizontalLength > 0.0001D) {
            double correctionStrength = 0.3D;
            double targetDistance = Mth.lerp(correctionStrength, horizontalLength, desiredDistance);
            horizontal = horizontal.normalize().scale(targetDistance);
        }

        double maxVerticalOffset = 0.3D;
        //Math.max(
        //        0.3D,
        //        0.45D - (this.getIndex() * 0.01D)
        //);
//
        double bodyInfluence = Math.min(0.08D, this.getIndex() * 0.01D);

        double bodyY = Mth.lerp(bodyInfluence, front.getY(), head.getY());
        double targetY = Mth.clamp(pos.y, bodyY - maxVerticalOffset, bodyY + maxVerticalOffset);
        double dy = targetY - front.getY();
        double supportY = this.getGroundY(pos.x, pos.y, pos.z);
        double distanceToGround = pos.y - supportY;
        // Unsupported segments sag downward
        if (distanceToGround > 0.4D) {
            double sagStrength = 0.22D;
            dy -= (distanceToGround - 0.4D) * sagStrength;
        }

        return new Vec3(front.getX() + horizontal.x, front.getY() + dy, front.getZ() + horizontal.z);
    }

    private Vec3 pushOutOfBlocks(Vec3 pos) {

        AABB aabb = this.getBoundingBox().move(pos.x - this.getX(), pos.y - this.getY(), pos.z - this.getZ());

        // Check nearby blocks
        BlockPos min = BlockPos.containing(aabb.minX, aabb.minY, aabb.minZ);
        BlockPos max = BlockPos.containing(aabb.maxX, aabb.maxY, aabb.maxZ);

        double pushY = 0.0D;

        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
                for (int z = min.getZ(); z <= max.getZ(); z++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    BlockState state = this.level().getBlockState(blockPos);
                    if (state.isAir()) {
                        continue;
                    }
                    VoxelShape shape = state.getCollisionShape(this.level(), blockPos);
                    if (shape.isEmpty()) {
                        continue;
                    }
                    AABB blockBox = shape.bounds().move(blockPos);
                    if (aabb.intersects(blockBox)) {
                        // Push upward above intersecting block
                        double penetration = blockBox.maxY - aabb.minY;

                        // Only allow tiny upward corrections
                        if (penetration < 0.7D) {
                            pushY = Math.max(pushY, penetration);
                        }
                    }
                }
            }
        }

        if (pushY > 0.0D) {
            pushY = Math.min(pushY, 1D);
            pos = pos.add(0.0D, pushY + 0.01D, 0.0D);
        }

        return pos;
    }

    private boolean collidesWithBlocks(Vec3 pos) {

        AABB box = this.getBoundingBox().move(pos.x - this.getX(), pos.y - this.getY(), pos.z - this.getZ());

        BlockPos min = BlockPos.containing(box.minX, box.minY, box.minZ);

        BlockPos max = BlockPos.containing(box.maxX, box.maxY, box.maxZ);

        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
                for (int z = min.getZ(); z <= max.getZ(); z++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    BlockState state = this.level().getBlockState(blockPos);

                    if (state.isAir()) {
                        continue;
                    }

                    // Ignore leaves
//                    if (state.is(BlockTags.LEAVES)) {
//                        continue;
//                    }

                    VoxelShape shape = state.getCollisionShape(this.level(), blockPos);

                    if (shape.isEmpty()) {
                        continue;
                    }

                    AABB blockBox = shape.bounds().move(blockPos);
                    double height = blockBox.maxY - blockBox.minY;

                    double width = blockBox.maxX - blockBox.minX;

                    double depth = blockBox.maxZ - blockBox.minZ;

                    // Ignore tall narrow obstacles
                    if (height > width * 1.5D && height > depth * 1.5D) {
                        continue;
                    }
                    if (box.intersects(blockBox)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private double getGroundY(double x, double startY, double z) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (double y = startY + 2.0D; y > startY - 4.0D; y -= 0.1D) {

            pos.set(x, y, z);

            BlockState state = this.level().getBlockState(pos);

            if (state.isAir()) {
                continue;
            }

            VoxelShape shape = state.getCollisionShape(this.level(), pos);

            if (shape.isEmpty()) {
                continue;
            }

            AABB box = shape.bounds();

            double width = box.maxX - box.minX;
            double depth = box.maxZ - box.minZ;
//            double height = box.maxY - box.minY;

            boolean floorLike = width > 0.6D && depth > 0.6D;
            if (!floorLike) {
                continue;
            }
            return pos.getY() + box.maxY;
        }
        return startY;
    }


    private void tickPartRotation(Entity front, float speed) {
        Vec3 direction = front.position().subtract(this.position());
//        Vec3 direction = front.position().add(new Vec3(0.0F, 0.0F, 1.0F).xRot(-(float) Math.toRadians(front.getXRot())).yRot(-(float) Math.toRadians(front.getYRot())));

        double xDirection = direction.x;
        double yDirection = direction.y;
        double zDirection = direction.z;
        double magnitude = Math.sqrt(xDirection * xDirection + zDirection * zDirection);
        float xRot = Mth.wrapDegrees((float) (-(Mth.atan2(yDirection, magnitude) * (double) (180.0F / (float) Math.PI))));
        float yRot = Mth.wrapDegrees((float) (Mth.atan2(zDirection, xDirection) * (double) (180.0F / (float) Math.PI)) - 90.0F);
        float yDelta = Mth.clamp(Mth.wrapDegrees(yRot - this.getYRot()), -45.0F, 45.0F);
        float xDelta = Mth.wrapDegrees(xRot - this.getXRot());
        this.setXRot(this.getXRot() + xDelta);
        this.setYRot(this.getYRot() + yDelta * speed);
    }

    private boolean isHeadMoving() {
        Entity head = this.getHeadEntity();
        if (head == null) {
            return false;
        }
        return head.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
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
            current.setPos(current.getIdealPosition(arthropleura, prev == null ? arthropleura : prev));
            current.setYRot(arthropleura.getYRot());
            arthropleura.setSegments(arthropleura.getSegments() + 1);
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
            UUID id = this.getHeadUUID();
            return id == null ? null : ((ServerLevel) this.level()).getEntity(id);
        } else {
            int id = this.entityData.get(HEAD_ENTITY_ID);
            return id == -1 ? null : this.level().getEntity(id);
        }
    }

    public Entity getFrontEntity() {
        if (!this.level().isClientSide) {
            UUID id = this.getFrontEntityUUID();
            return id == null ? null : ((ServerLevel) this.level()).getEntity(id);
        } else {
            int id = this.entityData.get(FRONT_ENTITY_ID);
            return id == -1 ? null : this.level().getEntity(id);
        }
    }

    public Entity getBackEntity() {
        if (!this.level().isClientSide) {
            UUID id = this.getBackEntityUUID();
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