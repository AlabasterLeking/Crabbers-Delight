package alabaster.crabbersdelight.integration.jei;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class CrabTrapWrapper {
    public ItemStack input;
    public Ingredient output;
    public CrabTrapWrapper(ItemStack input, Ingredient output) {
        this.input = input;
        this.output = output;
    }

    public ItemStack getInput() {
        return input;
    }

    public Ingredient getOutput() {
        return output;
    }
}