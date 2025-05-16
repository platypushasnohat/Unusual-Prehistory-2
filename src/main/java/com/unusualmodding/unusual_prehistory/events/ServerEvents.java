package com.unusualmodding.unusual_prehistory.events;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.messages.ExtractorRecipeS2C;
import com.unusualmodding.unusual_prehistory.messages.UP2Messages;
import com.unusualmodding.unusual_prehistory.recipes.extractor.ExtractorRecipeJsonManager;
import com.unusualmodding.unusual_prehistory.recipes.extractor.ItemWeightedPairCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
            ExtractorRecipeJsonManager.populateRecipeMap(event.getServer().getLevel(Level.OVERWORLD));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void sendMapPackets(PlayerEvent.PlayerLoggedInEvent event){

    }
    @SubscribeEvent
    public static void synchDataPack(OnDatapackSyncEvent event){
        ServerPlayer player = event.getPlayer();
        List<ServerPlayer> playerList = event.getPlayerList().getPlayers();

        Map<Item, List<ItemWeightedPairCodec>> extractorRecipes = ExtractorRecipeJsonManager.getRecipes();

        if(player != null){
            UP2Messages.sendToPlayer(new ExtractorRecipeS2C(extractorRecipes), player);
        }

        if(!playerList.isEmpty()){
            for(ServerPlayer player1 : playerList){
                ServerLevel serverLevel = (ServerLevel) player1.level();
                if(ExtractorRecipeJsonManager.getRecipes().isEmpty()){
                    ExtractorRecipeJsonManager.populateRecipeMap(serverLevel);
                }
                Map<Item, List<ItemWeightedPairCodec>> analyzerRecipesReload = ExtractorRecipeJsonManager.getRecipes();
                UP2Messages.sendToPlayer(new ExtractorRecipeS2C(analyzerRecipesReload), player1);
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new ExtractorRecipeJsonManager());
    }
}
