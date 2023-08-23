package alabaster.crabbersdelight.data.advancement;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.function.Consumer;

public class CDAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
        Advancement crabbersDelight = Advancement.Builder.advancement()
                .display(ModItems.RAW_CLAWSTER.get(),
                        TextUtils.getTranslation("advancement.root"),
                        TextUtils.getTranslation("advancement.root.desc"),
                        new ResourceLocation("minecraft:textures/block/sand.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("seeds", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                .save(consumer, getNameId("main/root"));

        Advancement huntAndGather = getAdvancement(crabbersDelight, ModItems.CRAB_TRAP.get(), "craft_crab_trap", FrameType.TASK, true, true, false)
                .addCriterion("crab_trap", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRAB_TRAP.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, getNameId("main/craft_crab_trap"));
    }

    protected static Advancement.Builder getAdvancement(Advancement parent, ItemLike display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(display,
                TextUtils.getTranslation("advancement." + name),
                TextUtils.getTranslation("advancement." + name + ".desc"),
                null, frame, showToast, announceToChat, hidden);
    }

    private String getNameId(String id) {
        return CrabbersDelight.MODID + ":" + id;
    }
}