package alabaster.crabbersdelight.common.entity.crab;

import net.minecraft.world.item.DyeColor;

import java.util.Arrays;
import java.util.Comparator;

public enum CrabVariant {
    BLACK(0),
    BLUE(1),
    BROWN(2),
    CYAN(3),
    GRAY(4),
    GREEN(5),
    LIGHT_BLUE(6),
    LIGHT_GRAY(7),
    LIME(8),
    MAGENTA(9),
    ORANGE(10),
    PINK(11),
    PURPLE(12),
    RED(13),
    WHITE(14),
    YELLOW(15);

    private static final CrabVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(CrabVariant::getId)).toArray(CrabVariant[]::new);
    private final int id;

    CrabVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CrabVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static CrabVariant fromDyeColor(DyeColor color) {
        return switch (color) {
            case BLACK -> CrabVariant.BLACK;
            case BLUE -> CrabVariant.BLUE;
            case BROWN -> CrabVariant.BROWN;
            case CYAN -> CrabVariant.CYAN;
            case GRAY -> CrabVariant.GRAY;
            case GREEN -> CrabVariant.GREEN;
            case LIGHT_BLUE -> CrabVariant.LIGHT_BLUE;
            case LIGHT_GRAY -> CrabVariant.LIGHT_GRAY;
            case LIME -> CrabVariant.LIME;
            case MAGENTA -> CrabVariant.MAGENTA;
            case ORANGE -> CrabVariant.ORANGE;
            case PINK -> CrabVariant.PINK;
            case PURPLE -> CrabVariant.PURPLE;
            case RED -> CrabVariant.RED;
            case WHITE -> CrabVariant.WHITE;
            case YELLOW -> CrabVariant.YELLOW;
        };
    }
}
