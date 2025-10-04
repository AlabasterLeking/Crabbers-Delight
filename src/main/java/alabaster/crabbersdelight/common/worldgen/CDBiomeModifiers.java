package alabaster.crabbersdelight.common.worldgen;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraftforge.registries.ForgeRegistries;

public class CDBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_TREE_PALM = registerKey("add_tree_palm");
    public static final ResourceKey<BiomeModifier> ADD_SEASHELLS = registerKey("add_seashells");
    public static final ResourceKey<BiomeModifier> ADD_SEASHELLS_UNDERWATER = registerKey("add_seashells_underwater");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_TREE_PALM, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BEACH)),
                HolderSet.direct(placedFeatures.getOrThrow(CDPlacedFeatures.PALM_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_SEASHELLS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.BEACH)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(CDPlacedFeatures.SEASHELLS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_SEASHELLS_UNDERWATER, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.OCEAN),
                        biomes.getOrThrow(Biomes.WARM_OCEAN),
                        biomes.getOrThrow(Biomes.LUKEWARM_OCEAN),
                        biomes.getOrThrow(Biomes.DEEP_LUKEWARM_OCEAN)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(CDPlacedFeatures.SEASHELLS_PLACED_KEY_UNDERWATER)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(CrabbersDelight.MODID, name));
    }
}
