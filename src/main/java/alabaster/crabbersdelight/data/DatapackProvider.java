package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDBottleMessages;
import alabaster.crabbersdelight.common.registry.CDModDatapackRegistries;
import alabaster.crabbersdelight.common.registry.CDTrimMaterials;
import alabaster.crabbersdelight.common.worldgen.CDBiomeModifiers;
import alabaster.crabbersdelight.common.worldgen.CDConfiguredFeatures;
import alabaster.crabbersdelight.common.worldgen.CDPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.TRIM_MATERIAL, CDTrimMaterials::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, CDConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, CDPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CDBiomeModifiers::bootstrap)
            .add(CDModDatapackRegistries.BOTTLE_MESSAGE_REGISTRY_KEY, CDBottleMessages::bootstrap);

    public DatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(CrabbersDelight.MODID));
    }
}