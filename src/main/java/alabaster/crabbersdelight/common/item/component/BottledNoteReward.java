package alabaster.crabbersdelight.common.item.component;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record BottledNoteReward(ItemStack item) {
    public static final Codec<BottledNoteReward> CODEC =
            ItemStack.OPTIONAL_CODEC.xmap(BottledNoteReward::new, BottledNoteReward::item);

    public static final StreamCodec<RegistryFriendlyByteBuf, BottledNoteReward> STREAM_CODEC =
            ItemStack.OPTIONAL_STREAM_CODEC.map(BottledNoteReward::new, BottledNoteReward::item);
}