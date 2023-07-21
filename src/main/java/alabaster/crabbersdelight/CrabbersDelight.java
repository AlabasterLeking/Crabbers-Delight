package alabaster.crabbersdelight;

import alabaster.crabbersdelight.common.registry.ModBlocks;
import alabaster.crabbersdelight.common.registry.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;

@Mod(CrabbersDelight.MODID)
public class CrabbersDelight {
    public static final String MODID = "crabbersdelight";

    public CrabbersDelight() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
    }

    public static final CreativeModeTab TAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return ModItems.RAW_CRAB.get().getDefaultInstance();
        }
    };
}