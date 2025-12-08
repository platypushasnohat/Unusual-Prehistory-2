package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.control.LivingOozeMoveControl;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.items.EmbryoItem;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class LivingOoze extends PathfinderMob implements Bucketable {

    private static final EntityDataAccessor<Integer> SPIT_TIME = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_CONTAINED_ENTITY = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> CONTAINED_ENTITY_TYPE = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> COOLDOWN = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> WANTS_TO_JUMP = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_JUMPED = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SAD_TIME = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PICKUP_COOLDOWN = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState processingAnimationState = new AnimationState();
    public final AnimationState spittingAnimationState = new AnimationState();
    public final AnimationState cooldownAnimationState = new AnimationState();

    private int spittingTicks;

    private float squishProgress;
    private float prevSquishProgress;
    private float jumpProgress;
    private float prevJumpProgress;
    private float jiggleTime;
    private float prevJiggleTime;

    public LivingOoze(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new LivingOozeMoveControl(this);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LivingOozeRandomDirectionGoal(this));
        this.goalSelector.addGoal(1, new LivingOozeKeepOnJumpingGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.getPose() == UP2Poses.SPITTING.get()) {
            this.getNavigation().stop();
            travelVec = Vec3.ZERO;
        }
        super.travel(travelVec);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public float getWaterSlowDown() {
        return 0.9F;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource damageSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float amount) {
        this.setSadTime(10);
        super.actuallyHurt(damageSource, amount);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getSpitTime() > 0) this.setSpitTime(this.getSpitTime() - 1);
        if (this.getCooldown() > 0) this.setCooldown(this.getCooldown() - 1);

        if (this.getSpitTime() == 35) this.setPose(UP2Poses.SPITTING.get());
        if (spittingTicks > 0) spittingTicks--;
        if (spittingTicks == 0 && this.getPose() == UP2Poses.SPITTING.get()) this.setPose(Pose.STANDING);

        if (this.getSadTime() > 0) this.setSadTime(this.getSadTime() - 1);

        if (this.getPickupCooldown() > 0) this.setPickupCooldown(this.getPickupCooldown() - 1);

        if (this.getSpitTime() == 2 && this.hasEntity()) {
            if (!this.level().isClientSide) {
                this.spawnMob();
            }
            this.addDeltaMovement(new Vec3(0, 0.25D, 0));
            this.addDeltaMovement(this.getLookAngle().scale(2.0D).multiply(-0.4D, 0, -0.4D));
            this.spawnSpitParticles();
            this.playSound(UP2SoundEvents.LIVING_OOZE_SPIT.get(), 1.5F, this.getSoundPitch());
        }

        if (this.level().isClientSide) this.setupAnimationStates();

        this.tickSquish();

        if (this.tickCount % 20 == 0 && this.getMainHandItem().isEmpty() && this.getPickupCooldown() == 0 && this.getCooldown() == 0) {
            this.tickItemAbsorption();
        }

        if (this.tickCount % 100 == 0 && this.getHealth() < this.getMaxHealth()) this.heal(2);
    }

    public void setupAnimationStates() {
        this.processingAnimationState.animateWhen(this.hasEntity() && this.getPose() != UP2Poses.SPITTING.get(), this.tickCount);
        this.cooldownAnimationState.animateWhen(this.getCooldown() > 0 && this.getPose() != UP2Poses.SPITTING.get(), this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.SPITTING.get()) {
                this.spittingAnimationState.start(this.tickCount);
                this.spittingTicks = 35;
            }
            else {
                this.spittingAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    protected float getEquipmentDropChance(@NotNull EquipmentSlot slot) {
        return 0;
    }

    @Override
    protected void dropEquipment() {
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!itemstack.isEmpty()) {
            this.spawnAtLocation(itemstack);
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    public int processingTime(RandomSource random) {
        return random.nextInt(3600, 12000);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isAlive() && this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && !itemstack.isEmpty() && this.getCooldown() == 0 && (!itemstack.is(Items.BUCKET) || player.isCrouching())) {
            if (itemstack.getItem() instanceof EmbryoItem embryoItem) {
                final ResourceLocation mobType = ForgeRegistries.ENTITY_TYPES.getKey(embryoItem.toSpawn.get());
                if (mobType != null) {
                    this.setContainedEntityType(mobType.toString());
                    this.setHasEntity(true);
                    this.setSpitTime(this.processingTime(this.level().getRandom()));
                    this.setOwnerUUID(player.getUUID());
                }
            }
            this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        else if (itemstack.getItem() == Items.BUCKET && this.isAlive() && !player.isCrouching()) {
            this.playSound(this.getPickupSound(), 1.0F, 1.0F);
            ItemStack bucketStack = this.getBucketItemStack();
            this.saveToBucketTag(bucketStack);
            ItemStack filledBucketStack = ItemUtils.createFilledResult(itemstack, player, bucketStack, false);
            player.setItemInHand(hand, filledBucketStack);
            if (!this.level().isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bucketStack);
            }
            this.discard();
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        else if (!this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && itemstack.isEmpty()) {
            this.dropEquipment();
            this.setContainedEntityType("minecraft:pig");
            this.setHasEntity(false);
            this.setSpitTime(-1);
            this.setPickupCooldown(40);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.level().isClientSide) {
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    public int cooldown(RandomSource random) {
        return random.nextInt(1200, 2400);
    }

    public void spawnMob() {
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(this.getContainedEntityType()));
        if (type != null) {
            Entity entity = type.create(this.level());
            UUID owner = this.getOwnerUUID();
            if (entity instanceof final Mob mob) {
                if (mob instanceof PrehistoricMob prehistoricMob) {
                    prehistoricMob.setAge(-24000);
                    prehistoricMob.setFromEgg(true);
                    prehistoricMob.setShotFromOoze(true);
                }
                Vec3 shootingVec = this.getLookAngle().scale(1.2D).multiply(0.25D, 1.0D, 0.25D);
                this.spitMob(mob, shootingVec.x(), 0.2F, shootingVec.z(), 0.6F, 15);
                this.setCooldown(this.cooldown(this.level().getRandom()));
                if (this.level().addFreshEntity(mob)) {
                    ForgeEventFactory.onFinalizeSpawn(mob, (ServerLevel) this.level(), this.level().getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.NATURAL, null, null);
                    this.setHasEntity(false);
                    this.setContainedEntityType("minecraft:pig");
                    this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    this.setOwnerUUID(null);
                    if (owner != null) {
                        Player player = this.level().getPlayerByUUID(owner);
                        if (player instanceof ServerPlayer serverPlayer) {
                            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, entity);
                        }
                    }
                }
            }
        }
    }

    private Vec3 getMouthVec() {
        final Vec3 vec3 = new Vec3(0, this.getBbHeight() * 0.7F, this.getBbWidth() * 0.5F).xRot(this.getXRot() * Mth.DEG_TO_RAD).yRot(-this.getYRot() * Mth.DEG_TO_RAD);
        return this.position().add(vec3);
    }

    public void spitMob(Mob mob, double x, double y, double z, float velocity, float inaccuracy) {
        Vec3 movement = (new Vec3(x, y, z)).normalize().add(mob.getRandom().triangle(0.0D, 0.0172275D * (double) inaccuracy), mob.getRandom().triangle(0.0D, 0.0172275D * (double) inaccuracy), mob.getRandom().triangle(0.0D, 0.0172275D * (double) inaccuracy)).scale(velocity);
        mob.setPos(this.getMouthVec());
        mob.setDeltaMovement(movement);
        double horizontalDistance = movement.horizontalDistance();
        mob.setYRot((float) (Mth.atan2(movement.x, movement.z) * (double) (180F / (float) Math.PI)));
        mob.setXRot((float) (Mth.atan2(movement.y, horizontalDistance) * (double) (180F / (float) Math.PI)));
        mob.yRotO = this.getYRot();
        mob.xRotO = this.getXRot();
        mob.hurtMarked = true;
    }

    private void tickSquish() {
        prevJumpProgress = jumpProgress;
        prevSquishProgress = squishProgress;
        prevJiggleTime = jiggleTime;
        boolean jumping = !this.onGround() && tickCount > 4;
        boolean squish = !jumping && (this.wantsToJump() || this.hasJumped() && this.onGround());
        if (jumping && jumpProgress < 3.0F) {
            jumpProgress++;
        }
        if (!jumping && jumpProgress > 0.0F) {
            jumpProgress--;
        }
        if (squish && squishProgress < 5.0F) {
            squishProgress++;
            if (squishProgress >= 5.0F) {
                this.setHasJumped(false);
            }
        }
        if (!squish && squishProgress > 0.0F) {
            squishProgress--;
        }
        if (this.hasJumped() && this.onGround()) {
            jiggleTime = 5;
        } else if (jiggleTime > 0) {
            if (jiggleTime == 4) {
                this.playSound(this.getSquishSound(), this.getSoundVolume(), this.getSoundPitch());
            }
            if (jiggleTime > 4) {
                this.spawnJumpParticles();
            }
            jiggleTime--;
        }
    }

    private void tickItemAbsorption() {
        final boolean canAbsorb = !this.level().isClientSide && this.isAlive() && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
        if (canAbsorb) {
            if (!this.getMainHandItem().isEmpty()) {
                return;
            }
            AABB boundingBox = this.getBoundingBox();
            List<ItemEntity> items = this.level().getEntities(EntityType.ITEM, boundingBox, item -> item.isAlive() && !item.isPickable() && !item.getItem().isEmpty());
            items.stream().filter(item -> !item.getItem().is(UP2ItemTags.LIVING_OOZE_CANNOT_ABSORB)).findFirst().ifPresent(item -> {
                ItemStack stack = item.getItem();
                ItemStack absorbedStack = stack.split(1);
                if (absorbedStack.getItem() instanceof EmbryoItem embryoItem) {
                    final ResourceLocation mobType = ForgeRegistries.ENTITY_TYPES.getKey(embryoItem.toSpawn.get());
                    if (mobType != null) {
                        this.setContainedEntityType(mobType.toString());
                        this.setHasEntity(true);
                        this.setSpitTime(this.processingTime(this.level().getRandom()));
                    }
                }
                this.setItemInHand(InteractionHand.MAIN_HAND, absorbedStack);
                this.setPickupCooldown(20);
                this.playSound(SoundEvents.ITEM_PICKUP, 0.1F, this.getSoundPitch());
                if (stack.isEmpty()) item.discard();
            });
        }
    }

    public float getJiggleTime(float partialTick) {
        return (prevJiggleTime + (jiggleTime - prevJiggleTime) * partialTick) * 0.2F;
    }

    public float getJumpProgress(float partialTick) {
        return (prevJumpProgress + (jumpProgress - prevJumpProgress) * partialTick) * 0.33F;
    }

    public float getSquishProgress(float partialTick) {
        return (prevSquishProgress + (squishProgress - prevSquishProgress) * partialTick) * 0.2F;
    }

    private void spawnJumpParticles() {
        for (int j = 0; j < 6; j++) {
            float f = this.random.nextFloat() * ((float) Math.PI * 2F);
            float f1 = this.random.nextFloat() * 0.5F + 0.5F;
            float f2 = Mth.sin(f) * 2 * 0.5F * f1;
            float f3 = Mth.cos(f) * 2 * 0.5F * f1;
            this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, UP2Items.ORGANIC_OOZE.get().getDefaultInstance()), this.getX() + (double) f2, this.getY(), this.getZ() + (double) f3, 0.0D, 0.0D, 0.0D);
        }
    }

    private void spawnSpitParticles() {
        for (int j = 0; j < 10; j++) {
            Vec3 shootingVec = this.getLookAngle().scale(0.6D);
            this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, UP2Items.ORGANIC_OOZE.get().getDefaultInstance()), this.getMouthVec().x() + this.getRandom().nextFloat() * 0.7F, this.getMouthVec().y() + this.getRandom().nextFloat() * 0.7F, this.getMouthVec().z() + this.getRandom().nextFloat() * 0.7F, shootingVec.x() * this.getRandom().nextFloat() * 0.6F, shootingVec.y() * this.getRandom().nextFloat() * 0.6F, shootingVec.z() * this.getRandom().nextFloat() * 0.6F);
        }
    }

    @Override
    public int getMaxHeadXRot() {
        return 0;
    }

    @Override
    protected void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, this.getJumpPower(), vec3.z);
        this.hasImpulse = true;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.LIVING_OOZE_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.LIVING_OOZE_DEATH.get();
    }

    protected SoundEvent getSquishSound() {
        return UP2SoundEvents.LIVING_OOZE_SQUISH.get();
    }

    public SoundEvent getJumpSound() {
        return UP2SoundEvents.LIVING_OOZE_JUMP.get();
    }

    public float getSoundPitch() {
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CONTAINED_ENTITY_TYPE, "minecraft:pig");
        this.entityData.define(HAS_CONTAINED_ENTITY, false);
        this.entityData.define(SPIT_TIME, -1);
        this.entityData.define(WANTS_TO_JUMP, false);
        this.entityData.define(HAS_JUMPED, false);
        this.entityData.define(COOLDOWN, 0);
        this.entityData.define(SAD_TIME, 0);
        this.entityData.define(PICKUP_COOLDOWN, 0);
        this.entityData.define(OWNER_UUID, Optional.empty());
        this.entityData.define(FROM_BUCKET, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpitTime", this.getSpitTime());
        compoundTag.putString("ContainedEntityType", this.getContainedEntityType());
        compoundTag.putBoolean("HasContainedEntity", this.hasEntity());
        compoundTag.putInt("Cooldown", this.getCooldown());
        if (this.getOwnerUUID() != null) {
            compoundTag.putUUID("Owner", this.getOwnerUUID());
        }
        compoundTag.putBoolean("FromBucket", this.fromBucket());
        compoundTag.putInt("Cooldown", this.getCooldown());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSpitTime(compoundTag.getInt("SpitTime"));
        this.setContainedEntityType(compoundTag.getString("ContainedEntityType"));
        this.setHasEntity(compoundTag.getBoolean("HasContainedEntity"));
        this.setCooldown(compoundTag.getInt("Cooldown"));
        this.setFromBucket(compoundTag.getBoolean("FromBucket"));
        UUID uuid;
        if (compoundTag.hasUUID("Owner")) {
            uuid = compoundTag.getUUID("Owner");
        } else {
            String s = compoundTag.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }
        if (uuid != null) {
            try {
                this.setOwnerUUID(uuid);
            } catch (Throwable ignored) {
            }
        }
    }

    public String getContainedEntityType() {
        return this.entityData.get(CONTAINED_ENTITY_TYPE);
    }

    public void setContainedEntityType(String containedEntityType) {
        this.entityData.set(CONTAINED_ENTITY_TYPE, containedEntityType);
    }

    public boolean hasEntity() {
        return this.entityData.get(HAS_CONTAINED_ENTITY);
    }

    public void setHasEntity(boolean hasEntity) {
        this.entityData.set(HAS_CONTAINED_ENTITY, hasEntity);
    }

    public int getSpitTime() {
        return this.entityData.get(SPIT_TIME);
    }

    public void setSpitTime(int time) {
        this.entityData.set(SPIT_TIME, time);
    }

    public void setWantsToJump(boolean wantsToJump) {
        this.entityData.set(WANTS_TO_JUMP, wantsToJump);
    }

    public boolean wantsToJump() {
        return this.entityData.get(WANTS_TO_JUMP);
    }

    public void setHasJumped(boolean hasJumped) {
        this.entityData.set(HAS_JUMPED, hasJumped);
    }

    public boolean hasJumped() {
        return this.entityData.get(HAS_JUMPED);
    }

    public int getCooldown() {
        return this.entityData.get(COOLDOWN);
    }

    public void setCooldown(int cooldown) {
        this.entityData.set(COOLDOWN, cooldown);
    }

    public int getSadTime() {
        return this.entityData.get(SAD_TIME);
    }

    public void setSadTime(int time) {
        this.entityData.set(SAD_TIME, time);
    }

    public int getPickupCooldown() {
        return this.entityData.get(PICKUP_COOLDOWN);
    }

    public void setPickupCooldown(int cooldown) {
        this.entityData.set(PICKUP_COOLDOWN, cooldown);
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override
    public void saveToBucketTag(@NotNull ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
        CompoundTag compoundTag = bucket.getOrCreateTag();
        this.addAdditionalSaveData(compoundTag);
    }

    @Override
    public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);
        this.readAdditionalSaveData(compoundTag);
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.LIVING_OOZE_BUCKET.get());
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.SLIME_BLOCK_PLACE;
    }

    private static class LivingOozeKeepOnJumpingGoal extends Goal {

        private final LivingOoze ooze;

        public LivingOozeKeepOnJumpingGoal(LivingOoze ooze) {
            this.ooze = ooze;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.ooze.isPassenger() && this.ooze.getPose() != UP2Poses.SPITTING.get();
        }

        @Override
        public void tick() {
            if (this.ooze.getMoveControl() instanceof LivingOozeMoveControl moveControl) {
                moveControl.setWantedMovement(1.0D);
            }
        }
    }

    private static class LivingOozeRandomDirectionGoal extends Goal {

        private final LivingOoze ooze;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public LivingOozeRandomDirectionGoal(LivingOoze ooze) {
            this.ooze = ooze;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return this.ooze.getPose() != UP2Poses.SPITTING.get() && this.ooze.getTarget() == null && (this.ooze.onGround() || this.ooze.isInWater() || this.ooze.isInLava() || this.ooze.hasEffect(MobEffects.LEVITATION)) && this.ooze.getMoveControl() instanceof LivingOozeMoveControl;
        }

        @Override
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.ooze.getRandom().nextInt(60));
                this.chosenDegrees = (float) this.ooze.getRandom().nextInt(360);
            }
            if (this.ooze.getMoveControl() instanceof LivingOozeMoveControl moveControl) {
                moveControl.setDirection(this.chosenDegrees);
            }
        }
    }
}
