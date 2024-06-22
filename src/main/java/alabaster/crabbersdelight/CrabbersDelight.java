package alabaster.crabbersdelight;

import alabaster.crabbersdelight.client.gui.CrabTrapGUI;
import alabaster.crabbersdelight.client.renderer.CrabRenderer;
import alabaster.crabbersdelight.common.Config;
import alabaster.crabbersdelight.common.event.CDSpriteSourceProvider;
import alabaster.crabbersdelight.common.event.ModEvents;
import alabaster.crabbersdelight.common.registry.*;
import alabaster.crabbersdelight.data.recipe.BrewingRecipes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
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

        bus.addListener(this::clientSetup);
        bus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModBlockEntity.BLOCK_ENTITY_TYPE.register(bus);
        ModMenus.MENU.register(bus);
        ModCreativeTabs.CREATIVE_TAB.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModPotions.POTIONS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.CRAB_TRAP_MENU.get(), CrabTrapGUI::new);
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BrewingRecipeRegistry.addRecipe(new BrewingRecipes(Potions.AWKWARD, Items.INK_SAC, ModPotions.INKY_POTION.get()));
        });
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

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.CRAB.get(), CrabRenderer::new);
        }
    }
}