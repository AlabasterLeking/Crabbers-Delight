package alabaster.crabbersdelight.common.worldgen;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.worldgen.placement.CDConfigPlacementModifier;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class CDPlacedFeatures {
    public static final ResourceKey<PlacedFeature> PALM_PLACED_KEY = registerKey("palm_placed");
    public static final ResourceKey<PlacedFeature> SEASHELLS_PLACED_KEY = registerKey("seashells_placed");
    public static final ResourceKey<PlacedFeature> SEASHELLS_PLACED_KEY_UNDERWATER = registerKey("seashells_placed_underwater");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // Palm tree — CDConfigPlacementModifier replaces countExtra + RarityFilter,
        // handling both the enable flag and the chance value from config at generation time
        register(context, PALM_PLACED_KEY, configuredFeatures.getOrThrow(CDConfiguredFeatures.PALM_KEY),
                VegetationPlacements.treePlacement(
                        CDConfigPlacementModifier.PALM,
                        CDModBlocks.PALM_SAPLING.get()));

        // Beach seashells — config modifier first so disabled check short-circuits
        // before any other placement work is done
        register(context, SEASHELLS_PLACED_KEY, configuredFeatures.getOrThrow(CDConfiguredFeatures.SEASHELLS_KEY),
                List.of(
                        CDConfigPlacementModifier.SEASHELL,
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.SAND)
                        ),
                        BiomeFilter.biome()
                ));

        // Underwater seashells
        register(context, SEASHELLS_PLACED_KEY_UNDERWATER, configuredFeatures.getOrThrow(CDConfiguredFeatures.SEASHELLS_KEY_UNDERWATER),
                List.of(
                        CDConfigPlacementModifier.SEASHELL_UNDERWATER,
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.SAND)
                        ),
                        BiomeFilter.biome()
                ));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}