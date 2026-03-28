package com.barlinc.unusual_prehistory.entity.mob.future;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Wonambi extends PrehistoricMob {

    public final WonambiPart body1Part;
    public final WonambiPart body2Part;
    public final WonambiPart body3Part;
    public final WonambiPart tail1Part;
    public final WonambiPart tail2Part;
    public final WonambiPart tail3Part;
    private final WonambiPart[] allParts;
    private float fakeYRot;
    private float[] yawBuffer = new float[128];
    private int yawPointer = -1;

    public Wonambi(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.body1Part = new WonambiPart(this, this, 0.9F, 0.5F);
        this.body2Part = new WonambiPart(this, body1Part, 0.9F, 0.5F);
        this.body3Part = new WonambiPart(this, body2Part, 0.9F, 0.5F);
        this.tail1Part = new WonambiPart(this, body3Part, 0.9F, 0.5F);
        this.tail2Part = new WonambiPart(this, tail1Part, 0.8F, 0.5F);
        this.tail3Part = new WonambiPart(this, tail2Part, 0.7F, 0.5F);
        this.allParts = new WonambiPart[]{body1Part, body2Part, body3Part, tail1Part, tail2Part, tail3Part};
        this.setId(ENTITY_COUNTER.getAndAdd(allParts.length + 1) + 1);
        this.fakeYRot = this.getYRot();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 18.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F);
    }

//    @Override
//    protected void registerGoals() {
//        this.goalSelector.addGoal(1, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TARTUOSTEUS_FOOD), false));
//        this.goalSelector.addGoal(2, new PrehistoricRandomStrollGoal(this, 1.0D));
//        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
//    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.allParts.length; i++) {
            this.allParts[i].setId(id + i + 1);
        }
    }

    @Override
    public int getHeadRotSpeed() {
        return 5;
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        super.travel(travelVector);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.TARTUOSTEUS_FOOD);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?>[] getParts() {
        return this.allParts;
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        super.remove(removalReason);
        if (allParts != null) {
            for (PartEntity<Wonambi> part : allParts) {
                part.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.yBodyRot = Mth.approachDegrees(this.yBodyRotO, yBodyRot, this.getHeadRotSpeed());
        this.fakeYRot = Mth.approachDegrees(fakeYRot, this.yBodyRot, 10);
        this.tickMultipart();
    }

    private void tickMultipart() {
        if (yawPointer == -1) {
            this.fakeYRot = this.yBodyRot;
            for (int i = 0; i < this.yawBuffer.length; i++) {
                this.yawBuffer[i] = this.fakeYRot;
            }
        }
        if (++this.yawPointer == this.yawBuffer.length) {
            this.yawPointer = 0;
        }
        this.yawBuffer[this.yawPointer] = this.fakeYRot;

        Vec3[] avector3d = new Vec3[this.allParts.length];
        for (int j = 0; j < this.allParts.length; ++j) {
            avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
        }
        Vec3 center = this.position().add(0, this.getBbHeight() * 0.5F, 0);

        this.body1Part.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -0.9F), this.getXRot() * 0.25F, this.getYHeadRot()).add(center));
        this.body2Part.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -0.9F), this.getXRot() * 0.25F, this.getYawFromBuffer(2, 1.0F)).add(this.body1Part.centeredPosition()));
        this.body3Part.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -0.9F), this.getXRot() * 0.25F, this.getYawFromBuffer(4, 1.0F)).add(this.body2Part.centeredPosition()));
        this.tail1Part.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -0.9F), this.getXRot() * 0.25F, this.getYawFromBuffer(6, 1.0F)).add(this.body3Part.centeredPosition()));
        this.tail2Part.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -0.9F), this.getXRot() * 0.25F, this.getYawFromBuffer(8, 1.0F)).add(this.tail1Part.centeredPosition()));
        this.tail3Part.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -0.9F), this.getXRot() * 0.25F, this.getYawFromBuffer(10, 1.0F)).add(this.tail2Part.centeredPosition()));

        for (int l = 0; l < this.allParts.length; l++) {
            this.allParts[l].xo = avector3d[l].x;
            this.allParts[l].yo = avector3d[l].y;
            this.allParts[l].zo = avector3d[l].z;
            this.allParts[l].xOld = avector3d[l].x;
            this.allParts[l].yOld = avector3d[l].y;
            this.allParts[l].zOld = avector3d[l].z;
        }
    }

    public float getTrailTransformation(int pointer, float partialTicks) {
        if (this.isRemoved()) {
            partialTicks = 1.0F;
        }
        int i = this.yawPointer - pointer & 127;
        int j = this.yawPointer - pointer - 1 & 127;
        float d0 = this.yawBuffer[j];
        float d1 = this.yawBuffer[i] - d0;
        return d0 + d1 * partialTicks;
    }

    private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
        return offset.xRot(-xRot * ((float) Math.PI / 180F)).yRot(-yRot * ((float) Math.PI / 180F));
    }

    public float getYawFromBuffer(int pointer, float partialTick) {
        int i = this.yawPointer - pointer & 127;
        int j = this.yawPointer - pointer - 1 & 127;
        float d0 = this.yawBuffer[j];
        float d1 = this.yawBuffer[i] - d0;
        return d0 + d1 * partialTick;
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(6, 4, 6);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.JAWLESS_FISH_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.JAWLESS_FISH_HURT.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.LYSTROSAURUS.get().create(serverLevel);
    }

    public static boolean canSpawn(EntityType<Wonambi> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.TELECREX_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }
}