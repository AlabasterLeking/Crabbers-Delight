package alabaster.crabbersdelight;

import alabaster.crabbersdelight.client.gui.CrabTrapGUI;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import alabaster.crabbersdelight.client.renderer.CrabRenderer;
import alabaster.crabbersdelight.common.Config;
import alabaster.crabbersdelight.common.event.CDSpriteSourceProvider;
import alabaster.crabbersdelight.common.registry.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vectorwing.farmersdelight.client.event.ClientSetupEvents;


import java.util.concurrent.CompletableFuture;

@Mod(CrabbersDelight.MODID)
public class CrabbersDelight {
    public static final String MODID = "crabbersdelight";
    public static final Logger LOGGER = LogManager.getLogger();

    public CrabbersDelight(IEventBus bus, ModContainer modContainer) {

        if (FMLEnvironment.dist.isClient()) {
            bus.addListener(ClientSetupEvents::init);
        }

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModBlockEntity.BLOCK_ENTITY_TYPES.register(bus);
        ModMenus.MENU.register(bus);
        ModCreativeTabs.CREATIVE_TAB.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModPotions.POTIONS.register(bus);

        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    public static ResourceLocation modPrefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, path);
    }

    public void gatherData(GatherDataEvent event) {
        boolean includeClient = event.includeClient();
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(includeClient, new CDSpriteSourceProvider(packOutput, lookupProvider, fileHelper));
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.CRAB.get(), CrabRenderer::new);
        }
    }
}