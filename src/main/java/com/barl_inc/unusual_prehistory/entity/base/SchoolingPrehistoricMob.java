package com.barl_inc.unusual_prehistory.entity.base;

import com.platypushasnohat.sinew.utils.SinewSoundUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public abstract class SchoolingPrehistoricMob extends AquaticPrehistoricMob {

    @Nullable
    private SchoolingPrehistoricMob leader;
    protected int schoolSize = 1;

    protected SchoolingPrehistoricMob(EntityType<? extends SchoolingPrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    public int getMaxSchoolSize() {
        return super.getMaxSpawnClusterSize();
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public void startFollowing(SchoolingPrehistoricMob mob) {
        this.leader = mob;
        mob.addFollower();
    }

    public void stopFollowing() {
        if (this.leader != null) {
            this.leader.removeFollower();
            this.leader = null;
        }
    }

    public void addFollower() {
        this.schoolSize++;
    }

    public void removeFollower() {
        this.schoolSize--;
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.schoolSize < this.getMaxSchoolSize();
    }

    public boolean hasFollowers() {
        return this.schoolSize > 1;
    }

    public boolean inRangeOfLeader() {
        if (this.leader != null) {
            return this.distanceToSqr(this.leader) <= 121.0D;
        }
        return false;
    }

    public boolean canAddFollowers(SchoolingPrehistoricMob mob) {
        return true;
    }

    public void addFollowers(Stream<? extends SchoolingPrehistoricMob> entity) {
        entity.limit(this.getMaxSchoolSize() - this.schoolSize).filter((mob) -> mob != this).forEach((mob) -> {
            if (this.canAddFollowers(mob)) {
                mob.startFollowing(this);
            }
        });
    }

    public void pathToLeader() {
        if (this.isFollower() && this.leader != null) {
            this.getNavigation().moveTo(this.leader, 1.0D);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.hasFollowers() && this.level().getRandom().nextInt(200) == 1) {
            List<? extends SchoolingPrehistoricMob> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
            if (list.size() <= 1) {
                this.schoolSize = 1;
            }
        }
    }

    @Override
    protected void playSwimSound(float volume) {
        float multiplier = 1.0F;
        if (this.leader != null) {
            multiplier = Math.max(1.0F - (this.leader.schoolSize - 1) * 0.1F, 0.4F);
        }
        this.playSound(this.getSwimSound(), volume * multiplier, SinewSoundUtils.randomizePitch(this));
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return this.getMaxSchoolSize();
    }
}
