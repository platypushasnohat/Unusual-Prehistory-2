package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2Sounds;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Objects;

public class Kentrosaurus extends Animal {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Kentrosaurus.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        return new GroundPathNavigation(this, level);
    }

    public Kentrosaurus(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 36.0D).add(Attributes.ATTACK_DAMAGE, 7.0D).add(Attributes.MOVEMENT_SPEED, 0.18F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new KentrosaurusPanicGoal(this));
        this.goalSelector.addGoal(2, new KentrosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(UP2ItemTags.KENTROSAURUS_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public void tick () {
        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }

        if (this.tickCount % 200 == 0 && this.getHealth() < this.getMaxHealth()) {
            this.heal(2);
        }

        super.tick();
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.isAlive() && this.getAttackState() == 1, this.tickCount);
        this.attack2AnimationState.animateWhen(this.isAlive() && this.getAttackState() == 2, this.tickCount);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob p_146744_) {
        return UP2Entities.KENTROSAURUS.get().create(serverLevel);
    }

    protected SoundEvent getAmbientSound() {
        return UP2Sounds.KENTROSAURUS_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2Sounds.KENTROSAURUS_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UP2Sounds.KENTROSAURUS_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2Sounds.KENTROSAURUS_STEP.get(), 1.0F, 1.1F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 150;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AttackState", this.getAttackState());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAttackState(compoundTag.getInt("AttackState"));
    }

    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    public void setAttackState(int attackState) {
        this.entityData.set(ATTACK_STATE, attackState);
    }

    // goals
    private static class KentrosaurusAttackGoal extends Goal {

        private int attackTime = 0;
        Kentrosaurus kentrosaurus;

        public KentrosaurusAttackGoal(Kentrosaurus kentrosaurus) {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            this.kentrosaurus = kentrosaurus;
        }

        public boolean canUse() {
            return this.kentrosaurus.getTarget() != null && this.kentrosaurus.getTarget().isAlive();
        }

        public void start() {
            this.kentrosaurus.setAttackState(0);
            this.attackTime = 0;
        }

        public void stop() {
            this.kentrosaurus.setAttackState(0);
        }

        public void tick() {
            LivingEntity target = this.kentrosaurus.getTarget();
            if (target != null) {
                this.kentrosaurus.lookAt(this.kentrosaurus.getTarget(), 30F, 30F);
                this.kentrosaurus.getLookControl().setLookAt(this.kentrosaurus.getTarget(), 30F, 30F);

                double distance = this.kentrosaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int attackState = this.kentrosaurus.getAttackState();

                if (attackState == 1 || attackState == 2) {
                    tickAttack();
                } else {
                    this.kentrosaurus.getNavigation().moveTo(target, 1.75D);
                    if (distance <= 13) {
                        if (this.kentrosaurus.random.nextBoolean()) {
                            this.kentrosaurus.setAttackState(1);
                        } else {
                            this.kentrosaurus.setAttackState(2);
                        }
                    }
                }
            }
        }

        protected void tickAttack() {
            this.attackTime++;
            if (this.attackTime == 6) {
                if (this.kentrosaurus.distanceTo(Objects.requireNonNull(this.kentrosaurus.getTarget())) < 4.0F) {
                    this.kentrosaurus.doHurtTarget(this.kentrosaurus.getTarget());
                    this.kentrosaurus.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (this.attackTime >= 18) {
                this.attackTime = 0;
                this.kentrosaurus.setAttackState(0);
            }
        }
    }

    private static class KentrosaurusPanicGoal extends Goal {

        protected final Kentrosaurus kentrosaurus;

        protected double posX;
        protected double posY;
        protected double posZ;

        public KentrosaurusPanicGoal(Kentrosaurus kentrosaurus) {
            this.kentrosaurus = kentrosaurus;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (!this.shouldPanic()) {
                return false;
            } else {
                return this.findRandomPosition();
            }
        }

        protected boolean shouldPanic() {
            return this.kentrosaurus.getLastHurtByMob() != null && this.kentrosaurus.getHealth() <= this.kentrosaurus.getMaxHealth() * 0.25F;
        }

        protected boolean findRandomPosition() {
            Vec3 vec3 = LandRandomPos.getPos(this.kentrosaurus, 16, 8);
            if (vec3 == null) {
                return false;
            } else {
                this.posX = vec3.x;
                this.posY = vec3.y;
                this.posZ = vec3.z;
                return true;
            }
        }

        public void start() {
            this.kentrosaurus.getNavigation().moveTo(this.posX, this.posY, this.posZ, 1.75D);
        }

        public boolean canContinueToUse() {
            return !this.kentrosaurus.getNavigation().isDone();
        }
    }
}
