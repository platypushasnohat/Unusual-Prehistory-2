package com.barlinc.unusual_prehistory.integration.jade;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

public class PrehistoricMobProvider implements IEntityComponentProvider {

    @Override
    public ResourceLocation getUid() {
        return UnusualPrehistory2.modPrefix("prehistoric_mob");
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        PrehistoricMob prehistoricMob = (PrehistoricMob) accessor.getEntity();
        if (prehistoricMob.isBaby() && prehistoricMob.isAgeLocked()) {
            tooltip.remove(JadeIds.MC_MOB_GROWTH);
            tooltip.add(Component.translatable("unusual_prehistory.jade.prehistoric_mob.age_locked"));
        }
        if (prehistoricMob.isPacified()) {
            if (prehistoricMob.getPacifiedTicks() <= -1) {
                tooltip.add(Component.translatable("unusual_prehistory.jade.prehistoric_mob.pacified"));
            } else {
                tooltip.add(Component.translatable("unusual_prehistory.jade.prehistoric_mob.pacified_time", IThemeHelper.get().seconds(prehistoricMob.getPacifiedTicks(), accessor.tickRate())));
            }
        }
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.TAIL - 100;
    }
}
