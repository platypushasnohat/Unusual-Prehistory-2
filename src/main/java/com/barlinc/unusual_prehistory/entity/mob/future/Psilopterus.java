package com.barlinc.unusual_prehistory.entity.mob.future;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.goals.update_4.PsilopterusAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.update_4.PsilopterusOpenDoorGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.*;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class Psilopterus extends PrehistoricMob implements PackAnimal, ButtonPressingMob, LeverPullingMob, ChestLootingMob {

    private static final EntityDataAccessor<Boolean> PACK_LEADER = SynchedEntityData.defineId(Psilopterus.class, EntityDataSerializers.BOOLEAN);

    private Psilopterus priorPackMember;
    private Psilopterus afterPackMember;

    private int pushButtonCooldown = 0;
    private int pullLeverCooldown = 0;

    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState kickAnimationState = new AnimationState();
    public final AnimationState pokeAnimationState = new AnimationState();
    public final AnimationState dig1AnimationState = new AnimationState();
    public final AnimationState dig2AnimationState = new AnimationState();
    public final AnimationState preen1AnimationState = new AnimationState();
    public final AnimationState preen2AnimationState = new AnimationState();

    private int attackTicks;
    private int kickTicks;
    private int pokeTicks;

    private int digCooldown = 1500 + this.getRandom().nextInt(1600);
    private int preenCooldown = 1200 + this.getRandom().nextInt(1400);

    public Psilopterus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

//    @Override
//    protected void registerGoals() {
//        this.goalSelector.addGoal(0, new FloatGoal(this));
//        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.7D, 10, 4));
//        this.goalSelector.addGoal(2, new JoinPackGoal(this, 60, 6));
//        this.goalSelector.addGoal(3, new PsilopterusAttackGoal(this));
//        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.PSILOPTERUS_FOOD), false));
//        this.goalSelector.addGoal(5, new PsilopterusOpenDoorGoal(this));
//        this.goalSelector.addGoal(5, new PressButtonGoal(this, 1, 6, 4));
//        this.goalSelector.addGoal(5, new PullLeverGoal(this, 1, 6, 4));
//        this.goalSelector.addGoal(6, new LootChestGoal(this, 1, 8, 6));
//        this.goalSelector.addGoal(7, new PrehistoricRandomStrollGoal(this, 1));
//        this.goalSelector.addGoal(8, new FollowParentGoal(this, 1));
//        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 10.0F));
//        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
//        this.goalSelector.addGoal(10, new SleepingGoal(this));
//        this.goalSelector.addGoal(11, new PsilopterusDigGoal(this));
//        this.goalSelector.addGoal(11, new PsilopterusPreenGoal(this));
//        this.targetSelector.addGoal(0, (new HurtByTargetGoal(this, Psilopterus.class)).setAlertOthers());
//        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.SMALL_PSILOPTERUS_PACK_TARGETS)));
//        this.targetSelector.addGoal(2, new PsilopterusNearestAttackableTargetGoal<>(this, Player.class, 200, true, true, this::canAttack, 3));
//        this.targetSelector.addGoal(3, new PsilopterusNearestAttackableTargetGoal<>(this, LivingEntity.class, 400, true, true, entity -> entity.getType().is(UP2EntityTags.MEDIUM_PSILOPTERUS_PACK_TARGETS), 3));
//        this.targetSelector.addGoal(4, new PsilopterusNearestAttackableTargetGoal<>(this, LivingEntity.class, 500, true, true, entity -> entity.getType().is(UP2EntityTags.LARGE_PSILOPTERUS_PACK_TARGETS), 6));
//    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28F);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        NoSpinGroundPathNavigation navigation = new NoSpinGroundPathNavigation(this, level);
        navigation.setCanOpenDoors(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.9F;
    }

    @Override
    public int getMaxFallDistance() {
        return super.getMaxFallDistance() + 10;
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    public float getStepHeight() {
        return this.isRunning() ? 1.0F : 0.6F;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.PSILOPTERUS_FOOD);
    }

    @Override
    public boolean isColliding(@NotNull BlockPos pos, BlockState blockState) {
        return !(blockState.getBlock() instanceof DoorBlock && blockState.getValue(DoorBlock.OPEN)) && super.isColliding(pos, blockState);
    }

    @Override
    public void pressButton() {
        this.setPose(UP2Poses.POKING.get());
    }

    @Override
    public int getPushButtonCooldown() {
        return this.pushButtonCooldown;
    }

    @Override
    public void setPushButtonCooldown(int cooldown) {
        this.pushButtonCooldown = cooldown;
    }

    @Override
    public void pullLever() {
        this.setPose(UP2Poses.POKING.get());
    }

    @Override
    public int getPullLeverCooldown() {
        return this.pullLeverCooldown;
    }

    @Override
    public void setPullLeverCooldown(int cooldown) {
        this.pullLeverCooldown = cooldown;
    }

    @Override
    public boolean shouldLootItem(ItemStack stack) {
        return stack.is(UP2ItemTags.PSILOPTERUS_FOOD);
    }

    @Override
    public void startOpeningChest() {
        if (this.getPose() == Pose.STANDING) {
            this.setPose(UP2Poses.POKING.get());
        }
    }

    @Override
    public boolean openChest() {
        return this.getPose() == UP2Poses.POKING.get() && pokeTicks == 10;
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0, 0, -this.getBbWidth() * 1.1F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = this.getTarget();
        if (target != null && target.isAlive() && !(target instanceof Player player && player.isCreative())) {
            if (this.isPackLeader()) {
                PackAnimal leader = this;
                while (leader.getAfterPackMember() != null) {
                    leader = leader.getAfterPackMember();
                    if (!((Psilopterus) leader).isAlliedTo(target)) {
                        ((Psilopterus) leader).setTarget(target);
                    }
                }
            }
        }

        if (!this.level().isClientSide) {
            if (pushButtonCooldown > 0) pushButtonCooldown--;
            if (pullLeverCooldown > 0) pullLeverCooldown--;
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackTicks > 0) attackTicks--;
        if (kickTicks > 0) kickTicks--;
        if (pokeTicks > 0) pokeTicks--;
        if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) this.setPose(Pose.STANDING);
        if (kickTicks == 0 && this.getPose() == UP2Poses.KICKING.get()) this.setPose(Pose.STANDING);
        if (pokeTicks == 0 && this.getPose() == UP2Poses.POKING.get()) this.setPose(Pose.STANDING);
        if (digCooldown > 0) digCooldown--;
        if (preenCooldown > 0) preenCooldown--;
    }

    @Override
    public void setupAnimationStates() {
        if (attackTicks == 0 && (this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted())) {
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
        }
        if (kickTicks == 0 && this.kickAnimationState.isStarted()) this.kickAnimationState.stop();
        if (pokeTicks == 0 && this.pokeAnimationState.isStarted()) this.pokeAnimationState.stop();

        this.idleAnimationState.animateWhen(this.canPlayIdleAnimation() && !this.isInWater(), this.tickCount);
        this.swimAnimationState.animateWhen(this.canPlayIdleAnimation() && this.isInWater(), this.tickCount);
    }

    private boolean canPlayIdleAnimation() {
        return this.getIdleState() != 1 && this.getIdleState() != 2 && this.getPose() != UP2Poses.KICKING.get();
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                if (this.getRandom().nextBoolean()) this.attack1AnimationState.start(this.tickCount);
                else this.attack2AnimationState.start(this.tickCount);
                this.attackTicks = 10;
            }
            else if (this.getPose() == UP2Poses.KICKING.get()) {
                this.kickAnimationState.start(this.tickCount);
                this.kickTicks = 30;
            }
            else if (this.getPose() == UP2Poses.POKING.get()) {
                this.pokeAnimationState.start(this.tickCount);
                this.pokeTicks = 20;
            }
            else if (this.getPose() == Pose.STANDING) {
                this.attack1AnimationState.stop();
                this.attack2AnimationState.stop();
                this.kickAnimationState.stop();
                this.pokeAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    public void handleEntityEvent(byte id) {
        switch (id) {
            case 67 -> {
                if (this.getRandom().nextBoolean()) this.dig1AnimationState.start(this.tickCount);
                else this.dig2AnimationState.start(this.tickCount);
            }
            case 68 -> {
                this.dig1AnimationState.stop();
                this.dig2AnimationState.stop();
            }
            case 69 -> {
                if (this.getRandom().nextBoolean()) this.preen1AnimationState.start(this.tickCount);
                else this.preen2AnimationState.start(this.tickCount);
            }
            case 70 -> {
                this.preen1AnimationState.stop();
                this.preen2AnimationState.stop();
            }
            default -> super.handleEntityEvent(id);
        }
    }

    protected void digCooldown() {
        this.digCooldown = 1500 + this.getRandom().nextInt(1600);
    }

    protected void preenCooldown() {
        this.preenCooldown = 1200 + this.getRandom().nextInt(1400);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 2;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PACK_LEADER, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("PackLeader", this.isPackLeader());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setPackLeader(compoundTag.getBoolean("PackLeader"));
    }

    public boolean isPackLeader() {
        return this.entityData.get(PACK_LEADER);
    }
    public void setPackLeader(boolean packLeader) {
        this.entityData.set(PACK_LEADER, packLeader);
    }

    @Override
    public PackAnimal getPriorPackMember() {
        return this.priorPackMember;
    }

    @Override
    public PackAnimal getAfterPackMember() {
        return afterPackMember;
    }

    @Override
    public void setPriorPackMember(PackAnimal animal) {
        this.priorPackMember = (Psilopterus) animal;
    }

    @Override
    public void setAfterPackMember(PackAnimal animal) {
        this.afterPackMember = (Psilopterus) animal;
    }

    @Override
    public boolean isValidLeader(PackAnimal packLeader) {
        return packLeader instanceof Psilopterus && ((Psilopterus) packLeader).isAlive() && ((Psilopterus) packLeader).isPackLeader();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        Psilopterus psilopterus = UP2Entities.PSILOPTERUS.get().create(level);
        psilopterus.setPackLeader(this.isPackLeader());
        return psilopterus;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.PSILOPTERUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.PSILOPTERUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.PSILOPTERUS_DEATH.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 130;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        this.setPackLeader(this.getRandom().nextInt(4) == 0);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
    }

    public static boolean canSpawn(EntityType<Psilopterus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.PSILOPTERUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    private static class PsilopterusNearestAttackableTargetGoal<T extends LivingEntity> extends PackAnimalNearestAttackableTargetGoal<T> {

        protected final Psilopterus psilopterus;

        public PsilopterusNearestAttackableTargetGoal(Psilopterus psilopterus, Class<T> targetClass, int interval, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> entityPredicate, int packSizeRequired) {
            super(psilopterus, targetClass, interval, mustSee, mustReach, entityPredicate, packSizeRequired);
            this.psilopterus = psilopterus;
        }

        @Override
        public void start() {
            super.start();
            if (psilopterus.isPackLeader()) {
                this.psilopterus.playSound(UP2SoundEvents.PSILOPTERUS_CALL.get(), 3.0F, 0.9F + psilopterus.getRandom().nextFloat() * 0.25F);
            }
        }

        @Override
        public boolean canUse() {
            if (super.canUse()) {
                return packAnimal.getPackSize() >= packSizeRequired && psilopterus.isPackLeader();
            }
            return false;
        }
    }

    private static class PsilopterusDigGoal extends IdleAnimationGoal {

        private final Psilopterus psilopterus;

        public PsilopterusDigGoal(Psilopterus psilopterus) {
            super(psilopterus, 60, 1, (byte) 67, (byte) 68);
            this.psilopterus = psilopterus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && psilopterus.digCooldown == 0 && psilopterus.level().getBlockState(psilopterus.blockPosition().below()).is(UP2BlockTags.PSILOPTERUS_DIGGING_BLOCKS);
        }

        @Override
        public void stop() {
            super.stop();
            this.psilopterus.digCooldown();
        }

        @Override
        public void tick() {
            super.tick();
            if (timer % 6 == 0 && timer < 50 && timer > 20) {
                this.spawnEffectsAtBlock(psilopterus.blockPosition().below());
                this.psilopterus.playSound(psilopterus.level().getBlockState(psilopterus.blockPosition().below()).getSoundType().getHitSound(), 0.3F, 0.8F + psilopterus.getRandom().nextFloat() * 0.25F);
            }
        }

        public void spawnEffectsAtBlock(BlockPos blockPos) {
            float radius = 0.3F;
            for (int i1 = 0; i1 < 3; i1++) {
                double motionX = psilopterus.getRandom().nextGaussian() * 0.07D;
                double motionY = psilopterus.getRandom().nextGaussian() * 0.07D;
                double motionZ = psilopterus.getRandom().nextGaussian() * 0.07D;
                float angle = (float) ((0.0174532925 * psilopterus.yBodyRot) + i1);
                double extraX = radius * Mth.sin(Mth.PI + angle);
                double extraY = 0.8F;
                double extraZ = radius * Mth.cos(angle);
                BlockState state = psilopterus.level().getBlockState(blockPos);
                ((ServerLevel) psilopterus.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), blockPos.getX() + 0.5 + extraX, blockPos.getY() + 0.5 + extraY, blockPos.getZ() + 0.5 + extraZ, 1, motionX, motionY, motionZ, 1);
            }
        }
    }

    private static class PsilopterusPreenGoal extends IdleAnimationGoal {

        private final Psilopterus psilopterus;

        public PsilopterusPreenGoal(Psilopterus psilopterus) {
            super(psilopterus, 80, 2, (byte) 69, (byte) 70);
            this.psilopterus = psilopterus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && psilopterus.preenCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.psilopterus.preenCooldown();
        }
    }
}
