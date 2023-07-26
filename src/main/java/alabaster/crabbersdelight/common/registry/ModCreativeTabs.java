package alabaster.crabbersdelight.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import alabaster.crabbersdelight.CrabbersDelight;

public class ModCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CrabbersDelight.MODID);

    public static final RegistryObject<CreativeModeTab> TAB_FARMERS_DELIGHT = CREATIVE_TABS.register(CrabbersDelight.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.crabbersdelight"))
                    .icon(() -> new ItemStack(ModItems.RAW_CLAWSTER.get()))
                    .build());
}