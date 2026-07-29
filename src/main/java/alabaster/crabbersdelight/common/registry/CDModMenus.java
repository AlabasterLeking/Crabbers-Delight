package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.container.CrabTrapMenu;
import alabaster.crabbersdelight.common.block.container.TackleBoxMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CDModMenus {

    public static final DeferredRegister<MenuType<?>> MENU = DeferredRegister.create(BuiltInRegistries.MENU, CrabbersDelight.MODID);

    public static final Supplier<MenuType<CrabTrapMenu>> CRAB_TRAP_MENU = MENU.register("crab_trap",
            () -> IMenuTypeExtension.create(CrabTrapMenu::new));

    public static final Supplier<MenuType<TackleBoxMenu>> TACKLE_BOX_MENU = MENU.register("tackle_box",
            () -> IMenuTypeExtension.create(TackleBoxMenu::new));
}