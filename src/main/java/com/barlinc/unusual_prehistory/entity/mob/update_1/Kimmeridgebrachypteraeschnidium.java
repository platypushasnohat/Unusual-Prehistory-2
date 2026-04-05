package com.barlinc.unusual_prehistory.entity.mob.update_1;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricFlyingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricFlyingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.FlyingPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.IdleAnimationGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.RandomFlightGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinFlyingPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("deprecation")
public class Kimmeridgebrachypteraeschnidium extends PrehistoricFlyingMob implements Bucketable {

    private static final EntityDataAccessor<Integer> BASE_COLOR = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_COLOR = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_PATTERN = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> WING_COLOR = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> PREEN_COOLDOWN = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SWELL_DURATION = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);

    private int oldSwell;
    private int swell;
    private final int maxSwell = 60;

    public final SmoothAnimationState preenAnimationState = new SmoothAnimationState();

    public Kimmeridgebrachypteraeschnidium(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricFlyingMoveControl(this);
        this.lookControl = new PrehistoricFlyingLookControl(this, 85);
        this.setPathfindingMalus(PathType.LEAVES, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.01F)
                .add(Attributes.FLYING_SPEED, 1.3F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FlyingPanicGoal(this));
        this.goalSelector.addGoal(2, new KimmeridgebrachypteraeschnidiumScatterGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_FOOD), false));
        this.goalSelector.addGoal(4, new RandomFlightGoal(this, 1.0F, 1.5F, 13, 5, 60, 600));
        this.goalSelector.addGoal(5, new KimmeridgebrachypteraeschnidiumLookAroundGoal(this));
        this.goalSelector.addGoal(6, new KimmeridgebrachypteraeschnidiumPreenGoal(this));
    }

