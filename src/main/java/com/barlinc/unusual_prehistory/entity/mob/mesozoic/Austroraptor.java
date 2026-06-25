package com.barlinc.unusual_prehistory.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2LootTables;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Austroraptor extends PrehistoricMob {

    private static final EntityDataAccessor<Integer> SHEARED_TICKS = SynchedEntityData.defineId(Austroraptor.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> HATED_UUID = SynchedEntityData.defineId(Austroraptor.class, EntityDataSerializers.OPTIONAL_UUID);

    public int attackCooldown = 0;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState fallAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState fishingAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState preen1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState preen2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();

    private boolean preenAlt = false;

    public Austroraptor(EntityType<? extends Austroraptor> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new AustroraptorAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new SurfaceSwimGoal(this, 0.65D));
        this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1.0D, false));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new SleepingGoal(this));
        // todo: probably make fishing more immersive (either spawn and eat a fish mob or fish item)
        this.goalSelector.addGoal(8, new IdleAnimationGoal(this, 80, 1, true, 0.001F, this::canFish));
        this.goalSelector.addGoal(8, new IdleAnimationGoal(this, 80, 2, true, 0.001F, this::canPreen));
        this.goalSelector.addGoal(8, new IdleAnimationGoal(this, 20, 3, false, 0.001F));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 10, true, false, entity -> entity == this.getHatedEntity()));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.STEP_HEIGHT, 1.2D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.75D;
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

    public boolean canFish(Entity entity) {
        return entity.isInWaterOrBubble();
    }

    public boolean canPreen(Entity entity) {
        return !entity.isInWaterOrBubble() && ((Austroraptor) entity).getShearedTicks() <= 0;
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 1400 + this.getRandom().nextInt(1400);
        }
        else if (idleState == 2) {
            return 800 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 3) {
            return 600 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        Vec3 deltaMovement = this.getDeltaMovement();
        if (!this.onGround() && !this.isInWaterOrBubble() && deltaMovement.y < 0.0D) {
            this.setDeltaMovement(deltaMovement.multiply(1.0D, 0.8D, 1.0D));
            if (this.getMovementEmission().emitsEvents()) {
                this.gameEvent(GameEvent.FLAP);
            }
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (this.getShearedTicks() > 0) {
            this.setShearedTicks(this.getShearedTicks() - 1);
        }
        if (this.getShearedTicks() <= 0 && this.getHatedEntity() != null) {
            this.setHatedUUID(null);
        }
        if (attackCooldown > 0) {
            this.attackCooldown--;
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy() && this.onGround() && !this.isInWaterOrBubble() && this.getIdleState() != 2, tickCount);
        this.fallAnimationState.animateWhen(!this.onGround() && !this.isInWaterOrBubble() && !this.onClimbable() && !this.isPassenger() && !this.isEepy(), tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), tickCount);
        this.fishingAnimationState.animateWhen(this.getIdleState() == 1, tickCount);
        this.preen1AnimationState.animateWhen(this.getIdleState() == 2 && !preenAlt, tickCount);
        this.preen2AnimationState.animateWhen(this.getIdleState() == 2 && preenAlt, tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 3, tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float) Mth.length(this.getX() - xo, includeHeight ? this.getY() - yo : 0.0, this.getZ() - zo);
        if (this.isBaby()) {
            this.updateWalkAnimation(f * 0.5F);
        } else {
            this.updateWalkAnimation(f);
        }
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f = Math.min(partialTick * 25.0F, 1.0F);
        this.walkAnimation.update(f, 0.4F);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (IDLE_STATE.equals(accessor)) {
            if (this.getIdleState() == 2) {
                this.preenAlt = this.getRandom().nextBoolean();
            }
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.AUSTRORAPTOR.get().create(level);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, this.getBbHeight() * 0.3F, this.getBbWidth() * 1.15F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.refuseToMove()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVector = travelVector.multiply(0.0D, 1.0D, 0.0D);
        }
        super.travel(travelVector);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (this.getShearedTicks() <= 0 && itemStack.is(Tags.Items.TOOLS_SHEAR)) {
            if (this.isEepy()) {
                this.setEepy(false);
                this.setEepyCooldown(100);
            }
            this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
            this.playSound(UP2SoundEvents.AUSTRORAPTOR_SCREAM.get(), 2.0F, this.getVoicePitch());
            this.gameEvent(GameEvent.SHEAR, player);
            if (!this.level().isClientSide) {
                LootTable loottable = Objects.requireNonNull(this.level().getServer()).reloadableRegistries().getLootTable(UP2LootTables.AUSTRORAPTOR_SHEARING);
                List<ItemStack> items = loottable.getRandomItems((new LootParams.Builder((ServerLevel) this.level())).withParameter(LootContextParams.THIS_ENTITY, this).create(LootContextParamSets.PIGLIN_BARTER));
                items.forEach(this::spawnFeathers);
            }
            this.setShearedTicks(24000 + this.getRandom().nextInt(24000));
            this.setHatedUUID(player.getUUID());
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @SuppressWarnings("DataFlowIssue")
    public void spawnFeathers(ItemStack stack) {
        ItemEntity item = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), stack);
        item.setDefaultPickUpDelay();
        if (this.captureDrops() != null) {
            this.captureDrops().add(item);
        } else {
            this.level().addFreshEntity(item);
        }
        item.setDeltaMovement(item.getDeltaMovement().add((item.getRandom().nextFloat() - item.getRandom().nextFloat()) * 0.1F, item.getRandom().nextFloat() * 0.05F, (item.getRandom().nextFloat() - item.getRandom().nextFloat()) * 0.1F));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHEARED_TICKS, 0);
        builder.define(HATED_UUID, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("ShearedTicks", this.getShearedTicks());
        if (this.getHatedUUID() != null) {
            compoundTag.putUUID("Hated", this.getHatedUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setShearedTicks(compoundTag.getInt("ShearedTicks"));
        if (compoundTag.hasUUID("Hated")) {
            this.setHatedUUID(compoundTag.getUUID("Hated"));
        }
    }

    public int getShearedTicks() {
        return entityData.get(SHEARED_TICKS);
    }
    public void setShearedTicks(int i) {
        this.entityData.set(SHEARED_TICKS, i);
    }

    @Nullable
    public UUID getHatedUUID() {
        return entityData.get(HATED_UUID).orElse(null);
    }
    public void setHatedUUID(@Nullable UUID uuid) {
        this.entityData.set(HATED_UUID, Optional.ofNullable(uuid));
    }
    @Nullable
    public LivingEntity getHatedEntity() {
        UUID uuid = this.getHatedUUID();
        return uuid == null ? null : this.level().getPlayerByUUID(uuid);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.getShearedTicks() > 0 ? UP2SoundEvents.AUSTRORAPTOR_HISS.get() : UP2SoundEvents.AUSTRORAPTOR_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.AUSTRORAPTOR_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.AUSTRORAPTOR_DEATH.get();
    }

    // Goals
    private static class AustroraptorAttackGoal extends AttackGoal {

        protected final Austroraptor austroraptor;

        public AustroraptorAttackGoal(Austroraptor austroraptor) {
            super(austroraptor);
            this.austroraptor = austroraptor;
        }

        @Override
        public void tick() {
            LivingEntity target = austroraptor.getTarget();
            if (target != null) {
                double distance = austroraptor.distanceToSqr(target);
                this.austroraptor.lookAt(target, 30.0F, 30.0F);
                this.austroraptor.getLookControl().setLookAt(target, 30.0F, 30.0F);
                if (this.austroraptor.getAttackState() == 1) {
                    this.austroraptor.getNavigation().stop();
                    this.tickAttack(target);
                } else {
                    this.austroraptor.getNavigation().moveTo(target, 1.8D);
                    if (distance <= this.getAttackReachSqr(target) && austroraptor.attackCooldown == 0) {
                        this.austroraptor.setAttackState(1);
                    }
                }
            }
        }

        private void tickAttack(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.austroraptor.playSound(UP2SoundEvents.AUSTRORAPTOR_ATTACK.get(), 1.0F, austroraptor.getVoicePitch());
                this.austroraptor.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 6) {
                if (this.isInAttackRange(target, 2.0D)) {
                    this.austroraptor.doHurtTarget(target);
                }
            }
            if (timer > 10) {
                this.timer = 0;
                this.austroraptor.attackCooldown = 6;
                this.austroraptor.setPose(Pose.STANDING);
                this.austroraptor.setAttackState(0);
            }
        }
    }
}
