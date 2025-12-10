package com.barlinc.unusual_prehistory.events;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.Dunkleosteus;
import com.barlinc.unusual_prehistory.entity.Kentrosaurus;
import com.barlinc.unusual_prehistory.entity.ai.goals.ZombieAttackEggGoal;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.BlockEvent;
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
            if (mob instanceof Guardian guardian) {
                guardian.goalSelector.addGoal(3, new AvoidEntityGoal<>(guardian, Dunkleosteus.class, 12.0F, 1.5D, 1.5D));
            }
        }
    }

    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event) {
        BlockState state = event.getState();
        if (state.is(UP2BlockTags.GUARDED_BY_KENTROSAURUS)) {
            Kentrosaurus.angerNearbyKentrosaurus(event.getPlayer(), false);
        }
    }

    @SubscribeEvent
    public static void onMobHurt(final LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource damageSource = event.getSource();

        if (entity instanceof Guardian && damageSource.getEntity() instanceof Dunkleosteus) {
            event.setAmount(event.getAmount() * 4);
        }
    }
}
