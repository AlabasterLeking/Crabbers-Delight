package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.worldgen.tree.PalmTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CDTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, CrabbersDelight.MODID);

    public static final Supplier<TrunkPlacerType<PalmTrunkPlacer>> PALM =
            TRUNK_PLACERS.register("palm", () -> new TrunkPlacerType<>(PalmTrunkPlacer.CODEC));
}
