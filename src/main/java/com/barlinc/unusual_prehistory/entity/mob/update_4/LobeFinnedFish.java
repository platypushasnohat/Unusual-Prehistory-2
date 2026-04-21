package com.barlinc.unusual_prehistory.entity.mob.update_4;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.SchoolingAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.MobUtils;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LobeFinnedFish extends SchoolingAquaticMob {

    private static final EntityDimensions ALLENYPTERUS_DIMENSIONS = EntityDimensions.scalable(0.5F, 0.8F).withEyeHeight(0.4F);
    private static final EntityDimensions EUSTHENOPTERON_DIMENSIONS = EntityDimensions.scalable(0.6F, 0.7F).withEyeHeight(0.35F);
    private static final EntityDimensions GOOLOOGONGIA_DIMENSIONS = EntityDimensions.scalable(0.6F, 0.5F).withEyeHeight(0.25F);
    private static final EntityDimensions LACCOGNATHUS_DIMENSIONS = EntityDimensions.scalable(0.98F, 0.5F).withEyeHeight(0.25F);
    private static final EntityDimensions SCAUMENACIA_DIMENSIONS = EntityDimensions.scalable(0.5F, 0.4F).withEyeHeight(0.2F);

    public LobeFinnedFish(EntityType<? extends SchoolingAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 1000, 10, 0.02F, 0.1F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0)
                .add(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 2.0D, 10, 7));
        this.goalSelector.addGoal(2, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 6.0F, 2.0D, entity -> entity.getType().is(UP2EntityTags.JAWLESS_FISH_AVOIDS)));
        this.goalSelector.addGoal(2, new PrehistoricAvoidEntityGoal<>(this, Player.class, 6.0F, 2.0D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.LOBE_FINNED_FISH_FOOD), false));
        this.goalSelector.addGoal(4, new AquaticNibbleBlockGoal(this, UP2BlockTags.LOBE_FINNED_FISH_FOOD_BLOCKS));
        this.goalSelector.addGoal(5, new CustomizableRandomSwimGoal(this, 1.0D, 30));
        this.goalSelector.addGoal(6, new LobeFinnedFishFollowVariantLeaderGoal(this));
    }

    @Override
    public int getMaxSchoolSize() {
        return switch (this.getVariant()) {
            case 1 -> 2;
            case 2 -> 3;
            case 3 -> 1;
            case 4 -> 5;
            default -> 4;
        };
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.LOBE_FINNED_FISH_FOOD);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (VARIANT.equals(accessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        return this.getDimsForLobeFinnedFish().scale(this.getAgeScale());
    }

    private EntityDimensions getDimsForLobeFinnedFish() {
        return switch (this.getVariant()) {
            case 1 -> EUSTHENOPTERON_DIMENSIONS;
            case 2 -> GOOLOOGONGIA_DIMENSIONS;
            case 3 -> LACCOGNATHUS_DIMENSIONS;
            case 4 -> SCAUMENACIA_DIMENSIONS;
            default -> ALLENYPTERUS_DIMENSIONS;
        };
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.LOBE_FINNED_FISH_BUCKET.get());
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
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        LobeFinnedFish lobeFinnedFish = UP2Entities.LOBE_FINNED_FISH.get().create(level);
        if (lobeFinnedFish != null) {
            lobeFinnedFish.setVariant(this.getVariant());
        }
        return lobeFinnedFish;
    }

    public enum LobeFinnedFishVariant {
        ALLENYPTERUS(0),
        EUSTHENOPTERON(1),
        GOOLOOGONGIA(2),
        LACCOGNATHUS(3),
        SCAUMENACIA(4);

        private final int variant;

        LobeFinnedFishVariant(int variant) {
            this.variant = variant;
        }

        public int getId() {
            return this.variant;
        }

        public static LobeFinnedFishVariant byId(int id) {
            if (id < 0 || id >= LobeFinnedFishVariant.values().length) {
                id = 0;
            }
            return LobeFinnedFishVariant.values()[id];
        }
    }

    @Override
    public int getVariantCount() {
        return LobeFinnedFishVariant.values().length;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        if (spawnType == MobSpawnType.BUCKET) {
            return spawnGroupData;
        } else {
            this.setVariant(random.nextInt(LobeFinnedFishVariant.values().length));
        }
        return spawnGroupData;
    }

    // Goals
    private static class LobeFinnedFishFollowVariantLeaderGoal extends FollowVariantLeaderGoal {

        public LobeFinnedFishFollowVariantLeaderGoal(SchoolingAquaticMob mob) {
            super(mob);
        }

        @Override
        public boolean canUse() {
            return super.canUse() && mob.getVariant() != 3;
        }
    }
}