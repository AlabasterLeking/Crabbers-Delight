package alabaster.crabbersdelight.common.utils;

import net.minecraft.world.entity.projectile.FishingHook;

import java.lang.reflect.Field;

public class FishingHookReflection {
    private static Field bitingField;
    private static Field timeUntilLuredField;

    static {
        try {
            bitingField = FishingHook.class.getDeclaredField("biting");
            bitingField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            bitingField = null;
        }

        try {
            timeUntilLuredField = FishingHook.class.getDeclaredField("timeUntilLured");
            timeUntilLuredField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            timeUntilLuredField = null;
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

    public static void speedUpLure(FishingHook hook, int amount) {
        if (timeUntilLuredField == null) {
            return;
        }
        try {
            int current = timeUntilLuredField.getInt(hook);
            if (current > 0) {
                timeUntilLuredField.setInt(hook, Math.max(0, current - amount));
            }
        } catch (IllegalAccessException ignored) {
        }
    }
}