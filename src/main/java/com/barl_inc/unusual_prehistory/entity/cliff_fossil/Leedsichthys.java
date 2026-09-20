package com.barl_inc.unusual_prehistory.entity.cliff_fossil;

import com.barl_inc.unusual_prehistory.entity.base.AquaticPrehistoricMob;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.platypushasnohat.sinew.entity.ai.control.SwimmingMoveControl;
import com.platypushasnohat.sinew.entity.ai.goal.SwimWanderGoal;
import com.platypushasnohat.sinew.entity.utils.BodyChain;
import com.platypushasnohat.sinew.entity.utils.BodyChainMob;
import com.platypushasnohat.sinew.entity.utils.SinewPartEntity;
import com.platypushasnohat.sinew.entity.utils.SinewPathfindingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;

import javax.annotation.Nullable;
import java.util.Arrays;

public class Leedsichthys extends AquaticPrehistoricMob implements BodyChainMob {

    private final SinewPartEntity<Leedsichthys> headPart;
    private final SinewPartEntity<Leedsichthys> tailPart1;
    private final SinewPartEntity<Leedsichthys> tailPart2;
    private final SinewPartEntity<?>[] allParts;

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private final float[] yawBuffer = new float[128];
    private int yawPointer = -1;

    private boolean wasPreviouslyBaby;

    public final BodyChain bodyChain = new BodyChain(0.15F, 5.0F, 30.0F, 0.06F, new float[]{0.1F, 0.14F, 0.2F}, new float[]{0.1F, 0.1F, 0.13F});

    public float prevSwimPitch;
    public float swimPitch;

