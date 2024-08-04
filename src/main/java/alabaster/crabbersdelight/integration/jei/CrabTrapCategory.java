package alabaster.crabbersdelight.integration.jei;

import com.google.common.collect.ImmutableList;
import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.entity.CrabTrapBlockEntity;
import alabaster.crabbersdelight.common.registry.ModItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CrabTrapCategory implements IRecipeCategory<CrabTrapRecipeWrapper> {
    private static final ResourceLocation CRAB_TRAP_LOCATION = CrabbersDelight.modPrefix("textures/gui/jei_crab_trap.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final Component title;
    public CrabTrapCategory(IGuiHelper helper) {
        this.title = Component.translatable(CrabbersDelight.MODID + ".jei." + getUid().getPath());
        this.background = helper.createDrawable(CRAB_TRAP_LOCATION, 0, 0, 79, 39);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.CRAB_TRAP.get()));
    }

    @Override
    public RecipeType<CrabTrapRecipeWrapper> getRecipeType() {
        return JEIPlugin.CRAB_TRAP_RECIPE;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    private static boolean iconPosition(double mouseX, double mouseY) {
        int iconPosX = 32;
        int iconPosY = 2;
        int iconHeight = 10;
        int iconWidth = 15;
        return iconPosX <= mouseX && mouseX < iconPosX + iconWidth && iconPosY <= mouseY && mouseY < iconPosY + iconHeight;
    }

    @Override
    public List<Component> getTooltipStrings(CrabTrapRecipeWrapper recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (iconPosition(mouseX, mouseY)) {
            if (CrabTrapBlockEntity.getMinMax().getSecond() > CrabTrapBlockEntity.getMinMax().getFirst()) {
                return ImmutableList.of(Component.literal("Collects every " + CrabTrapBlockEntity.getMinMax().getFirst() + "-" + CrabTrapBlockEntity.getMinMax().getSecond() + " ticks"));
            }
            return ImmutableList.of(Component.literal("Error: Minimum value is higher than maximum value!").withStyle(ChatFormatting.RED));
        }
        return Collections.emptyList();
    }

    public ResourceLocation getUid() {
        return this.getRecipeType().getUid();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrabTrapRecipeWrapper recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 7, 16).addItemStack(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 57, 16).addItemStacks(Arrays.stream(recipe.getOutput().getItems()).toList());
    }
}