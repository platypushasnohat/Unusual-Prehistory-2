package com.barlinc.unusual_prehistory.entity.mob.update_4;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricFlyingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricFlyingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.FlyingPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.update_4.PterodactylusFlyAndHangGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinFlyingPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("deprecation")
public class Pterodactylus extends PrehistoricFlyingMob implements Bucketable {

    private static final EntityDataAccessor<Boolean> HANGING = SynchedEntityData.defineId(Pterodactylus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Pterodactylus.class, EntityDataSerializers.BOOLEAN);

    private boolean validHangingPos = false;
    private int checkHangingTime;
    private BlockPos prevHangPos;
    public int timeHanging = 0;

    public final SmoothAnimationState hangIdleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState stretchAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState hangingStretchAnimationState = new SmoothAnimationState();

    public Pterodactylus(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricFlyingMoveControl(this);
        this.lookControl = new PrehistoricFlyingLookControl(this, 85);
        this.setPathfindingMalus(PathType.LEAVES, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.FLYING_SPEED, 1.1F)
                .add(Attributes.MOVEMENT_SPEED, 0.01F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FlyingPanicGoal(this));
        this.goalSelector.addGoal(2, new PterodactylusScatterGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.PTERODACTYLUS_FOOD), true));
        this.goalSelector.addGoal(4, new PterodactylusFlyAndHangGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
//        this.goalSelector.addGoal(7, new PterodactylusStretchGoal(this));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new NoSpinFlyingPathNavigation(this, level);
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public void switchNavigator(boolean onLand) {
    }

//    @Override
//    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
//        return dimensions.height * 0.7F;
//    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() || this.onGround() || this.isHanging()) {
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
        if (hurt && source.getEntity() != null && this.isAlive() && source.getEntity() instanceof LivingEntity livingEntity) {
            double range = 8;
            List<? extends Pterodactylus> entities = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(range, range / 2, range));
            for (Pterodactylus pterodactylus : entities) {
                pterodactylus.setLastHurtByMob(livingEntity);
            }
        }
        return hurt;
    }

    public boolean canHangFrom(BlockPos pos, BlockState state) {
        return state.isFaceSturdy(level(), pos, Direction.DOWN) && level().isEmptyBlock(pos.below()) && level().isEmptyBlock(pos.below(2));
    }

    public BlockPos posAbove() {
        return BlockPos.containing(this.getX(), this.getBoundingBox().maxY + 0.1F, this.getZ());
    }

    @Override
    protected void doPush(@NotNull Entity entity) {
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    protected Entity.@NotNull MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    protected void doWaterSplashEffect() {
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) this.tickHanging();

        if (this.isInWaterOrBubble()) {
            this.setFlying(true);
        }
    }

    private void tickHanging() {
        if (this.isHanging()) {
            BlockPos above = posAbove();
            if (checkHangingTime-- < 0 || random.nextFloat() < 0.1F || prevHangPos != above) {
                this.validHangingPos = this.canHangFrom(above, level().getBlockState(above));
                this.checkHangingTime = 5 + random.nextInt(5);
                this.prevHangPos = above;
            }
            if (validHangingPos) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.1F, 0.3F, 0.1F).add(0, 0.08D, 0));
            } else {
                this.setHanging(false);
                this.setFlying(true);
            }
            this.timeHanging++;
            if (this.isHanging() && timeHanging > 800) {
                this.setFlying(true);
                this.setHanging(false);
            }
        } else {
            this.timeHanging = 0;
            this.validHangingPos = false;
            this.prevHangPos = null;
        }
    }

    @Override
    public void tickFlight() {
        if (this.isFlying() && flyProgress < 5F) this.flyProgress++;
        if (!this.isFlying() && flyProgress > 0F) this.flyProgress--;

        if (this.isFlying()) {
            this.flightTicks++;
            this.setNoGravity(true);
            if (groundTicks > 0) this.setFlying(false);
        } else {
            this.flightTicks = 0;
            this.setNoGravity(false);
        }
        if (groundTicks > 0) groundTicks--;

        if (!level().isClientSide) {
            if (this.isFlying() && this.isAlive() && !this.isVehicle()) {
                if (landingFlag) this.setDeltaMovement(this.getDeltaMovement().add(0, -0.1D, 0));
                if (horizontalCollision && !landingFlag) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, 0.05D, 0));
                }
            }
            if (this.isFlying() && flightTicks > 40 && this.onGround()) this.setFlying(false);
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1;
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlying() && !this.isHanging(), this.tickCount);
        this.hangIdleAnimationState.animateWhen(!this.isFlying() && this.isHanging(), this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying() && !this.isRunning() && !this.isHanging(), this.tickCount);
        this.flyFastAnimationState.animateWhen(this.isFlying() && this.isRunning() && !this.isHanging(), this.tickCount);
        this.stretchAnimationState.animateWhen(this.getIdleState() == 1 && !this.isHanging(), this.tickCount);
        this.hangingStretchAnimationState.animateWhen(this.getIdleState() == 1 && this.isHanging(), this.tickCount);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.PTERODACTYLUS_FOOD);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == Items.FLOWER_POT && this.isAlive()) {
            playSound(SoundEvents.ITEM_PICKUP, 0.5F, 1.1F);
            itemstack.shrink(1);
            ItemStack pot = new ItemStack(UP2Items.PTERODACTYLUS_POT.get());
            this.saveToBucketTag(pot);
            if (!this.level().isClientSide) CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, pot);
            if (itemstack.isEmpty() && !player.isCreative()) player.setItemInHand(hand, pot);
            else if (!player.getInventory().add(pot)) player.drop(pot, false);
            this.discard();
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HANGING, false);
        builder.define(FROM_BUCKET, false);
    }

    @Override
    public void saveToBucketTag(@NotNull ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, (compoundTag) -> {
            compoundTag.putInt("BucketVariantTag", this.getVariant());
            compoundTag.putInt("Age", this.getAge());
            compoundTag.putInt("PacifiedTicks", this.getPacifiedTicks());
            compoundTag.putBoolean("FromEgg", this.isFromEgg());
            compoundTag.putInt("EatingCooldown", this.getEatCooldown());
        });
    }

    @Override
    public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);
        if (compoundTag.contains("BucketVariantTag", 3)) {
            this.setVariant(compoundTag.getInt("BucketVariantTag"));
        }
        this.setAge(compoundTag.getInt("Age"));
        this.setPacifiedTicks(compoundTag.getInt("PacifiedTicks"));
        this.setFromEgg(compoundTag.getBoolean("FromEgg"));
        this.setEatCooldown(compoundTag.getInt("EatingCooldown"));
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.PTERODACTYLUS_POT.get());
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
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.ITEM_PICKUP;
    }

    public boolean isHanging() {
        return this.entityData.get(HANGING);
    }

    public void setHanging(boolean hanging) {
        this.entityData.set(HANGING, hanging);
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.PARROT_EAT;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.PTERODACTYLUS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.PTERODACTYLUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.PTERODACTYLUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    }

    @Override
    public int getAmbientSoundInterval() {
        return 150;
    }

    @Override
    protected float getSoundVolume() {
        return 0.8F;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        Pterodactylus pterodactylus = UP2Entities.PTERODACTYLUS.get().create(level);
        if (pterodactylus != null) {
            pterodactylus.setVariant(this.getVariant());
        }
        return pterodactylus;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3, 3, 3);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return Math.sqrt(distance) < 1024.0D;
    }

    public enum PterodactylusVariant {
        BROWN(0),
        BANANA(1);

        private final int id;

        PterodactylusVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static PterodactylusVariant byId(int id) {
            if (id < 0 || id >= PterodactylusVariant.values().length) {
                id = 0;
            }
            return PterodactylusVariant.values()[id];
        }
    }

    @Override
    public int getVariantCount() {
        return PterodactylusVariant.values().length;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
        if (spawnType == MobSpawnType.BUCKET) {
            return spawnData;
        } else {
            this.setVariant(random.nextInt(PterodactylusVariant.values().length));
        }
        return spawnData;
    }

    // Goals
    private static class PterodactylusScatterGoal extends Goal {

        private final Pterodactylus pterodactylus;

        public PterodactylusScatterGoal(Pterodactylus pterodactylus) {
            this.pterodactylus = pterodactylus;
        }

        @Override
        public boolean canUse() {
            if (pterodactylus.isFlying()) {
                return false;
            }
            long worldTime = pterodactylus.level().getGameTime() % 10;
            if (pterodactylus.getRandom().nextInt(10) != 0 && worldTime != 0) {
                return false;
            }
            AABB aabb = pterodactylus.getBoundingBox().inflate(6);
            List<Entity> list = pterodactylus.level().getEntitiesOfClass(Entity.class, aabb, (entity -> entity.getType().is(UP2EntityTags.TELECREX_AVOIDS) || entity instanceof Player && !((Player) entity).isCreative()));
            return !list.isEmpty();
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            if (pterodactylus.isHanging()) {
                pterodactylus.setHanging(false);
            }
            this.pterodactylus.setFlying(true);
            if (pterodactylus.onGround()) {
                this.pterodactylus.setDeltaMovement(pterodactylus.getDeltaMovement().add(0.0D, 0.5D, 0.0D));
            }
        }
    }

//    private static class PterodactylusStretchGoal extends IdleAnimationGoal {
//
//        private final Pterodactylus pterodactylus;
//
//        public PterodactylusStretchGoal(Pterodactylus pterodactylus) {
//            super(pterodactylus, 40, 1);
//            this.pterodactylus = pterodactylus;
//        }
//
//        @Override
//        public boolean canUse() {
//            return super.canUse() && pterodactylus.stretchCooldown == 0 && (pterodactylus.onGround() || pterodactylus.isHanging()) && !pterodactylus.isFlying();
//        }
//
//        @Override
//        public boolean canContinueToUse() {
//            return super.canContinueToUse() && !pterodactylus.isFlying();
//        }
//
//        @Override
//        public void stop() {
//            super.stop();
//            this.pterodactylus.stretchCooldown();
//        }
//    }
}