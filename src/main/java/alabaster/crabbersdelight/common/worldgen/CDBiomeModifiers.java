package alabaster.crabbersdelight.common.worldgen;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class CDBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_TREE_PALM = registerKey("add_tree_palm");
    public static final ResourceKey<BiomeModifier> ADD_SEASHELLS = registerKey("add_seashells");
    public static final ResourceKey<BiomeModifier> ADD_SEASHELLS_UNDERWATER = registerKey("add_seashells_underwater");
    public static final ResourceKey<BiomeModifier> ADD_CRAB_SPAWNS = registerKey("add_crab_spawns");

    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, CrabbersDelight.MODID);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<CDConditionalSpawnModifier>> CONDITIONAL_SPAWN =
            BIOME_MODIFIER_SERIALIZERS.register("conditional_spawn", () -> CDConditionalSpawnModifier.CODEC);


    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_TREE_PALM, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BEACH)),
                HolderSet.direct(placedFeatures.getOrThrow(CDPlacedFeatures.PALM_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_SEASHELLS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.BEACH)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(CDPlacedFeatures.SEASHELLS_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_SEASHELLS_UNDERWATER, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.OCEAN),
                        biomes.getOrThrow(Biomes.WARM_OCEAN),
                        biomes.getOrThrow(Biomes.LUKEWARM_OCEAN),
                        biomes.getOrThrow(Biomes.DEEP_LUKEWARM_OCEAN)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(CDPlacedFeatures.SEASHELLS_PLACED_KEY_UNDERWATER)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(ADD_CRAB_SPAWNS, new CDConditionalSpawnModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.MANGROVE_SWAMP),
                        biomes.getOrThrow(Biomes.SWAMP),
                        biomes.getOrThrow(Biomes.BEACH),
                        biomes.getOrThrow(Biomes.STONY_SHORE),
                        biomes.getOrThrow(Biomes.SNOWY_BEACH)
                ),
                MobCategory.CREATURE,
                CDModEntities.CRAB.get(),
                "crab_spawns"
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, name));
    }
}
