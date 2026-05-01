package com.barlinc.unusual_prehistory.mixins;

import com.barlinc.unusual_prehistory.entity.accessor.EvokerAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;

@Mixin(Evoker.class)
public abstract class EvokerMixin extends SpellcasterIllager implements EvokerAccessor {

    @Unique
    @Nullable
    private LivingEntity unusualPrehistory2$livingWololoTarget;

    protected EvokerMixin(EntityType<? extends SpellcasterIllager> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void unusualPrehistory2$setLivingWololoTarget(@Nullable LivingEntity entity) {
        this.unusualPrehistory2$livingWololoTarget = entity;
    }

    @Nullable
    @Override
    public LivingEntity unusualPrehistory2$getLivingWololoTarget() {
        return this.unusualPrehistory2$livingWololoTarget;
    }

    @Override
    public void unusualPrehistory2$setSpellCastingTickCount(int spellCastingTickCount) {
        this.spellCastingTickCount = spellCastingTickCount;
    }
}
