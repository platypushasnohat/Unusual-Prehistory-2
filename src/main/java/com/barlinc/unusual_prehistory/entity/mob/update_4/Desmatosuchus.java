package com.barlinc.unusual_prehistory.entity.mob.update_4;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Desmatosuchus extends PrehistoricMob {

    private static final EntityDataAccessor<Integer> DIRTY = SynchedEntityData.defineId(Desmatosuchus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ROLL_COOLDOWN = SynchedEntityData.defineId(Desmatosuchus.class, EntityDataSerializers.INT);

    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(1.3F, 0.8F);
    private static final EntityDimensions EEPY_DIMENSIONS = EntityDimensions.scalable(1.3F, 0.4F);

    public static final ResourceLocation[] SHEARING_LOOT = { UnusualPrehistory2.modPrefix("entities/desmatosuchus_shearing_moss"),
                                                             UnusualPrehistory2.modPrefix("entities/desmatosuchus_shearing_mud"),
                                                             UnusualPrehistory2.modPrefix("entities/desmatosuchus_shearing_snow") };

    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState sniff1AnimationState = new AnimationState();
    public final AnimationState sniff2AnimationState = new AnimationState();
    public final AnimationState shakeAnimationState = new AnimationState();
    public final AnimationState grazeAnimationState = new AnimationState();
    public final AnimationState chewAnimationState = new AnimationState();
    public final AnimationState rollAnimationState = new AnimationState();

    public int sniffCooldown = 700 + this.getRandom().nextInt(60 * 60);
    public int shakeCooldown = 600 + this.getRandom().nextInt(60 * 60);
    public int grazeCooldown = 800 + this.getRandom().nextInt(70 * 70);
    public int chewCooldown = 700 + this.getRandom().nextInt(70 * 70);

    public Desmatosuchus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 2.0D, 10, 4));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DESMATOSUCHUS_FOOD), false));
        this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new RandomSitGoal(this));
        this.goalSelector.addGoal(8, new SleepingGoal(this));
        this.goalSelector.addGoal(9, new DesmatosuchusRollGoal(this));
        this.goalSelector.addGoal(9, new DesmatosuchusShakeGoal(this));
        this.goalSelector.addGoal(9, new DesmatosuchusSniffGoal(this));
        this.goalSelector.addGoal(9, new DesmatosuchusGrazeGoal(this));
        this.goalSelector.addGoal(9, new DesmatosuchusChewGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.18F)
                .add(Attributes.ARMOR, 12.0F);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.9F;
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && this.horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.5D * this.getBbHeight();
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
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 3 || this.getIdleState() == 4;
    }

    @Override
    public long getEepyPoseTransitionTime() {
        return 30L;
    }

    @Override
    public boolean isEepyTime() {
        return this.level().isNight() && this.level().getBlockState(this.blockPosition().below()).is(UP2BlockTags.DESMATOSUCHUS_PREFERRED_WALKING_BLOCKS);
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0, 0, -this.getBbWidth() * 1.25F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.DESMATOSUCHUS_PREFERRED_WALKING_BLOCKS) ? 10.0F : super.getWalkTargetValue(pos, level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DESMATOSUCHUS_FOOD);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        if (pose == UP2Poses.SLEEPING.get()) return EEPY_DIMENSIONS.scale(this.getScale());
        return pose == UP2Poses.SITTING.get() ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose);
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return super.canCollideWith(entity) && !(entity instanceof Desmatosuchus);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);
        if (this.isDirty() && itemstack.is(Tags.Items.SHEARS)) {
            this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
            this.gameEvent(GameEvent.SHEAR, player);
            int dirtLevel = this.getDirtLevel();
            this.setDirtLevel(0);
            if (!this.level().isClientSide) {
                System.out.println(dirtLevel + "Dirt level. expected table" + SHEARING_LOOT[dirtLevel - 1]);
                LootTable loottable = this.level().getServer().getLootData().getLootTable(SHEARING_LOOT[dirtLevel - 1]);
                List<ItemStack> items = loottable.getRandomItems((new LootParams.Builder((ServerLevel) this.level())).withParameter(LootContextParams.THIS_ENTITY, this).create(LootContextParamSets.PIGLIN_BARTER));
                items.forEach(this::spawnAtLocation);
            }
            return InteractionResult.SUCCESS;
        }
        return type;
    }

    @Override
    public void setupAnimationCooldowns() {
        if (!this.isMobEepy()) {
            if (this.getRollCooldown() > 0) this.setRollCooldown(this.getRollCooldown() - 1);
            if (shakeCooldown > 0) shakeCooldown--;
            if (sniffCooldown > 0) sniffCooldown--;
            if (chewCooldown > 0) chewCooldown--;
            if (grazeCooldown > 0) grazeCooldown--;
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWater() && !this.isInSitPoseTransition() && !this.isInEepyPoseTransition(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWater(), this.tickCount);

        if (this.isMobVisuallySitting()) {
            this.sitEndAnimationState.stop();
            this.grazeAnimationState.stop();
            this.idleAnimationState.stop();
            this.shakeAnimationState.stop();
            this.sniff1AnimationState.stop();
            this.sniff2AnimationState.stop();
            this.rollAnimationState.stop();
            this.eepyStartAnimationState.stop();
            this.eepyAnimationState.stop();
            this.eepyEndAnimationState.stop();

            if (this.isVisuallySitting()) {
                this.sitStartAnimationState.startIfStopped(this.tickCount);
                this.sitAnimationState.stop();
            } else {
                this.sitStartAnimationState.stop();
                this.sitAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.sitStartAnimationState.stop();
            this.sitAnimationState.stop();
            this.sitEndAnimationState.animateWhen(this.isInSitPoseTransition() && this.getSitPoseTime() >= 0L, this.tickCount);
        }

        if (this.isMobVisuallyEepy()) {
            this.eepyEndAnimationState.stop();
            this.grazeAnimationState.stop();
            this.idleAnimationState.stop();
            this.shakeAnimationState.stop();
            this.sniff1AnimationState.stop();
            this.sniff2AnimationState.stop();
            this.rollAnimationState.stop();
            this.sitStartAnimationState.stop();
            this.sitAnimationState.stop();
            this.sitEndAnimationState.stop();

            if (this.isVisuallyEepy()) {
                this.eepyStartAnimationState.startIfStopped(this.tickCount);
                this.eepyAnimationState.stop();
            } else {
                this.eepyStartAnimationState.stop();
                this.eepyAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.eepyStartAnimationState.stop();
            this.eepyAnimationState.stop();
            this.eepyEndAnimationState.animateWhen(this.isInEepyPoseTransition() && this.getEepyPoseTime() >= 0L, this.tickCount);
        }
    }

    public void handleEntityEvent(byte id) {
        switch (id) {
            case 67 -> this.rollAnimationState.start(this.tickCount);
            case 68 -> this.rollAnimationState.stop();

            case 69 -> this.shakeAnimationState.start(this.tickCount);
            case 70 -> this.shakeAnimationState.stop();

            case 71 -> {
                if (this.getRandom().nextBoolean()) this.sniff1AnimationState.start(this.tickCount);
                else this.sniff2AnimationState.start(this.tickCount);
            }
            case 72 -> {
                this.sniff1AnimationState.stop();
                this.sniff2AnimationState.stop();
            }

            case 73 -> this.grazeAnimationState.start(this.tickCount);
            case 74 -> this.grazeAnimationState.stop();

            case 75 -> this.chewAnimationState.start(this.tickCount);
            case 76 -> this.chewAnimationState.stop();

            default -> super.handleEntityEvent(id);
        }
    }

    protected void rollCooldown() {
        this.setRollCooldown(1000 + this.getRandom().nextInt(70 * 70));
    }

    protected void shakeCooldown() {
        this.shakeCooldown = 600 + this.getRandom().nextInt(60 * 60);
    }

    protected void sniffCooldown() {
        this.sniffCooldown = 700 + this.getRandom().nextInt(60 * 60);
    }

    protected void grazeCooldown() {
        this.grazeCooldown = 800 + this.getRandom().nextInt(70 * 70);
    }

    protected void chewCooldown() {
        this.chewCooldown = 700 + this.getRandom().nextInt(70 * 70);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DIRTY, 0);
        this.entityData.define(ROLL_COOLDOWN, 1000 + this.getRandom().nextInt(70 * 70));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Dirty", this.getDirtLevel());
        compoundTag.putInt("RollCooldown", this.getRollCooldown());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setDirtLevel(compoundTag.getInt("Dirty"));
        this.setRollCooldown(compoundTag.getInt("RollCooldown"));
    }

    public boolean isDirty() {
        return this.entityData.get(DIRTY) > 0;
    }
    public void setDirtLevel(int dirt) {
        this.entityData.set(DIRTY, dirt);
    }

    public int getDirtLevel() {
        return this.entityData.get(DIRTY);
    }

    public int getRollCooldown() {
        return this.entityData.get(ROLL_COOLDOWN);
    }
    public void setRollCooldown(int cooldown) {
        this.entityData.set(ROLL_COOLDOWN, cooldown);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.DESMATOSUCHUS.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.DESMATOSUCHUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.DESMATOSUCHUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.DESMATOSUCHUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.DESMATOSUCHUS_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    public static boolean canSpawn(EntityType<Desmatosuchus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.DESMATOSUCHUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    // Goals
    private static class DesmatosuchusRollGoal extends AnimationGoal {

        private final Desmatosuchus desmatosuchus;

        public DesmatosuchusRollGoal(Desmatosuchus desmatosuchus) {
            super(desmatosuchus, 80, 1, (byte) 67, (byte) 68);
            this.desmatosuchus = desmatosuchus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && desmatosuchus.getRollCooldown() == 0 && !desmatosuchus.isMobSitting() && this.isRollingBlock();
        }

        @Override
        public void stop() {
            super.stop();
            this.desmatosuchus.rollCooldown();
        }

        @Override
        public void tick() {
            super.tick();
            if(timer == 30 && !desmatosuchus.isDirty()) {
                if (desmatosuchus.level().getBlockState(desmatosuchus.blockPosition().below()).is(UP2BlockTags.DESMATOSUCHUS_MOSSY_BLOCKS)) {
                    desmatosuchus.setDirtLevel(1);
                } else if (desmatosuchus.level().getBlockState(desmatosuchus.blockPosition().below()).is(UP2BlockTags.DESMATOSUCHUS_MUDDY_BLOCKS)) {
                    desmatosuchus.setDirtLevel(2);
                } else if (desmatosuchus.level().getBlockState(desmatosuchus.blockPosition().below()).is(UP2BlockTags.DESMATOSUCHUS_SNOWY_BLOCKS)) {
                    desmatosuchus.setDirtLevel(3);
                }
            }
        }

        protected boolean isRollingBlock() {
            return desmatosuchus.level().getBlockState(desmatosuchus.blockPosition().below()).is(UP2BlockTags.DESMATOSUCHUS_ROLLING_BLOCKS) || desmatosuchus.level().getBlockState(desmatosuchus.blockPosition()).is(UP2BlockTags.DESMATOSUCHUS_ROLLING_BLOCKS);
        }
    }

    private static class DesmatosuchusShakeGoal extends AnimationGoal {

        private final Desmatosuchus desmatosuchus;

        public DesmatosuchusShakeGoal(Desmatosuchus desmatosuchus) {
            super(desmatosuchus, 40, 2, (byte) 69, (byte) 70, false);
            this.desmatosuchus = desmatosuchus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && desmatosuchus.shakeCooldown == 0 && !desmatosuchus.isMobSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.desmatosuchus.shakeCooldown();
        }
    }

    private static class DesmatosuchusSniffGoal extends AnimationGoal {

        private final Desmatosuchus desmatosuchus;

        public DesmatosuchusSniffGoal(Desmatosuchus desmatosuchus) {
            super(desmatosuchus, 40, 3, (byte) 71, (byte) 72);
            this.desmatosuchus = desmatosuchus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && desmatosuchus.sniffCooldown == 0 && !desmatosuchus.isMobSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.desmatosuchus.sniffCooldown();
        }
    }

    private static class DesmatosuchusGrazeGoal extends AnimationGoal {

        private final Desmatosuchus desmatosuchus;

        public DesmatosuchusGrazeGoal(Desmatosuchus desmatosuchus) {
            super(desmatosuchus, 40, 4, (byte) 73, (byte) 74);
            this.desmatosuchus = desmatosuchus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && desmatosuchus.grazeCooldown == 0 && !desmatosuchus.isMobSitting() && desmatosuchus.level().getBlockState(desmatosuchus.blockPosition().below()).is(UP2BlockTags.DESMATOSUCHUS_GRAZING_BLOCKS);
        }

        @Override
        public void stop() {
            super.stop();
            this.desmatosuchus.grazeCooldown();
        }
    }

    private static class DesmatosuchusChewGoal extends AnimationGoal {

        private final Desmatosuchus desmatosuchus;

        public DesmatosuchusChewGoal(Desmatosuchus desmatosuchus) {
            super(desmatosuchus, 40, 5, (byte) 75, (byte) 76, false);
            this.desmatosuchus = desmatosuchus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && desmatosuchus.chewCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.desmatosuchus.chewCooldown();
        }
    }
}
