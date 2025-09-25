package alabaster.crabbersdelight.common.worldgen.tree;

import alabaster.crabbersdelight.common.block.CoconutBlock;
import alabaster.crabbersdelight.common.registry.CDFoliagePlacerTypes;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class PalmFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<PalmFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance).apply(instance, PalmFoliagePlacer::new));

    public PalmFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return CDFoliagePlacerTypes.PALM_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter setter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos center = attachment.pos();

        // --- Tuft cross ABOVE frond level ---
        BlockPos tuftCenter = attachment.pos().above(0);
        placeLeafWithCoconut(level, setter, random, config, tuftCenter);
        placeLeafWithCoconut(level, setter, random, config, tuftCenter.north());
        placeLeafWithCoconut(level, setter, random, config, tuftCenter.south());
        placeLeafWithCoconut(level, setter, random, config, tuftCenter.east());
        placeLeafWithCoconut(level, setter, random, config, tuftCenter.west());

        // Cardinals
        createFrond(center, level, setter, random, config,  1,  0); // east
        createFrond(center, level, setter, random, config, -1,  0); // west
        createFrond(center, level, setter, random, config,  0,  1); // south
        createFrond(center, level, setter, random, config,  0, -1); // north

        // Diagonals
        createFrond(center, level, setter, random, config,  1,  1); // SE
        createFrond(center, level, setter, random, config,  1, -1); // NE
        createFrond(center, level, setter, random, config, -1,  1); // SW
        createFrond(center, level, setter, random, config, -1, -1); // NW
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return false;
    }

    private static void createFrond(BlockPos start, LevelSimulatedReader level, FoliageSetter setter, RandomSource random, TreeConfiguration config, int dx, int dz) {
        BlockPos.MutableBlockPos pos = start.mutable();

        // Step 1: initial drop 1 down
        pos.set(start.getX() + dx, start.getY() - 1, start.getZ() + dz);
        placeLeafWithCoconut(level, setter, random, config, pos);

        // Step 2: extend horizontally 2 blocks at same level
        for (int i = 2; i <= 3; i++) {
            pos.set(start.getX() + dx * i, start.getY() - 1, start.getZ() + dz * i);
            placeLeafWithCoconut(level, setter, random, config, pos);
        }

        // Step 3: tip leaf 1 lower
        pos.set(start.getX() + dx * 4, start.getY() - 2, start.getZ() + dz * 4);
        placeLeafWithCoconut(level, setter, random, config, pos);
    }

    private static void placeLeafWithCoconut(LevelSimulatedReader level,
                                             FoliageSetter setter,
                                             RandomSource random,
                                             TreeConfiguration config,
                                             BlockPos pos) {
        // Place persistent leaf
        BlockState leaf = config.foliageProvider.getState(random, pos);
        if (leaf.hasProperty(LeavesBlock.PERSISTENT)) {
            leaf = leaf.setValue(LeavesBlock.PERSISTENT, true);
        }
        setter.set(pos, leaf);

        // Chance to place coconut beneath any leaf
        if (random.nextInt(5) == 0) {
            BlockPos below = pos.below();
            if (level.isStateAtPosition(below, BlockBehaviour.BlockStateBase::isAir)) {
                setter.set(below, CDModBlocks.COCONUT.get().defaultBlockState().setValue(CoconutBlock.HANGING, true));
            }
        }
    }
}
