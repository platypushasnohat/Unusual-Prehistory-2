package com.barlinc.unusual_prehistory.entity.mob.update_6;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.update_6.ConcavenatorAttackGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class Concavenator extends PrehistoricMob {

    private static final EntityDataAccessor<Integer> ARMOR_TYPE = SynchedEntityData.defineId(Concavenator.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SAND_SWIMMING = SynchedEntityData.defineId(Concavenator.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SAND_SWIM_COOLDOWN = SynchedEntityData.defineId(Concavenator.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SAND_SWIM_TIME = SynchedEntityData.defineId(Concavenator.class, EntityDataSerializers.INT);

    private static final EntityDimensions SAND_SWIMMING_DIMENSIONS = EntityDimensions.scalable(1.5F, 1.1F).withEyeHeight(0.7F);

    public boolean isLandNavigator;

    public int attackCooldown = 0;
    public int diveAttackCooldown = 0;

    public final SmoothAnimationState sandSwimIdleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sandSwimStartAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sandSwimEndAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState diveAttackAnimationState = new SmoothAnimationState();

    private int sandSwimStartTicks;
    private int sandSwimEndTicks;

    public Concavenator(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.6D, 10, 4));
        this.goalSelector.addGoal(2, new ConcavenatorAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.CONCAVENATOR_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.2D));
        this.goalSelector.addGoal(5, new SandSwimmingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new ConcavenatorRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                .add(Attributes.MOVEMENT_SPEED, 0.24F)
                .add(Attributes.ARMOR, 1.0D)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        if (this.shouldBePushedDown()) {
            this.addDeltaMovement(new Vec3(0, -0.7F, 0));
        }
        super.travel(travelVec);
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.getNavigation().stop();
            this.moveControl = new PrehistoricMoveControl(this);
            this.lookControl = new PrehistoricLookControl(this);
            this.isLandNavigator = true;
        } else {
            this.getNavigation().stop();
            this.moveControl = new SandSwimmingMoveControl(this, 10);
            this.lookControl = new SandSwimmingLookControl(this, 10);
            this.isLandNavigator = false;
        }
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        if (this.isSandSwimming()) return SAND_SWIMMING_DIMENSIONS.scale(this.getAgeScale());
        return super.getDefaultDimensions(pose);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.CONCAVENATOR_FOOD);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.isSwitchingToSandSwim();
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.CACTUS);
    }

    @Override
    protected float getBlockSpeedFactor() {
        BlockState blockstate = this.level().getBlockState(this.blockPosition());
        float speedFactor = blockstate.getBlock().getSpeedFactor();
        if (blockstate.is(Blocks.SOUL_SAND) && this.isSandSwimming()) {
            return Mth.lerp((float) this.getAttributeValue(Attributes.MOVEMENT_EFFICIENCY), speedFactor * 2.5F, 1.0F);
        } else {
            return Mth.lerp((float) this.getAttributeValue(Attributes.MOVEMENT_EFFICIENCY), super.getBlockSpeedFactor(), 1.0F);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isSandSwimming() && this.isLandNavigator) {
            this.switchNavigator(false);
        }
        if (!this.isSandSwimming() && !this.isLandNavigator) {
            this.switchNavigator(true);
        }

        if (!this.level().isClientSide) {
            this.tickSandSwimming();
        }
        else if (this.isSandSwimming() && !this.isInWaterOrBubble() && this.getDeltaMovement().horizontalDistance() > 0.05D) {
            this.spawnSandSwimmingParticles();
        }

        if (this.isSandSwimming()) {
            this.setSandSwimTime(this.getSandSwimTime() + 1);
            if (this.getSandSwimTime() > 400 && !this.hasArmor()) {
                ArmorType type = ArmorType.armorTypeFromBlock(this.level().getBlockState(this.blockPosition().below()));
                if (type != ArmorType.NONE) {
                    if (this.level().isClientSide) {
                        this.spawnArmorParticles();
                    }
                    this.playSound(this.level().getBlockState(this.getOnPos()).getSoundType().getPlaceSound(), 1.0F, 0.9F + this.getRandom().nextFloat() * 0.25F);
                    this.setArmorType(type);
                }
            }
        } else if (this.getSandSwimTime() > 0) {
            this.setSandSwimTime(0);
        }
    }

    private void tickSandSwimming() {
        if (this.getSandSwimCooldown() > 0) {
            this.setSandSwimCooldown(this.getSandSwimCooldown() - 1);
        }

        if ((this.isSandSwimmingBlockBelow(1) || this.level().getBlockState(this.blockPosition()).is(UP2BlockTags.CONCAVENATOR_SWIMS_ON)) && !this.isSandSwimming() && this.getPose() == Pose.STANDING && this.getSandSwimCooldown() == 0 && !this.isInWaterOrBubble()) {
            this.setSandSwimming(true);
            this.setPose(UP2Poses.START_SWIMMING.get());
        }

        if (this.isSandSwimming() && this.getPose() == Pose.STANDING && (!this.isSandSwimmingBlockBelow(2) || this.isInWaterOrBubble())) {
            this.setSandSwimming(false);
            this.setSandSwimCooldown(200 + this.getRandom().nextInt(200));
            if (this.onGround()) {
                this.setPose(UP2Poses.STOP_SWIMMING.get());
            } else {
                this.setPose(Pose.STANDING);
            }
        }
    }

    private boolean isSandSwimmingBlockBelow(int depth) {
        int valid = 0;
        for (int i = 1; i <= depth; i++) {
            BlockState state = this.level().getBlockState(this.blockPosition().below(i));
            if (state.is(UP2BlockTags.CONCAVENATOR_SWIMS_ON)) {
                valid++;
            }
        }
        return valid >= 1;
    }

    private boolean shouldBePushedDown() {
        if (!this.isSandSwimming()) {
            return false;
        } else if (this.onGround()) {
            return false;
        }
        return this.fallDistance > 0.0F && this.fallDistance < 0.2F && this.canStepDownBlock();
    }

    private void spawnSandSwimmingParticles() {
        BlockPos onPos = this.getOnPos(0.2F);
        BlockState blockstate = this.level().getBlockState(onPos);
        Vec3 deltaMovement = this.getDeltaMovement();
        BlockPos blockPos = this.blockPosition();

        for (int i = 0; i < 2; i++) {
            if (!blockstate.addRunningEffects(this.level(), onPos, this) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                double xPos = this.getX() + (this.getRandom().nextDouble() - (double) 0.5F) * (double) this.getDimensions(this.getPose()).width();
                double zPos = this.getZ() + (this.getRandom().nextDouble() - (double) 0.5F) * (double) this.getDimensions(this.getPose()).width();
                if (blockPos.getX() != onPos.getX()) {
                    xPos = Mth.clamp(xPos, onPos.getX(), (double) onPos.getX() + (double) 1.5F);
                }
                if (blockPos.getZ() != onPos.getZ()) {
                    zPos = Mth.clamp(zPos, onPos.getZ(), (double) onPos.getZ() + (double) 1.5F);
                }
                this.level().addParticle((new BlockParticleOption(ParticleTypes.BLOCK, blockstate)).setPos(onPos), xPos, this.getY() + 0.1, zPos, deltaMovement.x * (double) -6.0F, 1.5F, deltaMovement.z * (double) -6.0F);
            }
        }
    }

    private void spawnArmorParticles() {
        BlockPos onPos = this.getOnPos(0.2F);
        BlockState blockstate = this.level().getBlockState(onPos);
        for (int i = 0; i < 20; i++) {
            double xVelocity = this.random.nextGaussian() * 0.1D;
            double yVelocity = this.random.nextGaussian() * 0.1D;
            double zVelocity = this.random.nextGaussian() * 0.1D;
            this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(onPos), this.getRandomX(0.8D), this.getRandomY(), this.getRandomZ(0.8D), xVelocity, yVelocity, zVelocity);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy() && !this.isSandSwimming() && !this.isInWaterOrBubble() && !(this.getPose() == UP2Poses.ATTACKING.get() && this.isSandSwimming()), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.sandSwimStartAnimationState.animateWhen(this.getPose() == UP2Poses.START_SWIMMING.get(), this.tickCount);
        this.sandSwimEndAnimationState.animateWhen(this.getPose() == UP2Poses.STOP_SWIMMING.get(), this.tickCount);
        this.sandSwimIdleAnimationState.animateWhen(this.isSandSwimming() && !this.isSwitchingToSandSwim() && !this.isInWaterOrBubble(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
        this.diveAttackAnimationState.animateWhen(this.getPose() == UP2Poses.CHARGING.get(), this.tickCount);
    }

    public boolean isSwitchingToSandSwim() {
        return this.getPose() == UP2Poses.START_SWIMMING.get() || this.getPose() == UP2Poses.STOP_SWIMMING.get();
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackCooldown > 0) attackCooldown--;
        if (diveAttackCooldown > 0) diveAttackCooldown--;
        if (this.sandSwimStartTicks > 0) sandSwimStartTicks--;
        if (this.sandSwimEndTicks > 0) sandSwimEndTicks--;
        if (this.sandSwimStartTicks == 0 && this.getPose() == UP2Poses.START_SWIMMING.get()) {
            this.setPose(Pose.STANDING);
        }
        if (this.sandSwimEndTicks == 0 && this.getPose() == UP2Poses.STOP_SWIMMING.get()) {
            this.setPose(Pose.STANDING);
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (SAND_SWIMMING.equals(accessor)) {
            this.refreshDimensions();
        }
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.START_SWIMMING.get()) {
                this.sandSwimStartTicks = 20;
            }
            else if (this.getPose() == UP2Poses.STOP_SWIMMING.get()) {
                this.sandSwimEndTicks = 20;
            }
        }
        if (ARMOR_TYPE.equals(accessor)) {
            if (this.getArmorType() == ArmorType.NONE) {
                Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(1.0D);
            } else {
                Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(15.0D);
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ARMOR_TYPE, ArmorType.NONE.getId());
        builder.define(SAND_SWIMMING, false);
        builder.define(SAND_SWIM_COOLDOWN, 80 + this.getRandom().nextInt(80));
        builder.define(SAND_SWIM_TIME, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("ArmorType", this.getArmorType().getId());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setArmorType(ArmorType.byId(compoundTag.getInt("ArmorType")));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasArmor() {
        return this.getArmorType() != ArmorType.NONE;
    }

    public ArmorType getArmorType() {
        return ArmorType.byId(this.entityData.get(ARMOR_TYPE));
    }

    public void setArmorType(ArmorType type) {
        this.entityData.set(ARMOR_TYPE, type.getId());
    }

    public boolean isSandSwimming() {
        return this.entityData.get(SAND_SWIMMING);
    }

    public void setSandSwimming(boolean digging) {
        this.entityData.set(SAND_SWIMMING, digging);
    }

    public int getSandSwimCooldown() {
        return this.entityData.get(SAND_SWIM_COOLDOWN);
    }

    public void setSandSwimCooldown(int cooldown) {
        this.entityData.set(SAND_SWIM_COOLDOWN, cooldown);
    }

    public int getSandSwimTime() {
        return this.entityData.get(SAND_SWIM_TIME);
    }

    public void setSandSwimTime(int time) {
        this.entityData.set(SAND_SWIM_TIME, time);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.CONCAVENATOR.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.MAJUNGASAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.MAJUNGASAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.MAJUNGASAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        if (this.isSandSwimming()) {
            SoundType soundtype = state.getSoundType(this.level(), pos, this);
            this.playSound(soundtype.getHitSound(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
        } else {
            this.playSound(UP2SoundEvents.MAJUNGASAURUS_STEP.get(), 0.2F, 1.0F);
        }
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }

    public enum ArmorType {
        NONE(0, null, null),
        ARMOR_SAND(1, UP2BlockTags.CONCAVENATOR_SAND_ARMOR_BLOCKS, Blocks.SANDSTONE.getLootTable()),
        ARMOR_RED_SAND(2, UP2BlockTags.CONCAVENATOR_RED_SAND_ARMOR_BLOCKS, Blocks.RED_SANDSTONE.getLootTable()),
        ARMOR_SOUL_SAND(3, UP2BlockTags.CONCAVENATOR_SOUL_SAND_ARMOR_BLOCKS, Blocks.SOUL_SOIL.getLootTable());

        private final int id;
        @Nullable
        private final TagKey<Block> blockTag;
        @Nullable
        private final ResourceKey<LootTable> lootTable;

        ArmorType(int id, @Nullable TagKey<Block> blockTag, @Nullable ResourceKey<LootTable> lootTable) {
            this.id = id;
            this.blockTag = blockTag;
            this.lootTable = lootTable;
        }

        public int getId() {
            return id;
        }

        public ResourceKey<LootTable> getLootTable() {
            return lootTable;
        }

        public static ArmorType armorTypeFromBlock(BlockState state) {
            for (ArmorType type : values()) {
                if (type.blockTag != null && state.is(type.blockTag)) {
                    return type;
                }
            }
            return NONE;
        }

        public static ArmorType byId(int id) {
            if (id < 0 || id >= ArmorType.values().length) {
                id = 0;
            }
            return ArmorType.values()[id];
        }
    }

    // Goals
    private static class ConcavenatorRandomStrollGoal extends PrehistoricRandomStrollGoal {

        private final Concavenator concavenator;

        public ConcavenatorRandomStrollGoal(Concavenator concavenator, double speedModifier) {
            super(concavenator, speedModifier, true);
            this.concavenator = concavenator;
        }

        @Override
        public boolean canUse() {
            return !concavenator.isSandSwimming() && concavenator.isLandNavigator && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !concavenator.isSandSwimming() && concavenator.isLandNavigator && super.canContinueToUse();
        }
    }

    private static class SandSwimmingRandomStrollGoal extends PrehistoricRandomStrollGoal {

        private final Concavenator concavenator;

        public SandSwimmingRandomStrollGoal(Concavenator concavenator, double speedModifier) {
            super(concavenator, speedModifier, 40, true);
            this.concavenator = concavenator;
        }

        @Override
        public boolean canUse() {
            return concavenator.isSandSwimming() && !concavenator.isLandNavigator && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return concavenator.isSandSwimming() && !concavenator.isLandNavigator && super.canContinueToUse();
        }

        @Nullable
        @Override
        protected Vec3 getPosition() {
            Vec3 position = LandRandomPos.getPos(concavenator, 20, 5);
            if (position != null) {
                BlockPos blockPos = BlockPos.containing(position);
                if (concavenator.level().getBlockState(blockPos).is(UP2BlockTags.CONCAVENATOR_SWIMS_ON)) {
                    return position;
                }
            }
            return super.getPosition();
        }
    }

    private static class SandSwimmingLookControl extends PrehistoricLookControl {

        private final int maxYRotFromCenter;

        public SandSwimmingLookControl(PrehistoricMob mob, int maxYRotFromCenter) {
            super(mob);
            this.maxYRotFromCenter = maxYRotFromCenter;
        }

        @Override
        public void tick() {
            if (!mob.refuseToLook()) {
                if (this.resetXRotOnTick()) {
                    this.mob.setXRot(0.0F);
                }

                if (this.lookAtCooldown > 0) {
                    this.lookAtCooldown--;
                    this.getYRotD().ifPresent(f -> this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, f + 20.0F, this.yMaxRotSpeed));
                    this.getXRotD().ifPresent(f -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), f + 10.0F, this.xMaxRotAngle)));
                } else {
                    this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, this.yMaxRotSpeed);
                }

                float f = Mth.wrapDegrees(this.mob.yHeadRot - this.mob.yBodyRot);
                if (f < (float) (-this.maxYRotFromCenter)) {
                    this.mob.yBodyRot -= 4.0F;
                } else if (f > (float) this.maxYRotFromCenter) {
                    this.mob.yBodyRot += 4.0F;
                }
            }
        }
    }

    private static class SandSwimmingMoveControl extends PrehistoricMoveControl {

        private final int maxTurnY;

        public SandSwimmingMoveControl(PrehistoricMob mob, int maxTurnY) {
            super(mob);
            this.maxTurnY = maxTurnY;
        }

        @Override
        public void tick() {
            if (!prehistoricMob.refuseToMove()) {
                if (this.operation == Operation.MOVE_TO && !mob.getNavigation().isDone()) {
                    double d0 = wantedX - mob.getX();
                    double d2 = wantedZ - mob.getZ();
                    double d3 = d0 * d0 + d2 * d2;
                    if (d3 < (double) 2.5000003E-7F) {
                        this.mob.setZza(0.0F);
                    } else {
                        float f = (float) (Mth.atan2(d2, d0) * (double) 180.0F / (double) (float) Math.PI) - 90.0F;
                        this.mob.setYRot(this.rotlerp(mob.getYRot(), f, (float) maxTurnY));
                        this.mob.yBodyRot = mob.getYRot();
                        this.mob.yHeadRot = mob.getYRot();
                        float speed = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                        this.mob.setSpeed(speed * 3.0F);
                        float f6 = Mth.cos(mob.getXRot() * ((float) Math.PI / 180F));
                        float f4 = Mth.sin(mob.getXRot() * ((float) Math.PI / 180F));
                        this.mob.zza = f6 * speed;
                        this.mob.yya = -f4 * speed;
                    }
                } else {
                    this.mob.setSpeed(0.0F);
                    this.mob.setXxa(0.0F);
                    this.mob.setYya(0.0F);
                    this.mob.setZza(0.0F);
                }
            }
        }
    }
}
