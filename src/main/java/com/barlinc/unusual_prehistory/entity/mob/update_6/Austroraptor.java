package com.barlinc.unusual_prehistory.entity.mob.update_6;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Austroraptor extends PrehistoricMob {

    private static final EntityDataAccessor<Integer> SHEARED_TICKS = SynchedEntityData.defineId(Austroraptor.class, EntityDataSerializers.INT);

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState fallAnimationState = new SmoothAnimationState();

    public Austroraptor(EntityType<? extends Austroraptor> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new AustroraptorAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new PrehistoricRandomStrollGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new SleepingGoal(this));
        this.targetSelector.addGoal(0, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, false, entity -> entity.getType().is(UP2EntityTags.DROMAEOSAURUS_TARGETS)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.STEP_HEIGHT, 1.2D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        Vec3 deltaMovement = this.getDeltaMovement();
        if (!this.onGround() && !this.isInWaterOrBubble() && deltaMovement.y < 0.0D) {
            this.setDeltaMovement(deltaMovement.multiply(1.0D, 0.8D, 1.0D));
            if (this.getMovementEmission().emitsEvents()) {
                this.gameEvent(GameEvent.FLAP);
            }
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (this.getShearedTicks() > 0) {
            this.setShearedTicks(this.getShearedTicks() - 1);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy(), tickCount);
        this.fallAnimationState.animateWhen(!this.onGround() && !this.isInWaterOrBubble() && !this.onClimbable() && !this.isPassenger() && !this.isEepy(), tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), tickCount);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.AUSTRORAPTOR.get().create(level);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, -this.getBbHeight() * 0.35F, this.getBbWidth() * 1.3F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.refuseToMove()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVector = travelVector.multiply(0.0D, 1.0D, 0.0D);
        }
        super.travel(travelVector);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (this.getShearedTicks() <= 0 && itemStack.is(Tags.Items.TOOLS_SHEAR)) {
            this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
            this.playSound(UP2SoundEvents.AUSTRORAPTOR_SCREAM.get(), 2.0F, this.getVoicePitch());
            this.gameEvent(GameEvent.SHEAR, player);
//            if (!this.level().isClientSide) {
//                LootTable loottable = Objects.requireNonNull(this.level().getServer()).reloadableRegistries().getLootTable(this.getLootTable());
//                List<ItemStack> items = loottable.getRandomItems((new LootParams.Builder((ServerLevel) this.level())).withParameter(LootContextParams.THIS_ENTITY, this).create(LootContextParamSets.PIGLIN_BARTER));
//                items.forEach(this::spawnAtLocation);
//            }
            this.setShearedTicks(24000 + this.getRandom().nextInt(24000));
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHEARED_TICKS, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("ShearedTicks", this.getShearedTicks());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setShearedTicks(compoundTag.getInt("ShearedTicks"));
    }

    public int getShearedTicks() {
        return entityData.get(SHEARED_TICKS);
    }
    public void setShearedTicks(int i) {
        this.entityData.set(SHEARED_TICKS, i);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.getShearedTicks() > 0 ? UP2SoundEvents.AUSTRORAPTOR_HISS.get() : UP2SoundEvents.AUSTRORAPTOR_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.AUSTRORAPTOR_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.AUSTRORAPTOR_DEATH.get();
    }

    // Goals
    private static class AustroraptorAttackGoal extends AttackGoal {

        protected final Austroraptor austroraptor;

        public AustroraptorAttackGoal(Austroraptor austroraptor) {
            super(austroraptor);
            this.austroraptor = austroraptor;
        }

        @Override
        public void tick() {
            LivingEntity target = this.austroraptor.getTarget();
            if (target != null) {
                double distance = this.austroraptor.distanceToSqr(target);
                this.austroraptor.getLookControl().setLookAt(target, 30F, 30F);
                this.austroraptor.getNavigation().moveTo(target, 1.0D);
                if (this.austroraptor.getAttackState() == 1) {
                    this.tickAttack(target);
                } else if (distance <= this.getAttackReachSqr(target)) {
                    this.austroraptor.setAttackState(1);
                }
            }
        }

        private void tickAttack(LivingEntity target) {
            this.timer++;
            if (timer == 1) austroraptor.setPose(UP2Poses.ATTACKING.get());
            if (timer == 6) {
                if (this.isInAttackRange(target, 1.5D)) {
                    this.austroraptor.doHurtTarget(target);
                    this.austroraptor.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 15) {
                this.timer = 0;
                this.austroraptor.setPose(Pose.STANDING);
                this.austroraptor.setAttackState(0);
            }
        }
    }
}
