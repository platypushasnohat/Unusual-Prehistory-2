package com.barlinc.unusual_prehistory.entity.mob.update_1;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.goals.update_1.MajungasaurusAttackGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class Majungasaurus extends PrehistoricMob implements VariantHolder<Majungasaurus.MajungasaurusVariant> {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Majungasaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CAMO = SynchedEntityData.defineId(Majungasaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> CAMO_COOLDOWN = SynchedEntityData.defineId(Majungasaurus.class, EntityDataSerializers.INT);

    public float prevCamoProgress;
    public float camoProgress;

    public float prevAngryProgress;
    public float angryProgress;

    public final SmoothAnimationState eyesAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState swimAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sniff1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sniff2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();

    public boolean attackAlt = false;
    private boolean sniffAlt = false;

    public Majungasaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.7D, 10, 4));
        this.goalSelector.addGoal(2, new MajungasaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.MAJUNGASAURUS_FOOD), false));
        this.goalSelector.addGoal(4, new PrehistoricRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new MajungasaurusAvoidEntityGoal<>(this, Majungasaurus.class, this::avoidsParents) {
            public boolean canUse(){
                return super.canUse() && Majungasaurus.this.isBaby();
            }
        });
        this.goalSelector.addGoal(6, new MajungasaurusAvoidEntityGoal<>(this, LivingEntity.class, entity -> entity.getType().is(UP2EntityTags.MAJUNGASAURUS_AVOIDS)));
        this.goalSelector.addGoal(7, new SleepingGoal(this));
        this.goalSelector.addGoal(8, new IdleAnimationGoal(this, 60, 1, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(8, new IdleAnimationGoal(this, 80, 2, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 80, 3, true, 0.001F, this::canPlayIdles) {
            @Override
            public void start() {
                super.start();
                Majungasaurus.this.sniffAlt = Majungasaurus.this.getRandom().nextBoolean();
            }

            @Override
            public void tick() {
                super.tick();
                if (timer == 50) Majungasaurus.this.playSound(UP2SoundEvents.MAJUNGASAURUS_SNIFF.get(), 1.0F, Majungasaurus.this.getVoicePitch());
            }
        });
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, Majungasaurus.class, 200, true, true, this::canCannibalize));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.MAJUNGASAURUS_TARGETS)));
        this.targetSelector.addGoal(3, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 300, true, true, this::attacksPlayers));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 32.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.19F)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                .add(Attributes.ARMOR, 4.0D)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource source, float amount) {
        if (this.isCamo()) {
            this.setCamo(false);
            this.camoCooldown();
        }
        super.actuallyHurt(source, amount);
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
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.MAJUNGASAURUS_FOOD);
    }

    public float getCamoProgress(float partialTicks) {
        return (prevCamoProgress + (camoProgress - prevCamoProgress) * partialTicks) * 0.2F;
    }

    public float getAngryProgress(float partialTicks) {
        return (prevAngryProgress + (angryProgress - prevAngryProgress) * partialTicks) * 0.2F;
    }

    public boolean canCannibalize(LivingEntity entity) {
        return this.canAttack(entity) && (entity.getHealth() < entity.getMaxHealth() * 0.5F || entity.isBaby());
    }

    public boolean avoidsParents(LivingEntity entity) {
        Majungasaurus majungasaurus = (Majungasaurus) entity;
        return this.isBaby() && !entity.isBaby() && !majungasaurus.isCamo();
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    public boolean isNightTime() {
        return this.level().getDayTime() < 23000 && this.level().getDayTime() > 12000 && !this.level().dimensionType().hasFixedTime();
    }

    protected boolean attacksPlayers(LivingEntity entity) {
        return this.canAttack(entity) && this.isNightTime();
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 3;
    }

    @Override
    public boolean isEepyTime() {
        return this.level().isDay();
    }

    @Override
    public void tick () {
        super.tick();

        this.prevCamoProgress = camoProgress;
        this.prevAngryProgress = angryProgress;

        if (this.isCamo() && camoProgress < 5F) {
            this.camoProgress++;
        }
        if (!this.isCamo() && camoProgress > 0F) {
            this.camoProgress--;
        }

        if (this.isAggressive() && angryProgress < 5F) {
            this.angryProgress++;
        }
        if (!this.isAggressive() && angryProgress > 0F) {
            this.angryProgress--;
        }

        if (this.getCamoCooldown() > 0) this.setCamoCooldown(this.getCamoCooldown() - 1);
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWater() && !this.isEepy(), this.tickCount);
        this.eyesAnimationState.animateWhen(!this.isAggressive() && !this.isEepy(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWater(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.sniff1AnimationState.animateWhen(this.getIdleState() == 3 && !sniffAlt, this.tickCount);
        this.sniff2AnimationState.animateWhen(this.getIdleState() == 3 && sniffAlt, this.tickCount);
    }

    @Override
    public float getWalkAnimationSpeed() {
        return this.isBaby() ? 2.0F : 4.0F;
    }

    private boolean canPlayIdles(Entity entity) {
        return !entity.isInWaterOrBubble();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 850 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 2) {
            return 900 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 3) {
            return 1000 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(CAMO, false);
        builder.define(CAMO_COOLDOWN, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant().getId());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(MajungasaurusVariant.byId(compoundTag.getInt("Variant")));
    }

    @Override
    public @NotNull MajungasaurusVariant getVariant() {
        return MajungasaurusVariant.byId(this.entityData.get(VARIANT));
    }

    @Override
    public void setVariant(MajungasaurusVariant variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant.getId(), 0, MajungasaurusVariant.values().length));
    }

    public boolean isCamo() {
        return this.entityData.get(CAMO);
    }

    public void setCamo(boolean camo) {
        this.entityData.set(CAMO, camo);
    }

    public int getCamoCooldown() {
        return this.entityData.get(CAMO_COOLDOWN);
    }

    public void setCamoCooldown(int cooldown) {
        this.entityData.set(CAMO_COOLDOWN, cooldown);
    }

    public void camoCooldown() {
        this.setCamoCooldown(30 + this.getRandom().nextInt(10));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        Majungasaurus majungasaurus = UP2Entities.MAJUNGASAURUS.get().create(level);
        if (majungasaurus != null) {
            majungasaurus.setVariant(this.getVariant());
        }
        return majungasaurus;
    }

    @Override
    public boolean canPlayAmbientSound() {
        return !this.isCamo() && super.canPlayAmbientSound();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.MAJUNGASAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.MAJUNGASAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.MAJUNGASAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
       if (!this.isCamo()) {
           this.playSound(UP2SoundEvents.MAJUNGASAURUS_STEP.get(), 1.0F, 0.9F);
       }
    }

    @Override
    protected @NotNull MovementEmission getMovementEmission() {
        return this.isCamo() ? MovementEmission.NONE : super.getMovementEmission();
    }

    public enum MajungasaurusVariant {
        CHAMELEON(0),
        DUSKLURKER(1);

        private final int id;

        MajungasaurusVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static MajungasaurusVariant byId(int id) {
            if (id < 0 || id >= MajungasaurusVariant.values().length) {
                id = 0;
            }
            return MajungasaurusVariant.values()[id];
        }
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        if (level.getRandom().nextBoolean() && level.getLevel().isNight()) {
            this.setVariant(MajungasaurusVariant.DUSKLURKER);
        } else {
            this.setVariant(MajungasaurusVariant.CHAMELEON);
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }

    // Goals
    private static class MajungasaurusAvoidEntityGoal<T extends LivingEntity> extends PrehistoricAvoidEntityGoal<T> {

        private final Majungasaurus majungasaurus;

        public MajungasaurusAvoidEntityGoal(Majungasaurus mob, Class<T> classToAvoid, Predicate<LivingEntity> predicateOnAvoid) {
            super(mob, classToAvoid, 12.0F, 1.7D, predicateOnAvoid);
            this.majungasaurus = mob;
        }

        @Override
        public void start() {
            super.start();
            if (majungasaurus.getCamoCooldown() == 0) majungasaurus.setCamo(true);
        }

        @Override
        public void stop() {
            super.stop();
            this.majungasaurus.setCamo(false);
            this.majungasaurus.camoCooldown();
        }

        @Override
        public void tick() {
            this.mob.getNavigation().setSpeedModifier(majungasaurus.isCamo() ? 0.9D : 2.0D);
        }
    }
}
