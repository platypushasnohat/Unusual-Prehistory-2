package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.DunkleosteusAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.GroundSeekingRandomSwimGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricNearestAttackableTargetGoal;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
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
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class Dunkleosteus extends PrehistoricAquaticMob {

    private static final EntityDimensions SMALL_SIZE = EntityDimensions.scalable(0.5F, 0.5F);
    private static final EntityDimensions MEDIUM_SIZE = EntityDimensions.scalable(0.8F, 0.98F);
    private static final EntityDimensions LARGE_SIZE = EntityDimensions.scalable(1.7F, 1.98F);

    public final AnimationState bitingAnimationState = new AnimationState();
    public final AnimationState quirkAnimationState = new AnimationState();

    private int biteTicks;
    private final byte QUIRK = 67;

    public Dunkleosteus(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 20, 4, 0.02F, 0.1F, false);
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
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D, 10, 4) {
            public boolean canUse() {
                return super.canUse() && Dunkleosteus.this.getVariant() == 0 || Dunkleosteus.this.isBaby();
            }
        });
        this.goalSelector.addGoal(2, new DunkleosteusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DUNKLEOSTEUS_FOOD), false));
        this.goalSelector.addGoal(4, new GroundSeekingRandomSwimGoal(this, 1.0D, 70, 10, 7));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this) {
            public boolean canUse() {
                return super.canUse() && Dunkleosteus.this.getVariant() != 0;
            }
        });
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, this::isTarget));

        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 300, true, true, this::canAttackPlayer));
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.horizontalCollision && this.isEyeInFluid(FluidTags.WATER) && this.isPathFinding()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.005, 0.0));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void setupAnimationCooldowns() {
        if (this.biteTicks > 0) biteTicks--;
        if (this.biteTicks == 0 && this.getPose() == UP2Poses.BITING.get()) this.setPose(Pose.STANDING);
        if (this.isInWaterOrBubble() && this.getBehavior().equals(Behaviors.IDLE.getName()) && !this.quirkAnimationState.isStarted() && this.random.nextInt(600) == 0) {
            this.level().broadcastEntityEvent(this, this.QUIRK);
        }
    }

    @Override
    public void setupAnimationStates() {
        super.setupAnimationStates();
        if (this.biteTicks == 0 && this.bitingAnimationState.isStarted()) this.bitingAnimationState.stop();
        this.bitingAnimationState.animateWhen(this.getAttackState() == 1, this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (VARIANT.equals(accessor)) {
            this.refreshDimensions();
            this.setupSizeAttributes();
        }
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.BITING.get()) {
                this.bitingAnimationState.start(this.tickCount);
                this.biteTicks = 10;
            }
            else {
                this.bitingAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    private void setupSizeAttributes() {
        if (this.getVariant() == 0) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(2.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.9F);
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
        }
        if (this.getVariant() == 1) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(24.0D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(4.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.8F);
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.25D);
        }
        if (this.getVariant() == 2) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40.0D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(10.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.6F);
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
        }
        this.heal(50.0F);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == this.QUIRK) this.quirkAnimationState.start(this.tickCount);
        else super.handleEntityEvent(id);
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
        if (this.getVariant() == 1) return entity.getType().is(UP2EntityTags.MEDIUM_DUNKLEOSTEUS_TARGETS);
        else if (this.getVariant() == 2) return entity.getType().is(UP2EntityTags.BIG_DUNKLEOSTEUS_TARGETS);
        else return entity.getType().is(UP2EntityTags.SMALL_DUNKLEOSTEUS_TARGETS);
    }

    public boolean canAttackPlayer(LivingEntity entity) {
        return this.canAttack(entity) && this.getVariant() > 0;
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
        if (this.getVariant() == 0) return UP2SoundEvents.SMALL_DUNKLEOSTEUS_BITE.get();
        else if (this.getVariant() == 1) return UP2SoundEvents.MEDIUM_DUNKLEOSTEUS_BITE.get();
        else return UP2SoundEvents.LARGE_DUNKLEOSTEUS_BITE.get();
    }

    @Override
    public float getVoicePitch() {
        final float size = (3 - this.getVariant()) * 0.33F;
        return (float) (super.getVoicePitch() * Math.sqrt(size) * 1.2F);
    }

    @Override
    public boolean isPushable() {
        return super.isPushable() && this.getVariant() != 2;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return this.getDimsForDunk().scale(this.getScale());
    }

    private EntityDimensions getDimsForDunk() {
        return switch (this.getVariant()) {
            case 1 -> MEDIUM_SIZE;
            case 2 -> LARGE_SIZE;
            default -> SMALL_SIZE;
        };
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_DUNKLEOSTEUS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        Dunkleosteus dunkleosteus = UP2Entities.DUNKLEOSTEUS.get().create(serverLevel);
        dunkleosteus.setVariant(this.getVariant());
        return dunkleosteus;
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return this.getVariant() == 0 ? new ItemStack(UP2Items.DUNKLEOSTEUS_BUCKET.get()) : ItemStack.EMPTY;
    }

    @Override
    public boolean canBucket() {
        return this.getVariant() == 0;
    }

    public enum DunkleosteusVariant {
        RAVERI(0),
        MARSAISI(1),
        TERRELLI(2);

        private final int id;

        DunkleosteusVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static DunkleosteusVariant byId(int id) {
            if (id < 0 || id >= DunkleosteusVariant.values().length) {
                id = 0;
            }
            return DunkleosteusVariant.values()[id];
        }
    }

    @Override
    public int getVariantCount() {
        return DunkleosteusVariant.values().length;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @org.jetbrains.annotations.Nullable SpawnGroupData spawnGroupData, @org.jetbrains.annotations.Nullable CompoundTag compoundTag) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, compoundTag);
        if (spawnType == MobSpawnType.BUCKET && compoundTag != null && compoundTag.contains("BucketVariantTag", 3)) {
            this.setVariant(compoundTag.getInt("BucketVariantTag"));
        } else {
            this.setVariant(random.nextInt(DunkleosteusVariant.values().length));
        }
        return spawnGroupData;
    }
}
