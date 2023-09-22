package alabaster.crabbersdelight.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.data.BlockTags;

@Mod.EventBusSubscriber(modid = CrabbersDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if (event.includeServer()) {
            BlockTags blockTags = new BlockTags(generator, CrabbersDelight.MODID, helper);
            generator.addProvider(blockTags);
            generator.addProvider(new ItemTags(generator, blockTags, CrabbersDelight.MODID, helper));
            generator.addProvider(new Recipes(generator));
            generator.addProvider(new Advancements(generator));

            BlockStates blockStates = new BlockStates(generator, helper);
            generator.addProvider(blockStates);
            generator.addProvider(new ItemModels(generator, blockStates.models().existingFileHelper));
        }
    }
}