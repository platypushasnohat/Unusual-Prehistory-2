package com.unusualmodding.unusual_prehistory.entity;

import com.google.common.base.Predicate;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.DirectPathNavigator;
import com.unusualmodding.unusual_prehistory.entity.base.FlyingPrehistoricMob;
import com.unusualmodding.unusual_prehistory.entity.enums.BaseBehaviors;
import com.unusualmodding.unusual_prehistory.entity.enums.TelecrexBehaviors;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2EntityTags;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class Telecrex extends FlyingPrehistoricMob {

    public static final EntityDataAccessor<Integer> LOOKOUT_COOLDOWN = SynchedEntityData.defineId(Telecrex.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> LOOKOUT_TIMER = SynchedEntityData.defineId(Telecrex.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> PREEN_COOLDOWN = SynchedEntityData.defineId(Telecrex.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> PREEN_TIMER = SynchedEntityData.defineId(Telecrex.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> PECK_COOLDOWN = SynchedEntityData.defineId(Telecrex.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> PECK_TIMER = SynchedEntityData.defineId(Telecrex.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();
    public final AnimationState lookoutAnimationState = new AnimationState();
    public final AnimationState preen1AnimationState = new AnimationState();
    public final AnimationState preen2AnimationState = new AnimationState();
    public final AnimationState peckAnimationState = new AnimationState();

    public Telecrex(EntityType<? extends FlyingPrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
        switchNavigator(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.16F);
    }

    @Override
    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new TelecrexMoveControl(this, false);
            this.navigation = new DirectPathNavigator(this, level());
            this.isLandNavigator = false;
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TelecrexScatterGoal(this));
        this.goalSelector.addGoal(2, new TelecrexWanderGoal(this));
        this.goalSelector.addGoal(3, new LargePanicGoal(this, 1.5D));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TELECREX_FOOD), false));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new TelecrexPeckGoal(this));
        this.goalSelector.addGoal(8, new TelecrexLookoutGoal(this));
        this.goalSelector.addGoal(9, new TelecrexPreenGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers(Telecrex.class));
    }

    @Override
    public void tick() {
        if (this.onGround()) {
            if (this.getLookoutCooldown() > 0) {
                if (this.getBehavior().equals(TelecrexBehaviors.LOOKOUT.getName()))
                    this.setBehavior(BaseBehaviors.IDLE.getName());
                this.setLookoutCooldown(this.getLookoutCooldown() - 1);
            }
            if (this.getPreenCooldown() > 0) {
                if (this.getBehavior().equals(TelecrexBehaviors.PREEN.getName())) this.setBehavior(BaseBehaviors.IDLE.getName());
                this.setPreenCooldown(this.getPreenCooldown() - 1);
            }
            if (this.getPeckCooldown() > 0) {
                if (this.getBehavior().equals(TelecrexBehaviors.PECK.getName()))
                    this.setBehavior(BaseBehaviors.IDLE.getName());
                this.setPeckCooldown(this.getPeckCooldown() - 1);
            }
        }

        if (this.getLookoutTimer() > 0) {
            this.setLookoutTimer(this.getLookoutTimer() - 1);
            if (this.getLookoutTimer() == 0) {
                this.setPose(Pose.STANDING);
                this.setBehavior(BaseBehaviors.IDLE.getName());
                this.lookoutCooldown();
            }
        }

        if (this.getPreenTimer() > 0) {
            this.setPreenTimer(this.getPreenTimer() - 1);
            if (this.getPreenTimer() == 0) {
                this.setPose(Pose.STANDING);
                this.setBehavior(BaseBehaviors.IDLE.getName());
                this.preenCooldown();
            }
        }


        if (this.getPeckTimer() > 0) {
            this.setPeckTimer(this.getPeckTimer() - 1);
            if (this.getPeckTimer() == 0) {
                this.setPose(Pose.STANDING);
                this.setBehavior(BaseBehaviors.IDLE.getName());
                this.peckCooldown();
            }
        }

        super.tick();
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive() && !this.isFlying(), this.tickCount);
        this.flyingAnimationState.animateWhen(this.isAlive() && this.isFlying(), this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == UP2Poses.LOOKOUT.get()) this.lookoutAnimationState.start(this.tickCount);
            if (this.getPose() == UP2Poses.PREENING.get()) {
                this.idleAnimationState.stop();
                if (this.getRandom().nextBoolean()) {
                    this.preen1AnimationState.start(this.tickCount);
                } else {
                    this.preen2AnimationState.start(this.tickCount);
                }
            }
            if (this.getPose() == UP2Poses.PECKING.get()) {
                this.idleAnimationState.stop();
                this.peckAnimationState.start(this.tickCount);
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.TELECREX_FOOD);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if (!this.level().isClientSide) {
                this.level().broadcastEntityEvent(this, (byte) 7);
            }
            this.playSound(SoundEvents.PARROT_EAT, 0.25F, this.getVoicePitch());
            this.gameEvent(GameEvent.EAT, this);
            this.heal(2);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void travel(Vec3 vec3d) {
        if (this.isInWater() && this.getDeltaMovement().y > 0F) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.5D, 1.0D));
        }
        super.travel(vec3d);
    }

    @Override
    public boolean refuseToMove() {
        return (this.getPose() == UP2Poses.PECKING.get() || this.getPose() == UP2Poses.PREENING.get()) && super.refuseToMove();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LOOKOUT_TIMER, 0);
        this.entityData.define(LOOKOUT_COOLDOWN, 50 * 2 * 20 + random.nextInt(50 * 8 * 20));
        this.entityData.define(PREEN_TIMER, 0);
        this.entityData.define(PREEN_COOLDOWN, 60 * 2 * 20 + random.nextInt(50 * 8 * 90));
        this.entityData.define(PECK_TIMER, 0);
        this.entityData.define(PECK_COOLDOWN, 70 * 2 * 20 + random.nextInt(50 * 8 * 90));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("LookoutCooldown", this.getLookoutCooldown());
        compoundTag.putInt("LookoutTimer", this.getLookoutTimer());
        compoundTag.putInt("PreenCooldown", this.getPreenCooldown());
        compoundTag.putInt("PreenTimer", this.getPreenTimer());
        compoundTag.putInt("PeckCooldown", this.getPeckCooldown());
        compoundTag.putInt("PeckTimer", this.getPeckTimer());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLookoutCooldown(compoundTag.getInt("LookoutCooldown"));
        this.setLookoutTimer(compoundTag.getInt("LookoutTimer"));
        this.setPreenCooldown(compoundTag.getInt("PreenCooldown"));
        this.setPreenTimer(compoundTag.getInt("PreenTimer"));
        this.setPeckCooldown(compoundTag.getInt("PeckCooldown"));
        this.setPeckTimer(compoundTag.getInt("PeckTimer"));
    }

    public int getLookoutTimer() {
        return this.entityData.get(LOOKOUT_TIMER);
    }

    public void setLookoutTimer(int timer) {
        this.entityData.set(LOOKOUT_TIMER, timer);
    }

    public int getLookoutCooldown() {
        return this.entityData.get(LOOKOUT_COOLDOWN);
    }

    public void setLookoutCooldown(int cooldown) {
        this.entityData.set(LOOKOUT_COOLDOWN, cooldown);
    }

    public void lookoutCooldown() {
        this.entityData.set(LOOKOUT_COOLDOWN, 50 * 2 * 20 + random.nextInt(50 * 8 * 20));
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
        this.entityData.set(PREEN_COOLDOWN, 60 * 2 * 20 + random.nextInt(50 * 8 * 20));
    }

    public int getPeckTimer() {
        return this.entityData.get(PECK_TIMER);
    }

    public void setPeckTimer(int timer) {
        this.entityData.set(PECK_TIMER, timer);
    }

    public int getPeckCooldown() {
        return this.entityData.get(PECK_COOLDOWN);
    }

    public void setPeckCooldown(int cooldown) {
        this.entityData.set(PECK_COOLDOWN, cooldown);
    }

    public void peckCooldown() {
        this.entityData.set(PECK_COOLDOWN, 70 * 2 * 20 + random.nextInt(50 * 8 * 20));
    }

    public boolean isTargetBlocked(Vec3 target) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        return this.level().clip(new ClipContext(Vector3d, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.MISS;
    }

    public Vec3 getBlockInViewAway(Vec3 fleePos, float radiusAdd) {
        final float radius = 3.15F * -3 - this.getRandom().nextInt(24) - radiusAdd;
        final float angle = getAngle1();
        final double extraX = radius * Mth.sin(Mth.PI + angle);
        final double extraZ = radius * Mth.cos(angle);
        final BlockPos radialPos = new BlockPos((int) (fleePos.x() + extraX), 0, (int) (fleePos.z() + extraZ));
        final BlockPos ground = getGround(radialPos);
        final int distFromGround = (int) this.getY() - ground.getY();
        final BlockPos newPos;
        if (distFromGround > 8) {
            final int flightHeight = 4 + this.getRandom().nextInt(10);
            newPos = ground.above(flightHeight);
        } else {
            newPos = ground.above(this.getRandom().nextInt(6) + 1);
        }

        if (!this.isTargetBlocked(Vec3.atCenterOf(newPos)) && this.distanceToSqr(Vec3.atCenterOf(newPos)) > 1) {
            return Vec3.atCenterOf(newPos);
        }
        return null;
    }

    private BlockPos getGround(BlockPos in) {
        BlockPos position = new BlockPos(in.getX(), (int) this.getY(), in.getZ());
        while (position.getY() > -64 && !level().getBlockState(position).isSolid() && level().getFluidState(position).isEmpty()) {
            position = position.below();
        }
        return position;
    }

    public Vec3 getBlockGrounding(Vec3 fleePos) {
        final float radius = 3.15F * -3 - this.getRandom().nextInt(24);
        final float angle = getAngle1();
        final double extraX = radius * Mth.sin(Mth.PI + angle);
        final double extraZ = radius * Mth.cos(angle);
        final BlockPos radialPos = new BlockPos((int) (fleePos.x() + extraX), (int) getY(), (int) (fleePos.z() + extraZ));
        BlockPos ground = this.getGround(radialPos);
        if (ground.getY() == -64) {
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

    private float getAngle1() {
        final float neg = this.getRandom().nextBoolean() ? 1 : -1;
        final float renderYawOffset = this.yBodyRot;
        return (0.0174532925F * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
    }

    public boolean isOverWater() {
        BlockPos position = this.blockPosition();
        while (position.getY() > -64 && level().isEmptyBlock(position)) {
            position = position.below();
        }
        return !level().getFluidState(position).isEmpty();
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.TELECREX_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return UP2SoundEvents.TELECREX_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.TELECREX_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.1F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.TELECREX.get().create(serverLevel);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL) || source.is(DamageTypes.IN_WALL);
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3, 3, 3);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return Math.sqrt(distance) < 1024.0D;
    }

    static class TelecrexWanderGoal extends Goal {

        private Telecrex telecrex;
        protected double x;
        protected double y;
        protected double z;
        private boolean flightTarget = false;

        public TelecrexWanderGoal(Telecrex telecrex) {
            super();
            this.setFlags(EnumSet.of(Flag.MOVE));
            this.telecrex = telecrex;
        }

        @Override
        public boolean canUse() {
            if (this.telecrex.isPassenger() || this.telecrex.isVehicle() || this.telecrex.refuseToMove()) {
                return false;
            } else {
                if (this.telecrex.getRandom().nextInt(50) != 0 && !this.telecrex.isFlying()) {
                    return false;
                }
                if (this.telecrex.onGround()) {
                    this.flightTarget = this.telecrex.getRandom().nextBoolean();
                } else {
                    this.flightTarget = this.telecrex.getRandom().nextInt(5) > 0 && this.telecrex.timeFlying < 200;
                }
                Vec3 target = this.getPosition();
                if (target == null) {
                    return false;
                } else {
                    this.x = target.x;
                    this.y = target.y;
                    this.z = target.z;
                    return true;
                }
            }
        }

        public void tick() {
            if (this.flightTarget) {
                this.telecrex.getMoveControl().setWantedPosition(x, y, z, 1F);
            } else {
                this.telecrex.getNavigation().moveTo(this.x, this.y, this.z, 1F);

                if (this.telecrex.isFlying() && this.telecrex.onGround()) {
                    this.telecrex.setFlying(false);
                }
            }

            if (this.telecrex.isFlying() && this.telecrex.onGround() && this.telecrex.timeFlying > 10) {
                this.telecrex.setFlying(false);
            }
        }

        @Nullable
        protected Vec3 getPosition() {
            final Vec3 vector3d = this.telecrex.position();
            if (this.telecrex.isOverWater()) {
                this.flightTarget = true;
            }
            if (this.flightTarget) {
                if (this.telecrex.timeFlying < 50 || this.telecrex.isOverWater()) {
                    return this.telecrex.getBlockInViewAway(vector3d, 0);
                } else {
                    return this.telecrex.getBlockGrounding(vector3d);
                }
            } else {
                return LandRandomPos.getPos(this.telecrex, 10, 7);
            }
        }

        public boolean canContinueToUse() {
            if (this.flightTarget) {
                return this.telecrex.isFlying() && this.telecrex.distanceToSqr(x, y, z) > 2F;
            } else {
                return (!this.telecrex.getNavigation().isDone()) && !this.telecrex.isVehicle();
            }
        }

        public void start() {
            if (this.flightTarget) {
                this.telecrex.setFlying(true);
                this.telecrex.getMoveControl().setWantedPosition(x, y, z, 1F);
            } else {
                this.telecrex.getNavigation().moveTo(this.x, this.y, this.z, 1F);
            }
        }

        public void stop() {
            this.telecrex.getNavigation().stop();
            super.stop();
        }
    }

    private class TelecrexScatterGoal extends Goal {

        protected final TelecrexScatterGoal.Sorter theNearestAttackableTargetSorter;
        protected final Predicate<? super Entity> targetEntitySelector;
        protected int executionChance = 8;
        protected boolean mustUpdate;
        private final Telecrex telecrex;
        private Entity targetEntity;
        private Vec3 flightTarget = null;
        private int cooldown = 0;

        TelecrexScatterGoal(Telecrex telecrex) {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            this.theNearestAttackableTargetSorter = new TelecrexScatterGoal.Sorter(Telecrex.this);
            this.targetEntitySelector = (Predicate<Entity>) entity -> entity.isAlive() && entity.getType().is(UP2EntityTags.SCATTERS_TELECREX) || entity instanceof Player && !((Player) entity).isCreative();
            this.telecrex = telecrex;
        }

        @Override
        public boolean canUse() {
            Entity entity = this.telecrex.getTarget();
            if (this.telecrex.isPassenger() || this.telecrex.isVehicle() || entity != null && entity.isAlive()) {
                return false;
            }
            if (!this.mustUpdate) {
                long worldTime = this.telecrex.level().getGameTime() % 10;
                if (this.telecrex.getNoActionTime() >= 100 && worldTime != 0) {
                    return false;
                }
                if (this.telecrex.getRandom().nextInt(this.executionChance) != 0 && worldTime != 0) {
                    return false;
                }
            }
            List<Entity> list = this.telecrex.level().getEntitiesOfClass(Entity.class, this.getTargetableArea(this.getTargetDistance()), this.targetEntitySelector);
            if (list.isEmpty()) {
                return false;
            } else {
                Collections.sort(list, this.theNearestAttackableTargetSorter);
                this.targetEntity = list.get(0);
                this.mustUpdate = false;
                return true;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return targetEntity != null;
        }

        public void stop() {
            super.stop();
            this.flightTarget = null;
            this.targetEntity = null;
        }

        @Override
        public void tick() {
            if (cooldown > 0) {
                cooldown--;
            }
            if (this.flightTarget != null) {
                this.telecrex.setFlying(true);
                this.telecrex.getMoveControl().setWantedPosition(this.flightTarget.x, this.flightTarget.y, this.flightTarget.z, 1F);
                if (this.cooldown == 0 && this.telecrex.isTargetBlocked(this.flightTarget)) {
                    this.cooldown = 30;
                    this.flightTarget = null;
                }
            }

            if (this.targetEntity != null) {
                if (this.telecrex.onGround() || this.flightTarget == null || flightTarget != null && this.telecrex.distanceToSqr(this.flightTarget) < 3) {
                    Vec3 vec = this.telecrex.getBlockInViewAway(targetEntity.position(), 0);
                    if (vec != null && vec.y() > this.telecrex.getY()) {
                        this.flightTarget = vec;
                    }
                }
                if (this.telecrex.distanceTo(this.targetEntity) > 20.0F) {
                    this.stop();
                }
            }
        }

        protected double getTargetDistance() {
            return 4D;
        }

        protected AABB getTargetableArea(double targetDistance) {
            Vec3 renderCenter = new Vec3(this.telecrex.getX(), this.telecrex.getY() + 0.5, this.telecrex.getZ());
            AABB aabb = new AABB(-targetDistance * 2F, -targetDistance * 1.5F, -targetDistance * 2F, targetDistance * 2F, targetDistance * 1.5F, targetDistance * 2F);
            return aabb.move(renderCenter);
        }

        public record Sorter(Entity entity) implements Comparator<Entity> {
            public int compare(Entity entity1, Entity entity2) {
                final double d0 = this.entity.distanceToSqr(entity1);
                final double d1 = this.entity.distanceToSqr(entity2);
                return Double.compare(d0, d1);
            }
        }
    }

    private static class TelecrexLookoutGoal extends Goal {

        protected final Telecrex entity;
        protected final TelecrexBehaviors behavior;

        public TelecrexLookoutGoal(Telecrex entity) {
            this.entity = entity;
            this.behavior = TelecrexBehaviors.LOOKOUT;
        }

        @Override
        public boolean canUse() {
            return this.entity.getLookoutCooldown() == 0 && this.entity.getBehavior().equals(BaseBehaviors.IDLE.getName()) && !this.entity.isInWater() && this.entity.onGround();
        }

        @Override
        public boolean canContinueToUse() {
            return this.entity.getLookoutTimer() > 0 && !this.entity.isInWater() && this.entity.onGround();
        }

        @Override
        public void start() {
            super.start();
            this.entity.setPose(UP2Poses.LOOKOUT.get());
            this.entity.setLookoutTimer(behavior.getLength());
            this.entity.setBehavior(behavior.getName());
            this.entity.playSound(behavior.getSound(), 1.0F, this.entity.getVoicePitch());
        }
    }

    private static class TelecrexPeckGoal extends Goal {

        protected final Telecrex entity;
        protected final TelecrexBehaviors behavior;

        public TelecrexPeckGoal(Telecrex entity) {
            this.entity = entity;
            this.behavior = TelecrexBehaviors.PECK;
        }

        @Override
        public boolean canUse() {
            return this.entity.getPeckCooldown() == 0 && this.entity.getBehavior().equals(BaseBehaviors.IDLE.getName()) && !this.entity.isInWater() && this.entity.onGround();
        }

        @Override
        public boolean canContinueToUse() {
            return this.entity.getPeckTimer() > 0 && !this.entity.isInWater() && this.entity.onGround();
        }

        @Override
        public void start() {
            super.start();
            this.entity.setPose(UP2Poses.PECKING.get());
            this.entity.setPeckTimer(behavior.getLength());
            this.entity.setBehavior(behavior.getName());
            this.entity.playSound(behavior.getSound(), 1.0F, this.entity.getVoicePitch());
        }
    }

    private static class TelecrexPreenGoal extends Goal {

        protected final Telecrex entity;
        protected final TelecrexBehaviors behavior;

        public TelecrexPreenGoal(Telecrex entity) {
            this.entity = entity;
            this.behavior = TelecrexBehaviors.PREEN;
        }

        @Override
        public boolean canUse() {
            return this.entity.getPreenCooldown() == 0 && this.entity.getBehavior().equals(BaseBehaviors.IDLE.getName()) && !this.entity.isInWater() && this.entity.onGround();
        }

        @Override
        public boolean canContinueToUse() {
            return this.entity.getPreenTimer() > 0 && !this.entity.isInWater() && this.entity.onGround();
        }

        @Override
        public void start() {
            super.start();
            this.entity.setPose(UP2Poses.PREENING.get());
            this.entity.setPreenTimer(behavior.getLength());
            this.entity.setBehavior(behavior.getName());
            this.entity.playSound(behavior.getSound(), 1.0F, this.entity.getVoicePitch());
        }
    }

    private static class TelecrexMoveControl extends MoveControl {

        private final Telecrex telecrex;
        private final boolean shouldLookAtTarget;

        public TelecrexMoveControl(Telecrex telecrex, boolean shouldLookAtTarget) {
            super(telecrex);
            this.telecrex = telecrex;
            this.shouldLookAtTarget = shouldLookAtTarget;
        }

        public void tick() {
            if (!telecrex.refuseToMove()) {
                if (this.operation == Operation.MOVE_TO) {
                    Vec3 vector3d = new Vec3(this.wantedX - telecrex.getX(), this.wantedY - telecrex.getY(), this.wantedZ - telecrex.getZ());
                    double d0 = vector3d.length();
                    if (d0 < telecrex.getBoundingBox().getSize()) {
                        this.operation = Operation.WAIT;
                        telecrex.setDeltaMovement(telecrex.getDeltaMovement().scale(0.5D));
                    } else {
                        telecrex.setDeltaMovement(telecrex.getDeltaMovement().add(vector3d.scale(this.speedModifier * 1 * 0.05D / d0)));
                        if (telecrex.getTarget() == null || !shouldLookAtTarget) {
                            Vec3 vector3d1 = telecrex.getDeltaMovement();
                            telecrex.setYRot(-((float) Mth.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI));
                        } else {
                            double d2 = telecrex.getTarget().getX() - telecrex.getX();
                            double d1 = telecrex.getTarget().getZ() - telecrex.getZ();
                            telecrex.setYRot(-((float) Mth.atan2(d2, d1)) * (180F / (float) Math.PI));
                        }
                        telecrex.yBodyRot = telecrex.getYRot();
                    }
                } else if (this.operation == Operation.STRAFE) {
                    this.operation = Operation.WAIT;
                }
            }
        }
    }
}