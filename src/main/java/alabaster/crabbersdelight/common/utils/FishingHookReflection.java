package alabaster.crabbersdelight.common.utils;

import net.minecraft.world.entity.projectile.FishingHook;

import java.lang.reflect.Field;

public class FishingHookReflection {
    private static Field bitingField;

    static {
        try {
            bitingField = FishingHook.class.getDeclaredField("biting");
            bitingField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            bitingField = null;
        }
    }

    public static boolean isBiting(FishingHook hook) {
        if (bitingField == null) {
            return false;
        }
        try {
            return bitingField.getBoolean(hook);
        } catch (IllegalAccessException e) {
            return false;
        }
    }
}