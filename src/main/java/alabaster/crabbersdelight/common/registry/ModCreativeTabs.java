package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.function.Supplier;

public class ModCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CrabbersDelight.MODID);

    public static final Supplier<CreativeModeTab> TAB_CRABBERS_DELIGHT = CREATIVE_TAB.register(FarmersDelight.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.crabbersdelight"))
                    .icon(() -> new ItemStack(ModItems.RAW_CLAWSTER.get()))
                    .displayItems((parameters, output) -> ModItems.CREATIVE_TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get())))
                    .build()
            );
}