package com.unusualmodding.unusual_prehistory.events;

import com.unusualmodding.unusual_prehistory.network.ExtractorRecipeSyncClient;
import com.unusualmodding.unusual_prehistory.recipes.extractor.ExtractorRecipeJsonManager;
import com.unusualmodding.unusual_prehistory.recipes.extractor.ItemWeightedPairCodec;
import com.unusualmodding.unusual_prehistory.registry.UP2Network;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Map;

public class ServerEvents {

    @SubscribeEvent
    public static void synchDataPack(OnDatapackSyncEvent event){
        ServerPlayer player = event.getPlayer();
        List<ServerPlayer> playerList = event.getPlayerList().getPlayers();

        Map<Item, List<ItemWeightedPairCodec>> extractorRecipes = ExtractorRecipeJsonManager.getRecipes();

        if (player != null) {
            UP2Network.sendToPlayer(new ExtractorRecipeSyncClient(extractorRecipes), player);
        }

        if (!playerList.isEmpty()) {
            for (ServerPlayer player1 : playerList) {
                ServerLevel serverLevel = (ServerLevel) player1.level();
                if (ExtractorRecipeJsonManager.getRecipes().isEmpty()) {
                    ExtractorRecipeJsonManager.populateRecipeMap(serverLevel);
                }
                Map<Item, List<ItemWeightedPairCodec>> reloadExtractingRecipes = ExtractorRecipeJsonManager.getRecipes();
                UP2Network.sendToPlayer(new ExtractorRecipeSyncClient(reloadExtractingRecipes), player1);
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(ServerStartedEvent event) {
        try {
            ExtractorRecipeJsonManager.populateRecipeMap(event.getServer().getLevel(Level.OVERWORLD));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new ExtractorRecipeJsonManager());
    }
}
