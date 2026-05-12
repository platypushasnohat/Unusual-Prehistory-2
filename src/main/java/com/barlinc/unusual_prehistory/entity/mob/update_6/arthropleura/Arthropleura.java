package com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Arthropleura extends PrehistoricMob {

    private static final EntityDataAccessor<Optional<UUID>> CHILD_UUID = SynchedEntityData.defineId(Arthropleura.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> CHILD_ID = SynchedEntityData.defineId(Arthropleura.class, EntityDataSerializers.INT);

    public final float[] yawBuffer = new float[64];
    public int yawPointer = -1;
    private ArthropleuraPart[] parts;

    private int lSteps;
    private double lx;
    private double ly;
    private double lz;
    private double lyr;
    private double lxr;
    private double lxd;
    private double lyd;
    private double lzd;

    public Arthropleura(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.STEP_HEIGHT, 1.1D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
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
    public void pushEntities() {
        final List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expandTowards(0.2D, 0.0D, 0.2D));
        entities.stream().filter(entity -> !(entity instanceof ArthropleuraPart) && entity.isPushable()).forEach(entity -> entity.push(this));
    }

    @Override
    public int getMaxHeadXRot() {
        return 1;
    }

    @Override
    public int getMaxHeadYRot() {
        return 3;
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.RHIZODUS_FOOD);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.IN_WALL)  || super.isInvulnerableTo(source);
    }

    @Override
    public void lerpTo(double x, double y, double z, float yr, float xr, int steps) {
        this.lx = x;
        this.ly = y;
        this.lz = z;
        this.lyr = yr;
        this.lxr = xr;
        this.lSteps = steps;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    @Override
    public void lerpMotion(double lerpX, double lerpY, double lerpZ) {
        this.lxd = lerpX;
        this.lyd = lerpY;
        this.lzd = lerpZ;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    private float getYawForPart(int i) {
        return this.getYawBuffer(4 + i * 2, 1.0F);
    }

    public float getYawBuffer(int bufferOffset, float partialTicks) {
        if (this.isDeadOrDying()) {
            partialTicks = 0.0F;
        }
        partialTicks = 1.0F - partialTicks;
        final int i = this.yawPointer - bufferOffset & 63;
        final int j = this.yawPointer - bufferOffset - 1 & 63;
        final float d0 = this.yawBuffer[i];
        final float d1 = this.yawBuffer[j] - d0;
        return Mth.wrapDegrees(d0 + d1 * partialTicks);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.yawPointer < 0) {
            for (int i = 0; i < this.yawBuffer.length; ++i) {
                this.yawBuffer[i] = this.getYRot();
            }
        }
        this.yawPointer++;
        if (this.yawPointer == this.yawBuffer.length) {
            this.yawPointer = 0;
        }
        this.yawBuffer[this.yawPointer] = this.getYRot();

        this.yHeadRot = Mth.wrapDegrees(this.getYRot());

        if (this.level().isClientSide) {
            if (this.lSteps > 0) {
                double d5 = this.getX() + (this.lx - this.getX()) / (double) this.lSteps;
                double d6 = this.getY() + (this.ly - this.getY()) / (double) this.lSteps;
                double d7 = this.getZ() + (this.lz - this.getZ()) / (double) this.lSteps;
                this.setYRot(Mth.wrapDegrees((float) this.lyr));
                this.setXRot(this.getXRot() + (float) (this.lxr - (double) this.getXRot()) / (float) this.lSteps);
                this.lSteps--;
                this.setPos(d5, d6, d7);
            } else {
                this.reapplyPosition();
            }
        } else {
            this.tickParts();
        }
    }

    private boolean shouldReplaceParts() {
        if (parts == null || parts[0] == null) {
            return true;
        }
        for (int i = 0; i < 7; i++) {
            if (parts[i] == null) {
                return true;
            }
        }
        return false;
    }

    private void tickParts() {
        final int segments = 7;
        final Entity child = this.getChild();
        if (child == null) {
            Entity partParent = this;
            this.parts = new ArthropleuraPart[segments];
            Vec3 prevPos = this.position();
            ArthropleuraPartIndex partIndex = ArthropleuraPartIndex.HEAD;
            for (int i = 0; i < segments; i++) {
                ArthropleuraPart part = new ArthropleuraPart(UP2Entities.ARTHROPLEURA_PART.get(), this);
                part.setParent(partParent);
                part.setIndex(i);
                part.setPartType(ArthropleuraPartIndex.sizeAt(1 + i));
                if (partParent == this) {
                    this.setChildId(part.getUUID());
                    this.entityData.set(CHILD_ID, part.getId());
                }
                if (partParent instanceof ArthropleuraPart arthropleuraPart) {
                    arthropleuraPart.setChildId(part.getUUID());
                }
                part.setPos(part.tickMultipartPosition(partIndex, prevPos, this.getXRot(), this.getYawForPart(i), false));
                partParent = part;
                level().addFreshEntity(part);
                this.parts[i] = part;
                partIndex = part.getPartType();
                prevPos = part.position();
            }
        }
        if (this.shouldReplaceParts() && this.getChild() instanceof ArthropleuraPart) {
            this.parts = new ArthropleuraPart[segments];
            this.parts[0] = (ArthropleuraPart) this.getChild();
            this.entityData.set(CHILD_ID, parts[0].getId());
            int i = 1;
            while (i < parts.length && parts[i - 1].getChild() instanceof ArthropleuraPart) {
                this.parts[i] = (ArthropleuraPart) parts[i - 1].getChild();
                i++;
            }
        }
        Vec3 prev = this.position();
        float xRot = this.getXRot();
        ArthropleuraPartIndex partIndex = ArthropleuraPartIndex.HEAD;
        for (int i = 0; i < segments; i++) {
            if (this.parts[i] != null) {
                prev = parts[i].tickMultipartPosition(partIndex, prev, xRot, this.getYawForPart(i), true);
                xRot = parts[i].getXRot();
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CHILD_UUID, Optional.empty());
        builder.define(CHILD_ID, -1);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.hasUUID("ChildUUID")) {
            this.setChildId(compoundTag.getUUID("ChildUUID"));
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.getChildId() != null) {
            compoundTag.putUUID("ChildUUID", this.getChildId());
        }
    }

    @Nullable
    public UUID getChildId() {
        return this.entityData.get(CHILD_UUID).orElse(null);
    }
    public void setChildId(@Nullable UUID uniqueId) {
        this.entityData.set(CHILD_UUID, Optional.ofNullable(uniqueId));
    }
    public Entity getChild() {
        UUID id = getChildId();
        if (id != null && !level().isClientSide) {
            return ((ServerLevel) level()).getEntity(id);
        }
        return null;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.ARTHROPLEURA.get().create(level);
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }
}