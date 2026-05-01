package com.barlinc.unusual_prehistory.entity.mob.update_6;

import com.barlinc.unusual_prehistory.entity.accessor.LivingEntityAccessor;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.goals.update_6.LorrainosaurusAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.AmphibiousMob;
import com.barlinc.unusual_prehistory.entity.utils.GrabbingMob;
import com.barlinc.unusual_prehistory.entity.utils.MobUtils;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Lorrainosaurus extends AmphibiousMob implements GrabbingMob {

    private static final EntityDataAccessor<Integer> HELD_MOB_ID = SynchedEntityData.defineId(Lorrainosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GRAB_TIME = SynchedEntityData.defineId(Lorrainosaurus.class, EntityDataSerializers.INT);

    public int biteCooldown = 0;
    public int grabCooldown = 0;
    private int grabTicks;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState nip1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState nip2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState grabStartAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState grabAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState aggroAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();

    public boolean attackAlt = false;
    private boolean nipAlt = false;

    private int grabStartTicks;

    public Lorrainosaurus(EntityType<? extends AmphibiousMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(true);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15F)
                .add(Attributes.ATTACK_DAMAGE, 10.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                .add(Attributes.STEP_HEIGHT, 1.1D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new EnterWaterGoal(this, 1.0D, 100, true));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 2.0D, 16, 8));
        this.goalSelector.addGoal(2, new LorrainosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.LORRAINOSAURUS_FOOD), false));
        this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 40, 30, 15, 3, true));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 60, 1, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 20, 2, false, 0.001F, this::canPlayIdles) {
            @Override
            public void start() {
                super.start();
                Lorrainosaurus.this.nipAlt = Lorrainosaurus.this.getRandom().nextBoolean();
            }
        });
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, true, entity -> entity.getType().is(UP2EntityTags.LORRAINOSAURUS_TARGETS)));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 100, true, true, this::canAttack));
    }

    @Override
    public boolean killedEntity(@NotNull ServerLevel level, @NotNull LivingEntity victim) {
        this.heal(8);
        return super.killedEntity(level, victim);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new PrehistoricMoveControl(this);
            this.lookControl = new PrehistoricLookControl(this);
            this.navigation = this.createNavigation(this.level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new PrehistoricSwimmingMoveControl(this, 1000, 6, 0.8F);
            this.lookControl = new PrehistoricSwimmingLookControl(this, 4);
            this.navigation = new SmoothAmphibiousPathNavigation(this, this.level());
            this.isLandNavigator = false;
        }
    }

    @Override
    public boolean isPushable() {
        return this.isBaby();
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isInWaterOrBubble() ? 1 : super.getMaxHeadXRot();
    }

    @Override
    public int getMaxHeadYRot() {
        return this.isInWaterOrBubble() ? 1 : super.getMaxHeadYRot();
    }

    @Override
    public float getAdditionalStepHeight() {
        return 0.0F;
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        super.remove(removalReason);
        if (this.getHeldMobId() != -1) {
            this.setHeldMobId(-1);
        }
    }

    @Override
    public void tick() {
        super.tick();
        final boolean ground = !this.isInWaterOrBubble();
        if (!ground && this.isLandNavigator) {
            this.switchNavigator(false);
        }
        if (ground && !this.isLandNavigator) {
            this.switchNavigator(true);
        }

        if ((this.getPose() == UP2Poses.GRAB_START.get() || this.getPose() == UP2Poses.GRABBING.get()) && this.getHeldMobId() != -1) {
            this.positionHeldMob();
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.aggroAnimationState.animateWhen(this.isAggressive() && !this.isInAttackPose(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.grabStartAnimationState.animateWhen(this.getPose() == UP2Poses.GRAB_START.get(), this.tickCount);
        this.grabAnimationState.animateWhen(this.getPose() == UP2Poses.GRABBING.get(), this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.nip1AnimationState.animateWhen(this.getIdleState() == 2 && !nipAlt, this.tickCount);
        this.nip2AnimationState.animateWhen(this.getIdleState() == 2 && nipAlt, this.tickCount);
    }

    private boolean isInAttackPose() {
        return this.getPose() == UP2Poses.GRAB_START.get() || this.getPose() == UP2Poses.ATTACKING.get() || this.getPose() == UP2Poses.GRABBING.get();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 700 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 2) {
            return 800 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    public boolean canPlayIdles(Entity entity) {
        return entity.isInWaterOrBubble();
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (biteCooldown > 0) biteCooldown--;
        if (grabCooldown > 0) grabCooldown--;
        if (grabTicks > 0) grabTicks--;
        if (grabStartTicks > 0) grabStartTicks--;
        if (grabStartTicks == 0 && this.getPose() == UP2Poses.GRAB_START.get()) {
            if (this.getHeldMobId() != -1) {
                this.setPose(UP2Poses.GRABBING.get());
            } else {
                this.setPose(Pose.STANDING);
            }
        }
    }

    public boolean canPickUpTarget(LivingEntity target) {
        if (target == null) {
            return false;
        }
        if (((LivingEntityAccessor) target).unusualPrehistory2$isBeingGrabbed()) {
            return false;
        }
        if (target.getType().is(UP2EntityTags.LORRAINOSAURUS_CANT_GRAB)) {
            return false;
        }
        return (target.getBbWidth() < this.getBbWidth() && target.getBbHeight() < this.getBbHeight()) || target.getType().is(UP2EntityTags.LORRAINOSAURUS_CAN_GRAB);
    }

    private void positionHeldMob() {
        Entity entity = this.level().getEntity(this.getHeldMobId());
        if (entity != null) {
            if (grabTicks < this.getGrabTime()) {
                Vec3 heldPos = this.position().add(new Vec3(0.0F, 0.1F, 3.0F).yRot(-yBodyRot * ((float) Math.PI / 180F)));
                Vec3 minus = new Vec3(heldPos.x - entity.getX(), heldPos.y - entity.getY(), heldPos.z - entity.getZ());
                entity.setDeltaMovement(minus);
                entity.fallDistance = 0.0F;
                entity.setYRot(0.0F);
                entity.setYBodyRot(0.0F);
                entity.setYHeadRot(0.0F);
                entity.setXRot(0.0F);
                if (grabTicks < (this.getGrabTime() * 0.8F) && grabTicks % 30 == 0) {
                    entity.hurt(damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.25F);
                }
            }
        } else {
            this.setHeldMobId(-1);
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.GRAB_START.get()) {
                this.grabStartTicks = 20;
            }
            else if (this.getPose() == UP2Poses.GRABBING.get()) {
                this.grabTicks = this.getGrabTime();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(2);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.LORRAINOSAURUS_FOOD);
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HELD_MOB_ID, -1);
        builder.define(GRAB_TIME, 150 + this.getRandom().nextInt(50));
    }

    @Override
    public void setHeldMobId(int id) {
        int oldId = this.getHeldMobId();
        if (oldId != -1) {
            Entity oldEntity = this.level().getEntity(oldId);
            if (oldEntity instanceof LivingEntity living) {
                ((LivingEntityAccessor) living).unusualPrehistory2$setBeingGrabbed(false);
            }
        }
        this.entityData.set(HELD_MOB_ID, id);
        if (id != -1) {
            Entity entity = this.level().getEntity(id);
            if (entity instanceof LivingEntity living) {
                ((LivingEntityAccessor) living).unusualPrehistory2$setBeingGrabbed(true);
            }
        }
    }

    @Override
    public int getHeldMobId() {
        return this.entityData.get(HELD_MOB_ID);
    }

    public void setGrabTime(int grabTime) {
        this.entityData.set(GRAB_TIME, grabTime);
    }

    public int getGrabTime() {
        return this.entityData.get(GRAB_TIME);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.LORRAINOSAURUS.get().create(level);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.LORRAINOSAURUS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.LORRAINOSAURUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.LORRAINOSAURUS_DEATH.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }
}
