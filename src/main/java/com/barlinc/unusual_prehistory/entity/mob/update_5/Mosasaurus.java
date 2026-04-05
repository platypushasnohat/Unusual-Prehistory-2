package com.barlinc.unusual_prehistory.entity.mob.update_5;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricBodyRotationControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.GrabbingMob;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class Mosasaurus extends PrehistoricAquaticMob implements LeapingMob, GrabbingMob {

    private static final EntityDataAccessor<Integer> HELD_MOB_ID = SynchedEntityData.defineId(Mosasaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Mosasaurus.class, EntityDataSerializers.BOOLEAN);

    public final MosasaurusPart headPart;
    public final MosasaurusPart tailPart1;
    public final MosasaurusPart tailPart2;
    private final MosasaurusPart[] allParts;

    private boolean wasPreviouslyBaby;

    private float fakeYRot = 0;
    private float[] yawBuffer = new float[128];
    private int yawPointer = -1;

    public int biteCooldown = 0;
    public int grabCooldown = 0;
    private int grabTicks;

    private int yawnCooldown = 500 + this.getRandom().nextInt(50 * 50);
    private int tongueCooldown = 150 + this.getRandom().nextInt(30 * 30);
    private int nipCooldown = 600 + this.getRandom().nextInt(60 * 60);

    public final SmoothAnimationState leapAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState grabStartAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState grabAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState tongueAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState nip1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState nip2AnimationState = new SmoothAnimationState(1.0F);

    private boolean attackAlt = false;
    private boolean nipAlt = false;

    private int grabStartTicks;
    private int grabEndTicks;

    public Mosasaurus(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 1000, 5, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 4);
        this.headPart = new MosasaurusPart(this, this, 2.5F, 2.1F);
        this.tailPart1 = new MosasaurusPart(this, this, 2.3F, 2.4F);
        this.tailPart2 = new MosasaurusPart(this, tailPart1, 2.1F, 1.7F);
        this.allParts = new MosasaurusPart[]{headPart, tailPart1, tailPart2};
        this.setId(ENTITY_COUNTER.getAndAdd(allParts.length + 1) + 1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 240.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.95F)
                .add(Attributes.ATTACK_DAMAGE, 18.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LargeBabyPanicGoal(this, 2.7D, 16, 8));
        this.goalSelector.addGoal(1, new AquaticLeapGoal(this, 10, 1.0D, 1.25D));
        this.goalSelector.addGoal(2, new MosasaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new BreathAirGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.MOSASAURUS_FOOD), false));
        this.goalSelector.addGoal(5, new CustomizableRandomSwimGoal(this, 1.0D, 20, 30, 15, 3, true));
        this.goalSelector.addGoal(6, new MosasaurusYawnGoal(this));
        this.goalSelector.addGoal(6, new MosasaurusTongueGoal(this));
        this.goalSelector.addGoal(6, new MosasaurusNipGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, false, entity -> entity.getType().is(UP2EntityTags.MOSASAURUS_FIGHT_TARGETS)));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 400, true, true, entity -> entity.getType().is(UP2EntityTags.MOSASAURUS_TARGETS)));
        this.targetSelector.addGoal(3, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 400, true, true, this::canAttack));
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new PrehistoricBodyRotationControl(this, 0.4F, 25.0F, 0.2F, 20.0F, 0.8F, this.getMaxHeadYRot());
    }

