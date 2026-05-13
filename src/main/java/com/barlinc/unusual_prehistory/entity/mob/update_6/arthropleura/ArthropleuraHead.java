package com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class ArthropleuraHead extends ArthropleuraPart {

    public ArthropleuraHead(EntityType<? extends ArthropleuraPart> entity, Level level) {
        super(entity, level);
        this.setSegmentID(0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricRandomStrollGoal(this, 1.0D));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 26.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        CompoundTag behindSegmentsTag = new CompoundTag();
        if (behindSegments != null) {
            for (int i = 0; i < behindSegments.size(); i++) {
                CompoundTag segmentTag = new CompoundTag();
                ArthropleuraBody segment = behindSegments.get(i);
                if (segment != null) {
                    segment.saveWithoutId(segmentTag);
                    behindSegmentsTag.put("Segment" + i, segmentTag);
                }
            }
        }
        compoundTag.put("BodySegments", behindSegmentsTag);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        CompoundTag behindSegmentsTag = compoundTag.getCompound("BodySegments");
        int length = behindSegmentsTag.size();
        this.behindSegments = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            ArthropleuraBody segment = UP2Entities.ARTHROPLEURA_BODY.get().create(this.level());
            if (segment != null) {
                segment.load(behindSegmentsTag.getCompound("Segment" + i));
                segment.head = this;
                this.level().addFreshEntity(segment);
                this.getSegmentFromIndex(i - 1).behind = segment;
                segment.ahead = this.getSegmentFromIndex(i - 1);
                this.behindSegments.add(segment);
            }
        }
    }

    @Override
    public boolean isFood(@NotNull ItemStack itemStack) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isDeadOrDying()) {
            this.applyRopeConstraints(16);
        }
    }

    @Override
    public boolean onClimbable() {
        return this.isInWater() || super.onClimbable();
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        int arthropleuraLength = 5;
        this.setOriginalSegmentID(0);
        this.setSegmentID(0);
        this.setBodyLength(arthropleuraLength + 1);
        this.setOriginalBodyLength(arthropleuraLength + 1);
        Vec3 forward = new Vec3(this.getLookAngle().x, 0, this.getLookAngle().z).normalize();
        for (int i = 0; i < arthropleuraLength; i++) {
            ArthropleuraBody segment = UP2Entities.ARTHROPLEURA_BODY.get().create(this.level());
            if (segment != null) {
                segment.head = this;
                segment.setSegmentID(i + 1);
                segment.setOriginalSegmentID(i + 1);
                segment.setBodyLength(arthropleuraLength + 1);
                segment.setOriginalBodyLength(arthropleuraLength + 1);
                Vec3 offset = forward.scale(-(i + 1) * segment.segmentLength);
                segment.setPos(this.position().add(offset));
                level.addFreshEntityWithPassengers(segment);
                this.getSegmentFromIndex(i - 1).behind = segment;
                segment.ahead = this.getSegmentFromIndex(i - 1);
                segment.setTail(i == arthropleuraLength - 1);
                this.behindSegments.add(segment);
            }
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        super.travel(travelVector);
        if (behindSegments != null) {
            for (int segmentIndex = 0; segmentIndex < behindSegments.size(); segmentIndex++) {
                ArthropleuraPart segment = this.getSegmentFromIndex(segmentIndex);
                ArthropleuraPart aheadSegment = this.getSegmentFromIndex(segmentIndex - 1);
                Vec3 dir = aheadSegment.position().subtract(segment.position());
                dir = new Vec3(dir.x, 0.0, dir.z);
                if (dir.lengthSqr() > 1.0E-6) {
                    dir = dir.normalize().scale(travelVector.length());
                    segment.travel(dir);
                }
            }
        }
    }

    @Override
    public void die(@NotNull DamageSource source) {
        super.die(source);
        if (behindSegments != null) {
            for (ArthropleuraBody segment : behindSegments) {
                segment.kill();
            }
        }
    }
}