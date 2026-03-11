package com.barlinc.unusual_prehistory.entity.mob.update_1;

import com.barlinc.unusual_prehistory.entity.ai.goals.IdleAnimationGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.RandomFlightGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class Telecrex extends PrehistoricFlyingMob {

    private static final EntityDataAccessor<Boolean> SPLAT = SynchedEntityData.defineId(Telecrex.class, EntityDataSerializers.BOOLEAN);

    public final SmoothAnimationState peckAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState preen1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState preen2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState splatAnimationState = new SmoothAnimationState(0.75F);

    private boolean preenAlt = false;

    public int preenCooldown = 700 + this.getRandom().nextInt(60 * 60);
    public int peckCooldown = 800 + this.getRandom().nextInt(60 * 60);

    private int splatTicks;

    public Telecrex(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(false);
        this.setPathfindingMalus(BlockPathTypes.LEAVES, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.FLYING_SPEED, 1.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TelecrexScatterGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TELECREX_FOOD), false));
        this.goalSelector.addGoal(3, new PrehistoricRandomStrollGoal(this, 1.0D) {
            @Override
            public boolean canUse() {
                return super.canUse() && !Telecrex.this.isFlying();
            }
        });
        this.goalSelector.addGoal(3, new RandomFlightGoal(this, 0.8F, 1.4F, 16, 4, 1600, 200));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new TelecrexPreenGoal(this));
        this.goalSelector.addGoal(6, new TelecrexPeckGoal(this));
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        if (this.isEffectiveAi() && this.getPose() == UP2Poses.START_FLYING.get()) {
            double horizontalSpeed = this.isRunning() ? 0.4D : 0.7D;
            travelVec = travelVec.multiply(horizontalSpeed, 1.0D, horizontalSpeed);
        }
        super.travel(travelVec);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (hurt && source.getEntity() != null && this.isAlive()) {
            double range = 8;
            this.setFlying(true);
            this.setRunning(true);
            this.setRunningTicks(this.getFastFlyingTicks());
            List<? extends Telecrex> entities = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(range, range / 2, range));
            for (Telecrex telecrex : entities) {
                telecrex.setFlying(true);
                telecrex.setRunning(true);
                telecrex.setRunningTicks(telecrex.getFastFlyingTicks());
            }
        }
        return hurt;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getRunningTicks() > 0) this.setRunningTicks(this.getRunningTicks() - 1);
        if (this.isRunning() && this.getRunningTicks() == 0) this.setRunning(false);

        if (this.isFlying() && this.horizontalCollision && this.getRandom().nextBoolean() && !this.level().isClientSide) {
            this.setSplat(true);
            this.setFlying(false);
        }

        if (this.hasSplat()) {
            this.splatTicks++;
            this.setDeltaMovement(this.getDeltaMovement().x * 0.1F, this.getDeltaMovement().y * 0.4F, this.getDeltaMovement().z * 0.1F);
        }
        if (splatTicks > 60 || this.onGround() || this.isInWaterOrBubble()) this.setSplat(false);
    }

    @Override
    public void setupAnimationCooldowns() {
        if (!this.isFlying()) {
            if (preenCooldown > 0) preenCooldown--;
            if (peckCooldown > 0) peckCooldown--;
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 2 || this.hasSplat();
    }

    @Override
    public boolean refuseToLook() {
        return super.refuseToMove() || this.hasSplat();
    }

    public int getFastFlyingTicks() {
        return 100 + this.getRandom().nextInt(50);
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlying() && this.getIdleState() != 1 && this.getIdleState() != 2, this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying() && this.getPose() == Pose.FALL_FLYING && !this.isRunning(), this.tickCount);
        this.flyFastAnimationState.animateWhen(this.isFlying() && this.getPose() == Pose.FALL_FLYING && this.isRunning(), this.tickCount);
        this.splatAnimationState.animateWhen(this.hasSplat(), this.tickCount);
        this.preen1AnimationState.animateWhen(this.getIdleState() == 1 && !preenAlt, this.tickCount);
        this.preen2AnimationState.animateWhen(this.getIdleState() == 1 && preenAlt, this.tickCount);
        this.peckAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
    }

    protected void preenCooldown() {
        this.preenCooldown = 700 + this.getRandom().nextInt(60 * 60);
    }

    protected void peckCooldown() {
        this.peckCooldown = 800 + this.getRandom().nextInt(60 * 60);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPLAT, false);
    }

    public boolean hasSplat() {
        return this.entityData.get(SPLAT);
    }

    public void setSplat(boolean splat) {
        this.entityData.set(SPLAT, splat);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.TELECREX_FOOD);
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.PARROT_EAT;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.TELECREX_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.TELECREX_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.TELECREX_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.06F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.TELECREX.get().create(serverLevel);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3, 3, 3);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return Math.sqrt(distance) < 1024.0D;
    }

    public static boolean canSpawn(EntityType<Telecrex> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.TELECREX_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    private static class TelecrexScatterGoal extends Goal {

        private final Telecrex telecrex;

        public TelecrexScatterGoal(Telecrex telecrex) {
            this.telecrex = telecrex;
        }

        @Override
        public boolean canUse() {
            if (telecrex.isFlying()) {
                return false;
            }
            long worldTime = telecrex.level().getGameTime() % 10;
            if (telecrex.getRandom().nextInt(10) != 0 && worldTime != 0) {
                return false;
            }
            AABB aabb = telecrex.getBoundingBox().inflate(8);
            List<Entity> list = telecrex.level().getEntitiesOfClass(Entity.class, aabb, (entity -> entity.getType().is(UP2EntityTags.TELECREX_AVOIDS) || entity instanceof Player && !((Player) entity).isCreative()));
            return !list.isEmpty();
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            this.telecrex.setFlying(true);
            this.telecrex.setRunning(true);
            this.telecrex.setRunningTicks(telecrex.getFastFlyingTicks());
        }
    }

    private static class TelecrexPreenGoal extends IdleAnimationGoal {

        private final Telecrex telecrex;

        public TelecrexPreenGoal(Telecrex telecrex) {
            super(telecrex, 60, 1, (byte) 67, (byte) 68);
            this.telecrex = telecrex;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !telecrex.hasSplat() && telecrex.preenCooldown == 0 && !telecrex.isFlying();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canUse() && !telecrex.isFlying();
        }

        @Override
        public void start() {
            super.start();
            this.telecrex.preenAlt = telecrex.getRandom().nextBoolean();
        }

        @Override
        public void stop() {
            super.stop();
            this.telecrex.preenCooldown();
        }
    }

    private static class TelecrexPeckGoal extends IdleAnimationGoal {

        private final Telecrex telecrex;

        public TelecrexPeckGoal(Telecrex telecrex) {
            super(telecrex, 60, 2, (byte) 69, (byte) 70);
            this.telecrex = telecrex;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !telecrex.hasSplat() && telecrex.peckCooldown == 0 && !telecrex.isFlying() && telecrex.level().getBlockState(telecrex.blockPosition().below()).is(UP2BlockTags.TELECREX_PECKING_BLOCKS);
        }

        @Override
        public boolean canContinueToUse() {
            return super.canUse() && !telecrex.isFlying();
        }

        @Override
        public void stop() {
            super.stop();
            this.telecrex.peckCooldown();
        }
    }
}