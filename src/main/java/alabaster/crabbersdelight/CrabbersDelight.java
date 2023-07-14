package alabaster.crabbersdelight;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import alabaster.crabbersdelight.common.registry.*;

import javax.annotation.Nonnull;


@Mod(CrabbersDelight.MODID)
public class CrabbersDelight
{
    public static final String MODID = "crabbersdelight";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(CrabbersDelight.MODID)
    {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.RAW_CRAB.get());
        }
    };

    public CrabbersDelight() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}