package com.barlinc.unusual_prehistory.entity.ai.goals.pterodactylus;

import com.barlinc.unusual_prehistory.entity.Pterodactylus;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class PterodactylusScatterGoal extends Goal {

    private final Pterodactylus pterodactylus;

    public PterodactylusScatterGoal(Pterodactylus pterodactylus) {
        this.pterodactylus = pterodactylus;
    }

    @Override
    public boolean canUse() {
        if (pterodactylus.isFlying()) {
            return false;
        }
        long worldTime = pterodactylus.level().getGameTime() % 10;
        if (pterodactylus.getRandom().nextInt(10) != 0 && worldTime != 0) {
            return false;
        }
        AABB aabb = pterodactylus.getBoundingBox().inflate(6);
        List<Entity> list = pterodactylus.level().getEntitiesOfClass(Entity.class, aabb, (entity -> entity.getType().is(UP2EntityTags.SCATTERS_TELECREX) || entity instanceof Player && !((Player) entity).isCreative()));
        return !list.isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        if (pterodactylus.isHanging()) pterodactylus.setHanging(false);
        this.pterodactylus.setFlying(true);
        this.pterodactylus.setRunning(true);
        this.pterodactylus.setRunningTicks(pterodactylus.getFastFlyingTicks());
    }
}
