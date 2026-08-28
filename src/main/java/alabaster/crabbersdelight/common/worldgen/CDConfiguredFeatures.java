package alabaster.crabbersdelight.common.worldgen;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.registry.CDModFeatures;
import alabaster.crabbersdelight.common.worldgen.tree.PalmFoliagePlacer;
import alabaster.crabbersdelight.common.worldgen.tree.PalmTrunkPlacer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class CDConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALM_KEY = registerKey("palm");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SEASHELLS_KEY = registerKey("seashells_beach");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SEASHELLS_KEY_UNDERWATER = registerKey("seashells_underwater");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WORMY_DIRT_KEY = registerKey("wormy_dirt");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        // Palm tree registration
        register(context, PALM_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(CDModBlocks.PALM_LOG.get()),
                new PalmTrunkPlacer(5, 3, 3, ConstantInt.of(3)),

                BlockStateProvider.simple(CDModBlocks.PALM_LEAVES.get()),
                new PalmFoliagePlacer(ConstantInt.of(2), ConstantInt.of(3)),

                new TwoLayersFeatureSize(1, 0, 2))
                .dirt(BlockStateProvider.simple(Blocks.SAND))
                .build());

        // Beach seashells (dry)
        BlockPredicate beachPlacement = BlockPredicate.allOf(
                BlockPredicate.matchesBlocks(BlockPos.ZERO.below(), Blocks.SAND),
                BlockPredicate.matchesBlocks(BlockPos.ZERO, List.of(Blocks.AIR))
        );
        BlockStateProvider beachProvider = new RandomSeashellStateProvider(false);
        RandomPatchConfiguration beachPatch = new RandomPatchConfiguration(
                10, 4, 1,
                PlacementUtils.filtered(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(beachProvider),
                        beachPlacement
                )
        );
        register(context, SEASHELLS_KEY, Feature.RANDOM_PATCH, beachPatch);

        // Underwater seashells
        BlockPredicate underwaterPlacement = BlockPredicate.allOf(
                BlockPredicate.matchesBlocks(BlockPos.ZERO.below(), Blocks.SAND),
                BlockPredicate.matchesBlocks(BlockPos.ZERO, List.of(Blocks.WATER))
        );
        BlockStateProvider underwaterProvider = new RandomSeashellStateProvider(true);
        RandomPatchConfiguration underwaterPatch = new RandomPatchConfiguration(
                10, 4, 1,
                PlacementUtils.filtered(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(underwaterProvider),
                        underwaterPlacement
                )
        );
        register(context, SEASHELLS_KEY_UNDERWATER, Feature.RANDOM_PATCH, underwaterPatch);

        // Wormy dirt
        register(context, WORMY_DIRT_KEY, CDModFeatures.WORMY_DIRT_FEATURE.get(), NoneFeatureConfiguration.INSTANCE);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>>
    void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}