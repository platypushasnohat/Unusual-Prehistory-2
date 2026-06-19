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
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GastricBroodingFrog extends AmphibiousMob implements Bucketable, LeapingMob {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(GastricBroodingFrog.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LAUNCHED = SynchedEntityData.defineId(GastricBroodingFrog.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_ATTACK = SynchedEntityData.defineId(GastricBroodingFrog.class, EntityDataSerializers.BOOLEAN);

    public int attackCooldown = 0;

    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState leapAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState eatAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState launchAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);

    public GastricBroodingFrog(EntityType<? extends AmphibiousMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.lookControl = new FrogLookControl(this);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F);
    }

    @Override
    protected void registerGoals() {
//        this.goalSelector.addGoal(0, new AmphibiousPanicGoal(this, 2.0D, 10, 5, true));
        this.goalSelector.addGoal(1, new FrogAttackGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_INSECTIVORE), false));
        this.goalSelector.addGoal(3, new LeaveWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new EnterWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LeapRandomlyGoal(this, 80, 6, 0.8F));
        this.goalSelector.addGoal(5, new CustomizableRandomSwimGoal(this, 1.0D, 40));
        this.goalSelector.addGoal(5, new SemiAquaticRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, true, entity -> entity.getType().is(UP2EntityTags.THYLACINE_TARGETS)));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0F)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.STEP_HEIGHT, 1.15D);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothAmphibiousNavigation(this, level);
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0D, 1.0D, 0.0D);
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
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.getEatTicks() <= 0 && this.isFood(itemstack) ) {
            int i = this.getAge();
            if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
                this.usePlayerItem(player, hand, itemstack);
                this.playSound(this.getEatingSound());
                this.setInLove(player);
                return InteractionResult.SUCCESS;
            }
        }
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
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
        return 5;
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    @Override
    public void onLeap() {
        this.setLeaping(true);
        this.level().playSound(null, this, SoundEvents.FROG_LONG_JUMP, SoundSource.NEUTRAL, 1.0F, 1.0F);
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
        return true;
    }

    protected boolean canHitEntity(Entity entity) {
        return !entity.isSpectator() && !(entity instanceof GastricBroodingFrog) && entity != this.getOwner();
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public void shootFroglet(double x, double y, double z, float scale) {
        final double angle = 57.2957763671875D;
        Vec3 shootVec = (new Vec3(x, y, z)).normalize().scale(scale);
        this.setDeltaMovement(shootVec);
        float horizontalDistance = (float) shootVec.horizontalDistanceSqr();
        this.setYRot((float) (Mth.atan2(shootVec.x, shootVec.z) * angle));
        this.setXRot((float) (Mth.atan2(shootVec.y, horizontalDistance) * angle));
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
                this.level().playSound(null, this, SoundEvents.FROG_STEP, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
            this.setLeaping(false);
        }

        if (this.wasLaunched()) {
            this.yBodyRot = this.getYRot();
            HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitResult.getType() != HitResult.Type.MISS) {
                if (hitResult.getType() == HitResult.Type.ENTITY) {
                    this.doHurtTarget(((EntityHitResult) hitResult).getEntity());
                    this.setLaunched(false);
                }
            }
        }
        if (this.isFromAttack()) {
            if (this.onGround()) {
                this.setLaunched(false);
            }
            if (this.tickCount > 60) {
                if (this.isAlive()) {
                    this.spawnAnim();
                    this.remove(Entity.RemovalReason.KILLED);
                }
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isSitting() && !this.wasLaunched(), this.tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isSitting() && !this.wasLaunched(), this.tickCount);
        this.leapAnimationState.animateWhen(this.isLeaping() && !this.wasLaunched(), this.tickCount);
        this.eatAnimationState.animateWhen(this.getPose() == UP2Poses.EATING.get(), this.tickCount);
        this.launchAnimationState.animateWhen(this.wasLaunched(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float) Mth.length(this.getX() - this.xo, includeHeight ? this.getY() - this.yo : 0.0, this.getZ() - this.zo);
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
        builder.define(LEAPING, false);
        builder.define(LAUNCHED, false);
        builder.define(FROM_ATTACK, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("FromAttack", this.isFromAttack());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setFromAttack(compoundTag.getBoolean("FromAttack"));
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    public void setLaunched(boolean launched) {
        this.entityData.set(LAUNCHED, launched);
    }

    public boolean wasLaunched() {
        return this.entityData.get(LAUNCHED);
    }

    public void setFromAttack(boolean fromAttack) {
        this.entityData.set(FROM_ATTACK, fromAttack);
    }

    public boolean isFromAttack() {
        return this.entityData.get(FROM_ATTACK);
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
        return new ItemStack(UP2Items.HYNERPETON_BUCKET.get());
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    @Override
    public void saveToBucketTag(@NotNull ItemStack bucket) {
        MobUtils.savePrehistoricDataToBucket(this, bucket);
    }

    @Override
    public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
        MobUtils.loadPrehistoricDataFromBucket(this, compoundTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.GASTRIC_BROODING_FROG.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.FROG_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return SoundEvents.FROG_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.FROG_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.FROG_STEP, 0.15F, 1.0F);
    }

    private static class FrogAttackGoal extends AttackGoal {

        protected final GastricBroodingFrog frog;

        public FrogAttackGoal(GastricBroodingFrog frog) {
            super(frog);
            this.frog = frog;
        }

        @Override
        public void tick() {
            LivingEntity target = frog.getTarget();
            if (target != null) {
                double distance = frog.distanceTo(target);
                this.frog.lookAt(target, 30.0F, 30.0F);
                this.frog.getLookControl().setLookAt(target, 30.0F, 30.0F);

                if (this.frog.getAttackState() == 1) {
                    this.frog.getNavigation().stop();
                    this.tickEat(target);
                }
                else if (this.frog.getAttackState() == 2) {
                    this.frog.getNavigation().stop();
                    this.tickLaunchFroglet(target);
                }
                else {
                    if (frog.attackCooldown == 0) {
                        if (distance <= 1.75D) {
                            this.frog.setAttackState(1);
                        }
                        if (distance > 1.75D) {
                            this.frog.setAttackState(2);
                        }
                    }
                    this.frog.getNavigation().moveTo(target, 1.2D);
                }
            }
        }

        protected void tickEat(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.frog.setPose(UP2Poses.EATING.get());
            }
            if (timer > 3 && timer < 10 && frog.canSwallowTarget(target)) {
                target.setDeltaMovement(target.position().vectorTo(frog.position()).normalize().scale(0.75D));
            }
            if (timer == 10 && this.isInAttackRange(target, 1.75D)) {
                if (target.isAlive()) {
                    this.frog.doHurtTarget(target);
                    if (!target.isAlive()) {
                        target.remove(Entity.RemovalReason.KILLED);
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
            ServerLevel serverLevel = (ServerLevel) frog.level();
            if (timer == 1) {
                this.frog.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 2) {
                GastricBroodingFrog froglet = UP2Entities.GASTRIC_BROODING_FROG.get().create(frog.level());
                if (froglet != null) {
                    froglet.setFromAttack(true);
                    froglet.setBaby(true);
                    froglet.setPos(frog.getX(), frog.getEyeY(), frog.getZ());
                    double eyeY = target.getEyeY() - (double) 1.1F;
                    double x = target.getX() - frog.getX();
                    double y = eyeY - froglet.getY();
                    double z = target.getZ() - frog.getZ();
                    float distanceSqr = Mth.sqrt((float) (x * x + y * y + z * z)) * 0.2F;
                    this.frog.gameEvent(GameEvent.PROJECTILE_SHOOT);
                    this.frog.playSound(SoundEvents.SLIME_ATTACK, 1.0F, 1.0F / (frog.getRandom().nextFloat() * 0.4F + 0.8F));
                    double horizontalDistance = Math.sqrt(x * x + z * z);
                    float speed = Mth.lerp(Mth.clamp((float) (horizontalDistance / 16.0D), 0.0F, 1.0F), 1.15F, 2.15F);
                    froglet.shootFroglet(x, y + (double) distanceSqr, z, speed);
                    froglet.setYRot(frog.getYRot() % 360.0F);
                    froglet.setXRot(Mth.clamp(frog.getYRot(), -90.0F, 90.0F) % 360.0F);
                    if (!frog.level().isClientSide) {
                        this.frog.level().addFreshEntity(froglet);
                        EventHooks.finalizeMobSpawn(mob, serverLevel, serverLevel.getCurrentDifficultyAt(frog.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
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
    }

    private static class FrogLookControl extends PrehistoricLookControl {

        public FrogLookControl(GastricBroodingFrog frog) {
            super(frog);
        }

        @Override
        protected boolean resetXRotOnTick() {
            return false;
        }
    }
}
