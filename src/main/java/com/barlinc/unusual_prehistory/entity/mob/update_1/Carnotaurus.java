package com.barlinc.unusual_prehistory.entity.mob.update_1;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.goals.update_1.CarnotaurusAttackGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2MobEffects;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Carnotaurus extends PrehistoricMob {

    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(Carnotaurus.class, EntityDataSerializers.BOOLEAN);

    public int chargeCooldown = 200 + this.getRandom().nextInt(200);
    public int roarCooldown = 100;
    public int biteCooldown = 0;
    public int headbuttCooldown = 0;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState chargeStartAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState chargeEndAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState headbuttAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState angryAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState roarAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState swimAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attackFast1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attackFast2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState headbuttFastAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sniff1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sniff2AnimationState = new SmoothAnimationState();

    public boolean attackAlt = false;
    private boolean sniffAlt = false;

    private int startChargeTicks;
    private int stopChargeTicks;

    public Carnotaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.7D, 10, 4));
        this.goalSelector.addGoal(2, new CarnotaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.CARNOTAURUS_FOOD), false));
        this.goalSelector.addGoal(4, new PrehistoricRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new SleepingGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && !Carnotaurus.this.isAngry();
            }
        });
        this.goalSelector.addGoal(7, new IdleAnimationGoal(this, 60, 1, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(8, new IdleAnimationGoal(this, 40, 2, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 80, 3, true, 0.001F, this::canPlayIdles) {
            @Override
            public void start() {
                super.start();
                Carnotaurus.this.sniffAlt = Carnotaurus.this.getRandom().nextBoolean();
            }

            @Override
            public void tick() {
                super.tick();
                if (timer == 50) Carnotaurus.this.playSound(UP2SoundEvents.CARNOTAURUS_SNIFF.get(), 1.0F, Carnotaurus.this.getVoicePitch());
            }
        });
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 100, true, true, this::canAttack));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, 60, true, true, entity -> !entity.getType().is(UP2EntityTags.CARNOTAURUS_IGNORES)));
        this.targetSelector.addGoal(3, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.CARNOTAURUS_TARGETS)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.ATTACK_DAMAGE, 9.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.STEP_HEIGHT, 1.1D);
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

    public void roar() {
        if (this.isAlive()) {
            this.level().broadcastEntityEvent(this, (byte) 39);
            this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
            this.addEffect(new MobEffectInstance(UP2MobEffects.FURY, 300, 0));
            this.gameEvent(GameEvent.ENTITY_ACTION);
        }
    }

    public boolean isFurious() {
        return this.hasEffect(UP2MobEffects.FURY);
    }

    @Override
    public boolean killedEntity(@NotNull ServerLevel level, @NotNull LivingEntity victim) {
        this.heal(10);
        return super.killedEntity(level, victim);
    }

    public boolean isWithinYRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - this.getY()) < 2;
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_CARNOTAURUS);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.CARNOTAURUS_FOOD);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 3;
    }

    public void chargeCooldown() {
        this.chargeCooldown = 200 + this.getRandom().nextInt(200);
    }

    public void roarCooldown() {
        this.roarCooldown = 600 + this.getRandom().nextInt(400);
    }

    @Override
    public void tick() {
        super.tick();
        if (chargeCooldown > 0) chargeCooldown--;
        if (roarCooldown > 0) roarCooldown--;
        if (headbuttCooldown > 0) headbuttCooldown--;
        if (biteCooldown > 0) biteCooldown--;
        this.setAngry(this.getHealth() < this.getMaxHealth() * 0.5F && !this.isBaby());
    }

    public void setupAnimationStates() {
        this.angryAnimationState.animateWhen(this.canPlayAngryAnimation(), this.tickCount);
        this.idleAnimationState.animateWhen(this.getPose() != Pose.ROARING && !this.isInWater() && !this.isEepy(), this.tickCount);
        this.swimAnimationState.animateWhen(this.getPose() != Pose.ROARING && this.isInWater(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.attackFast1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING_FAST.get() && !attackAlt, this.tickCount);
        this.attackFast2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING_FAST.get() && attackAlt, this.tickCount);
        this.headbuttAnimationState.animateWhen(this.getPose() == UP2Poses.HEADBUTTING.get(), this.tickCount);
        this.headbuttFastAnimationState.animateWhen(this.getPose() == UP2Poses.HEADBUTTING_FAST.get(), this.tickCount);
        this.chargeStartAnimationState.animateWhen(this.getPose() == UP2Poses.START_CHARGING.get(), this.tickCount);
        this.chargeEndAnimationState.animateWhen(this.getPose() == UP2Poses.STOP_CHARGING.get(), this.tickCount);
        this.roarAnimationState.animateWhen(this.getPose() == UP2Poses.ROARING.get(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.sniff1AnimationState.animateWhen(this.getIdleState() == 3 && !sniffAlt, this.tickCount);
        this.sniff2AnimationState.animateWhen(this.getIdleState() == 3 && sniffAlt, this.tickCount);
    }

    private boolean canPlayAngryAnimation() {
        if (this.getPose() == UP2Poses.ROARING.get() && this.getPose() == UP2Poses.ATTACKING.get() && this.getPose() == UP2Poses.ATTACKING_FAST.get() && this.getPose() == UP2Poses.HEADBUTTING.get() && this.getPose() == UP2Poses.HEADBUTTING_FAST.get()) {
            return false;
        }
        return this.isAngry();
    }

    private boolean canPlayIdles(Entity entity) {
        return !entity.isInWaterOrBubble();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 900 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 2) {
            return 800 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 3) {
            return 1000 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public float getWalkAnimationSpeed() {
        return this.isBaby() ? 2.0F : 4.0F;
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (this.startChargeTicks > 0) startChargeTicks--;
        if (this.stopChargeTicks > 0) stopChargeTicks--;
        if (this.startChargeTicks == 0 && this.getPose() == UP2Poses.START_CHARGING.get()) this.setPose(UP2Poses.CHARGING.get());
        if (this.stopChargeTicks == 0 && this.getPose() == UP2Poses.STOP_CHARGING.get()) this.setPose(Pose.STANDING);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.START_CHARGING.get()) {
                this.startChargeTicks = 30;
            }
            else if (this.getPose() == UP2Poses.STOP_CHARGING.get()) {
                this.stopChargeTicks = 15;
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 39) {
            this.roarEffect();
        } else {
            super.handleEntityEvent(id);
        }
    }

    private void roarEffect() {
        for (int i = 0; i < 10; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANGRY, false);
    }

    public boolean isAngry() {
        return this.entityData.get(ANGRY);
    }

    public void setAngry(boolean angry) {
        this.entityData.set(ANGRY, angry);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        Carnotaurus carnotaurus = UP2Entities.CARNOTAURUS.get().create(level);
        if (carnotaurus != null) {
            carnotaurus.setVariant(this.getVariant());
        }
        return carnotaurus;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.CARNOTAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.CARNOTAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.CARNOTAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.CARNOTAURUS_STEP.get(), this.isBaby() ? 0.25F : 1.0F, this.isBaby() ? 1.2F : 0.9F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }

    public enum CarnotaurusVariant {
        DEMON(0),
        GOLDEN_EMPEROR(1);

        private final int id;

        CarnotaurusVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static CarnotaurusVariant byId(int id) {
            if (id < 0 || id >= CarnotaurusVariant.values().length) {
                id = 0;
            }
            return CarnotaurusVariant.values()[id];
        }
    }

    @Override
    public int getVariantCount() {
        return CarnotaurusVariant.values().length;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        if (level.getRandom().nextFloat() < 0.25F) {
            this.setVariant(1);
        }
        else this.setVariant(0);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }
}
