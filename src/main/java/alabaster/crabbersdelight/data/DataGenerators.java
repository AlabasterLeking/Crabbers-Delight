package alabaster.crabbersdelight.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.data.loot.CDBlockLoot;
import alabaster.crabbersdelight.data.loot.CDGameplayLoot;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        CDBlockTags blockTags = new CDBlockTags(output, lookupProvider, helper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new CDItemTags(output, lookupProvider, blockTags.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new Recipes(output, lookupProvider));
        generator.addProvider(event.includeServer(), new Advancements(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(CDBlockLoot::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(CDGameplayLoot::new, LootContextParamSets.FISHING)
        ), lookupProvider));

        BlockStates blockStates = new BlockStates(output, helper);
        generator.addProvider(event.includeClient(), blockStates);
        generator.addProvider(event.includeClient(), new ItemModels(output, blockStates.models().existingFileHelper));
        generator.addProvider(event.includeServer(), new DatapackProvider(output, lookupProvider));
    }
}