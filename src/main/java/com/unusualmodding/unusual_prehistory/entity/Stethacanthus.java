package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goals.*;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.AdvancedWaterboundPathNavigation;
import com.unusualmodding.unusual_prehistory.entity.base.SchoolingAquaticMob;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Stethacanthus extends SchoolingAquaticMob {

    private static final EntityDataAccessor<Boolean> PASSIVE = SynchedEntityData.defineId(Stethacanthus.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState attackAnimationState = new AnimationState();

    public Stethacanthus(EntityType<? extends SchoolingAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.1F)
                .add(Attributes.FOLLOW_RANGE, 16.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 1.5D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 6.0F, 2.0D, 2.0D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 6.0F, 2.0D, 2.0D, entity -> entity.getType().is(UP2EntityTags.STETHACANTHUS_AVOIDS)));
        this.goalSelector.addGoal(2, new StethacanthusAttackGoal(this));
        this.goalSelector.addGoal(3, new AquaticLeapGoal(this, 10, 0.5D, 0.4D));
        this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 10, 20, 20, 3));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.STETHACANTHUS_TARGETS)) {
        @Override
        public boolean canUse() {
            if (this.mob instanceof Stethacanthus stethacanthus) {
                if (stethacanthus.isPassive() || stethacanthus.isBaby()) return false;
            }
            return super.canUse();
        }});
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        return new AdvancedWaterboundPathNavigation(this, level, false, true);
    }

    @Override
    public int getMaxSchoolSize() {
        return 3;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.55F;
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void setupAnimationStates() {
        super.setupAnimationStates();
        this.attackAnimationState.animateWhen(this.getAttackState() == 1, this.tickCount);
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.STETHACANTHUS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return UP2SoundEvents.STETHACANTHUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.STETHACANTHUS_FLOP.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PASSIVE, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Passive", this.isPassive());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setPassive(compound.getBoolean("Passive"));
    }

    public void setPassive(boolean passive) {
        this.entityData.set(PASSIVE, passive);
    }

    public boolean isPassive() {
        return this.entityData.get(PASSIVE);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.STETHACANTHUS_BUCKET.get());
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(UP2ItemTags.PACIFIES_STETHACANTHUS) && !this.isPassive()) {
            if (!this.level().isClientSide) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 1));
                this.level().broadcastEntityEvent(this, (byte) 20);
                this.setPassive(true);
                this.gameEvent(GameEvent.EAT, this);
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.STETHACANTHUS.get().create(serverLevel);
    }
}