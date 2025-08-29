package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.AgeableFollowParentGoal;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Unicorn extends Animal {

    private static final EntityDataAccessor<Boolean> SKELETAL = SynchedEntityData.defineId(Unicorn.class, EntityDataSerializers.BOOLEAN);
    private UUID lastLightningBoltUUID;

    public final AnimationState idleAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        return new GroundPathNavigation(this, level);
    }

    public Unicorn(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 16.0D).add(Attributes.MOVEMENT_SPEED, 0.16F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.25D, Ingredient.of(UP2ItemTags.UNICORN_FOOD), false));
        this.goalSelector.addGoal(4, new AgeableFollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick () {
        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }

        if (this.tickCount % 600 == 0 && this.getHealth() < this.getMaxHealth()) {
            this.heal(2);
        }
        super.tick();
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout == 0) {
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (itemstack.is(UP2ItemTags.UNICORN_FOOD) && this.getHealth() < this.getMaxHealth()) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            for (int i = 0; i < 3; i++) {
                final double d2 = this.random.nextGaussian() * 0.02D;
                final double d0 = this.random.nextGaussian() * 0.02D;
                final double d1 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemstack), this.getX() + (double) (this.random.nextFloat() * this.getBbWidth()) - (double) this.getBbWidth() * 0.5F, this.getY() + this.getBbHeight() * 0.5F + (double) (this.random.nextFloat() * this.getBbHeight() * 0.5F), this.getZ() + (double) (this.random.nextFloat() * this.getBbWidth()) - (double) this.getBbWidth() * 0.5F, d0, d1, d2);
            }
            this.playSound(SoundEvents.GOAT_EAT, 0.5F, this.getVoicePitch());
            this.gameEvent(GameEvent.EAT, this);
            if (itemstack.is(Items.CAKE)) this.heal(this.getMaxHealth());
            else this.heal((float) itemstack.getFoodProperties(this).getNutrition());
            return InteractionResult.SUCCESS;
        }
        if (itemstack.is(Items.BUCKET) && !this.isBaby()) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack result = ItemUtils.createFilledResult(itemstack, player, Items.MILK_BUCKET.getDefaultInstance());
            player.setItemInHand(hand, result);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (itemstack.is(Items.BOWL) && !this.isBaby()) {
            player.playSound(SoundEvents.MOOSHROOM_MILK, 1.0F, 1.0F);
            ItemStack result = ItemUtils.createFilledResult(itemstack, player, Items.BEETROOT_SOUP.getDefaultInstance());
            player.setItemInHand(hand, result);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        else return InteractionResult.FAIL;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob p_146744_) {
        return UP2Entities.UNICORN.get().create(serverLevel);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.UNICORN_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        if(this.isSkeletal()) {
            return SoundEvents.SKELETON_HURT;
        }
        else return UP2SoundEvents.UNICORN_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        if (this.isSkeletal()) {
            return SoundEvents.SKELETON_DEATH;
        }
        else return UP2SoundEvents.UNICORN_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(SoundEvents.WOLF_STEP, 0.25F, 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 150;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Skeletal", this.isSkeletal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSkeletal(compound.getBoolean("Skeletal"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SKELETAL, false);
    }

    public boolean isSkeletal() {
        return this.entityData.get(SKELETAL);
    }

    private void setSkeletal(boolean isSkeletal) {
        this.entityData.set(SKELETAL, isSkeletal);
    }

    public void thunderHit(ServerLevel pLevel, LightningBolt pLightning) {
        UUID uuid = pLightning.getUUID();
        if (!uuid.equals(this.lastLightningBoltUUID)) {
            this.setSkeletal((!this.isSkeletal() || this.isSkeletal()) != this.isSkeletal());
            this.lastLightningBoltUUID = uuid;
            this.playSound(SoundEvents.SKELETON_DEATH, 2.0F, 1.0F);
        }
    }
}
