package com.barlinc.unusual_prehistory.entity.mob.future;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Mammoth extends PrehistoricMob {

    public Mammoth(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

//    @Override
//    protected void registerGoals() {
//        this.goalSelector.addGoal(0, new FloatGoal(this));
//        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D, 10, 4));
//        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.PACHYCEPHALOSAURUS_FOOD), false));
//        this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1));
//        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
//        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
//        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
//        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
//    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.ATTACK_DAMAGE, 9.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.18F);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.9F;
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    public float getStepHeight() {
        return 1.1F;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.PACHYCEPHALOSAURUS_FOOD);
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy(), this.tickCount);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        Mammoth mammoth = UP2Entities.MAMMOTH.get().create(level);
        mammoth.setVariant(this.getVariant());
        return mammoth;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.PACHYCEPHALOSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.PACHYCEPHALOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.PACHYCEPHALOSAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.PACHYCEPHALOSAURUS_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    public static boolean canSpawn(EntityType<Mammoth> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.PACHYCEPHALOSAURUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }
}
