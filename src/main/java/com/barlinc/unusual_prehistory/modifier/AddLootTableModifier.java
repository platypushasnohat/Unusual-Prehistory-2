package com.barlinc.unusual_prehistory.modifier;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class AddLootTableModifier extends LootModifier {

    public static final Supplier<Codec<AddLootTableModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(instance -> codecStart(instance)
                    .and(ResourceLocation.CODEC.fieldOf("lootTable").forGetter((modifier) -> modifier.lootTable))
                    .apply(instance, AddLootTableModifier::new)
            )
    );

    private final ResourceLocation lootTable;

    protected AddLootTableModifier(LootItemCondition[] conditions, ResourceLocation lootTable) {
        super(conditions);
        this.lootTable = lootTable;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LootTable extraTable = context.getResolver().getLootTable(this.lootTable);
        extraTable.getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), generatedLoot::add));
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
