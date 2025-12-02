package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.navigation.LivingOozeMoveControl;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.items.EmbryoItem;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

@SuppressWarnings("deprecation")
public class LivingOoze extends PathfinderMob {

    private static final EntityDataAccessor<Integer> SPIT_TIME = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_CONTAINED_ENTITY = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> CONTAINED_ENTITY_TYPE = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Boolean> WANTS_TO_JUMP = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_JUMPED = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.BOOLEAN);

    private float squishProgress;
    private float prevSquishProgress;
    private float jumpProgress;
    private float prevJumpProgress;
    private float jiggleTime;
    private float prevJiggleTime;

    public final AnimationState processingAnimationState = new AnimationState();
    public final AnimationState spittingAnimationState = new AnimationState();

    private int spittingTicks;

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
//        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
//        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F);
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
    public void tick() {
        super.tick();
        if (this.getSpitTime() > 0) this.setSpitTime(this.getSpitTime() - 1);

        if (this.getSpitTime() == 10) this.setPose(UP2Poses.SPITTING.get());
        if (spittingTicks > 0) spittingTicks--;
        if (spittingTicks == 0 && this.getPose() == UP2Poses.SPITTING.get()) this.setPose(Pose.STANDING);

        if (this.getSpitTime() == 0 && !this.level().isClientSide && this.hasEntity()) {
            this.spawnMob();
        }

        if (this.level().isClientSide) this.setupAnimationStates();

        this.tickSquish();
    }

    public void setupAnimationStates() {
        this.processingAnimationState.animateWhen(this.hasEntity() && this.getPose() != UP2Poses.SPITTING.get(), this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.SPITTING.get()) {
                this.spittingAnimationState.start(this.tickCount);
                this.spittingTicks = 10;
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
        super.dropEquipment();
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
        if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && !itemstack.isEmpty()) {
            if (itemstack.getItem() instanceof EmbryoItem embryoItem) {
                final ResourceLocation mobType = ForgeRegistries.ENTITY_TYPES.getKey(embryoItem.toSpawn.get());
                if (mobType != null) {
                    this.setContainedEntityType(mobType.toString());
                    this.setHasEntity(true);
                    this.setSpitTime(100/*this.processingTime(this.level().getRandom())*/);
                }
            }
            this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (!this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && itemstack.isEmpty()) {
            this.dropEquipment();
            this.setContainedEntityType("minecraft:pig");
            this.setHasEntity(false);
            this.setSpitTime(-1);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.level().isClientSide) {
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    public void spawnMob() {
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(this.getContainedEntityType()));
        if (type != null) {
            Entity entity = type.create(this.level());
            if (entity instanceof final Mob mob) {
                if (mob instanceof PrehistoricMob prehistoricMob) {
                    prehistoricMob.setAge(-24000);
                    prehistoricMob.setFromEgg(true);
                    prehistoricMob.setShotFromOoze(true);
                }
                Vec3 shootingVec = this.getLookAngle().scale(1.2D).multiply(0.25D, 1.0D, 0.25D);
                this.spitMob(mob, shootingVec.x(), 0.2F, shootingVec.z(), 0.6F, 15);
                if (this.level().addFreshEntity(mob)) {
                    ForgeEventFactory.onFinalizeSpawn(mob, (ServerLevel) this.level(), this.level().getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.NATURAL, null, null);
                    this.setHasEntity(false);
                    this.setContainedEntityType("minecraft:pig");
                    this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
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
            if(jiggleTime == 4){
                this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            }
            if (jiggleTime > 4) {
                this.spawnParticles();
            }
            jiggleTime--;
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

    private void spawnParticles() {
        for(int j = 0; j < 8; j++) {
            float f = this.random.nextFloat() * ((float) Math.PI * 2F);
            float f1 = this.random.nextFloat() * 0.5F + 0.5F;
            float f2 = Mth.sin(f) * 2 * 0.5F * f1;
            float f3 = Mth.cos(f) * 2 * 0.5F * f1;
            this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, UP2Items.ORGANIC_OOZE.get().getDefaultInstance()), this.getX() + (double) f2, this.getY(), this.getZ() + (double) f3, 0.0D, 0.0D, 0.0D);
        }
    }

    public int getJumpDelay() {
        return this.random.nextInt(24) + 16;
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
        return SoundEvents.SLIME_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH;
    }

    protected SoundEvent getSquishSound() {
        return SoundEvents.SLIME_SQUISH;
    }

    public SoundEvent getJumpSound() {
        return SoundEvents.SLIME_JUMP;
    }

    public float getSoundPitch() {
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CONTAINED_ENTITY_TYPE, "minecraft:pig");
        this.entityData.define(HAS_CONTAINED_ENTITY, false);
        this.entityData.define(SPIT_TIME, -1);
        this.entityData.define(WANTS_TO_JUMP, false);
        this.entityData.define(HAS_JUMPED, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpitTime", this.getSpitTime());
        compoundTag.putString("ContainedEntityType", this.getContainedEntityType());
        compoundTag.putBoolean("HasContainedEntity", this.hasEntity());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSpitTime(compoundTag.getInt("SpitTime"));
        this.setContainedEntityType(compoundTag.getString("ContainedEntityType"));
        this.setHasEntity(compoundTag.getBoolean("HasContainedEntity"));
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

    private static class LivingOozeKeepOnJumpingGoal extends Goal {

        private final LivingOoze ooze;

        public LivingOozeKeepOnJumpingGoal(LivingOoze ooze) {
            this.ooze = ooze;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.ooze.isPassenger() && (this.ooze.getSpitTime() > 16 || this.ooze.getSpitTime() == -1);
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
            return (this.ooze.getSpitTime() > 16 || this.ooze.getSpitTime() == -1) && this.ooze.getTarget() == null && (this.ooze.onGround() || this.ooze.isInWater() || this.ooze.isInLava() || this.ooze.hasEffect(MobEffects.LEVITATION)) && this.ooze.getMoveControl() instanceof LivingOozeMoveControl;
        }

        @Override
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.ooze.getRandom().nextInt(60));
                this.chosenDegrees = (float)this.ooze.getRandom().nextInt(360);
            }
            if (this.ooze.getMoveControl() instanceof LivingOozeMoveControl moveControl) {
                moveControl.setDirection(this.chosenDegrees);
            }
        }
    }
}
