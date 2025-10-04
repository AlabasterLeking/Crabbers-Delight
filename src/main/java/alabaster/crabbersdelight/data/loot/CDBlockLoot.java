package alabaster.crabbersdelight.data.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.storage.loot.LootTable;
import alabaster.crabbersdelight.common.registry.CDModBlocks;

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
        dropSelf(CDModBlocks.COD_BARREL.get());
        dropSelf(CDModBlocks.SALMON_BARREL.get());
        dropSelf(CDModBlocks.PUFFERFISH_BARREL.get());
        dropSelf(CDModBlocks.TROPICAL_FISH_BARREL.get());
        dropSelf(CDModBlocks.CRAB_BARREL.get());
        dropSelf(CDModBlocks.CLAM_BARREL.get());
        dropSelf(CDModBlocks.CLAWSTER_BARREL.get());
        dropSelf(CDModBlocks.SHRIMP_BARREL.get());
        dropSelf(CDModBlocks.SQUID_BARREL.get());
        dropSelf(CDModBlocks.GLOW_SQUID_BARREL.get());
        dropSelf(CDModBlocks.FROG_LEG_BARREL.get());
        dropSelf(CDModBlocks.SCUTE_BLOCK.get());
        dropSelf(CDModBlocks.NAUTILUS_SHELL_BLOCK.get());
        dropSelf(CDModBlocks.PEARL_BLOCK.get());
        dropSelf(CDModBlocks.SEASHELLS.get());

        dropSelf(CDModBlocks.PALM_LOG.get());
        dropSelf(CDModBlocks.STRIPPED_PALM_LOG.get());
        dropSelf(CDModBlocks.PALM_WOOD.get());
        dropSelf(CDModBlocks.STRIPPED_PALM_WOOD.get());
        dropSelf(CDModBlocks.PALM_SAPLING.get());
        dropSelf(CDModBlocks.PALM_PLANKS.get());
        dropSelf(CDModBlocks.PALM_STAIRS.get());
        dropSelf(CDModBlocks.PALM_TRAPDOOR.get());
        dropSelf(CDModBlocks.PALM_FENCE.get());
        dropSelf(CDModBlocks.PALM_FENCE_GATE.get());
        dropSelf(CDModBlocks.PALM_BUTTON.get());
        dropSelf(CDModBlocks.PALM_PRESSURE_PLATE.get());
        dropSelf(CDModBlocks.PALM_SIGN.get());
        dropSelf(CDModBlocks.PALM_HANGING_SIGN.get());
        dropSelf(CDModBlocks.PALM_WALL_SIGN.get());
        dropSelf(CDModBlocks.PALM_WALL_HANGING_SIGN.get());
        dropSelf(CDModBlocks.PALM_CABINET.get());
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