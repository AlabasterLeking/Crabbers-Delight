package alabaster.crabbersdelight.common.utils;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TextUtil {

    private static final MutableComponent NO_EFFECTS = Component.translatable("effect.none").withStyle(ChatFormatting.GRAY);

    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable(CrabbersDelight.MODID + "." + key, args);
    }
}