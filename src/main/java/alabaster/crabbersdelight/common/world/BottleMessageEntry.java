package alabaster.crabbersdelight.common.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

public record BottleMessageEntry(String text, String author, Optional<String> title, Optional<String> rewardItem, int rewardCount) {
    public static final Codec<BottleMessageEntry> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("text").forGetter(BottleMessageEntry::text),
                    Codec.STRING.fieldOf("author").forGetter(BottleMessageEntry::author),
                    Codec.STRING.optionalFieldOf("title").forGetter(BottleMessageEntry::title),
                    Codec.STRING.optionalFieldOf("reward_item").forGetter(BottleMessageEntry::rewardItem),
                    Codec.INT.optionalFieldOf("reward_count", 1).forGetter(BottleMessageEntry::rewardCount)
            ).apply(instance, BottleMessageEntry::new)
    );
}