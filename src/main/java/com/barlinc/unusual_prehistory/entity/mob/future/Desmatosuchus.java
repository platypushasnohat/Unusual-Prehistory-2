package com.barlinc.unusual_prehistory.entity.mob.future;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.ai.goals.IdleAnimationGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

    private static final EntityDimensions EEPY_DIMENSIONS = EntityDimensions.scalable(1.3F, 0.4F);

    public static final ResourceLocation[] SHEARING_LOOT = { UnusualPrehistory2.modPrefix("entities/desmatosuchus_shearing_moss"),
                                                             UnusualPrehistory2.modPrefix("entities/desmatosuchus_shearing_mud"),
                                                             UnusualPrehistory2.modPrefix("entities/desmatosuchus_shearing_snow") };

    public final SmoothAnimationState sniff1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sniff2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState grazeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState rollAnimationState = new SmoothAnimationState();

    private boolean sniffAlt = false;
    private int sniffCooldown = 700 + this.getRandom().nextInt(700);
    private int shakeCooldown = 600 + this.getRandom().nextInt(600);
    private int grazeCooldown = 800 + this.getRandom().nextInt(800);

    public Desmatosuchus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

//    @Override
//    protected void registerGoals() {
//        this.goalSelector.addGoal(0, new FloatGoal(this));
//        this.goalSelector.addGoal(1, new LargePanicGoal(this, 2.0D, 10, 4));
//        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DESMATOSUCHUS_FOOD), false));
//        this.goalSelector.addGoal(3, new PrehistoricRandomStrollGoal(this, 1));
//        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1));
//        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
//        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
//        this.goalSelector.addGoal(6, new SleepingGoal(this));
//        this.goalSelector.addGoal(7, new DesmatosuchusRollGoal(this));
//        this.goalSelector.addGoal(7, new DesmatosuchusShakeGoal(this));
//        this.goalSelector.addGoal(7, new DesmatosuchusSniffGoal(this));
//        this.goalSelector.addGoal(7, new DesmatosuchusGrazeGoal(this));
//    }

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
        return this.isEepy() ? EEPY_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose);
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
                LootTable loottable = this.level().getServer().getLootData().getLootTable(SHEARING_LOOT[dirtLevel - 1]);
                List<ItemStack> items = loottable.getRandomItems((new LootParams.Builder((ServerLevel) this.level())).withParameter(LootContextParams.THIS_ENTITY, this).create(LootContextParamSets.PIGLIN_BARTER));
                items.forEach(this::spawnAtLocation);
            }
            return InteractionResult.SUCCESS;
        }
        return type;
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (!this.isEepy()) {
            if (this.getRollCooldown() > 0) this.setRollCooldown(this.getRollCooldown() - 1);
            if (shakeCooldown > 0) shakeCooldown--;
            if (sniffCooldown > 0) sniffCooldown--;
            if (grazeCooldown > 0) grazeCooldown--;
        }
    }

    @Override
    public void setupAnimationStates() {
        super.setupAnimationStates();
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isEepy() && this.getIdleState() != 1, this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.rollAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.sniff1AnimationState.animateWhen(this.getIdleState() == 3 && !sniffAlt, this.tickCount);
        this.sniff2AnimationState.animateWhen(this.getIdleState() == 3 && sniffAlt, this.tickCount);
        this.grazeAnimationState.animateWhen(this.getIdleState() == 4, this.tickCount);
    }

    protected void shakeCooldown() {
        this.shakeCooldown = 600 + this.getRandom().nextInt(600);
    }

    protected void sniffCooldown() {
        this.sniffCooldown = 700 + this.getRandom().nextInt(700);
    }

    protected void grazeCooldown() {
        this.grazeCooldown = 800 + this.getRandom().nextInt(800);
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
    private static class DesmatosuchusRollGoal extends IdleAnimationGoal {

        private final Desmatosuchus desmatosuchus;

        public DesmatosuchusRollGoal(Desmatosuchus desmatosuchus) {
            super(desmatosuchus, 80, 1);
            this.desmatosuchus = desmatosuchus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && desmatosuchus.getRollCooldown() == 0 && this.isRollingBlock();
        }

        @Override
        public void stop() {
            super.stop();
            this.desmatosuchus.setRollCooldown(1200 + desmatosuchus.getRandom().nextInt(1200));
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

    private static class DesmatosuchusShakeGoal extends IdleAnimationGoal {

        private final Desmatosuchus desmatosuchus;

        public DesmatosuchusShakeGoal(Desmatosuchus desmatosuchus) {
            super(desmatosuchus, 40, 2, false);
            this.desmatosuchus = desmatosuchus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && desmatosuchus.shakeCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.desmatosuchus.shakeCooldown();
        }
    }

    private static class DesmatosuchusSniffGoal extends IdleAnimationGoal {

        private final Desmatosuchus desmatosuchus;

        public DesmatosuchusSniffGoal(Desmatosuchus desmatosuchus) {
            super(desmatosuchus, 40, 3);
            this.desmatosuchus = desmatosuchus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && desmatosuchus.sniffCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.desmatosuchus.sniffCooldown();
        }

        @Override
        public void start() {
            super.start();
            this.desmatosuchus.sniffAlt = desmatosuchus.getRandom().nextBoolean();
        }
    }

    private static class DesmatosuchusGrazeGoal extends IdleAnimationGoal {

        private final Desmatosuchus desmatosuchus;

        public DesmatosuchusGrazeGoal(Desmatosuchus desmatosuchus) {
            super(desmatosuchus, 40, 4);
            this.desmatosuchus = desmatosuchus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && desmatosuchus.grazeCooldown == 0 && desmatosuchus.level().getBlockState(desmatosuchus.blockPosition().below()).is(UP2BlockTags.DESMATOSUCHUS_GRAZING_BLOCKS);
        }

        @Override
        public void stop() {
            super.stop();
            this.desmatosuchus.grazeCooldown();
        }
    }
}
