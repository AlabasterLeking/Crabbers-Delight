package alabaster.crabbersdelight.common.item.component;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record CrabTrapContents(List<ItemStack> items) {
    public static final CrabTrapContents EMPTY = new CrabTrapContents(List.of());

    public static final Codec<CrabTrapContents> CODEC =
            ItemStack.OPTIONAL_CODEC.listOf().xmap(CrabTrapContents::new, CrabTrapContents::items);

    public static final StreamCodec<RegistryFriendlyByteBuf, CrabTrapContents> STREAM_CODEC =
            ByteBufCodecs.collection(ArrayList::new, ItemStack.OPTIONAL_STREAM_CODEC)
                    .map(CrabTrapContents::new, contents -> new ArrayList<>(contents.items()));
}