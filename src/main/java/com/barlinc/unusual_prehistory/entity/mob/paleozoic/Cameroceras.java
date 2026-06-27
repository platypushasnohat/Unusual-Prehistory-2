package com.barlinc.unusual_prehistory.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricSwimGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Cameroceras extends PrehistoricAquaticMob {

    private static final EntityDataAccessor<Boolean> CRAWLING = SynchedEntityData.defineId(Cameroceras.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> CRAWLING_COOLDOWN = SynchedEntityData.defineId(Cameroceras.class, EntityDataSerializers.INT);

    private int attackCooldown = 0;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState eyeAnimationState = new SmoothAnimationState();

    public Cameroceras(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(false);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.44F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 20);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1F)
                .add(Attributes.ATTACK_DAMAGE, 6.0F)
                .add(Attributes.ARMOR, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.STEP_HEIGHT, 1.2D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new CamerocerasCrawlAroundGoal(this));
        this.goalSelector.addGoal(4, new CamerocerasSwimGoal(this));
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9F));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean isPushable() {
        return this.isBaby();
    }

    @Override
    public boolean shouldFlop() {
        return false;
    }

    @Override
    protected boolean shouldUseShallowNavigation() {
        return true;
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove();
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.isInWaterOrBubble()) {
                if (this.getCrawlingCooldown() > 0) {
                    this.setCrawlingCooldown(this.getCrawlingCooldown() - 1);
                }
                if (this.getCrawlingCooldown() == 0 && this.getRandom().nextFloat() < 0.02F) {
                    boolean currentlyWalking = this.isCrawling();
                    this.setCrawling(!currentlyWalking);
                    this.setCrawlingCooldown(this.getRandom().nextInt(800) + 800);
                    this.getNavigation().stop();
                }
                if (this.isCrawling() && this.getCrawlingCooldown() > 20) {
                    this.addDeltaMovement(new Vec3(0.0F, -0.04, 0.0F));
                } else {
                    this.addDeltaMovement(new Vec3(0.0F, 0.0015, 0.0F));
                }
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), tickCount);
        this.eyeAnimationState.animateWhen(this.isInWaterOrBubble(), tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float pos = (float) Mth.length(this.getX() - xo, this.isCrawling() ? 0 : this.getY() - yo, this.getZ() - zo);
        float speed = Math.min(pos * 20.0F, 1.0F);
        if (this.isBaby()) {
            this.walkAnimation.update(speed * 0.5F, 0.4F);
        } else {
            this.walkAnimation.update(speed, 0.4F);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CRAWLING, false);
        builder.define(CRAWLING_COOLDOWN, 800 + this.getRandom().nextInt(800));
    }

    public boolean isCrawling() {
        return entityData.get(CRAWLING);
    }
    public void setCrawling(boolean crawling) {
        this.entityData.set(CRAWLING, crawling);
    }

    public int getCrawlingCooldown() {
        return entityData.get(CRAWLING_COOLDOWN);
    }
    public void setCrawlingCooldown(int cooldown) {
        this.entityData.set(CRAWLING_COOLDOWN, cooldown);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.CAMEROCERAS.get().create(level);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.TUSOTEUTHIS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.TUSOTEUTHIS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.TUSOTEUTHIS_DEATH.get();
    }

    private static class CamerocerasSwimGoal extends PrehistoricSwimGoal {

        protected final Cameroceras cameroceras;

        public CamerocerasSwimGoal(Cameroceras cameroceras) {
            super(cameroceras, 1.0D, 100, 15, 12);
            this.cameroceras = cameroceras;
        }

        @Override
        public boolean canUse() {
            return cameroceras.isInWaterOrBubble() && !cameroceras.onGround() && !cameroceras.isCrawling() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return cameroceras.isInWaterOrBubble() && !cameroceras.onGround() && super.canContinueToUse();
        }
    }

    private static class CamerocerasCrawlAroundGoal extends RandomStrollGoal {

        private final Cameroceras cameroceras;

        public CamerocerasCrawlAroundGoal(Cameroceras cameroceras) {
            super(cameroceras, 1.0D);
            this.cameroceras = cameroceras;
        }

        @Override
        public boolean canUse() {
            return cameroceras.isInWaterOrBubble() && cameroceras.onGround() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return cameroceras.isInWaterOrBubble() && cameroceras.onGround() && super.canContinueToUse();
        }
    }
}
