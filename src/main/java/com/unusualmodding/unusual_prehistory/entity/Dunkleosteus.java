package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goals.GroundseekingRandomSwimGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.PrehistoricNearestAttackableTargetGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.dunkleosteus.DunkleosteusAttackGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.dunkleosteus.DunkleosteusHurtByTargetGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.dunkleosteus.DunkleosteusNearestAttackableTargetGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.dunkleosteus.DunkleosteusPanicGoal;
import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricAquaticMob;
import com.unusualmodding.unusual_prehistory.entity.utils.Behaviors;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2EntityTags;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Dunkleosteus extends PrehistoricAquaticMob {

    private static final EntityDataAccessor<Integer> DUNK_SIZE = SynchedEntityData.defineId(Dunkleosteus.class, EntityDataSerializers.INT);

    private static final EntityDimensions SMALL_SIZE = EntityDimensions.scalable(0.5F, 0.5F);
    private static final EntityDimensions MEDIUM_SIZE = EntityDimensions.scalable(0.8F, 0.98F);
    private static final EntityDimensions LARGE_SIZE = EntityDimensions.scalable(1.7F, 1.98F);

    public final AnimationState bitingAnimationState = new AnimationState();
    public final AnimationState yawningAnimationState = new AnimationState();

    private int yawningTimer = 0;

    public Dunkleosteus(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 1000, 4, 0.02F, 0.1F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 5);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new DunkleosteusPanicGoal(this));
        this.goalSelector.addGoal(2, new DunkleosteusAttackGoal(this));
        this.goalSelector.addGoal(3, new GroundseekingRandomSwimGoal(this, 1.0D, 70, 20, 20, 0.02));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DUNKLEOSTEUS_FOOD), false));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(0, new DunkleosteusHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new DunkleosteusNearestAttackableTargetGoal(this));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 300, true, true, this::canAttackPlayer));
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.horizontalCollision) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.3 * this.getSpeed(), 0.0));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void setupAnimationStates() {
        super.setupAnimationStates();
        this.bitingAnimationState.animateWhen(this.getAttackState() == 1, this.tickCount);
        this.yawningAnimationState.animateWhen(this.isYawning(), this.tickCount);
    }

    @Override
    public void setupAnimationCooldowns() {
        if (this.isInWaterOrBubble() && this.getBehavior().equals(Behaviors.IDLE.getName())) {
            if (!this.isYawning() && this.random.nextInt(600) == 0) {
                this.setYawning(true);
            }
            if (this.isYawning() && this.yawningTimer++ > 40) {
                this.yawningTimer = 0;
                this.setYawning(false);
            }
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DUNKLEOSTEUS_FOOD);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.5F;
    }

    public boolean isTarget(Entity entity) {
        if (this.getDunkSize() == 1) return entity.getType().is(UP2EntityTags.MEDIUM_DUNKLEOSTEUS_TARGETS);
        else if (this.getDunkSize() == 2) return entity.getType().is(UP2EntityTags.BIG_DUNKLEOSTEUS_TARGETS);
        else return entity.getType().is(UP2EntityTags.SMALL_DUNKLEOSTEUS_TARGETS);
    }

    public boolean canAttackPlayer(LivingEntity entity) {
        return this.canAttack(entity) && this.getDunkSize() > 0;
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if (prev && this.isPacified() && entity instanceof LivingEntity && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().getUUID().equals(entity.getUUID()))) return false;
        else return prev;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.DUNKLEOSTEUS_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.DUNKLEOSTEUS_DEATH.get();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.DUNKLEOSTEUS_FLOP.get();
    }

    public SoundEvent getBiteSound() {
        if (this.getDunkSize() == 0) return UP2SoundEvents.SMALL_DUNKLEOSTEUS_BITE.get();
        else if (this.getDunkSize() == 1) return UP2SoundEvents.MEDIUM_DUNKLEOSTEUS_BITE.get();
        else return UP2SoundEvents.LARGE_DUNKLEOSTEUS_BITE.get();
    }

    @Override
    public float getVoicePitch() {
        final float f = (3 - this.getDunkSize()) * 0.33F;
        return (float) (super.getVoicePitch() * Math.sqrt(f) * 1.2F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DUNK_SIZE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat("Size", this.getDunkSize());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setDunkSize(compoundTag.getInt("Size"));
    }

    public boolean isYawning() {
        return this.getFlag(16);
    }

    public void setYawning(boolean yawning) {
        this.setFlag(16, yawning);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DUNK_SIZE.equals(accessor)) {
            this.refreshDimensions();
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0F * this.getDunkSize() + 10.0F);
            this.getAttribute(Attributes.ARMOR).setBaseValue(2.0F * this.getDunkSize());
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.0F * this.getDunkSize() + 3.0F);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.9F - this.getDunkSize() * 0.1F);
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.1F + this.getDunkSize() * 0.3F);
            this.heal(50.0F);
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public boolean isPushable() {
        return super.isPushable() && this.getDunkSize() != 2;
    }

    public int getDunkSize() {
        return Mth.clamp(this.entityData.get(DUNK_SIZE), 0, 2);
    }

    public void setDunkSize(int dunkSize) {
        this.entityData.set(DUNK_SIZE, dunkSize);
    }

    @Override
    public int getVariantCount() {
        return 3;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return getDimsForDunk().scale(this.getScale());
    }

    private EntityDimensions getDimsForDunk() {
        return switch (this.getDunkSize()) {
            case 1 -> MEDIUM_SIZE;
            case 2 -> LARGE_SIZE;
            default -> SMALL_SIZE;
        };
    }

    public String getVariantName() {
        return switch (this.getDunkSize()) {
            case 1 -> "marsaisi";
            case 2 -> "terrelli";
            default -> "raveri";
        };
    }

    @Override
    public boolean canPacifiy() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_DUNKLEOSTEUS);
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        int sizeChange = this.random.nextInt(0, 100);
        if (sizeChange <= 30) this.setDunkSize(1);
        else if (sizeChange <= 60) this.setDunkSize(2);
        else this.setDunkSize(0);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        Dunkleosteus dunkleosteus = UP2Entities.DUNKLEOSTEUS.get().create(serverLevel);
        dunkleosteus.setDunkSize(this.getDunkSize());
        return dunkleosteus;
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return this.getDunkSize() == 0 ? new ItemStack(UP2Items.DUNKLEOSTEUS_BUCKET.get()) : ItemStack.EMPTY;
    }
}
