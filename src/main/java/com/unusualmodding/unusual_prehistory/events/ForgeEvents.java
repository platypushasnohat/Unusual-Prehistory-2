package com.unusualmodding.unusual_prehistory.events;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.ZombieAttackEggGoal;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {

    @SubscribeEvent
    public static void onBabySpawn(BabyEntitySpawnEvent event) {
        if (event.getParentA() instanceof Cow parent && event.getParentB() instanceof Cow) {
            Level level = parent.getCommandSenderWorld();
            if (level.getRandom().nextInt(10000) == 0) {
                event.setChild(UP2Entities.UNICORN.get().create(level));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Mob mob) {
            if (mob instanceof Zombie zombie) {
                zombie.goalSelector.addGoal(4, new ZombieAttackEggGoal(zombie));
            }
        }
    }
}
