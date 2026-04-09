package com.barlinc.unusual_prehistory.entity.mob.update_6;

import com.barlinc.unusual_prehistory.entity.ai.goals.IdleAnimationGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.SleepingGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Cotylorhynchus extends PrehistoricMob {

    private static final EntityDataAccessor<Integer> GROG_TICKS = SynchedEntityData.defineId(Cotylorhynchus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GROG_TYPE = SynchedEntityData.defineId(Cotylorhynchus.class, EntityDataSerializers.INT);

    private static final EntityDimensions EEPY_DIMENSIONS = EntityDimensions.scalable(2.1F, 1.3F);

    public final SmoothAnimationState grazeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState burpAnimationState = new SmoothAnimationState();

    private int grazeCooldown = 800 + this.getRandom().nextInt(800);
    private int burpTicks;

    public Cotylorhynchus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 2.0D, 10, 4));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.COTYLORHYNCHUS_FOOD), false));
        this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new SleepingGoal(this));
        this.goalSelector.addGoal(9, new CotylorhynchusGrazeGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 26.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.15F);
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && this.horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.4D * this.getBbHeight();
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
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
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.COTYLORHYNCHUS_FOOD);
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return super.canCollideWith(entity) && !(entity instanceof Cotylorhynchus);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return this.isBaby();
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getPose() == UP2Poses.BURPING.get() || this.getIdleState() == 1;
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        if (this.isEepy()) return EEPY_DIMENSIONS.scale(this.getScale());
        return super.getDefaultDimensions(pose);
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0, 0, -this.getBbWidth() * 0.9F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);
        if (!this.isFullOfGrog() && !this.level().isClientSide && !this.isEepy()) {
            if (itemstack.is(UP2ItemTags.SWEET_COTYLORHYNCHUS_FOOD)) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                if (this.getRandom().nextFloat() <= 0.2F) {
                    if (this.getNavigation().getPath() != null) {
                        this.getNavigation().stop();
                    }
                    this.setPose(UP2Poses.BURPING.get());
                    this.setGrogType(1);
                    this.setGrogTicks(2400 + this.getRandom().nextInt(1200));
                }
                this.playSound(this.getEatingSound(itemstack), 1.0F, 0.9F + this.getRandom().nextFloat() * 0.25F);
                return InteractionResult.SUCCESS;
            }
            else if (itemstack.is(UP2ItemTags.STINKY_COTYLORHYNCHUS_FOOD)) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                if (this.getRandom().nextFloat() <= 0.2F) {
                    if (this.getNavigation().getPath() != null) {
                        this.getNavigation().stop();
                    }
                    this.setPose(UP2Poses.BURPING.get());
                    this.setGrogType(2);
                    this.setGrogTicks(2400 + this.getRandom().nextInt(1200));
                }
                this.playSound(this.getEatingSound(itemstack), 1.0F, 0.9F + this.getRandom().nextFloat() * 0.25F);
                return InteractionResult.SUCCESS;
            }
        }
        else if (itemstack.is(Items.GLASS_BOTTLE) && this.isFullOfGrog() && this.getGrogTicks() == 0) {
            if (this.getGrogType() == 1) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                if (!player.addItem(new ItemStack(Items.HONEY_BOTTLE))) {
                    player.spawnAtLocation(Items.HONEY_BOTTLE);
                }
                this.setGrogType(0);
                this.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
            else if (this.getGrogType() == 2) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                if (!player.addItem(new ItemStack(Items.DRAGON_BREATH))) {
                    player.spawnAtLocation(Items.DRAGON_BREATH);
                }
                this.setGrogType(0);
                this.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return type;
    }

    private void spawnGrogParticle(Level level, double xStart, double xEnd, double zStart, double zEnd, double yPos) {
        level.addParticle(ParticleTypes.DRIPPING_HONEY, Mth.lerp(level.random.nextDouble(), xStart, xEnd), yPos, Mth.lerp(level.random.nextDouble(), zStart, zEnd), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getGrogTicks() > 0) {
            this.setGrogTicks(this.getGrogTicks() - 1);
        }
        if (!this.isEepy()) {
            if (grazeCooldown > 0) grazeCooldown--;
        }

        if (burpTicks > 0) burpTicks--;
        if (this.getPose() == UP2Poses.BURPING.get() && burpTicks == 14) {
            this.playSound(UP2SoundEvents.COTYLORHYNCHUS_BURP.get(), 1.0F, 0.9F + this.getRandom().nextFloat() * 0.25F);
        }
        if (this.getPose() == UP2Poses.BURPING.get() && burpTicks == 0) {
            this.setPose(Pose.STANDING);
        }

        if (this.isFullOfGrog() && this.getGrogTicks() <= 0 && this.getRandom().nextFloat() < 0.05F && this.level().isClientSide) {
            for (int i = 0; i < 3; i++) {
                this.spawnGrogParticle(this.level(), this.getX() - (double) 1.6F, this.getX() + (double) 1.6F, this.getZ() - (double) 1.6F, this.getZ() + (double) 1.6F, this.getY(0.8D));
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isEepy(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.grazeAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.burpAnimationState.animateWhen(this.getPose() == UP2Poses.BURPING.get(), this.tickCount);
    }

    @Override
    public float getWalkAnimationSpeed() {
        return this.isBaby() ? 5.0F : 10.0F;
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.BURPING.get()) {
                this.burpTicks = 40;
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(GROG_TICKS, 0);
        builder.define(GROG_TYPE, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("GrogTicks", this.getGrogTicks());
        compoundTag.putInt("GrogType", this.getGrogType());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setGrogTicks(compoundTag.getInt("GrogTicks"));
        this.setGrogType(compoundTag.getInt("GrogType"));
    }

    public int getGrogTicks() {
        return this.entityData.get(GROG_TICKS);
    }

    public void setGrogTicks(int grogTicks) {
        this.entityData.set(GROG_TICKS, grogTicks);
    }

    public int getGrogType() {
        return this.entityData.get(GROG_TYPE);
    }

    public void setGrogType(int grogType) {
        this.entityData.set(GROG_TYPE, grogType);
    }

    public boolean isFullOfGrog() {
        return this.getGrogType() != 0;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.COTYLORHYNCHUS.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.COTYLORHYNCHUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.COTYLORHYNCHUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.COTYLORHYNCHUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.COTYLORHYNCHUS_STEP.get(), 0.2F, 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }

    private static class CotylorhynchusGrazeGoal extends IdleAnimationGoal {

        private final Cotylorhynchus cotylorhynchus;

        public CotylorhynchusGrazeGoal(Cotylorhynchus cotylorhynchus) {
            super(cotylorhynchus, 40, 1);
            this.cotylorhynchus = cotylorhynchus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && cotylorhynchus.grazeCooldown == 0 && cotylorhynchus.level().getBlockState(cotylorhynchus.blockPosition().below()).is(UP2BlockTags.COTYLORHYNCHUS_GRAZING_BLOCKS);
        }

        @Override
        public void stop() {
            super.stop();
            this.cotylorhynchus.grazeCooldown = 800 + cotylorhynchus.getRandom().nextInt(800);
        }
    }
}
