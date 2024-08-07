package alabaster.crabbersdelight.integration.jei;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModItems;
import alabaster.crabbersdelight.common.tags.CDModTags;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_ID = CrabbersDelight.modPrefix("jei_plugin");
    public static final RecipeType<CrabTrapRecipeWrapper> CRAB_TRAP_RECIPE = RecipeType.create(CrabbersDelight.MODID, "crab_trap_loot", CrabTrapRecipeWrapper.class);

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CrabTrapCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(CRAB_TRAP_RECIPE, addWrappers());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModItems.CRAB_TRAP.get()), CRAB_TRAP_RECIPE);
    }

    public List<CrabTrapRecipeWrapper> addWrappers() {
        List<CrabTrapRecipeWrapper> list = new ArrayList<>();
        for (ItemStack item : BuiltInRegistries.ITEM.stream().map(ItemStack::new).toList()) {
            if (item.is(CDModTags.CRAB_TRAP_BAIT)) {
                ResourceLocation registryName = BuiltInRegistries.ITEM.getKey(item.getItem());
                TagKey<Item> outputTag = TagKey.create(Registries.ITEM, CrabbersDelight.modPrefix("jei_display_results/" + registryName.getNamespace() + "/" + registryName.getPath()));
                if (BuiltInRegistries.ITEM.getTag(outputTag).isPresent()) {
                    list.add(new CrabTrapRecipeWrapper(item, Ingredient.of(outputTag)));
                }

            }
        }
        return list;
    }
}
