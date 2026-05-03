package com.barlinc.unusual_prehistory.entity.mob.update_6;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Concavenator extends PrehistoricMob {

    private static final EntityDataAccessor<Boolean> SAND_SWIMMING = SynchedEntityData.defineId(Concavenator.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SAND_SWIM_COOLDOWN = SynchedEntityData.defineId(Concavenator.class, EntityDataSerializers.INT);

    private static final EntityDimensions SAND_SWIMMING_DIMENSIONS = EntityDimensions.scalable(1.5F, 0.5F).withEyeHeight(0.4F);

    public boolean isLandNavigator;

    public final SmoothAnimationState sandSwimIdleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sandSwimStartAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sandSwimEndAnimationState = new SmoothAnimationState();

    private int sandSwimStartTicks;
    private int sandSwimEndTicks;

    public Concavenator(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.CONCAVENATOR_FOOD), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.2D));
        this.goalSelector.addGoal(4, new SandSwimmingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new ConcavenatorRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                .add(Attributes.MOVEMENT_SPEED, 0.24F)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        if (this.isSandSwimming()) {
            if (this.shouldBePushedDown()) {
                this.addDeltaMovement(new Vec3(0, -0.7F, 0));
            }
        }
        super.travel(travelVec);
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.getNavigation().stop();
            this.moveControl = new PrehistoricMoveControl(this);
            this.lookControl = new PrehistoricLookControl(this);
            this.isLandNavigator = true;
        } else {
            this.getNavigation().stop();
            this.moveControl = new SandSwimmingMoveControl(this, 10);
            this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
            this.isLandNavigator = false;
        }
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        if (this.isSandSwimming()) return SAND_SWIMMING_DIMENSIONS.scale(this.getAgeScale());
        return super.getDefaultDimensions(pose);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.CONCAVENATOR_FOOD);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.isSwitchingToSandSwim();
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypes.IN_WALL);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isSandSwimming() && this.isLandNavigator) {
            this.switchNavigator(false);
        }
        if (!this.isSandSwimming() && !this.isLandNavigator) {
            this.switchNavigator(true);
        }

        if (!this.level().isClientSide) {
            this.tickSandSwimming();
        }
        else if (this.isSandSwimming() && !this.isInWaterOrBubble() && this.getDeltaMovement().horizontalDistance() > 0.05D) {
            for (int i = 0; i < 2; i++) {
                double velocityX = this.random.nextGaussian() * 0.15D;
                double velocityY = this.random.nextGaussian() * 0.15D;
                double velocityZ = this.random.nextGaussian() * 0.15D;
                this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()), this.getRandomX(1), this.getRandomY() - 0.5D, this.getRandomZ(1) - 0.75D, velocityX, velocityY, velocityZ);
            }
        }
    }

    private void tickSandSwimming() {
        if (this.getSandSwimCooldown() > 0) {
            this.setSandSwimCooldown(this.getSandSwimCooldown() - 1);
        }

        if (this.isSandSwimmingBlockBelow(2) && !this.isSandSwimming() && this.getPose() == Pose.STANDING && this.getSandSwimCooldown() == 0 && !this.isInWaterOrBubble()) {
            this.setSandSwimming(true);
            this.setPose(UP2Poses.START_SWIMMING.get());
        }

        if (this.isSandSwimming() && this.getPose() == Pose.STANDING && (!this.isSandSwimmingBlockBelow(3) || this.isInWaterOrBubble())) {
            this.setSandSwimming(false);
            this.setSandSwimCooldown(200 + this.getRandom().nextInt(200));
            if (this.onGround()) {
                this.setPose(UP2Poses.STOP_SWIMMING.get());
            } else {
                this.setPose(Pose.STANDING);
            }
        }
    }

    private boolean isSandSwimmingBlockBelow(int depth) {
        int valid = 0;
        for (int i = 1; i <= depth; i++) {
            BlockState state = this.level().getBlockState(this.blockPosition().below(i));
            if (state.is(UP2BlockTags.CONCAVENATOR_SWIMS_ON)) {
                valid++;
            }
        }
        return valid >= 2;
    }

    private boolean shouldBePushedDown() {
        if (this.onGround()) {
            return false;
        }
        return this.fallDistance > 0.0F && this.fallDistance < 0.2F && this.canStepDownBlock();
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy() && !this.isSandSwimming() && !this.isInWaterOrBubble(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.sandSwimStartAnimationState.animateWhen(this.getPose() == UP2Poses.START_SWIMMING.get(), this.tickCount);
        this.sandSwimEndAnimationState.animateWhen(this.getPose() == UP2Poses.STOP_SWIMMING.get(), this.tickCount);
        this.sandSwimIdleAnimationState.animateWhen(this.isSandSwimming() && !this.isSwitchingToSandSwim() && !this.isInWaterOrBubble(), this.tickCount);
    }

    public boolean isSwitchingToSandSwim() {
        return this.getPose() == UP2Poses.START_SWIMMING.get() || this.getPose() == UP2Poses.STOP_SWIMMING.get();
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (this.sandSwimStartTicks > 0) sandSwimStartTicks--;
        if (this.sandSwimEndTicks > 0) sandSwimEndTicks--;
        if (this.sandSwimStartTicks == 0 && this.getPose() == UP2Poses.START_SWIMMING.get()) {
            this.setPose(Pose.STANDING);
        }
        if (this.sandSwimEndTicks == 0 && this.getPose() == UP2Poses.STOP_SWIMMING.get()) {
            this.setPose(Pose.STANDING);
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (SAND_SWIMMING.equals(accessor)) {
            this.refreshDimensions();
        }
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.START_SWIMMING.get()) {
                this.sandSwimStartTicks = 20;
            }
            else if (this.getPose() == UP2Poses.STOP_SWIMMING.get()) {
                this.sandSwimEndTicks = 20;
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SAND_SWIMMING, false);
        builder.define(SAND_SWIM_COOLDOWN, 80);
    }

    public boolean isSandSwimming() {
        return this.entityData.get(SAND_SWIMMING);
    }

    public void setSandSwimming(boolean digging) {
        this.entityData.set(SAND_SWIMMING, digging);
    }

    public int getSandSwimCooldown() {
        return this.entityData.get(SAND_SWIM_COOLDOWN);
    }

    public void setSandSwimCooldown(int cooldown) {
        this.entityData.set(SAND_SWIM_COOLDOWN, cooldown);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.CONCAVENATOR.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.MAJUNGASAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.MAJUNGASAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.MAJUNGASAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        if (this.isSandSwimming()) {
            SoundType soundtype = state.getSoundType(this.level(), pos, this);
            this.playSound(soundtype.getHitSound(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
        } else {
            this.playSound(UP2SoundEvents.MAJUNGASAURUS_STEP.get(), 0.2F, 1.0F);
        }
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }

    // Goals
    private static class ConcavenatorRandomStrollGoal extends PrehistoricRandomStrollGoal {

        private final Concavenator concavenator;

        public ConcavenatorRandomStrollGoal(Concavenator concavenator, double speedModifier) {
            super(concavenator, speedModifier, true);
            this.concavenator = concavenator;
        }

        @Override
        public boolean canUse() {
            return !concavenator.isSandSwimming() && concavenator.isLandNavigator && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !concavenator.isSandSwimming() && concavenator.isLandNavigator && super.canContinueToUse();
        }
    }

    private static class SandSwimmingRandomStrollGoal extends PrehistoricRandomStrollGoal {

        private final Concavenator concavenator;

        public SandSwimmingRandomStrollGoal(Concavenator concavenator, double speedModifier) {
            super(concavenator, speedModifier, 40, true);
            this.concavenator = concavenator;
        }

        @Override
        public boolean canUse() {
            return concavenator.isSandSwimming() && !concavenator.isLandNavigator && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return concavenator.isSandSwimming() && !concavenator.isLandNavigator && super.canContinueToUse();
        }

        @Nullable
        @Override
        protected Vec3 getPosition() {
            RandomSource random = concavenator.getRandom();
            BlockPos.MutableBlockPos mutable = concavenator.blockPosition().mutable();

            for (int i = 0; i < 10; i++) {
                mutable.move(random.nextInt(20) - 10, random.nextInt(8) - 4, random.nextInt(20) - 10);
                if (concavenator.level().getBlockState(mutable).is(UP2BlockTags.CONCAVENATOR_SWIMS_ON)) {
                    return Vec3.atCenterOf(mutable.immutable());
                }
            }

            return super.getPosition();
        }
    }

    private static class SandSwimmingMoveControl extends PrehistoricMoveControl {

        private final int maxTurnY;

        public SandSwimmingMoveControl(PrehistoricMob mob, int maxTurnY) {
            super(mob);
            this.maxTurnY = maxTurnY;
        }

        @Override
        public void tick() {
            if (!prehistoricMob.refuseToMove()) {
                if (this.operation == Operation.MOVE_TO && !mob.getNavigation().isDone()) {
                    double d0 = wantedX - mob.getX();
                    double d2 = wantedZ - mob.getZ();
                    double d3 = d0 * d0 + d2 * d2;
                    if (d3 < (double) 2.5000003E-7F) {
                        this.mob.setZza(0.0F);
                    } else {
                        float f = (float) (Mth.atan2(d2, d0) * (double) 180.0F / (double) (float) Math.PI) - 90.0F;
                        this.mob.setYRot(this.rotlerp(mob.getYRot(), f, (float) maxTurnY));
                        this.mob.yBodyRot = mob.getYRot();
                        this.mob.yHeadRot = mob.getYRot();
                        float f1 = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                        this.mob.setSpeed(f1 * 2);
                        float f6 = Mth.cos(mob.getXRot() * ((float) Math.PI / 180F));
                        float f4 = Mth.sin(mob.getXRot() * ((float) Math.PI / 180F));
                        this.mob.zza = f6 * f1;
                        this.mob.yya = -f4 * f1;
                    }
                } else {
                    this.mob.setSpeed(0.0F);
                    this.mob.setXxa(0.0F);
                    this.mob.setYya(0.0F);
                    this.mob.setZza(0.0F);
                }
            }
        }
    }
}
