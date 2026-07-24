package alabaster.crabbersdelight.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

public record SignedNoteContent(String title, String text, String author, int generation) {
    public static final int GENERATION_ORIGINAL = 0;
    public static final int GENERATION_COPY = 1;
    public static final int GENERATION_COPY_OF_COPY = 2;
    public static final int GENERATION_TATTERED = 3;

    public static final Codec<SignedNoteContent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("title").forGetter(SignedNoteContent::title),
            Codec.STRING.fieldOf("text").forGetter(SignedNoteContent::text),
            Codec.STRING.fieldOf("author").forGetter(SignedNoteContent::author),
            Codec.INT.fieldOf("generation").forGetter(SignedNoteContent::generation)
    ).apply(instance, SignedNoteContent::new));

    public static final StreamCodec<ByteBuf, SignedNoteContent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SignedNoteContent::title,
            ByteBufCodecs.STRING_UTF8, SignedNoteContent::text,
            ByteBufCodecs.STRING_UTF8, SignedNoteContent::author,
            ByteBufCodecs.VAR_INT, SignedNoteContent::generation,
            SignedNoteContent::new
    );

    @Nullable
    public SignedNoteContent tryCraftCopy() {
        return switch (generation) {
            case GENERATION_ORIGINAL -> new SignedNoteContent(title, text, author, GENERATION_COPY);
            case GENERATION_COPY -> new SignedNoteContent(title, text, author, GENERATION_COPY_OF_COPY);
            case GENERATION_COPY_OF_COPY -> new SignedNoteContent(title, text, author, GENERATION_TATTERED);
            default -> null;
        };
    }
}