package com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.SaddlelessItemBasedSteering;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class Arthropleura extends PrehistoricMob implements ItemSteerable {

    private static final EntityDataAccessor<Integer> BOOST_TIME = SynchedEntityData.defineId(Arthropleura.class, EntityDataSerializers.INT);

    private final SaddlelessItemBasedSteering steering = new SaddlelessItemBasedSteering(this.entityData, BOOST_TIME);

    private int lSteps;
    private double lx;
    private double ly;
    private double lz;
    private double lyr;
    private double lxr;
    private double lxd;
    private double lyd;
    private double lzd;

    public Arthropleura(EntityType<? extends PrehistoricMob> type, Level level) {
        super(type, level);
        this.moveControl = new ArthropleuraMoveControl(this, 5);
        this.lookControl = new ArthropleuraLookControl(this, 5);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.225F)
                .add(Attributes.ARMOR, 12.0D)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ArthropleuraRandomStrollGoal(this));
    }

    @Override
    public boolean isFood(@NotNull ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return super.canCollideWith(entity) && !(entity instanceof ArthropleuraPart) && !(entity instanceof Arthropleura);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        this.floatWhileRidden(this, travelVec);
        super.travel(travelVec);
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && this.horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.48D * this.getBbHeight();
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        if (entity instanceof Player player) {
            if (player.getMainHandItem().is(UP2Items.BROWN_MUSHROOM_ON_A_STICK.get()) || player.getOffhandItem().is(UP2Items.BROWN_MUSHROOM_ON_A_STICK.get())) {
                return player;
            }
        }
        return null;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        boolean flag = this.isFood(player.getItemInHand(hand));
        if (player.isShiftKeyDown()) {
            Entity leashed = this.getLeashed(player).orElse(null);
            if (leashed instanceof Mob mob) {
                mob.stopRiding();
                mob.setLeashedTo(player, true);
                mob.startRiding(this, true);
                return InteractionResult.SUCCESS;
            }
            else if (leashed == null) {
                for (Entity entity : this.getAllRidingEntities()) {
                    entity.stopRiding();
                    this.yeetPassengers(entity);
                }
                for (Entity passenger : this.getPassengers()) {
                    if (this.getControllingPassenger() != passenger) {
                        passenger.stopRiding();
                        this.yeetPassengers(passenger);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        else if (!flag && !this.isBaby() && !this.isVehicle() && !player.isSecondaryUseActive() && !player.isShiftKeyDown()) {
            if (!this.level().isClientSide) {
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        else {
            return super.mobInteract(player, hand);
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean canBeLeashed() {
        return true;
    }

    private void yeetPassengers(Entity passenger) {
        if (!this.level().isClientSide && passenger instanceof LivingEntity living) {
            double x = (passenger.getRandom().nextDouble() - 0.5D) * 0.5D;
            double y = 0.25D + passenger.getRandom().nextDouble() * 0.25D;
            double z = (passenger.getRandom().nextDouble() - 0.5D) * 0.5D;
            living.setDeltaMovement(living.getDeltaMovement().add(x, y, z));
            living.hasImpulse = true;
        }
    }

    public Optional<Entity> getLeashed(Player player) {
        List<Entity> entities = player.level().getEntities((Entity) null, player.getBoundingBox().inflate(10), entity -> true);
        for (Entity entity : entities) {
            if (entity instanceof Mob mob && mob.getLeashHolder() == player) {
                return Optional.of(mob);
            }
        }
        return Optional.empty();
    }

    public List<Entity> getAllRidingEntities() {
        return this.level().getEntities((Entity) null, this.getBoundingBox().inflate(32), entity -> entity.getVehicle() instanceof ArthropleuraPart part && part.getHeadEntity() == this);
    }
    @Override
    protected @NotNull Vec3 getRiddenInput(Player player, @NotNull Vec3 vec3) {
        return new Vec3(0.0D, 0.0D, 1.0D);
    }

    @Override
    protected float getRiddenSpeed(@NotNull Player player) {
        return (float) (this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.35D) * this.getBoostFactor();
    }

    public float getBoostFactor() {
        return this.steering.boostFactor();
    }

    public boolean isBoosting() {
        return this.steering.isBoosting();
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity passenger) {
        return new Vec3(this.getX(), this.getBoundingBox().maxY + 0.1F, this.getZ());
    }

    @Override
    public boolean boost() {
        return this.steering.boost(this.getRandom());
    }

    private float getTurnSpeed() {
        if (!this.isBoosting()) {
            return 0.03F;
        }
        float factor = 1.0F - Mth.sin(this.steering.getBoostProgress() * (float) Math.PI);
        factor = Mth.lerp(factor * factor, 0.008F / 0.03F, 1.0F);
        return 0.03F * factor;
    }

    @Override
    protected void tickRidden(@NotNull Player player, @NotNull Vec3 travelVector) {
        float targetYaw = player.getYRot();
        float currentYaw = this.getYRot();
        float yDelta = Mth.wrapDegrees(targetYaw - currentYaw);
        this.setYRot(currentYaw + yDelta * this.getTurnSpeed());
    }

    @Override
    public void tick() {
        super.tick();
        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
        if (this.level().isClientSide) {
            if (this.lSteps > 0) {
                double x = this.getX() + (this.lx - this.getX()) / (double) this.lSteps;
                double y = this.getY() + (this.ly - this.getY()) / (double) this.lSteps;
                double z = this.getZ() + (this.lz - this.getZ()) / (double) this.lSteps;
                this.setYRot(Mth.wrapDegrees((float) this.lyr));
                this.setXRot(this.getXRot() + (float) (this.lxr - (double) this.getXRot()) / (float) this.lSteps);
                this.lSteps--;
                this.setPos(x, y, z);
                this.calculateEntityAnimation(false);
            } else {
                this.reapplyPosition();
            }
        }
        this.steering.tickBoost();
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        Arthropleura arthropleura = UP2Entities.ARTHROPLEURA.get().create(serverLevel);
        if (arthropleura != null) {
            serverLevel.getServer().execute(() -> {
                if (!arthropleura.isRemoved()) {
                    ArthropleuraPart.createArthropleuraSegments(arthropleura, 5 + serverLevel.getRandom().nextInt(3));
                }
            });
        }
        return arthropleura;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void push(@NotNull Entity entity) {
        if (!this.isPassengerOfSameVehicle(entity) && !(entity instanceof ArthropleuraPart)) {
            super.push(entity);
        }
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FALL);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(2);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (BOOST_TIME.equals(accessor) && this.level().isClientSide) {
            this.steering.onSynced();
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BOOST_TIME, 0);
    }

    @Override
    public void lerpTo(double x, double y, double z, float yr, float xr, int steps) {
        this.lx = x;
        this.ly = y;
        this.lz = z;
        this.lyr = yr;
        this.lxr = xr;
        this.lSteps = steps;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    @Override
    public void lerpMotion(double lerpX, double lerpY, double lerpZ) {
        this.lxd = lerpX;
        this.lyd = lerpY;
        this.lzd = lerpZ;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.ARTHROPLEURA_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.ARTHROPLEURA_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.ARTHROPLEURA_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.ARTHROPLEURA_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        ArthropleuraPart.createArthropleuraSegments(this, 5 + level.getRandom().nextInt(3));
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }

    // Goals
    public static class ArthropleuraRandomStrollGoal extends PrehistoricRandomStrollGoal {

        protected Vec3 wantedPos;

        public ArthropleuraRandomStrollGoal(PathfinderMob mob) {
            super(mob, 1.0D, 80, true);
        }

        @Override
        public boolean canContinueToUse() {
            this.wantedPos = new Vec3(this.wantedX, this.wantedY, this.wantedZ);
            return super.canContinueToUse() && !(this.wantedPos.distanceTo(mob.position()) <= mob.getBbWidth() * 4);
        }

        @Nullable
        @Override
        protected Vec3 getPosition() {
            Vec3 randomPos;
            if (mob.isInWater()) {
                randomPos = LandRandomPos.getPos(mob, 30, 8);
                return randomPos == null ? LandRandomPos.getPos(mob, 20, 7) : randomPos;
            }
            randomPos = mob.getRandom().nextFloat() > 0.001F ? LandRandomPos.getPos(mob, 20, 7) : DefaultRandomPos.getPos(mob, 20, 7);
            return randomPos;
        }
    }

    private static class ArthropleuraMoveControl extends PrehistoricMoveControl {

        public ArthropleuraMoveControl(PrehistoricMob mob, float maxYRotChange) {
            super(mob, maxYRotChange);
        }

        @Override
        public void doMoveTo() {
            if (!prehistoricMob.refuseToMove()) {
                if (this.operation == Operation.MOVE_TO && !mob.getNavigation().isDone()) {
                    double xDiff = wantedX - mob.getX();
                    double zDiff = wantedZ - mob.getZ();
                    double horizontalDist = xDiff * xDiff + zDiff * zDiff;
                    if (horizontalDist < (double) 2.5000003E-7F) {
                        this.mob.setZza(0.0F);
                    } else {
                        if (horizontalDist > 0.12D) {
                            float yRot = (float) Mth.wrapDegrees(Mth.atan2(zDiff, xDiff) * Mth.RAD_TO_DEG - 90.0F);
                            float currentYRot = mob.getYRot();
                            float yDelta = Mth.wrapDegrees(yRot - mob.getYRot());
                            float moveAngle = currentYRot + yDelta * 0.15F;
                            this.mob.setYRot(this.rotlerp(mob.getYRot(), moveAngle, maxYRotChange));
                        }
                        float speed = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                        this.mob.setSpeed(speed);
                        float zza = Mth.cos(mob.getXRot() * ((float) Math.PI / 180F));
                        float yya = Mth.sin(mob.getXRot() * ((float) Math.PI / 180F));
                        this.mob.zza = zza * speed;
                        this.mob.yya = -yya * speed;
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

    private static class ArthropleuraLookControl extends PrehistoricLookControl {

        private final int maxYRotFromCenter;

        public ArthropleuraLookControl(PrehistoricMob mob, int maxYRotFromCenter) {
            super(mob);
            this.maxYRotFromCenter = maxYRotFromCenter;
        }

        @Override
        public void tick() {
            if (!mob.refuseToLook()) {
                if (this.resetXRotOnTick()) {
                    this.mob.setXRot(0.0F);
                }
                if (this.lookAtCooldown > 0) {
                    this.lookAtCooldown--;
                    this.getYRotD().ifPresent(f -> this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, f + 20.0F, this.yMaxRotSpeed));
                    this.getXRotD().ifPresent(f -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), f + 10.0F, this.xMaxRotAngle)));
                } else {
                    this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, this.yMaxRotSpeed);
                }
                float f = Mth.wrapDegrees(this.mob.yHeadRot - this.mob.yBodyRot);
                if (f < (float) (-this.maxYRotFromCenter)) {
                    this.mob.yBodyRot -= 4.0F;
                } else if (f > (float) this.maxYRotFromCenter) {
                    this.mob.yBodyRot += 4.0F;
                }
            }
        }
    }
}