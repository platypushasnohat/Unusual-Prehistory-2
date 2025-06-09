package com.unusualmodding.unusual_prehistory.entity;

import com.google.common.base.Predicate;
import com.unusualmodding.unusual_prehistory.entity.ai.goal.LargePanicGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.DirectPathNavigator;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.FlyingMoveController;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2Sounds;
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
import net.minecraft.world.entity.animal.Animal;
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

public class Telecrex extends Animal {

    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(Telecrex.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(Telecrex.class, EntityDataSerializers.BOOLEAN);

    public float currentRoll = 0.0F;

    public float prevFlyProgress;
    public float flyProgress;
    private boolean isLandNavigator;
    public int timeFlying;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();

    public Telecrex(EntityType<? extends Animal> entityType, Level level) {
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

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyingMoveController(this, 1, false);
            this.navigation = new DirectPathNavigator(this, level());
            this.isLandNavigator = false;
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(3, new TelecrexFlightGoal(this));
        this.goalSelector.addGoal(4, new TelecrexScatterGoal(this));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TELECREX_FOOD), false));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(Telecrex.class));
    }

    @Override
    public void tick() {
        super.tick();

        this.prevFlyProgress = flyProgress;
        if (isFlying()) {
            if (flyProgress < 5F)
                flyProgress++;
        } else {
            if (flyProgress > 0F)
                flyProgress--;
        }
        if (!this.level().isClientSide) {
            final boolean isFlying = isFlying();
            if (isFlying && this.isLandNavigator) {
                switchNavigator(false);
            }
            if (!isFlying && !this.isLandNavigator) {
                switchNavigator(true);
            }
            if (isFlying) {
                timeFlying++;
                this.setNoGravity(true);
                if (this.onGround()) {
                    this.setFlying(false);
                }
            }
            else {
                timeFlying = 0;
                this.setNoGravity(false);
            }
        }

        float prevRoll = this.currentRoll;
        float targetRoll = Math.max(-0.45F, Math.min(0.45F, (this.getYRot() - this.yRotO) * 0.1F));
        targetRoll = -targetRoll;
        this.currentRoll = prevRoll + (targetRoll - prevRoll) * 0.05F;

        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive() && !this.isFlying(), this.tickCount);
        this.flyAnimationState.animateWhen(this.isAlive() && this.isFlying(), this.tickCount);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FLYING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Flying", this.isFlying());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setFlying(compoundTag.getBoolean("Flying"));
    }

    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.TELECREX_FOOD);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if(!this.level().isClientSide) {
                this.level().broadcastEntityEvent(this, (byte) 7);
            }
            this.playSound(SoundEvents.PARROT_EAT, 0.25F, this.getVoicePitch());
            this.gameEvent(GameEvent.EAT, this);
            this.heal(2);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public void travel(Vec3 vec3d) {
        if (this.isInWater() && this.getDeltaMovement().y > 0F) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.5D, 1.0D));
        }
        super.travel(vec3d);
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
        return UP2Sounds.TELECREX_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return UP2Sounds.TELECREX_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2Sounds.TELECREX_DEATH.get();
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

    public boolean canTrample(BlockState state, BlockPos pos, float fallDistance) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    private static class TelecrexFlightGoal extends Goal {

        protected final Telecrex telecrex;
        protected double x;
        protected double y;
        protected double z;
        private boolean flightTarget = false;

        public TelecrexFlightGoal(Telecrex telecrex) {
            super();
            this.setFlags(EnumSet.of(Flag.MOVE));
            this.telecrex = telecrex;
        }

        @Override
        public boolean canUse() {
            if (this.telecrex.isPassenger()  || this.telecrex.isVehicle()) {
                return false;
            } else {
                if (this.telecrex.getRandom().nextInt(30) != 0 && !this.telecrex.isFlying()) {
                    return false;
                }
                if (this.telecrex.onGround()) {
                    this.flightTarget = this.telecrex.getRandom().nextBoolean();
                } else {
                    this.flightTarget = this.telecrex.getRandom().nextInt(5) > 0 && this.telecrex.timeFlying < 200;
                }
                Vec3 lvt_1_1_ = this.getPosition();
                if (lvt_1_1_ == null) {
                    return false;
                } else {
                    this.x = lvt_1_1_.x;
                    this.y = lvt_1_1_.y;
                    this.z = lvt_1_1_.z;
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
            if (this.telecrex.isPassenger()  || this.telecrex.isVehicle() || entity != null && entity.isAlive()) {
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
                if(this.cooldown == 0 && this.telecrex.isTargetBlocked(this.flightTarget)){
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
            AABB aabb = new AABB(-targetDistance, -targetDistance, -targetDistance, targetDistance, targetDistance, targetDistance);
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
}