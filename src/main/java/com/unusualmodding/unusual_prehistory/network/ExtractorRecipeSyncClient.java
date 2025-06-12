package com.unusualmodding.unusual_prehistory.network;

import com.mojang.serialization.Codec;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.recipes.extractor.ExtractorRecipeJsonManager;
import com.unusualmodding.unusual_prehistory.recipes.extractor.ItemWeightedPairCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ExtractorRecipeSyncClient {

    private static final Codec<Map<Item, List<ItemWeightedPairCodec>>> MAPPER = Codec.unboundedMap(BuiltInRegistries.ITEM.byNameCodec(), ItemWeightedPairCodec.CODEC.listOf()).xmap(ItemWeightedPairCodec::convertToMap, ItemWeightedPairCodec::convertFromMap).orElse(reason -> {
        UnusualPrehistory2.LOGGER.error("Failed to parse extracting entries can't send packet! Due to " + reason);
    }, new HashMap<>());

    public static Map<Item, List<ItemWeightedPairCodec>> SYNCED_DATA = new HashMap<>();
    private final Map<Item, List<ItemWeightedPairCodec>> map;

    public ExtractorRecipeSyncClient(Map<Item, List<ItemWeightedPairCodec>> map) {
        this.map = map;
    }

    public void encode(FriendlyByteBuf buffer) {
        CompoundTag encodedTag = (CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, this.map).result().orElse(new CompoundTag()));
        buffer.writeNbt(encodedTag);
    }

    public static ExtractorRecipeSyncClient decode(FriendlyByteBuf buffer) {
        CompoundTag receivedTag = buffer.readNbt();
        Map<Item, List<ItemWeightedPairCodec>> decodedMap = MAPPER.parse(NbtOps.INSTANCE, receivedTag).result().orElse(new HashMap<>());
        return new ExtractorRecipeSyncClient(decodedMap);
    }

    public void onPacketReceived(Supplier<NetworkEvent.Context> contextGetter) {
        NetworkEvent.Context context = contextGetter.get();
        context.enqueueWork(this::handlePacketOnMainThread);
        context.setPacketHandled(true);
    }

    private void handlePacketOnMainThread() {
        SYNCED_DATA = this.map;
        ExtractorRecipeJsonManager.setRecipeList(SYNCED_DATA);
    }
}


