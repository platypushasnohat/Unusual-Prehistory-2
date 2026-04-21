package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.accessor.EvokerAccessor;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class WololoSpellGoal<T extends LivingEntity> extends Goal {

    protected final Evoker evoker;
    protected final EvokerAccessor evokerAccess;
    protected final Class<T> targetType;
    protected TargetingConditions targetConditions;
    protected final int redVariant;
    protected int attackWarmupDelay;
    protected int nextAttackTickCount;

    public WololoSpellGoal(Evoker evoker, Class<T> targetType, @Nullable Predicate<LivingEntity> entityPredicate, int redVariant) {
        this.evoker = evoker;
        this.evokerAccess = (EvokerAccessor) evoker;
        this.targetType = targetType;
        this.targetConditions = TargetingConditions.forNonCombat().range(16.0D).selector(entityPredicate);
        this.redVariant = redVariant;
    }

    @Override
    public boolean canUse() {
        if (evoker.getTarget() != null) {
            return false;
        } else if (evoker.isCastingSpell()) {
            return false;
        } else if (evoker.tickCount < this.nextAttackTickCount) {
            return false;
        } else if (!EventHooks.canEntityGrief(evoker.level(), evoker)) {
            return false;
        } else {
            List<T> list = evoker.level().getNearbyEntities(targetType, targetConditions, evoker, evoker.getBoundingBox().inflate(16.0D, 4.0D, 16.0D));
            if (list.isEmpty()) {
                return false;
            } else {
                this.evokerAccess.unusualPrehistory2$setLivingWololoTarget(list.get(evoker.getRandom().nextInt(list.size())));
                return true;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return evokerAccess.unusualPrehistory2$getLivingWololoTarget() != null && attackWarmupDelay > 0;
    }

    @Override
    public void start() {
        this.attackWarmupDelay = this.adjustedTickDelay(this.getCastWarmupTime());
        this.evokerAccess.unusualPrehistory2$setSpellCastingTickCount(this.getCastingTime());
        this.nextAttackTickCount = evoker.tickCount + this.getCastingInterval();
        SoundEvent soundevent = this.getSpellPrepareSound();
        if (soundevent != null) {
            this.evoker.playSound(soundevent, 1.0F, 1.0F);
        }
        this.evoker.setIsCastingSpell(this.getSpell());
    }

    @Override
    public void stop() {
        this.evokerAccess.unusualPrehistory2$setLivingWololoTarget(null);
    }

    protected int getCastWarmupTime() {
        return 40;
    }

    protected int getCastingTime() {
        return 60;
    }

    protected int getCastingInterval() {
        return 140;
    }

    @Override
    public void tick() {
        this.attackWarmupDelay--;
        if (this.attackWarmupDelay == 0) {
            this.performSpellCasting();
            this.evoker.playSound(SoundEvents.EVOKER_CAST_SPELL, 1.0F, 1.0F);
        }
    }

    protected void performSpellCasting() {
        LivingEntity wololoTarget = evokerAccess.unusualPrehistory2$getLivingWololoTarget();
        if (wololoTarget != null && wololoTarget.isAlive() && wololoTarget instanceof PrehistoricMob prehistoricMob) {
            prehistoricMob.setVariant(redVariant);
        }
    }

    protected SoundEvent getSpellPrepareSound() {
        return SoundEvents.EVOKER_PREPARE_WOLOLO;
    }

    protected SpellcasterIllager.@NotNull IllagerSpell getSpell() {
        return SpellcasterIllager.IllagerSpell.WOLOLO;
    }
}