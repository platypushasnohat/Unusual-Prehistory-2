package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.AquaticDigBlockGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.CustomizableRandomSwimGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.FollowVariantLeaderGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.base.SchoolingAquaticMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class JawlessFish extends SchoolingAquaticMob {

    public JawlessFish(EntityType<? extends SchoolingAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 1000, 10, 0.02F, 0.1F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0)
                .add(Attributes.MOVEMENT_SPEED, 0.9F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 2.0D, 10, 7));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, LivingEntity.class, 6.0F, 2.0D, 2.0D, entity -> entity.getType().is(UP2EntityTags.JAWLESS_FISH_AVOIDS)));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 6.0F, 2.0D, 2.0D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.JAWLESS_FISH_FOOD), false));
        this.goalSelector.addGoal(4, new AquaticDigBlockGoal(this, UP2BlockTags.JAWLESS_FISH_NIBBLING_BLOCKS));
        this.goalSelector.addGoal(5, new CustomizableRandomSwimGoal(this, 1.0D, 20, 10, 7));
        this.goalSelector.addGoal(6, new FollowVariantLeaderGoal(this));
    }

    @Override
    public int getMaxSchoolSize() {
        return 10;
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.horizontalCollision && this.isEyeInFluid(FluidTags.WATER) && this.isPathFinding()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return this.getDepthPathfindingFavor(pos, level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.JAWLESS_FISH_FOOD);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.4F;
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.JAWLESS_FISH_BUCKET.get());
    }

    @Override
    public boolean canBucket() {
        return true;
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
        JawlessFish jawlessFish = UP2Entities.JAWLESS_FISH.get().create(serverLevel);
        jawlessFish.setVariant(this.getVariant());
        return jawlessFish;
    }

    public enum JawlessFishVariant {
        ARANDASPIS(0),
        CEPHALASPIS(1),
        DORYASPIS(2),
        FURCACAUDA(3),
        SACABAMBASPIS(4);

        private final int variant;

        JawlessFishVariant(int variant) {
            this.variant = variant;
        }

        public int getId() {
            return this.variant;
        }

        public static JawlessFishVariant byId(int id) {
            if (id < 0 || id >= JawlessFishVariant.values().length) {
                id = 0;
            }
            return JawlessFishVariant.values()[id];
        }
    }

    @Override
    public int getVariantCount() {
        return JawlessFishVariant.values().length;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, compoundTag);
        if (spawnType == MobSpawnType.BUCKET && compoundTag != null && compoundTag.contains("BucketVariantTag", 3)) {
            this.setVariant(compoundTag.getInt("BucketVariantTag"));
        } else {
            this.setVariant(random.nextInt(JawlessFishVariant.values().length));
        }
        return spawnGroupData;
    }

    public static boolean canSpawn(EntityType<JawlessFish> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return PrehistoricAquaticMob.checkSpawnRules(entityType, level, spawnType, pos, random);
    }
}