package com.barlinc.unusual_prehistory.entity.mob.update_4;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Leptictidium extends PrehistoricMob {

    public int attackCooldown = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState sniffAnimationState = new AnimationState();
    public final AnimationState preenAnimationState = new AnimationState();

    private int sniffCooldown = 400 + this.getRandom().nextInt(400);
    private int preenCooldown = 700 + this.getRandom().nextInt(700);

    private int attackTicks;

    public Leptictidium(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LeptictidiumAttackGoal(this));
        this.goalSelector.addGoal(2, new LeptictidiumRunLikeCrazyGoal(this));
        this.goalSelector.addGoal(3, new LargePanicGoal(this, 1.6D, 10, 4));
        this.goalSelector.addGoal(4, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 8.0F, 1.6D, entity -> entity.getType().is(UP2EntityTags.LEPTICTIDIUM_AVOIDS)));
        this.goalSelector.addGoal(4, new PrehistoricAvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.LEPTICTIDIUM_FOOD), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(7, new PrehistoricRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new LeptictidiumPreenGoal(this));
        this.goalSelector.addGoal(9, new LeptictidiumSniffGoal(this));
        this.targetSelector.addGoal(0, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 200, true, true, entity -> entity.getMobType() == MobType.ARTHROPOD && !entity.getType().is(UP2EntityTags.LEPTICTIDIUM_DOESNT_TARGET)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.8F;
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && this.horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.7D * this.getBbHeight();
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
        return this.isRunning() ? 1.0F : 0.6F;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.LEPTICTIDIUM_FOOD);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.infectNearbyMobs();
            this.getInfectedByNearbyMobs();
            if (attackTicks > 0) attackTicks--;
            if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) this.setPose(Pose.STANDING);
            if (attackCooldown > 0) attackCooldown--;
        }
    }

    private void infectNearbyMobs() {
        List<LivingEntity> nearbyEntities = this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat(), this, this.getBoundingBox().inflate(3.0));
        if (!this.getActiveEffects().isEmpty() && !nearbyEntities.isEmpty()) {
            for (MobEffectInstance effectInstance : this.getActiveEffects()) {
                nearbyEntities.stream().filter(entity -> entity != this && !entity.hasEffect(effectInstance.getEffect()) && effectInstance.getDuration() > 20 && !effectInstance.getEffect().isInstantenous()).forEach(entity -> entity.addEffect(new MobEffectInstance(effectInstance.getEffect(), (int) (effectInstance.getDuration() * 0.3F), 0), this));
            }
        }
    }

    private void getInfectedByNearbyMobs() {
        List<LivingEntity> nearbyEntities = this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat(), this, this.getBoundingBox().inflate(3.0));
        if (!nearbyEntities.isEmpty()) {
            nearbyEntities.stream().filter(entity -> entity != this).forEach(entity -> {
                if (!entity.getActiveEffects().isEmpty()) {
                    for (MobEffectInstance effectInstance : entity.getActiveEffects()) {
                        if (!this.hasEffect(effectInstance.getEffect()) && effectInstance.getDuration() > 20 && !effectInstance.getEffect().isInstantenous()) {
                            this.addEffect(new MobEffectInstance(effectInstance.getEffect(), (int) (effectInstance.getDuration() * 0.3F), effectInstance.getAmplifier()), entity);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void setupAnimationCooldowns() {
        if (!this.level().isClientSide) {
            if (preenCooldown > 0) preenCooldown--;
            if (sniffCooldown > 0) sniffCooldown--;
        }
    }

    @Override
    public void setupAnimationStates() {
        if (this.attackAnimationState.isStarted() && this.attackTicks == 0) this.attackAnimationState.stop();
        this.idleAnimationState.animateWhen(!this.isInWater() && this.getPose() != UP2Poses.ATTACKING.get() && this.getIdleState() != 1, this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWater() && this.getPose() != UP2Poses.ATTACKING.get(), this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.attackAnimationState.start(this.tickCount);
                this.attackTicks = 20;
            }
            else if (this.getPose() == Pose.STANDING) {
                this.attackAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    public void handleEntityEvent(byte id) {
        switch (id) {
            case 67 -> this.preenAnimationState.start(this.tickCount);
            case 68 -> this.preenAnimationState.stop();
            case 69 -> this.sniffAnimationState.start(this.tickCount);
            case 70 -> this.sniffAnimationState.stop();
            default -> super.handleEntityEvent(id);
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.LEPTICTIDIUM.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.LEPTICTIDIUM_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.LEPTICTIDIUM_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.LEPTICTIDIUM_DEATH.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 150;
    }

    public static boolean canSpawn(EntityType<Leptictidium> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.LEPTICTIDIUM_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    private static class LeptictidiumPreenGoal extends AnimationGoal {

        private final Leptictidium leptictidium;

        public LeptictidiumPreenGoal(Leptictidium leptictidium) {
            super(leptictidium, 30, 1, (byte) 67, (byte) 68);
            this.leptictidium = leptictidium;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && leptictidium.preenCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.leptictidium.preenCooldown = 700 + leptictidium.getRandom().nextInt(700);
        }
    }

    private static class LeptictidiumSniffGoal extends AnimationGoal {

        private final Leptictidium leptictidium;

        public LeptictidiumSniffGoal(Leptictidium leptictidium) {
            super(leptictidium, 30, 1, (byte) 69, (byte) 70, false);
            this.leptictidium = leptictidium;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && leptictidium.sniffCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.leptictidium.sniffCooldown = 400 + leptictidium.getRandom().nextInt(400);
        }
    }
}
