package com.barlinc.unusual_prehistory.entity.utils;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class JukeboxListener implements GameEventListener {

    protected final PrehistoricMob mob;
    private final PositionSource listenerSource;
    private final int listenerRadius;

    public JukeboxListener(PrehistoricMob mob, PositionSource source, int radius) {
        this.mob = mob;
        this.listenerSource = source;
        this.listenerRadius = radius;
    }

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
    public boolean handleGameEvent(@NotNull ServerLevel level, @NotNull Holder<GameEvent> holder, GameEvent.@NotNull Context context, @NotNull Vec3 vec3) {
        if (holder == GameEvent.JUKEBOX_PLAY) {
            this.mob.danceToJukebox(BlockPos.containing(vec3), true);
            return true;
        } else if (holder == GameEvent.JUKEBOX_STOP_PLAY) {
            this.mob.danceToJukebox(BlockPos.containing(vec3), false);
            return true;
        } else {
            return false;
        }
    }
}