package com.unusualmodding.unusual_prehistory.messages;

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

public class ExtractorRecipeS2C {

    private static final Codec<Map<Item, List<ItemWeightedPairCodec>>> MAPPER =
            Codec.unboundedMap(
                    BuiltInRegistries.ITEM.byNameCodec(),
                    ItemWeightedPairCodec.CODEC.listOf()
            ).xmap(ItemWeightedPairCodec::convertToMap, ItemWeightedPairCodec::convertFromMap).orElse(e -> {
                        UnusualPrehistory2.LOGGER.error("Failed to parse Extractor Entries can't send packet! Due to " + e);
                    }, new HashMap<>());

    public static Map<Item, List<ItemWeightedPairCodec>> SYNCED_DATA = new HashMap<>();
    private final Map<Item, List<ItemWeightedPairCodec>> map;

    public ExtractorRecipeS2C(Map<Item, List<ItemWeightedPairCodec>> map) {
        this.map = map;
    }

    public void encode(FriendlyByteBuf buffer) {
        CompoundTag encodedTag = (CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, this.map).result().orElse(new CompoundTag()));
        buffer.writeNbt(encodedTag);
    }

    public static ExtractorRecipeS2C decode(FriendlyByteBuf buffer) {
        CompoundTag receivedTag = buffer.readNbt();
        Map<Item, List<ItemWeightedPairCodec>> decodedMap = MAPPER.parse(NbtOps.INSTANCE, receivedTag).result().orElse(new HashMap<>());
        return new ExtractorRecipeS2C(decodedMap);
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


