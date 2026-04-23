package com.barlinc.unusual_prehistory.integration.jade;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

public enum PrehistoricMobProvider implements IEntityComponentProvider {

    INSTANCE;

    @Override
    public ResourceLocation getUid() {
        return UnusualPrehistory2.modPrefix("prehistoric_mob");
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        PrehistoricMob prehistoricMob = (PrehistoricMob) accessor.getEntity();
        if (prehistoricMob.isPacified()) {
            if (prehistoricMob.getPacifiedTicks() <= -1) {
                tooltip.add(Component.translatable("unusual_prehistory.jade.prehistoric_mob.pacified"));
            } else {
                tooltip.add(Component.translatable("unusual_prehistory.jade.prehistoric_mob.pacified_time", IThemeHelper.get().seconds(prehistoricMob.getPacifiedTicks(), accessor.tickRate())));
            }
        }
    }
}
