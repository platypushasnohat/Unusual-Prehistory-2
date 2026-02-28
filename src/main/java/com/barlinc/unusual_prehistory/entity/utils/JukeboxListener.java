package com.barlinc.unusual_prehistory.entity.utils;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record JukeboxListener(PrehistoricMob mob, PositionSource listenerSource, int listenerRadius) implements GameEventListener {

    @Override
    @NotNull
    public PositionSource getListenerSource() {
        return this.listenerSource;
    }

    @Override
    public int getListenerRadius() {
        return this.listenerRadius;
    }

    @Override
    public boolean handleGameEvent(@NotNull ServerLevel level, @NotNull GameEvent gameEvent, GameEvent.@NotNull Context context, @NotNull Vec3 vec3) {
        if (mob.getLastHurtByMob() != null || mob.getTarget() != null || mob.hasControllingPassenger() || !mob.canDanceToJukebox()) return false;
        if (gameEvent == GameEvent.JUKEBOX_PLAY) {
            this.mob.danceToJukebox(BlockPos.containing(vec3), true);
            return true;
        } else if (gameEvent == GameEvent.JUKEBOX_STOP_PLAY) {
            this.mob.danceToJukebox(BlockPos.containing(vec3), false);
            return true;
        } else {
            return false;
        }
    }
}