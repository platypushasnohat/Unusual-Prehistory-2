package com.barlinc.unusual_prehistory.entity.mob.update_6;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.AmphibiousMob;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import com.barlinc.unusual_prehistory.entity.utils.MobUtils;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BiomeTags;
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
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GastricBroodingFrog extends AmphibiousMob implements Bucketable, LeapingMob, VariantHolder<GastricBroodingFrog.GastricBroodingFrogVariant> {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(GastricBroodingFrog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(GastricBroodingFrog.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LAUNCHED = SynchedEntityData.defineId(GastricBroodingFrog.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_ATTACK = SynchedEntityData.defineId(GastricBroodingFrog.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(GastricBroodingFrog.class, EntityDataSerializers.INT);

    public int attackCooldown = 0;

    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState leapAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState eatAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState launchAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);

    public GastricBroodingFrog(EntityType<? extends AmphibiousMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.lookControl = new PrehistoricLookControl(this, false);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PrehistoricSitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new AmphibiousPanicGoal(this, 2.0D, 10, 5, true));
        this.goalSelector.addGoal(2, new FrogAttackGoal(this));
        this.goalSelector.addGoal(3, new PrehistoricFollowOwnerGoal(this, 1.3D, 1.5D, 5.0F, 2.5F));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.3D, Ingredient.of(UP2ItemTags.DIET_INSECTIVORE), false));
        this.goalSelector.addGoal(5, new LeaveWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new EnterWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LeapRandomlyGoal(this, 70, 6, 0.8F));
        this.goalSelector.addGoal(7, new CustomizableRandomSwimGoal(this, 1.0D, 40));
        this.goalSelector.addGoal(7, new SemiAquaticRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new PrehistoricOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(4, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, true, entity -> entity.getType().is(UP2EntityTags.THYLACINE_TARGETS)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0F)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.STEP_HEIGHT, 1.2D)
                .add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothAmphibiousNavigation(this, level);
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0D, this.isInWater() ? 0.0D : 1.0D, 0.0D);
        }
        if (this.isEffectiveAi() && this.isInWater()) {
            MobUtils.travelInWater(this, travelVec);
        } else {
            super.travel(travelVec);
        }
    }

    @Override
    public boolean refuseToLook() {
        return super.refuseToLook() || this.wasLaunched();
    }

    @Override
    public boolean isPushable() {
        return !this.isSitting();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_INSECTIVORE);
    }

    public float getAgeScale() {
        return this.isBaby() ? 0.6F : 1.0F;
    }

    @Override
    public int getBaseExperienceReward() {
        return 1 + this.level().getRandom().nextInt(3);
    }

    @Override
    public boolean shouldDropExperience() {
        return !this.isBaby() && !this.isFromAttack();
    }

    @Override
    public boolean canFallInLove() {
        return this.getInLoveTime() <= 0;
    }

    @Override
    public boolean canMate(@NotNull Animal animal) {
        return true;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (this.isFromAttack()) {
            return InteractionResult.PASS;
        }
        else if (!this.isBaby() && !this.isTame() && this.getEatTicks() <= 0 && itemStack.is(UP2ItemTags.TAMES_THYLACINE) ) {
            this.setEatTicks(25);
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                this.playSound(this.getEatingSound(), 1.0F, this.getVoicePitch());
                if (this.getNavigation().getPath() != null) {
                    this.getNavigation().stop();
                }
                if (this.getTameAttempts() > 3 && this.getRandom().nextBoolean()) {
                    this.level().broadcastEntityEvent(this, (byte) 7);
                    this.tame(player);
                    this.setTameAttempts(0);
                    this.heal(this.getMaxHealth());
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                    this.setTameAttempts(this.getTameAttempts() + 1);
                }
            }
            return InteractionResult.SUCCESS;
        }
        else if (this.getEatTicks() > 0 && this.isFood(itemStack)) {
            return InteractionResult.PASS;
        }
        else if (this.getEatTicks() <= 0 && this.isFood(itemStack) ) {
            int i = this.getAge();
            if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
                this.usePlayerItem(player, hand, itemStack);
                this.playSound(this.getEatingSound());
                this.setInLove(player);
                return InteractionResult.SUCCESS;
            }
        }
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    public boolean canOwnerCommand(Player player, @NotNull InteractionHand hand) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        return !effectInstance.is(MobEffects.POISON) && super.canBeAffected(effectInstance);
    }

    @Override
    public void finalizeSpawnChildFromBreeding(ServerLevel level, Animal animal, @Nullable AgeableMob baby) {
        Optional.ofNullable(this.getLoveCause()).or(() -> Optional.ofNullable(animal.getLoveCause())).ifPresent((serverPlayer) -> {
            serverPlayer.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayer, this, animal, baby);
        });
        this.setAge(6000);
        animal.setAge(6000);
        this.resetLove();
        animal.resetLove();
        level.broadcastEntityEvent(this, (byte) 18);
        if (level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            level.addFreshEntity(new ExperienceOrb(level, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }
    }

    @Override
    public int getHeadRotSpeed() {
        return 35;
    }

    @Override
    public int getMaxHeadYRot() {
        return 45;
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    @Override
    public boolean canTrample(@NotNull BlockState state, @NotNull BlockPos pos, float fallDistance) {
        return false;
    }

    @Override
    public void onLeap() {
        this.setLeaping(true);
        this.level().playSound(null, this, UP2SoundEvents.GASTRIC_BROODING_FROG_LEAP.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    private boolean canSwallowTarget(LivingEntity target) {
        if (!this.canAttack(target)) {
            return false;
        }
        if (target instanceof Player) {
            return false;
        }
        if (target instanceof GastricBroodingFrog) {
            return false;
        }
        return target.getBbWidth() < this.getBbWidth() && target.getBbHeight() < this.getBbHeight();
    }

    protected boolean canHitEntity(Entity entity) {
        return !entity.isSpectator() && !(entity instanceof GastricBroodingFrog) && entity != this.getOwner();
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public void shootFroglet(double x, double y, double z, float scale) {
        final double angle = 57.2957763671875D;
        Vec3 shootVec = (new Vec3(x, y, z)).normalize().scale(scale);
        this.setDeltaMovement(shootVec);
        this.setYRot((float) (Mth.atan2(shootVec.x, shootVec.z) * angle));
        this.xRotO = this.getXRot();
        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
        this.yHeadRotO = this.getYRot();
        this.yRotO = this.getYRot();
        this.setLaunched(true);
    }

    @Override
    public boolean onClimbable() {
        return !this.wasLaunched() && super.onClimbable();
    }

    private void onHitEntity(EntityHitResult hitResult) {
        if (!this.level().isClientSide && hitResult.getEntity() instanceof LivingEntity target) {
            target.hurt(this.damageSources().mobProjectile(this, this), 6.0F);
            target.knockback(this.getKnockback(this, this.damageSources().mobProjectile(this, this)) * 0.5F, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180F)));
            this.setLaunched(false);
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackCooldown > 0) {
            this.attackCooldown--;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isLeaping() && (this.onGround() || this.isInFluidType())) {
            if (this.onGround() && !this.level().isClientSide) {
                this.level().playSound(null, this, UP2SoundEvents.GASTRIC_BROODING_FROG_STEP.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
            this.setLeaping(false);
        }

        if (this.wasLaunched()) {
            this.yBodyRot = this.getYRot();
            HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (this.isInWater()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.02D, 0.0D));
            }
            if (hitResult.getType() != HitResult.Type.MISS) {
                if (hitResult.getType() == HitResult.Type.ENTITY) {
                    this.onHitEntity(((EntityHitResult) hitResult));
                }
            }
        }
        if (this.isFromAttack()) {
            if (this.onGround() || (tickCount > 20 && this.isInWaterOrBubble())) {
                this.setLaunched(false);
            }
            if (tickCount > 80) {
                if (this.isAlive()) {
                    this.spawnAnim();
                    this.remove(Entity.RemovalReason.KILLED);
                }
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isSitting() && !this.wasLaunched(), tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isSitting() && !this.wasLaunched(), tickCount);
        this.leapAnimationState.animateWhen(this.isLeaping() && !this.wasLaunched(), tickCount);
        this.eatAnimationState.animateWhen(this.getPose() == UP2Poses.EATING.get(), tickCount);
        this.launchAnimationState.animateWhen(this.wasLaunched(), tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), tickCount);
        this.sitAnimationState.animateWhen(this.isSitting(), tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float) Mth.length(this.getX() - xo, includeHeight ? this.getY() - yo : 0.0, this.getZ() - zo);
        if (this.isBaby()) {
            this.updateWalkAnimation(f * 0.5F);
        } else {
            this.updateWalkAnimation(f);
        }
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f = Math.min(partialTick * 25.0F, 1.0F);
        this.walkAnimation.update(f, 0.4F);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(LEAPING, false);
        builder.define(LAUNCHED, false);
        builder.define(FROM_ATTACK, false);
        builder.define(TAME_ATTEMPTS, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant().getId());
        compoundTag.putBoolean("FromAttack", this.isFromAttack());
        compoundTag.putInt("TameAttempts", this.getTameAttempts());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(GastricBroodingFrogVariant.byId(compoundTag.getInt("Variant")));
        this.setFromAttack(compoundTag.getBoolean("FromAttack"));
        this.setTameAttempts(compoundTag.getInt("TameAttempts"));
    }

    @Override
    public @NotNull GastricBroodingFrogVariant getVariant() {
        return GastricBroodingFrogVariant.byId(entityData.get(VARIANT));
    }
    @Override
    public void setVariant(GastricBroodingFrogVariant variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant.getId(), 0, GastricBroodingFrogVariant.values().length));
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }
    @Override
    public boolean isLeaping() {
        return entityData.get(LEAPING);
    }

    public void setLaunched(boolean launched) {
        this.entityData.set(LAUNCHED, launched);
    }
    public boolean wasLaunched() {
        return entityData.get(LAUNCHED);
    }

    public void setFromAttack(boolean fromAttack) {
        this.entityData.set(FROM_ATTACK, fromAttack);
    }
    public boolean isFromAttack() {
        return entityData.get(FROM_ATTACK);
    }

    public void setTameAttempts(int tameAttempts) {
        this.entityData.set(TAME_ATTEMPTS, tameAttempts);
    }
    public int getTameAttempts() {
        return entityData.get(TAME_ATTEMPTS);
    }

    @Override
    public boolean fromBucket() {
        return false;
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.GASTRIC_BROODING_FROG_BUCKET.get());
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    @Override
    public void saveToBucketTag(@NotNull ItemStack bucket) {
        MobUtils.savePrehistoricDataToBucket(this, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, (compoundTag) -> compoundTag.putInt("Variant", this.getVariant().getId()));
    }

    @Override
    public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
        MobUtils.loadPrehistoricDataFromBucket(this, compoundTag);
        this.setVariant(GastricBroodingFrogVariant.byId(compoundTag.getInt("Variant")));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        GastricBroodingFrog frog = UP2Entities.GASTRIC_BROODING_FROG.get().create(level);
        if (frog != null) {
            frog.setVariant(this.getVariant());
        }
        return frog;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.GASTRIC_BROODING_FROG_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.GASTRIC_BROODING_FROG_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.GASTRIC_BROODING_FROG_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.GASTRIC_BROODING_FROG_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public SoundEvent getEatingSound() {
        return UP2SoundEvents.GASTRIC_BROODING_FROG_EAT.get();
    }

    public enum GastricBroodingFrogVariant {
        TEMPERATE(0),
        WARM(1),
        COLD(2);

        private final int id;

        GastricBroodingFrogVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static GastricBroodingFrogVariant byId(int id) {
            if (id < 0 || id >= GastricBroodingFrogVariant.values().length) {
                id = 0;
            }
            return GastricBroodingFrogVariant.values()[id];
        }
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        if (spawnType != MobSpawnType.MOB_SUMMONED) {
            Holder<Biome> holder = level.getBiome(this.blockPosition());
            if (holder.is(BiomeTags.SPAWNS_COLD_VARIANT_FROGS)) {
                this.setVariant(GastricBroodingFrogVariant.COLD);
            } else if (holder.is(BiomeTags.SPAWNS_WARM_VARIANT_FROGS)) {
                this.setVariant(GastricBroodingFrogVariant.WARM);
            } else {
                this.setVariant(GastricBroodingFrogVariant.TEMPERATE);
            }
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }

    private static class FrogAttackGoal extends AttackGoal {

        protected final GastricBroodingFrog frog;
        private boolean strafingClockwise;
        private boolean strafingBackwards;
        private boolean strafingDownwards;
        private int strafingTime = -1;
        private int verticalStrafeTime = -1;

        public FrogAttackGoal(GastricBroodingFrog frog) {
            super(frog);
            this.frog = frog;
        }

        @Override
        public void tick() {
            LivingEntity target = frog.getTarget();
            if (target != null) {
                double distance = frog.distanceToSqr(target);

                if (frog.canSwallowTarget(target)) {
                    this.lookAtTarget(target);

                    if (this.frog.getAttackState() == 1) {
                        this.frog.getNavigation().stop();
                        this.tickEat(target);
                    }
                    else {
                        if (frog.attackCooldown == 0 && distance <= this.getAttackReachSqr(target, 1.75D)) {
                            this.frog.setAttackState(1);
                        }
                        this.frog.getNavigation().moveTo(target, 1.3D);
                    }
                }
                else {
                    if (this.frog.getAttackState() == 1) {
                        this.frog.getNavigation().stop();
                        this.tickLaunchFroglet(target);
                    } else {
                        if (frog.attackCooldown == 0) {
                            this.frog.setAttackState(1);
                        }
                        else {
                            this.strafe(target, 0.75F, distance, 14.0D);
                        }
                    }
                }
            }
        }

        @SuppressWarnings("SameParameterValue")
        private void strafe(LivingEntity target, float speed, double distance, double maxAttackDistance) {
            maxAttackDistance = maxAttackDistance * maxAttackDistance;
            if (distance <= maxAttackDistance) {
                this.frog.getNavigation().stop();
                this.strafingTime++;
                if (frog.isInWater()) {
                    this.verticalStrafeTime++;
                }
            } else {
                this.frog.getNavigation().moveTo(target, 1.3D);
                this.strafingTime = -1;
                this.verticalStrafeTime = -1;
            }

            if (strafingTime >= 20) {
                this.strafingClockwise = frog.getRandom().nextBoolean();
                this.strafingBackwards = frog.getRandom().nextBoolean();
                this.strafingTime = 0;
            }
            if (verticalStrafeTime >= 20) {
                this.verticalStrafeTime = 0;
                if (frog.level().getFluidState(frog.blockPosition().above()).isEmpty()) {
                    this.strafingDownwards = true;
                } else {
                    this.strafingDownwards = frog.getRandom().nextBoolean();
                }
            }

            if (strafingTime > -1) {
                if (distance > (maxAttackDistance * 0.75F)) {
                    this.strafingBackwards = false;
                } else if (distance < (maxAttackDistance * 0.25F)) {
                    this.strafingBackwards = true;
                }
                this.frog.getMoveControl().strafe(strafingBackwards ? -speed : speed, strafingClockwise ? speed : -speed);
                this.frog.lookAt(target, 30.0F, 30.0F);

                if (frog.isInWater() && verticalStrafeTime > -1) {
                    double speedMultiplier = strafingDownwards ? -0.02D : 0.02D;
                    this.frog.setDeltaMovement(frog.getDeltaMovement().add(0.0D, speed * speedMultiplier, 0.0D));
                }
            }
            else {
                this.frog.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
        }

        private void lookAtTarget(LivingEntity target) {
            this.frog.lookAt(target, 30.0F, 30.0F);
            this.frog.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }

        protected void tickEat(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.frog.playSound(UP2SoundEvents.GASTRIC_BROODING_FROG_TONGUE.get(), 1.0F, frog.getVoicePitch());
                this.frog.setPose(UP2Poses.EATING.get());
            }
            if (timer > 3 && timer < 10 && frog.canSwallowTarget(target)) {
                target.setDeltaMovement(target.position().vectorTo(frog.position()).normalize().scale(0.75D));
            }
            if (timer == 10 && this.isInAttackRange(target, 1.75D)) {
                if (target.isAlive()) {
                    this.frog.doHurtTarget(target);
                    if (!target.isAlive()) {
                        target.remove(RemovalReason.KILLED);
                        this.frog.gameEvent(GameEvent.EAT);
                        this.frog.playSound(frog.getEatingSound(), frog.getSoundVolume(), frog.getVoicePitch());
                    }
                }
            }
            if (timer > 10) {
                this.timer = 0;
                this.frog.setPose(Pose.STANDING);
                this.frog.attackCooldown = 10 + frog.getRandom().nextInt(10);
                this.frog.setAttackState(0);
            }
        }

        protected void tickLaunchFroglet(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.frog.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 2) {
                ServerLevel serverLevel = (ServerLevel) frog.level();
                GastricBroodingFrog froglet = UP2Entities.GASTRIC_BROODING_FROG.get().create(frog.level());
                if (froglet != null) {
                    froglet.setFromAttack(true);
                    froglet.setBaby(true);
                    froglet.setVariant(frog.getVariant());
                    froglet.setPos(frog.getX(), frog.getEyeY() - 0.335D, frog.getZ());
                    double eyeY = target.getEyeY() - (frog.isInWater() ? 2.0D : 1.25D);
                    double x = target.getX() - frog.getX();
                    double y = eyeY - froglet.getY();
                    double z = target.getZ() - frog.getZ();
                    double sqrt = Mth.sqrt((float) (x * x + y * y + z * z)) * 0.2D;
                    this.frog.gameEvent(GameEvent.PROJECTILE_SHOOT);
                    this.frog.playSound(UP2SoundEvents.GASTRIC_BROODING_FROG_LAUNCH.get(), 1.0F, frog.getVoicePitch());
                    double horizontalDistance = Math.sqrt(x * x + z * z);
                    float speed = (float) Mth.lerp(Mth.clamp(horizontalDistance / 16.0D, 0.0F, 1.0F), 1.0F, 2.25F);
                    froglet.shootFroglet(x, y + sqrt, z, speed);
                    froglet.setYRot(frog.getYRot() % 360.0F);
                    if (!frog.level().isClientSide) {
                        this.frog.level().addFreshEntity(froglet);
                        EventHooks.finalizeMobSpawn(mob, serverLevel, serverLevel.getCurrentDifficultyAt(frog.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
                    }
                }
            }

            if (timer > 10) {
                this.timer = 0;
                this.frog.setPose(Pose.STANDING);
                this.frog.attackCooldown = 15 + frog.getRandom().nextInt(10);
                this.frog.setAttackState(0);
            }
        }
    }
}
