package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModItems;
import alabaster.crabbersdelight.common.utils.TextUtil;
import com.google.common.collect.Sets;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Advancements extends AdvancementProvider
{
    private final Path PATH;
    public static final Logger LOGGER = LogManager.getLogger();

    public Advancements(DataGenerator generatorIn) {
        super(generatorIn);
        PATH = generatorIn.getOutputFolder();
    }

    @Override
    public void run(CachedOutput cache) {
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            } else {
                Path path1 = getPath(PATH, advancement);

                try {
                    DataProvider.saveStable(cache, advancement.deconstruct().serializeToJson(), path1);
                }
                catch (IOException ioexception) {
                    LOGGER.error("Couldn't save advancement {}", path1, ioexception);
                }
            }
        };

        new CrabbersDelightAdvancements().accept(consumer);
    }

    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    public static class CrabbersDelightAdvancements implements Consumer<Consumer<Advancement>>
    {
        @Override
        @SuppressWarnings("unused")
        public void accept(Consumer<Advancement> consumer) {
            Advancement crabbersDelight = Advancement.Builder.advancement()
                    .display(ModItems.RAW_CLAWSTER.get(),
                            TextUtil.getTranslation("advancement.root"),
                            TextUtil.getTranslation("advancement.root.desc"),
                            new ResourceLocation("minecraft:textures/block/sand.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion("seeds", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                    .save(consumer, getNameId("main/root"));

            Advancement itsATrap = getAdvancement(crabbersDelight, ModItems.CRAB_TRAP.get(), "craft_crab_trap", FrameType.TASK, true, true, false)
                    .addCriterion("crab_trap", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRAB_TRAP.get()))
                    .save(consumer, getNameId("main/craft_crab_trap"));

            Advancement crustaceanCookingStation = getAdvancement(itsATrap, ModItems.RAW_CRAB.get(), "cook_crustaceans", FrameType.TASK, true, false, false)
                    .addCriterion("crab", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COOKED_CRAB.get()))
                    .addCriterion("shrimp", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COOKED_SHRIMP.get()))
                    .addCriterion("clawster", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COOKED_CLAWSTER.get()))
                    .requirements(RequirementsStrategy.OR)
                    .save(consumer, getNameId("main/cook_crustaceans"));

            Advancement motherOfPearl = getAdvancement(itsATrap, ModItems.CLAM.get(), "get_pearl", FrameType.TASK, true, false, false)
                    .addCriterion("pearl", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PEARL.get()))
                    .save(consumer, getNameId("main/get_pearl"));
        }

        protected static Advancement.Builder getAdvancement(Advancement parent, ItemLike display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
            return Advancement.Builder.advancement().parent(parent).display(display,
                    TextUtil.getTranslation("advancement." + name),
                    TextUtil.getTranslation("advancement." + name + ".desc"),
                    null, frame, showToast, announceToChat, hidden);
        }

        private String getNameId(String id) {
            return CrabbersDelight.MODID + ":" + id;
        }
    }
}