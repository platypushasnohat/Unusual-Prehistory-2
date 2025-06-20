package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.network.ExtractingRecipeS2C;
import com.unusualmodding.unusual_prehistory.network.SyncItemStackC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

public class UP2Network {

    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void registerNetwork() {

        SimpleChannel network = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(UnusualPrehistory2.MOD_ID, "network"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        network.messageBuilder(SyncItemStackC2SPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncItemStackC2SPacket::new)
                .encoder(SyncItemStackC2SPacket::toBytes)
                .consumerMainThread(SyncItemStackC2SPacket::handle)
                .add();

        network.registerMessage(id(), ExtractingRecipeS2C.class,
                ExtractingRecipeS2C::encode,
                ExtractingRecipeS2C::decode,
                ExtractingRecipeS2C::onPacketReceived);

        INSTANCE = network;

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static <MSG> void sendMSGToAll(MSG message) {
        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            sendNonLocal(message, player);
        }
    }

    public static <MSG> void sendNonLocal(MSG msg, ServerPlayer player) {
        INSTANCE.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}

