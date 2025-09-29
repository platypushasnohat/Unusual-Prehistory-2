package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goals.*;
import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricAquaticMob;
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
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Dunkleosteus extends PrehistoricAquaticMob {

    private static final EntityDataAccessor<Integer> DUNK_SIZE = SynchedEntityData.defineId(Dunkleosteus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PASSIVE = SynchedEntityData.defineId(Dunkleosteus.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDimensions SMALL_SIZE = EntityDimensions.scalable(0.75F, 0.6F);
    private static final EntityDimensions MEDIUM_SIZE = EntityDimensions.scalable(1.2F, 1.1F);
    private static final EntityDimensions LARGE_SIZE = EntityDimensions.scalable(2.2F, 2.1F);

    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState yawnAnimationState = new AnimationState();

    public Dunkleosteus(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 1000, 4, 0.02F, 0.1F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 5);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0F)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new DunkleosteusPanicGoal(this));
        this.goalSelector.addGoal(2, new DunkleosteusAttackGoal(this));
        this.goalSelector.addGoal(3, new AquaticLeapGoal(this, 10, 0.4D, 0.6D));
        this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 10, 20, 20, 3));
        this.targetSelector.addGoal(0, new DunkleosteusHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new DunkleosteusNearestAttackableTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 300, true, true, this::canAttackPlayer));
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
        if (this.getAttackState() == 1) {
            this.yawnAnimationState.stop();
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.5F;
    }

    public boolean isTarget(Entity entity) {
        if (this.getDunkSize() == 1) {
            return entity.getType().is(UP2EntityTags.MEDIUM_DUNKLEOSTEUS_TARGETS);
        } else if (this.getDunkSize() == 2) {
            return entity.getType().is(UP2EntityTags.BIG_DUNKLEOSTEUS_TARGETS);
        } else {
            return entity.getType().is(UP2EntityTags.SMALL_DUNKLEOSTEUS_TARGETS);
        }
    }

    public boolean canAttackPlayer(LivingEntity entity) {
        return this.canAttack(entity) && this.getDunkSize() > 0;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(UP2ItemTags.PACIFIES_DUNKLEOSTEUS) && !this.isPassive()) {
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
        return this.getDunkSize() == 0 ? super.mobInteract(player, hand) : InteractionResult.PASS;
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if (prev && this.isPassive() && entity instanceof LivingEntity && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().getUUID().equals(entity.getUUID()))) return false;
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

    @Override
    public float getVoicePitch() {
        final float f = (3 - this.getDunkSize()) * 0.33F;
        return (float) (super.getVoicePitch() * Math.sqrt(f) * 1.2F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DUNK_SIZE, 0);
        this.entityData.define(PASSIVE, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Passive", this.isPassive());
        compoundTag.putFloat("Size", this.getDunkSize());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setPassive(compoundTag.getBoolean("Passive"));
        this.setDunkSize(compoundTag.getInt("Size"));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (DUNK_SIZE.equals(accessor)) {
            this.refreshDimensions();
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0F * this.getDunkSize() + 10.0F);
            this.getAttribute(Attributes.ARMOR).setBaseValue(2.0F * this.getDunkSize() + 6.0F);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.0F * this.getDunkSize() + 6.0F);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(1.0F - this.getDunkSize() * 0.07F);
            this.heal(50.0F);
        }
        super.onSyncedDataUpdated(accessor);
    }

    public int getDunkSize() {
        return Mth.clamp(this.entityData.get(DUNK_SIZE), 0, 2);
    }

    public void setDunkSize(int dunkSize) {
        this.entityData.set(DUNK_SIZE, dunkSize);
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
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

    public void setPassive(boolean passive) {
        this.entityData.set(PASSIVE, passive);
    }

    public boolean isPassive() {
        return this.entityData.get(PASSIVE);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        int sizeChange = this.random.nextInt(0, 100);
        if (sizeChange <= 30) {
            this.setDunkSize(1);
        } else if (sizeChange <= 60) {
            this.setDunkSize(2);
        } else {
            this.setDunkSize(0);
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        Dunkleosteus dunkleosteus = UP2Entities.DUNKLEOSTEUS.get().create(serverLevel);
        dunkleosteus.setDunkSize(this.getDunkSize());
        return dunkleosteus;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return this.getDunkSize() == 0 ? new ItemStack(UP2Items.DUNKLEOSTEUS_BUCKET.get()) : ItemStack.EMPTY;
    }
}