    public Leedsichthys(EntityType<? extends Leedsichthys> entityType, Level level) {
        super(entityType, level);
        this.switchShallowNavigation(false);
        this.moveControl = new SwimmingMoveControl(this, 45, 4, 0.02F);
        this.lookControl = new SmoothSwimmingLookControl(this, 5);
        this.headPart = new SinewPartEntity<>(this, 4.5F, 4.25F);
        this.tailPart1 = new SinewPartEntity<>(this, 4.5F, 4.25F);
        this.tailPart2 = new SinewPartEntity<>(this, 4.5F, 4.25F);
        this.allParts = new SinewPartEntity[]{this.headPart, this.tailPart1, this.tailPart2};
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 500.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.63F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimWanderGoal(this, 1.0D, 60, 30, 15, 2, 100));
    }

    private void tickMultipart() {
        if (this.yawPointer == -1) {
            Arrays.fill(this.yawBuffer, this.yBodyRot);
        }
        if (++this.yawPointer == this.yawBuffer.length) {
            this.yawPointer = 0;
        }
        this.yawBuffer[this.yawPointer] = this.yBodyRot;

        Vec3[] vec3s = new Vec3[this.allParts.length];
        for (int j = 0; j < this.allParts.length; j++) {
            vec3s[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
        }

        Vec3 center = this.position().add(0, this.getBbHeight() * 0.5F, 0);
        float offset = 4.55F;
        this.headPart.setPosCenteredY(SinewPartEntity.rotateOffsetVec(new Vec3(0, 0, offset), this.getXRot() * 0.5F, this.getYRot()).add(center));
        this.tailPart1.setPosCenteredY(SinewPartEntity.rotateOffsetVec(new Vec3(0, 0, -offset), this.getXRot() * 0.5F, this.getYRot()).add(center));
        this.tailPart2.setPosCenteredY(SinewPartEntity.rotateOffsetVec(new Vec3(0, 0, -offset), this.getXRot() * 0.5F, this.getYRot()).add(this.tailPart1.centeredPosition()));

        for (int l = 0; l < this.allParts.length; l++) {
            this.allParts[l].xo = vec3s[l].x;
            this.allParts[l].yo = vec3s[l].y;
            this.allParts[l].zo = vec3s[l].z;
            this.allParts[l].xOld = vec3s[l].x;
            this.allParts[l].yOld = vec3s[l].y;
            this.allParts[l].zOld = vec3s[l].z;
        }
    }

    public float getSwimPitch(float partialTicks) {
        return Mth.lerp(partialTicks, this.prevSwimPitch, this.swimPitch);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        if (this.getRandom().nextFloat() < 0.23F) {
            return SinewPathfindingUtils.getDepthPathfindingFavor(pos, level);
        }
        return super.getWalkTargetValue(pos, level);
    }

    @Override
    public BodyChain getBodyChain() {
        return this.bodyChain;
    }

    @Override
    public float getRenderYaw(float partialTicks) {
        return this.bodyChain.getRenderYaw(partialTicks);
    }

    @Override
    public float getSegmentYawOffset(int index, float partialTicks) {
        return this.bodyChain.getSegmentYawOffset(index, partialTicks);
    }

    @Override
    public float getSegmentPitchOffset(int index, float partialTicks) {
        return this.bodyChain.getSegmentPitchOffset(index, partialTicks, this.getSwimPitch(partialTicks));
    }

    @Override
    public void tick() {
        if (!this.isBaby()) {
            this.tickMultipart();
            SinewPartEntity.pushEntities(this, this.allParts);
            if (!this.level().isClientSide) {
                SinewPartEntity.resolveCollisions(this, this.allParts);
            }
        }
        super.tick();

        this.prevSwimPitch = this.swimPitch;
        float targetPitch = 0.0F;
        Vec3 movement = this.getDeltaMovement();
        boolean grounded = this.onGround() || this.verticalCollisionBelow;
        if (!grounded && movement.horizontalDistanceSqr() > 1.0E-7 && (this.isInWater() || movement.lengthSqr() > 0.03D)) {
            targetPitch = -((float) (Mth.atan2(movement.y, movement.horizontalDistance()) * Mth.RAD_TO_DEG));
            targetPitch = Mth.clamp(targetPitch, -40.0F, 40.0F);
        }
        this.swimPitch += (targetPitch - this.swimPitch) * (grounded ? 0.25F : 0.07F);
        this.bodyChain.tick(this.yBodyRot, this.swimPitch, targetPitch);

        if (this.wasPreviouslyBaby != this.isBaby()) {
            this.wasPreviouslyBaby = this.isBaby();
            this.refreshDimensions();
            for (SinewPartEntity<?> part : this.allParts) {
                part.refreshDimensions();
            }
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (this.allParts != null) {
            for (SinewPartEntity<?> part : this.allParts) {
                part.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    public PartEntity<?>[] getParts() {
        return this.isBaby() ? super.getParts() : this.allParts;
    }

    @Override
    public boolean isMultipartEntity() {
        return !this.isBaby();
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.1F : 1.0F;
    }

    @Override
    public boolean isPushable() {
        return this.isBaby();
    }

    @Override
    public boolean shouldFlop() {
        return false;
    }

    @Override
    public boolean shouldUseShallowNavigation() {
        return true;
    }

    @Override
    public void setupAnimationStates() {
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float length = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float speed = Math.min(length * (this.isBaby() ? 5.0F : 10.0F), 1.0F);
        this.walkAnimation.update(speed, 0.4F);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    @Nullable
    public Leedsichthys getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return UP2Entities.LEEDSICHTHYS.get().create(level);
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(5);
    }

    @Override
    public float getSoundVolume() {
        return this.isBaby() ? 1.0F : 3.0F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.LEEDSICHTHYS_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.LEEDSICHTHYS_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return UP2SoundEvents.LEEDSICHTHYS_HURT.get();
    }

    @Override
    protected SoundEvent getSwimSound() {
        if (this.isBaby()) {
            return super.getSwimSound();
        }
        return UP2SoundEvents.LEEDSICHTHYS_SWIM.get();
    }

    @Override
    protected void playSwimSound(float volume) {
        if (this.isBaby()) {
            super.playSwimSound(volume);
        } else {
            if (this.getRandom().nextFloat() < 0.13F) {
                super.playSwimSound(volume);
            }
        }
    }
}
