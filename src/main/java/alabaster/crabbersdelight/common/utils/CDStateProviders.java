package alabaster.crabbersdelight.common.utils;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.worldgen.RandomSeashellStateProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CDStateProviders {
    public static final DeferredRegister<BlockStateProviderType<?>> PROVIDERS =
            DeferredRegister.create(Registries.BLOCK_STATE_PROVIDER_TYPE, CrabbersDelight.MODID);

    public static final Supplier<BlockStateProviderType<RandomSeashellStateProvider>> RANDOM_SEASHELL =
            PROVIDERS.register("random_seashell",
                    () -> new BlockStateProviderType<>(RandomSeashellStateProvider.CODEC));

}