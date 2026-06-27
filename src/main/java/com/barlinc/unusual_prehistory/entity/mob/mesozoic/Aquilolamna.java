package com.barlinc.unusual_prehistory.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Aquilolamna extends PrehistoricAquaticMob implements Bucketable {

    private float roll = 0.0F;
    private float prevRoll = 0.0F;

    public Aquilolamna(EntityType<? extends Aquilolamna> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 20, 6, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 6);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.7F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 1.8D, 16, 8));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(3, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 8.0F,1.8D, entity -> entity.getType().is(UP2EntityTags.STETHACANTHUS_AVOIDS)));
        this.goalSelector.addGoal(4, new PrehistoricSwimGoal(this, 1.0D, 20, 16, 8));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    public float getRoll(float partialTicks) {
        return (prevRoll + (roll - prevRoll) * partialTicks);
    }

    @Override
    public void tick() {
        super.tick();

        this.prevRoll = roll;
        float threshold = 5.0F;
        boolean flag = false;
        if (this.isInWater() && yRotO - this.getYRot() > threshold) {
            this.roll += 1.5F;
            flag = true;
        }
        if (this.isInWater() && yRotO - this.getYRot() < -threshold) {
            this.roll -= 1.5F;
            flag = true;
        }
        if (!flag) {
            if (roll > 0.0F) {
                this.roll = Math.max(roll - 1.5F, 0.0F);
            }
            if (roll < 0.0F) {
                this.roll = Math.min(roll + 1.5F, 0.0F);
            }
        }
        this.roll = Mth.clamp(roll, -35.0F, 35.0F);
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float) Mth.length(this.getX() - xo, this.isInWater() ? this.getY() - yo : 0.0, this.getZ() - zo);
        if (this.isBaby()) {
            this.updateWalkAnimation(f * 0.5F);
        } else {
            this.updateWalkAnimation(f);
        }
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f = Math.min(partialTick * 25.0F, 1.0F);
        this.walkAnimation.update(f, 0.4F);
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
        UP2MobUtils.savePrehistoricDataToBucket(this, bucket);
    }

    @Override
    public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
        UP2MobUtils.loadPrehistoricDataFromBucket(this, compoundTag);
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
        return UP2Entities.AQUILOLAMNA.get().create(serverLevel);
    }
}