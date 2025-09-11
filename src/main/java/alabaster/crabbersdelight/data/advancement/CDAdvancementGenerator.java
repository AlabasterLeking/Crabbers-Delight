package alabaster.crabbersdelight.data.advancement;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModItems;
import alabaster.crabbersdelight.common.utils.TextUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

public class CDAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
        Advancement crabbersDelight = Advancement.Builder.advancement()
                .display(CDModItems.RAW_CLAWSTER.get(),
                        TextUtil.getTranslation("advancement.root"),
                        TextUtil.getTranslation("advancement.root.desc"),
                        new ResourceLocation("minecraft:textures/block/sand.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("seeds", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                .save(consumer, getNameId("main/root"));

        Advancement itsATrap = getAdvancement(crabbersDelight, CDModItems.CRAB_TRAP.get(), "craft_crab_trap", FrameType.TASK, true, true, false)
                .addCriterion("crab_trap", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CRAB_TRAP.get()))
                .save(consumer, getNameId("main/craft_crab_trap"));

        Advancement crustaceanCookingStation = getAdvancement(itsATrap, CDModItems.RAW_CRAB.get(), "cook_crustaceans", FrameType.TASK, true, true, false)
                .addCriterion("crab", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.COOKED_CRAB.get()))
                .addCriterion("shrimp", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.COOKED_SHRIMP.get()))
                .addCriterion("clawster", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.COOKED_CLAWSTER.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, getNameId("main/cook_crustaceans"));

        Advancement motherOfPearl = getAdvancement(itsATrap, CDModItems.CLAM.get(), "get_pearl", FrameType.TASK, true, true, false)
                .addCriterion("pearl", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PEARL.get()))
                .save(consumer, getNameId("main/get_pearl"));

        Advancement aShrimpFriedThisRice = getAdvancement(itsATrap, CDModItems.SHRIMP_FRIED_RICE.get(), "a_shrimp_fried_this_rice", FrameType.CHALLENGE, true, true, true)
                .addCriterion("shrimp_fried_rice", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.SHRIMP_FRIED_RICE.get()))
                .save(consumer, getNameId("main/a_shrimp_fried_this_rice"));

        Advancement reachForTheSeaStars = getAdvancement(crabbersDelight, CDModItems.CRAB_CLAW.get(), "reach_for_the_sea_stars", FrameType.GOAL, true, true, false)
                .addCriterion("crab_claw", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CRAB_CLAW.get()))
                .save(consumer, getNameId("main/reach_for_the_sea_stars"));

        Advancement frogKiller = getAdvancement(crabbersDelight, CDModItems.RAW_FROG_LEG.get(), "frog_killer", FrameType.TASK, true, true, false)
                .addCriterion("raw_frog_legs", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.RAW_FROG_LEG.get()))
                .save(consumer, getNameId("main/frog_killer"));

        Advancement squidKiller = getAdvancement(crabbersDelight, CDModItems.RAW_SQUID_TENTACLES.get(), "squid_killer", FrameType.TASK, true, true, false)
                .addCriterion("raw_squid_tentacles", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.RAW_SQUID_TENTACLES.get()))
                .save(consumer, getNameId("main/squid_killer"));

        Advancement glowSquidKiller = getAdvancement(squidKiller, CDModItems.RAW_GLOW_SQUID_TENTACLES.get(), "glow_squid_killer", FrameType.GOAL, true, true, false)
                .addCriterion("raw_glow_squid_tentacles", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.RAW_GLOW_SQUID_TENTACLES.get()))
                .save(consumer, getNameId("main/glow_squid_killer"));
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