package alabaster.crabbersdelight.data.recipe;

import alabaster.crabbersdelight.common.registry.ModPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

public class BrewingRecipes {

    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {

        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(
                Potions.AWKWARD,
                Items.INK_SAC,
                INKY_POTION
        );
    }
}
