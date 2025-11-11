package com.unusualmodding.unusual_prehistory.entity.ai.goals.carnotaurus;

import com.unusualmodding.unusual_prehistory.entity.Carnotaurus;
import com.unusualmodding.unusual_prehistory.entity.utils.Behaviors;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class CarnotaurusRoarGoal extends Goal {

    private final Carnotaurus carnotaurus;
    private int timer;

    public CarnotaurusRoarGoal(Carnotaurus carnotaurus) {
        this.carnotaurus = carnotaurus;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return !this.carnotaurus.isVehicle() && !this.carnotaurus.isBaby() && this.carnotaurus.getTarget() != null && this.carnotaurus.getTarget().isAlive() && this.carnotaurus.getRoarCooldown() <= 0 && !this.carnotaurus.isInWaterOrBubble() && this.carnotaurus.getAttackState() == 0 && !this.carnotaurus.isCharging() && this.carnotaurus.getHealth() < this.carnotaurus.getMaxHealth() * 0.5F;
    }

    @Override
    public void start() {
        LivingEntity target = this.carnotaurus.getTarget();
        if (target == null) {
            return;
        }
        this.timer = 0;
        this.carnotaurus.setRoaring(true);
        this.carnotaurus.setBehavior(Behaviors.ANGRY.getName());
        this.carnotaurus.setAggressive(true);
    }

    @Override
    public void stop() {
        this.timer = 0;
        this.carnotaurus.setRoaring(false);
        this.carnotaurus.setBehavior(Behaviors.IDLE.getName());
        this.carnotaurus.setAggressive(false);
        this.carnotaurus.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.carnotaurus.getTarget();
        if (target != null) {
            this.timer++;
            if (timer == 5) {
                this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_ROAR.get(), 2.0F, 1.0F);
            }
            this.carnotaurus.getNavigation().stop();
            this.carnotaurus.lookAt(target, 360F, 30F);
            this.carnotaurus.getLookControl().setLookAt(target, 30F, 30F);

            if (timer == 8) {
                carnotaurus.roar();
            }

            if (this.timer > 40) {
                finishRoaring(this.carnotaurus);
            }
        }
    }

    private void finishRoaring(Carnotaurus carnotaurus) {
        this.timer = 0;
        carnotaurus.roarCooldown();
    }
}