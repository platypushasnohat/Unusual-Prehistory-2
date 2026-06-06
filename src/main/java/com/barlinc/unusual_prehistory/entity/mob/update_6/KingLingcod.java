package com.barlinc.unusual_prehistory.entity.mob.update_6;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.SchoolingAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.MobUtils;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class KingLingcod extends SchoolingAquaticMob implements Bucketable {

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();

    public KingLingcod(EntityType<? extends SchoolingAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.STETHACANTHUS_FOOD), false));
        this.goalSelector.addGoal(4, new KingLingcodAttackGoal(this));
        this.goalSelector.addGoal(5, new CustomizableRandomSwimGoal(this, 1.0D, 150));
        this.goalSelector.addGoal(6, new FollowVariantLeaderGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.STETHACANTHUS_TARGETS)));
    }

    @Override
    public int getMaxSchoolSize() {
        return 2;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.STETHACANTHUS_FOOD);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return MobUtils.getDepthPathfindingFavor(pos, level);
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && this.getPose() != UP2Poses.ATTACKING.get(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
    }

    @Override
    public boolean fromBucket() {
        return false;
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.STETHACANTHUS_BUCKET.get());
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    @Override
    public void saveToBucketTag(@NotNull ItemStack bucket) {
        MobUtils.savePrehistoricDataToBucket(this, bucket);
    }

    @Override
    public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
        MobUtils.loadPrehistoricDataFromBucket(this, compoundTag);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.STETHACANTHUS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.STETHACANTHUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.STETHACANTHUS_FLOP.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.KING_LINGCOD.get().create(serverLevel);
    }

    private static class KingLingcodAttackGoal extends AttackGoal {

        private final KingLingcod kingLingcod;

        public KingLingcodAttackGoal(KingLingcod kingLingcod) {
            super(kingLingcod);
            this.kingLingcod = kingLingcod;
        }

        @Override
        public void tick() {
            LivingEntity target = kingLingcod.getTarget();
            if (target != null && target.isInWater()) {
                this.kingLingcod.lookAt(target, 30.0F, 30.0F);
                this.kingLingcod.getLookControl().setLookAt(target, 30.0F, 30.0F);
                double distance = kingLingcod.distanceToSqr(target.getX(), target.getY(), target.getZ());

                if (kingLingcod.getAttackState() == 1) {
                    this.kingLingcod.getNavigation().stop();
                    this.tickAttack(target);
                } else {
                    if (distance <= 4) {
                        this.kingLingcod.setAttackState(1);
                    }
                    this.kingLingcod.getNavigation().moveTo(target, 1.5D);
                }
            }
        }

        protected void tickAttack(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.kingLingcod.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 5) {
                this.kingLingcod.playSound(UP2SoundEvents.STETHACANTHUS_BITE.get(), 0.7F, kingLingcod.getVoicePitch());
            }
            if (timer == 6) {
                if (this.isInAttackRange(target, 1.5D)) {
                    this.kingLingcod.doHurtTarget(target);
                    this.kingLingcod.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 20) {
                this.timer = 0;
                this.kingLingcod.setPose(Pose.STANDING);
                this.kingLingcod.setAttackState(0);
            }
        }
    }
}