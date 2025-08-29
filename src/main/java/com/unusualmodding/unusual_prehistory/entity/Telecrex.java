package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goals.*;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.FlyingPathNavigationNoSpin;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.TelecrexMoveControl;
import com.unusualmodding.unusual_prehistory.entity.base.FlyingPrehistoricMob;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class Telecrex extends FlyingPrehistoricMob {

    public static final EntityDataAccessor<Integer> LOOKOUT_COOLDOWN = SynchedEntityData.defineId(Telecrex.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> PREEN_COOLDOWN = SynchedEntityData.defineId(Telecrex.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> PECK_COOLDOWN = SynchedEntityData.defineId(Telecrex.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();
    public final AnimationState lookoutAnimationState = new AnimationState();
    public final AnimationState preen1AnimationState = new AnimationState();
    public final AnimationState preen2AnimationState = new AnimationState();
    public final AnimationState peckAnimationState = new AnimationState();

    public Telecrex(EntityType<? extends FlyingPrehistoricMob> entityType, Level level) {
        super(entityType, level);
        switchNavigator(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.FLYING_SPEED, 1.0F).add(Attributes.MOVEMENT_SPEED, 0.16F);
    }

    @Override
    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new TelecrexMoveControl(this);
            this.navigation = new FlyingPathNavigationNoSpin(this, level(), 1.0F);
            this.isLandNavigator = false;
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TelecrexScatterGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1) {
            @Override
            public boolean canUse() {
                return super.canUse() && !Telecrex.this.isFlying();
            }
        });
        this.goalSelector.addGoal(2, new TelecrexFlightGoal(this));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TELECREX_FOOD), false));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (hurt && source.getEntity() != null){
            double range = 8;
            this.setFlying(true);
            List<? extends Telecrex> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(range, range / 2, range));
            for (Telecrex telecrex : list){
                telecrex.setFlying(true);
            }
        }
        return hurt;
    }

    @Override
    public void setupAnimationCooldowns() {
        if (this.onGround()) {
            if (this.getLookoutCooldown() > 0) {
                this.setLookoutCooldown(this.getLookoutCooldown() - 1);
            }
            if (this.getPreenCooldown() > 0) {
                this.setPreenCooldown(this.getPreenCooldown() - 1);
            }
            if (this.getPeckCooldown() > 0) {
                this.setPeckCooldown(this.getPeckCooldown() - 1);
            }
        }
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
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
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
    public boolean refuseToMove() {
        return (this.getPose() == UP2Poses.PECKING.get() || this.getPose() == UP2Poses.PREENING.get()) && super.refuseToMove();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LOOKOUT_COOLDOWN, 50 * 2 * 20 + random.nextInt(20 * 8 * 20));
        this.entityData.define(PREEN_COOLDOWN, 40 * 2 * 20 + random.nextInt(20 * 8 * 20));
        this.entityData.define(PECK_COOLDOWN, 20 * 2 * 20 + random.nextInt(20 * 8 * 20));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("LookoutCooldown", this.getLookoutCooldown());
        compoundTag.putInt("PreenCooldown", this.getPreenCooldown());
        compoundTag.putInt("PeckCooldown", this.getPeckCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLookoutCooldown(compoundTag.getInt("LookoutCooldown"));
        this.setPreenCooldown(compoundTag.getInt("PreenCooldown"));
        this.setPeckCooldown(compoundTag.getInt("PeckCooldown"));
    }

    public int getLookoutCooldown() {
        return this.entityData.get(LOOKOUT_COOLDOWN);
    }

    public void setLookoutCooldown(int cooldown) {
        this.entityData.set(LOOKOUT_COOLDOWN, cooldown);
    }

    public int getPreenCooldown() {
        return this.entityData.get(PREEN_COOLDOWN);
    }

    public void setPreenCooldown(int cooldown) {
        this.entityData.set(PREEN_COOLDOWN, cooldown);
    }

    public int getPeckCooldown() {
        return this.entityData.get(PECK_COOLDOWN);
    }

    public void setPeckCooldown(int cooldown) {
        this.entityData.set(PECK_COOLDOWN, cooldown);
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
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3, 3, 3);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return Math.sqrt(distance) < 1024.0D;
    }
}