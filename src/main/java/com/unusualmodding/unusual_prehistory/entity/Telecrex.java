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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class Telecrex extends FlyingPrehistoricMob {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();
    public final AnimationState lookoutAnimationState = new AnimationState();
    public final AnimationState peckingAnimationState = new AnimationState();

    private int peckingTimer = 0;
    private int lookoutTimer = 0;

    public Telecrex(EntityType<? extends FlyingPrehistoricMob> entityType, Level level) {
        super(entityType, level);
        switchNavigator(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.FLYING_SPEED, 1.0F).add(Attributes.MOVEMENT_SPEED, 0.2F);
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
        this.goalSelector.addGoal(2, new RandomFlightGoal(this, 0.75F, 16, 4, 2000, 200));
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
        if (!this.isInWaterOrBubble() && !this.isFlying() && !this.isPecking() && !this.isLooking()) {
            if (this.random.nextInt(500) == 0 && this.level().getBlockState(this.blockPosition().below()).is(Blocks.GRASS_BLOCK)) {
                this.setPecking(true);
            }
            if (this.random.nextInt(600) == 0) {
                this.setLooking(true);
            }
        }
        if (this.isPecking() && this.peckingTimer++ > 40) {
            this.peckingTimer = 0;
            this.setPecking(false);
        }
        if (this.isLooking() && this.lookoutTimer++ > 40) {
            this.lookoutTimer = 0;
            this.setLooking(false);
        }
    }

    @Override
    public boolean isImmobile() {
        return super.isImmobile() || this.isPecking() || this.isLooking();
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlying() && !this.isPecking() && !this.isLooking(), this.tickCount);
        this.flyingAnimationState.animateWhen(this.isFlying(), this.tickCount);
        this.peckingAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isFlying() && this.isPecking(), this.tickCount);
        this.lookoutAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isFlying() && this.isLooking(), this.tickCount);
    }

    public boolean isPecking() {
        return this.getFlag(16);
    }

    public void setPecking(boolean pecking) {
        this.setFlag(16, pecking);
    }

    public boolean isLooking() {
        return this.getFlag(32);
    }

    public void setLooking(boolean looking) {
        this.setFlag(32, looking);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.TELECREX_FOOD);
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.PARROT_EAT;
    }

    @Override
    public boolean refuseToMove() {
        return (this.getPose() == UP2Poses.PECKING.get() || this.getPose() == UP2Poses.PREENING.get()) && super.refuseToMove();
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