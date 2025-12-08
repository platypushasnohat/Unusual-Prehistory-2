package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Metriorhynchus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class MetriorhynchusAttackGoal extends AttackGoal {

    private final Metriorhynchus metriorhynchus;

    public MetriorhynchusAttackGoal(Metriorhynchus metriorhynchus) {
        super(metriorhynchus);
        this.metriorhynchus = metriorhynchus;
    }

    @Override
    public void tick() {
        LivingEntity target = this.metriorhynchus.getTarget();
        if (target != null) {
            double distanceToTarget = this.metriorhynchus.getPerceivedTargetDistanceSquareForMeleeAttack(target);
            int attackState = this.metriorhynchus.getAttackState();

            this.metriorhynchus.getLookControl().setLookAt(target, 30F, 30F);

            if (attackState == 1) this.tickBite();
            else if (attackState == 2) this.tickDeathRoll();
            else if (distanceToTarget <= this.getAttackReachSqr(target)) {
                if (this.canDeathRoll()) this.metriorhynchus.setAttackState(2);
                else if (metriorhynchus.biteCooldown == 0 && !this.canDeathRoll()) this.metriorhynchus.setAttackState(1);
            } else {
                this.metriorhynchus.getNavigation().moveTo(target, metriorhynchus.isInWater() ? 1.4D : 1.5D);
            }
        }
    }

    private boolean canDeathRoll() {
        return metriorhynchus.deathRollCooldown == 0 && !(metriorhynchus.getTarget() instanceof Player) && metriorhynchus.isInWater() && metriorhynchus.canPickUpTarget(metriorhynchus.getTarget());
    }

    protected void tickBite() {
        this.timer++;
        LivingEntity target = metriorhynchus.getTarget();
        this.metriorhynchus.getNavigation().moveTo(target, metriorhynchus.isInWater() ? 1.1D : 1.3D);
        if (timer == 1) metriorhynchus.setPose(UP2Poses.BITING.get());
        if (timer == 2) metriorhynchus.playSound(UP2SoundEvents.METRIORHYNCHUS_BITE.get(), 1.0F, metriorhynchus.getVoicePitch());
        if (timer == 5) {
            if (metriorhynchus.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                this.metriorhynchus.doHurtTarget(target);
                this.metriorhynchus.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 10) {
            this.timer = 0;
            this.metriorhynchus.setAttackState(0);
            this.metriorhynchus.biteCooldown = 5;
        }
    }

    protected void tickDeathRoll() {
        this.timer++;
        this.metriorhynchus.getNavigation().stop();
        LivingEntity target = metriorhynchus.getTarget();
        if (timer == 1) metriorhynchus.setPose(UP2Poses.DEATH_ROLL.get());
        if (timer == 3) metriorhynchus.playSound(UP2SoundEvents.METRIORHYNCHUS_BITE.get(), 1.0F, metriorhynchus.getVoicePitch() * 0.9F);
        if (timer == 5) {
            if (metriorhynchus.distanceTo(target) < 5.5F) {
                metriorhynchus.setHeldMobId(target.getId());
            }
        }
        if (timer > 40) {
            this.timer = 0;
            this.metriorhynchus.setAttackState(0);
            this.metriorhynchus.deathRollCooldown = 200 + metriorhynchus.getRandom().nextInt(200);
            if (metriorhynchus.getHeldMobId() != -1) {
                this.metriorhynchus.setHeldMobId(-1);
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.8F * this.mob.getBbWidth() * 1.8 + target.getBbWidth();
    }
}