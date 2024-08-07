package alabaster.crabbersdelight.common.item.component;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public class CDItemStackWrapper {

    public static final CDItemStackWrapper EMPTY = new CDItemStackWrapper(ItemStack.EMPTY);
    public static final Codec<CDItemStackWrapper> CODEC = ItemStack.OPTIONAL_CODEC.xmap(CDItemStackWrapper::new, CDItemStackWrapper::getStack);
    public static final StreamCodec<RegistryFriendlyByteBuf, CDItemStackWrapper> STREAM_CODEC =
            ItemStack.OPTIONAL_STREAM_CODEC.map(CDItemStackWrapper::new, (itemStackWrapper) -> itemStackWrapper.itemStack);

    private final ItemStack itemStack;
    private final int hashCode;

    public CDItemStackWrapper(ItemStack stack) {
        this.itemStack = stack;
        this.hashCode = ItemStack.hashItemAndComponents(stack);
    }

    public ItemStack getStack() {
        return this.itemStack;
    }

    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof CDItemStackWrapper itemStackWrapper) {
            return ItemStack.matches(this.itemStack, itemStackWrapper.getStack());
        }
        return false;
    }
}
