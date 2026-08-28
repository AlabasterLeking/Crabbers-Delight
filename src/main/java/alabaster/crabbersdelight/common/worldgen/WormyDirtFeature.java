package alabaster.crabbersdelight.common.worldgen;

import alabaster.crabbersdelight.common.registry.CDModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WormyDirtFeature extends Feature<NoneFeatureConfiguration> {
    private static final int MIN_GROUP_SIZE = 1;
    private static final int MAX_GROUP_SIZE = 3;
    private static final int SEARCH_DEPTH_BELOW_ORIGIN = 8;

    public WormyDirtFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        int minY = Math.max(level.getMinBuildHeight(), origin.getY() - SEARCH_DEPTH_BELOW_ORIGIN);

        BlockPos chosen = null;
        int dirtSeen = 0;
        BlockPos.MutableBlockPos cursor = origin.mutable();
        for (int y = origin.getY(); y >= minY; y--) {
            cursor.setY(y);
            if (level.getBlockState(cursor).is(Blocks.DIRT)) {
                dirtSeen++;
                if (random.nextInt(dirtSeen) == 0) {
                    chosen = cursor.immutable();
                }
            }
        }

        if (chosen == null) {
            return false;
        }

        placeGroup(level, chosen, random);
        return true;
    }

    private void placeGroup(WorldGenLevel level, BlockPos start, RandomSource random) {
        int groupSize = MIN_GROUP_SIZE + random.nextInt(MAX_GROUP_SIZE - MIN_GROUP_SIZE + 1);
        BlockPos.MutableBlockPos cursor = start.mutable();
        for (int i = 0; i < groupSize; i++) {
            if (level.getBlockState(cursor).is(Blocks.DIRT)) {
                setBlock(level, cursor, CDModBlocks.WORMY_DIRT.get().defaultBlockState());
            }
            Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
            cursor.move(dir);
        }
    }
}