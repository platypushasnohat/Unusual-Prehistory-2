package com.barlinc.unusual_prehistory.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.AmphibiousMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Hesperornis extends AmphibiousMob {

    private static final EntityDataAccessor<Float> Z_ROT = SynchedEntityData.defineId(Hesperornis.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Z_ROT_OLD = SynchedEntityData.defineId(Hesperornis.class, EntityDataSerializers.FLOAT);

    private int attackCooldown = 0;
    private float targetZRot;

    private float swimYaw;
    private float prevSwimYaw;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();

    public Hesperornis(EntityType<? extends Hesperornis> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 2.0F)
                .add(Attributes.STEP_HEIGHT, 1.2D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 1.8D, 10, 8));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new LeaveWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new EnterWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new SemiAquaticWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new PrehistoricSwimGoal(this, 1.0D, 20));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothAmphibiousNavigation(this, level);
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new PrehistoricMoveControl(this);
            this.lookControl = new PrehistoricLookControl(this);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 6, 0.4F);
            this.lookControl = new PrehistoricSwimmingLookControl(this, 4);
            this.isLandNavigator = false;
        }
    }

    public float getSwimYaw(float partialTicks) {
        if (this.isPassenger()) {
            return 0.0F;
        } else {
            return (prevSwimYaw + (swimYaw - prevSwimYaw) * partialTicks);
        }
    }

    @Override
    public void tick() {
        super.tick();
        final boolean ground = !this.isInWaterOrBubble();
        if (!ground && isLandNavigator) {
            this.switchNavigator(false);
        }
        if (ground && !isLandNavigator) {
            this.switchNavigator(true);
        }

        if (!this.level().isClientSide && this.isAlive()) {
            if (this.isInWaterOrBubble()) {
                float yawDelta = Mth.wrapDegrees(this.getYRot() - yRotO);
                this.targetZRot = Mth.clamp(-yawDelta * 2.5F, -45.0F, 45.0F);
            } else {
                this.targetZRot = 0.0F;
            }
            this.setZWaterDirection(Mth.lerp(0.2F, this.getZWaterDirection(), targetZRot));
        }

        if (!this.level().isClientSide && this.isAlive() && this.isEffectiveAi()) {
            this.setZWaterDirectionOld(this.getZWaterDirection());
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble(), tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), tickCount);
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (!this.level().isClientSide) {
            if (attackCooldown > 0) {
                this.attackCooldown--;
            }
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(Z_ROT, 0.0F);
        builder.define(Z_ROT_OLD, 0.0F);
    }

    public float getZWaterDirection() {
        return entityData.get(Z_ROT);
    }
    public void setZWaterDirection(float zRot) {
        this.entityData.set(Z_ROT, zRot);
    }

    public float getZWaterDirectionOld() {
        return entityData.get(Z_ROT_OLD);
    }
    public void setZWaterDirectionOld(float zRot) {
        this.entityData.set(Z_ROT_OLD, zRot);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.HESPERORNIS.get().create(level);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.HESPERORNIS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.HESPERORNIS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.HESPERORNIS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        if (this.isInWaterOrBubble()) {
            return;
        }
        this.playSound(UP2SoundEvents.HESPERORNIS_STEP.get(), 0.25F, 1.0F);
    }
}
