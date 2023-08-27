package alabaster.crabbersdelight.data.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.storage.loot.LootTable;
import alabaster.crabbersdelight.common.registry.ModBlocks;

import java.util.HashSet;
import java.util.Set;

public class CDBlockLoot extends BlockLootSubProvider
{
    private final Set<Block> generatedLootTables = new HashSet<>();

    public CDBlockLoot() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.CRAB_BARREL.get());
        dropSelf(ModBlocks.CLAM_BARREL.get());
        dropSelf(ModBlocks.CLAWSTER_BARREL.get());
        dropSelf(ModBlocks.SHRIMP_BARREL.get());
        dropSelf(ModBlocks.NAUTILUS_SHELL_BLOCK.get());
        dropSelf(ModBlocks.PEARL_BLOCK.get());
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        this.generatedLootTables.add(block);
        this.map.put(block.getLootTable(), builder);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return generatedLootTables;
    }
}