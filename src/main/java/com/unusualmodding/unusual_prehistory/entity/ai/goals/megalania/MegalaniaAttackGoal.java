package com.unusualmodding.unusual_prehistory.entity.ai.goals.megalania;

import com.unusualmodding.unusual_prehistory.entity.Megalania;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.unusualmodding.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class MegalaniaAttackGoal extends AttackGoal {

    private final Megalania megalania;
    private int tailWhipCooldown;
    private int biteCooldown;

    public MegalaniaAttackGoal(Megalania megalania) {
        super(megalania);
        this.megalania = megalania;
        megalania.getNavigation().setCanFloat(false);
    }

    @Override
    public void start() {
        super.start();
        this.tailWhipCooldown =0;
        this.biteCooldown = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.tailWhipCooldown =0;
        this.biteCooldown = 0;
    }

    @Override
    public void tick() {
        LivingEntity target = this.megalania.getTarget();
        if (target != null) {
            this.megalania.lookAt(this.megalania.getTarget(), 30F, 30F);
            this.megalania.getLookControl().setLookAt(this.megalania.getTarget(), 30F, 30F);

            double distance = this.megalania.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.megalania.getAttackState();

            if (this.megalania.isInWater()) {
                this.megalania.getNavigation().moveTo(target, 1.2D);
            } else if (this.megalania.getTemperatureState().equals(Megalania.TemperatureStates.NETHER)) {
                this.megalania.getNavigation().moveTo(target, 1.8D);
            } else {
                this.megalania.getNavigation().moveTo(target, 2.0D);
            }

            if (this.biteCooldown > 0) biteCooldown--;
            if (this.tailWhipCooldown > 0 && !megalania.isInWater()) tailWhipCooldown--;

            if (attackState == 1) {
                this.timer++;
                if (timer == 1) this.megalania.setPose(UP2Poses.BITING.get());
                if (this.timer == 11) {
                    if (this.megalania.distanceTo(Objects.requireNonNull(target)) <= this.getAttackReachSqr(target)) {
                        this.megalania.swing(InteractionHand.MAIN_HAND);
                        this.megalania.doHurtTarget(target);
                        this.megalania.applyPoison(target);
                    }
                }
                if (this.timer > 22) {
                    this.timer = 0;
                    this.biteCooldown = 10 + megalania.getRandom().nextInt(6);
                    this.megalania.setAttackState(0);
                }
            } else if (attackState == 2) {
                this.timer++;
                this.megalania.getNavigation().stop();

                if (timer == 1) this.megalania.setPose(UP2Poses.TAIL_WHIPPING.get());
                if (this.timer == 12) this.megalania.addDeltaMovement(this.megalania.getLookAngle().scale(2.0D).multiply(0.25D, 0, 0.25D));

                if (this.timer == 14) {
                    if (this.megalania.distanceTo(Objects.requireNonNull(target)) <= this.getAttackReachSqr(target)) {
                        this.megalania.swing(InteractionHand.MAIN_HAND);
                        this.megalania.doHurtTarget(target);
                        this.megalania.strongKnockback(target, 1.3D, 0.1D);
                        if (target.isDamageSourceBlocked(this.megalania.damageSources().mobAttack(this.megalania)) && target instanceof Player player){
                            player.disableShield(true);
                        }
                    }
                }
                if (this.timer > 28) {
                    this.timer = 0;
                    this.tailWhipCooldown = 20 + megalania.getRandom().nextInt(24);
                    this.megalania.setAttackState(0);
                }
            } else {
                if (distance <= this.getAttackReachSqr(target)) {
                    if (!this.megalania.isInWater()) {
                        if (this.megalania.getRandom().nextFloat() < 0.38F) this.megalania.setAttackState(2);
                        else this.megalania.setAttackState(1);
                    } else {
                        this.megalania.setAttackState(1);
                    }
                }
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.75F * this.mob.getBbWidth() * 1.75F + target.getBbWidth();
    }
}
