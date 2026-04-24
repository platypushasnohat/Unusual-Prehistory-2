package com.barlinc.unusual_prehistory.entity.mob.update_1;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricFlyingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.FlyingPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.FlyingRandomLookAroundGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.IdleAnimationGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinFlyingPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.WallAttachingFlyingMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class Kimmeridgebrachypteraeschnidium extends WallAttachingFlyingMob implements Bucketable {

    private static final EntityDataAccessor<Integer> BASE_COLOR = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_COLOR = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_PATTERN = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> WING_COLOR = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SWELL_DURATION = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);

    private int oldSwell;
    private int swell;
    private final int maxSwell = 60;

    public final SmoothAnimationState attachedAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState preenAnimationState = new SmoothAnimationState(1.0F);

    public Kimmeridgebrachypteraeschnidium(EntityType<? extends WallAttachingFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.LEAVES, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.01F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FlyingPanicGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_FOOD), false));
        this.goalSelector.addGoal(3, new LandOrAttachFromFlightGoal(this, 300));
        this.goalSelector.addGoal(4, new KimmeridgebrachypteraeschnidiumFlightGoal(this));
        this.goalSelector.addGoal(5, new FlyingRandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 60, 1, true, 0.001F, this::canPreen));
    }

    @Override
    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new PrehistoricMoveControl(this);
            this.navigation = this.createNavigation(this.level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new PrehistoricFlyingMoveControl(this, 20);
            NoSpinFlyingPathNavigation flyingPathNavigation = new NoSpinFlyingPathNavigation(this, this.level()){
                @Override
                public boolean isStableDestination(BlockPos blockPos) {
                    return !level().getBlockState(blockPos.below()).isAir();
                }
            };
            flyingPathNavigation.setCanOpenDoors(false);
            flyingPathNavigation.setCanFloat(false);
            flyingPathNavigation.setCanPassDoors(true);
            this.navigation = flyingPathNavigation;
            this.isLandNavigator = false;
        }
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
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
    protected boolean shouldUseStuckTicks() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        // It is imperative that the name be changed
        this.tickDraconoptera();

        if (this.level().isClientSide && this.isAlive()) {
            UnusualPrehistory2.PROXY.playWorldSound(this, (byte) 1);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlying() && this.getIdleState() != 1 && !this.isAttachedToFace(), this.tickCount);
        this.hoverAnimationState.animateWhen(this.isFlying() && this.getDeltaMovement().length() <= 0.1D, this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying() && this.getDeltaMovement().length() > 0.1D, this.tickCount);
        this.preenAnimationState.animateWhen(!this.isFlying() && this.getIdleState() == 1, this.tickCount);
        this.attachedAnimationState.animateWhen(!this.isFlying() && this.isAttachedToFace(), this.tickCount);
    }

    private boolean canPreen(Entity entity) {
        if (entity instanceof Kimmeridgebrachypteraeschnidium dragonfly) {
            return dragonfly.onGround() && !dragonfly.isFlying() && !dragonfly.isAttachedToFace();
        }
        return false;
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 800 + this.getRandom().nextInt(1200);
        } else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    private void tickDraconoptera() {
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
            if (!this.level().isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bottle);
            }
            if (itemstack.isEmpty() && !player.isCreative()) {
                player.setItemInHand(hand, bottle);
            } else if (!player.getInventory().add(bottle)) {
                player.drop(bottle, false);
            }
            this.discard();
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || (this.onGround() && !this.isFlying());
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
        builder.define(SWELL_DURATION, -1);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("BaseColor", this.getBaseColor());
        compoundTag.putInt("Pattern", this.getPattern());
        compoundTag.putInt("PatternColor", this.getPatternColor());
        compoundTag.putInt("WingColor", this.getWingColor());
        compoundTag.putBoolean("HasPattern", this.hasPattern());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setBaseColor(compoundTag.getInt("BaseColor"));
        this.setPattern(compoundTag.getInt("Pattern"));
        this.setPatternColor(compoundTag.getInt("PatternColor"));
        this.setWingColor(compoundTag.getInt("WingColor"));
        this.setHasPattern(compoundTag.getBoolean("HasPattern"));
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
            compoundTag.putBoolean("HasPattern", this.hasPattern());
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

    public boolean hasPattern() {
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

    public int getSwellDuration() {
        return this.entityData.get(SWELL_DURATION);
    }

    public void setSwellDuration(int duration) {
        this.entityData.set(SWELL_DURATION, duration);
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
    protected void doWaterSplashEffect() {
    }

    @Override
    protected Entity.@NotNull MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
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

    // Goals
    private static class KimmeridgebrachypteraeschnidiumFlightGoal extends AttachingRandomFlightGoal {

        private final Kimmeridgebrachypteraeschnidium dragonfly;

        public KimmeridgebrachypteraeschnidiumFlightGoal(Kimmeridgebrachypteraeschnidium dragonfly) {
            super(dragonfly, 1.0F, 5);
            this.dragonfly = dragonfly;
        }

        @Override
        public boolean canUse() {
            if (dragonfly.isAttachedToFace()) {
                return false;
            } else if (dragonfly.isEepy() || dragonfly.isVehicle() || (dragonfly.getTarget() != null && dragonfly.getTarget().isAlive()) || dragonfly.getIdleState() != 0) {
                return false;
            } else if (pathCooldown-- > 0) {
                return false;
            } else if (dragonfly.canFly() && !dragonfly.isPassenger()) {
                if (!dragonfly.isFlying() && dragonfly.getRandom().nextInt(200) != 0 && dragonfly.fallDistance < 2.0F) {
                    return false;
                } else {
                    Vec3 target = this.findFlightPos();
                    this.x = target.x;
                    this.y = target.y;
                    this.z = target.z;
                    return true;
                }
            } else {
                return false;
            }
        }

        @Override
        public void start() {
            super.start();
            this.pathCooldown = 15 + dragonfly.getRandom().nextInt(12);
        }

        @Override
        protected Vec3 findFlightPos() {
            Vec3 target = dragonfly.position().add(dragonfly.getRandom().nextInt(7 * 2) - 7, 0, dragonfly.getRandom().nextInt(7 * 2) - 7);
            target = this.adjustFlightHeight(target);
            return this.clipFlightTarget(target);
        }
    }
}
