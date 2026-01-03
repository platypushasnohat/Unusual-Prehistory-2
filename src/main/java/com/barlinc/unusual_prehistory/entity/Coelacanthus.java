package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.CustomizableRandomSwimGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricAvoidEntityGoal;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("deprecation")
public class Coelacanthus extends PrehistoricAquaticMob {

    private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(Coelacanthus.class, EntityDataSerializers.INT);

    private final ListTag swallowedMobs = new ListTag();

    private int absorbCooldown = 0;

    public final AnimationState absorbAnimationState = new AnimationState();
    public final AnimationState vomitAnimationState = new AnimationState();

    private int absorbTicks;
    private int vomitTicks;

    public Coelacanthus(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.01D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 1.8D, 10, 7) {
            @Override
            public boolean canUse() {
                return super.canUse() && Coelacanthus.this.getCoelacanthusSize() <= 5;
            }
        });
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.STETHACANTHUS_FOOD), false));
        this.goalSelector.addGoal(4, new PrehistoricAvoidEntityGoal<>(this, Player.class, 8.0F, 1.8D, EntitySelector.NO_SPECTATORS::test) {
            @Override
            public boolean canUse() {
                return super.canUse() && Coelacanthus.this.getCoelacanthusSize() <= 5;
            }
        });
        this.goalSelector.addGoal(4, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 8.0F,1.8D, entity -> entity.getType().is(UP2EntityTags.COELACANTHUS_AVOIDS)) {
            @Override
            public boolean canUse() {
                return super.canUse() && Coelacanthus.this.getCoelacanthusSize() <= 5;
            }
        });
        this.goalSelector.addGoal(5, new CustomizableRandomSwimGoal(this, 1.0D, 40));
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.6F;
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_STETHACANTHUS);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.STETHACANTHUS_FOOD);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.horizontalCollision && this.isEyeInFluid(FluidTags.WATER) && this.isPathFinding()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.005, 0.0));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (absorbCooldown > 0) absorbCooldown--;
            if (this.getCoelacanthusSize() < 127 && this.isAlive() && absorbCooldown == 0) this.consumeNearbyMobs();
        }
    }

    public boolean canConsumeEntity(Entity entity) {
        if (entity == null) {
            return false;
        }
        if (entity.getType().is(UP2EntityTags.COELACANTHUS_NEVER_EATS)) {
            return false;
        }
        return (entity.getBbWidth() < this.getBbWidth() && entity.getBbHeight() < this.getBbHeight()) || entity.getType().is(UP2EntityTags.COELACANTHUS_ALWAYS_EATS);
    }

    private void consumeNearbyMobs() {
        List<LivingEntity> nearbyEntities = this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat(), this, this.getBoundingBox().inflate(2.0D));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.get(0);
            if (this.canConsumeEntity(entity)) {
                if (entity instanceof Player) entity.hurt(this.damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                else {
                    ResourceLocation entityKey = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
                    if (entityKey != null) {
                        CompoundTag entry = new CompoundTag();
                        CompoundTag mobData = new CompoundTag();
                        entity.addAdditionalSaveData(mobData);
                        entry.putString("SwallowedMobType", entityKey.toString());
                        entry.put("SwallowedMobData", mobData);
                        if (swallowedMobs.size() >= 8) this.swallowedMobs.remove(0);
                        this.swallowedMobs.add(entry);
                    }
                    entity.discard();
                    entity.gameEvent(GameEvent.ENTITY_DIE);
                    entity.level().broadcastEntityEvent(this, (byte) 3);
                }
                this.gameEvent(GameEvent.EAT);
                this.playSound(SoundEvents.GENERIC_EAT, this.getSoundVolume(), this.getVoicePitch());
                this.heal(this.getMaxHealth());
                this.setCoelacanthusSize(this.getCoelacanthusSize() + 1);
                if (this.getPose() == Pose.STANDING) this.setPose(UP2Poses.ATTACKING.get());
                this.absorbCooldown = 40 + this.getRandom().nextInt(60);
            }
        }
    }

    private void spitOutLastEatenMob() {
        if (!swallowedMobs.isEmpty()) {
            CompoundTag compoundTag = swallowedMobs.getCompound(swallowedMobs.size() - 1);
            EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(compoundTag.getString("SwallowedMobType")));
            if (type != null) {
                Entity entity = type.create(level());
                if (entity instanceof LivingEntity alive) {
                    alive.readAdditionalSaveData(compoundTag.getCompound("SwallowedMobData"));
                    alive.setYRot(random.nextFloat() * 360F);
                    alive.setPos(this.getMouthVec());
                    level().addFreshEntity(alive);
                }
            }
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (super.hurt(source, amount)) {
            this.spitOutLastEatenMob();
            return true;
        } else {
            return false;
        }
    }

    private Vec3 getMouthVec(){
        final Vec3 vec3 = new Vec3(0, this.getBbHeight() * 0.25F, this.getBbWidth() * 0.8F).xRot(this.getXRot() * Mth.DEG_TO_RAD).yRot(-this.getYRot() * Mth.DEG_TO_RAD);
        return this.position().add(vec3);
    }

    @Override
    public void setupAnimationStates() {
        super.setupAnimationStates();
        if (absorbTicks == 0 && this.absorbAnimationState.isStarted()) this.absorbAnimationState.stop();
        if (vomitTicks == 0 && this.vomitAnimationState.isStarted()) this.vomitAnimationState.stop();
    }

    @Override
    public void setupAnimationCooldowns() {
        if (this.absorbTicks > 0) absorbTicks--;
        if (this.vomitTicks > 0) vomitTicks--;
        if (this.absorbTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) this.setPose(Pose.STANDING);
        if (this.vomitTicks == 0 && this.getPose() == UP2Poses.SPITTING.get()) this.setPose(Pose.STANDING);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (SIZE.equals(accessor)) {
            this.refreshDimensions();
            this.setYRot(this.yHeadRot);
            this.yBodyRot = this.yHeadRot;
        }
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.absorbAnimationState.start(this.tickCount);
                this.absorbTicks = 10;
            }
            else if (this.getPose() == UP2Poses.SPITTING.get()) {
                this.vomitAnimationState.start(this.tickCount);
                this.vomitTicks = 20;
            }
            else if (this.getPose() == Pose.STANDING) {
                this.absorbAnimationState.stop();
                this.vomitAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public boolean isPushable() {
        return this.getCoelacanthusSize() <= 5;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SIZE, 1);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Size", this.getCoelacanthusSize() - 1);
        compoundTag.put("SwallowedMobs", this.swallowedMobs.copy());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setCoelacanthusSize(compoundTag.getInt("Size") + 1);
        this.swallowedMobs.clear();
        ListTag list = compoundTag.getList("SwallowedMobs", 10);
        for (int i = 0; i < list.size(); i++) {
            this.swallowedMobs.add(list.getCompound(i));
        }
    }

    public int getCoelacanthusSize() {
        return this.entityData.get(SIZE);
    }

    public void setCoelacanthusSize(int size) {
        int maxSize = Mth.clamp(size, 1, 127);
        this.entityData.set(SIZE, maxSize);
        this.reapplyPosition();
        this.refreshDimensions();
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1 + this.getCoelacanthusSize());
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.01D + this.getCoelacanthusSize() * 0.01D);
    }

    @Override
    public void refreshDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.refreshDimensions();
        this.setPos(x, y, z);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        int size = this.getCoelacanthusSize();
        EntityDimensions dimensions = super.getDimensions(pose);
        float scale = (dimensions.width + 0.05F * (float) size) / dimensions.width;
        return dimensions.scale(scale);
    }

    @Override
    public float getVoicePitch() {
        int size = this.getCoelacanthusSize();
        float pitch = Mth.clamp(size / 127.0F, 0.0F, 1.0F);
        return Mth.lerp(pitch, 2.0F, 0.1F);
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.STETHACANTHUS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.STETHACANTHUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.STETHACANTHUS_FLOP.get();
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return ItemStack.EMPTY;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.COELACANTHUS.get().create(level);
    }
}