package com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.SaddlelessItemBasedSteering;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

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

    public final SmoothAnimationState walkAnimationState = new SmoothAnimationState();

    public Arthropleura(EntityType<? extends PrehistoricMob> type, Level level) {
        super(type, level);
        this.moveControl = new ArthropleuraMoveControl(this, 5);
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
        this.goalSelector.addGoal(1, new PrehistoricRandomStrollGoal(this, 1.0D));
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
        if (!flag && !this.isBaby() && !this.isVehicle() && !player.isSecondaryUseActive() && !player.isShiftKeyDown()) {
            if (!this.level().isClientSide) {
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(Player player, @NotNull Vec3 vec3) {
        return new Vec3(0.0D, 0.0D, 1.0D);
    }

    @Override
    protected float getRiddenSpeed(@NotNull Player player) {
        return (float) (this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.225D) * this.getBoostFactor();
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

    @Override
    protected void tickRidden(@NotNull Player player, @NotNull Vec3 travelVector) {
        float targetYaw = player.getYRot();
        float currentYaw = this.getYRot();
        float yDelta = Mth.wrapDegrees(targetYaw - currentYaw);
        this.setYRot(currentYaw + yDelta * 0.03F);
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
            } else {
                this.reapplyPosition();
            }
        }
        this.steering.tickBoost();
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isMoving(), this.tickCount);
        this.walkAnimationState.animateWhen(this.isMoving(), this.tickCount);
    }

    public boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return null;
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
        int partCount;
        if (level.getRandom().nextFloat() < 0.05F) {
            partCount = 5 + level.getRandom().nextInt(5);
        } else {
            partCount = 3 + level.getRandom().nextInt(3);
        }
        ArthropleuraPart.createArthropleuraSegments(this, partCount);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }

    private static class ArthropleuraMoveControl extends PrehistoricMoveControl {

        public ArthropleuraMoveControl(PrehistoricMob mob, float maxYRotChange) {
            super(mob, maxYRotChange);
        }

        @Override
        public void doMoveTo() {
            this.operation = Operation.WAIT;
            float speed = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            double x = wantedX - mob.getX();
            double y = wantedY - mob.getY();
            double z = wantedZ - mob.getZ();
            double direction = x * x + y * y + z * z;
            if (direction < (double) 2.5000003E-7F) {
                this.mob.setZza(0.0F);
                return;
            }
            this.rotateBody();
            this.mob.setSpeed(speed);
            if (mob.isInWaterOrBubble()) {
                this.mob.setSpeed(mob.getSpeed() * 2);
            }
            float zza = Mth.cos(mob.getXRot() * ((float) Math.PI / 180F));
            float yya = Mth.sin(mob.getXRot() * ((float) Math.PI / 180F));
            this.mob.zza = zza * speed;
            this.mob.yya = -yya * speed;
            this.tryJump(x, y, z);
        }

        @Override
        public void rotateBody() {
            double xDiff = wantedX - mob.getX();
            double zDiff = wantedZ - mob.getZ();
            float yRot = (float) Mth.wrapDegrees(Mth.atan2(zDiff, xDiff) * Mth.RAD_TO_DEG - 90.0F);
            float currentYRot = mob.getYRot();
            float yDelta = Mth.wrapDegrees(yRot - mob.getYRot());
            float moveAngle = currentYRot + yDelta * 0.15F;
            this.mob.setYRot(this.rotlerp(mob.getYRot(), moveAngle, maxYRotChange));
        }
    }
}