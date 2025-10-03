package alabaster.crabbersdelight.common.worldgen;

import alabaster.crabbersdelight.common.Config;
import alabaster.crabbersdelight.common.block.SeashellBlock;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.utils.CDStateProviders;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class RandomSeashellStateProvider extends BlockStateProvider {
    private final boolean waterlogged;

    public RandomSeashellStateProvider(boolean waterlogged) {
        this.waterlogged = waterlogged;
    }

    public static final MapCodec<RandomSeashellStateProvider> CODEC =
            com.mojang.serialization.codecs.RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            com.mojang.serialization.Codec.BOOL
                                    .optionalFieldOf("waterlogged", false)
                                    .forGetter(p -> p.waterlogged)
                    ).apply(instance, RandomSeashellStateProvider::new)
            );

    @Override
    protected BlockStateProviderType<?> type() {
        return CDStateProviders.RANDOM_SEASHELL.get();
    }

    @Override
    public BlockState getState(RandomSource random, BlockPos pos) {
        int variant = random.nextInt(Config.SEASHELL_VARIANT_COUNT.get());
        Direction facing = Direction.Plane.HORIZONTAL.getRandomDirection(random);

        return CDModBlocks.SEASHELLS.get().defaultBlockState()
                .setValue(SeashellBlock.VARIANT, variant)
                .setValue(SeashellBlock.FACING, facing)
                .setValue(SeashellBlock.WATERLOGGED, waterlogged);
    }
}
