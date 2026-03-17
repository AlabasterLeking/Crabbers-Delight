package alabaster.crabbersdelight.common.worldgen.placement;

import alabaster.crabbersdelight.common.Config;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class CDConfigPlacementModifier extends PlacementModifier {

    public static final CDConfigPlacementModifier PALM = new CDConfigPlacementModifier("palm");
    public static final CDConfigPlacementModifier SEASHELL = new CDConfigPlacementModifier("seashell");
    public static final CDConfigPlacementModifier SEASHELL_UNDERWATER = new CDConfigPlacementModifier("seashell_underwater");

    public static final MapCodec<CDConfigPlacementModifier> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.STRING.fieldOf("config_key").forGetter(m -> m.configKey)
    ).apply(i, CDConfigPlacementModifier::new));

    private final String configKey;

    public CDConfigPlacementModifier(String configKey) {
        this.configKey = configKey;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
        return switch (configKey) {
            case "palm" -> {
                if (!Config.GENERATE_PALM_TREES.get()) yield Stream.empty();
                int chance = Config.CHANCE_PALM_TREES.get();
                yield chance > 0 && random.nextInt(chance) == 0 ? Stream.of(pos) : Stream.empty();
            }
            case "seashell" -> {
                if (!Config.GENERATE_SEASHELLS.get()) yield Stream.empty();
                int chance = Config.CHANCE_SEASHELLS.get();
                yield chance > 0 && random.nextInt(chance) == 0 ? Stream.of(pos) : Stream.empty();
            }
            case "seashell_underwater" -> {
                if (!Config.GENERATE_SEASHELLS.get()) yield Stream.empty();
                int chance = Config.CHANCE_SEASHELLS_UNDERWATER.get();
                yield chance > 0 && random.nextInt(chance) == 0 ? Stream.of(pos) : Stream.empty();
            }
            default -> Stream.of(pos);
        };
    }

    @Override
    public PlacementModifierType<?> type() {
        return CDPlacementModifiers.CONFIG_FILTER.get();
    }
}