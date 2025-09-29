package com.unusualmodding.unusual_prehistory.entity.base;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public abstract class SchoolingAquaticMob extends PrehistoricAquaticMob {

    @Nullable
    private SchoolingAquaticMob leader;
    protected int schoolSize = 1;

    protected SchoolingAquaticMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
            List<? extends SchoolingAquaticMob> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
            if (list.size() <= 1) {
                this.schoolSize = 1;
            }
        }
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return this.getMaxSchoolSize();
    }

    public int getMaxSchoolSize() {
        return super.getMaxSpawnClusterSize();
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public SchoolingAquaticMob startFollowing(SchoolingAquaticMob entity) {
        this.leader = entity;
        entity.addFollower();
        return entity;
    }

    public void stopFollowing() {
        this.leader.removeFollower();
        this.leader = null;
    }

    private void addFollower() {
        ++this.schoolSize;
    }

    private void removeFollower() {
        --this.schoolSize;
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.schoolSize < this.getMaxSchoolSize();
    }

    public boolean hasFollowers() {
        return this.schoolSize > 1;
    }

    public boolean inRangeOfLeader() {
        return this.distanceToSqr(this.leader) <= 121.0D;
    }

    public void addFollowers(Stream<? extends SchoolingAquaticMob> entity) {
        entity.limit(this.getMaxSchoolSize() - this.schoolSize).filter((entity1) -> entity1 != this).forEach((entity2) -> {
            if (this.getVariant() == entity2.getVariant()) {
                entity2.startFollowing(this);
            }
        });
    }

    public void pathToLeader() {
        if (this.isFollower()) {
            this.getNavigation().moveTo(this.leader, 1.0D);
        }
    }
}
