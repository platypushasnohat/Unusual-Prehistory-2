package com.barl_inc.unusual_prehistory.entity.cliff_fossil;

import com.barl_inc.unusual_prehistory.entity.ai.goal.FollowVariantLeaderGoal;
import com.barl_inc.unusual_prehistory.entity.base.SchoolingPrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.UP2MobUtils;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.platypushasnohat.sinew.entity.ai.control.SwimmingMoveControl;
import com.platypushasnohat.sinew.entity.ai.goal.AquaticPanicGoal;
import com.platypushasnohat.sinew.entity.ai.goal.SwimWanderGoal;
import com.platypushasnohat.sinew.entity.utils.SwimPitch;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Ammonite extends SchoolingPrehistoricMob implements Bucketable {

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Ammonite.class, EntityDataSerializers.BOOLEAN);

    public SwimPitch swimPitch = new SwimPitch(this, 85.0F, 20.0F);

    public Ammonite(EntityType<? extends Ammonite> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SwimmingMoveControl(this, 85, 10, 0.02F);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.7F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                .add(Attributes.ARMOR, 10.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AquaticPanicGoal(this, 1.4D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 6.0F, 1.4D, 1.4D));
        this.goalSelector.addGoal(3, new SwimWanderGoal(this, 1.0D, 40, 70));
        this.goalSelector.addGoal(4, new FollowVariantLeaderGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FROM_BUCKET, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("FromBucket", this.fromBucket());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setFromBucket(compoundTag.getBoolean("FromBucket"));
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.AMMONITE_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        UP2MobUtils.savePrehistoricDataToBucket(this, bucket);
    }

    @Override
    public void loadFromBucketTag(CompoundTag compoundTag) {
        UP2MobUtils.loadPrehistoricDataFromBucket(this, compoundTag);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    public int getMaxSchoolSize() {
        return 5;
    }

    @Override
    public float flopChance() {
        return 0.05F;
    }

    @Override
    public void tick() {
        super.tick();
        this.swimPitch.tick();
        if (this.level().isClientSide) {
            if (this.isInWater() && this.getDeltaMovement().lengthSqr() > 0.001D && this.tickCount % 10 == 0) {
                Vec3 viewVector = this.getViewVector(0.0F);
                this.level().addParticle(ParticleTypes.BUBBLE, this.getRandomX(0.5D) - viewVector.x * 0.5D, this.getRandomY() - viewVector.y * 0.25D, this.getRandomZ(0.5D) - viewVector.z * 0.5D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float length = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float speed = Math.min(length * (this.isBaby() ? 5.0F : 10.0F), 1.0F);
        this.walkAnimation.update(speed, 0.4F);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ItemTags.FISHES);
    }

    @Nullable
    @Override
    public Ammonite getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        Ammonite ammonite = UP2Entities.AMMONITE.get().create(level);
        if (ammonite != null) {
            ammonite.setVariant(this.getVariant());
        }
        return ammonite;
    }

    @Override
    public float getSoundVolume() {
        return 0.75F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.AMMONITE_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.AMMONITE_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return UP2SoundEvents.AMMONITE_HURT.get();
    }

    @Override
    protected SoundEvent getSwimSound() {
        return UP2SoundEvents.AMMONITE_SWIM.get();
    }

    @Override
    public SoundEvent getFlopSound() {
        return UP2SoundEvents.AMMONITE_FLOP.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    public enum AmmoniteVariant {
        AMMONITE_CRIOCERATITES(0),
        AMMONITE_HOPLITES(1),
        AMMONITE_NOSTOCERAS(2),
        AMMONITE_PINACOCERAS(3),
        AMMONITE_TROPITES(4);

        private final int variant;

        AmmoniteVariant(int variant) {
            this.variant = variant;
        }

        public int getId() {
            return this.variant;
        }

        public static AmmoniteVariant byId(int id) {
            if (id < 0 || id >= AmmoniteVariant.values().length) {
                id = 0;
            }
            return AmmoniteVariant.values()[id];
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        if (this.fromBucket()) {
            return spawnGroupData;
        }
        this.setVariant(this.getRandom().nextInt(AmmoniteVariant.values().length));
        return spawnGroupData;
    }
}
