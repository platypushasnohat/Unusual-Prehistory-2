package com.barlinc.unusual_prehistory.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class AddLootTableModifier implements IGlobalLootModifier {

    public static final Supplier<Codec<AddLootTableModifier>> CODEC = () ->
            RecordCodecBuilder.create(instance -> instance.group(
                            LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(modifier -> modifier.conditions),
                            ResourceLocation.CODEC.fieldOf("lootTable").forGetter((modifier) -> modifier.lootTable),
                            Codec.BOOL.optionalFieldOf("replace", false).forGetter((modifier) -> modifier.replace),
                            Codec.FLOAT.optionalFieldOf("replaceChance", 0.0F).forGetter(modifier -> modifier.replaceChance))
                    .apply(instance, AddLootTableModifier::new));

    private final LootItemCondition[] conditions;
    private final Predicate<LootContext> orConditions;
    private final ResourceLocation lootTable;
    private final boolean replace;
    private final float replaceChance;

    protected AddLootTableModifier(LootItemCondition[] conditions, ResourceLocation lootTable, boolean replace, float replaceChance) {
        this.lootTable = lootTable;
        this.conditions = conditions;
        this.orConditions = LootItemConditions.orConditions(conditions);
        this.replace = replace;
        this.replaceChance = replaceChance;
    }

    @NotNull
    @Override
    public ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        return this.orConditions.test(context) ? this.doApply(generatedLoot, context) : generatedLoot;
    }

    @NotNull
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LootTable extraTable = context.getResolver().getLootTable(this.lootTable);
        if (replace && context.getRandom().nextFloat() < replaceChance) generatedLoot.clear();
        extraTable.getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), generatedLoot::add));
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
