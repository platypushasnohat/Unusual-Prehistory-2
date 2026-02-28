package com.barlinc.unusual_prehistory.entity.mob.update_1;

import com.barlinc.unusual_prehistory.entity.ai.goals.AnimationGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.RandomSitGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.kentrosaurus.KentrosaurusAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.kentrosaurus.KentrosaurusDefendThornsGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.kentrosaurus.KentrosaurusFollowThornsGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2DamageTypeTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Kentrosaurus extends PrehistoricMob {

    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(1.6F, 1.75F);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState grazeAnimationState = new AnimationState();
    public final AnimationState shakeAnimationState = new AnimationState();
    public final AnimationState stretch1AnimationState = new AnimationState();
    public final AnimationState stretch2AnimationState = new AnimationState();
    public final AnimationState yawnAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();

    private int attackTicks;

    private int grazeCooldown = 400 + this.getRandom().nextInt(50 * 50);
    private int shakeCooldown = 300 + this.getRandom().nextInt(20 * 60);
    private int yawnCooldown = 400 + this.getRandom().nextInt(50 * 30);
    private int stretchCooldown = 400 + this.getRandom().nextInt(60 * 50);

    public Kentrosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
        this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new KentrosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new KentrosaurusFollowThornsGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.KENTROSAURUS_FOOD), false));
        this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new RandomSitGoal(this));
        this.goalSelector.addGoal(9, new KentrosaurusGrazeGoal(this));
        this.goalSelector.addGoal(9, new KentrosaurusShakeGoal(this));
        this.goalSelector.addGoal(9, new KentrosaurusStretchGoal(this));
        this.goalSelector.addGoal(9, new KentrosaurusYawnGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new KentrosaurusDefendThornsGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 36.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.16F)
                .add(Attributes.ARMOR, 4.0F);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.5F;
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && this.horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.4D * this.getBbHeight();
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
        return stack.is(UP2ItemTags.KENTROSAURUS_FOOD);
    }

    public boolean entityHasThorns(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getEnchantmentLevel(Enchantments.THORNS) > 0 || entity.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(Enchantments.THORNS) > 0 || entity.getItemBySlot(EquipmentSlot.LEGS).getEnchantmentLevel(Enchantments.THORNS) > 0 || entity.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(Enchantments.THORNS) > 0;
    }

    public static void angerNearbyKentrosaurus(Player player, boolean angerIfSeen) {
        List<Kentrosaurus> list = player.level().getEntitiesOfClass(Kentrosaurus.class, player.getBoundingBox().inflate(16));
        list.stream().filter((kentrosaurus) -> !angerIfSeen || BehaviorUtils.canSee(kentrosaurus, player)).forEach((kentrosaurus) -> kentrosaurus.standAndSetTarget(player));
    }

    private void standAndSetTarget(LivingEntity target) {
        this.setTarget(target);
        this.stopSitting();
    }

    @Override
    public void setupAnimationCooldowns() {
        if (grazeCooldown > 0) grazeCooldown--;
        if (shakeCooldown > 0) shakeCooldown--;
        if (stretchCooldown > 0) stretchCooldown--;
        if (yawnCooldown > 0) yawnCooldown--;
        if (this.attackTicks > 0) attackTicks--;
        if (this.attackTicks == 0 && this.getPose() == UP2Poses.TAIL_WHIPPING.get()) this.setPose(Pose.STANDING);
    }

    @Override
    public void setupAnimationStates() {
        if (attackTicks == 0 && (this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted())) {
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
        }
        this.idleAnimationState.animateWhen(this.getPose() != UP2Poses.TAIL_WHIPPING.get() && !this.isInWater(), this.tickCount);
        this.swimAnimationState.animateWhen(this.getPose() != UP2Poses.TAIL_WHIPPING.get() && this.isInWater(), this.tickCount);

        if (this.isMobVisuallySitting()) {
            this.sitEndAnimationState.stop();
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
            this.idleAnimationState.stop();
            this.grazeAnimationState.stop();
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
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.TAIL_WHIPPING.get()) {
                if (this.getRandom().nextBoolean()) this.attack1AnimationState.start(this.tickCount);
                else this.attack2AnimationState.start(this.tickCount);
                this.attackTicks = 40;
            }
            else {
                this.grazeAnimationState.stop();
                this.shakeAnimationState.stop();
                this.yawnAnimationState.stop();
                this.stretch1AnimationState.stop();
                this.stretch2AnimationState.stop();
                this.attack1AnimationState.stop();
                this.attack2AnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    public void handleEntityEvent(byte id) {
        switch (id) {
            case 67 -> this.grazeAnimationState.start(this.tickCount);
            case 68 -> this.grazeAnimationState.stop();

            case 69 -> this.shakeAnimationState.start(this.tickCount);
            case 70 -> this.shakeAnimationState.stop();

            case 71 -> this.yawnAnimationState.start(this.tickCount);
            case 72 -> this.yawnAnimationState.stop();

            case 73 -> {
                if (this.getRandom().nextBoolean()) this.stretch2AnimationState.start(this.tickCount);
                else this.stretch1AnimationState.start(this.tickCount);
            }
            case 74 -> {
                this.stretch1AnimationState.stop();
                this.stretch2AnimationState.stop();
            }
            default -> super.handleEntityEvent(id);
        }
    }

    protected void grazeCooldown() {
        this.grazeCooldown = 400 + this.getRandom().nextInt(50 * 50);
    }

    protected void shakeCooldown() {
        this.shakeCooldown = 300 + this.getRandom().nextInt(20 * 60);
    }

    protected void yawnCooldown() {
        this.yawnCooldown = 400 + this.getRandom().nextInt(50 * 30);
    }

    protected void stretchCooldown() {
        this.stretchCooldown = 400 + this.getRandom().nextInt(60 * 50);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return (pose == UP2Poses.SITTING.get() ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose));
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float amount) {
        if (!damageSource.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !damageSource.is(DamageTypes.THORNS)) {
            Entity entity = damageSource.getDirectEntity();
            if (entity instanceof LivingEntity target) {
                target.hurt(this.damageSources().thorns(this), 2.0F);
            }
        }
        super.actuallyHurt(damageSource, amount);
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(UP2DamageTypeTags.KENTROSAURUS_IMMUNE_TO);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 4;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.KENTROSAURUS.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.KENTROSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.KENTROSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.KENTROSAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.KENTROSAURUS_STEP.get(), 1.0F, 1.1F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 160;
    }

    public static boolean canSpawn(EntityType<Kentrosaurus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.KENTROSAURUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    // Goals
    private static class KentrosaurusGrazeGoal extends AnimationGoal {

        private final Kentrosaurus kentrosaurus;

        public KentrosaurusGrazeGoal(Kentrosaurus kentrosaurus) {
            super(kentrosaurus, 40, 1, (byte) 67, (byte) 68);
            this.kentrosaurus = kentrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && kentrosaurus.grazeCooldown == 0 && !kentrosaurus.isMobSitting() && kentrosaurus.level().getBlockState(kentrosaurus.blockPosition().below()).is(UP2BlockTags.KENTROSAURUS_GRAZING_BLOCKS);
        }

        @Override
        public void stop() {
            super.stop();
            this.kentrosaurus.grazeCooldown();
        }
    }

    private static class KentrosaurusShakeGoal extends AnimationGoal {

        private final Kentrosaurus kentrosaurus;

        public KentrosaurusShakeGoal(Kentrosaurus kentrosaurus) {
            super(kentrosaurus, 40, 2, (byte) 69, (byte) 70, false);
            this.kentrosaurus = kentrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && kentrosaurus.shakeCooldown == 0 && !kentrosaurus.isMobSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.kentrosaurus.shakeCooldown();
        }
    }

    private static class KentrosaurusYawnGoal extends AnimationGoal {

        private final Kentrosaurus kentrosaurus;

        public KentrosaurusYawnGoal(Kentrosaurus kentrosaurus) {
            super(kentrosaurus, 80, 3, (byte) 71, (byte) 72, false);
            this.kentrosaurus = kentrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && kentrosaurus.yawnCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.kentrosaurus.yawnCooldown();
        }
    }

    private static class KentrosaurusStretchGoal extends AnimationGoal {

        private final Kentrosaurus kentrosaurus;

        public KentrosaurusStretchGoal(Kentrosaurus kentrosaurus) {
            super(kentrosaurus, 60, 4, (byte) 73, (byte) 74);
            this.kentrosaurus = kentrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && kentrosaurus.stretchCooldown == 0 && !kentrosaurus.isMobSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.kentrosaurus.stretchCooldown();
        }
    }
}
