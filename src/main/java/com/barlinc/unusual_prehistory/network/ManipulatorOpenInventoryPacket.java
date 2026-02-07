package com.barlinc.unusual_prehistory.network;

import java.util.function.Supplier;

import com.barlinc.unusual_prehistory.events.ClientForgeEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class ManipulatorOpenInventoryPacket {

    private final int id;
    private final int size;
    private final int entityId;

    public ManipulatorOpenInventoryPacket(int id, int size, int entityId) {
        this.id = id;
        this.size = size;
        this.entityId = entityId;
    }
    
    public static ManipulatorOpenInventoryPacket decode(FriendlyByteBuf buf) {
        return new ManipulatorOpenInventoryPacket(buf.readUnsignedByte(), buf.readVarInt(), buf.readInt());
    }

    public static void encode(ManipulatorOpenInventoryPacket msg, FriendlyByteBuf buf) {
        buf.writeByte(msg.id);
        buf.writeVarInt(msg.size);
        buf.writeInt(msg.entityId);
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public int getEntityId() {
        return this.entityId;
    }

    public static void handle(ManipulatorOpenInventoryPacket msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ClientForgeEvents.openManipulatorInventory(msg));
        context.get().setPacketHandled(true);
    }
}