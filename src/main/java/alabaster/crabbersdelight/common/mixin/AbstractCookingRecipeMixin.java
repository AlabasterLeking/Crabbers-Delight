package alabaster.crabbersdelight.common.mixin;

import alabaster.crabbersdelight.common.Config;
import alabaster.crabbersdelight.common.event.FishSizeEvents;
import alabaster.crabbersdelight.common.item.component.FishSize;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCookingRecipe.class)
public abstract class AbstractCookingRecipeMixin {

    @Inject(method = "assemble", at = @At("RETURN"))
    private void crabbersdelight$scaleByFishSize(SingleRecipeInput input, HolderLookup.Provider registries, CallbackInfoReturnable<ItemStack> cir) {
        if (!((Object) this instanceof CampfireCookingRecipe)) {
            return;
        }
        if (!Config.FISH_SIZE_ENABLED.get()) {
            return;
        }
        FishSize size = input.item().get(CDModDataComponents.FISH_SIZE.get());
        if (size == null) {
            return;
        }
        ItemStack output = cir.getReturnValue();
        if (!output.is(ItemTags.FISHES)) {
            return;
        }
        output.set(CDModDataComponents.FISH_SIZE.get(), size);
        FishSizeEvents.applyScaledFood(output, size);
    }
}