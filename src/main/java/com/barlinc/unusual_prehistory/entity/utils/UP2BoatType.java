package com.barlinc.unusual_prehistory.entity.utils;

import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public interface UP2BoatType {

    UP2BoatType.Type getUP2BoatType();

    enum Type {
        GINKGO("ginkgo", UP2Blocks.GINKGO_PLANKS, UP2Items.GINKGO_BOAT, UP2Items.GINKGO_CHEST_BOAT),
        LEPIDODENDRON("lepidodendron", UP2Blocks.LEPIDODENDRON_PLANKS, UP2Items.LEPIDODENDRON_BOAT, UP2Items.LEPIDODENDRON_CHEST_BOAT);

        private final String name;
        private final Supplier<Block> plankSupplier;
        private final Supplier<Item> dropSupplier;
        private final Supplier<Item> chestDropSupplier;

        Type(String name, Supplier<Block> plankSupplier, Supplier<Item> dropSupplier, Supplier<Item> chestDropSupplier) {
            this.name = name;
            this.plankSupplier = plankSupplier;
            this.dropSupplier = dropSupplier;
            this.chestDropSupplier = chestDropSupplier;
        }

        public String getName() {
            return this.name;
        }

        public Supplier<Block> getPlankSupplier() {
            return this.plankSupplier;
        }

        public Supplier<Item> getDropSupplier() {
            return this.dropSupplier;
        }

        public Supplier<Item> getChestDropSupplier() {
            return this.chestDropSupplier;
        }

        public String toString() {
            return this.name;
        }

        public static Type byId(int id) {
            Type[] boatEntityType = values();
            if (id < 0 || id >= boatEntityType.length) {
                id = 0;
            }
            return boatEntityType[id];
        }

        public static Type byName(String name) {
            Type[] boatEntityType = values();

            for (Type pfBoatTypes : boatEntityType) {
                if (pfBoatTypes.getName().equals(name)) {
                    return pfBoatTypes;
                }
            }
            return boatEntityType[0];
        }
    }
}
