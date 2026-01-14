package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class PrehistoricAvoidEntityGoal<T extends LivingEntity> extends Goal {

    protected final PrehistoricMob mob;
    private final double speedModifier;
    @Nullable
    protected T toAvoid;
    protected final float maxDist;
    @Nullable
    protected Path path;
    protected final PathNavigation pathNav;
    protected final Class<T> avoidClass;
    protected final Predicate<LivingEntity> avoidPredicate;
    protected final Predicate<LivingEntity> predicateOnAvoidEntity;
    private final TargetingConditions avoidEntityTargeting;
    private final boolean shouldRun;

    public PrehistoricAvoidEntityGoal(PrehistoricMob mob, Class<T> classToAvoid, float distance, double speedModifier) {
        this(mob, classToAvoid, (entity) -> true, distance, speedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test, true);
    }

    public PrehistoricAvoidEntityGoal(PrehistoricMob mob, Class<T> classToAvoid, float distance, double speedModifier, Predicate<LivingEntity> predicateOnAvoid) {
        this(mob, classToAvoid, (entity) -> true, distance, speedModifier, predicateOnAvoid, true);
    }

    public PrehistoricAvoidEntityGoal(PrehistoricMob mob, Class<T> classToAvoid, float distance, double speedModifier, boolean shouldRun) {
        this(mob, classToAvoid, (entity) -> true, distance, speedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test, shouldRun);
    }

    public PrehistoricAvoidEntityGoal(PrehistoricMob mob, Class<T> classToAvoid, float distance, double speedModifier, Predicate<LivingEntity> predicateOnAvoid, boolean shouldRun) {
        this(mob, classToAvoid, (entity) -> true, distance, speedModifier, predicateOnAvoid, shouldRun);
    }

    public PrehistoricAvoidEntityGoal(PrehistoricMob mob, Class<T> classToAvoid, Predicate<LivingEntity> avoidPredicate, float distance, double speedModifier, Predicate<LivingEntity> predicateOnAvoid, boolean shouldRun) {
        this.mob = mob;
        this.avoidClass = classToAvoid;
        this.avoidPredicate = avoidPredicate;
        this.maxDist = distance;
        this.speedModifier = speedModifier;
        this.predicateOnAvoidEntity = predicateOnAvoid;
        this.pathNav = mob.getNavigation();
        this.shouldRun = shouldRun;
        this.avoidEntityTargeting = TargetingConditions.forCombat().range(distance).selector(predicateOnAvoid.and(avoidPredicate));
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        this.toAvoid = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.avoidClass, this.mob.getBoundingBox().inflate(this.maxDist, 3.0D, this.maxDist), (entity) -> true), this.avoidEntityTargeting, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
        if (this.toAvoid == null) {
            return false;
        } else {
            Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.toAvoid.position());
            if (vec3 == null) {
                return false;
            } else if (this.toAvoid.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.toAvoid.distanceToSqr(this.mob)) {
                return false;
            } else {
                this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
                return this.path != null;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.pathNav.isDone();
    }

    @Override
    public void start() {
        if (this.shouldRun) this.mob.setRunning(true);
        if (this.mob.isMobSitting()) this.mob.stopSitting();
        this.pathNav.moveTo(this.path, this.speedModifier);
    }

    @Override
    public void stop() {
        if (this.mob.isRunning()) this.mob.setRunning(false);
        this.toAvoid = null;
    }

    @Override
    public void tick() {
        this.mob.getNavigation().setSpeedModifier(this.speedModifier);
    }
}