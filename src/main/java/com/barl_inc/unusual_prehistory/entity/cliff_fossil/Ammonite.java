package com.barl_inc.unusual_prehistory.entity.cliff_fossil;

import com.barl_inc.unusual_prehistory.entity.ai.goal.FollowVariantLeaderGoal;
import com.barl_inc.unusual_prehistory.entity.base.SchoolingPrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.UP2MobUtils;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.mojang.serialization.Codec;
import com.platypushasnohat.sinew.entity.ai.control.SwimmingMoveControl;
import com.platypushasnohat.sinew.entity.ai.goal.AquaticPanicGoal;
import com.platypushasnohat.sinew.entity.ai.goal.SwimWanderGoal;
import com.platypushasnohat.sinew.entity.utils.SwimPitch;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
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
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class Ammonite extends SchoolingPrehistoricMob implements VariantHolder<Ammonite.AmmoniteVariant>, Bucketable {

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Ammonite.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Ammonite.class, EntityDataSerializers.INT);

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
        this.goalSelector.addGoal(3, new SwimWanderGoal(this, 1.0D, 40));
        this.goalSelector.addGoal(4, new FollowVariantLeaderGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(FROM_BUCKET, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant().getId());
        compound.putBoolean("FromBucket", this.fromBucket());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(AmmoniteVariant.byId(compound.getInt("Variant")));
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    @Override
    public AmmoniteVariant getVariant() {
        return AmmoniteVariant.byId(this.entityData.get(VARIANT));
    }

    @Override
    public void setVariant(AmmoniteVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
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
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, compoundTag -> compoundTag.putInt("Variant", this.getVariant().getId()));
    }

    @Override
    public void loadFromBucketTag(CompoundTag compoundTag) {
        UP2MobUtils.loadPrehistoricDataFromBucket(this, compoundTag);
        this.setVariant(AmmoniteVariant.byId(compoundTag.getInt("Variant")));
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
    public boolean canAddFollowers(SchoolingPrehistoricMob mob) {
        return this.getVariant() == ((Ammonite) mob).getVariant();
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

    public enum AmmoniteVariant implements StringRepresentable {
        AMMONITE_HOPLITES(0, "ammonite_hoplites"),
        AMMONITE_CRIOCERATITES(1, "ammonite_crioceratites"),
        AMMONITE_NOSTOCERAS(2, "ammonite_nostoceras"),
        AMMONITE_PINACOCERAS(3, "ammonite_pinacoceras"),
        AMMONITE_TROPITES(4, "ammonite_tropites");

        private static final IntFunction<AmmoniteVariant> BY_ID = ByIdMap.sparse(AmmoniteVariant::getId, values(), AMMONITE_HOPLITES);
        public static final Codec<AmmoniteVariant> CODEC = StringRepresentable.fromEnum(AmmoniteVariant::values);

        private final int id;
        private final String name;

        AmmoniteVariant(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public static AmmoniteVariant byId(int id) {
            return BY_ID.apply(id);
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        if (this.fromBucket()) {
            return spawnGroupData;
        }
        this.setVariant(Util.getRandom(AmmoniteVariant.values(), level.getRandom()));
        return spawnGroupData;
    }
}
