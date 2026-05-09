package com.barlinc.unusual_prehistory.entity.mob.update_6;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2GameEventTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;

public class Therizinosaurus extends PrehistoricMob implements VibrationSystem {

    private final TherizinosaurusPart[] allParts;
    private final TherizinosaurusPart headPart;

    @SuppressWarnings("all")
    private float[] yawBuffer = new float[128];
    private int yawPointer = -1;

    private boolean wasPreviouslyBaby;

    private int attackCooldown = 0;

    @SuppressWarnings("FieldMayBeFinal")
    private VibrationSystem.Data vibrationData;
    private final VibrationSystem.User vibrationUser;
    private final DynamicGameEventListener<MovementListener> movementListener;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState roarAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState stretchAnimationState = new SmoothAnimationState(1.0F);

    private int warnTicks = 0;
    private boolean attackAlt = false;

    public Therizinosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.headPart = new TherizinosaurusPart(this, 1.5F, 2.5F);
        this.allParts = new TherizinosaurusPart[]{headPart};
        this.vibrationUser = new VibrationUser(this);
        this.vibrationData = new VibrationSystem.Data();
        this.movementListener = new DynamicGameEventListener<>(new MovementListener(this, vibrationUser.getPositionSource()));
        this.setId(ENTITY_COUNTER.getAndAdd(allParts.length + 1) + 1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new TherizinosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.THERIZINOSAURUS_FOOD), false));
        this.goalSelector.addGoal(4, new PrehistoricRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new SleepingGoal(this));
        this.goalSelector.addGoal(7, new IdleAnimationGoal(this, 40, 1, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(7, new IdleAnimationGoal(this, 100, 2, true, 0.001F, this::canPlayIdles));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 140.0D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.STEP_HEIGHT, 1.5D);
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.allParts.length; i++) {
            this.allParts[i].setId(id + i + 1);
        }
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
        return stack.is(UP2ItemTags.THERIZINOSAURUS_FOOD);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getPose() == UP2Poses.WARNING.get();
    }

    private boolean canHearEntity(LivingEntity target) {
        return !this.isPacified() && !this.isEepy() && !(target instanceof Therizinosaurus) && this.canAttack(target) && !target.isShiftKeyDown() && (target instanceof Mob || target instanceof Player);
    }

    @Override
    public boolean isEepyTime() {
        return this.level().isDay();
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0, 0.5F, -this.getBbWidth() * 1.05F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
    }

    @Override
    public boolean isPushable() {
        return this.isBaby();
    }

    @Override
    protected void doPush(@NotNull Entity entity) {
        if (!this.isPacified() && !this.isBaby() && entity instanceof LivingEntity livingEntity && this.canAttack(livingEntity) && !(livingEntity instanceof Therizinosaurus)) {
            this.setTarget(livingEntity);
        }
        super.doPush(entity);
    }

    @Override
    public boolean killedEntity(@NotNull ServerLevel level, @NotNull LivingEntity victim) {
        this.heal(28);
        return super.killedEntity(level, victim);
    }

    @Override
    public void tick() {
        this.tickMultipart();
        super.tick();
        if (wasPreviouslyBaby != this.isBaby()) {
            this.wasPreviouslyBaby = this.isBaby();
            this.refreshDimensions();
            for (TherizinosaurusPart therizinosaurusPart : this.allParts) {
                therizinosaurusPart.refreshDimensions();
            }
        }

        // Lose target when far enough away
        if (!this.level().isClientSide && tickCount % 2 == 0) {
            LivingEntity target = this.getTarget();
            if (target != null) {
                double followRange = this.getAttributeValue(Attributes.FOLLOW_RANGE);
                if (!target.isAlive() || this.distanceToSqr(target) > followRange * followRange) {
                    this.setTarget(null);
                    this.getNavigation().stop();
                }
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy() && this.getPose() != UP2Poses.ATTACKING.get() && !this.isInWaterOrBubble(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.roarAnimationState.animateWhen(this.getPose() == UP2Poses.WARNING.get(), this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.stretchAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
    }

    private boolean canPlayIdles(Entity entity) {
        return !entity.isInWaterOrBubble() && !((Therizinosaurus) entity).isBaby();
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.WARNING.get()) {
                this.warnTicks = 60;
            }
            else if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.attackAlt = this.getRandom().nextBoolean();
            }
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackCooldown > 0) {
            this.attackCooldown--;
        }
        if (warnTicks > 0) {
            this.warnTicks--;
            if (warnTicks == 50) {
                this.playSound(UP2SoundEvents.THERIZINOSAURUS_ROAR.get(), 2.0F, this.getVoicePitch());
            }
        }
        if (warnTicks == 0 && this.getPose() == UP2Poses.WARNING.get()) {
            this.setPose(Pose.STANDING);
        }
    }

    private void tickMultipart() {
        if (yawPointer == -1) {
            for (int i = 0; i < yawBuffer.length; i++) {
                this.yawBuffer[i] = this.yBodyRot;
            }
        }
        if (++this.yawPointer == this.yawBuffer.length) {
            this.yawPointer = 0;
        }
        this.yawBuffer[this.yawPointer] = this.yBodyRot;

        Vec3[] vec3 = new Vec3[this.allParts.length];
        for (int j = 0; j < this.allParts.length; ++j) {
            vec3[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
        }

        Vec3 center = this.position().add(0, this.getBbHeight(), 0);
        this.headPart.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0.0F, 2.8F).scale(this.getAgeScale()), yBodyRot).add(center));

        for (int l = 0; l < this.allParts.length; l++) {
            this.allParts[l].xo = vec3[l].x;
            this.allParts[l].yo = vec3[l].y;
            this.allParts[l].zo = vec3[l].z;
            this.allParts[l].xOld = vec3[l].x;
            this.allParts[l].yOld = vec3[l].y;
            this.allParts[l].zOld = vec3[l].z;
        }
    }

    private Vec3 rotateOffsetVec(Vec3 offset, float yRot) {
        return offset.yRot(-yRot * ((float) Math.PI / 180F));
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?> @NotNull [] getParts() {
        return allParts;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.THERIZINOSAURUS.get().create(level);
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.25F : 1.0F;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.THERIZINOSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.THERIZINOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.THERIZINOSAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.THERIZINOSAURUS_STEP.get(), this.isBaby() ? 0.15F : 0.3F, this.isBaby() ? 1.5F : 1.0F);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(2.0D, 0.5D, 2.0D);
    }

    @Override
    public void updateDynamicGameEventListener(@NotNull BiConsumer<DynamicGameEventListener<?>, ServerLevel> biConsumer) {
        if (this.level() instanceof ServerLevel serverLevel) {
            biConsumer.accept(this.movementListener, serverLevel);
        }
    }

    @Override
    public @NotNull Data getVibrationData() {
        return this.vibrationData;
    }

    @Override
    public @NotNull User getVibrationUser() {
        return this.vibrationUser;
    }

    // Goals
    private static class TherizinosaurusAttackGoal extends AttackGoal {

        private final Therizinosaurus therizinosaurus;

        public TherizinosaurusAttackGoal(Therizinosaurus therizinosaurus) {
            super(therizinosaurus);
            this.therizinosaurus = therizinosaurus;
        }

        @Override
        public void start() {
            super.start();
            this.mob.setPose(UP2Poses.WARNING.get());
        }

        @Override
        public void tick() {
            LivingEntity target = therizinosaurus.getTarget();
            if (target != null) {
                if (therizinosaurus.getPose() == UP2Poses.WARNING.get()) {
                    this.therizinosaurus.lookAt(target, 30F, 30F);
                    this.therizinosaurus.getLookControl().setLookAt(target, 30F, 30F);
                }
                if (therizinosaurus.getAttackState() == 1) {
                    this.tickAttack(target);
                    this.therizinosaurus.getNavigation().stop();
                } else if (therizinosaurus.getPose() == Pose.STANDING) {
                    if (this.isInAttackRange(target, 1.05D)) {
                        this.therizinosaurus.setAttackState(1);
                    }
                    this.therizinosaurus.lookAt(target, 30F, 30F);
                    this.therizinosaurus.getLookControl().setLookAt(target, 30F, 30F);
                    this.therizinosaurus.getNavigation().moveTo(target, 2.0D);
                }
            }
        }

        private void tickAttack(LivingEntity target) {
            this.timer++;
            if (timer < 5) {
                this.therizinosaurus.lookAt(target, 30F, 30F);
                this.therizinosaurus.getLookControl().setLookAt(target, 30F, 30F);
            }
            if (timer == 5) {
                this.therizinosaurus.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 10) {
                this.therizinosaurus.playSound(UP2SoundEvents.THERIZINOSAURUS_ATTACK.get(), 1.0F, therizinosaurus.getVoicePitch());
            }
            if (timer == 20) {
                this.attackNearbyEntities();
            }
            if (timer > 25) {
                this.timer = 0;
                this.therizinosaurus.setPose(Pose.STANDING);
                this.therizinosaurus.attackCooldown = 10;
                this.therizinosaurus.setAttackState(0);
            }
        }

        private void attackNearbyEntities() {
            AABB attackBox = therizinosaurus.getBoundingBox().move(therizinosaurus.getLookAngle().normalize().scale(2.0D)).inflate(2.0D);
            List<LivingEntity> nearbyEntities = therizinosaurus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), therizinosaurus, attackBox);
            if (!nearbyEntities.isEmpty()) {
                nearbyEntities.stream().filter(entity -> entity != therizinosaurus).limit(5).forEach(entity -> {
                    entity.hurt(entity.damageSources().mobAttack(therizinosaurus), (float) therizinosaurus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    this.strongKnockback(entity, 1.0D, 0.1D);
                    if (entity.isDamageSourceBlocked(therizinosaurus.damageSources().mobAttack(therizinosaurus)) && entity instanceof Player player) {
                        player.disableShield();
                    }
                });
            }
        }
    }

    private static class VibrationUser implements VibrationSystem.User {

        private final Therizinosaurus therizinosaurus;
        private final PositionSource positionSource;

        private VibrationUser(Therizinosaurus therizinosaurus) {
            this.therizinosaurus = therizinosaurus;
            this.positionSource = new EntityPositionSource(therizinosaurus, therizinosaurus.getEyeHeight());
        }

        @Override
        public int getListenerRadius() {
            return 8;
        }

        @Override
        public @NotNull PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public boolean canReceiveVibration(@NotNull ServerLevel level, @NotNull BlockPos blockPos, @NotNull Holder<GameEvent> gameEvent, GameEvent.@NotNull Context context) {
            if (therizinosaurus.isNoAi() || therizinosaurus.isPacified() || therizinosaurus.isEepy() || therizinosaurus.isBaby() || !therizinosaurus.isAlive() || therizinosaurus.getTarget() != null) {
                return false;
            }
            else if (context.sourceEntity() == null) {
                return false;
            }
            else {
                return !(context.sourceEntity() instanceof Therizinosaurus) && context.sourceEntity() instanceof LivingEntity livingEntity && !livingEntity.isShiftKeyDown() && livingEntity.getDeltaMovement().horizontalDistance() > 0.03D;
            }
        }

        @Override
        public void onReceiveVibration(@NotNull ServerLevel level, @NotNull BlockPos blockPos, @NotNull Holder<GameEvent> gameEvent, @Nullable Entity entity, @Nullable Entity entity1, float f) {
        }

        public @NotNull TagKey<GameEvent> getListenableEvents() {
            return UP2GameEventTags.THERIZINOSAURUS_CAN_LISTEN;
        }
    }

    private record MovementListener(Therizinosaurus therizinosaurus, PositionSource listenerSource) implements GameEventListener {

        @Override
        public @NotNull PositionSource getListenerSource() {
            return this.listenerSource;
        }

        @Override
        public int getListenerRadius() {
            return 8;
        }

        @Override
        public boolean handleGameEvent(@NotNull ServerLevel level, @NotNull Holder<GameEvent> gameEvent, GameEvent.@NotNull Context context, @NotNull Vec3 pos) {
            if (context.sourceEntity() instanceof LivingEntity livingEntity && therizinosaurus.canHearEntity(livingEntity) && gameEvent.is(UP2GameEventTags.THERIZINOSAURUS_CAN_LISTEN)) {
                this.therizinosaurus.setTarget(livingEntity);
                return true;
            } else {
                return false;
            }
        }
    }
}
