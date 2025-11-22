package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class UP2CriteriaTriggers extends SimpleCriterionTrigger<UP2CriteriaTriggers.TriggerInstance> {

    private final ResourceLocation CRITERIA;

    public UP2CriteriaTriggers(String name) {
        CRITERIA = UnusualPrehistory2.modPrefix(name);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return CRITERIA;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, conditions -> true);
    }

    @Override
    protected @NotNull TriggerInstance createInstance(@NotNull JsonObject object, @NotNull ContextAwarePredicate predicate, @NotNull DeserializationContext context) {
        return new TriggerInstance(CRITERIA, predicate);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ResourceLocation id, ContextAwarePredicate predicate) {
            super(id, predicate);
        }
    }
}