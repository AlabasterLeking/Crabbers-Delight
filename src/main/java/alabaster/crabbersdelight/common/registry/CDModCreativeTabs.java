package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = CrabbersDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CDModCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CrabbersDelight.MODID);

    public static final Supplier<CreativeModeTab> TAB_CRABBERS_DELIGHT = CREATIVE_TAB.register(FarmersDelight.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.crabbersdelight"))
                    .icon(() -> new ItemStack(CDModItems.RAW_CLAWSTER.get()))
                    .displayItems((parameters, output) -> CDModItems.CREATIVE_TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get())))
                    .build()
    );
}