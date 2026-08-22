package alabaster.crabbersdelight.common.mixin;

import alabaster.crabbersdelight.common.Config;
import alabaster.crabbersdelight.common.item.component.FishSize;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;

import java.util.List;

@Mixin(CuttingBoardRecipe.class)
public abstract class CuttingBoardRecipeMixin {

    @Inject(method = "rollResults", at = @At("RETURN"))
    private void crabbersdelight$scaleByFishSize(RandomSource random, int fortuneLevel, RecipeWrapper input, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (!Config.FISH_SIZE_ENABLED.get()) {
            return;
        }
        FishSize size = input.getItem(0).get(CDModDataComponents.FISH_SIZE.get());
        if (size == null) {
            return;
        }
        for (ItemStack stack : cir.getReturnValue()) {
            int scaledCount = Math.max(1, Math.round(stack.getCount() * size.getMultiplier()));
            stack.setCount(scaledCount);
        }
    }
}