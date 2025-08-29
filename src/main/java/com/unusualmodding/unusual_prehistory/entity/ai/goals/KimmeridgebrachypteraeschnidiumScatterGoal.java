package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Kimmeridgebrachypteraeschnidium;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2EntityTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class KimmeridgebrachypteraeschnidiumScatterGoal extends Goal {

    private final Kimmeridgebrachypteraeschnidium dragonfly;

    public KimmeridgebrachypteraeschnidiumScatterGoal(Kimmeridgebrachypteraeschnidium dragonfly) {
        this.dragonfly = dragonfly;
    }

    @Override
    public boolean canUse() {
        if (dragonfly.isFlying()) {
            return false;
        }
        long worldTime = dragonfly.level().getGameTime() % 10;
        if (dragonfly.getRandom().nextInt(10) != 0 && worldTime != 0) {
            return false;
        }
        AABB aabb = dragonfly.getBoundingBox().inflate(4);
        List<Entity> list = dragonfly.level().getEntitiesOfClass(Entity.class, aabb, (entity -> entity.getType().is(UP2EntityTags.SCATTERS_KIMMERIDGEBRACHYPTERAESCHNIDIUM) || entity instanceof Player && !((Player) entity).isCreative()));
        return !list.isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        dragonfly.setFlying(true);
    }
}
