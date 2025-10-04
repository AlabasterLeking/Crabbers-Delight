package alabaster.crabbersdelight.common.worldgen.tree;

import alabaster.crabbersdelight.common.block.CoconutBlock;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.registry.CDTrunkPlacerTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class PalmTrunkPlacer extends TrunkPlacer {
    public static final Codec<PalmTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
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
        return CDTrunkPlacerTypes.PALM.get();
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

        // Rare ground coconuts
        final int CHANCE = 20;        // 1 in CHANCE chance per tree (100 -> ~1%)
        final int RADIUS = 3;          // Search radius around trunk base
        final int MAX_COCONUTS = 3;    // Coconuts to try place if the roll succeeds
        final int MAX_ATTEMPTS = 10;   // Attempts per coconut to find a valid spot
        final int MAX_DESCEND = 6;     // How many blocks down to search for surface (to handle uneven ground)

        if (random.nextInt(CHANCE) == 0) {
            int coconutCount = 1 + random.nextInt(MAX_COCONUTS); // 1..MAX_COCONUTS
            for (int i = 0; i < coconutCount; i++) {
                for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
                    int rx = pos.getX() + random.nextInt(RADIUS * 2 + 1) - RADIUS;
                    int rz = pos.getZ() + random.nextInt(RADIUS * 2 + 1) - RADIUS;
                    BlockPos candidate = new BlockPos(rx, pos.getY(), rz);
                    boolean placed = false;
                    for (int dy = 0; dy >= -MAX_DESCEND; dy--) {
                        BlockPos target = candidate.offset(0, dy, 0);
                        if (level.isStateAtPosition(target, BlockBehaviour.BlockStateBase::isAir)
                                && !level.isStateAtPosition(target.below(), BlockBehaviour.BlockStateBase::isAir)) {
                            blockSetter.accept(target, CDModBlocks.COCONUT.get().defaultBlockState()
                                    .setValue(CoconutBlock.HANGING, false));
                            placed = true;
                            break;
                        }
                    }
                    if (placed) break;
                }
            }
        }

        return List.of(new FoliagePlacer.FoliageAttachment(cursor, 0, false));
    }
}
