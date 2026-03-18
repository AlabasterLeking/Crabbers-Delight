package alabaster.crabbersdelight.common.worldgen;

import alabaster.crabbersdelight.common.Config;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public record CDConditionalSpawnModifier(
        HolderSet<Biome> biomes,
        MobCategory category,
        EntityType<?> entityType,
        String configKey
) implements BiomeModifier {

    public static final MapCodec<CDConditionalSpawnModifier> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(m -> m.biomes()),
            MobCategory.CODEC.fieldOf("category").forGetter(m -> m.category()),
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(m -> m.entityType()),
            Codec.STRING.fieldOf("config_key").forGetter(m -> m.configKey())
    ).apply(i, CDConditionalSpawnModifier::new));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.ADD || !biomes.contains(biome)) return;
        boolean disabled = switch (configKey) {
            case "crab_spawns" -> !Config.GENERATE_CRAB_SPAWNS.get();
            default            -> false;
        };
        if (disabled) return;
        MobSpawnSettings.SpawnerData spawner = switch (configKey) {
            case "crab_spawns" -> new MobSpawnSettings.SpawnerData(
                    entityType,
                    Config.CRAB_SPAWN_WEIGHT.get(),
                    Config.CRAB_SPAWN_MIN.get(),
                    Config.CRAB_SPAWN_MAX.get()
            );
            default -> new MobSpawnSettings.SpawnerData(entityType, 10, 1, 3);
        };

        builder.getMobSpawnSettings().addSpawn(category, spawner);
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}