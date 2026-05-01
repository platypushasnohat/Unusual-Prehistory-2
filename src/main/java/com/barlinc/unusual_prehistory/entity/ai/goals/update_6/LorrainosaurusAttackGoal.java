package com.barlinc.unusual_prehistory.entity.ai.goals.update_6;

import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Lorrainosaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class LorrainosaurusAttackGoal extends AttackGoal {

    private final Lorrainosaurus lorrainosaurus;
    @Nullable
    private BlockPos waterPos = null;
    private double wantedX;
    private double wantedY;
    private double wantedZ;

    public LorrainosaurusAttackGoal(Lorrainosaurus lorrainosaurus) {
        super(lorrainosaurus);
        this.lorrainosaurus = lorrainosaurus;
    }

    @Override
    public void tick() {
        LivingEntity target = lorrainosaurus.getTarget();
        if (target != null) {
            double distance = lorrainosaurus.distanceToSqr(target);
            int attackState = lorrainosaurus.getAttackState();

            if (waterPos != null) {
                this.lorrainosaurus.getNavigation().moveTo(waterPos.getX(), waterPos.getY(), waterPos.getZ(), 1.5D);
            }

            if (attackState == 1) {
                this.tickBite();
            } else if (attackState == 2) {
                this.tickGrab();
            } else {
                if (distance <= this.getAttackReachSqr(target) && lorrainosaurus.biteCooldown == 0 && !this.canGrab(target)) {
                    this.lorrainosaurus.setAttackState(1);
                } else if (distance <= this.getAttackReachSqr(target) && this.canGrab(target)) {
                    this.lorrainosaurus.setAttackState(2);
                } else {
                    this.lorrainosaurus.getNavigation().moveTo(target, 1.5D);
                }
                if (!target.isInWaterOrBubble() && lorrainosaurus.horizontalCollision && lorrainosaurus.isInWaterOrBubble()) {
                    float rot = lorrainosaurus.getYRot() * ((float) Math.PI / 180F);
                    this.lorrainosaurus.getNavigation().stop();
                    this.lorrainosaurus.setDeltaMovement(lorrainosaurus.getDeltaMovement().add(-Mth.sin(rot) * 0.4F, 0.2D, Mth.cos(rot) * 0.4F));
                }
            }
        }
    }

    protected boolean canGrab(LivingEntity target) {
        return !target.isInWaterOrBubble() && lorrainosaurus.grabCooldown == 0;
    }

    protected void tickBite() {
        this.timer++;
        this.lorrainosaurus.getNavigation().stop();
        if (timer == 1) {
            this.lorrainosaurus.attackAlt = lorrainosaurus.getRandom().nextBoolean();
            this.lorrainosaurus.setPose(UP2Poses.ATTACKING.get());
            this.lorrainosaurus.playSound(UP2SoundEvents.PROGNATHODON_ATTACK.get(), 1.5F, 1.0F * lorrainosaurus.getRandom().nextFloat() * 0.2F);
        }
        if (timer == 10) {
            this.hurtNearbyEntities();
        }
        if (timer > 20) {
            this.timer = 0;
            this.lorrainosaurus.setPose(Pose.STANDING);
            this.lorrainosaurus.setAttackState(0);
            this.lorrainosaurus.biteCooldown = 7;
        }
    }

    protected void tickGrab() {
        this.timer++;
        LivingEntity target = lorrainosaurus.getTarget();
        if (timer == 1) {
            this.lorrainosaurus.getNavigation().stop();
            this.lorrainosaurus.setPose(UP2Poses.GRAB_START.get());
        }
        if (timer == 3) {
            this.lorrainosaurus.playSound(UP2SoundEvents.METRIORHYNCHUS_BITE.get(), 1.0F, lorrainosaurus.getVoicePitch() * 0.9F);
        }
        if (timer == 5) {
            if (this.isInAttackRange(target, 2.0D)) {
                this.lorrainosaurus.setHeldMobId(target.getId());
            }
        }
        if (timer > 5 && timer <= 120 && lorrainosaurus.getHeldMobId() != -1) {
            if (waterPos == null && !lorrainosaurus.isInWaterOrBubble()) {
                this.findWaterPos();
            } else if (lorrainosaurus.isInWaterOrBubble() && this.findSwimmingPos()) {
                this.lorrainosaurus.getNavigation().moveTo(wantedX, wantedY, wantedZ, 1.5D);
            }
        }
        if (timer > 120 || (timer > 8 && lorrainosaurus.getHeldMobId() == -1)) {
            this.timer = 0;
            this.lorrainosaurus.setAttackState(0);
            this.lorrainosaurus.setPose(Pose.STANDING);
            this.waterPos = null;
            this.lorrainosaurus.getNavigation().stop();
            this.lorrainosaurus.grabCooldown = 90 + lorrainosaurus.getRandom().nextInt(90);
            if (lorrainosaurus.getHeldMobId() != -1) {
                this.lorrainosaurus.setHeldMobId(-1);
            }
        }
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = lorrainosaurus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), lorrainosaurus, lorrainosaurus.getBoundingBox().inflate(2.0D));
        if (!nearbyEntities.isEmpty()) {
            nearbyEntities.stream().filter(entity -> entity != lorrainosaurus).limit(3).forEach(entity -> {
                entity.hurt(entity.damageSources().mobAttack(lorrainosaurus), (float) lorrainosaurus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                this.lorrainosaurus.strongKnockback(entity, 0.75D, 0.1D);
                if (entity.isDamageSourceBlocked(lorrainosaurus.damageSources().mobAttack(lorrainosaurus)) && entity instanceof Player player) {
                    player.disableShield();
                }
                this.lorrainosaurus.swing(InteractionHand.MAIN_HAND);
            });
        }
    }

    private void findWaterPos() {
        RandomSource random = lorrainosaurus.getRandom();
        Level level = lorrainosaurus.level();
        BlockPos original = lorrainosaurus.blockPosition();
        BlockPos.MutableBlockPos mutable = original.mutable();

        for (int i = 0; i < 10; i++) {
            mutable.move(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
            if (level.getFluidState(mutable).is(FluidTags.WATER)) {
                this.waterPos = mutable.immutable();
                return;
            }
        }
    }

    protected boolean findSwimmingPos() {
        Vec3 vec3 = BehaviorUtils.getRandomSwimmablePos(lorrainosaurus, 10, 7);
        if (vec3 == null) {
            return false;
        } else {
            this.wantedX = vec3.x;
            this.wantedY = vec3.y;
            this.wantedZ = vec3.z;
            return true;
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return mob.getBbWidth() * 1.6F * mob.getBbWidth() * 1.6F + target.getBbWidth();
    }
}