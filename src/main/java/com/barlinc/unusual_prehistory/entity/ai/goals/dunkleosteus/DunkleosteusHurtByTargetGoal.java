package com.barlinc.unusual_prehistory.entity.ai.goals.dunkleosteus;

import com.barlinc.unusual_prehistory.entity.Dunkleosteus;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.GameRules;

import java.util.EnumSet;

public class DunkleosteusHurtByTargetGoal extends TargetGoal {

    private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();

    private final Dunkleosteus dunkleosteus;
    private int timestamp;

    public DunkleosteusHurtByTargetGoal(Dunkleosteus dunkleosteus) {
        super(dunkleosteus, true);
        this.dunkleosteus = dunkleosteus;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        int time = this.mob.getLastHurtByMobTimestamp();
        LivingEntity entity = this.mob.getLastHurtByMob();
        if (time != this.timestamp && entity != null) {
            if (entity.getType() == EntityType.PLAYER && this.mob.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER) || this.dunkleosteus.getDunkSize() == 0 || this.dunkleosteus.isBaby()) {
                return false;
            } else {
                return this.canAttack(entity, HURT_BY_TARGETING);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.mob.getLastHurtByMob());
        this.targetMob = this.mob.getTarget();
        this.timestamp = this.mob.getLastHurtByMobTimestamp();
        this.unseenMemoryTicks = 300;
        super.start();
    }
}