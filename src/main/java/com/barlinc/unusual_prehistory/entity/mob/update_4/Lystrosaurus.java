package com.barlinc.unusual_prehistory.entity.mob.update_4;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2DamageTypeTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.utils.UP2ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Lystrosaurus extends PrehistoricMob {

    public final SmoothAnimationState grazeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState digAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState scratch1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState scratch2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState blinkAnimationState = new SmoothAnimationState();

    private boolean scratchAlt = false;

    private int scratchCooldown = 1200 + this.getRandom().nextInt(1300);
    private int grazeCooldown = 1300 + this.getRandom().nextInt(1400);
    private int digCooldown = 2000 + this.getRandom().nextInt(2000);
    private int blinkCooldown = 400 + this.getRandom().nextInt(500);
    private int shakeCooldown = 1000 + this.getRandom().nextInt(1200);

    public Lystrosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LystrosaurusRunLikeCrazyGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 2.0D, 10, 4));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.LYSTROSAURUS_FOOD), false));
        this.goalSelector.addGoal(3, new PrehistoricRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new PrehistoricFollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new SleepingGoal(this));
        this.goalSelector.addGoal(7, new LystrosaurusBlinkGoal(this));
        this.goalSelector.addGoal(8, new LystrosaurusScratchGoal(this));
        this.goalSelector.addGoal(8, new LystrosaurusDigGoal(this));
        this.goalSelector.addGoal(8, new LystrosaurusShakeGoal(this));
        this.goalSelector.addGoal(8, new LystrosaurusGrazeGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.ARMOR, 30.0F)
                .add(Attributes.ARMOR_TOUGHNESS, 20.0F);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.9F;
    }

    @Override
    public float getStepHeight() {
        return this.isRunning() ? 1.0F : 0.6F;
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
        return stack.is(UP2ItemTags.LYSTROSAURUS_FOOD);
    }

    @Override
    public int getHealCooldown() {
        return this.isEepy() ? 80 : 150;
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource source, float amount) {
        if (source.is(DamageTypes.MAGIC) || source.is(DamageTypes.INDIRECT_MAGIC)) {
            super.actuallyHurt(source, amount * 0.1F);
        } else {
            super.actuallyHurt(source, amount * 0.2F);
        }
    }

    @Override
    public boolean canBeAffected(@NotNull MobEffectInstance effect) {
        return false;
    }

    // 3 hours of air
    @Override
    public int getMaxAirSupply() {
        return 216000;
    }

    @Override
    protected int increaseAirSupply(int currentAir) {
        return this.getMaxAirSupply();
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0, 0, -this.getBbWidth() * 1.05F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive() && !this.level().isClientSide && this.tickCount % 4 == 0) {
            this.breakFallingBlocks();
        }
    }

    private void breakFallingBlocks() {
        this.level().getEntities(this, this.getBoundingBox()).forEach((entity) -> {
            if (entity instanceof FallingBlockEntity fallingBlockEntity) {
                if (fallingBlockEntity.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                    fallingBlockEntity.spawnAtLocation(fallingBlockEntity.getBlockState().getBlock());
                }
                fallingBlockEntity.discard();
                UP2ParticleUtils.queueParticlesOnBlockFaces(fallingBlockEntity.level(), fallingBlockEntity.blockPosition(), new BlockParticleOption(ParticleTypes.BLOCK, fallingBlockEntity.getBlockState()), UniformInt.of(2, 4));
                fallingBlockEntity.callOnBrokenAfterFall(fallingBlockEntity.getBlockState().getBlock(), fallingBlockEntity.blockPosition());
            }
        });
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public void setupAnimationCooldowns() {
        if (!this.isEepy() && !this.isInWaterOrBubble()) {
            if (scratchCooldown > 0) scratchCooldown--;
            if (grazeCooldown > 0) grazeCooldown--;
            if (digCooldown > 0) digCooldown--;
            if (blinkCooldown > 0) blinkCooldown--;
            if (shakeCooldown > 0) shakeCooldown--;
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.getIdleState() != 3 && !this.isEepy(), this.tickCount);
        this.scratch1AnimationState.animateWhen(this.getIdleState() == 1 && !scratchAlt, this.tickCount);
        this.scratch2AnimationState.animateWhen(this.getIdleState() == 1 && scratchAlt, this.tickCount);
        this.grazeAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.digAnimationState.animateWhen(this.getIdleState() == 3, this.tickCount);
        this.blinkAnimationState.animateWhen(this.getIdleState() == 4, this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 5, this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble() && !this.onGround(), this.tickCount);
    }

    protected void scratchCooldown() {
        this.scratchCooldown = 1200 + this.getRandom().nextInt(1300);
    }

    protected void grazeCooldown() {
        this.grazeCooldown = 1300 + this.getRandom().nextInt(1400);
    }

    protected void digCooldown() {
        this.digCooldown = 2000 + this.getRandom().nextInt(2000);
    }

    protected void blinkCooldown() {
        this.blinkCooldown = 400 + this.getRandom().nextInt(500);
    }

    protected void shakeCooldown() {
        this.shakeCooldown = 1000 + this.getRandom().nextInt(1200);
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(UP2DamageTypeTags.LYSTROSAURUS_IMMUNE_TO);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 2 || this.getIdleState() == 3;
    }

    @Override
    public boolean refuseToLook() {
        return super.refuseToLook() || this.getIdleState() == 2 || this.getIdleState() == 3;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.LYSTROSAURUS.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.hasCustomName() && this.getName().getString().contains("chainsmoker") && this.getRandom().nextFloat() < 0.3F) {
            return UP2SoundEvents.LYSTROSAURUS_CHAINSMOKER.get();
        }
        return UP2SoundEvents.LYSTROSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.LYSTROSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.LYSTROSAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.LYSTROSAURUS_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }

    public static boolean canSpawn(EntityType<Lystrosaurus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.LYSTROSAURUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    // Goals
    private static class LystrosaurusScratchGoal extends IdleAnimationGoal {

        private final Lystrosaurus lystrosaurus;

        public LystrosaurusScratchGoal(Lystrosaurus lystrosaurus) {
            super(lystrosaurus, 30, 1, (byte) 67, (byte) 68);
            this.lystrosaurus = lystrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && lystrosaurus.scratchCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.lystrosaurus.scratchCooldown();
        }

        @Override
        public void start() {
            super.start();
            this.lystrosaurus.scratchAlt = lystrosaurus.getRandom().nextBoolean();
        }
    }

    private static class LystrosaurusGrazeGoal extends IdleAnimationGoal {

        private final Lystrosaurus lystrosaurus;

        public LystrosaurusGrazeGoal(Lystrosaurus lystrosaurus) {
            super(lystrosaurus, 40, 2, (byte) 69, (byte) 70);
            this.lystrosaurus = lystrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && lystrosaurus.grazeCooldown == 0 && lystrosaurus.level().getBlockState(lystrosaurus.blockPosition().below()).is(UP2BlockTags.LYSTROSAURUS_GRAZING_BLOCKS);
        }

        @Override
        public void stop() {
            super.stop();
            this.lystrosaurus.grazeCooldown();
        }
    }

    private static class LystrosaurusDigGoal extends IdleAnimationGoal {

        private final Lystrosaurus lystrosaurus;

        public LystrosaurusDigGoal(Lystrosaurus lystrosaurus) {
            super(lystrosaurus, 80, 3, (byte) 73, (byte) 74);
            this.lystrosaurus = lystrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && lystrosaurus.digCooldown == 0 && lystrosaurus.level().getBlockState(lystrosaurus.blockPosition().below()).is(UP2BlockTags.LYSTROSAURUS_DIGGING_BLOCKS);
        }

        @Override
        public void stop() {
            super.stop();
            this.lystrosaurus.digCooldown();
        }

        @Override
        public void tick() {
            super.tick();
            if (timer % 6 == 0 && timer < 60 && timer > 20) {
                this.spawnEffectsAtBlock(lystrosaurus.blockPosition().below());
                this.lystrosaurus.playSound(lystrosaurus.level().getBlockState(lystrosaurus.blockPosition().below()).getSoundType().getHitSound(), 0.3F, 0.8F + lystrosaurus.getRandom().nextFloat() * 0.25F);
            }
        }

        public void spawnEffectsAtBlock(BlockPos blockPos) {
            float radius = 0.4F;
            for (int i1 = 0; i1 < 3; i1++) {
                double motionX = lystrosaurus.getRandom().nextGaussian() * 0.07D;
                double motionY = lystrosaurus.getRandom().nextGaussian() * 0.07D;
                double motionZ = lystrosaurus.getRandom().nextGaussian() * 0.07D;
                float angle = (float) ((0.0174532925 * lystrosaurus.yBodyRot) + i1);
                double extraX = radius * Mth.sin(Mth.PI + angle);
                double extraY = 0.8F;
                double extraZ = radius * Mth.cos(angle);
                BlockState state = lystrosaurus.level().getBlockState(blockPos);
                ((ServerLevel) lystrosaurus.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), blockPos.getX() + 0.5 + extraX, blockPos.getY() + 0.5 + extraY, blockPos.getZ() + 0.5 + extraZ, 1, motionX, motionY, motionZ, 1);
            }
        }
    }

    private static class LystrosaurusBlinkGoal extends IdleAnimationGoal {

        private final Lystrosaurus lystrosaurus;

        public LystrosaurusBlinkGoal(Lystrosaurus lystrosaurus) {
            super(lystrosaurus, 60, 4, (byte) 75, (byte) 76, false, false);
            this.lystrosaurus = lystrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && lystrosaurus.blinkCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.lystrosaurus.blinkCooldown();
        }
    }

    private static class LystrosaurusShakeGoal extends IdleAnimationGoal {

        private final Lystrosaurus lystrosaurus;

        public LystrosaurusShakeGoal(Lystrosaurus lystrosaurus) {
            super(lystrosaurus, 40, 5, (byte) 77, (byte) 78, false);
            this.lystrosaurus = lystrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && lystrosaurus.shakeCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.lystrosaurus.shakeCooldown();
        }
    }
}
