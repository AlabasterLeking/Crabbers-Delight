package alabaster.crabbersdelight.common.worldgen.tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class PalmTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<PalmTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            trunkPlacerParts(instance)
                    .and(IntProvider.CODEC.fieldOf("bend_length").forGetter(p -> p.bendLength))
                    .apply(instance, PalmTrunkPlacer::new));

    private final IntProvider bendLength;

    public PalmTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, IntProvider bendLength) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
        this.bendLength = bendLength;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return alabaster.crabbersdelight.common.registry.CDTrunkPlacerTypes.PALM.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            int height,
            BlockPos pos,
            TreeConfiguration config) {

        int dx = random.nextBoolean() ? 1 : -1;
        int dz = random.nextBoolean() ? 1 : -1;
        int bend = bendLength.sample(random);

        BlockPos.MutableBlockPos cursor = pos.mutable();
        for (int y = 0; y < height; y++) {
            if (y > height / 3 && bend > 0) {
                if (random.nextInt(3) == 0) {
                    cursor.move(dx, 0, dz);
                    bend--;
                }
            }

            placeLog(level, blockSetter, random, cursor, config);
            cursor.move(0, 1, 0);
        }

        return List.of(new FoliagePlacer.FoliageAttachment(cursor.below(), 0, false));
    }
}
