package alabaster.crabbersdelight.data.loot;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.BiConsumer;

public class CDGameplayLoot implements LootTableSubProvider {

    public CDGameplayLoot(HolderLookup.Provider lookupProvider) {
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> writer) {
        generateCrabTrapLoot(writer);
        generateFishingBaitLoot(writer);
    }

    private void generateCrabTrapLoot(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> writer) {
        writer.accept(crabTrapKey("air"), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(item(CDModItems.RAW_CRAB.get(), 1))
                        .add(item(CDModItems.RAW_CLAWSTER.get(), 1))
                        .add(item(CDModItems.RAW_SHRIMP.get(), 1))
                        .add(item(CDModItems.CLAM.get(), 1))
                        .add(item(Items.STICK, 4))
                        .add(item(Items.KELP, 4))
                        .add(item(CDModItems.CAN.get(), 4))
                        .add(item(CDModItems.FISH_BONES.get(), 4))
                        .add(item(CDModItems.MESSAGE_BOTTLE.get(), 1))));

        writer.accept(crabTrapKey("bucket_of_clam_chum"), singleItemTable(CDModItems.CLAM.get()));
        writer.accept(crabTrapKey("bucket_of_clawster_chum"), singleItemTable(CDModItems.RAW_CLAWSTER.get()));
        writer.accept(crabTrapKey("bucket_of_crab_chum"), singleItemTable(CDModItems.RAW_CRAB.get()));
        writer.accept(crabTrapKey("bucket_of_shrimp_chum"), singleItemTable(CDModItems.RAW_SHRIMP.get()));

        writer.accept(crabTrapKey("cod"), fishBaitJunkTable(CDModItems.RAW_CRAB.get(), false));
        writer.accept(crabTrapKey("salmon"), fishBaitJunkTable(CDModItems.RAW_CLAWSTER.get(), false));
        writer.accept(crabTrapKey("pufferfish"), fishBaitJunkTable(CDModItems.CLAM.get(), true));
        writer.accept(crabTrapKey("tropical_fish"), fishBaitJunkTable(CDModItems.RAW_SHRIMP.get(), true));
    }

    private void generateFishingBaitLoot(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> writer) {
        writer.accept(baitKey("bucket_of_crab_chum"), singleItemTable(CDModItems.RAW_CRAB.get()));
        writer.accept(baitKey("bucket_of_clawster_chum"), singleItemTable(CDModItems.RAW_CLAWSTER.get()));
        writer.accept(baitKey("bucket_of_clam_chum"), singleItemTable(CDModItems.CLAM.get()));
        writer.accept(baitKey("bucket_of_shrimp_chum"), singleItemTable(CDModItems.RAW_SHRIMP.get()));

        writer.accept(baitKey("cod"), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(item(Items.SALMON, 8))
                        .add(item(Items.STICK, 3))
                        .add(item(CDModItems.CAN.get(), 2))
                        .add(item(CDModItems.MESSAGE_BOTTLE.get(), 1))));

        writer.accept(baitKey("salmon"), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(item(Items.COD, 8))
                        .add(item(Items.KELP, 3))
                        .add(item(CDModItems.FISH_BONES.get(), 2))
                        .add(item(ModItems.ROPE.get(), 1))));

        writer.accept(baitKey("pufferfish"), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(item(Items.TROPICAL_FISH, 6))
                        .add(item(CDModItems.SEASHELLS.get(), 3))
                        .add(item(Items.NAUTILUS_SHELL, 2))
                        .add(item(CDModItems.CRAB_CLAW.get(), 1))));

        writer.accept(baitKey("tropical_fish"), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(item(Items.PUFFERFISH, 6))
                        .add(item(CDModItems.CORAL_FRAGMENTS.get(), 3))
                        .add(item(CDModItems.COCONUT.get(), 2))
                        .add(item(CDModItems.MESSAGE_BOTTLE.get(), 1))));

        writer.accept(baitKey("worm"), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(item(Items.COD, 6))
                        .add(item(Items.SALMON, 6))
                        .add(item(Items.TROPICAL_FISH, 3))
                        .add(item(Items.PUFFERFISH, 3))));
    }

    private static LootTable.Builder singleItemTable(ItemLike item) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(item(item, 1)));
    }

    private static LootTable.Builder fishBaitJunkTable(ItemLike crustacean, boolean withCoralFragments) {
        LootPool.Builder pool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(item(crustacean, 8))
                .add(item(Items.STICK, 3))
                .add(item(Items.NAUTILUS_SHELL, 4))
                .add(item(ModItems.ROPE.get(), 3))
                .add(item(Items.COPPER_INGOT, 1))
                .add(item(CDModItems.CAN.get(), 2))
                .add(item(CDModItems.FISH_BONES.get(), 2))
                .add(item(CDModItems.MESSAGE_BOTTLE.get(), 1));

        if (withCoralFragments) {
            pool.add(item(CDModItems.CORAL_FRAGMENTS.get(), 3));
        }

        return LootTable.lootTable().withPool(pool);
    }

    private static LootPoolSingletonContainer.Builder<?> item(ItemLike item, int weight) {
        return LootItem.lootTableItem(item).setWeight(weight);
    }

    private static ResourceKey<LootTable> crabTrapKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, CrabbersDelight.modPrefix("gameplay/crab_trap_loot/" + id));
    }

    private static ResourceKey<LootTable> baitKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, CrabbersDelight.modPrefix("gameplay/fishing_bait_loot/" + id));
    }
}