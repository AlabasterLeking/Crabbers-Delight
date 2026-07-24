package alabaster.crabbersdelight.common.item.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record NoteContent(String text) {
    public static final NoteContent EMPTY = new NoteContent("");

    public static final Codec<NoteContent> CODEC = Codec.STRING.xmap(NoteContent::new, NoteContent::text);

    public static final StreamCodec<ByteBuf, NoteContent> STREAM_CODEC =
            ByteBufCodecs.STRING_UTF8.map(NoteContent::new, NoteContent::text);
}