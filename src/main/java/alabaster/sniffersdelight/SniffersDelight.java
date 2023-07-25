package alabaster.sniffersdelight;

import alabaster.sniffersdelight.common.registry.ModBlocks;
import alabaster.sniffersdelight.common.registry.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SniffersDelight.MODID)
public class SniffersDelight {
    public static final String MODID = "sniffersdelight";
    public static final Logger LOGGER = LogManager.getLogger();

    public SniffersDelight() {
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