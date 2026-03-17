package alabaster.crabbersdelight.common.worldgen.placement;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CDPlacementModifiers {

    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS =
            DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, CrabbersDelight.MODID);

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<CDConfigPlacementModifier>> CONFIG_FILTER =
            PLACEMENT_MODIFIERS.register("config_filter", () -> () -> CDConfigPlacementModifier.CODEC);
}