package alabaster.crabbersdelight;

import alabaster.crabbersdelight.client.gui.CrabTrapGUI;
import alabaster.crabbersdelight.common.Config;
import alabaster.crabbersdelight.common.entity.boat.CDBoatModelLayers;
import alabaster.crabbersdelight.common.entity.boat.CDBoatRenderer;
import alabaster.crabbersdelight.common.entity.crab.CrabEntity;
import alabaster.crabbersdelight.common.entity.crab.CrabModel;
import alabaster.crabbersdelight.common.entity.crab.CrabRenderer;
import alabaster.crabbersdelight.common.event.CDSpriteSourceProvider;
import alabaster.crabbersdelight.common.registry.*;
import alabaster.crabbersdelight.common.utils.CDStateProviders;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(CrabbersDelight.MODID)
public class CrabbersDelight {
    public static final String MODID = "crabbersdelight";
    public static final Logger LOGGER = LogManager.getLogger();

    public CrabbersDelight() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        CDModItems.ITEMS.register(bus);
        CDModBlocks.BLOCKS.register(bus);
        CDModBlockEntity.BLOCK_ENTITY_TYPE.register(bus);
        CDModMenus.MENU.register(bus);
        CDModCreativeTabs.CREATIVE_TAB.register(bus);
        CDModEntities.ENTITIES.register(bus);
        CDModPotions.POTIONS.register(bus);
        CDTrunkPlacerTypes.TRUNK_PLACERS.register(bus);
        CDFoliagePlacerTypes.FOLIAGE_PLACERS.register(bus);
        CDStateProviders.PROVIDERS.register(bus);

        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(CDModMenus.CRAB_TRAP_MENU.get(), CrabTrapGUI::new));
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> PotionBrewing.addMix(Potions.AWKWARD, Items.INK_SAC, CDModPotions.INKY_POTION.get()));
    }

    public static ResourceLocation modPrefix(String path) {
        return new ResourceLocation(CrabbersDelight.MODID, path);
    }

    public void gatherData(GatherDataEvent event) {
        boolean includeClient = event.includeClient();
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(includeClient, new CDSpriteSourceProvider(packOutput, fileHelper));
    }

    @Mod.EventBusSubscriber (modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(CDModEntities.CRAB.get(), CrabRenderer::new);
            EntityRenderers.register(CDModEntities.MOD_BOAT.get(), pContext -> new CDBoatRenderer(pContext, false));
            EntityRenderers.register(CDModEntities.MOD_CHEST_BOAT.get(), pContext -> new CDBoatRenderer(pContext, true));
        }

        @SubscribeEvent
        public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
            event.register(
                    CDModEntities.CRAB.get(),
                    SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    CrabEntity::checkCrabSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.REPLACE);
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(CDModBlockEntity.PALM_SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(CDModBlockEntity.HANGING_PALM_SIGN.get(), HangingSignRenderer::new);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class ModEventBusEvents {
        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(CrabModel.LAYER_LOCATION, CrabModel::createBodyLayer);
            event.registerLayerDefinition(CDBoatModelLayers.PALM_BOAT_LAYER, BoatModel::createBodyModel);
            event.registerLayerDefinition(CDBoatModelLayers.PALM_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(CDModEntities.CRAB.get(), CrabEntity.createAttributes().build());
        }
    }
}