package com.barlinc.unusual_prehistory.entity.mob.update_1;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SemiAquaticPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2DamageTypes;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class Dunkleosteus extends PrehistoricAquaticMob {

    private static final EntityDimensions SMALL_SIZE = EntityDimensions.scalable(0.5F, 0.5F).withEyeHeight(0.25F);
    private static final EntityDimensions MEDIUM_SIZE = EntityDimensions.scalable(1.1F, 1.1F).withEyeHeight(0.5F);
    private static final EntityDimensions LARGE_SIZE = EntityDimensions.scalable(1.9F, 2.1F).withEyeHeight(0.9F);

    public int attackCooldown = 0;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState quirkAnimationState = new SmoothAnimationState();

    public Dunkleosteus(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(false);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.ARMOR, 2.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 1.5D, 10, 4) {
            public boolean canUse() {
                return super.canUse() && Dunkleosteus.this.getVariant() == 0 || Dunkleosteus.this.isBaby();
            }
        });
        this.goalSelector.addGoal(2, new DunkleosteusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DUNKLEOSTEUS_FOOD), false));
        this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 30, 10, 7, 3, true));
        this.goalSelector.addGoal(5, new IdleAnimationGoal(this, 40, 1, false, 0.001F, this::canQuirk));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this) {
            public boolean canUse() {
                return super.canUse() && Dunkleosteus.this.getVariant() != 0;
            }
        });
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, this::isTarget));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 300, true, true, this::canAttackPlayer));
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.horizontalCollision && this.isEyeInFluid(FluidTags.WATER) && this.isPathFinding()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.005, 0.0));
            }
        } else {
            super.travel(travelVector);
        }
    }

    protected void switchNavigator(boolean inShallows) {
        this.navigation.stop();
        if (inShallows) {
            this.navigation = new SemiAquaticPathNavigation(this, this.level());
            this.shallowWater = true;
        } else {
            this.navigation = this.createNavigation(this.level());
            this.shallowWater = false;
        }
    }

    public double getChaseSpeed() {
        switch (this.getVariant()) {
            case 1 -> {
                return 1.5D;
            }
            case 2 -> {
                return 1.6D;
            }
            default -> {
                return 1.4D;
            }
        }
    }

