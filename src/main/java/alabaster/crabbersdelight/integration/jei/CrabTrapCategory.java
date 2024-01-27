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
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CrabTrapCategory implements IRecipeCategory<CrabTrapWrapper> {
    private static final ResourceLocation CRAB_TRAP_LOCATION = CrabbersDelight.modPrefix("textures/gui/jei_crab_trap.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final Component title;
    public CrabTrapCategory(IGuiHelper helper) {
        this.title = Component.translatable(CrabbersDelight.MODID + ".jei." + getUid().getPath());
        this.background = helper.createDrawable(CRAB_TRAP_LOCATION, 0, 0, 162, 51);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.CRAB_TRAP.get()));
    }

    @Override
    public RecipeType<CrabTrapWrapper> getRecipeType() {
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

    public ResourceLocation getUid() {
        return this.getRecipeType().getUid();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrabTrapWrapper recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 74, 1).addItemStack(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 1, 34).addItemStacks(Arrays.stream(recipe.getOutput().getItems()).toList());
    }

    @Override
    public List<Component> getTooltipStrings(CrabTrapWrapper recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (iconPosition(mouseX, mouseY)) {
            if (CrabTrapBlockEntity.getMinMax().getSecond() > CrabTrapBlockEntity.getMinMax().getFirst()) {
                return ImmutableList.of(Component.literal("Items are caught every " + CrabTrapBlockEntity.getMinMax().getFirst() + "-" + CrabTrapBlockEntity.getMinMax().getSecond() + " ticks"));
            }
            return ImmutableList.of(Component.literal("Error in Config: Min value must be below Max value").withStyle(ChatFormatting.RED));
        }
        return Collections.emptyList();
    }

    private static boolean iconPosition(double mouseX, double mouseY) {
        int iconPosX = 77;
        int iconPosY = 19;
        int iconHeight = 12;
        int iconWidth = 10;
        return iconPosX <= mouseX && mouseX < iconPosX + iconWidth && iconPosY <= mouseY && mouseY < iconPosY + iconHeight;
    }
}