//    @Override
//    public boolean canBreatheUnderwater() {
//        return true;
//    }
//
//    @Override
//    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
//        return dimensions.height * 0.6F;
//    }

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

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() || this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_FOOD);
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (this.getPreenCooldown() > 0 && !this.level().isClientSide) this.setPreenCooldown(this.getPreenCooldown() - 1);
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlying() && this.getIdleState() != 1, this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying(), this.tickCount);
        this.preenAnimationState.animateWhen(!this.isFlying() && this.getIdleState() == 1, this.tickCount);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isInWaterOrBubble()) {
            this.setFlying(true);
        }

        // It is imperative that the name be changed
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            if (this.hasCustomName() && this.getName().getString().contains("draconoptera")) {
                this.setSwellDuration(1);
            }
            int i = this.getSwellDuration();
            if (i > 0 && this.swell == 0) {
                this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                this.gameEvent(GameEvent.PRIME_FUSE);
            }

            this.swell += i;
            if (this.swell < 0) {
                this.swell = 0;
            }

            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                this.explode();
            }
        }

        if (this.level().isClientSide && this.isAlive()) UnusualPrehistory2.PROXY.playWorldSound(this, (byte) 1);
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

    private void explode() {
        if (!this.level().isClientSide) {
            this.dead = true;
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), (float) 7, Level.ExplosionInteraction.MOB);
            this.discard();
        }
    }

    public float getSwelling(float partialTicks) {
        return Mth.lerp(partialTicks, (float) this.oldSwell, (float) this.swell) / (float) (this.maxSwell - 2);
    }

    @Override
    public void remove(Entity.@NotNull RemovalReason removalReason) {
        UnusualPrehistory2.PROXY.clearSoundCacheFor(this);
        super.remove(removalReason);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == Items.GLASS_BOTTLE && this.isAlive()) {
            playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 0.5F, 1.0F);
            itemstack.shrink(1);
            ItemStack bottle = new ItemStack(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get());
            this.saveToBucketTag(bottle);
            if (!this.level().isClientSide) CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bottle);
            if (itemstack.isEmpty() && !player.isCreative()) player.setItemInHand(hand, bottle);
            else if (!player.getInventory().add(bottle)) player.drop(bottle, false);
            this.discard();
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() && this.getIdleState() == 1;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FROM_BUCKET, false);
        builder.define(BASE_COLOR, 0);
        builder.define(PATTERN, 0);
        builder.define(PATTERN_COLOR, 0);
        builder.define(HAS_PATTERN, false);
        builder.define(WING_COLOR, 0);
        builder.define(PREEN_COOLDOWN, 20 + random.nextInt(10 * 20));
        builder.define(SWELL_DURATION, -1);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("BaseColor", this.getBaseColor());
        compoundTag.putInt("Pattern", this.getPattern());
        compoundTag.putInt("PatternColor", this.getPatternColor());
        compoundTag.putInt("WingColor", this.getWingColor());
        compoundTag.putBoolean("HasPattern", this.getHasPattern());
        compoundTag.putInt("PreenCooldown", this.getPreenCooldown());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setBaseColor(compoundTag.getInt("BaseColor"));
        this.setPattern(compoundTag.getInt("Pattern"));
        this.setPatternColor(compoundTag.getInt("PatternColor"));
        this.setWingColor(compoundTag.getInt("WingColor"));
        this.setHasPattern(compoundTag.getBoolean("HasPattern"));
        this.setPreenCooldown(compoundTag.getInt("PreenCooldown"));
    }

    @Override
    public void saveToBucketTag(@NotNull ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, (compoundTag) -> {
            compoundTag.putFloat("Health", this.getHealth());
            compoundTag.putInt("BaseColor", this.getBaseColor());
            compoundTag.putInt("Pattern", this.getPattern());
            compoundTag.putInt("PatternColor", this.getPatternColor());
            compoundTag.putInt("WingColor", this.getWingColor());
            compoundTag.putBoolean("HasPattern", this.getHasPattern());
            compoundTag.putInt("Age", this.getAge());
        });
    }

    public static String getPatternName(int pattern) {
        return switch (pattern){
            case 1 -> "tailshade";
            case 2 -> "topshade";
            case 3 -> "halfshade";
            case 4 -> "large_stripe";
            case 5 -> "racing_stripe";
            case 6 -> "large_racing_stripe";
            default -> "stripe";
        };
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        if (spawnType == MobSpawnType.BUCKET) {
            return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
        } else {
            this.setBaseColor(this.random.nextInt(16));
            this.setPattern(this.random.nextInt(7));
            this.setPatternColor(this.random.nextInt(16));
            this.setWingColor(this.random.nextInt(16));
            this.setHasPattern(this.random.nextInt(3) == 0);
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }

    @Override
    public void loadFromBucketTag(@NotNull CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get());
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.BOTTLE_FILL_DRAGONBREATH;
    }

    public int getBaseColor() {
        return this.entityData.get(BASE_COLOR);
    }

    public void setBaseColor(int variant) {
        this.entityData.set(BASE_COLOR, Mth.clamp(variant, 0, 15));
    }

    public int getPattern() {
        return this.entityData.get(PATTERN);
    }

    public void setPattern(int variant) {
        this.entityData.set(PATTERN, Mth.clamp(variant, 0, 6));
    }

    public int getPatternColor() {
        return this.entityData.get(PATTERN_COLOR);
    }

    public void setPatternColor(int variant) {
        this.entityData.set(PATTERN_COLOR, Mth.clamp(variant, 0, 15));
    }

    public int getWingColor() {
        return this.entityData.get(WING_COLOR);
    }

    public void setWingColor(int variant) {
        this.entityData.set(WING_COLOR, Mth.clamp(variant, 0, 15));
    }

    public boolean getHasPattern() {
        return this.entityData.get(HAS_PATTERN);
    }

    public void setHasPattern(boolean hasPattern) {
        this.entityData.set(HAS_PATTERN, hasPattern);
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    public int getPreenCooldown() {
        return this.entityData.get(PREEN_COOLDOWN);
    }

    public void setPreenCooldown(int cooldown) {
        this.entityData.set(PREEN_COOLDOWN, cooldown);
    }

    public int getSwellDuration() {
        return this.entityData.get(SWELL_DURATION);
    }

    public void setSwellDuration(int duration) {
        this.entityData.set(SWELL_DURATION, duration);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL) || super.isInvulnerableTo(source);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return null;
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
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
    }

    @Override
    protected float getSoundVolume() {
        return 0.5F;
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3, 3, 3);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return Math.sqrt(distance) < 1024.0D;
    }

    // Goals
    private static class KimmeridgebrachypteraeschnidiumScatterGoal extends Goal {

        private final Kimmeridgebrachypteraeschnidium dragonfly;

        public KimmeridgebrachypteraeschnidiumScatterGoal(Kimmeridgebrachypteraeschnidium dragonfly) {
            this.dragonfly = dragonfly;
        }

        @Override
        public boolean canUse() {
            if (dragonfly.isFlying()) {
                return false;
            }
            long worldTime = dragonfly.level().getGameTime() % 10;
            if (dragonfly.getRandom().nextInt(10) != 0 && worldTime != 0) {
                return false;
            }
            AABB aabb = dragonfly.getBoundingBox().inflate(4);
            List<Entity> list = dragonfly.level().getEntitiesOfClass(Entity.class, aabb, (entity -> entity.getType().is(UP2EntityTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_AVOIDS) || entity instanceof Player && !((Player) entity).isCreative()));
            return !list.isEmpty();
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            this.dragonfly.setFlying(true);
            if (dragonfly.onGround()) {
                this.dragonfly.setDeltaMovement(dragonfly.getDeltaMovement().add(0.0D, 0.5D, 0.0D));
            }
        }
    }

    private static class KimmeridgebrachypteraeschnidiumLookAroundGoal extends RandomLookAroundGoal {

        private final Kimmeridgebrachypteraeschnidium dragonfly;

        public KimmeridgebrachypteraeschnidiumLookAroundGoal(Kimmeridgebrachypteraeschnidium dragonfly) {
            super(dragonfly);
            this.dragonfly = dragonfly;
        }

        @Override
        public boolean canUse() {
            return this.dragonfly.onGround() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return this.dragonfly.onGround() && super.canContinueToUse();
        }
    }

    private static class KimmeridgebrachypteraeschnidiumPreenGoal extends IdleAnimationGoal {

        private final Kimmeridgebrachypteraeschnidium kimmeridgebrachypteraeschnidium;

        public KimmeridgebrachypteraeschnidiumPreenGoal(Kimmeridgebrachypteraeschnidium kimmeridgebrachypteraeschnidium) {
            super(kimmeridgebrachypteraeschnidium, 60, 1);
            this.kimmeridgebrachypteraeschnidium = kimmeridgebrachypteraeschnidium;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && kimmeridgebrachypteraeschnidium.getPreenCooldown() == 0 && !kimmeridgebrachypteraeschnidium.isFlying() && kimmeridgebrachypteraeschnidium.onGround();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !kimmeridgebrachypteraeschnidium.isFlying() && kimmeridgebrachypteraeschnidium.onGround();
        }

        @Override
        public void stop() {
            super.stop();
            this.kimmeridgebrachypteraeschnidium.setPreenCooldown(20 + kimmeridgebrachypteraeschnidium.getRandom().nextInt(10 * 20));
        }
    }
}
