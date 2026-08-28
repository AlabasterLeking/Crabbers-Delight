package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.worldgen.WormyDirtFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CDModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(BuiltInRegistries.FEATURE, CrabbersDelight.MODID);

    public static final Supplier<WormyDirtFeature> WORMY_DIRT_FEATURE =
            FEATURES.register("wormy_dirt", WormyDirtFeature::new);
}