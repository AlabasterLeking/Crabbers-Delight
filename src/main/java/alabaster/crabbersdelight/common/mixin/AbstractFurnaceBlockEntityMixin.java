package alabaster.crabbersdelight.common.mixin;

import alabaster.crabbersdelight.common.Config;
import alabaster.crabbersdelight.common.event.FishSizeEvents;
import alabaster.crabbersdelight.common.item.component.FishSize;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 2;

    @Unique
    private static FishSize crabbersdelight$capturedSize;


    @Inject(method = "burn", at = @At("HEAD"))
    private static void crabbersdelight$captureInputSize(RegistryAccess registryAccess, RecipeHolder<?> recipe, NonNullList<ItemStack> inventory, int maxStackSize, AbstractFurnaceBlockEntity furnace, CallbackInfoReturnable<Boolean> cir) {
        crabbersdelight$capturedSize = inventory.get(INPUT_SLOT).get(CDModDataComponents.FISH_SIZE.get());
    }

    @Inject(method = "burn", at = @At("RETURN"))
    private static void crabbersdelight$applyOutputSize(RegistryAccess registryAccess, RecipeHolder<?> recipe, NonNullList<ItemStack> inventory, int maxStackSize, AbstractFurnaceBlockEntity furnace, CallbackInfoReturnable<Boolean> cir) {
        FishSize size = crabbersdelight$capturedSize;
        crabbersdelight$capturedSize = null;

        if (size == null || !cir.getReturnValue() || !Config.FISH_SIZE_ENABLED.get()) {
            return;
        }

        ItemStack output = inventory.get(OUTPUT_SLOT);
        if (!output.is(ItemTags.FISHES)) {
            return;
        }
        output.set(CDModDataComponents.FISH_SIZE.get(), size);
        FishSizeEvents.applyScaledFood(output, size);
    }
}