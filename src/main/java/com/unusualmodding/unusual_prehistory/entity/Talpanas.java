package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goals.*;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.talpanas.TalpanasFleeLightGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.talpanas.TalpanasSeekShelterGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.talpanas.TalpanasSwimGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.unusualmodding.unusual_prehistory.entity.base.BreedableMob;
import com.unusualmodding.unusual_prehistory.entity.utils.Behaviors;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Talpanas extends BreedableMob {

    public static final EntityDataAccessor<Integer> LIGHT_THRESHOLD = SynchedEntityData.defineId(Talpanas.class, EntityDataSerializers.INT);

    public int fleeLightFor = 0;
    public Vec3 fleeFromPosition;
    private int peckingTimer = 0;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flapAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState peckingAnimationState = new AnimationState();

    public Talpanas(EntityType<? extends BreedableMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_CAUTIOUS, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_CAUTIOUS, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_CAUTIOUS, 1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.24D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 2.0F, 1.25D, 1.25D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, LivingEntity.class, 2.0F, 1.25D, 1.25D, entity -> entity.getType().is(UP2EntityTags.TALPANAS_AVOIDS)));
        this.goalSelector.addGoal(3, new TalpanasFleeLightGoal(this));
        this.goalSelector.addGoal(4, new TalpanasSeekShelterGoal(this));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.25D, Ingredient.of(UP2ItemTags.TALPANAS_FOOD), false));
        this.goalSelector.addGoal(7, new TalpanasSwimGoal(this));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        SmoothGroundPathNavigation navigation = new SmoothGroundPathNavigation(this, level);
        navigation.setAvoidSun(true);
        return navigation;
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.85F : dimensions.height * 0.92F;
    }

    @Override
    public double getFluidJumpThreshold() {
        return this.isBaby() ? 0.2D : 0.4D;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0) {
            this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
            if (this.getMovementEmission().emitsEvents()) {
                this.gameEvent(GameEvent.FLAP);
            }
        }

        BlockPos blockPos = this.blockPosition();
        RandomSource randomSource = this.getRandom();
        BlockPos pos = blockPos.offset(randomSource.nextInt(20) - 10, randomSource.nextInt(6) - 3, randomSource.nextInt(20) - 10);

        if (this.level().getBrightness(LightLayer.BLOCK, blockPos) > this.getLightThreshold()) {
            this.fleeFromPosition = Vec3.atBottomCenterOf(pos);
        }

        if (fleeLightFor > 0) {
            fleeLightFor--;
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LIGHT_THRESHOLD, 10);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("LightThreshold", this.getLightThreshold());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLightThreshold(compoundTag.getInt("LightThreshold"));
    }

    public boolean isPecking() {
        return this.getFlag(16);
    }

    public void setPecking(boolean pecking) {
        this.setFlag(16, pecking);
    }

    public int getLightThreshold() {
        return this.entityData.get(LIGHT_THRESHOLD);
    }

    public void setLightThreshold(int threshold) {
        this.entityData.set(LIGHT_THRESHOLD, threshold);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob p_146744_) {
        return UP2Entities.TALPANAS.get().create(serverLevel);
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.onGround(), this.tickCount);
        this.flapAnimationState.animateWhen(!this.onGround() && !this.isInWaterOrBubble(), this.tickCount);
        this.swimmingAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.peckingAnimationState.animateWhen(!this.isInWaterOrBubble() && this.onGround() && this.isPecking(), this.tickCount);
    }

    @Override
    public void setupAnimationCooldowns() {
        if (!this.isInWaterOrBubble() && this.onGround() && this.getBehavior().equals(Behaviors.IDLE.getName())) {
            if (!this.isPecking() && this.random.nextInt(300) == 0 && this.level().getBlockState(this.blockPosition().below()).is(Blocks.GRASS_BLOCK)) {
                this.setPecking(true);
            }
        }
        if (this.isPecking() && this.peckingTimer++ > 40) {
            this.peckingTimer = 0;
            this.setPecking(false);
        }
    }

    @Override
    public boolean isImmobile() {
        return super.isImmobile() || this.isPecking();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL) || source.is(DamageTypes.IN_WALL);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return true;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.TALPANAS_FOOD);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.TALPANAS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return UP2SoundEvents.TALPANAS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.TALPANAS_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.1F, 1.0F);
    }
}
