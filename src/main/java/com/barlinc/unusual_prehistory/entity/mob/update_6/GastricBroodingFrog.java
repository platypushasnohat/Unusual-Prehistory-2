package com.barlinc.unusual_prehistory.entity.mob.update_6;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.AmphibiousMob;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import com.barlinc.unusual_prehistory.entity.utils.MobUtils;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GastricBroodingFrog extends AmphibiousMob implements Bucketable, LeapingMob {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(GastricBroodingFrog.class, EntityDataSerializers.BOOLEAN);

    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState leapAnimationState = new SmoothAnimationState();

    public GastricBroodingFrog(EntityType<? extends AmphibiousMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.lookControl = new FrogLookControl(this);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AmphibiousPanicGoal(this, 2.0D, 10, 5, true));
        this.goalSelector.addGoal(1, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 10.0F, 2.0D, true, entity -> entity.getType().is(UP2EntityTags.DIPLOCAULUS_AVOIDS)));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_INSECTIVORE), false));
        this.goalSelector.addGoal(3, new LeaveWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new EnterWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LeapRandomlyGoal(this, 80, 6, 0.8F));
        this.goalSelector.addGoal(5, new CustomizableRandomSwimGoal(this, 1.0D, 40));
        this.goalSelector.addGoal(5, new SemiAquaticRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0F)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.STEP_HEIGHT, 1.15D);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothAmphibiousNavigation(this, level);
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        if (this.isEffectiveAi() && this.isInWater()) {
            MobUtils.travelInWater(this, travelVec);
        } else {
            super.travel(travelVec);
        }
    }

    @Override
    public boolean isPushable() {
        return !this.isSitting();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_INSECTIVORE);
    }

    public float getAgeScale() {
        return this.isBaby() ? 0.66F : 1.0F;
    }

    @Override
    public int getBaseExperienceReward() {
        return 1 + this.level().getRandom().nextInt(3);
    }

    @Override
    public boolean shouldDropExperience() {
        return !this.isBaby();
    }

    @Override
    public boolean canFallInLove() {
        return this.getInLoveTime() <= 0;
    }

    @Override
    public boolean canMate(@NotNull Animal animal) {
        return true;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.getEatTicks() <= 0 && this.isFood(itemstack) ) {
            int i = this.getAge();
            if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
                this.usePlayerItem(player, hand, itemstack);
                this.playSound(this.getEatingSound());
                this.setInLove(player);
                return InteractionResult.SUCCESS;
            }
        }
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    public void finalizeSpawnChildFromBreeding(ServerLevel level, Animal animal, @Nullable AgeableMob baby) {
        Optional.ofNullable(this.getLoveCause()).or(() -> Optional.ofNullable(animal.getLoveCause())).ifPresent((serverPlayer) -> {
            serverPlayer.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayer, this, animal, baby);
        });
        this.setAge(6000);
        animal.setAge(6000);
        this.resetLove();
        animal.resetLove();
        level.broadcastEntityEvent(this, (byte) 18);
        if (level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            level.addFreshEntity(new ExperienceOrb(level, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }
    }

    @Override
    public int getHeadRotSpeed() {
        return 35;
    }

    @Override
    public int getMaxHeadYRot() {
        return 5;
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    @Override
    public void onLeap() {
        this.setLeaping(true);
        this.level().playSound(null, this, SoundEvents.FROG_LONG_JUMP, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isLeaping() && (this.onGround() || this.isInFluidType())) {
            if (this.onGround() && !this.level().isClientSide) {
                this.level().playSound(null, this, SoundEvents.FROG_STEP, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
            this.setLeaping(false);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isSitting(), this.tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isSitting(), this.tickCount);
        this.leapAnimationState.animateWhen(this.isLeaping(), this.tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float) Mth.length(this.getX() - this.xo, includeHeight ? this.getY() - this.yo : 0.0, this.getZ() - this.zo);
        if (this.isBaby()) {
            this.updateWalkAnimation(f * 0.5F);
        } else {
            this.updateWalkAnimation(f);
        }
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f = Math.min(partialTick * 25.0F, 1.0F);
        this.walkAnimation.update(f, 0.4F);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LEAPING, false);
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    @Override
    public boolean fromBucket() {
        return false;
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.HYNERPETON_BUCKET.get());
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    @Override
    public void saveToBucketTag(@NotNull ItemStack bucket) {
        MobUtils.savePrehistoricDataToBucket(this, bucket);
    }

    @Override
    public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
        MobUtils.loadPrehistoricDataFromBucket(this, compoundTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.GASTRIC_BROODING_FROG.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.FROG_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return SoundEvents.FROG_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.FROG_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.FROG_STEP, 0.15F, 1.0F);
    }

    private static class FrogLookControl extends PrehistoricLookControl {

        public FrogLookControl(GastricBroodingFrog frog) {
            super(frog);
        }

        @Override
        protected boolean resetXRotOnTick() {
            return false;
        }
    }
}
