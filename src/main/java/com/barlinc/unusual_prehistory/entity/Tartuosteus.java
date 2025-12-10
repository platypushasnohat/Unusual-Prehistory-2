package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.AquaticDigBlockGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.CustomizableRandomSwimGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.TartuosteusGlideGoal;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class Tartuosteus extends PrehistoricAquaticMob implements LeapingMob {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Tartuosteus.class, EntityDataSerializers.BOOLEAN);

    public Tartuosteus(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.55F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 2.0D, 10, 7));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, LivingEntity.class, 6.0F, 2.0D, 2.0D, entity -> entity.getType().is(UP2EntityTags.TARTUOSTEUS_AVOIDS)));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TARTUOSTEUS_FOOD), false));
        this.goalSelector.addGoal(4, new AquaticDigBlockGoal(this, 30, 800, UP2BlockTags.TARTUOSTEUS_NIBBLING_BLOCKS, 1.0D));
        this.goalSelector.addGoal(5, new CustomizableRandomSwimGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(6, new TartuosteusGlideGoal(this));
    }

    @Override
    public int getMaxHeadXRot() {
        return 1;
    }

    @Override
    public int getMaxHeadYRot() {
        return 1;
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
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.TARTUOSTEUS_FOOD);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.8F;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isLeaping()) {
            if (!this.isInWaterOrBubble() && this.getDeltaMovement().y < 0.0) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0F, 0.66F, 1.0F));
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LEAPING, false);
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.JAWLESS_FISH_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.JAWLESS_FISH_HURT.get();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.JAWLESS_FISH_FLOP.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        Tartuosteus tartuosteus = UP2Entities.TARTUOSTEUS.get().create(serverLevel);
        tartuosteus.setVariant(this.getVariant());
        return tartuosteus;
    }

    public enum TartuosteusVariant {
        TARTUOSTEUS(0),
        EVIL_TARTUOSTEUS(1);

        private final int variant;

        TartuosteusVariant(int variant) {
            this.variant = variant;
        }

        public int getId() {
            return this.variant;
        }

        public static TartuosteusVariant byId(int id) {
            if (id < 0 || id >= TartuosteusVariant.values().length) {
                id = 0;
            }
            return TartuosteusVariant.values()[id];
        }
    }

    @Override
    public int getVariantCount() {
        return TartuosteusVariant.values().length;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, compoundTag);
        if (spawnType == MobSpawnType.BUCKET && compoundTag != null && compoundTag.contains("BucketVariantTag", 3)) {
            this.setVariant(compoundTag.getInt("BucketVariantTag"));
        } else {
            if (level.getLevel().isNight()) this.setVariant(1);
            else this.setVariant(0);
        }
        return spawnGroupData;
    }

    public static boolean canSpawn(EntityType<Tartuosteus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return PrehistoricAquaticMob.checkSpawnRules(entityType, level, spawnType, pos, random);
    }
}