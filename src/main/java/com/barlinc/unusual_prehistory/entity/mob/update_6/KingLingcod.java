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
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
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
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class KingLingcod extends SchoolingAquaticMob implements Bucketable {

    protected static final EntityDataAccessor<Optional<UUID>> BONDED_WITH_UUID = SynchedEntityData.defineId(KingLingcod.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> BONDED_WITH_ID = SynchedEntityData.defineId(KingLingcod.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(KingLingcod.class, EntityDataSerializers.INT);

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();

    private final byte EAT = 67;

    public KingLingcod(EntityType<? extends SchoolingAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
        this.switchNavigator(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.75F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PrehistoricSitWhenOrderedToGoal(this, false));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new PrehistoricFollowOwnerGoal(this, 1.2D, 1.5D, 6.0F, 3.0F));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TEMPTS_KING_LINGCOD), false));
        this.goalSelector.addGoal(4, new KingLingcodAttackGoal(this));
        this.goalSelector.addGoal(5, new CustomizableRandomSwimGoal(this, 1.0D, 150));
        this.goalSelector.addGoal(6, new FollowVariantLeaderGoal(this) {
            @Override
            public boolean canUse() {
                return KingLingcod.this.getBondedEntity() != null && super.canUse();
            }
        });
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, LivingEntity.class, 8.0F));
        this.targetSelector.addGoal(0, new BondedWithHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new BondedWithHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new PrehistoricOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new PrehistoricOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(5, new PrehistoricNearestAttackableTargetGoal<>(this, KingLingcod.class, 100, true, true, this::canAttackOthers));
        this.targetSelector.addGoal(6, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, this::canHunt));
        this.targetSelector.addGoal(7, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 300, true, true, this::canAttack));
    }

    @Override
    public int getMaxSchoolSize() {
        return 2;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.TEMPTS_KING_LINGCOD);
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            if (this.onGround()) {
                travelVec = travelVec.multiply(0.0, 1.0, 0.0);
            } else if (this.isInWaterOrBubble()) {
                travelVec = travelVec.multiply(0.0, 0.0, 0.0);
            }
        }
        if (this.isEffectiveAi() && this.isInWater()) {
            MobUtils.travelInWater(this, travelVec);
        } else {
            super.travel(travelVec);
        }
    }

    @Override
    protected boolean shouldUseShallowNavigation() {
        return true;
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return MobUtils.getDepthPathfindingFavor(pos, level);
    }

    private boolean canAttackOthers(LivingEntity target) {
        if (!this.canAttack(target)) {
            return false;
        }
        return target instanceof KingLingcod kingLingcod && this.getBondedEntity() != kingLingcod;
    }

    private boolean canHunt(LivingEntity target) {
        if (!this.canAttack(target)) {
            return false;
        }
        return target.getBbWidth() < this.getBbWidth() * 1.1F && target.getBbHeight() < this.getBbHeight() * 1.1F;
    }

    @Override
    public void addFollowers(Stream<? extends SchoolingAquaticMob> entity) {
        entity.limit(this.getMaxSchoolSize() - this.schoolSize).filter((entity1) -> entity1 != this).forEach((entity2) -> {
            if (!this.isBaby() && this.getBondedEntity() == entity2 && !this.isSitting() && !entity2.isSitting()) {
                entity2.startFollowing(this);
            }
        });
    }

    @Override
    public boolean isAlliedTo(@NotNull Entity entity) {
        Entity bondedEntity = this.getBondedEntity();
        if (entity == bondedEntity) {
            return true;
        }
        return super.isAlliedTo(entity);
    }

    @Override
    public boolean canOwnerCommand(Player player, @NotNull InteractionHand hand) {
        return true;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!this.isTame() && this.getEatTicks() <= 0 && itemStack.is(UP2ItemTags.TAMES_KING_LINGCOD) ) {
            this.setEatTicks(25);
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                this.level().broadcastEntityEvent(this, EAT);
                this.playSound(this.getEatingSound(), 1.0F, this.getVoicePitch());
                if (this.getNavigation().getPath() != null) {
                    this.getNavigation().stop();
                }
                if (this.getTameAttempts() > 3 && this.getRandom().nextBoolean()) {
                    this.level().broadcastEntityEvent(this, (byte) 7);
                    this.tame(player);
                    this.setTameAttempts(0);
                    this.heal(this.getMaxHealth());
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                    this.setTameAttempts(this.getTameAttempts() + 1);
                }
            } else {
                this.spawnEatingParticles(itemStack);
            }
            return InteractionResult.SUCCESS;
        }
        if (this.getEatTicks() > 0 && this.isFood(itemStack)) {
            return InteractionResult.PASS;
        }
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    private void spawnEatingParticles(ItemStack itemStack) {
        for (int i = 0; i < 8; i++) {
            Vec3 eatPos = this.getEyePosition().add(this.getViewVector(0.0F).add(0, -0.5F, -this.getBbWidth() * 0.1F));
            Vec3 vec3 = (new Vec3((this.getRandom().nextFloat() - 0.5F) * 0.1F, this.getRandom().nextFloat() * 0.1F + 0.1F, 0.0F)).yRot(-this.getYRot() * ((float) Math.PI / 180F));
            this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemStack), eatPos.x, eatPos.y, eatPos.z, vec3.x, vec3.y + 0.05, vec3.z);
        }
    }

    @Override
    public void tick() {
        super.tick();

        Entity bondedWith = this.getBondedEntity();
        if (!this.level().isClientSide) {
            this.entityData.set(BONDED_WITH_ID, bondedWith != null ? bondedWith.getId() : -1);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && this.getPose() != UP2Poses.ATTACKING.get(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EAT) {
            this.eatAnimationState.start(this.tickCount);
        }
        else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BONDED_WITH_UUID, Optional.empty());
        builder.define(BONDED_WITH_ID, -1);
        builder.define(TAME_ATTEMPTS, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.getBondedWithUUID() != null) {
            compoundTag.putUUID("BondedWithUUID", this.getBondedWithUUID());
        }
        compoundTag.putInt("TameAttempts", this.getTameAttempts());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.hasUUID("BondedWithUUID")) {
            this.setBondedWithUUID(compoundTag.getUUID("BondedWithUUID"));
        }
        this.setTameAttempts(compoundTag.getInt("TameAttempts"));
    }

    @Nullable
    public UUID getBondedWithUUID() {
        return this.entityData.get(BONDED_WITH_UUID).orElse(null);
    }
    public void setBondedWithUUID(@Nullable UUID uuid) {
        this.entityData.set(BONDED_WITH_UUID, Optional.ofNullable(uuid));
    }

    public Entity getBondedEntity() {
        if (!this.level().isClientSide) {
            UUID uuid = this.getBondedWithUUID();
            return uuid == null ? null : ((ServerLevel) this.level()).getEntity(uuid);
        } else {
            int id = this.entityData.get(BONDED_WITH_ID);
            return id == -1 ? null : this.level().getEntity(id);
        }
    }

    public void setTameAttempts(int tameAttempts) {
        this.entityData.set(TAME_ATTEMPTS, tameAttempts);
    }
    public int getTameAttempts() {
        return this.entityData.get(TAME_ATTEMPTS);
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
        KingLingcod kingLingcod = UP2Entities.KING_LINGCOD.get().create(serverLevel);
        if (kingLingcod != null && this.getBondedEntity() == null && kingLingcod.getBondedEntity() == null) {
            this.setBondedWithUUID(kingLingcod.getUUID());
            kingLingcod.setBondedWithUUID(this.getUUID());
        }
        return kingLingcod;
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

    private static class BondedWithHurtTargetGoal extends TargetGoal {

        protected final KingLingcod kingLingcod;
        private LivingEntity bondedLastHurt;
        private int timestamp;

        public BondedWithHurtTargetGoal(KingLingcod kingLingcod) {
            super(kingLingcod, false);
            this.kingLingcod = kingLingcod;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        @Override
        public boolean canUse() {
            if (kingLingcod.getBondedEntity() != null && !kingLingcod.isOrderedToSit()) {
                LivingEntity livingentity = (LivingEntity) kingLingcod.getBondedEntity();
                if (livingentity == null) {
                    return false;
                } else {
                    this.bondedLastHurt = livingentity.getLastHurtMob();
                    int i = livingentity.getLastHurtMobTimestamp();
                    return i != timestamp && this.canAttack(bondedLastHurt, TargetingConditions.DEFAULT) && kingLingcod.wantsToAttack(bondedLastHurt, livingentity);
                }
            } else {
                return false;
            }
        }

        @Override
        public void start() {
            this.kingLingcod.setTarget(bondedLastHurt);
            LivingEntity livingentity = (LivingEntity) kingLingcod.getBondedEntity();
            if (livingentity != null) {
                this.timestamp = livingentity.getLastHurtMobTimestamp();
            }
            super.start();
        }
    }

    private static class BondedWithHurtByTargetGoal extends TargetGoal {

        protected final KingLingcod kingLingcod;
        protected LivingEntity bondedLastHurtBy;
        private int timestamp;

        public BondedWithHurtByTargetGoal(KingLingcod kingLingcod) {
            super(kingLingcod, false);
            this.kingLingcod = kingLingcod;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        @Override
        public boolean canUse() {
            if (kingLingcod.getBondedEntity() != null && !kingLingcod.isOrderedToSit()) {
                LivingEntity livingentity = (LivingEntity) kingLingcod.getBondedEntity();
                if (livingentity == null) {
                    return false;
                } else {
                    this.bondedLastHurtBy = livingentity.getLastHurtByMob();
                    int i = livingentity.getLastHurtByMobTimestamp();
                    return i != timestamp && this.canAttack(bondedLastHurtBy, TargetingConditions.DEFAULT) && kingLingcod.wantsToAttack(bondedLastHurtBy, livingentity);
                }
            } else {
                return false;
            }
        }

        @Override
        public void start() {
            this.kingLingcod.setTarget(bondedLastHurtBy);
            LivingEntity livingentity = (LivingEntity) kingLingcod.getBondedEntity();
            if (livingentity != null) {
                this.timestamp = livingentity.getLastHurtByMobTimestamp();
            }
            super.start();
        }
    }
}