//    @Override
//    public boolean shouldShowName() {
//        return true;
//    }
//
//    @Override
//    public Component getDisplayName() {
//        return Component.literal("shallowWater " + this.shallowWater);
//    }

    @Override
    public void tick() {
        super.tick();
        final boolean shallowWater = this.isInShallowWater();
        if (shallowWater && !this.shallowWater) {
            this.switchNavigator(true);
        } else if (!shallowWater && this.shallowWater) {
            this.switchNavigator(false);
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackCooldown > 0) attackCooldown--;
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
        this.quirkAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
    }

    private boolean canQuirk(Entity entity) {
        return entity.isInWaterOrBubble();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 800 + this.getRandom().nextInt(1200);
        } else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (VARIANT.equals(accessor)) {
            this.refreshDimensions();
            this.setupSizeAttributes();
        }
        super.onSyncedDataUpdated(accessor);
    }

    private void setupSizeAttributes() {
        if (this.getVariant() == 0) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(4.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.9F);
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
        }
        if (this.getVariant() == 1) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(24.0D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(6.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.8F);
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.25D);
        }
        if (this.getVariant() == 2) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40.0D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(10.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.6F);
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
        }
        this.heal(50.0F);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DUNKLEOSTEUS_FOOD);
    }

    public boolean isTarget(Entity entity) {
        if (this.getVariant() == 1) return entity.getType().is(UP2EntityTags.MEDIUM_DUNKLEOSTEUS_TARGETS);
        else if (this.getVariant() == 2) return entity.getType().is(UP2EntityTags.BIG_DUNKLEOSTEUS_TARGETS);
        else return entity.getType().is(UP2EntityTags.SMALL_DUNKLEOSTEUS_TARGETS);
    }

    public boolean canAttackPlayer(LivingEntity entity) {
        return this.canAttack(entity) && this.getVariant() > 0;
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if (prev && !this.isPacified() && entity instanceof LivingEntity && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().getUUID().equals(entity.getUUID()))) return false;
        else return prev;
    }

    public void doKnockback(LivingEntity target) {
        if (this.getVariant() == 0) this.strongKnockback(target, 0.1D, 0.01D);
        else if (this.getVariant() == 1) this.strongKnockback(target, 0.2D, 0.01D);
        else this.strongKnockback(target, 0.3D, 0.01D);
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.DUNKLEOSTEUS_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.DUNKLEOSTEUS_DEATH.get();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.DUNKLEOSTEUS_FLOP.get();
    }

    public SoundEvent getBiteSound() {
        if (this.getVariant() == 0) return UP2SoundEvents.SMALL_DUNKLEOSTEUS_BITE.get();
        else if (this.getVariant() == 1) return UP2SoundEvents.MEDIUM_DUNKLEOSTEUS_BITE.get();
        else return UP2SoundEvents.LARGE_DUNKLEOSTEUS_BITE.get();
    }

    @Override
    public float getVoicePitch() {
        final float size = (3 - this.getVariant()) * 0.33F;
        return (float) (super.getVoicePitch() * Math.sqrt(size) * 1.2F);
    }

    @Override
    public boolean isPushable() {
        return super.isPushable() && this.getVariant() != 2;
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        return this.getDimsForDunk().scale(this.getAgeScale());
    }

    private EntityDimensions getDimsForDunk() {
        return switch (this.getVariant()) {
            case 1 -> MEDIUM_SIZE;
            case 2 -> LARGE_SIZE;
            default -> SMALL_SIZE;
        };
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_DUNKLEOSTEUS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        Dunkleosteus dunkleosteus = UP2Entities.DUNKLEOSTEUS.get().create(serverLevel);
        if (dunkleosteus != null) {
            dunkleosteus.setVariant(this.getVariant());
        }
        return dunkleosteus;
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.DUNKLEOSTEUS_BUCKET.get());
    }

    @Override
    public boolean canBucket() {
        return true;
    }

    public enum DunkleosteusVariant {
        SMALL(0),
        MEDIUM(1),
        LARGE(2);

        private final int id;

        DunkleosteusVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static DunkleosteusVariant byId(int id) {
            if (id < 0 || id >= DunkleosteusVariant.values().length) {
                id = 0;
            }
            return DunkleosteusVariant.values()[id];
        }
    }

    @Override
    public int getVariantCount() {
        return DunkleosteusVariant.values().length;
    }

    private int getWaterDepthAbove(LevelReader level, BlockPos pos) {
        int depth = 0;
        BlockPos.MutableBlockPos checkPos = pos.mutable();
        while (level.getFluidState(checkPos).is(FluidTags.WATER)) {
            depth++;
            checkPos.move(0, 1, 0);
            if (depth > 20) break;
        }
        return depth;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        if (spawnType == MobSpawnType.BUCKET) {
            return spawnGroupData;
        } else {
            int depth = this.getWaterDepthAbove(level, this.blockPosition());
            if (level.getFluidState(this.blockPosition()).is(FluidTags.WATER)) {
                if (depth > 10) {
                    this.setVariant(2);
                } else if (depth > 5) {
                    this.setVariant(1);
                } else {
                    this.setVariant(0);
                }
            }
            else {
                this.setVariant(this.getRandom().nextInt(DunkleosteusVariant.values().length));
            }
        }
        return spawnGroupData;
    }

    // goals
    private static class DunkleosteusAttackGoal extends AttackGoal {

        private final Dunkleosteus dunkleosteus;

        public DunkleosteusAttackGoal(Dunkleosteus dunkleosteus) {
            super(dunkleosteus);
            this.dunkleosteus = dunkleosteus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && (dunkleosteus.getTarget().isInWaterOrBubble() || !dunkleosteus.isInWaterOrBubble());
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && (dunkleosteus.getTarget().isInWaterOrBubble() || !dunkleosteus.isInWaterOrBubble());
        }

        @Override
        public void tick() {
            LivingEntity target = this.dunkleosteus.getTarget();
            if (target != null) {
                this.dunkleosteus.lookAt(target, 30F, 30F);
                this.dunkleosteus.getLookControl().setLookAt(target, 30F, 30F);
                double distance = this.dunkleosteus.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int attackState = this.dunkleosteus.getAttackState();
                this.dunkleosteus.getNavigation().moveTo(target, dunkleosteus.getChaseSpeed());

                if (attackState == 1) {
                    this.tickBite();
                } else if (distance < this.getAttackReachSqr(target) && dunkleosteus.attackCooldown == 0) {
                    this.dunkleosteus.setAttackState(1);
                }
            }
        }

        protected void tickBite() {
            timer++;
            LivingEntity target = dunkleosteus.getTarget();
            if (timer == 1) dunkleosteus.setPose(UP2Poses.ATTACKING.get());
            if (timer == 2) dunkleosteus.playSound(dunkleosteus.getBiteSound(), 1.0F, dunkleosteus.getRandom().nextFloat() * 0.1F + 0.9F);
            if (timer == 5) {
                if (this.isInAttackRange(target, 1.5D)) {
                    DamageSource damagesource = UP2DamageTypes.execute(dunkleosteus.level(), dunkleosteus, dunkleosteus);
                    target.hurt(damagesource, (float) dunkleosteus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    this.dunkleosteus.doKnockback(target);
                    this.dunkleosteus.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 10) {
                this.timer = 0;
                this.dunkleosteus.setPose(Pose.STANDING);
                this.dunkleosteus.attackCooldown = 5 + dunkleosteus.getRandom().nextInt(3);
                this.dunkleosteus.setAttackState(0);
            }
        }
    }
}
