package alabaster.crabbersdelight.common.utils;

import net.minecraft.world.entity.projectile.FishingHook;

import java.lang.reflect.Field;

public class FishingHookReflection {
    private static Field bitingField;
    private static Field timeUntilLuredField;
    private static Field nibbleField;

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

        try {
            nibbleField = FishingHook.class.getDeclaredField("nibble");
            nibbleField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            nibbleField = null;
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

    public static void capLureTime(FishingHook hook, int maxTicks) {
        if (timeUntilLuredField == null) {
            return;
        }
        try {
            int current = timeUntilLuredField.getInt(hook);
            if (current > maxTicks) {
                timeUntilLuredField.setInt(hook, maxTicks);
            }
        } catch (IllegalAccessException ignored) {
        }
    }

    public static void extendNibbleTime(FishingHook hook, int minTicks) {
        if (nibbleField == null) {
            return;
        }
        try {
            int current = nibbleField.getInt(hook);
            if (current > 0 && current < minTicks) {
                nibbleField.setInt(hook, minTicks);
            }
        } catch (IllegalAccessException ignored) {
        }
    }
}