package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Telecrex;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2EntityTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class TelecrexScatterGoal extends Goal {

    private final Telecrex telecrex;

    public TelecrexScatterGoal(Telecrex telecrex) {
        this.telecrex = telecrex;
    }

    @Override
    public boolean canUse() {
        if (telecrex.isFlying()) {
            return false;
        }
        long worldTime = telecrex.level().getGameTime() % 10;
        if (telecrex.getRandom().nextInt(10) != 0 && worldTime != 0) {
            return false;
        }
        AABB aabb = telecrex.getBoundingBox().inflate(7);
        List<Entity> list = telecrex.level().getEntitiesOfClass(Entity.class, aabb, (entity -> entity.getType().is(UP2EntityTags.SCATTERS_TELECREX) || entity instanceof Player && !((Player) entity).isCreative()));
        return !list.isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        telecrex.setFlying(true);
    }
}
