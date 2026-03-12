package com.barlinc.unusual_prehistory.entity.mob.update_4;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricFlyingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricFlyingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinFlyingPathNavigation;
import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;
import com.barlinc.unusual_prehistory.entity.utils.GrabbingMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Dimorphodon extends PrehistoricFlyingMob implements GrabbingMob {

    private static final EntityDataAccessor<Integer> HELD_MOB_ID = SynchedEntityData.defineId(Dimorphodon.class, EntityDataSerializers.INT);

    public int grabCooldown = 0;
    private int runTicks = 0;

    public final SmoothAnimationState grabAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState nip1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState nip2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState tailChaseAnimationState = new SmoothAnimationState();

    private int nipCooldown = 900 + this.getRandom().nextInt(900);
    private int tailChaseCooldown = 1300 + this.getRandom().nextInt(1300);

    public Dimorphodon(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(false);
        this.setPathfindingMalus(BlockPathTypes.LEAVES, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.FLYING_SPEED, 1.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.17F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new DimorphodonGrabGoal(this));
        this.goalSelector.addGoal(2, new LargePanicGoal(this, 2.0D, 10, 4) {
            @Override
            public boolean canUse() {
                return !Dimorphodon.this.canPickUpTarget(Dimorphodon.this.getLastHurtByMob()) && super.canUse();
            }
        });
        this.goalSelector.addGoal(3, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 12.0F, 2.0D, entity -> entity.getType().is(UP2EntityTags.DIMORPHODON_AVOIDS)));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIMORPHODON_FOOD), false));
        this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1.0D) {
            @Override
            public boolean canUse() {
                return super.canUse() && !Dimorphodon.this.isFlying();
            }
        });
        this.goalSelector.addGoal(5, new RandomFlightGoal(this, 1.0F, 1.5F, 16, 5, 1500, 300));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new DimorphodonNipGoal(this));
        this.goalSelector.addGoal(8, new DimorphodonTailChaseGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    @Override
    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.lookControl = new PrehistoricLookControl(this);
            this.moveControl = new PrehistoricMoveControl(this);
            this.navigation = new NoSpinGroundPathNavigation(this, this.level());
            this.isLandNavigator = true;
        } else {
            this.lookControl = new PrehistoricFlyingLookControl(this, 85);
            this.moveControl = new PrehistoricFlyingMoveControl(this);
            this.navigation = new NoSpinFlyingPathNavigation(this, this.level());
            this.isLandNavigator = false;
        }
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if ((this.refuseToMove() || this.isDancing()) && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (hurt && this.getHeldMobId() != -1) {
            this.setHeldMobId(-1);
            if (this.getTarget() != null) {
                this.setTarget(null);
            }
        }
        if (hurt && source.getEntity() != null && this.isAlive() && this.getHealth() < this.getMaxHealth() * 0.5F) {
            this.setFlying(true);
            this.setRunning(true);
            this.runTicks = this.getFastFlyingTicks();
        }
        return hurt;
    }

    @Override
    public boolean canDanceToJukebox() {
        return !this.isFlying();
    }

    private void positionHeldMob() {
        Entity entity = this.level().getEntity(this.getHeldMobId());
        if (entity != null) {
            Vec3 heldPos = this.position().add(0.0D, -2.0D, 0.0D);
            Vec3 minus = new Vec3(heldPos.x - entity.getX(), heldPos.y - entity.getY(), heldPos.z - entity.getZ());
            entity.setDeltaMovement(minus);
        }
    }

    public boolean canPickUpTarget(LivingEntity target) {
        if (target == null) {
            return false;
        }
        if (target.getType().is(UP2EntityTags.DIMORPHODON_CANT_GRAB)) {
            return false;
        }
        return (target.getBbWidth() < this.getBbWidth() && target.getBbHeight() < this.getBbHeight()) || target.getType().is(UP2EntityTags.DIMORPHODON_CAN_GRAB) || target instanceof Player;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getHeldMobId() != -1) {
            this.positionHeldMob();
        }

        if (!this.level().isClientSide) {
            if (this.isFlying()) {
                if (runTicks > 0) runTicks--;
                if (this.isRunning() && runTicks == 0) this.setRunning(false);
            }
            if (grabCooldown > 0) grabCooldown--;
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (!this.isFlying() && !this.isDancing()) {
            if (nipCooldown > 0) nipCooldown--;
            if (tailChaseCooldown > 0) tailChaseCooldown--;
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 2;
    }

    public int getFastFlyingTicks() {
        return 80 + this.getRandom().nextInt(60);
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isDancing() && !this.isFlying() && this.getIdleState() != 2, this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying() && !this.isRunning() && this.getAttackState() != 1, this.tickCount);
        this.flyFastAnimationState.animateWhen(this.isFlying() && this.isRunning() && this.getAttackState() != 1, this.tickCount);
        this.hoverAnimationState.animateWhen(this.isFlying() && this.getAttackState() != 1, this.tickCount);
        this.danceAnimationState.animateWhen(this.isDancing(), this.tickCount);
        this.grabAnimationState.animateWhen(this.getAttackState() == 1, this.tickCount);
        this.nip1AnimationState.animateWhen(this.nip1AnimationState.isStarted(), this.tickCount);
        this.nip2AnimationState.animateWhen(this.nip2AnimationState.isStarted(), this.tickCount);
        this.tailChaseAnimationState.animateWhen(this.tailChaseAnimationState.isStarted(), this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
    }

    public void handleEntityEvent(byte id) {
        switch (id) {
            case 67 -> {
                if (this.getRandom().nextBoolean()) this.nip1AnimationState.start(this.tickCount);
                else this.nip2AnimationState.start(this.tickCount);
            }
            case 68 -> {
                this.nip1AnimationState.stop();
                this.nip2AnimationState.stop();
            }
            case 69 -> this.tailChaseAnimationState.start(this.tickCount);
            case 70 -> this.tailChaseAnimationState.stop();
            default -> super.handleEntityEvent(id);
        }
    }

    protected void nipCooldown() {
        this.nipCooldown = 900 + this.getRandom().nextInt(900);
    }

    protected void tailChaseCooldown() {
        this.tailChaseCooldown = 1300 + this.getRandom().nextInt(1300);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIMORPHODON_FOOD);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HELD_MOB_ID, -1);
    }

    @Override
    public void setHeldMobId(int id) {
        this.entityData.set(HELD_MOB_ID, id);
    }

    @Override
    public int getHeldMobId() {
        return this.entityData.get(HELD_MOB_ID);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.DIMORPHODON_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.DIMORPHODON_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.DIMORPHODON_DEATH.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.DIMORPHODON.get().create(serverLevel);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3, 3, 3);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return Math.sqrt(distance) < 1024.0D;
    }

    public static boolean canSpawn(EntityType<Dimorphodon> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.DIMORPHODON_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    // Goals
    private static class DimorphodonGrabGoal extends AttackGoal {

        private final Dimorphodon dimorphodon;

        public DimorphodonGrabGoal(Dimorphodon dimorphodon) {
            super(dimorphodon);
            this.dimorphodon = dimorphodon;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && dimorphodon.grabCooldown == 0 && dimorphodon.canPickUpTarget(dimorphodon.getTarget());
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && dimorphodon.grabCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.dimorphodon.setTarget(null);
            this.dimorphodon.grabCooldown = 100 + dimorphodon.getRandom().nextInt(100);
            if (!dimorphodon.onGround()) {
                this.dimorphodon.setFlying(true);
            }
        }

        @Override
        public void tick() {
            LivingEntity target = dimorphodon.getTarget();
            if (target != null) {
                double distance = dimorphodon.distanceToSqr(target);
                if (dimorphodon.getAttackState() == 1) this.tickGrab();
                else if (distance <= this.getAttackReachSqr(target) && dimorphodon.canPickUpTarget(target) && dimorphodon.grabCooldown == 0) {
                    this.dimorphodon.setAttackState(1);
                } else {
                    this.dimorphodon.getLookControl().setLookAt(target, 30F, 30F);
                    this.dimorphodon.getNavigation().moveTo(target, 2.0D);
                }
            }
        }

        protected void tickGrab() {
            this.timer++;
            this.dimorphodon.getNavigation().stop();
            LivingEntity target = dimorphodon.getTarget();
            if (timer == 1) {
                if (this.isInAttackRange(target, 1.5D)) {
                    this.dimorphodon.setHeldMobId(target.getId());
                }
            }
            if (timer > 1 && dimorphodon.getHeldMobId() != -1) {
                this.dimorphodon.setFlying(true);
                this.dimorphodon.setDeltaMovement(dimorphodon.getDeltaMovement().x, 0.18D, dimorphodon.getDeltaMovement().z);
            }
            if (timer > 80 || (timer > 2 && dimorphodon.getHeldMobId() == -1)) {
                this.stop();
            }
        }
    }

    private static class DimorphodonNipGoal extends IdleAnimationGoal {

        private final Dimorphodon dimorphodon;

        public DimorphodonNipGoal(Dimorphodon dimorphodon) {
            super(dimorphodon, 20, 1, (byte) 67, (byte) 68, false);
            this.dimorphodon = dimorphodon;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && dimorphodon.nipCooldown == 0 && !dimorphodon.isFlying() && !dimorphodon.isBaby();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !dimorphodon.isFlying();
        }

        @Override
        public void stop() {
            super.stop();
            this.dimorphodon.nipCooldown();
        }
    }

    private static class DimorphodonTailChaseGoal extends IdleAnimationGoal {

        private final Dimorphodon dimorphodon;

        public DimorphodonTailChaseGoal(Dimorphodon dimorphodon) {
            super(dimorphodon, 60, 2, (byte) 69, (byte) 70);
            this.dimorphodon = dimorphodon;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && dimorphodon.tailChaseCooldown == 0 && !dimorphodon.isFlying() && !dimorphodon.isBaby();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !dimorphodon.isFlying();
        }

        @Override
        public void stop() {
            super.stop();
            this.dimorphodon.tailChaseCooldown();
        }
    }
}