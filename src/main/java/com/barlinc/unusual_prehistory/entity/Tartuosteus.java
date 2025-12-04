package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.AquaticLeapGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.CustomizableRandomSwimGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.AdvancedWaterboundPathNavigation;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
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
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Tartuosteus extends PrehistoricAquaticMob {

    private static final EntityDataAccessor<Boolean> GLIDING = SynchedEntityData.defineId(Tartuosteus.class, EntityDataSerializers.BOOLEAN);

    public Tartuosteus(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 1000, 10, 0.02F, 0.1F, false);
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
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TARTUOSTEUS_FOOD), false));
        this.goalSelector.addGoal(2, new CustomizableRandomSwimGoal(this, 1.0D, 20, 10, 16, 3));
        this.goalSelector.addGoal(3, new TartuosteusGlideGoal(this));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, LivingEntity.class, 6.0F, 2.0D, 2.0D, entity -> entity.getType().is(UP2EntityTags.JAWLESS_FISH_AVOIDS)));
        this.goalSelector.addGoal(5, new LargePanicGoal(this, 1.5D));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new AdvancedWaterboundPathNavigation(this, level, false, true);
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
        if (this.isGliding()) {
            if (!this.isInWaterOrBubble() && this.getDeltaMovement().y < 0.0) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0F, 0.66F, 1.0F));
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GLIDING, false);
    }

    public boolean isGliding() {
        return this.entityData.get(GLIDING);
    }

    public void setGliding(boolean gliding) {
        this.entityData.set(GLIDING, gliding);
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

    private static class TartuosteusGlideGoal extends AquaticLeapGoal {

        private final Tartuosteus tartuosteus;

        public TartuosteusGlideGoal(Tartuosteus tartuosteus) {
            super(tartuosteus, 10, 1.0D, 0.5D);
            this.tartuosteus = tartuosteus;
        }

        @Override
        public void start() {
            super.start();
            this.tartuosteus.setGliding(true);
        }

        @Override
        public void stop() {
            this.tartuosteus.setGliding(false);
        }

        @Override
        public void tick() {
            boolean flag = this.breached;
            tartuosteus.getNavigation().stop();
            if (!flag) {
                FluidState fluidstate = tartuosteus.level().getFluidState(tartuosteus.blockPosition());
                this.breached = fluidstate.is(FluidTags.WATER);
            }

            if (this.breached && !flag) {
                tartuosteus.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
            }

            Vec3 vec3 = tartuosteus.getDeltaMovement();
            if (vec3.y * vec3.y < (double) 0.03F && tartuosteus.getXRot() != 0.0F) {
                tartuosteus.setXRot(Mth.rotLerp(0.2F, tartuosteus.getXRot(), 0.0F));
            } else if (vec3.length() > (double) 1.0E-5F) {
                double d0 = vec3.horizontalDistance();
                double d1 = Math.atan2(-vec3.y, d0) * (double) (180F / (float) Math.PI);
                tartuosteus.setXRot((float) d1);
            }

            Vec3 movement = new Vec3(tartuosteus.getMotionDirection().getStepX(), 0, tartuosteus.getMotionDirection().getStepZ()).normalize().scale(0.53F);
            Vec3 glide = new Vec3(movement.x, vec3.y, movement.z);
            tartuosteus.setDeltaMovement(glide);
            tartuosteus.setYRot(((float) Mth.atan2(tartuosteus.getMotionDirection().getStepZ(), tartuosteus.getMotionDirection().getStepX())) * Mth.RAD_TO_DEG - 90F);
        }
    }
}