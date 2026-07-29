package alabaster.crabbersdelight.common.item.component;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record TackleBoxContents(List<ItemStack> items) {
    public static final TackleBoxContents EMPTY = new TackleBoxContents(List.of());

    public static final Codec<TackleBoxContents> CODEC =
            ItemStack.OPTIONAL_CODEC.listOf().xmap(TackleBoxContents::new, TackleBoxContents::items);

    public static final StreamCodec<RegistryFriendlyByteBuf, TackleBoxContents> STREAM_CODEC =
            ByteBufCodecs.collection(ArrayList::new, ItemStack.OPTIONAL_STREAM_CODEC)
                    .map(TackleBoxContents::new, contents -> new ArrayList<>(contents.items()));
}