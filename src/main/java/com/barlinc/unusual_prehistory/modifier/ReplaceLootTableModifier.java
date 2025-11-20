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
public class ReplaceLootTableModifier extends LootModifier {

    public static final Supplier<Codec<ReplaceLootTableModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(instance -> codecStart(instance)
                    .and(ResourceLocation.CODEC.fieldOf("lootTable").forGetter((modifier) -> modifier.lootTable))
                    .and(Codec.FLOAT.optionalFieldOf("chance", 1.0F).forGetter((modifier) -> modifier.chance))
                    .apply(instance, ReplaceLootTableModifier::new)
            )
    );

    private final ResourceLocation lootTable;
    private final float chance;

    protected ReplaceLootTableModifier(LootItemCondition[] conditions, ResourceLocation lootTable, float chance) {
        super(conditions);
        this.lootTable = lootTable;
        this.chance = chance;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LootTable extraTable = context.getResolver().getLootTable(this.lootTable);
        if (context.getRandom().nextFloat() < chance) {
            generatedLoot.clear();
            extraTable.getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), generatedLoot::add));
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
