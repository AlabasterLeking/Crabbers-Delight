package alabaster.crabbersdelight.common.utils;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class TextUtil {

    private static final MutableComponent NO_EFFECTS = (new TranslatableComponent("effect.none").withStyle(ChatFormatting.GRAY));

    public static MutableComponent getTranslation(String key, Object... args) {
        return (new TranslatableComponent(CrabbersDelight.MODID + "." + key, args));
    }
}