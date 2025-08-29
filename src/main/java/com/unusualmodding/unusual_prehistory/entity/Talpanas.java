package com.unusualmodding.unusual_prehistory.entity;

import com.google.common.collect.Lists;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class Talpanas extends Animal {

    private static final EntityDataAccessor<Optional<BlockPos>> FEEDING_POS = SynchedEntityData.defineId(Talpanas.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Integer> FEEDING_TIME = SynchedEntityData.defineId(Talpanas.class, EntityDataSerializers.INT);

    public static final ResourceLocation TALPANAS_REWARD = new ResourceLocation(UnusualPrehistory2.MOD_ID, "gameplay/talpanas_reward");

    private Ingredient temptationItems;
    public float prevFeedProgress;
    public float feedProgress;
    private int rideCooldown = 0;
    public int soundTimer = 0;

    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    private float nextFlap = 1.0F;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flapAnimationState = new AnimationState();

    public Talpanas(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_CAUTIOUS, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 2));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, getTemptationItems(), false));
        this.goalSelector.addGoal(3, new TalpanasDigGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(5, new TalpanasFleeLightGoal(this, 1.5D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.98F;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (this.onGround() ? -1.0F : 4.0F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0) {
            this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
        }

        this.flap += this.flapping * 2.0F;
    }

    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    protected void onFlap() {
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    private Ingredient getTemptationItems() {
        if (temptationItems == null)
            temptationItems = Ingredient.merge(Lists.newArrayList(
                    Ingredient.of(ItemTags.LEAVES)
            ));
        return temptationItems;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.TALPANAS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UP2SoundEvents.TALPANAS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.TALPANAS_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.1F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob p_146744_) {
        return UP2Entities.TALPANAS.get().create(serverLevel);
    }

    public int getFeedingTime() {
        return this.entityData.get(FEEDING_TIME);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FEEDING_TIME, 0);
        this.entityData.define(FEEDING_POS, Optional.empty());
    }

    public void setFeedingTime(int feedingTime) {
        this.entityData.set(FEEDING_TIME, feedingTime);
    }

    public void tick() {
        super.tick();
        if (soundTimer > 0) {
            soundTimer--;
        }
        this.prevFeedProgress = feedProgress;
        if (this.getFeedingTime() > 0 && feedProgress < 5F) {
            feedProgress++;
        }
        if (this.getFeedingTime() <= 0 && feedProgress > 0F) {
            feedProgress--;
        }
        BlockPos feedingPos = this.entityData.get(FEEDING_POS).orElse(null);
        if (feedingPos == null) {
            float f2 = (float) -((float) this.getDeltaMovement().y * 2.2F * (double) (180F / (float) Math.PI));
            this.setXRot(f2);
        } else if (this.getFeedingTime() > 0) {
            Vec3 face = Vec3.atCenterOf(feedingPos).subtract(this.position());
            double d0 = face.horizontalDistance();
            this.setXRot((float) (-Mth.atan2(face.y, d0) * (double) (180F / (float) Math.PI)));
            this.setYRot(((float) Mth.atan2(face.z, face.x)) * (180F / (float) Math.PI) - 90F);
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.getYRot();
            BlockState state = level().getBlockState(feedingPos);
            if (random.nextInt(2) == 0 && !state.isAir()) {
                Vec3 mouth = new Vec3(0, this.getBbHeight() * 0.5F, 0.4F * -0.5).xRot(this.getXRot() * ((float) Math.PI / 180F)).yRot(-this.getYRot() * ((float) Math.PI / 180F));
                for (int i = 0; i < 4 + random.nextInt(2); i++) {
                    double motX = this.random.nextGaussian() * 0.02D;
                    double motY = 0.1F + random.nextFloat() * 0.2F;
                    double motZ = this.random.nextGaussian() * 0.02D;
                    level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), this.getX() + mouth.x, this.getY() + mouth.y, this.getZ() + mouth.z, motX, motY, motZ);
                }
            }
        }
        if (rideCooldown > 0) {
            rideCooldown--;
        }

        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive() && this.onGround(), this.tickCount);
        this.flapAnimationState.animateWhen(this.isAlive() && !this.onGround() && !this.isInWaterOrBubble(), this.tickCount);
    }

    @Override
    public void die(DamageSource pDamageSource) {
        this.stopRiding();
        super.die(pDamageSource);
    }

    public void rideTick() {
        Entity mount = this.getVehicle();

        if (this.isPassenger() && !mount.isAlive()) {
            this.stopRiding();
        } else if (mount instanceof Player player && this.isPassenger()) {
            this.setDeltaMovement(0, 0, 0);
            float radius = 0F;
            float angle = (0.01745329251F * (player.yBodyRot - 180F));
            double extraX = radius * Mth.sin((float) (Math.PI + angle));
            double extraZ = radius * Mth.cos(angle);
            playPanicSound();
            this.setPos(player.getX() + extraX, Math.max(player.getY() + player.getBbHeight() + 0.1, player.getY()), player.getZ() + extraZ);
            if (!player.isAlive() || rideCooldown == 0 || player.isShiftKeyDown() || !mount.isAlive()) {
                this.stopRiding();
            }
        } else {
            super.rideTick();
        }

    }

    private void playPanicSound() {
        if (this.soundTimer <= 0) {
            this.playSound(UP2SoundEvents.TALPANAS_PANIC.get(), this.getSoundVolume(), (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
            soundTimer = 80;
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL) || source.is(DamageTypes.IN_WALL);
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!isFood(itemstack) && itemstack.isEmpty() && hand == InteractionHand.MAIN_HAND) {
            if (player.getPassengers().isEmpty()) {
                this.startRiding(player);
                rideCooldown = 20;
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return false;
    }

    private boolean canSeeBlock(BlockPos destinationBlock) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        Vec3 blockVec = Vec3.atCenterOf(destinationBlock);
        BlockHitResult result = this.level().clip(new ClipContext(Vector3d, blockVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        return result.getBlockPos().equals(destinationBlock);
    }

    private static List<ItemStack> getDigLoot(Talpanas crab) {
        LootTable loottable = crab.level().getServer().getLootData().getLootTable(TALPANAS_REWARD);
        return loottable.getRandomItems((new LootParams.Builder((ServerLevel) crab.level())).withParameter(LootContextParams.THIS_ENTITY, crab).create(LootContextParamSets.PIGLIN_BARTER));
    }

    @Override
    protected boolean isImmobile() {
        return this.isPassenger();
    }

    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        Vec3 vec3 = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.getYRot() + (pLivingEntity.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F));
        Vec3 vec31 = this.getDismountLocationInDirection(vec3, pLivingEntity);
        if (vec31 != null) {
            return vec31;
        } else {
            Vec3 vec32 = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.getYRot() + (pLivingEntity.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F));
            Vec3 vec33 = this.getDismountLocationInDirection(vec32, pLivingEntity);
            return vec33 != null ? vec33 : this.position();
        }
    }

    @Nullable
    private Vec3 getDismountLocationInDirection(Vec3 pDirection, LivingEntity pPassenger) {
        double d0 = this.getX() + pDirection.x;
        double d1 = this.getBoundingBox().minY;
        double d2 = this.getZ() + pDirection.z;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(Pose pose : pPassenger.getDismountPoses()) {
            mutableBlockPos.set(d0, d1, d2);
            double d3 = this.getBoundingBox().maxY + 0.75D;

            while(true) {
                double d4 = this.level().getBlockFloorHeight(mutableBlockPos);
                if ((double) mutableBlockPos.getY() + d4 > d3) {
                    break;
                }

                if (DismountHelper.isBlockFloorValid(d4)) {
                    AABB aabb = pPassenger.getLocalBoundsForPose(pose);
                    Vec3 vec3 = new Vec3(d0, (double) mutableBlockPos.getY() + d4, d2);
                    if (DismountHelper.canDismountTo(this.level(), pPassenger, aabb.move(vec3))) {
                        pPassenger.setPose(pose);
                        return vec3;
                    }
                }

                mutableBlockPos.move(Direction.UP);
                if (!((double) mutableBlockPos.getY() < d3)) {
                    break;
                }
            }
        }
        return null;
    }

    // goals
    private static class TalpanasFleeLightGoal extends Goal {

        protected final Talpanas talpanas;
        private double shelterX;
        private double shelterY;
        private double shelterZ;
        private final double movementSpeed;
        private final Level world;
        private int executeChance = 50;
        private final int lightLevel = 10;

        public TalpanasFleeLightGoal(Talpanas talpanas, double movementSpeed) {
            this.talpanas = talpanas;
            this.movementSpeed = movementSpeed;
            this.world = talpanas.level();
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (this.talpanas.getTarget() != null || this.talpanas.getRandom().nextInt(executeChance) != 0) {
                return false;
            } else if (this.world.getMaxLocalRawBrightness(this.talpanas.blockPosition()) < lightLevel) {
                return false;
            } else {
                return this.isPossibleShelter();
            }
        }

        protected boolean isPossibleShelter() {
            Vec3 lvt_1_1_ = this.findPossibleShelter();
            if (lvt_1_1_ == null) {
                return false;
            } else {
                this.shelterX = lvt_1_1_.x;
                this.shelterY = lvt_1_1_.y;
                this.shelterZ = lvt_1_1_.z;
                return true;
            }
        }

        public boolean canContinueToUse() {
            return !this.talpanas.getNavigation().isDone();
        }

        public void start() {
            this.talpanas.getNavigation().moveTo(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
        }

        @Nullable
        protected Vec3 findPossibleShelter() {
            RandomSource randomSource = this.talpanas.getRandom();
            BlockPos blockPos = this.talpanas.blockPosition();

            for (int i = 0; i < 10; ++i) {
                BlockPos blockPos1 = blockPos.offset(randomSource.nextInt(20) - 10, randomSource.nextInt(6) - 3, randomSource.nextInt(20) - 10);
                if (this.talpanas.level().getMaxLocalRawBrightness(blockPos1) < lightLevel) {
                    return Vec3.atBottomCenterOf(blockPos1);
                }
            }
            return null;
        }
    }

    private class TalpanasDigGoal extends Goal {

        private final int searchLength;
        protected BlockPos destinationBlock;
        private Talpanas talpanas;
        private int runDelay = 70;
        private int maxFeedTime = 200;

        private TalpanasDigGoal(Talpanas talpanas) {
            this.setFlags(EnumSet.of(Flag.MOVE));
            this.talpanas = talpanas;
            this.searchLength = 16;
        }

        public boolean canContinueToUse() {
            return destinationBlock != null && isDigBlock(this.talpanas.level(), destinationBlock.mutable()) && isCloseToMoss(16);
        }

        public boolean isCloseToMoss(double dist) {
            return destinationBlock == null || this.talpanas.distanceToSqr(Vec3.atCenterOf(destinationBlock)) < dist * dist;
        }

        @Override
        public boolean canUse() {
            if (this.runDelay > 0) {
                --this.runDelay;
                return false;
            } else {
                this.runDelay = 200 + this.talpanas.random.nextInt(150);
                return this.searchForDestination();
            }
        }

        public void start() {
            maxFeedTime = 60 + random.nextInt(60);
        }

        public void tick() {
            Vec3 vec = Vec3.atCenterOf(destinationBlock);
            if (vec != null) {
                this.talpanas.getNavigation().moveTo(vec.x, vec.y, vec.z, 1F);
                if (this.talpanas.distanceToSqr(vec) < 1.15F) {
                    this.talpanas.entityData.set(FEEDING_POS, Optional.of(destinationBlock));
                    Vec3 face = vec.subtract(this.talpanas.position());
                    this.talpanas.setDeltaMovement(this.talpanas.getDeltaMovement().add(face.normalize().scale(0.1F)));
                    this.talpanas.setFeedingTime(this.talpanas.getFeedingTime() + 1);
                    this.talpanas.playSound(SoundEvents.ROOTED_DIRT_BREAK, this.talpanas.getSoundVolume(), this.talpanas.getVoicePitch());
                    if (this.talpanas.getFeedingTime() > maxFeedTime) {
                        destinationBlock = null;
                        if (random.nextInt(1) == 0) {
                            List<ItemStack> lootList = getDigLoot(this.talpanas);
                            if (!lootList.isEmpty()) {
                                for (ItemStack stack : lootList) {
                                    ItemEntity item = this.talpanas.spawnAtLocation(stack.copy());
                                    item.hasImpulse = true;
                                    item.setDeltaMovement(item.getDeltaMovement().multiply(0.2, 0.2, 0.2));
                                }
                            }
                        }
                    }
                } else {
                    this.talpanas.entityData.set(FEEDING_POS, Optional.empty());
                }
            }
        }

        public void stop() {
            this.talpanas.entityData.set(FEEDING_POS, Optional.empty());
            destinationBlock = null;
            this.talpanas.setFeedingTime(0);
        }

        protected boolean searchForDestination() {
            int length = this.searchLength;
            BlockPos position = this.talpanas.blockPosition();
            BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();

            for (int i = -8; i <= 2; i++) {
                for (int j = 0; j < length; ++j) {
                    for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                        for (int m = k < j && k > -j ? j : 0; m <= j; m = m > 0 ? -m : 1 - m) {
                            blockPos.setWithOffset(position, k, i - 1, m);
                            if (this.isDigBlock(this.talpanas.level(), blockPos) && this.talpanas.canSeeBlock(blockPos)) {
                                this.destinationBlock = blockPos;
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        private boolean isDigBlock(Level world, BlockPos.MutableBlockPos pos) {
            return world.getBlockState(pos).is(UP2BlockTags.TALPANAS_DIGABLES);
        }
    }
}
