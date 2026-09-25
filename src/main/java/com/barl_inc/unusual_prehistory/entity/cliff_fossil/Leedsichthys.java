package com.barl_inc.unusual_prehistory.entity.cliff_fossil;

import com.barl_inc.unusual_prehistory.entity.base.AquaticPrehistoricMob;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.platypushasnohat.sinew.Sinew;
import com.platypushasnohat.sinew.entity.ai.control.SwimmingMoveControl;
import com.platypushasnohat.sinew.entity.ai.goal.SwimWanderGoal;
import com.platypushasnohat.sinew.entity.ai.goal.TamedSitGoal;
import com.platypushasnohat.sinew.entity.utils.BodyChain;
import com.platypushasnohat.sinew.entity.utils.BodyChainMob;
import com.platypushasnohat.sinew.entity.utils.KeybindUsingMount;
import com.platypushasnohat.sinew.entity.utils.SinewPartEntity;
import com.platypushasnohat.sinew.network.MountedEntityKeyPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class Leedsichthys extends AquaticPrehistoricMob implements BodyChainMob, KeybindUsingMount {

    private final SinewPartEntity<Leedsichthys> headPart;
    private final SinewPartEntity<Leedsichthys> tailPart1;
    private final SinewPartEntity<Leedsichthys> tailPart2;
    private final SinewPartEntity<?>[] allParts;

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private final float[] yawBuffer = new float[128];
    private int yawPointer = -1;

    private boolean wasPreviouslyBaby;

    private final BodyChain bodyChain = new BodyChain(0.15F, 5.0F, 30.0F, 0.06F, new float[]{0.1F, 0.14F, 0.2F}, new float[]{0.1F, 0.1F, 0.13F});

    private float prevSwimPitch;
    private float swimPitch;

    private int controlUpTicks = 0;
    private int controlDownTicks = 0;

    public Leedsichthys(EntityType<? extends Leedsichthys> entityType, Level level) {
        super(entityType, level);
        this.switchShallowNavigation(false);
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
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
        this.goalSelector.addGoal(0, new TamedSitGoal(this, true));
        this.goalSelector.addGoal(1, new SwimWanderGoal(this, 1.0D, 50, 15, 10));
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.getCommand() == COMMAND_SIT && !this.isControlledByLocalInstance()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVector = Vec3.ZERO;
        }
        if (this.isControlledByLocalInstance() && this.getControllingPassenger() instanceof Player player && this.isInWaterOrBubble()) {
            this.travelRidden(travelVector, player);
        } else {
            super.travel(travelVector);
        }
    }

    private void travelRidden(Vec3 travelVector, Player player) {
        this.moveRelative(this.getRiddenSpeed(player), travelVector);
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        this.calculateEntityAnimation(false);
        if (this.controlDownTicks > 0 && !this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0F, -0.02D, 0.0F));
        } else if (this.controlUpTicks > 0 && this.getFluidTypeHeight(NeoForgeMod.WATER_TYPE.value()) > 4.0D) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0F, 0.02D, 0.0F));
        }
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
        float yaw = this.bodyChain.getRenderYaw();
        this.headPart.setPosCenteredY(SinewPartEntity.rotateOffsetVec(new Vec3(0, 0, offset), this.swimPitch, yaw).add(center));
        this.tailPart1.setPosCenteredY(SinewPartEntity.rotateOffsetVec(new Vec3(0, 0, -offset), this.swimPitch, yaw).add(center));
        this.tailPart2.setPosCenteredY(SinewPartEntity.rotateOffsetVec(new Vec3(0, 0, -offset), this.swimPitch, yaw).add(this.tailPart1.centeredPosition()));

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

    private void yeetPassenger(Entity passenger) {
        if (passenger instanceof LivingEntity living) {
            double x = (living.getRandom().nextDouble() - 0.5D) * 0.5D;
            double y = 0.25D + living.getRandom().nextDouble() * 0.25D;
            double z = (living.getRandom().nextDouble() - 0.5D) * 0.5D;
            living.hasImpulse = true;
            living.setDeltaMovement(living.getDeltaMovement().add(x, y, z));
        }
    }

    private void removePassengers() {
        for (Entity passenger : this.getPassengers()) {
            if (this.getControllingPassenger() != passenger) {
                passenger.stopRiding();
                if (!this.level().isClientSide) {
                    this.yeetPassenger(passenger);
                }
            }
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!this.isBaby()) {
            if (this.isTame()) {
                if (player.isShiftKeyDown()) {
                    if (!this.getPassengers().isEmpty()) {
                        this.removePassengers();
                    }
                    else if (this.getOwner() == player) {
                        if (this.getCommand() != COMMAND_SIT) {
                            this.setCommand(COMMAND_SIT);
                        } else {
                            this.setCommand(COMMAND_WANDER);
                        }
                        player.displayClientMessage(Component.translatable("entity.sinew.all.command_" + this.getCommand(), this.getName()), true);
                    }
                }
                else if (this.isInWaterOrBubble()) {
                    player.startRiding(this);
                }
                return InteractionResult.SUCCESS;
            }
            else {
                if (!this.level().isClientSide && itemStack.is(ItemTags.FISHES)) {
                    this.tryToTame(player, itemStack, 512, itemStack.getCount());
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive() && this.isTame() && this.getCommand() == COMMAND_SIT;
    }

    @Override
    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        if (entity instanceof Player player) {
            return player;
        }
        return null;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < 4;
    }

    @Override
    protected void tickRidden(Player player, Vec3 travelVector) {
        super.tickRidden(player, travelVector);
        this.yRotO = this.getYRot();
        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
    }

    @Override
    protected Vec3 getRiddenInput(Player player, Vec3 travelVector) {
        if (player.xxa != 0) {
            float turn = -Math.signum(player.xxa);
            this.setYRot(this.getYRot() + turn * 4.0F);
        }
        float z = player.zza;
        if (z <= 0.0F) {
            z *= 0.2F;
        }
        return new Vec3(0.0F, 0.0F, z);
    }

    @Override
    protected float getRiddenSpeed(Player player) {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.05F;
    }

    @Override
    public void positionRider(Entity passenger, MoveFunction moveFunction) {
        if (this.isPassengerOfSameVehicle(passenger) && passenger instanceof LivingEntity living && !this.touchingUnloadedChunk()) {
            living.setAirSupply(Math.min(living.getAirSupply() + 2, living.getMaxAirSupply()));
            super.positionRider(passenger, moveFunction);
        } else {
            super.positionRider(passenger, moveFunction);
        }
    }

    @Override
    public Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float partialTicks) {
        int index = Math.max(this.getPassengers().indexOf(entity), 0);
        float offsetZ = 0.0F;
        float offsetX = 0.0F;
        switch (index) {
            case 0 -> {
                offsetX = 1.0F;
                offsetZ = 1.0F;
            }
            case 1 -> {
                offsetX = -1.0F;
                offsetZ = 1.0F;
            }
            case 2 -> {
                offsetX = 1.0F;
                offsetZ = -1.0F;
            }
            case 3 -> {
                offsetX = -1.0F;
                offsetZ = -1.0F;
            }
        }
        return super.getPassengerAttachmentPoint(entity, dimensions, partialTicks).add(new Vec3(offsetX, 0.0F, offsetZ).yRot(-this.getYRot() * Mth.DEG_TO_RAD));
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

    public float getRoll(float partialTicks) {
        return this.bodyChain.getRoll(partialTicks);
    }

    @Override
    public void baseTick() {
        super.baseTick();

        if (!this.level().isClientSide && ((!this.isInWaterOrBubble() && this.onGround() && this.isVehicle()) || (this.isVehicle() && this.isBaby()))) {
            this.ejectPassengers();
        }
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
        if (this.isInWater()) {
            double dx = this.getX() - this.xo;
            double dy = this.getY() - this.yo;
            double dz = this.getZ() - this.zo;
            double horizontal = Math.sqrt(dx * dx + dz * dz);
            double speed = Math.sqrt(horizontal * horizontal + dy * dy);
            float speedFactor = (float) Mth.clamp((speed - 0.01D) / (0.05D - 0.01D), 0.0D, 1.0D);
            if (speedFactor > 0.0F) {
                float angle = (float) (-(Mth.atan2(dy, horizontal) * Mth.RAD_TO_DEG));
                targetPitch = Mth.clamp(angle, -35.0F, 35.0F) * speedFactor;
            }
        }
        this.swimPitch += (targetPitch - this.swimPitch) * 0.08F;
        this.bodyChain.tick(this.yBodyRot, this.swimPitch, targetPitch);

        if (this.wasPreviouslyBaby != this.isBaby()) {
            this.wasPreviouslyBaby = this.isBaby();
            this.refreshDimensions();
            for (SinewPartEntity<?> part : this.allParts) {
                part.refreshDimensions();
            }
        }

        if (this.isControlledByLocalInstance()) {
            if (this.controlDownTicks > 0 && !this.onGround()) {
                this.controlDownTicks--;
            } else if (this.controlUpTicks > 0 && this.getFluidTypeHeight(NeoForgeMod.WATER_TYPE.value()) > 4.0D) {
                this.controlUpTicks--;
            }
        }

        if (this.level().isClientSide) {
            Player player = Sinew.PROXY.getClientSidePlayer();
            if (player != null && player.isPassengerOfSameVehicle(this) && this.getControllingPassenger() == player) {
                if (Sinew.PROXY.isKeyDown(0) && this.controlUpTicks < 2) {
                    PacketDistributor.sendToServer(new MountedEntityKeyPacket(this.getId(), player.getId(), 0));
                    this.controlUpTicks = 10;
                }
                if (Sinew.PROXY.isKeyDown(1) && this.controlDownTicks < 2) {
                    PacketDistributor.sendToServer(new MountedEntityKeyPacket(this.getId(), player.getId(), 1));
                    this.controlDownTicks = 10;
                }
            }
        }

        if (!this.isBaby()) {
            this.addPassengers(this);
            for (SinewPartEntity<?> part : this.allParts) {
                this.addPassengers(part);
            }
        }
    }

    private boolean canSupportPassenger(Entity entity) {
        return entity instanceof Mob && !(entity instanceof AquaticPrehistoricMob) && !(entity instanceof WaterAnimal) && !(entity instanceof Drowned) && !(entity instanceof Player);
    }

    private void addPassengers(Entity entity) {
        List<Entity> list = this.level().getEntities(this, entity.getBoundingBox().inflate(0.2F, -0.01F, 0.2F), EntitySelector.pushableBy(this));
        if (!list.isEmpty()) {
            boolean flag = !this.level().isClientSide && !this.hasControllingPassenger() && this.getCommand() == COMMAND_SIT;
            for (Entity passenger : list) {
                if (!passenger.hasPassenger(this)) {
                    if (flag && this.canAddPassenger(passenger) && !passenger.isPassenger() && this.canSupportPassenger(passenger)) {
                        passenger.startRiding(this);
                    }
                }
            }
        }
    }

    @Override
    public void onKeyPacket(Entity keyPresser, int type) {
        if (keyPresser.isPassengerOfSameVehicle(this) && this.getControllingPassenger() == keyPresser) {
            if (type == 0) {
                this.controlUpTicks = 10;
            }
            if (type == 1) {
                this.controlDownTicks = 10;
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
        return this.isBaby();
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
        return itemStack.is(ItemTags.FISHES);
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
    public int getAmbientSoundInterval() {
        return 180;
    }

    @Override
    public boolean canPlayAmbientSound() {
        return this.getCommand() != COMMAND_SIT;
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
            if (this.getRandom().nextFloat() < 0.2F) {
                super.playSwimSound(0.4F);
            }
        }
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }
}
