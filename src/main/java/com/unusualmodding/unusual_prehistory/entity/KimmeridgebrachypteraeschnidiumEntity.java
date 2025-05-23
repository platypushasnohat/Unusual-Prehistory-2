package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.base.AncientEntity;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.FlyingMoveController;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import com.unusualmodding.unusual_prehistory.registry.UP2Sounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class KimmeridgebrachypteraeschnidiumEntity extends AncientEntity implements Bucketable {

    @Nullable
    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> BASE_COLOR = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_COLOR = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_PATTERN = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> WING_COLOR = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> PREEN_COOLDOWN = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> PREEN_TIMER = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.BOOLEAN);

    public final float[] ringBuffer = new float[64];
    public float prevFlyProgress;
    public float flyProgress;
    public int ringBufferIndex = -1;
    private boolean isLandNavigator;
    private int timeFlying;

    public float currentRoll = 0.0F;
    public float prevTilt;
    public float tilt;

    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState preenAnimationState = new AnimationState();

    public KimmeridgebrachypteraeschnidiumEntity(EntityType<? extends AncientEntity> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
        switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0D).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new KimmeridgebrachypteraeschnidiumPreenGoal(this));
        this.goalSelector.addGoal(2, new KimmeridgebrachypteraeschnidiumFlightGoal(this));
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyingMoveController(this, 0.9f, true, true);
            this.navigation = new FlyingPathNavigation(this, level()) {
                public boolean isStableDestination(BlockPos pos) {
                    return !this.level.getBlockState(pos.below(2)).isAir();
                }
            };
            navigation.setCanFloat(false);
            this.isLandNavigator = false;
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.6F;
    }

    public void tick() {
        super.tick();

        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }

        this.prevFlyProgress = flyProgress;
        if (this.isFlying() && flyProgress < 5F) {
            flyProgress++;
        }
        if (!this.isFlying() && flyProgress > 0F) {
            flyProgress--;
        }
        if (this.ringBufferIndex < 0) {
            for (int i = 0; i < this.ringBuffer.length; ++i) {
                this.ringBuffer[i] = 15;
            }
        }
        this.ringBufferIndex++;
        if (this.ringBufferIndex == this.ringBuffer.length) {
            this.ringBufferIndex = 0;
        }
        if (!level().isClientSide) {
            if (isFlying() && this.isLandNavigator) {
                switchNavigator(false);
            }
            if (!isFlying() && !this.isLandNavigator) {
                switchNavigator(true);
            }
            if (this.isFlying()) {
                if (this.isFlying() && !this.onGround()) {
                    if (!this.isInWaterOrBubble()) {
                        this.setDeltaMovement(this.getDeltaMovement().multiply(1F, 0.6F, 1F));
                    }
                }
                if (this.onGround() && timeFlying > 20) {
                    this.setFlying(false);
                }
                this.timeFlying++;
            } else {
                this.timeFlying = 0;
            }
        }

        prevTilt = tilt;
        if (this.isFlying() && !this.onGround()) {
            final float v = Mth.degreesDifference(this.getYRot(), yRotO);
            if (Math.abs(v) > 1) {
                if (Math.abs(tilt) < 25) {
                    tilt -= Math.signum(v);
                }
            } else {
                if (Math.abs(tilt) > 0) {
                    final float tiltSign = Math.signum(tilt);
                    tilt -= tiltSign * 0.85F;
                    if (tilt * tiltSign < 0) {
                        tilt = 0;
                    }
                }
            }
        } else {
            tilt = 0;
        }

        float prevRoll = this.currentRoll;
        float targetRoll = Math.max(-0.45F, Math.min(0.45F, (this.getYRot() - this.yRotO) * 0.1F));
        targetRoll = -targetRoll;
        this.currentRoll = prevRoll + (targetRoll - prevRoll) * 0.05F;

        if (this.getPreenCooldown() > 0) {
            this.setPreenCooldown(this.getPreenCooldown() - 1);
        }
        if (this.getPreenTimer() > 0) {
            this.setPreenTimer(this.getPreenTimer() - 1);
            if (this.getPreenTimer() == 0) {
                this.preenCooldown();
            }
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlying(), this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying(), this.tickCount);
        this.preenAnimationState.animateWhen(this.getPreenCooldown() == 0, this.tickCount);
        if (this.getPreenTimer() > 0) {
            this.idleAnimationState.stop();
        }
    }

    // mob interactions
    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);

        if (heldItem.getItem() == Items.GLASS_BOTTLE && this.isAlive()) {

            playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 0.5F, 1.0F);
            heldItem.shrink(1);
            ItemStack itemstack1 = new ItemStack(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get());

            this.setBucketData(itemstack1);
            if (!this.level().isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemstack1);
            }
            if (heldItem.isEmpty() && !player.isCreative()) {
                player.setItemInHand(hand, itemstack1);
            } else if (!player.getInventory().add(itemstack1)) {
                player.drop(itemstack1, false);
            }
            this.discard();
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(FLYING, false);
        this.entityData.define(BASE_COLOR, 0);
        this.entityData.define(PATTERN, 0);
        this.entityData.define(PATTERN_COLOR, 0);
        this.entityData.define(HAS_PATTERN, false);
        this.entityData.define(WING_COLOR, 0);
        this.entityData.define(PREEN_COOLDOWN, 2 * 20 + random.nextInt(12 * 20));
        this.entityData.define(PREEN_TIMER, 0);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Flying", this.isFlying());
        compoundTag.putInt("BaseColor", this.getBaseColor());
        compoundTag.putInt("Pattern", this.getPattern());
        compoundTag.putInt("PatternColor", this.getPatternColor());
        compoundTag.putInt("WingColor", this.getWingColor());
        compoundTag.putBoolean("HasPattern", this.getHasPattern());
        compoundTag.putInt("PreenCooldown", this.getPreenCooldown());
        compoundTag.putInt("PreenTimer", this.getPreenTimer());
    }

    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setFlying(compoundTag.getBoolean("Flying"));
        this.setBaseColor(compoundTag.getInt("BaseColor"));
        this.setPattern(compoundTag.getInt("Pattern"));
        this.setPatternColor(compoundTag.getInt("PatternColor"));
        this.setWingColor(compoundTag.getInt("WingColor"));
        this.setHasPattern(compoundTag.getBoolean("HasPattern"));
        this.setPreenCooldown(compoundTag.getInt("PreenCooldown"));
        this.setPreenTimer(compoundTag.getInt("PreenTimer"));
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundTag = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);

        compoundTag.putFloat("Health", this.getHealth());
        compoundTag.putInt("BaseColor", this.getBaseColor());
        compoundTag.putInt("Pattern", this.getPattern());
        compoundTag.putInt("PatternColor", this.getPatternColor());
        compoundTag.putInt("WingColor", this.getWingColor());
        compoundTag.putBoolean("HasPattern", this.getHasPattern());
        compoundTag.putInt("Age", this.getAge());

        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    private void setBucketData(ItemStack bucket) {
        CompoundTag compoundTag = bucket.getOrCreateTag();
        compoundTag.putFloat("Health", this.getHealth());
        compoundTag.putInt("BaseColor", this.getBaseColor());
        compoundTag.putInt("Pattern", this.getPattern());
        compoundTag.putInt("PatternColor", this.getPatternColor());
        compoundTag.putBoolean("HasPattern", this.getHasPattern());
        compoundTag.putInt("WingColor", this.getWingColor());

        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    public static String getPatternName(int pattern) {
        return switch (pattern){
            case 1 -> "tailshade";
            case 2 -> "topshade";
            case 3 -> "halfshade";
            case 4 -> "large_stripe";
            case 5 -> "racing_stripe";
            case 6 -> "large_racing_stripe";
            default -> "stripe";
        };
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        if (spawnType == MobSpawnType.BUCKET && dataTag != null && dataTag.contains("BaseColor", 3)) {
            this.setBaseColor(dataTag.getInt("BaseColor"));
            this.setPattern(dataTag.getInt("Pattern"));
            this.setPatternColor(dataTag.getInt("PatternColor"));
            this.setHasPattern(dataTag.getBoolean("HasPattern"));
            this.setWingColor(dataTag.getInt("WingColor"));
        } else {
            this.setBaseColor(this.random.nextInt(16));
            this.setPattern(this.random.nextInt(7));
            this.setPatternColor(this.random.nextInt(16));
            this.setHasPattern(this.random.nextInt(3)==0);
            this.setWingColor(this.random.nextInt(16));
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, dataTag);
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BOTTLE_FILL_DRAGONBREATH;
    }

    public int getBaseColor() {
        return this.entityData.get(BASE_COLOR);
    }

    public void setBaseColor(int variant) {
        this.entityData.set(BASE_COLOR, variant);
    }

    public int getPattern() {
        return this.entityData.get(PATTERN);
    }

    public void setPattern(int variant) {
        this.entityData.set(PATTERN, variant);
    }

    public int getPatternColor() {
        return this.entityData.get(PATTERN_COLOR);
    }

    public void setPatternColor(int variant) {
        this.entityData.set(PATTERN_COLOR, variant);
    }

    public int getWingColor() {
        return this.entityData.get(WING_COLOR);
    }

    public void setWingColor(int variant) {
        this.entityData.set(WING_COLOR, variant);
    }

    public Boolean getHasPattern() {
        return this.entityData.get(HAS_PATTERN);
    }

    public void setHasPattern(Boolean variant) {
        this.entityData.set(HAS_PATTERN, variant);
    }

    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean pFromBucket) {
        this.entityData.set(FROM_BUCKET, pFromBucket);
    }

    public int getPreenTimer() {
        return this.entityData.get(PREEN_TIMER);
    }
    public void setPreenTimer(int timer) {
        this.entityData.set(PREEN_TIMER, timer);
    }

    public int getPreenCooldown() {
        return this.entityData.get(PREEN_COOLDOWN);
    }
    public void setPreenCooldown(int cooldown) {
        this.entityData.set(PREEN_COOLDOWN, cooldown);
    }
    public void preenCooldown() {
        this.entityData.set(PREEN_COOLDOWN, 6 * 20 + random.nextInt(30 * 2 * 20));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL) || super.isInvulnerableTo(source);
    }

    @Override
    @NotNull
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return null;
    }

    @Override
    protected void doPush(Entity entity) {
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    protected Entity.@NotNull MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    protected void doWaterSplashEffect() {
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    // sounds
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2Sounds.KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UP2Sounds.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH.get();
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
    }

    @Override
    protected float getSoundVolume() {
        return 0.25F;
    }

    // flight
    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        if (flying && isBaby()) {
            return;
        }
        this.entityData.set(FLYING, flying);
    }

    public Vec3 getBlockGrounding(Vec3 pos) {
        final float radius = 10 + this.getRandom().nextInt(24);
        float neg = this.getRandom().nextBoolean() ? 1 : -1;
        float renderYawOffset = this.yBodyRot;
        float angle = (0.01745329251f * renderYawOffset) + 3.15f + (this.getRandom().nextFloat() * neg);
        final double extraX = radius * Mth.sin(Mth.PI + angle);
        final double extraZ = radius * Mth.cos(angle);
        final BlockPos radialPos = new BlockPos((int) (pos.x() + extraX), (int) getY(), (int) (pos.z() + extraZ));
        BlockPos ground = this.getGround(radialPos);
        if (ground.getY() == 0) {
            return this.position();
        } else {
            ground = this.blockPosition();
            while (ground.getY() > -64 && !level().getBlockState(ground).isSolid()) {
                ground = ground.below();
            }
        }
        if (!this.isTargetBlocked(Vec3.atCenterOf(ground.above()))) {
            return Vec3.atCenterOf(ground);
        }
        return null;
    }

    public boolean isTargetBlocked(Vec3 target) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        return this.level().clip(new ClipContext(Vector3d, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.MISS;
    }

    public Vec3 getBlockInViewAway(Vec3 fleePos, float radiusAdd) {
        final float radius = 5 + radiusAdd + this.getRandom().nextInt(5);
        final float neg = this.getRandom().nextBoolean() ? 1 : -1;
        final float renderYawOffset = this.yBodyRot;
        final float angle = (0.0174532925f * renderYawOffset) + 3.15f + (this.getRandom().nextFloat() * neg);
        final double extraX = radius * Mth.sin(Mth.PI + angle);
        final double extraZ = radius * Mth.cos(angle);
        BlockPos radialPos = new BlockPos((int) (fleePos.x() + extraX), 0, (int) (fleePos.z() + extraZ));
        BlockPos ground = getGround(radialPos);
        final int distFromGround = (int) this.getY() - ground.getY();
        final int flightHeight = 6 + this.getRandom().nextInt(4);
        BlockPos newPos = ground.above(distFromGround > 3 ? flightHeight : this.getRandom().nextInt(4) + 8);
        if (!this.isTargetBlocked(Vec3.atCenterOf(newPos)) && this.distanceToSqr(Vec3.atCenterOf(newPos)) > 1) {
            return Vec3.atCenterOf(newPos);
        }
        return null;
    }

    private boolean isOverWaterOrVoid() {
        BlockPos position = this.blockPosition();
        while (position.getY() > -64 && level().isEmptyBlock(position)) {
            position = position.below();
        }
        return !level().getFluidState(position).isEmpty() || position.getY() <= -64;
    }

    public BlockPos getGround(BlockPos in) {
        BlockPos position = new BlockPos(in.getX(), (int) this.getY(), in.getZ());
        while (position.getY() > -64 && !level().getBlockState(position).isSolid() && level().getFluidState(position).isEmpty()) {
            position = position.below();
        }
        return position;
    }

    // goals
    private class KimmeridgebrachypteraeschnidiumFlightGoal extends Goal {

        protected final KimmeridgebrachypteraeschnidiumEntity dragonfly;

        protected double x;
        protected double y;
        protected double z;

        public KimmeridgebrachypteraeschnidiumFlightGoal(KimmeridgebrachypteraeschnidiumEntity dragonfly) {
            super();
            this.setFlags(EnumSet.of(Flag.MOVE));
            this.dragonfly = dragonfly;
        }

        @Override
        public boolean canUse() {
            if (this.dragonfly.isVehicle() || (this.dragonfly.getTarget() != null && this.dragonfly.getTarget().isAlive()) || this.dragonfly.isPassenger() && this.dragonfly.getPreenTimer() == 0) {
                return false;
            } else {
                if (this.dragonfly.getRandom().nextInt(16) != 0 && !this.dragonfly.isFlying()) {
                    return false;
                }

                Vec3 vec3 = this.getPosition();
                if (vec3 == null) {
                    return false;
                } else {
                    this.x = vec3.x;
                    this.y = vec3.y;
                    this.z = vec3.z;
                    return true;
                }
            }
        }

        public void tick() {
            this.dragonfly.getMoveControl().setWantedPosition(this.x, this.y, this.z, 1f);
            if (isFlying() && this.dragonfly.onGround() && this.dragonfly.timeFlying > 10) {
                this.dragonfly.setFlying(false);
            }
            if (this.dragonfly.horizontalCollision && !this.dragonfly.onGround()) {
                stop();
            }
        }

        @Nullable
        protected Vec3 getPosition() {
            Vec3 vector3d = this.dragonfly.position();
            if (this.dragonfly.timeFlying < 340 || this.dragonfly.isOverWaterOrVoid()) {
                return this.dragonfly.getBlockInViewAway(vector3d, 0);
            }
            else {
                return this.dragonfly.getBlockGrounding(vector3d);
            }
        }

        public boolean canContinueToUse() {
            return this.dragonfly.isFlying() && this.dragonfly.distanceToSqr(x, y, z) > 5f;
        }

        public void start() {
            this.dragonfly.setFlying(true);
            this.dragonfly.getMoveControl().setWantedPosition(this.x, this.y, this.z, 1f);
        }

        public void stop() {
            this.dragonfly.getNavigation().stop();
            x = 0;
            y = 0;
            z = 0;
            super.stop();
        }
    }

    private static class KimmeridgebrachypteraeschnidiumPreenGoal extends Goal {

        KimmeridgebrachypteraeschnidiumEntity dragonfly;

        public KimmeridgebrachypteraeschnidiumPreenGoal(KimmeridgebrachypteraeschnidiumEntity dragonfly) {
            this.dragonfly = dragonfly;
        }

        @Override
        public boolean canUse() {
            return this.dragonfly.getPreenCooldown() == 0 && this.dragonfly.onGround();
        }

        @Override
        public boolean canContinueToUse() {
            return this.dragonfly.getPreenTimer() > 0 && this.dragonfly.onGround();
        }

        @Override
        public void start() {
            super.start();
            this.dragonfly.setPreenTimer(60);
        }

        @Override
        public void tick() {
            super.tick();
        }

        @Override
        public void stop() {
            super.stop();
            this.dragonfly.preenCooldown();
        }
    }
}
