package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.EnterWaterGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricAvoidEntityGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class KimmeridgebrachypteraeschnidiumNymph extends SemiAquaticMob implements Bucketable {

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumNymph.class, EntityDataSerializers.BOOLEAN);

    public static int ticksToBeDragonfly = Math.abs(-24000);

    protected int nymphAge;

    public KimmeridgebrachypteraeschnidiumNymph(EntityType<? extends SemiAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.16F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LargePanicGoal(this, 1.7D, 10, 4, true));
        this.goalSelector.addGoal(1, new EnterWaterGoal(this, 1.0D, 400));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_FOOD), false));
        this.goalSelector.addGoal(3, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 6.0F, 1.7D, entity -> entity.getType().is(UP2EntityTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_AVOIDS)));
        this.goalSelector.addGoal(3, new PrehistoricAvoidEntityGoal<>(this, Player.class, 6.0F, 1.7D));
        this.goalSelector.addGoal(4, new PrehistoricRandomStrollGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    @Override
    public @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothGroundPathNavigation(this, level);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    @Override
    public float getStepHeight() {
        return this.isInWaterOrBubble() ? 1.0F : 0.6F;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_FOOD);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isFood(itemstack)) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            this.increaseNymphAge(AgeableMob.getSpeedUpSecondsWhenFeeding(this.getTicksLeftUntilAdult()));
            this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
        }
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.isEffectiveAi() && this.isInWaterOrBubble()) {
            this.moveRelative(this.getSpeed(), travelVec);
            Vec3 delta = this.getDeltaMovement();
            this.move(MoverType.SELF, delta);
            if (this.jumping || horizontalCollision) {
                delta = delta.add(0, 0.1F, 0);
            } else {
                delta = delta.add(0, -0.05F, 0);
            }
            this.setDeltaMovement(delta.multiply(0.125D, 1.0D, 0.125D));
        } else {
            super.travel(travelVec);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            this.setNymphAge(nymphAge + 1);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("NymphAge", this.nymphAge);
        compoundTag.putBoolean("FromBucket", this.fromBucket());
    }

    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setNymphAge(compoundTag.getInt("NymphAge"));
        this.setFromBucket(compoundTag.getBoolean("FromBucket"));
    }

    public int getNymphAge() {
        return this.nymphAge;
    }

    public void increaseNymphAge(int offset) {
        this.setNymphAge(this.nymphAge + offset * 20);
    }

    public void setNymphAge(int age) {
        this.nymphAge = age;
        if (this.nymphAge >= ticksToBeDragonfly) {
            this.becomeDragonfly();
        }
    }

    private void becomeDragonfly() {
        if (this.level() instanceof ServerLevel serverLevel) {
            Kimmeridgebrachypteraeschnidium dragonfly = UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get().create(serverLevel);
            if (dragonfly != null) {
                dragonfly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                dragonfly.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(dragonfly.blockPosition()), MobSpawnType.CONVERSION, null, null);
                dragonfly.setNoAi(this.isNoAi());
                if (this.hasCustomName()) {
                    dragonfly.setCustomName(this.getCustomName());
                    dragonfly.setCustomNameVisible(this.isCustomNameVisible());
                }
                dragonfly.setPersistenceRequired();
                serverLevel.addFreshEntityWithPassengers(dragonfly);
                this.discard();
            }
        }
    }

    private int getTicksLeftUntilAdult() {
        return Math.max(0, ticksToBeDragonfly - this.nymphAge);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public void setBaby(boolean baby) {
    }

    @Override
    protected void ageBoundaryReached() {
    }

    @Override
    public int getAge() {
        return 1;
    }

    @Override
    public void ageUp(int amount, boolean forced) {
    }

    @Override
    public void ageUp(int amount) {
    }

    @Override
    public void setAge(int age) {
    }

    @Override
    @NotNull
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.SILVERFISH_STEP, 0.05F, 1.8F);
    }

    @Override
    protected float getSoundVolume() {
        return 0.5F;
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundTag = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundTag.putFloat("Health", this.getHealth());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
        compoundTag.putInt("NymphAge", this.getNymphAge());
    }

    @Override
    public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);
        if (compoundTag.contains("NymphAge")) {
            this.setNymphAge(compoundTag.getInt("NymphAge"));
        }
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET.get());
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    public static boolean canSpawn(EntityType<KimmeridgebrachypteraeschnidiumNymph> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        int seaLevel = level.getSeaLevel();
        int minHeight = seaLevel - 13;
        return pos.getY() >= minHeight && pos.getY() <= seaLevel && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }

    @Override
    public boolean requiresCustomPersistence() {
        return (this.getSpawnType() != MobSpawnType.CHUNK_GENERATION && this.getSpawnType() != MobSpawnType.NATURAL) || this.isFromEgg();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToPlayer) {
        return !this.requiresCustomPersistence();
    }
}
