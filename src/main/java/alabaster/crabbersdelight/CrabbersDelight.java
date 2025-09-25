package alabaster.crabbersdelight;

import alabaster.crabbersdelight.client.gui.CrabTrapGUI;
import alabaster.crabbersdelight.common.entity.crab.CrabEntity;
import alabaster.crabbersdelight.common.entity.crab.CrabModel;
import alabaster.crabbersdelight.common.entity.crab.CrabRenderer;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import alabaster.crabbersdelight.common.Config;
import alabaster.crabbersdelight.common.event.CDSpriteSourceProvider;
import alabaster.crabbersdelight.common.registry.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(CrabbersDelight.MODID)
public class CrabbersDelight {
    public static final String MODID = "crabbersdelight";
    public static final Logger LOGGER = LogManager.getLogger();

    public CrabbersDelight(IEventBus bus, ModContainer modContainer) {

        if (FMLEnvironment.dist.isClient()) {
            bus.addListener(this::registerScreens);
        }

        bus.addListener(this::gatherData);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        CDModItems.ITEMS.register(bus);
        CDModBlocks.BLOCKS.register(bus);
        CDModBlockEntity.BLOCK_ENTITY_TYPES.register(bus);
        CDModMenus.MENU.register(bus);
        CDModCreativeTabs.CREATIVE_TAB.register(bus);
        CDModPotions.POTIONS.register(bus);
        CDModEntities.ENTITY_TYPES.register(bus);
        CDTrunkPlacerTypes.TRUNK_PLACERS.register(bus);
        CDFoliagePlacerTypes.FOLIAGE_PLACERS.register(bus);

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

    public void registerScreens(RegisterMenuScreensEvent event) {
        event.register(CDModMenus.CRAB_TRAP_MENU.get(), CrabTrapGUI::new);
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(CDModEntities.CRAB.get(), CrabRenderer::new);
        }

        @SubscribeEvent
        public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
            event.register(
                    CDModEntities.CRAB.get(),
                    SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    CrabEntity::checkCrabSpawnRules,
                    RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(CDModBlockEntity.PALM_SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(CDModBlockEntity.HANGING_PALM_SIGN.get(), HangingSignRenderer::new);
        }
    }


    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
    public class ModEventBusEvents {
        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(CrabModel.LAYER_LOCATION, CrabModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(CDModEntities.CRAB.get(), CrabEntity.createAttributes().build());
        }
    }
}