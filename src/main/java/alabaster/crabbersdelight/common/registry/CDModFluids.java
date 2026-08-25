package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.fluid.CDFluidType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CDModFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, CrabbersDelight.MODID);
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, CrabbersDelight.MODID);

    private static FluidType.Properties defaultProps() {
        return FluidType.Properties.create().viscosity(2000).density(1400);
    }

    private static FluidEntry registerFluid(String name, FluidType.Properties typeProps, int amount, Supplier<Item> bottleItem) {
        Supplier<FluidType> type = FLUID_TYPES.register(name, () -> new FluidType(typeProps));
        Supplier<CDFluidType>[] holders = new Supplier[2];
        holders[0] = FLUIDS.register(name, () ->
                new CDFluidType(new BaseFlowingFluid.Properties(type, holders[0], holders[1]),
                        true, bottleItem, amount));
        holders[1] = FLUIDS.register("flowing_" + name, () ->
                new CDFluidType(new BaseFlowingFluid.Properties(type, holders[0], holders[1]),
                        false, bottleItem, amount));
        return new FluidEntry(type, holders[0], holders[1]);
    }

    private static FluidEntry registerFluidWithBucket(String name, FluidType.Properties typeProps, int amount, Supplier<Item> bottleItem, Supplier<Item> bucketItem) {
        Supplier<FluidType> type = FLUID_TYPES.register(name, () -> new FluidType(typeProps));
        Supplier<CDFluidType>[] holders = new Supplier[2];
        holders[0] = FLUIDS.register(name, () ->
                new CDFluidType(
                        new BaseFlowingFluid.Properties(type, holders[0], holders[1]).bucket(bucketItem),
                        true, bottleItem, amount));
        holders[1] = FLUIDS.register("flowing_" + name, () ->
                new CDFluidType(
                        new BaseFlowingFluid.Properties(type, holders[0], holders[1]),
                        false, bottleItem, amount));
        return new FluidEntry(type, holders[0], holders[1]);
    }

    private static FluidEntry registerFluid(String name, FluidType.Properties typeProps, Supplier<Item> bottleItem) {
        return registerFluid(name, typeProps, 250, bottleItem);
    }

    private static FluidEntry registerFluid(String name, Supplier<Item> bottleItem) {
        return registerFluid(name, defaultProps(), 250, bottleItem);
    }

    public record FluidEntry(
            Supplier<FluidType> type,
            Supplier<CDFluidType> source,
            Supplier<CDFluidType> flowing
    ) {}

    public static final FluidEntry SEA_PICKLE_JUICE = registerFluid("sea_pickle_juice",
            CDModItems.SEA_PICKLE_JUICE);

    public static final FluidEntry COCONUT_MILK = registerFluid("coconut_milk",
            CDModItems.COCONUT_MILK);
}