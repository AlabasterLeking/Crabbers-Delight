package alabaster.crabbersdelight.common.item.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public enum FishSize implements StringRepresentable {
    TINY("tiny", 0.25f, ChatFormatting.RED),
    SMALL("small", 0.5f, ChatFormatting.YELLOW),
    REGULAR("regular", 1.0f, ChatFormatting.WHITE),
    LARGE("large", 1.5f, ChatFormatting.GREEN),
    HUGE("huge", 2.0f, ChatFormatting.GOLD);

    public static final Codec<FishSize> CODEC = StringRepresentable.fromEnum(FishSize::values);
    public static final StreamCodec<ByteBuf, FishSize> STREAM_CODEC =
            ByteBufCodecs.VAR_INT.map(id -> values()[id], FishSize::ordinal);

    private final String serializedName;
    private final float multiplier;
    private final ChatFormatting color;

    FishSize(String serializedName, float multiplier, ChatFormatting color) {
        this.serializedName = serializedName;
        this.multiplier = multiplier;
        this.color = color;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public ChatFormatting getColor() {
        return color;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }
}