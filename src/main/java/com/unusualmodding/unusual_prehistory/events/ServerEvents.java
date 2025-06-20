package com.unusualmodding.unusual_prehistory.events;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.network.ExtractingRecipeS2C;
import com.unusualmodding.unusual_prehistory.recipes.data.ExtractingRecipeJsonManager;
import com.unusualmodding.unusual_prehistory.recipes.data.ItemWeightedPairCodec;
import com.unusualmodding.unusual_prehistory.registry.UP2Network;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public static void onRegisterReloadListeners(ServerStartedEvent event) {
        try {
            ExtractingRecipeJsonManager.populateRecipeMap(event.getServer().getLevel(Level.OVERWORLD));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void synchDataPack(OnDatapackSyncEvent event){
        ServerPlayer player = event.getPlayer();
        List<ServerPlayer> playerList = event.getPlayerList().getPlayers();
        Map<Item, List<ItemWeightedPairCodec>> analyzerRecipes = ExtractingRecipeJsonManager.getRecipes();

        if (player != null) {
            UP2Network.sendToPlayer(new ExtractingRecipeS2C(analyzerRecipes), player);
        }

        if (playerList != null && !playerList.isEmpty()) {
            for(ServerPlayer player1 : playerList){
                ServerLevel serverLevel = (ServerLevel) player1.level();
                if (ExtractingRecipeJsonManager.getRecipes().isEmpty()) {
                    ExtractingRecipeJsonManager.populateRecipeMap(serverLevel);
                }
                Map<Item, List<ItemWeightedPairCodec>> analyzerRecipesReload = ExtractingRecipeJsonManager.getRecipes();
                UP2Network.sendToPlayer(new ExtractingRecipeS2C(analyzerRecipesReload), player1);
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new ExtractingRecipeJsonManager());
    }
}
