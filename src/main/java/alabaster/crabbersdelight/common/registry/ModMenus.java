package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.container.CrabTrapMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENU = DeferredRegister.create(ForgeRegistries.CONTAINERS, CrabbersDelight.MODID);

    public static final RegistryObject<MenuType<CrabTrapMenu>> CRAB_TRAP_MENU = MENU.register("crab_trap_menu",
            () -> IForgeMenuType.create(CrabTrapMenu::new));
}