//    @Override
//    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
//        return size.height * 0.6F;
//    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.allParts.length; i++) {
            this.allParts[i].setId(id + i + 1);
        }
    }

    @Override
    public float getScale() {
        return this.isBaby() ? 0.25F : 1.0F;
    }

    @Override
    public boolean killedEntity(@NotNull ServerLevel level, @NotNull LivingEntity victim) {
        this.heal(40);
        return super.killedEntity(level, victim);
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
            if (!this.isEyeInFluid(FluidTags.WATER)) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean shouldFlop() {
        return false;
    }

    @Override
    public void tick() {
        this.tickMultipart();
        super.tick();
        if (biteCooldown > 0 && this.getHeldMobId() == -1) biteCooldown--;
        if (grabCooldown > 0) grabCooldown--;
        if (grabTicks > 0) grabTicks--;

        this.fakeYRot = Mth.approachDegrees(fakeYRot, this.yBodyRot, 10);

        if (wasPreviouslyBaby != this.isBaby()) {
            this.wasPreviouslyBaby = this.isBaby();
            this.refreshDimensions();
            for (MosasaurusPart mosasaurusPart : this.allParts) {
                mosasaurusPart.refreshDimensions();
            }
        }

        if ((this.getPose() == UP2Poses.GRAB_START.get() || this.getPose() == UP2Poses.GRAB.get()) && this.getHeldMobId() != -1) {
            this.positionHeldMob();
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.leapAnimationState.animateWhen(this.isLeaping(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.grabStartAnimationState.animateWhen(this.getPose() == UP2Poses.GRAB_START.get(), this.tickCount);
        this.grabAnimationState.animateWhen(this.getPose() == UP2Poses.GRAB.get(), this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.tongueAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.nip1AnimationState.animateWhen(this.getIdleState() == 3 && !nipAlt, this.tickCount);
        this.nip2AnimationState.animateWhen(this.getIdleState() == 3 && nipAlt, this.tickCount);
    }

    @Override
    public void tickCooldowns() {
        if (grabStartTicks > 0) grabStartTicks--;
        if (grabEndTicks > 0) grabEndTicks--;
        if (grabStartTicks == 0 && this.getPose() == UP2Poses.GRAB_START.get()) {
            if (this.getHeldMobId() != -1) this.setPose(UP2Poses.GRAB.get());
            else this.setPose(Pose.STANDING);
        }
        if (yawnCooldown > 0) yawnCooldown--;
        if (tongueCooldown > 0) tongueCooldown--;
        if (nipCooldown > 0) nipCooldown--;
    }

    private void tickMultipart() {
        if (yawPointer == -1) {
            this.fakeYRot = this.yBodyRot;
            for (int i = 0; i < yawBuffer.length; i++) {
                this.yawBuffer[i] = this.fakeYRot;
            }
        }
        if (++this.yawPointer == this.yawBuffer.length) {
            this.yawPointer = 0;
        }
        this.yawBuffer[this.yawPointer] = this.fakeYRot;

        Vec3[] avector3d = new Vec3[this.allParts.length];
        for (int j = 0; j < this.allParts.length; ++j) {
            avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
        }
        Vec3 center = this.position().add(0, this.getBbHeight() * 0.5F, 0);
        float headOffset = this.isBaby() ? 1.0F : 3.7F;
        this.headPart.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, headOffset), this.getXRot() * 0.33F, this.getYHeadRot()).add(center));
        this.tailPart1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, this.isBaby() ? -0.7F : -3.2F), this.getXRot() * 0.33F, this.getYawFromBuffer(2, 1.0F)).add(center));
        this.tailPart2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, this.isBaby() ? -0.6F : -2.9F), this.getXRot() * 0.33F, this.getYawFromBuffer(4, 1.0F)).add(this.tailPart1.centeredPosition()));
        for (int l = 0; l < this.allParts.length; ++l) {
            this.allParts[l].xo = avector3d[l].x;
            this.allParts[l].yo = avector3d[l].y;
            this.allParts[l].zo = avector3d[l].z;
            this.allParts[l].xOld = avector3d[l].x;
            this.allParts[l].yOld = avector3d[l].y;
            this.allParts[l].zOld = avector3d[l].z;
        }
    }

    public float getTrailTransformation(int pointer, float partialTick) {
        if (this.isRemoved()) {
            partialTick = 1.0F;
        }
        int i = this.yawPointer - pointer & 127;
        int j = this.yawPointer - pointer - 1 & 127;
        float d0 = this.yawBuffer[j];
        float d1 = this.yawBuffer[i] - d0;
        return d0 + d1 * partialTick;
    }

    private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
        return offset.xRot(-xRot * ((float) Math.PI / 180F)).yRot(-yRot * ((float) Math.PI / 180F));
    }

    public float getYawFromBuffer(int pointer, float partialTick) {
        int i = this.yawPointer - pointer & 127;
        int j = this.yawPointer - pointer - 1 & 127;
        float d0 = this.yawBuffer[j];
        float d1 = this.yawBuffer[i] - d0;
        return d0 + d1 * partialTick;
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?>[] getParts() {
        return allParts;
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3, 3, 3);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.GRAB_START.get()) {
                this.grabStartTicks = 20;
            }
            else if (this.getPose() == UP2Poses.GRAB.get()) {
                this.grabTicks = 60;
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    protected void yawnCooldown() {
        this.yawnCooldown = 500 + this.getRandom().nextInt(50 * 50);
    }

    protected void tongueCooldown() {
        this.tongueCooldown = 150 + this.getRandom().nextInt(30 * 30);
    }

    protected void nipCooldown() {
        this.nipCooldown = 600 + this.getRandom().nextInt(60 * 60);
    }

    private void positionHeldMob() {
        Entity entity = level().getEntity(this.getHeldMobId());
        if (entity != null) {
            if (grabTicks < 60) {
                Vec3 heldPos = this.position().add(new Vec3(0.0F, 0.25F, 4.5F).yRot(-yBodyRot * ((float) Math.PI / 180F)));
                Vec3 minus = new Vec3(heldPos.x - entity.getX(), heldPos.y - entity.getY(), heldPos.z - entity.getZ());
                entity.setDeltaMovement(minus);
                entity.fallDistance = 0.0F;
                entity.setYRot(0.0F);
                entity.setYBodyRot(0.0F);
                entity.setYHeadRot(0.0F);
                entity.setXRot(0.0F);
                entity.setDeltaMovement(minus);
                if (grabTicks % 20 == 0) {
                    entity.hurt(damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5F);
                }
            }
        }
    }

    public boolean canGrabTarget(LivingEntity target) {
        if (target == null) {
            return false;
        }
        if (target.getType().is(UP2EntityTags.MOSASAURUS_CANT_GRAB)) {
            return false;
        }
        return (target.getBbWidth() < this.getBbWidth() && target.getBbHeight() < this.getBbHeight()) || target.getType().is(UP2EntityTags.MOSASAURUS_CAN_GRAB);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.MOSASAURUS_FOOD);
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_MOSASAURUS);
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.MOSASAURUS.get().create(level);
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HELD_MOB_ID, -1);
        builder.define(LEAPING, false);
    }

    @Override
    public void setHeldMobId(int id) {
        this.entityData.set(HELD_MOB_ID, id);
    }

    @Override
    public int getHeldMobId() {
        return this.entityData.get(HELD_MOB_ID);
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.MOSASAURUS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.MOSASAURUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.MOSASAURUS_DEATH.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 350;
    }

    @Override
    public float getSoundVolume() {
        return this.isBaby() ? 1.0F : 3.0F;
    }

    private static class MosasaurusAttackGoal extends AttackGoal {

        private final Mosasaurus mosasaurus;

        public MosasaurusAttackGoal(Mosasaurus mosasaurus) {
            super(mosasaurus);
            this.mosasaurus = mosasaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && (mosasaurus.getTarget().isInWaterOrBubble() || !mosasaurus.isInWaterOrBubble());
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && (mosasaurus.getTarget().isInWaterOrBubble() || !mosasaurus.isInWaterOrBubble());
        }

        @Override
        public void tick() {
            LivingEntity target = this.mosasaurus.getTarget();
            if (target != null) {
                double distance = this.mosasaurus.distanceToSqr(target);
                int attackState = this.mosasaurus.getAttackState();

                this.mosasaurus.getLookControl().setLookAt(target, 30F, 30F);
                this.mosasaurus.lookAt(target, 30F, 30F);

                if (attackState == 1) this.tickBite();
                else if (attackState == 2) {
                    this.tickGrab();
                    this.mosasaurus.getNavigation().moveTo(target, 2.7D);
                }
                else {
                    if (distance <= this.getAttackReachSqr(target)) {
                        if (this.canGrab()) this.mosasaurus.setAttackState(2);
                        else if (mosasaurus.biteCooldown == 0 && !this.canGrab()) this.mosasaurus.setAttackState(1);
                    } else {
                        this.mosasaurus.getNavigation().moveTo(target, 2.5D);
                    }
                }
            }
        }

        private boolean canGrab() {
            return mosasaurus.grabCooldown == 0 && mosasaurus.canGrabTarget(mosasaurus.getTarget()) && mosasaurus.isInWaterOrBubble();
        }

        protected void tickBite() {
            this.timer++;
            if (timer == 1) {
                this.mosasaurus.attackAlt = mosasaurus.getRandom().nextBoolean();
                this.mosasaurus.setPose(UP2Poses.ATTACKING.get());
                this.mosasaurus.playSound(UP2SoundEvents.MOSASAURUS_ATTACK.get(), 2.0F, 1.0F * mosasaurus.getRandom().nextFloat() * 0.2F);
            }
            if (timer == 9) this.biteNearbyEntities();
            if (timer > 20) {
                this.timer = 0;
                this.mosasaurus.setPose(Pose.STANDING);
                this.mosasaurus.setAttackState(0);
                this.mosasaurus.biteCooldown = 7;
            }
        }

        protected void tickGrab() {
            this.timer++;
            LivingEntity target = mosasaurus.getTarget();
            if (timer == 1) {
                this.mosasaurus.setPose(UP2Poses.GRAB_START.get());
                this.mosasaurus.playSound(UP2SoundEvents.MOSASAURUS_ATTACK.get(), 2.0F, 1.0F * mosasaurus.getRandom().nextFloat() * 0.2F);
            }
            if (timer == 9) {
                if (mosasaurus.distanceTo(target) < 5.5F) {
                    this.mosasaurus.setHeldMobId(target.getId());
                }
            }
            if (timer > 70 || (timer > 10 && mosasaurus.getHeldMobId() == -1)) {
                this.timer = 0;
                this.mosasaurus.setPose(Pose.STANDING);
                this.mosasaurus.setAttackState(0);
                this.mosasaurus.grabCooldown = 300 + mosasaurus.getRandom().nextInt(300);
                if (mosasaurus.getHeldMobId() != -1) {
                    this.mosasaurus.setHeldMobId(-1);
                }
            }
        }

        private void biteNearbyEntities() {
            List<LivingEntity> nearbyEntities = mosasaurus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), mosasaurus, mosasaurus.getBoundingBox().inflate(3.5D));
            if (!nearbyEntities.isEmpty()) {
                nearbyEntities.stream().filter(entity -> entity != mosasaurus).limit(5).forEach(entity -> {
                    entity.hurt(entity.damageSources().mobAttack(mosasaurus), (float) mosasaurus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    this.mosasaurus.strongKnockback(entity, 2.25D, 0.1D);
                    if (entity.isDamageSourceBlocked(mosasaurus.damageSources().mobAttack(mosasaurus)) && entity instanceof Player player) {
                        player.disableShield();
                    }
                    this.mosasaurus.swing(InteractionHand.MAIN_HAND);
                });
            }
        }
    }

    private static class MosasaurusYawnGoal extends IdleAnimationGoal {

        private final Mosasaurus mosasaurus;

        public MosasaurusYawnGoal(Mosasaurus mosasaurus) {
            super(mosasaurus, 60, 1, false, false);
            this.mosasaurus = mosasaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && mosasaurus.yawnCooldown == 0 && mosasaurus.isInWaterOrBubble();
        }

        @Override
        public void stop() {
            super.stop();
            this.mosasaurus.yawnCooldown();
        }
    }

    private static class MosasaurusTongueGoal extends IdleAnimationGoal {

        private final Mosasaurus mosasaurus;

        public MosasaurusTongueGoal(Mosasaurus mosasaurus) {
            super(mosasaurus, 40, 2, false, false);
            this.mosasaurus = mosasaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && mosasaurus.tongueCooldown == 0 && mosasaurus.isInWaterOrBubble();
        }

        @Override
        public void stop() {
            super.stop();
            this.mosasaurus.tongueCooldown();
        }
    }

    private static class MosasaurusNipGoal extends IdleAnimationGoal {

        private final Mosasaurus mosasaurus;

        public MosasaurusNipGoal(Mosasaurus mosasaurus) {
            super(mosasaurus, 20, 3, false, false);
            this.mosasaurus = mosasaurus;
        }

        @Override
        public void start() {
            super.start();
            this.mosasaurus.nipAlt = mosasaurus.getRandom().nextBoolean();
        }

        @Override
        public boolean canUse() {
            return super.canUse() && mosasaurus.nipCooldown == 0 && mosasaurus.isInWaterOrBubble();
        }

        @Override
        public void stop() {
            super.stop();
            this.mosasaurus.nipCooldown();
        }
    }
}
